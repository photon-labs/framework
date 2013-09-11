define(["jquery", "application/application"], function($, Application) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { 
		runTests: function () {
		module("Application.js;Application");
		var application = new Application();  
		var self = this;
		asyncTest("Application page render test case", function() {
			 $.mockjax({
			  url: commonVariables.webserviceurl+"project/editApplication?appDirName=PhpDru7&userId=admin",
			  type: "GET",
			  dataType: "json",
			  contentType: "application/json",
			  status: 200,
			  response : function() {
				  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200009","data":{"embedList":{"response":"a61453bc-bb38-4b26-b7c5-23973d2aa0e6", "response1":"a61453bc-bb38-4b26-b7c5-23973d2aa0e5"},"projectInfo":{"version":"1.0","appInfos":[{"version":"1.0","modules":null,"pomFile":null,"code":"PhpDru7","appDirName":"PhpDru7","techInfo":{"version":"7.8","appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"PHP","techVersions":null,"customerIds":null,"used":false,"name":"php-Drupal7","id":"tech-phpdru7","displayName":null,"status":null,"description":null,"creationDate":1378807347000,"helpText":null,"system":false},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_wamp","artifactInfoIds":["b9b344ed-7cc6-472a-a8d8-bf8f842eac9c"],"name":null,"id":"68426637-6cf7-4312-8d91-978200648754","displayName":null,"status":null,"description":null,"creationDate":1378898757000,"helpText":null,"system":false}],"selectedDatabases":[{"artifactGroupId":"downloads_db2","artifactInfoIds":["3e49bbaf-79f8-41d0-af71-190e094a1b06"],"name":null,"id":"5dbd94f3-44fb-4937-a203-f5380f5461eb","displayName":null,"status":null,"description":null,"creationDate":1378898757000,"helpText":null,"system":false}],"selectedModules":["63e031f4-808f-4a5a-9354-f9aedc70bd0b","2e35e54c-894c-4a8f-adfa-adc92671648b","9a54f56b-5161-490f-8985-514f7946632a","ea7cb09e-6dc1-49e1-b5ee-d148362dec11","55f384ef-3718-4f12-b4ee-4e41ff9ef6ce"],"selectedJSLibs":[],"selectedComponents":[],"selectedWebservices":["restxml"],"functionalFrameworkInfo":{"version":"2.30","frameworkIds":"bf0170f7-2f03-44be-8f68-95c562347816","frameworkGroupId":"e9a72f4d-4d27-4a2f-84f7-fa975f08ebc7","name":null,"id":"811bcf46-03dc-4908-add2-f3f0236558ae","displayName":null,"status":null,"description":null,"creationDate":1378898757000,"helpText":null,"system":false},"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":"a61453bc-bb38-4b26-b7c5-23973d2aa0e6","phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":true,"customerIds":null,"used":false,"name":"PhpDru7","id":"2159a829-9316-4351-ada1-0098bc6712a1","displayName":null,"status":null,"description":"test","creationDate":1378898757000,"helpText":null,"system":false}],"projectCode":"PhpDru7","noOfApps":1,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"customerIds":["photon"],"used":false,"name":"PhpDru7","id":"6b821336-1a11-4bd2-a920-68a124e0d50f","displayName":null,"status":null,"description":"","creationDate":1378807347000,"helpText":null,"system":false}},"status":"success"});
			  }
			}); 
			
			$.mockjax({
			  url: commonVariables.webserviceurl+"appConfig/list?techId=tech-phpdru7&customerId=photon&type=SERVER&platform=Windows64&userId=admin",
			  type: "GET",
			  dataType: "json",
			  contentType: "application/json",
			  status: 200,
			  response : function() {
				  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR300001","data":[{"artifactGroup":{"type":null,"artifactId":"","classifier":null,"packaging":"zip","groupId":"","versions":[{"scope":null,"version":"2.3","artifactGroupId":"downloads_apache-server","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":0,"downloadURL":null,"name":null,"id":"6acf8885-0eaf-430c-b168-913706560a85","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":false},{"scope":null,"version":"2.2","artifactGroupId":"downloads_apache-server","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":0,"downloadURL":null,"name":null,"id":"9541f13e-16ab-42ec-942e-2d8506f9fe19","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":false},{"scope":null,"version":"2.0","artifactGroupId":"downloads_apache-server","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":0,"downloadURL":null,"name":null,"id":"cf0ca156-5508-47d8-92bf-2f907294e889","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":false},{"scope":null,"version":"1.3","artifactGroupId":"downloads_apache-server","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":0,"downloadURL":null,"name":null,"id":"40d27868-bdbd-4af5-bdff-75cadeb122ac","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":false}],"appliesTo":null,"imageURL":null,"licenseId":null,"customerIds":["photon"],"used":false,"name":"Apache Server","id":"downloads_apache-server","displayName":null,"status":null,"description":null,"creationDate":1378898802833,"helpText":null,"system":false},"appliesToTechIds":["tech-php","tech-phpdru6","tech-phpdru7","tech-java-webservice","tech-wordpress","tech-nodejs-webservice","tech-blackberry-hybrid"],"platformTypeIds":["Windows86","Windows64","Windows 786","Windows 764","Server86","Server64","Linux64","Linux86","Mac86","Mac64"],"category":"SERVER","customerIds":null,"used":false,"name":"Apache Server","id":"downloads_apache-server","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":true},{"artifactGroup":{"type":null,"artifactId":"wamp","classifier":null,"packaging":"exe","groupId":"downloads.files","versions":[{"scope":null,"version":"2.1e","artifactGroupId":"downloads_wamp","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":20608447,"downloadURL":"http://172.16.17.226:8080/repository/content/groups/public//downloads/files/wamp/2.1e/wamp-2.1e.exe","name":null,"id":"b9b344ed-7cc6-472a-a8d8-bf8f842eac9c","displayName":null,"status":null,"description":null,"creationDate":1356506210752,"helpText":null,"system":false}],"appliesTo":null,"imageURL":null,"licenseId":null,"customerIds":["photon"],"used":false,"name":"Wamp","id":"downloads_wamp","displayName":null,"status":null,"description":null,"creationDate":1378898802817,"helpText":null,"system":false},"appliesToTechIds":["tech-php","tech-phpdru6","tech-phpdru7","tech-wordpress"],"platformTypeIds":["Windows86","Windows64","Windows 786","Windows 764","Server86","Server64"],"category":"SERVER","customerIds":null,"used":false,"name":"Wamp","id":"downloads_wamp","displayName":null,"status":null,"description":null,"creationDate":1350451950046,"helpText":null,"system":true},{"artifactGroup":{"type":null,"artifactId":"xamp","classifier":null,"packaging":"zip","groupId":"downloads.files","versions":[{"scope":null,"version":"1.7.4","artifactGroupId":"downloads_xamp","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":66282028,"downloadURL":"http://172.16.17.226:8080/repository/content/groups/public//downloads/files/xamp/1.7.4/xamp-1.7.4.zip","name":null,"id":"346a356e-f128-4549-b4a1-acf6289cd540","displayName":null,"status":null,"description":null,"creationDate":1350451950390,"helpText":null,"system":false}],"appliesTo":null,"imageURL":null,"licenseId":null,"customerIds":["photon"],"used":false,"name":"Xamp","id":"downloads_xamp","displayName":null,"status":null,"description":null,"creationDate":1378898802833,"helpText":null,"system":false},"appliesToTechIds":["tech-php","tech-phpdru6","tech-phpdru7","tech-wordpress"],"platformTypeIds":["Windows86","Windows64","Windows 786","Windows 764","Server86","Server64","Linux64","Linux86","Mac86","Mac64"],"category":"SERVER","customerIds":null,"used":false,"name":"Xamp","id":"downloads_xamp","displayName":null,"status":null,"description":null,"creationDate":1350451950390,"helpText":null,"system":true}],"status":"success"});
			  }
			});
			
			$.mockjax({
			  url: commonVariables.webserviceurl+"appConfig/list?techId=tech-phpdru7&customerId=photon&type=DATABASE&platform=Windows64&userId=admin",
			  type: "GET",
			  dataType: "json",
			  contentType: "application/json",
			  status: 200,
			  response : function() {
				  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR300001","data":[{"artifactGroup":{"type":null,"artifactId":"","classifier":null,"packaging":"","groupId":"","versions":[{"scope":null,"version":"10","artifactGroupId":"downloads_db2","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":0,"downloadURL":null,"name":null,"id":"3e49bbaf-79f8-41d0-af71-190e094a1b06","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":false},{"scope":null,"version":"9.7","artifactGroupId":"downloads_db2","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":0,"downloadURL":null,"name":null,"id":"69920b2f-ec04-44d0-86fa-f6e5644202c8","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":false},{"scope":null,"version":"9.5","artifactGroupId":"downloads_db2","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":0,"downloadURL":null,"name":null,"id":"1ba113cc-0ec2-4ac8-beee-a1af4503ad66","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":false},{"scope":null,"version":"9","artifactGroupId":"downloads_db2","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":0,"downloadURL":null,"name":null,"id":"f58b262c-e7fb-438f-b731-a3f09c4f71a2","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":false}],"appliesTo":null,"imageURL":null,"licenseId":null,"customerIds":["photon"],"used":false,"name":"DB2","id":"downloads_db2","displayName":null,"status":null,"description":null,"creationDate":1378898803286,"helpText":null,"system":false},"appliesToTechIds":["tech-php","tech-phpdru6","tech-phpdru7","tech-java-webservice","tech-html5-mobile-widget","tech-html5-jquery-widget","tech-html5-widget","tech-wordpress","tech-nodejs-webservice","tech-html5-jquery-mobile-widget","tech-html5","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","62f5160b-2932-42fa-82d3-3308eddfd038"],"platformTypeIds":["Windows86","Windows64","Windows 786","Windows 764","Server86","Server64","Linux64","Linux86","Mac86","Mac64"],"category":"DATABASE","customerIds":null,"used":false,"name":"DB2","id":"downloads_db2","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":true},{"artifactGroup":{"type":null,"artifactId":"mongodb","classifier":null,"packaging":"zip","groupId":"downloads.files","versions":[{"scope":null,"version":"2.2.0","artifactGroupId":"downloads_mongodb","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":57131900,"downloadURL":"http://172.16.17.226:8080/repository/content/groups/public//downloads/files/mongodb/2.2.0/mongodb-2.2.0.zip","name":null,"id":"cab759e6-db33-4e72-abe5-94331a3f05b1","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":false}],"appliesTo":null,"imageURL":null,"licenseId":null,"customerIds":["photon"],"used":false,"name":"MongoDB","id":"downloads_mongodb","displayName":null,"status":null,"description":null,"creationDate":1378898803302,"helpText":null,"system":false},"appliesToTechIds":["tech-php","tech-phpdru6","tech-phpdru7","tech-java-webservice","tech-html5-mobile-widget","tech-html5-jquery-widget","tech-html5-widget","tech-wordpress","tech-nodejs-webservice","tech-html5-jquery-mobile-widget","tech-html5","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","62f5160b-2932-42fa-82d3-3308eddfd038"],"platformTypeIds":["Windows86","Windows64","Windows 786","Windows 764","Server86","Server64","Linux64","Linux86","Mac86","Mac64"],"category":"DATABASE","customerIds":null,"used":false,"name":"MongoDB","id":"downloads_mongodb","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":true},{"artifactGroup":{"type":null,"artifactId":"","classifier":null,"packaging":"zip","groupId":"","versions":[{"scope":null,"version":"2012","artifactGroupId":"downloads_mssql","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":0,"downloadURL":null,"name":null,"id":"6e56fbfa-7047-435a-9dab-111fb0281b55","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":false},{"scope":null,"version":"2008 R2","artifactGroupId":"downloads_mssql","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":0,"downloadURL":null,"name":null,"id":"cc09956e-b9e8-4693-bde1-3b79b8e594e4","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":false},{"scope":null,"version":"2008","artifactGroupId":"downloads_mssql","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":0,"downloadURL":null,"name":null,"id":"f86c105c-8d10-4137-8774-f18b4d7c416a","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":false},{"scope":null,"version":"2005","artifactGroupId":"downloads_mssql","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":0,"downloadURL":null,"name":null,"id":"0df8bdde-3d5e-42d4-9c0c-54d51fc9e6da","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":false}],"appliesTo":null,"imageURL":null,"licenseId":null,"customerIds":["photon"],"used":false,"name":"MsSQL","id":"downloads_mssql","displayName":null,"status":null,"description":null,"creationDate":1378898803333,"helpText":null,"system":false},"appliesToTechIds":["tech-php","tech-phpdru6","tech-phpdru7","tech-java-webservice","tech-html5-mobile-widget","tech-html5-jquery-widget","tech-html5-widget","tech-wordpress","tech-nodejs-webservice","tech-html5-jquery-mobile-widget","tech-html5","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","62f5160b-2932-42fa-82d3-3308eddfd038"],"platformTypeIds":["Windows86","Windows64","Windows 786","Windows 764","Server86","Server64","Linux64","Linux86","Mac86","Mac64"],"category":"DATABASE","customerIds":null,"used":false,"name":"MSSQL","id":"downloads_mssql","displayName":null,"status":null,"description":null,"creationDate":1350451950468,"helpText":null,"system":true},{"artifactGroup":{"type":null,"artifactId":"mysql","classifier":null,"packaging":"zip","groupId":"downloads.files","versions":[{"scope":null,"version":"5.5.1","artifactGroupId":"downloads_mysql","dependencyIds":null,"appliesTo":null,"used":false,"fileSize":60578865,"downloadURL":"http://172.16.17.226:8080/repository/content/groups/public//downloads/files/mysql/5.5.1/mysql-5.5.1.zip","name":null,"id":"26bb9f28-e847-4099-b255-429706ceb7b9","displayName":null,"status":null,"description":null,"creationDate":1350451950406,"helpText":null,"system":false}],"appliesTo":null,"imageURL":null,"licenseId":null,"customerIds":["photon"],"used":false,"name":"MySQL","id":"downloads_mysql","displayName":null,"status":null,"description":null,"creationDate":1378898803318,"helpText":null,"system":false},"appliesToTechIds":["tech-php","tech-phpdru6","tech-phpdru7","tech-java-webservice","tech-html5-mobile-widget","tech-html5-jquery-widget","tech-html5-widget","tech-wordpress","tech-nodejs-webservice","tech-html5-jquery-mobile-widget","tech-html5","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","62f5160b-2932-42fa-82d3-3308eddfd038"],"platformTypeIds":["Windows86","Windows64","Windows 786","Windows 764","Server86","Server64","Linux64","Linux86","Mac86","Mac64"],"category":"DATABASE","customerIds":null,"used":false,"name":"MYSQL","id":"downloads_mysql","displayName":null,"status":null,"description":null,"creationDate":1350451950406,"helpText":null,"system":true}],"status":"success"});
			  }
			});
			
			$.mockjax({
			  url: commonVariables.webserviceurl+"appConfig/webservices?userId=admin",
			  type: "GET",
			  dataType: "json",
			  contentType: "application/json",
			  status: 200,
			  response : function() {
				  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR300001","data":[{"version":"1.0","appliesToTechs":["tech-android-native","tech-android-hybrid","tech-html5-mobile-widget","tech-html5-jquery-widget","tech-html5-widget","tech-iphone-hybrid","tech-iphone-native"],"name":"REST/JSON","id":"restjson","displayName":null,"status":null,"description":"REST JSON web services","creationDate":1349094622093,"helpText":null,"system":true},{"version":"1.0","appliesToTechs":["tech-android-native","tech-android-hybrid","tech-html5-mobile-widget","tech-html5-jquery-widget","tech-html5-widget","tech-iphone-hybrid","tech-iphone-native"],"name":"REST/XML","id":"restxml","displayName":null,"status":null,"description":"REST XML web services","creationDate":1349094622093,"helpText":null,"system":true},{"version":"2.0","appliesToTechs":["tech-android-native","tech-android-hybrid","tech-html5-mobile-widget","tech-html5-jquery-widget","tech-html5-widget","tech-iphone-hybrid","tech-iphone-native"],"name":"JMS","id":"jms","displayName":null,"status":null,"description":"Java Message Service","creationDate":1349094622000,"helpText":null,"system":true},{"version":"1.2","appliesToTechs":["tech-android-native","tech-android-hybrid","tech-html5-mobile-widget","tech-html5-jquery-widget","tech-html5-widget","tech-iphone-hybrid","tech-iphone-native"],"name":"SOAP 1.2","id":"saop2","displayName":null,"status":null,"description":"SOAP web services","creationDate":1349094622000,"helpText":null,"system":true},{"version":"1.1","appliesToTechs":["tech-android-native","tech-android-hybrid","tech-html5-mobile-widget","tech-html5-jquery-widget","tech-html5-widget","tech-iphone-hybrid","tech-iphone-native"],"name":"SOAP 1.1","id":"saop1","displayName":null,"status":null,"description":"SOAP web services","creationDate":1349094622000,"helpText":null,"system":true}],"status":"success"});
				  
			  }
			});
			
			$.mockjax({
			  url: commonVariables.webserviceurl+"appConfig/functionalFrameworks?techId=tech-phpdru7&userId=admin",
			  type: "GET",
			  dataType: "json",
			  contentType: "application/json",
			  status: 200,
			  response : function() {
				  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR300001","data":[{"functionalFrameworks":[{"versions":["2.30"],"funcFrameworkProperties":[{"techId":"tech-phpdru7","testDir":"/test/functional","testReportDir":"/test/functional/target/surefire-reports","testcasePath":"/testcase","testsuiteXpathPath":"/testsuite","adaptConfigPath":"/test/functional/src/main/resources/phresco-env-config.xml"}],"groupIds":["e9a72f4d-4d27-4a2f-84f7-fa975f08ebc7","cac095c9-911a-4d8b-8613-65a994ed3a67","a62bd26f-4948-493d-a9eb-0dbf3dfbbe15","a753ffb0-f59e-4b8f-9d93-38a0b4741769"],"name":"webdriver","id":"bf0170f7-2f03-44be-8f68-95c562347816","displayName":"WebDriver","status":null,"description":null,"creationDate":1367938187281,"helpText":null,"system":false}],"techIds":["tech-php","tech-phpdru7","tech-phpdru6","tech-wordpress"],"name":"PhPUnit","id":"e9a72f4d-4d27-4a2f-84f7-fa975f08ebc7","displayName":null,"status":null,"description":null,"creationDate":1375093744812,"helpText":null,"system":false},{"functionalFrameworks":[{"versions":["2.30"],"funcFrameworkProperties":[{"techId":"tech-phpdru7","testDir":"/test/functional","testReportDir":"/test/functional/target/surefire-reports","testcasePath":"/testcase","testsuiteXpathPath":"/testsuite","adaptConfigPath":"/test/functional/src/main/resources/phresco-env-config.xml"}],"groupIds":["e9a72f4d-4d27-4a2f-84f7-fa975f08ebc7","cac095c9-911a-4d8b-8613-65a994ed3a67","a62bd26f-4948-493d-a9eb-0dbf3dfbbe15","a753ffb0-f59e-4b8f-9d93-38a0b4741769"],"name":"webdriver","id":"bf0170f7-2f03-44be-8f68-95c562347816","displayName":"WebDriver","status":null,"description":null,"creationDate":1367938187281,"helpText":null,"system":false},{"versions":["1.3.1"],"funcFrameworkProperties":[{"techId":"tech-phpdru7","testDir":"/test/functional","testReportDir":"/test/functional/target/surefire-reports","testcasePath":"/testcase","testsuiteXpathPath":"/testsuite","adaptConfigPath":"/test/functional/src/main/resources/phresco-env-config.xml"}],"groupIds":["cac095c9-911a-4d8b-8613-65a994ed3a67"],"name":"cucumber","id":"6ea3daf7-d911-4255-93e4-aacaf1dedb99","displayName":"Cucumber","status":null,"description":null,"creationDate":1367938241000,"helpText":null,"system":false}],"techIds":["tech-php","tech-phpdru7","tech-phpdru6","","tech-wordpress","tech-nodejs-webservice","tech-java-webservice","tech-html5-jquery-widget","tech-html5-mobile-widget","tech-html5","tech-html5-jquery-mobile-widget","tech-android-native","tech-android-hybrid","tech-dotnet","tech-java-standalone"],"name":"Junit","id":"cac095c9-911a-4d8b-8613-65a994ed3a67","displayName":null,"status":null,"description":null,"creationDate":1375094549718,"helpText":null,"system":false},{"functionalFrameworks":[{"versions":["2.30"],"funcFrameworkProperties":[{"techId":"tech-phpdru7","testDir":"/test/functional","testReportDir":"/test/functional/target/surefire-reports","testcasePath":"/testcase","testsuiteXpathPath":"/testsuite","adaptConfigPath":"/test/functional/src/main/resources/phresco-env-config.xml"}],"groupIds":["a62bd26f-4948-493d-a9eb-0dbf3dfbbe15"],"name":"grid","id":"e060a07a-c845-47eb-a82d-da85df167e63","displayName":"Grid","status":null,"description":null,"creationDate":1367938113093,"helpText":null,"system":false},{"versions":["2.30"],"funcFrameworkProperties":[{"techId":"tech-phpdru7","testDir":"/test/functional","testReportDir":"/test/functional/target/surefire-reports","testcasePath":"/testcase","testsuiteXpathPath":"/testsuite","adaptConfigPath":"/test/functional/src/main/resources/phresco-env-config.xml"}],"groupIds":["e9a72f4d-4d27-4a2f-84f7-fa975f08ebc7","cac095c9-911a-4d8b-8613-65a994ed3a67","a62bd26f-4948-493d-a9eb-0dbf3dfbbe15","a753ffb0-f59e-4b8f-9d93-38a0b4741769"],"name":"webdriver","id":"bf0170f7-2f03-44be-8f68-95c562347816","displayName":"WebDriver","status":null,"description":null,"creationDate":1367938187281,"helpText":null,"system":false}],"techIds":["tech-php","tech-phpdru7","tech-phpdru6","","tech-wordpress","tech-nodejs-webservice","tech-java-webservice","tech-html5-jquery-widget","tech-html5-mobile-widget","tech-html5","tech-html5-jquery-mobile-widget","tech-sharepoint"],"name":"TestNG","id":"a62bd26f-4948-493d-a9eb-0dbf3dfbbe15","displayName":null,"status":null,"description":null,"creationDate":1375094850312,"helpText":null,"system":false},{"functionalFrameworks":[{"versions":["2.1.0"],"funcFrameworkProperties":[{"techId":"tech-phpdru7","testDir":"/test/functional","testReportDir":"/test/functional/target","testcasePath":"/testcase","testsuiteXpathPath":"/testsuite","adaptConfigPath":"/test/functional/resources/phresco-env-config.xml"}],"groupIds":["2f58209b-86a9-4180-bd59-8cfd22b73bc4"],"name":"capybara","id":"8fed7cc4-a992-49e1-b719-1e1b19daf457","displayName":"Capybara","status":null,"description":null,"creationDate":1367938011546,"helpText":null,"system":false}],"techIds":["tech-php","tech-phpdru7","tech-phpdru6","","tech-wordpress","tech-nodejs-webservice","tech-java-webservice","tech-html5-jquery-widget","tech-html5-mobile-widget","tech-html5","tech-html5-jquery-mobile-widget","tech-android-native","tech-android-hybrid","tech-iphone-native","tech-iphone-hybrid"],"name":"Cucumber","id":"2f58209b-86a9-4180-bd59-8cfd22b73bc4","displayName":null,"status":null,"description":null,"creationDate":1375094946250,"helpText":null,"system":false}],"status":"success"});
				  
			  }
			});
			
			$.mockjax({
//				http://localhost:8234/framework/rest/api/util/techOptions?userId=admin&techId=tech-phpdru7&_=1378893014781
			  url: commonVariables.webserviceurl+"util/techOptions?userId=admin&techId=tech-phpdru7",
			  type: "GET",
			  dataType: "json",
			  contentType: "application/json",
			  status: 200,
			  response : function() {
				  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR300002","data":["Build","Deploy","Unit_Test","Functional_Test","Load_Test","Reports","CI","Code","Performance_Test","Embed_Application","Manual_Test"],"status":"success"});
				  
			  }
			});
			
			commonVariables.api.localVal.setSession("appDirName" , "PhpDru7");
					
			var editAplnContent = new Clazz.com.components.application.js.Application();	
			Clazz.navigationController.push(editAplnContent, commonVariables.animation);
			editAplnContent.appDirName = "PhpDru7";			
				setTimeout(function() {
					start();
					var output = $("input[name=appName]").val();
					equal("PhpDru7",output,"Application page rendered successfully");
					self.validationcheck(application);
				}, 4000);
			});
		},
		
		validationcheck : function(application) {
			var self = this;
			asyncTest("Test for checking Validation", function() {
				$("input[name='appCode']").bind('input');	
				$("input[name='appDirName']").bind('input');
				$("input[name='appName']").val('');
				$("#updatebutton").click();
				$("input[name='appName']").val('a');
				$("input[name='appCode']").val('');
				$("#updatebutton").click();
				$("input[name='appName']").val('a');
				$("input[name='appCode']").val('b');
				$("input[name='appDirName']").val('');
				$("#updatebutton").click();
				setTimeout(function() {
					start();
					var a = $("input[name='appName']").attr('class');
					var b = $("input[name='appCode']").attr('class');
					var c = $("input[name='appDirName']").attr('class');
					equal('errormessage', a, "Appname Validation done successfully");
					equal('errormessage', b, "Appcode Validation done successfully");
					equal('errormessage', c, "AppDirName Validation done successfully");
					self.updateapplication(application);
				}, 3000);
			});
		},
		
		updateapplication : function(application) {
			var self = this;
			asyncTest("Test for Update Application event", function() {
				$.mockjax({
//					http://localhost:2468/framework/rest/api/project/updateApplication?userId=admin&oldAppDirName=PhpDru7&customerId=photon
				  url: commonVariables.webserviceurl+"project/updateApplication?userId=admin&oldAppDirName=PhpDru7&customerId=photon",
				  type: "PUT",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200008","data":{"version":"1.0","modules":null,"pomFile":null,"code":"PhpDru7","appDirName":"PhpDru7","techInfo":{"version":"7.8","appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"PHP","techVersions":null,"customerIds":null,"used":false,"name":"php-Drupal7","id":"tech-phpdru7","displayName":null,"status":null,"description":null,"creationDate":1378807347000,"helpText":null,"system":false},"functionalFramework":null,"selectedServers":[],"selectedDatabases":[],"selectedModules":["63e031f4-808f-4a5a-9354-f9aedc70bd0b","2e35e54c-894c-4a8f-adfa-adc92671648b","9a54f56b-5161-490f-8985-514f7946632a","ea7cb09e-6dc1-49e1-b5ee-d148362dec11","55f384ef-3718-4f12-b4ee-4e41ff9ef6ce"],"selectedJSLibs":[],"selectedComponents":[],"selectedWebservices":[],"functionalFrameworkInfo":{"version":"2.30","frameworkIds":"bf0170f7-2f03-44be-8f68-95c562347816","frameworkGroupId":"e9a72f4d-4d27-4a2f-84f7-fa975f08ebc7","name":null,"id":"3af5ac38-38db-4e3d-b23d-2abf11af2bfb","displayName":null,"status":null,"description":null,"creationDate":1378894142593,"helpText":null,"system":false},"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":"a61453bc-bb38-4b26-b7c5-23973d2aa0e6","phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":true,"customerIds":null,"used":false,"name":"PhpDru7","id":"2159a829-9316-4351-ada1-0098bc6712a1","displayName":null,"status":null,"description":"test","creationDate":1378894142593,"helpText":null,"system":false},"status":"success"});
					  
					}
				});
					$("input[name='appCode']").val('PhpDru7');
					$("input[name='appDirName']").val('PhpDru7');
					$("input[name='appVersion']").val('1.0');
					$("input[name='appName']").val('PhpDru7');
					$("#appDesc").val('test');
