define(["configuration/api/configurationAPI"], function() {

	Clazz.createPackage("com.components.configuration.js.listener");

	Clazz.com.components.configuration.js.listener.ConfigurationListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		basePlaceholder :  window.commonVariables.basePlaceholder,
		configurationAPI : null,
		editConfigurations : null,
		configList : [],
		configListPage : null,
		cancelEditConfigurations : null,
		configRequestBody : {},
		envJson : [],
		configTemName : [],
		bcheck : null,
		count : 0,
		serverTypeVersion : null,
		databaseTypeVersion : null,
	
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
						Clazz.navigationController.push(self.editConfigurations, true);
					});
				});
			} else {
				self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "configTypes"), function(response) {
					self.editConfigurations.configType = response.data;
					Clazz.navigationController.push(self.editConfigurations, true);
				});
			}
		},

		scrollbarEnable : function(){
			$(".features_content_main").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced:{ updateOnContentResize: true}
			});

			$(".fix_height").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced:{ updateOnContentResize: true}
			});

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
					Clazz.navigationController.push(self.cancelEditConfigurations, true, true);
				});
			} else {
				Clazz.navigationController.push(self.cancelEditConfigurations, true, true);
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
			customerId = (customerId === "") ? "photon" : customerId;
			appDirName = self.configurationAPI.localVal.getSession("appDirName");
			data = JSON.parse(self.configurationAPI.localVal.getSession('userInfo'));
			if(data !== null) {
				var userId = data.id;
			}
			var techId = commonVariables.techId;
			self.bcheck = false;
			header = {
				contentType: "application/json",
				dataType: "json",
				requestMethod : "GET",
				webserviceurl : ''
			};
			if (action === "list") {
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/allEnvironments?appDirName="+appDirName;
			} else if (action === "edit") {
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"?appDirName="+appDirName+"&envName="+commonVariables.environmentName;
			}else if (action === "configTypes") {
				self.bcheck = true;
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/types?customerId="+customerId+"&userId="+userId+"&techId="+techId;
			} else if (action === "delete") {
				self.bcheck = true;
				header.requestMethod = "DELETE";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/deleteEnv?appDirName="+appDirName+"&envName="+deleteEnv;
			} else if (action === "template") {
					self.bcheck = true;
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?appDirName="+appDirName+"&customerId="+customerId+"&userId="+userId+"&type="+deleteEnv+"&techId="+techId;
			} else if (action === "saveEnv") {
					header.requestMethod = "POST";
					header.requestPostBody = JSON.stringify(configRequestBody);
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"?appDirName="+appDirName;
			} else if (action === "saveConfig") {
					header.requestMethod = "POST";
					header.requestPostBody = JSON.stringify(configRequestBody);
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/updateConfig?appDirName="+appDirName+"&envName="+deleteEnv+"&customerId="+customerId+"&userId="+userId;
			} else if (action === "cloneEnv") {
					header.requestMethod = "POST";
					header.requestPostBody = JSON.stringify(configRequestBody);
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/cloneEnvironment?appDirName="+appDirName+"&envName="+deleteEnv;
			} else if (action === "cronExpression") {
					self.bcheck = true;
					header.requestMethod = "POST";
					header.requestPostBody = JSON.stringify(configRequestBody);
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/cronExpression";
			} else if (action === "isAliveCheck") {
					header.requestMethod = "GET";
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/connectionAliveCheck?url="+configRequestBody.protocol+","+configRequestBody.host+","+configRequestBody.port;
			}
			return header;
		},
		
		constructHtml : function(configTemplates, configuration, currentConfig, WhereToAppend){
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
			var toAppend;
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
									self.successMsgPopUp("Server Already Added");		
									flag = 1;
								}
							}
							
							if(currentConfig === 'Email') {
								if($(this).attr('configType') === currentConfig) {
									bCheck = true;
									setTimeout(function(){
										self.successMsgPopUp("Email Already Added");		
									},2500);
								}
							}
						});
					}
				}
				
				if (self.count === 0) {
					self.count = "";
				}
				
				var headerTr = '<tr type="'+configTemplate.name+self.count+'" class="row_bg" configType="'+configTemplate.name+'"><td colspan="3">' + configTemplate.name + '</td><td colspan="3">'+'<a href="javascript:;" name="removeConfig"><img src="themes/default/images/helios/close_icon.png" border="0" alt="" class="flt_right"/></a></td></tr>';
				content = content.concat(headerTr);
				var defaultTd = '<tr name="'+configTemplate.name+'" class="'+configTemplate.name+self.count+'"><td class="labelTd">Name <sup>*</sup></td><td><input type="text" id="Config'+configTemplate.name+self.count+'" mandatory="true" class="configName" value="'+configName+'" placeholder= "Configuration Name"/></td><td class="labelTd">Description</td><td><input type="text" class="configDesc" value="'+configDesc+'" placeholder= "Configuration Description"/></td>';
				content = content.concat(defaultTd);
				var count = 2;
				var i = 2;
				var configPropertiesType = "";
				if (configuration.properties !== undefined) {
					configProperties = configuration.properties;
					configPropertiesType = configProperties.type;
				}
				$.each(configTemplate.properties, function(index, value) {
					var key = value.key;
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
						inputCtrl = '<select mandatory="'+required+'" class="'+configTemplate.name+self.count+'Configuration" temp="'+configTemplate.name+key+self.count+'" name="' + value.key + '">';
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
						inputCtrl = '<input value="'+ configValue +'" class="'+configTemplate.name+self.count+'Configuration" name="'+key+'" mandatory="'+required+'" type="password" placeholder=""/>';
					} else if (type === "FileType") {
						inputCtrl = '<div id="file-uploader-demo1" class="file-uploader" propTempName="'+key+'"><noscript><p>Please enable JavaScript to use file uploader.</p><!-- or put a simple form for upload here --></noscript>  </div>';
						fCheck = true;
					} else if (key === "scheduler") {
						inputCtrl = '<input value="'+ configValue +'" class="'+configTemplate.name+self.count+'Configuration" name="'+key+'" mandatory="'+required+'" type="text" placeholder=""/><a name="cron_expression"><img src="themes/default/images/helios/settings_icon.png" width="23" height="22" border="0" alt=""></a><div id="cron_expression" class="dyn_popup" style="display:none"><table class="table table-striped table_border table-bordered" cellspacing="0" cellpadding="0" border="0"><thead><tr><th colspan="4">Schedule</th></tr></thead><tbody><tr id="scheduleExpression"><td colspan="4"><label><input type="radio" name="scheduleOption" value="Daily" checked>Daily</label><label><input type="radio" name="scheduleOption" value="Weekly">Weekly</label><label><input type="radio" name="scheduleOption" value="Monthly">Monthly</label></td></tr></tbody></table><table class="table table-striped table_border table-bordered" cellspacing="0" cellpadding="0" border="0"><thead><tr> <th colspan="2">Crone Expression</th></tr></thead><tbody><tr><td><input id="CroneExpressionValue" type="text"><a href="javascript:;" id="cronepassword"> <img src="themes/default/images/helios/question_mark.png"></a></td></tr></tbody></table> <div class="flt_right"><input class="btn btn_style" type="button" name="croneOk" value="Ok"><input class="btn btn_style dyn_popup_close" type="button" value="Close"></div></div><div id="crone_triggered" class="dyn_popup" style="display:none"><span>Your Schedule will be triggered using the following pattern<br>Daily Schedule</span><table class="table table-striped table_border table-bordered" border="0" cellpadding="0" cellspacing="0"><thead><tr><th>Date</th> </tr></thead><tbody name="scheduleDates"></tbody></table><div class="flt_right"><input name="dyn_popupcon_close" class="btn btn_style dyn_popupcon_close" type="button" value="Close"></div></div>';
					} else {
						if (key === 'type') {
							inputCtrl = '<select mandatory="'+required+'" class="'+configTemplate.name+self.count+'Configuration" name="' + value.key + '">';
							
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
									self.successMsgPopUp("Choose the Server Type from App Info");
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
									self.successMsgPopUp("Choose the DB Type from App Info");
								}
							}
							inputCtrl = inputCtrl.concat(options1);
							inputCtrl = inputCtrl.concat("</select></td>");
						} else if (key === 'version') {
							inputCtrl = '<select mandatory="'+required+'" class="'+configTemplate.name+self.count+'Configuration" currentConfig="'+configTemplate.name+self.count+'" name="' + value.key + '">';
							var options1 = "";
							var i=0;
							if(currentConfig === 'Server') {
								if (configPropertiesType !== "") {
									$.each(self.serverTypeVersion, function(key, value){
										if (configPropertiesType === key) {
											for(var k=0; k<value.length; k++) {
												var selectedAttr = "";
												if (value[k] === configValue) {
													selectedAttr = "selected";
												}
												options1 = options1.concat('<option value="' + value[k] + '" '+selectedAttr+'>' + value[k] + '</option>');
											}
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
										if (configPropertiesType === key) {
											for(var k=0; k<value.length; k++) {
												var selectedAttr = "";
												if (value[k] === configValue) {
													selectedAttr = "selected";
												}
												options1 = options1.concat('<option value="' + value[k] + '" '+selectedAttr+'>' + value[k] + '</option>');
											}
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
						} else {
							inputCtrl = '<input mandatory="'+required+'" value="'+ configValue +'" class="'+configTemplate.name+self.count+'Configuration" name="'+key+'" temp="'+configTemplate.name+key+self.count+'" type="text" placeholder=""/>';
						}
					}
					control = control.concat(inputCtrl);
					content = content.concat(control);
					i = count++;
				});
				if (bCheck === false) {
					if (WhereToAppend === "") {
						toAppend = $("tbody[name=ConfigurationLists]");
					} else {
						toAppend = WhereToAppend;
					}
					toAppend.append(content);
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
				if (fCheck === true) {
					self.createUploader();
				}
			}
		},
		
		createUploader : function() {     
			var self = this, appDirName;
			appDirName = self.configurationAPI.localVal.getSession("appDirName");
            var uploader = new qq.FileUploader({
                element: document.getElementById('file-uploader-demo1'),
                action: commonVariables.webserviceurl+commonVariables.configuration+'/upload',
				actionType : "configuration",
				appDirName : appDirName,
				multiple: false,
				debug: true
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
						}
					});
				}
			});
			
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
					if(response === true) {
						$(".row_bg").each(function() {
							var type = $(this).attr("type");
							if (type == typeMatch) {
								$(this).find("td:first-child").after("<td class=active>Active</td>");
							}
						});
					} else {
						$(".row_bg").each(function() {
							var type = $(this).attr("type");
							if (type == typeMatch) {
								$(this).find("td:first-child").after("<td>In Active</td>");
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
			});
			
			$("#cronepassword").click(
				function openccvar() {
				
				$('.content_main').addClass('z_index_ci');
				
				var clicked = $("#cronepassword");
				var target = $("#crone_triggered");
				var twowidth = window.innerWidth/1.5;
			
				if (clicked.offset().left < twowidth) {	
					$(target).toggle();
					var a = target.height()/2;
					var b = clicked.height()/2;
					var t=clicked.offset().top + (b+12) - (a+12) ;
					var l=clicked.offset().left + clicked.width()+ 4;
					$(target).offset({
						top: t,
						left: l
					});
					
					$(target).addClass('speakstyleleft').removeClass('speakstyleright');
				}
				else {
					$(target).toggle();
					var t=clicked.offset().top - target.height()/2;
					var l=clicked.offset().left - (target.width()+ 15);
					$(target).offset({
						top: t,
						left: l
					});
					
					$(target).addClass('speakstyleright').removeClass('speakstyleleft');
			
				}
				self.closeAll(target);
				
			});
			
			$("input[name=dyn_popupcon_close]").click(function() {
				$(this).parent().parent().hide();
			});
			
			self.currentEvent($('input[name=scheduleOption]:checked').val(), '');
			$('input[name=scheduleOption]').bind('click', function() {
				$(this).attr('checked', true);
				self.currentEvent($(this).val(), '');
			});
		},
		  		
		closeAll : function(placeId) {
			
			$(document).keyup(function(e) {
				if(e.which === 27){
					$(placeId).hide();
				}
			});
			
			$('.dyn_popup_close').click( function() {
				$(placeId).hide();
				$("#cron_expression").hide();
			});
				
		},
		currentEvent : function(value, WhereToAppend) {
			var self=this, dailySchedule, weeklySchedule, monthlySchedule, toAppend;
			dailySchedule = '<tr id="schedule_daily" class="schedule_date"><td>Every At<input type="checkbox" name="everyAt"></td><td><select name="hours" class="selectpicker">'+self.hours()+'</select><span>Hrs</span></td> <td><select name="minutes" class="selectpicker">'+self.minutes()+'</select><span>Mins</span></td></tr>';
			weeklySchedule = '<tr id="schedule_weekly" class="schedule_date"><td><select name="weeks" class="selectpicker" multiple data-selected-text-format="count>2">'+self.weeks()+'</select><span>Weeks</span> <span>at</span></td><td><select name="hours" class="selectpicker">'+self.hours()+'</select><span>Hrs</span></td> <td><select name="minutes" class="selectpicker">'+self.minutes()+'</select><span>Mins</span></td></tr>';
			monthlySchedule = '<tr id="schedule_monthly" class="schedule_date"><td><span>Every</span><select name="days" class="selectpicker">'+self.days()+'</select></td><td><span>of</span><select name="months" class="selectpicker" multiple data-selected-text-format="count>2">'+self.months()+'</select><span>Months</span></td><td><span>at</span><select name="hours" class="selectpicker">'+self.hours()+'</select><span>Hrs</span></td> <td><select name="minutes" class="selectpicker">'+self.minutes()+'</select><span>Mins</span></td></tr>';
			$('.schedule_date').remove();
			if (WhereToAppend === "") {
				toAppend = $('tr #scheduleExpression:last');
			} else {
				toAppend = WhereToAppend;
			}
			if (value === 'Daily') {
				$(dailySchedule).insertAfter(toAppend);
			} else if (value === 'Weekly') {
				$(weeklySchedule).insertAfter(toAppend);
			} else {
				$(monthlySchedule).insertAfter(toAppend);
			}
			self.multiselect();
			self.cronExpressionValues(value);
		},
		
		cronExpressionValues : function (value) {
			var self=this, croneJson = {};
			if (value === 'Daily') {
				croneJson.cronBy = value;
				croneJson.every = $('input[name=everyAt]').is(':checked');
				croneJson.hours = $('select[name=hours]').val();
				croneJson.minutes = $('select[name=minutes]').val();
				self.cronExpressionLoad(croneJson);
				$('input[name=everyAt], select[name=hours], select[name=minutes]').bind('change', function(){
					croneJson.every = $('input[name=everyAt]').is(':checked');
					croneJson.hours = $('select[name=hours]').val();
					croneJson.minutes = $('select[name=minutes]').val();
					self.cronExpressionLoad(croneJson);
				});

			} else if (value === 'Weekly') {
				var weeks = [], val;
				croneJson.cronBy = value;
				if ($('select[name=weeks]').val() === null) {
					val = '*';
				}
				weeks.push(val);
				croneJson.week = weeks;
				croneJson.hours = $('select[name=hours]').val();
				croneJson.minutes = $('select[name=minutes]').val();
				self.cronExpressionLoad(croneJson);
				$('select[name=weeks], select[name=hours], select[name=minutes]').bind('change', function(){
					croneJson.week = $('select[name=weeks]').val();
					croneJson.hours = $('select[name=hours]').val();
					croneJson.minutes = $('select[name=minutes]').val();
					self.cronExpressionLoad(croneJson);
				});
			} else {
				var month = [], val;
				croneJson.cronBy = value;
				if ($('select[name=months]').val() === null) {
					val = '*';
				}
				croneJson.day = $('select[name=days]').val();
				month.push(val);
				croneJson.month = month;
				croneJson.hours = $('select[name=hours]').val();
				croneJson.minutes = $('select[name=minutes]').val();
				self.cronExpressionLoad(croneJson);
				$('select[name=days], select[name=months], select[name=hours], select[name=minutes]').bind('change', function(){
					var months = [];
					if ($('select[name=months]').val() === null) {
						val = '*';
						months.push(val);
						croneJson.month = months;
					} else {
						croneJson.month = $('select[name=months]').val();
					}
					croneJson.day = $('select[name=days]').val();
					croneJson.hours = $('select[name=hours]').val();
					croneJson.minutes = $('select[name=minutes]').val();
					self.cronExpressionLoad(croneJson);
				});
			}
		},
		
		cronExpressionLoad : function (croneJson) {
			var self=this, i, tr;
			self.configRequestBody = croneJson;
			self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "cronExpression", ''), function(response) {
				$("#CroneExpressionValue").val(response.data.cronExpression);
				$('tbody[name=scheduleDates]').html('');
				if (response.data.dates !== null) {
					for (i=0; i<response.data.dates.length; i++) {
						tr += '<tr><td>'+response.data.dates[i]+'</td></tr>';
					}
				}
				$('tbody[name=scheduleDates]').append(tr);
			});
			
			$("input[name=croneOk]").click(function(){
				$("#cron_expression").hide();
				$("input[name=scheduler]").val($("#CroneExpressionValue").val());
			});
		},
		
		hours : function() {
			var self=this, option='', i;
				option = '<option>*</option>';
			for(i=0; i<24; i++) {
				option += '<option value='+i+'>'+i+'</option>';
			}
			return option;
		},
		
		minutes : function() {
			var self=this, option='', i;
			option = '<option>*</option>';
			for(i=0; i<60; i++) {
				option += '<option value='+i+'>'+i+'</option>';
			}
			return option;
		},
		
		days : function() {
			var self=this, option='', i;
			option = '<option>*</option>';
			for(i=1; i<32; i++) {
				option += '<option value='+i+'>'+i+'</option>';
			}
			return option;
		},
		
		weeks : function () {
			var self=this, option='', i, weeks = ['*', 'Sunday', 'Monday', 'Tuesday', 'Wendesday', 'Thursday', 'Friday', 'Saturday'];
			
			for(i=0; i<weeks.length; i++) {
				var val = (i === 0) ? "*" : i;
				option += '<option value='+val+'>'+weeks[i]+'</option>';
			}
			return option;
		},
		
		months : function () {
			var self=this, option='', i, months = ["*","January","February","March","April","May","June","July","August","September","October","November","December"];
			
			for(i=0; i<months.length; i++) {
				var val = (i === 0) ? "*" : i;
				option += '<option value='+val+'>'+months[i]+'</option>';
			}
			return option;
		},
		
		htmlForOther : function(value, WhereToAppend) {
			var self = this, headerTr, content = '', textBox, apiKey = "", keyValue = "", type="Other", addIcon = '<img src="themes/default/images/helios/plus_icon.png" border="0" alt="">', toAppend;
				if (value !== null && value !== '') {
					type = value.type;
				}
				
				headerTr = '<tr class="row_bg" type="otherConfig"><div class="row"><td colspan="3">' + type + '</td><td colspan="3">'+
				'<a href="javascript:;" name="removeConfig"><img src="themes/default/images/helios/close_icon.png" border="0" alt="" class="flt_right"/></a></td></div></tr>';
				content = content.concat(headerTr);
				
				var defaultTd = '<tr name="configName" class="otherConfig" name="'+type+'"><td class="labelTd">Name <sup>*</sup></td><td><input type="text" id="ConfigOther" mandatory="true" class="configName" value="" placeholder= "Configuration Name"/></td><td class="labelTd">Description</td><td><input type="text" class="configDesc" value="" placeholder= "Configuration Description"/></td>';
				content = content.concat(defaultTd);
				
				if (value !== null && value !== '') {
					$.each(value.properties, function(key, value){
						textBox = '<tr class="otherConfig" name="'+type+'"><td></td><td><input type="text" placeholder= "key" class="otherKey" value="'+key+'"/><td><input type="text" placeholder= "value" class="otherKeyValue" value="'+value+'"/></td><td><div class="flt_right icon_center"><a href="javascript:;" name="addOther"></a> <a href="javascript:;" name="removeOther"><img src="themes/default/images/helios/minus_icon.png" border="0" alt=""></a></div></td></tr>';
						content = content.concat(textBox);
					});
				} else {
					textBox = '<tr class="otherConfig" name="'+type+'"><td></td><td><input type="text" placeholder= "key" class="otherKey"/><td><input type="text" placeholder= "value" class="otherKeyValue" /></td><td><div class="flt_right icon_center"><a href="javascript:;" name="addOther"><img src="themes/default/images/helios/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeOther"></a></div></td></tr>';
					content = content.concat(textBox);
				}
				if (WhereToAppend === "") {
						toAppend = $("tbody[name=ConfigurationLists]");
					} else {
						toAppend = WhereToAppend;
					}
				toAppend.append(content);
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
		
		addOtherConfig : function(toAppend) {
			var self = this, dynamicValue, textBox = '<tr class="otherConfig" name="Other"><td></td><td><input type="text" class="otherKey" placeholder= "key"/><td><input type="text" placeholder= "value" class="otherKeyValue"/></td><td><div class="flt_right icon_center"><a href="javascript:;" name="addOther"><img src="themes/default/images/helios/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeOther"><img src="themes/default/images/helios/minus_icon.png" border="0" alt=""></a></div></td></tr>', minusIcon = '<img src="themes/default/images/helios/minus_icon.png" border="0" alt="">';
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
			var self=this, addIcon = '<img src="themes/default/images/helios/plus_icon.png" border="0" alt="">';
				$("a[name=addOther]").html('');
				removeValue.parent().parent().parent().remove();
				$("a[name=removeOther]").parents('tr:last').find('a[name="addOther"]').html(addIcon);
				if (($("a[name=removeOther]").parents('tr.otherConfig').length) === 1) {
					$('tr.otherConfig').find('a[name="addOther"]').html(addIcon);
					$("a[name=removeOther]").html('');
				}
		},
		
		addEnvEvent : function(envName, envDesc, WhereToAppend) {
			var self = this, toAppend;
			if (WhereToAppend === "") {
				toAppend = $("ul[name=envList]");
			} else {
				toAppend = WhereToAppend;
			}
			toAppend.append('<li draggable="true" name="'+envName+'"><div><input type="radio" name="optionsRadiosfd"></div><div  class="envlistname" name="envListName">'+envName+'</div><input type="hidden" name="envListDesc" value="'+envDesc+'"></li>');
			$('.connected').sortable({
				connectWith: '.connected'
			});
			$("input[name=envName]").val('');
			$("input[name=envDesc]").val('');
		},
		
		saveEnvEvent : function(envWithConfig, WhereToAppend, callback) {
			var self = this, toAppend;
			self.envJson = [];
			if (WhereToAppend === "") {
				toAppend = $("ul[name=envList]");
			} else {
				toAppend = WhereToAppend;
			}
			$.each(toAppend.children(), function(index, value) {
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
			if (config !== "Other") {
				self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "template", config), function(response) {
					self.constructHtml(response, '', config, '');
				});
			} else {
				self.htmlForOther('', '');
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
			if(self.validation()) {
				self.getConfigurationList(self.getRequestHeader(self.configRequestBody, "saveConfig", envrName), function(response) {
					Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
					setTimeout(function(){
						self.successMsgPopUp(response.message);			
					},2500);
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
		
			validation : function() {
				var self = this, bCheck = false;
				$.each($(".row_bg"), function(index, value) {
					var type = $(this).attr("type");
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
								var val = $(this).attr("name");
								if(mandatory === 'true') {
									if($(this).val() !== undefined && $(this).val() !== null && $.trim($(this).val()) !== ""){
										if(val == "emailid") {
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
							if($(this).val() != undefined && $(this).val() != null && $.trim($(this).val()) != "") {
								bCheck = true;
							} else {
								bCheck = false;
								$(this).attr('placeholder','Enter Key');
								$(this).addClass("errormessage");
								$(this).focus();
								return bCheck;
								$(this).bind('keypress', function() {
									$(this).removeClass("errormessage");
								});
							}
						});
			
						$('.otherKeyValue').each(function() {
							if($(this).val() != undefined && $(this).val() != null && $.trim($(this).val()) != "") {
								bCheck = true;
							} else {
								bCheck = false;
								$(this).attr('placeholder','Enter Value');
								$(this).addClass("errormessage");
								$(this).focus();
								return bCheck;
								$(this).bind('keypress', function() {
									$(this).removeClass("errormessage");
								});
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
			} else {
				$('.envlist').each(function() {
					arr[count]=$(this).find('td:first').text();
					array[count] = (arr[count].replace(/\s*\(.*?\)\s*/g, ''));
					count++;
				});
				for (var i=0; i<array.length; i++) {
					if($.trim(ename) === $.trim(array[i])) {
						flag = 0;
					}		
				};
				if (flag === 0) {
					$(".display").show();
					setTimeout(function() {
						$(".display").hide();
					}, 1500);
					$(val).closest('tr').prev('tr').find('td:first').find('input').focus(); 
				} else {
					var desc = $(".cloneEnvDesc"+envName).val();
					var defaultEnv = $(".default").attr('name');
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
		}
		
	});

	return Clazz.com.components.configuration.js.listener.ConfigurationListener;
});