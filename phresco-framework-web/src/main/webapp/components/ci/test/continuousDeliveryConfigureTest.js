define(["ci/continuousDeliveryConfigure"], function(ContinuousDeliveryConfigure) {
	return { 
	
		runTests: function (configData) {
			
			module("continuousDeliveryConfigure.js");	
			var continuousDeliveryConfigure = new ContinuousDeliveryConfigure(), templateData = {}, self = this, sortList;
			asyncTest("ContinuousDeliveryView - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("projectId" , "a4329529-3c9d-476d-a310-e0cf4436e021");
				$.mockjax({						
					url: commonVariables.webserviceurl + commonVariables.ci+"/list?projectId=a4329529-3c9d-476d-a310-e0cf4436e021&appDirName=", 
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Continuous Delivery List Successfully","exception":null,"data":{"id":"a4329529-3c9d-476d-a310-e0cf4436e021","continuousDeliveries":null},"response":null});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/isAlive",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Jenkins is Alive","exception":null,"responseCode":null,"data":"200","status":null});
					}
				});
				
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});		
				
				commonVariables.navListener.onMytabEvent(commonVariables.continuousDeliveryView);
				
				setTimeout(function() {
					start();
					equal(1, 1, "ContinuousDeliveryConfigure - UI Tested");
					self.continuousDeliveryConfigure(continuousDeliveryConfigure);
				}, 2500);
			});					
		},
		
		continuousDeliveryConfigure : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("ContinuousDeliveryConfigure - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("projectId" , "a4329529-3c9d-476d-a310-e0cf4436e021");
				ciAPI.localVal.setSession("appDirName" , "");
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.configuration + "/listEnvironmentsByProjectId?customerId=photon&projectId=a4329529-3c9d-476d-a310-e0cf4436e021",							
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Environments Listed successfully","exception":null,"data":["Production","Testing"],"response":null});
					}
				});
				
				
				self.sortList = $.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/getJobTemplatesByEnvironment?customerId=photon&projectId=a4329529-3c9d-476d-a310-e0cf4436e021&appDirName=&envName=Production",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates Fetched Successfully","exception":null,"data":{"{\"appName\":\"1\",\"appDirName\":\"1\"}":[{"name":"bld","type":"build","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["1","2","3","SVNCHECK-wordpress"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"1","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["1"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"{\"appName\":\"3\",\"appDirName\":\"3\"}":[{"name":"bld","type":"build","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["1","2","3","SVNCHECK-wordpress"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"code","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["2","3","SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"{\"appName\":\"SVNCHECK-wordpress\",\"appDirName\":\"SVNCHECK-wordpress\"}":[{"name":"bld","type":"build","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["1","2","3","SVNCHECK-wordpress"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"code","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["2","3","SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"Test","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"{\"appName\":\"2\",\"appDirName\":\"2\"}":[{"name":"bld","type":"build","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["1","2","3","SVNCHECK-wordpress"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"code","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["2","3","SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}]},"response":null});
					}
				});
			
				$("input[id='createContinuousDelivery']").click();
				setTimeout(function() {
					start();
					var envName = $(commonVariables.contentPlaceholder).find('select[name=environments]').val();
					var length = $(commonVariables.contentPlaceholder).find('ul[id=sortable1]').find('li').length;
					equal("Production", envName, "ContinuousDeliveryConfigure - UI Tested");
					equal(9, length, "ContinuousDeliveryConfigure - UI Tested");
					self.changeEnvironmentTest(continuousDeliveryConfigure);
				}, 2500);
			});
		},
		
		changeEnvironmentTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("changeEnvironmentTest - UI Test", function() {
				$('select[name=environments]').val('Testing');
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("projectId" , "a4329529-3c9d-476d-a310-e0cf4436e021");
				ciAPI.localVal.setSession("appDirName" , "");
				ciAPI.localVal.setSession("customerId" , "photon");
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/getJobTemplatesByEnvironment?customerId=photon&projectId=a4329529-3c9d-476d-a310-e0cf4436e021&appDirName=&envName=Testing",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates Fetched Successfully","exception":null,"data":{"{\"appName\":\"SVNCHECK-wordpress\",\"appDirName\":\"SVNCHECK-wordpress\"}":[{"name":"bld","type":"build","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["1","2","3","SVNCHECK-wordpress"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"code","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["2","3","SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"Test","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}]},"response":null});
					}
				});
				$('select[name=environments]').change();
				setTimeout(function() {
					start();
					var envName = $(commonVariables.contentPlaceholder).find('select[name=environments]').val();
					var length = $(commonVariables.contentPlaceholder).find('ul[id=sortable1]').find('li').length;
					equal("Testing", envName, "changeEnvironmentTest - UI Tested");
					equal(3, length, "changeEnvironmentTest - UI Tested");
				}, 2500);
			});
		},
		
	};
});