define(["framework/widget", "framework/widgetWithTemplate", "projects/api/projectsAPI"], function() {

	Clazz.createPackage("com.components.projects.js.listener");

	Clazz.com.components.projects.js.listener.projectsListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
		},
		
		removelayer : function(object) {
			var layerId = object.attr('id');
			object.closest('tr').next().attr('name', layerId + "content");
			object.closest('tr').next().hide();
			object.closest('tr').attr('name', layerId);
			object.closest('tr').hide();
			$("input[name="+layerId+"]").attr('disabled', false);
		},
		
		addlayer : function(object) {
			var layerType = object.attr('name');
			$("input[name="+layerType+"]").attr('disabled', true);
			$("tr[name="+ layerType +"]").show();
			$("tr[name="+ layerType+"content]").show();
		},
		
		addLayers :function(layerType, whereToAppend) {
			var self = this, dynamicValue, applicatiolayer = '<tr class="applnLayer"><td data-i18n="project.create.label.appcode"></td><td><input type="text"></td><td data-i18n="project.create.label.technology"></td><td><select><option>Select Technology</option><option>Select Technology</option><option>Select Technology</option></select></td><td data-i18n="project.create.label.applicationversion"></td><td><select><option>Select Version</option><option>Select Version</option><option>Select Version</option></select><div class="flt_right"><a href="javascript:;"><img name="addApplnLayer" src="../themes/default/images/helios/plus_icon.png" width="25" height="20" border="0" alt=""></a> <a href="javascript:;"><img name="removeApplnLayer" src="../themes/default/images/helios/minus_icon.png"  width="25" height="20" border="0" alt=""></a></div></td></tr>',
			
			weblayer ='<tr class="webLayer"><td data-i18n="project.create.label.weblayer"></td><td><select><option>Select Web Layer</option><option>Select Web Layer</option><option>Select Web Layer</option></select></td><td data-i18n="project.create.label.widget"></td><td><select> <option>Select Widget</option><option>Select Widget</option><option>Select Widget</option></select></td><td data-i18n="project.create.label.widgetversion"></td><td> <select><option>Select Version</option><option>Select Version</option><option>Select Version</option></select><div class="flt_right"><a href="javascript:;"><img name="addWebLayer" src="../themes/default/images/helios/plus_icon.png" width="25" height="20" border="0" alt=""></a> <a href="javascript:;"><img name="removeWebLayer" src="../themes/default/images/helios/minus_icon.png"  width="25" height="20" border="0" alt=""></a></div></td></tr>',
			
			mobilelayer = '<tr class="mobileLayer"><td data-i18n="project.create.label.mobile"></td><td><select><option>Select Model</option></select></td><td data-i18n="project.create.label.types"></td><td><select><option>Select Widget</option></select></td><td data-i18n="project.create.label.mobileversion"></td><td><select><option>Select Version</option></select></td><td> <input type="checkbox">&nbsp;<font data-i18n="project.create.label.mobile"></font>&nbsp;&nbsp;<input type="checkbox">&nbsp;<font data-i18n="project.create.label.tablet"></font>&nbsp;&nbsp;<div class="flt_right"><a href="javascript:;"><img name="addMobileLayer" src="../themes/default/images/helios/plus_icon.png"  width="25" height="20" border="0" alt=""></a> <a href="javascript:;"><img name="removeMobileLayer" src="../themes/default/images/helios/minus_icon.png"  width="25" height="20" border="0" alt=""></a></div></td></tr>';
			if (layerType === "addApplnLayer") {
				dynamicValue = $(applicatiolayer).insertAfter(whereToAppend);
			} else if (layerType === "addWebLayer") {
					dynamicValue = $(weblayer).insertAfter(whereToAppend);
				} else {
					dynamicValue = $(mobilelayer).insertAfter(whereToAppend);
				}
			$("img[name=addApplnLayer]").unbind("click");
			$("img[name=addWebLayer]").unbind("click");
			$("img[name=addMobileLayer]").unbind("click");
			self.addLayersEvent();
			$("img[name=removeApplnLayer]").unbind("click");
			$("img[name=removeWebLayer]").unbind("click");
			$("img[name=removeMobileLayer]").unbind("click");
			self.removeLayersEvent();
		},
		
		addLayersEvent : function() {
			var self=this, whereToAppend = '';
			$("img[name=addApplnLayer]").click(function(){
				whereToAppend = $("img[name=addApplnLayer]").parents('tr.applnLayer:last');
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
				$(this).parent().parent().parent().parent().remove();;
			});
			
			$("img[name=removeWebLayer]").click(function(){
				$(this).parent().parent().parent().parent().remove();;
			});
			
			$("img[name=removeMobileLayer]").click(function(){
				$(this).parent().parent().parent().parent().remove();;
			});
		},
		
		dynamicRenderLocales : function(contentPlaceholder) {
			var self=this;
			self.renderlocales(contentPlaceholder);
		}
		
	});

	return Clazz.com.components.projects.js.listener.projectsListener;
});