define(["framework/widget", "framework/widgetWithTemplate", "application/api/applicationAPI", "projectlist/projectList"], function() {

	Clazz.createPackage("com.components.application.js.listener");

	Clazz.com.components.application.js.listener.ApplicationListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		applicationAPI : null,
		projectlistContent : null,

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
			self.projectlistContent = new Clazz.com.components.projectlist.js.ProjectList();
			Clazz.navigationController.push(self.projectlistContent, true);
		},
		
		addServerDatabase : function(appType, whereToAppend) {
			var self = this, dynamicValue, server = '<tr class="servers"> <td data-i18n="application.edit.servers"></td><td name="servers"><select name="appServers"><option>Select Servers</option>'+ self.getOptionData('serverData') +'</select></td><td data-i18n="application.edit.versions"></td><td colspan="3" name="version"><select name="server_version"><option>Select Version</option></select> <div class="flt_right"><a href="javascript:;"><img name="addServer" src="../themes/default/images/helios/plus_icon.png" border="0" alt=""></a> <a href="javascript:;"><img name="removeServer" src="../themes/default/images/helios/minus_icon.png"  border="0" alt=""></a></div></td></tr>',
			
			database ='<tr class="database"><td data-i18n="application.edit.database"></td><td name="servers"><select name="databases"><option>Select Database</option>'+ self.getOptionData('databaseData') +'</select></td><td data-i18n="application.edit.versions"></td> <td colspan="3" name="version"><select name="db_version"> <option>Select Version</option></select><div class="flt_right"><a href="javascript:;"><img src="../themes/default/images/helios/plus_icon.png" name="addDatabase" border="0" alt=""></a> <a href="javascript:;"><img src="../themes/default/images/helios/minus_icon.png" name="removeDatabase" border="0" alt=""></a></div></td></tr>';
			if (appType === "addServer") {
				dynamicValue = $(server).insertAfter(whereToAppend);
				dynamicValue.prev('tr').find('img[name="addServer"]').removeAttr("src");
				dynamicValue.prev('tr').find('img[name="removeServer"]').attr("src","../themes/default/images/helios/minus_icon.png");
			} else {
				dynamicValue = $(database).insertAfter(whereToAppend);
				dynamicValue.prev('tr').find('img[name="addDatabase"]').removeAttr("src");
				dynamicValue.prev('tr').find('img[name="removeDatabase"]').attr("src","../themes/default/images/helios/minus_icon.png");
			}
			$("img[name=addServer]").unbind("click");
			$("img[name=addDatabase]").unbind("click");
			self.addServerDatabaseEvent();
			$("img[name=removeServer]").unbind("click");
			$("img[name=removeDatabase]").unbind("click");
			self.removeServerDatabaseEvent();
			self.serverDBChangeEvent();
			
		},

		addServerDatabaseEvent : function(){
			var self=this, whereToAppend = '';
			$("img[name=addServer]").click(function(){
				whereToAppend = $("img[name=addServer]").parents('tr.servers:last');
				self.dynamicRenderLocales(commonVariables.contentPlaceholder);
				self.addServerDatabase($(this).attr('name'), whereToAppend);
			});
			
			$("img[name=addDatabase]").click(function(){
				whereToAppend = $("img[name=addDatabase]").parents('tr.database:last');
				self.dynamicRenderLocales(commonVariables.contentPlaceholder);
				self.addServerDatabase($(this).attr('name'), whereToAppend);
			});
		},
		
		removeServerDatabaseEvent : function() {
			var self=this;
			$("img[name=removeServer]").click(function(){
				$("img[name=addServer]").removeAttr('src');
				$(this).parent().parent().parent().parent().remove();
				$("img[name=removeServer]").parents('tr:last').find('img[name="addServer"]').attr("src", "../themes/default/images/helios/plus_icon.png");
				if (($("img[name=removeServer]").parents('tr.servers').length) === 1) {
					$("img[name=addServer]").attr("src", "../themes/default/images/helios/plus_icon.png");
					$("img[name=removeServer]").removeAttr('src');
				}
			});
			
			$("img[name=removeDatabase]").click(function(){
				$("img[name=addDatabase]").removeAttr('src');
				$(this).parent().parent().parent().parent().remove();
				$("img[name=removeDatabase]").parents('tr:last').find('img[name="addDatabase"]').attr("src", "../themes/default/images/helios/plus_icon.png");
				if (($("img[name=removeDatabase]").parents('tr.database').length) === 1) {
					$("img[name=addDatabase]").attr("src", "../themes/default/images/helios/plus_icon.png");
					$("img[name=removeDatabase]").removeAttr('src');
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
						option += '<option>'+ value.version +'</option>'
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
								//self.getVersions(appInfo.data[0].name, 'serverData');
								self.getAppConfig(response , 'DATABASE', function(appInfo){
									data.databaseData = appInfo.data;
									self.applicationAPI.localVal.setJson('databaseData', appInfo.data);
									//self.getVersions(appInfo.data[0].name, 'databaseData');
									self.getWSConfig(response, function(appInfo){
										data.webserviceData = appInfo.data;
										console.info('webserviceData = ' , appInfo.data);
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
		
		getRequestHeader : function(appDirName) {
			var self=this, header, data = {}, userId;
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: ''
			}
			//header.webserviceurl = "http://localhost:8080/framework/rest/api/project/editApplication?appDirName=1-jwsandhtmljquery-html5multichanneljquerywidget"
			header.webserviceurl = commonVariables.webserviceurl+"project/editApplication?appDirName="+appDirName;
			return header;
		},
		
		dynamicRenderLocales : function(contentPlaceholder) {
			var self=this;
			self.renderlocales(contentPlaceholder);
		}
		
	});

	return Clazz.com.components.application.js.listener.ApplicationListener;
});