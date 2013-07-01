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
		onRASEvent : null,
		onStopEvent : null,
		onRestartEvent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			
			if(self.buildListener === null){
				self.buildListener = new Clazz.com.components.build.js.listener.BuildListener();
			}
			
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
			
			if(self.onProgressEvent === null){
				self.onProgressEvent = new signals.Signal();
			}	
			if(self.onDownloadEvent === null){
				self.onDownloadEvent = new signals.Signal();
			}
			if(self.onDeleteEvent === null){
				self.onDeleteEvent = new signals.Signal();
			}
			if(self.onDeployEvent === null){
				self.onDeployEvent = new signals.Signal();
			}
			if(self.onBuildEvent === null){
				self.onBuildEvent = new signals.Signal();
			}
			if(self.onRASEvent === null){
				self.onRASEvent = new signals.Signal();
			}
			if(self.onStopEvent === null){
				self.onStopEvent = new signals.Signal();
			}
			if(self.onRestartEvent === null){
				self.onRestartEvent = new signals.Signal();
			}
			
			self.onProgressEvent.add(self.buildListener.onPrgoress, self.buildListener);
			self.onDownloadEvent.add(self.buildListener.downloadBuild, self.buildListener);
			self.onDeleteEvent.add(self.buildListener.deleteBuild, self.buildListener);
			self.onDeployEvent.add(self.buildListener.deployBuild, self.buildListener);
			self.onBuildEvent.add(self.buildListener.buildProject, self.buildListener);
			self.onRASEvent.add(self.buildListener.runAgainstSource, self.buildListener);
			self.onStopEvent.add(self.buildListener.stopServer, self.buildListener);
			self.onRestartEvent.add(self.buildListener.restartServer, self.buildListener);
		},
		
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(){
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(this, true);
		},
		
		loadPageType : function(){
			Clazz.navigationController.push(this , false);
		},
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
			self.buildListener.getBuildInfo(self.buildListener.getRequestHeader("", '', 'getList'), function(response) {
			var buildObject = {};
			var userPermissions = JSON.parse(self.buildListener.buildAPI.localVal.getSession('userPermissions'));
				buildObject.buildInfos = response.data;
				buildObject.userPermissions = userPermissions;
				renderFunction(buildObject, whereToRender);
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
            
			$("#build_genbuild ul").find(".selectpicker").selectpicker();
			self.buildListener.getBuildInfo(self.buildListener.getRequestHeader("", '', 'serverstatus'), function(response) {
				self.changeBtnStatus(response , '');
			});
			self.loadPostContent();
			
			$('#logContent').html(self.logContent);
			self.logContent = '';
			
			self.resizeConsoleWindow();
			self.logContent = '';
			self.closeConsole();
			var windowHeight = $(document).height();
			var marginTop = '';
			marginTop = $('.testSuiteTable').css("margin-top");
			if(marginTop !== undefined){
				marginTop = marginTop.substring(0, marginTop.length - 2);
			}	
			var footerHeight = $('#footer').height();
			var deductionHeight = Number(marginTop) + Number(footerHeight);
			var finalHeight = windowHeight - deductionHeight - 5;
			$('.testSuiteTable').height(finalHeight);					
		},
		
		changeBtnStatus : function(response , btnName){
			if(response.data === true){
				if(btnName !== ''){
					btnName.removeClass('btn_style');
					btnName.addClass('btn_style_off');
				}
				$("input[name=build_runagsource]").removeClass('btn_style');
				$("input[name=build_runagsource]").addClass('btn_style_off');
				$("#stop").removeClass('btn_style_off');
				$("#stop").addClass('btn_style');
				$("#restart").removeClass('btn_style_off');
				$("#restart").addClass('btn_style');
			}
		},

		loadPostContent : function(){
			var self = this;
			if(self.dynamicpage === null){
				commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(retVal){
					self.dynamicPage = retVal;
					self.dynamicPageListener = self.dynamicPage.dynamicPageListener;
				});
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
				if($(this).attr("class") === "btn btn_style"){
					commonVariables.goal = "start";
					commonVariables.phase = "run-against-source";
					var whereToRender = $('#build_runagsource ul');
					self.dynamicpage.getHtml(whereToRender, this, $(this).attr('name'), function() {});
					$("#buildConsole").attr('data-flag','false');
				}	
			});
			
			$("input[name=build_genbuild]").unbind("click");
			$("input[name=build_genbuild]").click(function() {
                var whereToRender = $('#build_genbuild ul');
                commonVariables.goal = "package";
                commonVariables.phase = "package";
                self.dynamicpage.getHtml(whereToRender, this, $(this).attr('name'));
			});
			
			$("input[name=build_minifier]").unbind("click");
			$("input[name=build_minifier]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$("#buildConsole").click(function() {
				var logContent = $('#logContent').text();
				if(logContent.match("INFO: Starting Coyote HTTP/1.1")){
					$("input[name=build_runagsource]").removeClass('btn_style');
					$("input[name=build_runagsource]").addClass('btn_style_off');
					$("#stop").removeClass('btn_style_off');
					$("#stop").addClass('btn_style');
					$("#restart").removeClass('btn_style_off');
					$("#restart").addClass('btn_style');
				}
				self.onProgressEvent.dispatch(this);
			});
			
			$("img[name=downloadBuild]").click(function(){
				self.onDownloadEvent.dispatch($(this).parent().parent().siblings(":first").text(), function(response){
				});
			});
			
			$("img[name=deployBuild]").click(function(){
				var divId = $(this).closest('tr').find('td:eq(0)').text();
				commonVariables.openccmini(this,'deploye_' + divId);
			});
			
			$("input[name=deploy]").click(function(){
				self.onDeployEvent.dispatch(function(response){
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
				self.openConsole();
				self.onBuildEvent.dispatch($('form[name=buildForm]').serialize(), function(response){
					$('.alert_div').hide();
					self.logContent = $('#logContent').html();
					self.loadPage();
				});
			});
			
			$("#runSource").click(function(){
				$(".dyn_popup").hide();
				self.openConsole();
				self.onRASEvent.dispatch($('form[name=runAgainstForm]').serialize(), function(response){
					$('.alert_div').hide();
					self.logContent = $('#logContent').html();
				});
			});
			
			$("#stop").click(function() {
				if($(this).attr("class") === "btn btn_style"){
					self.openConsole();
					$("#buildConsole").attr('data-flag','false');
					self.onStopEvent.dispatch(function(response){
						$('.alert_div').hide();
						self.logContent = $('#logContent').html();
						self.loadPage();
					});					
				}
				
			});		
			
			$("#restart").click(function() {
				if($(this).attr("class") === "btn btn_style"){
					self.openConsole();
					$("#buildConsole").attr('data-flag','false');
					self.onRestartEvent.dispatch(function(response){
						$('.alert_div').hide();
						self.logContent = $('#logContent').html();
					});					
				}
				
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
				self.resizeConsoleWindow();
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
			
			//To copy the console log content to the clip-board
			$('#buildCopyLog').unbind("click");
			$('#buildCopyLog').click(function() {
				commonVariables.navListener.copyToClipboard($('#logContent'));
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.build.js.Build;
});