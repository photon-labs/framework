define(["framework/widget"], function() {

	Clazz.createPackage("com.components.unittest.js.listener");

	Clazz.com.components.unittest.js.listener.UnitTestlistener = Clazz.extend(Clazz.Widget, {
				
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
		},
		
		onUnitTestResult : function() {
			$("#uniTestResultTab").show();
			$("#unitTestTab").hide();
			$(".unit_view").css("display", "block");
		},
		
		onUnitTestDesc : function() {
			$("#uniTestResultTab").hide();
			$("#uniTestDesc").show();
		},
		
		onUnitTestGraph : function() {
			$("#uniTestResultTab").hide();
			$("#uniTestDesc").hide();
			$("#graphView").show();
			$("a[name=unitTestGraph]").html('')
			$("a[name=unitTestGraph]").html('<img src="themes/default/images/helios/quality_graph_on.png" width="25" height="25" border="0" alt=""><b>Graph View</b>');
		}
		
	});

	return Clazz.com.components.unittest.js.listener.UnitTestlistener;
});