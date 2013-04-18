
var commonVariables = {
	globalconfig : "",
	webserviceurl : "../rest/api/",
	contexturl : "..",
	
	login : "login",
	loginContext : "login",

	projectlist : "projectlist",
	addproject : "addproject",
	editproject : "editproject",
	
	edit : "Edit",
	create : "Create",
	deleted : "Delete",
	
	basePlaceholder : "basepage\\:widget",
	headerPlaceholder : $("<header\\:widget></header\\:widget>"),
	navigationPlaceholder : $("<navigation\\:widget></navigation\\:widget>"),
	contentPlaceholder : $("<content\\:widget></content\\:widget>"),
	footerPlaceholder : $("<footer\\:widget></footer\\:widget>")
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
			
			var loginView = new Clazz.com.components.login.js.Login();
			loginView.loadPage();
			
			//Register hashChange function for History back
			/* $(window).on("hashChange", function(e){
				Clazz.navigationController.pop();
			});
				
			$(window).hashChange(); */
		});
	}, "json");
});