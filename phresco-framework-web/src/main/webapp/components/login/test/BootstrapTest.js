
var commonVariables = {
	globalconfig : "",
	webserviceurl : "",
	contexturl : "src/",
	
	navListener : null,
	
	login : "login",
	loginContext : "login",

	header : "header",
	headerContext : "header",
	upgrade : "upgrade",
	upgradeAvailable : "upgradeAvailable",

	footer : "footer",
	footerContext : "footer",
	
	dashboard : "dashboard",
	
	navigation : "navigation",
	navigationContext : "",
	mavenService : "mavenService",

	
	configuration : "configuration",
	editConfiguration : "editConfiguration",
	
	editApplication : "appinfo",
	codequality : "codequality",
	goal : "validate-code",
	phase : "validate-code",
	appDirName : null,
	mvnCodeValidation : "app/codeValidate",
	croneExpression : "croneExpression",

	techId : "tech-html5-jquery-mobile-widget",
	
	featurePage : "features",
	featurePageContext : "features",

	buildRepo : "buildRepo",
	sourceRepo : "sourceRepo",
	
	buildNo : null,
	build : "build",
	typeBuild : "build",
	mvnBuild : "app/build",
	mvnDeploy : "app/deploy",
	optionsBuild : "Build",
	optionsDeploy : "Deploy",
	
	loadingScreen : null,
	dashboard : "dashboard",
    
	projectlist : "projectlist",
    projectlistContext : "project",
	addproject : "addproject",
	editproject : "editproject",
	performanceTest : "performanceTest",
	qualityContext : "quality",
	performance : "performance",
	load : "load",
	loadTest: "loadTest",
	performanceTest : "performanceTest",
	mvnPerformanceTest : "app/performanceTest",
	mvnLoadTest : "app/loadTest",
	mvnlogService : "app/readlog",
	manual : "manual",
	manualTest : "manualTest",
	performanceTestResults : "performanceTestResults",
	functionalTest : "functionalTest",
	featurelist : "featurelist",
	unitTest : "unitTest",
	unit : "unit",
	integrationTest : "integrationTest",
	integration : "integration",
	testsuiteResult : "testsuiteResult",
	testcaseResult	: "testcaseResult",
	componentTest : "componentTest",
	mvnUnitTest : "app/runUnitTest",
	mvnIntegrationTest : "app/runIntegrationTest",
	mvnComponentTest : "app/runComponentTest",
	mvnFunctionalTest : "app/runFunctionalTest",
	logContent : "",
	downloads : "downloads",
	
	settings : "settings",
	projectSettings : "projectsettings",
	editprojectSettings : "editSettings",	
	dynamicPage : "dynamicPage",
	dynamicPageContext : "dynamic",
	templateContext : "template",

	ci : "ci",
	jobTemplates : "jobTemplates",
	continuousDeliveryView : "continuousDeliveryView",
	continuousDeliveryConfigure : "continuousDeliveryConfigure",
	ciPhase : "ci-",
	codeValidateGoal : "validate-code",
	packageGoal : "package",
	deployGoal : "deploy",
	unitTestGoal : "unit-test",
	componentTestGoal : "component-test",
	functionalTestGoal : "functional-test",
	integrationTestGoal : "integration-test",
	performanceTestGoal : "performance-test",
	mvnStartHub : "app/startHub",
	mvnStopHub : "app/stopHub",
	mvnStartNode : "app/startNode",
	mvnStopNode : "app/stopNode",
	loadTestGoal : "load-test",
	pdfReportGoal : "pdf-report",

	testSuiteName : "Downloads",
	
	startHubGoal : "start-hub",
	startNodeGoal : "start-node",
	
	paramaterContext : "parameter",
	dependencyContext : "dependency",

	callLadda : false,		
	
	clearInterval : {},

	basePlaceholder : "basepage\\:widget",
	headerPlaceholder : "<div id='header'></div>",
	contentPlaceholder : "content\\:widget",
	footerPlaceholder : "<div id='footer'></div>",
	navigationPlaceholder : "navigation\\:widget"
};

define([], function() {
	$(document).ready(function(){

		$.get('src/components/login/test/config.json', function(data) {
			commonVariables.globalconfig = data;
			commonVariables.animation = data.navigation.animation;
			commonVariables.webserviceurl = "framework/rest/api/";
			configJson = {
				// comment out the below line for production, this one is so require doesn't cache the result
				urlArgs: "time=" +  (new Date()).getTime(),
				baseUrl: "src/",
				
				paths : {
					lib : "lib",
					js : "js",
					framework : "js/framework",
					listener : "js/commonComponents/listener",
					fastclick : "lib/fastclick",
					api : "js/api",
					common : "js/commonComponents/common",
					modules: "js/commonComponents/modules",
					Clazz : "js/framework/class",
					components: "components",
					configData: data
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

			require(["framework/class", "framework/widget", "lib/jslib_bootstrap_datepicker-1.0.0", "lib/bootstrap_select_min-3.2", "framework/widgetWithTemplate", "framework/navigationController", "common/loading", "api/api", "lib/jquery_mockjax-1.0", "lib/json2-1.0", "lib/jslib_jss_min-1.0.0", "lib/i18next-1.6.0", "lib/signal-1.0.1", "lib/signalbinding-1.0.1", "lib/main-2.3", "lib/scrollbars-1.0", "lib/jquery_mousewheel-3.0", "lib/jquery_mousehold-1.0", "lib/jquery_ba_resize_min-1.0", "lib/jquery_event_drag_min-2.0", "lib/jquery_mCustomScrollbar_concat_min-2.8.1", "lib/jquery_ui-1.10.3", "lib/jquery_magnific_popup_min-1.0", "lib/fileuploader-2.4", "lib/bootstrap_min-2.3.1", "lib/jquery_zclip-2.0", "lib/highstock-1.3.7.1", "lib/jquery.tablesorter.min-1.0", "lib/bootstrap-colorpalette-1.0", "lib/bootstrap-datetimepicker.min-2.0", "lib/jstree-1.0.0", "lib/Jquery_file_downloader-1.4.2"], function () {

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
				
				commonVariables.loadingScreen = new Clazz.com.js.widget.common.Loading();
				commonVariables.api = new Clazz.com.js.api.API();
			
				require(["loginTest", "navigation/listener/navigationListener" ], function(loginTest){
					commonVariables.navListener = Clazz.com.components.navigation.js.listener.navigationListener();
					loginTest.runTests(data);
				});
			});
		}, "json");
	});
});
