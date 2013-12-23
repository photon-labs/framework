define([], function() {

	Clazz.createPackage("com.components.repository.js.listener");

	Clazz.com.components.repository.js.listener.RepositoryListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		initialize : function(config) {
			var self = this;
		},
		
		repository : function(header, callback) {
			var self = this;
			try {
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							callback(response);
						} else {
							commonVariables.api.showError(response.responseCode ,"error", true);
						}
					},
					function(textStatus) {
						commonVariables.api.showError("serviceerror" ,"error", true);
					}
				);
			} catch(exception) {
				
			}

		},
		
		getActionHeader : function(requestBody, action) {
			var self=this, header, data = {}, userId, projectId;
			var customerId = self.getCustomer();
			projectId = commonVariables.api.localVal.getSession('projectId');
			customerId = (customerId === "") ? "photon" : customerId;
			data = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			if (data !== undefined && data !== null && data !== "") {
				userId = data.id; 
			}
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			};
			if (action === "browseBuildRepo") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + 'repository/browseBuildRepo?userId='+userId+'&customerId='+customerId+'&projectId='+projectId;
			}
			if (action === "browseGitRepo") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + 'repository/browseGitRepo?userId='+userId+'&customerId='+customerId+'&projectId='+projectId;
			}
			if (action === "artifactInfo") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + 'repository/artifactInfo?userId='+userId+'&customerId='+customerId+'&appDirName='+requestBody.appDirName+'&version='+requestBody.version+'&nature='+requestBody.nature;
			}
			
			return header;
		},
		
		getxmlDoc : function(xmlStr, callback) {
			var parseXml;
			if (window.DOMParser) {
				parseXml = function(xmlStr) {
					return ( new window.DOMParser() ).parseFromString(xmlStr, "text/xml");
				};
			} else if (typeof window.ActiveXObject != "undefined" && new window.ActiveXObject("Microsoft.XMLDOM")) {
				parseXml = function(xmlStr) {
					var xmlDoc = new window.ActiveXObject("Microsoft.XMLDOM");
					xmlDoc.async = "false";
					xmlDoc.loadXML(xmlStr);
					return xmlDoc;
				};
			} else {
				parseXml = function() { return null; }
			}
			callback(parseXml(xmlStr));
		},
		
		constructTree : function(xmlStr) {
			var self = this;
			self.getxmlDoc(xmlStr, function(xmlDoc) {
				var rootItem = $(xmlDoc).contents().children().children();
				self.getList(rootItem, function(returnValue) {
					setTimeout(function() {
						$('.tree').append(returnValue);
						$('.bbtreeview').jstree({"ui" : {
							"select_limit" : 2,
							"select_multiple_modifier" : "alt",
							"selected_parent_close" : "select_parent"
						}, "plugins" : [ "themes", "html_data", "ui" ]}).bind("loaded.jstree", function (event, data) {
							$('span.folder').click(function(e) {
								if ($(this).find("a").attr('class') === "selected") {
									$(this).find("a").removeClass('selected');
									$(this).removeAttr("selected");
								} else {
									$(this).find("a").attr("class", "selected");
									$(this).attr("selected", "selected");
								}
								var path = $(this).parent().attr('value');
								path = path.replace(/\+/g,' ');
							});
						});
						
						$('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
						$('.tree li.parent_li > span').on('click', function (e) {
							var children = $(this).parent('li.parent_li').find(' > ul > li');
							if (children.is(":visible")) {
								children.hide('fast');
								$(this).attr('title', 'Expand this branch').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
							} else {
								children.show('fast');
								var iElement = $(this).attr('title', 'Collapse this branch').find(' > i');
								if (iElement.hasClass('icon-plus-sign')) {
									iElement.addClass('icon-minus-sign').removeClass('icon-plus-sign');
								}
							}
							e.stopPropagation();
						});
						
						$('.badge-success').each(function() {
							var len = $(this).parent().find("li:visible").length;
							if (len > 0) {
								$(this).find("i").removeClass("icon-plus-sign").addClass("icon-minus-sign");
							}
						});
						
						$(".badge-warning").unbind("click");
						$(".badge-warning").bind("click", function() {
							var appDirName = $(this).find('a').attr('appdirname');
							var nature = $(this).find('a').attr('nature');
							var version = $(this).parent().parent().prev().find('a').text()
							self.getArtifactInformation(appDirName, nature, version);
						});
					}, 500);
				});
			});
		},
		
		getArtifactInformation : function(appDirName, nature, version) {
			var self = this;
			var requestBody = {};
			requestBody.appDirName = appDirName;
			requestBody.nature = nature;
			requestBody.version = version;
			self.repository(self.getActionHeader(requestBody, "artifactInfo"), function(response) {
				var responseData = response.data;
				if (responseData !== undefined && responseData !== null) {
					$('#infoMessagedisp').hide();
					$('.file_view').show();
					$('#repoPath').text(responseData.repositoryPath);
					$('#uploader').text(responseData.uploader);
					$('#size').text(responseData.size/1024 + " KB");
					$('#uploadedDate').text(new Date(responseData.uploaded));
					$('#modifiedDate').text(new Date(responseData.lastChanged));
				}
			});
		},
		
		getList : function (ItemList, callback) {
			var self = this;
			var strUl = "", strRoot ="", strItems ="", strCollection = "";
			$(ItemList).each(function(index, value) {
				if ($(value).children().length > 0) {
					strRoot = $(value).attr('name');
					self.getList($(value).children(),function(callback) {
						strCollection = callback;
					});
					strItems += '<li role=treeItem class=parent_li>' + '<span class="badge badge-success" title="Collapse this branch">'+
								'<i class="icon-plus-sign"></i>' + '<a version="'+ strRoot +'">' + strRoot + '</a></span>' + strCollection +'</li>';
				} else {
					strItems += '<li role=treeItem class="parent_li hideContent">' + '<span class="badge badge-warning" title="Expand this branch">'+ '<i></i>' +
								'<a appDirName="'+$(value).attr('appDirName')+'" nature="'+$(value).attr('nature')+'">' + $(value).attr('name') + '</a></span></li>';
				}
			});
			
			strUl = '<ul role=group >' + strItems + '</ul>';
			callback(strUl); 
		}
	});

	return Clazz.com.components.repository.js.listener.RepositoryListener;
});