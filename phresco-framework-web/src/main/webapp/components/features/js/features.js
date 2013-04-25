define(["framework/widgetWithTemplate", "features/listener/featuresListener"], function() {
	Clazz.createPackage("com.components.features.js");

	Clazz.com.components.features.js.Features = Clazz.extend(Clazz.WidgetWithTemplate, {
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "/components/features/template/features.tmp",
		configUrl: "../components/projects/config/config.json",
		name : commonVariables.featurelist,
		featuresListener: null,
		onPreviousEvent: null,
		onSearchEvent: null,
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
			self.featuresListener = new Clazz.com.components.features.js.listener.FeaturesListener(globalConfig);
			self.registerEvents();
		},
		
		
		registerEvents : function () {
			var self = this;
			self.onPreviousEvent = new signals.Signal();
			self.onPreviousEvent.add(self.featuresListener.onPrevious, self.featuresListener);

			self.onSearchEvent = new signals.Signal();
			self.onSearchEvent.add(self.featuresListener.search, self.featuresListener); 
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
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self=this;
			$(".dyn_popup").hide();
			$('.switch').css('background', 'url("../themes/default/images/helios/on_off_switch.png")');
			$('.on_off').css('display','none');
			 
			$("input[name=on_off]").click(function() {
				var button = $(this).val();
				if(button == 'off'){ $(this).closest('fieldset').css('background-position', 'right'); }
				if(button == 'on'){ $(this).closest('fieldset').css('background-position', 'left'); }	
			});

			$("#content_1").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin"
			});
			
			$("#content_2").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin"
			});
			
			$("#content_3").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin"
			});
			
			$("#prev").click(function() {
				self.onPreviousEvent.dispatch();
			});

			$('#module').keyup(function(event) {
				var txtSearch = $('#module').val();
				var divId = "moduleContent";
				//search(txtSearch, divId);
				self.onSearchEvent.dispatch(txtSearch, divId);
           	});

           	$('#jsibraries').keyup(function(event) {
				var txtSearch = $('#jsibraries').val();
				var divId = "jsibrariesContent";
				//search(txtSearch, divId);
				self.onSearchEvent.dispatch(txtSearch, divId);
           	});

           	$('#components').keyup(function(event) {
				var txtSearch = $('#components').val();
				var divId = "componentsContent";
				//search(txtSearch, divId);
				self.onSearchEvent.dispatch(txtSearch, divId);
           	});

           
		}
	});

	return Clazz.com.components.features.js.Features;
});