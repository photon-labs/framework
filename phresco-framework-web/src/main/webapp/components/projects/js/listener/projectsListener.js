define(["framework/widget", "projects/api/projectsAPI"], function() {

	Clazz.createPackage("com.components.projects.js.listener");

	Clazz.com.components.projects.js.listener.projectsListener = Clazz.extend(Clazz.Widget, {
		
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
		}	
		
	});

	return Clazz.com.components.projects.js.listener.projectsListener;
});