//					$("select[name=func_framework]").val('e9a72f4d-4d27-4a2f-84f7-fa975f08ebc7');
					$("select[name=func_framework]").selectpicker('val', 'e9a72f4d-4d27-4a2f-84f7-fa975f08ebc7');
//					$("select[name=func_framework_tools]").val('bf0170f7-2f03-44be-8f68-95c562347816');
					$("select[name=func_framework_tools]").selectpicker('val', 'bf0170f7-2f03-44be-8f68-95c562347816');
//					$("select[name=tools_version]").val('2.30');
					$("select[name=tools_version]").selectpicker('val', '2.30');
					$("#updatebutton").click();
					setTimeout(function() {
						start();
						output = $("#appDesc").val();
						equal('test', output, "Application updated successfully");
						self.serverdbchangefunction(application);
					}, 3000);
			});
		}, 
		
		serverdbchangefunction : function(application) {
			var self = this;
			asyncTest("Test for Change event", function() {
				$("select[name='appServers']").selectpicker('val', 'downloads_apache-server');
				$("select[name='appServers']").change();
				$("select[name='databases']").selectpicker('val', 'downloads_mongodb');
				$("select[name='databases']").change();
				$("select[name='func_framework']").selectpicker('val', 'e9a72f4d-4d27-4a2f-84f7-fa975f08ebc7');
				$("select[name='func_framework']").change();
				$("select[name='func_framework_tools']").selectpicker('val', 'bf0170f7-2f03-44be-8f68-95c562347816');
				$("select[name='func_framework_tools']").change();
				setTimeout(function() {
					start();
					serverversion = $("select.server_version").find("option:contains(2.3)").text();
					dbversion = $(".db_version").find("option:contains(2.2.0)").text();
					functionaltools = $(".tools").find("option:contains(WebDriver)").text();
					functionalversion = $(".tools_version").find("option:contains(2.30)").text();
					equal('2.3', serverversion, "Server changed successfully");
					equal('2.2.0', dbversion, "Database changed successfully");
					equal('WebDriver',functionaltools, "Functional framework changed successfully");
					equal('2.30',functionalversion, "Functional Tools changed successfully");
					self.removeserver(application);
				}, 3000);
			});
		},
		
		removeserver : function(application) {
			var self = this;
			asyncTest("Test for Remove Server event", function() {
				$("img[name='close']").attr('id','servers');
				$("img[name='close']").click();
				setTimeout(function() {
					start();
					output = $('tr[name=servers]').css('display');
					equal('none', output, "Server removed successfully");
					self.addremovedlayer(application);
				}, 3000);
			});
		},
		
		addremovedlayer : function(application) {
			var self = this;
			asyncTest("Test for Adding Removed Layer", function() {
				$(".content_end input").attr('name','servers');
				$(".content_end input").click();
				setTimeout(function() {
					start();
					output = $('tr[name=servers]').css('display');
					equal('table-row', output, "Removed Layer added successfully");
					self.removedatabase(application);
				}, 3000);
			});
		},
		
		removedatabase : function(application) {
			var self = this;
			asyncTest("Test for Remove Database event", function() {
				$("img[name='close']").attr('id','database');
				$("img[name='close']").click();
				setTimeout(function() {
					start();
					output = $('tr[name=database]').css('display');
					equal('none', output, "Database removed successfully");
					self.addserver(application);
				}, 3000);
			});
		},
		

		addserver : function(application) {
			var self = this;
			asyncTest("Test for Add Server event", function() {
				$("a[name=addServer]").click();
				setTimeout(function() {
					start();
					output = $('tr.servers').length;
					equal('2', output, "Server added successfully");
					self.adddatabase(application);
				}, 3000);
			});
		},
		
		adddatabase : function(application) {
			var self = this;
			asyncTest("Test for Add Database event", function() {
				$("a[name=addDatabase]").click();
				setTimeout(function() {
					start();
					output = $('tr.database').length;
					equal('2', output, "Database added successfully");
					self.removeserverdatabase(application);
				}, 3000);
			});
		},
		
		 removeserverdatabase : function(application) {
			var self = this;
			asyncTest("Test for Remove Server/Database event", function() {
			
				$("a[name=removeServer]").click();
				$("a[name=removeDatabase]").click();
				setTimeout(function() {
					start();
					serverlength = $('tr.servers').length;
					databaselength = $('tr.database').length;
					equal('0', serverlength, "Server removed successfully");
					equal('0', databaselength, "Database removed successfully");
					//self.updateapplication(application);
				}, 3000);
			});
		}, 
		
	}

});	
		
		
		
			
	