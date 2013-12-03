define([], function() {

	Clazz.createPackage("com.components.projects.js.listener");

	Clazz.com.components.projects.js.listener.projectsListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder : commonVariables.basePlaceholder,
		//loadingScreen : null,
		applicationlayerData : null,
		weblayerData : null,
		mobilelayerData : null,
		projectInfo : {},
		customerIds : [],
		appInfos : [],
		appInfosweb : [],
		appInfosmobile : [],
		projectRequestBody : {},
		hasError: false,
		contentContainer : commonVariables.contentPlaceholder,
		projectlistContent : null,
		counter:null,
		appDepsArray : [],
		appCodeArray : [],
		selectedVal : [],
		count : 3,
		oldModuleName : "",
		
		initialize : function(config) {
			var self = this;
		},
		
		cancelCreateproject : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			commonVariables.navListener.getMyObj(commonVariables.projectlist, function(projectlistObj){
				Clazz.navigationController.push(projectlistObj, true, true);
			});
		},
		
		removelayer : function(object) {
			var self=this;
		 	if(self.counter !== 2) {
				var layerId = object.attr('id');
		 		object.closest('tr').next().hide('slow');
		 		object.closest('tr').next().attr('key', 'hidden');
		 		object.closest('tr').hide('slow');
		 		$("input[name="+layerId+"]").show();
		 		self.counter=self.counter+1;
				if(self.counter === 2) {
					$('img[name="close"]').hide();
				}
		 	}
		},
		
		addlayer : function(object) {
			var self=this;
		 	$('img[name="close"]').show();
			var layerType = object.attr('name');
		 	$("input[name="+layerType+"]").hide();
		 	$("tr[name="+ layerType +"]").show('slow');
			$("tr[name="+ layerType +"]").closest('tr').next().show('slow');
			$("tr[name="+ layerType +"]").closest('tr').next().attr('key', 'displayed');
		 	self.counter--;
		},
		
		addlayeredit:function(object) {
			var self=this;
		 	var layerType = object.attr('name');
			$("img[id="+layerType+"]").show();
		 	$("input[name="+layerType+"]").hide();
			$("tr[id="+ layerType +"]").show('slow');
			$("tr[id="+ layerType +"]").next().show('slow');
			$("tr[id="+ layerType +"]").next().attr('key', 'displayed');
			var clasname = $("tr[id="+ layerType +"]").closest('tr').next().attr('id');
			$("tr[id="+ layerType +"]").next().find("tbody."+ clasname).children().each(function() {
		 		$(this).show('slow');
		 		$(this).attr('key', 'displayed');
		 	}); 
		 	self.counter--;
		},
		
		removelayeredit : function(object) {
			var self=this;
		 	if(self.counter !== 2){
				var layerId = object.attr('id');
				$("tr[id="+ layerId +"]").hide('slow');
				$("tr[id="+ layerId +"]").next().hide('slow');
				$("tr[id="+ layerId +"]").next().attr('key', 'hidden');
				$("input[name="+layerId+"]").show();
				self.counter=self.counter+1;
				if(self.counter === 2) {
					$('img[name="close"]').hide();
				}
			}	
		},
		
		
		getEditProject : function(header, callback) {
			var self = this;
			$(".widget-mask-mid-content").addClass('widget-mask-mid-content-altered');
			try {
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							if(response.responseCode !== null && response.responseCode !== 'PHR200005' && response.responseCode !== 'PHR200001' && response.responseCode !== 'PHR200025') {
								$(".msgdisplay").removeClass("error").addClass("success");
								$(".success").attr('data-i18n', 'successCodes.' + response.responseCode);
								self.renderlocales(commonVariables.contentPlaceholder);	
								$(".success").show();
								$(".success").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
							}
							
							if(response.responseCode === "PHR200004" || response.responseCode === "PHR200006"){
								setTimeout(function() {
									$(".success").hide();
									callback(response);
								},1200);
							} else {
								callback(response);
							}	
						} else {
							if(response.responseCode === "PHR210043") {
								$(".appCodeText").each(function(index, value){
									var currentVal = $(value).val();
									if(currentVal === response.data){
										$(this).focus();
										$(this).addClass('errormessage');
										$(this).attr('placeholder', 'module name already exists');
										return false;
									}
								});
								$(appCodeTextObj).bind('input', function() {
									$(this).removeClass("errormessage");
									$(this).removeAttr("placeholder");
								});
							}
							$(".msgdisplay").removeClass("success").addClass("error");
							$(".error").attr('data-i18n', 'errorCodes.' + response.responseCode);
							self.renderlocales(commonVariables.contentPlaceholder);	
							$(".error").show();
							$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
							setTimeout(function() {
								$(".error").hide();
							},2500);
						}

					},

					function(textStatus) {
						$(".msgdisplay").removeClass("success").addClass("error");
						$(".error").attr('data-i18n', 'commonlabel.errormessage.serviceerror');
						self.renderlocales(commonVariables.contentPlaceholder);	
						$(".error").show();
						setTimeout(function() {
							$(".error").hide();
						},2500);
					}
				);
			} catch(exception) {
			}

		},
		
		getRequestHeader : function(projectRequestBody, id, action) {
			var self=this, header, data = {}, userId;
			self.projectId = id;
			data = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			var userId = (data !== null) ? data.id : "";
		
				header = {
					contentType: "application/json",
					requestMethod: "GET",
					dataType: "json",
					webserviceurl: commonVariables.webserviceurl + "project/edit?customerId="+self.getCustomer()+"&projectId="+id
				};

				if(action === "projectlist"){
					header.requestMethod = "GET";
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.projectlistContext +"/list?customerId="+ self.getCustomer();
				}

				if(action === "update"){
					header.requestMethod = "PUT";
					header.requestPostBody = JSON.stringify(projectRequestBody);
					header.webserviceurl = commonVariables.webserviceurl + "project/updateproject?userId="+userId;
				}
				
				if(action === "create"){
					header.requestMethod = "POST";
					header.requestPostBody = JSON.stringify(projectRequestBody);
					header.webserviceurl = commonVariables.webserviceurl + "project/create?userId="+userId;
				}
				
				if(action === "apptypes"){
					header.requestMethod = "GET";
					header.webserviceurl = commonVariables.webserviceurl + "technology/apptypes?userId="+userId+"&customerId="+self.getCustomer();
				}
				
				if(action === "pilotlist"){
					header.requestMethod = "GET";
					header.webserviceurl = commonVariables.webserviceurl + "pilot/prebuilt?userId="+userId+"&customerId="+self.getCustomer();
				}
				
				if (action === "subModules"){
					header.requestMethod = "GET";
					header.webserviceurl = commonVariables.webserviceurl + "technology/techInfo?userId="+userId+"&techId="+projectRequestBody.techId;
				}

				return header;
		},
		
		valid : function(item, msg) {
			$(item).focus();
			$(item).attr('placeholder', msg);
			$(item).addClass("errormessage");
			$(item).bind('keypress', function() {
				$(this).removeClass("errormessage");
			});
		},

		appvalid : function() {
			var self = this;
			var hasError = false;
			
			var isMultiModule = Boolean($('#isMultiModule').val());
			var frontEndTrNames = ["staticApplnLayer"];
			if (isMultiModule) {
				frontEndTrNames.push("dynamicAppLayer");
			}
			
			if ($("tr[class='applnLayer']").attr('key') === 'displayed') {
				$('.applnlayercontent').each(function(index, value) {
					if ($(value).css('display') !== 'none' && $.inArray($(value).attr('name'), frontEndTrNames) !== -1) {
						var applnAppCodeObj = $(value).find('input.appln-appcode');
						//App code validation
						if (self.isBlank(applnAppCodeObj.val())) {
							applnAppCodeObj.addClass("errormessage").focus().attr('placeholder', 'Enter AppCode');
							applnAppCodeObj.bind('keypress', function() {
								$(this).removeClass("errormessage");
								$(this).removeAttr("placeholder");
							});
							hasError = true;
							return false;
						}
						//Technology group validation
						var techGroupObj = $(value).find('select[name=frontEnd]');
						var techGroup = techGroupObj.val();
						$(".errmsg1").hide();
						techGroupObj.next().find('button.dropdown-toggle').removeClass("btn-danger");
						if (self.isBlank(techGroup) || techGroup === "Select Group") {
							$(".errmsg1").show().text("Select Group");
							techGroupObj.next().find('button.dropdown-toggle').addClass("btn-danger");
							hasError = true;
							return false;
						}
						//Technology validation
						var technologyObj = $(value).find('select[name=appln_technology]');
						var technology = technologyObj.val();
						$(".errmsg1").hide();
						technologyObj.next().find('button.dropdown-toggle').removeClass("btn-danger");
						if (self.isBlank(technology) || technology === "Select Group") {
							$(".errmsg1").show().text("Select Technology");
							technologyObj.next().find('button.dropdown-toggle').addClass("btn-danger");
							hasError = true;
							return false;
						}
						
						//Sub-modules validation
						var layer = $(value).attr('name');
						var position = $(value).attr('position');
						$('tr.multi_module[position="'+position+'"][layer="'+layer+'"]').each(function() {
							//Module name validation
							var subModuleNameObj = $(this).find('input[name=subModuleName]');
							var subModuleName = subModuleNameObj.val();
							if (self.isBlank(subModuleName)) {
								subModuleNameObj.addClass("errormessage").focus().attr('placeholder', 'Enter Module Name');
								subModuleNameObj.bind('keypress', function() {
									$(this).removeClass("errormessage");
									$(this).removeAttr("placeholder");
								});
								hasError = true;
								return false;
							}
							//Technology validation
							$(".errmsg1").hide();
							var modTechObj = $(this).find('select[name=technology]');
							modTechObj.next().find('button.dropdown-toggle').removeClass("btn-danger");
							var modTechnology = modTechObj.val();
							if (self.isBlank(modTechnology)) {
								$(".errmsg1").show().text("Select Technology");
								modTechObj.next().find('button.dropdown-toggle').addClass("btn-danger");
								hasError = true;
								return false;
							}
						});
					}
				});
			}
			return hasError;
		},

		webvalid : function() {
			var self = this;
			$(".errmsg1").hide();
			var hasError = false;
			
			var isMultiModule = Boolean($('#isMultiModule').val());
			var middleTierTrNames = ["staticWebLayer"];
			if (isMultiModule) {
				middleTierTrNames.push("dynamicWebLayer");
			}
			if ($("tr[class='webLayer']").attr('key') === 'displayed') {
				$('.weblayercontent').each(function(index, value) {
					if($(value).css('display') !== 'none' && $.inArray($(value).attr('name'), middleTierTrNames) !== -1){
						var webAppCodeObj = $(value).find('input.web-appcode');
						//App code validation
						if (self.isBlank(webAppCodeObj.val())) {
							webAppCodeObj.addClass("errormessage").focus().attr('placeholder', 'Enter AppCode');
							webAppCodeObj.bind('keypress', function() {
								$(this).removeClass("errormessage");
								$(this).removeAttr("placeholder");
							});
							hasError = true;
							return false;
						}
						
						//Technology group validation
						var techGroupObj = $(value).find('select[name=weblayer]');
						var techGroup = techGroupObj.val();
						$(".errmsg2").hide();
						techGroupObj.next().find('button.dropdown-toggle').removeClass("btn-danger");
						if (self.isBlank(techGroup) || techGroup === "Select Group") {
							$(".errmsg2").show().text("Select Group");
							techGroupObj.next().find('button.dropdown-toggle').addClass("btn-danger");
							hasError = true;
							return false;
						}
						
						//Technology validation
						var technologyObj = $(value).find('select[name=web_widget]');
						var technology = technologyObj.val();
						$(".errmsg2").hide();
						technologyObj.next().find('button.dropdown-toggle').removeClass("btn-danger");
						if (self.isBlank(technology) || technology === "Select Group") {
							$(".errmsg2").show().text("Select Technology");
							technologyObj.next().find('button.dropdown-toggle').addClass("btn-danger");
							hasError = true;
							return false;
						}
						
						//Sub-modules validation
						var layer = $(value).attr('name');
						var position = $(value).attr('position');
						$('tr.multi_module[position="'+position+'"][layer="'+layer+'"]').each(function() {
							//Module name validation
							var subModuleNameObj = $(this).find('input[name=subModuleName]');
							var subModuleName = subModuleNameObj.val();
							if (self.isBlank(subModuleName)) {
								subModuleNameObj.addClass("errormessage").focus().attr('placeholder', 'Enter Module Name');
								subModuleNameObj.bind('keypress', function() {
									$(this).removeClass("errormessage");
									$(this).removeAttr("placeholder");
								});
								hasError = true;
								return false;
							}
							
							//Technology validation
							$(".errmsg2").hide();
							var modTechObj = $(this).find('select[name=technology]');
							modTechObj.next().find('button.dropdown-toggle').removeClass("btn-danger");
							var modTechnology = modTechObj.val();
							if (self.isBlank(modTechnology)) {
								$(".errmsg2").show().text("Select Technology");
								modTechObj.next().find('button.dropdown-toggle').addClass("btn-danger");
								hasError = true;
								return false;
							}
						});
					}
				});
			}
			return hasError;
		},

		mobvalid : function() {
			$(".errmsg2").hide();
			var self = this;
			var hasError = false;
			
			var isMultiModule = Boolean($('#isMultiModule').val());
			var cmsTrNames = ["staticMobileLayer"];
			if (isMultiModule) {
				cmsTrNames.push("dynamicMobileLayer");
			}
			
			if ($("tr[class='mobLayer']").attr('key') === 'displayed') {
				$('.mobilelayercontent').each(function(index,value) {
					if($(value).css('display') !== 'none' && $.inArray($(value).attr('name'), cmsTrNames) !== -1) {
						var mobAppCodeObj = $(value).find('input.mobile-appcode');
						//App code validation
						if (self.isBlank(mobAppCodeObj.val())) {
							mobAppCodeObj.addClass("errormessage").focus().attr('placeholder', 'Enter AppCode');
							mobAppCodeObj.bind('keypress', function() {
								$(this).removeClass("errormessage");
								$(this).removeAttr("placeholder");
							});
							hasError = true;
							return false;
						}
						
						//Technology group validation
						var techGroupObj = $(value).find('select[name=mobile_layer]');
						var techGroup = techGroupObj.val();
						$(".errmsg3").hide();
						techGroupObj.next().find('button.dropdown-toggle').removeClass("btn-danger");
						if (self.isBlank(techGroup) || techGroup === "Select Group") {
							$(".errmsg3").show().text("Select Group");
							techGroupObj.next().find('button.dropdown-toggle').addClass("btn-danger");
							hasError = true;
							return false;
						}
						
						//Technology validation
						var technologyObj = $(value).find('select[name=mobile_types]');
						var technology = technologyObj.val();
						$(".errmsg3").hide();
						technologyObj.next().find('button.dropdown-toggle').removeClass("btn-danger");
						if (self.isBlank(technology) || technology === "Select Group") {
							$(".errmsg3").show().text("Select Technology");
							technologyObj.next().find('button.dropdown-toggle').addClass("btn-danger");
							hasError = true;
							return false;
						}
						
						//Sub-modules validation
						var layer = $(value).attr('name');
						var position = $(value).attr('position');
						$('tr.multi_module[position="'+position+'"][layer="'+layer+'"]').each(function() {
							//Module name validation
							var subModuleNameObj = $(this).find('input[name=subModuleName]');
							var subModuleName = subModuleNameObj.val();
							if (self.isBlank(subModuleName)) {
								subModuleNameObj.addClass("errormessage").focus().attr('placeholder', 'Enter Module Name');
								subModuleNameObj.bind('keypress', function() {
									$(this).removeClass("errormessage");
									$(this).removeAttr("placeholder");
								});
								hasError = true;
								return false;
							}
							
							//Technology validation
							$(".errmsg3").hide();
							var modTechObj = $(this).find('select[name=technology]');
							modTechObj.next().find('button.dropdown-toggle').removeClass("btn-danger");
							var modTechnology = modTechObj.val();
							if (self.isBlank(modTechnology)) {
								$(".errmsg3").show().text("Select Technology");
								modTechObj.next().find('button.dropdown-toggle').addClass("btn-danger");
								hasError = true;
								return false;
							}
						});
					}	
				});
			}
			return hasError;	
		},
		
		validation : function() {
			 var self = this;
			 var name = $("input[name='projectname']").val();
			 var code = $("input[name='projectcode']").val();
			 var labelversion = $("input[name='projectversion']").val();
			 var appcode = $("#appcode").val();
			 var webappcode = $("#webappcode").val();
			 var mobileappcode = $("#mobileappcode").val();
			 var startdate = $("#startDate").val();
			 var enddate = $("#endDate").val();
			 var errorJson = self.validateStartEndDate(startdate, enddate);
			 	
			    if(name === ""){
					self.valid("input[name='projectname']", "Enter Name");
					self.hasError = true;
			    } else if(code === "") {
					self.valid("input[name='projectcode']", "Enter Code");
					self.hasError = true;
			    } else if(labelversion === "") {
					self.valid("input[name='projectversion']", "Enter Version");
					self.hasError = true;
			    } else if(errorJson.hasError) {
					if (errorJson.errorIn === "startDate") {
						$("#startDate").val('');
						$("#startDate").attr('placeholder', errorJson.errorMsg);
						$("#startDate").addClass("errormessage");
					} else if (errorJson.errorIn === "endDate") {
						$("#endDate").val('');
						$("#endDate").attr('placeholder', errorJson.errorMsg);
						$("#endDate").addClass("errormessage");
					}
					self.hasError = true;
				} else if(self.appvalid()) {
					self.hasError = true;
			    }else if(self.webvalid()) {
			    	$(".errmsg1").hide();
					self.hasError = true;
			    }else if(self.mobvalid()) {
			    	$(".errmsg2").hide();
					self.hasError = true;
			    }else {	
			    	$(".errmsg3").hide();
					self.hasError=false;
					return self.hasError;
				}		
			return self.hasError;	
		},
		
		validateStartEndDate : function(startDateTime, endDateTime) {
			var errorJson = {};
			var startDate = startDateTime.substring(0, 10);
			var startDateSplits = startDate.split('/');
			var startMonth = Number(startDateSplits[0]);
			var startDay = Number(startDateSplits[1]);
			var startYear = Number(startDateSplits[2]);

			var endDate = endDateTime.substring(0, 10);
			var endDateSplits = endDate.split('/');
			var endMonth = Number(endDateSplits[0]);
			var endDay = Number(endDateSplits[1]);
			var endYear = Number(endDateSplits[2]);

			errorJson.hasError = false;
			
			if (endYear === startYear && endMonth === startMonth && endDay == startDay) {
				errorJson.errorIn = "endDate";
				errorJson.errorMsg = "Select valid end date";
				errorJson.hasError = true;
			} else if (endYear < startYear && endMonth < startMonth && endDay < startDay) {
				errorJson.errorIn = "endDate";
				errorJson.errorMsg = "Start date should be lesser than the end date";
				errorJson.hasError = true;
			} else if (endYear === startYear && endMonth < startMonth) {
				errorJson.errorIn = "endDate";
				errorJson.errorMsg = "Start date should be lesser than the end date";
				errorJson.hasError = true;
			} else if (endYear === startYear && endMonth === startMonth && endDay < startDay) {
				errorJson.errorIn = "endDate";
				errorJson.errorMsg = "Start date should be lesser than the end date";
				errorJson.hasError = true;
			} 
			
			return errorJson;
		},

		getCustomer : function() {
			var selectedcustomer = $("#selectedCustomer").text();
			var customerId = "";
			
			$.each($("#customer").children(), function(index, value){
				var customerText = $(value).children().text();
				if(customerText === selectedcustomer){
					customerId = $(value).children().attr('id');
				}
			});
			
			return customerId;
		},
		
		addLayers : function(layerType, whereToAppend, position) {
			var self=this, dynamicValue;
			position = Number(position) + 1;
			if (layerType === "addApplnLayer") {
				self.count++;
				var applicationlayer = '<tr class="applnlayercontent" name="staticApplnLayer" position="'+position+'"><td class="applnappcode"><input type="text" id="appcode" maxlength="30" title="30 Characters only" class="appln-appcode appCodeText" count='+ self.count +'></td>'+
										'<td name="frontEnd" class="frontEnd"><select name="frontEnd" class="frontEnd selectpicker" title="Select Group"><option value="Select Group" selected disabled>Select Group</option>'+self.getFrontEndTechGrp()+'</select></td>'+
										'<td name="technology" class="technology"><select name="appln_technology" class="appln_technology selectpicker"><option value="Select Technology" disabled>Select Technology</option></select></td>'+
										'<td name="version" class="version"><select name="appln_version" class="appln_version selectpicker"><option disabled>Select Version</option></select></td>'+
										'<td><input type="button" value="Multi Module" class="btn btn_style add_icon_btn multi_module_btn hideContent" name="multiModuleBtn"></td>'+
										'<td><div class="flt_right icon_center"><a name="addApplnLayer" position="'+position+'" href="javascript:;"><img src="themes/default/images/Phresco/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeApplnLayer"><img src="themes/default/images/Phresco/minus_icon.png" border="0" alt=""></a></div></td></tr>';
				
				dynamicValue = $(applicationlayer).insertAfter(whereToAppend);
				self.multiselect();
			} else if (layerType === "addWebLayer") {
				self.count++;
				var weblayer ='<tr class="weblayercontent" name="staticWebLayer" position="'+position+'"><td class="webappcode"><input type="text" id="webappcode" maxlength="30" title="30 Characters only" class="web-appcode appCodeText" count='+ self.count +'></td>'+
								'<td name="web" class="web"><select name="weblayer" class="weblayer selectpicker"><option selected disabled>Select Group</option>'+self.getWidget() +'</select></td>'+
								'<td name="widget" class="widget"><select name="web_widget" class="web_widget selectpicker"><option disabled>Select Technology</option> </select></td>'+
								'<td name="widgetversion" class="widgetversion"><select name="web_version" class="web_version selectpicker"><option disabled>Select Version</option></select></td>'+
								'<td><input type="button" value="Multi Module" class="btn btn_style add_icon_btn multi_module_btn hideContent" name="multiModuleBtn"></td>'+
								'<td> <div class="flt_right icon_center"><a href="javascript:;" position="'+position+'" name="addWebLayer"><img src="themes/default/images/Phresco/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeWebLayer"><img src="themes/default/images/Phresco/minus_icon.png" border="0" alt=""></a></div></td></tr>';
				
				dynamicValue = $(weblayer).insertAfter(whereToAppend);
				self.multiselect();
			} else {
				self.count++;
				var mobilelayer = '<tr class="mobilelayercontent" name="staticMobileLayer" position="'+position+'"><td class="mobileappcode"><input type="text" id="mobileappcode" maxlength="30" title="30 Characters only" class="mobile-appcode appCodeText" count='+ self.count +'></td>'+
									'<td name="mobile" class="mobile"><select name="mobile_layer" class="mobile_layer selectpicker"><option selected disabled>Select Group</option>'+self.getMobile() +'</select></td>'+
									'<td name="types" class="types"><select name="mobile_types" class="mobile_types selectpicker"><option disabled>Select Technology</option></select></td>'+
									'<td name="mobileversion" class="mobileversion selectpicker"><select name="mobile_version" class="mobile_version selectpicker"><option disabled>Select Version</option></select></td>'+
									'<td><input type="button" value="Multi Module" class="btn btn_style add_icon_btn multi_module_btn hideContent" name="multiModuleBtn"></td>'+
									'<td><div class="flt_right icon_center"><a href="javascript:;" position="'+position+'" name="addMobileLayer"><img src="themes/default/images/Phresco/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeMobileLayer"><img src="themes/default/images/Phresco/minus_icon.png" border="0" alt=""></a></div></td></tr>';
				
				$(mobilelayer).find('input.appCodeText').attr('count', self.count);
				dynamicValue = $(mobilelayer).insertAfter(whereToAppend);
				self.multiselect();
			}

			self.addLayersEvent();
			self.removeLayersEvent();
			$("select[name='appln_technology']").unbind('click');
			$("select[name='weblayer']").unbind('click');
			$("select[name='mobile_layer']").unbind('click');
			$("select[name='appln_technology']").unbind('change');
			$("select[name='web_widget']").unbind('change');
			$("select[name='mobile_layer']").unbind('change');
			$("select[name='weblayer']").unbind('change');
			self.technologyAndVersionChangeEvent();
		},
		
		addLayersEvent : function() {
			var self=this, whereToAppend = '';
			
			$(".appln-appcode, .web-appcode, .mobile-appcode").unbind('input');
			$(".appln-appcode, .web-appcode, .mobile-appcode").bind('input propertychange', function(){
				var str = $(this).val();
				str = str.replace(/[^a-zA-Z 0-9\-\_]+/g, '');
				str = str.replace(/\s+/g, '');
				$(this).val(str);
			});
			
			$(".appln-appcode, .web-appcode, .mobile-appcode").focusout(function(){
				var totalLength = $(this).val().length;
				if($(this).val().match(/[._-]/g) !== null){ 
					var charLength =  $(this).val().match(/[._-]/g).length; 
				}
				if(charLength !== null && totalLength === charLength){
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
			
			$("a[name=addApplnLayer]").unbind("click");
			$("a[name=addApplnLayer]").click(function(){
				$(this).hide();
				$(this).next("a[name=removeApplnLayer]").show();
				whereToAppend = $('.applnlayer tr:last');
				var position = $(this).attr("position");
				self.addLayers($(this).attr('name'), whereToAppend, position);
			});
			
			$("a[name=addWebLayer]").unbind("click");
			$("a[name=addWebLayer]").click(function(){
				$(this).hide();
				$(this).next("a[name=removeWebLayer]").show();
				whereToAppend = $('.WebLayer tr:last');
				var position = $(this).attr("position");
				self.addLayers($(this).attr('name'), whereToAppend, position);
			});
			
			$("a[name=addMobileLayer]").unbind("click");
			$("a[name=addMobileLayer]").click(function(){
				$(this).hide();
				$(this).next("a[name=removeMobileLayer]").show();
				whereToAppend = $('.cmsLayer tr:last');
				var position = $(this).attr("position");
				self.addLayers($(this).attr('name'), whereToAppend, position);
			});
			
			$("input[name=multiModuleBtn]").unbind("click");
			$("input[name=multiModuleBtn]").bind("click", function() {
				$(this).attr("disabled", true);
				var techId = "";
				var layerName = $(this).parent().parent().attr("class");
				if (layerName === "applnlayercontent") {
					techId = $(this).parent().parent().find('select[name=appln_technology]').val();
				}
				if (layerName === "weblayercontent") {
					techId = $(this).parent().parent().find('select[name=web_widget]').val();
				}
				if (layerName === "mobilelayercontent") {
					techId = $(this).parent().parent().find('select[name=mobile_types]').val();
				}
				var renderElement = $(this).parent().parent();
				var layer = $(this).parent().parent().attr("name");
				var position = $(this).parent().parent().attr("position");
				var projectRequestBody = {};
				projectRequestBody.techId = techId;
				self.getEditProject(self.getRequestHeader(projectRequestBody, "", "subModules"), function(response) {
					self.constructModuleDepd(renderElement, response.data, layer, position);
				});
			});
		},
		
		constructModuleDepd : function(renderElement, subModules, layer, position, availableDepnds) {
			var self = this;
			var techOptions = self.getTechnologyOptions(subModules);
			var depndOptions = "";
			if (availableDepnds !== undefined && availableDepnds.length > 0) {
				$.each(availableDepnds, function(i, value) {
					depndOptions += "<option value="+ value +">"+ value +"</option>";
				})
			}
			var multiModule = "<tr newModule='true' class='multi_module' layer='"+layer+"' position='"+position+"'><td><input type='text' placeholder='Module Name' name='subModuleName' class='moduleName'></td>"+
								"<td name='techGroup' class='frontEnd hideContent'><select name='techGroup' class='selectpicker' title='Select Group'><option selected disabled value=''>Select Group</option></select></td>"+
								"<td name='technology' class='technology'><select name='technology' class='selectpicker' title='Select Technology'><option value='' data-i18n='project.create.label.seltech'>Select Technology</option>" + techOptions + "</select></td>"+
								"<td name='version' class='version'><select name='version' class='selectpicker' title='Select Version'><option value='Select Version'>Select Version</option></select></td>"+
								"<td><select class='selectpicker appdependencySelect' title='Select Dependency' data-selected-text-format='count>2' multiple><option value=''>Select Dependency</option>"+depndOptions+"</select></td>"+
								"<td><div class='icon_center'><img style='cursor:pointer;' position='"+position+"' layer='"+layer+"' src='themes/default/images/Phresco/plus_icon.png' border='0' alt='' class='addDependency' data-params='"+ JSON.stringify(subModules) +"'> <img style='cursor:pointer;' src='themes/default/images/Phresco/minus_icon.png' border='0' alt='' class='removeDependency'></div></td></tr>";
			renderElement.after(multiModule);
			self.multiselect();
			self.dependencyEvent();
			$('select[name=technology]').selectpicker('refresh');
		},
		
		getTechnologyOptions : function(subModules) {
			var options = '';
			if (subModules.length > 0) {
				$.each(subModules, function(index, value) {
					var techVersions = JSON.stringify(value.techVersions);
					options += "<option data-params='"+ JSON.stringify(value) +"' value="+ value.id +">"+ value.name +"</option>";
				});
			}
			return options;
		},
		
		dependencyEvent : function() {
			var self=this;
			$(".addDependency").unbind('click');
			$(".addDependency").bind('click', function() {
				$(this).hide();
				var layer = $(this).attr("layer");
				var position = $(this).attr("position");
				var dataParams = JSON.parse($(this).attr("data-params"));
				var renderElement = $(this).parent().parent().parent();
				var depClass = $(this).parent().parent().parent().attr("class");
				var trs = $('tr.'+depClass+'[position="'+position+'"][layer="'+layer+'"]');
				var availableDepnds = [];
				$.each(trs, function() {
					var availableModName = $(this).find('input[name=subModuleName]').val();
					if (!self.isBlank(availableModName)) {
						availableDepnds.push(availableModName);
					}
				});
				self.constructModuleDepd(renderElement, dataParams, layer, position, availableDepnds);
			});

			$(".removeDependency").unbind('click');
			$(".removeDependency").click(function() {
				var multiModTrObj = $(this).parent().parent().parent();
				var position = multiModTrObj.attr("position");
				var layer = multiModTrObj.attr("layer");
				multiModTrObj.remove();
				$('tr.multi_module[position="'+position+'"][layer="'+layer+'"]:last').find(".addDependency").show();
				if ($('tr.multi_module[position="'+position+'"][layer="'+layer+'"]').length === 0) {
					$('tr[position="'+position+'"][name="'+layer+'"]').find("input[name=multiModuleBtn]").attr("disabled", false);
				}
			});
			
			$("select[name=technology]").unbind("change");
			$("select[name=technology]").bind("change", function() {
				var dataParams = JSON.parse($(this).find(":selected").attr("data-params"));
				var renderElement = $(this).parent().parent().find('select[name=version]');
				self.getVersionOptions(renderElement, dataParams);
			});
			
			$('input[name=subModuleName]').unbind("focus");
			$('input[name=subModuleName]').bind("focus", function() {
				self.oldModuleName = $(this).val();
			});
			
			$('input[name=subModuleName]').unbind("blur");
			$('input[name=subModuleName]').bind("blur", function() {
				var moduleNameObj = $(this);
				var moduleName = moduleNameObj.val();
				if (!self.isBlank(moduleName) && self.oldModuleName !== moduleName) {
					moduleNameObj.removeClass("errormessage");
					moduleNameObj.parent().parent().attr("active", "true");
					var position = moduleNameObj.parent().parent().attr("position");
					var layer = moduleNameObj.parent().parent().attr("layer");
					var depClass = moduleNameObj.parent().parent().attr("class");
					var trs = $('tr.'+depClass+'[position="'+position+'"][layer="'+layer+'"]');
					$.each(trs, function() {
						if ($(this).attr("active") !== "true") {
							if ($(this).find("input[name=subModuleName]").val() === moduleName) {
								moduleNameObj.addClass("errormessage").val("").attr("placeholder", "Duplicate Module Name").focus();
							} else {
								var available = false;
								$(this).find("select.appdependencySelect option").each(function() {
									if ($(this).val() === moduleName) {
										available = true;
										return false;
									}
								});
								if (!available) {
									if (!self.isBlank(self.oldModuleName)) {
										$(this).find("select.appdependencySelect option[value=" + self.oldModuleName + "]").remove();
									}
									$(this).find("select.appdependencySelect").append('<option value="' + moduleName + '">' + moduleName + '</option>');
									$(this).find("select.appdependencySelect").selectpicker('refresh');
								}
							}
						}
					});
				} else if (self.isBlank(moduleName)) {
					moduleNameObj.addClass("errormessage").attr("placeholder", "Enter Module Name").focus();
				}
				moduleNameObj.parent().parent().removeAttr("active");
			});
			
			$('.appdependencySelect').unbind('change');
			$('.appdependencySelect').bind('change', function() {
				var thisObj = $(this);
				var currentModName = thisObj.parent().parent().find('input[name=subModuleName]').val();
				var position = thisObj.parent().parent().attr("position");
				var layer = thisObj.parent().parent().attr("layer");
				var depClass = thisObj.parent().parent().attr("class");
				var trs = $('tr.'+depClass+'[position="'+position+'"][layer="'+layer+'"]');
				thisObj.find("option:selected").each(function() {
					var selectedDepncy = $(this).val();
					$.each(trs, function() {
						var modName = $(this).find('input[name=subModuleName]').val();
						$(this).find(".appdependencySelect option:selected").each(function() {
							var dependModule = $(this).val();
							thisObj.parent().parent().find(".appdependencySelect").next().find('button.dropdown-toggle').removeClass("btn-danger");
							if (!self.isBlank(dependModule) && modName === selectedDepncy && dependModule === currentModName) {
								thisObj.parent().parent().find(".appdependencySelect").next().find('button.dropdown-toggle').addClass("btn-danger");
								return false;
							}
						});
					});
				});
				$(this).selectpicker('refresh');
			});
		},
		
		getVersionOptions : function(renderElement, dataParams) {
			var options = '';
			if (dataParams !== null && dataParams.techVersions !== null && dataParams.techVersions.length > 0) {
				$.each(dataParams.techVersions, function(index, value) {
					options += "<option value="+ value +">"+ value +"</option>";
				});
				renderElement.html(options);
				renderElement.selectpicker('refresh');
			}
		},
		
		removeLayersEvent : function() {
			var self=this;
			$("a[name=removeApplnLayer]").unbind("click");
			$("a[name=removeApplnLayer]").click(function() {
				$(this).parent().parent().parent().hide();
				$('.applnlayercontent:visible').last().find('a[name="addApplnLayer"]').show();
				if ($('tr[name=staticApplnLayer]:visible').length === 1) {
					$('tr.applnlayercontent:visible').last().find('a[name="removeApplnLayer"]').hide();
				}
				if ($('tr[name=dynamicAppLayer]:visible').length >= 1) {
					$('tr.applnlayercontent:visible').last().find('a[name="removeApplnLayer"]').show();
				}
				$(this).parent().parent().parent().remove();
			});
			
			$("a[name=removeWebLayer]").unbind("click");
			$("a[name=removeWebLayer]").click(function() {
				$(this).parent().parent().parent().hide();
				$('.weblayercontent:visible').last().find('a[name="addWebLayer"]').show();
				if ($('tr[name=staticWebLayer]:visible').length === 1) {
					$('tr.weblayercontent:visible').last().find('a[name="removeWebLayer"]').hide();
				}
				if ($('tr[name=dynamicWebLayer]:visible').length >= 1) {
					$('tr.weblayercontent:visible').last().find('a[name="removeWebLayer"]').show();
				}
				$(this).parent().parent().parent().remove();
			});

			$("a[name=removeMobileLayer]").unbind("click");
			$("a[name=removeMobileLayer]").click(function() {
				$(this).parent().parent().parent().hide();
				$('.mobilelayercontent:visible').last().find('a[name="addMobileLayer"]').show();
				if ($('tr[name=staticMobileLayer]:visible').length === 1) {
					$('tr.mobilelayercontent:visible').last().find('a[name="removeMobileLayer"]').hide();
				}
				if ($('tr[name=dynamicMobileLayer]:visible').length >= 1) {
					$('tr.mobilelayercontent:visible').last().find('a[name="removeMobileLayer"]').show();
				}
				$(this).parent().parent().parent().remove();
			});
		},
		
		technologyAndVersionChangeEvent : function() {
		
			var self=this;
			
			$("input.appCodeText").keyup(function() {
				var currentCount = $(this).attr('count');
				var currentVal =  $(this).val();
				var appCodeTextObj = $(this);
				var totalLength = $(this).val().length;
				if($(this).val().match(/[._-]/g) !== null){ 
					var charLength =  $(this).val().match(/[._-]/g).length; 
				}
				if(currentVal === '' || currentVal === null) {
					$(appCodeTextObj).addClass('errormessage');
					$(appCodeTextObj).attr('placeholder', 'Enter AppCode');
				} /*else if(charLength !== null && totalLength === charLength){
					$(this).val('');
					$(this).focus();
					$(this).addClass('errormessage');
					$(this).attr('placeholder', 'Invalid Code');
					$(this).bind('input', function() {
						$(this).removeClass("errormessage");
						$(this).removeAttr("placeholder");
					});
				} */else {
					$(".appCodeText").each(function(index, value){
						var keyAttr = $(value).parents('tr[name=appvalidation]').attr('key');
						if(keyAttr === 'displayed'){
							var valueCount = $(value).attr('count');
							var appcodeVal = $(value).val();
							if(currentCount !== valueCount && currentVal === appcodeVal){
								$(appCodeTextObj).val("");
								$(appCodeTextObj).focus();
								$(appCodeTextObj).addClass('errormessage');
								$(appCodeTextObj).attr('placeholder', 'Appcode Already Exists');
								return false;
							}
						}
					});
				}	
				$(appCodeTextObj).bind('input', function() {
					$(this).removeClass("errormessage");
					$(this).removeAttr("placeholder");
				});
			});
			
			/***
			 ** Front End Change Event For Technology
			 */
			$("select[name='frontEnd']").bind('change', function(){
				$(this).parent().parent().nextUntil('tr.applnlayercontent').remove();
				$(this).parent().parent().find("input[name=multiModuleBtn]").hide();
				var group = $(this).val();
				var frontEndTechPlaceholder = $(this).parent().parent().find("select[name='appln_technology']");
				self.getFrontEndTechnology(group, frontEndTechPlaceholder);
			});
			
			/***
			 ** Front End - Technology Change Event For Technology version
			 */
			$("select[name='appln_technology']").bind('change', function(){
				self.showMultiModuleBtn($(this));
				$(this).parent().parent().nextUntil('tr.applnlayercontent').remove();
				var techId = $(this).val();
				var versionplaceholder = $(this).parents("td[name='technology']").siblings("td[name='version']").children("select[name='appln_version']");
				self.gettechnologyversion(techId, versionplaceholder);
			});
			
			/***
			 ** Web Layer - Web Layer Change Event For Widget Type
			 */
			$("select[name='weblayer']").bind('change', function(){
				var type = $(this).val();
				var widgetTypePlaceholder = $(this).parents("td[name='web']").siblings("td[name='widget']").children("select[name='web_widget']");
				self.getwidgettype(type, widgetTypePlaceholder);
				$(this).parent().parent().find("input[name=multiModuleBtn]").hide();
				$(this).parent().parent().nextUntil('tr.weblayercontent').remove();
			});
			
			/***
			 ** Web Layer - Type Change Event For Widget Version
			 */
			$("select[name='web_widget']").bind('change', function(){
				self.showMultiModuleBtn($(this));
				$(this).parent().parent().nextUntil('tr.weblayercontent').remove();
				var widgetType = $(this).val();
				var widgetTypePlaceholder = $(this).parents("td[name='widget']").siblings("td[name='widgetversion']").children("select[name='web_version']");
				self.getwidgetversion(widgetType, widgetTypePlaceholder);
			});

			/***
			 ** CMS - Mobile Change Event For Mobile Type
			 */
			$("select[name='mobile_layer']").bind('change', function(){
				var mobile = $(this).val();
				var mobileTypePlaceholder = $(this).parents("td[name='mobile']").siblings("td[name='types']").children("select[name='mobile_types']");
				self.getmobiletype(mobile, mobileTypePlaceholder);
			});
			
			/***
			 **	CMS - Type Change Event For Mobile Version
			 */
			$("select[name='mobile_types']").unbind('change');
			$("select[name='mobile_types']").bind('change', function(){
				self.showMultiModuleBtn($(this));
				$(this).parent().parent().nextUntil('tr.mobilelayercontent').remove();
				var mobileType = $(this).val();
				var mobileTypePlaceholder = $(this).parents("td[name='types']").siblings("td[name='mobileversion']").children("select[name='mobile_version']");
				self.getmobileversion(mobileType, mobileTypePlaceholder);
			});
			
		},
		
		showMultiModuleBtn : function(thisObj) {
			var multiModule = thisObj.find(':selected').attr("multiModule");
			var display = "none";
			if (multiModule === "true") {
				display = "block";
			}
			thisObj.parent().parent().find("input[name=multiModuleBtn]").css("display", display);
			thisObj.parent().parent().find("input[name=multiModuleBtn]").attr("disabled", false);
		},
		
		getFrontEndTechGrp : function() {
			var self=this, option;
			self.applicationlayerData = commonVariables.api.localVal.getJson("Front End");
			option = '';
			$.each(self.applicationlayerData.techGroups, function(index, value){
				option += '<option value='+ value.id +'>'+ value.name +'</option>';
			});
			
			return option;
		},
		
		getTechnology : function(id, isMultiModule) {
			var self=this, option;
			self.applicationlayerData = commonVariables.api.localVal.getJson("Front End");
			option = '';
			$.each(self.applicationlayerData.techGroups, function(index, value){
				$.each(value.techInfos, function(index, value){
					if (id === value.id) {
						option += '<option multiModule='+ Boolean(isMultiModule) +' value='+ value.id +' selected=selected>'+ value.name +'</option>';
					} else {
						option += '<option value='+ value.id +'>'+ value.name +'</option>';
					}
				});
			});
			
			return option;
		},
		
		getWidget : function() {
			var self=this, option;
			self.weblayerData = commonVariables.api.localVal.getJson("Middle Tier");
			option = '';
			$.each(self.weblayerData.techGroups, function(index, value){
				option += '<option value='+ value.id +'>'+ value.name +'</option>';
			});
			
			return option;
		},
		
		getMobile : function(id) {
			var self=this, option;
			self.mobilelayerData = commonVariables.api.localVal.getJson("CMS");
			option = '';
			$.each(self.mobilelayerData.techGroups, function(index, value){
				if (id === value.id) {
					option += '<option value='+ value.id +' selected=selected>'+ value.name +'</option>';
				} else {
					if(value !== '' && value !== undefined) {
						option += '<option value='+ value.id +'>'+ value.name +'</option>';
					}
				}
			});
			
			return option;
		},
		
		getFrontEndTechnology : function(groupId, versionplaceholder) {
			var self=this, option;
			self.applicationlayerData = commonVariables.api.localVal.getJson("Front End");
			$.each(self.applicationlayerData.techGroups, function(index, value) {
				if (value.id === groupId) {
					option = '<option disabled selected value="">Select Technology</option>';
					$.each(value.techInfos, function(index, value){
						option += '<option multiModule='+ value.multiModule +' value="'+value.id+'">'+ value.name +'</option>';
					});
					$(versionplaceholder).html(option);
					$(versionplaceholder).selectpicker('refresh');
					return false;
				}
			});
		},
		
		gettechnologyversion : function(technologyId, versionplaceholder) {
			var self=this, option;
			self.applicationlayerData = commonVariables.api.localVal.getJson("Front End");
			$.each(self.applicationlayerData.techGroups, function(index, value){
				$.each(value.techInfos, function(index, value){
				    if(value.id === technologyId){
						option = '';
						if (value.techVersions !== null && value.techVersions !== null && value.techVersions.length > 0) {
							$.each(value.techVersions, function(index, value){
								option += '<option>'+ value +'</option>';
							});
						} else {
							option += '<option value="">No Versions Available</option>';
						}
						
						$(versionplaceholder).html(option);
						$(versionplaceholder).selectpicker('refresh');
						return false;
					}
				});
			});
		},
		
		getwidgettype : function(type, widgetTypePlaceholder) {
			var self=this, option;
			self.weblayerData = commonVariables.api.localVal.getJson("Middle Tier");
			$.each(self.weblayerData.techGroups, function(index, value){
				if(value.id === type){
					option = '';
					option += '<option disabled selected>Select Technology</option>';
					$.each(value.techInfos, function(index, value){
						option += '<option multiModule='+ value.multiModule +' value='+ value.id +'>'+ value.name +'</option>';
					});
					
					$(widgetTypePlaceholder).html(option);
					$(widgetTypePlaceholder).selectpicker('refresh');
				}
			});
		},
		
		editgetwidgettype : function(id, isMultiModule) {
			var self=this, option;
			self.weblayerData = commonVariables.api.localVal.getJson("Middle Tier");
			$.each(self.weblayerData.techGroups, function(index, value){
				$.each(value.techInfos, function(index, value){
					if (id === value.id) {
						option += '<option multiModule='+ Boolean(isMultiModule) +' value='+ value.id +' selected=selected>'+ value.name +'</option>';
					} else {
						option += '<option value='+ value.id +'>'+ value.name +'</option>';
					}
				});
			});
			
			return option;
		},
		
		getwidgetversion : function(widgettype, widgetTypePlaceholder) {
			var self=this, option;
			self.weblayerData = commonVariables.api.localVal.getJson("Middle Tier");
			$.each(self.weblayerData.techGroups, function(index, value){
				$.each(value.techInfos, function(index, value){
					if(value.id === widgettype){
						option = '';
						$.each(value.techVersions, function(index, value){
							option += '<option>'+ value +'</option>';
						});
						
						$(widgetTypePlaceholder).html(option);
						$(widgetTypePlaceholder).selectpicker('refresh');
					}
				});
			});
		},
		
		getmobiletype : function(mobile, mobileTypePlaceholder) {
			var self=this, option;
			self.mobilelayerData = commonVariables.api.localVal.getJson("CMS");
			$.each(self.mobilelayerData.techGroups, function(index, value){
				if(value.id === mobile){
					option = '';
					option += '<option disabled selected>Select Technology</option>';
					$.each(value.techInfos, function(index, value){
						option += '<option multiModule='+ value.multiModule +' value='+ value.id +'>'+ value.name +'</option>';
					});
					
					$(mobileTypePlaceholder).html(option);
					$(mobileTypePlaceholder).selectpicker('refresh');
				}
			});
		},
		
		editgetmobiletype : function(id, isMultiModule) {
			var self=this, option;
			self.mobilelayerData = commonVariables.api.localVal.getJson("CMS");
			$.each(self.mobilelayerData.techGroups, function(index, value){
				$.each(value.techInfos, function(index, value){
					if (id === value.id) {
						option += '<option multiModule='+ value.multiModule +' value='+ value.id +' selected=selected>'+ value.name +'</option>';
					} else {
						option += '<option value='+ value.id +'>'+ value.name +'</option>';
					}
				});
			});
			
			return option;
		},
		
		getmobileversion : function(mobileType, mobileTypePlaceholder) {
			var self=this, option;
			self.mobilelayerData = commonVariables.api.localVal.getJson("CMS");
			$.each(self.mobilelayerData.techGroups, function(index, value){
				$.each(value.techInfos, function(index, value){
					if(value.id === mobileType){
						option = '';
						if(value.techVersions !== undefined && value.techVersions !== null) {
							if(value !== '' && value !== null) {
								$.each(value.techVersions, function(index, value){
									if(value !== '' && value !== null) {
										option += '<option>'+ value +'</option>';
									} else {
										option += '<option>No Versions available</option>';
									}
								});
							} else {
								option += '<option>No Versions available</option>';
							}
						} else {
							option += '<option>No Versions available</option>';
						}	
						
						$(mobileTypePlaceholder).html(option);
						$(mobileTypePlaceholder).selectpicker('refresh');
					}
				});
			});
		},
		
		pilotprojectsEvent : function() {
			var self=this;
			$("select[name='prebuiltapps']").hide();
			$("select[name='builtmyself']").unbind('change');
			$("select[name='builtmyself']").bind('change', function(){
				$("input.appln-appcode").val('');
				$("input.web-appcode").val('');
				$("input.mobile-appcode").val('');
				$("input[name=applicationlayer]").hide();
				$("input[name=weblayer]").hide();
				$("input[name=mobilelayer]").hide();
				self.counter = null;
				var selectedText = $(this).find(':selected').val();
				if(selectedText === "prebuilt"){
					$("select[name='builtmyselfapps']").hide();
					
					self.setPilotData(function(Option){
						$("select[name='prebuiltapps']").css('display' , 'inline-block');
						$("select[name='prebuiltapps']").html(Option);
						var selectedPilot = $("select[name='prebuiltapps']").find(':selected').text();
						if(selectedPilot === 'Select PilotType'){
							$("input[name=Create]").attr('disabled', true);
						} else {
							self.layerRender(selectedPilot);
						}	
					});
				} else {
					$("input[name=Create]").attr('disabled', false);
					$("tr[name=applicationlayer]").show();
					$("tr.applnLayer").show();
					$(".applnlayercontent").show();
					
					$("tr[name=weblayer]").show();
					$("tr.webLayer").show();
					
					$("tr[name=mobilelayer]").show();
					$("tr.mobLayer").show();
					
					 $("tr.applnLayer").attr('key','displayed');	
					 $("tr.webLayer").attr('key','displayed');	
					 $("tr.mobLayer").attr('key','displayed');	
					 $("select[name='builtmyselfapps']").css('display' , 'inline-block');
					 $("input[name='startdate']").attr("disabled", false);
					 $("input[name='enddate']").attr("disabled", false);
					 $("select[name='appln_technology']").attr('disabled', false);
					 $("select[name='appln_version']").attr('disabled', false);
					 $("select[name='weblayer']").attr('disabled', false);
					 $("select[name='web_widget']").attr('disabled', false);
					 $("select[name='web_version']").attr('disabled', false);
					 $("select[name='mobile_layer']").attr('disabled', false);
					 $("select[name='mobile_types']").attr('disabled', false);
					 $("select[name='mobile_version']").attr('disabled', false);
					 $("select[name='prebuiltapps']").hide();
					 $("#applicationlayer").show();
					 $("#weblayer").show();
					 $("#mobilelayer").show();
					 self.revertSelectValues($("select[name=appln_technology]"),"Select Technology");
					 $("select[name=appln_version]").html('<option>Select Version</option>');
					 $("select[name=appln_version]").selectpicker('refresh');
					 self.revertSelectValues($("select[name=weblayer]"),"Select Group");
					 self.revertSelectValues($("select[name=mobile_layer]"),"Select Group");
					 $("select[name=web_widget]").html('<option>Select Technology</option>');
					 $("select[name=web_widget]").selectpicker('refresh');
					 $("select[name=web_version]").html('<option>Select Version</option>');
					 $("select[name=web_version]").selectpicker('refresh');
					 $("select[name=mobile_types]").html('<option>Select Technology</option>');
					 $("select[name=mobile_types]").selectpicker('refresh');
					 $("select[name=mobile_version]").html('<option>Select Version</option>');
					 $("select[name=mobile_version]").selectpicker('refresh');
					 
					 $("tr.applnlayercontent:not(:first)").remove();
					 $("tr.weblayercontent:not(:first)").remove();
					 $("tr.mobilelayercontent:not(:first)").remove();
					 $("tr.weblayercontent").show();
				}
			});
			
			$("select[name='prebuiltapps']").change(function() {
				$("input.appln-appcode").val('');
				$("input.web-appcode").val('');
				$("input.mobile-appcode").val('');
				var selectedPilot = $(this).find(':selected').text();
				if(selectedPilot === 'Select PilotType'){
					$("input[name=Create]").attr('disabled', true);
				} else {
					self.layerRender(selectedPilot);
				}
			});
		},
		
		revertSelectValues : function(obj, OptionText) {
			$(obj).removeAttr('appInfoId');
			$(obj).find('option').each(function(index, value) {
				$(value).removeAttr('selected');
				$(obj).selectpicker('refresh');
			});
			if($(obj).find('option').val() === OptionText) {
				$(obj).val(OptionText);
				$(obj).selectpicker('refresh');
			}
		},
		
		setPilotData : function(callback) {
			var self=this, option = '';
			self.getEditProject(self.getRequestHeader(self.projectRequestBody, "", "pilotlist"), function(response) {
				if (response !== null && (response.status !== "error" || response.status !== "failure")){
					option += '<option value="0">Select PilotType</option>';
					$.each(response.data, function(index, value){
						commonVariables.api.localVal.setJson(value.name, value);
						if(value.displayName !== undefined && value.displayName !== null && value.displayName !== '') {
							option += '<option>'+ value.displayName +'</option>';
						}
						if(response.data.length === (index + 1)){
							callback(option);
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
		
		layerRender : function(selectedPilot) {
			var self=this;
			var selectedPilotData = commonVariables.api.localVal.getJson(selectedPilot), dynamicValue, minusIcon = '<img src="themes/default/images/Phresco/minus_icon.png" border="0" alt="">';
			if(selectedPilotData !== undefined && selectedPilotData !== null) {
				$("input[name=Create]").attr('disabled', false);
				$('#applicationlayer').hide();
				$('#weblayer').hide();
				$('#mobilelayer').hide();
				
				$("tr[name=applicationlayer]").hide();
				$("tr.applnLayer").hide();
				$("tr.applnLayer").attr('key','hidden');
				
				$("tr[name=weblayer]").hide();
				$("tr.webLayer").hide();
				$("tr.webLayer").attr('key','hidden');
				
				$("tr[name=mobilelayer]").hide();
				$("tr.mobLayer").hide();
				$("tr.mobLayer").attr('key','hidden');
				
				$("tr.applnlayercontent:not(:first)").remove();
				$("tr.weblayercontent:not(:first)").remove();
				$("tr.mobilelayercontent:not(:first)").remove();
				
				$.each(selectedPilotData.appInfos, function(index, appInfo){
					if (appInfo.techInfo.appTypeId === "1dbcf61c-e7b7-4267-8431-822c4580f9cf") {
						$("tr[name=applicationlayer]").show();
						$("tr.applnLayer").show();
						$("tr.applnLayer").attr('key','displayed');
						var frontEnd = '<tr class="applnlayercontent" name="staticApplnLayer"><td class="applnappcode"><input type="text" id="appcode" maxlength="30" title="30 Characters only" class="appln-appcode appCodeText"></td><td name="frontEnd" class="frontEnd"><select name="frontEnd" class="frontEnd selectpicker" title="Select Group"><option value="Select Group" selected disabled>Select Group</option>'+self.getFrontEndTechGrp()+'</select></td><td name="technology" class="technology"><select name="appln_technology" class="appln_technology selectpicker"><option disabled>Select Technology</option></select></td><td name="version" class="version"><select name="appln_version" class="appln_version selectpicker"><option disabled>Select Version</option></select></td><td></td><td><div class="flt_right icon_center"><a name="addApplnLayer"><img src="themes/default/images/Phresco/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeApplnLayer"></a></div></td></tr>';
						dynamicValue = $(frontEnd).insertAfter("tr.applnlayercontent:last");
						
						if (dynamicValue.prev('tr').attr("name") !== "dynamicAppLayer") {
							dynamicValue.prev('tr').find('a[name="addApplnLayer"]').html('');
							dynamicValue.prev('tr').find('a[name="removeApplnLayer"]').html(minusIcon);
						} else {
							dynamicValue.prev('tr').find('a[name="addApplnLayer"]').html('');
						}
						
						self.addLayersEvent();
						self.removeLayersEvent();
						$("tr.applnlayercontent:first").hide();
						$("tr.applnlayercontent:last").show();
						
						$("select[name='frontEnd']:last option").each(function(index, value) {
							if ($(value).val() === appInfo.techInfo.techGroupId.toLowerCase()) {
								$(value).attr('selected', 'selected');
								$("select[name='frontEnd']:last").attr('appInfoId', appInfo.id);
								$("select[name='frontEnd']:last").val($(value).val());
								$("select[name='frontEnd']:last").attr('disabled', 'disabled');
								$("select[name='frontEnd']:last").selectpicker('refresh');
							}
						});
						
						var frontEndGrp = $("select[name='frontEnd']:last").val();
						var frontEndTechPlaceholder = $("select[name='frontEnd']:last").parents("td[name='frontEnd']:last").siblings("td[name='technology']:last").children("select[name='appln_technology']:last");
						self.getFrontEndTechnology(frontEndGrp, frontEndTechPlaceholder);
						
						$("select[name='appln_technology']:last option").each(function(index, value) {
							$(value).removeAttr('selected');	
							if($(value).val() === appInfo.techInfo.id.toLowerCase()) {
								$(value).attr('selected', 'selected');
								$("select[name='appln_technology']:last").attr('appInfoId', appInfo.id);
								$("select[name='appln_technology']:last").val($(value).val());
								$("select[name='appln_technology']:last").attr('disabled', 'disabled');
								$("select[name='appln_technology']:last").selectpicker('refresh');
							}
						});
						
						var techId = $("select[name='appln_technology']:last").val();
						var versionplaceholder = $("select[name='appln_technology']:last").parents("td[name='technology']:last").siblings("td[name='version']:last").children("select[name='appln_version']:last");
						self.gettechnologyversion(techId, versionplaceholder);
						$("select[name='appln_version']:last").attr('disabled', 'disabled');
						$("select[name='appln_version']:last").selectpicker('refresh');
						$("tr.applnLayer").attr('key','displayed');
					} else if (appInfo.techInfo.appTypeId === "e1af3f5b-7333-487d-98fa-46305b9dd6ee") {
						$("tr[name=weblayer]").show();
						$("tr.webLayer").show();
						$("tr.webLayer").attr('key','displayed');
						
						var middleTier ='<tr class="weblayercontent" name="staticWebLayer"><td class="webappcode"><input type="text" id="webappcode" maxlength="30" title="30 Characters only" class="web-appcode appCodeText"></td><td name="web" class="web"><select name="weblayer" class="weblayer selectpicker"><option selected disabled>Select Group</option>'+self.getWidget() +'</select></td><td name="widget" class="widget"><select name="web_widget" class="web_widget selectpicker"><option disabled>Select Technology</option></select></td><td name="widgetversion" class="widgetversion"><select name="web_version" class="web_version selectpicker"><option disabled>Select Version</option></select></td><td></td><td> <div class="flt_right icon_center"><a href="javascript:;" name="addWebLayer"><img src="themes/default/images/Phresco/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeWebLayer"></a></div></td></tr>';
						dynamicValue = $(middleTier).insertAfter("tr.weblayercontent:last");
						
						if (dynamicValue.prev('tr').attr("name") !== "dynamicWebLayer") {
							dynamicValue.prev('tr').find('a[name="addWebLayer"]').html('');
							dynamicValue.prev('tr').find('a[name="removeWebLayer"]').html(minusIcon);
						} else {
							dynamicValue.prev('tr').find('a[name="addWebLayer"]').html('');
						}
						
						self.addLayersEvent();
						self.removeLayersEvent();
						$("tr.applnlayercontent:first").hide();
						$("tr.applnlayercontent:last").show();
						$("tr.weblayercontent:first").hide();
						$("tr.weblayercontent:last").show();
						
						$("select[name='weblayer']:last option").each(function(index, value) {
							$(value).removeAttr('selected');
							var techGroupId = appInfo.techInfo.techGroupId;	
							if($(value).val() === techGroupId.toLowerCase()) {
								$(value).attr('selected', 'selected');
								$("select[name='weblayer']:last").val($(value).val());
								$("select[name='weblayer']:last").attr('appInfoId', appInfo.id);
								$("select[name='weblayer']:last").attr('disabled', 'disabled');
								$("select[name='weblayer']:last").selectpicker('refresh');
							}
						});
						var type = $("select[name='weblayer']:last").val();
						var widgetTypePlaceholder = $("select[name='weblayer']:last").parents("td[name='web']:last").siblings("td[name='widget']:last").children("select[name='web_widget']:last");
						self.getwidgettype(type, widgetTypePlaceholder);
						$("select[name='web_widget']:last option").each(function(index, value) {
							$(value).removeAttr('selected');	
							if($(value).val() === appInfo.techInfo.id) {
								$(value).attr('selected', 'selected');
								$("select[name='web_widget']:last").val($(value).val());
								$("select[name='web_widget']:last").attr('disabled', 'disabled');
								$("select[name='web_widget']:last").selectpicker('refresh');
							}
						});
						var widgetType = $("select[name='web_widget']:last").val();
						var widgetTypePlaceholder = $("select[name='web_widget']:last").parents("td[name='widget']:last").siblings("td[name='widgetversion']:last").children("select[name='web_version']:last");
						self.getwidgetversion(widgetType, widgetTypePlaceholder);
						$("select[name='web_version']:last").attr('disabled', 'disabled');
						$("select[name='web_version']:last").selectpicker('refresh');
						$("tr.webLayer:last").attr('key','displayed');
					} else if(appInfo.techInfo.appTypeId === "99d55693-dacd-4f77-994a-f02a66176ff9") {
						$("tr[name=mobilelayer]").show();
						$("tr.mobLayer").show();
						$("tr.mobLayer").attr('key','displayed');
						
						var cmsLayer = '<tr class="mobilelayercontent" name="staticMobileLayer"><td class="mobileappcode"><input type="text" id="mobileappcode" maxlength="30" title="30 Characters only" class="mobile-appcode appCodeText"></td><td name="mobile" class="mobile"><select name="mobile_layer" class="mobile_layer selectpicker"><option selected disabled>Select Group</option>'+self.getMobile() +'</select></td><td name="types" class="types"><select name="mobile_types" class="mobile_types selectpicker"><option disabled>Select Technology</option></select></td><td colspan="2" name="mobileversion" class="mobileversion selectpicker"><select name="mobile_version" class="mobile_version selectpicker"><option disabled>Select Version</option></select></td><td></td><td><div class="flt_right icon_center"><a href="javascript:;" name="addMobileLayer"><img src="themes/default/images/Phresco/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeMobileLayer"></a></div></td></tr>';
						dynamicValue = $(cmsLayer).insertAfter("tr.mobilelayercontent:last");
						
						if (dynamicValue.prev('tr').attr("name") !== "dynamicMobileLayer") {
							dynamicValue.prev('tr').find('a[name="addMobileLayer"]').html('');
							dynamicValue.prev('tr').find('a[name="removeMobileLayer"]').html(minusIcon);
						} else {
							dynamicValue.prev('tr').find('a[name="addMobileLayer"]').html('');
						}
						
						self.addLayersEvent();
						self.removeLayersEvent();
						$("tr.applnlayercontent:first").hide();
						$("tr.applnlayercontent:last").show();
						$("tr.mobilelayercontent:first").hide();
						$("tr.mobilelayercontent:last").show();
					
						$("select[name='mobile_layer']:last option").each(function(index, value){
							$(value).removeAttr('selected');	
							if($(value).val() === appInfo.techInfo.techGroupId) {
								$(value).attr('selected', 'selected');
								$("select[name='mobile_layer']:last").val($(value).val());
								$("select[name='mobile_layer']:last").attr('appInfoId', appInfo.id);
								$("select[name='mobile_layer']:last").attr('disabled', 'disabled');
								$("select[name='mobile_layer']:last").selectpicker('refresh');
							}
						});
						var mobile = $("select[name='mobile_layer']:last").val();
						var mobileTypePlaceholder = $("select[name='mobile_layer']:last").parents("td[name='mobile']:last").siblings("td[name='types']:last").children("select[name='mobile_types']:last");
						self.getmobiletype(mobile, mobileTypePlaceholder);
						$("select[name='mobile_types']:last option").each(function(index, value){
							$(value).removeAttr('selected');	
							if($(value).val() === appInfo.techInfo.id) {
								$(value).attr('selected', 'selected');
								$("select[name='mobile_types']:last").val($(value).val());
								$("select[name='mobile_types']:last").attr('disabled', 'disabled');
								$("select[name='mobile_types']:last").selectpicker('refresh');
							}
						});
						var mobileType = $("select[name='mobile_types']:last").val();
						var mobileTypePlaceholder = $("select[name='mobile_types']:last").parents("td[name='types']:last").siblings("td[name='mobileversion']:last").children("select[name='mobile_version']:last");
						self.getmobileversion(mobileType, mobileTypePlaceholder);
						$("select[name='mobile_version']:last").attr('disabled', 'disabled');
						$("select[name='mobile_version']:last").selectpicker('refresh');
						$("tr.mobLayer").attr('key','displayed');
					}
				});
			}		
		}, 
		
		enablebuttonAdd : function() {
			if($('tr.applnLayer').attr('key') === "hidden") {
				$("input[name='applicationlayer']").show();
			} else {
				$("input[name='applicationlayer']").hide();
			}	
			if($('tr.webLayer').attr('key') === "hidden") {
				$("input[name='weblayer']").show();
			} else {
				$("input[name='weblayer']").hide();
			}	
			if($('tr.mobLayer').attr('key') === "hidden") {
				$("input[name='mobilelayer']").show();
			} else {
				$("input[name='mobilelayer']").hide();
			}
		},
		
		enablebuttonEdit : function(preBuilt) {
			if (preBuilt !== "true") {
				if($('#appLayaer').css('display') === "none") {
						$("input[name='appLayaer']").show();
				}	
				if($('#webLayers').css('display') === "none") {
						$("input[name='webLayers']").show();
				}	
				if($('#mobLayers').css('display') === "none") {
						$("input[name='mobLayers']").show();
				}
			}
		},
		
		editSeriveTechnolyEvent : function(getData) {
			var self = this;
			$("#appLayaer").hide();
			$("tr.applnLayer").hide();
			$("#webLayers").hide();
			$("tr.webLayer").hide();
			$("#mobLayers").hide();
			$("tr.mobLayer").hide();
			var position = 1;
			$.each(getData, function(index, value) {
				if (value.techInfo.appTypeId === "1dbcf61c-e7b7-4267-8431-822c4580f9cf") {
					$("#appLayaer").show();
					$("tr.applnLayer").show();
					$('img[name="close"]').hide();
					
					var layer = "dynamicAppLayer";
					var techOptions = "";
					var projectRequestBody = {};
					projectRequestBody.techId = value.techInfo.id;
					self.getEditProject(self.getRequestHeader(projectRequestBody, "", "subModules"), function(response) {
						var versionMsg = value.techInfo.version;
						if(versionMsg === "" || versionMsg === null || versionMsg === undefined){
							versionMsg = "No Version Available";
						}
						var modules = value.modules;
						var isMultiModule = Boolean($('#isMultiModule').val());
						var multiModBtnCls = "btn btn_style add_icon_btn multi_module_btn hideContent";
						if (isMultiModule) {
							multiModBtnCls = "btn btn_style add_icon_btn multi_module_btn";
						}
						var disabledAttr = "";
						if (modules !== null && modules !== undefined && modules.length > 0) {
							disabledAttr = "disabled";
						}
						var appendData = '<tr class="applnlayercontent" position="'+position+'" name="dynamicAppLayer"><td class="applnappcode"><input id="appcode" type="text" value="'+value.code+'" disabled class="appln-appcode appCodeText"></td>'+
											'<td name="frontEnd" class="frontEnd"><select name="frontEnd" class="frontEnd selectpicker" title="Select Group" disabled><option>'+value.techInfo.techGroupId+'</option></select></td>'+
											'<td name="technology" class="technology"><select class="selectpicker appln_technology" name="appln_technology" disabled>'+ self.getTechnology(value.techInfo.id, isMultiModule) +'</select></td>'+
											'<td name="version" class="version"><select name="appln_version" class="selectpicker appln_version" disabled><option>'+versionMsg+'</option></select></td>'+
											'<td><input type="button" value="Multi Module" class="'+multiModBtnCls+'" '+disabledAttr+' name="multiModuleBtn"></td>'+
											'<td><div class="flt_right icon_center"><a href="javascript:;" name="addApplnLayer"><img src="themes/default/images/Phresco/plus_icon.png" border="0" alt=""></a></div></td></tr>';
						if (modules !== null && modules !== undefined && modules.length > 0) {
							var subModules = response.data;
							$.each(modules, function(i, module) {
								var selectedDepndtMods = [];
								var dependOptions = "<option value=''>Select Dependency</option>";
								$.each(modules, function(moduleIndex, dependModule) {
									if (dependModule.code !== module.code) {
										var selectedStr = "";
										if ($.inArray(dependModule.code, module.dependentModules) !== -1) {
											selectedDepndtMods.push(dependModule.code);
											selectedStr = "selected";
										}
										dependOptions += '<option '+selectedStr+' value="'+dependModule.code+'">'+dependModule.code+'</option>';
									}
								});
								
								var selectedDepndtModsAttr = '';
								if (selectedDepndtMods.length > 0) {
									selectedDepndtModsAttr = 'selectedDepndtMods='+selectedDepndtMods;
								}
								
								var imgClass = "addDependency hideContent";
								if (modules.length === i + 1) {
									imgClass = "addDependency";
								}
								appendData += "<tr class='multi_module' layer='"+layer+"' position='"+position+"'><td><input type='text' placeholder='Module Name' name='subModuleName' class='moduleName' value='"+module.code+"' disabled></td>"+
												"<td name='techGroup' class='frontEnd hideContent'><select name='techGroup' class='selectpicker' title='Select Group'><option selected disabled value=''>Select Group</option></select></td>"+
												"<td name='technology' class='technology'><select name='technology' class='selectpicker' disabled><option value='"+module.techInfo.id+"'>"+module.techInfo.name+"</option>" + techOptions + "</select></td>"+
												"<td name='version' class='version'><select name='version' class='selectpicker' disabled><option value='"+module.techInfo.version+"'>"+module.techInfo.version+"</option></select></td>"+
												"<td><select class='selectpicker appdependencySelect' title='Select Dependency' "+selectedDepndtModsAttr+" data-selected-text-format='count>2' multiple>"+dependOptions+"</select></td>"+
												"<td><div class='icon_center'><img style='cursor:pointer;' position='"+position+"' layer='"+layer+"' src='themes/default/images/Phresco/plus_icon.png' border='0' alt='' class='"+imgClass+"' data-params='"+ JSON.stringify(subModules) +"'></div></td></tr>";
							});
							$("tbody.applnLayer").append(appendData);
							self.multiselect();
							self.dependencyEvent();
							self.showAddIcon();
							position = position + 1;
						} else {
							$("tbody.applnLayer").append(appendData);
							self.multiselect();
							self.showAddIcon();
						}
					});
				} else if (value.techInfo.appTypeId === "e1af3f5b-7333-487d-98fa-46305b9dd6ee") {
					$("#webLayers").show();
					$("tr.webLayer").show();
					$('img[name="close"]').hide();
					
					var layer = "dynamicWebLayer";
					var techOptions = "";
					var projectRequestBody = {};
					projectRequestBody.techId = value.techInfo.id;
					self.getEditProject(self.getRequestHeader(projectRequestBody, "", "subModules"), function(response) {
						var versionMsg = value.techInfo.version;
						if(versionMsg === "" || versionMsg === null || versionMsg === undefined){
							versionMsg = "No Version Available";
						}
						var modules = value.modules;
						var isMultiModule = Boolean($('#isMultiModule').val());
						var multiModBtnCls = "btn btn_style add_icon_btn multi_module_btn hideContent";
						if (isMultiModule) {
							multiModBtnCls = "btn btn_style add_icon_btn multi_module_btn";
						}
						var disabledAttr = "";
						if (modules !== null && modules !== undefined && modules.length > 0) {
							disabledAttr = "disabled";
						}
						var appendData = '<tr class="weblayercontent" position="'+position+'" name="dynamicWebLayer"><td class="webappcode"><input id="webappcode" class="web-appcode appCodeText" type="text" value="'+value.code+'" disabled></td>'+
											'<td name="web" class="web"><select name="weblayer" class="weblayer selectpicker" disabled><option>'+value.techInfo.techGroupId+'</option></select></td>'+
											'<td name="widget" class="widget"><select name="web_widget" class="selectpicker web_widget" disabled> '+ self.editgetwidgettype(value.techInfo.id, isMultiModule) +'</select></td>'+
											'<td name="widgetversion" class="widgetversion"><select name="web_version" class="selectpicker web_version" disabled><option>'+value.techInfo.version+'</option></select></td>'+
											'<td><input type="button" value="Multi Module" class="'+multiModBtnCls+'" '+disabledAttr+' name="multiModuleBtn"></td>'+
											'<td><div class="flt_right icon_center"><a href="javascript:;" name="addWebLayer"><img src="themes/default/images/Phresco/plus_icon.png" border="0" alt=""></a></div></td></tr>';
						if (modules !== null && modules !== undefined && modules.length > 0) {
							var subModules = response.data;
							var selectedDepndtMods = [];
							$.each(modules, function(i, module) {
								var dependOptions = "<option value=''>Select Dependency</option>";
								$.each(modules, function(moduleIndex, dependModule) {
									if (dependModule.code !== module.code) {
										var selectedStr = "";
										if ($.inArray(dependModule.code, module.dependentModules) !== -1) {
											selectedDepndtMods.push(dependModule.code);
											selectedStr = "selected";
										}
										dependOptions += '<option '+selectedStr+' value="'+dependModule.code+'">'+dependModule.code+'</option>';
									}
								});
								
								var selectedDepndtModsAttr = '';
								if (selectedDepndtMods.length > 0) {
									selectedDepndtModsAttr = 'selectedDepndtMods='+selectedDepndtMods;
								}
								
								var imgClass = "addDependency hideContent";
								if (modules.length === i + 1) {
									imgClass = "addDependency";
								}
								appendData += "<tr class='multi_module' layer='"+layer+"' position='"+position+"'><td><input type='text' placeholder='Module Name' name='subModuleName' class='moduleName' value='"+module.code+"' disabled></td>"+
												"<td name='techGroup' class='frontEnd hideContent'><select name='techGroup' class='selectpicker' title='Select Group'><option selected disabled value=''>Select Group</option></select></td>"+
												"<td name='technology' class='technology'><select name='technology' class='selectpicker' disabled><option value='"+module.techInfo.id+"'>"+module.techInfo.name+"</option>" + techOptions + "</select></td>"+
												"<td name='version' class='version'><select name='version' class='selectpicker' disabled><option value='"+module.techInfo.version+"'>"+module.techInfo.version+"</option></select></td>"+
												"<td><select class='selectpicker appdependencySelect' title='Select Dependency' "+selectedDepndtModsAttr+" data-selected-text-format='count>2' multiple>"+dependOptions+"</select></td>"+
												"<td><div class='icon_center'><img style='cursor:pointer;' position='"+position+"' layer='"+layer+"' src='themes/default/images/Phresco/plus_icon.png' border='0' alt='' class='"+imgClass+"' data-params='"+ JSON.stringify(subModules) +"'></div></td></tr>";
							});
							$("tbody.WebLayer").append(appendData);
							self.multiselect();
							position = position + 1;
							self.dependencyEvent();
							self.showAddIcon();
						} else {
							$("tbody.WebLayer").append(appendData);
							self.multiselect();
							self.showAddIcon();
						}
					});
				} else if (value.techInfo.appTypeId === "99d55693-dacd-4f77-994a-f02a66176ff9") {
					$("#mobLayers").show();
					$("tr.mobLayer").show();
					$('img[name="close"]').hide();
					
					var layer = "dynamicMobileLayer";
					var techOptions = "";
					var projectRequestBody = {};
					projectRequestBody.techId = value.techInfo.id;
					self.getEditProject(self.getRequestHeader(projectRequestBody, "", "subModules"), function(response) {
						var versionMsg = value.techInfo.version;
						if(versionMsg === "" || versionMsg === null || versionMsg === undefined || versionMsg === "null"){
							versionMsg = "No Version Available";
						}
						var modules = value.modules;
						var isMultiModule = Boolean($('#isMultiModule').val());
						var multiModBtnCls = "btn btn_style add_icon_btn multi_module_btn hideContent";
						if (isMultiModule) {
							multiModBtnCls = "btn btn_style add_icon_btn multi_module_btn";
						}
						var disabledAttr = "";
						if (modules !== null && modules !== undefined && modules.length > 0) {
							disabledAttr = "disabled";
						}
						var appendData = '<tr class="mobilelayercontent" position="'+position+'" name="dynamicMobileLayer"><td class="mobileappcode"><input id="mobileappcode" class="mobile-appcode appCodeText" type="text" value="'+value.code+'" disabled></td>'+
											'<td name="mobile" class="mobile"><select name="mobile_layer" class="selectpicker mobile_layer" disabled><option>'+value.techInfo.techGroupId+'</option></select></td>'+
											'<td name="types" class="types"><select name="mobile_types" class="selectpicker mobile_types" disabled>'+self.editgetmobiletype(value.techInfo.id, isMultiModule)+'</select></td>'+
											'<td name="mobileversion" class="mobileversion"><select name="mobile_version" disabled class="mobile_version selectpicker"><option>'+versionMsg +'</option></select></td>'+
											'<td><input type="button" value="Multi Module" class="'+multiModBtnCls+'" '+disabledAttr+' name="multiModuleBtn"></td>'+
											'<td><div class="flt_right icon_center"><a href="javascript:;" name="addMobileLayer"><img src="themes/default/images/Phresco/plus_icon.png" border="0" alt=""></a></div></td></tr>';
						if (modules !== null && modules !== undefined && modules.length > 0) {
							var subModules = response.data;
							var selectedDepndtMods = [];
							$.each(modules, function(i, module) {
								var dependOptions = "<option value=''>Select Dependency</option>";
								$.each(modules, function(moduleIndex, dependModule) {
									if (dependModule.code !== module.code) {
										var selectedStr = "";
										if ($.inArray(dependModule.code, module.dependentModules) !== -1) {
											selectedStr = "selected";
											selectedDepndtMods.push(dependModule.code);
										}
										dependOptions += '<option '+selectedStr+' value="'+dependModule.code+'">'+dependModule.code+'</option>';
									}
								});
								
								var selectedDepndtModsAttr = '';
								if (selectedDepndtMods.length > 0) {
									selectedDepndtModsAttr = 'selectedDepndtMods='+selectedDepndtMods;
								}
								
								var imgClass = "addDependency hideContent";
								if (modules.length === i + 1) {
									imgClass = "addDependency";
								}
								appendData += "<tr class='multi_module' layer='"+layer+"' position='"+position+"'><td><input type='text' placeholder='Module Name' name='subModuleName' class='moduleName' value='"+module.code+"' disabled></td>"+
												"<td name='techGroup' class='frontEnd hideContent'><select name='techGroup' class='selectpicker' title='Select Group'><option selected disabled value=''>Select Group</option></select></td>"+
												"<td name='technology' class='technology'><select name='technology' class='selectpicker' disabled><option value='"+module.techInfo.id+"'>"+module.techInfo.name+"</option>" + techOptions + "</select></td>"+
												"<td name='version' class='version'><select name='version' class='selectpicker' disabled><option value='"+module.techInfo.version+"'>"+module.techInfo.version+"</option></select></td>"+
												"<td><select class='selectpicker appdependencySelect' title='Select Dependency' "+selectedDepndtModsAttr+" data-selected-text-format='count>2' multiple>"+dependOptions+"</select></td>"+
												"<td><div class='icon_center'><img style='cursor:pointer;' position='"+position+"' layer='"+layer+"' src='themes/default/images/Phresco/plus_icon.png' border='0' alt='' class='"+imgClass+"' data-params='"+ JSON.stringify(subModules) +"'></div></td></tr>";
							});
							$("tbody.MobLayer").append(appendData);
							self.multiselect();
							position = position + 1;
							self.dependencyEvent();
							self.showAddIcon();
						} else {
							$("tbody.MobLayer").append(appendData);
							self.multiselect();
							self.showAddIcon();
						}
					});
				}
			});
		},
		
		showAddIcon : function() {
			var self = this;
			$("tr[name=dynamicAppLayer]:last").find("a[name=addApplnLayer]").show();
			$("tr[name=dynamicWebLayer]:last").find("a[name=addWebLayer]").show();
			$("tr[name=dynamicMobileLayer]:last").find("a[name=addMobileLayer]").show();
			self.addLayersEvent();
		},
		
		 /**
         * Called during the page refresh, displays the message, and total number of records
         * @response: response from the service
         */
        pageRefresh : function(response) {
			commonVariables.navListener.getMyObj(commonVariables.projectlist, function(projectlistObj){
				projectlistObj.loadPage(true);
			});
		},
		
		createproject : function(projectId, action) {
			var self = this;
			if (!self.validation()) {
				var projectname = $("input[name='projectname']").val();
				var projectcode = $("input[name='projectcode']").val();
				var projectversion = $("input[name='projectversion']").val();
				var majorVersion = $("#majorVersion").val();
				var minorVersion = $("#minorVersion").val();
				var fixedVersion = $("#fixedVersion").val();
				var buildType = $("#iterationType").val();
				var weekStart = $("#weekStart").val();
				var projectdescription = $("textarea[name='projectdescription']").val();
				var startdate = $("input[name='startdate']").val();
				var enddate = $("input[name='enddate']").val();
				var integrationTest = $("input[name=integrationTest]").is(":checked");
				var groupId = $("#groupId").val();
				var isMultiModule = false;
				//To convert dd/mm//yyyy to (month date,year) format
				if (startdate.length !== 0 && enddate.length !== 0) {
					var myStartDate = new Date(startdate);
					var myEndDate = new Date(enddate);
				} else {
					var myStartDate = null;
					var myEndDate = null;
				}	
				var	preBuilt = $("select[name=builtmyself]").val() === "custom"? false:true;
				var count = 0;
				self.customerIds = [];
				self.appInfos = [];
				self.appInfosweb = [];
				self.appInfosmobile = [];
				self.customerIds.push(self.getCustomer());
				self.projectInfo.version = projectversion;
				self.projectInfo.name = projectname;
				self.projectInfo.projectCode = projectcode;
				self.projectInfo.description = projectdescription;
				self.projectInfo.startDate = myStartDate;
				self.projectInfo.endDate = myEndDate;
				self.projectInfo.preBuilt = preBuilt;
				self.projectInfo.customerIds = self.customerIds;
				self.projectInfo.integrationTest = integrationTest;
				self.projectInfo.groupId = groupId;
				
				var versionInfo = {};
				versionInfo.major = Number(majorVersion);
				versionInfo.minor = Number(minorVersion);
				versionInfo.fix = Number(fixedVersion);
				versionInfo.buildType = buildType;
				versionInfo.weekStart = Number(weekStart);
				self.projectInfo.versionInfo = versionInfo;
				
				var multiModuleEleVal = Boolean($('#isMultiModule').val());
				var frontEndTrNames = ["staticApplnLayer"];
				var middleTierTrNames = ["staticWebLayer"];
				var cmsTrNames = ["staticMobileLayer"];
				if (multiModuleEleVal) {
					frontEndTrNames.push("dynamicAppLayer");
					middleTierTrNames.push("dynamicWebLayer");
					cmsTrNames.push("dynamicMobileLayer");
				}
				
				$.each($("tbody[name='layercontents']").children(), function(index, value) {
					var techInfo = {};
					var tech;
					var appInfoId = "";
					var techName = "";
					var code = "";
					var appInfo = {};
					var mobdata = {};
					var versionText = "";
					
					if ($(value).attr('class') === "applnLayer" && $(value).attr('key') === "displayed") {
						var applnlayerDiv = $(value).children('td.appln').children('table.applnlayer').children('tbody.applnlayer');
						$.each($(applnlayerDiv).children(), function(index, value){
							var trNameAttr = $(value).attr('name');
							var canAddAppInfo = false;
							if ($(value).css('display') !== "none" && $.inArray(trNameAttr, frontEndTrNames) !== -1) {
								var appInfo = {};
								var techInfo = {};
								tech = $(value).children("td.technology").children("select.appln_technology");
								appInfoId = $(value).children("td.technology").children("select.appln_technology").attr('appInfoId');
								techName = $(tech).find(":selected").text();
								code = $(value).children("td.applnappcode").children("input.appln-appcode").val();
								if(appInfoId !== undefined && appInfoId !== null) {
									appInfo.id = appInfoId;
								}
								appInfo.code = code;
								appInfo.appDirName = code;
								appInfo.version = projectversion;
								appInfo.name = code;
								techInfo.id = $(value).children("td.technology").children("select.appln_technology").val();
								techInfo.appTypeId = "1dbcf61c-e7b7-4267-8431-822c4580f9cf";
								techInfo.techGroupId = $(value).children("td.frontEnd").children("select.frontEnd").find(":selected").text();
								techInfo.name = $(value).children("td.technology").children("select.appln_technology").find(":selected").text();
								techInfo.version = $(value).children("td.version").children("select.appln_version").val();
								
								var multiModule = $(tech).find(":selected").attr('multiModule');
								if (multiModule === "true") {
									var position = $(value).attr('position');
									var layer = $(value).attr('name');
									var moduleInfos = [];
									$('tr.multi_module[position="'+position+'"][layer="'+layer+'"]').each(function() {
										var newmodule = $(this).attr("newmodule");
										if (Boolean(newmodule)) {
											var dependentModules = [];
											$(this).find('.appdependencySelect option:selected').each(function() {
												var dependency = $(this).val();
												if (!self.isBlank(dependency)) {
													dependentModules.push(dependency);
												}
											});
											var moduleInfo = self.getModuleInfoObj($(this), "1dbcf61c-e7b7-4267-8431-822c4580f9cf", false, dependentModules, code);
											moduleInfos.push(moduleInfo);
											canAddAppInfo = true;
										} else {
											var selectedDepndtMods = [];
											if ($(this).find('.appdependencySelect').attr("selectedDepndtMods") !== undefined) {
												selectedDepndtMods = $(this).find('.appdependencySelect').attr("selectedDepndtMods").split(",");
											}
											var dependentModules = [];
											$(this).find('.appdependencySelect option:selected').each(function() {
												var dependency = $(this).val();
												if (!self.isBlank(dependency)) {
													dependentModules.push(dependency);
												}
											});
											var modified = false;
											if (selectedDepndtMods.length > dependentModules.length || selectedDepndtMods.length < dependentModules.length) {
												modified = true;
											} else {
												var difference = $(selectedDepndtMods).not(dependentModules).get();
												if (difference.length > 0) {
													modified = true;
												}
											}
											
											if (modified) {
												var moduleInfo = self.getModuleInfoObj($(this), "1dbcf61c-e7b7-4267-8431-822c4580f9cf", true, dependentModules, code);
												moduleInfos.push(moduleInfo);
												canAddAppInfo = true;
											}
										}
										isMultiModule = true;
									});
									appInfo.modules = moduleInfos;
									self.projectInfo.multiModule = true;
								}
								
								if (appInfo.code !== undefined && appInfo.code !== null) {
									appInfo.techInfo = techInfo;
									if(preBuilt){
										var selectedPilot = $("select[name='prebuiltapps']").find(':selected').text();
										var selectedPilotData = commonVariables.api.localVal.getJson(selectedPilot);
										$.each(selectedPilotData.appInfos, function(index, appInfo){
											if(appInfo.techInfo.appTypeId === "1dbcf61c-e7b7-4267-8431-822c4580f9cf" && appInfo.techInfo.id === techInfo.id){
												appInfo.appDirName = code;
												appInfo.code = code;
												appInfo.name = code;
												appInfo.version = projectversion;
												appInfo.techInfo.version = techInfo.version;
												self.appInfos.push(appInfo);
											}
										});
									} else {
										if (trNameAttr === "staticApplnLayer" ||  (trNameAttr === "dynamicAppLayer" && canAddAppInfo)) {
											self.appInfos.push(appInfo);
										}
									}
									count++;
								}
							}
						});	
					} 
					
					if(($(value).attr('class') === "webLayer") && ($(value).attr('key') === "displayed")) {
						var weblayerDiv = $(value).children('td.WebLayer').children('table.WebLayer').children('tbody.WebLayer');
						$.each($(weblayerDiv).children(), function(index, value){
							var trNameAttr = $(value).attr('name');
							var canAddAppInfo = false;
							if($(value).css('display') !== "none" && $.inArray(trNameAttr, middleTierTrNames) !== -1) {
								var appInfo = {};
								var techInfo = {};
								tech = $(value).children("td.widget").children("select.web_widget");
								techName = $(tech).find(":selected").text();
								code = $(value).children("td.webappcode").children("input.web-appcode").val();
								appInfoId = $(value).children("td.web").children("select.weblayer").attr('appInfoId');
								if(appInfoId !== undefined && appInfoId !== null){
									appInfo.id = appInfoId;
								}
								appInfo.code = code;
								appInfo.appDirName = code;
								appInfo.version = projectversion;
								appInfo.name = code;
								techInfo.id = $(value).children("td.widget").children("select.web_widget").val();
								techInfo.appTypeId = "e1af3f5b-7333-487d-98fa-46305b9dd6ee";
								techInfo.techGroupId = $(value).children("td.web").children("select.weblayer").find(":selected").text();
								techInfo.version = $(value).children("td.widgetversion").children("select.web_version").find(":selected").text();
								techInfo.name = $(value).children("td.widget").children("select.web_widget").find(":selected").text();
								
								var multiModule = $(tech).find(":selected").attr('multiModule');
								if (multiModule === "true") {
									var position = $(value).attr('position');
									var layer = $(value).attr('name');
									var moduleInfos = [];
									$('tr.multi_module[position="'+position+'"][layer="'+layer+'"]').each(function() {
										var newmodule = $(this).attr("newmodule");
										if (Boolean(newmodule)) {
											var dependentModules = [];
											$(this).find('.appdependencySelect option:selected').each(function() {
												var dependency = $(this).val();
												if (!self.isBlank(dependency)) {
													dependentModules.push(dependency);
												}
											});
											var moduleInfo = self.getModuleInfoObj($(this), "e1af3f5b-7333-487d-98fa-46305b9dd6ee", false, dependentModules, code);
											moduleInfos.push(moduleInfo);
											canAddAppInfo = true;
										} else {
											var selectedDepndtMods = [];
											if ($(this).find('.appdependencySelect').attr("selectedDepndtMods") !== undefined) {
												selectedDepndtMods = $(this).find('.appdependencySelect').attr("selectedDepndtMods").split(",");
											}
											var dependentModules = [];
											$(this).find('.appdependencySelect option:selected').each(function() {
												var dependency = $(this).val();
												if (!self.isBlank(dependency)) {
													dependentModules.push(dependency);
												}
											});
											var modified = false;
											if (selectedDepndtMods.length > dependentModules.length || selectedDepndtMods.length < dependentModules.length) {
												modified = true;
											} else {
												var difference = $(selectedDepndtMods).not(dependentModules).get();
												if (difference.length > 0) {
													modified = true;
												}
											}
											
											if (modified) {
												var moduleInfo = self.getModuleInfoObj($(this), "e1af3f5b-7333-487d-98fa-46305b9dd6ee", true, dependentModules, code);
												moduleInfos.push(moduleInfo);
												canAddAppInfo = true;
											}
										}
									});
									appInfo.modules = moduleInfos;
									self.projectInfo.multiModule = true;
								}
								
								if (appInfo.code !== undefined && appInfo.code !== null) {
									appInfo.techInfo = techInfo;
									if(preBuilt){
										var selectedPilot = $("select[name='prebuiltapps']").find(':selected').text();
										var selectedPilotData = commonVariables.api.localVal.getJson(selectedPilot);
										$.each(selectedPilotData.appInfos, function(index, appInfo){
											if(appInfo.techInfo.appTypeId === "e1af3f5b-7333-487d-98fa-46305b9dd6ee" && appInfo.techInfo.id === techInfo.id){
												appInfo.appDirName = code;
												appInfo.code = code;
												appInfo.name = code;
												appInfo.version = projectversion;
												self.appInfos.push(appInfo);
											}
										});
									} else {
										if (trNameAttr === "staticWebLayer" ||  (trNameAttr === "dynamicWebLayer" && canAddAppInfo)) {
											self.appInfos.push(appInfo);
										}
									}	
									count++;
								}
							}	
						});	
					}  

					if(($(value).attr('class') === "mobLayer") && ($(value).attr('key') === "displayed")) {
						var mobilelayerDiv = $(value).children('td.mob').children('table.mob-table').children('tbody');
						$.each($(mobilelayerDiv).children(), function(index, value){
							var trNameAttr = $(value).attr('name');
							var canAddAppInfo = false;
							if($(value).css('display') !== "none" && $.inArray(trNameAttr, cmsTrNames) !== -1) {
								var appInfo = {};
								var techInfo = {};
								versionText = $(value).children("td.mobileversion").children("select.mobile_version").find(":selected").text();
								tech = $(value).children("td.types").children("select.mobile_types");
								techName = $(tech).find(":selected").text();
								code = $(value).children("td.mobileappcode").children("input.mobile-appcode").val();
								appInfoId = $(value).children("td.mobile").children("select.mobile_layer").attr('appInfoId');
								if(appInfoId !== undefined && appInfoId !== null){
									appInfo.id = appInfoId;
								}
								appInfo.code = code;
								appInfo.appDirName = code; 
								appInfo.version = projectversion;
								appInfo.name = code; 
								techInfo.id = $(value).children("td.types").children("select.mobile_types").find(':selected').val();
								techInfo.appTypeId = "99d55693-dacd-4f77-994a-f02a66176ff9";
								techInfo.techGroupId = $(value).children("td.mobile").children("select.mobile_layer").find(':selected').text();
								techInfo.name = $(value).children("td.types").children("select.mobile_types").find(':selected').text();
								if(versionText === "No Versions available") {
									techInfo.version = "";
								} else {
									techInfo.version = $(value).children("td.mobileversion").children("select.mobile_version").find(":selected").text();
								}
								
								var multiModule = $(tech).find(":selected").attr('multiModule');
								if (multiModule === "true") {
									var position = $(value).attr('position');
									var layer = $(value).attr('name');
									var moduleInfos = [];
									$('tr.multi_module[position="'+position+'"][layer="'+layer+'"]').each(function() {
										var newmodule = $(this).attr("newmodule");
										if (Boolean(newmodule)) {
											var dependentModules = [];
											$(this).find('.appdependencySelect option:selected').each(function() {
												var dependency = $(this).val();
												if (!self.isBlank(dependency)) {
													dependentModules.push(dependency);
												}
											});
											var moduleInfo = self.getModuleInfoObj($(this), "99d55693-dacd-4f77-994a-f02a66176ff9", false, dependentModules, code);
											moduleInfos.push(moduleInfo);
											canAddAppInfo = true;
										} else {
											var selectedDepndtMods = [];
											if ($(this).find('.appdependencySelect').attr("selectedDepndtMods") !== undefined) {
												selectedDepndtMods = $(this).find('.appdependencySelect').attr("selectedDepndtMods").split(",");
											}
											var dependentModules = [];
											$(this).find('.appdependencySelect option:selected').each(function() {
												var dependency = $(this).val();
												if (!self.isBlank(dependency)) {
													dependentModules.push(dependency);
												}
											});
											var modified = false;
											if (selectedDepndtMods.length > dependentModules.length || selectedDepndtMods.length < dependentModules.length) {
												modified = true;
											} else {
												var difference = $(selectedDepndtMods).not(dependentModules).get();
												if (difference.length > 0) {
													modified = true;
												}
											}
											
											if (modified) {
												var moduleInfo = self.getModuleInfoObj($(this), "99d55693-dacd-4f77-994a-f02a66176ff9", true, dependentModules, code);
												moduleInfos.push(moduleInfo);
												canAddAppInfo = true;
											}
										}
									});
									appInfo.modules = moduleInfos;
									self.projectInfo.multiModule = true;
								}
								
								if (appInfo.code !== undefined && appInfo.code !== null) {
									appInfo.techInfo = techInfo;
									if(preBuilt){
										var selectedPilot = $("select[name='prebuiltapps']").find(':selected').text();
										var selectedPilotData = commonVariables.api.localVal.getJson(selectedPilot);
										$.each(selectedPilotData.appInfos, function(index, appInfo){
											if(appInfo.techInfo.appTypeId === "99d55693-dacd-4f77-994a-f02a66176ff9" && appInfo.techInfo.id === techInfo.id){
												appInfo.appDirName = code;
												appInfo.code = code;
												appInfo.name = code;
												appInfo.version = projectversion;
												self.appInfos.push(appInfo);
											}
										});
									} else {
										if (trNameAttr === "staticMobileLayer" ||  (trNameAttr === "dynamicMobileLayer" && canAddAppInfo)) {
											self.appInfos.push(appInfo);
										}
									}
									count++;
								}
							}
						});
					}
				});
				var appInfos = $.merge($.merge($.merge([],self.appInfos), self.appInfosweb), self.appInfosmobile);
				self.projectInfo.noOfApps = count;
				self.projectInfo.appInfos = appInfos;
				self.projectInfo.displayName = $("select[name='prebuiltapps']").val();
				self.projectRequestBody = self.projectInfo;
				if(action === "update") {
					self.projectInfo.id = projectId;
				}
				self.appDepsArray = [];
				self.getEditProject(self.getRequestHeader(self.projectRequestBody, "", action), function(response) {
					self.counter = null;
					self.projectRequestBody = {};
					commonVariables.api.localVal.deleteSession("projectId");
					self.getEditProject(self.getRequestHeader(self.projectRequestBody, "", "projectlist"), function(response) {
						self.pageRefresh(response);
					});
				});
			}
		},
		
		getModuleInfoObj : function(thisObj, appTypeId, modified, dependentModules, rootModule) {
			var appModuleName = thisObj.find('input[name=subModuleName]').val();
			var moduleInfo = {};
			moduleInfo.code = appModuleName;
			moduleInfo.dependentModules = dependentModules;
			moduleInfo.modified = modified;
			var technologyInfo = {};
			technologyInfo.version = thisObj.find('select[name=version]').val();
			technologyInfo.id = thisObj.find('select[name=technology]').val();
			technologyInfo.name = thisObj.find('select[name=technology] option:selected').text();
			technologyInfo.appTypeId = appTypeId;
			moduleInfo.techInfo = technologyInfo;
			moduleInfo.rootModule = rootModule;
			return moduleInfo;
		}
	});

	return Clazz.com.components.projects.js.listener.projectsListener;
});