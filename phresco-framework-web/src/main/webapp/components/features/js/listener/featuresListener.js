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
			if (txtSearch !== "" && txtSearch !== undefined) {
				var txtSearch = txtSearch.toLowerCase();
				if(classval === "switch switchOff") {
					if (txtSearch !== "" && txtSearch !== undefined) {
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
			} else {
			
					$("#"+divId+" li").show();
					self.norecordHide(divId);
			}
			self.scrollbarUpdate();
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
								if($(".msgdisplay").hasClass("error")) {
									$(".msgdisplay").removeClass("error");
								}
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
								if($(".msgdisplay").hasClass("error")) {
									$(".msgdisplay").removeClass("error");
								}
								commonVariables.api.showError(response.responseCode ,"success", true, false, true);
							}	
							callback(response);
						} else {
							//commonVariables.loadingScreen.removeLoading();
							if(response.responseCode === "PHR210008") {
								commonVariables.api.showError(response.responseCode ,"error", true, false, true);
							} else if(response.responseCode === "PHR410004") {
								callback(response);
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
			var currentclick, hasclass1, hasclass2, uiId1, uiId2, popupcount = 0, onpointer=null;
			self.getFeaturesUpdate(self.getRequestHeader(self.featureUpdatedArray, "DEPENDENCY", versionID), function(response) {
					if(response.data !== null){
						$.each(response.data[versionID], function(index, value){
							$("select.input-mini option").each(function(index, currentVal) {
								if(currentVal.value === versionID) {
									currentclick = currentVal;
								}
								var uiId = $(this).val();
								if(value === uiId){
									if(button === 'true'){
										$(currentVal).parents("div").siblings("fieldset").removeClass('switchOff').addClass("switchOn");
										$(this).attr("selected", "selected");
										var showversionId = $(currentVal).parents("div").attr("id");
										$("#"+showversionId).show();
									} else if(button === 'false'){
										self.getFeaturesUpdate(self.getRequestHeader(self.featureUpdatedArray, "requireddependancies", versionID), function(response) {
											if(response.data.length === 0) {
												$(currentclick).parents("div").siblings("fieldset").removeClass('switchOn').addClass("switchOff");
												$(currentclick).parents("div").siblings("fieldset").attr('value','false');
												var showversionId = $(currentclick).parents("div").attr("id");
												$("#"+showversionId).hide();
											}
										});
										self.getFeaturesUpdate(self.getRequestHeader(self.featureUpdatedArray, "requireddependancies", value), function(response) {
											var counter_new = 0, counter_new2 = 0;
											if(response.data !== null && response.data.length !== 0) {
												$.each(response.data, function(index, value1){														
													hasclass1 = $(currentVal).parents("div").siblings("fieldset").attr('class');
													$("select.input-mini option").each(function(index, currentValue) {
														uiId2 = $(this).val();
														if(value1 === uiId2) {
															var hasclassed = $(currentValue).parents("div").siblings("fieldset").attr('class');
															if(hasclassed === 'switch switchOn') {
																counter_new++;
															}	
														}
													});	
												});		
													$("select.input-mini option").each(function(index, currentValue) {
														uiId2 = $(this).val();
														if(counter_new2 === 0) {
															if(value === uiId2) {
																if(counter_new === 0) {
																	counter_new2 = 1;
																	$(currentValue).parents("div").siblings("fieldset").removeClass('switchOn').addClass("switchOff");
																	var showversionId = $(currentValue).parents("div").attr("id");
																	$("#"+showversionId).hide();
																}
															}
														}	
													});													
											} /* else {			
												$(currentVal).parents("div").siblings("fieldset").removeClass('switchOn').addClass("switchOff");
												var showversionId = $(currentVal).parents("div").attr("id");
												$("#"+showversionId).hide();
											} */
										});	
									}	
								} 
							}); 
						});
					}	
				else {
					if(button === 'false'){
						self.getFeaturesUpdate(self.getRequestHeader(self.featureUpdatedArray,"requireddependancies",versionID), function(response) {
						$("select.input-mini option").each(function(index, currentVal) {	
							var counter_new = 0;
							if(response.data !== null && response.data.length !== 0) {
								$.each(response.data, function(index, value1){			
									hasclass1 = $(currentVal).parents("div").siblings("fieldset").attr('class');
									$("select.input-mini option").each(function(index, currentValue) {
										uiId2 = $(this).val();
										if(value1 === uiId2) {
											var hasclassed = $(currentValue).parents("div").siblings("fieldset").attr('class');
											if(hasclassed === 'switch switchOn') {
												counter_new++;
											}	
										}
									});	
								});		
								$("select.input-mini option").each(function(index, currentValue) {
									uiId2 = $(this).val();
									if(versionID === uiId2) {
										if(counter_new === 0) {
											$(currentValue).parents("div").siblings("fieldset").removeClass('switchOn').addClass("switchOff");
											var showversionId = $(currentValue).parents("div").attr("id");
											$("#"+showversionId).hide();
										} else {
											popupcount++;
											onpointer = currentValue;
											$(currentValue).parents("div").siblings("fieldset").removeClass('switchOff').addClass("switchOn");
											$(currentValue).parents("div").siblings("fieldset").attr('value','true');
											var showversionId = $(currentValue).parents("div").attr("id");
											$("#"+showversionId).show();
										}
									}
								});													
							} /* else {													
								$(currentVal).parents("div").siblings("fieldset").removeClass('switchOn').addClass("switchOff");
								var showversionId = $(currentVal).parents("div").attr("id");
								$("#"+showversionId).hide();
							} */
						});	
							if(popupcount !==0) {
								commonVariables.api.showError("hasdependencies" ,"error", true, false, true);	
								if(onpointer !== null) {
									var valtoprepend = $(onpointer).parents("div").siblings("fieldset").parent().attr('name');
									$('.msgdisplay').prepend(valtoprepend + ' ');
								}	
								/* var depid = $(obj).parent().attr('depid');
								$("#dependancypopup").children().remove();
								var toappend = '<div id="'+depid+'" class="newdyn_popup"><p>This feature has dependencies.</p><div style="float:right;"><input class="btn btn_style" type="button" value="Close"></div></div>';		
								$("#dependancypopup").append(toappend);														
								self.popupforDesc(obj,depid); 
								$("#"+depid).show(); */
							}
						});
					}
				}				
			});
		},

		hideLoad : function(){
			var self = this;
			//commonVariables.loadingScreen.removeLoading();
		},

		getRequestHeader : function(projectRequestBody, type, descid) {
			var url, self = this, appDirName = '', userId, custname, techId, header, projectInfo, rootModule;
			custname = self.getCustomer();
			userId = JSON.parse(commonVariables.api.localVal.getSession("userInfo"));
			var moduleParam = self.isBlank($('.moduleName').val()) ? "" : '&moduleName='+$('.moduleName').val();
			if(commonVariables.api.localVal.getProjectInfo() !== null){
				appdetails = commonVariables.api.localVal.getProjectInfo();
				appDirName = appdetails.data.projectInfo.appInfos[0].appDirName;
				techId = appdetails.data.projectInfo.appInfos[0].techInfo.id;
			}
			appDirName = self.isBlank(moduleParam) ? appDirName : $('.rootModule').val();
			rootModule = self.isBlank($('.rootModule').val()) ? "" : '&rootModule='+$('.rootModule').val();
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
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.featurePageContext+"/selectedFeature?userId="+(userId !== null? userId.id : "")+"&appDirName="+(appDirName !== null? appDirName : "")+moduleParam;
			} else if (type === "UPDATE") {
				var displayName="", userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
				if (userInfo !== null) {
					displayName = userInfo.displayName;
				}
				header.requestMethod = "PUT";
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.projectlistContext + "/updateFeature?customerId="+custname+"&userId="+(userId !== null? userId.id : "")+"&appDirName="+(appDirName !== null? appDirName : "")+"&displayName="+displayName + moduleParam +rootModule;
			} else if (type === "DEPENDENCY") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.featurePageContext + "/dependencyFeature?userId="+(userId !== null? userId.id : "")+"&versionId="+descid; // descid is versionId
			} else if (type === "populate") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.featurePageContext + "/populate?userId="+(userId !== null? userId.id : "")+"&customerId="+custname+"&featureName="+projectRequestBody.featureName+"&appDirName="+(appDirName !== null? appDirName : "") + moduleParam;
			} else if (type === "configureFeature") {
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.featurePageContext + "/configureFeature?userId="+(userId !== null? userId.id : "")+"&customerId="+custname+"&featureName="+projectRequestBody.featureName+"&appDirName="+(appDirName !== null? appDirName : "")+"&"+projectRequestBody.serval + moduleParam;
			} else if (type === "requireddependancies") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.featurePageContext + "/dependentToFeatures?userId="+(userId !== null? userId.id : "")+"&versionId="+descid+"&techId="+ (techId !== null? techId : "") + moduleParam;
			}
			return header;
		}
		
	});

	return Clazz.com.components.features.js.listener.FeaturesListener;
});