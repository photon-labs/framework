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

		},
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
			setTimeout(function() {
				self.editApplicationListener.getAppInfo(self.editApplicationListener.getRequestHeader(self.appDirName , "getappinfo"), function(response) {
					var projectlist = {};
					projectlist.projectlist = response;
					var userPermissions = JSON.parse(self.editApplicationListener.applicationAPI.localVal.getSession('userPermissions'));
					projectlist.userPermissions = userPermissions;
					self.renderData = response;
					var techId = response.appdetails.data.appInfos[0].techInfo.id;
					self.editApplicationListener.getApplicableOptions(self.editApplicationListener.getRequestHeader(self.appDirName, "getApplicableOptions", techId), function(response) {
						self.editApplicationListener.applicationAPI.localVal.setSession("applicableOptions", JSON.stringify(response.data));
						renderFunction(projectlist, whereToRender);
					});
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
			
			$("input[name='appDirName']").bind('input', function(){
				var str = $(this).val();
				str = str.replace(/\s/g, "");
				$(this).val(str);
			});
			
			$(".content_end input").unbind('click');
			$(".content_end input").bind('click', function(){
				self.onAddLayerEvent.dispatch($(this));
				return false;
			});
			$('#cancelbutton').unbind('click');
			$('#cancelbutton').click(function(){
				self.onCancelEvent.dispatch();
			}); 
			self.editApplicationListener.serverDBChangeEvent();
			self.editApplicationListener.addServerDatabaseEvent();
			self.editApplicationListener.removeServerDatabaseEvent();
			
			$("#updatebutton").unbind('click');
			$("#updatebutton").bind('click', function(){
				self.updateApp.dispatch(self.renderData);
			});			
		}
	});

	return Clazz.com.components.application.js.Application;
});