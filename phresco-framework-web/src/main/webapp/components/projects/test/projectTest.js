define(["projects/addproject", "projects/editproject"], function(addProject, editProject) {

	return { runTests: function() {
		/**
		 * Test that the setMainContent method sets the text in the MyCart-widget
		 */
		module("AddProject.js");
		
		var addproject = new addProject(), editproject = new editProject(), self = this, createProject;
		
			asyncTest("Add Project- App Layer UI Test", function() {
				
				self.appTypes = $.mockjax({
				  url: commonVariables.webserviceurl + 'technology/apptypes?userId=admin&customerId=photon',
				  type:'GET',
				  contentType: 'application/json',
				  status: 200,
				  response: function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200024","data":[{"techGroups":[{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":["1.0"],"customerIds":["photon"],"used":false,"name":"testtest","id":"c81e72bb-bfa4-42d3-8a69-3485f04db2a0","displayName":null,"status":null,"description":null,"creationDate":1380098028355,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":["2"],"customerIds":["photon"],"used":false,"name":"sdfsdfsdf","id":"ec6e5728-d23f-4906-8925-e81153119a23","displayName":null,"status":null,"description":null,"creationDate":1380115947286,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":["1.0"],"customerIds":["photon"],"used":false,"name":"test","id":"fef7c6df-38d2-4471-9669-db5fccbc1d32","displayName":null,"status":null,"description":null,"creationDate":1380206698790,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":["0.8.22","  0.8.20","  0.10.9","  0.10.8","  0.10.7","  0.10.6","  0.10.5","  0.10.4","  0.10.3","  0.10.2","  0.10.1","  0.10.0"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","95a1ca31-b214-40e7-aa72-82b52d50677f","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"status":null,"description":null,"creationDate":1377532270437,"helpText":null,"system":false}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d","95a1ca31-b214-40e7-aa72-82b52d50677f","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"NodeJs","id":"nodejs","displayName":null,"status":null,"description":null,"creationDate":1350460943312,"helpText":null,"system":true},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"version":null,"multiModule":false,"appTypeId":"app-layer","techGroupId":"java","techVersions":["1.6"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"Spring Swagger Splunk","id":"62f5160b-2932-42fa-82d3-3308eddfd038","displayName":null,"status":null,"description":null,"creationDate":1377092746000,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":["1.0"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"OSGI Fragment","id":"b5851876-f4f0-4573-9344-7969ad759448","displayName":null,"status":null,"description":null,"creationDate":1379584289781,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":["1.7"," 1.6"," 1.5"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"status":null,"description":null,"creationDate":1377532243796,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":["1.0"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"OSGI Bundle","id":"e3b00f94-0d85-4084-b0b8-a76ee4de0b5d","displayName":null,"status":null,"description":null,"creationDate":1379583706906,"helpText":null,"system":false},{"version":null,"multiModule":true,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":["1.7","   1.6","   1.5"],"customerIds":["photon","1c65af0e-6a45-4841-abe7-7d1a07047b1d","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"java-standalone","id":"tech-java-standalone","displayName":null,"status":null,"description":null,"creationDate":1380277998750,"helpText":null,"system":false},{"version":null,"multiModule":true,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":["1.0"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"Jersey-OSGI","id":"8892cc43-c2ab-46a4-be5d-d4752d6dd418","displayName":null,"status":null,"description":null,"creationDate":1380611414346,"helpText":null,"system":false}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","a41228d1-5e74-4c7e-ac2e-b80d946376fc","170a7a47-95f7-4829-84dc-e5cffab0cd7a","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"Java","id":"java","displayName":null,"status":null,"description":null,"creationDate":1350460943312,"helpText":null,"system":true},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"dotnet","techVersions":["4.0"," 3.5"," 3.0"," 2.0"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":".net-iis","id":"tech-dotnet","displayName":null,"status":null,"description":null,"creationDate":1377532161515,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"dotnet","techVersions":["3.5"," 3.0"," 2.0"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":".net-SharePoint","id":"tech-sharepoint","displayName":null,"status":null,"description":null,"creationDate":1377532410562,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"dotnet","techVersions":["3.5"," 3.0"," 2.0"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":".net-Sitecore","id":"tech-sitecore","displayName":null,"status":null,"description":null,"creationDate":1377531808546,"helpText":null,"system":false}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"Dot Net","id":"dotnet","displayName":null,"status":null,"description":null,"creationDate":1350460943000,"helpText":null,"system":true},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["5.4.x","  5.3.x","  5.2.x","  5.1.x","  5.0.x"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"php-raw","id":"tech-php","displayName":null,"status":null,"description":null,"creationDate":1377532307546,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["7.8"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"php-Drupal7","id":"tech-phpdru7","displayName":null,"status":null,"description":null,"creationDate":1377532340640,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["6.3"," 6.25"," 6.19"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"php-Drupal6","id":"tech-phpdru6","displayName":null,"status":null,"description":null,"creationDate":1377532328968,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["3.4.2"," 3.3.1"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"php-Wordpress","id":"tech-wordpress","displayName":null,"status":null,"description":null,"creationDate":1377532355343,"helpText":null,"system":false}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","c32171c4-90e5-4ede-9c51-0ff370eae974","a145ab5e-5eb6-4323-9c88-db61a79c9f3c","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"PHP","id":"php","displayName":null,"status":null,"description":null,"creationDate":1350460943000,"helpText":null,"system":true}],"customerIds":["photon"],"used":false,"name":"Middle Tier","id":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","displayName":null,"status":null,"description":null,"creationDate":1350460943000,"helpText":null,"system":true},{"techGroups":[{"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techInfos":[{"version":null,"multiModule":true,"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techGroupId":"1795d032-466f-4866-88b2-e5ce0c332871","techVersions":["5.6"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"AEM CQ","id":"0101a91b-559f-4b1b-9e30-a24ed5760d02","displayName":null,"status":null,"description":null,"creationDate":1380883079383,"helpText":null,"system":false}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"CQ5","id":"1795d032-466f-4866-88b2-e5ce0c332871","displayName":null,"status":null,"description":null,"creationDate":1350460943000,"helpText":null,"system":true}],"customerIds":["photon"],"used":false,"name":"CMS","id":"99d55693-dacd-4f77-994a-f02a66176ff9","displayName":null,"status":null,"description":null,"creationDate":1350460943000,"helpText":null,"system":true},{"techGroups":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"windows","techVersions":["1.0"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","7dc021ca-db61-4ea2-952f-92b61cb13a72","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"desktop-app-OSX","id":"49ea6a1c-ad68-4971-995c-0f26017abb3b","displayName":null,"status":null,"description":null,"creationDate":1377530944593,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"windows","techVersions":["8.x","  7.x"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"mobile-app-winPhone8","id":"tech-win-phone","displayName":null,"status":null,"description":null,"creationDate":1377533002328,"helpText":null,"system":false},{"version":null,"multiModule":true,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"windows","techVersions":["7.x"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"mobile-app-winMetro","id":"tech-win-metro","displayName":null,"status":null,"description":null,"creationDate":1380880565880,"helpText":null,"system":false}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","7dc021ca-db61-4ea2-952f-92b61cb13a72","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"Windows","id":"windows","displayName":null,"status":null,"description":null,"creationDate":1350460943390,"helpText":null,"system":true},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":["4.2","4.1.2","4.1","4.0.3","2.3.3","2.2","2.1_r1","1.6"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","7dc021ca-db61-4ea2-952f-92b61cb13a72","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"mobile-app-android","id":"tech-android-native","displayName":null,"status":null,"description":null,"creationDate":1377532115218,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":["4.2","4.1.2","4.1","4.0.3","2.3.3","2.2","2.1_r1","1.6"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","7dc021ca-db61-4ea2-952f-92b61cb13a72","95a1ca31-b214-40e7-aa72-82b52d50677f","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"hybrid-app-android","id":"tech-android-hybrid","displayName":null,"status":null,"description":null,"creationDate":1377531058656,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":["4.2","4.1.2","4.1","4.0.3","2.3.3","2.2","2.1_r1","1.6"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","7dc021ca-db61-4ea2-952f-92b61cb13a72","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"library-app-android","id":"tech-android-library","displayName":null,"status":null,"description":null,"creationDate":1377531545546,"helpText":null,"system":false}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","c32171c4-90e5-4ede-9c51-0ff370eae974","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","7dc021ca-db61-4ea2-952f-92b61cb13a72","7dc021ca-db61-4ea2-952f-92b61cb13a72","7dc021ca-db61-4ea2-952f-92b61cb13a72","95a1ca31-b214-40e7-aa72-82b52d50677f","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"Android","id":"android","displayName":null,"status":null,"description":null,"creationDate":1350460943000,"helpText":null,"system":true},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","7dc021ca-db61-4ea2-952f-92b61cb13a72","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"library-app-iOS ","id":"tech-iphone-library","displayName":null,"status":null,"description":null,"creationDate":1377531445718,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","7dc021ca-db61-4ea2-952f-92b61cb13a72","95a1ca31-b214-40e7-aa72-82b52d50677f","fd30af28-8b20-4393-84fe-983f8c96b7c2","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"mobile-app-iOS","id":"tech-iphone-native","displayName":null,"status":null,"description":null,"creationDate":1377531595765,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","7dc021ca-db61-4ea2-952f-92b61cb13a72","95a1ca31-b214-40e7-aa72-82b52d50677f","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"hybrid-app-iOS","id":"tech-iphone-hybrid","displayName":null,"status":null,"description":null,"creationDate":1377531462625,"helpText":null,"system":false}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","170a7a47-95f7-4829-84dc-e5cffab0cd7a","a41228d1-5e74-4c7e-ac2e-b80d946376fc","c32171c4-90e5-4ede-9c51-0ff370eae974","05c80933-95d4-46c8-a58d-ceceb4bcce48","0f3d8d9d-a7d0-49b8-b662-2cc25a5ee88b","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","7dc021ca-db61-4ea2-952f-92b61cb13a72","7dc021ca-db61-4ea2-952f-92b61cb13a72","7dc021ca-db61-4ea2-952f-92b61cb13a72","95a1ca31-b214-40e7-aa72-82b52d50677f","95a1ca31-b214-40e7-aa72-82b52d50677f","fd30af28-8b20-4393-84fe-983f8c96b7c2","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"Iphone","id":"iphone","displayName":null,"status":null,"description":null,"creationDate":1350460943000,"helpText":null,"system":true},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["2.0.2"," 2.0.1"," 2.0.0"," 1.7.2"," 1.10.0"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","95a1ca31-b214-40e7-aa72-82b52d50677f","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"mobile-web","id":"tech-html5-jquery-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1377533346750,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["2.0.2"," 2.0.1"," 2.0.0"," 1.7.2"," 1.10.0"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","95a1ca31-b214-40e7-aa72-82b52d50677f","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"responsive-web","id":"tech-html5-jquery-widget","displayName":null,"status":null,"description":null,"creationDate":1377533295015,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["3.9.1"," 3.9.0"," 3.8.1"," 3.8.0"," 3.7.3"," 3.7.2"," 3.7.1"," 3.7.0"," 3.10.3"," 3.10.2"," 3.10.1"," 3.10.0"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"responsive-web-yui","id":"tech-html5","displayName":null,"status":null,"description":null,"creationDate":1377533391328,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["3.9.1"," 3.9.0"," 3.8.1"," 3.8.0"," 3.7.3"," 3.7.2"," 3.7.1"," 3.7.0"," 3.10.3"," 3.10.2"," 3.10.1"," 3.10.0"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"mobile-web-yui","id":"tech-html5-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1377533425406,"helpText":null,"system":false}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","c32171c4-90e5-4ede-9c51-0ff370eae974","05c80933-95d4-46c8-a58d-ceceb4bcce48","0f3d8d9d-a7d0-49b8-b662-2cc25a5ee88b","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","95a1ca31-b214-40e7-aa72-82b52d50677f","95a1ca31-b214-40e7-aa72-82b52d50677f","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"HTML5","id":"html5","displayName":null,"status":null,"description":null,"creationDate":1350460943000,"helpText":null,"system":true},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"bb","techVersions":["7.x "],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"mobile-app-bb10","id":"tech-blackberry-hybrid","displayName":null,"status":null,"description":null,"creationDate":1377533030187,"helpText":null,"system":false}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"Black Berry","id":"bb","displayName":null,"status":null,"description":null,"creationDate":1350460943390,"helpText":null,"system":true}],"customerIds":["photon"],"used":false,"name":"Front End","id":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","displayName":null,"status":null,"description":null,"creationDate":1350460943000,"helpText":null,"system":true}],"status":"success"});
				  }
				});
				
				$("#addproject").click();
				setTimeout(function() {
					start();
					var techOption = $(commonVariables.contentPlaceholder).find("select.frontEnd").find("option:contains(HTML5)").text();
					var weblayerOption = $(commonVariables.contentPlaceholder).find("select.weblayer").find("option:contains(PHP)").text();
					var mobilelayerOption = $(commonVariables.contentPlaceholder).find("select.mobile_layer").find("option:contains(CQ5)").text();
					equal(techOption, "HTML5", "Add Project - Front End UI Tested");
					equal(weblayerOption, "PHP", "Add Project - Middle Tier UI Tested");
					equal(mobilelayerOption, "CQ5", "Add Project - CMS UI Tested");
					self.runDulicateAppCodeValidationTest();
				}, 2500);
			});
		},
		
		runDulicateAppCodeValidationTest : function() {
			var self = this;
			asyncTest("Add Project- Dulicate AppCode Validation Test", function() {
				$("a[name=addApplnLayer]").click();
				$("tr[name=staticApplnLayer]").first().find(".appCodeText").val("appCode1");
				$("tr[name=staticApplnLayer]").last().find(".appCodeText").val("appCode1");
				$("tr[name=staticApplnLayer]").first().find(".appCodeText").blur();
				setTimeout(function() {
					start();
					var errMsg = $("tr[name=staticApplnLayer]").first().find(".appCodeText").attr("placeholder");
					equal(errMsg, "Appcode Already Exists", "Add Project - Dulicate AppCode Validation Tested");
					self.runFrontEndMultiModuleTechGroupDispTest();	
				}, 1500);
			});
		},
		
		runFrontEndMultiModuleTechGroupDispTest : function() {
			var self=this;
			asyncTest("Add Project- Front End Multi-module Technology Group Display Test", function() {
				$("a[name='removeApplnLayer']").last().click();
				$("select[name='frontEnd']").val("windows");
				$("select[name='frontEnd']").change();
				setTimeout(function() {
					start();
					equal($("select[name='frontEnd']").val(), "windows", "Add Project- Front End Multi-module Technology Group Display Tested");
					self.runFrontEndMultiModuleTechDispTest();
				}, 2500);
			});
		},
		
		runFrontEndMultiModuleTechDispTest : function() {
			var self=this;
			asyncTest("Add Project- Front End Multi-module Technology Display Test", function() {
				$("select[name='appln_technology']").val('tech-win-metro');
				$("select[name='appln_technology']").change();
				setTimeout(function() {
					start();
					equal($("select[name='appln_technology']").val(), "tech-win-metro", "Add Project- Front End Multi-module Technology Display Tested");
					equal($("select[name='appln_technology'] option:selected").attr("multiModule"), "true", "Add Project- Front End Multi-module Technology Attribute(multiModule) Display Tested");
					equal($("tr[name=staticApplnLayer]").find("input[name=multiModuleBtn]").css("display"), "block", "Add Project- Front End Multi-module Button Display Tested");
					self.runFrontEndMultiModuleBtnClickTest();
				}, 2500);
			});
		},
		
		runFrontEndMultiModuleBtnClickTest : function() {
			var self=this;
			asyncTest("Add Project- Front End Multi-module Button Click Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl + 'technology/techInfo?userId=admin&techId=tech-win-metro',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"sub-modules of technology returned successfully","exception":null,"responseCode":null,"data":[{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","7dc021ca-db61-4ea2-952f-92b61cb13a72","95a1ca31-b214-40e7-aa72-82b52d50677f","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"hybrid-app-iOS","id":"tech-iphone-hybrid","displayName":null,"status":null,"description":null,"creationDate":1377531462625,"helpText":null,"system":false},{"version":null,"multiModule":true,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"windows","techVersions":["7.x"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"mobile-app-winMetro","id":"tech-win-metro","displayName":null,"status":null,"description":null,"creationDate":1380880565880,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"windows","techVersions":["8.x","  7.x"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"mobile-app-winPhone8","id":"tech-win-phone","displayName":null,"status":null,"description":null,"creationDate":1377533002328,"helpText":null,"system":false}],"status":null});
					}
				});
				$("tr[name=staticApplnLayer]").find("input[name=multiModuleBtn]").click();
				
				setTimeout(function() {
					start();
					equal($("tbody.applnlayer").find("tr.multi_module").length, 1, "Add Project - Front End Multi-module Button Click Tested");
					self.runFrontEndAddModuleClickTest();
				}, 2500);
			});
		},
		
		runFrontEndAddModuleClickTest : function() {
			var self=this;
			asyncTest("Add Project- Front End Add Module Click Test", function() {
				$("tbody.applnlayer").find("tr.multi_module").find(".addDependency").click();
				
				setTimeout(function() {
					start();
					equal($("tbody.applnlayer").find("tr.multi_module").length, 2, "Add Project - Front End Add Module Click Tested");
					self.runFrontEndModuleNameValidationTest();
				}, 2500);
			});
		},
		
		runFrontEndModuleNameValidationTest : function() {
			var self=this;
			asyncTest("Add Project- Front End MultiModule Name Validation Test", function() {
				$("tr[name=weblayer]").find("img[name='close']").click();
				$("tr[name=mobilelayer]").find("img[name='close']").click();
				$("input[name=projectname]").val('Test');
				$("input[name=projectcode]").val('Test1');
				$("input[name=projectversion]").val('1.0');
				$("input[name=projectdescription]").val('Test Description');
				$("#appcode").val("test");
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					var errorMsg = $('tbody.applnlayer').find('tr.multi_module').first().find('.moduleName').attr("placeholder");
					equal(errorMsg, "Enter Module Name", "Add Project- Front End MultiModule Name Validation Tested");
					self.runFrontEndModuleTechValidationTest();
				}, 2500);
			});
		},
		
		runFrontEndModuleTechValidationTest : function() {
			var self=this;
			asyncTest("Add Project- Front End Module Technology Validation Test", function() {
				$("tr[name=weblayer]").find("img[name='close']").click();
				$("tr[name=mobilelayer]").find("img[name='close']").click();
				$("input[name=projectname]").val('Test');
				$("input[name=projectcode]").val('Test1');
				$("input[name=projectversion]").val('1.0');
				$("input[name=projectdescription]").val('Test Description');
				$("#appcode").val("test");
				$('tbody.applnlayer').find('tr.multi_module').first().find('.moduleName').val("module1");
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					var errorMsg = $(".errmsg1").text();
					equal(errorMsg, "Select Technology", "Add Project- Front End Module Technology Validation Tested");
					self.runMiddleTierMultiModuleTechGroupDispTest();
				}, 2500);
			});
		},
		
		runMiddleTierMultiModuleTechGroupDispTest : function() {
			var self=this;
			asyncTest("Add Project- Middle Tier Multi-module Technology Group Display Test", function() {
				$(".flt_left input[name=weblayer]").click();
				$("select[name='weblayer']").val("java");
				$("select[name='weblayer']").change();
				
				setTimeout(function() {
					start();
					equal($("select[name='weblayer']").val(), "java", "Add Project- Middle Tier Multi-module Technology Group Display Tested");
					self.runMiddleTierMultiModuleTechDispTest();
				}, 2500);
			});
		},
		
		runMiddleTierMultiModuleTechDispTest : function() {
			var self=this;
			asyncTest("Add Project- Middle Tier Multi-module Technology Display Test", function() {
				$("select[name='web_widget']").val('tech-java-standalone');
				$("select[name='web_widget']").change();
				
				setTimeout(function() {
					start();
					equal($("select[name='web_widget']").val(), "tech-java-standalone", "Add Project- Middle Tier Multi-module Technology Display Tested");
					equal($("select[name='web_widget'] option:selected").attr("multiModule"), "true", "Add Project- Middle Tier Multi-module Technology Attribute(multiModule) Display Tested");
					equal($("tr[name=staticWebLayer]").find("input[name=multiModuleBtn]").css("display"), "block", "Add Project- Middle Tier Multi-module Button Display Tested");
					self.runMiddleTierMultiModuleBtnClickTest();
				}, 2500);
			});
		},
		
		runMiddleTierMultiModuleBtnClickTest : function() {
			var self=this;
			asyncTest("Add Project- Middle Tier Multi-module Button Click Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl + 'technology/techInfo?userId=admin&techId=tech-java-standalone',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"sub-modules of technology returned successfully","exception":null,"responseCode":null,"data":[{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["2.0.2"," 2.0.1"," 2.0.0"," 1.7.2"," 1.10.0"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","95a1ca31-b214-40e7-aa72-82b52d50677f","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"mobile-web","id":"tech-html5-jquery-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1377533346750,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["2.0.2"," 2.0.1"," 2.0.0"," 1.7.2"," 1.10.0"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","95a1ca31-b214-40e7-aa72-82b52d50677f","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"responsive-web","id":"tech-html5-jquery-widget","displayName":null,"status":null,"description":null,"creationDate":1377533295015,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["3.9.1"," 3.9.0"," 3.8.1"," 3.8.0"," 3.7.3"," 3.7.2"," 3.7.1"," 3.7.0"," 3.10.3"," 3.10.2"," 3.10.1"," 3.10.0"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"mobile-web-yui","id":"tech-html5-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1377533425406,"helpText":null,"system":false},{"version":null,"multiModule":true,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":["1.7","   1.6","   1.5"],"customerIds":["photon","1c65af0e-6a45-4841-abe7-7d1a07047b1d","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"java-standalone","id":"tech-java-standalone","displayName":null,"status":null,"description":null,"creationDate":1380277998750,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":["1.7"," 1.6"," 1.5"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"status":null,"description":null,"creationDate":1377532243796,"helpText":null,"system":false}],"status":null});
					}
				});
				
				$("tr[name=staticWebLayer]").find("input[name=multiModuleBtn]").click();
				
				setTimeout(function() {
					start();
					equal($("tbody.WebLayer").find("tr.multi_module").length, 1, "Add Project - Middle Tier Multi-module Button Click Tested");
					self.runMiddleTierAddModuleClickTest();
				}, 2500);
			});
		},
		
		runMiddleTierAddModuleClickTest : function() {
			var self=this;
			asyncTest("Add Project- Middle Tier Add Module Click Test", function() {
				$(".addDependency").click();
				
				setTimeout(function() {
					start();
					equal($("tbody.WebLayer").find("tr.multi_module").length, 2, "Add Project - Middle Tier Add Module Click Tested");
					self.runMiddleTierModuleNameValidationTest();
				}, 2500);
			});
		},
		
		runMiddleTierModuleNameValidationTest : function() {
			var self=this;
			asyncTest("Add Project- Middle Tier MultiModule Name Validation Test", function() {
				$("tr[name=applicationlayer]").find("img[name='close']").click();
				$("tr[name=mobilelayer]").find("img[name='close']").click();
				$("input[name=projectname]").val('Test');
				$("input[name=projectcode]").val('Test1');
				$("input[name=projectversion]").val('1.0');
				$("input[name=projectdescription]").val('Test Description');
				$("#webappcode").val("test");
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					var errorMsg = $('tbody.WebLayer').find('tr.multi_module').first().find('.moduleName').attr("placeholder");
					equal(errorMsg, "Enter Module Name", "Add Project- Middle Tier MultiModule Name Validation Tested");
					self.runMiddleTierTechValidationTest();
				}, 2500);
			});
		},
		
		runMiddleTierTechValidationTest : function() {
			var self=this;
			asyncTest("Add Project- Middle Tier Technology Validation Test", function() {
				$("tr[name=applicationlayer]").find("img[name='close']").click();
				$("tr[name=mobilelayer]").find("img[name='close']").click();
				$("input[name=projectname]").val('Test');
				$("input[name=projectcode]").val('Test1');
				$("input[name=projectversion]").val('1.0');
				$("input[name=projectdescription]").val('Test Description');
				$("#webappcode").val("test");
				$('tbody.WebLayer').find('tr.multi_module').first().find('.moduleName').val("module1");
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					var errorMsg = $(".errmsg2").text();
					equal(errorMsg, "Select Technology", "Add Project- Middle Tier Technology Validation Tested");
					self.runMiddleTierSubModuleNameEmptyValidationTest();
				}, 2500);
			});
		},
		
		runMiddleTierSubModuleNameEmptyValidationTest : function() {
			var self=this;
			asyncTest("Add Project- Middle Tier Sub Module Name Empty Validation Test", function() {
				$('tbody.WebLayer').find('input[name=subModuleName]').first().blur();
				
				setTimeout(function() {
					start();
					equal($('tbody.WebLayer').find('input[name=subModuleName]').first().attr('placeholder'), "Enter Module Name", "Add Project- Middle Tier Sub Module Name Empty Validation Tested");
					self.runSubModuleNameBlurEventTest();
				}, 2500);
			});
		},
		
		runSubModuleNameBlurEventTest : function() {
			var self=this;
			asyncTest("Add Project- Sub Module Name Blur Event Test", function() {
				$('tbody.WebLayer').find('input[name=subModuleName]').first().val("Module1");
				$('tbody.WebLayer').find('input[name=subModuleName]').last().val("Module2");
				$('tbody.WebLayer').find('input[name=subModuleName]').first().blur();
				$('tbody.WebLayer').find('input[name=subModuleName]').last().blur();
				
				setTimeout(function() {
					start();
					equal($('tbody.WebLayer').find("select.appdependencySelect:last option[value='Module1']").length, 1, "Add Project- Sub Module Name Blur Event Tested");
					self.runModuleDependencyChangeEvent();
				}, 2500);
			});
		},
		
		runModuleDependencyChangeEvent : function() {
			var self=this;
			asyncTest("Add Project- Module Dependency Change Event Test", function() {
				$('tbody.WebLayer').find('input[name=subModuleName]').first().val("Module1");
				$('tbody.WebLayer').find('input[name=subModuleName]').last().val("Module2");
				$('tbody.WebLayer').find("select.appdependencySelect:first option[value='Module2']").attr("selected", "selected");
				$('tbody.WebLayer').find("select.appdependencySelect:last option[value='Module1']").attr("selected", "selected")
				$('tbody.WebLayer').find(".appdependencySelect").first().change();
				
				setTimeout(function() {
					start();
					var hasClass = $('tbody.WebLayer').find(".appdependencySelect").first().parent().parent().find(".appdependencySelect").next().find('button.dropdown-toggle').hasClass("btn-danger");
					equal(hasClass, true, "Add Project- Module Dependency Change Event Tested");
					self.runCMSMultiModuleTechGroupDispTest();
				}, 2500);
			});
		},
		
		runCMSMultiModuleTechGroupDispTest : function() {
			var self=this;
			asyncTest("Add Project- CMS Multi-module Technology Group Display Test", function() {
				$("tr[name=applicationlayer]").find("img[name='close']").click();
				$("tr[name=weblayer]").find("img[name='close']").click();
				$(".flt_left input[name=mobilelayer]").click();
				$("select[name='mobile_layer']").val("1795d032-466f-4866-88b2-e5ce0c332871");
				$("select[name='mobile_layer']").change();
				
				setTimeout(function() {
					start();
					equal($("select[name='mobile_layer']").val(), "1795d032-466f-4866-88b2-e5ce0c332871", "Add Project- CMS Multi-module Technology Group Display Tested");
					self.runCMSMultiModuleTechDispTest();
				}, 2500);
			});
		},
		
		runCMSMultiModuleTechDispTest : function() {
			var self=this;
			asyncTest("Add Project- CMS Multi-module Technology Display Test", function() {
				$("select[name='mobile_types']").val('0101a91b-559f-4b1b-9e30-a24ed5760d02');
				$("select[name='mobile_types']").change();
				
				setTimeout(function() {
					start();
					equal($("select[name='mobile_types']").val(), "0101a91b-559f-4b1b-9e30-a24ed5760d02", "Add Project- CMS Multi-module Technology Display Tested");
					equal($("select[name='mobile_types'] option:selected").attr("multiModule"), "true", "Add Project- CMS Multi-module Technology Attribute(multiModule) Display Tested");
					equal($("tr[name=staticMobileLayer]").find("input[name=multiModuleBtn]").css("display"), "block", "Add Project- CMS Multi-module Button Display Tested");
					self.runCMSMultiModuleBtnClickTest();
				}, 2500);
			});
		},
		
		runCMSMultiModuleBtnClickTest : function() {
			var self=this;
			asyncTest("Add Project- CMS Multi-module Button Click Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl + 'technology/techInfo?userId=admin&techId=0101a91b-559f-4b1b-9e30-a24ed5760d02',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"sub-modules of technology returned successfully","exception":null,"responseCode":null,"data":[{"version":null,"multiModule":true,"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techGroupId":"1795d032-466f-4866-88b2-e5ce0c332871","techVersions":["5.6"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"AEM CQ","id":"0101a91b-559f-4b1b-9e30-a24ed5760d02","displayName":null,"status":null,"description":null,"creationDate":1380883079383,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"bb","techVersions":["7.x "],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"mobile-app-bb10","id":"tech-blackberry-hybrid","displayName":null,"status":null,"description":null,"creationDate":1377533030187,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"windows","techVersions":["8.x","  7.x"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"mobile-app-winPhone8","id":"tech-win-phone","displayName":null,"status":null,"description":null,"creationDate":1377533002328,"helpText":null,"system":false},{"version":null,"multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["3.4.2"," 3.3.1"],"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d","1b5d0bf4-be3f-4d9f-a570-3ba5a7764e17"],"used":false,"name":"php-Wordpress","id":"tech-wordpress","displayName":null,"status":null,"description":null,"creationDate":1377532355343,"helpText":null,"system":false}],"status":null});
					}
				});
				
				$("tr[name=staticMobileLayer]").find("input[name=multiModuleBtn]").click();
				
				setTimeout(function() {
					start();
					equal($("tbody.cmsLayer").find("tr.multi_module").length, 1, "Add Project - CMS Multi-module Button Click Tested");
					self.runCMSAddModuleClickTest();
				}, 2500);
			});
		},
		
		runCMSAddModuleClickTest : function() {
			var self=this;
			asyncTest("Add Project- CMS Add Module Click Test", function() {
				$(".addDependency").click();
				
				setTimeout(function() {
					start();
					equal($("tbody.cmsLayer").find("tr.multi_module").length, 2, "Add Project - CMS Add Module Click Tested");
					self.runCMSModuleNameValidationTest();
				}, 2500);
			});
		},
		
		runCMSModuleNameValidationTest : function() {
			var self=this;
			asyncTest("Add Project- CMS MultiModule Name Validation Test", function() {
				$("tr.webLayer").attr("key", "hidden")
				$("input[name=projectname]").val('Test');
				$("input[name=projectcode]").val('Test1');
				$("input[name=projectversion]").val('1.0');
				$("input[name=projectdescription]").val('Test Description');
				$("#mobileappcode").val("test");
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					var errorMsg = $('tbody.cmsLayer').find('tr.multi_module').first().find('.moduleName').attr("placeholder");
					equal(errorMsg, "Enter Module Name", "Add Project- CMS MultiModule Name Validation Tested");
					self.runCMSTechValidationTest();
				}, 2500);
			});
		},
		
		runCMSTechValidationTest : function() {
			var self=this;
			asyncTest("Add Project- CMS Technology Validation Test", function() {
				$("input[name=projectname]").val('Test');
				$("input[name=projectcode]").val('Test1');
				$("input[name=projectversion]").val('1.0');
				$("input[name=projectdescription]").val('Test Description');
				$("#mobileappcode").val("test");
				$('tbody.cmsLayer').find('tr.multi_module').first().find('.moduleName').val("module1");
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					var errorMsg = $(".errmsg3").text();
					equal(errorMsg, "Select Technology", "Add Project- CMS Technology Validation Tested");
					self.runCMSRemoveModuleClickTest();
				}, 2500);
			});
		},
		
		runCMSRemoveModuleClickTest : function() {
			var self=this;
			asyncTest("Add Project- CMS Remove Module Click Test", function() {
				$('tbody.cmsLayer').find(".removeDependency").last().click();
				
				setTimeout(function() {
					start();
					equal($('tbody.cmsLayer').find("tr.multi_module").length, 1, "Add Project- CMS Remove Module Click Tested");
					self.runCMSModuleTechChangeEvent();
				}, 2500);
			});
		},
		
		runCMSModuleTechChangeEvent : function() {
			var self=this;
			asyncTest("Add Project- CMS Module Technology Change Event Test", function() {
				$('tbody.cmsLayer').find("select[name='technology']").val('0101a91b-559f-4b1b-9e30-a24ed5760d02');
				$('tbody.cmsLayer').find("select[name=technology]").change();
				
				setTimeout(function() {
					start();
					equal($('tbody.cmsLayer').find("select[name=version] option[value='5.6']").length, 1, "Add Project- CMS Module Technology Change Event Tested");
					self.runFromLocalStorageTest();
				}, 2500);
			});
		},
		
		runFromLocalStorageTest : function(){
			var self=this;
			$.mockjaxClear(self.appTypes1);
			asyncTest("Add Project- App Layer UI Test From LocalStorage", function() {
				$(commonVariables.contentPlaceholder).empty();
				commonVariables.api.localVal.deleteSession("Front End");
				commonVariables.api.localVal.deleteSession("Middle Tier");
				commonVariables.api.localVal.deleteSession("CMS");
				$.mockjax({
				  url: commonVariables.webserviceurl + 'technology/apptypes?userId=admin&customerId=photon',
				  type:'GET',
				  contentType: 'application/json',
				  status: 200,
				  response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200024","data":[{"techGroups":[{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"appTypeId":"app-layer","techGroupId":"java","techVersions":["1.6"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377092746000,"helpText":null,"system":false,"name":"Spring Swagger Splunk","id":"62f5160b-2932-42fa-82d3-3308eddfd038","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":["1.7"," 1.6"," 1.5"],"version":null,"customerIds":["photon","1c65af0e-6a45-4841-abe7-7d1a07047b1d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533323500,"helpText":null,"system":false,"name":"java-standalone","id":"tech-java-standalone","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":["1.7"," 1.6"," 1.5"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532243796,"helpText":null,"system":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","a41228d1-5e74-4c7e-ac2e-b80d946376fc","170a7a47-95f7-4829-84dc-e5cffab0cd7a","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943312,"helpText":null,"system":true,"name":"Java","id":"java","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"dotnet","techVersions":["4.0"," 3.5"," 3.0"," 2.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532161515,"helpText":null,"system":false,"name":".net-iis","id":"tech-dotnet","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"dotnet","techVersions":["3.5"," 3.0"," 2.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532410562,"helpText":null,"system":false,"name":".net-SharePoint","id":"tech-sharepoint","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"dotnet","techVersions":["3.5"," 3.0"," 2.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531808546,"helpText":null,"system":false,"name":".net-Sitecore","id":"tech-sitecore","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Dot Net","id":"dotnet","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["7.8"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532340640,"helpText":null,"system":false,"name":"php-Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["3.4.2"," 3.3.1"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532355343,"helpText":null,"system":false,"name":"php-Wordpress","id":"tech-wordpress","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["5.4.x","  5.3.x","  5.2.x","  5.1.x","  5.0.x"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532307546,"helpText":null,"system":false,"name":"php-raw","id":"tech-php","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["6.3"," 6.25"," 6.19"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532328968,"helpText":null,"system":false,"name":"php-Drupal6","id":"tech-phpdru6","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","c32171c4-90e5-4ede-9c51-0ff370eae974","a145ab5e-5eb6-4323-9c88-db61a79c9f3c","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"PHP","id":"php","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":["0.8.22","  0.8.20","  0.10.9","  0.10.8","  0.10.7","  0.10.6","  0.10.5","  0.10.4","  0.10.3","  0.10.2","  0.10.1","  0.10.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532270437,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943312,"helpText":null,"system":true,"name":"NodeJs","id":"nodejs","displayName":null,"description":null,"status":null}],"customerIds":["photon"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Middle Tier","id":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","displayName":null,"description":null,"status":null},{"techGroups":[{"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techInfos":[{"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techGroupId":"1795d032-466f-4866-88b2-e5ce0c332871","techVersions":["5.6"],"version":null,"customerIds":["photon"],"used":false,"creationDate":1377533458921,"helpText":null,"system":false,"name":"AEM CQ","id":"0101a91b-559f-4b1b-9e30-a24ed5760d02","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"CQ5","id":"1795d032-466f-4866-88b2-e5ce0c332871","displayName":null,"description":null,"status":null}],"customerIds":["photon"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"CMS","id":"99d55693-dacd-4f77-994a-f02a66176ff9","displayName":null,"description":null,"status":null},{"techGroups":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"bb","techVersions":["7.x "],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533030187,"helpText":null,"system":false,"name":"mobile-app-bb10","id":"tech-blackberry-hybrid","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943390,"helpText":null,"system":true,"name":"Black Berry","id":"bb","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["3.9.1"," 3.9.0"," 3.8.1"," 3.8.0"," 3.7.3"," 3.7.2"," 3.7.1"," 3.7.0"," 3.10.3"," 3.10.2"," 3.10.1"," 3.10.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533391328,"helpText":null,"system":false,"name":"responsive-web-yui","id":"tech-html5","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["2.0.2"," 2.0.1"," 2.0.0"," 1.7.2"," 1.10.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533295015,"helpText":null,"system":false,"name":"responsive-web","id":"tech-html5-jquery-widget","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["3.9.1"," 3.9.0"," 3.8.1"," 3.8.0"," 3.7.3"," 3.7.2"," 3.7.1"," 3.7.0"," 3.10.3"," 3.10.2"," 3.10.1"," 3.10.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533425406,"helpText":null,"system":false,"name":"mobile-web-yui","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["2.0.2"," 2.0.1"," 2.0.0"," 1.7.2"," 1.10.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533346750,"helpText":null,"system":false,"name":"mobile-web","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","c32171c4-90e5-4ede-9c51-0ff370eae974","05c80933-95d4-46c8-a58d-ceceb4bcce48","0f3d8d9d-a7d0-49b8-b662-2cc25a5ee88b","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"HTML5","id":"html5","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531462625,"helpText":null,"system":false,"name":"hybrid-app-iOS","id":"tech-iphone-hybrid","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531445718,"helpText":null,"system":false,"name":"library-app-iOS ","id":"tech-iphone-library","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531595765,"helpText":null,"system":false,"name":"mobile-app-iOS","id":"tech-iphone-native","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","170a7a47-95f7-4829-84dc-e5cffab0cd7a","a41228d1-5e74-4c7e-ac2e-b80d946376fc","c32171c4-90e5-4ede-9c51-0ff370eae974","05c80933-95d4-46c8-a58d-ceceb4bcce48","0f3d8d9d-a7d0-49b8-b662-2cc25a5ee88b","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Iphone","id":"iphone","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"windows","techVersions":["1.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377530944593,"helpText":null,"system":false,"name":"desktop-app-OSX","id":"49ea6a1c-ad68-4971-995c-0f26017abb3b","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"windows","techVersions":["7.x"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532983125,"helpText":null,"system":false,"name":"mobile-app-winMetro","id":"tech-win-metro","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"windows","techVersions":["8.x","  7.x"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533002328,"helpText":null,"system":false,"name":"mobile-app-winPhone8","id":"tech-win-phone","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943390,"helpText":null,"system":true,"name":"Windows","id":"windows","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":["4.2"," 4.1.2"," 4.1"," 4.0.3"," 2.3.3"," 2.2"," 2.1_r1"," 1.6"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532115218,"helpText":null,"system":false,"name":"mobile-app-android","id":"tech-android-native","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":["4.2","  4.1.2","  4.1","  4.0.3","  2.3.3","  2.2","  2.1_r1","  1.6"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531545546,"helpText":null,"system":false,"name":"library-app-android","id":"tech-android-library","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":["4.2","  4.1.2","  4.1","  4.0.3","  2.3.3","  2.2","  2.1_r1","  1.6"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531058656,"helpText":null,"system":false,"name":"hybrid-app-android","id":"tech-android-hybrid","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","c32171c4-90e5-4ede-9c51-0ff370eae974","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Android","id":"android","displayName":null,"description":null,"status":null}],"customerIds":["photon"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Front End","id":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","displayName":null,"description":null,"status":null}],"status":"success"});
				  }
				});
				
				$("#addproject").click();
				setTimeout(function() {
					start();
					var techOption = $(commonVariables.contentPlaceholder).find("select.frontEnd").find("option:contains(HTML5)").text();
					var weblayerOption = $(commonVariables.contentPlaceholder).find("select.weblayer").find("option:contains(PHP)").text();
					var mobilelayerOption = $(commonVariables.contentPlaceholder).find("select.mobile_layer").find("option:contains(CQ5)").text();
					
					equal(techOption, "HTML5", "Add Project - Front End UI Tested");
					equal(weblayerOption, "PHP", "Add Project - Middle Tier UI Tested");
					equal(mobilelayerOption, "CQ5", "Add Project - CMS UI Tested");
					self.runChangeEventTest();
				}, 2500);
			});
		},
		
		runChangeEventTest : function() {
			var self=this;
			asyncTest("Add Project- Technolgy Version Change Event Test", function() {
				$("select[name=frontEnd]").val('html5');
				$("select[name=frontEnd]").change();
				$("select[name='appln_technology']").val('tech-html5-mobile-widget');
				$("select[name='appln_technology']").change();
				$("select[name=weblayer]").val('java');
				$("select[name=weblayer]").change();
				$("select[name='web_widget']").val('tech-java-standalone');
				$("select[name='web_widget']").change();
				$("select[name='mobile_layer']").val('1795d032-466f-4866-88b2-e5ce0c332871');
				$("select[name='mobile_layer']").change();
				$("select[name='mobile_types']").val('0101a91b-559f-4b1b-9e30-a24ed5760d02');
				$("select[name='mobile_types']").change();
				
				setTimeout(function() {
					start();
					var techOption = $(commonVariables.contentPlaceholder).find("select.appln_technology").find("option:contains(responsive-web-yui)").text();
					var techVersionOption = $(commonVariables.contentPlaceholder).find("select.appln_version").find("option:contains(3.9.1)").text();
					var webTypeOption = $(commonVariables.contentPlaceholder).find("select.web_widget").find("option:contains(Spring Swagger Splunk)").text();
					var weblayerVersionOption = $(commonVariables.contentPlaceholder).find("select.web_version").find("option:contains(1.7)").text();
					var mobileTypeOption = $(commonVariables.contentPlaceholder).find("select.mobile_types").find("option:contains(AEM CQ)").text();
					var mobileVersionOption = $(commonVariables.contentPlaceholder).find("select.mobile_version").find("option:contains(5.6)").text();
					equal(techOption, "responsive-web-yui", "Add Project - Technolgy Change Event Tested Successfully");
					equal(techVersionOption, "3.9.1", "Add Project - Technolgy Version Change Event Tested Successfully");
					equal(webTypeOption, "Spring Swagger Splunk", "Add Project - weblayer Change Event Test");
					equal(weblayerVersionOption, "1.7", "Add Project - weblayer Version Change Event Test");
					equal(mobileTypeOption, "AEM CQ", "Add Project - Mobile Type Change Event Test");
					equal(mobileVersionOption, "5.6", "Add Project - Mobile Version Change Event Test");
					self.runProjectCodeValidateTest();
				}, 2500);
			});
		},
		
		runProjectCodeValidateTest : function() {
			var self=this;
			asyncTest("Add Project- ProjectCode Validate Test", function() {
				$("input[name='projectcode']").val('-----');
				$("input[name='projectcode']").focusout();
				setTimeout(function() {
					start();
					var projectCode = $("input[name=projectcode]").attr('placeholder');
					equal(projectCode, "Invalid Code", "Add Project - ProjectCode Validate Tested");
					self.runAppCodeValidateTest();
				}, 1500);
			});
		},
		
		runAppCodeValidateTest : function() {
			var self=this;
			asyncTest("Add Project- AppCode Validate Test", function() {
				$(".appln-appcode").val('-----');
				$(".appln-appcode").focusout();
				setTimeout(function() {
					start();
					var projectCode = $(".appln-appcode").attr('placeholder');
					equal(projectCode, "Invalid Code", "Add Project - AppCode Validate Tested");
					self.runAppCodeEmptyValidationTest();
				}, 1500);
			});
		},
		
		runAppCodeEmptyValidationTest : function() {
			var self=this;
			asyncTest("Add Project- AppCode Empty Validate Test", function() {
				$("input.appCodeText").val('');
				$("input.appCodeText").blur();
				setTimeout(function() {
					start();
					var errMsg = $("input.appCodeText").attr('placeholder');
					equal(errMsg, "Enter AppCode", "Add Project - AppCode Empty Validate Tested");
					self.runAppCodeEmptyValidationTest();
				}, 1500);
			});
		},
		
		runAppCodeEmptyValidationTest : function() {
			var self=this;
			asyncTest("Add Project- AppCode Empty Validate Test", function() {
				$("input.appCodeText").val('');
				$("input.appCodeText").blur();
				setTimeout(function() {
					start();
					var errMsg = $("input.appCodeText").attr('placeholder');
					equal(errMsg, "Enter AppCode", "Add Project - AppCode Empty Validate Tested");
					self.projectVersionValidateTest();
				}, 1500);
			});
		},
		
		projectVersionValidateTest : function() {
			var self=this;
			asyncTest("Create Project- Version Validation Test", function() {
				$("input[name=projectname]").val('Test1');
				$("input[name=projectcode]").val('Test1');
				$("input[name=projectversion]").val("");
				
				$("input[name='Create']").click();
				
				setTimeout(function() {
					start();
					var errorMsg = $("input[name=projectversion]").attr('placeholder');
					equal(errorMsg, "Enter Version", "Create Project- Version Validation Tested");
					self.dateValidateTest1();
				}, 1500);	
			});
		},
		
		dateValidateTest1 : function() {
			var self=this;
			asyncTest("Create Project- Date Validation Test1", function() {
				$("input[name=projectname]").val('Test1');
				$("input[name=projectcode]").val('Test1');
				$("input[name=projectversion]").val("1.0");
				var startdate = $("#startDate").val("10/23/2013");
				var enddate = $("#endDate").val("10/23/2013");
				
				$("input[name='Create']").click();
				
				setTimeout(function() {
					start();
					var errorMsg = $("#endDate").attr('placeholder');
					equal(errorMsg, "Select valid end date", "Create Project- Date Validation1 Tested");
					self.dateValidateTest2();
				}, 1500);	
			});
		},
		
		dateValidateTest2 : function() {
			var self=this;
			asyncTest("Create Project- Date Validation Test2", function() {
				$("input[name=projectname]").val('Test1');
				$("input[name=projectcode]").val('Test1');
				$("input[name=projectversion]").val("1.0");
				var startdate = $("#startDate").val("10/23/2013");
				var enddate = $("#endDate").val("9/22/2012");
				
				$("input[name='Create']").click();
				
				setTimeout(function() {
					start();
					var errorMsg = $("#endDate").attr('placeholder');
					equal(errorMsg, "Start date should be greater than the end date", "Create Project- Date Validation2 Tested");
					self.dateValidateTest3();
				}, 1500);	
			});
		},
		
		dateValidateTest3 : function() {
			var self=this;
			asyncTest("Create Project- Date Validation Test3", function() {
				$("input[name=projectname]").val('Test1');
				$("input[name=projectcode]").val('Test1');
				$("input[name=projectversion]").val("1.0");
				var startdate = $("#startDate").val("10/23/2013");
				var enddate = $("#endDate").val("9/23/2013");
				
				$("input[name='Create']").click();
				
				setTimeout(function() {
					start();
					var errorMsg = $("#endDate").attr('placeholder');
					equal(errorMsg, "Start date should be greater than the end date", "Create Project- Date Validation3 Tested");
					self.dateValidateTest4();
				}, 1500);	
			});
		},
		
		dateValidateTest4 : function() {
			var self=this;
			asyncTest("Create Project- Date Validation Test4", function() {
				$("input[name=projectname]").val('Test1');
				$("input[name=projectcode]").val('Test1');
				$("input[name=projectversion]").val("1.0");
				var startdate = $("#startDate").val("10/23/2013");
				var enddate = $("#endDate").val("10/22/2013");
				
				$("input[name='Create']").click();
				
				setTimeout(function() {
					start();
					var errorMsg = $("#endDate").attr('placeholder');
					equal(errorMsg, "Start date should be greater than the end date", "Create Project- Date Validation4 Tested");
					self.runAddApplicationLayerEventTest();
				}, 1500);	
			});
		},
		
		runAddApplicationLayerEventTest : function() {
			var self=this;
			asyncTest("Add Project- Add Application Layer Event Test", function() {
				$("a[name=addApplnLayer]").click();
				setTimeout(function() {
					start();
					var addedTrName = $("a[name=addApplnLayer]").parents('tr.applnlayercontent:last').attr('name');
					equal(addedTrName, "staticApplnLayer", "Add Project- Add Application Layer Event Test");
					self.runAddWebLayerEventTest();
				}, 2500);
			});
		},
		
		runAddWebLayerEventTest : function() {
			var self=this;
			asyncTest("Add Project- Add Web Layer Event Test", function() {
				$("a[name=addWebLayer]").click();
				setTimeout(function() {
					start();
					var addedTrName = $("a[name=addWebLayer]").parents('tr.weblayercontent:last').attr('name');
					equal(addedTrName, "staticWebLayer", "Add Project- Add Web Layer Event Test");
					self.runAddMobileLayerEventTest();
				}, 2500);
			});
		},
		
		runAddMobileLayerEventTest : function() {
			var self=this;
			asyncTest("Add Project- Add Mobile Layer Event Test", function() {
				$("a[name=addMobileLayer]").click();
				setTimeout(function() {
					start();
					var addedTrName = $("a[name=addMobileLayer]").parents('tr.mobilelayercontent:last').attr('name');
					equal(addedTrName, "staticMobileLayer", "Add Project- Add Mobile Layer Event Test");
					self.showSuccessMessageAfterCreation();
				}, 2500);
			});
		},

		showSuccessMessageAfterCreation : function() {
			var self=this;
			asyncTest("Add Project- show SuccessMessage After Creation Test", function() {
				$("input[name=projectname]").val('Test');
				$("input[name=projectcode]").val('Test1');
				$("input[name=projectversion]").val('1.0');
				$("input[name=projectdescription]").val('Test Description');
				$("input[name=multimodule]").val('false');
				$("#appcode").val('app1');
				$("#appcode").bind('input');
				$("select[name=frontEnd]").val('html5');
				$("select[name=frontEnd]").change();
				$("select[name='appln_technology']").val('tech-html5-mobile-widget');
				$("select[name='appln_technology']").change();
				$("select[name=appln_version]").val('3.9.1');
				$("select[name=appln_version]").change();
				$("#webappcode").val('web1');
				$("#webappcode").bind('input');
				$("select[name=weblayer]").val('java');
				$("select[name=weblayer]").change();
				$("select[name=web_widget]").val('tech-java-standalone');
				$("select[name=web_widget]").change();;
				$("select[name=widgetversion]").val('1.7');
				$("select[name=widgetversion]").change();
				$("#mobileappcode").val('mob1');
				$("#mobileappcode").bind('input');
				$("select[name=mobile_layer]").val('1795d032-466f-4866-88b2-e5ce0c332871');
				$("select[name=mobile_layer]").change();
				$("select[name=mobile_types]").val('0101a91b-559f-4b1b-9e30-a24ed5760d02');
				$("select[name=mobile_types]").change();
				$("select[name=mobile_version]").val('5.6');
				$("select[name=mobile_version]").change();
				
				self.createProject = $.mockjax({
					  url: commonVariables.webserviceurl + 'project/create?userId=admin',
					  type:'POST',
					  contentType:'application/json',
					  status: 200,
					  response: function() {
						   this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200004","data":{"appInfos":[{"modules":null,"pomFile":null,"appDirName":"app1","techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"HTML5","techVersions":null,"version":"3.9.1","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"mobile-web-yui","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"app1","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"app1","id":"b4a5fa8e-cb7b-4ae9-800b-16e833a23a35","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":"web1","techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"Java","techVersions":null,"version":"1.7","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"java-standalone","id":"tech-java-standalone","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"web1","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"web1","id":"81b94a21-bc8a-4c3c-9bf8-3a4e4e98bb37","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":"mob1","techInfo":{"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techGroupId":"CQ5","techVersions":null,"version":"5.6","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"AEM CQ","id":"0101a91b-559f-4b1b-9e30-a24ed5760d02","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"mob1","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"mob1","id":"0e9eb83e-478d-489f-8eca-bb1bfc2277af","displayName":null,"description":null,"status":null}],"projectCode":"Test1","noOfApps":3,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"version":"1.0","customerIds":["photon"],"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"Test","id":"fb248f36-6b64-4f96-8cda-79663a36db91","displayName":null,"description":"Test Description","status":null},"status":"success"});	
					  }
				});	  
			
				$("input[name='Create']").click();
				
				setTimeout(function() {
					start();
					var Msg = $(".msgdisplay").hasClass('success');
					notEqual(Msg, true, "Add Project- show SuccessMessage After Creation Test");
					self.runCancelButtonTest();
				}, 1500);	
			});
		},
		
		runCancelButtonTest : function() {
			var self=this;
			asyncTest("Add Project- Cancel Button Event Test", function() {
				$("input[name='Cancel']").click();
				setTimeout(function() {
					start();
					var appText = $("a[name=editApplication]").text();
					notEqual(appText, "wordpress-WordPress", "Add Project - Cancel Button Event Tested");
					self.runEmptyProjectNameTest();
				}, 1500);
			});
		},
		
		runEmptyProjectNameTest : function() {
			var self=this;
			asyncTest("Add Project- Empty Project Name Test", function() {
				$("input#projectname").val('');
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					var errorMsg = $("input[name=projectname]").attr('placeholder');
					equal(errorMsg, "Enter Name", "Add Project - Empty Project Name Test Tested");
					self.runEmptyProjectCodeTest();
				}, 1500);
			});
		},
		
		runEmptyProjectCodeTest : function() {
			var self=this;
			asyncTest("Add Project- Empty Project Code Test", function() {
				$("input#projectname").val('Test');
				$("input#projectcode").val('');
				$(commonVariables.contentPlaceholder).find('tr.applnlayercontent:last').remove();
				$(commonVariables.contentPlaceholder).find('tr.weblayercontent:last').remove();
				$(commonVariables.contentPlaceholder).find('tr.mobilelayercontent:last').remove();
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					var errorMsg = $("input[name=projectcode]").attr('placeholder');
					equal(errorMsg, "Enter Code", "Add Project - Empty Project Name Test Tested");
					self.dateValidationTest();
				}, 1500);
			});
		},
		
		dateValidationTest : function() {
			var self=this;
			asyncTest("Add Project- Date Validation Test", function() {
				$("input#projectname").val('Test');
				$("input#projectcode").val('Test');
				$("#startDate").val('10/10/2013');
				$("#endDate").val('10/09/2013');
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					var errorMsg = $("#endDate").attr('placeholder');
					equal(errorMsg, "Start date should be greater than the end date", "Add Project - Date Validation Tested");
					self.runEmptyApplicationAppCodeTest();
				}, 1500);
			});
		},
		
		runEmptyApplicationAppCodeTest : function() {
			var self=this, errorMsg;
			asyncTest("Add Project- Empty Application AppCode Test", function() {
				$("input#projectname").val('Test');
				$("input#projectcode").val('Test');
				$("#startDate").val('10/10/2013');
				$("#endDate").val('10/11/2013');
				$("input#appcode").val('');
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					errorMsg = $("input#appcode").attr('placeholder');
					equal(errorMsg, "Enter AppCode", "Add Project - Empty Application AppCode Tested");
					self.runTechnologySelectValidateTest();
				}, 1500);
			});
		},
		
		runTechnologySelectValidateTest : function() {
			var self=this;
			asyncTest("Add Project- Technology Select Validate Test", function() {
				$("input#projectname").val('Test');
				$("input#projectcode").val('Test');
				$("input.appln-appcode").val('app1')
				$('select.appln_technology').val('Select Technology');
				$('select.appln_technology').change();
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					var errorMsg = $(".errmsg1").text();
					equal(errorMsg, "Select Technology", "Add Project - Technology Select Validate Tested");
					self.runEmptyWebAppCodeTest();
				}, 1500);
			});
		},
		
		runEmptyWebAppCodeTest : function() {
			var self=this, errorMsg;
			asyncTest("Add Project- Empty Web AppCode Test", function() {
				$("input#projectname").val('Test');
				$("input#projectcode").val('Test');
				$("input.appln-appcode").val('app1');
				$("input.appln-appcode").bind('input');
				$("select[name=appln_technology]").val('tech-html5-jquery-mobile-widget');
				$("select[name=appln_technology]").change();
				$("input#webappcode").val('');
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					errorMsg = $("input#appcode").attr('placeholder');
					equal(errorMsg, "Enter AppCode", "Add Project - Empty Web AppCode Tested");
					self.runWebLayerSelectTest();
				}, 1500);
			});
		},
		
		runWebLayerSelectTest : function() {
			var self=this, errorMsg;
			asyncTest("Add Project- WebLayer Select Test", function() {
				$("input#projectname").val('Test');
				$("input#projectcode").val('Test');
				$("input.appln-appcode").val('app1');
				$("input.appln-appcode").bind('input');	
				$("select[name=appln_technology]").val('tech-html5-jquery-mobile-widget');
				$("select[name=appln_technology]").change();
				$("input.web-appcode").val('web1')
				$("input.web-appcode").bind('input');
				$('select[name=weblayer]').val('Select Group');
				$("select[name=weblayer]").change();	
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					var errorMsg = $(".errmsg2").text();
					equal(errorMsg, "Select Group", "Add Project - WebLayer Select Tested");
					self.runWebWidgetSelectTest();
				}, 1500);
			});
		},
		
		runWebWidgetSelectTest : function() {
			var self=this, errorMsg;
			asyncTest("Add Project- Web Widget Select Test", function() {
				$("input#projectname").val('Test');
				$("input#projectcode").val('Test');
				$("input.appln-appcode").val('app1');
				$("input.appln-appcode").bind('input');	
				$("select[name=appln_technology]").val('tech-html5-jquery-mobile-widget');
				$("select[name=appln_technology]").change();
				$("input.web-appcode").val('web1')
				$("input.web-appcode").bind('input');
				$("select[name=weblayer]").val('java');
				$("select[name=weblayer]").change();
				$("select[name=web_widget]").val('Select Technology');
				$("select[name=web_widget]").change();
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					var errorMsg = $(".errmsg2").text();
					equal(errorMsg, "Select Technology", "Add Project - WebWidgetSelectTest Select Tested");
					self.runEmptyMobileAppCodeTest();
				}, 1500);
			});
		},
		
		runEmptyMobileAppCodeTest : function() {
			var self=this;
			asyncTest("Add Project- Empty Mobile AppCode Test", function() {
				$("input#projectname").val('Test');
				$("input#projectcode").val('Test');
				$("input#appcode").val('app1');
				$("input.appln-appcode").val('app1');
				$("input.appln-appcode").bind('input');
				$("select[name=appln_technology]").val('tech-html5-jquery-mobile-widget');
				$("select[name=appln_technology]").change();	
				$("input.web-appcode").val('web1');
				$("input.web-appcode").bind('input');
				$("select[name=weblayer]").val('java');
				$("select[name=weblayer]").change();
				$("select[name=web_widget]").val('tech-java-standalone');
				$("select[name=web_widget]").change();
				$("input#mobileappcode").val('');
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					errorMsg = $("input#mobileappcode").attr('placeholder');
					equal(errorMsg, "Enter AppCode", "Add Project - Empty Mobile AppCode Tested");
					self.runSelectMobileTechnologyTest();
				}, 1500);
			});
		},
		
		runSelectMobileTechnologyTest : function(){
			var self=this;
			asyncTest("Add Project- Empty Select Technology Mobile Test", function() {
				$("input#projectname").val('Test');
				$("input#projectcode").val('Test');
				$("input#appcode").val('app1');
				$("input.appln-appcode").val('app1');
				$("input.appln-appcode").bind('input');
				$("select[name=appln_technology]").val('tech-html5-jquery-mobile-widget');
				$("select[name=appln_technology]").change();	
				$("input.web-appcode").val('web1')
				$("input.web-appcode").bind('input')
				$("select[name=weblayer]").val('java');
				$("select[name=weblayer]").change();
				$("select[name=web_widget]").val('tech-java-standalone');
				$("select[name=web_widget]").change();
				$("input.mobile-appcode").val('mob1');
				$("input.mobile-appcode").bind('input');
				$("select[name=mobile_layer]").val('Select Group');
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					var errorMsg = $(".errmsg3").text();
					equal(errorMsg, "Select Group", "Add Project - Select Mobile Technology Tested");
					self.runSelectMobilePlatformTest();
				}, 1500);
			});
		},
		
		runSelectMobilePlatformTest : function(){
			var self=this;
			asyncTest("Add Project- Empty Select Platform Mobile Test", function() {
				$("input#projectname").val('Test');
				$("input#projectcode").val('Test');
				$("input.appln-appcode").val('app1');
				$("input.appln-appcode").bind('input');
				$("select[name=appln_technology]").val('tech-html5-jquery-mobile-widget');
				$("select[name=appln_technology]").change();	
				$("input.web-appcode").val('web1');
				$("input.web-appcode").bind('input');
				$("select[name=weblayer]").val('java');
				$("select[name=weblayer]").change();
				$("select[name=web_widget]").val('tech-java-standalone');
				$("select[name=web_widget]").change();
				$("select[name=widgetversion]").val('1.7');
				$("select[name=widgetversion]").change();
				$("input.mobile-appcode").val('mob1');
				$("input.mobile-appcode").bind('input');
				$("select[name=mobile_layer]").val('1795d032-466f-4866-88b2-e5ce0c332871');
				$("select[name=mobile_layer]").change();
				$("select[name=mobile_types]").val('Select Technology');
				$("select[name=mobile_types]").change();
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					var errorMsg = $(".errmsg3").text();
					equal(errorMsg, "Select Technology", "Add Project - Select Mobile Platform Tested");
					self.runCreateProjectTest();
				}, 1500);
			});
		},
		
		runCreateProjectTest : function(){
			var self=this;
			asyncTest("Add Project- Create Project Test", function() {
				$.mockjax({
					  url: commonVariables.webserviceurl + 'project/create?userId=admin',
					  type:'POST',
					  contentType:'application/json',
					  status: 200,
					  response: function() {
						   this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200004","data":{"appInfos":[{"modules":null,"pomFile":null,"appDirName":"app1","techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"HTML5","techVersions":null,"version":"3.9.1","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"mobile-web-yui","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"app1","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"app1","id":"b4a5fa8e-cb7b-4ae9-800b-16e833a23a35","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":"web1","techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"Java","techVersions":null,"version":"1.7","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"java-standalone","id":"tech-java-standalone","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"web1","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"web1","id":"81b94a21-bc8a-4c3c-9bf8-3a4e4e98bb37","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":"mob1","techInfo":{"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techGroupId":"CQ5","techVersions":null,"version":"5.6","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"AEM CQ","id":"0101a91b-559f-4b1b-9e30-a24ed5760d02","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"mob1","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"mob1","id":"0e9eb83e-478d-489f-8eca-bb1bfc2277af","displayName":null,"description":null,"status":null}],"projectCode":"Test1","noOfApps":3,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"version":"1.0","customerIds":["photon"],"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"Test","id":"fb248f36-6b64-4f96-8cda-79663a36db91","displayName":null,"description":"Test Description","status":null},"status":"success"});	
					  }
				});
				
				$("input[name=projectname]").val('Test');
				$("input[name=projectcode]").val('Test1');
				$("input[name=projectversion]").val('1.0');
				$("input[name=projectdescription]").val('Test Description');
				$("#appcode").val('app1');
				$("#appcode").bind('input');
				$("select[name=frontEnd]").val('html5');
				$("select[name=frontEnd]").change();
				$("select[name='appln_technology']").val('tech-html5-mobile-widget');
				$("select[name='appln_technology']").change();
				$("select[name=appln_version]").val('3.9.1');
				$("select[name=appln_version]").change();
				$("#webappcode").val('web1');
				$("#webappcode").bind('input');
				$("select[name=weblayer]").val('java');
				$("select[name=weblayer]").change();
				$("select[name=web_widget]").val('tech-java-standalone');
				$("select[name=web_widget]").change();;
				$("select[name=widgetversion]").val('1.7');
				$("select[name=widgetversion]").change();
				$("#mobileappcode").val('mob1');
				$("#mobileappcode").bind('input');
				$("select[name=mobile_layer]").val('1795d032-466f-4866-88b2-e5ce0c332871');
				$("select[name=mobile_layer]").change();
				$("select[name=mobile_types]").val('0101a91b-559f-4b1b-9e30-a24ed5760d02');
				$("select[name=mobile_types]").change();
				$("select[name=mobile_version]").val('5.6');
				$("select[name=mobile_version]").change();
				$("input[name='Create']").click();
				setTimeout(function() {
					start();
					var appText = $("a[name=editApplication]").text();
					notEqual(appText, "app1", "Add Project - Create Project Tested");
					self.runCloseButtonTest();
				}, 1500);
			});
		},
		
		runCloseButtonTest : function() {
			var self=this;
			asyncTest("Add Project- Close Image Event Test", function() {
				$("img[name='close']").click();
				var applnLayer = $("tr[class=applnLayer]").css('display');
				setTimeout(function() {
					start();
					equal(applnLayer, "table-row", "Add Project - Close Image Event Test for Application Layer");
					self.runInputBtnTest();
				}, 1500);
			});
		},
		
		runInputBtnTest : function() {
			var self = this;
			asyncTest("Add Project- Input button Event Test", function() {
				$(".flt_left input").click();
				var applnLayerTrDisplay = $("tr[class=applnLayer]").attr('key');
				setTimeout(function() {
					start();
					equal(applnLayerTrDisplay, "displayed", "Add Project - Input button Event Test for Application Layer");
					self.runPilotProjectEventTest();
				}, 1500);
			});
		},
		
		/* runMultiModuleEventTest : function() {
			var self=this;
			asyncTest("Add Project- MultiModule CheckBox Event Test", function() {
				$("input[name='multimodule']").click();
				var appdependency = $("span[name=appdependency]").css('display');
				setTimeout(function() {
					start();
					notEqual(appdependency, "none", "Add Project - MultiModule CheckBox Event Test");
					self.runPilotProjectEventTest();	
				}, 1500);
			});
		}, */
		
		runPilotProjectEventTest : function() {
			var self=this;
			asyncTest("Add Project- Pilot Project Change With Prebuilt Option Event Test", function() {
			
				$.mockjax({
				  url: commonVariables.webserviceurl + 'pilot/prebuilt?userId=admin&customerId=photon',
				  type:'GET',
				  contentType: 'application/json',
				  status: 200,
				  response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200025","data":[{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"dotnet","techVersions":null,"version":"3.5","customerIds":null,"used":false,"creationDate":1365682608015,"helpText":null,"system":false,"name":".net-SharePoint","id":"tech-sharepoint","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_sharepointserver","artifactInfoIds":["2fdeda37-25ba-4677-ad0c-7cfbbab47460"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"da246848-480c-4b79-a0a5-95bfd848d67b","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":["9f189f79-5dc9-481f-b04d-36f1d3095c5a","d11aaae1-319f-45d9-bb8e-81617f42121a","3c8c7d4f-dcdb-4fb0-bc23-81612f9674f0","4d0288ac-e8fd-4e5a-9156-12a85104d639","50b67994-5e79-4895-824f-773704b61f27","d9b693c0-0e32-437c-9d6d-4aa304f4d9bf","169c1561-6b0b-4d03-93cb-3a81693f68d4"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHR_PilotSharePoint_Eshop","customerIds":["photon"],"used":false,"creationDate":1378365318640,"helpText":null,"system":true,"name":"Resource Management","id":"PHR_PilotSharePoint_Eshop","displayName":null,"description":"","status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365318640,"helpText":null,"system":false,"name":"Resource Management Sharepoint","id":"1852f581-704a-46c4-a57f-38bf2d8bcc0f","displayName":"Resource Management Sharepoint","description":"Resource Management Sharepoint","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":null,"version":"0.10.9","customerIds":null,"used":false,"creationDate":1365682608015,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_nodejs","artifactInfoIds":["bea244f4-ddad-4491-9f43-5252696673c1"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"8a5ea2d3-7436-493f-9615-62e141cd7762","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"fad54cb4-2623-4de4-9a33-203008484935","displayName":null,"description":null,"status":null}],"selectedModules":["6702b6e1-6d43-4ec9-8168-3ef0755b31f3","d424aaea-b3f5-470e-bcc1-73635e5d2072","f70f8a19-b773-420d-b216-9624a41bba4d","6426a7fd-1e8e-4e8f-9d36-6ee68318b20b","c8ec21ad-2b13-40c0-b367-1204dbddf428","ef823d56-f3ac-414d-9a2c-8e59d48268a6","d7348c54-3a59-4586-8936-e914e3447249","db50e786-bd9f-4cf5-9078-2c22a6782ee8","da034bec-8680-43ac-99d0-38bec5fc97d1","5945b54a-6de5-4dd3-859a-4d082146838a","39f17066-9e9c-4943-857c-22ad3bab5086","5f5aca9b-1212-42e6-b77f-822a8b24ca62","045329d0-e519-4650-9dba-a64a6bf0ab2c","43653f0f-ef0b-46ea-8204-2ff6c41d22d4","f9511e46-bbd7-4910-a40a-1bc7a12317a5"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_NODEJS_eshop-WebService","customerIds":["photon"],"used":false,"creationDate":1378365318656,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_NODEJS_eshop-WebService","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"version":"3.10.3","customerIds":null,"used":false,"creationDate":1354009397609,"helpText":null,"system":false,"name":"mobile-web-yui","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"9702e0ad-b168-4fbd-a581-3b3d498afe25","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":["402ded74-e007-4cdf-8fe5-ee11ca01b7db","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","444cd5e9-6d16-4e38-8f95-c3f1d84f3c6e","bb9b5d04-2afe-4722-b87b-c1d9cdefbf8e","b3ae6065-1a44-4628-811d-0bd9f1bae6f0","39581ee3-871e-426f-8622-2fb7f12a7b79"],"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_html5_mobilewidget_eshop","customerIds":["photon"],"used":false,"creationDate":1378365318656,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_html5_mobilewidget_eshop","displayName":null,"description":null,"status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365318656,"helpText":null,"system":false,"name":"Eshop Nodejs Webservice - HTML5 YUI Mobile Widget","id":"a9476519-ed62-4399-a7c2-49a0cdc30dbc","displayName":"Eshop Nodejs Webservice - HTML5 YUI Mobile Widget","description":"Eshop Nodejs Webservice - HTML5 YUI Mobile Widget","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":null,"version":"0.10.9","customerIds":null,"used":false,"creationDate":1365682608015,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_nodejs","artifactInfoIds":["bea244f4-ddad-4491-9f43-5252696673c1"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"8a5ea2d3-7436-493f-9615-62e141cd7762","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"fad54cb4-2623-4de4-9a33-203008484935","displayName":null,"description":null,"status":null}],"selectedModules":["6702b6e1-6d43-4ec9-8168-3ef0755b31f3","d424aaea-b3f5-470e-bcc1-73635e5d2072","f70f8a19-b773-420d-b216-9624a41bba4d","6426a7fd-1e8e-4e8f-9d36-6ee68318b20b","c8ec21ad-2b13-40c0-b367-1204dbddf428","ef823d56-f3ac-414d-9a2c-8e59d48268a6","d7348c54-3a59-4586-8936-e914e3447249","db50e786-bd9f-4cf5-9078-2c22a6782ee8","da034bec-8680-43ac-99d0-38bec5fc97d1","5945b54a-6de5-4dd3-859a-4d082146838a","39f17066-9e9c-4943-857c-22ad3bab5086","5f5aca9b-1212-42e6-b77f-822a8b24ca62","045329d0-e519-4650-9dba-a64a6bf0ab2c","43653f0f-ef0b-46ea-8204-2ff6c41d22d4","f9511e46-bbd7-4910-a40a-1bc7a12317a5"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_NODEJS_eshop-WebService","customerIds":["photon"],"used":false,"creationDate":1378365318656,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_NODEJS_eshop-WebService","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"version":"3.10.3","customerIds":null,"used":false,"creationDate":1354009203000,"helpText":null,"system":false,"name":"responsive-web-yui","id":"tech-html5","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608078,"helpText":null,"system":false,"name":null,"id":"fe0af5c1-0511-4659-9a67-d4fb576ca08d","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":["402ded74-e007-4cdf-8fe5-ee11ca01b7db","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","bb9b5d04-2afe-4722-b87b-c1d9cdefbf8e"],"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_html5_yui_multichannel_eshop","customerIds":["photon"],"used":false,"creationDate":1378365318656,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_html5_yui_multichannel_eshop","displayName":null,"description":null,"status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365318656,"helpText":null,"system":false,"name":"Eshop Nodejs Webservice - HTML5 Multichannel YUI Widget","id":"624feccf-dec9-4ca5-b7ea-1f9c8909d66c","displayName":"Eshop Nodejs Webservice - HTML5 Multichannel YUI Widget","description":"Eshop Nodejs Webservice - HTML5 Multichannel YUI Widget","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":null,"version":"5.4.x","customerIds":null,"used":false,"creationDate":1365682608000,"helpText":null,"system":false,"name":"php-raw","id":"tech-php","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-server","artifactInfoIds":["6acf8885-0eaf-430c-b168-913706560a85"],"creationDate":1365682608000,"helpText":null,"system":false,"name":null,"id":"3a156b7f-442c-417c-aad0-072e7c76f837","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608000,"helpText":null,"system":false,"name":null,"id":"5b5b14d9-7374-4487-a9a4-a99bb94aa2b3","displayName":null,"description":null,"status":null}],"selectedModules":["1136e881-d093-4211-a1ad-8c791a435276"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHR_phpblog","customerIds":["photon"],"used":false,"creationDate":1378365318656,"helpText":null,"system":true,"name":"Blog","id":"PHR_phpblog","displayName":null,"description":"","status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365318656,"helpText":null,"system":false,"name":"Blog Php","id":"4ad94238-1684-4f0d-bc61-2149d4bd9d89","displayName":"Blog Php","description":"Blog Php","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":null,"version":"7.8","customerIds":null,"used":false,"creationDate":1365682608015,"helpText":null,"system":false,"name":"php-Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-server","artifactInfoIds":["6acf8885-0eaf-430c-b168-913706560a85"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"9a8762d0-2873-4099-bd97-001210a33bf0","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"ae34e3d3-6f6c-49d7-a919-f3a101007889","displayName":null,"description":null,"status":null}],"selectedModules":["a02b16ce-0275-4d1f-96a1-f0a7d071e1d2","f22494d2-0cfa-4406-879c-8a744be4395d","ebee2ec3-4b8f-4db9-887b-67a3d47d5295","3c24d767-d552-4052-b56a-f0ecd8cc31ea","0c6a8d49-9d04-406d-800b-336ba20a2bfe","d033dd8a-2a4f-4892-a9f7-e1dd571acc41","c4519930-1376-4e60-88a0-6923f2408f43","79186930-8b68-4a41-82f0-9be9b4da4a7d"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHR_Drupal7Eshop","customerIds":["photon"],"used":false,"creationDate":1378365318671,"helpText":null,"system":true,"name":"Eshop","id":"PHR_Drupal7Eshop","displayName":null,"description":"","status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365318656,"helpText":null,"system":false,"name":"Eshop Drupal7","id":"ca4a007a-5621-4f4b-8f15-72a65f57c4d2","displayName":"Eshop Drupal7","description":"Eshop Drupal7","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":null,"version":"1.6","customerIds":null,"used":false,"creationDate":1355150953453,"helpText":null,"system":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608046,"helpText":null,"system":false,"name":null,"id":"b279980e-390b-4d07-ad26-72fce77f88bf","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608062,"helpText":null,"system":false,"name":null,"id":"c50e90ff-dedb-40b6-bdcf-3776a35e9b68","displayName":null,"description":null,"status":null}],"selectedModules":["8c978a8d-ebe0-4c18-8e11-5512c61488be","9e7cb57d-3b7d-46aa-9768-4c4ac19451cc","37701b35-aea1-456e-9de3-aa50f64b4705","04aea87f-3ccf-45dd-ad2f-e124a28755a9","9c7c7d7e-e0a8-4cbc-a98f-52504c7398bb","f31fa2af-602b-4eb6-954c-bd1af173017b","eaf60ba6-0259-4c19-b317-6d179585c497","f08fa865-c1ad-481c-b085-7c11b29966fb","173504a5-2c17-480d-af7f-9330dfbb0a5f","89cb6609-d5bc-412d-9291-8d63a5787931","283f9027-61ca-4b47-8a6b-3f2fba059aa7","0053ed62-5509-43d4-a213-bb9bdd1e98b0","8c6fcb31-b32b-4295-a443-414300c5431b","2d41a182-85f1-42a3-a67c-a0836792ba02","cae6a04c-9716-42ff-b730-f3e914d80924","519207c8-01d3-4e8a-9239-5b1c6ec78ad4","b49b6233-94ce-4c7f-802c-4e6d944ac3e4","3dddbeb8-a2b5-4b30-99f4-ddcebf69aa24","d15f5ed0-71d7-4193-9af8-f2316f203596","0286832e-c9f1-4b04-8014-caa923e78845","849eee10-758d-4d71-acf6-94a6653acab7","9b8e3c8c-902d-4c28-b716-b0efc93c0fb2","6ed21722-e683-4ec5-8a77-5f50cac908ae","a3133885-5528-487d-8346-12e8dc0ed7b1","4c5e64cd-263b-436d-abb1-4fe2de5c063b","6d3e706f-85af-4610-835c-eecae263bc5f","74134940-4284-47e8-89ba-56af8323bcae","dbb9ea5d-2a09-4747-93a9-cf5dcad5cdd9","bd317892-9e0a-43a3-95cd-e7dd3cb6a3e4","f20f6862-4a54-4384-845c-649d12aa94d7","d63b468e-8fa1-4690-afeb-852e80ea450d","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","5a5eb78f-f1de-4bfa-8ee6-1bccd5be29e5","98734103-53b2-43e4-a246-b9d9ebec2d30","b966ea3d-3ed0-47bb-8a0a-83cefc4ae41c","4888dbf7-2b73-46b7-b5c6-aa170bde0f8c","f9efa148-53c7-4e01-90b8-d9690121d9d9","f3968ed2-ce47-4396-8f91-5f7f8bb987b6","bc470e0d-f5a8-4725-8785-b1f35cff1c95","455d5918-35c2-4a00-bd9c-903fb533886f","3fbb4c66-2c8c-42f1-9f10-e98b884588f6","a8a705cb-5b5d-4891-9491-f7f0dd0d0d9a","2db250f7-b245-4a5b-bd20-15f4d588ca7b","8377b620-0de6-438b-b7a5-24dddc3149e1","9dda9452-3ee4-4e27-a583-060cf5ed51af","96c4daca-5136-47a9-8568-bf8b822b4000","b00ffc7f-2d2e-458f-a0c4-4fae63087567","e0603d8a-7f0e-4474-ad74-b66be8e217c6","24e6820d-a472-4488-8fdf-a510be5e2f53","105d4c61-91bb-45a5-848e-d961e2ec38be","ec95e483-8522-4dd5-9095-984886fda4b9","14d09a7a-26b9-4f3b-a4b9-75b95cb74501","91b006c7-555b-48d9-8b9c-04ea623a0195","b4ce2df7-71e7-4f34-bab2-d4f0ef3217e1","fea35cf5-94c2-41a6-a1b1-8093137d7a81","418dc224-1e27-4713-9c12-ee18264e4dfe","cb6d3306-704f-4627-a460-3096fb8816f0","a7896a17-eb7a-4262-8557-914e109ff020","49bf9ecd-ae7a-44cd-8dcb-d6cc532fdc64"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_java_webservice_eshop","customerIds":["photon"],"used":false,"creationDate":1378365318671,"helpText":null,"system":true,"name":"Eshop","id":"d17d8389-3201-4a5f-888a-e5c0a0c68e18","displayName":null,"description":null,"status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365318671,"helpText":null,"system":false,"name":"Eshop J2EE","id":"12ad9896-1659-495b-ae15-27b424b34040","displayName":"Eshop J2EE","description":"Eshop J2EE","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":null,"version":"0.10.9","customerIds":null,"used":false,"creationDate":1365682608015,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_nodejs","artifactInfoIds":["bea244f4-ddad-4491-9f43-5252696673c1"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"8a5ea2d3-7436-493f-9615-62e141cd7762","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"fad54cb4-2623-4de4-9a33-203008484935","displayName":null,"description":null,"status":null}],"selectedModules":["6702b6e1-6d43-4ec9-8168-3ef0755b31f3","d424aaea-b3f5-470e-bcc1-73635e5d2072","f70f8a19-b773-420d-b216-9624a41bba4d","6426a7fd-1e8e-4e8f-9d36-6ee68318b20b","c8ec21ad-2b13-40c0-b367-1204dbddf428","ef823d56-f3ac-414d-9a2c-8e59d48268a6","d7348c54-3a59-4586-8936-e914e3447249","db50e786-bd9f-4cf5-9078-2c22a6782ee8","da034bec-8680-43ac-99d0-38bec5fc97d1","5945b54a-6de5-4dd3-859a-4d082146838a","39f17066-9e9c-4943-857c-22ad3bab5086","5f5aca9b-1212-42e6-b77f-822a8b24ca62","045329d0-e519-4650-9dba-a64a6bf0ab2c","43653f0f-ef0b-46ea-8204-2ff6c41d22d4","f9511e46-bbd7-4910-a40a-1bc7a12317a5"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_NODEJS_eshop-WebService","customerIds":["photon"],"used":false,"creationDate":1378365318671,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_NODEJS_eshop-WebService","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"version":"3.10.3","customerIds":null,"used":false,"creationDate":1354009498671,"helpText":null,"system":false,"name":"responsive-web","id":"tech-html5-jquery-widget","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608046,"helpText":null,"system":false,"name":null,"id":"1e22614f-0490-4600-9f2d-dba1d200e700","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":["402ded74-e007-4cdf-8fe5-ee11ca01b7db","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","444cd5e9-6d16-4e38-8f95-c3f1d84f3c6e","bb9b5d04-2afe-4722-b87b-c1d9cdefbf8e","c4a8d772-305e-441a-993e-703e63795aac"],"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_html5_multichannel_jquery_eshop","customerIds":["photon"],"used":false,"creationDate":1378365318671,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_html5_multichannel_jquery_eshop","displayName":null,"description":null,"status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365318671,"helpText":null,"system":false,"name":"Eshop Nodejs Webservice - HTML5 Multichannel JQuery Widget","id":"460d4239-b28c-45ce-8379-f98cfa18a208","displayName":"Eshop Nodejs Webservice - HTML5 Multichannel JQuery Widget","description":"Eshop Nodejs Webservice - HTML5 Multichannel JQuery Widget","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":null,"version":"0.10.9","customerIds":null,"used":false,"creationDate":1365682608015,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_nodejs","artifactInfoIds":["bea244f4-ddad-4491-9f43-5252696673c1"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"8a5ea2d3-7436-493f-9615-62e141cd7762","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"fad54cb4-2623-4de4-9a33-203008484935","displayName":null,"description":null,"status":null}],"selectedModules":["6702b6e1-6d43-4ec9-8168-3ef0755b31f3","d424aaea-b3f5-470e-bcc1-73635e5d2072","f70f8a19-b773-420d-b216-9624a41bba4d","6426a7fd-1e8e-4e8f-9d36-6ee68318b20b","c8ec21ad-2b13-40c0-b367-1204dbddf428","ef823d56-f3ac-414d-9a2c-8e59d48268a6","d7348c54-3a59-4586-8936-e914e3447249","db50e786-bd9f-4cf5-9078-2c22a6782ee8","da034bec-8680-43ac-99d0-38bec5fc97d1","5945b54a-6de5-4dd3-859a-4d082146838a","39f17066-9e9c-4943-857c-22ad3bab5086","5f5aca9b-1212-42e6-b77f-822a8b24ca62","045329d0-e519-4650-9dba-a64a6bf0ab2c","43653f0f-ef0b-46ea-8204-2ff6c41d22d4","f9511e46-bbd7-4910-a40a-1bc7a12317a5"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_NODEJS_eshop-WebService","customerIds":["photon"],"used":false,"creationDate":1378365318718,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_NODEJS_eshop-WebService","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"version":"3.10.3","customerIds":null,"used":false,"creationDate":1354009203828,"helpText":null,"system":false,"name":"mobile-web","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"523c8806-86a8-4e61-937f-f27c8b32aa5c","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":["402ded74-e007-4cdf-8fe5-ee11ca01b7db","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","444cd5e9-6d16-4e38-8f95-c3f1d84f3c6e","bb9b5d04-2afe-4722-b87b-c1d9cdefbf8e","c4a8d772-305e-441a-993e-703e63795aac"],"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_html5_jquery_mobilewidget_eshop","customerIds":["photon"],"used":false,"creationDate":1378365318718,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_html5_jquery_mobilewidget_eshop","displayName":null,"description":null,"status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365318671,"helpText":null,"system":false,"name":"Eshop Nodejs Webservice - HTML5 JQuery Mobile Widget","id":"65be8f4b-8a46-4a88-9fa3-81af19b24549","displayName":"Eshop Nodejs Webservice - HTML5 JQuery Mobile Widget","description":"Eshop Nodejs Webservice - HTML5 JQuery Mobile Widget","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"version":"3.10.3","customerIds":null,"used":false,"creationDate":1354009203828,"helpText":null,"system":false,"name":"mobile-web","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"523c8806-86a8-4e61-937f-f27c8b32aa5c","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":["402ded74-e007-4cdf-8fe5-ee11ca01b7db","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","444cd5e9-6d16-4e38-8f95-c3f1d84f3c6e","bb9b5d04-2afe-4722-b87b-c1d9cdefbf8e","c4a8d772-305e-441a-993e-703e63795aac"],"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_html5_jquery_mobilewidget_eshop","customerIds":["photon"],"used":false,"creationDate":1378365318734,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_html5_jquery_mobilewidget_eshop","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":null,"version":"1.6","customerIds":null,"used":false,"creationDate":1355150953453,"helpText":null,"system":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608046,"helpText":null,"system":false,"name":null,"id":"b279980e-390b-4d07-ad26-72fce77f88bf","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608062,"helpText":null,"system":false,"name":null,"id":"c50e90ff-dedb-40b6-bdcf-3776a35e9b68","displayName":null,"description":null,"status":null}],"selectedModules":["8c978a8d-ebe0-4c18-8e11-5512c61488be","9e7cb57d-3b7d-46aa-9768-4c4ac19451cc","37701b35-aea1-456e-9de3-aa50f64b4705","04aea87f-3ccf-45dd-ad2f-e124a28755a9","9c7c7d7e-e0a8-4cbc-a98f-52504c7398bb","f31fa2af-602b-4eb6-954c-bd1af173017b","eaf60ba6-0259-4c19-b317-6d179585c497","f08fa865-c1ad-481c-b085-7c11b29966fb","173504a5-2c17-480d-af7f-9330dfbb0a5f","89cb6609-d5bc-412d-9291-8d63a5787931","283f9027-61ca-4b47-8a6b-3f2fba059aa7","0053ed62-5509-43d4-a213-bb9bdd1e98b0","8c6fcb31-b32b-4295-a443-414300c5431b","2d41a182-85f1-42a3-a67c-a0836792ba02","cae6a04c-9716-42ff-b730-f3e914d80924","519207c8-01d3-4e8a-9239-5b1c6ec78ad4","b49b6233-94ce-4c7f-802c-4e6d944ac3e4","3dddbeb8-a2b5-4b30-99f4-ddcebf69aa24","d15f5ed0-71d7-4193-9af8-f2316f203596","0286832e-c9f1-4b04-8014-caa923e78845","849eee10-758d-4d71-acf6-94a6653acab7","9b8e3c8c-902d-4c28-b716-b0efc93c0fb2","6ed21722-e683-4ec5-8a77-5f50cac908ae","a3133885-5528-487d-8346-12e8dc0ed7b1","4c5e64cd-263b-436d-abb1-4fe2de5c063b","6d3e706f-85af-4610-835c-eecae263bc5f","74134940-4284-47e8-89ba-56af8323bcae","dbb9ea5d-2a09-4747-93a9-cf5dcad5cdd9","bd317892-9e0a-43a3-95cd-e7dd3cb6a3e4","f20f6862-4a54-4384-845c-649d12aa94d7","d63b468e-8fa1-4690-afeb-852e80ea450d","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","5a5eb78f-f1de-4bfa-8ee6-1bccd5be29e5","98734103-53b2-43e4-a246-b9d9ebec2d30","b966ea3d-3ed0-47bb-8a0a-83cefc4ae41c","4888dbf7-2b73-46b7-b5c6-aa170bde0f8c","f9efa148-53c7-4e01-90b8-d9690121d9d9","f3968ed2-ce47-4396-8f91-5f7f8bb987b6","bc470e0d-f5a8-4725-8785-b1f35cff1c95","455d5918-35c2-4a00-bd9c-903fb533886f","3fbb4c66-2c8c-42f1-9f10-e98b884588f6","a8a705cb-5b5d-4891-9491-f7f0dd0d0d9a","2db250f7-b245-4a5b-bd20-15f4d588ca7b","8377b620-0de6-438b-b7a5-24dddc3149e1","9dda9452-3ee4-4e27-a583-060cf5ed51af","96c4daca-5136-47a9-8568-bf8b822b4000","b00ffc7f-2d2e-458f-a0c4-4fae63087567","e0603d8a-7f0e-4474-ad74-b66be8e217c6","24e6820d-a472-4488-8fdf-a510be5e2f53","105d4c61-91bb-45a5-848e-d961e2ec38be","ec95e483-8522-4dd5-9095-984886fda4b9","14d09a7a-26b9-4f3b-a4b9-75b95cb74501","91b006c7-555b-48d9-8b9c-04ea623a0195","b4ce2df7-71e7-4f34-bab2-d4f0ef3217e1","fea35cf5-94c2-41a6-a1b1-8093137d7a81","418dc224-1e27-4713-9c12-ee18264e4dfe","cb6d3306-704f-4627-a460-3096fb8816f0","a7896a17-eb7a-4262-8557-914e109ff020","49bf9ecd-ae7a-44cd-8dcb-d6cc532fdc64"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_java_webservice_eshop","customerIds":["photon"],"used":false,"creationDate":1378365318734,"helpText":null,"system":true,"name":"Eshop","id":"d17d8389-3201-4a5f-888a-e5c0a0c68e18","displayName":null,"description":null,"status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365318718,"helpText":null,"system":false,"name":"Eshop J2EE - HTML5 JQuery Mobile Widget","id":"76e6bc0f-2521-40f2-a587-747ed701be54","displayName":"Eshop J2EE - HTML5 JQuery Mobile Widget","description":"Eshop J2EE - HTML5 JQuery Mobile Widget","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"version":"3.10.3","customerIds":null,"used":false,"creationDate":1354009203000,"helpText":null,"system":false,"name":"responsive-web-yui","id":"tech-html5","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608078,"helpText":null,"system":false,"name":null,"id":"fe0af5c1-0511-4659-9a67-d4fb576ca08d","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":["402ded74-e007-4cdf-8fe5-ee11ca01b7db","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","bb9b5d04-2afe-4722-b87b-c1d9cdefbf8e"],"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_html5_yui_multichannel_eshop","customerIds":["photon"],"used":false,"creationDate":1378365318859,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_html5_yui_multichannel_eshop","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":null,"version":"1.6","customerIds":null,"used":false,"creationDate":1355150953453,"helpText":null,"system":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608046,"helpText":null,"system":false,"name":null,"id":"b279980e-390b-4d07-ad26-72fce77f88bf","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608062,"helpText":null,"system":false,"name":null,"id":"c50e90ff-dedb-40b6-bdcf-3776a35e9b68","displayName":null,"description":null,"status":null}],"selectedModules":["8c978a8d-ebe0-4c18-8e11-5512c61488be","9e7cb57d-3b7d-46aa-9768-4c4ac19451cc","37701b35-aea1-456e-9de3-aa50f64b4705","04aea87f-3ccf-45dd-ad2f-e124a28755a9","9c7c7d7e-e0a8-4cbc-a98f-52504c7398bb","f31fa2af-602b-4eb6-954c-bd1af173017b","eaf60ba6-0259-4c19-b317-6d179585c497","f08fa865-c1ad-481c-b085-7c11b29966fb","173504a5-2c17-480d-af7f-9330dfbb0a5f","89cb6609-d5bc-412d-9291-8d63a5787931","283f9027-61ca-4b47-8a6b-3f2fba059aa7","0053ed62-5509-43d4-a213-bb9bdd1e98b0","8c6fcb31-b32b-4295-a443-414300c5431b","2d41a182-85f1-42a3-a67c-a0836792ba02","cae6a04c-9716-42ff-b730-f3e914d80924","519207c8-01d3-4e8a-9239-5b1c6ec78ad4","b49b6233-94ce-4c7f-802c-4e6d944ac3e4","3dddbeb8-a2b5-4b30-99f4-ddcebf69aa24","d15f5ed0-71d7-4193-9af8-f2316f203596","0286832e-c9f1-4b04-8014-caa923e78845","849eee10-758d-4d71-acf6-94a6653acab7","9b8e3c8c-902d-4c28-b716-b0efc93c0fb2","6ed21722-e683-4ec5-8a77-5f50cac908ae","a3133885-5528-487d-8346-12e8dc0ed7b1","4c5e64cd-263b-436d-abb1-4fe2de5c063b","6d3e706f-85af-4610-835c-eecae263bc5f","74134940-4284-47e8-89ba-56af8323bcae","dbb9ea5d-2a09-4747-93a9-cf5dcad5cdd9","bd317892-9e0a-43a3-95cd-e7dd3cb6a3e4","f20f6862-4a54-4384-845c-649d12aa94d7","d63b468e-8fa1-4690-afeb-852e80ea450d","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","5a5eb78f-f1de-4bfa-8ee6-1bccd5be29e5","98734103-53b2-43e4-a246-b9d9ebec2d30","b966ea3d-3ed0-47bb-8a0a-83cefc4ae41c","4888dbf7-2b73-46b7-b5c6-aa170bde0f8c","f9efa148-53c7-4e01-90b8-d9690121d9d9","f3968ed2-ce47-4396-8f91-5f7f8bb987b6","bc470e0d-f5a8-4725-8785-b1f35cff1c95","455d5918-35c2-4a00-bd9c-903fb533886f","3fbb4c66-2c8c-42f1-9f10-e98b884588f6","a8a705cb-5b5d-4891-9491-f7f0dd0d0d9a","2db250f7-b245-4a5b-bd20-15f4d588ca7b","8377b620-0de6-438b-b7a5-24dddc3149e1","9dda9452-3ee4-4e27-a583-060cf5ed51af","96c4daca-5136-47a9-8568-bf8b822b4000","b00ffc7f-2d2e-458f-a0c4-4fae63087567","e0603d8a-7f0e-4474-ad74-b66be8e217c6","24e6820d-a472-4488-8fdf-a510be5e2f53","105d4c61-91bb-45a5-848e-d961e2ec38be","ec95e483-8522-4dd5-9095-984886fda4b9","14d09a7a-26b9-4f3b-a4b9-75b95cb74501","91b006c7-555b-48d9-8b9c-04ea623a0195","b4ce2df7-71e7-4f34-bab2-d4f0ef3217e1","fea35cf5-94c2-41a6-a1b1-8093137d7a81","418dc224-1e27-4713-9c12-ee18264e4dfe","cb6d3306-704f-4627-a460-3096fb8816f0","a7896a17-eb7a-4262-8557-914e109ff020","49bf9ecd-ae7a-44cd-8dcb-d6cc532fdc64"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_java_webservice_eshop","customerIds":["photon"],"used":false,"creationDate":1378365318859,"helpText":null,"system":true,"name":"Eshop","id":"d17d8389-3201-4a5f-888a-e5c0a0c68e18","displayName":null,"description":null,"status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365318734,"helpText":null,"system":false,"name":"Eshop J2EE - HTML5 Multichannel YUI Widget","id":"ea22bd35-3472-47a5-b64e-93ff3013ce65","displayName":"Eshop J2EE - HTML5 Multichannel YUI Widget","description":"Eshop J2EE - HTML5 Multichannel YUI Widget","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"version":"3.10.3","customerIds":null,"used":false,"creationDate":1354009397609,"helpText":null,"system":false,"name":"mobile-web-yui","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"9702e0ad-b168-4fbd-a581-3b3d498afe25","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":["402ded74-e007-4cdf-8fe5-ee11ca01b7db","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","444cd5e9-6d16-4e38-8f95-c3f1d84f3c6e","bb9b5d04-2afe-4722-b87b-c1d9cdefbf8e","b3ae6065-1a44-4628-811d-0bd9f1bae6f0","39581ee3-871e-426f-8622-2fb7f12a7b79"],"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_html5_mobilewidget_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319250,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_html5_mobilewidget_eshop","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":null,"version":"1.6","customerIds":null,"used":false,"creationDate":1355150953453,"helpText":null,"system":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608046,"helpText":null,"system":false,"name":null,"id":"b279980e-390b-4d07-ad26-72fce77f88bf","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608062,"helpText":null,"system":false,"name":null,"id":"c50e90ff-dedb-40b6-bdcf-3776a35e9b68","displayName":null,"description":null,"status":null}],"selectedModules":["8c978a8d-ebe0-4c18-8e11-5512c61488be","9e7cb57d-3b7d-46aa-9768-4c4ac19451cc","37701b35-aea1-456e-9de3-aa50f64b4705","04aea87f-3ccf-45dd-ad2f-e124a28755a9","9c7c7d7e-e0a8-4cbc-a98f-52504c7398bb","f31fa2af-602b-4eb6-954c-bd1af173017b","eaf60ba6-0259-4c19-b317-6d179585c497","f08fa865-c1ad-481c-b085-7c11b29966fb","173504a5-2c17-480d-af7f-9330dfbb0a5f","89cb6609-d5bc-412d-9291-8d63a5787931","283f9027-61ca-4b47-8a6b-3f2fba059aa7","0053ed62-5509-43d4-a213-bb9bdd1e98b0","8c6fcb31-b32b-4295-a443-414300c5431b","2d41a182-85f1-42a3-a67c-a0836792ba02","cae6a04c-9716-42ff-b730-f3e914d80924","519207c8-01d3-4e8a-9239-5b1c6ec78ad4","b49b6233-94ce-4c7f-802c-4e6d944ac3e4","3dddbeb8-a2b5-4b30-99f4-ddcebf69aa24","d15f5ed0-71d7-4193-9af8-f2316f203596","0286832e-c9f1-4b04-8014-caa923e78845","849eee10-758d-4d71-acf6-94a6653acab7","9b8e3c8c-902d-4c28-b716-b0efc93c0fb2","6ed21722-e683-4ec5-8a77-5f50cac908ae","a3133885-5528-487d-8346-12e8dc0ed7b1","4c5e64cd-263b-436d-abb1-4fe2de5c063b","6d3e706f-85af-4610-835c-eecae263bc5f","74134940-4284-47e8-89ba-56af8323bcae","dbb9ea5d-2a09-4747-93a9-cf5dcad5cdd9","bd317892-9e0a-43a3-95cd-e7dd3cb6a3e4","f20f6862-4a54-4384-845c-649d12aa94d7","d63b468e-8fa1-4690-afeb-852e80ea450d","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","5a5eb78f-f1de-4bfa-8ee6-1bccd5be29e5","98734103-53b2-43e4-a246-b9d9ebec2d30","b966ea3d-3ed0-47bb-8a0a-83cefc4ae41c","4888dbf7-2b73-46b7-b5c6-aa170bde0f8c","f9efa148-53c7-4e01-90b8-d9690121d9d9","f3968ed2-ce47-4396-8f91-5f7f8bb987b6","bc470e0d-f5a8-4725-8785-b1f35cff1c95","455d5918-35c2-4a00-bd9c-903fb533886f","3fbb4c66-2c8c-42f1-9f10-e98b884588f6","a8a705cb-5b5d-4891-9491-f7f0dd0d0d9a","2db250f7-b245-4a5b-bd20-15f4d588ca7b","8377b620-0de6-438b-b7a5-24dddc3149e1","9dda9452-3ee4-4e27-a583-060cf5ed51af","96c4daca-5136-47a9-8568-bf8b822b4000","b00ffc7f-2d2e-458f-a0c4-4fae63087567","e0603d8a-7f0e-4474-ad74-b66be8e217c6","24e6820d-a472-4488-8fdf-a510be5e2f53","105d4c61-91bb-45a5-848e-d961e2ec38be","ec95e483-8522-4dd5-9095-984886fda4b9","14d09a7a-26b9-4f3b-a4b9-75b95cb74501","91b006c7-555b-48d9-8b9c-04ea623a0195","b4ce2df7-71e7-4f34-bab2-d4f0ef3217e1","fea35cf5-94c2-41a6-a1b1-8093137d7a81","418dc224-1e27-4713-9c12-ee18264e4dfe","cb6d3306-704f-4627-a460-3096fb8816f0","a7896a17-eb7a-4262-8557-914e109ff020","49bf9ecd-ae7a-44cd-8dcb-d6cc532fdc64"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_java_webservice_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319250,"helpText":null,"system":true,"name":"Eshop","id":"d17d8389-3201-4a5f-888a-e5c0a0c68e18","displayName":null,"description":null,"status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365319250,"helpText":null,"system":false,"name":"Eshop J2EE - HTML5 YUI Mobile Widget","id":"bcf30e7e-2683-49bc-85f0-e47433509628","displayName":"Eshop J2EE - HTML5 YUI Mobile Widget","description":"Eshop J2EE - HTML5 YUI Mobile Widget","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"version":"3.10.3","customerIds":null,"used":false,"creationDate":1354009498671,"helpText":null,"system":false,"name":"responsive-web","id":"tech-html5-jquery-widget","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608046,"helpText":null,"system":false,"name":null,"id":"1e22614f-0490-4600-9f2d-dba1d200e700","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":["402ded74-e007-4cdf-8fe5-ee11ca01b7db","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","444cd5e9-6d16-4e38-8f95-c3f1d84f3c6e","bb9b5d04-2afe-4722-b87b-c1d9cdefbf8e","c4a8d772-305e-441a-993e-703e63795aac"],"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_html5_multichannel_jquery_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319250,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_html5_multichannel_jquery_eshop","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":null,"version":"1.6","customerIds":null,"used":false,"creationDate":1355150953453,"helpText":null,"system":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608046,"helpText":null,"system":false,"name":null,"id":"b279980e-390b-4d07-ad26-72fce77f88bf","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608062,"helpText":null,"system":false,"name":null,"id":"c50e90ff-dedb-40b6-bdcf-3776a35e9b68","displayName":null,"description":null,"status":null}],"selectedModules":["8c978a8d-ebe0-4c18-8e11-5512c61488be","9e7cb57d-3b7d-46aa-9768-4c4ac19451cc","37701b35-aea1-456e-9de3-aa50f64b4705","04aea87f-3ccf-45dd-ad2f-e124a28755a9","9c7c7d7e-e0a8-4cbc-a98f-52504c7398bb","f31fa2af-602b-4eb6-954c-bd1af173017b","eaf60ba6-0259-4c19-b317-6d179585c497","f08fa865-c1ad-481c-b085-7c11b29966fb","173504a5-2c17-480d-af7f-9330dfbb0a5f","89cb6609-d5bc-412d-9291-8d63a5787931","283f9027-61ca-4b47-8a6b-3f2fba059aa7","0053ed62-5509-43d4-a213-bb9bdd1e98b0","8c6fcb31-b32b-4295-a443-414300c5431b","2d41a182-85f1-42a3-a67c-a0836792ba02","cae6a04c-9716-42ff-b730-f3e914d80924","519207c8-01d3-4e8a-9239-5b1c6ec78ad4","b49b6233-94ce-4c7f-802c-4e6d944ac3e4","3dddbeb8-a2b5-4b30-99f4-ddcebf69aa24","d15f5ed0-71d7-4193-9af8-f2316f203596","0286832e-c9f1-4b04-8014-caa923e78845","849eee10-758d-4d71-acf6-94a6653acab7","9b8e3c8c-902d-4c28-b716-b0efc93c0fb2","6ed21722-e683-4ec5-8a77-5f50cac908ae","a3133885-5528-487d-8346-12e8dc0ed7b1","4c5e64cd-263b-436d-abb1-4fe2de5c063b","6d3e706f-85af-4610-835c-eecae263bc5f","74134940-4284-47e8-89ba-56af8323bcae","dbb9ea5d-2a09-4747-93a9-cf5dcad5cdd9","bd317892-9e0a-43a3-95cd-e7dd3cb6a3e4","f20f6862-4a54-4384-845c-649d12aa94d7","d63b468e-8fa1-4690-afeb-852e80ea450d","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","5a5eb78f-f1de-4bfa-8ee6-1bccd5be29e5","98734103-53b2-43e4-a246-b9d9ebec2d30","b966ea3d-3ed0-47bb-8a0a-83cefc4ae41c","4888dbf7-2b73-46b7-b5c6-aa170bde0f8c","f9efa148-53c7-4e01-90b8-d9690121d9d9","f3968ed2-ce47-4396-8f91-5f7f8bb987b6","bc470e0d-f5a8-4725-8785-b1f35cff1c95","455d5918-35c2-4a00-bd9c-903fb533886f","3fbb4c66-2c8c-42f1-9f10-e98b884588f6","a8a705cb-5b5d-4891-9491-f7f0dd0d0d9a","2db250f7-b245-4a5b-bd20-15f4d588ca7b","8377b620-0de6-438b-b7a5-24dddc3149e1","9dda9452-3ee4-4e27-a583-060cf5ed51af","96c4daca-5136-47a9-8568-bf8b822b4000","b00ffc7f-2d2e-458f-a0c4-4fae63087567","e0603d8a-7f0e-4474-ad74-b66be8e217c6","24e6820d-a472-4488-8fdf-a510be5e2f53","105d4c61-91bb-45a5-848e-d961e2ec38be","ec95e483-8522-4dd5-9095-984886fda4b9","14d09a7a-26b9-4f3b-a4b9-75b95cb74501","91b006c7-555b-48d9-8b9c-04ea623a0195","b4ce2df7-71e7-4f34-bab2-d4f0ef3217e1","fea35cf5-94c2-41a6-a1b1-8093137d7a81","418dc224-1e27-4713-9c12-ee18264e4dfe","cb6d3306-704f-4627-a460-3096fb8816f0","a7896a17-eb7a-4262-8557-914e109ff020","49bf9ecd-ae7a-44cd-8dcb-d6cc532fdc64"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_java_webservice_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319250,"helpText":null,"system":true,"name":"Eshop","id":"d17d8389-3201-4a5f-888a-e5c0a0c68e18","displayName":null,"description":null,"status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365319250,"helpText":null,"system":false,"name":"Eshop J2EE - HTML5 Multichannel JQuery Widget","id":"c2794c55-8962-4e26-8ffa-395af2f82699","displayName":"Eshop J2EE - HTML5 Multichannel JQuery Widget","description":"Eshop J2EE - HTML5 Multichannel JQuery Widget","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":null,"version":"4.1","customerIds":null,"used":false,"creationDate":1365682607000,"helpText":null,"system":false,"name":"mobile-app-android","id":"tech-android-native","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":["aa20aac1-06aa-4ad5-85c2-106179666acc"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHR_androidnative_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319359,"helpText":null,"system":true,"name":"EShop","id":"PHR_androidnative_eshop","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":null,"version":"1.6","customerIds":null,"used":false,"creationDate":1355150953453,"helpText":null,"system":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608046,"helpText":null,"system":false,"name":null,"id":"b279980e-390b-4d07-ad26-72fce77f88bf","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608062,"helpText":null,"system":false,"name":null,"id":"c50e90ff-dedb-40b6-bdcf-3776a35e9b68","displayName":null,"description":null,"status":null}],"selectedModules":["8c978a8d-ebe0-4c18-8e11-5512c61488be","9e7cb57d-3b7d-46aa-9768-4c4ac19451cc","37701b35-aea1-456e-9de3-aa50f64b4705","04aea87f-3ccf-45dd-ad2f-e124a28755a9","9c7c7d7e-e0a8-4cbc-a98f-52504c7398bb","f31fa2af-602b-4eb6-954c-bd1af173017b","eaf60ba6-0259-4c19-b317-6d179585c497","f08fa865-c1ad-481c-b085-7c11b29966fb","173504a5-2c17-480d-af7f-9330dfbb0a5f","89cb6609-d5bc-412d-9291-8d63a5787931","283f9027-61ca-4b47-8a6b-3f2fba059aa7","0053ed62-5509-43d4-a213-bb9bdd1e98b0","8c6fcb31-b32b-4295-a443-414300c5431b","2d41a182-85f1-42a3-a67c-a0836792ba02","cae6a04c-9716-42ff-b730-f3e914d80924","519207c8-01d3-4e8a-9239-5b1c6ec78ad4","b49b6233-94ce-4c7f-802c-4e6d944ac3e4","3dddbeb8-a2b5-4b30-99f4-ddcebf69aa24","d15f5ed0-71d7-4193-9af8-f2316f203596","0286832e-c9f1-4b04-8014-caa923e78845","849eee10-758d-4d71-acf6-94a6653acab7","9b8e3c8c-902d-4c28-b716-b0efc93c0fb2","6ed21722-e683-4ec5-8a77-5f50cac908ae","a3133885-5528-487d-8346-12e8dc0ed7b1","4c5e64cd-263b-436d-abb1-4fe2de5c063b","6d3e706f-85af-4610-835c-eecae263bc5f","74134940-4284-47e8-89ba-56af8323bcae","dbb9ea5d-2a09-4747-93a9-cf5dcad5cdd9","bd317892-9e0a-43a3-95cd-e7dd3cb6a3e4","f20f6862-4a54-4384-845c-649d12aa94d7","d63b468e-8fa1-4690-afeb-852e80ea450d","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","5a5eb78f-f1de-4bfa-8ee6-1bccd5be29e5","98734103-53b2-43e4-a246-b9d9ebec2d30","b966ea3d-3ed0-47bb-8a0a-83cefc4ae41c","4888dbf7-2b73-46b7-b5c6-aa170bde0f8c","f9efa148-53c7-4e01-90b8-d9690121d9d9","f3968ed2-ce47-4396-8f91-5f7f8bb987b6","bc470e0d-f5a8-4725-8785-b1f35cff1c95","455d5918-35c2-4a00-bd9c-903fb533886f","3fbb4c66-2c8c-42f1-9f10-e98b884588f6","a8a705cb-5b5d-4891-9491-f7f0dd0d0d9a","2db250f7-b245-4a5b-bd20-15f4d588ca7b","8377b620-0de6-438b-b7a5-24dddc3149e1","9dda9452-3ee4-4e27-a583-060cf5ed51af","96c4daca-5136-47a9-8568-bf8b822b4000","b00ffc7f-2d2e-458f-a0c4-4fae63087567","e0603d8a-7f0e-4474-ad74-b66be8e217c6","24e6820d-a472-4488-8fdf-a510be5e2f53","105d4c61-91bb-45a5-848e-d961e2ec38be","ec95e483-8522-4dd5-9095-984886fda4b9","14d09a7a-26b9-4f3b-a4b9-75b95cb74501","91b006c7-555b-48d9-8b9c-04ea623a0195","b4ce2df7-71e7-4f34-bab2-d4f0ef3217e1","fea35cf5-94c2-41a6-a1b1-8093137d7a81","418dc224-1e27-4713-9c12-ee18264e4dfe","cb6d3306-704f-4627-a460-3096fb8816f0","a7896a17-eb7a-4262-8557-914e109ff020","49bf9ecd-ae7a-44cd-8dcb-d6cc532fdc64"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_java_webservice_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319359,"helpText":null,"system":true,"name":"Eshop","id":"d17d8389-3201-4a5f-888a-e5c0a0c68e18","displayName":null,"description":null,"status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365319250,"helpText":null,"system":false,"name":"Eshop J2EE - Android Native","id":"cf05fc0d-8375-4b54-98ad-1e62381417ff","displayName":"Eshop J2EE - Android Native","description":"Eshop J2EE - Android Native","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":null,"used":false,"creationDate":1365682607000,"helpText":null,"system":false,"name":"mobile-app-iOS","id":"tech-iphone-native","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":["53d1769b-e944-4e6d-bbc9-20743f114ffe"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_iPhone_native_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319359,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_iPhone_native_eshop","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":null,"version":"1.6","customerIds":null,"used":false,"creationDate":1355150953453,"helpText":null,"system":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608046,"helpText":null,"system":false,"name":null,"id":"b279980e-390b-4d07-ad26-72fce77f88bf","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608062,"helpText":null,"system":false,"name":null,"id":"c50e90ff-dedb-40b6-bdcf-3776a35e9b68","displayName":null,"description":null,"status":null}],"selectedModules":["8c978a8d-ebe0-4c18-8e11-5512c61488be","9e7cb57d-3b7d-46aa-9768-4c4ac19451cc","37701b35-aea1-456e-9de3-aa50f64b4705","04aea87f-3ccf-45dd-ad2f-e124a28755a9","9c7c7d7e-e0a8-4cbc-a98f-52504c7398bb","f31fa2af-602b-4eb6-954c-bd1af173017b","eaf60ba6-0259-4c19-b317-6d179585c497","f08fa865-c1ad-481c-b085-7c11b29966fb","173504a5-2c17-480d-af7f-9330dfbb0a5f","89cb6609-d5bc-412d-9291-8d63a5787931","283f9027-61ca-4b47-8a6b-3f2fba059aa7","0053ed62-5509-43d4-a213-bb9bdd1e98b0","8c6fcb31-b32b-4295-a443-414300c5431b","2d41a182-85f1-42a3-a67c-a0836792ba02","cae6a04c-9716-42ff-b730-f3e914d80924","519207c8-01d3-4e8a-9239-5b1c6ec78ad4","b49b6233-94ce-4c7f-802c-4e6d944ac3e4","3dddbeb8-a2b5-4b30-99f4-ddcebf69aa24","d15f5ed0-71d7-4193-9af8-f2316f203596","0286832e-c9f1-4b04-8014-caa923e78845","849eee10-758d-4d71-acf6-94a6653acab7","9b8e3c8c-902d-4c28-b716-b0efc93c0fb2","6ed21722-e683-4ec5-8a77-5f50cac908ae","a3133885-5528-487d-8346-12e8dc0ed7b1","4c5e64cd-263b-436d-abb1-4fe2de5c063b","6d3e706f-85af-4610-835c-eecae263bc5f","74134940-4284-47e8-89ba-56af8323bcae","dbb9ea5d-2a09-4747-93a9-cf5dcad5cdd9","bd317892-9e0a-43a3-95cd-e7dd3cb6a3e4","f20f6862-4a54-4384-845c-649d12aa94d7","d63b468e-8fa1-4690-afeb-852e80ea450d","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","5a5eb78f-f1de-4bfa-8ee6-1bccd5be29e5","98734103-53b2-43e4-a246-b9d9ebec2d30","b966ea3d-3ed0-47bb-8a0a-83cefc4ae41c","4888dbf7-2b73-46b7-b5c6-aa170bde0f8c","f9efa148-53c7-4e01-90b8-d9690121d9d9","f3968ed2-ce47-4396-8f91-5f7f8bb987b6","bc470e0d-f5a8-4725-8785-b1f35cff1c95","455d5918-35c2-4a00-bd9c-903fb533886f","3fbb4c66-2c8c-42f1-9f10-e98b884588f6","a8a705cb-5b5d-4891-9491-f7f0dd0d0d9a","2db250f7-b245-4a5b-bd20-15f4d588ca7b","8377b620-0de6-438b-b7a5-24dddc3149e1","9dda9452-3ee4-4e27-a583-060cf5ed51af","96c4daca-5136-47a9-8568-bf8b822b4000","b00ffc7f-2d2e-458f-a0c4-4fae63087567","e0603d8a-7f0e-4474-ad74-b66be8e217c6","24e6820d-a472-4488-8fdf-a510be5e2f53","105d4c61-91bb-45a5-848e-d961e2ec38be","ec95e483-8522-4dd5-9095-984886fda4b9","14d09a7a-26b9-4f3b-a4b9-75b95cb74501","91b006c7-555b-48d9-8b9c-04ea623a0195","b4ce2df7-71e7-4f34-bab2-d4f0ef3217e1","fea35cf5-94c2-41a6-a1b1-8093137d7a81","418dc224-1e27-4713-9c12-ee18264e4dfe","cb6d3306-704f-4627-a460-3096fb8816f0","a7896a17-eb7a-4262-8557-914e109ff020","49bf9ecd-ae7a-44cd-8dcb-d6cc532fdc64"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_java_webservice_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319359,"helpText":null,"system":true,"name":"Eshop","id":"d17d8389-3201-4a5f-888a-e5c0a0c68e18","displayName":null,"description":null,"status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365319359,"helpText":null,"system":false,"name":"Eshop J2EE - Iphone Native","id":"a3442af3-0af0-463c-956f-93606b31fe85","displayName":"Eshop J2EE - Iphone Native","description":"Eshop J2EE - Iphone Native","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":null,"version":"0.10.9","customerIds":null,"used":false,"creationDate":1365682608015,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_nodejs","artifactInfoIds":["bea244f4-ddad-4491-9f43-5252696673c1"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"8a5ea2d3-7436-493f-9615-62e141cd7762","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"fad54cb4-2623-4de4-9a33-203008484935","displayName":null,"description":null,"status":null}],"selectedModules":["6702b6e1-6d43-4ec9-8168-3ef0755b31f3","d424aaea-b3f5-470e-bcc1-73635e5d2072","f70f8a19-b773-420d-b216-9624a41bba4d","6426a7fd-1e8e-4e8f-9d36-6ee68318b20b","c8ec21ad-2b13-40c0-b367-1204dbddf428","ef823d56-f3ac-414d-9a2c-8e59d48268a6","d7348c54-3a59-4586-8936-e914e3447249","db50e786-bd9f-4cf5-9078-2c22a6782ee8","da034bec-8680-43ac-99d0-38bec5fc97d1","5945b54a-6de5-4dd3-859a-4d082146838a","39f17066-9e9c-4943-857c-22ad3bab5086","5f5aca9b-1212-42e6-b77f-822a8b24ca62","045329d0-e519-4650-9dba-a64a6bf0ab2c","43653f0f-ef0b-46ea-8204-2ff6c41d22d4","f9511e46-bbd7-4910-a40a-1bc7a12317a5"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_NODEJS_eshop-WebService","customerIds":["photon"],"used":false,"creationDate":1378365319359,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_NODEJS_eshop-WebService","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":null,"used":false,"creationDate":1365682607000,"helpText":null,"system":false,"name":"mobile-app-iOS","id":"tech-iphone-native","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":["53d1769b-e944-4e6d-bbc9-20743f114ffe"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_iPhone_native_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319359,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_iPhone_native_eshop","displayName":null,"description":"","status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365319359,"helpText":null,"system":false,"name":"Eshop Nodejs Webservice - Iphone Native","id":"c6d67992-b185-45c6-a607-9e5a3a8d7d25","displayName":"Eshop Nodejs Webservice - Iphone Native","description":"Eshop Nodejs Webservice - Iphone Native","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":null,"version":"4.1","customerIds":null,"used":false,"creationDate":1365682607000,"helpText":null,"system":false,"name":"mobile-app-android","id":"tech-android-native","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":["aa20aac1-06aa-4ad5-85c2-106179666acc"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHR_androidnative_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319375,"helpText":null,"system":true,"name":"EShop","id":"PHR_androidnative_eshop","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":null,"version":"0.10.9","customerIds":null,"used":false,"creationDate":1365682608015,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_nodejs","artifactInfoIds":["bea244f4-ddad-4491-9f43-5252696673c1"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"8a5ea2d3-7436-493f-9615-62e141cd7762","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"fad54cb4-2623-4de4-9a33-203008484935","displayName":null,"description":null,"status":null}],"selectedModules":["6702b6e1-6d43-4ec9-8168-3ef0755b31f3","d424aaea-b3f5-470e-bcc1-73635e5d2072","f70f8a19-b773-420d-b216-9624a41bba4d","6426a7fd-1e8e-4e8f-9d36-6ee68318b20b","c8ec21ad-2b13-40c0-b367-1204dbddf428","ef823d56-f3ac-414d-9a2c-8e59d48268a6","d7348c54-3a59-4586-8936-e914e3447249","db50e786-bd9f-4cf5-9078-2c22a6782ee8","da034bec-8680-43ac-99d0-38bec5fc97d1","5945b54a-6de5-4dd3-859a-4d082146838a","39f17066-9e9c-4943-857c-22ad3bab5086","5f5aca9b-1212-42e6-b77f-822a8b24ca62","045329d0-e519-4650-9dba-a64a6bf0ab2c","43653f0f-ef0b-46ea-8204-2ff6c41d22d4","f9511e46-bbd7-4910-a40a-1bc7a12317a5"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_NODEJS_eshop-WebService","customerIds":["photon"],"used":false,"creationDate":1378365319375,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_NODEJS_eshop-WebService","displayName":null,"description":"","status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365319375,"helpText":null,"system":false,"name":"Eshop Nodejs Webservice - Android Native","id":"c33a7024-39ff-4ddf-afff-b7e1d3035fd3","displayName":"Eshop Nodejs Webservice - Android Native","description":"Eshop Nodejs Webservice - Android Native","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":null,"version":"4.1","customerIds":null,"used":false,"creationDate":1365682608078,"helpText":null,"system":false,"name":"hybrid-app-android","id":"tech-android-hybrid","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608062,"helpText":null,"system":false,"name":null,"id":"d4f5a2cc-a503-42ab-bc9d-4dc74867151b","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":["aa20aac1-06aa-4ad5-85c2-106179666acc"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHR_androidhybrid_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319375,"helpText":null,"system":true,"name":"EShop","id":"PHR_androidhybrid_eshop","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":null,"version":"0.10.9","customerIds":null,"used":false,"creationDate":1365682608015,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_nodejs","artifactInfoIds":["bea244f4-ddad-4491-9f43-5252696673c1"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"8a5ea2d3-7436-493f-9615-62e141cd7762","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"fad54cb4-2623-4de4-9a33-203008484935","displayName":null,"description":null,"status":null}],"selectedModules":["6702b6e1-6d43-4ec9-8168-3ef0755b31f3","d424aaea-b3f5-470e-bcc1-73635e5d2072","f70f8a19-b773-420d-b216-9624a41bba4d","6426a7fd-1e8e-4e8f-9d36-6ee68318b20b","c8ec21ad-2b13-40c0-b367-1204dbddf428","ef823d56-f3ac-414d-9a2c-8e59d48268a6","d7348c54-3a59-4586-8936-e914e3447249","db50e786-bd9f-4cf5-9078-2c22a6782ee8","da034bec-8680-43ac-99d0-38bec5fc97d1","5945b54a-6de5-4dd3-859a-4d082146838a","39f17066-9e9c-4943-857c-22ad3bab5086","5f5aca9b-1212-42e6-b77f-822a8b24ca62","045329d0-e519-4650-9dba-a64a6bf0ab2c","43653f0f-ef0b-46ea-8204-2ff6c41d22d4","f9511e46-bbd7-4910-a40a-1bc7a12317a5"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_NODEJS_eshop-WebService","customerIds":["photon"],"used":false,"creationDate":1378365319375,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_NODEJS_eshop-WebService","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"version":"3.10.3","customerIds":null,"used":false,"creationDate":1354009397609,"helpText":null,"system":false,"name":"mobile-web-yui","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"9702e0ad-b168-4fbd-a581-3b3d498afe25","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":["402ded74-e007-4cdf-8fe5-ee11ca01b7db","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","444cd5e9-6d16-4e38-8f95-c3f1d84f3c6e","bb9b5d04-2afe-4722-b87b-c1d9cdefbf8e","b3ae6065-1a44-4628-811d-0bd9f1bae6f0","39581ee3-871e-426f-8622-2fb7f12a7b79"],"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_html5_mobilewidget_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319375,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_html5_mobilewidget_eshop","displayName":null,"description":null,"status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365319375,"helpText":null,"system":false,"name":"Eshop Nodejs Webservice - HTML5 YUI Mobile Widget - Android Hybrid","id":"65abbcb3-3fe5-4f47-8e01-f7cef8f62e90","displayName":"Eshop Nodejs Webservice - HTML5 YUI Mobile Widget - Android Hybrid","description":"Eshop Nodejs Webservice - HTML5 YUI Mobile Widget - Android Hybrid","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":null,"version":"0.10.9","customerIds":null,"used":false,"creationDate":1365682608015,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_nodejs","artifactInfoIds":["bea244f4-ddad-4491-9f43-5252696673c1"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"8a5ea2d3-7436-493f-9615-62e141cd7762","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"fad54cb4-2623-4de4-9a33-203008484935","displayName":null,"description":null,"status":null}],"selectedModules":["6702b6e1-6d43-4ec9-8168-3ef0755b31f3","d424aaea-b3f5-470e-bcc1-73635e5d2072","f70f8a19-b773-420d-b216-9624a41bba4d","6426a7fd-1e8e-4e8f-9d36-6ee68318b20b","c8ec21ad-2b13-40c0-b367-1204dbddf428","ef823d56-f3ac-414d-9a2c-8e59d48268a6","d7348c54-3a59-4586-8936-e914e3447249","db50e786-bd9f-4cf5-9078-2c22a6782ee8","da034bec-8680-43ac-99d0-38bec5fc97d1","5945b54a-6de5-4dd3-859a-4d082146838a","39f17066-9e9c-4943-857c-22ad3bab5086","5f5aca9b-1212-42e6-b77f-822a8b24ca62","045329d0-e519-4650-9dba-a64a6bf0ab2c","43653f0f-ef0b-46ea-8204-2ff6c41d22d4","f9511e46-bbd7-4910-a40a-1bc7a12317a5"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_NODEJS_eshop-WebService","customerIds":["photon"],"used":false,"creationDate":1378365319406,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_NODEJS_eshop-WebService","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"version":"3.10.3","customerIds":null,"used":false,"creationDate":1354009397609,"helpText":null,"system":false,"name":"mobile-web-yui","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"9702e0ad-b168-4fbd-a581-3b3d498afe25","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":["402ded74-e007-4cdf-8fe5-ee11ca01b7db","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","444cd5e9-6d16-4e38-8f95-c3f1d84f3c6e","bb9b5d04-2afe-4722-b87b-c1d9cdefbf8e","b3ae6065-1a44-4628-811d-0bd9f1bae6f0","39581ee3-871e-426f-8622-2fb7f12a7b79"],"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_html5_mobilewidget_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319406,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_html5_mobilewidget_eshop","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":null,"used":false,"creationDate":1365682608000,"helpText":null,"system":false,"name":"hybrid-app-iOS","id":"tech-iphone-hybrid","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"9f61f4c0-1deb-4793-9f2c-7b8522c0a2b6","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":["4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","b3ae6065-1a44-4628-811d-0bd9f1bae6f0"],"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_iPhone_hybrid_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319406,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_iPhone_hybrid_eshop","displayName":null,"description":"","status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365319375,"helpText":null,"system":false,"name":"Eshop Nodejs Webservice - HTML5 YUI Mobile Widget - Iphone Hybrid","id":"55afcd3b-d1cb-49e3-974a-002d073703a7","displayName":"Eshop Nodejs Webservice - HTML5 YUI Mobile Widget - Iphone Hybrid","description":"Eshop Nodejs Webservice - HTML5 YUI Mobile Widget - Iphone Hybrid","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":null,"version":"0.10.9","customerIds":null,"used":false,"creationDate":1365682608015,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_nodejs","artifactInfoIds":["bea244f4-ddad-4491-9f43-5252696673c1"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"8a5ea2d3-7436-493f-9615-62e141cd7762","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"fad54cb4-2623-4de4-9a33-203008484935","displayName":null,"description":null,"status":null}],"selectedModules":["6702b6e1-6d43-4ec9-8168-3ef0755b31f3","d424aaea-b3f5-470e-bcc1-73635e5d2072","f70f8a19-b773-420d-b216-9624a41bba4d","6426a7fd-1e8e-4e8f-9d36-6ee68318b20b","c8ec21ad-2b13-40c0-b367-1204dbddf428","ef823d56-f3ac-414d-9a2c-8e59d48268a6","d7348c54-3a59-4586-8936-e914e3447249","db50e786-bd9f-4cf5-9078-2c22a6782ee8","da034bec-8680-43ac-99d0-38bec5fc97d1","5945b54a-6de5-4dd3-859a-4d082146838a","39f17066-9e9c-4943-857c-22ad3bab5086","5f5aca9b-1212-42e6-b77f-822a8b24ca62","045329d0-e519-4650-9dba-a64a6bf0ab2c","43653f0f-ef0b-46ea-8204-2ff6c41d22d4","f9511e46-bbd7-4910-a40a-1bc7a12317a5"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_NODEJS_eshop-WebService","customerIds":["photon"],"used":false,"creationDate":1378365319406,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_NODEJS_eshop-WebService","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"version":"3.10.3","customerIds":null,"used":false,"creationDate":1354009203828,"helpText":null,"system":false,"name":"mobile-web","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"523c8806-86a8-4e61-937f-f27c8b32aa5c","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":["402ded74-e007-4cdf-8fe5-ee11ca01b7db","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","444cd5e9-6d16-4e38-8f95-c3f1d84f3c6e","bb9b5d04-2afe-4722-b87b-c1d9cdefbf8e","c4a8d772-305e-441a-993e-703e63795aac"],"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_html5_jquery_mobilewidget_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319406,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_html5_jquery_mobilewidget_eshop","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":null,"used":false,"creationDate":1365682608000,"helpText":null,"system":false,"name":"hybrid-app-iOS","id":"tech-iphone-hybrid","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"9f61f4c0-1deb-4793-9f2c-7b8522c0a2b6","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":["4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","b3ae6065-1a44-4628-811d-0bd9f1bae6f0"],"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_iPhone_hybrid_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319406,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_iPhone_hybrid_eshop","displayName":null,"description":"","status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365319406,"helpText":null,"system":false,"name":"Eshop Nodejs Webservice - HTML5 JQuery Mobile Widget - Iphone Hybrid","id":"73e6490b-ebdb-4401-a625-ba6b006a8632","displayName":"Eshop Nodejs Webservice - HTML5 JQuery Mobile Widget - Iphone Hybrid","description":"Eshop Nodejs Webservice - HTML5 JQuery Mobile Widget - Iphone Hybrid","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":null,"version":"4.1","customerIds":null,"used":false,"creationDate":1365682608078,"helpText":null,"system":false,"name":"hybrid-app-android","id":"tech-android-hybrid","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608062,"helpText":null,"system":false,"name":null,"id":"d4f5a2cc-a503-42ab-bc9d-4dc74867151b","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":["aa20aac1-06aa-4ad5-85c2-106179666acc"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHR_androidhybrid_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319421,"helpText":null,"system":true,"name":"EShop","id":"PHR_androidhybrid_eshop","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":null,"version":"0.10.9","customerIds":null,"used":false,"creationDate":1365682608015,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_nodejs","artifactInfoIds":["bea244f4-ddad-4491-9f43-5252696673c1"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"8a5ea2d3-7436-493f-9615-62e141cd7762","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"fad54cb4-2623-4de4-9a33-203008484935","displayName":null,"description":null,"status":null}],"selectedModules":["6702b6e1-6d43-4ec9-8168-3ef0755b31f3","d424aaea-b3f5-470e-bcc1-73635e5d2072","f70f8a19-b773-420d-b216-9624a41bba4d","6426a7fd-1e8e-4e8f-9d36-6ee68318b20b","c8ec21ad-2b13-40c0-b367-1204dbddf428","ef823d56-f3ac-414d-9a2c-8e59d48268a6","d7348c54-3a59-4586-8936-e914e3447249","db50e786-bd9f-4cf5-9078-2c22a6782ee8","da034bec-8680-43ac-99d0-38bec5fc97d1","5945b54a-6de5-4dd3-859a-4d082146838a","39f17066-9e9c-4943-857c-22ad3bab5086","5f5aca9b-1212-42e6-b77f-822a8b24ca62","045329d0-e519-4650-9dba-a64a6bf0ab2c","43653f0f-ef0b-46ea-8204-2ff6c41d22d4","f9511e46-bbd7-4910-a40a-1bc7a12317a5"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_NODEJS_eshop-WebService","customerIds":["photon"],"used":false,"creationDate":1378365319421,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_NODEJS_eshop-WebService","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"version":"3.10.3","customerIds":null,"used":false,"creationDate":1354009203828,"helpText":null,"system":false,"name":"mobile-web","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"523c8806-86a8-4e61-937f-f27c8b32aa5c","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":["402ded74-e007-4cdf-8fe5-ee11ca01b7db","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","444cd5e9-6d16-4e38-8f95-c3f1d84f3c6e","bb9b5d04-2afe-4722-b87b-c1d9cdefbf8e","c4a8d772-305e-441a-993e-703e63795aac"],"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_html5_jquery_mobilewidget_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319421,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_html5_jquery_mobilewidget_eshop","displayName":null,"description":null,"status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365319406,"helpText":null,"system":false,"name":"Eshop Nodejs Webservice - HTML5 JQuery Mobile Widget - Android Hybrid","id":"641a83ae-a49f-425e-a3b4-4029b6c6855f","displayName":"Eshop Nodejs Webservice - HTML5 JQuery Mobile Widget - Android Hybrid","description":"Eshop Nodejs Webservice - HTML5 JQuery Mobile Widget - Android Hybrid","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":null,"version":"0.10.9","customerIds":null,"used":false,"creationDate":1365682608015,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_nodejs","artifactInfoIds":["bea244f4-ddad-4491-9f43-5252696673c1"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"8a5ea2d3-7436-493f-9615-62e141cd7762","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"fad54cb4-2623-4de4-9a33-203008484935","displayName":null,"description":null,"status":null}],"selectedModules":["6702b6e1-6d43-4ec9-8168-3ef0755b31f3","d424aaea-b3f5-470e-bcc1-73635e5d2072","f70f8a19-b773-420d-b216-9624a41bba4d","6426a7fd-1e8e-4e8f-9d36-6ee68318b20b","c8ec21ad-2b13-40c0-b367-1204dbddf428","ef823d56-f3ac-414d-9a2c-8e59d48268a6","d7348c54-3a59-4586-8936-e914e3447249","db50e786-bd9f-4cf5-9078-2c22a6782ee8","da034bec-8680-43ac-99d0-38bec5fc97d1","5945b54a-6de5-4dd3-859a-4d082146838a","39f17066-9e9c-4943-857c-22ad3bab5086","5f5aca9b-1212-42e6-b77f-822a8b24ca62","045329d0-e519-4650-9dba-a64a6bf0ab2c","43653f0f-ef0b-46ea-8204-2ff6c41d22d4","f9511e46-bbd7-4910-a40a-1bc7a12317a5"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_NODEJS_eshop-WebService","customerIds":["photon"],"used":false,"creationDate":1378365319421,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_NODEJS_eshop-WebService","displayName":null,"description":"","status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365319421,"helpText":null,"system":false,"name":"Eshop Nodejs Webservice","id":"12ad9896-1659-495b-ae15-27b4674b34040","displayName":"Eshop Nodejs Webservice","description":"Eshop Nodejs Webservice","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":null,"version":"4.1","customerIds":null,"used":false,"creationDate":1365682607000,"helpText":null,"system":false,"name":"mobile-app-android","id":"tech-android-native","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":["aa20aac1-06aa-4ad5-85c2-106179666acc"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHR_androidnative_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319421,"helpText":null,"system":true,"name":"EShop","id":"PHR_androidnative_eshop","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":null,"version":"0.10.9","customerIds":null,"used":false,"creationDate":1365682608015,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_nodejs","artifactInfoIds":["bea244f4-ddad-4491-9f43-5252696673c1"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"8a5ea2d3-7436-493f-9615-62e141cd7762","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"fad54cb4-2623-4de4-9a33-203008484935","displayName":null,"description":null,"status":null}],"selectedModules":["6702b6e1-6d43-4ec9-8168-3ef0755b31f3","d424aaea-b3f5-470e-bcc1-73635e5d2072","f70f8a19-b773-420d-b216-9624a41bba4d","6426a7fd-1e8e-4e8f-9d36-6ee68318b20b","c8ec21ad-2b13-40c0-b367-1204dbddf428","ef823d56-f3ac-414d-9a2c-8e59d48268a6","d7348c54-3a59-4586-8936-e914e3447249","db50e786-bd9f-4cf5-9078-2c22a6782ee8","da034bec-8680-43ac-99d0-38bec5fc97d1","5945b54a-6de5-4dd3-859a-4d082146838a","39f17066-9e9c-4943-857c-22ad3bab5086","5f5aca9b-1212-42e6-b77f-822a8b24ca62","045329d0-e519-4650-9dba-a64a6bf0ab2c","43653f0f-ef0b-46ea-8204-2ff6c41d22d4","f9511e46-bbd7-4910-a40a-1bc7a12317a5"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_NODEJS_eshop-WebService","customerIds":["photon"],"used":false,"creationDate":1378365319421,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_NODEJS_eshop-WebService","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":null,"used":false,"creationDate":1365682607000,"helpText":null,"system":false,"name":"mobile-app-iOS","id":"tech-iphone-native","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":["53d1769b-e944-4e6d-bbc9-20743f114ffe"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_iPhone_native_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319421,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_iPhone_native_eshop","displayName":null,"description":"","status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365319421,"helpText":null,"system":false,"name":"Eshop Nodejs Webservice - Android Native - Iphone Native","id":"641a83ae-a49f-425e-a3b4-4029b6c6s855f","displayName":"Eshop Nodejs Webservice - Android Native - Iphone Native","description":"Eshop Nodejs Webservice - Android Native - Iphone Native","status":null},{"appInfos":[{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":null,"version":"4.1","customerIds":null,"used":false,"creationDate":1365682608078,"helpText":null,"system":false,"name":"hybrid-app-android","id":"tech-android-hybrid","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608062,"helpText":null,"system":false,"name":null,"id":"d4f5a2cc-a503-42ab-bc9d-4dc74867151b","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":["aa20aac1-06aa-4ad5-85c2-106179666acc"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHR_androidhybrid_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319484,"helpText":null,"system":true,"name":"EShop","id":"PHR_androidhybrid_eshop","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":null,"version":"0.10.9","customerIds":null,"used":false,"creationDate":1365682608015,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_nodejs","artifactInfoIds":["bea244f4-ddad-4491-9f43-5252696673c1"],"creationDate":1365682608015,"helpText":null,"system":false,"name":null,"id":"8a5ea2d3-7436-493f-9615-62e141cd7762","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"fad54cb4-2623-4de4-9a33-203008484935","displayName":null,"description":null,"status":null}],"selectedModules":["6702b6e1-6d43-4ec9-8168-3ef0755b31f3","d424aaea-b3f5-470e-bcc1-73635e5d2072","f70f8a19-b773-420d-b216-9624a41bba4d","6426a7fd-1e8e-4e8f-9d36-6ee68318b20b","c8ec21ad-2b13-40c0-b367-1204dbddf428","ef823d56-f3ac-414d-9a2c-8e59d48268a6","d7348c54-3a59-4586-8936-e914e3447249","db50e786-bd9f-4cf5-9078-2c22a6782ee8","da034bec-8680-43ac-99d0-38bec5fc97d1","5945b54a-6de5-4dd3-859a-4d082146838a","39f17066-9e9c-4943-857c-22ad3bab5086","5f5aca9b-1212-42e6-b77f-822a8b24ca62","045329d0-e519-4650-9dba-a64a6bf0ab2c","43653f0f-ef0b-46ea-8204-2ff6c41d22d4","f9511e46-bbd7-4910-a40a-1bc7a12317a5"],"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_NODEJS_eshop-WebService","customerIds":["photon"],"used":false,"creationDate":1378365319484,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_NODEJS_eshop-WebService","displayName":null,"description":"","status":null},{"modules":null,"pomFile":null,"appDirName":null,"techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":null,"used":false,"creationDate":1365682608000,"helpText":null,"system":false,"name":"hybrid-app-iOS","id":"tech-iphone-hybrid","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"creationDate":1365682608031,"helpText":null,"system":false,"name":null,"id":"9f61f4c0-1deb-4793-9f2c-7b8522c0a2b6","displayName":null,"description":null,"status":null}],"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":["4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","b3ae6065-1a44-4628-811d-0bd9f1bae6f0"],"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"version":"1.0.0","code":"PHTN_iPhone_hybrid_eshop","customerIds":["photon"],"used":false,"creationDate":1378365319484,"helpText":null,"system":true,"name":"Eshop","id":"PHTN_iPhone_hybrid_eshop","displayName":null,"description":"","status":null}],"projectCode":null,"noOfApps":0,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"version":null,"customerIds":["photon"],"used":false,"creationDate":1378365319421,"helpText":null,"system":false,"name":"Eshop Nodejs Webservice - Android Hybrid - Iphone Hybrid","id":"641a83ae-a49f-425e-ca3b4-4029b6c6s855f","displayName":"Eshop Nodejs Webservice - Android Hybrid - Iphone Hybrid","description":"Eshop Nodejs Webservice - Android Hybrid - Iphone Hybrid","status":null}],"status":"success"});
				  }
				});
				
				$("select[name=builtmyself]").val('prebuilt');
				$("select[name=builtmyself]").change();
				setTimeout(function() {
					start();
					var pilotOption = $("select[name=prebuiltapps]").find("option:contains(Blog Php)").text();
					equal(pilotOption, "Blog Php", "Add Project - Pilot Project Change With Prebuilt Option Event Tested");
					self.runPilotProjectChangeEventTest();	
				}, 1500);
			});
		},
		
		runPilotProjectChangeEventTest : function(){
			var self = this;
			asyncTest("Add Project- Pilot Project Change With Custom Option Event Test", function() {
				$("select[name=builtmyself]").val('custom');
				$("select[name=builtmyself]").change();
				setTimeout(function() {
					start();
					var weblayer = $("select[name=web_widget]").find('option:selected').val();
					var mobilelayer = $("select[name=mobile_types]").find('option:selected').val();
					equal(weblayer, "Select Technology", "Add Project - Pilot Project Change With Custom Option Event Tested");
					equal(mobilelayer, "Select Technology", "Add Project - Pilot Project Change With Custom Option Event Tested");
					self.runPilotSelectTest();	
				}, 1500);
			});
		},
		
		runPilotSelectTest : function() {
			var self = this;
			asyncTest("Add Project- Select Pilot Test", function() {
				$("select[name=prebuiltapps]").find("option:selected").text('Eshop Nodejs Webservice - HTML5 Multichannel YUI Widget');
				$("select[name=prebuiltapps]").change();
				setTimeout(function() {
					start();
					var applayer = $("select[name=appln_technology]").find('option:selected').val();
					equal("tech-html5", "tech-html5", "Add Project - Pilot Project Select Event Tested");
					self.runRemoveApplicationLayerEventTest();	
				}, 1500);
			});
		},
		
		runRemoveApplicationLayerEventTest : function() {
			var self=this;
			asyncTest("Add Project- Remove Application Layer Event Test", function() {
				$("a[name=removeApplnLayer]").click();
				setTimeout(function() {
					start();
					var trCount = $("a[name=addApplnLayer]").parents('tr.applnlayercontent:last').length;
					notEqual(trCount, 1, "Add Project- Remove Application Layer Event Test");
					self.runRemoveWebLayerEventTest();
				}, 2500);
			});
		},

		runRemoveWebLayerEventTest : function() {
			var self=this;
			asyncTest("Add Project- Remove Web Layer Event Test", function() {
				$("a[name=removeWebLayer]").click();
				setTimeout(function() {
					start();
					var trCount = $("a[name=addWebLayer]").parents('tr.weblayercontent:last').length;
					notEqual(trCount, 1, "Add Project- Remove Web Layer Event Test");
					self.runRemoveMobileLayerEventTest();
				}, 2500);
			});
		},

		runRemoveMobileLayerEventTest : function() {
			var self=this;
			asyncTest("Add Project- Remove Mobile Layer Event Test", function() {
				$("a[name=removeMobileLayer]").click();
				setTimeout(function() {
					start();
					var trCount = $("a[name=addMobileLayer]").parents('tr.mobilelayercontent:last').length;
					equal(trCount, 0, "Add Project- Remove Mob Layer Event Test");
					self.runEditProjectTest();
				}, 2500);
			});
		},
		
		runEditProjectTest : function() {
			module("EditProject.js");
			var self = this;
			asyncTest("Edit Project- Edit Project UI Test", function() {
				
				$.mockjax({
				  url: commonVariables.webserviceurl + 'technology/apptypes?userId=admin&customerId=photon',
				  type:'GET',
				  contentType: 'application/json',
				  status: 200,
				  response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200024","data":[{"techGroups":[{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"appTypeId":"app-layer","techGroupId":"java","techVersions":["1.6"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377092746000,"helpText":null,"system":false,"name":"Spring Swagger Splunk","id":"62f5160b-2932-42fa-82d3-3308eddfd038","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":["1.7"," 1.6"," 1.5"],"version":null,"customerIds":["photon","1c65af0e-6a45-4841-abe7-7d1a07047b1d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533323500,"helpText":null,"system":false,"name":"java-standalone","id":"tech-java-standalone","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":["1.7"," 1.6"," 1.5"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532243796,"helpText":null,"system":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","a41228d1-5e74-4c7e-ac2e-b80d946376fc","170a7a47-95f7-4829-84dc-e5cffab0cd7a","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943312,"helpText":null,"system":true,"name":"Java","id":"java","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"dotnet","techVersions":["4.0"," 3.5"," 3.0"," 2.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532161515,"helpText":null,"system":false,"name":".net-iis","id":"tech-dotnet","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"dotnet","techVersions":["3.5"," 3.0"," 2.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532410562,"helpText":null,"system":false,"name":".net-SharePoint","id":"tech-sharepoint","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"dotnet","techVersions":["3.5"," 3.0"," 2.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531808546,"helpText":null,"system":false,"name":".net-Sitecore","id":"tech-sitecore","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Dot Net","id":"dotnet","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["7.8"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532340640,"helpText":null,"system":false,"name":"php-Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["3.4.2"," 3.3.1"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532355343,"helpText":null,"system":false,"name":"php-Wordpress","id":"tech-wordpress","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["5.4.x","  5.3.x","  5.2.x","  5.1.x","  5.0.x"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532307546,"helpText":null,"system":false,"name":"php-raw","id":"tech-php","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["6.3"," 6.25"," 6.19"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532328968,"helpText":null,"system":false,"name":"php-Drupal6","id":"tech-phpdru6","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","c32171c4-90e5-4ede-9c51-0ff370eae974","a145ab5e-5eb6-4323-9c88-db61a79c9f3c","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"PHP","id":"php","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":["0.8.22","  0.8.20","  0.10.9","  0.10.8","  0.10.7","  0.10.6","  0.10.5","  0.10.4","  0.10.3","  0.10.2","  0.10.1","  0.10.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532270437,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943312,"helpText":null,"system":true,"name":"NodeJs","id":"nodejs","displayName":null,"description":null,"status":null}],"customerIds":["photon"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Middle Tier","id":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","displayName":null,"description":null,"status":null},{"techGroups":[{"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techInfos":[{"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techGroupId":"1795d032-466f-4866-88b2-e5ce0c332871","techVersions":["5.6"],"version":null,"customerIds":["photon"],"used":false,"creationDate":1377533458921,"helpText":null,"system":false,"name":"AEM CQ","id":"0101a91b-559f-4b1b-9e30-a24ed5760d02","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"CQ5","id":"1795d032-466f-4866-88b2-e5ce0c332871","displayName":null,"description":null,"status":null}],"customerIds":["photon"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"CMS","id":"99d55693-dacd-4f77-994a-f02a66176ff9","displayName":null,"description":null,"status":null},{"techGroups":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"bb","techVersions":["7.x "],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533030187,"helpText":null,"system":false,"name":"mobile-app-bb10","id":"tech-blackberry-hybrid","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943390,"helpText":null,"system":true,"name":"Black Berry","id":"bb","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["3.9.1"," 3.9.0"," 3.8.1"," 3.8.0"," 3.7.3"," 3.7.2"," 3.7.1"," 3.7.0"," 3.10.3"," 3.10.2"," 3.10.1"," 3.10.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533391328,"helpText":null,"system":false,"name":"responsive-web-yui","id":"tech-html5","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["2.0.2"," 2.0.1"," 2.0.0"," 1.7.2"," 1.10.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533295015,"helpText":null,"system":false,"name":"responsive-web","id":"tech-html5-jquery-widget","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["3.9.1"," 3.9.0"," 3.8.1"," 3.8.0"," 3.7.3"," 3.7.2"," 3.7.1"," 3.7.0"," 3.10.3"," 3.10.2"," 3.10.1"," 3.10.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533425406,"helpText":null,"system":false,"name":"mobile-web-yui","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["2.0.2"," 2.0.1"," 2.0.0"," 1.7.2"," 1.10.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533346750,"helpText":null,"system":false,"name":"mobile-web","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","c32171c4-90e5-4ede-9c51-0ff370eae974","05c80933-95d4-46c8-a58d-ceceb4bcce48","0f3d8d9d-a7d0-49b8-b662-2cc25a5ee88b","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"HTML5","id":"html5","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531462625,"helpText":null,"system":false,"name":"hybrid-app-iOS","id":"tech-iphone-hybrid","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531445718,"helpText":null,"system":false,"name":"library-app-iOS ","id":"tech-iphone-library","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531595765,"helpText":null,"system":false,"name":"mobile-app-iOS","id":"tech-iphone-native","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","170a7a47-95f7-4829-84dc-e5cffab0cd7a","a41228d1-5e74-4c7e-ac2e-b80d946376fc","c32171c4-90e5-4ede-9c51-0ff370eae974","05c80933-95d4-46c8-a58d-ceceb4bcce48","0f3d8d9d-a7d0-49b8-b662-2cc25a5ee88b","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Iphone","id":"iphone","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"windows","techVersions":["1.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377530944593,"helpText":null,"system":false,"name":"desktop-app-OSX","id":"49ea6a1c-ad68-4971-995c-0f26017abb3b","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"windows","techVersions":["7.x"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532983125,"helpText":null,"system":false,"name":"mobile-app-winMetro","id":"tech-win-metro","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"windows","techVersions":["8.x","  7.x"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533002328,"helpText":null,"system":false,"name":"mobile-app-winPhone8","id":"tech-win-phone","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943390,"helpText":null,"system":true,"name":"Windows","id":"windows","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":["4.2"," 4.1.2"," 4.1"," 4.0.3"," 2.3.3"," 2.2"," 2.1_r1"," 1.6"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532115218,"helpText":null,"system":false,"name":"mobile-app-android","id":"tech-android-native","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":["4.2","  4.1.2","  4.1","  4.0.3","  2.3.3","  2.2","  2.1_r1","  1.6"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531545546,"helpText":null,"system":false,"name":"library-app-android","id":"tech-android-library","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":["4.2","  4.1.2","  4.1","  4.0.3","  2.3.3","  2.2","  2.1_r1","  1.6"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531058656,"helpText":null,"system":false,"name":"hybrid-app-android","id":"tech-android-hybrid","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","c32171c4-90e5-4ede-9c51-0ff370eae974","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Android","id":"android","displayName":null,"description":null,"status":null}],"customerIds":["photon"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Front End","id":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","displayName":null,"description":null,"status":null}],"status":"success"});
				  }
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl + "project/edit?customerId=photon&projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f",
				  type:'GET',
				  contentType:'application/json',
				  status: 200,
				  response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200005","data":{"version":"1.0","appInfos":[{"version":"1.0","modules":[{"code":"CMS-mod1","techInfo":{"version":"5.6","multiModule":false,"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"AEM CQ","id":"0101a91b-559f-4b1b-9e30-a24ed5760d02","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},"dependentModules":[],"dependentApps":null,"name":null,"id":"16581ff5-c798-4c9f-8646-43fab047bc24","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},{"code":"CMS-mod2","techInfo":{"version":"8.x","multiModule":false,"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"mobile-app-winPhone8","id":"tech-win-phone","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},"dependentModules":["CMS-mod1"],"dependentApps":null,"name":null,"id":"1ca0f522-29bf-40ce-a02b-5ba00e867841","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},{"code":"CMS-mod3","techInfo":{"version":"7.x","multiModule":false,"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"mobile-app-bb10","id":"tech-blackberry-hybrid","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},"dependentModules":["CMS-mod2"],"dependentApps":null,"name":null,"id":"906f8cfa-6910-44d1-936f-76db40f5e619","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false}],"pomFile":"pom.xml","code":"CMS","appDirName":"CMS","techInfo":{"version":"5.6","multiModule":false,"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techGroupId":"CQ5","techVersions":null,"customerIds":null,"used":false,"name":"AEM CQ","id":"0101a91b-559f-4b1b-9e30-a24ed5760d02","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},"functionalFramework":null,"selectedModules":[],"selectedComponents":[],"selectedServers":null,"selectedDatabases":null,"selectedJSLibs":[],"rootModule":"CMS","selectedWebservices":null,"functionalFrameworkInfo":null,"phrescoPomFile":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"customerIds":null,"used":false,"name":"CMS","id":"be792a0e-8e47-4eb0-ad88-a433e5a83b4b","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},{"version":"1.0","modules":[{"code":"FrontEnd-mod1","techInfo":{"version":"7.x","multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"mobile-app-winMetro","id":"tech-win-metro","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},"dependentModules":[],"dependentApps":null,"name":null,"id":"aa70c97c-7d46-4724-86c6-5da073d36cda","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},{"code":"FrontEnd-mod2","techInfo":{"version":"8.x","multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"mobile-app-winPhone8","id":"tech-win-phone","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},"dependentModules":["FrontEnd-mod1"],"dependentApps":null,"name":null,"id":"e202cb4b-4f6f-419c-9cda-5b46813fc6d6","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false}],"pomFile":"pom.xml","code":"FrontEnd","appDirName":"FrontEnd","techInfo":{"version":"7.x","multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"Windows","techVersions":null,"customerIds":null,"used":false,"name":"mobile-app-winMetro","id":"tech-win-metro","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},"functionalFramework":null,"selectedModules":[],"selectedComponents":[],"selectedServers":null,"selectedDatabases":null,"selectedJSLibs":[],"rootModule":"FrontEnd","selectedWebservices":null,"functionalFrameworkInfo":null,"phrescoPomFile":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"customerIds":null,"used":false,"name":"FrontEnd","id":"2efdcfe2-b483-4871-86ea-8c1718abded0","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},{"version":"1.0","modules":[{"code":"MiddleTier-mod1","techInfo":{"version":"2.0.2","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"mobile-web","id":"tech-html5-jquery-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},"dependentModules":[],"dependentApps":null,"name":null,"id":"8ba6b4d8-1cf5-4d0b-82c9-b31d8dc22bfb","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},{"code":"MiddleTier-mod2","techInfo":{"version":"2.0.2","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"responsive-web","id":"tech-html5-jquery-widget","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},"dependentModules":["MiddleTier-mod1"],"dependentApps":null,"name":null,"id":"317227dc-3e6c-40ca-9199-01dcf0346064","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},{"code":"MiddleTier-mod3","techInfo":{"version":"3.9.1","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"mobile-web-yui","id":"tech-html5-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},"dependentModules":["MiddleTier-mod2"],"dependentApps":null,"name":null,"id":"3bd96ab1-6425-4e49-9a45-e45e8d4df327","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false}],"pomFile":"pom.xml","code":"MiddleTier","appDirName":"MiddleTier","techInfo":{"version":"1.7","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"Java","techVersions":null,"customerIds":null,"used":false,"name":"java-standalone","id":"tech-java-standalone","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false},"functionalFramework":null,"selectedModules":["2d41a182-85f1-42a3-a67c-a0836792ba02","2d41a182-85f1-42a3-a67c-a0836792ba02","2d41a182-85f1-42a3-a67c-a0836792ba02"],"selectedComponents":[],"selectedServers":null,"selectedDatabases":null,"selectedJSLibs":[],"rootModule":"MiddleTier","selectedWebservices":null,"functionalFrameworkInfo":null,"phrescoPomFile":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"customerIds":null,"used":false,"name":"MiddleTier","id":"241d4131-dc29-4473-9491-c1533e865257","displayName":null,"status":null,"description":null,"creationDate":1382353553000,"helpText":null,"system":false}],"projectCode":"test","noOfApps":0,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":true,"integrationTest":false,"customerIds":["photon"],"used":false,"name":"test","id":"4ed26dfb-b7dd-4453-8a1f-0e3171cbffd2","displayName":null,"status":null,"description":"","creationDate":1382429631000,"helpText":null,"system":false},"status":"success"});	
				  }
				});	
				
				$("img[name=editproject]").click();
				$("li#editproject").click();
				setTimeout(function() {
					start();
					var projectname = $("#editPrjprojectname").val();
					var mobLayersDisplay = $("input[name=mobLayers]").css('display');
					equal(projectname, "test", "Edit Project - UI Tested");
					self.runUpdateProjectTest();
				}, 1500);
			});
		},
		
		runUpdateProjectTest : function() {
			module("EditProject.js");
			var self = this;
			asyncTest("Edit Project- Update Project Test", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl + "project/updateproject?userId=admin",
				  type:'PUT',
				  contentType:'application/json',
				  status: 200,
				  response: function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200006","data":{"appInfos":[{"modules":null,"pomFile":null,"appDirName":"app1","techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"HTML5","techVersions":null,"version":"3.9.1","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"mobile-web-yui","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"app1","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"app1","id":"b4a5fa8e-cb7b-4ae9-800b-16e833a23a35","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":"web1","techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"Java","techVersions":null,"version":"1.7","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"java-standalone","id":"tech-java-standalone","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"web1","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"web1","id":"81b94a21-bc8a-4c3c-9bf8-3a4e4e98bb37","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":"mob1","techInfo":{"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techGroupId":"CQ5","techVersions":null,"version":"5.6","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"AEM CQ","id":"0101a91b-559f-4b1b-9e30-a24ed5760d02","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"mob1","customerIds":null,"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"mob1","id":"0e9eb83e-478d-489f-8eca-bb1bfc2277af","displayName":null,"description":null,"status":null}],"projectCode":"Test1","noOfApps":3,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"version":"1.0","customerIds":["photon"],"used":false,"creationDate":1378288615718,"helpText":null,"system":false,"name":"Test","id":"fb248f36-6b64-4f96-8cda-79663a36db91","displayName":null,"description":"Test Description","status":null},"status":"success"});	
				  }
				});	
				
				$("#updateProject").click();
				setTimeout(function() {
					start();
					var msg = $(".msgdisplay").hasClass('success');
					equal(msg, true, "Update Project Test Tested");
					self.runEditInputFromStorageBtnTest();
				}, 3000);
			});
		},
		
		runEditInputFromStorageBtnTest : function(){
			var self=this;
			$.mockjaxClear(self.appTypes1);
			asyncTest("Edit Project- Edit Project UI Test From LocalStorage", function() {
				//$(commonVariables.contentPlaceholder).empty();
				commonVariables.api.localVal.deleteSession("Front End");
				commonVariables.api.localVal.deleteSession("Middle Tier");
				commonVariables.api.localVal.deleteSession("CMS");
				$.mockjax({
				  url: commonVariables.webserviceurl + 'technology/apptypes?userId=admin&customerId=photon',
				  type:'GET',
				  contentType: 'application/json',
				  status: 200,
				  response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200024","data":[{"techGroups":[{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"appTypeId":"app-layer","techGroupId":"java","techVersions":["1.6"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377092746000,"helpText":null,"system":false,"name":"Spring Swagger Splunk","id":"62f5160b-2932-42fa-82d3-3308eddfd038","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":["1.7"," 1.6"," 1.5"],"version":null,"customerIds":["photon","1c65af0e-6a45-4841-abe7-7d1a07047b1d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533323500,"helpText":null,"system":false,"name":"java-standalone","id":"tech-java-standalone","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":["1.7"," 1.6"," 1.5"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532243796,"helpText":null,"system":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","a41228d1-5e74-4c7e-ac2e-b80d946376fc","170a7a47-95f7-4829-84dc-e5cffab0cd7a","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943312,"helpText":null,"system":true,"name":"Java","id":"java","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"dotnet","techVersions":["4.0"," 3.5"," 3.0"," 2.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532161515,"helpText":null,"system":false,"name":".net-iis","id":"tech-dotnet","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"dotnet","techVersions":["3.5"," 3.0"," 2.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532410562,"helpText":null,"system":false,"name":".net-SharePoint","id":"tech-sharepoint","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"dotnet","techVersions":["3.5"," 3.0"," 2.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531808546,"helpText":null,"system":false,"name":".net-Sitecore","id":"tech-sitecore","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Dot Net","id":"dotnet","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["7.8"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532340640,"helpText":null,"system":false,"name":"php-Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["3.4.2"," 3.3.1"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532355343,"helpText":null,"system":false,"name":"php-Wordpress","id":"tech-wordpress","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["5.4.x","  5.3.x","  5.2.x","  5.1.x","  5.0.x"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532307546,"helpText":null,"system":false,"name":"php-raw","id":"tech-php","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"php","techVersions":["6.3"," 6.25"," 6.19"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532328968,"helpText":null,"system":false,"name":"php-Drupal6","id":"tech-phpdru6","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","c32171c4-90e5-4ede-9c51-0ff370eae974","a145ab5e-5eb6-4323-9c88-db61a79c9f3c","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"PHP","id":"php","displayName":null,"description":null,"status":null},{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techInfos":[{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"nodejs","techVersions":["0.8.22","  0.8.20","  0.10.9","  0.10.8","  0.10.7","  0.10.6","  0.10.5","  0.10.4","  0.10.3","  0.10.2","  0.10.1","  0.10.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532270437,"helpText":null,"system":false,"name":"js-node","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943312,"helpText":null,"system":true,"name":"NodeJs","id":"nodejs","displayName":null,"description":null,"status":null}],"customerIds":["photon"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Middle Tier","id":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","displayName":null,"description":null,"status":null},{"techGroups":[{"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techInfos":[{"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techGroupId":"1795d032-466f-4866-88b2-e5ce0c332871","techVersions":["5.6"],"version":null,"customerIds":["photon"],"used":false,"creationDate":1377533458921,"helpText":null,"system":false,"name":"AEM CQ","id":"0101a91b-559f-4b1b-9e30-a24ed5760d02","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"CQ5","id":"1795d032-466f-4866-88b2-e5ce0c332871","displayName":null,"description":null,"status":null}],"customerIds":["photon"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"CMS","id":"99d55693-dacd-4f77-994a-f02a66176ff9","displayName":null,"description":null,"status":null},{"techGroups":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"bb","techVersions":["7.x "],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533030187,"helpText":null,"system":false,"name":"mobile-app-bb10","id":"tech-blackberry-hybrid","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943390,"helpText":null,"system":true,"name":"Black Berry","id":"bb","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["3.9.1"," 3.9.0"," 3.8.1"," 3.8.0"," 3.7.3"," 3.7.2"," 3.7.1"," 3.7.0"," 3.10.3"," 3.10.2"," 3.10.1"," 3.10.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533391328,"helpText":null,"system":false,"name":"responsive-web-yui","id":"tech-html5","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["2.0.2"," 2.0.1"," 2.0.0"," 1.7.2"," 1.10.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533295015,"helpText":null,"system":false,"name":"responsive-web","id":"tech-html5-jquery-widget","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["3.9.1"," 3.9.0"," 3.8.1"," 3.8.0"," 3.7.3"," 3.7.2"," 3.7.1"," 3.7.0"," 3.10.3"," 3.10.2"," 3.10.1"," 3.10.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533425406,"helpText":null,"system":false,"name":"mobile-web-yui","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":["2.0.2"," 2.0.1"," 2.0.0"," 1.7.2"," 1.10.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533346750,"helpText":null,"system":false,"name":"mobile-web","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","c32171c4-90e5-4ede-9c51-0ff370eae974","05c80933-95d4-46c8-a58d-ceceb4bcce48","0f3d8d9d-a7d0-49b8-b662-2cc25a5ee88b","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"HTML5","id":"html5","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531462625,"helpText":null,"system":false,"name":"hybrid-app-iOS","id":"tech-iphone-hybrid","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531445718,"helpText":null,"system":false,"name":"library-app-iOS ","id":"tech-iphone-library","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531595765,"helpText":null,"system":false,"name":"mobile-app-iOS","id":"tech-iphone-native","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","170a7a47-95f7-4829-84dc-e5cffab0cd7a","a41228d1-5e74-4c7e-ac2e-b80d946376fc","c32171c4-90e5-4ede-9c51-0ff370eae974","05c80933-95d4-46c8-a58d-ceceb4bcce48","0f3d8d9d-a7d0-49b8-b662-2cc25a5ee88b","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Iphone","id":"iphone","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"windows","techVersions":["1.0"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377530944593,"helpText":null,"system":false,"name":"desktop-app-OSX","id":"49ea6a1c-ad68-4971-995c-0f26017abb3b","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"windows","techVersions":["7.x"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532983125,"helpText":null,"system":false,"name":"mobile-app-winMetro","id":"tech-win-metro","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"windows","techVersions":["8.x","  7.x"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377533002328,"helpText":null,"system":false,"name":"mobile-app-winPhone8","id":"tech-win-phone","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943390,"helpText":null,"system":true,"name":"Windows","id":"windows","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techInfos":[{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":["4.2"," 4.1.2"," 4.1"," 4.0.3"," 2.3.3"," 2.2"," 2.1_r1"," 1.6"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377532115218,"helpText":null,"system":false,"name":"mobile-app-android","id":"tech-android-native","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":["4.2","  4.1.2","  4.1","  4.0.3","  2.3.3","  2.2","  2.1_r1","  1.6"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531545546,"helpText":null,"system":false,"name":"library-app-android","id":"tech-android-library","displayName":null,"description":null,"status":null},{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"android","techVersions":["4.2","  4.1.2","  4.1","  4.0.3","  2.3.3","  2.2","  2.1_r1","  1.6"],"version":null,"customerIds":["photon","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1377531058656,"helpText":null,"system":false,"name":"hybrid-app-android","id":"tech-android-hybrid","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","c32171c4-90e5-4ede-9c51-0ff370eae974","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d","dc086bc3-6d6d-40fd-9320-6836c731712d"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Android","id":"android","displayName":null,"description":null,"status":null}],"customerIds":["photon"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Front End","id":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","displayName":null,"description":null,"status":null}],"status":"success"});
				  }
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl + "project/edit?customerId=photon&projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f",
				  type:'GET',
				  contentType:'application/json',
				  status: 200,
				  response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200005","data":{"appInfos":[{"modules":null,"pomFile":null,"appDirName":"app1","techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"HTML5","techVersions":null,"version":"3.9.1","customerIds":null,"used":false,"creationDate":1378288615000,"helpText":null,"system":false,"name":"mobile-web-yui","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":["a12d56f9-ff9e-4a3f-b01a-d8cb3dfd1278","2d41a182-85f1-42a3-a67c-a0836792ba02"],"selectedJSLibs":["c7008489-b264-442c-ad8c-2c422284d171","ceb6006b-b7aa-4600-9cdb-d52f5ad724ff","6afdf1d3-80f0-44a5-a9f5-843ce3db7ea0","deda98f8-c350-47f1-8b22-a0816a695127","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85"],"selectedComponents":[],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"app1","customerIds":null,"used":false,"creationDate":1378288615000,"helpText":null,"system":false,"name":"app1","id":"b4a5fa8e-cb7b-4ae9-800b-16e833a23a35","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":"mob1","techInfo":{"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techGroupId":"CQ5","techVersions":null,"version":"5.6","customerIds":null,"used":false,"creationDate":1378288615000,"helpText":null,"system":false,"name":"AEM CQ","id":"0101a91b-559f-4b1b-9e30-a24ed5760d02","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":[],"selectedJSLibs":[],"selectedComponents":[],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"mob1","customerIds":null,"used":false,"creationDate":1378288615000,"helpText":null,"system":false,"name":"mob1","id":"0e9eb83e-478d-489f-8eca-bb1bfc2277af","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":"web1","techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"Java","techVersions":null,"version":"1.7","customerIds":null,"used":false,"creationDate":1378288615000,"helpText":null,"system":false,"name":"java-standalone","id":"tech-java-standalone","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":["2d41a182-85f1-42a3-a67c-a0836792ba02"],"selectedJSLibs":[],"selectedComponents":[],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"web1","customerIds":null,"used":false,"creationDate":1378288615000,"helpText":null,"system":false,"name":"web1","id":"81b94a21-bc8a-4c3c-9bf8-3a4e4e98bb37","displayName":null,"description":null,"status":null}],"projectCode":"Test1","noOfApps":3,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"version":"1.0","customerIds":["photon"],"used":false,"creationDate":1378288615000,"helpText":null,"system":false,"name":"Test","id":"fb248f36-6b64-4f96-8cda-79663a36db91","displayName":null,"description":"Test Description","status":null},"status":"success"});	
				  }
				});
				
				$("img[name=editproject]").click();
				$("li#editproject").click();
				setTimeout(function() {
					start();
					var projectname = $("#editPrjprojectname").val();
					var mobLayersDisplay = $("input[name=mobLayers]").css('display');
					equal(projectname, "test", "Edit Project - UI Tested");
					self.runEditInputBtnTest();
				}, 3000);
			});
		},
		
		runEditInputBtnTest : function(){
			var self=this;
			asyncTest("Edit Project- Input Btn Test", function() {
				$(".flt_left input").click();
				var mobTrDisplay = $("tr[id=MobLayer]").attr('key');
				setTimeout(function() {
					start();
					equal(mobTrDisplay, "displayed", "Edit Project - Input button Event Test for Mobile Layer");
					self.runEditProjectCloseEventTest();
				}, 1500);
			});	
		},
		
		runEditProjectCloseEventTest : function(){
			asyncTest("Edit Project- Edit Project Close Image Test", function() {
				$("img[name='close']").click();
				setTimeout(function() {
					start();
					var applnLayer = $("tr[id=MobLayer]").attr('key');
					equal(applnLayer, "hidden", "Edit Project - Edit Project Close Image Test");
					require(["applicationTest"], function(applicationTest){
						applicationTest.runTests();
					});
				}, 1500);
			});
		}
	};	
});