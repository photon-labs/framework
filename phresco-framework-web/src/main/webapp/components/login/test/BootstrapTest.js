
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
    
	testResult : "testResult",
    projectlist : "projectlist",
    projectlistContext : "project",
	addproject : "addproject",
	
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
			commonVariables.animation = data.navigation.animation;
			commonVariables.webserviceurl = "framework/rest/api";
			configJson = {
				// comment out the below line for production, this one is so require doesn't cache the result
				urlArgs: "time=" +  (new Date()).getTime(),
				baseUrl: "src/",
				
				paths : {
					framework : "js/framework",
					listener : "js/commonComponents/listener",
					fastclick : "lib/fastclick",
					api : "js/api",
					common : "js/commonComponents/common",
					modules: "js/commonComponents/modules",
					Clazz : "js/framework/class",
					components: "components",
					configData: data,
					jslib_bootstrap_datepicker: "jslib_bootstrap_datepicker-1.0.0",
					handlebars: "handlebars-1.0.0",
					signal: "signal-1.0.1",
					bootstrap_select_min: "bootstrap_select_min-1.0",
					signalbinding: "signalbinding-1.0.1",
					i18next: "i18next-1.6.0",
					jslib_jquery_sortable_min: "jslib_jquery_sortable_min-1.0.0",
					bootstrap_min: "bootstrap_min-2.3.1",
					jquery_mCustomScrollbar_concat_min: "jquery_mCustomScrollbar_concat_min-2.8.1",
					jquery_mockjax: "jquery_mockjax-1.0",
					json2: "json2-1.0"
				}
			};
            
            commonVariables.basePlaceholder=$("<div id='base'></div>");
            commonVariables.headerPlaceholder=$("<div id='header'></div>");
			commonVariables.navigationPlaceholder=$("<navigation\\:widget></navigation\\:widget>");
            commonVariables.contentPlaceholder=$("<div id='content'></div>");
            commonVariables.footerPlaceholder=$("<div id='footer'></div>");
			
			$.each(commonVariables.globalconfig.components, function(index, value){
				configJson.paths[index] = value.path;
			});
			// setup require.js
			var requireConfig = requirejs.config(configJson);
			
			require(["framework/class", "framework/widget", "common/loading",  "i18next", "framework/widgetWithTemplate", "framework/navigationController", "jquery_mockjax", "json2", "login/login"], function () {
				Clazz.config = data;
				Clazz.navigationController = new Clazz.NavigationController({
					mainContainer : "basepage\\:widget",
					transitionType : Clazz.config.navigation.transitionType,
					cancelTransitionType : Clazz.config.navigation.cancelTransitionType,
					isNative : Clazz.config.navigation.isNative
				});

				//Apply customer based theme
				if(localStorage.getItem('customertheme') !== null && localStorage.getItem('customertheme') !== ""){
					JSS.css(eval('(' + localStorage.getItem('customertheme') + ')'));
				}
				
				commonVariables.loadingScreen =new Clazz.com.js.widget.common.Loading();
			});
		
			require(["loginTest", "projectlistTest", "headerTest", "footerTest", "navigationTest", "projectTest", "applicationTest", "featuresTest", "codequalityTest", "configurationTest", "buildTest", "editConfigurationTest", "jobTemplateListTest","unitTestTest", "componentTestTest", "signal", "signalbinding",  "jslib_bootstrap_datepicker", "handlebars", "jslib_jquery_sortable_min", "bootstrap_min", "jquery_mCustomScrollbar_concat_min", "bootstrap_select_min"], function(loginTest, projectlistTest, headerTest, footerTest, navigationTest, projectTest,applicationTest, featuresTest, codequalityTest, configurationTest, buildTest, editConfigurationTest, jobTemplateListTest, unitTestTest, componentTestTest, signals, signalbinding, datepicker, handlebars,  sortable, bootstrap, scrollbar, select){
				commonVariables.navListener = Clazz.com.components.navigation.js.listener.navigationListener();
				loginTest.runTests(data, function() {
					projectTest.runTests(data);
					/* editConfigurationTest.runTests(data);
					configurationTest.runTests(data);
					navigationTest.runTests(data);
					headerTest.runTests(data);
					projectTest.runTests(data);	
					footerTest.runTests(data);
					projectlistTest.runTests(data);
					applicationTest.runTests(data);
					featuresTest.runTests(data);
					jobTemplateListTest.runTests(data);
					buildTest.runTests(data);
					codequalityTest.runTests(data);
					unitTestTest.runTests(data);
					componentTestTest.runTests(data);
					dynamicPageTest.runTests(data);
					performanceTestTest.runTests(data); */
				});
			});
		}, "json");
	});
});