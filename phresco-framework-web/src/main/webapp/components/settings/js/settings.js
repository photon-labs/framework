define(["framework/widgetWithTemplate", "settings/listener/settingsListener"], function() {
	Clazz.createPackage("com.components.settings.js");

	Clazz.com.components.settings.js.Settings = Clazz.extend(Clazz.WidgetWithTemplate, {
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/settings/template/settings.tmp",
		configUrl: "components/projects/config/config.json",
		name : commonVariables.settings,
		getSettings : null,
		switchStatus : null,
		start : null,
		stop : null,
		sonarstart : null,
		sonarstop : null,
		sonarsetup : null,
		sonarpresetup : null,
		setup : null,
		presetup : null,
		save : null,
		sonarsave : null,
		settingsListener: null,
		onShowHideConsoleEvent : null,
		remotesonar : null,
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			if (self.settingsListener === null) {
				self.settingsListener = new Clazz.com.components.settings.js.listener.SettingsListener(globalConfig);
			}
			
			self.registerEvents(self.settingsListener);
		},
		
		registerEvents : function (settingsListener) {
			var self=this;
			// Register events
			if (self.getSettings === null) {
				self.getSettings = new signals.Signal();
			}
			
			if (self.switchStatus === null) {
				self.switchStatus = new signals.Signal();
			}
			
			if (self.setup === null) {
				self.setup = new signals.Signal();
			}
			
			if (self.start === null) {
				self.start = new signals.Signal();
			}
			
			if (self.stop === null) {
				self.stop = new signals.Signal();
			}
			
			if (self.save === null) {
				self.save = new signals.Signal();
			}
			
			if (self.onShowHideConsoleEvent === null) {
				self.onShowHideConsoleEvent = new signals.Signal();
			}
			
			if (self.presetup === null) {
				self.presetup = new signals.Signal();
			}
			
			if (self.sonarpresetup === null) {
			    self.sonarpresetup = new signals.Signal();
			}
			
			if (self.sonarsetup === null) {
			    self.sonarsetup = new signals.Signal();
			}
			
			if (self.sonarstart === null) {
			   self.sonarstart = new signals.Signal();
			}
			
			if (self.sonarstop === null) {
			   self.sonarstop = new signals.Signal();
			}
			
			if (self.sonarsave === null) {
			   self.sonarsave = new signals.Signal();
			}

			
			// Trigger registered events
			self.getSettings.add(settingsListener.getSettings, settingsListener);
			self.switchStatus.add(settingsListener.switchStatus, settingsListener);
			self.start.add(settingsListener.start, settingsListener);
			self.stop.add(settingsListener.stop, settingsListener);
			self.setup.add(settingsListener.setup, settingsListener);
			self.save.add(settingsListener.save, settingsListener);
			self.onShowHideConsoleEvent.add(settingsListener.showHideConsole, settingsListener);
			self.presetup.add(settingsListener.presetup, settingsListener);
			self.sonarsetup.add(settingsListener.sonarsetup, settingsListener);
			self.sonarpresetup.add(settingsListener.sonarpresetup, settingsListener);
			self.sonarstart.add(settingsListener.sonarstart, settingsListener);
			self.sonarstop.add(settingsListener.sonarstop, settingsListener);
			self.sonarsave.add(settingsListener.sonarvalue,settingsListener);
			
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
			self.resizeConsoleWindow();
			self.settingsListener.getSettings();
			commonVariables.navListener.showHideControls(commonVariables.settings);
			$('input[name=sonarjdbcUrl]').prop("disabled",true);
		    $('input[name=sonarName]').prop("disabled",true);
			$('input[name=sonarpassword]').prop("disabled",true);
			$('input[name=sonarsave]').prop("disabled",true);
		},
		
		
		/* dynamicEvent : function() {
			var self = this; 
			var dependency = '';
			dependency = $("select[name='sonar']").find(':selected').attr('dependency');
				
		}, */

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
   			$(".dyn_popup").hide();
   			
   			$('input[name=presetup]').click(function() {
   				self.presetup.dispatch(this);
   				$("#unit_popup").toggle();
			});
   			
			$('input[name=setup]').click(function() {
					self.setup.dispatch();
					$("#unit_popup").toggle();
			});
				
   			$("input[name=switch]").click(function() {		
   				if ($(this).attr('value') === 'Start') {
   					$('input[name=switch]').attr('value', "Stop");
   					self.start.dispatch();
   				} else if ($(this).attr('value') === 'Stop') {
					$('input[name=switch]').attr('value', "Start");
					$('input[name=presetup]').attr('disabled', false);
					self.stop.dispatch();
				}
   				$("#unit_popup").toggle();
   			});
			
			$('input[name=sonarpresetup]').click(function() {
   				self.sonarpresetup.dispatch(this);
   				$("#unit_popup").toggle();
			});
				
			$('input[name=sonarsetup]').click(function() {
					self.sonarsetup.dispatch();
					$("#unit_popup").toggle();
			 });
   			
			$("input[name=sonarswitch]").click(function() {		
				if ($(this).attr('value') === 'Start') {
					$('input[name=sonarswitch]').attr('value', "Stop");
					self.sonarstart.dispatch();
				} else if ($(this).attr('value') === 'Stop') {
					$('input[name=sonarswitch]').attr('value', "Start");
					$('input[name=presonarsetup]').attr('disabled', false);
					self.sonarstop.dispatch();
				}
					$("#unit_popup").toggle();
				});
				
   			
   		//To show hide the console content when the console is clicked
			$('#consoleImg').unbind("click");
			$('#consoleImg').click(function() {
				self.onShowHideConsoleEvent.dispatch();
			});
			
   			$('.addConfluence').bind('click', function() {
				var tds = $('#prototype').html();
				var tr = $('<tr class=confluence>');
				tr.append(tds);
				tr.insertBefore($('#save'));
			});
   			
   			
   			$('.addKeychain').bind('click', function() {
				var tds = $('#keychainPrototype').html();
				var tr = $('<tr class=keychain>');
				tr.append(tds);
				tr.insertBefore($('#keychainSave'));
			});
   			
			$('.addTestFlight').bind('click', function() {
				var tds = $('#testFlightPrototype').html();
				var tr = $('<tr class=testFlight>');
				tr.append(tds);
				tr.insertBefore($('#testFlightSave'));
			});
			
   		//To copy the console log content to the clip-board
			/* $('#copyLog').unbind("click");
			$('#copyLog').click(function() {
				commonVariables.navListener.copyToClipboard($('#testConsole'));
			}); */
   			
   			$('input[name=save]').click(function() {
   				self.save.dispatch();
			});
			
			$('input[name=sonarsave]').click(function() {
   				self.sonarsave.dispatch();
			});
			
			$('input[name=remotesonar]').click(function(){
				 if ($(this).is(":checked")) {
				    $('input[name=sonarjdbcUrl]').prop("disabled",false);
					$('input[name=sonarName]').prop("disabled",false);
					$('input[name=sonarpassword]').prop("disabled",false);
					$('input[name=sonarsave]').prop("disabled",false);
					$('input[name=sonarpresetup]').prop("disabled",true);
					$('input[name=sonarswitch]').prop("disabled",true);
					
					
				 }else{
				    $('input[name=sonarjdbcUrl]').prop("disabled",true);
					$('input[name=sonarName]').prop("disabled",true);
					$('input[name=sonarpassword]').prop("disabled",true);
					$('input[name=sonarsave]').prop("disabled",true);
					$('input[name=sonarpresetup]').prop("disabled",false);
					$('input[name=sonarswitch]').prop("disabled",false);
				 }
			 });
			
   			self.customScroll($(".consolescrolldiv"));

			self.tableScrollbar();
		}
	});

	return Clazz.com.components.settings.js.Settings;
});