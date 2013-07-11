define(["performanceTest/performanceTest"], function(PerformanceTest) {

	return {
		runTests: function (configData) {
			module("PerformanceTest.js");
			var performanceTest = new PerformanceTest(), self = this;
			asyncTest("Performance template render test", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+commonVariables.qualityContext+"/"+commonVariables.performance+"?appDirName=MergePerformanceAndLoad1-php5.4.x",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Parameter returned successfully","exception":null,"data":{"resultAvailable":true,"testResultFiles":["HTTP Request1.jtl","LoginTest.jtl","testServer.jtl"],"showDevice":false,"devices":[],"testAgainsts":["server","webservice","database"]}});
				  }
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/"+commonVariables.performanceTestResults+"?appDirName=MergePerformanceAndLoad1-php5.4.x&testAgainst=server&resultFileName=HTTP Request1.jtl&deviceId=&showGraphFor=responseTime",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Parameter returned successfully","exception":null,"data":{"perfromanceTestResult":[{"totalBytes":86117,"max":1253,"throughtPut":0.8,"min":1253,"avg":1253,"noOfSamples":1,"times":[1253],"totalTime":1253,"err":0,"maxTs":1371464304079,"lastTime":1253,"minTs":1371464304079,"avgBytes":86117,"kbPerSec":67.12,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request1"},{"totalBytes":86114,"max":214,"throughtPut":4.7,"min":214,"avg":214,"noOfSamples":1,"times":[214],"totalTime":214,"err":0,"maxTs":1371464305377,"lastTime":214,"minTs":1371464305377,"avgBytes":86114,"kbPerSec":392.97,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request2"},{"totalBytes":86130,"max":225,"throughtPut":4.4,"min":225,"avg":225,"noOfSamples":1,"times":[225],"totalTime":225,"err":0,"maxTs":1371464305592,"lastTime":225,"minTs":1371464305592,"avgBytes":86130,"kbPerSec":373.83,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request3"},{"totalBytes":86021,"max":1048,"throughtPut":1,"min":1048,"avg":1048,"noOfSamples":1,"times":[1048],"totalTime":1048,"err":0,"maxTs":1371464305818,"lastTime":1048,"minTs":1371464305818,"avgBytes":86021,"kbPerSec":80.16,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request4"},{"totalBytes":86077,"max":883,"throughtPut":1.1,"min":883,"avg":883,"noOfSamples":1,"times":[883],"totalTime":883,"err":0,"maxTs":1371464306868,"lastTime":883,"minTs":1371464306868,"avgBytes":86077,"kbPerSec":95.2,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request5"},{"totalBytes":86121,"max":408,"throughtPut":2.5,"min":408,"avg":408,"noOfSamples":1,"times":[408],"totalTime":408,"err":0,"maxTs":1371464307752,"lastTime":408,"minTs":1371464307752,"avgBytes":86121,"kbPerSec":206.13,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request6"}],"graphData":"[1253.0,214.0,225.0,1048.0,883.0,408.0]","graphAlldata":"[], [], []","aggregateResult":{"error":"0.00","max":1253,"min":214,"avgBytes":86096.67,"stdDev":408.81,"average":671.83,"throughput":1.5,"kb":123.61,"sample":6},"totalStdDev":0,"totalThroughput":0,"graphFor":"responseTime","label":"['HTTP Request1','HTTP Request2','HTTP Request3','HTTP Request4','HTTP Request5','HTTP Request6']"}});
				  }
				});
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});						

				var performanceTestAPI =  new Clazz.com.components.performanceTest.js.api.PerformanceTestAPI();
				performanceTestAPI.localVal.setSession("appDirName" , "MergePerformanceAndLoad1-php5.4.x");
				
				commonVariables.navListener.onMytabEvent("performanceTest");
				setTimeout(function() {
					start();
					equal($('.unit_text').text().trim(), "Performance Test", "performance template rendering verified");
					self.runNegativeEventTest(performanceTest);
				}, 1000);
			});
	},
	
	runNegativeEventTest : function(performanceTest) {
			module("PerformanceTest.js");
			var performanceTest = new PerformanceTest(), self = this;
			asyncTest("Performance template render negative test test", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+commonVariables.qualityContext+"/"+commonVariables.performance+"?appDirName=MergePerformanceAndLoad1-php5.4.x",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Parameter returned successfully","exception":null,"data":{"resultAvailable":true,"testResultFiles":["HTTP Request1.jtl","LoginTest.jtl","testServer.jtl"],"showDevice":false,"devices":[],"testAgainsts":["server","webservice","database"]}});
				  }
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/"+commonVariables.performanceTestResults+"?appDirName=MergePerformanceAndLoad1-php5.4.x&testAgainst=server&resultFileName=HTTP Request1.jtl&deviceId=&showGraphFor=responseTime",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Parameter returned successfully","exception":null,"data":{"perfromanceTestResult":[{"totalBytes":86117,"max":1253,"throughtPut":0.8,"min":1253,"avg":1253,"noOfSamples":1,"times":[1253],"totalTime":1253,"err":0,"maxTs":1371464304079,"lastTime":1253,"minTs":1371464304079,"avgBytes":86117,"kbPerSec":67.12,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request1"},{"totalBytes":86114,"max":214,"throughtPut":4.7,"min":214,"avg":214,"noOfSamples":1,"times":[214],"totalTime":214,"err":0,"maxTs":1371464305377,"lastTime":214,"minTs":1371464305377,"avgBytes":86114,"kbPerSec":392.97,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request2"},{"totalBytes":86130,"max":225,"throughtPut":4.4,"min":225,"avg":225,"noOfSamples":1,"times":[225],"totalTime":225,"err":0,"maxTs":1371464305592,"lastTime":225,"minTs":1371464305592,"avgBytes":86130,"kbPerSec":373.83,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request3"},{"totalBytes":86021,"max":1048,"throughtPut":1,"min":1048,"avg":1048,"noOfSamples":1,"times":[1048],"totalTime":1048,"err":0,"maxTs":1371464305818,"lastTime":1048,"minTs":1371464305818,"avgBytes":86021,"kbPerSec":80.16,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request4"},{"totalBytes":86077,"max":883,"throughtPut":1.1,"min":883,"avg":883,"noOfSamples":1,"times":[883],"totalTime":883,"err":0,"maxTs":1371464306868,"lastTime":883,"minTs":1371464306868,"avgBytes":86077,"kbPerSec":95.2,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request5"},{"totalBytes":86121,"max":408,"throughtPut":2.5,"min":408,"avg":408,"noOfSamples":1,"times":[408],"totalTime":408,"err":0,"maxTs":1371464307752,"lastTime":408,"minTs":1371464307752,"avgBytes":86121,"kbPerSec":206.13,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request6"}],"graphData":"[1253.0,214.0,225.0,1048.0,883.0,408.0]","graphAlldata":"[], [], []","aggregateResult":{"error":"0.00","max":1253,"min":214,"avgBytes":86096.67,"stdDev":408.81,"average":671.83,"throughput":1.5,"kb":123.61,"sample":6},"totalStdDev":0,"totalThroughput":0,"graphFor":"responseTime","label":"['HTTP Request1','HTTP Request2','HTTP Request3','HTTP Request4','HTTP Request5','HTTP Request6']"}});
				  }
				});
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});						

				var performanceTestAPI =  new Clazz.com.components.performanceTest.js.api.PerformanceTestAPI();
				performanceTestAPI.localVal.setSession("appDirName" , "MergePerformanceAndLoad1-php5.4.x");
				
				commonVariables.navListener.onMytabEvent("performanceTest");
				setTimeout(function() {
					start();
					notEqual($('.unit_text').text().trim(), "Performance Tests", "performance template rendering negative test case");
					self.runTablelengthTest(performanceTest);
				}, 1000);
			});
		},	
		
		runTablelengthTest : function(performanceTest) {
			module("PerformanceTest.js");
			var performanceTest = new PerformanceTest(), self = this;
			asyncTest("Result table length test", function() {
			
				$.mockjax({
				  url: commonVariables.webserviceurl+commonVariables.qualityContext+"/"+commonVariables.performance+"?appDirName=MergePerformanceAndLoad1-php5.4.x",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Parameter returned successfully","exception":null,"data":{"resultAvailable":true,"testResultFiles":["HTTP Request1.jtl","LoginTest.jtl","testServer.jtl"],"showDevice":false,"devices":[],"testAgainsts":["server","webservice","database"]}});
				  }
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/"+commonVariables.performanceTestResults+"?appDirName=MergePerformanceAndLoad1-php5.4.x&testAgainst=server&resultFileName=HTTP Request1.jtl&deviceId=&showGraphFor=responseTime",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Parameter returned successfully","exception":null,"data":{"perfromanceTestResult":[{"totalBytes":86117,"max":1253,"throughtPut":0.8,"min":1253,"avg":1253,"noOfSamples":1,"times":[1253],"totalTime":1253,"err":0,"maxTs":1371464304079,"lastTime":1253,"minTs":1371464304079,"avgBytes":86117,"kbPerSec":67.12,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request1"},{"totalBytes":86114,"max":214,"throughtPut":4.7,"min":214,"avg":214,"noOfSamples":1,"times":[214],"totalTime":214,"err":0,"maxTs":1371464305377,"lastTime":214,"minTs":1371464305377,"avgBytes":86114,"kbPerSec":392.97,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request2"},{"totalBytes":86130,"max":225,"throughtPut":4.4,"min":225,"avg":225,"noOfSamples":1,"times":[225],"totalTime":225,"err":0,"maxTs":1371464305592,"lastTime":225,"minTs":1371464305592,"avgBytes":86130,"kbPerSec":373.83,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request3"},{"totalBytes":86021,"max":1048,"throughtPut":1,"min":1048,"avg":1048,"noOfSamples":1,"times":[1048],"totalTime":1048,"err":0,"maxTs":1371464305818,"lastTime":1048,"minTs":1371464305818,"avgBytes":86021,"kbPerSec":80.16,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request4"},{"totalBytes":86077,"max":883,"throughtPut":1.1,"min":883,"avg":883,"noOfSamples":1,"times":[883],"totalTime":883,"err":0,"maxTs":1371464306868,"lastTime":883,"minTs":1371464306868,"avgBytes":86077,"kbPerSec":95.2,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request5"},{"totalBytes":86121,"max":408,"throughtPut":2.5,"min":408,"avg":408,"noOfSamples":1,"times":[408],"totalTime":408,"err":0,"maxTs":1371464307752,"lastTime":408,"minTs":1371464307752,"avgBytes":86121,"kbPerSec":206.13,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request6"}],"graphData":"[1253.0,214.0,225.0,1048.0,883.0,408.0]","graphAlldata":"[], [], []","aggregateResult":{"error":"0.00","max":1253,"min":214,"avgBytes":86096.67,"stdDev":408.81,"average":671.83,"throughput":1.5,"kb":123.61,"sample":6},"totalStdDev":0,"totalThroughput":0,"graphFor":"responseTime","label":"['HTTP Request1','HTTP Request2','HTTP Request3','HTTP Request4','HTTP Request5','HTTP Request6']"}});
				  }
				});
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});						

				var performanceTestAPI =  new Clazz.com.components.performanceTest.js.api.PerformanceTestAPI();
				performanceTestAPI.localVal.setSession("appDirName" , "MergePerformanceAndLoad1-php5.4.x");
				
				commonVariables.navListener.onMytabEvent("performanceTest");
				
				setTimeout(function() {
					start();

					equal($("#testResultTable tr").length, 8, "Result table length test");
					self.runTablelengthNegativeTest(performanceTest);
				}, 1000);
			});
		},
				
		runTablelengthNegativeTest : function(performanceTest) {
			module("PerformanceTest.js");
			var performanceTest = new PerformanceTest(), self = this;
			asyncTest("Result table length negative test", function() {
			//$("#testResultTable").empty();
			
				$.mockjax({
				  url: commonVariables.webserviceurl+commonVariables.qualityContext+"/"+commonVariables.performance+"?appDirName=MergePerformanceAndLoad1-php5.4.x",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Parameter returned successfully","exception":null,"data":{"resultAvailable":true,"testResultFiles":["HTTP Request1.jtl","LoginTest.jtl","testServer.jtl"],"showDevice":false,"devices":[],"testAgainsts":["server","webservice","database"]}});
				  }
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/"+commonVariables.performanceTestResults+"?appDirName=MergePerformanceAndLoad1-php5.4.x&testAgainst=server&resultFileName=HTTP Request1.jtl&deviceId=&showGraphFor=responseTime",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Parameter returned successfully","exception":null,"data":{"perfromanceTestResult":[{"totalBytes":86117,"max":1253,"throughtPut":0.8,"min":1253,"avg":1253,"noOfSamples":1,"times":[1253],"totalTime":1253,"err":0,"maxTs":1371464304079,"lastTime":1253,"minTs":1371464304079,"avgBytes":86117,"kbPerSec":67.12,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request1"},{"totalBytes":86114,"max":214,"throughtPut":4.7,"min":214,"avg":214,"noOfSamples":1,"times":[214],"totalTime":214,"err":0,"maxTs":1371464305377,"lastTime":214,"minTs":1371464305377,"avgBytes":86114,"kbPerSec":392.97,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request2"},{"totalBytes":86130,"max":225,"throughtPut":4.4,"min":225,"avg":225,"noOfSamples":1,"times":[225],"totalTime":225,"err":0,"maxTs":1371464305592,"lastTime":225,"minTs":1371464305592,"avgBytes":86130,"kbPerSec":373.83,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request3"},{"totalBytes":86021,"max":1048,"throughtPut":1,"min":1048,"avg":1048,"noOfSamples":1,"times":[1048],"totalTime":1048,"err":0,"maxTs":1371464305818,"lastTime":1048,"minTs":1371464305818,"avgBytes":86021,"kbPerSec":80.16,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request4"},{"totalBytes":86077,"max":883,"throughtPut":1.1,"min":883,"avg":883,"noOfSamples":1,"times":[883],"totalTime":883,"err":0,"maxTs":1371464306868,"lastTime":883,"minTs":1371464306868,"avgBytes":86077,"kbPerSec":95.2,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request5"},{"totalBytes":86121,"max":408,"throughtPut":2.5,"min":408,"avg":408,"noOfSamples":1,"times":[408],"totalTime":408,"err":0,"maxTs":1371464307752,"lastTime":408,"minTs":1371464307752,"avgBytes":86121,"kbPerSec":206.13,"stdDev":0,"totalStdDev":0,"totalThroughput":0,"label":"HTTP Request6"}],"graphData":"[1253.0,214.0,225.0,1048.0,883.0,408.0]","graphAlldata":"[], [], []","aggregateResult":{"error":"0.00","max":1253,"min":214,"avgBytes":86096.67,"stdDev":408.81,"average":671.83,"throughput":1.5,"kb":123.61,"sample":6},"totalStdDev":0,"totalThroughput":0,"graphFor":"responseTime","label":"['HTTP Request1','HTTP Request2','HTTP Request3','HTTP Request4','HTTP Request5','HTTP Request6']"}});
				  }
				});
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});						

				var performanceTestAPI =  new Clazz.com.components.performanceTest.js.api.PerformanceTestAPI();
				performanceTestAPI.localVal.setSession("appDirName" , "MergePerformanceAndLoad1-php5.4.x");
				
				commonVariables.navListener.onMytabEvent("performanceTest");
				setTimeout(function() {
					start();
					notEqual($("#testResultTable tr").length, 1, "Result table length negative test");
				}, 1000);
			});
		}

	};
});