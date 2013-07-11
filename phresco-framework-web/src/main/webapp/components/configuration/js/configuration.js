define(["configuration/listener/configurationListener"], function() {
	Clazz.createPackage("com.components.configuration.js");

	Clazz.com.components.configuration.js.Configuration = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/configuration/template/configuration.tmp",
		configUrl: "components/configuration/config/config.json",
		name : commonVariables.configuration,
		configurationAPI : null,
		configurationlistener : null,
		editConfigurationEvent : null,
		addEnvEvent : null,
		saveEnvEvent : null,
		configRequestBody : {},
		templateData : {},
		envWithConfig: null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.configurationlistener = new Clazz.com.components.configuration.js.listener.ConfigurationListener(globalConfig);
			self.registerEvents(self.configurationlistener);
		},

		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(transitionType){
			Clazz.navigationController.push(this, transitionType);
		},
		
		/***
		* For Unit Tests
		*/
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
			self.configurationlistener.getConfigurationList(self.configurationlistener.getRequestHeader(self.configRequestBody, "list"), function(response) {
				self.templateData.configurationList = response.data;
				self.envWithConfig = response.data;
				var userPermissions = JSON.parse(self.configurationlistener.configurationAPI.localVal.getSession('userPermissions'));
				self.templateData.userPermissions = userPermissions;
				renderFunction(self.templateData, whereToRender);
			});			
		}, 
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			commonVariables.navListener.applyRBAC(commonVariables.configuration);
		},
		
		registerEvents : function(configurationlistener) {
			var self=this;
			self.editConfigurationEvent = new signals.Signal();
			self.addEnvEvent = new signals.Signal();
			self.saveEnvEvent = new signals.Signal();
			self.cloneEnvEvent = new signals.Signal();
			self.editConfigurationEvent.add(configurationlistener.editConfiguration, configurationlistener);
			self.addEnvEvent.add(configurationlistener.addEnvEvent, configurationlistener);
			self.saveEnvEvent.add(configurationlistener.saveEnvEvent, configurationlistener);
			self.cloneEnvEvent.add(configurationlistener.cloneEnv, configurationlistener);
		},
		
		getAction : function(configRequestBody, action, deleteEnvironment, value) {
			var self=this;
			self.configurationlistener.getConfigurationList(self.configurationlistener.getRequestHeader(self.configRequestBody, action, deleteEnvironment), function(response) {
				if (action === "delete") {
					self.deleteEnv(value);
				} else {
					setTimeout(function(){
						self.successMsgPopUp(response.message);			
					},2500);
					self.loadPage(true);
				}
			});
		},
		
		deleteEnv : function (value) {
			$(value).parent().parent().parent().parent().remove();
			$("#content_Env li").each(function(){
				if ($(this).attr('name') === deleteEnvironment) {
					$(this).remove();
				}
			});
		},
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			
			$('.connected').sortable({
				connectWith: '.connected'
			});
			
			$(".tooltiptop").tooltip();
			$(".dyn_popup").hide();
			$("a[name=clone_pop]").unbind("click");
			$("a[name=clone_pop]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$("input[name=addEnv]").unbind("click");
			$("input[name=addEnv]").click(function() {
				var arr=[];
				var count=0;
				var found = false;
				$('.envlistname').each(function() {
					arr[count]=$(this).text();
					count++;
				});
				var name = $("input[name=envName]").val();
				var envDesc = $("input[name=envDesc]").val();
				if(name === ""){	
					$("input[name='envName']").focus();
					$("input[name='envName']").attr('placeholder','Enter Environment Name');
				} else {							  
					$('.envlistname').each(function() {
						if ($(this).text().toLowerCase() === $("input[name='envName']").val().toLowerCase()) {
							found = true;
							return false;
						}
					});
					
					if (found === false) {
						self.addEnvEvent.dispatch(name, envDesc);
						$("input[name='envName']").attr('placeholder','Environment Name');
						$("input[name='envDesc']").attr('placeholder','Environment Description');
					} else {
						$("#errdisplay").show();
						$("#errdisplay").text("Environment already Exist");
						setTimeout(function() {
							$("#errdisplay").hide();
						}, 1000);
						$("input[name='envName']").focus();
					}
				}
			});
			
			$('input[name=envName]').keypress(function(e) {
				if ((e.which >= 65 && e.which <= 90) ||(e.which >= 97 && e.which <= 122) || (e.which === 8)) {
					return true;
				} else {
					e.preventDefault();
				}
			});
			
			$("input[name=saveEnvironment]").unbind("click");
			$("input[name=saveEnvironment]").click(function() {
				self.saveEnvEvent.dispatch(self.envWithConfig, function(response){
					self.configRequestBody = response;
					self.getAction(self.configRequestBody, 'saveEnv', '', '');
				});
			});
			
			$(".tooltiptop").unbind("click");
			$(".tooltiptop").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$("input[name=env_pop]").unbind("click");
			$("input[name=env_pop]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$("input[name='deleteEnv']").unbind('click');
			$("input[name='deleteEnv']").click(function(e) {
				deleteEnvironment = $(this).parent().parent().attr('id');
				self.configRequestBody = {};
				self.getAction(self.configRequestBody, 'delete', deleteEnvironment, $(this));
			});
			
			$("a[name=editConfiguration]").unbind("click");
			$("a[name=editConfiguration]").click(function() {
				self.editConfigurationEvent.dispatch($(this).attr('key'));
			});
			
			$('input[name=envrName]').keypress(function(e) {
				if ((e.which >= 65 && e.which <= 90) ||(e.which >= 97 && e.which <= 122) || (e.which === 8)) {
					return true;
				} else {
					e.preventDefault();
				}
			});
			
			$("input[name='cloneEnvr']").unbind("click");
			$("input[name='cloneEnvr']").click(function() {
				var envrName = $(this).parent().attr('name');
				self.cloneEnvEvent.dispatch($(this), envrName, function(response){
					self.configRequestBody = response;
					self.getAction(self.configRequestBody, 'cloneEnv', envrName);
				}); 
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
		
	});

	return Clazz.com.components.configuration.js.Configuration;
});