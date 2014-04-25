define(["configuration/listener/configurationListener"], function() {
	Clazz.createPackage("com.components.configuration.js");

	Clazz.com.components.configuration.js.ProjectSettings = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/configuration/template/settings.tmp",
		configUrl: "components/configuration/config/config.json",
		name : commonVariables.projectSettings,
		configRequestBody : {},
		templateData : {},
		configurationlistener : null,
		envWithConfig : null,
		editConfigurationEvent : null,
		addEnvEvent : null,
		saveEnvEvent : null,
		favourite : null,
		
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

		loadPage : function(){
			Clazz.navigationController.push(this, commonVariables.animation);
		},
		
		preRender: function(whereToRender, renderFunction){
			var self=this;
			self.configurationlistener.favourite = self.favourite;
			self.configurationlistener.getConfigurationList(self.configurationlistener.getRequestHeader(self.configRequestBody, "list"), function(response) {
				self.templateData.configurationList = response.data;
				self.envWithConfig = response.data;
				var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
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
			var self=this;
			commonVariables.navListener.currentTab = commonVariables.projectSettings;
		},
		
		registerEvents : function(configurationlistener) {
			var self=this;
			self.addEnvEvent = new signals.Signal();
			self.saveEnvEvent = new signals.Signal();
			self.editConfigurationEvent = new signals.Signal();
			self.cloneEnvEvent = new signals.Signal();
			self.addEnvEvent.add(configurationlistener.addEnvEvent, configurationlistener);
			self.saveEnvEvent.add(configurationlistener.saveEnvEvent, configurationlistener);
			self.editConfigurationEvent.add(configurationlistener.editConfiguration, configurationlistener);
			self.cloneEnvEvent.add(configurationlistener.cloneEnv, configurationlistener);
		},
		
		getAction : function(configRequestBody, action, deleteEnvironment, value) {
			var self=this;
			self.configurationlistener.getConfigurationList(self.configurationlistener.getRequestHeader(self.configRequestBody, action, deleteEnvironment), function(response) {
				if (action === "delete" || action === "deleteConfig") {
					self.deleteEnv(value);
					commonVariables.api.showError(response.responseCode ,"success", true);
				} else {
					commonVariables.api.showError(response.responseCode ,"success", true);				
					setTimeout(function() {
						self.loadPage(commonVariables.animation);
					},1200);
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
			var self=this, array=[], defaultEnv=[], count=0;
			array[count]=$('.envlistname').text();
			$("input[name='optionsRadiosfd']").each(function() {
				defaultEnv[count] = $(this).is(':checked');
				count++;
			});
			
			$(".tooltiptop").tooltip();
			$(".dyn_popup").hide();
			
			$("a[name=clone_pop]").unbind("click");
			$("a[name=clone_pop]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$(".tooltiptop").unbind("click");
			$(".tooltiptop").click(function() {
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
						self.addEnvEvent.dispatch(name, envDesc, "settings");
						$("input[name='envName']").attr('placeholder','Environment Name');
						$("input[name='envDesc']").attr('placeholder','Environment Description');
					} else {
						commonVariables.api.showError("envalreadyexists" ,"error", true);	
						$("input[name='envName']").focus();
					}
				}
			});
			
			$("input[name=saveEnvironment]").unbind("click");
			$("input[name=saveEnvironment]").click(function() {
				var arr=[];
				var Env=[];
				var counts=0;
				arr[counts]=$('.envlistname').text();
				$("input[name='optionsRadiosfd']").each(function() {
					Env[counts] = $(this).is(':checked');
					counts++;
				}); 
				if (($.trim(Env) === $.trim(defaultEnv)) && ($.trim(arr) === $.trim(array))){
					$("input[name='envName']").focus();
					$("input[name='envName']").attr('placeholder','Enter Environment Name');
					$("input[name='envName']").addClass("errormessage");
					$("input[name='envName']").bind('keypress', function() {
						$("input[name='envName']").removeClass("errormessage");
					});
					$("input[value='Close']").bind('click', function() {
					$("input[name='envName']").removeClass("errormessage");
				});
				} else {
					self.saveEnvEvent.dispatch(self.envWithConfig, function(response){
						self.configRequestBody = response;
						if(response.status !== 'failure' || response.status !== 'error') {
							self.getAction(self.configRequestBody, 'saveEnv', '', '');
						}	
					}); 
				}
			});
			
			$("a[name=editConfiguration]").unbind("click");
			$("a[name=editConfiguration]").click(function() {
				self.editConfigurationEvent.dispatch($(this).attr('key'), $(this).attr('envSpecificValue'));
			});
			
			$("input[name=env_pop]").unbind("click");
			$("input[name=env_pop]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$('input[name=envrName]').keypress(function(e) {
				if ((e.which >= 40 && e.which <= 95) ||(e.which >= 97 && e.which <= 122) || (e.which === 8) || (e.which === 0)) {
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
			
			$("input[name='deleteEnv']").unbind('click');
			$("input[name='deleteEnv']").click(function(e) {
				deleteEnvironment = $(this).parent().parent().attr('id');
				self.configRequestBody = {};
				self.getAction(self.configRequestBody, 'delete', deleteEnvironment, $(this));
			});
			self.tableScrollbar();
		}
		
	});

	return Clazz.com.components.configuration.js.ProjectSettings;
});