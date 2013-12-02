define(["repository/buildRepository"], function(BuildRepository) {
	return { 
		runTests : function (configData) {
			module("buildRepository.js");
			var self = this;
			
			asyncTest("Build Repository Test Template Render Test", function() {
			
				require(["navigation/navigation"], function() {
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});
				
				commonVariables.projectCode = "test-query";
				
				$("#editprojecttitle").attr('projectname', 'TestJquery');
				commonVariables.api.localVal.setProjectInfo(null);
				commonVariables.navListener.onMytabEvent("buildRepo");
				setTimeout(function() {
					start();
					var len = $('input[name=download]').length;
					equal(len, 1, "Build Repository test template rendering tested");
				}, 3000);
			});
		}		
	};
});