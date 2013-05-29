define(["framework/widget", "framework/widgetWithTemplate", "application/api/applicationAPI"], function() {

	Clazz.createPackage("com.components.application.js.listener");

	Clazz.com.components.application.js.listener.ApplicationListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		applicationAPI : null,
		projectlistContent : null,
		appInfos : [],

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
				this.applicationAPI = new Clazz.com.components.application.js.api.ApplicationAPI();
				this.loadingScreen = new Clazz.com.js.widget.common.Loading();
		},
		
		onCancelUpdate : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.projectlistContent = commonVariables.navListener.getMyObj(commonVariables.projectlist);
			Clazz.navigationController.push(self.projectlistContent, true);
		},
		removelayer : function(object) {
			var layerId = object.attr('id');
			object.closest('tr').next().attr('name', layerId + "content");
			object.closest('tr').next().hide();
			object.closest('tr').attr('name', layerId);
			object.closest('tr').hide();
			$("input[name="+layerId+"]").toggle();
		},
		
		addlayer : function(object) {
			var layerType = object.attr('name');
			$("input[name="+layerType+"]").toggle();
			$("tr[name="+ layerType +"]").show();
			$("tr[name="+ layerType+"content]").show();
		},
		
		addServerDatabase : function(appType, whereToAppend, rowId) {
			var self = this, dynamicValue, server = '<tr class="servers" key="displayed"> <td><span data-i18n="application.edit.servers"></span>&nbsp;<span class="paid">'+rowId+'</span></td><td name="servers" class="servers"><select name="appServers" class="appServers"><option>Select Server</option>'+ self.getOptionData('serverData') +'</select></td><td data-i18n="application.edit.versions"></td><td colspan="4" name="version" class="version"><select multiple name="server_version" class="server_version"><option>Select Version</option></select> <div class="flt_right"><a href="javascript:;" name="addServer"><img src="themes/default/images/helios/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeServer"><img src="themes/default/images/helios/minus_icon.png"  border="0" alt=""></a></div></td></tr>',
			
			database ='<tr class="database" key="displayed"><td><span data-i18n="application.edit.database"></span>&nbsp;<span class="paid">'+rowId+'</span></td><td name="servers" class="databases"><select name="databases" class="databases"><option>Select Database</option>'+ self.getOptionData('databaseData') +'</select></td><td data-i18n="application.edit.versions"></td> <td colspan="4" name="version" class="version"><select multiple name="db_version" class="db_version"> <option>Select Version</option></select><div class="flt_right"><a href="javascript:;" name="addDatabase"><img src="themes/default/images/helios/plus_icon.png"  border="0" alt=""></a> <a href="javascript:;" name="removeDatabase"><img src="themes/default/images/helios/minus_icon.png" border="0" alt=""></a></div></td></tr>';
			if (appType === "addServer") {
				dynamicValue = $(server).insertAfter(whereToAppend);
				dynamicValue.prev('tr').find('a[name="addServer"]').html('');
				dynamicValue.prev('tr').find('a[name="removeServer"]').html('<img src="themes/default/images/helios/minus_icon.png" border="0" alt="">');
			} else {
				dynamicValue = $(database).insertAfter(whereToAppend);
				dynamicValue.prev('tr').find('a[name="addDatabase"]').html('');
				dynamicValue.prev('tr').find('a[name="removeDatabase"]').html('<img src="themes/default/images/helios/minus_icon.png" border="0" alt="">');
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
				//console.info('this = ' , $(this).closest('tr').find('span.paid').text());
				serverRow = Number($(this).closest('tr').find('span.paid').text()) + Number(1);
				whereToAppend = $("a[name=addServer]").parents('tr.servers:last');
				self.dynamicRenderLocales(commonVariables.contentPlaceholder);
				self.addServerDatabase($(this).attr('name'), whereToAppend, serverRow);
			});
			
			$("a[name=addDatabase]").click(function(){
				//console.info('addDatabase = ' , $(this).closest('tr').find('span.paid').text());
				dbRow = Number($(this).closest('tr').find('span.paid').text()) + Number(1);
				whereToAppend = $("a[name=addDatabase]").parents('tr.database:last');
				self.dynamicRenderLocales(commonVariables.contentPlaceholder);
				self.addServerDatabase($(this).attr('name'), whereToAppend, dbRow);
			});
		},
		
		removeServerDatabaseEvent : function() {
			var self=this;
			$("a[name=removeServer]").click(function(){
				$("a[name=addServer]").html('');
				$(this).parent().parent().parent().remove();
				$("a[name=removeServer]").parents('tr:last').find('a[name="addServer"]').html('<img src="themes/default/images/helios/plus_icon.png" border="0" alt="">');
				if (($("a[name=removeServer]").parents('tr.servers').length) === 1) {
					$("a[name=addServer]").html('<img src="themes/default/images/helios/plus_icon.png" border="0" alt="">');
					$("a[name=removeServer]").html('');
				}
			});
			
			$("a[name=removeDatabase]").click(function(){
				$("a[name=addDatabase]").html('');
				$(this).parent().parent().parent().remove();
				$("a[name=removeDatabase]").parents('tr:last').find('a[name="addDatabase"]').html('<img src="themes/default/images/helios/plus_icon.png" border="0" alt="">');
				if (($("a[name=removeDatabase]").parents('tr.database').length) === 1) {
					$("a[name=addDatabase]").html('<img src="themes/default/images/helios/plus_icon.png" border="0" alt="">');
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
		},

		getVersions : function(object, currentData, technology, type) {
			var self=this, option, version, versionplaceholder;
			version = object.parents("td[name='servers']");
			versionplaceholder = $(version).siblings("td[name='version']").children("select[name="+type+"_version]");

			var applicationlayerData = self.applicationAPI.localVal.getJson(technology);
			$.each(applicationlayerData, function(index, value){
				if(value.id === currentData){
					option = '';
					$.each(value.artifactGroup.versions, function(index, value){
						option += '<option value='+value.id+'>'+ value.version +'</option>'
					});	
					$(versionplaceholder).html(option);
				}
			});
		}, 
		
		getOptionData : function(technology) {
			var self=this, option;
			var applicationlayerData = self.applicationAPI.localVal.getJson(technology);
			option = '';
			$.each(applicationlayerData, function(index, value){
			    option += '<option value='+ value.id +'>'+ value.name +'</option>';
			});
			return option;
		},
		
		getAppInfo : function(header, callback) {
			var self = this;
			try {
				self.loadingScreen.showLoading();
				self.applicationAPI.appinfo(header,
					function(response) {
						if (response !== null) {
						var data = {};
							data.appdetails = response;
							self.getAppConfig(response , 'SERVER', function(appInfo){
								data.serverData = appInfo.data;
								self.applicationAPI.localVal.setJson('serverData', appInfo.data);
								self.getAppConfig(response , 'DATABASE', function(appInfo){
									data.databaseData = appInfo.data;
									self.applicationAPI.localVal.setJson('databaseData', appInfo.data);
									self.getWSConfig(response, function(appInfo){
										data.webserviceData = appInfo.data;
										self.applicationAPI.localVal.setJson('webserviceData', appInfo.data);
										self.loadingScreen.removeLoading();
										callback(data);
									});	
								});
							});
						} else {
						self.loadingScreen.removeLoading();
							callback({ "status" : "service failure"});
						}

					},

					function(textStatus) {
						
					}
				);
			} catch(exception) {
				
			}
		},
		
		getAppConfig : function(appInfo , type, callback){
			var self=this, header, data = {}, userId, techId, customerId;
			userId = self.applicationAPI.localVal.getSession('username');
			techId = appInfo.data.appInfos[0].techInfo.id;
			customerId = appInfo.data.customerIds[0];
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: ''
			}
			
			header.webserviceurl = commonVariables.webserviceurl+ "appConfig/list?techId="+techId+"&customerId="+customerId+"&type="+type+"&platform=Windows64&userId="+userId;
			try {
				self.applicationAPI.appinfo(header,
					function(response) {
						if (response !== null) {
							//console.info('response data= ' , response.data);
							callback(response);
						} else {
							callback({ "status" : "service failure"});
						}

					},

					function(textStatus) {
						
					}
				);
			} catch(exception) {
				
			}	
		},
		
		updateApplication : function(renderData){
			var self = this;
			self.appInfos = [];
			var appInfo = {};
			var techInfo = {};
			appInfo.code = $("input[name='appCode']").val();
			appInfo.appDirName = $("input[name='appDirName']").val();
			appInfo.version = $("input[name='appVersion']").val();
			appInfo.name = $("input[name='appName']").val();
			appInfo.selectedFrameworks = renderData.appdetails.data.appInfos[0].selectedFrameworks;
			appInfo.emailSupported = renderData.appdetails.data.appInfos[0].emailSupported;
			appInfo.phoneEnabled = renderData.appdetails.data.appInfos[0].phoneEnabled;
			appInfo.tabletEnabled = renderData.appdetails.data.appInfos[0].tabletEnabled;
			appInfo.pilot = renderData.appdetails.data.appInfos[0].pilot;
			appInfo.used = renderData.appdetails.data.appInfos[0].used;
			appInfo.system = renderData.appdetails.data.appInfos[0].system;
			appInfo.id = renderData.appdetails.data.appInfos[0].id;
			appInfo.description = "";
			appInfo.status = renderData.appdetails.data.appInfos[0].status;
			
			var selectedDatabases = [];
			var selectedServers = [];
			var databasesJson = {};
			var serversJson = {};
			$.each($("tbody[name='layercontents']").children(), function(index, value){
			
				if($(value).attr('class') == "servers" && $(value).attr('key') == "displayed") {
					var serverId = {};
					var artifactInfoIds = [];
					var tech = $(value).children("td.servers").children("select.appServers");
					var selSerId = $(tech).find(":selected").val();
					var selVersion = $(value).children("td.version").children("select.server_version").val();
					//console.info('selVersion = ' , selVersion);
					serverId.artifactGroupId = selSerId;
					artifactInfoIds.push(selVersion);
					serverId.artifactInfoIds = artifactInfoIds;
					
					selectedServers.push(serverId)
				}
				
				if($(value).attr('class') == "database" && $(value).attr('key') == "displayed") {
					var serverId = {};
					var artifactInfoIds = [];
					var tech = $(value).children("td.databases").children("select.databases");
					var selSerId = $(tech).find(":selected").val();
					var selVersion = $(value).children("td.version").children("select.db_version").val();
					
					serverId.artifactGroupId = selSerId;
					artifactInfoIds.push(selVersion);
					serverId.artifactInfoIds = artifactInfoIds;
					selectedDatabases.push(serverId)
				}
				
			});
			if (appInfo.code !== '') {
				appInfo.techInfo.appTypeId = renderData.appdetails.data.appInfos[0].techInfo.appTypeId;
				appInfo.techInfo.version = renderData.appdetails.data.appInfos[0].techInfo.version;
				appInfo.techInfo.id = renderData.appdetails.data.appInfos[0].techInfo.id;
				appInfo.techInfo.system = renderData.appdetails.data.appInfos[0].techInfo.system;
				appInfo.selectedDatabases = selectedDatabases;
				appInfo.selectedServers = selectedServers;
			}
				console.info('appInfos = ' , JSON.stringify(appInfo));
			
				self.editAppInfo(self.getRequestHeader(JSON.stringify(appInfo), "editApplication"), function(response) {
					appInfo = {};
					self.getAppInfo(self.getRequestHeader(self.appDirName, "getappinfo"), function(response) {
						self.pageRefresh(response);
					});
				});			
		},
		
		getWSConfig : function(appInfo , callback){
			var self=this, header, data = {}, userId, techId, customerId;
			userId = self.applicationAPI.localVal.getSession('username');
			techId = appInfo.data.appInfos[0].techInfo.id;
			customerId = appInfo.data.customerIds[0];
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: ''
			}
			
			header.webserviceurl = commonVariables.webserviceurl+ "appConfig/webservices?userId="+userId;
			try {
				self.applicationAPI.appinfo(header,
					function(response) {
						if (response !== null) {
							callback(response);
						} else {
							callback({ "status" : "service failure"});
						}

					},

					function(textStatus) {
						
					}
				);
			} catch(exception) {
				
			}	
		},
		
		getRequestHeader : function(appDirName , action) {
			var self=this, header, data = {}, userId, oldAppDirName;
			userId = self.applicationAPI.localVal.getSession('username');
			oldAppDirName = self.applicationAPI.localVal.getJson("appDirName");
			console.info('oldAppDirName = ' ,oldAppDirName);
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: '',
				data: ''
			}
			if(action == 'getappinfo'){
				//header.webserviceurl = "http://localhost:8080/framework/rest/api/project/editApplication?appDirName=1-jwsandhtmljquery-html5multichanneljquerywidget"
				header.webserviceurl = commonVariables.webserviceurl+"project/editApplication?appDirName="+appDirName;
			}	
			if(action == 'editApplication'){
				header.requestMethod ="PUT";
				header.data = appDirName;
				console.info('data = ' , header.data);
				header.webserviceurl = commonVariables.webserviceurl+"project/updateApplication?userId="+userId+"&oldAppDirName="+oldAppDirName+"&customerId=photon";
			}
			return header;
		},

				
		editAppInfo : function(header, callback) {
			var self = this;
			try {
				self.loadingScreen.showLoading();
				self.applicationAPI.appinfo(header,
					function(response) {
					console.info('response1 = ' , response);
						if (response !== null) {
							console.info('response = ' , response);
							callback(response);
							self.loadingScreen.removeLoading();
						} else {
							self.loadingScreen.removeLoading();
							callback({ "status" : "service failure"});
						}

					},

					function(textStatus) {
						self.loadingScreen.removeLoading();
					}
				);
			} catch(exception) {
				self.loadingScreen.removeLoading();
			}

		},
		
		dynamicRenderLocales : function(contentPlaceholder) {
			var self=this;
			self.renderlocales(contentPlaceholder);
		}
		
	});

	return Clazz.com.components.application.js.listener.ApplicationListener;
});