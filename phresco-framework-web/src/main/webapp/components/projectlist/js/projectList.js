define(["framework/widgetWithTemplate", "projectlist/listener/projectListListener"], function() {
	
	Clazz.createPackage("com.components.projectlist.js");

	Clazz.com.components.projectlist.js.ProjectList = Clazz.extend(Clazz.WidgetWithTemplate, {
		projectsEvent : null,
		projectsHeader : null,
		templateUrl: commonVariables.contexturl + "/components/projectlist/template/projectList.tmp",
		configUrl: "../components/projectlist/config/config.json",
		name : commonVariables.projectlist,
		contentContainer : commonVariables.contentPlaceholder,
		projectslistListener : null,
		onProjectsEvent : null,
		projectRequestBody: {},
		data : null,
		onProjectEditEvent : null,
		registerEvents : null,
		repositoryEvent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.projectslistListener = new Clazz.com.components.projectlist.js.listener.ProjectsListListener;
			self.registerEvents(self.projectslistListener);
		},

		/*** 
		 * Called in once the login is success
		 *
		 */
		loadPage :function() {
			var self = this;
			Clazz.navigationController.push(this, true);
		},
		
		registerEvents : function(projectslistListener,repositoryListener) {
			var self = this;
			self.onProjectsEvent = new signals.Signal();
			self.onProjectEditEvent = new signals.Signal();
			self.onProjectEditEvent.add(projectslistListener.onEditProject, projectslistListener);			
			self.onProjectsEvent.add(projectslistListener.editApplication, projectslistListener); 

		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {	
			
		},

		preRender: function(whereToRender, renderFunction){
			$("#projectList").show();
			$("#createProject").hide();
			var self = this;
			self.projectslistListener.getProjectList(self.projectslistListener.getActionHeader(self.projectRequestBody, "get"), function(response) {
				var projectlist = {};
				projectlist.projectlist = response.data;				
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
			});
			
			$("#applicationedit").css("display", "none");
			$("#editprojectTab").css("display", "none");
			$("img[name=editproject]").unbind("click");
			$("img[name=editproject]").click(function(){
				self.onProjectEditEvent.dispatch($(this).attr('key'));
			});	
			
			$("#myTab li a").removeClass("act");
			$('a[name=editApplication]').click(function(){
				var value = $(this).closest("tr").attr("class");
				var techid = $(this).closest("tr").attr("techid");
				$("#myTab li#appinfo a").addClass("act");
				self.onProjectsEvent.dispatch(value , techid);
			});

			$(".tooltiptop").unbind("click");
			$(".tooltiptop").click(function() {
				var currentPrjName = $(this).closest("tr").attr("class");
				self.opencc(this, $(this).attr('name'), currentPrjName);
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
		}
	});

	return Clazz.com.components.projectlist.js.ProjectList;
});