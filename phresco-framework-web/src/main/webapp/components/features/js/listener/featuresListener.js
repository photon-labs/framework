define(["features/features",  "application/application",  "projectlist/projectList"], function() {

	Clazz.createPackage("com.components.features.js.listener");

	Clazz.com.components.features.js.listener.FeaturesListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		appinfoContent : null,
		projectListContent : null,
		flagged:null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;	
		},
		
		cancelUpdate : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.projectListContent = new Clazz.com.components.projectlist.js.ProjectList();
			Clazz.navigationController.push(self.projectListContent, true, true);
		},

		search : function (txtSearch, divId, classval){
			var self = this;
       		var txtSearch = txtSearch.toLowerCase();
			if(classval === "switch switchOff") {
				if (txtSearch !== "") {
					$("#"+divId+" li").hide();//To hide all the ul
					var hasRecord = false;				
					var i=0;
					$("#"+divId+" li").each(function() {//To search for the txtSearch and search option thru all td
						var tdText = $(this).text().toLowerCase();
						if (tdText.match(txtSearch)) {
							$(this).show();
							hasRecord = true;
							i++;
						}
						
					});
					if (hasRecord === false) {
						if(divId === "moduleContent"){
							$("#norecord1").show();
						} if(divId === "jsibrariesContent"){
							$("#norecord2").show();
						} if(divId === "componentsContent"){
							$("#norecord3").show();
						}					
					} else {
						self.norecordHide(divId);
					}
				}
				else {				
					$("#"+divId+" li").show();
					self.norecordHide(divId);
				}
				self.scrollbarUpdate();
			} else {
				
				if (txtSearch !== "") {
					$("#"+divId+" li").hide();//To hide all the ul
					var hasRecord = false;				
					var i=0;
					$("#"+divId+" li").each(function(index, value) {//To search for the txtSearch and search option thru all td
						if($(value).find("fieldset").attr('class') === "switch switchOn default" || $(value).find("fieldset").attr('class') === "switch switchOn" || $(value).find("fieldset").attr('class') === "switch default switchOn"){
							var tdText = $(this).text().toLowerCase();
							if (tdText.match(txtSearch)) {
								$(this).show();
								hasRecord = true;
								i++;
							}
						}	
						
					});
					if (hasRecord === false) {
						if(divId === "moduleContent"){
							$("#norecord1").show();
						} if(divId === "jsibrariesContent"){
							$("#norecord2").show();
						} if(divId === "componentsContent"){
							$("#norecord3").show();
						}					
					} else {
						self.norecordHide(divId);
					}
				}
				else {
					$("#"+divId+" li").each(function(index, value) {
						if($(value).find("fieldset").attr('class') === "switch switchOn default" || $(value).find("fieldset").attr('class') === "switch switchOn" || $(value).find("fieldset").attr('class') === "switch default switchOn"){
							$(this).show();
							self.norecordHide(divId);
						}
					});	
				} 
				self.scrollbarUpdate();
			}
       	},
	
		norecordHide : function(divId) {
			if(divId === "moduleContent"){
				$("#norecord1").hide();				
			} if(divId === "jsibrariesContent"){
				$("#norecord2").hide();
			} if(divId === "componentsContent"){
				$("#norecord3").hide();
			}
		},

       	getFeaturesList : function(header, callback) {
			var self = this;
			try {
				//commonVariables.loadingScreen.showLoading();
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							//commonVariables.loadingScreen.removeLoading();
							if(response.responseCode === 'PHR400006') {
								commonVariables.api.showError(response.responseCode ,"success", true, false, true);	
							}
							callback(response);
						} else {
							//commonVariables.loadingScreen.removeLoading();
							commonVariables.api.showError(response.responseCode ,"error", true, false, true);	
						}

					},

					function(textStatus) {
						//commonVariables.loadingScreen.removeLoading();
						commonVariables.api.showError("serviceerror" ,"error", true, false, true);	
					}
				);
			} catch(exception) {
				//commonVariables.loadingScreen.removeLoading();
			}

		},
		
		getFeaturesUpdate : function(header, callback) {
			var self = this;
			try {
				//commonVariables.loadingScreen.showLoading();
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							//commonVariables.loadingScreen.removeLoading();
							if(response.responseCode === "PHR200007") {
								commonVariables.api.showError(response.responseCode ,"success", true, false, true);
							}	
							callback(response);
						} else {
							//commonVariables.loadingScreen.removeLoading();
							if(response.responseCode === "PHR210008") {
								commonVariables.api.showError(response.responseCode ,"error", true, false, true);
							}	
							
						}
					},

					function(textStatus) {
						commonVariables.api.showError("serviceerror" ,"error", true, false, true);
					}
				);
			} catch(exception) {
				//commonVariables.loadingScreen.removeLoading();
			}
		},
		
		showLoad : function(){
			var self = this;
			//commonVariables.loadingScreen.showLoading();
		},
		
		scrollbarEnable : function(){
			var self=this;
			if(self.flagged === 2) {
				self.customScroll($(".features_box").find(".feature_content"));
				$(".dyn_popup").hide();
			}
			
		},
		
		scrollbarUpdate : function(){
			$("#content_1").mCustomScrollbar("update"); 
			$("#content_2").mCustomScrollbar("update"); 
			$("#content_3").mCustomScrollbar("update");
			$(".features_cont").mCustomScrollbar("update"); 	
		},
		
		showSelected : function(eachList) {
			var self = this, val;
			$("#moduleContent li").hide();
			$("#jsibrariesContent li").hide();
			$("#componentsContent li").hide();
			if (eachList === '') {
				val = $("ul li fieldset");
			} else {
				val = eachList;
			}
			val.each(function() {
				if($(this).attr("class") === "switch default" || $(this).attr("class") === "switch default switchOn" || $(this).attr("class") === "switch switchOn" || $(this).attr("class") === "switch default switchOff"){
					$(this).parent().show();
					self.scrollbarUpdate();					
				}
			});			
		},
		
		bcheck : function(obj, buttonId){
			var self = this;
			var button = $(obj).attr("value");
			var versionID = $(obj).parent().next('div.flt_right').children('select.input-mini').find(':selected').val();
			$(obj).closest('fieldset').removeClass('switchOn'); 
			$(obj).closest('fieldset').removeClass('switchOff');			
			if(button === 'false'){
				$(obj).closest('fieldset').addClass('switchOff');
				$(obj).closest('fieldset').attr('value', "false");
			}else if(button === 'true'){
				$(obj).closest('fieldset').addClass('switchOn');
				$(obj).closest('fieldset').attr('value', "true");
			}
			if(buttonId === undefined){
				self.defendentmodule(versionID, button);
			}

		},
		
		defendentmodule : function(versionID, button){
			var self = this;
			self.getFeaturesUpdate(self.getRequestHeader(self.featureUpdatedArray, "DEPENDENCY", versionID), function(response) {
			if(response.data !== null){
					$.each(response.data, function(index, value){
						$("select.input-mini option").each(function(index, currentVal) {
						var uiId = $(this).val();
							if(value === uiId){
								if(button === 'true'){
									$(currentVal).parents("div").siblings("fieldset").removeClass('switchOff').addClass("switchOn");
									$(this).attr("selected", "selected");
									var showversionId = $(currentVal).parents("div").attr("id");
									$("#"+showversionId).show();
								} else if(button === 'false'){
									$(currentVal).parents("div").siblings("fieldset").removeClass('switchOn').addClass("switchOff");
									var showversionId = $(currentVal).parents("div").attr("id");
									$("#"+showversionId).hide();
								}
							} 
						}); 
					});
				}	
			});
		},

		hideLoad : function(){
			var self = this;
			//commonVariables.loadingScreen.removeLoading();
		},

		getRequestHeader : function(projectRequestBody, type, descid) {
			var url, self = this, appDirName = '', userId, custname, techId, header, projectInfo;
			custname = self.getCustomer();
			userId = JSON.parse(commonVariables.api.localVal.getSession("userInfo"));
			if(commonVariables.api.localVal.getProjectInfo() !== null){
				appdetails = commonVariables.api.localVal.getProjectInfo();
				appDirName = appdetails.data.projectInfo.appInfos[0].appDirName;
				techId = appdetails.data.projectInfo.appInfos[0].techInfo.id;
			}
			header = {
				contentType: "application/json",
				dataType: "json"
			};
			if(type === "FEATURE" || type === "JAVASCRIPT" || type === "COMPONENT"){
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.featurePageContext+"/list?customerId="+custname+"&techId="+ (techId !== null? techId : "") +"&type="+type+"&userId="+ (userId !== null? userId.id : "");
			} else if (type === "desc") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.featurePageContext+"/desc?artifactGroupId="+descid+"&userId="+(userId !== null? userId.id : "");
			} else if (type === "SELECTED") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.featurePageContext+"/selectedFeature?userId="+(userId !== null? userId.id : "")+"&appDirName="+(appDirName !== null? appDirName : "");
			} else if (type === "UPDATE") {
				var displayName="", userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
				if (userInfo !== null) {
					displayName = userInfo.displayName;
				}
				header.requestMethod = "PUT";
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.projectlistContext + "/updateFeature?customerId="+custname+"&userId="+(userId !== null? userId.id : "")+"&appDirName="+(appDirName !== null? appDirName : "")+"&displayName="+displayName;
			} else if (type === "DEPENDENCY") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.featurePageContext + "/dependencyFeature?userId="+(userId !== null? userId.id : "")+"&versionId="+descid; // descid is versionId
			} else if (type === "populate") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.featurePageContext + "/populate?userId="+(userId !== null? userId.id : "")+"&customerId="+custname+"&featureName="+projectRequestBody.featureName+"&appDirName="+(appDirName !== null? appDirName : "");
			} else if (type === "configureFeature") {
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.featurePageContext + "/configureFeature?userId="+(userId !== null? userId.id : "")+"&customerId="+custname+"&featureName="+projectRequestBody.featureName+"&appDirName="+(appDirName !== null? appDirName : "")+"&"+projectRequestBody.serval;
			}
			return header;
		}
		
	});

	return Clazz.com.components.features.js.listener.FeaturesListener;
});