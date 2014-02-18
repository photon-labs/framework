define(["croneExpression/croneExpression"], function() {

	Clazz.createPackage("com.components.configuration.js.listener");

	Clazz.com.components.configuration.js.listener.ConfigurationListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		basePlaceholder :  window.commonVariables.basePlaceholder,
		editConfigurations : null,
		configList : [],
		configListPage : null,
		cancelEditConfigurations : null,
		configRequestBody : {},
		envJson : [],
		configTemName : [],
		configTemp : [],
		bcheck : null,
		count : '',
		countVal : 0,
		serverTypeVersion : null,
		databaseTypeVersion : null,
		croneExp : null,
		nonEnvEditConfigurations : null,
		desc : null,
		favourite : null,
		editSettings : null,
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
		},
		
		editConfiguration : function(envName, envSpecificVal) {
			var self=this, envSpecific;
			commonVariables.environmentName = envName;
			envSpecific = (envSpecificVal === "true") ? true : false;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			if(commonVariables.navListener.currentTab !== commonVariables.projectSettings) {
				if(self.editConfigurations  === null) {
					commonVariables.navListener.getMyObj(commonVariables.editConfiguration, function(retVal) {
						self.editConfigurations = retVal;
						self.editConfigurations.envSpecificVal = envSpecific;
						self.editConfigurations.favourite = self.favourite;
						self.editConfigurations.configClick = "EditConfiguration";
						Clazz.navigationController.push(self.editConfigurations, commonVariables.animation);
					});
				} else {
					self.editConfigurations.envSpecificVal = envSpecific;
					self.editConfigurations.favourite = self.favourite;
					self.editConfigurations.configClick = "EditConfiguration";
					Clazz.navigationController.push(self.editConfigurations, commonVariables.animation);
				}
		    } else {
				if(self.editSettings === null) {
					commonVariables.navListener.getMyObj(commonVariables.editprojectSettings, function(retVal) {
						self.editSettings = retVal;
						/* self.editSettings.envSpecificVal = envSpecific;
						self.editSettings.configClick = "EditSettings"; */
						self.editSettings.favourite = self.favourite;
						Clazz.navigationController.push(self.editSettings, commonVariables.animation);
					});
				} else {
					/* self.editSettings.envSpecificVal = envSpecific;
					self.editSettings.configClick = "EditSettings"; */
					self.editSettings.favourite = self.favourite;
					Clazz.navigationController.push(self.editSettings, commonVariables.animation);
				}	
			}
		},

		windowResize : function(){
			var resultvalue = 0;
			$('.features_content_main').prevAll().each(function() {
				resultvalue = resultvalue + $(this).height(); 
			});
			
			resultvalue = resultvalue + $('.footer_section').height() + 65;
			$('.features_content_main').height($(window).height() - (resultvalue + 155));
		},
		
		cancelEditConfiguration : function() {
			var self=this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			if(self.cancelEditConfigurations  === null) {
				commonVariables.navListener.getMyObj(commonVariables.configuration, function(retVal) {
					self.cancelEditConfigurations = retVal;
					Clazz.navigationController.push(self.cancelEditConfigurations, commonVariables.animation, commonVariables.animation);
				});
			} else {
				Clazz.navigationController.push(self.cancelEditConfigurations, commonVariables.animation, commonVariables.animation);
			}
		},
		
		getConfigurationList : function(header, callback) {
			var self = this;
			try {
				self.showpopupLoad();
				if (self.bcheck === false) {
				}
				commonVariables.api.ajaxRequest(header,
					function(response) {
						self.hidePopupLoad();
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							if(response.responseCode === "PHR600006" || response.responseCode === "PHR600015") {
								commonVariables.api.showError(response.responseCode ,"success", true, false, true);
								setTimeout(function() {
									callback(response);
								},1200);
							}
							else {
								callback(response);
							}
						} else {
							self.hidePopupLoad();
							//self.loadingScreen.removeLoading();
							commonVariables.api.showError(response.responseCode ,"error", true, false, true);
						}

					},

					function(textStatus, xhr, e) {
						self.hidePopupLoad();
						if (self.bcheck === false) {
						}
						commonVariables.api.showError("serviceerror" ,"error", true, false, true);
					}
				);
			} catch(exception) {
				self.hidePopupLoad();
				if (self.bcheck === false) {
				}
			}

		},
		
		getValidationResult : function(header, callback) {
			var self = this;
			try {
				self.showpopupLoad();
				commonVariables.api.ajaxRequest(header,
					function(response) {
						self.hidePopupLoad();
						callback(response);
					},

					function(textStatus, xhr, e) {
						self.hidePopupLoad();
					}
				);
			} catch(exception) {
				self.hidePopupLoad();
			}
		},
		
		showpopupLoad : function(){
			$('.popuploading').show();
		},
		
		hidePopupLoad : function(){
			$('.popuploading').hide();
		},
		
		getRequestHeader : function(configRequestBody, action, deleteEnv) {
			var self = this, header, appDirName, techId, projectCode, projectId;
			var customerId = self.getCustomer();
			customerId = (customerId === "") ? "photon" : customerId;
			projectCode = commonVariables.api.localVal.getSession('projectCode');
			projectId = commonVariables.api.localVal.getSession('projectId');
			var moduleParam = self.isBlank($('.moduleName').val()) ? "" : '&moduleName='+$('.moduleName').val();
			var projectInfo = commonVariables.api.localVal.getProjectInfo();
			if(projectInfo !== undefined && projectInfo !== null){
				techId = projectInfo.data.projectInfo.appInfos[0].techInfo.id;
			}
			if (!self.isBlank(moduleParam)) {
				appDirName = $('.rootModule').val()
			} else if(commonVariables.api.localVal.getProjectInfo() !== null){
				appDirName = projectInfo.data.projectInfo.appInfos[0].appDirName;
			}

			data = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			if(data !== null) {
				var userId = data.id;
			}
			self.bcheck = false;
			header = {
				contentType: "application/json",
				dataType: "json",
				requestMethod : "GET",
				webserviceurl : ''
			};
			if(commonVariables.navListener.currentTab !== commonVariables.projectSettings) {
				if (action === 'list') {
					if (configRequestBody.envSpecific === false && configRequestBody.configType !== "") {
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/allEnvironments?appDirName="+appDirName+"&isEnvSpecific="+configRequestBody.envSpecific+"&configType="+configRequestBody.configType + moduleParam;
					} else {				
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/allEnvironments?appDirName="+appDirName + moduleParam;
					}
				} else if (action === 'edit') {
					if (configRequestBody.envSpecific === false) {
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"?appDirName="+appDirName+"&configName="+commonVariables.environmentName+"&isEnvSpecific="+configRequestBody.envSpecific + moduleParam;
					} else {
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"?appDirName="+appDirName+"&envName="+commonVariables.environmentName + moduleParam;
					}
				}else if (action === 'configTypes') {
					self.bcheck = true;
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/types?customerId="+customerId+"&userId="+userId+"&techId="+techId;
				} else if (action === 'delete') {
					self.bcheck = true;
					header.requestMethod = "DELETE";
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/deleteEnv?appDirName="+appDirName+"&envName="+deleteEnv+moduleParam;
				} else if (action === "addEnvValidate") {
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/validate?appDirName="+appDirName+"&customerId="+customerId+"&environmentName="+deleteEnv+moduleParam;
				} else if (action === "deleteConfig") {
					header.requestMethod = "DELETE";
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/deleteConfig?appDirName="+appDirName+"&configName="+deleteEnv+moduleParam;
				} else if (action === "template") {
						self.bcheck = true;
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?appDirName="+appDirName+"&customerId="+customerId+"&userId="+userId+"&type="+deleteEnv+"&techId="+techId+moduleParam;
				} else if (action === "saveEnv") {
						header.requestMethod = "POST";
						header.requestPostBody = JSON.stringify(configRequestBody);
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"?appDirName="+appDirName + moduleParam;
				} else if (action === "saveConfig") {
						header.requestMethod = "POST";
						header.requestPostBody = JSON.stringify(configRequestBody);
						if (deleteEnv === "false") {
							header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/updateConfig?appDirName="+appDirName+"&isEnvSpecific="+deleteEnv+"&configName="+commonVariables.updateConfigName+"&customerId="+customerId+"&userId="+userId+moduleParam;
						} else {
							header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/updateConfig?appDirName="+appDirName+"&envName="+deleteEnv+"&customerId="+customerId+"&userId="+userId+"&oldEnvName="+self.oldEnvName+"&defaultEnv="+self.defaultEnv+"&desc="+self.desc+"&isfavoric="+self.favourite+"&favtype="+commonVariables.favConfig+moduleParam;
						}
				} else if (action === "cloneEnv") {
						header.requestMethod = "POST";
						header.requestPostBody = JSON.stringify(configRequestBody);
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/cloneEnvironment?appDirName="+appDirName+"&envName="+deleteEnv+moduleParam;
				} else if (action === "isAliveCheck") {
						header.requestMethod = "GET";
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/connectionAliveCheck?url="+configRequestBody.protocol+","+configRequestBody.host+","+configRequestBody.port;
				} else if (action === "listUploadedFiles") {
						header.requestMethod = "GET";
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/listUploadedFiles?appDirName="+appDirName+"&isEnvSpecific="+commonVariables.envSpecifig+"&configName="+configRequestBody.name+"&envName="+configRequestBody.envName+"&configType="+configRequestBody.type+"&propName="+configRequestBody.propName+moduleParam;
				} else if (action === "deleteFile") {
						header.requestMethod = "GET";
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/removeFile?appDirName="+appDirName+"&configType="+configRequestBody.configType+"&propName="+configRequestBody.propertyName+"&fileName="+configRequestBody.fileName+"&envName="+configRequestBody.envName+"&configName="+configRequestBody.configName+moduleParam;
				} else if (action === "fileBrowse") {
						header.requestMethod = "GET";
						header.dataType = "xml";
						header.contentType = "application/xml",
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/fileBrowse?&appDirName="+appDirName+moduleParam;
				} else if (action === "certificate") {
						header.requestMethod = "GET";
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/returnCertificate?host="+configRequestBody.host+"&port="+configRequestBody.port+"&appDirName="+appDirName;
				} else if (action === "addCertificate") {
						header.requestMethod = "POST";
						header.requestPostBody = JSON.stringify(configRequestBody);
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/addCertificate";
				} else if(action === "configType") {
						header.requestMethod = "POST";
						header.requestPostBody = {};
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/configType?userId="+userId+"&type="+deleteEnv+"&customerId="+customerId+"&appDirName="+appDirName+moduleParam;
				} else if(action === "showFeatureConfigs") {
						header.requestMethod = "POST";
						header.requestPostBody = {};
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/showFeatureConfigs?userId="+userId+"&customerId="+customerId+"&appDirName="+appDirName+"&envName="+deleteEnv+"&featureName="+ configRequestBody+moduleParam;
				}
			} else {
				if (action === 'list') {
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/allEnvironments?projectCode="+projectCode;
				} else if (action === "saveEnv") {
						header.requestMethod = "POST";
						header.requestPostBody = JSON.stringify(configRequestBody);
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"?projectCode="+projectCode+"&projectId="+projectId+"&customerId="+customerId;
				} else if (action === 'edit') {
					if (configRequestBody.envSpecific === false) {
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"?projectCode="+projectCode+"&configName="+commonVariables.environmentName+"&isEnvSpecific="+configRequestBody.envSpecific;
					} else {
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"?projectCode="+projectCode+"&envName="+commonVariables.environmentName;
					} 
				} else if (action === "template") {
					self.bcheck = true;
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?projectId="+projectId+"&customerId="+customerId+"&userId="+userId+"&type="+deleteEnv;
				} else if(action === "configType") {
					header.requestMethod = "POST";
					header.requestPostBody = {};
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/configType?userId="+userId+"&type="+deleteEnv+"&customerId="+customerId+"projectCode="+projectCode;
				} else if (action === "cloneEnv") {
					header.requestMethod = "POST";
					header.requestPostBody = JSON.stringify(configRequestBody);
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/cloneEnvironment?projectCode="+projectCode+"&envName="+deleteEnv;
				} else if (action === 'delete') {
					self.bcheck = true;
					header.requestMethod = "DELETE";
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/deleteEnv?projectCode="+projectCode+"&envName="+deleteEnv;
				} else if (action === "deleteConfig") {
					header.requestMethod = "DELETE";
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/deleteConfig?projectCode="+projectCode+"&configName="+deleteEnv;
				} else if (action === "saveConfig") {
					header.requestMethod = "POST";
					header.requestPostBody = JSON.stringify(configRequestBody);
					if (deleteEnv === "false") {
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/updateConfig?appDirName="+appDirName+"&isEnvSpecific="+deleteEnv+"&configName="+commonVariables.updateConfigName+"&customerId="+customerId+"&userId="+userId+moduleParam+"&projectId="+projectId;
					} else {
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/updateConfig?projectCode="+projectCode+"&envName="+deleteEnv+"&customerId="+customerId+"&userId="+userId+"&oldEnvName="+self.oldEnvName+"&defaultEnv="+self.defaultEnv+"&desc="+self.desc+"&isfavoric="+false+"&projectId="+projectId;
					}
				} else if (action === "isAliveCheck") {
					header.requestMethod = "GET";
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/connectionAliveCheck?url="+configRequestBody.protocol+","+configRequestBody.host+","+configRequestBody.port;
				} else if (action === "certificate") {
					header.requestMethod = "GET";
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/returnCertificate?host="+configRequestBody.host+"&port="+configRequestBody.port+"&appDirName=''";
				} else if (action === "addCertificate") {
						header.requestMethod = "POST";
						header.requestPostBody = JSON.stringify(configRequestBody);
						header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/addCertificate";
				} else if (action === "fileBrowse") {
					header.requestMethod = "GET";
					header.dataType = "xml";
					header.contentType = "application/xml",
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/fileBrowse";
				} else if (action === "addEnvValidate") {
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/validate?customerId="+customerId+"&environmentName="+deleteEnv+"&projectId="+projectId+"&projectCode="+projectCode;
				}
			}	
			return header;
		},
		
		constructHtml : function(configTemplates, configuration, currentConfig, envSpecificVal){
			var flag = 0;
			var htmlTag = "";
			var key = "";
			var self=this;
			var show = "";
			var required = "";
			var editable = "";
			var multiple = "";
			var sort = "";
			var multiple = "";
			var checked = "";
			var configProperties = "";
			var bCheck = false;
			var fCheck = false;
			var configTemplate = configTemplates.data.settingsTemplate;
			if(currentConfig === 'Server') {
				self.serverTypeVersion = configTemplates.data.downloadInfo;
			}
			
			if(currentConfig === 'Database') {
				self.databaseTypeVersion = configTemplates.data.downloadInfo;
			}
			
			if (configTemplate.length !== 0) {
				var content = "";
				var configName = "";
				var configDesc = "";
				if (configuration !== null && configuration !== undefined && configuration !== "") {
					configName = configuration.name;
					configDesc = configuration.desc;
				}
				if ($.isEmptyObject(self.configTemName)) {
					self.configTemName.push(configTemplate.name);
				} else {
					var found = $.inArray(configTemplate.name, self.configTemName) > -1;
					if (found === false) {
						self.configTemName.push(configTemplate.name);
					} else {
						self.count++;
						$("tbody[name=ConfigurationLists]").children('tr.row_bg').each(function(){
							if(currentConfig === 'Server') {
								if($(this).attr('configType') === currentConfig) {
									bCheck = true;									
									commonVariables.api.showError("serveralreadyadded" ,"error", true, false, true);
									flag = 1;
								}
							}
							
							if(currentConfig === 'Email') {
								if($(this).attr('configType') === currentConfig) {
									bCheck = true;		
									commonVariables.api.showError("emailalready" ,"error", true, false, true);
								}
							}
						});
					}
				}
				
				if (self.count === 0) {
					self.count = "";
				}
				
				var removeIcon = "";
				if(envSpecificVal === false) { 
					removeIcon = ''; 
				} else {
					removeIcon = '<a href="javascript:;" name="removeConfig"><img src="themes/default/images/Phresco/close_red.png" border="0" alt="" class="flt_right"/></a>';
				}
				
				var headerTr = '<tr type="'+configTemplate.name+self.count+'" class="row_bg" envSpecificVal="'+envSpecificVal+'" configType="'+configTemplate.name+'"><td colspan="3">' + configTemplate.name + '</td><td colspan="3">'+removeIcon+'</td></tr>';
				content = content.concat(headerTr);
				var defaultTd = '<tr name="'+configTemplate.name+'" class="'+configTemplate.name+self.count+'"><td class="labelTd">Name <sup>*</sup></td><td><input type="text" id="Config'+configTemplate.name+self.count+'" mandatory="true" maxlength="30" title="30 Characters only" class="configName" value="'+configName+'" placeholder= "Configuration Name"/></td><td class="labelTd">Description</td><td><input type="text" class="configDesc" value="'+configDesc+'" maxlength="150" title="150 Characters only" placeholder= "Configuration Description"/></td>';
				content = content.concat(defaultTd);
				var count = 2;
				var i = 2;
				var configPropertiesType = "";
				if (configuration.properties !== undefined) {
					configProperties = configuration.properties;
					configPropertiesType = configProperties.type;
				}
				
				var propTempKeys = [];
				$.each(configTemplate.properties, function(index, value) {
					var key = value.key;
					propTempKeys.push(key);
					var label = value.name;
					var type = value.type;
					
					var configValue = "";
					if (configProperties[key] !== undefined) {
						configValue = configProperties[key];
					}
					
					var mandatoryCtrl = "";
					var required = false;
					if (value.required) {
						mandatoryCtrl = ' <sup>*</sup>';
						required = true;
					}
					var control = "";
					if  (count % 3 === 0) {
						control = '<tr name="'+configTemplate.name+'" class="'+configTemplate.name+self.count+'"><td class="labelTd">' + label + mandatoryCtrl + '</td><td>';
					} else {
						control = '<td class="labelTd">' + label + mandatoryCtrl + '</td><td>';
					}
					var inputCtrl = "";
					if (value.possibleValues !== null &&  value.possibleValues.length !== 0) {
						inputCtrl = '<select mandatory="'+required+'" class="'+configTemplate.name+self.count+'Configuration selectpicker" configKey ="configKey'+configTemplate.name+key+'" temp="'+configTemplate.name+key+self.count+'" name="' + value.key + '">';
						var possibleValues = value.possibleValues;
						var options = "";
						for (j in possibleValues) {
							var selectedAttr = "";
							if (possibleValues[j] === configValue) {
								selectedAttr = "selected";
							}
							options = options.concat('<option value="' + possibleValues[j] + '" '+ selectedAttr +'>' + possibleValues[j] + '</option>');
						}
						inputCtrl = inputCtrl.concat(options);
						inputCtrl = inputCtrl.concat("</select></td>");
						
					} else if (type === "Password") {
						inputCtrl = '<input value="'+ configValue +'" class="'+configTemplate.name+self.count+'Configuration" name="'+key+'" mandatory="'+required+'" type="password" temp="'+configTemplate.name+key+self.count+'" placeholder=""/>';
					} else if (type === "FileType") {
						inputCtrl = '<div fileUpload = "fileupload" id="file-uploader'+currentConfig+'" currentConfig="'+currentConfig+'" class="'+configTemplate.name+self.count+'Configuration" mandatory="'+required+'" class="file-uploader" propTempName="'+key+'"><noscript><p>Please enable JavaScript to use file uploader.</p><!-- or put a simple form for upload here --></noscript>  </div>';
						fCheck = true;
					} else if (type === "Actions") {
						inputCtrl = '<input value="'+ label +'" validateButton="validate" class="'+configTemplate.name+self.count+'Configuration btn btn_style" name="'+key+'" mandatory="'+required+'" type="button" />';
						fCheck = true;
					} else if (key === "scheduler") {
						inputCtrl = '<input value="'+ configValue +'" class="'+configTemplate.name+self.count+'Configuration" temp="'+configTemplate.name+key+self.count+'" name="'+key+'" mandatory="'+required+'" type="text" placeholder=""/><a name="cron_expression"><img src="themes/default/images/Phresco/settings_icon.png" width="23" height="22" border="0" alt=""></a><div id="cron_expression" class="dyn_popup" style="display:none"></div>';
					} else if (type === "Boolean") {
						var checked = "";
						if(configValue === "true") {
							checked = "checked";
						} else if(configValue === "" || configValue === null) {
							configValue = "false";
						}
						inputCtrl = '<input value="'+ configValue +'" '+checked+' class="'+configTemplate.name+self.count+'Configuration" name="'+key+'" mandatory="'+required+'" type="checkbox"/>';
					} else {
						if (key === 'type') {
							inputCtrl = '<select mandatory="'+required+'" class="'+configTemplate.name+self.count+'Configuration selectpicker" name="' + value.key + '">';
							
							var options1 = "";
							if(currentConfig === 'Server') {
								$.each(self.serverTypeVersion, function(key, value){
									var selectedAttr = "";
									if (key === configValue) {
										selectedAttr = "selected";
									}
									options1 = options1.concat('<option value="' + key + '" '+selectedAttr+'>' + key + '</option>');
								});
							
								 if ((options1 === "") && (flag === 0)){ 
									commonVariables.api.showError("chooseserver" ,"success", true, false, true);									
								} 
							} else if(currentConfig === 'Database') {
								$.each(self.databaseTypeVersion, function(key, value){
									var selectedAttr = "";
									if (key === configValue) {
										selectedAttr = "selected";
									}
									options1 = options1.concat('<option value="' + key + '" '+selectedAttr+'>' + key + '</option>');
								});
							
								 if (options1 === ""){
									commonVariables.api.showError("choosedb" ,"success", true, false, true);
								}
							}
							inputCtrl = inputCtrl.concat(options1);
							inputCtrl = inputCtrl.concat("</select></td>");
						} else if (key === 'version') {
							inputCtrl = '<select mandatory="'+required+'" class="'+configTemplate.name+self.count+'Configuration selectpicker" currentConfig="'+configTemplate.name+self.count+'" name="' + value.key + '">';
							var options1 = "";
							var i=0;
							if(currentConfig === 'Server') {
								if (configPropertiesType !== "") {
									$.each(self.serverTypeVersion, function(key, value){
										for(var k=0; k<value.length; k++) {
											var selectedAttr = "";
											if (value[k] === configValue && configPropertiesType === key) {
												selectedAttr = "selected";
											}
											options1 = options1.concat('<option value="' + value[k] + '" '+selectedAttr+'>' + value[k] + '</option>');
										}
									});
								} else {
									$.each(self.serverTypeVersion, function(key, value){
										for(var k=0; k<value.length; k++) {
											options1 = options1.concat('<option value="' + value[k] + '">' + value[k] + '</option>');
										}
										i++;
										if (i === 1) {
											return false;
										}
									});
								}
							} else if(currentConfig === 'Database') {
								if (configPropertiesType !== "") {
									$.each(self.databaseTypeVersion, function(key, value){
										for(var k=0; k<value.length; k++) {
											var selectedAttr = "";
											if (value[k] === configValue && configPropertiesType === key) {
												selectedAttr = "selected";
											}
											options1 = options1.concat('<option value="' + value[k] + '" '+selectedAttr+'>' + value[k] + '</option>');
										}
									});
								} else {
									$.each(self.databaseTypeVersion, function(key, value){
										for(var k=0; k<value.length; k++) {
											options1 = options1.concat('<option value="' + value[k] + '">' + value[k] + '</option>');
										}
										i++;
										if (i === 1) {
											return false;
										}
									});
								}
							}
							inputCtrl = inputCtrl.concat(options1);
							inputCtrl = inputCtrl.concat("</select></td>"); 
						} else if (key === 'certificate') { 
							inputCtrl = '<input mandatory="'+required+'" value="'+ configValue +'" class="'+configTemplate.name+self.count+'Configuration" name="'+key+'" temp="'+configTemplate.name+key+self.count+'" type="text" placeholder="" readonly/><a href="javascript:void(0)" name="remote_deploy"><img src="themes/default/images/Phresco/settings_icon.png" width="23" height="22" border="0" alt=""></a><div id="remote_deploy" class="dyn_popup" style="display:none"><div id="certificateValue"></div><div class="flt_right"><input type="button" name="selectFilePath" class="btn btn_style" value="Ok">&nbsp;&nbsp;<input type="button" value="Close" name="treePopupClose" class="btn btn_style dyn_popup_close"></div></div>';
						} else {
							inputCtrl = '<input mandatory="'+required+'" value="'+ configValue +'" configKey ="configKey'+configTemplate.name+key+'" class="'+configTemplate.name+self.count+'Configuration" name="'+key+'" temp="'+configTemplate.name+key+self.count+'" type="text" placeholder=""/>';
						}
					}
					control = control.concat(inputCtrl);
					content = content.concat(control);
					i = count++;
					
				});
				
				var bCheckVal = false;
				if(configTemplate.customProp === true) {
					var textBox = '<tr keyValue="keyValue'+configTemplate.name+self.count+'" name="'+configTemplate.name+'" class="'+configTemplate.name+self.count+'"><td><input configKey ="configKey'+configTemplate.name+'" class="ConfigKey" name="" temp="'+configTemplate.name+self.count+'" type="text" placeholder="Enter Key" /></td><td><input configKey ="configKey'+configTemplate.name+'" class="ConfigKeyValue" name="" temp="'+configTemplate.name+self.count+'" type="text" placeholder="Enter Value" /></td><td><div class="flt_right icon_center"><a href="javascript:;" name="add'+configTemplate.name+self.count+'"><img src="themes/default/images/Phresco/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="remove'+configTemplate.name+self.count+'"><img src="themes/default/images/Phresco/minus_icon.png" border="0" alt=""></a></div></td></tr>';
					if (configuration.properties !== undefined) {
						$.each(configuration.properties, function(keyVal, val) {
							if (($.inArray(keyVal, propTempKeys) === -1) && (keyVal !== "files")) {
								var textBoxAppend = '<tr keyValue="keyValue'+configTemplate.name+self.count+'" name="'+configTemplate.name+'" class="'+configTemplate.name+self.count+'"><td><input configKey ="configKey'+configTemplate.name+'" class="ConfigKey" name="" temp="'+configTemplate.name+self.count+'" type="text" value="'+keyVal+'" placeholder="Enter Key" /></td><td><input configKey ="configKey'+configTemplate.name+'" class="ConfigKeyValue" name="" temp="'+configTemplate.name+self.count+'" type="text" placeholder="Enter Value" value="'+val+'"/></td><td><div class="flt_right icon_center"><a href="javascript:;" name="add'+configTemplate.name+self.count+'"></a> <a href="javascript:;" name="remove'+configTemplate.name+self.count+'"><img src="themes/default/images/Phresco/minus_icon.png" border="0" alt=""></a></div></td></tr>';
								content = content.concat(textBoxAppend);
								bCheckVal = true;
							}
						});
					}
					if (bCheckVal === false) {	
						content = content.concat(textBox);
					}
				}
				
				if (bCheck === false) {
					if (envSpecificVal === true && self.favourite === true) {
						if(currentConfig === commonVariables.favConfig) {
							$("tbody[name=ConfigurationLists]").append(content);
						}
					} else {
						$("tbody[name=ConfigurationLists]").append(content);
					}
					
					var addIcon = '<img src="themes/default/images/Phresco/plus_icon.png" border="0" alt="">';
					if(configTemplate.customProp === true) {
						if (bCheckVal === true) {
							$('tr[keyValue=keyValue'+configTemplate.name+self.count+']').last('tr[keyValue=keyValue'+configTemplate.name+self.count+']').find("a[name=add"+configTemplate.name+self.count+"]").html(addIcon);
						}
						self.addRemoveConfigKeyValue(configTemplate.name+self.count, textBox);
					}
					
					if (currentConfig === 'Server') {
						self.remoteDeploy();
					}
					self.multiselect();
					$("a[name=removeConfig]").unbind("change");
					$('select[name=type]').unbind("change");
					self.severDbOnChangeEvent();
					
					var host = $('input[temp='+configTemplate.name+key+'host'+self.count+']').val();
					var port = $('input[temp='+configTemplate.name+key+'port'+self.count+']').val();
					var protocol = $('select[temp='+configTemplate.name+key+'protocol'+self.count+'] option:selected').val();
					var type = configTemplate.name+self.count;
					if (protocol === undefined) {
						protocol = "http";
					}
					self.isAliveCheck(host, port, type, protocol);
					if (currentConfig === 'Scheduler') {
						self.CroneExpression();
					}
					
				}
								
				$("a[name=removeConfig]").unbind("click");
				self.removeConfiguration();
				self.spclCharValidation();
				self.restrictSpaceInOthersKey();
				if (fCheck === true) {
					var strFile = '';
					var configFileData = '';
					if (configuration.properties !== undefined) {
						configFileData = configuration;
						var arr = configuration.properties.files.split("\\");
						strFile = arr[arr.length-1];
					}
					self.createUploader("file-uploader"+currentConfig, currentConfig, strFile, configFileData);
				}
			}
		},
		
		addRemoveConfigKeyValue : function(addConfig, value) {
			var self = this, addIcon = '<img src="themes/default/images/Phresco/plus_icon.png" border="0" alt="">';
			$("a[name=add"+addConfig+"]").click(function(){
				var appendTo = $(this).parent().parent().parent("."+addConfig+":last");
				$(this).html('');
				self.addConfigValue(value, appendTo, addConfig);
				self.restrictSpaceInOthersKey();
			});
			
			$("a[name=remove"+addConfig+"]").click(function(){	
				$(this).parent().parent().parent().remove();
				$('tr[keyValue=keyValue'+addConfig+']').find("a[name=add"+addConfig+"]").html('');
				$('tr[keyValue=keyValue'+addConfig+']').last('tr[keyValue=keyValue'+addConfig+']').find("a[name=add"+addConfig+"]").html(addIcon);
			});
		},
		
		addConfigValue : function (value, appendTo, addConfig) {
			var self = this;
			$(value).insertAfter(appendTo);
			$("a[name=add"+addConfig+"]").unbind('click');
			self.addRemoveConfigKeyValue(addConfig, value);
		},
		
		createUploader : function(id, configType, file, configFileData) {     
			var self = this, appDirName, propertyName, envName;
			propertyName = $("#"+id).attr('proptempname');
			envName = $('input[name=EnvName]').val();
			$("input[validateButton=validate]").attr("disabled", true);
			if(commonVariables.api.localVal.getProjectInfo() !== null){
				var projectInfo = commonVariables.api.localVal.getProjectInfo();
				appDirName = projectInfo.data.projectInfo.appInfos[0].appDirName;
			}
            var uploader = new qq.FileUploader({
                element: document.getElementById(id),
                action: commonVariables.webserviceurl+commonVariables.configuration+'/uploadFile',
				actionType : "configuration",
				envName : envName,
				appDirName : appDirName,
				configType : configType,
				oldName : "oldName",
				propName : propertyName,
				buttonLabel: "Upload File",
				allowedExtensions: ["zip"],
				multiple: false,
				debug: true,	
				onComplete: function (id, fileName, responseJSON) {
					if ($("ul.qq-upload-list li").length > 0 ) {
						$("div[name=qq-upload-file]").attr("disabled", true).children().attr("disabled", true);
						$("input[validateButton=validate]").attr("disabled", false);
					} else {
						$("div[name=qq-upload-file]").attr("disabled", false).children().attr("disabled", false);
						$("input[validateButton=validate]").attr("disabled", true);
					}
					self.removeFile(configType, propertyName, envName, $(".configName").val());
				}
            }); 

			if ($(".configName").val() === "") {
				$("div[name=qq-upload-file]").attr("disabled", true).children().attr("disabled", true);
			}
			$(".configName").bind('input', function() {
				if ($(this).val() === "") {
					$("div[name=qq-upload-file]").attr("disabled", true).children().attr("disabled", true);
				} else {
					$("div[name=qq-upload-file]").attr("disabled", false).children().attr("disabled", false);
				}
			});
			
			if (file !== '' && file !== undefined) {
				var val = {};
				val.name = configFileData.name;
				val.type = configFileData.type;
				val.propName = propertyName;
				val.propName = propertyName;
				if (configFileData.envName !== null) {
					val.envName = configFileData.envName;
				} else {
					val.envName = '';
				}
				self.configRequestBody = val;
				self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "listUploadedFiles", ''), function(response) {
					if(response.data[0] !== undefined) {
						var arr = response.data[0].split("/");
						var strFile = arr[arr.length-1];
						$("ul.qq-upload-list").append('<li><span class="qq-upload-file">'+strFile+'</span><a name="removeFile" href="javascript:void(0)"><img src="themes/default/images/Phresco/cross_red.png" border="0" alt=""></a></li>');
						$("div[name=qq-upload-file]").attr("disabled", true).children().attr("disabled", true);
						$("input[validateButton=validate]").attr("disabled", false);
						self.removeFile(configType, propertyName, envName, $(".configName").val());
					} else {
						$("div[name=qq-upload-file]").attr("disabled", false).children().attr("disabled", false);
						$("input[validateButton=validate]").attr("disabled", true);
					}
				});
				$("#validationConsole").css("display", "block");
				
			}
			$("input[validateButton=validate]").click(function(){
				self.ValidateTheme();
			});
        },
		
		removeFile : function(configType, propertyName, envName, configName) {
			var self=this, val = {}, fileName;
			$("a[name=removeFile]").click(function(){
				fileName = $(this).parent().find(".qq-upload-file").text();
				val.configType = configType;
				val.propertyName = propertyName;
				if (envName === '' || envName === undefined || envName === null) {
					val.envName = '';
				} else {
					val.envName = envName;
				}
				val.configName = configName;
				val.fileName = fileName;
				self.configRequestBody = val;
				self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "deleteFile", ''), function(response) {
					commonVariables.api.showError(response.responseCode ,"success", true, false, true);
				});
				$(this).parent().remove();
				$("div[name=qq-upload-file]").attr("disabled", false).children().attr("disabled", false);
				$("input[validateButton=validate]").attr("disabled", true);
			});
		},
		
		ValidateTheme : function() {
			var self=this;
			var appdetails = commonVariables.api.localVal.getProjectInfo();
			var queryString = '';
			appId = appdetails.data.projectInfo.appInfos[0].id;
			projectId = appdetails.data.projectInfo.id;
			customerId = appdetails.data.projectInfo.customerIds[0];
			username = commonVariables.api.localVal.getSession('username');
						
			if (appdetails !== null) {
				queryString ="username="+username+"&appId="+appId+"&customerId="+customerId+"&goal=unit-test&phase=unit-test&projectId="+projectId;
			}
			$('#logContent').html('');
			self.openConsole();//To open the console
			$("section[name=configContent]").hide();
			commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
				retVal.mvnValidateTheme(queryString, '#logContent', function(response) {
					self.closeConsole();
					$("section[name=configContent]").show();
				});
			});
		},
		
		severDbOnChangeEvent : function () {
			var self=this;	
			$('select[name=type]').change(function() {
				var configTypeClass = $(this).parent().parent().attr('class');
				var configtype = $(this).parent().parent().attr('name');
				var configValue = $(this).val();
				var options = "";
				if(configtype === 'Server') {
					$.each(self.serverTypeVersion, function(key, value) {
						if (configValue === key) {
							$('select[currentConfig='+configTypeClass+']').html('');
							for(var k=0; k<value.length; k++) {
								options += '<option value="' + value[k] + '">' + value[k] + '</option>';
							}
							$('select[currentConfig='+configTypeClass+']').append(options);
							$('select[currentConfig='+configTypeClass+']').selectpicker('refresh');
						}
					});
				} 
				if (configtype === 'Database') {
					$.each(self.databaseTypeVersion, function(key, value) {
						if (configValue === key) {
							$('select[currentConfig='+configTypeClass+']').html('');
							for(var k=0; k<value.length; k++) {
								options += '<option value="' + value[k] + '">' + value[k] + '</option>';
							}
							$('select[currentConfig='+configTypeClass+']').append(options);
							$('select[currentConfig='+configTypeClass+']').selectpicker('refresh');
						}
					});
				}
			});
			
		},
		
		remoteDeploy : function() {
			var self=this;
			$("input[name=certificate]").parent().prev('td').hide();
			$("a[name=remote_deploy]").html('');
			$("input[name=certificate]").hide();
			if ($("input[name=remoteDeployment]").val() === '' && $("input[name=remoteDeployment]").val() === null) {
				$("input[name=remoteDeployment]").val(false);
			} else if ($("input[name=remoteDeployment]").val() === "true") {
				self.showCertificate();
			}
			
			$("input[name=remoteDeployment]").click(function() {
				$("input[name=deploy_dir]").val('');
				if ($(this).is(':checked')) { 
					$(this).val(true);
					self.showCertificate();
				} else {
					$(this).val(false);
					self.hideCertificateTab();
					$("input[name=deploy_dir]").attr("mandatory", true);
					$("input[name=deploy_dir]").parent().prev('td').show();
					$("input[name=deploy_dir]").show();
					$("input[name=admin_username]").attr("mandatory", false);
					$("input[name=admin_password]").attr("mandatory", false);
					$("input[name=admin_username]").parent().prev('td').find('sup').html('');
					$("input[name=admin_password]").parent().prev('td').find('sup').html('');
				}
			});
			
			$('a[name=remote_deploy]').click(function(){
				var current = this;
				var val = {}, disName = [];
				val.host = $("input[configKey=configKeyServerhost]").val();
				val.port = $("input[configKey=configKeyServerport]").val();
				self.configRequestBody = val;
				self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "certificate", ''), function(response) {
					if(response.data == null){
						$('input[name=certificate]').val('');
					} 
					$("#certificateValue").css("height", "250px");
					$("#certificateValue").html('');
					if(response.data !== null) {
						disName = response.data.certificates[0].displayName.split(",");
						$("#certificateValue").append('<select name="certificateValue">"'+self.getDisName(disName)+'"</select>');
						self.popupforTree(current, $(current).attr('name'));
						var cerValue = $('select[name=certificateValue]').val();
						self.saveCertificate(cerValue);
					} else {
						self.configRequestBody = {};
						self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "fileBrowse", ''), function(response) {
							self.fileTree(response, false, "", function(res){
								var temptree = $('<div class=""></div>');
								temptree.append('<ul id="filetree" class="filetree"><li><span><strong>Click here to select file path </strong></span>' + res + '</li></ul>');
								setTimeout(function(){
									$(temptree).jstree({
									"themes": {
										"theme": "default",
										"dots": false,
										"icons": false,
										"url": "themes/default/css/Helios/style.css"
									}
									}).bind("init.jstree", function(event, data){ 
									}).bind("loaded.jstree", function (event, data) {
										$("#certificateValue").append(temptree);
										self.treeclickEvent();	
										self.popupforTree(current, $(current).attr('name'));
										$("#certificateValue").mCustomScrollbar({
											autoHideScrollbar:true,
											theme:"light-thin",
											advanced:{ updateOnContentResize: true}
										});	
									}); 
								}, 100);
							});
						});
					}
				});
				
			});
			
			$('input[name=treePopupClose]').click(function(){
				$("#remote_deploy").hide();
			});
			
			$('select[configKey=configKeyServerprotocol]').change(function() {
				if($(this).val() === 'https' && $("input[configKey=configKeyServerport]").val() !== "" && $("input[configKey=configKeyServerhost]").val() !== "" && $("input[name=remoteDeployment]").is(':checked')) {
					self.showCertificateTab();
				} else {
					self.hideCertificateTab();
				}
			});
			
		},
		
		treeclickEvent : function() {
			var self=this;
			$('span.folder, span.file').click(function(e){
				$("span.folder a, span.file a").removeClass("selected");
				$(this).find("a").attr("class", "selected");
				var path = $(this).parent().attr('value');
				path = path.replace(/\+/g,' ');
				$("input[name=selectFilePath]").unbind('click');
				self.saveCertificate(path);
			});
		},
		
		saveCertificate : function(value) {
			var self=this, appDirName;
			$("input[name=selectFilePath]").unbind('click');
			$("input[name=selectFilePath]").click(function() {
				var cerficate = value;
				$("input[name=certificate]").val(cerficate);
				var customerId = self.getCustomer();
				customerId = (customerId === "") ? "photon" : customerId;
				if(commonVariables.api.localVal.getProjectInfo() !== null){
					var projectInfo = commonVariables.api.localVal.getProjectInfo();
					appDirName = projectInfo.data.projectInfo.appInfos[0].appDirName;
				}
				var projectCode = commonVariables.api.localVal.getSession('projectCode');
				var certificateJson = {};
				
				if (appDirName !== null  && appDirName !== undefined) {
					certificateJson.appDirName = appDirName;
					certificateJson.fromPage = 'configuration';
				} else {
					certificateJson.projectCode = projectCode;
					certificateJson.fromPage = 'settings';
				}
				certificateJson.customerId = customerId;
				certificateJson.host = $("input[configKey=configKeyServerhost]").val();
				certificateJson.port = $("input[configKey=configKeyServerport]").val();
				certificateJson.certificateName = value;
				certificateJson.environmentName = $('input[name=EnvName]').val();
				certificateJson.configName = $(this).parent().parent().parent().parent().attr('name');
				certificateJson.propValue = value;
				certificateJson.moduleName = $('.moduleName').val();
				self.configRequestBody = certificateJson;
				self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "addCertificate", ''), function(response) {
					commonVariables.api.showError(response.responseCode ,"success", true, false, true);
					$("#remote_deploy").hide();
					self.closeTreePopup();
					if(response.data != null){
						$('input[name=certificate]').val(response.data);
					} else {
						$('input[name=certificate]').val('');
					}
				});
			});
		},
		
		showCertificate : function() {
			var self = this;
			$("input[name=deploy_dir]").attr("mandatory", false);
			$("input[name=deploy_dir]").parent().prev('td').hide();
			$("input[name=deploy_dir]").hide();
			$("input[name=admin_username]").attr("mandatory", true);
			$("input[name=admin_password]").attr("mandatory", true);
			$("input[name=admin_username]").parent().prev('td').append(' <sup>*</sup>');
			$("input[name=admin_password]").parent().prev('td').append(' <sup>*</sup>');
			if ($("select[configKey=configKeyServerprotocol]").val() === 'https' && $("input[configKey=configKeyServerport]").val() !== "" && $("input[configKey=configKeyServerhost]").val() !== "") {
				self.showCertificateTab();
			}
		},
		
		showCertificateTab : function () {
			var self = this, remoteValue = '<img src="themes/default/images/Phresco/settings_icon.png" width="23" height="22" border="0" alt="">';;
			$("input[name=certificate]").parent().prev('td').show();
			$("input[name=certificate]").show();
			$("a[name=remote_deploy]").html(remoteValue);
		},
		
		hideCertificateTab : function() {
			var self = this;
			$("input[name=certificate]").parent().prev('td').hide();
			$("input[name=certificate]").hide();
			$("input[name=certificate]").val('');
			$("a[name=remote_deploy]").html('');
		},
		
		getDisName : function(disName) {
			var self=this, option='';
			
			for(i=0; i<disName.length; i++) {
				var val = (i === 0) ? "*" : i;
				option += '<option value='+disName[i]+'>'+disName[i]+'</option>';
			}
			return option;
		},
		
		isAliveCheck : function(host, port, type, protocol) {
			var self=this, aliveJson = {};
			if (host !== '' && host !== undefined && port !== '' && port !== undefined) {
				aliveJson.host = host;
				aliveJson.port = port;
				aliveJson.type = type;
				aliveJson.protocol = protocol;
				self.configRequestBody = aliveJson;
				self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "isAliveCheck", ''), function(response) {
					var typeMatch = aliveJson.type;
					if(response.data === true) {
						$(".row_bg").each(function() {
							var type = $(this).attr("type");
							if (type === typeMatch) {
								$(this).find("td:first-child").after("<td class=active>Active</td>");
							}
						});
					} else {
						$(".row_bg").each(function() {
							var type = $(this).attr("type");
							if (type === typeMatch) {
								$(this).find("td:first-child").after("<td class=inactive>In Active</td>");
							}
						});
					}
				});
			}
			
		},
		
		CroneExpression : function() {
			var self=this;
			$('a[name=cron_expression]').click(function(){
				self.opencc(this, $(this).attr('name'));
				self.croneExp = new Clazz.com.components.croneExpression.js.croneExpression();
				self.croneExp.croneExpressionlistener.rendercrontemp($("#cron_expression"));
				
			});
		},	
		
		getComponentName : function(type, value, callback) {
			var self = this, selectedVal = '';
			if(type === "Components" || type === "Features") {
				$.each(value.properties, function(key, val){
					if(key === "components" || key === "features") {
						selectedVal = val;
					}
				});
			}
			callback(selectedVal);
		},
		
		htmlForOther : function(data, value, types) {
			var self = this, headerTr, content = '', textBox, apiKey = "", keyValue = "", type="Other", name="", desc="", addIcon = '<img src="themes/default/images/Phresco/plus_icon.png" border="0" alt="">';
				if (value !== null && value !== '') {
					type = value.type;
					name = value.name;
					desc = value.desc;
				}
				
				if ($.isEmptyObject(self.configTemp)) {
					self.configTemp.push(types);
				} else {
					var found = $.inArray(types, self.configTemp) > -1;
					if (found === false) {
						self.configTemp.push(types);
					} else {
						self.countVal++;
					}
				}
				 
				if (types === "") {
					headerTr = '<tr class="row_bg" type="otherConfig" configType="'+type+'"><div class="row"><td colspan="3">' + type + '</td><td colspan="3">'+'<a href="javascript:;" name="removeConfig"><img src="themes/default/images/Phresco/close_red.png" border="0" alt="" class="flt_right"/></a></td></div></tr>';
					content = content.concat(headerTr);
					
					var defaultTd = '<tr name="configName" class="otherConfig" name="'+type+'"><td class="labelTd">Name <sup>*</sup></td><td><input type="text" id="ConfigOther" maxlength="30" title="30 Characters only" mandatory="true" class="configName" value="'+name+'" placeholder= "Configuration Name"/></td><td class="labelTd">Description</td><td><input type="text" id="ConfigOther" class="configDesc" maxlength="150" title="150 Characters only"  value="'+desc+'" placeholder= "Configuration Description"/></td>';
					content = content.concat(defaultTd);
					
					if (value !== null && value !== '') {
						$.each(value.properties, function(key, value){
							textBox = '<tr class="otherConfig" name="'+type+'"><td></td><td><input type="text" placeholder= "key" class="otherKey" value="'+key+'"/><td><input type="text" placeholder= "value" class="otherKeyValue" value="'+value+'"/></td><td><div class="flt_right icon_center"><a href="javascript:;" name="addOther"></a> <a href="javascript:;" name="removeOther"><img src="themes/default/images/Phresco/minus_icon.png" border="0" alt=""></a></div></td></tr>';
							content = content.concat(textBox);
						});
					} else {
						textBox = '<tr class="otherConfig" name="'+type+'"><td></td><td><input type="text" placeholder= "key" class="otherKey"/><td><input type="text" placeholder= "value" class="otherKeyValue" /></td><td><div class="flt_right icon_center"><a href="javascript:;" name="addOther"><img src="themes/default/images/Phresco/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeOther"></a></div></td></tr>';
						content = content.concat(textBox);
					}
					
				} else {
					headerTr = '<tr class="row_bg" type="'+types+self.countVal+'" configType="'+types+'"><div class="row"><td colspan="3">' + types + '</td><td colspan="3">'+
					'<a href="javascript:;" name="removeConfig"><img src="themes/default/images/Phresco/close_red.png" border="0" alt="" class="flt_right"/></a></td></div></tr>';
					content = content.concat(headerTr);
					var defaultTd = '<tr name="'+types+'" class="'+types+self.countVal+'" ><td class="labelTd">Name <sup>*</sup></td><td><input type="text" maxlength="30" title="30 Characters only" mandatory="true" id="'+types+self.countVal+'" class="configName" value="'+name+'" placeholder= "Configuration Name"/></td><td class="labelTd">Description</td><td><input type="text" class="configDesc" maxlength="150" title="150 Characters only"  value="'+desc+'" placeholder= "Configuration Description"/></td><td class="labelTd">'+types+'</td><td><select compType="components'+self.countVal+'" name="components" count="'+self.countVal+'" class="'+types+self.countVal+'Configuration" configkey="configkey'+types+'">'+self.ComponentData(data, types, value)+'</select></td>';
					content = content.concat(defaultTd);
				}
				
				$("tbody[name=ConfigurationLists]").append(content);
				$("select[compType=components"+self.countVal+"]").unbind("change");
				self.componentChange(types, self.countVal, value);
				
				if (value !== null && value !== '') {
					$("tr.otherConfig:last").find("a[name=addOther]").html(addIcon);
					if ($("tr.otherConfig").length === 1) {
						$("a[name=removeOther]").html('');
					}
				}
				$("a[name=removeConfig]").unbind("click");
				self.removeConfiguration();
				self.addClick();
		},
		
		ComponentData : function(data, types, value) {
			var self=this, option='', selected = '';
			option = '<option>Select '+types+'</option>';
			$.each(data, function(index, val){
				if(value !== '' && value !== null){
					self.getComponentName(types, value, function(response){
						if(response === val){
							option += '<option value='+val.replace(/ /g, "%20")+' selected>'+val+'</option>';
						} else {
							option += '<option value='+val.replace(/ /g, "%20")+'>'+val+'</option>';
						}
					});
				} else {
					option += '<option value='+val.replace(/ /g, "%20")+'>'+val+'</option>';
				}
			});
			return option;
		},
		
		componentChange : function(types, countValue, value) {
			var self = this;
			if(value !== '' && value !== null && types !== '') {
				var fName = $("select[compType=components"+countValue+"]").val();
				self.getProperties(fName, $("select[compType=components"+countValue+"]"), $("select[compType=components"+countValue+"]").attr('count'), types);
			}
			
			$("select[compType=components"+countValue+"]").change(function(){
				countValue = $(this).attr('count');
				var current = $(this);
				var currentRow = $(this).parent().parent().next();
				$(this).parent().parent().next().addClass('remove');				
				while(currentRow !== null && currentRow.length > 0) {
				   if (currentRow.attr('class') !== "row_bg") {
					   currentRow.addClass('remove');
					   currentRow = currentRow.next('tr');
				   } else {
					   currentRow = null;
				   }
				}
				$('.remove').remove();
				var featureName = $(this).val();
				self.getProperties(featureName.replace(/%20/g, " "), current, countValue, types);
			});
		},
		
		getProperties : function(featureName, current, countValue, types) {
			var self = this, content='', count = 3;;
			var envrName = "";
			if ($('input[name=EnvName]').val() !== undefined) {
				envrName = $('input[name=EnvName]').val();
			}
			self.getConfigurationList(self.getRequestHeader(featureName, "showFeatureConfigs", envrName), function(response) {
				$.each(response.data.propertyTemplates,function(index, propertyTemplate) {
					var str = null;
					self.getPropertyValue(propertyTemplate.key, response.data.properties, function(returnVal){
						var inputnameval = propertyTemplate.key.replace(/ /g,'_');
						var control = "";
						if  (count % 3 === 0) {
							control = '<tr name="'+types+'" class="'+types+countValue+'"><td class="labelTd">'+propertyTemplate.key+'</td><td><input class="'+types+countValue+'Configuration" name='+inputnameval+' configkey="configkey'+inputnameval+'" temp="'+inputnameval+countValue+'" type="text" value='+returnVal+'></td>';
						} else {
							control = '<td class="labelTd">'+propertyTemplate.key+'</td><td><input class="'+types+countValue+'Configuration" name='+inputnameval+' configkey="configkey'+inputnameval+'" type="text" temp="'+inputnameval+countValue+'" value='+returnVal+'></td>';
						}
						count++;
						content = content.concat(control);
					}); 
				});
				$(content).insertAfter(current.parent().parent());
			});
		},
		
		getPropertyValue : function(key, response, callback) {
			var property = '';
			callback(response[key]); 
		},
		
		addOtherConfig : function(toAppend) {
			var self = this, dynamicValue, textBox = '<tr class="otherConfig" name="Other"><td></td><td><input type="text" class="otherKey" placeholder= "key"/><td><input type="text" placeholder= "value" class="otherKeyValue"/></td><td><div class="flt_right icon_center"><a href="javascript:;" name="addOther"><img src="themes/default/images/Phresco/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeOther"><img src="themes/default/images/Phresco/minus_icon.png" border="0" alt=""></a></div></td></tr>', minusIcon = '<img src="themes/default/images/Phresco/minus_icon.png" border="0" alt="">';
			dynamicValue = $(textBox).insertAfter(toAppend);
			dynamicValue.prev('tr').find('a[name="addOther"]').html('');
			dynamicValue.prev('tr').find('a[name="removeOther"]').html(minusIcon);
			$("a[name=addOther]").unbind("click");
			$("a[name=removeOther]").unbind("click");
			self.addClick();
		},
		
		addClick : function() {
			var self = this;
			$("a[name=addOther]").click(function() {
				var toAppend = $(this).parents(".otherConfig:last");
				self.addOtherConfig(toAppend);
			});
			
			$("a[name=removeOther]").click(function(){
				self.removeClick($(this));
			});
		},
		
		removeClick : function(removeValue) {
			var self=this, addIcon = '<img src="themes/default/images/Phresco/plus_icon.png" border="0" alt="">';
				$("a[name=addOther]").html('');
				removeValue.parent().parent().parent().remove();
				$("a[name=removeOther]").parents('tr:last').find('a[name="addOther"]').html(addIcon);
				if (($("a[name=removeOther]").parents('tr.otherConfig').length) === 1) {
					$('tr.otherConfig').find('a[name="addOther"]').html(addIcon);
					$("a[name=removeOther]").html('');
				}
		},
		
		addEnvEvent : function(envName, envDesc, type) {
			var self = this, val = '', res = '';
			if (type === "config") {
				val = '<div><input type="radio" name="optionsRadiosfd"></div>';
				res = "configurations";
			} else {
				res = "settings";
				val = ''
			}
			self.getValidationResult(self.getRequestHeader(self.configRequestBody, "addEnvValidate", envName), function(response) {
				if (response.status === "success") {
					$("ul[name=envList]").append('<li draggable="true" name="'+envName+'">'+val+'<div  class="envlistname" name="envListName">'+envName+'</div><input type="hidden" name="envListDesc" value="'+envDesc+'"></li>');
					$('.connected').sortable({
						connectWith: '.connected'
					});
					$("input[name=envName]").val('');
					$("input[name=envDesc]").val('');
				} else {
					commonVariables.api.showError(res ,"error", true, false, true);
				}
			});
		},
		
		saveEnvEvent : function(envWithConfig, callback) {
			var self = this, toAppend;
			self.envJson = [];
			$.each($("ul[name=envList]").children(), function(index, value) {
				var envNameDesc = {};
				envNameDesc.name = $(value).find("div[name=envListName]").html();
				envNameDesc.desc = $(value).find("input[name=envListDesc]").val();
				envNameDesc.configurations = [];
				for (i=0; i<envWithConfig.length; i++) {
					if(envWithConfig[i].name === envNameDesc.name) {
						if (envWithConfig[i].configurations !== null) {
							envNameDesc.configurations = envWithConfig[i].configurations;
						}
					}
				}
				envNameDesc.appliesTo = [""];
				envNameDesc.defaultEnv = $(value).find("input[name=optionsRadiosfd]").is(':checked');
				if (envNameDesc.name !== undefined && envNameDesc.name !== null) {
					self.envJson.push(envNameDesc);
				}
			});
			callback(self.envJson);
		},
		
		addConfiguration : function(config) {
			var self=this;
			if(config === "Components" || config === "Features") {
				self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "configType", config), function(response) {
					self.htmlForOther(response.data, '', config);
				});
			} else if (config !== "Other" && (config !== "Components" || config !== "Features")) {
				self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "template", config), function(response) {
					self.constructHtml(response, '', config, '', true);
				});
			} else {
				self.htmlForOther('', '', '');
			}
		},
		
		removeConfiguration : function() {
			var self = this;
			$("a[name=removeConfig]").click(function() {
				self.removeConfigFunction($(this));
			});
		},
		
		removeConfigFunction : function(removeValue) {
			var currentRow = removeValue.parent().parent().next();
			removeValue.parent().parent().addClass('remove');				
			while(currentRow !== null && currentRow.length > 0) {
			   if (currentRow.attr('class') !== "row_bg") {
				   currentRow.addClass('remove');
				   currentRow = currentRow.next('tr');
			   } else {
				currentRow = null;
			   }
			}
			$('.remove').remove();
		},
		
		updateConfig : function() {
			var self=this, configJson = {}, properties = {};
			self.configList = [];
			$.each($(".row_bg"), function(index, value) {
				properties = {};
				configJson = {};
				var type = $(this).attr("type");
				if (($(this).attr("configtype") === "Theme") || ($(this).attr("configtype") === "Content")) {
					$("." + type).each(function() {
						if ($(this).children().find('.configName').val() !== undefined) {
							configJson.name = $(this).children().find('.configName').val();
						}
						if ($(this).children().find('.configDesc').val() !== undefined) {
							configJson.desc = $(this).children().find('.configDesc').val();
						}
						properties.files = $("li span.qq-upload-file").text();
						configJson.properties = properties;
						configJson.type = $(this).attr("name");
					});
				} else {
						if ($("li span.qq-upload-file").text() !== '') {
							properties.files = $("li span.qq-upload-file").text();
							configJson.properties = properties;
						}
					$("." + type).each(function() {
						if ($(this).children().find('.configName').val() !== undefined) {
							configJson.name = $(this).children().find('.configName').val();
						}
						if ($(this).children().find('.configDesc').val() !== undefined) {
							configJson.desc = $(this).children().find('.configDesc').val();
						}
						$(this).find("." + type + "Configuration").each(function() {
							var proValue = $(this).attr("name");
							if(proValue !== undefined) {
								if($(this).val() || $(this).val() === '') {
									properties[proValue] = $(this).val().replace(/%20/g, " ");
								}
							}
						});
						
						var otherKey = $(this).children().find('.otherKey').val();
						var otherKeyValue = $(this).children().find('.otherKeyValue').val();
						if (otherKey !== undefined && otherKeyValue !== undefined) {
							properties[otherKey] = otherKeyValue;
							configJson.properties = properties;
						} else {
							configJson.properties = properties;
						}
						
						var configKey = $(this).children().find('.ConfigKey').val();
						var configKeyValue = $(this).children().find('.ConfigKeyValue').val();
						if (configKey !== undefined && configKey !== "" && configKeyValue !== undefined) {
							properties[configKey] = configKeyValue;
							configJson.properties = properties;
						}
						
						configJson.type = $(this).attr("name");
					});
				}
				self.configList.push(configJson);
			});

			
			var envrName = "";
			if ($('input[name=EnvName]').val() !== undefined) {
				envrName = $('input[name=EnvName]').val();
				self.desc = $('input[name=EnvDesc]').val();
			} else {
				envrName = $(".row_bg").attr('envspecificval');
			}
			
			self.configRequestBody = self.configList;
			if(self.validation()) {
				self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "saveConfig", envrName), function(response) {
					Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
					if(self.configListPage === null) {
						commonVariables.navListener.getMyObj(commonVariables.configuration, function(retVal) {
							self.configListPage = retVal;
							Clazz.navigationController.push(self.configListPage, true);
						});
					} else {
						Clazz.navigationController.push(self.configListPage, true);
					}
				});
			}
		},
		
		restrictSpaceInOthersKey : function () {
			$(".ConfigKey").unbind('input');
			$(".ConfigKey").bind('input propertychange', function(){
				var str = $(this).val();
				str = str.replace(/[^a-zA-Z 0-9\-\_]+/g, '');
				str = str.replace(/\s+/g, '');
				$(this).val(str);
			});
		},

		validation : function() {
			var self = this, bCheck = true, counter_select_server = 0, counter_select_db = 0;
			$.each($(".row_bg"), function(index, value) {
				bCheck = false;
				var type = $(this).attr("type");
				var conftype = $(this).attr("configtype");			
				if (type !== "otherConfig") {
					$("." + type).each(function() {
						$(this).find("." + type + "Configuration").each(function() {
							$(".configName").each(function() {
								var id = $(this).attr("id");
								var val = $("#"+id).val();
								if(val === undefined || val === null || $.trim(val) === ""){
									bCheck = false;
									$("#"+id).attr('placeholder','Enter Configuration Name');
									$("#"+id).addClass("errormessage");
									$("#"+id).focus();
									$("#"+id).val('');
									$("#"+id).bind('keypress', function() {
									$(this).removeClass("errormessage");
									});
									return bCheck;
								} else {
									bCheck = true;
								}
							});
							if(bCheck === false){
								return bCheck;
							}
							var mandatory = $(this).attr("mandatory");
							if (($(this).attr("currentconfig") === "Theme") || ($(this).attr("currentconfig") === "Content")) {
								if ($(this).find('ul.qq-upload-list').html() === "") {
									$(this).append('<div class="fileValidation">Please Upload File</div>');
									setTimeout(function(){
										$(".fileValidation").remove();
									}, 1000);
									bCheck = false;
								} else {
									bCheck = true;
								}
								return bCheck;
							}
							if(conftype === 'Server') {
								if($(this).hasClass('selectpicker')) {
									counter_select_server++;
									if(counter_select_server === 2) {
										if($(this).val() === null)
										commonVariables.api.showError("chooseserver" ,"success", true, false, true);	
									}
								}
							} else if(conftype === 'Database') {
								if($(this).hasClass('selectpicker')) {
									counter_select_db++;
									if(counter_select_db === 1) {
										if($(this).val() === null)
										commonVariables.api.showError("choosedb" ,"success", true, false, true);
									}
								}
							}

							var val = $(this).attr("name");
							if(mandatory === 'true') {
								if($(this).val() !== undefined && $(this).val() !== null && $.trim($(this).val()) !== ""){
									if(val === "emailid") {
										var email = $("input[name=emailid]").val();
										var emailMatcher = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
										if (!emailMatcher.test(email)) {
											$("input[name=emailid]").val("");
											$("input[name=emailid]").attr('placeholder','Please Enter valid email address');
											$("input[name=emailid]").addClass("errormessage");
											$("input[name=emailid]").bind('keypress', function() {
												$(this).removeClass("errormessage");
											});
											bCheck = false;
										} else {
											bCheck = true;
										}
							
									} else {
										bCheck = true;
									}
								} else{
									bCheck = false;
									var temp = $(this).attr("temp");
									$("input[temp='"+temp+"']").attr('placeholder','Enter Value');
									$("input[temp='"+temp+"']").addClass("errormessage");
									$("input[temp='"+temp+"']").focus();
									$("input[temp='"+temp+"']").bind('keypress', function() {
										$(this).removeClass("errormessage");
									});
									
									if ($(this).attr("fileUpload") === "fileupload") {
										bCheck = true;
									}
									return bCheck;
								}
							}
						});
						if(bCheck === false){
							return bCheck;
						}
					});
				} else {
					$(".configName").each(function() {
						var id = $(this).attr("id");
						var val = $("#"+id).val();
						if(val === undefined || val === null || $.trim(val) === ""){
							bCheck = false;
							$("#"+id).attr('placeholder','Enter Configuration Name');
							$("#"+id).addClass("errormessage");
							$("#"+id).focus();
							$("#"+id).val('');
							$("#"+id).bind('keypress', function() {
							$(this).removeClass("errormessage");
							});
							return bCheck;
						} else {
							bCheck = true;
						}
					});
					if(bCheck === false){
						return bCheck;
					}

					$('.otherKey').each(function() {
						if($(this).val() !== undefined && $(this).val() !== null && $.trim($(this).val()) !== "") {
							bCheck = true;
						} else {
							bCheck = false;
							$(this).attr('placeholder','Enter Key');
							$(this).addClass("errormessage");
							$(this).focus();
							$(this).bind('keypress', function() {
								$(this).removeClass("errormessage");
							});
							return bCheck;
						}
					});
					$('.otherKeyValue').each(function() {
						if($(this).val() !== undefined && $(this).val() !== null && $.trim($(this).val()) !== "") {
							bCheck = true;
						} else {
							bCheck = false;
							$(this).attr('placeholder','Enter Value');
							$(this).addClass("errormessage");
							$(this).focus();
							$(this).bind('keypress', function() {
								$(this).removeClass("errormessage");
							});
							return bCheck;
						}
		
					});
				}
				if(bCheck === false) {
					return bCheck;
				}
			});
			return bCheck;
		},

		
		spclCharValidation : function() {
			var self = this;
			$("input[name=port]").bind('input propertychange', function (e) {
				var str = $(this).val();
				str = str.replace(/[^0-9]+/g, '');
				$(this).val(str);
			});
			
			$("input[name=host]").bind('input propertychange', function (e) {
				var str = $(this).val();
				str = str.replace(/[^a-zA-Z 0-9\.\-\_]+/g, '');
				$(this).val(str);
			});
		},
		
		cloneEnv : function(val, envName, callback) {
			var self = this;
			var ename = $(val).closest('tr').prev('tr').find('td:first').find('input').val();
			var count = 0;
			var flag = 1;
			var arr = [];
			var array = [];
			if(ename === "") {	
				$("input[name='envrName']").focus();
				$("input[name='envrName']").attr('placeholder','Enter Environment Name');
				$("input[name='envrName']").addClass("errormessage");
				$("input[name='envrName']").bind('keypress', function() {
					$("input[name='envrName']").removeClass("errormessage");
				});
			} else {
				$('.envlist').each(function() {
					arr[count]=$(this).find('td:first').text();
					array[count] = (arr[count].replace(/\s*\(.*?\)\s*/g, ''));
					count++;
				});
				if(ename !== undefined) {
					for (var i=0; i<array.length; i++) {
						if($.trim(ename.toUpperCase()) === $.trim(array[i].toUpperCase())) {
							flag = 0;
						}		
					};
				}
				if (flag === 0) {
					commonVariables.api.showError("enteruniquename" ,"error", true, false, true);					
					$(val).closest('tr').prev('tr').find('td:first').find('input').focus(); 
				} else {
					var desc = $(".cloneEnvDesc"+envName).val();
					var defaultEnv = $('input[name=cloneName'+envName+']').is(':checked');
					self.constructJson(ename, desc, defaultEnv, function(response){
						callback(response); 
					});
				};
			}
		},
		
		constructJson : function (ename, desc, defaultEnv, callback) {
			var self = this, envJson = {};
			envJson.name = ename;
			envJson.desc = desc;
			envJson.defaultEnv = defaultEnv;
			callback(envJson);
		},
		
		nonEnvConfig : function (configName, envSpecificVal) {
			var self=this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			commonVariables.navListener.getMyObj(commonVariables.editConfiguration, function(retVal) {
				self.nonEnvEditConfigurations = retVal;
				self.nonEnvEditConfigurations.configName = configName;
				self.nonEnvEditConfigurations.configClick = "AddConfiguration";
				self.nonEnvEditConfigurations.envSpecificVal = (envSpecificVal === "false") ? false : true;
				Clazz.navigationController.push(self.nonEnvEditConfigurations, true);
			});
		},
		
		validationConsole : function(clicked) {
			var check = $(clicked).attr('data-flag');
			var self = this;
			if(check === "true") {
				self.openConsole();
				$("section[name=configContent]").hide();
				$(clicked).attr('data-flag','false');
			} else {
				self.closeConsole();
				$("section[name=configContent]").show();
				$(clicked).attr('data-flag','true');
				$(window).resize();
			}
		}
		
	});

	return Clazz.com.components.configuration.js.listener.ConfigurationListener;
});
