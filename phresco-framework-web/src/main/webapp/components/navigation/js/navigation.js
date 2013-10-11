define(["navigation/listener/navigationListener"], function() {
	
	Clazz.createPackage("com.components.navigation.js");

	Clazz.com.components.navigation.js.navigation = Clazz.extend(Clazz.WidgetWithTemplate, {
		navigationEvent : null,
		navigationHeader : null,
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/navigation/template/navigation.tmp",
		configUrl: "components/navigation/config/config.json",
		name : window.commonVariables.navigation,
		navigationListener : null,
		onAddNewProjectEvent : null,
		onMytabEvent : null,
		currentContent : null,
		onImportEvent : null,
		 
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			
			if(self.navigationListener === null){
				commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				self.navigationListener = commonVariables.navListener;
			}
			self.registerEvents(self.navigationListener);
		},

		/***
		 * Called in once the navigation is success
		 *
		 */
		loadPage : function(){
			if(self.navigationListener === null){
				commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				self.navigationListener = commonVariables.navListener;
			}
		},
		
		registerEvents : function(navigationListener) {
			var self = this;
			
			if(self.onAddNewProjectEvent === null){
				self.onAddNewProjectEvent = new signals.Signal();
			}	
			if(self.onMytabEvent === null){
				self.onMytabEvent = new signals.Signal();
			}
			if(self.onImportEvent === null){
				self.onImportEvent = new signals.Signal();
			}
			self.onAddNewProjectEvent.add(navigationListener.onAddProject, navigationListener); 
			self.onMytabEvent.add(navigationListener.onMytabEvent, navigationListener); 
			self.onImportEvent.add(navigationListener.addImportEvent, navigationListener);
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {	
			var self = this;
			self.navigationListener.landingPage(self.currentContent);
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			
			self.windowResize();
			var data = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			
			$('#addproject').unbind('click');
			$('#addproject').click(function(){
				self.onAddNewProjectEvent.dispatch();
			}); 
			 
			$("#editprojectTab li").click(function() {
				$("#editprojectTab li a").removeClass("act");
				$(this).children().addClass("act");
				self.onMytabEvent.dispatch(this.id);
			});
			
			$("li[name=editMenu]").unbind("click");
			$("li[name=editMenu]").click(function() {
				if(!commonVariables.subtabClicked){
					$("#myTab li a").removeClass("act");
					$(this).children().addClass("act");
					self.onMytabEvent.dispatch(this.id);
					commonVariables.favConfig = '';
				}
				commonVariables.subtabClicked = false;
			});
			
			$("li[name=qualityMenu]").off("click");
			$("li[name=qualityMenu]").on("click", function() {
				$("#myTab li a").removeClass("act");
				$('#qualityMenu').addClass("act");
				self.onMytabEvent.dispatch(this.id);
			});
			
			$("#importType").unbind("change");
			$("#importType").change(function() {
				$('.branchval').val('');
				$('.passPhraseval').val('');
				$('#revision').val('');
			});
			
			//var counter = 0;
			$("#importApp").unbind("click");
			$("#importApp").click(function() {
				var currentPrjName = "";
				/* if(counter === 1) {
					$('#importUserName').val('');
					$('#importPassword').val('');
				} else { */				
					$('#importUserName').val(data.id);
					$('#importPassword').val('');
				//}
				if($("#importType").val() === 'svn') {
					$(".seperatetd").show();
					$(".seperatetd").parent().show();
				} else {
					$(".seperatetd").hide();
					$(".seperatetd").parent().hide();
				}	
				$("#importRepourl").val('');	
				$("#testRepoUrl").val("");
				$("#testImportUserName").val(data.id);
				$("#testImportPassword").val("");
				$("#testRevision").val("");
				$("#importRepourl").removeClass("errormessage");
				$("#importUserName").removeClass("errormessage");
				$("#importPassword").removeClass("errormessage");
				$("#gitUserName").val("");
				$("#gitPassword").val("");
				$("#gitUserName").removeClass("errormessage");
				$("#gitPassword").removeClass("errormessage");
				$("#revision").removeClass("errormessage");
				$("#testRepoUrl").removeClass("errormessage");
				$("#testImportUserName").removeClass("errormessage");
				$("#testImportPassword").removeClass("errormessage");
				self.navigationListener.validateTextBox($("#importRepourl"),"");
				self.navigationListener.validateTextBox($("#importUserName"),"");
				self.navigationListener.validateTextBox($("#importPassword"),"");
				self.navigationListener.validateTextBox($("#revision"),"");
				self.navigationListener.validateTextBox($("#testRepoUrl"),"");
				self.navigationListener.validateTextBox($("#testImportUserName"),"");
				self.navigationListener.validateTextBox($("#testImportPassword"),"");
				self.navigationListener.validateTextBox($("#testRepoUrl"),"");
				//counter = 1;
				self.opencc(this, "project_list_import", currentPrjName);
			});
			
			$(".gitdata").hide();
			$(".perforcedata").hide();
			$(".importselect select").change(function () {
				if($(this).val() === "bitkeeper") {
					$(".svndata").hide();
					$(".svnusr").hide();
					$(".svnpswd").hide();
					$(".gitdata").show();
					$(".seperatetd").hide();
					$(".testCheckoutData").hide();
					$("label[name='branch']").hide();
					$(".gitdata").find('tbody').children('tr:last').show();
					$(".gitdata").find('tbody').children('tr:last').prev('tr').show();
					$(".branchval").hide();
					$("#gitUserName").parent().parent().prev('tr').find('sup').show();
					$(".perforcedata").hide();
					$("#importRepourl").attr("placeholder","");

				}

				else if($(this).val() === "git") {
					$(".svndata").hide();
					$(".svnusr").hide();
					$(".svnpswd").hide();
					$(".gitdata").show();
					$(".seperatetd").hide();
					$(".testCheckoutData").hide();
					$("label[name='branch']").show();
					$(".gitdata").find('tbody').children('tr:last').show();
					$(".gitdata").find('tbody').children('tr:last').prev('tr').show();
					$(".branchval").show();
					$("#gitUserName").parent().parent().prev('tr').find('sup').hide();
					$(".perforcedata").hide();
					$("#importRepourl").attr("placeholder","");
				}

				else if($(this).val() === "svn") {
					$(".svndata").show();
					$(".seperatetd").show();
					$(".seperatetd").parent().show();
					$(".svnusr").show();
					$(".svnpswd").show();
					$(".gitdata").hide();
					$(".perforcedata").hide();
					$("#importRepourl").attr("placeholder","");
				}

				else if($(this).val() === "perforce") {
					$(".svndata").hide();
					$(".svnusr").hide();
					$(".svnpswd").hide();
					$(".gitdata").show();
					$(".gitdata").find('tbody').children('tr:last').hide();
					$(".gitdata").find('tbody').children('tr:last').prev('tr').hide();
					$(".seperatetd").hide();
					$(".testCheckoutData").hide();
					$("label[name='branch']").hide();
					$(".branchval").hide();
					$("#gitUserName").parent().parent().prev('tr').find('sup').hide();
					$(".perforcedata").show();
					$("#importRepourl").attr("placeholder","host:port");

				}
			});
			
			$("input[name=headoption]").change(function() {
				$("#revision").removeClass("errormessage");
				$("#revision").removeAttr("placeholder");
				if("HEAD" === $(this).val()) {
					$("#revision").attr("readonly", "readonly");
				} else {
					$("#revision").removeAttr("readonly");
				}
			});
			
			var checkbox = $("#importCredential").find('input[type=checkbox]');
			checkbox.unbind("change");
			checkbox.on("change", function(){	
				if(checkbox.is(':checked')) {
					$('#importUserName').removeAttr('readonly');
					$('#importPassword').removeAttr('readonly');
					$('#importUserName').val('');
					$('#importPassword').val('');
				} else {
					$('#importUserName').val(data.id);
					//$('#importPassword').val(data.password);
					$('#importUserName').attr('readonly','readonly');
					$('#importPassword').attr('readonly','readonly');
				}	
			});
			
			var checkboxtestcheckout = $("#testCredentials");
			checkboxtestcheckout.unbind("change");
			checkboxtestcheckout.on("change", function(){	
				if(checkboxtestcheckout.is(':checked')) {
					$('#testImportUserName').removeAttr('readonly');
					$('#testImportPassword').removeAttr('readonly');
					$('#testImportUserName').val('');
					$('#testImportPassword').val('');
				} else {
					$('#testImportUserName').val(data.id);
					$('#testImportUserName').attr('readonly','readonly');
					$('#testImportPassword').attr('readonly','readonly');
				}	
			});
			
			$("input[name=testHeadOption]").change(function() {
				$("#testRevision").removeClass("errormessage");
				$("#testRevision").removeAttr("placeholder");
				if("HEAD" === $(this).val()) {
					$("#testRevision").attr("readonly", "readonly");
				} else {
					$("#testRevision").removeAttr("readonly");
				}
			});
			
			$(".testCheckout").unbind("click");
			$(".testCheckout").click(function() {
				if ($(this).is(':checked')) {
					$(".testCheckoutData").show();
				} else {
					$(".testCheckoutData").hide();
				}
			});
			
			$("input[name='importbtn']").unbind("click");
			$("input[name='importbtn']").click(function() {
				self.navigationListener.validateImport(function(response) {
					if (!response) {
						$("#importloading").show();
						self.onImportEvent.dispatch();
					}
				});
			});
			
			$('#gitName').keypress(function(e) {
				if ((e.which >= 65 && e.which <= 90) ||(e.which >= 97 && e.which <= 122) || (e.which === 8) || (e.which === 45)|| (e.which >= 48 && e.which <= 57) || (e.which === 95)) {
					return true;
				} else {
					e.preventDefault();
				}
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			
		}
	});

	return Clazz.com.components.navigation.js.navigation;
});