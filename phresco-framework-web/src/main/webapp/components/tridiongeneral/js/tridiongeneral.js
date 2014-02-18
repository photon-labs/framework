define(["tridiongeneral/listener/tridiongeneralListener"], function() {
	Clazz.createPackage("com.components.tridiongeneral.js");

	Clazz.com.components.tridiongeneral.js.tridiongeneral = Clazz.extend(Clazz.WidgetWithTemplate, {
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/tridiongeneral/template/tridiongeneral.tmp",
		configUrl: "components/tridiongeneral/config/config.json",
		name : commonVariables.tridiongeneral,
		tridiongeneralListener : null,
		onSearchEvent: null,
		getAllParentsEvent: null,
		savePublicationEvent : null,
		readConfigEvent : null,
		submitPublicationEvent : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(){
			var self = this;
			if (self.tridiongeneralListener === null) {
				self.tridiongeneralListener = new Clazz.com.components.tridiongeneral.js.listener.tridiongeneralListener();
			}
			
			self.registerEvents();
		},
		
		registerEvents : function () {
			var self=this;
			if(self.onSearchEvent === null ){
				self.onSearchEvent = new signals.Signal();				
			}
				self.onSearchEvent.add(self.tridiongeneralListener.parentsearch, self.tridiongeneralListener);	
				
			if(self.getAllParentsEvent === null ){
				self.getAllParentsEvent = new signals.Signal();				
			}
				self.getAllParentsEvent.add(self.tridiongeneralListener.getAllParents, self.tridiongeneralListener);	
				
			if(self.savePublicationEvent === null ){
				self.savePublicationEvent = new signals.Signal();				
			}
				self.savePublicationEvent.add(self.tridiongeneralListener.savePublication, self.tridiongeneralListener);	
				
			if(self.readConfigEvent === null ){
				self.readConfigEvent = new signals.Signal();				
			}
				self.readConfigEvent.add(self.tridiongeneralListener.readConfigData, self.tridiongeneralListener);	
							
			if(self.submitPublicationEvent === null ){
				self.submitPublicationEvent = new signals.Signal();				
			}
				self.submitPublicationEvent.add(self.tridiongeneralListener.submitPublication, self.tridiongeneralListener);	

		},

		/***
		 * Called in once the login is success
		 *
		 */
		loadPage :function() {
			Clazz.navigationController.push(this);
		},
		
 		preRender: function(whereToRender, renderFunction) {
			var self = this;
			var publicationInfo = {};
			setTimeout(function() {
				self.tridiongeneralListener.getData(self.tridiongeneralListener.getRequestHeader('' , "readConfig"), function(response) {
					commonVariables.api.localVal.setJson('readConfigData', response.data);
					publicationInfo.publicationInfo = response;
					renderFunction(publicationInfo, whereToRender);
				});
			}, 200);
		}, 
		postRender : function(element) {
			var self = this;
			//self.readConfigEvent.dispatch('readConfig');
		},
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */

		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			self.tableScrollbar();
			this.customScroll($(".cus_themes"));
			
		/* 	$("#classification").on('change',  function() {
				var classificationId = $(this).val();
				$("#classificationId").text(classificationId);
				self.readConfigEvent.dispatch(classificationId , 'readConfig');
			}); */
	
			$("#parent_publication").on('click', function (){
				if($(".parentpublicationdiv").attr("key") === "hidden"){
					self.getAllParentsEvent.dispatch('getAllParents');
					$(".parentpublicationdiv").show('slow');
					$(".parentpublicationdiv").attr("key", "displayed");
				} else {
					$(".parentpublicationdiv").hide('slow');
					$(".parentpublicationdiv").attr("key", "hidden");
				}
			});

			$("#reportUl li[name=selectType]").click(function() {
				$('.dyn_popup').hide();
				validateAgainst = $(this).attr('key');
				$("#repTypes").html($(this).attr('data'));
				$("#repTypes").attr("key",validateAgainst);
				$("#classificationId").text($(this).attr('data'));
				$(".msgdisplay").empty();
				$(".msgdisplay").css("display", "none");

				self.readConfigEvent.dispatch($(this).attr('data') , 'readConfig');
				$(".parentpublicationdiv").hide('slow');
				$(".parentpublicationdiv").attr("key", "hidden");
				//self.getIframeReport(validateAgainst);
			});
			
			$('#avbleparent').keyup(function(event) {
				//var classval = $("#search").attr("class");
				var txtSearch = $('#avbleparent').val();
				var divId = "sortable1";
				self.onSearchEvent.dispatch(txtSearch, divId);
           	});
			
			
			$('.cleartext').click(function() {
				var temp = $(this).attr('name');
				$("#"+temp).val('');
				self.onSearchEvent.dispatch("", 'sortable1');
				$("#norecord1").hide();	
			});
			
			$("#savePublication").unbind('click');
			$("#savePublication").bind('click', function(){
				self.savePublicationEvent.dispatch();
			});
					
						
			$("#submitPublication").unbind('click');
			$("#submitPublication").bind('click', function(){
				self.submitPublicationEvent.dispatch();
			});
					
			
			$(function() {
				// sortable1 functionality
				$( "#sortable1" ).sortable({
					connectWith: ".connectedSortable",
					items: "> li",
					start: function( event, ui ) {
						$(".dyn_popup").hide();
						$('#header').css('z-index','7');
						$('.content_title').css('z-index','6');
						$('.qual_unit').css('z-index','6');
					},

					stop: function( event, ui ) {
						$(".dyn_popup").hide();
						$('#header').css('z-index','7');
						$('.content_title').css('z-index','6');
						$('.qual_unit').css('z-index','6');
						//self.downStreamCriteria.dispatch();
						//self.lastChild.dispatch();
					},

					receive: function( event, ui ) {
						//self.sortableOneReceive.dispatch(ui);
					},

					change: function( event, ui ) {	
						//self.sortableOneChange.dispatch(ui);
					}
				});

				// sortable2 functionality
			    $( "#sortable2" ).sortable({
					connectWith: ".connectedSortable",
					items: "> li",
					start: function( event, ui ) {
						$(".dyn_popup").hide();
					},

					stop: function( event, ui ) {
						$(".dyn_popup").hide();
					},

					change: function( event, ui ) {
						//self.sortableTwoChange.dispatch(ui);
					},

					receive: function( event, ui ) {
						//self.sortableTwoReceive.dispatch(ui);
					},
					
					update: function( event, ui ) {
						//self.sortableTwoHold.dispatch(ui);
					}
				}); 
			});

	  		$(function () {
				$(".tooltiptop").tooltip();
			});
		
			$(".code_content .scrollContent").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced: {
					updateOnContentResize: true
				}
			});
			
			// By Default gear icon should not be displayed
			$("#sortable1 li.ui-state-default a").hide();
			
   			$('#sortable2').on('click', 'a[name=jobConfigurePopup]', function() {
				$('#header').css('z-index','0');
				$('.content_title').css('z-index','0');
				$('.qual_unit').css('z-index','0');
   				var envName = $("[name=environments]").val();
   				$(this).attr("envName",envName);
   				//self.onConfigureJobPopupEvent.dispatch(this);
   			});

   			// on change of environemnt change function
/*    			$("[name=environments]").change(function() {
   				$("#sortable2").empty();
				self.onLoadEnvironmentEvent.dispatch(function(params) {
						self.getAction(self.ciRequestBody, 'getJobTemplatesByEnvironment', params, function(response) {
							self.ciListener.constructJobTemplateViewByEnvironment(response, function() {});
						});
				});
   			});	 */					
			
		}
	});

	return Clazz.com.components.tridiongeneral.js.tridiongeneral;
});