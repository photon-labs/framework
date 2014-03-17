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
			var pass = commonVariables.api.localVal.getSession('password');
			
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
				
				var importType = $(this).val();
				self.showHideImportAppCtrls(importType);
			});
			
			$("#importApp").unbind("click");
			$("#importApp").bind("click", function() {
				self.hideBtnLoading("button[name='importbtn']");
				self.navigationListener.showSrcTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
				
				$(".importType").val("svn");
				self.showHideImportAppCtrls($(".importType").val());
				
				var currentPrjName = "";
				
				$('#importUserName').val(data.id).attr("readonly","readonly");
				$('#importPassword').val(pass).attr("readonly","readonly");
				
				$('#importPhrescoUserName').val(data.id).attr("readonly","readonly");
				$('#importPhrescoPassword').val(pass).attr("readonly","readonly");
				
				$('#importTestUserName').val(data.id).attr("readonly","readonly");
				$('#importTestPassword').val(pass).attr("readonly","readonly");
				
				if($("#importType").val() === 'svn') {
					$(".seperatetd").show();
					$(".seperatetd").parent().show();
				}
				
				$("#importRepourl").val('');
				$('#importCredential').find('input[type=checkbox]').removeAttr("checked");
				$('input[name=headoption]').last().removeAttr("checked");
				$('input[name=headoption]').first().attr("checked", "checked");
				$('#revision').val('').attr("readonly","readonly");
				$('#gitUserName').val('');
				$('#gitPassword').val('');
				$('.branchval').val("");
				$('.passPhraseval').val('');
				$(".stream").val('');
				
				$("#importDotPhrescoA").removeAttr("data-toggle").removeAttr("href");
				$('#importDotPhrescoSrc').attr("checked", false);
				$("#importPhrescoRepourl").val('');
				$('#importPhrescoCredential').find('input[type=checkbox]').removeAttr("checked");
				$('input[name=phrescoHeadoption]').last().removeAttr("checked");
				$('input[name=phrescoHeadoption]').first().attr("checked", "checked");
				$('#phrescoRevision').val('').attr("readonly","readonly");
				$('#phrescoGitUserName').val('');
				$('#phrescoGitPassword').val('');
				$('.phrescoBranchval').val("");
				$('.phrescoPassPhraseval').val('');
				$(".phrescoStream").val('');
				
				$("#importTestA").removeAttr("data-toggle").removeAttr("href");
				$('#importTestSrc').attr("checked", false);
				$("#importTestRepourl").val('');
				$('#importTestCredential').find('input[type=checkbox]').removeAttr("checked");
				$('input[name=testHeadoption]').last().removeAttr("checked");
				$('input[name=testHeadoption]').first().attr("checked", "checked");
				$('#testRevision').val('').attr("readonly","readonly");
				$('#testGitUserName').val('');
				$('#testGitPassword').val('');
				$('.testBranchval').val("");
				$('.testPassPhraseval').val('');
				$(".testStream").val('');
				
				self.navigationListener.validateTextBox($("#importRepourl"), "");
				self.navigationListener.validateTextBox($("#importUserName"), "");
				self.navigationListener.validateTextBox($("#importPassword"), "");
				self.navigationListener.validateTextBox($("#revision"), "");
				self.navigationListener.validateTextBox($("#importPhrescoRepourl"), "");
				self.navigationListener.validateTextBox($("#importPhrescoUserName"), "");
				self.navigationListener.validateTextBox($("#importPhrescoPassword"), "");
				self.navigationListener.validateTextBox($("#phrescoRevision"), "");
				self.navigationListener.validateTextBox($("#importTestRepourl"), "");
				self.navigationListener.validateTextBox($("#importTestUserName"), "");
				self.navigationListener.validateTextBox($("#importTestPassword"), "");
				self.navigationListener.validateTextBox($("#testRevision"), "");
				self.opencc(this, "project_list_import", currentPrjName);
			});
			
			var checkbox = $("#importCredential").find('input[type=checkbox]');
			checkbox.unbind("click");
			checkbox.bind("click", function(){
				if(checkbox.is(':checked')) {
					$('#importUserName').removeAttr('readonly');
					$('#importPassword').removeAttr('readonly');
					$('#importUserName').val('');
					$('#importPassword').val('');
				} else {
					$('#importUserName').val(data.id);
					$('#importPassword').val(pass);
					$('#importUserName').attr('readonly','readonly');
					$('#importPassword').attr('readonly','readonly');
				}	
			});
			
			var phrCredCheckbox = $("#importPhrescoCredential").find('input[type=checkbox]');
			phrCredCheckbox.unbind("click");
			phrCredCheckbox.bind("click", function(){	
				if(phrCredCheckbox.is(':checked')) {
					$('#importPhrescoUserName').removeAttr('readonly');
					$('#importPhrescoPassword').removeAttr('readonly');
					$('#importPhrescoUserName').val('');
					$('#importPhrescoPassword').val('');
				} else {
					$('#importPhrescoUserName').val(data.id);
					$('#importPhrescoPassword').val(pass);
					$('#importPhrescoUserName').attr('readonly','readonly');
					$('#importPhrescoPassword').attr('readonly','readonly');
				}	
			});
			
			var testCredCheckbox = $("#importTestCredential").find('input[type=checkbox]');
			testCredCheckbox.unbind("click");
			testCredCheckbox.bind("click", function(){	
				if(testCredCheckbox.is(':checked')) {
					$('#importTestUserName').removeAttr('readonly');
					$('#importTestPassword').removeAttr('readonly');
					$('#importTestUserName').val('');
					$('#importTestPassword').val('');
				} else {
					$('#importTestUserName').val(data.id);
					$('#importTestPassword').val(pass);
					$('#importTestUserName').attr('readonly','readonly');
					$('#importTestPassword').attr('readonly','readonly');
				}	
			});
			
			$("button[name='importbtn']").unbind("click");
			$("button[name='importbtn']").bind("click", function() {
				var btnObj = $(this);
				self.navigationListener.validateImport(function(response) {
					if (!response) {
						self.showBtnLoading("button[name='importbtn']");
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
			
			$("#importDotPhrescoA, #importTestA").unbind("click");
			$("#importDotPhrescoA, #importTestA").bind("click", function() {
				var selectedType = $("#importType").val();
				$("#phrescoImportType").val(selectedType);
				$("#testImportType").val(selectedType);
			});
			
			$("#importDotPhrescoSrc").unbind("click");
			$("#importDotPhrescoSrc").bind("click", function() {
				var selectedType = $("#importType").val();
				$("#phrescoImportType").val(selectedType);
				$("#testImportType").val(selectedType);
				if ($(this).is(":checked")) {
					$("#importDotPhrescoA").attr("data-toggle", "tab").attr("href", "#importDotphresco");
					self.navigationListener.showDotPhrescoTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
				} else {
					$("#importDotPhrescoA").removeAttr("data-toggle").removeAttr("href");
					if ($(this).parent().hasClass("active")) {
						self.navigationListener.showSrcTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
					}
				}
			});
			
			$("#importTestSrc").unbind("click");
			$("#importTestSrc").bind("click", function() {
				var selectedType = $("#importType").val();
				$("#phrescoImportType").val(selectedType);
				$("#testImportType").val(selectedType);
				if ($(this).is(":checked")) {
					$("#importTestA").attr("data-toggle", "tab").attr("href", "#importTest");
					self.navigationListener.showTestTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
				} else {
					$("#importTestA").removeAttr("data-toggle").removeAttr("href");
					if ($(this).parent().hasClass("active")) {
						self.navigationListener.showSrcTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
					}
				}
			});
			
			$("input[name=headoption]").unbind("click");
			$("input[name=headoption]").bind("click", function() {
				$("#revision").removeClass("errormessage");
				$("#revision").removeAttr("placeholder");
				if("HEAD" === $(this).val()) {
					$("#revision").attr("readonly", "readonly");
				} else {
					$("#revision").removeAttr("readonly");
				}
			});
			
			$("input[name=phrescoHeadoption]").unbind("click");
			$("input[name=phrescoHeadoption]").bind("click", function() {
				$("#phrescoRevision").removeClass("errormessage");
				$("#phrescoRevision").removeAttr("placeholder");
				if("HEAD" === $(this).val()) {
					$("#phrescoRevision").attr("readonly", "readonly");
				} else {
					$("#phrescoRevision").removeAttr("readonly");
				}
			});
			
			$("input[name=testHeadoption]").unbind("click");
			$("input[name=testHeadoption]").bind("click", function() {
				$("#testRevision").removeClass("errormessage");
				$("#testRevision").removeAttr("placeholder");
				if("HEAD" === $(this).val()) {
					$("#testRevision").attr("readonly", "readonly");
				} else {
					$("#testRevision").removeAttr("readonly");
				}
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			
		},
		
		showHideImportAppCtrls : function(importType) {
			if (importType === "git") {
				$(".svndata").hide();
				$(".perforcedata").hide();
				$(".importCredential").hide();
				$(".tfsdata").hide();
				$(".gitdata").show();
			}
			if (importType === "svn") {
				$(".svndata").show();
				$(".importCredential").show();
				$(".gitdata").hide();
				$(".perforcedata").hide();
				$(".tfsdata").hide();
			}
			if (importType === "perforce") {
				$(".perforcedata").show();
				$(".svndata").hide();
				$(".gitdata").hide();
				$(".importCredential").hide();
				$(".tfsdata").hide();
				$(".bitkeeperdata").show();
			}
			if (importType === "bitkeeper") {
				$(".svndata").hide();
				$(".gitdata").hide();
				$(".perforcedata").hide();
				$(".importCredential").hide();
				$(".tfsdata").hide();
				$(".bitkeeperdata").show();
			}
			if (importType === "tfs") {
			    $('#importUserName').removeAttr('disabled','disabled');
				$('#importPassword').removeAttr('disabled','disabled');
				$('#importUserName').removeAttr('readonly','readonly');
				$('#importPassword').removeAttr('readonly','readonly');
				$('#importPhrescoUserName').removeAttr('disabled','disabled');
				$('#importPhrescoPassword').removeAttr('disabled','disabled');
				$('#importPhrescoUserName').removeAttr('readonly','readonly');
				$('#importPhrescoPassword').removeAttr('readonly','readonly');
				$('#importTestUserName').removeAttr('disabled','disabled');
				$('#importTestPassword').removeAttr('disabled','disabled');
				$('#importTestUserName').removeAttr('readonly','readonly');
				$('#importTestPassword').removeAttr('readonly','readonly');
				$('#importUserName').val('');
				$('#importPassword').val('');
				$('#importPhrescoUserName').val('');
				$('#importPhrescoPassword').val('');
				$('#importTestUserName').val('');
				$('#importTestPassword').val('');
				$(".svndata").show();
				$(".gitdata").hide();
				$(".perforcedata").hide();
				$(".importCredential").hide();
				$(".tfsdata").show();
				$(".revisionOption").hide();
			}
		}
	});

	return Clazz.com.components.navigation.js.navigation;
});
