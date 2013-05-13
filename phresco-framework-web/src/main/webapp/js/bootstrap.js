
var commonVariables = {
	globalconfig : "",
	webserviceurl : "../rest/api/",
	contexturl : "..",
	
	navListener : null,
	
	header : "header",
	headerContext : "",
	
	footer : "footer",
	footerContext : "",
		
	login : "login",
	loginContext : "login",

	projectlist : "projectlist",
	projectlistContext : "project",

	projects : "projects",
	projectContext : "projects",
	editproject : "editproject",
	
	dynamicPage : "dynamicPage",
	dynamicPageContext : "dynamicPage",

	featurePage : "features",
	featurePageContext : "features",
	
	editApplication : "appinfo",
	
	featurelist : "featurelist",
	
	codequality : "codequality",
	
	configuration : "configuration",
	
	customerTheme : null,
	defaultcustomer : "Photon",
	customerInfoContext : "technology/customerinfo?customerName=",
	
	build : "build",
	
	edit : "Edit",
	create : "Create",
	deleted : "Delete",
	
	basePlaceholder : "basepage\\:widget",
	headerPlaceholder : $("<header\\:widget></header\\:widget>"),
	navigationPlaceholder : $("<navigation\\:widget></navigation\\:widget>"),
	contentPlaceholder : $("<content\\:widget></content\\:widget>"),
	footerPlaceholder : $("<footer\\:widget></footer\\:widget>"),


	openccmini : function(e,place) {
			
		var clicked = $(e);
		var target = $("#" + place);
		var twowidth = window.innerWidth/1.5;;

		if (clicked.offset().left < twowidth) {	
			$(target).toggle();
			var t=clicked.offset().top - target.height()/2 + 10;
			var l=clicked.offset().left + clicked.width()+ 15;
			$(target).offset({
				top: t,
				left: l
			});
			
			$(target).addClass('speakstyleleft').removeClass('speakstyleright');
		}
		else {
			$(target).toggle();
			var t=clicked.offset().top - target.height()/2 + 10;
			var l=clicked.offset().left - (target.width()+15);
			$(target).offset({
				top: t,
				left: l
			});
			
			$(target).addClass('speakstyleright').removeClass('speakstyleleft');

		}
		
		$(document).keyup(function(e) {
			if(e.which == 27){
				$("#" + place).hide();
			}
		});
		
		$('.dyn_popup_close').click( function() {
			$("#" + place).hide();
		});
	}

};

$(document).ready(function(){
	$.get($("basepage\\:widget").attr("config"), function(data) {
		commonVariables.globalconfig = data;
		configJson = {
			// comment out the below line for production, this one is so require doesn't cache the result
			urlArgs: "time=" +  (new Date()).getTime(),
			baseUrl: "../js/",
			
			paths : {
				framework : "framework",
				listener : "commonComponents/listener",
				api : "api",
				lib : "../lib",
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

		require(["framework/class", "framework/navigationController", "login/login"],	function () {
		 	Clazz.config = data;
			Clazz.navigationController = new Clazz.NavigationController({
				mainContainer : "basepage\\:widget",
				transitionType : Clazz.config.navigation.transitionType,
				isNative : Clazz.config.navigation.isNative
			});

			//Apply customer based theme
			//location.href
			$.get(commonVariables.webserviceurl + commonVariables.customerInfoContext + commonVariables.defaultcustomer, function(themeData){
				JSS.css(themeData.data.frameworkTheme);
			});
			
			var loginView = new Clazz.com.components.login.js.Login();
			loginView.loadPage();
		});
	}, "json");
});