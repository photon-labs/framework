define(["framework/widget", "framework/widgetWithTemplate", "projects/api/projectsAPI"], function() {

	Clazz.createPackage("com.components.projects.js.listener");

	Clazz.com.components.projects.js.listener.projectsListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		projectsAPI : null,
		applicationlayerData : null,
		weblayerData : null,
		mobilelayerData : null,
		webLayerValue : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			self.projectAPI = new Clazz.com.components.projects.js.api.ProjectsAPI();
		},
	
		removelayer : function(object) {
			var layerId = object.attr('id');
			object.closest('tr').next().attr('name', layerId + "content");
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
		},
		
		getEditProject : function(header, callback) {
			var self = this;
			try {
				self.projectAPI.projects(header,
					function(response) {
						if (response !== null) {
							callback(response);
						} else {
							callback({ "status" : "service failure"});
						}

					},

					function(textStatus) {
						
					}
				);
			} catch(exception) {
				
			}

		},
		
		getRequestHeader : function(projectRequestBody, id) {
			var self=this, header, data = {}, userId;
			
			data = JSON.parse(self.projectAPI.localVal.getSession('userInfo'));
			userId = data.id;
			
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: commonVariables.webserviceurl+"project/edit?userId="+userId+"&customerId=photon&projectId="+id
			}
			
			return header;
		},
		
		addLayers :function(layerType, whereToAppend) {
			var self=this;
			
			var self = this, dynamicValue, applicationlayer = '<tr class="applnLayer"><td data-i18n="project.create.label.appcode"></td><td><input type="text"></td><td data-i18n="project.create.label.technology"></td><td name="technology"><select name="appln_technology"><option>Select Technology</option>'+ self.getTechnology() +'</select></td><td data-i18n="project.create.label.applicationversion"></td><td colspan="3" name="version"><select name="appln_version"><option>Select Version</option></select><div class="flt_right"><a href="javascript:;"><img name="addApplnLayer" src="../themes/default/images/helios/plus_icon.png" width="25" height="20" border="0" alt=""></a> <a href="javascript:;"><img name="removeApplnLayer" src="../themes/default/images/helios/minus_icon.png"  width="25" height="20" border="0" alt=""></a></div></td></tr>',
			
			weblayer ='<tr class="webLayer"><td data-i18n="project.create.label.appcode"></td><td><input type="text"></td><td data-i18n="project.create.label.weblayer"></td><td name="web"><select name="weblayer"><option>Select Web Layer</option>'+self.getWidget() +'</select></td><td data-i18n="project.create.label.widget"></td><td name="widget"><select name="web_widget"> <option>Select Widget</option></select></td><td data-i18n="project.create.label.widgetversion"></td><td name="widgetversion"> <select name="web_version"><option>Select Version</option></select><div class="flt_right"><a href="javascript:;"><img name="addWebLayer" src="../themes/default/images/helios/plus_icon.png" width="25" height="20" border="0" alt=""></a> <a href="javascript:;"><img name="removeWebLayer" src="../themes/default/images/helios/minus_icon.png"  width="25" height="20" border="0" alt=""></a></div></td></tr>',
			
			mobilelayer = '<tr class="mobileLayer"><td data-i18n="project.create.label.appcode"></td><td><input type="text"></td><td data-i18n="project.create.label.mobile"></td><td name="mobile"><select name="mobile_layer"><option>Select Model</option>'+self.getMobile() +'</select></td><td data-i18n="project.create.label.types"></td><td name="types"><select name="mobile_types"><option>Select Types</option></select></td><td data-i18n="project.create.label.mobileversion"></td><td name="mobileversion"><select name="mobile_version"><option>Select Version</option></select></td><td style="padding-right:0;"><input type="checkbox"> <font data-i18n="project.create.label.mobile"></font> &nbsp;&nbsp;&nbsp;<input type="checkbox"> <font data-i18n="project.create.label.tablet"></font>&nbsp;&nbsp;&nbsp;<div class="flt_right"><a href="javascript:;"><img name="addMobileLayer" src="../themes/default/images/helios/plus_icon.png"  width="25" height="20" border="0" alt=""></a> <a href="javascript:;"><img name="removeMobileLayer" src="../themes/default/images/helios/minus_icon.png" width="25" height="20" border="0" alt=""></a></div></td>';
			
			if (layerType === "addApplnLayer") {
				dynamicValue = $(applicationlayer).insertAfter(whereToAppend);
				dynamicValue.prev('tr').find('img[name="addApplnLayer"]').removeAttr("src");
				dynamicValue.prev('tr').find('img[name="removeApplnLayer"]').attr("src","../themes/default/images/helios/minus_icon.png");
			} else if (layerType === "addWebLayer") {
				dynamicValue = $(weblayer).insertAfter(whereToAppend);
				dynamicValue.prev('tr').find('img[name="addWebLayer"]').removeAttr("src");
				dynamicValue.prev('tr').find('img[name="removeWebLayer"]').attr("src","../themes/default/images/helios/minus_icon.png");
			} else {
				dynamicValue = $(mobilelayer).insertAfter(whereToAppend);
				dynamicValue.prev('tr').find('img[name="addMobileLayer"]').removeAttr("src");
				dynamicValue.prev('tr').find('img[name="removeMobileLayer"]').attr("src","../themes/default/images/helios/minus_icon.png");
			}
			
			$("img[name=addApplnLayer]").unbind("click");
			$("img[name=addWebLayer]").unbind("click");
			$("img[name=addMobileLayer]").unbind("click");
			self.addLayersEvent();
			$("img[name=removeApplnLayer]").unbind("click");
			$("img[name=removeWebLayer]").unbind("click");
			$("img[name=removeMobileLayer]").unbind("click");
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
			$("img[name=addApplnLayer]").click(function(){
				whereToAppend = $(this).parents('tr.applnLayer:last');
				self.dynamicRenderLocales(commonVariables.contentPlaceholder);
				self.addLayers($(this).attr('name'), whereToAppend);
			});
			
			$("img[name=addWebLayer]").click(function(){
				whereToAppend = $("img[name=addWebLayer]").parents('tr.webLayer:last');
				self.dynamicRenderLocales(commonVariables.contentPlaceholder);
				self.addLayers($(this).attr('name'), whereToAppend);
			});
			
			$("img[name=addMobileLayer]").click(function(){
				whereToAppend = $("img[name=addMobileLayer]").parents('tr.mobileLayer:last');
				self.dynamicRenderLocales(commonVariables.contentPlaceholder);
				self.addLayers($(this).attr('name'), whereToAppend);
			});
		},
		
		removeLayersEvent : function() {
			var self=this;
			$("img[name=removeApplnLayer]").click(function(){
				$("img[name=addApplnLayer]").removeAttr('src');
				$(this).parent().parent().parent().parent().remove();
				$("img[name=removeApplnLayer]").parents('tr:last').find('img[name="addApplnLayer"]').attr("src", "../themes/default/images/helios/plus_icon.png");
				if (($("img[name=removeApplnLayer]").parents('tr.applnLayer').length) === 1) {
					$("img[name=addApplnLayer]").attr("src", "../themes/default/images/helios/plus_icon.png");
					$("img[name=removeApplnLayer]").removeAttr('src');
				}
			});
			
			$("img[name=removeWebLayer]").click(function(){
				$("img[name=addWebLayer]").removeAttr('src');
				$(this).parent().parent().parent().parent().remove();
				$("img[name=removeWebLayer]").parents('tr:last').find('img[name="addWebLayer"]').attr("src", "../themes/default/images/helios/plus_icon.png");
				if (($("img[name=removeWebLayer]").parents('tr.webLayer').length) === 1) {
					$("img[name=addWebLayer]").attr("src", "../themes/default/images/helios/plus_icon.png");
					$("img[name=removeWebLayer]").removeAttr('src');
				}
			});
			
			$("img[name=removeMobileLayer]").click(function(){
				$("img[name=addMobileLayer]").removeAttr('src');
				$(this).parent().parent().parent().parent().remove();
				$("img[name=removeMobileLayer]").parent().parent().parent().parent('tr:last').find('img[name="addMobileLayer"]').attr("src", "../themes/default/images/helios/plus_icon.png");
				if (($("img[name=removeMobileLayer]").parents('tr.mobileLayer').length) === 1) {
					$("img[name=addMobileLayer]").attr("src", "../themes/default/images/helios/plus_icon.png");
					$("img[name=removeMobileLayer]").removeAttr('src');
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
		
		getTechnology : function() {
			var self=this, option;
			self.applicationlayerData = self.projectAPI.localVal.getJson("Application Layer");
			option = '';
			$.each(self.applicationlayerData.techGroups, function(index, value){
				$.each(value.techInfos, function(index, value){
				    option += '<option value='+ value.id +'>'+ value.name +'</option>';
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
		
		getMobile : function() {
			var self=this, option;
			self.mobilelayerData = self.projectAPI.localVal.getJson("Mobile Layer");
			option = '';
			$.each(self.mobilelayerData.techGroups, function(index, value){
				if(value !== '' && value !== undefined) {
					option += '<option value='+ value.id +'>'+ value.name +'</option>';
				} else {
					option += '<option>No Versions available</option>';
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
					$.each(value.techInfos, function(index, value){
						option += '<option value='+ value.id +'>'+ value.name +'</option>';
					});
					
					$(widgetTypePlaceholder).html(option);
				}
			});
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
					$.each(value.techInfos, function(index, value){
						option += '<option value='+ value.id +'>'+ value.name +'</option>';
					})
					
					$(mobileTypePlaceholder).html(option);
				}
			});
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
			var self = this;
			$("#appLayaer").hide();
			$("#appLayaer").next('tr').hide();
			$("#webLayaer").hide();
			$("#webLayaer").next('tr').hide();
			$("#mobLayaer").hide();
			$("#mobLayaer").next('tr').hide();
			for (var i=0; i<getData.length; i++) {
				if (getData[i].techInfo.appTypeId == "app-layer") {
					$("#appLayaer").show();
					$("#appLayaer").next('tr').show();
					$("select[name='appln_technology'] option").each(function(){
						if ($(this).val() === getData[i].techInfo.id) {
							$(this).attr("selected", "selected");
							$("select[name='appln_technology']").attr("disabled", true);
						}
					});
					self.gettechnologyversion($("select[name='appln_technology']"), getData[i].techInfo.id);
					$("select[name='appln_version'] option").each(function(){
						if ($(this).val() === getData[i].techInfo.version) {
							$(this).attr("selected", "selected");
							$("select[name='appln_version']").attr("disabled", true);
						}
					});
				} else if (getData[i].techInfo.appTypeId == "web-layer") {
					$("#webLayaer").show();
					$("#webLayaer").next('tr').show();
					$("select[name='weblayer'] option").each(function(){
						$(this).val('html5').attr("selected", "selected");
						$("select[name='weblayer']").attr("disabled", true);
					});
					self.getwidgettype($("select[name='weblayer']"), "html5");
					$("select[name='web_widget'] option").each(function(){
						if ($(this).val() === getData[i].techInfo.id) {
							$(this).attr("selected", "selected");
							$("select[name='web_widget']").attr("disabled", true);
						}
					});
					self.getwidgetversion($("select[name='web_widget']"), getData[i].techInfo.id);
					$("select[name='web_version'] option").each(function(){
						if ($(this).val() === getData[i].techInfo.version) {
							$(this).attr("selected", "selected");
							$("select[name='web_version']").attr("disabled", true);
						}
					});
				} else if (getData[i].techInfo.appTypeId == "mob-layer") {
					$("#mobLayaer").show();
					$("#mobLayaer").next('tr').show();
				}
			}
		}
	});

	return Clazz.com.components.projects.js.listener.projectsListener;
});