
define(["framework/widgetWithTemplate", "ci/listener/ciListener", "lib/jquery-tojson-1.0"], function() {
	Clazz.createPackage("com.components.ci.js");

	Clazz.com.components.ci.js.ContinuousDeliveryView = Clazz.extend(Clazz.WidgetWithTemplate, {
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/ci/template/continuousDeliveryView.tmp",
		configUrl: "components/projects/config/config.json",
		name : commonVariables.continuousDeliveryView,
		ciListener: null,
		dynamicpage : null,
		ciRequestBody : {},
		templateData : {},
		continuousDeliveryConfigureLoadEvent : null,
		continuousDeliveryConfigureEditEvent : null,
		listBuildsEvent : null,
		generateBuildEvent : null,
		deleteJobEvent : null,
		cloneCiEvent : null,
		lastBuildStatusEvent : null,
		listEnvironmentsEvent : null,
		ciStatusEvent : null,
		jenkinsStatus : null,
		interval : {},
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			if (self.dynamicpage === null) {
				commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(retVal) {
					self.dynamicpage = retVal;
				});
			}

			if (self.ciListener === null) {
				self.ciListener = new Clazz.com.components.ci.js.listener.CIListener(globalConfig);
			}
			self.registerEvents(self.ciListener);
		},
		
		
		registerEvents : function (ciListener) {
			var self = this;
			if (self.continuousDeliveryConfigureEditEvent === null) {
				self.continuousDeliveryConfigureEditEvent = new signals.Signal();
			}

			if (self.continuousDeliveryConfigureLoadEvent === null) {
				self.continuousDeliveryConfigureLoadEvent = new signals.Signal();
			}
			
			if (self.listBuildsEvent === null) {
				self.listBuildsEvent = new signals.Signal();
			}
			
			if (self.generateBuildEvent === null) {
				self.generateBuildEvent = new signals.Signal();
			}
			
			if (self.deleteJobEvent === null) {
				self.deleteJobEvent = new signals.Signal();
			}
			
			if (self.cloneCiEvent === null) {
				self.cloneCiEvent = new signals.Signal();
			}
			
			if (self.lastBuildStatusEvent === null) {
				self.lastBuildStatusEvent = new signals.Signal();
			}
			
			if (self.listEnvironmentsEvent === null) {
				self.listEnvironmentsEvent = new signals.Signal();
			}
			
			if (self.ciStatusEvent === null) {
				self.ciStatusEvent = new signals.Signal();
			}
			
			if (self.jenkinsStatus === null) {
				self.jenkinsStatus = new signals.Signal();
			}
			
			self.continuousDeliveryConfigureEditEvent.add(ciListener.editContinuousDeliveryConfigure, ciListener);
			self.continuousDeliveryConfigureLoadEvent.add(ciListener.loadContinuousDeliveryConfigure, ciListener);
			self.listBuildsEvent.add(ciListener.getBuilds, ciListener);
			self.generateBuildEvent.add(ciListener.generateBuild, ciListener);
			self.deleteJobEvent.add(ciListener.deleteContinuousDelivery, ciListener);
			self.cloneCiEvent.add(ciListener.cloneCi, ciListener);
			self.lastBuildStatusEvent.add(ciListener.lastBuildStatus, ciListener);
			self.listEnvironmentsEvent.add(ciListener.listEnvironment, ciListener);
			self.ciStatusEvent.add(ciListener.ciStatus, ciListener);
			self.jenkinsStatus.add(ciListener.jenkinsStatus, ciListener);
		},
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage :function(){
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(this, commonVariables.animation);
		},

		loadPageTest :function(){
			Clazz.navigationController.push(this);
		},
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
			self.ciListener.listJobTemplate(self.ciListener.getRequestHeader(self.ciRequestBody, "continuousDeliveryList"), function(response) {
				self.templateData.projectDelivery = response.data;
				renderFunction(self.templateData, whereToRender);
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
			//to hide all opts @ startup in view page
			$(".opts").hide();
			self.jenkinsStatus.dispatch(function(callback) {
				$(".pipeline_box").each(function() {
	   				self.ciStatusEvent.dispatch($(this));
	   			});
			});
		},
		
		getAction : function(ciRequestBody, action, param, callback) {
			var self=this;
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			var resultvalue = 0;
			$(".tooltiptop").tooltip();
			$('.content_main').prevAll().each(function() {
				resultvalue = resultvalue + $(this).height(); 
			});
				
			resultvalue = resultvalue + $('.footer_section').height() + 65;
			$('.content_main').height($(window).height() - (resultvalue + 155));

			$(".execute_button").hover(function() {
				$(this).attr("src","themes/default/images/helios/execute_icon_hover.png");
			}, function() {
				$(this).attr("src","themes/default/images/helios/execute_icon.png");
			});
			
			$(".wait_button").hover(function() {
				$(this).attr("src","themes/default/images/helios/wait_hover.png");
			}, function() {
				$(this).attr("src","themes/default/images/helios/wait.png");
			});
			
			$(".time_button").hover(function() {
				$(this).attr("src","themes/default/images/helios/time_hover.png");
			}, function() {
				$(this).attr("src","themes/default/images/helios/time.png");
			});
			
   			$(".dyn_popup").hide();
	  		
   			$(window).resize(function() {
   				$(".dyn_popup").hide();
			});
   			
   			$("#createContinuousDelivery").click(function() {
				self.continuousDeliveryConfigureLoadEvent.dispatch();
   			});

   			$("a[name=editContinuousDelivery]").click(function() {
   				var contName = $(this).attr("continuousName");
				self.continuousDeliveryConfigureEditEvent.dispatch(contName);
   			});
   			clearInterval(self.interval);
   			self.interval = setInterval(function() {   
   				$(".pipeline_box").each(function() {
   	   				self.ciStatusEvent.dispatch($(this));
   	   			});
 			}, 10000);
   			
			$(".datetime_status").click(function() {
				self.openccwait(this, $(this).attr('class'));
			});
			
			$("a[temp=deleteCI]").click(function() {
				var name = $(this).attr('name');
				$("input[name=cdName]").val(name);
				self.openccci(this, 'deleteCI');
			});
			
			$("a[temp=clone]").click(function() {
				var name = $(this).attr('name');
				$("input[name=continuousName]").val(name);
				self.openccci(this, 'clone_popup');
				self.listEnvironmentsEvent.dispatch($(this));
			});
			
			$("input[name='confirmDeleteCI']").unbind('click');
			$("input[name='confirmDeleteCI']").click(function(e) {
				self.deleteJobEvent.dispatch($(this));
			});
			
			$(".ci_info").click(function() {
				self.opencctime(this, $(this).attr('class'));
			});
			
			$("#clone_ci").click(function() {
				self.cloneCiEvent.dispatch($(this));
			});
			
			$("a[temp=buildStatus]").click(function() {
				self.lastBuildStatusEvent.dispatch($(this));
			});
			
			$("a[temp=list_builds]").click(function() {
				self.listBuildsEvent.dispatch($(this),'','', $(this).attr('operation'));
			});
			
			$("a[temp=generate_build]").click(function() {
				$(this).parent().parent().parent("div").find(".img_process").attr('src',"themes/default/images/helios/processing.gif");
				self.generateBuildEvent.dispatch($(this));
			});
			
			this.customScroll($(".scrollpage"));
			
		}
	});

	return Clazz.com.components.ci.js.ContinuousDeliveryView;
});