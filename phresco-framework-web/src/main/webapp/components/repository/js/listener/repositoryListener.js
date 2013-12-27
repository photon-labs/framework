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
			if (action === "browseSourceRepo") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + 'repository/browseSourceRepo?userId='+userId+'&customerId='+customerId+'&projectId='+projectId+'&userName=loheswaran_g&password=SaveAnimals56';
			}
			if (action === "artifactInfo") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + 'repository/artifactInfo?userId='+userId+'&customerId='+customerId+'&appDirName='+requestBody.appDirName+'&version='+requestBody.version+'&nature='+requestBody.nature;
				if (!self.isBlank(requestBody.moduleName)) {
					header.webserviceurl = header.webserviceurl + "&moduleName="+requestBody.moduleName
				}
			}
			if (action === "downloadBuild") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + 'repository/download?userId='+userId+'&customerId='+customerId+'&appDirName='+requestBody.appDirName+'&version='+requestBody.version+'&nature='+requestBody.nature;
				if (!self.isBlank(requestBody.moduleName)) {
					header.webserviceurl = header.webserviceurl + "&moduleName="+requestBody.moduleName
				}
			}
			
			return header;
		},
		
		getxmlDoc : function(xmlStr, callback) {
			var parseXml;
			if (window.DOMParser) {
				parseXml = function(xmlStr) {
					return ( new window.DOMParser() ).parseFromString(xmlStr, "text/xml");
				};
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
						}, "plugins" : [ "themes", "html_data", "ui" ]}).bind("loaded.jstree");
						
						$('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
						$('.tree li.parent_li > span').unbind('click');
						$('.tree li.parent_li > span').bind('click', function (e) {
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
							var currentTab = commonVariables.navListener.currentTab;
							if (currentTab === "buildRepo") {
								var appDirName = $(this).find('a').attr('appdirname');
								var nature = $(this).find('a').attr('nature');
								var moduleName = $(this).find('a').attr('modulename');
								var version = $(this).parent().parent().prev().find('a').text();
								if (appDirName !== undefined && nature !== undefined) {
									self.getArtifactInformation(appDirName, nature, version, moduleName);
								}
							}
							if (currentTab === "sourceRepo") {
								
							}
						});
					}, 500);
				});
			});
		},
		
		getArtifactInformation : function(appDirName, nature, version, moduleName) {
			var self = this;
			var requestBody = {};
			requestBody.appDirName = appDirName;
			requestBody.nature = nature;
			requestBody.version = version;
			requestBody.moduleName = moduleName;
			self.repository(self.getActionHeader(requestBody, "artifactInfo"), function(response) {
				var responseData = response.data;
				if (responseData !== undefined && responseData !== null) {
					$('#infoMessagedisp').hide();
					$('.file_view').show();
					var artifactInfo = responseData.artifactInfo;
					$('#repoPath').text(artifactInfo.repositoryPath);
					$('#uploader').text(artifactInfo.uploader);
					$('#size').text((artifactInfo.size/1024).toFixed(2) + " KB");
					$('#uploadedDate').text(new Date(artifactInfo.uploaded));
					$('#modifiedDate').text(new Date(artifactInfo.lastChanged));
					var mavenInfo = responseData.mavenInfo;
					$('#groupId').text(mavenInfo.groupId);
					$('#artifactId').text(mavenInfo.artifactId);
					$('#artifactVersion').text(mavenInfo.version);
					$('#artifactType').text(mavenInfo.extension);
					var xmlContent = "<dependency>" + 
										"<groupId>" + mavenInfo.groupId + "</groupId>" +
					  					"<artifactId>"+ mavenInfo.artifactId +"</artifactId>" +
				  						"<version>" + mavenInfo.version + "</version>";
					if (!self.isBlank(mavenInfo.extension)) {
						xmlContent = xmlContent + "<type>"+ mavenInfo.extension +"</type"
					}
					xmlContent = xmlContent + "</dependency>";
					$('#artifactXml').val(xmlContent);
				}
			});
			
			$("input[name=downloadArtifact]").attr("appdirname", appDirName).attr("nature", nature).attr("version", version).attr("moduleName", moduleName);
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
					var moduleName = $(value).attr('moduleName');
					var appDirName = $(value).attr('appDirName');
					var nature = $(value).attr('nature');
					strItems += '<li role=treeItem class="parent_li hideContent">' + '<span class="badge badge-warning" title="Expand this branch">'+ '<i></i><a ';
					if (moduleName !== undefined) {
						strItems += 'moduleName="'+moduleName+'"';
					}
					if (appDirName !== undefined) {
						strItems += 'appDirName="'+appDirName+'"';
					}
					if (nature !== undefined) {
						strItems += 'nature="'+nature+'"';
					}
					strItems += '>' + $(value).attr('name') + '</a></span></li>';
				}
			});
			
			strUl = '<ul role=group >' + strItems + '</ul>';
			callback(strUl); 
		}
	});

	return Clazz.com.components.repository.js.listener.RepositoryListener;
});