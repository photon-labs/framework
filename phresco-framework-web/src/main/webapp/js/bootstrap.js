var commonVariables = {
	globalconfig : "",
	webserviceurl : "rest/api/",
	contexturl : "",
	
	navListener : null,
	appDirName : null,
	goal : null,
	phase : null,
	buildNo : null,
	iphoneDeploy : null,
	
	header : "header",
	headerContext : "",
	
	customerContext : "",
	
	footer : "footer",
	footerContext : "",
	
	environmentName : "",
	techId : "",
	logContent : "",
	
	login : "login",
	loginContext : "login",
	loginView : null,
	loginlistenerObj : null,
	upgrade : "upgrade",
	upgradeAvailable : "upgradeAvailable",
	projectlist : "projectlist",
	projectlistContext : "project",

	addproject : "addproject",
	projectContext : "projects",
	editproject : "editproject",
	
	dynamicPage : "dynamicPage",
	dynamicPageContext : "dynamic",
	templateContext : "template",
	
	paramaterContext : "parameter",
	dependencyContext : "dependency",	

	featurePage : "features",
	featurePageContext : "features",
	
	editApplication : "appinfo",
	
	featurelist : "featurelist",
	
	codequality : "codequality",
	
	configuration : "configuration",
	editConfiguration : "editConfiguration",
	updateConfigName : '',
	envSpecifig : '',
	
	croneExpression : "croneExpression",
	
	qualityContext : "quality",
	testType : null,
	unit : "unit",
	unitTest : "unitTest",
	component : "component",
	componentTest : "componentTest",
	functional : "functional",
	functionalTest : "functionalTest",
	integration : "integration",
	integrationTest : "integrationTest",
	from : null,
	testSuiteName : null,
	testSuites : null,
	
	performance : "performance",
	performanceTest : "performanceTest",
	performanceTestResults : "performanceTestResults",

	load : "load",
	loadTest : "loadTest",
	testsuiteResult : "testsuiteResult",
	testcaseResult	: "testcaseResult",
//	testResult : "testResult",
	manual : "manual",
	manualTest : "manualTest",

	ci : "ci",
	jobTemplates : "jobTemplates",
	continuousDeliveryView : "continuousDeliveryView",
	continuousDeliveryConfigure : "continuousDeliveryConfigure",
	
	settings : "settings", 

	downloads : "downloads",
	
	customerTheme : null,
	defaultcustomer : "Photon",
	customerInfoContext : "technology/customerinfo?customerName=",
	
	build : "build",

	megabyte : "MB",
	kilobyte : "KB",
	
	mavenService : "mavenService",
	mavenServiceContext : "mavenService",
	
	openFolderContext : "util/openFolder",
	copyPathContext : "util/copyPath",
	copyToClipboardContext : "util/copyToClipboard",
	
	/******* Open folder and copy path constants *******/
	typeBuild : "build",
	typeUnitTest : "unitTest",
	typeFunctionalTest : "functionalTest",
	typeManualTest : "manualTest",
	typeLoadTest : "loadTest",
	typePerformanceTest : "performanceTest",
	typeComponentTest : "componentTest",
	typeIntegrationTest : "integrationTest",
	
	/******* Technology options constants *******/
	optionsCode : "Code",
	optionsReports : "Reports",
	optionsUnitTest : "Unit_Test",
	optionsComponentTest : "Component_Test",
	optionsFunctionalTest : "Functional_Test",
	optionsPerformanceTest : "Performance_Test",
	optionsLoadTest : "Load_Test",
	optionsManualTest : "Manual_Test",
	optionsSettings : "settings",
	optionsCI : "CI",
	optionsRunAgainstSrc : "Run_Against_Source",
	optionsMinification : "Minification",
	optionsBuild : "Build",
	optionsDeploy : "Deploy",
	optionsExeDownload : "Exe_Download",
	optionsFeatureConfig : "Feature_Config",
	optionsComponentConfig : "Component_Config",
	optionsProcessBuild : "Process_Build",
	optionsRemoteDeployment : "Remote_Deployment",
	optionsEmbedApplication : "Embed_Application",
	optionsThemeBuilder : "Theme_Builder",

	/******* Customer main menu options constants *******/
	optionsDashboard : "Home",
    optionsHelp : "Help",
    optionsSettings : "Settings",
    optionsDownload : "Download",
    optionsAdmin : "Admin",
	
	/******* Phase *********/
	ciPhase : "ci-",
	projectLevel : false,

	/******* Goals *********/
	codeValidateGoal : "validate-code",
	packageGoal : "package",
	deployGoal : "deploy",
	unitTestGoal : "unit-test",
	componentTestGoal : "component-test",
	functionalTestGoal : "functional-test",
	performanceTestGoal : "performance-test",
	loadTestGoal : "load-test",
	pdfReportGoal : "pdf-report",

	startHubGoal : "start-hub",
	startNodeGoal : "start-node",
	
	/******* mvn Context *********/
	mvnlogService : "app/readlog",
	mvnBuild : "app/build",
	mvnDeploy : "app/deploy",
	mvnProcessBuild : "app/processBuild",
	mvnUnitTest : "app/runUnitTest",
	mvnComponentTest : "app/runComponentTest",
	mvnCodeValidation : "app/codeValidate",
	mvnRunagainstSource : "app/runAgainstSource",
	mvnStopServer : "app/stopServer",
	mvnRestartServer : "app/restartServer",
	mvnPerformanceTest : "app/performanceTest",
	mvnLoadTest : "app/loadTest",
	mvnFunctionalTest : "app/runFunctionalTest",
	mvnMinification : "app/minification",
	mvnStartHub : "app/startHub",
	mvnStopHub : "app/stopHub",
	mvnCheckHub : "app/checkForHub",
	mvnShowStartedHub : "app/showStartedHubLog",
	mvnStartNode : "app/startNode",
	mvnStopNode : "app/stopNode",
	mvnCheckNode : "app/checkForNode",
	mvnShowStartedNode : "app/showStartedNodeLog",
	mvnCiSetup : "app/ciSetup",
	mvnCiStart : "app/ciStart",
	mvnCiStop : "app/ciStop",
	mvnValidateTheme : "app/validateTheme",
	
	/******** mvn Context end****/
	
	edit : "Edit",
	create : "Create",
	deleted : "Delete",
	subtabClicked : false,
	
	loadingScreen : null,
	hideloading : false,
	continueloading : false,
	requireLoading : true,
	api : null,
	ajaxXhr : null,
	
	basePlaceholder : "basepage\\:widget",
	headerPlaceholder : $("<header\\:widget></header\\:widget>"),
	navigationPlaceholder : $("<navigation\\:widget></navigation\\:widget>"),
	contentPlaceholder : $("<content\\:widget></content\\:widget>"),
	footerPlaceholder : $("<footer\\:widget></footer\\:widget>"),
	
	getParameterByName : function (name) {
		name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
		var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),	results = regex.exec(location.search);
		return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
};

