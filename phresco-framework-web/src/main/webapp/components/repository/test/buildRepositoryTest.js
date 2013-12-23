define(["repository/buildRepository"], function(BuildRepository) {
	return { 
		runTests : function (configData) {
			module("buildRepository.js");
			var self = this;
			
			asyncTest("Build Repository - Template Render Test", function() {
			
				require(["navigation/navigation"], function() {
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});
				
				commonVariables.projectCode = "test-query";
				commonVariables.api.localVal.setSession('projectId', "b95a4b23-c7a9-47db-b5a6-f35c6fdb8568");
				$.mockjax({
					  url: commonVariables.webserviceurl +"repository/browseBuildRepo?userId=admin&customerId=photon&projectId=b95a4b23-c7a9-47db-b5a6-f35c6fdb8568",
					  type: "GET",
					  dataType: "json",
					  contentType: "application/json",
					  status: 200,
					  response : function() {
						  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":null,"data":["<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><item name=\"rootItem\" path=\"\" type=\"folder\"><item name=\"commons\" path=\"\" type=\"folder\"><item name=\"SNAPSHOT\" path=\"\" type=\"folder\"><item name=\"3.2.0.3003-SNAPSHOT\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.3003-SNAPSHOT.jar\" nature=\"SNAPSHOT\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.3003-SNAPSHOT/phresco-commons-3.2.0.3003-SNAPSHOT.jar\" type=\"file\"/></item><item name=\"3.2.0.3004-SNAPSHOT\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.3004-SNAPSHOT.jar\" nature=\"SNAPSHOT\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.3004-SNAPSHOT/phresco-commons-3.2.0.3004-SNAPSHOT.jar\" type=\"file\"/></item><item name=\"3.2.0.4001-SNAPSHOT\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.4001-SNAPSHOT.jar\" nature=\"SNAPSHOT\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.4001-SNAPSHOT/phresco-commons-3.2.0.4001-SNAPSHOT.jar\" type=\"file\"/></item><item name=\"3.2.0.4002-SNAPSHOT\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.4002-SNAPSHOT.jar\" nature=\"SNAPSHOT\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.4002-SNAPSHOT/phresco-commons-3.2.0.4002-SNAPSHOT.jar\" type=\"file\"/></item><item name=\"3.2.0.4003-SNAPSHOT\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.4003-SNAPSHOT.jar\" nature=\"SNAPSHOT\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.4003-SNAPSHOT/phresco-commons-3.2.0.4003-SNAPSHOT.jar\" type=\"file\"/></item><item name=\"3.2.0.5001-SNAPSHOT\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.5001-SNAPSHOT.jar\" nature=\"SNAPSHOT\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.5001-SNAPSHOT/phresco-commons-3.2.0.5001-SNAPSHOT.jar\" type=\"file\"/></item><item name=\"3.2.0.5002-SNAPSHOT\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.5002-SNAPSHOT.jar\" nature=\"SNAPSHOT\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.5002-SNAPSHOT/phresco-commons-3.2.0.5002-SNAPSHOT.jar\" type=\"file\"/></item><item name=\"3.2.0.5003-SNAPSHOT\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.5003-SNAPSHOT.jar\" nature=\"SNAPSHOT\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.5003-SNAPSHOT/phresco-commons-3.2.0.5003-SNAPSHOT.jar\" type=\"file\"/></item><item name=\"3.2.0.5004-SNAPSHOT\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.5004-SNAPSHOT.jar\" nature=\"SNAPSHOT\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.5004-SNAPSHOT/phresco-commons-3.2.0.5004-SNAPSHOT.jar\" type=\"file\"/></item><item name=\"3.2.0.5005-SNAPSHOT\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.5005-SNAPSHOT.jar\" nature=\"SNAPSHOT\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.5005-SNAPSHOT/phresco-commons-3.2.0.5005-SNAPSHOT.jar\" type=\"file\"/></item></item><item name=\"RELEASE\" path=\"\" type=\"folder\"><item name=\"3.2.0.3002\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.3002.jar\" nature=\"RELEASE\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.3002/phresco-commons-3.2.0.3002.jar\" type=\"file\"/></item><item name=\"3.2.0.3003\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.3003.jar\" nature=\"RELEASE\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.3003/phresco-commons-3.2.0.3003.jar\" type=\"file\"/></item><item name=\"3.2.0.4000\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.4000.jar\" nature=\"RELEASE\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.4000/phresco-commons-3.2.0.4000.jar\" type=\"file\"/></item><item name=\"3.2.0.4001\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.4001.jar\" nature=\"RELEASE\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.4001/phresco-commons-3.2.0.4001.jar\" type=\"file\"/></item><item name=\"3.2.0.4002\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.4002.jar\" nature=\"RELEASE\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.4002/phresco-commons-3.2.0.4002.jar\" type=\"file\"/></item><item name=\"3.2.0.5000\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.5000.jar\" nature=\"RELEASE\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.5000/phresco-commons-3.2.0.5000.jar\" type=\"file\"/></item><item name=\"3.2.0.5001\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.5001.jar\" nature=\"RELEASE\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.5001/phresco-commons-3.2.0.5001.jar\" type=\"file\"/></item><item name=\"3.2.0.5002\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.5002.jar\" nature=\"RELEASE\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.5002/phresco-commons-3.2.0.5002.jar\" type=\"file\"/></item><item name=\"3.2.0.5003\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.5003.jar\" nature=\"RELEASE\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.5003/phresco-commons-3.2.0.5003.jar\" type=\"file\"/></item><item name=\"3.2.0.5004\" type=\"folder\"><item appDirName=\"commons\" name=\"phresco-commons-3.2.0.5004.jar\" nature=\"RELEASE\" path=\"com/photon/phresco/commons/phresco-commons/3.2.0.5004/phresco-commons-3.2.0.5004.jar\" type=\"file\"/></item></item></item></item></root>"],"status":null});
					  }	
				});
				
				$("#editprojecttitle").attr('projectname', 'TestJquery');
				commonVariables.api.localVal.setProjectInfo(null);
				commonVariables.navListener.onMytabEvent("buildRepo");
				setTimeout(function() {
					start();
					var len = $('input[name=download]').length;
					equal(len, 1, "Build Repository test template rendering tested");
					self.artifactInformationDisplayTest();
				}, 3000);
			});
		},
		
		artifactInformationDisplayTest : function() {
			var self = this;
			asyncTest("Build Repository - Artifact information display test", function() {			
				$.mockjax({
					url: commonVariables.webserviceurl +"repository/artifactInfo?userId=admin&customerId=photon&appDirName=commons&version=3.2.0.3003&nature=RELEASE",
					type: "GET",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":null,"data":{"presentLocally":true,"repositoryId":"repo-3","repositoryName":"repo-3","repositoryPath":"/com/photon/phresco/commons/phresco-commons/3.2.0.3003/phresco-commons-3.2.0.3003.jar","mimeType":"application/java-archive","uploader":"admin","uploaded":1386270049091,"lastChanged":1386270049091,"size":361226,"sha1Hash":"25b7dd804b62c5a5d1330f1d4d2d49c2ca9f4ffa","md5Hash":"698f41c3717c6ec256d6c3e116183cb5","data":[{"repositoryId":"repo-3","repositoryName":"repo-3","path":"/com/photon/phresco/commons/phresco-commons/3.2.0.3003/phresco-commons-3.2.0.3003.jar","artifactUrl":"http://172.16.17.226:8080/repository/content/repositories/repo-3/com/photon/phresco/commons/phresco-commons/3.2.0.3003/phresco-commons-3.2.0.3003.jar","canView":true}],"canDelete":false},"status":null});
					}
				});
				$('a[version="3.2.0.3003"]').parent().parent().find('.badge-warning').click();
				setTimeout(function() {
					start();
					equal($("#repoPath").text(), "/com/photon/phresco/commons/phresco-commons/3.2.0.3003/phresco-commons-3.2.0.3003.jar", "Artifact information display tested");
					require(["sourceRepositoryTest"], function(sourceRepositoryTest){
						sourceRepositoryTest.runTests();
					});
				}, 1500);
			}); 
		}
	};
});