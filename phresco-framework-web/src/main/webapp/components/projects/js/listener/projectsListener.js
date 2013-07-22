define(["projects/api/projectsAPI"], function() {

	Clazz.createPackage("com.components.projects.js.listener");

	Clazz.com.components.projects.js.listener.projectsListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder : commonVariables.basePlaceholder,
		//loadingScreen : null,
		projectAPI : null,
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
		selectedVal : [],
		
		initialize : function(config) {
			var self = this;
			if(self.projectAPI === null) {
				self.projectAPI = new Clazz.com.components.projects.js.api.ProjectsAPI();
			}	
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
		 	if(self.counter !== 2){
		 	var layerId = object.attr('id');
		 	var findclass = object.closest('tr').next().attr('class');
		 		object.closest('tr').next().attr('name', layerId + "content");
		 		$("tr[class="+findclass+"]").each(function() {
		 			$(this).attr('key', 'hidden');
		 			$(this).hide('slow');
		 		});
		 		object.closest('tr').attr('name', layerId);
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
		 	var clasname = $("tr[name="+ layerType +"]").closest('tr').next().attr('class');
		 	$("tr[class="+clasname+"]").each(function() {
		 		$(this).show('slow');
		 		$(this).attr('key', 'displayed');
		 	});
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
				$("input[name="+layerId+"]").show();
				self.counter=self.counter+1;
				if(self.counter === 2) {
					$('img[name="close"]').hide();
				}
			}	
		},
		
		
		getEditProject : function(header, callback) {
			var self = this;
			try {
				//commonVariables.loadingScreen.showLoading();
				self.projectAPI.projects(header,
					function(response) {
						if (response !== null) {
							callback(response);
							//commonVariables.loadingScreen.removeLoading();
						} else {
							callback({ "status" : "service failure"});
							//commonVariables.loadingScreen.removeLoading();
						}

					},

					function(textStatus) {
						//commonVariables.loadingScreen.removeLoading();
					}
				);
			} catch(exception) {
				//commonVariables.loadingScreen.removeLoading();
			}

		},
		
		getRequestHeader : function(projectRequestBody, id, action) {
			var self=this, header, data = {}, userId;
			self.projectId = id;
			data = JSON.parse(self.projectAPI.localVal.getSession('userInfo'));
			var userId = (data !== null) ? data.id : "";
		
				header = {
					contentType: "application/json",
					requestMethod: "GET",
					dataType: "json",
					webserviceurl: commonVariables.webserviceurl + "project/edit?userId="+userId+"&customerId=photon&projectId="+id
				};

				if(action === "projectlist"){
					header.requestMethod = "GET";
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.projectlistContext +"/list?customerId=photon";
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
			var textboxval,techval,verval,count1 = 0,flagnew1 = 0,arr1 = [],flag = 0;
			$('.applnlayercontent').each(function(index,value) {
				if(($(value).css('display') !== 'none') && ($(value).attr('name') === 'staticApplnLayer')){
					textboxval = $(this).children('td.applnappcode').children('input.appln-appcode').val();
					techval = $(this).children('td.technology').children('select.appln_technology').val();
					techvalObj = $(this).children('td.technology').children('select.appln_technology');
					verval = $(this).children('td:eq(2)').children().val();
					if($("tr[class='applnLayer']").attr('key') === 'displayed'){
						if(textboxval === '') {
							var t = $(this).children('td:eq(0)').children();
							t.addClass("errormessage");
						 	t.focus();
							t.attr('placeholder','Enter AppCode');
							flag = 1;
							t.bind('keypress', function() {
								$(this).removeClass("errormessage");
							});
						}
						if(flag !== 1) {
							if(techval !== undefined || techval !== 'undefined') { 
								if(techval === 'Select Technology' || techval === null) {
									flagnew1 = 1;
									$(techvalObj).focus();
									$(".errmsg1").show();
									$(".errmsg1").text("Select Technology.");
									setTimeout(function() {
										$(".errmsg1").hide();
									}, 5000);
								} 
							}
						}
					}
					arr1[count1]=textboxval+techval+verval;
					$(this).next();
					count1++;
				}	
			});
			if(flag === 1) {
				return true;
			}	
			if(flagnew1 === 1) {
				return true;
			}	
			if($("tr[class='applnLayer']").attr('key') === 'displayed'){	
				for (var i = 0; i < arr1.length; i++) {
					for(var j = i+1;j<arr1.length;j++) {
						if(arr1[i] === arr1[j]) {
							$(".errmsg1").show();
							$(".errmsg1").text("Added Technology and Version already exists.");
							setTimeout(function() {
								$(".errmsg1").hide();
							}, 5000);
							return true;
						}	
					};	
				};
			}
		},

		webvalid : function() {
			$(".errmsg1").hide();
			var textbox2val,tech2val,widgetval,webverval,count2 = 0,flagged = 0,flagg = 0,flagnew2 = 0,arr2 = [];
			$('.weblayercontent').each(function(index,value) {
				if(($(value).css('display') !== 'none') && ($(value).attr('name') === 'staticWebLayer')){
					textbox2val = $(this).children('td.webappcode').children('input.web-appcode').val();
					tech2val = $(this).children('td.web').children('select.weblayer').val();
					widgetval = $(this).children('td.widget').children('select.web_widget').val();
					webverval = $(this).children('td.widgetversion').children('select.web_version').val();
					tech2valobj = $(this).children('td.web').children('select.weblayer');
					widgetvalobj = $(this).children('td.widget').children('select.web_widget');
					if($("tr[class='webLayer']").attr('key') === 'displayed'){
						if(textbox2val ==='') {
							var t = $(this).children('td:eq(0)').children();
							t.addClass("errormessage");
						 	t.focus();
							t.attr('placeholder', 'Enter AppCode');
							flagg = 1;
							t.bind('keypress', function() {
								$(this).removeClass("errormessage");
							});
						}
						if(flagg !== 1) {
							if(tech2val !== undefined || tech2val !== 'undefined') { 
								if(tech2val === 'Select Layer' || tech2val === null) {
									flagged=1;
									flagnew2 = 1;
									$(".errmsg2").show();
									$(".errmsg2").text("Select Layer.");
									$(tech2valobj).focus();	
								}
							}

							if(flagged !== 1) {
								if(widgetval !== undefined || widgetval !== 'undefined' ) {
									if(widgetval === 'Select Widget' || widgetval ===null) {
										flagnew2 = 1;
										$(".errmsg2").show();
										$(".errmsg2").text("Select Widget.");
										$(widgetvalobj).focus();
										setTimeout(function() {
											$(".errmsg2").hide();
										}, 5000);	
									}
								}
							}
						}
					}	
					arr2[count2]=textbox2val+tech2val+widgetval+webverval;
					$(this).next();
					count2++;
				}
			});
			if(flagg === 1)
				return true;
			if(flagnew2 === 1)
				return true;	
			$(".errmsg2").hide();
			if($("tr[class='webLayer']").attr('key')=='displayed'){
				for (var i = 0; i < arr2.length; i++) {
					for(var j=i+1;j<arr2.length;j++) {
						if(arr2[i]==arr2[j]) {
							$(".errmsg2").show();
							$(".errmsg2").text("Added Web Layer,Widget and Version already exists.");
							setTimeout(function() {
								$(".errmsg2").hide();
							}, 5000);
							return true;
						}	
					};	
			    };
		    }
		},

		mobvalid : function() {
			$(".errmsg2").hide();
			var textbox3val,layerval,typeval,mobverval,count3 = 0,flagger = 0,flaggg = 0,flagnew3 = 0,arr3 = [];
			$('.mobilelayercontent').each(function(index,value) {
				if(($(value).css('display') !== 'none') && ($(value).attr('name') === 'staticMobileLayer')){
					textbox3val = $(this).children('td.mobileappcode').children('input.mobile-appcode').val();
					layerval = $(this).children('td.mobile').children('select.mobile_layer').val();
					typeval = $(this).children('td.types').children('select.mobile_types').val();
					mobverval = $(this).children('td.mobileversion').children('select.mobile_version').val();
					layervalobj = $(this).children('td.mobile').children('select.mobile_layer');
					typevalobj = $(this).children('td.types').children('select.mobile_types'); 
					if($("tr[class='mobLayer']").attr('key') === 'displayed'){
						if(textbox3val === '') {
							var t = $(this).children('td:eq(0)').children();
							t.addClass("errormessage");
						 	t.focus();
							t.attr('placeholder','Enter AppCode');
							flaggg = 1;
							t.bind('keypress', function() {
								$(this).removeClass("errormessage");
							});
						}
						if(flaggg !== 1) {
							if(layerval !== undefined || layerval !== 'undefined') { 
								if(layerval === 'Select Model' ||layerval === null) {
									flagger=1;
									flagnew3=1;
									$(".errmsg3").show();
									$(".errmsg3").text("Select Model.");
									$(layervalobj).focus();
								}
							}
							if(flagger !== 1) {
								if(typeval !== undefined || typeval !=='undefined') {
									if(typeval === 'Select Type' || typeval === null) {
										flagnew3=1;
										$(".errmsg3").show();
										$(".errmsg3").text("Select Type.");
										$(typevalobj).focus();
										setTimeout(function() {
											$(".errmsg2").hide();
										}, 5000);
									}
								}
							}
						}	
					}
					arr3[count3]=textbox3val+layerval+typeval+mobverval;
					$(this).next();
					count3++;
				}	
			});
			if(flaggg === 1) {
				return true;
			}	
			if(flagnew3 === 1) {
				return true;	
			}
			$(".errmsg3").hide();	
			if($("tr[class='mobLayer']").attr('key') === 'displayed'){
				for (var i = 0; i < arr3.length; i++) {
					for(var j=i+1;j<arr3.length;j++) {
						if(arr3[i]==arr3[j]) {
							$(".errmsg3").show();
							$(".errmsg3").text("Added Mobile,Type and Version already exists.");
							setTimeout(function() {
								$(".errmsg3").hide();
							}, 5000);
							return true;
					    }	
					};	
			    };
			}
		},
		
		validation : function() {
			 var self = this;
			 var name = $("input[name='projectname']").val();
			 var code = $("input[name='projectcode']").val();
			 var labelversion = $("input[name='projectversion']").val();
			 var appcode = $("#appcode").val();
			 var webappcode = $("#webappcode").val();
			 var mobileappcode = $("#mobileappcode").val();

			    if(name === ""){
					self.valid("input[name='projectname']", "Enter Name");
					self.hasError = true;
			    }else if(code === ""){
					self.valid("input[name='projectcode']", "Enter Code");
					self.hasError = true;
			    }else if(labelversion === ""){
					self.valid("input[name='projectversion']", "Enter Version");
					self.hasError = true;
			    }else if(self.appvalid()) {
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
		
		addLayers :function(layerType, whereToAppend) {
			var self=this, minusIcon = '<img src="themes/default/images/helios/minus_icon.png" border="0" alt="">';
			
			var self = this, dynamicValue, applicationlayer = '<tr class="applnlayercontent" name="staticApplnLayer"><td class="applnappcode"><input type="text" id="appcode" maxlength="30" title="30 Characters only" class="appln-appcode"></td><td name="technology" class="technology"><select name="appln_technology" class="appln_technology"><option>Select Technology</option>'+ self.getTechnology() +'</select></td><td colspan="3" name="version" class="version"><select name="appln_version" class="appln_version"><option>Select Version</option></select></td><td class="appdependencyTd" colspan="2"><select class="selectpicker appdependencySelect" name="appdependencySelect" style="display:none" multiple><option value="0">Select Dependency</option></select></td><td><div class="flt_right icon_center"><a name="addApplnLayer"><img src="themes/default/images/helios/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeApplnLayer"><img src="themes/default/images/helios/minus_icon.png" border="0" alt=""></a></div></td></tr>',

			weblayer ='<tr class="weblayercontent" name="staticWebLayer"><td class="webappcode"><input type="text" id="webappcode" maxlength="30" title="30 Characters only" class="web-appcode"></td><td name="web" class="web"><select name="weblayer" class="weblayer"><option>Select Layer</option>'+self.getWidget() +'</select></td><td name="widget" class="widget"><select name="web_widget" class="web_widget"><option>Select Widget</option> </select></td><td name="widgetversion" class="widgetversion"><select name="web_version" class="web_version"><option>Select Version</option></select></td><td class="webdependencyTd"><select class="selectpicker webdependencySelect" name="webdependencySelect" style="display:none" multiple><option>Select Dependency</option></select></td><td> <div class="flt_right icon_center"><a href="javascript:;" name="addWebLayer"><img src="themes/default/images/helios/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeWebLayer"><img src="themes/default/images/helios/minus_icon.png" border="0" alt=""></a></div></td></tr>',

			mobilelayer = '<tr class="mobilelayercontent" name="staticMobileLayer"><td class="mobileappcode"><input type="text" id="mobileappcode" maxlength="30" title="30 Characters only" class="mobile-appcode"></td><td name="mobile" class="mobile"><select name="mobile_layer" class="mobile_layer"><option>Select Model</option>'+self.getMobile() +'</select></td><td name="types" class="types"><select name="mobile_types" class="mobile_types"><option>Select Type</option></select></td><td colspan="2" name="mobileversion" class="mobileversion"><select name="mobile_version" class="mobile_version"><option>Select Version</option></select></td><td><div class="flt_right icon_center"><a href="javascript:;" name="addMobileLayer"><img src="themes/default/images/helios/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeMobileLayer"><img src="themes/default/images/helios/minus_icon.png" border="0" alt=""></a></div></td></tr>';
			
			if (layerType === "addApplnLayer") {
				dynamicValue = $(applicationlayer).insertAfter(whereToAppend);
				self.multiselect();
				if($("input[name=multimodule]").val() === "false") {
					$(".appdependencyTd div.appdependencySelect").hide();
				} else {
					$(".appdependencyTd div.appdependencySelect").show();
				}	
				if (dynamicValue.prev('tr').attr("name") !== "dynamicAppLayer") {
					dynamicValue.prev('tr').find('a[name="addApplnLayer"]').html('');
					dynamicValue.prev('tr').find('a[name="removeApplnLayer"]').html(minusIcon);
				} else {
					dynamicValue.prev('tr').find('a[name="addApplnLayer"]').html('');
				}

			} else if (layerType === "addWebLayer") {
				dynamicValue = $(weblayer).insertAfter(whereToAppend);
				self.multiselect();
				if($("input[name=multimodule]").val() === "false") {
					$(".webdependencyTd div.webdependencySelect").hide();
				} else {
					$(".webdependencyTd div.webdependencySelect").show();
				}
				if (dynamicValue.prev('tr').attr("name") !== "dynamicWebLayer") {
					dynamicValue.prev('tr').find('a[name="addWebLayer"]').html('');
					dynamicValue.prev('tr').find('a[name="removeWebLayer"]').html(minusIcon);
				} else {
					dynamicValue.prev('tr').find('a[name="addWebLayer"]').html('');
				}
			} else {
				dynamicValue = $(mobilelayer).insertAfter(whereToAppend);
				self.multiselect();
				if (dynamicValue.prev('tr').attr("name") !== "dynamicMobileLayer") {
					dynamicValue.prev('tr').find('a[name="addMobileLayer"]').html('');
					dynamicValue.prev('tr').find('a[name="removeMobileLayer"]').html(minusIcon);
				} else {
					dynamicValue.prev('tr').find('a[name="addMobileLayer"]').html('');
				}
			}

			$("a[name=addApplnLayer]").unbind("click");
			$("a[name=addWebLayer]").unbind("click");
			$("a[name=addMobileLayer]").unbind("click");
			self.addLayersEvent();
			$("a[name=removeApplnLayer]").unbind("click");
			$("a[name=removeWebLayer]").unbind("click");
			$("a[name=removeMobileLayer]").unbind("click");
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
			
			$("#appcode, #webappcode, #mobileappcode").on('keyup', function(){
				$(this).val(self.specialCharValidation($(this).val().replace(/\s/g, "")));
			});

			$("a[name=addApplnLayer]").click(function(){
				whereToAppend = $(this).parents('tr.applnlayercontent:last');
				self.addLayers($(this).attr('name'), whereToAppend);
			});
			
			$("a[name=addWebLayer]").click(function(){
				whereToAppend = $("a[name=addWebLayer]").parents('tr.weblayercontent:last');
				self.addLayers($(this).attr('name'), whereToAppend);
			});
			
			$("a[name=addMobileLayer]").click(function(){
				whereToAppend = $("a[name=addMobileLayer]").parents('tr.mobilelayercontent:last');
				self.addLayers($(this).attr('name'), whereToAppend);
			});
		},
		
		removeLayersEvent : function() {
			var self=this, addIcon = '<img src="themes/default/images/helios/plus_icon.png" border="0" alt="">';
			$("a[name=removeApplnLayer]").click(function() {
				$("a[name=addApplnLayer]").html('');
				$(this).parent().parent().parent().remove();
				$("a[name=removeApplnLayer]").parent().parent().parent('tr:last').find('a[name="addApplnLayer"]').html(addIcon);
				if (($("a[name=removeApplnLayer]").parent().parent().parent('tr[name=staticApplnLayer]').length) === 1) {
					$('tr[name=staticApplnLayer]').find('a[name="addApplnLayer"]').html(addIcon);
					$("a[name=removeApplnLayer]").html('');
				}
				var removedAppcode = $(this).parents('tr.applnlayercontent').children('td.applnappcode').children('input.appln-appcode').val();
				var index = $.inArray(removedAppcode, self.appDepsArray);
				if (index >= 0) {
				   self.appDepsArray.splice(index, 1);
				}
				self.removedLayerDependency(removedAppcode);
			});

			$("a[name=removeWebLayer]").click(function(){
				$("a[name=addWebLayer]").html('');
				$(this).parent().parent().parent().remove();
				$("a[name=removeWebLayer]").parent().parent().parent('tr:last').find('a[name="addWebLayer"]').html(addIcon);
				if (($("a[name=removeWebLayer]").parent().parent().parent('tr[name=staticWebLayer]').length) === 1) {
					$('tr[name=staticWebLayer]').find('a[name="addWebLayer"]').html(addIcon);
					$("a[name=removeWebLayer]").html('');
				}
			});

			$("a[name=removeMobileLayer]").click(function(){
				$("a[name=addMobileLayer]").html('');
				$(this).parent().parent().parent().remove();
				$("a[name=removeMobileLayer]").parent().parent().parent('tr:last').find('a[name="addMobileLayer"]').html(addIcon);
				if (($("a[name=removeMobileLayer]").parent().parent().parent('tr[name=staticMobileLayer]').length) === 1) {
					$('tr[name=staticMobileLayer]').find('a[name="addMobileLayer"]').html(addIcon);
					$("a[name=removeMobileLayer]").html('');
				}
			});
		},
		
		technologyAndVersionChangeEvent : function() {
		
			var self=this;
			
			/***
			 ** Disabling select technology option
			 */
			$("select[name='appln_technology']").bind('click', function(){
				$("select[name='appln_technology'] option").each(function(index, value) {
					if($(value).text() === "Select Technology"){
						$(this).attr('disabled','disabled');
					}	
				});
			});
			
			/***
			 ** Disabling select Web Layer option
			 */
			$("select[name='weblayer']").bind('click', function(){
				$("select[name='weblayer'] option").each(function(index, value) {
					if($(value).text() === "Select Web Layer"){
						$(this).attr('disabled','disabled');
					}	
				});
			});
			
			/***
			 ** Disabling select model option
			 */
			$("select[name='mobile_layer']").bind('click', function(){
				$("select[name='mobile_layer'] option").each(function(index, value) {
					if($(value).text() === "Select Model"){
						$(this).attr('disabled','disabled');
					}	
				});
			});
			
			/***
			 ** Application Layer - Technology Change Event For Technology version
			 */
			$("select[name='appln_technology']").bind('change', function(){
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
			});
			
			/***
			 ** Web Layer - Type Change Event For Widget Version
			 */
			$("select[name='web_widget']").bind('change', function(){
				var widgetType = $(this).val();
				var widgetTypePlaceholder = $(this).parents("td[name='widget']").siblings("td[name='widgetversion']").children("select[name='web_version']");
				self.getwidgetversion(widgetType, widgetTypePlaceholder);
			});

			/***
			 ** Mobile Layer - Mobile Change Event For Mobile Type
			 */
			$("select[name='mobile_layer']").bind('change', function(){
				var mobile = $(this).val();
				var mobileTypePlaceholder = $(this).parents("td[name='mobile']").siblings("td[name='types']").children("select[name='mobile_types']");
				self.getmobiletype(mobile, mobileTypePlaceholder);
			});
			
			/***
			 **	Mobile Layer - Type Change Event For Mobile Version
			 */
			$("select[name='mobile_types']").unbind('change');
			$("select[name='mobile_types']").bind('change', function(){
				var mobileType = $(this).val();
				var mobileTypePlaceholder = $(this).parents("td[name='types']").siblings("td[name='mobileversion']").children("select[name='mobile_version']");
				self.getmobileversion(mobileType, mobileTypePlaceholder);
			});
			
			/***
			 **	AppLayer - Appcode Focus Event For Dependency 
			 */
			$("input.appln-appcode").bind('focusout', function(){
				if($(this).val() !== null && $(this).val() !== ''){
					var code = $(this).val();
					self.constructDepsArray(code);
					self.constructAppDependencyOptions();
					self.depsOptionsClick();
				} else {
					$(this).focus();
					$(this).attr('Placeholder', 'Enter Appcode');
				}	
			});
			
			$("input.web-appcode").bind('focusout', function(){
				if($(this).val() !== null && $(this).val() !== ''){
					var code = $(this).val();
					self.constructWebDependencyOptions();
					self.depsOptionsClick();
				} else {
					$(this).focus();
					$(this).attr('Placeholder', 'Enter Appcode');
				}	
			});
		},
		
		depsOptionsClick : function() {
			var self = this;
			$('.appdependencyTd .dropdown-menu li').unbind('click');
			$('.appdependencyTd .dropdown-menu li').click(function(){
				var selectedOption = $(this).find('span').text();
				var selectedAppcode = $(this).parents('td.appdependencyTd').siblings('td.applnappcode').children('input.appln-appcode').val();
				
				if(selectedOption !== "" && jQuery.inArray(selectedOption, self.selectedVal) === -1){
					self.selectedVal.push(selectedOption);
				}
				
				if($(this).attr('class') !== "selected"){
				   self.removeSelectedDependecy(selectedOption, selectedAppcode);
				} else {
					self.constructRemovedDependency(selectedOption, selectedAppcode);
				}  	
			});
		},
		
		getTechnology : function(id) {
			var self=this, option;
			self.applicationlayerData = self.projectAPI.localVal.getJson("Application Layer");
			option = '';
			$.each(self.applicationlayerData.techGroups, function(index, value){
				$.each(value.techInfos, function(index, value){
					if (id === value.id) {
						option += '<option value='+ value.id +' selected=selected>'+ value.name +'</option>';
					} else {
						option += '<option value='+ value.id +'>'+ value.name +'</option>';
					}
				});
			});
			
			return option;
		},
		
		getWidget : function() {
			var self=this, option;
			self.weblayerData = self.projectAPI.localVal.getJson("Web Layer");
			option = '';
			$.each(self.weblayerData.techGroups, function(index, value){
				option += '<option value='+ value.id +'>'+ value.name +'</option>';
			});
			
			return option;
		},
		
		getMobile : function(id) {
			var self=this, option;
			self.mobilelayerData = self.projectAPI.localVal.getJson("Mobile Layer");
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
		
		gettechnologyversion : function(technologyId, versionplaceholder) {
			var self=this, option;
			self.applicationlayerData = self.projectAPI.localVal.getJson("Application Layer");
			$.each(self.applicationlayerData.techGroups, function(index, value){
				$.each(value.techInfos, function(index, value){
				    if(value.id === technologyId){
						option = '';
						$.each(value.techVersions, function(index, value){
							option += '<option>'+ value +'</option>';
						});
						
						$(versionplaceholder).html(option);
					}
				});
			});
		},
		
		getwidgettype : function(type, widgetTypePlaceholder) {
			var self=this, option;
			self.weblayerData = self.projectAPI.localVal.getJson("Web Layer");
			$.each(self.weblayerData.techGroups, function(index, value){
				if(value.id === type){
					option = '';
					option += '<option disabled selected>Select Widget</option>';
					$.each(value.techInfos, function(index, value){
						option += '<option value='+ value.id +'>'+ value.name +'</option>';
					});
					
					$(widgetTypePlaceholder).html(option);
				}
			});
		},
		
		editgetwidgettype : function(id) {
			var self=this, option;
			self.weblayerData = self.projectAPI.localVal.getJson("Web Layer");
			$.each(self.weblayerData.techGroups, function(index, value){
				$.each(value.techInfos, function(index, value){
					if (id === value.id) {
						option += '<option value='+ value.id +' selected=selected>'+ value.name +'</option>';
					} else {
						option += '<option value='+ value.id +'>'+ value.name +'</option>';
					}
				});
			});
			
			return option;
		},
		
		getwidgetversion : function(widgettype, widgetTypePlaceholder) {
			var self=this, option;
			self.weblayerData = self.projectAPI.localVal.getJson("Web Layer");
			$.each(self.weblayerData.techGroups, function(index, value){
				$.each(value.techInfos, function(index, value){
					if(value.id === widgettype){
						option = '';
						$.each(value.techVersions, function(index, value){
							option += '<option>'+ value +'</option>';
						});
						
						$(widgetTypePlaceholder).html(option);
					}
				});
			});
		},
		
		getmobiletype : function(mobile, mobileTypePlaceholder) {
			var self=this, option;
			self.mobilelayerData = self.projectAPI.localVal.getJson("Mobile Layer");
			$.each(self.mobilelayerData.techGroups, function(index, value){
				if(value.id === mobile){
					option = '';
					option += '<option disabled selected>Select Type</option>';
					$.each(value.techInfos, function(index, value){
						option += '<option value='+ value.id +'>'+ value.name +'</option>';
					});
					
					$(mobileTypePlaceholder).html(option);
				}
			});
		},
		
		editgetmobiletype : function(id) {
			var self=this, option;
			self.mobilelayerData = self.projectAPI.localVal.getJson("Mobile Layer");
			$.each(self.mobilelayerData.techGroups, function(index, value){
				$.each(value.techInfos, function(index, value){
					if (id === value.id) {
						option += '<option value='+ value.id +' selected=selected>'+ value.name +'</option>';
					} else {
						option += '<option value='+ value.id +'>'+ value.name +'</option>';
					}
				});
			});
			
			return option;
		},
		
		getmobileversion : function(mobileType, mobileTypePlaceholder) {
			var self=this, option;
			self.mobilelayerData = self.projectAPI.localVal.getJson("Mobile Layer");
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
					}
				});
			});
		},
		
		pilotprojectsEvent : function() {
			var self=this;
			$("select[name='prebuiltapps']").hide();
			$("select[name='builtmyself']").bind('change', function(){
				$("input.appln-appcode").val('');
				$("input.web-appcode").val('');
				$("input.mobile-appcode").val('');
				var selectedText = $(this).find(':selected').val();
				if(selectedText === "prebuilt"){
					 $("tr[name=applicationLayer]").hide();
					 $("tr.applnLayer").hide();
					 $("tr.applnLayer").attr('key','hidden');	
					 $("tr[name=web-Layer]").hide();
					 $("tr.webLayer").hide();
					 $("tr.webLayer").attr('key','hidden');	
					 $("tr[name=mobile-Layer]").hide();
					 $("tr.mobLayer").hide();
					 $("tr.mobLayer").attr('key','hidden');	
					 $("input[name='startdate']").attr("disabled", true);
					 $("input[name='enddate']").attr("disabled", true);
					 $("select[name='builtmyselfapps']").hide();
					 $("#applicationlayer").hide();
					 $("a[name=addApplnLayer]").hide();
					 $("a[name=addWebLayer]").hide();
					 $("a[name=addMobileLayer]").hide();
					 $("#weblayer").hide();
					 $("#mobilelayer").hide();
					 self.setPilotData(function(Option){
						$("select[name='prebuiltapps']").css('display' , 'inline-block');
						$("select[name='prebuiltapps']").html(Option);
						var selectedPilot = $("select[name='prebuiltapps']").find(':selected').text();
						self.layerRender(selectedPilot);
					 });
				} else {
					 $("tr[name=applicationLayer]").show();
					 $("tr.applnLayer").show();
					 $("tr.applnLayer").attr('key','displayed');	
					 $("tr[name=web-Layer]").show();
					 $("tr.webLayer").show();
					 $("tr.webLayer").attr('key','displayed');	
					 $("tr[name=mobile-Layer]").show();
					 $("tr.mobLayer").show();
					 $("tr.mobLayer").attr('key','displayed');	
					 $("select[name='builtmyselfapps']").css('display' , 'inline-block');
					 $("input[name='startdate']").attr("disabled", false);
					 $("input[name='enddate']").attr("disabled", false);
					 $("select[name='prebuiltapps']").hide();
					 $("#applicationlayer").show();
					 $("#weblayer").show();
					 $("#mobilelayer").show();
					 $("a[name=addApplnLayer]").show();
					 $("a[name=addWebLayer]").show();
					 $("a[name=addMobileLayer]").show();
					 self.revertSelectValues($("select[name='appln_technology']"),"Select Technology");
					 $("select[name=appln_version]").html('<option>Select Version</option>');
					 self.revertSelectValues($("select[name='weblayer']"),"Select Layer");
					 self.revertSelectValues($("select[name='mobile_layer']"),"Select Model");
					 $("select[name=web_widget]").html('<option>Select Widget</option>');
					 $("select[name=web_version]").html('<option>Select Version</option>');
					 $("select[name=mobile_types]").html('<option>Select Type</option>');
					 $("select[name=mobile_version]").html('<option>Select Version</option>'); 
				}
			});
			
			$("select[name='prebuiltapps']").change(function() {
				$("input.appln-appcode").val('');
				$("input.web-appcode").val('');
				$("input.mobile-appcode").val('');
				var selectedPilot = $(this).find(':selected').text();
				self.layerRender(selectedPilot);
			});
		},
		
		revertSelectValues : function(obj, OptionText) {
			$(obj).find('option').each(function(index, value) {
				$(value).removeAttr('selected');
			});
			if($(obj).find('option').val() === OptionText) {
				$(obj).find('option').attr('selected', 'selected');
				$(obj).val(OptionText);
			}
		},
		
		setPilotData : function(callback) {
			var self=this, option = '';
			self.getEditProject(self.getRequestHeader(self.projectRequestBody, "", "pilotlist"), function(response) {
				$.each(response.data, function(index, value){
					self.projectAPI.localVal.setJson(value.name, value);
					if(value.displayName !== undefined && value.displayName !== null && value.displayName !== '') {
						option += '<option>'+ value.displayName +'</option>';
					}
					if(response.data.length === (index + 1)){
						callback(option);
					} 
				});
			});
		},
		
		layerRender : function(selectedPilot) {
			var self=this;
			var selectedPilotData = self.projectAPI.localVal.getJson(selectedPilot);
			if(selectedPilotData !== undefined && selectedPilotData !== null) {
				$("tr[name=applicationLayer]").hide();
				$("tr.applnLayer").hide();
				$("tr.applnLayer").attr('key','hidden');	
				$("tr[name=web-Layer]").hide();
				$("tr.webLayer").hide();
				$("tr.webLayer").attr('key','hidden');	
				$("tr[name=mobile-Layer]").hide();
				$("tr.mobLayer").hide();
				$("tr.mobLayer").attr('key','hidden');
				$.each(selectedPilotData.appInfos, function(index, appInfo){
					if(appInfo.techInfo.appTypeId === "app-layer"){
						$("select[name='appln_technology'] option").each(function(index, value) {
							$(value).removeAttr('selected');	
							if($(value).val() === appInfo.techInfo.id) {
								$(value).attr('selected', 'selected');
								$("select[name='appln_technology']").val($(value).val());
							}
						});
						var techId = $("select[name='appln_technology']").val();
						var versionplaceholder = $("select[name='appln_technology']").parents("td[name='technology']").siblings("td[name='version']").children("select[name='appln_version']");
						self.gettechnologyversion(techId, versionplaceholder);
						$("tr[name=applicationLayer]").show();
						$("tr.applnLayer").show();
						$("tr.applnLayer").attr('key','displayed');
					} else if (appInfo.techInfo.appTypeId === "web-layer") {
						$("select[name='weblayer'] option").each(function(index, value) {
							$(value).removeAttr('selected');	
							if($(value).val() === appInfo.techInfo.techGroupId) {
								$(value).attr('selected', 'selected');
								$("select[name='weblayer']").val($(value).val());
							}
						});
						var type = $("select[name='weblayer']").val();
						var widgetTypePlaceholder = $("select[name='weblayer']").parents("td[name='web']").siblings("td[name='widget']").children("select[name='web_widget']");
						self.getwidgettype(type, widgetTypePlaceholder);
						$("select[name='web_widget'] option").each(function(index, value) {
							$(value).removeAttr('selected');	
							if($(value).val() === appInfo.techInfo.id) {
								$(value).attr('selected', 'selected');
								$("select[name='web_widget']").val($(value).val());
							}
						});
						var widgetType = $("select[name='web_widget']").val();
						var widgetTypePlaceholder = $("select[name='web_widget']").parents("td[name='widget']").siblings("td[name='widgetversion']").children("select[name='web_version']");
						self.getwidgetversion(widgetType, widgetTypePlaceholder);
						$("tr[name=web-Layer]").show();
						$("tr.webLayer").show();
						$("tr.webLayer").attr('key','displayed');
					} else if(appInfo.techInfo.appTypeId === "mob-layer") {
						$("select[name='mobile_layer'] option").each(function(index, value){
							$(value).removeAttr('selected');	
							if($(value).val() === appInfo.techInfo.techGroupId) {
								$(value).attr('selected', 'selected');
								$("select[name='mobile_layer']").val($(value).val());
							}
						});
						var mobile = $("select[name='mobile_layer']").val();
						var mobileTypePlaceholder = $("select[name='mobile_layer']").parents("td[name='mobile']").siblings("td[name='types']").children("select[name='mobile_types']");
						self.getmobiletype(mobile, mobileTypePlaceholder);
						$("select[name='mobile_types'] option").each(function(index, value){
							$(value).removeAttr('selected');	
							if($(value).val() === appInfo.techInfo.id) {
								$(value).attr('selected', 'selected');
								$("select[name='mobile_types']").val($(value).val());
							}
						});
						var mobileType = $("select[name='mobile_types']").val();
						var mobileTypePlaceholder = $("select[name='mobile_types']").parents("td[name='types']").siblings("td[name='mobileversion']").children("select[name='mobile_version']");
						self.getmobileversion(mobileType, mobileTypePlaceholder);	
						$("tr[name=mobile-Layer]").show();
						$("tr.mobLayer").show();
						$("tr.mobLayer").attr('key','displayed');
					}
				});
			}		
		}, 
		
		multiModuleEvent : function(multimodule){
			var self = this;
			if(multimodule === "true"){
				$("span[name=appdependency]").show();
				$("span[name=webdependency]").show();
				$("span[name=artifactId-lbl]").show();
				$("input[name=artifactId]").show();
				self.showDependency();
			} else {
				$("span[name=appdependency]").hide();
				$("span[name=webdependency]").hide();
				$("span[name=artifactId-lbl]").hide();
				$("input[name=artifactId]").hide();
				self.hideDependency();
			}
		},
		
		showDependency : function() {
			var self = this, appdependencyDiv, webdependencyDiv;
			$.each($("tbody[name='layercontents'] > div.mCustomScrollBox > div.mCSB_container").children(), function(index, value){
				if($(value).attr('class') === "applnLayer" && $(value).attr('key') === "displayed") {
					var applnlayerDiv = $(value).children('td.appln').children('table.applnlayer').children('tbody.applnlayer');
					$.each($(applnlayerDiv).children(), function(index, value){
						appdependencyDiv = $(value).children("td.appdependencyTd");
						$(appdependencyDiv).find("div.appdependencySelect").show();
					});
				} else if($(value).attr('class') === "webLayer" && $(value).attr('key') === "displayed") {
					var weblayerDiv = $(value).children('td.WebLayer').children('table.WebLayer').children('tbody.WebLayer');
					$.each($(weblayerDiv).children(), function(index, value){
						webdependencyDiv = $(value).children("td.webdependencyTd");
						$(webdependencyDiv).find("div.webdependencySelect").show();
					});
				}
			});
		},

		hideDependency : function() {
			$.each($("tbody[name='layercontents'] > div.mCustomScrollBox > div.mCSB_container").children(), function(index, value){
				if($(value).attr('class') === "applnLayer" && $(value).attr('key') === "displayed") {
					var applnlayerDiv = $(value).children('td.appln').children('table.applnlayer').children('tbody.applnlayer');
					$.each($(applnlayerDiv).children(), function(index, value){
					var appdependencyDiv = $(value).children("td.appdependencyTd");
					$(appdependencyDiv).find("div.appdependencySelect").hide();
					});
					} else if($(value).attr('class') === "webLayer" && $(value).attr('key') === "displayed") {
					var weblayerDiv = $(value).children('td.WebLayer').children('table.WebLayer').children('tbody.WebLayer');
					$.each($(weblayerDiv).children(), function(index, value){
						var webdependencyDiv = $(value).children("td.webdependencyTd");
						$(webdependencyDiv).find("div.webdependencySelect").hide();
					});
				}
			});
		},

		
		constructDepsArray : function(applnAppcode) {
			var self = this;
			$.each($("tbody[name='layercontents'] > div.mCustomScrollBox > div.mCSB_container").children(), function(index, value){
				if($(value).attr('class') === "applnLayer" && $(value).attr('key') === "displayed") {
					var applnlayerDiv = $(value).children('td.appln').children('table.applnlayer').children('tbody.applnlayer');
					$.each($(applnlayerDiv).children(), function(index, value){
						if(applnAppcode !== "" && jQuery.inArray(applnAppcode, self.appDepsArray) === -1){
							self.appDepsArray.push(applnAppcode);
						} 
					});
				}	
			});
		},
		
		constructAppDependencyOptions : function() {
		
			var self = this, depsSelectPlaceholder;
			$.each($("tbody[name='layercontents'] > div.mCustomScrollBox > div.mCSB_container").children(), function(index, value){
				if($(value).attr('class') === "applnLayer" && $(value).attr('key') === "displayed") {
					var applnlayerDiv = $(value).children('td.appln').children('table.applnlayer').children('tbody.applnlayer');
					$.each($(applnlayerDiv).children(), function(index, value){
						var code = $(value).children("td.applnappcode").children("input.appln-appcode").val();
						depsSelectPlaceholder = $(value).children('td.appdependencyTd').children("select.appdependencySelect");
						if(depsSelectPlaceholder !== undefined && depsSelectPlaceholder !== null) {
							var option = '';
							$.each(self.appDepsArray, function(index, depsValue){
								var exists = false;
								$(depsSelectPlaceholder).find("option").each(function(index, value){
									if($(value).val() !== 0){
										if($(value).val() === depsValue) {
											exists = true;
											return false;
										}
									}
								});
								if(!exists && code !== depsValue) {
									$(depsSelectPlaceholder).append($('<option/>').attr('name','selectedVal').val(depsValue).text(depsValue));
									$(depsSelectPlaceholder).selectpicker('refresh');
								}
							});	
						}
					});
				}
			});
		},
		
		constructWebDependencyOptions : function(){
		
			var self = this, depsSelectPlaceholder;
			$.each($("tbody[name='layercontents'] > div.mCustomScrollBox > div.mCSB_container").children(), function(index, value){
				if($(value).attr('class') === "webLayer" && $(value).attr('key') === "displayed") {
					var weblayerDiv = $(value).children('td.WebLayer').children('table.WebLayer').children('tbody.WebLayer');
					$.each($(weblayerDiv).children(), function(index, value){
						var code = $(value).children("td.webappcode").children("input.web-appcode").val();
						depsSelectPlaceholder = $(value).children('td.webdependencyTd').children("select.webdependencySelect");
						if(depsSelectPlaceholder !== undefined && depsSelectPlaceholder !== null) {
							var option = '';
							$.each(self.appDepsArray, function(index, depsValue){
								var exists = false;
								$(depsSelectPlaceholder).find("option").each(function(index, value){
									if($(value).val() !== 0){
										if($(value).val() === depsValue) {
											exists = true;
											return false;
										}
									}
								});
								if(!exists) {
									$(depsSelectPlaceholder).append($('<option/>').attr('name','selectedVal').val(depsValue).text(depsValue));
									$(depsSelectPlaceholder).selectpicker('refresh');
								}
							});	
						}
					});
				}
			});
		},
		
		removeSelectedDependecy : function(selectedOption, selectedAppcode){
			
			var self = this, depsSelectPlaceholder;
			$.each($("tbody[name='layercontents'] > div.mCustomScrollBox > div.mCSB_container").children(), function(index, value){
				if($(value).attr('class') === "applnLayer" && $(value).attr('key') === "displayed") {
					var applnlayerDiv = $(value).children('td.appln').children('table.applnlayer').children('tbody.applnlayer');
					$.each($(applnlayerDiv).children(), function(index, value){
						var code = $(value).children("td.applnappcode").children("input.appln-appcode").val();
						depsSelectPlaceholder = $(value).children('td.appdependencyTd').children("select.appdependencySelect");
						if(depsSelectPlaceholder !== undefined && depsSelectPlaceholder !== null){
							if(code === selectedOption){
								$(depsSelectPlaceholder).find("option").each(function(index, value){
									if($(value).val() !== 0){
										if($(value).val() === selectedAppcode) {
											$(value).remove();
											$(depsSelectPlaceholder).selectpicker('refresh');
											self.depsOptionsClick();
										}
									}
								});
							}
						}
					});
				}	
			});
		},
		
		removedLayerDependency : function(removedAppcode){
			
			var self=this, depsSelectPlaceholder;
			$.each($("tbody[name='layercontents'] > div.mCustomScrollBox > div.mCSB_container").children(), function(index, value){
				if($(value).attr('class') === "applnLayer" && $(value).attr('key') === "displayed") {
					var applnlayerDiv = $(value).children('td.appln').children('table.applnlayer').children('tbody.applnlayer');
					$.each($(applnlayerDiv).children(), function(index, value){
						var code = $(value).children("td.applnappcode").children("input.appln-appcode").val();
						depsSelectPlaceholder = $(value).children('td.appdependencyTd').children("select.appdependencySelect");
						if(depsSelectPlaceholder !== undefined && depsSelectPlaceholder !== null){
							$(depsSelectPlaceholder).find("option").each(function(index, value){
								if($(value).val() !== 0){
									if($(value).val() === removedAppcode) {
										$(value).remove();
										$(depsSelectPlaceholder).selectpicker('refresh');
										self.depsOptionsClick();
									}
								}
							});
						}
					});
				}
				
				if($(value).attr('class') === "webLayer" && $(value).attr('key') === "displayed") {
					var weblayerDiv = $(value).children('td.WebLayer').children('table.WebLayer').children('tbody.WebLayer');
					$.each($(weblayerDiv).children(), function(index, value){
						var code = $(value).children("td.webappcode").children("input.web-appcode").val();
						depsSelectPlaceholder = $(value).children('td.webdependencyTd').children("select.webdependencySelect");
						if(depsSelectPlaceholder !== undefined && depsSelectPlaceholder !== null) {
							$(depsSelectPlaceholder).find("option").each(function(index, value){
								if($(value).val() !== 0){
									if($(value).val() === removedAppcode) {
										$(value).remove();
										$(depsSelectPlaceholder).selectpicker('refresh');
										self.depsOptionsClick();
									}
								}
							});
						}
					});
				}	
			});
		},
		
		constructRemovedDependency : function(selectedOption, selectedAppcode){
			 
			 var self = this, depsSelectPlaceholder;
			 $.each($("tbody[name='layercontents'] > div.mCustomScrollBox > div.mCSB_container").children(), function(index, value){
				if($(value).attr('class') === "applnLayer" && $(value).attr('key') === "displayed") {
					var applnlayerDiv = $(value).children('td.appln').children('table.applnlayer').children('tbody.applnlayer');
					$.each($(applnlayerDiv).children(), function(index, value){
						var code = $(value).children("td.applnappcode").children("input.appln-appcode").val();
						depsSelectPlaceholder = $(value).children('td.appdependencyTd').children("select.appdependencySelect");
						if(code === selectedOption){
							if(depsSelectPlaceholder !== undefined && depsSelectPlaceholder !== null) {
								var exists = false;
								$(depsSelectPlaceholder).find("option").each(function(index, value){
									if($(value).val() !== 0){
										if($(value).val() === selectedAppcode) {
											exists = true;
											return false;
										}
									}
								});
								if(!exists) {
									$(depsSelectPlaceholder).append($('<option/>').attr('name','selectedVal').val(selectedAppcode).text(selectedAppcode));
									self.depsOptionsClick();
									var index = $.inArray(selectedOption, self.selectedVal);
									 if (index >= 0) {
										self.selectedVal.splice(index, 1);
									 }
									$(depsSelectPlaceholder).selectpicker('refresh');
									self.depsOptionsClick();
								}
							}
						}	
					});
			     }
             }); 				
		},
		
		enablebutton : function() {
			if($('#appLayaer').css('display') === "none") {
					$("input[name='appLayaer']").show();
			}	
			if($('#webLayers').css('display') === "none") {
					$("input[name='webLayers']").show();
			}	
			if($('#mobLayers').css('display') === "none") {
					$("input[name='mobLayers']").show();
			}
		},
		
		editSeriveTechnolyEvent : function(getData) {
			var self = this, addIcon = '<img src="themes/default/images/helios/plus_icon.png" border="0" alt="">';
			$("#appLayaer").hide();
			$("tr.applnLayer").hide();
			$("#webLayers").hide();
			$("tr.webLayer").hide();
			$("#mobLayers").hide();
			$("tr.mobLayer").hide();

			$.each(getData, function(index, value) {
				if (value.techInfo.appTypeId === "app-layer") {
					$("#appLayaer").show();
					$("tr.applnLayer").show();
					$('img[name="close"]').hide();
					var option = '';
					if(value.dependentModules !== null) {
						$.each(value.dependentModules, function(index, value){
							option += '<option selected>'+ value +'</option>';
						});
					} else {
						option = '';
					}	
					var appendData = '<tr class="applnlayercontent" name="dynamicAppLayer"><td><input type="text" value="'+value.code+'" disabled></td><td><select disabled>'+ self.getTechnology(value.techInfo.id) +'</select></td><td colspan="3"><select disabled><option>'+value.techInfo.version+'</option></select></td><td colspan="2" class="appdependencyTd"><select class="selectpicker appdependencySelect" multiple title="Select Dependency" data-selected-text-format="count>2"><option value="0" disabled>Select Dependency</option>'+ option +'</select></td><td><div class="flt_right icon_center"><a name="addApplnLayer"></a><a href="javascript:;" name="removeApplnLayer"></a></div></td></tr>';
					$("tbody.applnLayer").append(appendData);
					self.multiselect();
					if($("input[name=multimodule]").val() === "false") {
						$(".appdependencyTd div.appdependencySelect").hide();
					} else {
						$(".appdependencyTd div.appdependencySelect").show();
					}
				} else if (value.techInfo.appTypeId === "web-layer") {
					$("#webLayers").show();
					$("tr.webLayer").show();
					$('img[name="close"]').hide();
					if(value.dependentModules !== null) {
						$.each(value.dependentModules, function(index, value){
							option += '<option selected>'+ value +'</option>';
						});
					} else {
						option = '';
					}
					var appendData = '<tr class="weblayercontent" name="dynamicWebLayer"><td><input type="text" value="'+value.code+'" disabled></td><td><select name="weblayer" disabled><option>'+value.techInfo.techGroupId+'</option></select></td><td name="widget"><select name="web_widget" disabled> '+ self.editgetwidgettype(value.techInfo.id) +'</select></td> <td name="widgetversion"><select name="web_version" disabled><option>'+value.techInfo.version+'</option></select></td><td class="webdependencyTd"><select class="selectpicker webdependencySelect" multiple data-selected-text-format="count>2" title="Select Dependency"><option value="0" disabled>Select Dependency</option>'+ option +'</select></td><td><div class="flt_right icon_center"><a href="javascript:;" name="addWebLayer"></a><a href="javascript:;" name="removeWebLayer"></a></div></td></tr>';
					$("tbody.WebLayer").append(appendData);
					self.multiselect();
					if($("input[name=multimodule]").val() === "false") {
						$(".webdependencyTd div.webdependencySelect").hide();
					} else {
						$(".webdependencyTd div.webdependencySelect").show();
					}
				} else if (value.techInfo.appTypeId === "mobile-layer") {
					$("#mobLayers").show();
					$("tr.mobLayer").show();
					$('img[name="close"]').hide();
					var appendData = '<tr class="mobilelayercontent" name="dynamicMobileLayer"><td><input type="text" value="'+value.code+'" disabled></td><td><select disabled><option>'+value.techInfo.techGroupId+'</option></select></td><td><select name="mobile_types" disabled>'+self.editgetmobiletype(value.techInfo.id)+'</select></td><td colspan="2"><select disabled><option>'+value.techInfo.version+'</option></select></td><td><div class="flt_right icon_center"><a href="javascript:;" name="addMobileLayer"></a><a href="javascript:;" name="removeMobileLayer"></a> </div></td></tr>';
					$("tbody.MobLayer").append(appendData);
					self.multiselect();
				}
			});
			$("tr[name=dynamicAppLayer]:last").find("a[name=addApplnLayer]").html(addIcon);
			$("tr[name=dynamicWebLayer]:last").find("a[name=addWebLayer]").html(addIcon);
			$("tr[name=dynamicMobileLayer]:last").find("a[name=addMobileLayer]").html(addIcon);
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
			if(!self.validation()) {
				var projectname = $("input[name='projectname']").val();
				var projectcode = $("input[name='projectcode']").val();
				var projectversion = $("input[name='projectversion']").val();
				var projectdescription = $("textarea[name='projectdescription']").val();
				var startdate = $("input[name='startdate']").val();
				var enddate = $("input[name='enddate']").val();
				//To convert dd/mm//yyyy to (month date,year) format
				var myStartDate = new Date(startdate);
				var myEndDate = new Date(enddate);
				var multimodule = $("input[name='multimodule']").val() === "true"? true:false;
				if($("select[name=builtmyself]").find(":selected").val() !== undefined) {
					var	preBuilt = $("select[name=builtmyself]").find(":selected").val() === "custom"? false:true;
				}	
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
				self.projectInfo.multiModule = multimodule;
				self.projectInfo.customerIds = self.customerIds;
							
				$.each($("tbody[name='layercontents'] > div.mCustomScrollBox > div.mCSB_container").children(), function(index, value){
				
					var techInfo = {};
					var tech;
					var techName = "";
					var code = "";
					var dependency = "";
					var appInfo = {};
					var mobdata = {};
					var versionText = "";
					
					if($(value).attr('class') === "applnLayer" && $(value).attr('key') === "displayed") {
						var applnlayerDiv = $(value).children('td.appln').children('table.applnlayer').children('tbody.applnlayer');
						$.each($(applnlayerDiv).children(), function(index, value){
							if(($(value).css('display') !== "none") && ($(value).attr('name') === "staticApplnLayer")) {
								var appInfo = {};
								var techInfo = {};
								tech = $(value).children("td.technology").children("select.appln_technology");
								techName = $(tech).find(":selected").text();
								code = $(value).children("td.applnappcode").children("input.appln-appcode").val();
								dependency = $(value).children('td.appdependencyTd').children("select.appdependencySelect").val();
								appInfo.code = code;
								appInfo.appDirName = code;
								appInfo.version = projectversion;
								appInfo.name = code;
								if(dependency !== null){
									appInfo.dependentModules = dependency;
								}	
								techInfo.id = $(value).children("td.technology").children("select.appln_technology").val();
								techInfo.appTypeId = "app-layer";
								techInfo.name = $(value).children("td.technology").children("select.appln_technology").find(":selected").text();
								techInfo.version = $(value).children("td.version").children("select.appln_version").val();
								if (appInfo.code !== undefined && appInfo.code !== null) {
									appInfo.techInfo = techInfo;
									self.appInfos.push(appInfo);
									count++;
								}
							}	
						});	
					} 
					
					if(($(value).attr('class') === "webLayer") && ($(value).attr('key') === "displayed")) {
						var weblayerDiv = $(value).children('td.WebLayer').children('table.WebLayer').children('tbody.WebLayer');
						$.each($(weblayerDiv).children(), function(index, value){
							if($(value).css('display') !== "none" && $(value).attr('name') === "staticWebLayer") {
								var appInfo = {};
								var techInfo = {};
								tech = $(value).children("td.widget").children("select.web_widget");
								techName = $(tech).find(":selected").text();
								code = $(value).children("td.webappcode").children("input.web-appcode").val();
								dependency = $(value).children('td.webdependencyTd').children("select.webdependencySelect").val();
								appInfo.code = code;
								appInfo.appDirName = code;
								appInfo.version = projectversion;
								appInfo.name = code;
								if(dependency !== null){
									appInfo.dependentModules = dependency;
								}		
								techInfo.id = $(value).children("td.widget").children("select.web_widget").val();
								techInfo.appTypeId = "web-layer";
								techInfo.techGroupId = $(value).children("td.web").children("select.weblayer").find(":selected").text();
								techInfo.version = $(value).children("td.widgetversion").children("select.web_version").find(":selected").text();
								techInfo.name = $(value).children("td.widget").children("select.web_widget").find(":selected").text();
								if (appInfo.code !== undefined && appInfo.code !== null) {
									appInfo.techInfo = techInfo;
									self.appInfos.push(appInfo);
									count++;
								}
							}	
						});	
					}  

					if(($(value).attr('class') === "mobLayer") && ($(value).attr('key') === "displayed")) {
						var mobilelayerDiv = $(value).children('td.mob').children('table.mob-table').children('tbody');
						$.each($(mobilelayerDiv).children(), function(index, value){
							if($(value).css('display') !== "none" && $(value).attr('name') === "staticMobileLayer") {
								var appInfo = {};
								var techInfo = {};
								versionText = $(value).children("td.mobileversion").children("select.mobile_version").find(":selected").text();
								tech = $(value).children("td.types").children("select.mobile_types");
								techName = $(tech).find(":selected").text();
								code = $(value).children("td.mobileappcode").children("input.mobile-appcode").val();
								appInfo.code = code;
								appInfo.appDirName = code; 
								appInfo.version = projectversion;
								appInfo.name = code; 
								techInfo.id = $(value).children("td.types").children("select.mobile_types").find(':selected').val();
								techInfo.appTypeId = "mobile-layer";
								techInfo.techGroupId = $(value).children("td.mobile").children("select.mobile_layer").find(':selected').text();
								techInfo.name = $(value).children("td.types").children("select.mobile_types").find(':selected').text();
								if(versionText === "No Versions available") {
									techInfo.version = "";
								} else {
									techInfo.version = $(value).children("td.mobileversion").children("select.mobile_version").find(":selected").text();
								}
								if (appInfo.code !== undefined && appInfo.code !== null) {
									appInfo.techInfo = techInfo;
									self.appInfos.push(appInfo);
									count++;
								}
							}
						});
					}    	
					
				});
				
				var appInfos = $.merge($.merge($.merge([],self.appInfos), self.appInfosweb), self.appInfosmobile);
				self.projectInfo.noOfApps = count;
				self.projectInfo.appInfos = appInfos;
				self.projectRequestBody = self.projectInfo;
				
				if(projectId !== '') {
					self.projectInfo.id = projectId;
				}
				
				self.appDepsArray = [];
				self.getEditProject(self.getRequestHeader(self.projectRequestBody, "", action), function(response) {
					self.projectRequestBody = {};
					
					if(((response.message) === "Project created Successfully") || ((response.message) === "Project updated Successfully")) {
						setTimeout(function(){
							$(".blinkmsg").removeClass("poperror").addClass("popsuccess");
							self.effectFadeOut('popsuccess', (response.message));		
						},2000);
					} else {
						$(".blinkmsg").removeClass("popsuccess").addClass("poperror");						
						self.effectFadeOut('poperror', (response.message));
					}	
				
					self.getEditProject(self.getRequestHeader(self.projectRequestBody, "", "projectlist"), function(response) {
						self.pageRefresh(response);
					});
				});
			}
		}
	});

	return Clazz.com.components.projects.js.listener.projectsListener;
});