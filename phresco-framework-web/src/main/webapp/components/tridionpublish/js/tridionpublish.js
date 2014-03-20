define(["tridionpublish/listener/tridionpublishListener"], function() {
	Clazz.createPackage("com.components.tridionpublish.js");

	Clazz.com.components.tridionpublish.js.tridionpublish = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/tridionpublish/template/tridionpublish.tmp",
		count : 0,
		tridionpublishListener : null,
		onChangeEvent: null,
		publishWebsite: null,
		unPublishWebsite: null,
		publishingQueueEvent: null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			commonVariables.runType = "tridionpublish";
			if(self.tridionpublishListener === null){
				self.tridionpublishListener = new Clazz.com.components.tridionpublish.js.listener.tridionpublishListener();
			}
				self.registerEvents();
			},
		
	
		
		registerEvents : function(){
			var self = this;
			
			if(self.onChangeEvent === null){
				self.onChangeEvent = new signals.Signal();
			}	
			self.onChangeEvent.add(self.tridionpublishListener.getTargets, self.tridionpublishListener);
						
			if(self.publishWebsite === null){
				self.publishWebsite = new signals.Signal();
			}	
			self.publishWebsite.add(self.tridionpublishListener.publishWebsite, self.tridionpublishListener);
									
			if(self.unPublishWebsite === null){
				self.unPublishWebsite = new signals.Signal();
			}	
			self.unPublishWebsite.add(self.tridionpublishListener.unPublishWebsite, self.tridionpublishListener);		
			
			if(self.publishingQueueEvent === null){
				self.publishingQueueEvent = new signals.Signal();
			}	
			self.publishingQueueEvent.add(self.tridionpublishListener.getPublishingQueue, self.tridionpublishListener);
			
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
		
		/* 	 preRender: function(whereToRender, renderFunction){
			var self = this;
		self.tridionpublishListener.getInfo(self.tridionpublishListener.getRequestHeader("", '', 'getList'), function(response){
			var tridionpublishObject = {};
			var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
				tridionpublishObject.tridionpublishInfos = response.data;
				tridionpublishObject.userPermissions = userPermissions;
				renderFunction(tridionpublishObject, whereToRender);
			}); 
		}, */
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
 		preRender: function(whereToRender, renderFunction) {
			var self = this;
			var envlist = {};
			setTimeout(function() {
				self.tridionpublishListener.getData(self.tridionpublishListener.getRequestHeader('' , "getEnv"), function(response) {
					envlist.envlist = response.data;
					renderFunction(envlist, whereToRender);
				});
			}, 200);
		}, 
		
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
            var self = this;
			$("#envList").on('change',  function() {
				$("#envTarget").empty();
				var environmentSelected = $(this).val();
				if(environmentSelected !== "0"){
					self.onChangeEvent.dispatch(environmentSelected , 'getTargets');
				}	
			});
			
			$("#publish").unbind('click');
			$("#publish").bind('click', function(){
				self.publishWebsite.dispatch();
			});	
			
			$("#unpublish").unbind('click');
			$("#unpublish").bind('click', function(){
				self.unPublishWebsite.dispatch();
			});
			
			$("#viewQueue").unbind('click');
			$("#viewQueue").bind('click', function(){
				var publishQueue = $("#publishQueue").attr("current");
				var publishQueueList = $("#publishQueueList").attr("current");
				if(publishQueue === "hidden"){
					$("#publishQueue").show("slow");
					$("#publishQueue").attr("current" , "displayed");
					$("#publishQueueList").show("slow");
					$("#publishQueueList").attr("current" , "displayed");
					self.publishingQueueEvent.dispatch('publishQueue');

				} else {
					$("#publishQueue").hide("slow");
					$("#publishQueue").attr("current" , "hidden");	
					$("#publishQueueList").hide("slow");
					$("#publishQueueList").attr("current" , "hidden");
				}
			});
			
		}
	});

	return Clazz.com.components.tridionpublish.js.tridionpublish;
});