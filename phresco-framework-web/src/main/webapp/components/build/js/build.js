define(["build/listener/buildListener"], function() {
	Clazz.createPackage("com.components.build.js");

	Clazz.com.components.build.js.Build = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/build/template/build.tmp",
		configUrl: "components/build/config/config.json",
		name : commonVariables.build,
		buildListener : null,
		onProgressEvent : null,
		onDownloadEvent : null,
		onDeleteEvent : null,
		onDeployEvent : null,
		onBuildEvent : null,
		dynamicpage : null,
		dynamicPageListener : null,
		generateBuildContent : "",
		logContent : '',
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			
			if(self.buildListener === null)
				self.buildListener = new Clazz.com.components.build.js.listener.BuildListener();
			
			if(self.dynamicpage === null){
				commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(retVal){
					self.dynamicpage = retVal;
					self.dynamicPageListener = self.dynamicpage.dynamicPageListener;
					self.registerEvents();
				});
			}else{self.registerEvents();}
		},
		
		registerEvents : function(){
			var self = this;
			
			if(self.onProgressEvent === null)
				self.onProgressEvent = new signals.Signal();
			if(self.onDownloadEvent === null)	
				self.onDownloadEvent = new signals.Signal();
			if(self.onDeleteEvent === null)
				self.onDeleteEvent = new signals.Signal();
			if(self.onDeployEvent === null)
				self.onDeployEvent = new signals.Signal();
			if(self.onBuildEvent === null)
				self.onBuildEvent = new signals.Signal();
				
			self.onProgressEvent.add(self.buildListener.onPrgoress, self.buildListener);
			self.onDownloadEvent.add(self.buildListener.downloadBuild, self.buildListener);
			self.onDeleteEvent.add(self.buildListener.deleteBuild, self.buildListener);
			self.onDeployEvent.add(self.buildListener.deployBuild, self.buildListener);
			self.onBuildEvent.add(self.buildListener.buildProject, self.buildListener);
		},
		
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(){
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(this, true);
		},
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
			self.buildListener.getBuildInfo(self.buildListener.getRequestHeader("", '', 'getList'), function(response) {
				commonVariables.goal = "package";
				if(self.dynamicpage === null){
					commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(retVal){
						self.dynamicpage = retVal;
						self.dynamicPageListener = self.dynamicpage.dynamicPageListener;
						self.loadDynamicContent(whereToRender, renderFunction, response);
					});
				}else{
					self.loadDynamicContent(whereToRender, renderFunction, response);
				}
			});
		},
		
		loadDynamicContent : function(whereToRender, renderFunction, response){
			var self = this;
			self.dynamicpage.getHtml(false, function(callbackVal){
				self.generateBuildContent = callbackVal;
				var userPermissions = JSON.parse(self.buildListener.buildAPI.localVal.getSession('userPermissions'));
				response.userPermissions = userPermissions;
				renderFunction(response, whereToRender);
			});
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self = this;
			commonVariables.navListener.showHideTechOptions();
			$("tbody[name='dynamicContent']").html(self.generateBuildContent);
			$("tbody[name='dynamicContent']").find(".selectpicker").selectpicker();
			self.loadPostContent();
			
			$('#logContent').html(self.logContent);
			self.logContent = '';
		},
		
		loadPostContent : function(){
			var self = this;
			if(self.dynamicpage === null){
				commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(retVal){
					self.dynamicPage = retVal;
					self.dynamicPageListener = self.dynamicPage.dynamicPageListener;
					self.dynamicpage.showParameters();
					self.dynamicPageListener.controlEvent();					
				});
			}else{
				self.dynamicpage.showParameters();
				self.dynamicPageListener.controlEvent();
			}
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
			
			$("#buildConsole").click(function() {
				self.onProgressEvent.dispatch(this);
			});
			
			$("img[name=downloadBuild]").click(function(){
				self.onDownloadEvent.dispatch($(this).parent().parent().siblings(":first").text(), function(response){
					console.info('download',response);
				});
			});
			
			$("img[name=deployBuild]").click(function(){
				var divId = $(this).closest('tr').find('td:eq(0)').text();
				commonVariables.openccmini(this,'deploye_' + divId);
			});
			
			$("input[name=deploy]").click(function(){
				self.onDeployEvent.dispatch(function(response){
					console.info('deploy',response);
				});
			});
			
			
			$('input[name=buildDelete]').click(function(){
				var divId = $(this).closest('tr').find('td:eq(0)').text();
				self.onDeleteEvent.dispatch(divId, function(response){
					self.loadPage();
				});
			});
			
			$("#buildRun").click(function(){
				$('.alert_div').show();
				self.onBuildEvent.dispatch($('form[name=buildForm]').serialize(), function(response){
					$('.alert_div').hide();
					self.logContent = $('#logContent').html();
					self.loadPage();
				});
			});
			
			$("#runSource").click(function(){
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
				var twowidth = window.innerWidth/1.7;
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
			
			$(".scrollContent").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced:{ updateOnContentResize: true}
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