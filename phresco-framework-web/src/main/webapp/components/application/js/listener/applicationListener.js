define([], function() {

	Clazz.createPackage("com.components.application.js.listener");

	Clazz.com.components.application.js.listener.ApplicationListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		projectlistContent : null,
		appInfos : [],
		hasError: false,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
		},
		
		onCancelUpdate : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			commonVariables.navListener.getMyObj(commonVariables.projectlist, function(retVal){
				self.projectlistContent = retVal;
				Clazz.navigationController.push(self.projectlistContent, true, true);
			});
		},
		removelayer : function(object) {
			var layerId = object.attr('id');
			object.closest('tr').next().attr('name', layerId + "content");
			object.closest('tr').attr('name', layerId);
			object.closest('tr').hide();
			$("input[name="+layerId+"]").toggle();
			$("tr[name="+layerId+"content]").hide();
		},
		
		addlayer : function(object) {
			var layerType = object.attr('name');
			$("input[name="+layerType+"]").toggle();
			$("tr[name="+ layerType +"]").show('slow');
			$("tr[name="+ layerType+"content]").show('slow');
		},
		
		addServerDatabase : function(appType, whereToAppend, rowId) {
			var self = this, dynamicValue, server = '<tr class="servers" key="displayed" name="serverscontent"> <td><span>Server</span>&nbsp;<span class="paid">'+rowId+'</span></td><td name="servers" class="servers"><select name="appServers" class="appServers selectpicker"><option value="0">Select Server</option>'+ self.getOptionData('serverData') +'</select></td><td>Versions</td><td colspan="4" name="version" class="version"><select title="Select Version" multiple data-selected-text-format="count>3" name="server_version" class="server_version selectpicker"></select> <div class="flt_right"><a href="javascript:;" name="addServer"><img src="themes/default/images/Phresco/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeServer"><img src="themes/default/images/Phresco/minus_icon.png"  border="0" alt=""></a></div></td></tr>',
			
			database ='<tr class="database" key="displayed" name="databasecontent"><td><span>Database</span>&nbsp;<span class="paid">'+rowId+'</span></td><td name="servers" class="databases"><select name="databases" class="databases selectpicker"><option value=0>Select Database</option>'+ self.getOptionData('databaseData') +'</select></td><td>Versions</td> <td colspan="4" name="version" class="version"><select title="Select Version" multiple data-selected-text-format="count>3" name="db_version" class="db_version selectpicker"></select><div class="flt_right"><a href="javascript:;" name="addDatabase"><img src="themes/default/images/Phresco/plus_icon.png"  border="0" alt=""></a> <a href="javascript:;" name="removeDatabase"><img src="themes/default/images/Phresco/minus_icon.png" border="0" alt=""></a></div></td></tr>';
			if (appType === "addServer") {
				dynamicValue = $(server).insertAfter(whereToAppend);
				dynamicValue.prev('tr').find('a[name="addServer"]').html('');
				dynamicValue.prev('tr').find('a[name="removeServer"]').html('<img src="themes/default/images/Phresco/minus_icon.png" border="0" alt="">');
			} else {
				dynamicValue = $(database).insertAfter(whereToAppend);
				dynamicValue.prev('tr').find('a[name="addDatabase"]').html('');
				dynamicValue.prev('tr').find('a[name="removeDatabase"]').html('<img src="themes/default/images/Phresco/minus_icon.png" border="0" alt="">');
			}
			$("a[name=addServer]").unbind("click");
			$("a[name=addDatabase]").unbind("click");
			self.addServerDatabaseEvent();
			$("a[name=removeServer]").unbind("click");
			$("a[name=removeDatabase]").unbind("click");
			self.removeServerDatabaseEvent();
			self.serverDBChangeEvent();
			
		},

		addServerDatabaseEvent : function(){
			var self=this, whereToAppend = '', serverRow, dbRow;
			$("a[name=addServer]").click(function(){
				serverRow = Number($(this).closest('tr').find('span.paid').text()) + Number(1);
				whereToAppend = $("a[name=addServer]").parents('tr.servers:last');
				self.dynamicRenderLocales(commonVariables.contentPlaceholder);
				self.addServerDatabase($(this).attr('name'), whereToAppend, serverRow);
				self.multiselect();
			});
			
			$("a[name=addDatabase]").click(function(){
				dbRow = Number($(this).closest('tr').find('span.paid').text()) + Number(1);
				whereToAppend = $("a[name=addDatabase]").parents('tr.database:last');
				self.dynamicRenderLocales(commonVariables.contentPlaceholder);
				self.addServerDatabase($(this).attr('name'), whereToAppend, dbRow);
				self.multiselect();
			});
		},
		
		removeServerDatabaseEvent : function() {
			var self=this;
			$("a[name=removeServer]").click(function(){
				$("a[name=addServer]").html('');
				$(this).parent().parent().parent().remove();
				$("a[name=removeServer]").parents('tr:last').find('a[name="addServer"]').html('<img src="themes/default/images/Phresco/plus_icon.png" border="0" alt="">');
				if (($("a[name=removeServer]").parents('tr.servers').length) === 1) {
					$("a[name=addServer]").html('<img src="themes/default/images/Phresco/plus_icon.png" border="0" alt="">');
					$("a[name=removeServer]").html('');
				}
			});
			
			$("a[name=removeDatabase]").click(function(){
				$("a[name=addDatabase]").html('');
				$(this).parent().parent().parent().remove();
				$("a[name=removeDatabase]").parents('tr:last').find('a[name="addDatabase"]').html('<img src="themes/default/images/Phresco/plus_icon.png" border="0" alt="">');
				if (($("a[name=removeDatabase]").parents('tr.database').length) === 1) {
					$("a[name=addDatabase]").html('<img src="themes/default/images/Phresco/plus_icon.png" border="0" alt="">');
					$("a[name=removeDatabase]").html('');
				}
			});
		},
		
		serverDBChangeEvent : function() {
			var self=this;
			$("select[name='appServers']").bind('change', function(){
				var currentData = $(this).val();
				self.getVersions($(this), currentData, 'serverData', 'server');
			});			
			
			$("select[name='databases']").bind('change', function(){
				var currentData = $(this).val();
				self.getVersions($(this), currentData, 'databaseData', 'db');
			});	
			
			$("select[name='func_framework']").bind('change', function(){
				var currentData = $(this).val();
				self.getTools($(this), currentData, 'functionalFrameworkData', 'func_framework');
				tools_versionplaceholder = $("select[name=tools_version]");
				$(tools_versionplaceholder).html('');
				$("<option>").val("").text('Select Version').appendTo(tools_versionplaceholder);
				$(tools_versionplaceholder).selectpicker('refresh');				
			});
			
			$("select[name='func_framework_tools']").bind('change', function(){
				var currentData = $(this).val();
				self.getToolsVersion($(this), currentData, 'functionalFrameworkData', 'tools_version');
				var selectFramework = $(this).find(":selected").text();
				("TAW" === selectFramework) ? $('.functionalFrameworkURL').show(): $('.functionalFrameworkURL').hide();
			});
		},
		
		getToolsVersion : function(object, currentData, technology, type){
			var self=this, option,  versionplaceholder;
			versionplaceholder = $("select[name=tools_version]");
			$(versionplaceholder).html('');
			if(currentData === "0"){
				$(versionplaceholder).selectpicker('refresh');
				$("<option>").val("").text('Select Version').appendTo(versionplaceholder);	
			}else {
				var jsontechData = commonVariables.api.localVal.getJson(technology);
				$.each(jsontechData, function(index, value){
					if(value.id === $("select[name='func_framework']").val()){
						$.each(value.functionalFrameworks, function(index, value){
							if(value.id === currentData){
								$.each(value.versions, function(index, value){
									$("<option>").val(value).text(value).appendTo(versionplaceholder);
								});
								$(versionplaceholder).selectpicker('refresh');
							}
						});					
					}
				});
			}	
		},
		
		getTools : function(object, currentData, technology, type){
			var self=this, option, version, versionplaceholder;
			version = object.parents("td[name='servers']");
			versionplaceholder = $(version).siblings("td[name='tools']").children("select[name=func_framework_tools]");
			$(versionplaceholder).html('');
			$("<option>").val('0').text('Select Tools').appendTo(versionplaceholder);	
			// To clear Version
			
			if(currentData === "0"){
				$(versionplaceholder).selectpicker('refresh');
			}else {
				var jsontechData = commonVariables.api.localVal.getJson(technology);
				$.each(jsontechData, function(index, value){
					if(value.id === currentData){
						$.each(value.functionalFrameworks, function(index, value){
							$("<option>").val(value.id).text(value.displayName).appendTo(versionplaceholder);
						});
						$(versionplaceholder).selectpicker('refresh');
						
					}
				});
			}	
		},
		
		getVersions : function(object, currentData, technology, type) {
			var self=this, option, version, versionplaceholder;
			version = object.parents("td[name='servers']");
			versionplaceholder = $(version).siblings("td[name='version']").children("select[name="+type+"_version]");
			if(currentData === "0"){
				$(versionplaceholder).html('');
				$("<option>").val('Server Version').text('Select Version').appendTo(versionplaceholder);	
				$(versionplaceholder).selectpicker('refresh');
			}else {
				var applicationlayerData = commonVariables.api.localVal.getJson(technology);
				$.each(applicationlayerData, function(index, value){
					if(value.id === currentData){
						self.getOptions(value, versionplaceholder, function(){
							$(versionplaceholder).selectpicker('refresh');
						});
					}
				});
			}	
		},

		getOptions : function(appData, versionplaceholder, callback){
			var option = '';
			$(versionplaceholder).html('');
			$.each(appData.artifactGroup.versions, function(index, value){
				$("<option>").val(value.id).text(value.version).appendTo(versionplaceholder);	
			});
			callback();
		},	
		
		getOptionData : function(technology) {
			var self=this, option;
			var applicationlayerData = commonVariables.api.localVal.getJson(technology);
			option = '';
			$.each(applicationlayerData, function(index, value){
			    option += '<option value='+ value.id +'>'+ value.name +'</option>';
			});
			return option;
		},
		
		getAppInfo : function(header, callback) {
			var self = this;
			try {
				//commonVariables.loadingScreen.showLoading();
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null && response.status !== "error" && response.status !== "failure") {
						commonVariables.api.localVal.setJson('functionalFrameworkInfo', response.data.projectInfo.appInfos[0].functionalFrameworkInfo);
						var data = {};
							data.appdetails = response;
							if (response){
								$('.hProjectId').val(response.data.projectInfo.appInfos[0].id);
								gmtdate = new Date(response.data.projectInfo.appInfos[0].creationDate);
								localdate = new Date(gmtdate);
								var tail = 'GMT', D= [localdate.getUTCMonth()+1, localdate.getUTCDate(),localdate.getUTCFullYear()];
								T= [localdate.getHours(), localdate.getMinutes(), localdate.getSeconds()];
								if(+T[0]> 12){
									T[0]-= 12;
									tail = ' PM '+tail;
								}
								else {
									tail = ' AM '+tail;
								}
							    var i= 3;
							    while(i){
							        --i;
							        if(D[i]<10) D[i]= '0'+D[i];
							        if(T[i]<10) T[i]= '0'+T[i];
							    }
							   var date= D.join('-')+' '+T.join(':')+ tail;
					 		response.data.projectInfo.appInfos[0].creationDate=date;
							}
							commonVariables.api.localVal.setProjectInfo(response);
							self.getAppConfig(response , 'SERVER', function(appInfo){
								data.serverData = appInfo.data;
								commonVariables.api.localVal.setJson('serverData', appInfo.data);
								self.getAppConfig(response , 'DATABASE', function(appInfo){
									data.databaseData = appInfo.data;
									commonVariables.api.localVal.setJson('databaseData', appInfo.data);
									self.getWSConfig(response, function(appInfo){
										data.webserviceData = appInfo.data;
										commonVariables.api.localVal.setJson('webserviceData', appInfo.data);
										self.getAppConfig(response , 'functionalFrameworks', function(appInfo){
											data.functionalFrameworkData = appInfo.data;
											commonVariables.api.localVal.setJson('functionalFrameworkData', appInfo.data);
											callback(data);
										});	
									});	
								});
							});
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
		
		getAppConfig : function(appInfo , type, callback){
			var self=this, header, data = {}, userId, techId, customerId;
			userId = commonVariables.api.localVal.getSession('username');
			techId = appInfo.data.projectInfo.appInfos[0].techInfo.id;
			customerId = appInfo.data.projectInfo.customerIds[0];
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: ''
			};
			if("functionalFrameworks" === type){
				header.webserviceurl = commonVariables.webserviceurl+ "appConfig/functionalFrameworks?techId="+techId+"&userId="+userId+"&customerId="+customerId;			
			}else{
					var platform;
					var ua = navigator.userAgent.toLowerCase();
					if (ua.indexOf("win") != -1) {
						platform = "Windows64";
					} else if (ua.indexOf("mac") != -1) {
						platform = "Mac64";
					} else if (ua.indexOf("linux") != -1) {
						platform = "Linux64";
					} else {
						platform = "Windows64";
					}
				header.webserviceurl = commonVariables.webserviceurl+ "appConfig/list?techId="+techId+"&customerId="+customerId+"&type="+type+"&platform="+platform+"&userId="+userId;
			}
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
		
		updateApplication : function(renderData){
			var self = this;
			self.appInfos = [];
			
			if(!self.validation()) {
				var appInfo = {};
				var techInfo = {};
				var appdescription = $("#appDesc").val();
					appdescription = appdescription.replace("<" , "&lt;");
					appdescription = appdescription.replace(">" , "&gt;");

				appInfo.code = $("input[name='appCode']").val();
				appInfo.appDirName = $("input[name='appDirName']").val();
				var rootModule = self.isBlank($('.rootModule').val()) ? "" : $('.rootModule').val();
				appInfo.rootModule = rootModule;
				appInfo.version = $("input[name='appVersion']").val();
				appInfo.name = $("input[name='appName']").val();
				appInfo.emailSupported = renderData.appdetails.data.projectInfo.appInfos[0].emailSupported;
				appInfo.phoneEnabled = renderData.appdetails.data.projectInfo.appInfos[0].phoneEnabled;
				appInfo.tabletEnabled = renderData.appdetails.data.projectInfo.appInfos[0].tabletEnabled;
				appInfo.pilot = renderData.appdetails.data.projectInfo.appInfos[0].pilot;
				appInfo.id = renderData.appdetails.data.projectInfo.appInfos[0].id;
				appInfo.description = appdescription;
				
				appInfo.selectedModules = renderData.appdetails.data.projectInfo.appInfos[0].selectedModules;
				appInfo.selectedFeatureMap = renderData.appdetails.data.projectInfo.appInfos[0].selectedFeatureMap;
				appInfo.selectedJSLibs = renderData.appdetails.data.projectInfo.appInfos[0].selectedJSLibs;
				appInfo.selectedComponents = renderData.appdetails.data.projectInfo.appInfos[0].selectedComponents;
				appInfo.phrescoPomFile = renderData.appdetails.data.projectInfo.appInfos[0].phrescoPomFile;
				appInfo.pomFile = renderData.appdetails.data.projectInfo.appInfos[0].pomFile;
				appInfo.embedAppId = $("#embed").val();
				appInfo.modules = renderData.appdetails.data.projectInfo.appInfos[0].modules;
				var selectedDatabases = [];
				var selectedServers = [];
				var selectedWebServices = [];
				var databasesJson = {};
				var serversJson = {};
				var functionalFrameworkInfo = null;
				var submitFlag = 0;
				var showServer = false;
				var showDatabase = false;
				var showWebservice = false;
				var showTestingFramework = false;
				var regex = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
				var isValid = regex.test($('.funcionalIframeUrl').val()) ;
				//console.log($("tbody[name='layercontents']").children().children().children());
				$.each($("tbody[name='layercontents']").children(), function(index, value){
				
					if($(value).attr('class') === "servers" && $(value).css('display') !== "none") {
						var serverId = {};
						var artifactInfoIds = [];
						var tech = $(value).children("td.servers").children("select.appServers");
						var selSerId = $(tech).find(":selected").val();
						var selVersion = $(value).children("td.version").children("select.server_version").val();
						if(selSerId !== "0" && selVersion === null){
							$("#servererror").focus();
							$("#servererror").addClass("errormessage");
							$("#servererror").text('Please select server version');
							$("select[name='server_version']").bind('change', function() {
								$("#servererror").text("");
								$("#servererror").removeClass("errormessage");
							});
							submitFlag = 1;
						} else {
							if(selSerId !== "0" && selSerId !== 0){
								serverId.artifactGroupId = selSerId;
								serverId.artifactInfoIds = selVersion;
								selectedServers.push(serverId);
							}	
						}
						showServer = true;
					}
					
					if($(value).attr('class') === "database" && $(value).css('display') !== "none") {
						var serverId = {};
						var artifactInfoIds = [];
						var tech = $(value).children("td.databases").children("select.databases");
						var selSerId = $(tech).find(":selected").val();
						var selVersion = $(value).children("td.version").children("select.db_version").val();
						if(selSerId !== "0" && selVersion === null){
							$("#dberror").focus();
							$("#dberror").addClass("errormessage");
							$("#dberror").text('Please select database version');
							$("select[name='db_version']").bind('change', function() {
								$("#dberror").text("");
								$("#dberror").removeClass("errormessage");
							});
							submitFlag = 1;
						} else {
							if(selSerId !== "0" && selSerId !== 0){
								serverId.artifactGroupId = selSerId;
								serverId.artifactInfoIds = selVersion;
								selectedDatabases.push(serverId);
							}	
						}
						showDatabase = true;
					}
					if($(value).attr('class') === "webservice" && $(value).css('display') !== "none") {
						$.each($(this).find(".webservice_chkbox:checked"), function() {
							selectedWebServices.push($(this).val());
						});
						showWebservice = true;
					}
					
					if($(value).attr('class') === "functionalFramework" && $(value).css('display') !== "none") {
						var frameworkGroupId = $("select[name=func_framework]").val();
						var frameworkIds = $("select[name=func_framework_tools]").val();
						var version = $("select[name=tools_version]").val();
						var selectFrameworkTool = $("select[name=func_framework_tools]").find(":selected").text();
						
						if(frameworkGroupId === "0"){
							$("#fferror").focus();
							$("#fferror").addClass("errormessage");
							$("#fferror").text('Please select functional framework');
							$("select[name='func_framework']").bind('change', function() {
								$("#fferror").text("");
								$("#fferror").removeClass("errormessage");
							});
							submitFlag = 1;
							
						}else{
							 if(frameworkGroupId !== "0" && frameworkIds === "0"){
								$("#fferror").focus();
								$("#fferror").addClass("errormessage");
								$("#fferror").text('Please select tools');
								$("select[name='func_framework_tools']").bind('change', function() {
									$("#fferror").text("");
									$("#fferror").removeClass("errormessage");
								});
								submitFlag = 1;
							} else if ("TAW" === selectFrameworkTool && self.isBlank($('.funcionalIframeUrl').val())) {
								$("#fferror").focus();
								$("#fferror").addClass("errormessage");
								$("#fferror").text('Please enter iframe url');
								submitFlag = 1;
							} else if ("TAW" === selectFrameworkTool && !self.isBlank($('.funcionalIframeUrl').val()) && !isValid) {
						    	$("#fferror").focus();
								$("#fferror").addClass("errormessage");
								$("#fferror").text('Please enter valid url');
						    	submitFlag = 1;
							} else {
								functionalFrameworkInfo = {};							
								functionalFrameworkInfo.frameworkGroupId = frameworkGroupId;
								functionalFrameworkInfo.frameworkIds = frameworkIds;
								functionalFrameworkInfo.version = version;
								functionalFrameworkInfo.iframeUrl = self.isBlank($('.funcionalIframeUrl').val()) ? "" : $('.funcionalIframeUrl').val();
							}	
						}	
						showTestingFramework =true;
					}					
				});
				if (appInfo.code !== '') {
					appInfo.techInfo = renderData.appdetails.data.projectInfo.appInfos[0].techInfo;
					appInfo.selectedDatabases = selectedDatabases;
					appInfo.selectedServers = selectedServers;
					appInfo.selectedWebservices = selectedWebServices;
					appInfo.functionalFrameworkInfo = functionalFrameworkInfo;
					appInfo.showServer = showServer;
					appInfo.showDatabase = showDatabase;
					appInfo.showWebservice = showWebservice;
					appInfo.showTestingFramework = showTestingFramework;
				}

				if(submitFlag === 0){
					self.editAppInfo(self.getRequestHeader(JSON.stringify(appInfo), "editApplication"), function(response) {
						if(response.responseCode === "PHR200008"){
							var appDir = commonVariables.api.localVal.getSession('appDirName');
							localStorage.setItem(appDir + '_AppUpdateMsg', response.message);
							var newAppDirName = response.data.projectInfo.appInfos[0].appDirName;
							!self.isBlank($('.moduleName').val()) ? $('.moduleName').val(newAppDirName) : $('.moduleName').val('');
							commonVariables.navListener.getMyObj(commonVariables.editApplication, function(retVal){
								self.editAplnContent = retVal;
								self.editAplnContent.appDirName = response.data.projectInfo.appInfos[0].appDirName;
								commonVariables.appDirName = response.data.projectInfo.appInfos[0].appDirName;
								commonVariables.api.localVal.setSession('appDirName', response.data.projectInfo.appInfos[0].appDirName);
								if (response){$('.hProjectId').val(response.data.projectInfo.appInfos[0].id);}
								commonVariables.api.localVal.setProjectInfo(response);
								Clazz.navigationController.push(self.editAplnContent, true);
								setTimeout(function(){
									commonVariables.api.showError(response.responseCode ,"success", true, false, true);
								},3000);
							});
						}
					});
				}	
			}
		},
		
		getWSConfig : function(appInfo , callback){
			var self=this, header, data = {}, userId, techId, customerId;
			userId = commonVariables.api.localVal.getSession('username');
			techId = appInfo.data.projectInfo.appInfos[0].techInfo.id;
			customerId = appInfo.data.projectInfo.customerIds[0];
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: ''
			};
			
			header.webserviceurl = commonVariables.webserviceurl+ "appConfig/webservices?userId="+userId+"&techId="+techId;
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
		
		getRequestHeader : function(appDirName, action, techId) {
			var self=this, header, data = {}, userId, oldAppDirName;
			userId = commonVariables.api.localVal.getSession('username');
			oldAppDirName = commonVariables.api.localVal.getSession("appDirName");
			var moduleParam = self.isBlank($('.moduleName').val()) ? "" : '&module='+$('.moduleName').val();
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: '',
				data: ''
			};
			if(action === 'getappinfo'){
				
				header.webserviceurl = commonVariables.webserviceurl+"project/editApplication?appDirName="+appDirName+"&userId="+userId+moduleParam;
			}	
			if(action === 'editApplication'){
				var displayName="", userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
				if (userInfo !== null) {
					displayName = userInfo.displayName;
				}
				moduleParam = self.isBlank($('.moduleName').val()) ? "" : '&rootModule='+$('.rootModule').val()+'&moduleName='+$('.moduleName').val();
				oldAppDirName = self.isBlank($('.moduleName').val()) ? oldAppDirName : $('.moduleName').val();
				header.requestMethod ="PUT";
				header.requestPostBody = appDirName;
				header.webserviceurl = commonVariables.webserviceurl+"project/updateApplication?userId="+userId+"&oldAppDirName="+oldAppDirName+"&customerId=photon&displayName="+displayName+moduleParam;
			}
			if (action === 'getApplicableOptions') {
				header.requestMethod ="GET";
				header.webserviceurl = commonVariables.webserviceurl+"util/techOptions?userId="+userId+"&techId="+techId;
			}
			return header;
		},

				
		editAppInfo : function(header, callback) {
			var self = this;
			try {
				//commonVariables.loadingScreen.showLoading();
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							callback(response);
							//commonVariables.loadingScreen.removeLoading();
						} else {
							commonVariables.api.showError(response.responseCode ,"error", true);
						}
					},

					function(textStatus) {
						//commonVariables.loadingScreen.removeLoading();
						commonVariables.api.showError("serviceerror" ,"error", true);
					}
				);
			} catch(exception) {
				//commonVariables.loadingScreen.removeLoading();
			}

		},
		
		dynamicRenderLocales : function(contentPlaceholder) {
			var self=this;
			self.renderlocales(contentPlaceholder);
		},
		
		validation : function() {
		
			 var flag1=0,flag2=0,flag3=0;
			 var name = $("input[name='appName']").val();
			 var code = $("input[name='appCode']").val();
			 var appDirName = $("input[name='appDirName']").val();
			 self.hasError = false;

			 if(name === ""){
					$("input[name='appName']").focus();
					$("input[name='appName']").attr('placeholder','Enter Name');
					$("input[name='appName']").addClass("errormessage");
					$("input[name='appName']").bind('keypress', function() {
						$(this).removeClass("errormessage");
					});
					self.hasError = true;
			   } else if(code === ""){
					$("input[name='appCode']").focus();
					$("input[name='appCode']").attr('placeholder','Enter Code');
					$("input[name='appCode']").addClass("errormessage");
					$("input[name='appCode']").bind('keypress', function() {
						$(this).removeClass("errormessage");
					});
					self.hasError = true;
			   } else if(appDirName === ""){
					$("input[name='appDirName']").focus();
					$("input[name='appDirName']").attr('placeholder','Enter app directory');
					$("input[name='appDirName']").addClass("errormessage");
					$("input[name='appDirName']").bind('keypress', function() {
						$(this).removeClass("errormessage");
					});
					self.hasError = true;
			   }
			  return self.hasError;	
		},
		
		getApplicableOptions : function(header, callback) {
			var self = this;
			try {
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							callback(response);
							//commonVariables.loadingScreen.removeLoading();
						} else {
							//commonVariables.loadingScreen.removeLoading();
							commonVariables.api.showError(response.responseCode ,"error", true);
						}

					}
				);
			} catch(exception) {
				
			}
		}
	});

	return Clazz.com.components.application.js.listener.ApplicationListener;
});