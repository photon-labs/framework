define(["framework/widget", "framework/widgetWithTemplate", "common/loading", "projects/api/projectsAPI"], function() {

	Clazz.createPackage("com.components.projects.js.listener");

	Clazz.com.components.projects.js.listener.projectsListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder : commonVariables.basePlaceholder,
		loadingScreen : null,
		projectsAPI : null,
		applicationlayerData : null,
		weblayerData : null,
		mobilelayerData : null,
		projectInfo : {},
		customerIds : [],
		appInfos : [],
		appInfosweb : [],
		appInfosmobile : [],
		projectRequestBody : {},
		contentContainer : commonVariables.contentPlaceholder,
		projectlistContent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			self.loadingScreen = new Clazz.com.js.widget.common.Loading();
			self.projectAPI = new Clazz.com.components.projects.js.api.ProjectsAPI();
		},
		
		cancelCreateproject : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.projectlistContent = commonVariables.navListener.getMyObj(commonVariables.projectlist);
			Clazz.navigationController.push(self.projectlistContent, true);
		},
		
		removelayer : function(object) {
			var layerId = object.attr('id');
			object.closest('tr').next().attr('name', layerId + "content");
			object.closest('tr').next().attr('key', 'hidden');
			object.closest('tr').next().hide('slow');
			object.closest('tr').attr('name', layerId);
			object.closest('tr').hide('slow');
			$("input[name="+layerId+"]").attr('disabled', false);
		},
		
		addlayer : function(object) {
			var layerType = object.attr('name');
			$("input[name="+layerType+"]").attr('disabled', true);
			$("tr[name="+ layerType +"]").show('slow');
			$("tr[name="+ layerType+"content]").show('slow');
			$("tr[name="+ layerType+"content]").attr('key', 'displayed');
		},
		
		getEditProject : function(header, callback) {
			var self = this;
			try {
				this.loadingScreen.showLoading();
				self.projectAPI.projects(header,
					function(response) {
						if (response !== null) {
							callback(response);
							self.loadingScreen.removeLoading();
						} else {
							self.loadingScreen.removeLoading();
							callback({ "status" : "service failure"});
						}

					},

					function(textStatus) {
						self.loadingScreen.removeLoading();
					}
				);
			} catch(exception) {
				self.loadingScreen.removeLoading();
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
					requestPostBody : '',
					webserviceurl: commonVariables.webserviceurl + "project/edit?userId="+userId+"&customerId=photon&projectId="+id
				}

				if(projectRequestBody != "") {
					header.requestPostBody = JSON.stringify(projectRequestBody);
				}
				
				if(action == "projectlist"){
					header.requestMethod = "GET";
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.projectlistContext +"/list?customerId=photon"
				}

				if(action == "update"){
					header.requestMethod = "PUT";
					header.webserviceurl = commonVariables.webserviceurl + "project/updateproject?userId="+userId;
				}
				
				if(action == "create"){
					header.requestMethod = "POST";
					header.webserviceurl = commonVariables.webserviceurl + "project/create?userId="+userId;
				}
				
				if(action === "apptypes"){
					header.requestMethod = "GET";
					header.webserviceurl = commonVariables.webserviceurl + "technology/apptypes?userId="+userId+"&customerId="+self.getCustomer();
				}

				return header;
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
			var self=this, minusIcon = '<img src="../themes/default/images/helios/minus_icon.png" border="0" alt="">';
			
			var self = this, dynamicValue, applicationlayer = '<tr class="applnLayer" name="staticApplnLayer" key="displayed"><td data-i18n="project.create.label.appcode" ></td><td class="applnappcode"><input type="text" class="appln-appcode"></td><td data-i18n="project.create.label.technology"></td><td name="technology" class="technology"><select name="appln_technology" class="appln_technology"><option>Select Technology</option>'+ self.getTechnology() +'</select></td><td data-i18n="project.create.label.applicationversion"></td><td colspan="3" name="version" class="version"><select name="appln_version" class="appln_version"><option>Select Version</option></select><div class="flt_right icon_center"><a href="javascript:;" name="addApplnLayer"><img src="../themes/default/images/helios/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeApplnLayer"><img src="../themes/default/images/helios/minus_icon.png" border="0" alt=""></a></div></td></tr>',

			weblayer ='<tr class="webLayer" name="staticWebLayer" key="displayed"><td data-i18n="project.create.label.appcode"></td><td class="webappcode"><input type="text" class="web-appcode"></td><td data-i18n="project.create.label.weblayer"></td><td name="web"><select name="weblayer"><option>Select Web Layer</option>'+self.getWidget() +'</select></td><td data-i18n="project.create.label.widget"></td><td name="widget" class="widget"><select name="web_widget" class="web_widget"> <option>Select Widget</option></select></td><td data-i18n="project.create.label.widgetversion"></td><td name="widgetversion" class="widgetversion"> <select name="web_version" class="web_version"><option>Select Version</option></select><div class="flt_right icon_center"><a href="javascript:;" name="addWebLayer"><img src="../themes/default/images/helios/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeWebLayer"><img src="../themes/default/images/helios/minus_icon.png" border="0" alt=""></a></div></td></tr>',

			mobilelayer = '<tr class="mobileLayer" name="staticMobileLayer"><td data-i18n="project.create.label.appcode"></td><td class="mobileappcode"><input type="text" class="mobile-appcode"></td><td data-i18n="project.create.label.mobile"></td><td name="mobile" class="mobile"><select name="mobile_layer" class="mobile_layer"><option>Select Model</option>'+self.getMobile() +'</select></td><td data-i18n="project.create.label.types"></td><td name="types" class="types"><select name="mobile_types" class="mobile_types"><option>Select Types</option></select></td><td data-i18n="project.create.label.mobileversion"></td><td name="mobileversion" class="mobileversion"><select name="mobile_version" class="mobile_version"><option>Select Version</option></select></td><td style="padding-right:0;"> <input type="checkbox"> <font data-i18n="project.create.label.mobile"></font> &nbsp;&nbsp;&nbsp;<input type="checkbox"> <font data-i18n="project.create.label.tablet"></font>&nbsp;&nbsp;&nbsp;<div class="flt_right icon_center"><a href="javascript:;" name="addMobileLayer"><img src="../themes/default/images/helios/plus_icon.png" border="0" alt=""></a> <a href="javascript:;" name="removeMobileLayer"><img src="../themes/default/images/helios/minus_icon.png" border="0" alt=""></a></div></td></tr>';
			
			if (layerType === "addApplnLayer") {
				dynamicValue = $(applicationlayer).insertAfter(whereToAppend);
				if (dynamicValue.prev('tr').attr("name") !== "dynamicAppLayer") {
					dynamicValue.prev('tr').find('a[name="addApplnLayer"]').html('');
					dynamicValue.prev('tr').find('a[name="removeApplnLayer"]').html(minusIcon);
				} else {
					dynamicValue.prev('tr').find('a[name="addApplnLayer"]').html('');
				}

			} else if (layerType === "addWebLayer") {
				dynamicValue = $(weblayer).insertAfter(whereToAppend);
				if (dynamicValue.prev('tr').attr("name") !== "dynamicWebLayer") {
					dynamicValue.prev('tr').find('a[name="addWebLayer"]').html('');
					dynamicValue.prev('tr').find('a[name="removeWebLayer"]').html(minusIcon);
				} else {
					dynamicValue.prev('tr').find('a[name="addWebLayer"]').html('');
				}
			} else {
				dynamicValue = $(mobilelayer).insertAfter(whereToAppend);
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
			$("a[name=addApplnLayer]").click(function(){
				whereToAppend = $(this).parents('tr.applnLayer:last');
				self.dynamicRenderLocales(commonVariables.contentPlaceholder);
				self.addLayers($(this).attr('name'), whereToAppend);
			});
			
			$("a[name=addWebLayer]").click(function(){
				whereToAppend = $("a[name=addWebLayer]").parents('tr.webLayer:last');
				self.dynamicRenderLocales(commonVariables.contentPlaceholder);
				self.addLayers($(this).attr('name'), whereToAppend);
			});
			
			$("a[name=addMobileLayer]").click(function(){
				whereToAppend = $("a[name=addMobileLayer]").parents('tr.mobileLayer:last');
				self.dynamicRenderLocales(commonVariables.contentPlaceholder);
				self.addLayers($(this).attr('name'), whereToAppend);
			});
		},
		
		removeLayersEvent : function() {
			var self=this, addIcon = '<img src="../themes/default/images/helios/plus_icon.png" border="0" alt="">';
			$("a[name=removeApplnLayer]").click(function(){
				$("a[name=addApplnLayer]").html('');
				$(this).parent().parent().parent().remove();
				$("a[name=removeApplnLayer]").parents('tr:last').find('a[name="addApplnLayer"]').html(addIcon);
				if (($("a[name=removeApplnLayer]").parents('tr[name=staticApplnLayer]').length) === 1) {
					$('tr[name=staticApplnLayer]').find('a[name="addApplnLayer"]').html(addIcon);
					$("a[name=removeApplnLayer]").html('');
				}
			});

			$("a[name=removeWebLayer]").click(function(){
				$("a[name=addWebLayer]").html('');
				$(this).parent().parent().parent().remove();
				$("a[name=removeWebLayer]").parents('tr:last').find('a[name="addWebLayer"]').html(addIcon);
				if (($("a[name=removeWebLayer]").parents('tr[name=staticWebLayer]').length) === 1) {
					$('tr[name=staticWebLayer]').find('a[name="addWebLayer"]').html(addIcon);
					$("a[name=removeWebLayer]").html('');
				}
			});

			$("a[name=removeMobileLayer]").click(function(){
				$("a[name=addMobileLayer]").html('');
				$(this).parent().parent().parent().remove();
				$("a[name=removeMobileLayer]").parent().parent().parent('tr:last').find('a[name="addMobileLayer"]').html(addIcon);
				if (($("a[name=removeMobileLayer]").parents('tr[name=staticMobileLayer]').length) === 1) {
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
					if($(value).text() == "Select Technology"){
						$(this).attr('disabled','disabled')
					}	
				});
			});
			
			/***
			 ** Disabling select Web Layer option
			 */
			$("select[name='weblayer']").bind('click', function(){
				$("select[name='weblayer'] option").each(function(index, value) {
					if($(value).text() == "Select Web Layer"){
						$(this).attr('disabled','disabled')
					}	
				});
			});
			
			/***
			 ** Disabling select model option
			 */
			$("select[name='mobile_layer']").bind('click', function(){
				$("select[name='mobile_layer'] option").each(function(index, value) {
					if($(value).text() == "Select Model"){
						$(this).attr('disabled','disabled')
					}	
				});
			});
			
			/***
			 ** Application Layer - Technology Change Event For Technology version
			 */
			$("select[name='appln_technology']").bind('change', function(){
				var techId = $(this).val();
				self.gettechnologyversion($(this), techId);
			});
			
			/***
			 ** Web Layer - Web Layer Change Event For Widget Type
			 */
			$("select[name='weblayer']").bind('change', function(){
				var type = $(this).val();
				self.getwidgettype($(this), type);
			});
			
			/***
			 ** Web Layer - Type Change Event For Widget Version
			 */
			$("select[name='web_widget']").bind('change', function(){
				var widgetType = $(this).val();
				self.getwidgetversion($(this), widgetType);
			});

			/***
			 ** Mobile Layer - Mobile Change Event For Mobile Type
			 */
			$("select[name='mobile_layer']").bind('change', function(){
				var mobile = $(this).val();
				self.getmobiletype($(this), mobile);
			});
			
			/***
			 **	Mobile Layer - Type Change Event For Mobile Version
			 */
			$("select[name='mobile_types']").unbind('change');
			$("select[name='mobile_types']").bind('change', function(){
				var mobileType = $(this).val();
				self.getmobileversion($(this), mobileType);
			});
		},
		
		dynamicRenderLocales : function(contentPlaceholder) {
			var self=this;
			self.renderlocales(contentPlaceholder);
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
		
		gettechnologyversion : function(object, technologyId) {
			var self=this, option, version, versionplaceholder;
			version = object.parents("td[name='technology']");
			versionplaceholder = $(version).siblings("td[name='version']").children("select[name='appln_version']");
			
			self.applicationlayerData = self.projectAPI.localVal.getJson("Application Layer");
			$.each(self.applicationlayerData.techGroups, function(index, value){
				$.each(value.techInfos, function(index, value){
				    if(value.id === technologyId){
						option = '';
						$.each(value.techVersions, function(index, value){
							option += '<option>'+ value +'</option>'
						});
						
						$(versionplaceholder).html(option);
					}
				});
			});
		},
		
		getwidgettype : function(object, type) {
			var self=this, option, widget, widgetTypePlaceholder;
			widget = object.parents("td[name='web']");
			widgetTypePlaceholder = $(widget).siblings("td[name='widget']").children("select[name='web_widget']");
			
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
		
		getwidgetversion : function(object, widgettype) {
			var self=this, option, widget, widgetTypePlaceholder;
			widget = object.parents("td[name='widget']");
			widgetTypePlaceholder = $(widget).siblings("td[name='widgetversion']").children("select[name='web_version']");
			
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
		
		getmobiletype : function(object, mobile) {
			var self=this, option, mobilediv, mobileTypePlaceholder;
			mobilediv = object.parents("td[name='mobile']");
			mobileTypePlaceholder = $(mobilediv).siblings("td[name='types']").children("select[name='mobile_types']");
			
			self.mobilelayerData = self.projectAPI.localVal.getJson("Mobile Layer");
			$.each(self.mobilelayerData.techGroups, function(index, value){
				if(value.id === mobile){
					option = '';
					option += '<option disabled selected>Select Type</option>';
					$.each(value.techInfos, function(index, value){
						option += '<option value='+ value.id +'>'+ value.name +'</option>';
					})
					
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
				})
			});
			
			return option;
		},
		
		getmobileversion : function(object, mobileType) {
			var self=this, option, mobilediv, mobileTypePlaceholder;
			mobilediv = object.parents("td[name='types']");
			mobileTypePlaceholder = $(mobilediv).siblings("td[name='mobileversion']").children("select[name='mobile_version']");
			
			self.mobilelayerData = self.projectAPI.localVal.getJson("Mobile Layer");
			$.each(self.mobilelayerData.techGroups, function(index, value){
				$.each(value.techInfos, function(index, value){
					if(value.id === mobileType){
						option = '';
						if(value.techVersions !== undefined && value.techVersions !== null) {
							if(value !== '' && value != null) {
								$.each(value.techVersions, function(index, value){
									if(value !== '' && value != null) {
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
		
		editSeriveTechnolyEvent : function(getData) {
			var self = this, addIcon = '<img src="../themes/default/images/helios/plus_icon.png" border="0" alt="">';;
			$("#appLayaer").hide();
			$("#webLayaer").hide();
			$("#webLayaer").next('tr').hide();
			$("#mobLayaer").hide();
			$("#mobLayaer").next('tr').hide();

			$.each(getData, function(index, value) {
				if (value.techInfo.appTypeId == "app-layer") {
					$("#appLayaer").show();
					var splitValue = value.appDirName;
					var finalValue = splitValue.split("-");
					var appendData = '<tr class="applnLayer" name="dynamicAppLayer"><td data-i18n="project.create.label.appcode"></td><td><input type="text" value="'+finalValue[0]+'" disabled></td><td data-i18n="project.create.label.technology"></td><td name="technology"><select name="appln_technology" disabled>'+ self.getTechnology(value.techInfo.id) +'</select></td><td data-i18n="project.create.label.applicationversion"></td><td colspan="3" name="version"><select name="appln_version" disabled><option>'+value.techInfo.version+'</option></select><div class="flt_right icon_center"><a href="javascript:;" name="addApplnLayer"></a> <a href="javascript:;" name="removeApplnLayer"></a></div></td></tr>';
					$(appendData).insertAfter("#appLayaer:last");
					$("tr[name=dynamicAppLayer]:last").find("a[name=addApplnLayer]").html(addIcon);
				} else if (value.techInfo.appTypeId == "web-layer") {
					$("#webLayaer").show();
					var splitValue = value.appDirName;
					var finalValue = splitValue.split("-");
					var appendData = '<tr class="webLayer" name="dynamicWebLayer"><td data-i18n="project.create.label.appcode"></td><td><input type="text" value="'+finalValue[0]+'" disabled></td><td data-i18n="project.create.label.weblayer"></td><td name="web"><select name="weblayer" disabled><option>'+value.techInfo.techGroupId+'</option></select></td><td data-i18n="project.create.label.widget"></td><td name="widget"><select name="web_widget" disabled> '+ self.editgetwidgettype(value.techInfo.id) +'</select></td><td data-i18n="project.create.label.widgetversion"></td><td name="widgetversion"> <select name="web_version" disabled><option>'+value.techInfo.version+'</option></select><div class="flt_right icon_center"><a href="javascript:;" name="addWebLayer"><img border="0" alt=""></a> <a href="javascript:;" name="removeWebLayer"></a></div></td></tr>';
					$(appendData).insertAfter("#webLayaer:last");
					$("tr[name=dynamicWebLayer]:last").find("a[name=addWebLayer]").html(addIcon);
				} else if (value.techInfo.appTypeId == "mobile-layer") {
					$("#mobLayaer").show();
					$("#mobLayaer").next('tr').show();
					var splitValue = value.appDirName;
					var finalValue = splitValue.split("-");
					var appendData = '<tr class="mobileLayer" name="dynamicMobileLayer"><td data-i18n="project.create.label.appcode"></td><td class="mobileappcode"><input type="text" value="'+finalValue[0]+'" disabled></td> <td data-i18n="project.create.label.mobile"> </td><td><select disabled><option>'+value.techInfo.techGroupId+'</option></select></td><td data-i18n="project.create.label.types"></td><td><select class="mobile_types" disabled><option>'+self.editgetmobiletype(value.techInfo.id)+'</option></select></td><td data-i18n="project.create.label.mobileversion"></td><td><select disabled><option>'+value.techInfo.version+'</option></select></td><td style="padding-right:0;"> <input type="checkbox"> <font data-i18n="project.create.label.mobile"></font> &nbsp;&nbsp;&nbsp;<input type="checkbox"> <font data-i18n="project.create.label.tablet"></font>&nbsp;&nbsp;&nbsp;<div class="flt_right icon_center"><a href="javascript:;" name="addMobileLayer"></a> <a href="javascript:;" name="removeMobileLayer"></a></div></td></tr>';
					$("#mobileTable").append(appendData);
				}
			});
			
			$("#mobileTable tr:last").find("a[name=addMobileLayer]").html(addIcon);
			self.addLayersEvent();
		},

		
		 /**
         * Called during the page refresh, displays the message, and total number of records
         * @response: response from the service
         */
        pageRefresh : function(response) {
			var projectlist = commonVariables.navListener.getMyObj(commonVariables.projectlist);
			projectlist.loadPage();
		},
		
		createproject : function(projectId, action) {
		
			var self=this;
			var projectname = $("input[name='projectname']").val();
			var projectcode = $("input[name='projectcode']").val();
			var projectversion = $("input[name='projectversion']").val();
			var projectdescription = $("input[name='projectdescription']").val();
			var startdate = $("input[name='startdate']").val();
			var enddate = $("input[name='enddate']").val();
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
			self.projectInfo.customerIds = self.customerIds;
						
			$.each( $("tbody[name='layercontents']").children(), function(index, value){
			
				var techInfo = {};
				var tech;
				var techName = "";
				var code = "";
				var appInfo = {};
				var mobdata = {};
				var versionText = "";
				
				if($(value).attr('class') == "applnLayer" && $(value).attr('key') == "displayed") {
					tech = $(value).children("td.technology").children("select.appln_technology");
					techName = $(tech).find(":selected").text();
					code = $(value).children("td.applnappcode").children("input.appln-appcode").val();
					appInfo.code = code + "-" + techName;
					appInfo.appDirName = code + "-" + techName;
					appInfo.version = projectversion;
					appInfo.name = projectname + "-" + techName; 
					techInfo.id = $(value).children("td.technology").children("select.appln_technology").val();
					techInfo.appTypeId = "app-layer"
					techInfo.version = $(value).children("td.version").children("select.appln_version").val();
					if (appInfo.code !== undefined+"-") {
						appInfo.techInfo = techInfo;
						self.appInfos.push(appInfo);
					}
					count++;
				} 
				
				if($(value).attr('class') == "webLayer" && $(value).attr('key') == "displayed") {
					tech = $(value).children("td.widget").children("select.web_widget");
					techName = $(tech).find(":selected").text();
					code = $(value).children("td.webappcode").children("input.web-appcode").val();
					appInfo.code = code + "-" + techName;
					appInfo.appDirName = code + "-" + techName;
					appInfo.version = projectversion;
					appInfo.name = projectname + "-" + techName; 
					techInfo.id = $(value).children("td.widget").children("select.web_widget").val();
					techInfo.appTypeId = "web-layer";
					techInfo.techGroupId = $(value).children("td.web").children("select.weblayer").find(":selected").text();
					techInfo.version = $(value).children("td.widgetversion").children("select.web_version").find(":selected").text();
					if (appInfo.code !== undefined+"-") {
						appInfo.techInfo = techInfo;
						self.appInfos.push(appInfo);
					}
					count++;
				}  

				if($(value).attr('class') == "mobLayer" && $(value).attr('key') == "displayed") {
					var mobilelayerDiv = $(value).children('td.mob').children('table.mob-table').children('tbody');
					$.each($(mobilelayerDiv).children(), function(index, value){
						var appInfo = {};
						var techInfo = {};
						versionText = $(value).children("td.mobileversion").children("select.mobile_version").find(":selected").text();
						tech = $(value).children("td.types").children("select.mobile_types");
						techName = $(tech).find(":selected").text();
						code = $(value).children("td.mobileappcode").children("input.mobile-appcode").val();
						appInfo.code = code + "-" + techName;
						appInfo.appDirName = code + "-" + techName; 
						appInfo.version = projectversion;
						appInfo.name = projectname + "-" + techName; 
						techInfo.id = $(value).children("td.types").children("select.mobile_types").find(':selected').val();
						techInfo.appTypeId = "mobile-layer";
						techInfo.techGroupId = $(value).children("td.mobile").children("select.mobile_layer").find(':selected').text();
						if(versionText == "No Versions available") {
							techInfo.version = "";
						} else {
							techInfo.version = $(value).children("td.mobileversion").children("select.mobile_version").find(":selected").text();
						}
						if (appInfo.code !== undefined+"-") {
							appInfo.techInfo = techInfo;
							self.appInfos.push(appInfo);
						}
						count++;
					});
				}   	
				
			});
			
			var appInfos = $.merge($.merge($.merge([],self.appInfos), self.appInfosweb), self.appInfosmobile);
			self.projectInfo.noOfApps = count;
			self.projectInfo.appInfos = appInfos;
			self.projectRequestBody = self.projectInfo;
			
			if(projectId != '') {
				self.projectInfo.id = projectId;
			}
			
			self.getEditProject(self.getRequestHeader(self.projectRequestBody, "", action), function(response) {
				self.projectRequestBody = {};
				self.getEditProject(self.getRequestHeader(self.projectRequestBody, "", "projectlist"), function(response) {
					self.pageRefresh(response);
				});
			});
		}
	});

	return Clazz.com.components.projects.js.listener.projectsListener;
});