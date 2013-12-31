define(["framework/widgetWithTemplate", "repository/listener/repositoryListener"], function() {
	Clazz.createPackage("com.components.repository.js");

	Clazz.com.components.repository.js.SourceRepository = Clazz.extend(Clazz.WidgetWithTemplate, {

		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/repository/template/sourceRepository.tmp",
		configUrl: "components/repository/config/config.json",
		name : commonVariables.sourceRepo,
		repositoryListener : null,
		createBranchEvent : null,
		createTagEvent : null,

		/***
		 * Called in initialization time of this class 
		 */
		initialize : function(){
			var self = this;
			
			if (self.repositoryListener === null) {
				self.repositoryListener = new Clazz.com.components.repository.js.listener.RepositoryListener();
			}
			self.registerEvents(self.repositoryListener);
		},
		
		registerEvents : function(repositoryListener) {
			var self = this;
			if (self.createBranchEvent === null) {
				self.createBranchEvent = new signals.Signal();
			}
			self.createBranchEvent.add(repositoryListener.createBranch, repositoryListener);
			
			if (self.createTagEvent === null) {
				self.createTagEvent = new signals.Signal();
			}
			self.createTagEvent.add(repositoryListener.createTag, repositoryListener);
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {	
			var self = this;
			var self = this;
			var requestBody = {};
			self.repositoryListener.repository(self.repositoryListener.getActionHeader(requestBody, "browseSourceRepo"), function(response) {
				var responseData = response.data;
				if (responseData !== undefined && responseData !== null && responseData.length > 0) {
					$.each(responseData, function(index, value) {
						self.repositoryListener.constructTree(value);
						if (responseData.length === index + 1) {
							setTimeout(function() {
								self.customScroll($(".tree_view"));
							}, 700);
						}
					});
				}
			});
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			
			$("input[name=rep_create]").unbind("click");
			$("input[name=rep_create]").bind("click", function() {
				if (commonVariables.callLadda) {
					Ladda.stopAll();
				}
				var selectedBranch = $("input[name=selectedBranchName]").val();
				$("#branchFromName").val(selectedBranch);
				$("#tagFromName").val(selectedBranch);
				self.opencc(this, $(this).attr('name'));
			});
			
			$("input[name=rep_release]").unbind("click");
			$("input[name=rep_release]").bind("click", function() {
				if (commonVariables.callLadda) {
					Ladda.stopAll();
				}
				self.opencc(this, $(this).attr('name'));
			});
			
			$("#createBranchVersion").unbind('click');
			$("#createBranchVersion").bind("click", function() {
				self.openVersionPopup();
			});
			
			$("#submitVersion").unbind('click');
			$("#submitVersion").bind("click", function() {
				$("#version_popup").toggle();
				var majorVersion = $("#majorVersion").val();
				var minorVersion = $("#minorVersion").val();
				var fixedVersion = $("#fixedVersion").val();
				var iterationType = $("#iterationType").val();
				var weekStart = Number($("#weekStart").val());
				
				var version = majorVersion + "." + minorVersion + "." + fixedVersion + "-SNAPSHOT"
				$("#createBranchVersion").val(version);
			});
			
			$("#createBranch").unbind('click');
			$("#createBranch").bind("click", function() {
				self.createBranchEvent.dispatch();
			});
			
			$("#createTag").unbind('click');
			$("#createTag").bind("click", function() {
				self.createTagEvent.dispatch();
			});
			
			self.customScroll($(".file_view"));
		},
		
		openVersionPopup : function() {
			var self = this;
			$('.content_main').addClass('z_index_ci');
			var clicked = $("#createBranchVersion");
			var target = $("#version_popup");
			var twowidth = window.innerWidth/1.5;
		
			if (clicked.offset().left < twowidth) {
				target.toggle();
				var a = target.height()/2;
				var b = clicked.height()/2;
				var t=clicked.offset().top + (b+12) - (a+12) ;
				var l=clicked.offset().left + clicked.width()+ 4;
				target.offset({
					top: t,
					left: l
				});
				target.addClass('speakstyleleft').removeClass('speakstyleright');
			} else {
				target.toggle();
				var t=clicked.offset().top - target.height()/2;
				var l=clicked.offset().left - (target.width()+ 15);
				target.offset({
					top: t,
					left: l
				});
				target.addClass('speakstyleright').removeClass('speakstyleleft');
			}
		},
	});

	return Clazz.com.components.repository.js.SourceRepository;
});