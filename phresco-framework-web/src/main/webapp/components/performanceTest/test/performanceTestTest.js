define(["performanceTest/performanceTest"], function(PerformanceTest) {
	
	return {
		runTests: function (configData) {
			module("PerformanceTest.js;PerformanceTest");
			var performanceTest = new PerformanceTest(), self = this;
			asyncTest("Performance template render test", function() {
				mockGetperformanceTestOptions = mockFunction();
				when(mockGetperformanceTestOptions)(anything()).then(function(arg) {
					var response =  {"response":null,"message":"Parameter returned successfully","exception":null,"data":{"resultAvailable":true,"testResultFiles":["HTTP Request1.jtl","testServer.jtl","LoginTest.jtl"],"showDevice":false,"devices":[],"testAgainsts":["server","webservice","database"]}};
					$(commonVariables.contentPlaceholder).empty();
					performanceTest.performanceTestListener.renderPerformanceTemplate(response, performanceTest.renderFnc, commonVariables.contentPlaceholder);
				});
				performanceTest.performanceTestListener.getPerformanceTestReportOptions = mockGetperformanceTestOptions;
				
				performanceTest.loadPage(false);
				
				setTimeout(function() {
					start();
					equal(commonVariables.contentPlaceholder.find('.unit_text').text().trim(), "Performance Test", "performance template rendering verified");
					self.renderTableTest(performanceTest);
				}, 1500);
			});
	},
	
	renderTableTest : function (performanceTest) {
		asyncTest("Performance result table render test", function() {
			mockGetperformanceTestOptions = mockFunction();
			when(mockGetperformanceTestOptions)(anything()).then(function(arg) {
				var response =  {"response":null,"message":"Parameter returned successfully","exception":null,"data":{"resultAvailable":true,"testResultFiles":["HTTP Request1.jtl","testServer.jtl","LoginTest.jtl"],"showDevice":false,"devices":[],"testAgainsts":["server","webservice","database"]}};
				$(commonVariables.contentPlaceholder).empty();
				performanceTest.performanceTestListener.renderPerformanceTemplate(response, performanceTest.renderFnc, commonVariables.contentPlaceholder);
			});
			performanceTest.performanceTestListener.getPerformanceTestReportOptions = mockGetperformanceTestOptions;

			mockGetTestResults = mockFunction();
			when(mockGetTestResults)(anything()).then(function(arg) {
				var response =  {"response":null,"message":"Parameter returned successfully","exception":null,"data":[{"totalBytes":86117,"max":1253,"throughtPut":0.7980845969672786,"min":1253,"avg":1253,"noOfSamples":1,"times":[1253],"totalTime":1253,"err":0,"maxTs":1371464304079,"lastTime":1253,"minTs":1371464304079,"totalThroughput":0,"totalStdDev":0,"stdDev":0,"kbPerSec":67.11782347366322,"avgBytes":86117,"label":"HTTP Request1"},{"totalBytes":86114,"max":214,"throughtPut":4.672897196261682,"min":214,"avg":214,"noOfSamples":1,"times":[214],"totalTime":214,"err":0,"maxTs":1371464305377,"lastTime":214,"minTs":1371464305377,"totalThroughput":0,"totalStdDev":0,"stdDev":0,"kbPerSec":392.9705753504673,"avgBytes":86114,"label":"HTTP Request2"},{"totalBytes":86130,"max":225,"throughtPut":4.444444444444445,"min":225,"avg":225,"noOfSamples":1,"times":[225],"totalTime":225,"err":0,"maxTs":1371464305592,"lastTime":225,"minTs":1371464305592,"totalThroughput":0,"totalStdDev":0,"stdDev":0,"kbPerSec":373.828125,"avgBytes":86130,"label":"HTTP Request3"},{"totalBytes":86021,"max":1048,"throughtPut":0.9541984732824427,"min":1048,"avg":1048,"noOfSamples":1,"times":[1048],"totalTime":1048,"err":0,"maxTs":1371464305818,"lastTime":1048,"minTs":1371464305818,"totalThroughput":0,"totalStdDev":0,"stdDev":0,"kbPerSec":80.15733092795801,"avgBytes":86021,"label":"HTTP Request4"},{"totalBytes":86077,"max":883,"throughtPut":1.1325028312570782,"min":883,"avg":883,"noOfSamples":1,"times":[883],"totalTime":883,"err":0,"maxTs":1371464306868,"lastTime":883,"minTs":1371464306868,"totalThroughput":0,"totalStdDev":0,"stdDev":0,"kbPerSec":95.19770137315969,"avgBytes":86077,"label":"HTTP Request5"},{"totalBytes":86121,"max":408,"throughtPut":2.450980392156863,"min":408,"avg":408,"noOfSamples":1,"times":[408],"totalTime":408,"err":0,"maxTs":1371464307752,"lastTime":408,"minTs":1371464307752,"totalThroughput":0,"totalStdDev":0,"stdDev":0,"kbPerSec":206.13367417279414,"avgBytes":86121,"label":"HTTP Request6"}]};
				setTimeout(function() {
					performanceTest.performanceTestListener.constructResultTable(response.data, commonVariables.contentPlaceholder);
				}, 500);	
			});
			performanceTest.performanceTestListener.getTestResults = mockGetTestResults;
						
			performanceTest.loadPage(false);
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('tbody').find('tr').length !== 0,  true, "performance result table rendering verified");
			}, 1500);
		});
	}
	};
});