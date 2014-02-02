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
		releaseEvent : null,
		onShowHideConsoleEvent : null,

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
			
			if (self.releaseEvent === null) {
				self.releaseEvent = new signals.Signal();
			}
			self.releaseEvent.add(repositoryListener.release, repositoryListener);
			
			if (self.onShowHideConsoleEvent === null) {
				self.onShowHideConsoleEvent = new signals.Signal();
			}
			self.onShowHideConsoleEvent.add(repositoryListener.showHideConsole, repositoryListener);
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {	
			var self = this;
			var requestBody = {};
			self.repositoryListener.repository(self.repositoryListener.getActionHeader(requestBody, "browseSourceRepo"), function(response) {
				if (response.responseCode === "PHRSR10007") {
					var repoUrl = response.data[0];
					$('.modal-backdrop, #repoCredentials').remove();
					$(commonVariables.basePlaceholder).append('<div id="repoCredentials" class="modal fade errpopup hideContent" tabindex="-1"><div class="modal-body temp"><table><tr><td colspan="2"><span>URL : </span>'+repoUrl+'</td></tr><tr><td><span>User Name</span><sup>*</sup></td><td><span>Password</span><sup>*</sup></td></tr><tr><td><input type="text" id="repoUsername" style="margin-right:10px"></td><td><input type="password" id="repoPassword"></td></tr></table></div><div class="modal-footer"><input type="button" id="submitCredentials" class="btn btn_style" value="Ok"><input type="button" data-dismiss="modal" value="Cancel" class="btn btn_style" id="closeCredPopup"></div><input type="hidden" id="repoUrl" value="'+repoUrl+'"/></div>');
					$("#repoCredentials").modal("show");
					self.submitCredentials();
				} else {
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
					} else {
						$(".tree_view").hide();
						$("#messagedisp").show()
					}
				}
			});
			
			self.resizeConsoleWindow();
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			
			$("input[name=rep_create]").unbind("click");
			$("input[name=rep_create]").bind("click", function() {
				var selectedBranch = $("input[name=selectedBranchName]").val();
				$("#branchFromName").val(selectedBranch);
				$("#tagFromName").val(selectedBranch);
				$("#tagNameSkipTest").prop('checked', false);
				self.opencc(this, $(this).attr('name'));
			});
			
			$("#releaseVersion,#devVersion").bind('input propertychange', function() {
				var str = $(this).val();
				str = self.specialCharValidation(str);
				str = str.replace(/[^a-zA-Z 0-9\.\-\_]+/g, '');
				str = str.replace(/\s+/g, '');
				$(this).val(str);
			});
			
			$("input[name=rep_release]").unbind("click");
			$("input[name=rep_release]").bind("click", function() {
				$('#releaseSkipTests').prop('checked', false);
				self.opencc(this, $(this).attr('name'));
			});
			
			$("#createBranchVersion").unbind('click');
			$("#createBranchVersion").bind("click", function() {
				self.openVersionPopup();
			});
			
			$("#submitVersion").unbind('click');
			$("#submitVersion").bind("click", function() {
				$("#version_popup").hide();
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
			
			$("#release").unbind('click');
			$("#release").bind("click", function() {
				self.releaseEvent.dispatch();
			});
			
			$('#consoleImg').unbind("click");
			$('#consoleImg').click(function() {
				self.onShowHideConsoleEvent.dispatch();
			});
			
			self.customScroll($(".file_view"));
			self.customScroll($(".consolescrolldiv"));
		},
		
		submitCredentials : function() {
			var self = this;
			$('#submitCredentials').unbind("click");
			$('#submitCredentials').bind("click", function() {
				self.repositoryListener.saveCredentials();
			});
			
			$('#closeCredPopup').unbind("click");
			$('#closeCredPopup').bind("click", function() {
				commonVariables.navListener.onMytabEvent(commonVariables.sourceRepo);
			});
		},
		
		openVersionPopup : function() {
			var self = this;
			$('.content_main').addClass('z_index_ci');
			var clicked = $("#createBranchVersion");
			var target = $("#version_popup");
			var twowidth = window.innerWidth/1.5;
		
			if (clicked.offset().left < twowidth) {
				target.show();
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
				target.show();
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
