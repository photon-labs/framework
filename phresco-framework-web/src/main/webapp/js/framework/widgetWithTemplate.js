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

			renderFnc : null,
			
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

			handleResponse : function(response) {

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
				this.renderFnc = $.proxy(this.renderTemplate, this);
				this.preRender(whereToRender, this.renderFnc);
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
			 * To apply the custom scrollbar style to the table
			 */
			tableScrollbar : function() {
				if ($(".fixed-table-container-inner") !== undefined && $(".fixed-table-container-inner").length > 0) {
					this.customScroll($(".fixed-table-container-inner"));
				}
			},
			
			customScroll : function(divId) {
				var self=this;
				self.fixScrollHeight(divId);
				if ( $('.header-background').offset() !== undefined) {
					$('.th-inner').css('top', $('.header-background').offset().top+'px');
				}
				$(window).resize(function() {
					self.fixScrollHeight(divId);
				});
				divId.scrollbars();

			},
			
			fixScrollHeight : function(divId) {

				if ($(divId).find('.fixed-table-container-inner').offset() !== undefined) {
					var height = $(window).height() - $('.fixed-table-container-inner').offset().top - 40;
					$('.fixed-table-container, .fixed-table-container-inner, .scroll-bar').css('height', height);
				} else if(divId.length > 0 && !divId.hasClass('popup_scroll')) {
					var height = $(window).height() - $(divId).offset().top - 40;
					if ($('.content_end').length > 0){
						height = height - $('.content_end').height();
					}
					$(divId).css('height', height);
					$('.scroll-bar').css('height',  height);
				} else {
					$(divId).find('.scroll-bar').css('height', divId.css('height'));
						/*if ($('.consolescrolldiv').offset() !== undefined) {
							var scrollTo = $('.consolescrolldiv').find('.scroll-content').height() + $('.consolescrolldiv').offset().top; 
							$('.consolescrolldiv').animate({scrollTop: scrollTo});
						}*/
					}

				if ($(divId).hasClass('cus_themes')) {
					$(divId).find('.scroll-bar').css('height', '200px');
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
				
				resultvalue = resultvalue + $('.footer_section').height() + 40;
				$('.content_main .scrollContent').height($(window).height() - (resultvalue + 155));
			},
			
			popupforDesc : function(e,place) {
				var self=this;
				var clicked = $(e);
				var target = $("#" + place);
				var twowidth = window.innerWidth/1.5;;

				if (clicked.offset().left < twowidth) {	
					$(target).toggle();
					var t=clicked.offset().top - target.height()/2 + 10;
					var l=clicked.offset().left + clicked.width()+ 15;
					$(target).offset({
						top: t,
						left: l
					});
					
					$(target).addClass('speakstyleleft').removeClass('speakstyleright');
					$(".header_section").css("z-index","4");
					$(".content_title").css("z-index","4");
					$(".optiontitle").css("z-index","0");
				}
				else {
					$(target).toggle();
					var t=clicked.offset().top - target.height()/2 + 10;
					var l=clicked.offset().left - (target.width()+15);
					$(target).offset({
						top: t,
						left: l
					});
					
					$(target).addClass('speakstyleright').removeClass('speakstyleleft');
					$(".header_section").css("z-index","4");
					$(".content_title").css("z-index","4");
					$(".optiontitle").css("z-index","0");

				}
				
				$(document).keyup(function(e) {
					if(e.which === 27){
						$("#" + place).hide();
						$(".header_section").css("z-index","7");
						$(".content_title").css("z-index","6");
						$(".optiontitle").css("z-index","1");
					}
				});
				
				$('.dyn_popup_close').click( function() {
					$("#" + place).hide();
					$(".header_section").css("z-index","7");
					$(".content_title").css("z-index","6");
					$(".optiontitle").css("z-index","1");
				});
				self.customScroll(target.find('.popup_scroll'));
			},
			
			popupforTree : function(e,place) {
				var clicked = $(e), self=this;
				var target = $("#" + place);
				var twowidth = window.innerWidth/1.5;;

				if (clicked.offset().left < twowidth) {
					$(target).toggle();
					var t=clicked.offset().top - target.height()/2 + 10;
					var l=clicked.offset().left + clicked.width()+ 15;
					$(target).offset({
						top: t,
						left: l
					});

					$(target).addClass('speakstyleleft').removeClass('speakstyleright');
					$(".header_section").css("z-index","4");
					$(".content_title").css("z-index","4");
					$(".optiontitle").css("z-index","0");
				}
				else {
					$(target).toggle();
					var t=clicked.offset().top - 130;
					var l=clicked.offset().left - 340;
					$(target).offset({
						top: t,
						left: l
					});

					$(target).addClass('speakstyleright').removeClass('speakstyleleft');
					$(target).css({"height":"272px" ,"width": "309px"});
					$(".header_section").css("z-index","4");
					$(".content_title").css("z-index","4");
					$(".optiontitle").css("z-index","0");

				}

				$(document).keyup(function(e) {
					if(e.which === 27){
						$("#" + place).hide();
						self.closeTreePopup();
					}
				});

				$('.dyn_popup_close').click( function() {
					$("#" + place).hide();
					self.closeTreePopup();
				});
				self.customScroll(target.find('.popup_scroll'));
			},
			
			closeTreePopup : function() {
				var self=this;
				$(".header_section").css("z-index","7");
				$(".content_title").css("z-index","6");
				$(".optiontitle").css("z-index","1");
			},
			
			opencc : function(ee, placeId, currentPrjName, adjestVal) {
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
					var mtop = 10;
					if(adjestVal !== undefined && adjestVal !== null && adjestVal !== "upgrade"){
						d = d -50;
					} else if (adjestVal === "upgrade") {
						d= 67;
						mtop = 25;
					}
						
					$(target).css({"right":d ,"margin-top":mtop,"left": "auto","top": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstylebottomright').removeClass('speakstylebottomleft').addClass('speakstyletopright').addClass('dyn_popup');
				} else if (clicked.offset().top > halfheight && clicked.offset().left < halfwidth){
					var BottomHeight = clicked.position().top - (target.height() + 33 );
					$(target).css({"left": clicked.offset().left,"top": BottomHeight ,"right": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstylebottomright').removeClass('speakstyletopright').addClass('speakstylebottomleft').addClass('dyn_popup');	
				} else if (clicked.offset().top > halfheight && clicked.offset().left > halfwidth){
					var d= ($(window).width() - (clicked.offset().left + clicked.outerWidth()));
					
					if(adjestVal != undefined && adjestVal != null){
						d = d -50;
					}
					
					var BottomHeight = clicked.position().top - (target.height() + 33 );
					$(target).css({"right":d ,"top":BottomHeight,"left": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstyletopright').removeClass('speakstylebottomleft').addClass('speakstylebottomright').addClass('dyn_popup');	
				} 
				
				self.customScroll(target.find('.popup_scroll'));
				self.closeAll(placeId);
			},
			
			openccci : function(ee, placeId, paramVal) {
				var self=this;
				$(".dyn_popup").hide();
				
				$('.features_content_main').removeClass('z_index');
				
				var clicked = $(ee);
				var target = $("#" + placeId);
				var t= clicked.offset().top + 33;
				var halfheight= window.innerHeight/2;
				var halfwidth= window.innerWidth/2;
				
				if (clicked.offset().top < halfheight && clicked.offset().left < halfwidth) {
					$(target).css({"left":clicked.offset().left ,"margin-top":10,"right": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopright').removeClass('speakstylebottomright').removeClass('speakstylebottomleft').addClass('speakstyletopleft').addClass('dyn_popup');
				} else if (clicked.offset().top < halfheight && clicked.offset().left > halfwidth){
					var d= ($(window).width() - (clicked.offset().left + clicked.outerWidth()))-11;
					var BottomHeight = clicked.position().top + clicked.height() +  (paramVal === undefined ? 10 : 97 );
					$(target).css({"right":d,"left": "auto","top": BottomHeight});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstylebottomright').removeClass('speakstylebottomleft').addClass('speakstyletopright').addClass('dyn_popup');
				} else if (clicked.offset().top > halfheight && clicked.offset().left < halfwidth){
					var BottomHeight = clicked.position().top - (target.height() + 33 );
					$(target).css({"left": clicked.offset().left,"top": BottomHeight ,"right": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstylebottomright').removeClass('speakstyletopright').addClass('speakstylebottomleft').addClass('dyn_popup');	
				} else if (clicked.offset().top > halfheight && clicked.offset().left > halfwidth){
					var d = null,BottomHeight = null;
					d= ($(window).width() - (clicked.offset().left + clicked.outerWidth()))-11;
					BottomHeight = clicked.position().top - (target.height() + (paramVal === undefined ? 30 : (-57)));
					$(target).css({"right":d,"left": "auto","top": BottomHeight});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstyletopright').removeClass('speakstylebottomleft').addClass('speakstylebottomright').addClass('dyn_popup');
				} 

				self.closeAll(placeId);
			},
			
			popForCi : function(e,place) {
				var self=this;
				$(".dyn_popup").hide();
				
				$('.content_main').addClass('z_index_ci');
				
				var clicked = $(e);
				var target = $("#" + place);
				var twowidth = window.innerWidth/1.5;
				
				if (clicked.offset().left < twowidth) {
				$(target).toggle();
				var a = target.height()/2;
				var b = clicked.height()/2;
				// var t=clicked.offset().top + (b+12) - a ;
				// var l=clicked.offset().left + clicked.width()+ 15;
				var t=clicked.offset().top + (b+12) - a ;
				var l=clicked.offset().left + clicked.width()+ 8;
				$(target).offset({
				top: t,
				left: l
				});
				
				$(target).addClass('speakstyleleft').removeClass('speakstyleright');
				}
				else {
				$(target).toggle();
				var t=clicked.offset().top - target.height()/2;
				var l=clicked.offset().left - (target.width()+clicked.width()+15);
				$(target).offset({
				top: t,
				left: l
				});
				$(target).addClass('speakstyleright').removeClass('speakstyleleft');
				
				}
				self.customScroll(target.find('.popup_scroll'));
				self.closeAll(place);
				},
				
			
			openccpl : function(ee, placeId, currentPrjName) {
				var self=this;
				$(".dyn_popup").hide();
				
				$('.features_content_main').removeClass('z_index');
				var act = $(ee).attr("data-original-title");

				var clicked = $(ee);
				var target = $("#" + placeId);
				var t= clicked.offset().top + 33;
				var halfheight= window.innerHeight/2;
				var halfwidth= window.innerWidth/2;
				$(target).attr('currentPrjName',currentPrjName);
				var style;
				
				if (clicked.offset().top < halfheight && clicked.offset().left < halfwidth) {
					$(target).css({"left":clicked.offset().left ,"margin-top":10,"right": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopright').removeClass('speakstylebottomright').removeClass('speakstylebottomleft').addClass('speakstyletopleft').addClass('dyn_popup');
				} else if (clicked.offset().top < halfheight && clicked.offset().left > halfwidth){
					var d= ($(window).width() - (clicked.offset().left + clicked.outerWidth())) - 18;
					if(($(ee).parent('td').attr('class')!='delimages') && (act === "Delete "+currentPrjName)) {
						var BottomHeight = clicked.position().top + clicked.height() ;
						$(target).css({"right":d,"left": "auto","top": BottomHeight});
					}
					else {		
						$(target).css({"right":d ,"margin-top":10,"left": "auto","top": "auto"});
					}
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstylebottomright').removeClass('speakstylebottomleft').addClass('speakstyletopright').addClass('dyn_popup');
				} else if (clicked.offset().top > halfheight && clicked.offset().left < halfwidth){
					var BottomHeight = clicked.position().top - (target.height() + 33 );
					$(target).css({"left": clicked.offset().left,"top": BottomHeight ,"right": "auto"});
					$(target).toggle();
					$(target).removeClass('speakstyletopleft').removeClass('speakstylebottomright').removeClass('speakstyletopright').addClass('speakstylebottomleft').addClass('dyn_popup');	
				} else if (clicked.offset().top > halfheight && clicked.offset().left > halfwidth){
					var d = null,BottomHeight = null;
					d = ($(window).width() - (clicked.offset().left + clicked.outerWidth())) - 15;
					if(($(ee).parent('td').attr('class')!='delimages') && (act === "Delete "+currentPrjName)) {
						BottomHeight = clicked.position().top - 70;
						$(target).css({"right":d ,"top":BottomHeight,"left": "auto"});
						$(target).toggle();
						$(target).removeClass('speakstyletopleft').removeClass('speakstyletopright').removeClass('speakstylebottomleft').addClass('speakstylebottomright').addClass('dyn_popup');
					}
						else {
							BottomHeight = clicked.position().top - (target.height() + 28 );
							$(target).css({"right":d ,"top":BottomHeight,"left": "auto"});
							$(target).toggle();
							$(target).removeClass('speakstyletopleft').removeClass('speakstyletopright').removeClass('speakstylebottomleft').addClass('speakstylebottomright').addClass('dyn_popup');
						}
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
					$('#endDate').removeAttr('disabled');
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
			
			effectFadeOut : function (classname, msg) {
				$("."+classname).css("left", Math.max(0, (($(window).width() - $("."+classname).outerWidth()) / 2) +    $(window).scrollLeft()) + "px");
				$("."+classname).html(msg);
				$("."+classname).show();
				$("."+classname).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
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
			showPopUp : function(id, name, titile, callback) {
				$('div[name=myModalPopup]').attr('id', id);
				$('#saveChange').attr('name', name);
				$('#myModalLabel').html(titile);
				callback(true);
			},
			
			showErrorPopUp : function(configErrorMsg){
				var self = this;
				$(".blinkmsg").removeClass("popsuccess").addClass("poperror");
				self.effectFadeOut('poperror', configErrorMsg);
			},

	        //Show mandatory validation error msg
	        showDynamicErrors : function(response) {
	            $('.dynamicControls > li :input').removeClass("errormessage").attr("placeholder", "");
	            var self = this, control = $("#" + response.parameterKey);
	            if (response.configErr) {
	            	self.showErrorPopUp(response.configErrorMsg);
	            } else {
	            	control.focus();
		            control.addClass("errormessage");
		            if (control.prop('tagName') === "INPUT") {
		            	control.attr("placeholder", response.configErrorMsg);
		            } else if (control.prop('tagName') === "SELECT") {
		            	control.parent().find('button').addClass("btn-danger");
		            }	
	            }
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
			
			isValidUrl : function (url) {
				var urlPattern = /^(((ht|f){1}((tp|tps):[/][/]){1}))[-a-zA-Z0-9@:%_\+.~#!?&amp;//=]+$/;				
				if( !urlPattern.test(url)) {
					return false;
				} else {
					return true;
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
				//$('.unit_progress').find('#logContent').css("height", finalHeight - 20);
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
				$('.mask').fadeOut("slow", function() {
					$('.mask').remove();
				});
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
			
			//Allows A to Z a to Z, 0 to 9 and . - _
			specialCharValidation : function(inputStr) {
				return inputStr.replace(/[^a-zA-Z 0-9\.\-\_]+/g, '');
	        },

			showDynamicPopupLoading : function () {
				$('.dynamicPopupLoading').show();
			},
			
			hideDynamicPopupLoading : function () {
				$('.dynamicPopupLoading').hide();
			},
			
			fileTree : function (retValue, callback) {
				var self=this;
				//var strTree = $('<div></div>');
				var rootItem = $(retValue).contents().children().children();
				self.getList(rootItem, function(returnValue){
					//var tree = $(strTree).append(returnValue);	
					callback('<div>'+ returnValue +'</div>');
				});
			},
			
			getList : function(ItemList, callback) {
				var self=this, strUl = "", strRoot ="", strItems ="", strCollection = "";
				$(ItemList).each(function(index, value){
					if($(value).children().length > 0) {
						strRoot = $(value).attr('name');
						self.getList($(value).children(),function(callback) {
							strCollection = callback;
						});
						strItems += '<li value='+$(value).attr('path').replace(/\s/g,"+")+'><span class="folder"><a>' + strRoot + '</a></span>' + strCollection +'</li>';
					} else { 
						
						strItems += '<li value='+$(value).attr('path').replace(/\s/g,"+")+'><span class="folder"><a>' + $(value).attr('name') + '</a></span></li>';
					}
				});
				
				strUl = '<ul >' + strItems + '</ul>';
				callback(strUl); 
			},
			
			CSVToArray : function(strData, strDelimiter) {
				// Check to see if the delimiter is defined. If not,
				// then default to comma.
				strDelimiter = (strDelimiter || ",");

				// Create a regular expression to parse the CSV values.
				var objPattern = new RegExp(
					(
						// Delimiters.
						"(\\" + strDelimiter + "|\\r?\\n|\\r|^)" +

						// Quoted fields.
						"(?:\"([^\"]*(?:\"\"[^\"]*)*)\"|" +

						// Standard fields.
						"([^\"\\" + strDelimiter + "\\r\\n]*))"
					),
					"gi"
					);


				// Create an array to hold our data. Give the array
				// a default empty first row.
				var arrData = [[]];

				// Create an array to hold our individual pattern
				// matching groups.
				var arrMatches = null;


				// Keep looping over the regular expression matches
				// until we can no longer find a match.
				while (arrMatches = objPattern.exec( strData )){

					// Get the delimiter that was found.
					var strMatchedDelimiter = arrMatches[ 1 ];

					// Check to see if the given delimiter has a length
					// (is not the start of string) and if it matches
					// field delimiter. If id does not, then we know
					// that this delimiter is a row delimiter.
					if (
						strMatchedDelimiter.length &&
						(strMatchedDelimiter != strDelimiter)
						){

						// Since we have reached a new row of data,
						// add an empty row to our data array.
						arrData.push( [] );

					}


					// Now that we have our delimiter out of the way,
					// let's check to see which kind of value we
					// captured (quoted or unquoted).
					if (arrMatches[ 2 ]){

						// We found a quoted value. When we capture
						// this value, unescape any double quotes.
						var strMatchedValue = arrMatches[ 2 ].replace(
							new RegExp( "\"\"", "g" ),
							"\""
							);

					} else {

						// We found a non-quoted value.
						var strMatchedValue = arrMatches[ 3 ];

					}


					// Now that we have our value string, let's add
					// it to the data array.
					arrData[ arrData.length - 1 ].push( strMatchedValue );
				}
				// Return the parsed data.
				return( arrData );
			},
		}
	);

	return Clazz.WidgetWithTemplate;
});