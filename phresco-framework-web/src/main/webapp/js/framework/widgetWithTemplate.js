define(["framework/widget", "framework/templateProvider"], function() {

	Clazz.WidgetWithTemplate = Clazz.extend(Clazz.Widget, {
			// the url of the template
			templateUrl: null,
			localConfig: null,
			
			// default the provider to handlebar
			templateProvider: TemplateProvider !== undefined ? TemplateProvider.HANDLE_BAR : '',
			
			// data for data binding process. If you don't override preRender(), this is the default
			// data that it will use
			data : {},
			
			// the last result of the element after the element has been bounded and rendered
			element: null,
			
			// default container if render place is not specified
			// for example: <app:account-list>
			defaultContainer : null,
			
			showingDatePicker : false,
			
			doMore: function(element) {}, 
			
			/***
			 * Called after the renderTemplate() and bindUI() completes. 
			 * Override and add any preRender functionality here
			 *
			 * @element: Element as the result of the template + data binding
			 */
			postRender : function(element) {
			
			if (commonVariables.userInfo.role.name === "ROLE_ADMIN"){
				$('.userpermissions').addclass('show'); 
				}
			},
			 
			/****
			 * Called before the render() function, override and add any preRender functionality
			 * here
			 * 
			 * @whereToRender The node where we are going to render the UI
			 * @renderFunction The renderTemplate function by default, use to render the UI and call bindUI afterwards
			 */
			preRender: function(whereToRender, renderFunction) {
				// default implementation just call renderFunction
				renderFunction(this.data, whereToRender);
			},
			
			/***
			 * A call to render the UI fragment implemented in renderUI method
			 * @param whereToRender A placeholder to hold the fragment, this would typically
			 * be a div tag or other valid HTML element. 
			 * 
			 * @whereToRender The node where we are going to render the UI
			 */
			render : function(whereToRender) {
				if(whereToRender === null) {
					whereToRender = $(this.defaultContainer);
				}
				
				this.preRender(whereToRender, $.proxy(this.renderTemplate, this));
			},
			
			/***
			 * Render using the template url specified with the template engine
			 * 
			 * @data The data that is used for binding with the template
			 * @whereToRender The node where we are going to render the UI
			 */
			renderTemplate: function(data, whereToRender) {
				if(this.templateUrl !== null) {
					var self = this;
					var templateProvider = new Clazz.TemplateProvider({ templateEngine : this.templateProvider});
					templateProvider.merge(this.templateUrl, data, function(element) {
						$(whereToRender).html(element);
						self.bindUI();
						self.postRender(element);
						self.renderlocales(whereToRender);
						self.element = element;
						self.doMore(element);
					});
				}
			},
		 
			/***
			 *  Method to get the HTML String representation, can be used to include
			 *  the part of the Widget to other template
			 *  
			 *  @return The HTML string from the result of data binding with the template
			 */
			getHtmlString: function() {
				if(this.element !== null) {
					return $(this.element)[0].outerHTML;
				}
			},
			
			/**
			 * Method to initiate any event handling
			 * called after the renderTemplate() completes
			 */
			handleEvent : function(element){
				this.bindUI();
				this.postRender(element);
				this.element = element;
			},
			
			/**
			*	Method to refresh to load the locale values
			*/
			renderlocales : function(whereToRender){
				var self=this;
				$.i18n.init({
					lng: 'en',
					fallbackLng: 'en',
					ns: { namespaces: ['framework'], defaultNs: 'framework'},
					resGetPath: commonVariables.globalconfig.environments.locales,
					useLocalStorage: true,   // To avoid loading framework.json multiple times
					debug: false
				}, function() {
					$(whereToRender).i18n();
				});
			},
			
			windowResize : function(){
				var resultvalue = 0;
				$('.content_main').prevAll().each(function() {
					resultvalue = resultvalue + $(this).height(); 
				});
				
				resultvalue = resultvalue + $('.footer_section').height() + 65;
				$('.content_main .scrollContent').height($(window).height() - (resultvalue + 155));
			},
			
			opencc : function(ee, placeId, currentPrjName) {
				var self=this;
				$(".dyn_popup").hide();
				
				$('.features_content_main').removeClass('z_index');
				
				var clicked = $(ee);
				var target = $("#" + placeId);
				var t= clicked.offset().top + 33;
				var halfheight= window.innerHeight/2;
				var halfwidth= window.innerWidth/2;
				$(target).attr('currentPrjName',currentPrjName);
				
				if (clicked.offset().top < halfheight && clicked.offset().left < halfwidth) {
					$(target).css({"left":clicked.offset().left ,"margin-top":10,"right": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopright').removeClass('speakstylebottomright').removeClass('speakstylebottomleft').addClass('speakstyletopleft').addClass('dyn_popup');
				} else if (clicked.offset().top < halfheight && clicked.offset().left > halfwidth){
					var d= ($(window).width() - (clicked.offset().left + clicked.outerWidth()));
					$(target).css({"right":d ,"margin-top":10,"left": "auto","top": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstylebottomright').removeClass('speakstylebottomleft').addClass('speakstyletopright').addClass('dyn_popup');
				} else if (clicked.offset().top > halfheight && clicked.offset().left < halfwidth){
					var BottomHeight = clicked.position().top - (target.height() + 33 );
					$(target).css({"left": clicked.offset().left,"top": BottomHeight ,"right": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstylebottomright').removeClass('speakstyletopright').addClass('speakstylebottomleft').addClass('dyn_popup');	
				} else if (clicked.offset().top > halfheight && clicked.offset().left > halfwidth){
					var d= ($(window).width() - (clicked.offset().left + clicked.outerWidth()));
					var BottomHeight = clicked.position().top - (target.height() + 33 );
					$(target).css({"right":d ,"top":BottomHeight,"left": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstyletopright').removeClass('speakstylebottomleft').addClass('speakstylebottomright').addClass('dyn_popup');	
				} 

				self.closeAll(placeId);
			},
			
			openccci : function(ee, placeId, currentPrjName) {
				var self=this;
				$(".dyn_popup").hide();
				
				$('.features_content_main').removeClass('z_index');
				
				var clicked = $(ee);
				var target = $("#" + placeId);
				var t= clicked.offset().top + 33;
				var halfheight= window.innerHeight/2;
				var halfwidth= window.innerWidth/2;
				$(target).attr('currentPrjName',currentPrjName);
				
				if (clicked.offset().top < halfheight && clicked.offset().left < halfwidth) {
					$(target).css({"left":clicked.offset().left ,"margin-top":10,"right": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopright').removeClass('speakstylebottomright').removeClass('speakstylebottomleft').addClass('speakstyletopleft').addClass('dyn_popup');
				} else if (clicked.offset().top < halfheight && clicked.offset().left > halfwidth){
					var d= ($(window).width() - (clicked.offset().left + clicked.outerWidth()));
					var BottomHeight = clicked.position().top + clicked.height() + 57;
					$(target).css({"right":d,"left": "auto","top": BottomHeight});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstylebottomright').removeClass('speakstylebottomleft').addClass('speakstyletopright').addClass('dyn_popup');
				} else if (clicked.offset().top > halfheight && clicked.offset().left < halfwidth){
					var BottomHeight = clicked.position().top - (target.height() + 33 );
					$(target).css({"left": clicked.offset().left,"top": BottomHeight ,"right": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstylebottomright').removeClass('speakstyletopright').addClass('speakstylebottomleft').addClass('dyn_popup');	
				} else if (clicked.offset().top > halfheight && clicked.offset().left > halfwidth){
					var d= ($(window).width() - (clicked.offset().left + clicked.outerWidth()));
					var BottomHeight = clicked.position().top + clicked.height() + 57;
					$(target).css({"right":d,"left": "auto","top": BottomHeight});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstylebottomright').removeClass('speakstylebottomleft').addClass('speakstyletopright').addClass('dyn_popup');	
				} 

				self.closeAll(placeId);
			},
			
			openccpl : function(ee, placeId, currentPrjName) {
				var self=this;
				$(".dyn_popup").hide();
				
				$('.features_content_main').removeClass('z_index');
				
				var clicked = $(ee);
				var target = $("#" + placeId);
				var t= clicked.offset().top + 33;
				var halfheight= window.innerHeight/2;
				var halfwidth= window.innerWidth/2;
				$(target).attr('currentPrjName',currentPrjName);
				
				if (clicked.offset().top < halfheight && clicked.offset().left < halfwidth) {
					$(target).css({"left":clicked.offset().left ,"margin-top":10,"right": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopright').removeClass('speakstylebottomright').removeClass('speakstylebottomleft').addClass('speakstyletopleft').addClass('dyn_popup');
				} else if (clicked.offset().top < halfheight && clicked.offset().left > halfwidth){
					var d= ($(window).width() - (clicked.offset().left + clicked.outerWidth())) - 18;
					$(target).css({"right":d ,"margin-top":10,"left": "auto","top": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstylebottomright').removeClass('speakstylebottomleft').addClass('speakstyletopright').addClass('dyn_popup');
				} else if (clicked.offset().top > halfheight && clicked.offset().left < halfwidth){
					var BottomHeight = clicked.position().top - (target.height() + 33 );
					$(target).css({"left": clicked.offset().left,"top": BottomHeight ,"right": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstylebottomright').removeClass('speakstyletopright').addClass('speakstylebottomleft').addClass('dyn_popup');	
				} else if (clicked.offset().top > halfheight && clicked.offset().left > halfwidth){
					var d= ($(window).width() - (clicked.offset().left + clicked.outerWidth())) - 15;
					var BottomHeight = clicked.position().top - (target.height() + 28 );
					$(target).css({"right":d ,"top":BottomHeight,"left": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstyletopright').removeClass('speakstylebottomleft').addClass('speakstylebottomright').addClass('dyn_popup');	
				} 

				self.closeAll(placeId);
			},
			
			opencctime : function(ee, placeId) {
				var self=this;
				$('.content_main').addClass('z_index_ci');
				
				var clicked = $(ee);
				var target = $("#" + placeId);
				var twowidth = window.innerWidth/1.5;
			
				if (clicked.offset().left < twowidth) {	
					$(target).toggle();
					var a = target.height()/2;
					var b = clicked.height()/2;
					var t=clicked.offset().top + (b+12) - (a+12) ;
					var l=clicked.offset().left + clicked.width()+ 4;
					$(target).offset({
						top: t,
						left: l
					});
					
					$(target).addClass('speakstyleleft').removeClass('speakstyleright');
				} else {
					$(target).toggle();
					var t=clicked.offset().top - target.height()/2;
					var l=clicked.offset().left - (target.width()+clicked.width()+15);
					$(target).offset({
						top: t,
						left: l
					});
					
					$(target).addClass('speakstyleright').removeClass('speakstyleleft');
			
				}
				
				self.closeAll(placeId);
			},

			openccwait : function(ee, placeId) {
				var self=this;
				$('.content_main').addClass('z_index_ci');
				
				var clicked = $(ee);
				var target = $("#" + placeId);
				var twowidth = window.innerWidth/1.5;
			
				if (clicked.offset().left < twowidth) {	
					$(target).toggle();
					var a = target.height()/2;
					var b = clicked.height()/2;
					var t=clicked.offset().top + (b+12) - (a+20) ;
					var l=clicked.offset().left + clicked.width()+ 5;
					$(target).offset({
						top: t,
						left: l
					});
					
					$(target).addClass('speakstyleleft').removeClass('speakstyleright');
				} else {
					$(target).toggle();
					var t=clicked.offset().top - target.height()/2;
					var l=clicked.offset().left - (target.width()+clicked.width()+15);
					$(target).offset({
						top: t,
						left: l
					});
					
					$(target).addClass('speakstyleright').removeClass('speakstyleleft');
			
				}
				
				self.closeAll(placeId);
			},
			
			closeAll : function(placeId) {

				$('.dlt').click( function() {
					$("#" + placeId).hide();
				});	

				$(document).keyup(function(e) {
					if(e.which === 27){
						$("#" + placeId).hide();
						$(".dyn_popup_text").val("");
					}
				});
				
				$('.dyn_popup_close').click( function() {
					$("#" + placeId).hide();
					$(".dyn_popup_text").val("");
					$("input[name='envrName']").attr('placeholder','Environment Name');
					$(".repo_error1").hide();
					$(".repo_error2").hide();
					$(".repo_error3").hide();
				});
					
			},

			setDateTimePicker : function(){
				var nowTemp = new Date();
				var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
				var flag=1;
				$(".datepicker").remove();
				var checkin = $('#startDate').datepicker({
					onRender: function(date) {return date.valueOf();}
				}).on('changeDate', function(ev) {
				   if ((ev.date.valueOf() > checkout.date.valueOf())||(flag === 1)) {
						var newDate = new Date(ev.date);
						newDate.setDate(newDate.getDate() + 1);
						checkout.setValue(newDate);
					}
					checkin.hide();
					$('#endDate')[0].focus();
				}).data('datepicker');
				
				var checkout = $('#endDate').datepicker({
					onRender: function(date) {return date.valueOf() <= checkin.date.valueOf() ? '' : '';}
				}).on('changeDate', function(ev) {checkout.hide();}).data('datepicker');
				$("#startDate").bind('keydown', function(e) { 
  					var keyCode = e.keyCode || e.which; 
 			 		if (keyCode === 9){
						checkin.hide();
					}	
				});

				$("#startDate").click(function() { 
  			 		checkin.show();
				});

				$("#endDate").bind('keydown', function(e) { 
  					var keyCode = e.keyCode || e.which; 
 			 		if (keyCode === 9){
						checkout.hide();
					}	
				});
				
				$("#endDate").click(function() { 
  			 		checkout.show();
				});
				
			},
			
			getCustomer : function() {
				var selectedcustomer = $("#selectedCustomer").text();
				var customerId = "";
				$.each($("#customer").children(), function(index, value){
					var customerText = $(value).children().text();
					if(customerText === selectedcustomer){
						customerId = $(value).children().attr('id');
					}
				});
				
				return customerId;
			},
			
			// popUp for success event to close auto
			successMsgPopUp : function(msg) {
				$('#myModal').removeClass('errorpop');
				$('#myModal').css({top:'50%',left:'50%',margin:'-'+($('#myModal').height() / 2)+'px 0 0 -'+($('#myModal').width() / 2)+'px'});
				$('#myModal').css('z-index', '1051');
				$('#myModal').modal('show');
				$('#myModal').addClass('alert-success').removeClass('alert-error');
				$('#myModal').html(msg);
				setTimeout("$('#myModal').modal('hide')", 3000);
			},

			// popUp for success event to close auto
			failureMsgPopUp : function(msg) {
				$('#myModal').removeClass('successpop');
				$('#myModal').css({top:'50%',left:'50%',margin:'-'+($('#myModal').height() / 2)+'px 0 0 -'+($('#myModal').width() / 2)+'px'});
				$('#myModal').css('z-index', '1051');
				$('#myModal').modal('show');
				$('#myModal').addClass('alert-error').removeClass('alert-success');
				$('#myModal').html(msg);
				setTimeout("$('#myModal').modal('hide')", 3000);
			},

			
			// popUp onclick to open with dynamic content
			showPopUp : function(popUpheader,content) {
				$('#myModal').modal('show');
				$('#myModalLabel').html(popUpheader);
				$('.modal-body').html(content);
			},
			
			multiselect : function() {
				$('.selectpicker').selectpicker();
			},

			isBlank : function (obj) {
				if (obj !== null && obj !== undefined && obj !== '') {
					return false;
				}
				return true;
			},
		
			showHideConsole : function() {
				var self = this;
				var check = $('#consoleImg').attr('data-flag');
				if (check === "true") {
					self.openConsole();
				} else {
					self.closeConsole();
				}
			},
			// Open console window - Added by sudhakar
			openConsole : function() {
				$('.testSuiteTable').append('<div class="mask"></div>');
				$('.mask').show();
				$('.unit_close').css("z-index", 1001);
				$('.unit_progress').css("z-index", 1001);
				$('.unit_close').css("height", 0);
				var value = $('.unit_info').width();
				var value1 = $('.unit_progress').width();
				$('.unit_info').animate({left: -value},500);
				$('.unit_progress').animate({right: '10px'},500);
				$('.unit_close').animate({right: value1+10},500);
				$('.unit_info table').removeClass("big").addClass("small");
				$('#consoleImg').attr('data-flag','false');
				
				var height = $(window).height();
				var resultvalue = 0;
				$('.mainContent').prevAll().each(function() {
					var rv = $(this).height();
					resultvalue = resultvalue + rv; 
				});
				var footervalue = $('.footer_section').height();
				resultvalue = resultvalue + footervalue + 200;
				finalHeight = height - resultvalue;
				$(".unit_progress").css("height", finalHeight + 10);
				$('.unit_progress').find('.scrollContent').css("height", finalHeight - 20);
			},
			// Close console window - Added by sudhakar
			closeConsole : function() {
				var value = $('.unit_info').width();
				var value1 = $('.unit_progress').width();
				$('.unit_info').animate({left: '20'},500);
				$('.unit_progress').animate({right: -value1},500);
				$('.unit_close').animate({right: '0px'},500);
				$('.unit_info table').removeClass("small").addClass("big");
				$('#consoleImg').attr('data-flag','true');
				$('.mask').remove();
			},
			
			// Resize window - Added by sudhakar
			resizeConsoleWindow : function() {
				var twowidth = window.innerWidth-60;
				var progwidth = window.innerWidth/2;
				var onewidth = window.innerWidth - (twowidth+70);
				$('.unit_info').css("width",twowidth);
				$('.unit_progress').css("width",progwidth);
				$('.unit_progress').css("right",-twowidth);
				$('.unit_close').css("right",0);
			},
						
			checkBoxEvent: function (parentObj, childCheckBoxClass, buttonObj) {
				$('.' + childCheckBoxClass).bind('click', function(){
					var checkedLength = $('.' + childCheckBoxClass + ':checked').size();
					var totalCheckBoxes = $('.' + childCheckBoxClass).size();
					if (totalCheckBoxes === checkedLength) {
						$(parentObj).prop("checked", true);
					} else {
						$(parentObj).prop("checked", false);
					}
					
					if (checkedLength > 0) {
						buttonObj.prop("disabled", false);
						buttonObj.addClass("btn_style");
					} else {
						buttonObj.prop("disabled", true);
						buttonObj.removeClass("btn_style");
					}
				});
			},
			
			checkAllEvent: function (parentObj, childObj, buttonObj) {
				$(parentObj).bind('click', function(){
					if ($(parentObj).is(':checked')) {
						$(childObj).prop("checked", true);
						buttonObj.prop("disabled", false);
						buttonObj.addClass("btn_style");
					} else {
						$(childObj).prop("checked", false);
						buttonObj.prop("disabled", true);
						buttonObj.removeClass("btn_style");
					}
				});
			},
			
			showDynamicPopupLoading : function () {
				$('.dynamicPopupLoading').show();
			},
			
			hideDynamicPopupLoading : function () {
				$('.dynamicPopupLoading').hide();
			},
		}
	);

	return Clazz.WidgetWithTemplate;
});