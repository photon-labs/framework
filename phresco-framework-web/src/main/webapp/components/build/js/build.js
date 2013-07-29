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
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(this, commonVariables.animation);
		},
		
		loadPageType : function(){
			//console.info('loadPageType',this);
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(this , false);
		},
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
			self.buildListener.getBuildInfo(self.buildListener.getRequestHeader("", '', 'getList'), function(response) {
			var buildObject = {};
			var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
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
			self.runAgainSourceStatus();
			self.loadPostContent();
			self.resizeConsoleWindow();
			self.closeConsole();
			$(window).resize();
			
			var windowHeight = $(document).height(), marginTop = '';
			marginTop = $('.testSuiteTable').css("margin-top");
			
			if(marginTop !== undefined){
				marginTop = marginTop.substring(0, marginTop.length - 2);
			}
			
			var footerHeight = $('#footer').height();
			var deductionHeight = Number(marginTop) + Number(footerHeight);
			var finalHeight = windowHeight - deductionHeight - 5;
			$('.testSuiteTable').height(finalHeight);
			commonVariables.navListener.showHideTechOptions();
		},
		
		runAgainSourceStatus : function(){
			var self = this;
			self.buildListener.getBuildInfo(self.buildListener.getRequestHeader("", '', 'serverstatus'), function(response) {
				self.changeBtnStatus(response , '');
			});
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
		
		refreshContent : function(loadContent){
			var self = this;

			$('.alert_div').hide();
			if(loadContent){
				self.buildListener.getBuildInfo(self.buildListener.getRequestHeader("", '', 'getList'), function(response) {
					if(response != undefined && response != null && response.data != null && response.data.length > 0){
						var tbody = "";
						
						if($("#buildRow").length < 1){
							var table = '<table class="table table-striped table_border table-bordered big" cellpadding="0" cellspacing="0" border="0" id="buildRow"><thead class="fixedHeader"><tr><th data-i18n="build.label.bNo"></th><th data-i18n="build.label.date"></th><th data-i18n="build.label.download"></th><th data-i18n="build.label.deploy"></th><th data-i18n="build.label.delete"></th></tr></thead><tbody class="scrollContent"></tbody></table>';
							$('.qual_unit_main').html(table);
						}

						$.each(response.data, function(index, current){
							tbody += '<tr><td name="'+ current.buildNo +'">'+ current.buildNo +'</td><td>'+ current.timeStamp +'</td><td><a href="#"><img name="downloadBuild" src="themes/default/images/helios/download_icon.png" width="15" height="18" border="0" alt=""></a></td><td><a href="#"><img name="deployBuild" src="themes/default/images/helios/deploy_icon.png" width="16" height="20" border="0" alt=""></a><div id="deploye_'+ current.buildNo +'" class="dyn_popup popup_bg" style="display:none;"><div id="bdeploy_'+ current.buildNo +'"><form name="deployForm"><ul class="row dynamicControls"></ul><input type="hidden" name="buildNumber" value="'+ current.buildNo +'" /></form><div class="flt_right"><input type="button" name="deploy" data-i18n="[value]build.label.deploy" class="btn btn_style dyn_popup_close"><input type="button"  data-i18n="[value]build.label.close" class="btn btn_style dyn_popup_close"></div></div></div></td><td><a name="delete_'+ current.buildNo +'" class="tooltiptop" title="" data-placement="top" data-toggle="tooltip" href="#" data-original-title="Delete Row"><img name="deleteBuild" src="themes/default/images/helios/delete_icon.png" width="16" height="20" border="0" alt=""></a><div id="delete_'+ current.buildNo +'" class="dyn_popup"><div data-i18n="build.label.deleteConform"></div><div><input type="button" name="buildDelete" data-i18n="[value]build.label.yes" class="btn btn_style dyn_popup_close"><input type="button"  data-i18n="[value]build.label.no" class="btn btn_style dyn_popup_close"></div></div></td></tr>';
						});
						
						$("#buildRow tbody").html(tbody);
						$('.dyn_popup').css('display', 'none');
						self.contentDivEvents();
						self.setConsoleScrollbar(false);
						$(window).resize();
						self.closeConsole();
						self.renderlocales(commonVariables.contentPlaceholder);
					}
				});
			}
		},
		
		contentDivEvents : function(){
			self = this;
			
			//Deploy build popup click event
			$("img[name=deployBuild]").unbind('click');
			$("img[name=deployBuild]").click(function(){
				var current = this, divId = $(this).closest('tr').find('td:eq(0)').text(),
				whereToRender = $('#deploye_' + divId + ' ul');
				
				if(whereToRender.children().length < 1){
					commonVariables.goal = "deploy";
					commonVariables.phase = "deploy";
					commonVariables.buildNo = divId;
					self.dynamicpage.getHtml(whereToRender, this, '', function(retVal){
						self.opencc(current,'deploye_' + divId, '' , 50);
					}); //'deploye_' + divId
					
				}else {self.opencc(this,'deploye_' + divId, '', 50);}
			});
			
			//build deploy click event
			$("input[name=deploy]").unbind('click');
			$("input[name=deploy]").click(function(){
			
				//$('.alert_div').show();
				self.clearLogContent();
				self.setConsoleScrollbar(true);
				self.openConsole();
				$('.progress_loading').css('display','block');
				$('input[name=buildDelete]').hide();
				self.onDeployEvent.dispatch($('form[name=deployForm]').serialize(), function(response){
					$('input[name=buildDelete]').show();
					$('.progress_loading').css('display','none');
					self.setConsoleScrollbar(false);
				});
			});
			
			//Build download click event
			$("img[name=downloadBuild]").unbind('click');
			$("img[name=downloadBuild]").click(function(){
				self.onDownloadEvent.dispatch($(this).parent().parent().siblings(":first").text(), function(response){});
			});
			
			//build delete click event
			$('input[name=buildDelete]').unbind('click');
			$('input[name=buildDelete]').click(function(){
				var current = this, divId = $(this).closest('tr').find('td:eq(0)').text();
				self.clearLogContent();
				self.onDeleteEvent.dispatch(divId, function(response){
					if(response.message == "Build deleted Successfully"){
						$(current).closest('tr').remove();

						if($("#buildRow tbody tr").length < 1){
							$('.qual_unit_main').children().remove();
							$('.qual_unit_main').html('<div class="alert" style="text-align: center;" data-i18n="build.label.nodata"></div>');
							self.renderlocales(commonVariables.contentPlaceholder);
						}
					}
				});
			});
			
			//Show tooltip event
			$(".tooltiptop").unbind("click");
			$(".tooltiptop").click(function() {
				self.opencc(this, $(this).attr('name'), '', 50);
			});
			
			$("#buildRow .scrollContent").mCustomScrollbar("destroy");
			$("#buildRow .scrollContent").mCustomScrollbar({
				autoHideScrollbar:true,
				advanced:{ updateOnContentResize: true},
				theme:"light-thin"
			});
		},
		
		clearLogContent : function(){
			$('#logContent').html('');
		},
		
		setConsoleScrollbar : function(bcheck){
			if(bcheck){
				$("#build_progress .scrollContent").mCustomScrollbar("destroy");
				$("#build_progress .scrollContent").mCustomScrollbar({
					autoHideScrollbar: false,
					scrollInertia: 1000,
					theme:"light-thin",
					advanced:{ updateOnContentResize: true},
					callbacks:{
						onScrollStart:function(){
							$("#build_progress .scrollContent").mCustomScrollbar("scrollTo","bottom");
						}
					}
				});
			}else{
				$("#build_progress .scrollContent").mCustomScrollbar("destroy");
				$("#build_progress .scrollContent").mCustomScrollbar({
					autoHideScrollbar:true,
					scrollInertia: 200,
					theme:"light-thin",
					advanced:{ updateOnContentResize: true}
				});
			}
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			
			//Run again source popup click event
			$("input[name=build_runagsource]").unbind("click");
			$("input[name=build_runagsource]").click(function() {
				if($(this).attr("class") === "btn btn_style"){
					commonVariables.goal = "start";
					commonVariables.phase = "run-against-source";
					self.dynamicpage.getHtml($('#build_runagsource ul'), this, $(this).attr('name'), function(retVal){
						$('.connectedSortable').sortable({
							connectWith: '.connectedSortable'
						});
					});
					$("#buildConsole").attr('data-flag','false');
				}	
			});
			
			//run again source click event
			$("#runSource").click(function(){
				$(".dyn_popup").hide();
				self.clearLogContent();
				self.setConsoleScrollbar(true);
				self.openConsole();
				$('.progress_loading').css('display','block');
				self.onRASEvent.dispatch($('form[name=runAgainstForm]').serialize(), function(response){
					$('.progress_loading').css('display','none');
					self.setConsoleScrollbar(false);
					self.runAgainSourceStatus();
				});
			});
			
			//Run again source stop click event
			$("#stop").click(function() {
				if($(this).attr("class") === "btn btn_style"){
					self.clearLogContent();
					self.setConsoleScrollbar(true);
					self.openConsole();
					$("#buildConsole").attr('data-flag','false');
					$('.progress_loading').css('display','block');
					self.onStopEvent.dispatch(function(response){
						$('.progress_loading').css('display','none');
						self.setConsoleScrollbar(false);
						self.runAgainSourceStatus();
					});					
				}
			});		
			
			//Run again source restart click event
			$("#restart").click(function() {
				if($(this).attr("class") === "btn btn_style"){
					self.clearLogContent();
					self.setConsoleScrollbar(true);
					self.openConsole();
					$("#buildConsole").attr('data-flag','false');
					$('.progress_loading').css('display','block');
					self.onRestartEvent.dispatch(function(response){
						$('.progress_loading').css('display','none');
						self.setConsoleScrollbar(false);
						self.runAgainSourceStatus();						
					});
				}
			});
			
			//Generate build popup click event
			$("input[name=build_genbuild]").unbind("click");
			$("input[name=build_genbuild]").click(function() {
                var whereToRender = $('#build_genbuild ul');
				
				if(whereToRender.children().length < 1){
					commonVariables.goal = "package";
					commonVariables.phase = "package";
					self.dynamicpage.getHtml(whereToRender, this, $(this).attr('name'), function(retVal){});
				}else{self.opencc(this,$(this).attr('name'));}
			});
			
			//build run click event
			$("#buildRun").click(function(){
				$('.alert_div').show();
				self.clearLogContent();
				self.setConsoleScrollbar(true);
				self.openConsole();
				$('.progress_loading').css('display','block');
				self.onBuildEvent.dispatch($('form[name=buildForm]').serialize(), function(response){
					$('.progress_loading').css('display','none');
					if(response != null && response.errorFound == true){
						$('.alert_div').hide();
						self.closeConsole();
						$("form[name=buildForm] #build_genbuild").show();
						$(".blinkmsg").removeClass("popsuccess").addClass("poperror");
							self.effectFadeOut('poperror', response.configErrorMsg[0]);
					}else if(response != null && response.errorFound == false){
						self.refreshContent(true);
					}
				});
			});
			
			//Minifier popup click event
			$("input[name=build_minifier]").unbind("click");
			$("input[name=build_minifier]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			//Log console div click event
			$("#buildConsole").click(function() {
				if($('#logContent').text().match("INFO: Starting Coyote HTTP/1.1")){
					$("input[name=build_runagsource]").removeClass('btn_style');
					$("input[name=build_runagsource]").addClass('btn_style_off');
					$('.progress_loading').css('display','none');
					$("#stop").removeClass('btn_style_off');
					$("#stop").addClass('btn_style');
					$("#restart").removeClass('btn_style_off');
					$("#restart").addClass('btn_style');
					self.setConsoleScrollbar(false);
					self.runAgainSourceStatus();
				}
				self.onProgressEvent.dispatch(this);
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
			
			$(window).resize(function() {
				$(".dyn_popup").hide();
				
				var w1 = $(".scrollContent tr:nth-child(1) td:first-child").width();
				var w2 = $(".scrollContent tr:nth-child(1) td:nth-child(2)").width();
				var w3 = $(".scrollContent tr:nth-child(1) td:nth-child(3)").width();
				var w4 = $(".scrollContent tr:nth-child(1) td:nth-child(4)").width();
				var w5 = $(".scrollContent tr:nth-child(1) td:nth-child(5)").width();
				
				$(".fixedHeader tr th:first-child").css("width",w1);
				$(".fixedHeader tr th:nth-child(2)").css("width",w2);
				$(".fixedHeader tr th:nth-child(3)").css("width",w3);
				$(".fixedHeader tr th:nth-child(4)").css("width",w4);
				$(".fixedHeader tr th:nth-child(5)").css("width",w5);
				
				self.resizeConsoleWindow();
			});
			
			//set log console scroll content event
			self.setConsoleScrollbar(false);
			
			//call content div events
			self.contentDivEvents();

			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.build.js.Build;
});