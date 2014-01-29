define(["projects/listener/projectsListener"], function() {

	Clazz.createPackage("com.components.projects.js");

	Clazz.com.components.projects.js.addProject = Clazz.extend(Clazz.WidgetWithTemplate, {
		projectsEvent : null,
		templateUrl: commonVariables.contexturl + "components/projects/template/addProject.tmp",
		configUrl: "components/projects/config/config.json",
		name : commonVariables.addproject,
		projectsListener : null,
		applicationlayerData : null,
		weblayerData : null,
		mobilelayerData : null,
		templateData : {},
		onProjectsEvent : null,
		onRemoveLayerEvent : null,
		onAddLayerEvent : null,
		onCreateEvent : null,
		onCancelCreateEvent : null,
		
			
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			if(self.projectsListener === null){
				self.projectsListener = new Clazz.com.components.projects.js.listener.projectsListener();
			}
			self.registerEvents(self.projectsListener);
		},

		/***
		 * Called once to register all the events 
		 *
		 * @projectsListener: projectsListener methods getting registered
		 */
		registerEvents : function (projectsListener) {
			var self = this;
			if(self.onProjectsEvent === null) {	
				self.onProjectsEvent = new signals.Signal();
			}	
			if(self.onRemoveLayerEvent === null) {	
				self.onRemoveLayerEvent = new signals.Signal();
			}
			
			if(self.onAddLayerEvent === null) {
				self.onAddLayerEvent = new signals.Signal();
			}
			
			if(self.onCreateEvent === null) {
				self.onCreateEvent = new signals.Signal();
			}
			
			if(self.onCancelCreateEvent === null) {
				self.onCancelCreateEvent = new signals.Signal();
			}
			
			self.onRemoveLayerEvent.add(projectsListener.removelayer, projectsListener);
			self.onAddLayerEvent.add(projectsListener.addlayer, projectsListener);
			self.onCreateEvent.add(projectsListener.createproject, projectsListener);
			self.onCancelCreateEvent.add(projectsListener.cancelCreateproject, projectsListener);
		},
		
		preRender : function(whereToRender, renderFunction) {
			$("#projectList").hide();
			$("#createProject").show();
			var self=this;
			self.projectsListener.counter = null;
			$(".widget-mask-mid-content").addClass('widget-mask-mid-content-altered');
			self.applicationlayerData = commonVariables.api.localVal.getJson("Front End");
			self.weblayerData = commonVariables.api.localVal.getJson("Middle Tier");
			self.mobilelayerData = commonVariables.api.localVal.getJson("CMS");
			if(self.applicationlayerData !== null &&  self.weblayerData !== null && self.mobilelayerData !== null) {
				self.templateData.applicationlayerData = self.applicationlayerData;
				self.templateData.weblayerData = self.weblayerData;
				self.templateData.mobilelayerData = self.mobilelayerData;
				renderFunction(self.templateData, whereToRender);
				setTimeout(function() {
					$(".widget-mask-mid-content").removeClass('widget-mask-mid-content-altered');
				},1500);
				
			} else {
				self.setTechnologyData(function(bCheck){
					if(bCheck){
						self.applicationlayerData = commonVariables.api.localVal.getJson("Front End");
						self.weblayerData = commonVariables.api.localVal.getJson("Middle Tier");
						self.mobilelayerData = commonVariables.api.localVal.getJson("CMS");
						self.templateData.applicationlayerData = self.applicationlayerData;
						self.templateData.weblayerData = self.weblayerData;
						self.templateData.mobilelayerData = self.mobilelayerData;
						renderFunction(self.templateData, whereToRender);
						setTimeout(function() {
							$(".widget-mask-mid-content").removeClass('widget-mask-mid-content-altered');
						},1500);
					}
				});
			}	
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self=this;
			self.multiselect();
			commonVariables.navListener.currentTab = commonVariables.addproject;
		},
		
		setTechnologyData : function(callback) {
			var self=this;
			self.userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			self.projectsListener.getEditProject(self.projectsListener.getRequestHeader(self.projectRequestBody, '', 'apptypes'), function(response) {
				if (response !== null && (response.status !== "error" || response.status !== "failure")){
					$.each(response.data, function(index, value){
						commonVariables.api.localVal.setJson(value.name, value);
						if(response.data.length === (index + 1)){
							callback(true);
						}
					});
				} else {
					$(".msgdisplay").removeClass("success").addClass("error");
					$(".error").attr('data-i18n', 'errorCodes.' + response.responseCode);
					self.renderlocales(commonVariables.contentPlaceholder);	
					$(".error").show();
					$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
					setTimeout(function() {
						$(".error").hide();
					},2500);
				}
			});
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self=this;
			self.projectsListener.addLayersEvent();
			self.projectsListener.removeLayersEvent();
			self.projectsListener.technologyAndVersionChangeEvent();
			self.projectsListener.pilotprojectsEvent();
			if(commonVariables.animation) {
				self.setDateTimePicker();
			}
			self.windowResize();
			
			$("img[name='close']").unbind('click');
			$("img[name='close']").bind('click', function(){
				self.onRemoveLayerEvent.dispatch($(this));
			});
			
			$("#projectversion").unbind('click');
			$("#projectversion").click(function() {
				var majorVersion = $("#projectversion").attr("major");
				var minorVersion = $("#projectversion").attr("minor");
				var fixedVersion = $("#projectversion").attr("fixed");
				var iterationType = $("#projectversion").attr("iterationType");
				var weekStart = $("#projectversion").attr("weekStart");
				
				$("#majorVersion option[value=" + majorVersion +"]").attr("selected", true);
				$("#minorVersion option[value=" + minorVersion +"]").attr("selected", true);
				$("#fixedVersion option[value=" + fixedVersion +"]").attr("selected", true);
				$("#iterationType option[value=" + iterationType +"]").attr("selected", true);
				$("#weekStart option[value=" + weekStart +"]").attr("selected", true);
				
				self.openccpl(this,'version_popup');
			});
			
			$("#submitVersion").unbind('click');
			$("#submitVersion").click(function() {
				var majorVersion = $("#majorVersion").val();
				var minorVersion = $("#minorVersion").val();
				var fixedVersion = $("#fixedVersion").val();
				var iterationType = $("#iterationType").val();
				var weekStart = Number($("#weekStart").val());
				
				var version = majorVersion + "." + minorVersion + "." + fixedVersion + "-SNAPSHOT"
				$("#projectversion").val(version).attr("major", majorVersion).attr("minor", minorVersion);
				$("#projectversion").attr("fixed", fixedVersion).attr("iterationType", iterationType).attr("weekStart", weekStart);
			});
			
			$(document).keyup(function(e) {
				if (e.keyCode == 27) {
					$("#versionForm").trigger('reset');
				}
			});
			
			$(document).click(function(e) {
				if($(e.target).attr('id') !== 'projectversion' && $(e.target).parents('div').attr('id') !== 'version_popup' && $(e.target).attr('id') !== 'version_popup') {
					$("#version_popup").hide();
				}
			});
			
			$("img[name='close']").dblclick(function(){
				self.projectsListener.counter--;
				$('img[name="close"]').show();
			});
			
			$(".flt_left input").unbind('click');
			$(".flt_left input").bind('click', function(){
				self.onAddLayerEvent.dispatch($(this));
			});
			
			$(".appln-appcode, .web-appcode, .mobile-appcode").unbind('input');
            $(".appln-appcode, .web-appcode, .mobile-appcode").bind('input propertychange', function(){
                var temp, temp2, str;
                temp = $(this).prop('selectionStart');
                temp2 = $(this).prop('selectionEnd');                 
                str = $(this).val();
                str = str.replace(/[^a-zA-Z 0-9\-\_]+/g, '');
                str = str.replace(/\s+/g, '');
                $(this).val(str);
                $(this).prop('selectionStart', temp);
                $(this).prop('selectionEnd', temp2);
            });
			
			$("input[name='Create']").unbind('click');
			$("input[name='Create']").bind('click', function(){
				self.projectsListener.projectInfo = {}
				self.onCreateEvent.dispatch('', 'create');
			});
			
			$("input[name='Cancel']").unbind('click');
			$("input[name='Cancel']").bind('click', function(){
				self.onCancelCreateEvent.dispatch();
			});
			
			$("#startDate,#endDate").bind('keydown', function(e) {
				var keyCode = e.keyCode || e.which;
				if ((e.which >= 48 && e.which <= 57 && !e.shiftKey) || (keyCode === 191 && !e.shiftKey) || (keyCode === 9) || (keyCode === 8)){
					return true;
				} else {
					e.preventDefault();
				}
			});


			$("#endDate").blur(function(){
				if($('#projectname').val() === '') {
					$('#projectname').focus();
				} else if($('.applnLayer').attr('key') === 'displayed') {
					$("#appcode").focus();
				} else if($('.webLayer').attr('key') === 'displayed') {
					$("#webappcode").focus();
				} else {
					$("#mobileappcode").focus();
				}
			});

			$("input[name='projectname']").on("keypress", function(e) {
				if (e.which === 32 && !this.value.length) {
					e.preventDefault();
				}	
			});
			
			$("input[name='projectname']").bind('input propertychange', function (e) {
				var str = $(this).val();
				str = self.specialCharValidation(str);
				str = str.replace(/[^a-zA-Z 0-9\-\_]+/g, '');
				str = str.replace(/\s+/g, '');
				$("input[name='projectcode']").val(str);
			});

			$("input[name='projectcode']").bind('input propertychange', function() {
				var str = $(this).val();
				str = self.specialCharValidation(str);
				str = str.replace(/[^a-zA-Z 0-9\-\_]+/g, '');
				str = str.replace(/\s+/g, '');
				$(this).val(str);
			});

			$("input[name='projectcode']").focusout(function() {
				$(this).val(self.specialCharValidation($(this).val().replace(/\s/g, "")));
				var totalLength = $(this).val().length;
				if($(this).val().match(/[._-]/g) !== null){ 
					var charLength =  $(this).val().match(/[._-]/g).length; 
				}
				if(totalLength === charLength){
					$(this).val('');
					$(this).focus();
					$(this).addClass('errormessage');
					$(this).attr('placeholder', 'Invalid Code');
				}
				$(this).bind('input', function() {
					$(this).removeClass("errormessage");
					$(this).removeAttr("placeholder");
				});
			});
			
			$("#strdt").click(function() {
				$("#startDate").focus();
			});

			$("#enddt").click(function() {
				$("#endDate").focus();
			});
			
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			this.customScroll($(".scrolldiv"));
		}
	});

	return Clazz.com.components.projects.js.addProject;
});