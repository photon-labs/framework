define(["jquery", "codequality/codequality"], function($, Codequality) {

	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { runTests: function (configData) {
		module("codequality.js;Codequality");
		var codequality = new Codequality();
		
		asyncTest("Codequality get reports test", function() {
			mockgetReports = mockFunction();
			when(mockgetReports)(anything()).then(function(arg) {
				var response = {"response":null,"message":"Dependency returned successfully","exception":null,"data":[{"options":null,"validateAgainst":{"value":"Source","key":"src","dependency":"skipTests"}},{"options":null,"validateAgainst":{"value":"Functional Test","key":"functional","dependency":null}}]}

			 //{"response":null,"message":"Dependency returned successfully","exception":null,"data":[{"options":[{"value":"js","key":"js","dependency":"environmentName,showSettings"},{"value":"java","key":"java","dependency":null},{"value":"html","key":"html","dependency":null},{"value":"jsp/jsf","key":"web","dependency":null}],"validateAgainst":{"value":"Source","key":"src","dependency":"src,skipTests"}},{"options":null,"validateAgainst":{"value":"Functional Test","key":"functional","dependency":null}}]}
				var projectlist = {};
				projectlist.projectlist = response;					
				codequality.renderedData = response;
				codequality.renderTemplate(projectlist, commonVariables.contentPlaceholder);
				codequality.codequalityListener.constructHtml(response);
			});
			
			codequality.codequalityListener.codequalityAPI.codequality = mockgetReports;
			
			mockgetIframe = mockFunction();
			when(mockgetIframe)(anything()).then(function(arg) {
				var response =  {"response":null,"message":"Dependency returned successfully","exception":null,"data":"http://localhost:9000/dashboard/index/com.photon.phresco:node-test"}
			});
			codequality.codequalityListener.getIframeReport = mockgetIframe;
			
			codequality.loadPage();
			
			setTimeout(function() {
				start();
				//output = $("#repTypes").val();
				console.info('val = ' , $(commonVariables.contentPlaceholder).find("#codereportTypes"));
				output = $(commonVariables.contentPlaceholder).find("#repTypes").val();
				console.info('output = ' , output);
				equal(output, "Source", "Codequality Service Tested");
				//runOtherTests();
			}, 1500);
		});
	return true;}};
	
});
