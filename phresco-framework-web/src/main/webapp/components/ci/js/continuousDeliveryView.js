define(["framework/widgetWithTemplate", "ci/listener/ciListener"], function() {
	Clazz.createPackage("com.components.ci.js");

	Clazz.com.components.ci.js.ContinuousDeliveryView = Clazz.extend(Clazz.WidgetWithTemplate, {
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/ci/template/continuousDeliveryView.tmp",
		configUrl: "components/projects/config/config.json",
		name : commonVariables.continuousDeliveryView,
		ciListener: null,
		dynamicpage : null,
		continuousDeliveryConfigureLoadEvent : null,
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.dynamicpage = commonVariables.navListener.getMyObj(commonVariables.dynamicPage);
			self.ciListener = new Clazz.com.components.ci.js.listener.CIListener(globalConfig);
			self.registerEvents(self.ciListener);
		},
		
		
		registerEvents : function (ciListener) {
			var self = this;
			self.continuousDeliveryConfigureLoadEvent = new signals.Signal();
			self.continuousDeliveryConfigureLoadEvent.add(ciListener.loadContinuousDeliveryConfigure, ciListener);
		},
		/***
		 * Called in once the login is success
		 *
		 */
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
			var self = this; 
		},
		
		/* dynamicEvent : function() {
			var self = this; 
			var dependency = '';
			dependency = $("select[name='sonar']").find(':selected').attr('dependency');
				
		}, */

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
   			$(".dyn_popup").hide();
	  		
	  		/*$(window).resize(function() {
				$(".dyn_popup").hide();
	  		});
	  		$(".first_list").find("span").hide();

			$(function() {
			    $( "#sortable1, #sortable2" ).sortable({
			      connectWith: ".connectedSortable",
				  items: "> li",
				  start: function( event, ui ) {
					  $("#sortable1 li.ui-state-default a").hide();
					  $("#sortable2 li.ui-state-default a").show();	
					  $(".dyn_popup").hide();
					  },
				  stop: function( event, ui ) {
					  $("#sortable2 li.ui-state-default a").show();
					  $("#sortable1 li.ui-state-default a").hide();	
					  $(".dyn_popup").hide();
					  }	  
			    }).disableSelection();
			 }); 

	  		$(function () {
				$(".tooltiptop").tooltip();
			});
		
			$(".code_content .scrollContent").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced:{
						updateOnContentResize: true
				}
			});
		
			$("#sortable1 li.ui-state-default a").hide();

			$("input[name=cont_delivery]", "input[name=code_build]").click(function() {
   				self.openccmini(this, $(this).attr('name'));
   			});*/

   			$("#createContinuousDelivery").click(function() {
				self.continuousDeliveryConfigureLoadEvent.dispatch();
   			});

			$(".datetime_status").click(function() {
				self.openccwait(this, $(this).attr('class'));
			});

			$(".ci_info").click(function() {
				self.opencctime(this, $(this).attr('class'));
			});
		}
	});

	return Clazz.com.components.ci.js.ContinuousDeliveryView;
});