define(["framework/widgetWithTemplate", "features/listener/featuresListener"], function() {
	Clazz.createPackage("com.components.features.js");

	Clazz.com.components.features.js.Features = Clazz.extend(Clazz.WidgetWithTemplate, {
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/features/template/features.tmp",
		configUrl: "components/projects/config/config.json",
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
			
			Handlebars.registerHelper('versionShowHide', function(versions) {
				var fieldset;
				$.each(versions, function(index, value){
					$.each(value.appliesTo, function(index, value){
						if(value.required == true){
							fieldset = '<div class="flt_right" style="display:block;">';
						}else{							
							fieldset = '<div class="flt_right" style="display:none;">';
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
				console.info("responseData", responseData);
				renderFunction(responseData, whereToRender);
				self.featuresListener.hideLoad();
			});
		},


		getFeatures : function(collection, callback){
			var self = this;
			self.featuresListener.getFeaturesList(self.featuresListener.getRequestHeader(self.featureRequestBody, "FEATURE"), function(response) {
				collection.featureslist = response.data;	
				self.getLibraries(collection, callback);
			});
		},

		getLibraries : function(collection, callback){
			var self = this;
			self.featuresListener.getFeaturesList(self.featuresListener.getRequestHeader(self.featureRequestBody, "JAVASCRIPT"), function(response) {
				collection.jsibrarielist = response.data;	
				self.getComponents(collection, callback);
			});
		},

		getComponents : function(collection, callback){
			var self = this;
			self.featuresListener.getFeaturesList(self.featuresListener.getRequestHeader(self.featureRequestBody, "COMPONENT"), function(response) {
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
			$(window).resize(function() {
				$(".dyn_popup").hide();
				var height = $(this).height();
				$('.box_div').height(height - 306);
			  });

			$('.switch').css('background', 'url("themes/default/images/helios/on_off_switch.png")');
			$("label[name=on_off]").click(function() {
				self.bcheck(this);
			});
			 
			$("input[name=on_off]").click(function() {
				var button = $(this).val();
				if(button == 'off'){ $(this).closest('fieldset').css('background-position', 'right'); }
				if(button == 'on'){ $(this).closest('fieldset').css('background-position', 'left'); }	
			});

			self.scrollbarEnable();
			
			$('#module').keyup(function(event) {
				var txtSearch = $('#module').val();
				var divId = "moduleContent";
				self.onSearchEvent.dispatch(txtSearch, divId);
           	});

           	$('#jsibraries').keyup(function(event) {
				var txtSearch = $('#jsibraries').val();
				var divId = "jsibrariesContent";
				self.onSearchEvent.dispatch(txtSearch, divId);
           	});

           	$('#components').keyup(function(event) {
				console.info("test");
				var txtSearch = $('#components').val();
				var divId = "componentsContent";
				self.onSearchEvent.dispatch(txtSearch, divId);
           	});

           	$('#switchoffbutton').on("click", function(event) {
           		self.showSelected();
           	});
			
			$('label.on').click(function() {
				$(this).parent().next('div').show();	      		
           	});
			
			$('label.off').click(function() {
				$(this).parent().next('div').hide();
				var value = $('#switchoffbutton').parent().attr('class');
				if (value === "switch switchOn") {
					self.showSelected();	      		
				}
           	});
			
           	$('#switchonbutton').on("click", function(event) {
           		$("ul li").show();
           		self.scrollbarUpdate();
           	});

       		$('#cancelUpdateFeature').click(function() {
				self.onCancelEvent.dispatch();
           	});

          	$('.featureinfo_img').on("click", function(event) {				
				var descid = $(this).attr("value");
				var currentObj = this;				
				self.featuresListener.getFeaturesList(self.featuresListener.getRequestHeader(self.featureRequestBody, "desc", descid), function(response) {
					var divhtml = '<div id="'+descid+'" class="dyn_popup featureinfo"><h1>Description</h1><a href="#" class="dyn_popup_close">X</a><div class="features_cont"><span><img src="themes/default/images/helios/feature_info_logo.png" width="42" height="42" border="0" alt=""></span>'+response.data+'</div></div>';
					$("#desc").children().remove();
					$("#desc").append(divhtml);
					commonVariables.temp = currentObj;
					commonVariables.openccmini(currentObj,descid);
					$("#"+descid).show();
				});
           	});
			
			$('#featureUpdate').on("click", function() {
				var featureUpdatedataArray = [];
				var featureUpdatedata = {};
				$(".switchOn").each(function(index, currentVal) {
					featureUpdatedata.name = $(currentVal).parent().attr("type");
					featureUpdatedata.dispName = $(currentVal).parent().attr("dispName");
					featureUpdatedata.packaging = $(currentVal).parent().attr("packaging");
					featureUpdatedata.type = $(currentVal).parent().attr("type");					
					featureUpdatedata.defaultModule = true;
					featureUpdatedata.scope = $(currentVal).parent().children('div.flt_right').children('select.input-mini').find(':selected').attr("scope");
					featureUpdatedata.versionID = $(currentVal).parent().children('div.flt_right').children('select.input-mini').find(':selected').val();
					featureUpdatedata.dispValue = $(currentVal).parent().children('div.flt_right').children('select.input-mini').find(':selected').text();
					var moduleId = $(currentVal).parent().children('div.flt_right').children('.moduleId').val();
					featureUpdatedata.moduleId = moduleId; 
					featureUpdatedata.artifactGroupId = moduleId;
					featureUpdatedataArray.push(featureUpdatedata);
					console.info("featureUpdatedataArray", featureUpdatedataArray);
				});
				
				self.featuresListener.getFeaturesUpdate(self.featuresListener.getRequestHeader(featureUpdatedataArray, "", ""), function(response) {
					//console.info("response", response);
				});
			});
		},
		
		showSelected : function() {
			var self = this;
			$("#moduleContent li").hide();
			$("#jsibrariesContent li").hide();
			$("#componentsContent li").hide();

			$("ul li fieldset").each(function() {
				if($(this).attr("class") == "switch switchOn"){
					$(this).parent().show();
					self.scrollbarUpdate();
				}     			
			});
		},
		
		scrollbarEnable : function(){
			$("#content_1").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				updateOnContentResize: true
			});
			
			$("#content_2").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin"
			});
			
			$("#content_3").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin"
			});
		},
		scrollbarUpdate : function(){
			$("#content_1").mCustomScrollbar("update"); 
			$("#content_2").mCustomScrollbar("update"); 
			$("#content_3").mCustomScrollbar("update"); 
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