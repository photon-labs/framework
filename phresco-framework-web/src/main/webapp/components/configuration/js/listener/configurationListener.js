define(["configuration/api/configurationAPI"], function() {

	Clazz.createPackage("com.components.configuration.js.listener");

	Clazz.com.components.configuration.js.listener.ConfigurationListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		basePlaceholder :  window.commonVariables.basePlaceholder,
		configurationAPI : null,
		editConfigurations : null,
		configList : [],
		configListPage : null,
		cancelEditConfiguration : null,
		configRequestBody : {},
		envJson : [],
		configTemName : [],
		bcheck : null,
		count : 0,
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			self.configurationAPI = new Clazz.com.components.configuration.js.api.ConfigurationAPI();
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
		},
		
		editConfiguration : function(envName) {
			var self=this;
			commonVariables.environmentName = envName;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			if(self.editConfigurations  === null) {
				commonVariables.navListener.getMyObj(commonVariables.editConfiguration, function(retVal) {
					self.editConfigurations = retVal;
					self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "configTypes"), function(response) {
						self.editConfigurations.configType = response.data;
					});
					Clazz.navigationController.push(self.editConfigurations, true);
				});
			} else {
				self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "configTypes"), function(response) {
					self.editConfigurations.configType = response.data;
				});
				Clazz.navigationController.push(self.editConfigurations, true);
			}
		},
		
		cancelEditConfiguation : function() {
			var self=this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			if(self.cancelEditConfiguration  === null) {
				commonVariables.navListener.getMyObj(commonVariables.configuration, function(retVal) {
					self.cancelEditConfiguration = retVal;
					Clazz.navigationController.push(self.cancelEditConfiguration, true, true);
				});
			} else {
				Clazz.navigationController.push(self.cancelEditConfiguration, true, true);
			}
		},
		
		getConfigurationList : function(header, callback) {
			var self = this;
			try {
				if (self.bcheck === false) {
					this.loadingScreen.showLoading();
				}
				self.configurationAPI.configuration(header,
					function(response) {
						if (response !== null) {
							callback(response);
							self.loadingScreen.removeLoading();
						} else {
							self.loadingScreen.removeLoading();
							callback({ "status" : "service failure"});
						}

					},

					function(textStatus) {
						if (self.bcheck === false) {
							self.loadingScreen.removeLoading();
						}
					}
				);
			} catch(exception) {
				if (self.bcheck === false) {
					self.loadingScreen.removeLoading();
				}
			}

		},
		
		getRequestHeader : function(configRequestBody, action, deleteEnv) {
			var self = this, header, appDirName;
			var customerId = self.getCustomer();
			customerId = (customerId == "") ? "photon" : customerId;
			appDirName = self.configurationAPI.localVal.getSession("appDirName");
			data = JSON.parse(self.configurationAPI.localVal.getSession('userInfo'));
			var userId = data.id;
			var techId = commonVariables.techId;
			self.bcheck = false;
			header = {
				contentType: "application/json",
				dataType: "json",
				requestMethod : "GET",
				webserviceurl : ''
			};
			if (action === "list") {
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"?appDirName="+appDirName;
			} else if (action === "edit") {
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"?appDirName="+appDirName+"&envName="+commonVariables.environmentName;
			}else if (action === "configTypes") {
				self.bcheck = true;
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/types?customerId="+customerId+"&userId="+userId+"&techId="+techId;
			} else if (action === "delete") {
				header.requestMethod = "DELETE";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/deleteEnv?appDirName="+appDirName+"&envName="+deleteEnv;
			} else if (action === "template") {
					self.bcheck = true;
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?customerId="+customerId+"&userId="+userId+"&type="+deleteEnv;
			} else if (action === "saveEnv") {
					header.requestMethod = "POST";
					header.requestPostBody = JSON.stringify(configRequestBody);
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"?appDirName="+appDirName;
			} else if (action === "saveConfig") {
					header.requestMethod = "POST";
					header.requestPostBody = JSON.stringify(configRequestBody);
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/updateConfig?appDirName="+appDirName+"&envName="+deleteEnv;
			} else if (action === "cloneEnv") {
					header.requestMethod = "POST";
					header.requestPostBody = JSON.stringify(configRequestBody);
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/cloneEnvironment?appDirName="+appDirName+"&envName="+deleteEnv;
			}
			return header;
		},
		
		constructHtml : function(configTemplate, configuration){
			
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
			
			if (configTemplate.data.length !== 0) {
				var content = "";
				var configName = "";
				var configDesc = "";
				if (configuration !== null && configuration !== undefined && configuration !== "") {
					configName = configuration.name;
					configDesc = configuration.desc;
				}
				if ($.isEmptyObject(self.configTemName)) {
					self.configTemName.push(configTemplate.data.name);
				} else {
					var found = $.inArray(configTemplate.data.name, self.configTemName) > -1;
					if (found === false) {
						self.configTemName.push(configTemplate.data.name);
					} else {
						self.count++;
					}
				}
				
				if (self.count == 0) {
					self.count = "";
				}
				var headerTr = '<tr type="'+configTemplate.data.name+self.count+'" class="row_bg"><td colspan="3">' + configTemplate.data.name + '</td><td colspan="3">'+'<a href="javascript:;" name="removeConfig"><img src="themes/default/images/helios/close_icon.png" border="0" alt="" class="flt_right"/></a></td></tr>';
				content = content.concat(headerTr);
				var defaultTd = '<tr name="'+configTemplate.data.name+'" class="'+configTemplate.data.name+self.count+'"><td class="labelTd">Name <sup>*</sup></td><td><input type="text" class="configName" value="'+configName+'" placeholder= "Configuration Name"/></td><td class="labelTd">Description</td><td><input type="text" class="configDesc" value="'+configDesc+'" placeholder= "Configuration Description"/></td>';
				content = content.concat(defaultTd);
				var count = 2;
				var i = 2;
				if (configuration.properties !== undefined) {
					configProperties = configuration.properties;
				}
				$.each(configTemplate.data.properties, function(index, value) {
					var key = value.key;
					var label = value.name;
					var type = value.type;
					
					var configValue = "";
					
					if (configProperties[key] !== undefined) {
						configValue = configProperties[key];
					}
					
					var mandatoryCtrl = "";
					if (value.required) {
						mandatoryCtrl = ' <sup>*</sup>';
					}
					var control = "";
					if  (count % 3 == 0) {
						control = '<tr name="'+configTemplate.data.name+'" class="'+configTemplate.data.name+self.count+'"><td class="labelTd">' + label + mandatoryCtrl + '</td><td>';
					} else {
						control = '<td class="labelTd">' + label + mandatoryCtrl + '</td><td>';
					}
					var inputCtrl = "";
					if (value.possibleValues !== null &&  value.possibleValues.length !== 0) {
						inputCtrl = '<select class="'+configTemplate.data.name+self.count+'Configuration" name="' + value.key + '">';
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
						if  (count % 3 == 0) {
							//inputCtrl = inputCtrl.concat("</tr>");
						}
						//inputCtrl = inputCtrl.concat("</tr>");
					} else if (type == "Password") {
						inputCtrl = '<input value="'+ configValue +'" class="'+configTemplate.data.name+self.count+'Configuration" name="'+key+'" type="password" placeholder=""/>';
					} else if (type == "FileType") {
						inputCtrl = '<div id="'+key+'file-uploader" class="file-uploader" propTempName="'+key+'"></div>';
					} else {
						inputCtrl = '<input value="'+ configValue +'" class="'+configTemplate.data.name+self.count+'Configuration" name="'+key+'" type="text" placeholder=""/>';
					}
					control = control.concat(inputCtrl);
					content = content.concat(control);
					i = count++;
				});
				$("tbody[name=ConfigurationLists]").append(content);
				$("a[name=removeConfig]").unbind("click");
				self.removeConfiguation();
			}
		},
			
		htmlForOhter : function(value) {
			var self = this, headerTr, content = '', textBox, apiKey = "", keyValue = "", type="Other", addIcon = '<img src="themes/default/images/helios/plus_icon.png" border="0" alt="">';
				if (value !== null && value !== '') {
					type = value.type;
				}
				
				headerTr = '<tr class="row_bg" type="otherConfig"><div class="row"><td colspan="3">' + type + '</td><td colspan="3">'+
				'<a href="javascript:;" name="removeConfig"><img src="themes/default/images/helios/close_icon.png" border="0" alt="" class="flt_right"/></a></td></div></tr>';
				content = content.concat(headerTr);
				if (value !== null && value !== '') {
					$.each(value.properties, function(key, value){
						textBox = '<tr class="otherConfig" name="'+type+'"><td></td><td><input type="text" placeholder= "key" class="otherKey" value="'+key+'"/><td><input type="text" placeholder= "value" class="otherKeyValue" value="'+value+'"/></td><td><div class="flt_right icon_center"><a href="javascript:;" name="addOther"></a> <a href="javascript:;" name="removeOther"><img src="themes/default/images/helios/minus_icon.png" border="0" alt=""></a></div></td></tr>';
						content = content.concat(textBox);
					});
				} else {
					textBox = '<tr class="otherConfig" name="'+type+'"><td></td><td><input type="text" placeholder= "key" class="otherKey"/><td><input type="text" placeholder= "value" class="otherKeyValue" /></td><td><div class="flt_right icon_center"><a href="javascript:;" name="addOther"><img src="themes/default/images/helios/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeOther"></a></div></td></tr>';
					content = content.concat(textBox);
				}
				$("tbody[name=ConfigurationLists]").append(content);
				if (value !== null && value !== '') {
					$("tr.otherConfig:last").find("a[name=addOther]").html(addIcon);
				}
				$("a[name=removeConfig]").unbind("click");
				self.removeConfiguation();
				self.addClick();
				self.removeClick();
		},
		
		addOtherConfig : function(toAppend){
			var self = this, dynamicValue, textBox = '<tr class="otherConfig" name="Other"><td></td><td><input type="text" class="otherKey" placeholder= "key"/><td><input type="text" placeholder= "value" class="otherKeyValue"/></td><td><div class="flt_right icon_center"><a href="javascript:;" name="addOther"><img src="themes/default/images/helios/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeOther"><img src="themes/default/images/helios/minus_icon.png" border="0" alt=""></a></div></td></tr>', minusIcon = '<img src="themes/default/images/helios/minus_icon.png" border="0" alt="">';
			dynamicValue = $(textBox).insertAfter(toAppend);
			dynamicValue.prev('tr').find('a[name="addOther"]').html('');
			dynamicValue.prev('tr').find('a[name="removeOther"]').html(minusIcon);
			$("a[name=addOther]").unbind("click");
			$("a[name=removeOther]").unbind("click");
			self.addClick();
			self.removeClick();
		},
		
		addClick : function() {
			var self = this;
			$("a[name=addOther]").click(function() {
				var toAppend = $(this).parents(".otherConfig:last");
				self.addOtherConfig(toAppend);
			});
		},
		
		removeClick : function() {
			var self=this, addIcon = '<img src="themes/default/images/helios/plus_icon.png" border="0" alt="">';
			$("a[name=removeOther]").click(function(){
				$("a[name=addOther]").html('');
				$(this).parent().parent().parent().remove();
				$("a[name=removeOther]").parents('tr:last').find('a[name="addOther"]').html(addIcon);
				if (($("a[name=removeOther]").parents('tr.otherConfig').length) === 1) {
					$('tr.otherConfig').find('a[name="addOther"]').html(addIcon);
					$("a[name=removeOther]").html('');
				}
			});
		},
		
		addEnvrEvent : function() {
			var self = this;
			var envName = $("input[name=envName]").val();
			var envDesc = $("input[name=envDesc]").val();
			$("ul[name=envList]").append('<li draggable="true"><div><input type="radio" name="optionsRadiosfd"></div><div  class="envlistname" name="envListName">'+envName+'</div><input type="hidden" name="envListDesc" value="'+envDesc+'"></li>');
			$('.connected').sortable({
				connectWith: '.connected'
			});
			$("input[name=envName]").val('')
			$("input[name=envDesc]").val('')
		},
		
		saveEnvEvent : function(callback) {
			var self = this;
			self.envJson = [];
			$.each($("ul[name=envList]").children(), function(index, value) {
				var envNameDesc = {};
				envNameDesc.name = $(value).find("div[name=envListName]").html();
				envNameDesc.desc = $(value).find("input[name=envListDesc]").val();
				envNameDesc.configurations = [];
				envNameDesc.appliesTo = [""];
				envNameDesc.defaultEnv = $(value).find("input[name=optionsRadiosfd]").is(':checked');
				envNameDesc.delete = false;
				if (envNameDesc.name !== undefined && envNameDesc.name !== null) {
					self.envJson.push(envNameDesc);
				}
			});
			callback(self.envJson);
		},
		
		addConfiguation : function(config) {
			var self=this;
			if (config !== "Other") {
				self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "template", config), function(response) {
					self.constructHtml(response, '');
				});
			} else {
				self.htmlForOhter('');
			}
		},
		
		removeConfiguation : function() {
			var self = this;
			$("a[name=removeConfig]").click(function() {
				var currentRow = $(this).parent().parent().next();
				$(this).parent().parent().addClass('remove');				
				while(currentRow != null && currentRow.length > 0) {
				   if (currentRow.attr('class') !== "row_bg") {
					   currentRow.addClass('remove');
					   currentRow = currentRow.next('tr');
				   } else {
					currentRow = null;
				   }
				}
				$('.remove').remove();
			});
		},
		
		UpdateConfig : function() {
			var self=this, configJson = {}, properties = {};
			self.configList = [];
			$.each($(".row_bg"), function(index, value) {
				properties = {};
				configJson = {};
				var type = $(this).attr("type");
				$("." + type).each(function() {
					if ($(this).children().find('.configName').val() !== undefined) {
						configJson.name = $(this).children().find('.configName').val();
					}
					if ($(this).children().find('.configDesc').val() !== undefined) {
						configJson.desc = $(this).children().find('.configDesc').val();
					}
					$(this).find("." + type + "Configuration").each(function() {
						var proValue = $(this).attr("name");
						properties[proValue] = $(this).val();
					});
					
					var otherKey = $(this).children().find('.otherKey').val();
					var otherKeyValue = $(this).children().find('.otherKeyValue').val();
					if (otherKey !== undefined && otherKeyValue !== undefined) {
						properties[otherKey] = otherKeyValue;
						configJson.properties = properties;
					} else {
						configJson.properties = properties;
					}
					configJson.type = $(this).attr("name");
				});
				self.configList.push(configJson);
			});
			var envrName = $('input[name=EnvName]').val();
			self.configRequestBody = self.configList;
			self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "saveConfig", envrName), function(response) {
				Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
				if(self.configuration  === null) {
					commonVariables.navListener.getMyObj(commonVariables.configuration, function(retVal) {
						self.configListPage = retVal;
						Clazz.navigationController.push(self.configList, true);
					});
				}
			});
		},
		
		cloneEnv : function(envName, callback) {
			var self = this, envJson = {};
			envJson.name = $(".cloneEnvName"+envName).val();
			envJson.desc = $(".cloneEnvDesc"+envName).val();
			envJson.defaultEnv = $('input[name=DefaultValue'+envName+']').is(':checked');
			callback(envJson);
		},
		
	});

	return Clazz.com.components.configuration.js.listener.ConfigurationListener;
});