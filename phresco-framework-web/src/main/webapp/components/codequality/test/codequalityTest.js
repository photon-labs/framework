define(["jquery", "codequality/codequality"], function($, Codequality) {

	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { runTests: function (configData) {
		module("codequality.js;Codequality");
		var codequality = new Codequality();
		
		asyncTest("Codequality UI test case ", function() {
			mockgetReports = mockFunction();
			when(mockgetReports)(anything()).then(function(arg) {
				var response = {"response":null,"message":"Dependency returned successfully","exception":null,"data":[{"options":null,"validateAgainst":{"value":"Source","key":"src","dependency":"skipTests"}},{"options":null,"validateAgainst":{"value":"Functional Test","key":"functional","dependency":null}}]};
	 
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
				output = $(commonVariables.contentPlaceholder).find(".code_report").children('font').text();
				equal('Report', output, "Codequality UI test case tested");
			}, 1500);
		});
		/*
		asyncTest("Codequality iFrame test case ", function() {
			 mockgetReports1 = mockFunction();
			when(mockgetReports1)(anything()).then(function(arg) {
				var response = {"response":null,"message":"Dependency returned successfully","exception":null,"data":[{"options":null,"validateAgainst":{"value":"Source","key":"src","dependency":"skipTests"}},{"options":null,"validateAgainst":{"value":"Functional Test","key":"functional","dependency":null}}]}

				var projectlist = {};
				projectlist.projectlist = response;					
				codequality.renderedData = response;
				codequality.renderTemplate(projectlist, commonVariables.contentPlaceholder);
				//codequality.codequalityListener.constructHtml(response);
			});
			
			codequality.codequalityListener.codequalityAPI.codequality = mockgetReports1; 
			
			mockgetIframe1 = mockFunction();
			when(mockgetIframe1)(anything()).then(function(arg) {
				var response =  {"response":null,"message":"Dependency returned successfully","exception":null,"data":"http://localhost:9000/dashboard/index/com.photon.phresco:html:js"}
			});
			codequality.codequalityListener.getIframeReport = mockgetIframe1;
			
			codequality.loadPage();
			
			
			setTimeout(function() {
				start();
				console.info('value = ' , $(commonVariables.contentPlaceholder).find("#content_div").children().eq(0))
				output = $(commonVariables.contentPlaceholder).find("#content_div").children('font').text();
				equal('Report', output, "Codequality iFrame test case");
			}, 1500);
		});*/
		
		
	return true;}};
	
});
