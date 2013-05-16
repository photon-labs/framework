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
					useLocalStorage: false,
					debug: false
				}, function() {
					$(whereToRender).i18n();
				});
			},
			
			opencc : function(ee,placeId, currentPrjName) {
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
			
			closeAll : function(placeId) {
				$(document).keyup(function(e) {
					if(e.which == 27){
						$("#" + placeId).hide();
					}
				});
				
				$('.dyn_popup_close').click( function() {
					$("#" + placeId).hide();
				});
					
			},

			setDateTimePicker : function(){
				var nowTemp = new Date();
				var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
				
				$(".datepicker").remove();
				var checkin = $('#startDate').datepicker({
					onRender: function(date) {return date.valueOf() < now.valueOf() ? 'disabled' : '';}
				}).on('changeDate', function(ev) {
				   if (ev.date.valueOf() > checkout.date.valueOf()) {
						var newDate = new Date(ev.date)
						newDate.setDate(newDate.getDate() + 1);
						checkout.setValue(newDate);
					}
					checkin.hide();
					$('#endDate')[0].focus();
				}).data('datepicker');
				
				var checkout = $('#endDate').datepicker({
					onRender: function(date) {return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';}
				}).on('changeDate', function(ev) {checkout.hide();}).data('datepicker');
			}
		}
	);

	return Clazz.WidgetWithTemplate;
});