$(document).ready(function(){
	$.get($("basepage\\:widget").attr("config"), function(data) {
		commonVariables.animation = data.navigation.animation;
		commonVariables.globalconfig = data;
		configJson = {
			// comment out the below line for production, this one is so require doesn't cache the result
			urlArgs: "time=" +  (new Date()).getTime(),
			baseUrl: "js",
			
			paths : {
				framework : "framework",
				listener : "commonComponents/listener",
				api : "api",
				lib : "../lib",
                handlebars: "handlebars-1.0.0",
				common : "commonComponents/common",
				modules: "commonComponents/modules",
				footer: "commonComponents/modules/footer",
				header: "commonComponents/modules/header",
				Clazz : "framework/class",
				components: "../components"				
			}
		};

		$.each(commonVariables.globalconfig.components, function(index, value){
			configJson.paths[index] = value.path;
		});

		// setup require.js
		requirejs.config(configJson);
		
		require(["framework/class", "framework/base", "framework/widget", "common/loading", "api/api",  "framework/widgetWithTemplate", "framework/navigationController", "login/login"], function () {
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
			commonVariables.api = new Clazz.com.js.api.API();
			
			/* $(document).ajaxStart(function() {
				if(!Clazz.navigationController.loadingActive && !commonVariables.continueloading && !commonVariables.hideloading){
					commonVariables.loadingScreen.showLoading($(commonVariables.contentPlaceholder));
				}
			}); 
			
			$(document).ajaxStop(function() {
				if(!Clazz.navigationController.loadingActive && !commonVariables.continueloading){
					commonVariables.hideloading = false;
					commonVariables.loadingScreen.removeLoading();
				}
			});*/
			
			app.initialize();
		});
	}, "json");
});

 app = {
    initialize: function() {
        this.bind();
    },
    bind: function() {
        //router  
        hasher.initialized.add(this.handleChanges);
        hasher.changed.add(this.handleChanges);
        hasher.init();
        if(!window.location.hash){
        }  
    },
    handleChanges: function(newHash, oldHash){
		if((localStorage.getItem("userInfo") === null) || ((newHash === undefined || newHash === null || newHash === "") && (oldHash === undefined || oldHash === null || oldHash === ""))){
			location.hash = '';
			this.app.loadDefault();
		}else{
			if(newHash !== undefined && newHash !== null && newHash !==""){
				this.app.loadComponent(newHash);
			}else if((newHash === undefined || newHash === null || newHash === "") && 
			(oldHash !== undefined && oldHash !== null && oldHash !== "")){
				this.app.loadComponent(oldHash);
			}
		}
	},
	
	loadComponent : function(hashVal){
		if(!$.isEmptyObject(Clazz.navigationController.stack)){
			if(!$.isEmptyObject(Clazz.navigationController.stack[hashVal])){
				var data = Clazz.navigationController.stack[hashVal];
				if(data !== undefined && data !== null && !$.isEmptyObject(data) && data !== ""){
					Clazz.navigationController.browserBack = true;
					Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
					Clazz.navigationController.push(data.view, true, true);
					$(commonVariables.navigationPlaceholder).find('ul li a').removeClass('act');
					$(commonVariables.navigationPlaceholder).find('ul li#' + hashVal + ' a').addClass('act')
				}
			}
		}else{
			if(commonVariables.loginlistenerObj === null)
				commonVariables.loginlistenerObj = new Clazz.com.components.login.js.listener.LoginListener();
			commonVariables.loginlistenerObj.pageRefresh(commonVariables.projectlist);
		}
	},
	
	loadDefault : function(){
		if(commonVariables.loginView === null)
			commonVariables.loginView = new Clazz.com.components.login.js.Login();
		commonVariables.loginView.loadPage();
	}
};


