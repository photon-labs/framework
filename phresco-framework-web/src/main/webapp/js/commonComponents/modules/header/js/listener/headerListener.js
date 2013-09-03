define(["header/api/headerAPI"], function() {

	Clazz.createPackage("com.commonComponents.modules.header.js.listener");

	Clazz.com.commonComponents.modules.header.js.listener.HeaderListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		//loadingScreen : null,
		headerAPI : null,
		projectListContent: null,
		currentTab : null,
		
		initialize : function(config){
			if(this.headerAPI === null){
				this.headerAPI = new Clazz.com.commonComponents.modules.header.js.api.HeaderAPI;
			}	
		},
		
		doLogout : function(){
			this.clearSession();
			Clazz.navigationController.jQueryContainer = commonVariables.basePlaceholder;
			this.removePlaceholder();
			this.headerAPI.localVal.setSession('loggedout', 'true');
			location.hash = '';
			location.reload();
		},
		
		clearSession : function(){
			var username = this.headerAPI.localVal.getSession('username'), password = this.headerAPI.localVal.getSession('password'), rememberMe = this.headerAPI.localVal.getSession('rememberMe');
			
			this.headerAPI.localVal.clearSession();
			
			if(rememberMe === "true"){
				this.headerAPI.localVal.setSession('username', username);
				this.headerAPI.localVal.setSession('password', password);
				this.headerAPI.localVal.setSession('rememberMe', "true");
			}
		},
		
		loadTab : function(){
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			
			if(self.currentTab === "Dashboard"){
			}else if(self.currentTab === "Projects"){
				commonVariables.navListener.getMyObj(commonVariables.projectlist, function(retVal) {
					self.loadTabRender(retVal);
				});
			}else if(self.currentTab === "Settings"){
				commonVariables.navListener.getMyObj(commonVariables.settings, function(retVal) {
					self.loadTabRender(retVal);
				});
			}else if(self.currentTab === "Downloads"){
			}else if(self.currentTab === "Admin"){
			}	
		},
		
		loadTabRender : function(currentObj){
			var self=this;
			if(currentObj !== null){
				Clazz.navigationController.push(currentObj, commonVariables.animation);
			}
			
		},

		removePlaceholder : function() {
			$(commonVariables.headerPlaceholder).remove();
			$(commonVariables.headerPlaceholder).empty();
			
			$(commonVariables.navigationPlaceholder).remove();
			$(commonVariables.navigationPlaceholder).empty();
			
			$(commonVariables.contentPlaceholder).remove();
			$(commonVariables.contentPlaceholder).empty();
			
			$(commonVariables.footerPlaceholder).remove();
			$(commonVariables.footerPlaceholder).empty();
		},
		
		selectCoustomer : function(customerValue, customerId) {
			var self=this;
			self.headerAPI.localVal.deleteSession("Front End");
			self.headerAPI.localVal.deleteSession("Middle Tier");
			self.headerAPI.localVal.deleteSession("CMS");
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			$("#selectedCustomer").text(customerValue);
			if (commonVariables.navListener.currentTab === "projectlist" || commonVariables.navListener.currentTab === "addproject" || commonVariables.navListener.currentTab === "editproject") {
				commonVariables.navListener.getMyObj(commonVariables.projectlist, function(retVal) {
					obj = retVal;
					Clazz.navigationController.push(obj, commonVariables.animation);
				});
			}

			var data = {};
			data.customerId = customerId;
			self.performAction(self.getActionHeader("getCustomerTheme", data), function(response) {
				$('#bannerlogo').attr("src", "data:image/png;base64," + response.data.logo);
				JSS.css(response.data.theme);
			});

			self.showHideMainMenu(customerValue);
		},

		//To show hide the main menu(Dashboard, Projects, Settings, Downloads, Admin) based on the customer
		showHideMainMenu : function (customerName) {
			var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			var customers = userInfo.customers;
			$.each(customers, function(index, value) {
				if (value.name === customerName) {
					var customerOptions = value.options;
					if (jQuery.inArray(commonVariables.optionsDashboard, customerOptions) === -1) {
						$('#dashboardMenu').hide();
					} else {
						$('#dashboardMenu').show();
					}

					if (jQuery.inArray(commonVariables.optionsHelp, customerOptions) === -1) {
						$('#helpMenu').hide();
					} else {
						$('#helpMenu').show();
					}

					if (jQuery.inArray(commonVariables.optionsSettings, customerOptions) === -1) {
						$('#settingsMenu').hide();
					} else {
						$('#settingsMenu').show();
					}

					if (jQuery.inArray(commonVariables.optionsDownload, customerOptions) === -1) {
						$('#downloadsMenu').hide();
					} else {
						$('#downloadsMenu').show();
					}

					if (jQuery.inArray(commonVariables.optionsAdmin, customerOptions) === -1) {
						$('#adminMenu').hide();
					} else {
						$('#adminMenu').show();
					}
					return false;
				}
			});
		},

		aboutPhresco : function(openccObj) {
			var self = this;
			self.performAction(self.getActionHeader("upgradeAvailable"), function(response) {
				if (response.data !== null && response.status === "success") {
					$("#currentVersion").text(response.data.currentVersion);
					$("#latestVersion").text(response.data.latestVersion);
					$("#upgradeSuccess").hide();
					$("#aboutDesc").show();
					$("#versionTable").show();
					$("#upgradeSuccess").empty();
					$("#upgradeSuccess").hide();
					$("#upgradeError").empty();
					$("#upgradeError").hide();
					if (response.data.updateAvaillable) {
						$("#upgradeDisable").hide();
						$("#upgrade").show();
					} else {
						$("#upgradeDisable").show();
						$("#upgrade").hide();
					}
					self.opencc(openccObj, "aboutPopup", '', 'upgrade');
				}
			});

		},

		upgradePhresco : function() {
			var self = this;
			self.performAction(self.getActionHeader("upgradePhresco"), function(response) {
				$("#upgrade").hide();
				if (response.status === "success") {
					var currentVersion  = $("#currentVersion").text();
					$("#aboutDesc").hide();
					$("#versionTable").hide();
					$("#upgradeError").hide();
					$("#upgradeSuccess").html(currentVersion + " has been upgraded successfully.<br> Phresco is up to date. Please restart.");
					$("#upgradeSuccess").show();
				} else if (response.status === "error" || response.status === "failure") {
					$("#upgradeSuccess").hide();
					$("#upgradeError").text("Failed to upgrade");
					$("#upgradeError").show();
				}	
			});
		},

		getActionHeader : function(action, requestBody) {
			var self = this, header;
			
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			};
			var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			var userId = userInfo.id;
			if (action === "upgradeAvailable") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.upgrade + '/' +commonVariables.upgradeAvailable + "?userId="+userId;				
			}
			if (action === "upgradePhresco") {
				var newVersion = $("#latestVersion").text();
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.upgrade + "?newVersion="+ newVersion + "&userId=" +userId+ "&customerId="+self.getCustomer();
			}
			if (action === "getCustomerTheme") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl+ "customer/theme?userId="+userId+"&customerId="+requestBody.customerId;				
			}
			return header;
		},

		performAction : function (header, callback) {
			var self = this;
			try {
				commonVariables.api.ajaxRequest(header, function(response) {
					if (response !== null) {
						callback(response);
					} else {
						$(".content_end").show();
						$(".msgdisplay").removeClass("success").addClass("error");
						$(".error").attr('data-i18n', 'errorCodes.' + response.responseCode);
						self.renderlocales(commonVariables.contentPlaceholder);	
						$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
						setTimeout(function() {
							$(".content_end").hide();
						},2500);
					}
				},
				function(textStatus){
					$(".content_end").show();
					$(".msgdisplay").removeClass("success").addClass("error");
					$(".error").attr('data-i18n', 'commonlabel.errormessage.serviceerror');
					self.renderlocales(commonVariables.contentPlaceholder);		
					$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
					setTimeout(function() {
						$(".content_end").hide();
					},2500);
				}
				);
			} catch (exception) {
				
			}
		},
	});

	return Clazz.com.commonComponents.modules.header.js.listener.HeaderListener;
});