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
		featureRequestBody: {},
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
			self.registerHandlebars();
		},
		
		
		registerEvents : function () {
			var self = this;

			self.onSearchEvent = new signals.Signal();
			self.onSearchEvent.add(self.featuresListener.search, self.featuresListener);
			self.onCancelEvent = new signals.Signal();
			self.onCancelEvent.add(self.featuresListener.cancelUpdate, self.featuresListener);
		},

		registerHandlebars : function () {
			Handlebars.registerHelper('versiondata', function(versions) {
				var fieldset;
				$.each(versions, function(index, value){
					$.each(value.appliesTo, function(index, value){
						if(value.required == true){
							fieldset = '<fieldset class="switch switchOn" value="false"><label class="off" name="on_off" value="false"></label><label class="on" name="on_off" value="true"></label></fieldset>';
						}else{							
							fieldset = '<fieldset class="switch switchOff" value="false"><label class="off" name="on_off" value="false"></label><label class="on" name="on_off" value="true"></label></fieldset>';
						}
					});					
				});
				return fieldset;
			});
		},


		/***
		 * Called in once the login is success */
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

		preRender: function(whereToRender, renderFunction){
			var self = this;
			var collection = {};
			self.featuresListener.showLoad();
			self.getFeatures(collection, function(responseData){
				renderFunction(responseData, whereToRender);
				self.featuresListener.hideLoad();
			});
		},


		getFeatures : function(collection, callback){
			var self = this;
			self.featuresListener.getFeaturesList(self.featuresListener.getRequestHeader(self.featureRequestBody, "features"), function(response) {
				collection.featureslist = response.data;	
				self.getLibraries(collection, callback);
			});
		},

		getLibraries : function(collection, callback){
			var self = this;
			self.featuresListener.getFeaturesList(self.featuresListener.getRequestHeader(self.featureRequestBody, "jsibraries"), function(response) {
				collection.jsibrarielist = response.data;	
				self.getComponents(collection, callback);
			});
		},

		getComponents : function(collection, callback){
			var self = this;
			self.featuresListener.getFeaturesList(self.featuresListener.getRequestHeader(self.featureRequestBody, "components"), function(response) {
				collection.componentList = response.data;	
				callback(collection);
			});
		},


		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self=this;
			$(".dyn_popup").hide();
			//$('.switch').css('background', 'url("../themes/default/images/helios/on_off_switch.png")');
			//$('.on_off').css('display','none');

			$('.switch').css('background', 'url("../themes/default/images/helios/on_off_switch.png")');
			$("label[name=on_off]").click(function() {
				self.bcheck(this);
			});
			 
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

           	$('#switchoffbutton').on("click", function(event) {
           		$("#moduleContent li").hide();
           		$("ul li fieldset").each(function() {
           			if($(this).attr("class") == "switch switchOn"){
           				$(this).parent().show();
           			}     			
           		});
           		
           	});
           	$('#switchonbutton').on("click", function(event) {
           		$("ul li").show();         
           	});

       		$('#cancelUpdateFeature').click(function() {
				self.onCancelEvent.dispatch();
           	});
           
		},

		bcheck : function(obj){
			var button = $(obj).attr("value");
			$(obj).closest('fieldset').removeClass('switchOn'); 
			$(obj).closest('fieldset').removeClass('switchOff'); 
			
			if(button == 'false'){ 
				$(obj).closest('fieldset').addClass('switchOff');
				$(obj).closest('fieldset').attr('value', "false"); 
			}else if(button == 'true'){ 
				$(obj).closest('fieldset').addClass('switchOn');
				$(obj).closest('fieldset').attr('value', "true");
			}	 
		}
	});

	return Clazz.com.components.features.js.Features;
});