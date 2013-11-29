define(["repository/sourceRepository"], function(SourceRepository) {
	return { 
		runTests : function (configData) {
			module("sourceRepository.js");
			var self = this;
			
			asyncTest("Source Repository Test Template Render Test", function() {
			
				require(["navigation/navigation"], function() {
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});
				
				commonVariables.projectCode = "test-query";
				
				$("#editprojecttitle").attr('projectname', 'TestJquery');
				commonVariables.api.localVal.setProjectInfo(null);
				commonVariables.navListener.onMytabEvent("sourceRepo");
				setTimeout(function() {
					start();
					var len = $('input[name=rep_release]').length;
					equal(len, 1, "Source Repository test template rendering tested");
				}, 3000);
			});
		}		
	};
});