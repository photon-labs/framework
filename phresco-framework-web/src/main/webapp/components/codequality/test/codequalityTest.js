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
		
		asyncTest("Sonar status test case ", function() {
			mocksonarStatus = mockFunction();

			when(mocksonarStatus)(anything()).then(function(arg) {
				
				var codequalityresponse = {"message":"Dependency returned successfully","exception":null,"data":[{"options":null,"validateAgainst":{"value":"Source","key":"src","dependency":null}},{"options":null,"validateAgainst":{"value":"Functional Test","key":"functional","dependency":null}}],"response":null};
				var sonarStatus = {};
				sonarStatus.getReportTypes = codequalityresponse;	
				codequality.renderTemplate(sonarStatus, commonVariables.contentPlaceholder);
				
			});
			
			codequality.codequalityListener.codequalityAPI.codequality = mocksonarStatus;
			codequality.loadPage();
			
			setTimeout(function() {
				start();
				var param = $(commonVariables.contentPlaceholder).find("#codereportTypes");
				
				var codequalityresponse = {"message":"Dependency returned successfully","exception":null,"data":[{"options":null,"validateAgainst":{"value":"Source","key":"src","dependency":null}},{"options":null,"validateAgainst":{"value":"Functional Test","key":"functional","dependency":null}}],"response":null};
				codequality.codequalityListener.constructHtml(codequalityresponse, param);
				
				var output = $(commonVariables.contentPlaceholder).find("#codereportTypes ul:last-child").find("li").attr("id");
				equal(output, "fat-menu", "Sonar status test case tested");
			}, 1500);
		});
		
		
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
				var response = {"message":"Report not available","exception":null,"data":null,"response":null};
			});
			codequality.codequalityListener.getIframeReport = mockgetIframe1;
			
			codequality.loadPage();
			
			
			setTimeout(function() {
				start();
				var response = {"message":"Report not available","exception":null,"data":null,"response":null};
				$(commonVariables.contentPlaceholder).find("#content_div").html(response.message);
				var output = $(commonVariables.contentPlaceholder).find("#content_div").text();
				equal(output, "Report not available", "Codequality iFrame test case");
			}, 1500);
		});
		
		asyncTest("Codequality validate test case ", function() {
			mockcodevalidate = mockFunction();
			when(mockcodevalidate)(anything()).then(function(arg) {
				var response = {"status":"STARTED","log":"STARTED","connectionAlive":false,"uniquekey":"771f1572-db91-415f-8e7b-463702ca361c","service_exception":""};

				var validate = {};
				validate.codeValidate = response;					
				codequality.renderedData = response;
				codequality.renderTemplate(validate, commonVariables.contentPlaceholder);
				//codequality.codequalityListener.constructHtml(response);
			});
			
			codequality.codequalityListener.codequalityAPI.codequality = mockcodevalidate; 
			codequality.loadPage();
			
			
			setTimeout(function() {
				start();
				var response = {"status":"STARTED","log":"STARTED","connectionAlive":false,"uniquekey":"771f1572-db91-415f-8e7b-463702ca361c","service_exception":""};
				$(commonVariables.contentPlaceholder).find("#iframePart").html(response.status);
				var output = $(commonVariables.contentPlaceholder).find("#iframePart").text();
				equal(output, "STARTED", "Codequality iFrame test case");
			}, 1500);
		});
		
	return true;}};
	
});
