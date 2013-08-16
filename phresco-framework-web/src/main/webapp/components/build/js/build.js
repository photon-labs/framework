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
		onipaDownloadEvent : null,
		onDeleteEvent : null,
		onDeployEvent : null,
		onBuildEvent : null,
		onProcessBuildEvent : null,
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
			
			self.registerHandlebars();
		},
		
		registerHandlebars : function () {
			Handlebars.registerHelper('devicedploy', function(paramVal) {
				if(paramVal === null){
					return '<img name="deployBuild" deviceDeploy="" src="themes/default/images/helios/deploy_icon.png" width="16" height="20" border="0" alt="">';
				}else{							
					return '<img name="deployBuild" deviceDeploy="' + paramVal + '" src="themes/default/images/helios/deploy_icon.png" width="16" height="20" border="0" alt="">';
				}		
			});
		},
		
		registerEvents : function(){
			var self = this;
			
			if(self.onProgressEvent === null){
				self.onProgressEvent = new signals.Signal();
			}	
			if(self.onDownloadEvent === null){
				self.onDownloadEvent = new signals.Signal();
			}
			if(self.onipaDownloadEvent === null){
				self.onipaDownloadEvent = new signals.Signal();
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
			if(self.onProcessBuildEvent === null){
				self.onProcessBuildEvent = new signals.Signal();
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
			self.onipaDownloadEvent.add(self.buildListener.ipadownload, self.buildListener);
			self.onDeleteEvent.add(self.buildListener.deleteBuild, self.buildListener);
			self.onDeployEvent.add(self.buildListener.deployBuild, self.buildListener);
			self.onBuildEvent.add(self.buildListener.buildProject, self.buildListener);
			self.onProcessBuildEvent.add(self.buildListener.processBuild, self.buildListener);
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
            commonVariables.navListener.showHideTechOptions();
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
			var finalHeight = windowHeight - deductionHeight - 6;
			$('.testSuiteTable').height(finalHeight);
		},
		
		runAgainSourceStatus : function(){
			var self = this;
			if($("input[name=build_runagsource]").is(':visible')){
				self.buildListener.getBuildInfo(self.buildListener.getRequestHeader("", '', 'serverstatus'), function(response) {
					self.changeBtnStatus(response);
				});
			}
		},
		
		dragDrop : function(){
			$('.connectedSortable').sortable({
				connectWith: '.connectedSortable',
				cancel: ".ui-state-disabled"
			});
		},
		
		showErrorPopUp : function(responseCode){
			var self = this;
			$(".content_end").show();
			$(".msgdisplay").removeClass("success").addClass("error");
			$(".error").attr('data-i18n', 'errorCodes.' + responseCode);
			self.renderlocales(commonVariables.contentPlaceholder);	
			$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
			setTimeout(function() {
				$(".content_end").hide();
			},2500);
		},
		
		sqlQueryParam : function(ststus, control, callback){
			var sqlParamVal = "";
			
			if(ststus){
				sqlParamVal = {};
				$.each(control, function(index, current){
					if(sqlParamVal.hasOwnProperty($(current).attr('dbName'))){
						sqlParamVal[$(current).attr('dbName')].push($(current).attr('path'));
					}else{
						sqlParamVal[$(current).attr('dbName')] = []
						sqlParamVal[$(current).attr('dbName')].push($(current).attr('path'));
					}
				});
				callback(JSON.stringify(sqlParamVal));
			} else {
				callback(sqlParamVal);
			}
		},
		
		changeBtnStatus : function(response){
			var show = "btn_style", hide = "btn_style_off";
		
			if(!response.data){
				show = "btn_style_off";
				hide = "btn_style";
			}
			
			$("input[name=build_runagsource]").removeClass(show);
			$("#stop").removeClass(hide);
			$("#restart").removeClass(hide);
			
			$("input[name=build_runagsource]").addClass(hide);
			$("#stop").addClass(show);
			$("#restart").addClass(show);
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
		
		callDepOpencc : function(current, divId){
			var self = this;
			
			if($('#deploye_' + divId).find('form[name=deployForm] ul').children().length > 0){
				self.dragDrop();
				self.opencc(current,'deploye_' + divId, '' , 50);
			}else{$('#deploye_' + divId).hide();}
		},
		
		clearLogContent : function(){
			$('#logContent').html('');
		},
		
		setConsoleScrollbar : function(bcheck){
			/* if(bcheck){
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
			} */
		},
		
		refreshContent : function(loadContent){
			var self = this;

			$('.alert_div').hide();
			if(loadContent){
				self.buildListener.getBuildInfo(self.buildListener.getRequestHeader("", '', 'getList'), function(response) {
					if(response !== undefined && response !== null && response.data !== null && response.data.length > 0){
						var tbody = "", buildObject = {}, userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
						buildObject.buildInfos = response.data;
						buildObject.userPermissions = userPermissions;
						
						if($("#buildRow").length < 1){
							var table = '<table class="table table-striped table_border table-bordered big" cellpadding="0" cellspacing="0" border="0" id="buildRow"><thead class="fixedHeader"><tr><th data-i18n="build.label.bNo"></th><th data-i18n="build.label.date"></th><th data-i18n="build.label.download"><th name="prcBuild" data-i18n="build.label.processBuild"></th></th><th name="buildDep" data-i18n="build.label.deploy"></th><th data-i18n="build.label.delete"></th></tr></thead><tbody class="scrollContent"></tbody></table>';
							$('.qual_unit_main').html(table);
						}

						$.each(buildObject.buildInfos, function(index, current){
							var cancreateIpa = "";
							var manageBuilds = "";
							var deviceDeploy = "";
							var deleteOpt = "";

							if(current.options !== null && current.options.canCreateIpa === true){
								cancreateIpa = '<a href="#"><img name="ipaDownload" src="themes/default/images/helios/ipa_icon.png" width="13" height="18" border="0" alt=""></a>';
							}

							if(current.options === null || current.options.deviceDeploy === false){
								deviceDeploy = '<div id="deploye_'+ current.buildNo +'" class="dyn_popup popup_bg" style="display:none;"><div id="bdeploy_'+ current.buildNo +'"><form name="deployForm"><ul class="row dynamicControls"></ul><input type="hidden" name="buildNumber" value="'+ current.buildNo +'" /></form><div class="flt_right"><input type="button" name="deploy" data-i18n="[value]build.label.deploy" class="btn btn_style dyn_popup_close"><input type="button" data-i18n="[value]build.label.close" class="btn btn_style dyn_popup_close"></div></div></div>';
							}else{
								deviceDeploy = '<div class="dyn_popup popup_bg" style="display:none;"></div>';
							}

							if(buildObject.userPermissions.manageBuilds !== null && buildObject.userPermissions.manageBuilds === true){
								manageBuilds = '<a href="#"><img name="deployBuild" deviceDeploy="' + (current.options === null ? "" : current.options.deviceDeploy) + '" src="themes/default/images/helios/deploy_icon.png" width="16" height="20" border="0" alt=""></a>' + deviceDeploy;
								
								deleteOpt ='<a name="delete_'+ current.buildNo +'" class="tooltiptop" title="" data-placement="top" data-toggle="tooltip" href="#" data-original-title="Delete Row"><img name="deleteBuild" src="themes/default/images/helios/delete_icon.png" width="16" height="20" border="0" alt=""></a><div id="delete_'+ current.buildNo +'" class="dyn_popup"><div data-i18n="build.label.deleteConform"></div><div><input type="button" name="buildDelete" data-i18n="[value]build.label.yes" class="btn btn_style dyn_popup_close" /><input type="button" data-i18n="[value]build.label.no" class="btn btn_style dyn_popup_close" /></div></div>';
								
							}else{
								manageBuilds = '<img src="themes/default/images/helios/deploy_icon_off.png" width="16" height="20" border="0" alt="">';
								deleteOpt ='<img src="themes/default/images/helios/delete_row_off.png" width="16" height="20" border="0" alt="">';
							}
						
							tbody += '<tr><td name="'+ current.buildNo +'">'+ current.buildNo +'</td><td>'+ current.timeStamp +'</td><td class="list_img"><a href="#"><img name="downloadBuild" src="themes/default/images/helios/download_icon.png" width="15" height="18" border="0" alt=""></a>'+ cancreateIpa +'</td><td name="prcBuild" class="list_img"><a href="#"><img name="procBuild" src="themes/default/images/helios/download_icon.png" width="15" height="18" border="0" alt=""></a><div id="prcBuild_'+ current.buildNo +'" class="dyn_popup popup_bg" style="display:none; width:30%;"><div id="prcBuild_'+ current.buildNo +'"><form name="prcBForm"><ul class="row dynamicControls"></ul><input type="hidden" name="buildNumber" value="'+ current.buildNo +'"/></form><div class="flt_right"><input class="btn btn_style" type="button" name="processBuild" data-i18n="[value]common.btn.ok"><input class="btn btn_style dyn_popup_close" type="button"  data-i18n="[value]common.btn.close"></div></div></div></td><td name="buildDep" class="list_img">'+ manageBuilds +'</td><td class="list_img">'+ deleteOpt +'</td></tr>';
						});
						
						$("#buildRow tbody").html(tbody);
						$('.dyn_popup').css('display', 'none');
						self.contentDivEvents();
						//self.setConsoleScrollbar(false);
						self.renderlocales(commonVariables.contentPlaceholder);
						commonVariables.navListener.showHideTechOptions();
						$(window).resize();
						self.closeConsole();
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
				whereToRender = $('#deploye_' + divId + ' ul'), deviceDeploy = $(this).attr("deviceDeploy");
				
				commonVariables.goal = "deploy";
				commonVariables.phase = "deploy";
				commonVariables.buildNo = divId;
				commonVariables.iphoneDeploy = $(this).attr("deviceDeploy");

				if(deviceDeploy === "true"){
					// call device deployment service
					
					self.dynamicpage.getHtml(whereToRender, this, '', function(retVal){
						self.clearLogContent();
						//self.setConsoleScrollbar(true);
						self.openConsole();
						$('input[name=buildDelete]').hide();
						self.onDeployEvent.dispatch("", function(response){
							$('input[name=buildDelete]').show();
							$('.progress_loading').css('display','none');
							//self.setConsoleScrollbar(false);
							if(response !== null && response.errorFound === true){
								$(current).closest('tr').find('form[name=deployForm]').show();
								self.showErrorPopUp(response.responseCode);
							}
						});
					});
				}else{
					if(whereToRender.children().length < 1){
						self.dynamicpage.getHtml(whereToRender, this, '', function(retVal){
							self.callDepOpencc(current, divId);
						});
					}else {
						self.callDepOpencc(current, divId);
					}
				}
			});
			
			//build deploy click event
			$("input[name=deploy]").unbind('click');
			$("input[name=deploy]").click(function(){
				var current = this, sqlParam = "", queryStr = "";;
				self.clearLogContent();
				//self.setConsoleScrollbar(true);
				self.openConsole();
				$('input[name=buildDelete]').hide();

				self.sqlQueryParam($(this).closest('tr').find('form[name=deployForm] #executeSql').is(':checked'), $(this).closest('tr').find('form[name=deployForm] ul[name=sortable2] li'), function(retVal){
					sqlParam = retVal;
				});
				
				queryStr = $(this).closest('tr').find('form[name=deployForm]').serialize().replace("=on", "=true");
				queryStr += '&fetchSql=' + ($.isEmptyObject(sqlParam) === true ? "" : sqlParam);
				
				self.onDeployEvent.dispatch(queryStr, function(response){
					$('input[name=buildDelete]').show();
					$('.progress_loading').css('display','none');
					//self.setConsoleScrollbar(false);
					if(response !== null && response.errorFound === true){
						$(current).closest('tr').find('form[name=deployForm]').show();
						self.showErrorPopUp(response.responseCode);
					}
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
					if(response.responseCode === "PHR700002"){
						$(current).closest('tr').remove();

						if($("#buildRow tbody tr").length < 1){
							$('.qual_unit_main').children().remove();
							$('.qual_unit_main').html('<div class="alert" style="text-align: center;" data-i18n="build.label.nodata"></div>');
							self.renderlocales(commonVariables.contentPlaceholder);
						}
						
						$(".content_end").show();
						$(".msgdisplay").removeClass("error").addClass("success");
						$(".success").attr('data-i18n', 'successCodes.' + response.responseCode);
						self.renderlocales(commonVariables.contentPlaceholder);	
						$(".success").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
						setTimeout(function() {
							$(".content_end").hide();
						},2500);
					}
				});
			});
			
			//Process build popup event
			$('table td img[name=procBuild]').unbind('click');
			$('table td img[name=procBuild]').click(function(){
				var current = this, divId = $(this).closest('tr').find('td:eq(0)').text(),
				whereToRender = $('#prcBuild_' + divId + ' ul');
				commonVariables.goal = "process-build";
				commonVariables.phase = "process-build";
				commonVariables.buildNo = divId;
				if(whereToRender.children().length < 1){
					self.dynamicpage.getHtml(whereToRender, this, '', function(retVal){
						if($('#prcBuild_' + divId).find('form[name=prcBForm] ul').children().length > 0){
							self.opencc(current,'prcBuild_' + divId, '' , 50);
						}else{$('#prcBuild_' + divId).hide();}
					});
				}else {
					if($('#prcBuild_' + divId).find('form[name=prcBForm] ul').children().length > 0){
						self.opencc(current,'prcBuild_' + divId, '' , 50);
					}else{$('#prcBuild_' + divId).hide();}
				}
				
			});
			
			//Process build click event
			$('input[name=processBuild]').unbind('click');
			$('input[name=processBuild]').click(function(){
				self.clearLogContent();
				//self.setConsoleScrollbar(true);
				self.openConsole();
				$(".dyn_popup").hide();
				self.onProcessBuildEvent.dispatch($(this).closest('tr').find('form[name=prcBForm]').serialize(), function(response){
				$('.progress_loading').css('display','none');
				if(response !== null && response.errorFound === true){
					$('.alert_div').hide();
					self.closeConsole();
					self.showErrorPopUp(response.responseCode);
				}else if(response !== null && response.errorFound === false){
					self.refreshContent(true);
				}
				});
			});
			
			//ipa download click event
			$('img[name=ipaDownload]').unbind('click');
			$('img[name=ipaDownload]').click(function(){
				self.onipaDownloadEvent.dispatch($(this).parent().parent().siblings(":first").text(), function(response){});
			});
			
			//Show tooltip event
			$(".tooltiptop").unbind("click");
			$(".tooltiptop").click(function() {
				self.opencc(this, $(this).attr('name'), '', 50);
			});
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
							connectWith: '.connectedSortable',
							cancel: ".ui-state-disabled"
						});
					});
					$("#buildConsole").attr('data-flag','false');
				}	
			});
			
			//run again source click event
			$("#runSource").click(function(){
				var sqlParam = "", queryStr = "";
				$(".dyn_popup").hide();
				self.clearLogContent();
				//self.setConsoleScrollbar(true);
				self.openConsole();

				self.sqlQueryParam($('form[name=runAgainstForm] #executeSql').is(':checked'), $('form[name=runAgainstForm] ul[name=sortable2] li'), function(retVal){
					sqlParam = retVal;
				});
				
				queryStr = $('form[name=runAgainstForm]').serialize().replace("=on", "=true");
				queryStr += '&fetchSql=' + ($.isEmptyObject(sqlParam) === true ? "" : sqlParam);
				
				self.onRASEvent.dispatch(queryStr, function(response){
					$('.progress_loading').css('display','none');
					//self.setConsoleScrollbar(false);
					
					if(response !== null && response.errorFound === true) {
						self.closeConsole();
						$("form[name=runAgainstForm] #build_runagsource").show();
						self.showErrorPopUp(response.responseCode);
					}else if(response !== null && response.errorFound === false) { 	
						self.runAgainSourceStatus();
					}
				});
			});
			
			//Run again source stop click event
			$("#stop").click(function() {
				if($(this).attr("class") === "btn btn_style"){
					self.clearLogContent();
					//self.setConsoleScrollbar(true);
					self.openConsole();
					$("#buildConsole").attr('data-flag','false');
					self.onStopEvent.dispatch(function(response){
						$('.progress_loading').css('display','none');
						//self.setConsoleScrollbar(false);
						self.runAgainSourceStatus();
					});					
				}
			});		
			
			//Run again source restart click event
			$("#restart").click(function() {
				if($(this).attr("class") === "btn btn_style"){
					self.clearLogContent();
					//self.setConsoleScrollbar(true);
					self.openConsole();
					$("#buildConsole").attr('data-flag','false');
					self.onRestartEvent.dispatch(function(response){
						$('.progress_loading').css('display','none');
						//self.setConsoleScrollbar(false);
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
					self.dynamicpage.getHtml(whereToRender, this, $(this).attr('name'), function(retVal){
						$("#buildNumber").bind('keypress',function(e) {
							if((e.which >= 48 && e.which <= 57) || (e.which === 8)){return true;}else {e.preventDefault();}
						});
					});
				}else{self.opencc(this,$(this).attr('name'));}
			});
			
			//build run click event
			$("#buildRun").click(function(){
				$('.alert_div').show();
				self.clearLogContent();
				//self.setConsoleScrollbar(true);
				self.openConsole();
				
				var sqlParam = "";
				queryStr = $('form[name=buildForm]').serialize().replace("=on", "=true");
				
				self.onBuildEvent.dispatch(queryStr, function(response){
					$('.progress_loading').css('display','none');
					if(response !== null && response.errorFound === true) {
						$('.alert_div').hide();
						self.closeConsole();
						$("form[name=buildForm] #build_genbuild").show();
						self.showErrorPopUp(response.responseCode);
					}else if(response !== null && response.errorFound === false) {
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
					//self.setConsoleScrollbar(false);
					self.runAgainSourceStatus();
				}
				self.onProgressEvent.dispatch(this);
			});

			//To open the build directory
			$('#openFolder').unbind('click');
			$("#openFolder").click(function() {
				var paramJson = {};
				paramJson.type = commonVariables.typeBuild;
				commonVariables.hideloading = true;
				commonVariables.navListener.openFolder(paramJson);
			});
			
			//To copy the path of build directory
			$('#copyPath').unbind('click');
			$("#copyPath").click(function() {
				var paramJson = {};
				paramJson.type = commonVariables.typeBuild;
				commonVariables.hideloading = true;
				commonVariables.navListener.copyPath(paramJson);
			});
			
			//To copy the console log content to the clip-board
			$('#buildCopyLog').unbind("click");
			$('#buildCopyLog').click(function() {
				commonVariables.hideloading = true;
				commonVariables.navListener.copyToClipboard($('#logContent'));
			});
			
			$(window).resize(function() {
				$(".dyn_popup").hide();
				
				var w1 = $(".scrollContent tr:nth-child(1) td:first-child").width();
				var w2 = $(".scrollContent tr:nth-child(1) td:nth-child(2)").width();
				var w3 = $(".scrollContent tr:nth-child(1) td:nth-child(3)").width();
				var w4 = $(".scrollContent tr:nth-child(1) td:nth-child(4)").width();
				var w5 = $(".scrollContent tr:nth-child(1) td:nth-child(5)").width();
				var w6 = $(".scrollContent tr:nth-child(1) td:nth-child(6)").width();
				
				$(".fixedHeader tr th:first-child").css("width",w1);
				$(".fixedHeader tr th:nth-child(2)").css("width",w2);
				$(".fixedHeader tr th:nth-child(3)").css("width",w3);
				$(".fixedHeader tr th:nth-child(4)").css("width",w4);
				$(".fixedHeader tr th:nth-child(5)").css("width",w5);
				$(".fixedHeader tr th:nth-child(6)").css("width",w6);
				
				//self.resizeConsoleWindow();
			});
			
			//set log console scroll content event
			//self.setConsoleScrollbar(false);
			self.tableScrollbar();
			
			//call content div events
			self.contentDivEvents();
			//self.customScroll($(".consolescrolldiv"));

			//Show tool tip
			$(".tooltiptop").tooltip();

			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.build.js.Build;
});