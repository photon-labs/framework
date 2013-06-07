define(["projectlist/listener/projectListListener"], function() {
	
	Clazz.createPackage("com.components.projectlist.js");

	Clazz.com.components.projectlist.js.ProjectList = Clazz.extend(Clazz.WidgetWithTemplate, {
		projectsEvent : null,
		projectsHeader : null,
		templateUrl: commonVariables.contexturl + "components/projectlist/template/projectList.tmp",
		configUrl: "components/projectlist/config/config.json",
		name : commonVariables.projectlist,
		contentContainer : commonVariables.contentPlaceholder,
		projectslistListener : null,
		onProjectsEvent : null,
		projectRequestBody: {},
		data : null,
		onProjectEditEvent : null,
		registerEvents : null,
		repositoryEvent : null,
		onAddRepoEvent : null,
		onAddCommitEvent : null,
		onAddUpdateEvent : null,
		onAddReportEvent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			if(self.projectslistListener === null)
				self.projectslistListener = new Clazz.com.components.projectlist.js.listener.ProjectsListListener;
			self.registerEvents(self.projectslistListener);
		},

		/*** 
		 * Called in once the login is success
		 *
		 */
		loadPage :function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(this, true);
		},
		
		registerEvents : function(projectslistListener,repositoryListener) {
			var self = this;
			if(self.onProjectsEvent === null)
				self.onProjectsEvent = new signals.Signal();
			if(self.onProjectEditEvent === null)	
				self.onProjectEditEvent = new signals.Signal();
			self.onProjectEditEvent.add(projectslistListener.onEditProject, projectslistListener);			
			self.onProjectsEvent.add(projectslistListener.editApplication, projectslistListener);
			
			if(self.onAddRepoEvent === null)
				self.onAddRepoEvent = new signals.Signal();
			self.onAddRepoEvent.add(projectslistListener.addRepoEvent, projectslistListener);
			
			if(self.onAddCommitEvent === null)
				self.onAddCommitEvent = new signals.Signal();
			self.onAddCommitEvent.add(projectslistListener.addCommitEvent, projectslistListener);
			
			if(self.onAddUpdateEvent === null)
				self.onAddUpdateEvent = new signals.Signal();
			self.onAddUpdateEvent.add(projectslistListener.addUpdateEvent, projectslistListener);
			
			if(self.onAddReportEvent === null)
				self.onAddReportEvent = new signals.Signal();
			self.onAddReportEvent.add(projectslistListener.addReportEvent, projectslistListener);

		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			commonVariables.navListener.showHideControls(commonVariables.projectlist);
		},

		preRender: function(whereToRender, renderFunction){
			var self = this;
			self.projectslistListener.getProjectList(self.projectslistListener.getActionHeader(self.projectRequestBody, "get"), function(response) {
				var projectlist = {};
				projectlist.projectlist = response.data;
				var userPermissions = JSON.parse(self.projectslistListener.projectListAPI.localVal.getSession('userPermissions'));
				projectlist.userPermissions = userPermissions;
				renderFunction(projectlist, whereToRender);
			});
		},

		getAction : function(actionBody, action, callback) {
			var self = this;
			
			self.projectslistListener.projectListAction(self.projectslistListener.getActionHeader(actionBody, action), function(response) {
				self.loadPage();
			});
		},
		
	
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			$(".tooltiptop").tooltip();
			$(".dyn_popup").hide();
			
			var w1 = $(".scrollContent tr:nth-child(2) td:first-child").width();
			var w2 = $(".scrollContent tr:nth-child(2) td:nth-child(2)").width();
			var w3 = $(".scrollContent tr:nth-child(2) td:nth-child(3)").width();
			var w4 = $(".scrollContent tr:nth-child(2) td:nth-child(4)").width();
			var w5 = $(".scrollContent tr:nth-child(2) td:nth-child(5)").width();
			var w6 = $(".scrollContent tr:nth-child(2) td:nth-child(6)").width();
			
			$(".fixedHeader tr th:first-child").css("width",w1);
			$(".fixedHeader tr th:nth-child(2)").css("width",w2);
			$(".fixedHeader tr th:nth-child(3)").css("width",w3);
			$(".fixedHeader tr th:nth-child(4)").css("width",w4);
			$(".fixedHeader tr th:nth-child(5)").css("width",w5);
			$(".fixedHeader tr th:nth-child(6)").css("width",w6);
			
			$(window).resize(function() {
				var w1 = $(".scrollContent tr:nth-child(2) td:first-child").width();
				var w2 = $(".scrollContent tr:nth-child(2) td:nth-child(2)").width();
				var w3 = $(".scrollContent tr:nth-child(2) td:nth-child(3)").width();
				var w4 = $(".scrollContent tr:nth-child(2) td:nth-child(4)").width();
				var w5 = $(".scrollContent tr:nth-child(2) td:nth-child(5)").width();
				var w6 = $(".scrollContent tr:nth-child(2) td:nth-child(6)").width();
				
				$(".fixedHeader tr th:first-child").css("width",w1);
				$(".fixedHeader tr th:nth-child(2)").css("width",w2);
				$(".fixedHeader tr th:nth-child(3)").css("width",w3);
				$(".fixedHeader tr th:nth-child(4)").css("width",w4);
				$(".fixedHeader tr th:nth-child(5)").css("width",w5);
				$(".fixedHeader tr th:nth-child(6)").css("width",w6);
				self.windowResize();
			});			
			
			$(".proj_list .scrollContent").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced:{ updateOnContentResize: true}
			});
			
			$("#applicationedit").css("display", "none");
			$("#editprojectTab").css("display", "none");
			$("img[name=editproject]").unbind("click");
			$("img[name=editproject]").click(function(){
				self.onProjectEditEvent.dispatch($(this).attr('key'));
			});	
			
			$("#myTab li a").removeClass("act");
			$('a[name=editApplication]').click(function(){
				commonVariables.appDirName = $(this).closest("tr").attr("class");
				var value = $(this).closest("tr").attr("class");
				var techid = $(this).closest("tr").attr("techid");
				commonVariables.techId = techid;
				$("#myTab li#appinfo a").addClass("act");
				self.onProjectsEvent.dispatch(value , techid);
			});

			$(".tooltiptop").unbind("click");
			$(".tooltiptop").click(function() {
				var currentPrjName = $(this).closest("tr").attr("class");
				self.opencc(this, $(this).attr('name'), currentPrjName);
				var data = JSON.parse(self.projectslistListener.projectListAPI.localVal.getSession('userInfo'));
				userId = data.id;
				$('#uname').val(data.id);
				$('#pwd').val(data.password);
				
				$('#commitUsername').val(data.id);
				$('#commitPassword').val(data.password);
				
				$('#updateUsername').val(data.id);
				$('#updatePassword').val(data.password);
			});
			
			$("a[name = 'updatesvn']").unbind("click");
			$("a[name = 'updatesvn']").bind("click",function(){
				$("#svn_update").show();
			});
			$("input[name='deleteConfirm']").unbind('click');
			$("input[name='deleteConfirm']").click(function(e) {
				var deletearray = [];
				var deleteproject = $(this).parent().parent().attr('currentPrjName');
				deletearray.push(deleteproject);
				self.getAction(deletearray,"delete");
			});

			$("input[name='holeDelete']").unbind('click');
			$("input[name='holeDelete']").click(function(e) {
				var projectnameArray = [];
				var currentRow =  $(this).parents().parent("td.delimages").parent().next();
				while(currentRow != null && currentRow.length > 0) {
				   var classname = currentRow.attr("class");
				   if(classname != "proj_title") {
				        currentRow = currentRow.next('tr');
				        projectnameArray.push(classname);
				   }else {currentRow = null}
				}
				self.getAction(projectnameArray,"delete");
			});			
			
			$("#repocredential").unbind("click");
			$("#repocredential").click(function() {
				if ($('#repocredential').is(':checked')) {
					$("#uname").val('');
					$("#pwd").val('');
				} else {
					var data = JSON.parse(self.projectslistListener.projectListAPI.localVal.getSession('userInfo'));
					$('#uname').val(data.id);
					$('#pwd').val(data.password);
				}
			});			
					
			$("input[name='addrepobtn']").unbind("click");
			$("input[name='addrepobtn']").click(function() {
				var dynid = $(this).attr('id');
				self.onAddRepoEvent.dispatch($(this), dynid);				
			});			
			
			$("#commitCredential").click(function() {
				if ($('#commitCredential').is(':checked')) {
					$("#commitUsername").val('');
					$("#commitPassword").val('');
				} else {
					var data = JSON.parse(self.projectslistListener.projectListAPI.localVal.getSession('userInfo'));
					$('#commitUsername').val(data.id);
					$('#commitPassword').val(data.password);
				}
			});
			
			$("input[name='commitbtn']").unbind("click");
			$("input[name='commitbtn']").click(function() {
				var dynid = $(this).attr('id');
				self.onAddCommitEvent.dispatch($(this), dynid);				
				
			});
			
			$("#updateCredential").click(function() {
				if ($('#updateCredential').is(':checked')) {
					$("#updateUsername").val('');
					$("#updatePassword").val('');
				} else {
					var data = JSON.parse(self.projectslistListener.projectListAPI.localVal.getSession('userInfo'));
					$('#updateUsername').val(data.id);
					$('#updatePassword').val(data.password);
				}
			});
			
			$("input[name='updatebtn']").unbind("click");
			$("input[name='updatebtn']").click(function() {
				var dynid, revision
				dynid = $(this).attr('id');
				var revision = $("input[name='revision']:checked").val();
				if(revision !== ""){
					revision = revision;
				} else{
					revision = $("#revision_"+dynid).val();
				}				
				self.onAddUpdateEvent.dispatch($(this), dynid, revision);				
			});

			$("input[name='generate']").unbind("click");
			$("input[name='generate']").click(function() {
				self.onAddReportEvent.dispatch();				
			});
			
			$("input[name='revision']").unbind("click");
			$("input[name='revision']").click(function() {
				if($(this).is(':checked')) {
					var value = $(this).val();
				}
			});
			self.windowResize();
			
		}
	});

	return Clazz.com.components.projectlist.js.ProjectList;
});