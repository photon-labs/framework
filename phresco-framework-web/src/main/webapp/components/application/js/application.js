define(["application/listener/applicationListener"], function() {
	Clazz.createPackage("com.components.application.js");

	Clazz.com.components.application.js.Application = Clazz.extend(Clazz.WidgetWithTemplate, {
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/application/template/application.tmp",
		configUrl: "components/projects/config/config.json",
		editApplicationListener: null,
		name : commonVariables.editApplication,
		addServerEvent : null,
		onRemoveLayerEvent : null,
		onAddLayerEvent : null,
		renderData : {},
		positionCounter : 0,
		header: {
			contentType: null,
			requestMethod: null,
			dataType: null,
			requestPostBody: null,
			webserviceurl: null
		},
		layerDisplay : null,
		selectedFunctionalFramework : null,
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			if(self.editApplicationListener === null){
				self.editApplicationListener = new Clazz.com.components.application.js.listener.ApplicationListener(globalConfig);
			}
				self.registerEvents(self.editApplicationListener);
		},
		
		registerEvents : function(editApplicationListener) {
			var self = this;
			self.onRemoveLayerEvent = new signals.Signal();
			self.onAddLayerEvent = new signals.Signal();
			self.onRemoveLayerEvent.add(editApplicationListener.removelayer, editApplicationListener);
			self.onAddLayerEvent.add(editApplicationListener.addlayer, editApplicationListener);
			self.onCancelEvent = new signals.Signal();
			self.appConfig = new signals.Signal();
			self.updateApp = new signals.Signal();
			self.onCancelEvent.add(editApplicationListener.onCancelUpdate, editApplicationListener); 
			self.appConfig.add(editApplicationListener.getAppConfig, editApplicationListener); 
			self.updateApp.add(editApplicationListener.updateApplication, editApplicationListener); 
			
			Handlebars.registerHelper('compare', function(val1, val2, val3) {
				if(val1 === val2){
					return '<option value="'+ val1 +'" selected="selected">'+ val3 +'</option>';
				}else{
					return '<option value="'+ val1 +'">'+ val3 +'</option>';
				}
			});
			
			Handlebars.registerHelper('compareversion', function(val1, val2, artfgroup, selectedVersion) {
				if(val1 === val2){
					var option = '';
					$.each(artfgroup.versions, function(index, value){
						var inArray = $.inArray(value.id, selectedVersion);
						if (inArray > -1) {
							option +='<option selected="selected" value='+value.id+'>'+ value.version +'</option>';
						} else {
							option +='<option value='+value.id+'>'+ value.version +'</option>';
						}
					});
					return option;
				}
			});
			
			
			Handlebars.registerHelper('position', function(indexVal) {
				indexVal = indexVal+1;
				return indexVal++;
			});
								
			Handlebars.registerHelper('positionouter', function(selectedServers) {
				if(selectedServers[0].selectedServers != undefined){
					return selectedServers[0].selectedServers.length+1;
				} else {
					return 1;
				}	
			});		
			
			Handlebars.registerHelper('isLastRow', function(array, indexVal, options) {
				if(array !== undefined && array.length === indexVal + 1){
					return options.fn(this);
				} else {
					return options.inverse(this);
				}	
			});	
		
			Handlebars.registerHelper('isNotSingleRow', function(array, options) {
				if(array !== undefined && array.length === 1){
					return options.inverse(this);
				} else {
					return options.fn(this);
				}	
			});	
			
			Handlebars.registerHelper('getSuccessMsg', function(appDirName) {
				var succesMsg = localStorage.getItem(appDirName + '_AppUpdateMsg');
				localStorage.removeItem(appDirName + '_AppUpdateMsg');
				setTimeout(function () {
					$('.appSuccessMsg').fadeOut(2000);
				}, 2200);
				return succesMsg;
			});
			
			Handlebars.registerHelper('dbouter', function(selectedDatabases) {
				if(selectedDatabases[0].selectedDatabases != undefined ){
					return selectedDatabases[0].selectedDatabases.length+1;
				} else {
					return 1;
				}	
			});
			
			Handlebars.registerHelper('isSelectedWebService', function(appInfos, webServiceId, options) {
				if(appInfos[0].selectedWebservices !== undefined && $.inArray(webServiceId, appInfos[0].selectedWebservices) > -1){
					return options.fn(this);
				} else {
					return options.inverse(this);
				}
			});
			
			Handlebars.registerHelper('comparetools', function(frameworkGroupId, frameworkIds, functionalFrameworkData) {
				var option = '';
				$.each(functionalFrameworkData, function(index, value){
					if(frameworkGroupId === value.id){
						$.each(value.functionalFrameworks, function(index, value){
							if (value.id === frameworkIds) {
								self.selectedFunctionalFramework = value.displayName;
								option +='<option selected="selected" value='+value.id+'>'+ value.displayName +'</option>';
							} else {
								option +='<option value='+value.id+'>'+ value.displayName +'</option>';
							}
						});
					}
				});
				return option;
			});
						
			Handlebars.registerHelper('comparetoolsversion', function(functionalFrameworkInfo, functionalFrameworkData) {
				var option = '';
				$.each(functionalFrameworkData, function(index, value){
					if(functionalFrameworkInfo.frameworkGroupId === value.id){
						$.each(value.functionalFrameworks, function(index, value){
							if (value.id === functionalFrameworkInfo.frameworkIds) {
								$.each(value.versions, function(index, value){
									if (value === functionalFrameworkInfo.version) {
										option +='<option selected="selected" value='+value+'>'+ value +'</option>';
									} else {
										option +='<option value='+value+'>'+ value +'</option>';
									}
								});		
							}
						});
					}
				});
				return option;
			});
			
			
			Handlebars.registerHelper('embedselect', function(embedList, selectedEmbed) {
				var option = '';
				$.each(embedList, function(index, value){
					if(selectedEmbed === value) {
						option +='<option selected="selected" value='+value+'>'+ index +'</option>';
					} else {
						option +='<option value='+value+'>'+ index +'</option>';
					}
				});				
				return option;
			});
			
			Handlebars.registerHelper('showFunctionalIframeUrl', function() {
				var display = ("TAW" === self.selectedFunctionalFramework) ? 'display:table-row;' : 'display:none;';
				self.selectedFunctionalFramework = null;
				return display;
			});
			
			Handlebars.registerHelper('functionalIframeUrl', function(functionalFrameworkInfo) {
				var iframeUrl = (functionalFrameworkInfo !== null && functionalFrameworkInfo !== undefined && !self.isBlank(functionalFrameworkInfo.iframeUrl)) ?
					functionalFrameworkInfo.iframeUrl : "";
				return iframeUrl;
			});
			
			Handlebars.registerHelper('appinfopermission', function(userPermissions) {
				var importapp;
				if(userPermissions.manageApplication || userPermissions.importApplication){
					importapp = '<input type="submit" data-i18n="[value]application.btn.update;" id="updatebutton" class="btn btn_style update_btn">';  
					}else{		
					importapp ='<input type="submit" data-i18n="[value]application.btn.update;" id="updatebutton" class="btn btn_style update_btn" disabled>';
				}return importapp;
			});	
		},
		 
		
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage :function(){
			Clazz.navigationController.push(this);
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self = this;
			self.multiselect();
			commonVariables.navListener.showHideControls(commonVariables.editApplication);
			commonVariables.navListener.showHideTechOptions();
			$(".scrollContent").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced:{ updateOnContentResize: true}
			});
			var appdetails = commonVariables.api.localVal.getProjectInfo();
			if (appdetails !== null) {
				var appId = appdetails.data.projectInfo.appInfos[0].id;
				$('.headerAppId').val(appId);
			}
			/* if (commonVariables.api.localVal.getJson('functionalFrameworkInfo') !== null) {
				$('select[name=func_framework]').attr("disabled", true);
				$('select[name=func_framework_tools]').attr("disabled", true);
				$('select[name=tools_version]').attr("disabled", true);
			} */
			
			if (self.layerDisplay.showServer === false) {
				self.onRemoveLayerEvent.dispatch($("#servers"));
				self.addLayers();
			}
			
			if (self.layerDisplay.showDatabase === false) {
				self.onRemoveLayerEvent.dispatch($("#database"));
				self.addLayers();
			}
			
			if (self.layerDisplay.showWebservice === false) {
				self.onRemoveLayerEvent.dispatch($("#webservice"));
				self.addLayers();
			}
			
			if (self.layerDisplay.showTestingFramework === false) {
				self.onRemoveLayerEvent.dispatch($("#testing"));
				self.addLayers();
			}
			
			$('#cancelbutton').unbind('click');
			$('#cancelbutton').bind('click', function(){
				self.onCancelEvent.dispatch();
			});
			
			$("#updatebutton").unbind('click');
			$("#updatebutton").bind('click', function(){
				self.checkForLock("applnUpdate", '', '', function(response){
					if (response.status === "success" && response.responseCode === "PHR10C00002") {
						self.updateApp.dispatch(self.renderData);
					} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
						commonVariables.api.showError(self.getLockErrorMsg(response), 'error', true, true, true);
					}	
				});	
			});
						
		},
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
			setTimeout(function() {
				var appDirName = self.isBlank($('.rootModule').val()) ? self.appDirName : $('.rootModule').val();
				self.editApplicationListener.getAppInfo(self.editApplicationListener.getRequestHeader(appDirName , "getappinfo"), function(response) {
					var projectlist = {};
					projectlist.projectlist = response;
					var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
					projectlist.userPermissions = userPermissions;
					self.renderData = response;
					var techId = response.appdetails.data.projectInfo.appInfos[0].techInfo.id;
					self.editApplicationListener.getApplicableOptions(self.editApplicationListener.getRequestHeader(self.appDirName, "getApplicableOptions", techId), function(response) {
						commonVariables.api.localVal.setSession("applicableOptions", JSON.stringify(response.data));
						renderFunction(projectlist, whereToRender);
						setTimeout(function() {
							if(response.data.indexOf('Embed_Application') !== -1) {
								$('.embed').show();
							} else {
								$('.embed').hide();
							}
						}, 500);
						
					});
					self.layerDisplay = response.appdetails.data.projectInfo.appInfos[0];
				});
			}, 200);	
		},
				
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			$("img[name='close']").unbind('click');
			$("img[name='close']").bind('click', function(){
				self.onRemoveLayerEvent.dispatch($(this));
			});
			
			/*$("input[name='appname']").on('keyup',function() {
				$("input[name='projectcode']").val(self.editApplicationListener.specialCharValidation($(this).val()));
			});*/

			$("input[name='appCode']").bind('input',function() {
				var str = $(this).val();
				str = str.replace(/[^a-zA-Z 0-9\-\_]+/g, '');
				str = str.replace(/\s+/g, '');
				$(this).val(str);
			});
			
			$("input[name='appDirName']").bind('input',function() {
				$("input[name='appDirName']").val(self.specialCharValidation($(this).val()));
			}); 

			self.addLayers();
	
			self.editApplicationListener.serverDBChangeEvent();
			self.editApplicationListener.addServerDatabaseEvent();
			self.editApplicationListener.removeServerDatabaseEvent();
			
			
			$("input[name='appDirName']").focusout(function() {
				$(this).val(self.specialCharValidation($(this).val().replace(/\s/g, "")));
				var totalLength = $(this).val().length;
				if($(this).val().match(/[._-]/g) !== null){
					var charLength = $(this).val().match(/[._-]/g).length;
				}
				if(totalLength === charLength){
					$(this).val('');
					$(this).focus();
					$(this).addClass('errormessage');
					$(this).attr('placeholder', 'Invalid AppDirectory Name');
				}
				$(this).bind('input', function() {
					$(this).removeClass("errormessage");
					$(this).removeAttr("placeholder");
				});
			});

			
			self.windowResize();	
			this.customScroll($(".scrolldiv"));
			
					
		},
		
		addLayers : function() {
			var self=this;
			$(".content_end input").unbind('click');
			$(".content_end input").bind('click', function(){
				self.onAddLayerEvent.dispatch($(this));
				var selectedFwTool = $("select[name='func_framework_tools']").find(":selected").text();
				("testing" === $(this).attr("name")) ? ("TAW" === selectedFwTool ? $('.functionalFrameworkURL').show() :  $('.functionalFrameworkURL').hide()) : "";
				return false;
			});
		}
	});

	return Clazz.com.components.application.js.Application;
});