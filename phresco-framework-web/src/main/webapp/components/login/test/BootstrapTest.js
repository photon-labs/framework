
var commonVariables = {
	globalconfig : "",
	webserviceurl : "",
	contexturl : "src/",
	
	navListener : null,
	
	login : "login",
	loginContext : "login",

	header : "header",
	headerContext : "header",
	
	footer : "footer",
	footerContext : "footer",
	
	navigation : "navigation",
	navigationContext : "",
	
	loadingScreen : null,
    
    projectlist : "projectlist",
    projectlistContext : "project",

	basePlaceholder : "basepage\\:widget",
	headerPlaceholder : "<div id='header'></div>",
	contentPlaceholder : "content\\:widget",
	footerPlaceholder : "<div id='footer'></div>",
	navigationPlaceholder : "navigation\\:widget"
};

define(["jquery"], function($) {
	$(document).ready(function(){

		$.get('src/components/login/test/config.json', function(data) {
			commonVariables.globalconfig = data;
			commonVariables.webserviceurl = "framework/";
			configJson = {
				// comment out the below line for production, this one is so require doesn't cache the result
				urlArgs: "time=" +  (new Date()).getTime(),
				baseUrl: "src/",
				
				paths : {
					framework : "js/framework",
					listener : "js/commonComponents/listener",
					fastclick : "lib/fastclick",
					api : "js/api",
					lib : "lib",
					signals : "lib/signals",
					common : "js/commonComponents/common",
					modules: "js/commonComponents/modules",
					Clazz : "js/framework/class",
					components: "components",
					configData: data,
					jshamcrest: "lib/jshamcrest-0.5.2",
					jsmockito: "lib/jsmockito-1.0.3"
				}
			};
            
            commonVariables.headerPlaceholder=$("<div id='header'></div>");
            commonVariables.contentPlaceholder=$("<div id='content'></div>");
            commonVariables.footerPlaceholder=$("<div id='footer'></div>");


			$.each(commonVariables.globalconfig.components, function(index, value){
				configJson.paths[index] = value.path;
			});
			// setup require.js
			var requireConfig = requirejs.config(configJson);
			
			require(["framework/class", "framework/widget", "common/loading", "framework/widgetWithTemplate", "framework/navigationController", "login/login"], function () {
				Clazz.config = data;
				Clazz.navigationController = new Clazz.NavigationController({
					mainContainer : "basepage\\:widget",
					transitionType : Clazz.config.navigation.transitionType,
					cancelTransitionType : Clazz.config.navigation.cancelTransitionType,
					isNative : Clazz.config.navigation.isNative
				});

				//Apply customer based theme
				if(localStorage.getItem('customertheme') != null && localStorage.getItem('customertheme') != ""){
					JSS.css(eval('(' + localStorage.getItem('customertheme') + ')'));
				}
				
				commonVariables.loadingScreen =new Clazz.com.js.widget.common.Loading();
			});
		
			require(["lib/jslib_bootstrap_datepicker-1.0.0", "lib/bootstrap_select_min-1.0", "lib/handlebars-1.0.0", "lib/Signal-1.0.0", "lib/SignalBinding-1.0.0", "lib/i18next-1.6.0", "lib/jslib_jquery_sortable_min-1.0.0", "lib/bootstrap_min-2.3.1","jquery_mCustomScrollbar_concat_min-2.8.1", "loginTest", "projectlistTest", "headerTest", "footerTest", "navigationTest", "projectTest", "applicationTest", "featuresTest", "codequalityTest", "configurationTest", "buildTest", "jshamcrest", "jsmockito"],	function (Datepicker, Select, Handlebars, Signal, SignalBinding, next, sortable, bootrsap, mCustomScrollbar, loginTest, projectlistTest, headerTest, footerTest, navigationTest, projectTest,applicationTest, featuresTest, codequalityTest, configurationTest, buildTest){
				JsHamcrest.Integration.JsTestDriver();
				JsMockito.Integration.JsTestDriver();
				var status = loginTest.runTests(data);/*, function() {
					projectlistTest.runTests(data);*/
				//});
				if (status) {
					projectlistTest.runTests(data);
					projectTest.runTests(data);	
					applicationTest.runTests(data);
					configurationTest.runTests(data);
					featuresTest.runTests(data);
				}
				
				/* navigationTest.runTests(data);
				headerTest.runTests(data);
				applicationTest.runTests(data);		
				footerTest.runTests(data);
				featuresTest.runTests(data); */
				
				/* projectTest.runTests(data);
				codequalityTest.runTests(data);	
				configurationTest.runTests(data);
				buildTest.runTests(data); */
			});
		}, "json");
	});
});