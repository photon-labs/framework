define(["framework/widgetWithTemplate", "navigation/listener/navigationListener"], function() {
	
	Clazz.createPackage("com.components.navigation.js");

	Clazz.com.components.navigation.js.navigation = Clazz.extend(Clazz.WidgetWithTemplate, {
		navigationEvent : null,
		navigationHeader : null,
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/navigation/template/navigation.tmp",
		configUrl: "components/navigation/config/config.json",
		name : window.commonVariables.navigation,
		navigationListener : null,
		onAddNewProjectEvent : null,
		onMytabEvent : null,
		currentContent : null,
		onImportEvent : null,
		 
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
			self.navigationListener = commonVariables.navListener;
			self.registerEvents(self.navigationListener);
		},

		/***
		 * Called in once the navigation is success
		 *
		 */
		loadPage : function(){
			commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
			self.navigationListener = commonVariables.navListener;
		},
		
		registerEvents : function(navigationListener) {
			var self = this;
			self.onAddNewProjectEvent = new signals.Signal();
			self.onMytabEvent = new signals.Signal();
			self.onQualitytabEvent = new signals.Signal();
			self.onAddNewProjectEvent.add(navigationListener.onAddProject, navigationListener); 
			self.onMytabEvent.add(navigationListener.onMytabEvent, navigationListener); 
			self.onQualitytabEvent.add(navigationListener.onQualitytab, navigationListener); 			
			self.onImportEvent = new signals.Signal();
			self.onImportEvent.add(navigationListener.addImportEvent, navigationListener);
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {	
			var self = this;
			self.navigationListener.landingPage(self.currentContent);
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			
			$('#addproject').unbind('click');
			$('#addproject').click(function(){
				self.onAddNewProjectEvent.dispatch();
			}); 
			 
			$("#editprojectTab li").click(function() {
				$("#editprojectTab li a").removeClass("act");
				$(this).children().addClass("act");
				self.onMytabEvent.dispatch(this.id);
			});

			$("#myTab li").click(function() {
				$("#myTab li a").removeClass("act");
				$(this).children().addClass("act");
				self.onMytabEvent.dispatch(this.id);
			});
			
			$("#qualityAssurance li").click(function() {
				self.onQualitytabEvent.dispatch(this.id);
			});

			$("#importApp").click(function() {
				var currentPrjName = "";
				self.opencc(this, "project_list_import", currentPrjName);
			});
			
			$(".gitdata").hide();
			$(".importselect select").change(function () {
				if($(this).val() == "Bitkeeper") {
					$(".svndata").hide();
					$(".gitdata").show();
				}

				else if($(this).val() == "Git") {
					$(".svndata").hide();
					$(".gitdata").show();	
				}

				else if($(this).val() == "SVN") {
					$(".svndata").show();
					$(".gitdata").hide();	
				}
			});
			
			$("input[name='importbtn']").unbind("click");
			$("input[name='importbtn']").click(function() {
				var  revision;
				var revision = $("input[name='headoption']:checked").val();
				if(revision !== ""){
					revision = revision;
				} else{
					revision = $("#revision").val();
					console.info("revision", revision);
				}
				self.onImportEvent.dispatch(revision);				
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			
		}
	});

	return Clazz.com.components.navigation.js.navigation;
});