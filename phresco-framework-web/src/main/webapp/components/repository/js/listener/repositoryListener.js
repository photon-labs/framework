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
						if (response !== null && response.status !== "error") {
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
				header.webserviceurl = commonVariables.webserviceurl + 'repository/browseSourceRepo?userId='+userId+'&customerId='+customerId+'&projectId='+projectId;
			}
			if (action === "saveCredentials") {
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl + 'repository/saveCredentails?url='+requestBody.url+'&username='+requestBody.username+'&password='+requestBody.password;
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
			if (action === "getVersion") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + 'repository/version?appDirName='+requestBody.appDirName+'&currentBranch='+requestBody.currentBranch;
			}
			if(action === "searchlogmessage") {
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(requestBody);
				header.webserviceurl = commonVariables.webserviceurl +"repository/logMessages";
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
							var appDirName = $(this).find('a').attr('appdirname');
							if (currentTab === "buildRepo") {
								var nature = $(this).find('a').attr('nature');
								var moduleName = $(this).find('a').attr('modulename');
								var version = $(this).parent().parent().prev().find('a').text();
								if (appDirName !== undefined && nature !== undefined) {
									self.getArtifactInformation(appDirName, nature, version, moduleName);
								}
							}
							if (currentTab === "sourceRepo") {
								var nature = $(this).find('a').attr("nature");
								if (nature === "branches" || nature === "trunk") {
									$('input[name=selectedAppDirName]').val(appDirName);
									var baseRepoUrl = $(this).find('a').attr("url");
									$("input[name=baseRepoUrl]").val(baseRepoUrl);
									var selectedBranch = $(this).find('a').text();
									$('#fileListHead').text(selectedBranch);
									$("input[name=selectedBranchName]").val(selectedBranch);
									var appDirName = $(this).find('a').attr("appdirname");
									var requestBody = {};
									requestBody.appDirName = appDirName;
									requestBody.currentBranch = selectedBranch;
									self.repository(self.getActionHeader(requestBody, "getVersion"), function(response) {
										var responseData = response.data
										$("#branchFromVersion").val(responseData.currentVersion);
										$("#tagFromVersion").val(responseData.currentVersion);
										$("#tagName").val(responseData.tagVersion);
										$("#releaseVersion").val(responseData.tagVersion);
										$("#releaseTagName").val(responseData.tagVersion);
										$("#devVersion").val(responseData.devVersion);
										$("#createBranchVersion").val(responseData.devVersion);
										$(".file_view").show();
										$('.unit_close').show();
										setTimeout(function() {
											$(this).css("background", "#e3e3e3 !important");
										}, 1000);
									});
								} else {
									$(".file_view").hide();
								}
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
					var url = $(value).attr('url');
					strItems += '<li role="treeItem" class="parent_li">' + '<span class="badge badge-success">'+
								'<i class="icon-plus-sign"></i>' + '<a version="'+ strRoot +'" title="'+ url +'">' + strRoot + '</a></span>' + strCollection +'</li>';
				} else {
					var name = $(value).attr('name');
					var moduleName = $(value).attr('moduleName');
					var appDirName = $(value).attr('appDirName');
					var nature = $(value).attr('nature');
					var url = $(value).attr('url');
					var hideClass = "hideContent";
					if (name === "trunk" || name === "tags" || name === "branches" || name === "SNAPSHOT" || name === "RELEASE") {
						hideClass = "";
					}
					strItems += '<li role="treeItem" class="parent_li '+hideClass+'">' + '<span class="badge badge-warning">'+ '<i></i><a title="'+ name +'" ';
					if (moduleName !== undefined) {
						strItems += 'moduleName="'+moduleName+'"';
					}
					if (appDirName !== undefined) {
						strItems += 'appDirName="'+appDirName+'"';
					}
					if (nature !== undefined) {
						strItems += 'nature="'+nature+'"';
					}
					if (url !== undefined) {
						strItems += 'url="'+url+'"';
					}
					
					strItems += '>' + name + '</a></span></li>';
				}
			});
			
			strUl = '<ul role=group >' + strItems + '</ul>';
			callback(strUl); 
		},
		
		createBranch : function() {
			var self = this;
			if (!self.validateCreateBranch()) {
				commonVariables.hideloading = true;
				self.showHideConsole();
				var appDirName = $('input[name=selectedAppDirName]').val();
				var releaseVersion = $("#createBranchVersion").val();
				var comment = $("#branchComment").val();
				var currentbranch = $("#branchFromName").val();
				var branchname = $("#newBranchName").val();
				var downloadoption = $("#downloadBrToWorkspace").is(":checked");
				
				var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
				var userId = "";
				if (userInfo !== "") {
					userId = userInfo.id; 
				}
				
				var queryString = 'appDirName='+appDirName+'&comment='+comment+'&branchName='+branchname+'&currentBranch='+currentbranch+
									'&releaseVersion='+releaseVersion+'&downloadOption='+downloadoption;
				commonVariables.consoleError = false;
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal) {
					retVal.mvnCreateBranch(queryString, '#repoTextConsole', function(response) {
						commonVariables.hideloading = false;
						$('.progress_loading').hide();
						if (!commonVariables.consoleError) {
							commonVariables.navListener.onMytabEvent(commonVariables.sourceRepo);
						}
					});
				});
			}
		},
		
		validateCreateBranch : function() {
			var self = this;
			var branchName = $("#newBranchName").val();
			var version = $("#createBranchVersion").val();
			var username = $("#branchUsername").val();
			var password = $("#branchPassword").val();
			var branchFromVersion = $("#branchFromVersion").val();
			if (self.isBlank(branchName)) {
				commonVariables.navListener.validateTextBox($("#newBranchName"), 'Enter branch name');
				return true;
			}
			if (self.isBlank(version)) {
				commonVariables.navListener.validateTextBox($("#createBranchVersion"), 'Enter version');
				return true;
			}
			
			if (version === branchFromVersion) {
				$("#createBranchVersion").val('');
				commonVariables.navListener.validateTextBox($("#createBranchVersion"), 'Duplicate branch Version');
				return true;
			}
			return false;
		},
		
		createTag : function() {
			var self = this;
			if (!self.validateCreateTag()) {
				self.showHideConsole();
				commonVariables.hideloading = true;
				var appDirName = $('input[name=selectedAppDirName]').val();
				var comment = $("#tagComment").val();
				var currentBranch = $("#tagFromName").val();
				var releaseVersion = $("#tagFromVersion").val();
				var tag = $("#tagName").val();
				var downloadoption = $("#downloadTagToWorkspace").is(":checked");
				var skipTests = $("#tagNameSkipTest").is(":checked");
				var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
				var userId = "";
				if (userInfo !== "") {
					userId = userInfo.id; 
				}
				
				var queryString = 'appDirName='+appDirName+'&comment='+comment+'&currentBranch='+currentBranch+'&releaseVersion='+releaseVersion+
									'&tag='+tag + '&skipTests='+skipTests;
				commonVariables.consoleError = false;
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal) {
					retVal.mvnTag(queryString, '#repoTextConsole', function(response) {
						commonVariables.hideloading = false;
						$('.progress_loading').hide();
						if (!commonVariables.consoleError) {
							commonVariables.navListener.onMytabEvent(commonVariables.sourceRepo);
						}
					});
				});
			}
		},
		
		validateCreateTag : function() {
			var self = this;
			var tagName = $("#tagName").val();
			var username = $("#tagUsername").val();
			var password = $("#tagPassword").val();
			if (self.isBlank(tagName)) {
				commonVariables.navListener.validateTextBox($("#tagName"), 'Enter Tag name');
				return true;
			}
			return false;
		},
		
		release : function() {
			var self = this;
			if (!self.validateReleaseData()) {
				$('#rep_release').hide();
				commonVariables.hideloading = true;
				self.showHideConsole();
				var releaseVersion = $("#releaseVersion").val();
				var developmentVersion = $("#devVersion").val();
				var tag = $("#releaseTagName").val();
				var comment = $("#releaseComment").val();
				var branchName = $("input[name=selectedBranchName]").val();
				var appDirName = $('input[name=selectedAppDirName]').val();
				var skipTests = $('#releaseSkipTests').is(":checked");
				var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
				var userId = "";
				if (userInfo !== "") { 
					userId = userInfo.id; 
				}
				
				var queryString = 'appDirName='+appDirName+'&message='+comment+
									'&developmentVersion='+developmentVersion+'&releaseVersion='+releaseVersion+'&tag='+tag+'&branchName='+branchName+'&userId='+userId+'&skipTests='+skipTests;
				commonVariables.consoleError = false;
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal) {
					retVal.mvnRelease(queryString, '#repoTextConsole', function(response) {
						commonVariables.hideloading = false;
						$('.progress_loading').hide();
						if (!commonVariables.consoleError) {
							commonVariables.navListener.onMytabEvent(commonVariables.sourceRepo);
						}
					});
				});
			}
		},
		
		validateReleaseData : function() {
			var self = this;
			var releaseVersion = $("#releaseVersion").val();
			var tagName = $("#releaseTagName").val();
			var username = $("#releaseUsername").val();
			var password = $("#releasePassword").val();
			var devVersion = $("#devVersion").val();
			if (self.isBlank(releaseVersion)) {
				commonVariables.navListener.validateTextBox($("#releaseVersion"), 'Enter Release Version');
				return true;
			}
			if (self.isBlank(tagName)) {
				commonVariables.navListener.validateTextBox($("#releaseTagName"), 'Enter Tag name');
				return true;
			}
			if (self.isBlank(devVersion)) {
				commonVariables.navListener.validateTextBox($("#devVersion"), 'Enter Development Version');
				return true;
			}
			return false;
		},
		
		showHideConsole : function() {
			var self = this;
			var check = $('#consoleImg').attr('data-flag');
			if (check === "true") {
				self.openConsoleDiv();
			} else {
				self.closeConsole();
				$('.progress_loading').hide();
			}
		},
		
		openConsoleDiv : function() {
			var self=this;
			$('.testSuiteTable').append('<div class="mask"></div>');
			$('.mask').fadeIn("slow");
			$('.unit_close').css("z-index", 1001);
			$('.unit_progress').css("z-index", 1001);
			$('.unit_close').css("height", 0);
			var value = $('.unit_info').width();
			var value1 = $('.unit_progress').width();
			$('.unit_info').animate({left: -value},500);
			$('.unit_progress').animate({right: '10px'},500);
			$('.unit_close').animate({right: value1+10},500);
			$('.unit_info table').removeClass("big").addClass("small");
			$('#consoleImg').attr('data-flag','false');
			self.copyLog();
		},
		
		saveCredentials : function() {
			var self = this;
			if (!self.validateSaveCredentials()) {
				var requestBody = {};
				requestBody.url = $("#repoUrl").val();
				requestBody.username = $("#repoUsername").val();
				requestBody.password = $("#repoPassword").val();
				self.repository(self.getActionHeader(requestBody, "saveCredentials"), function(response) {
					$("#repoCredentials").modal("hide");
					commonVariables.navListener.onMytabEvent(commonVariables.sourceRepo);
				});
			}
		},
			
		hidePopupLoad : function(){
			$('.popuploading').hide();
		},

		showpopupLoad : function(){
			$('.popuploading').show();
		},

		projectListAction : function(header, loadingObj, callback) {
			var self = this;			
			try {
				if (!self.isBlank(loadingObj)) {
					self.showpopupLoad(loadingObj);
				}
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if(response !== null && response.status !== "error" && response.status !== "failure"){
							self.hidePopupLoad();
							if(response.responseCode !== 'PHR210051' && response.responseCode !== 'PHR200015' && response.responseCode !== 'PHR200021' && response.responseCode !== 'PHR600004' && response.responseCode !== null && response.responseCode !== undefined && response.responseCode !== 'PHR200027') {
								commonVariables.api.showError(response.responseCode ,"success", true);
								if(response.responseCode === 'PHR200010' || response.responseCode === 'PHR200026') {
									$('.msgdisplay').prepend(self.delprojectname+' ');
								}
							}	
							callback(response);		
						} else {
							if(response.responseCode === "PHR210035") {
								callback(response);
								self.hidePopupLoad();
							} else if(response.responseCode === "PHR210037") {
								callback(response);
								self.hidePopupLoad();
							} else {
								commonVariables.api.showError(response.responseCode ,"error", true);
								self.hidePopupLoad();
							}
						}
					},					
					function(textStatus) {
						commonVariables.api.showError("serviceerror" ,"error", true);
						self.hidePopupLoad();
					}
				);
			} catch(exception) {
				self.hidePopupLoad();
			}
		},
		
		validateSaveCredentials : function() {
			var self = this;
			var username = $("#repoUsername").val();
			var password = $("#repoPassword").val();
			if (self.isBlank(username)) {
				commonVariables.navListener.validateTextBox($("#repoUsername"), 'Enter Username');
				return true;
			}
			if (self.isBlank(password)) {
				commonVariables.navListener.validateTextBox($("#repoPassword"), 'Enter Password');
				return true;
			}
			return false;
		},
	});

	return Clazz.com.components.repository.js.listener.RepositoryListener;
});