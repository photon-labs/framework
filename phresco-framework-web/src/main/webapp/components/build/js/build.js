define(["framework/widgetWithTemplate", "build/listener/buildListener"], function() {
	Clazz.createPackage("com.components.build.js");

	Clazz.com.components.build.js.Build = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/build/template/build.tmp",
		configUrl: "components/build/config/config.json",
		name : commonVariables.build,
		buildListener : null,
		onProgressEvent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.buildListener = new Clazz.com.components.build.js.listener.BuildListener(globalConfig);
			self.registerEvents();
		},
		
		registerEvents : function () {
			var self = this;
			self.onProgressEvent = new signals.Signal();
			self.onProgressEvent.add(self.buildListener.onPrgoress, self.buildListener);
		},
		
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(){
			Clazz.navigationController.push(this);
		},
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
			self.buildListener.getBuildInfo(self.buildListener.getRequestHeader(self.projectRequestBody), function(response) {
				renderFunction(response, whereToRender);
			});
			
			/* var buildInfo = {"buildInfo":[
				{"version" : "1000", "datetime":"18/Mar/2013 10:51:43"},
				{"version" : "1001", "datetime":"19/Mar/2013 10:23:43"},
				{"version" : "1002", "datetime":"20/Mar/2013 10:45:43"},
				{"version" : "1003", "datetime":"21/Mar/2013 10:11:43"},
				{"version" : "1004", "datetime":"23/Mar/2013 10:33:43"},
				{"version" : "1005", "datetime":"24/Mar/2013 10:55:43"}
			]};
			renderFunction(buildInfo, whereToRender); */
		},

		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {			
		},
		
		registerEvents : function(configurationlistener) {
			var self=this;
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			
			$("input[name=build_runagsource]").unbind("click");
			$("input[name=build_runagsource]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$("input[name=build_genbuild]").unbind("click");
			$("input[name=build_genbuild]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$("input[name=build_minifier]").unbind("click");
			$("input[name=build_minifier]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$("#buildclose").click(function() {
				self.buildListener.onPrgoress(this);
			});
			
			$("img[name=downloadBuild]").click(function(){
				console.info("download clicked");
			});
			
			$("#buildRun").click(function(){
				console.info("Build run clicked");
			});
			
			$(".tooltiptop").unbind("click");
			$(".tooltiptop").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$('.connected').sortable({
				connectWith: '.connected'
			});

			$(window).resize(function() {
			
				$(".dyn_popup").hide();

				var height = $(this).height();
				var twowidth = window.innerWidth/1.6;
				var onewidth = window.innerWidth - (twowidth+70);
				
				$('.features_content_main').height(height - 230);
				$('.build_progress').height(height - 230);
				
				$('.build_info').css("width",twowidth);
				$('.build_progress').css("width",onewidth);
				$('.build_close').css("right",onewidth+10);
				
			});
			
			$(window).resize();
		
			$("#content_div").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin"
			});
			
			//To open the build directory
			$('#openFolder').unbind('click');
			$("#openFolder").click(function() {
				var paramJson = {};
				paramJson.type = commonVariables.typeBuild;
				commonVariables.navListener.openFolder(paramJson);
			});
			
			//To copy the path of build directory
			$('#copyPath').unbind('click');
			$("#copyPath").click(function() {
				var paramJson = {};
				paramJson.type = commonVariables.typeBuild;
				commonVariables.navListener.copyPath(paramJson);
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.build.js.Build;
});