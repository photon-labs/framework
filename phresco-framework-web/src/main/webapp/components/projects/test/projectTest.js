define(["projects/addproject", "projects/editproject"], function(addProject, editProject) {

	return { runTests: function (configData) {
		/**
		 * Test that the setMainContent method sets the text in the MyCart-widget
		 */
		module("Project.js");
		
		var addproject = new addProject(), editproject = new editProject(), templateData = {}, self = this;
		
			asyncTest("Add Project- App Layer UI Test", function() {
				
				$.mockjax({
				  url: commonVariables.webserviceurl + 'technology/apptypes?userId=admin&customerId=photon',
				  type:'GET',
				  contentType: 'application/json',
				  status: 200,
				  response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Apptypes listed successfully","exception":null,"data":[{"techGroups":[{"appTypeId":"web-layer","techInfos":[{"appTypeId":"web-layer","techGroupId":"html5","techVersions":["2.0.2","2.0.1","2.0.0","1.10.0","1.7.2"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"JQuery Mobile Widget","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},{"appTypeId":"web-layer","techGroupId":"html5","techVersions":["3.10.3","3.10.2","3.10.1","3.10.0","3.9.1","3.9.0","3.8.1","3.8.0","3.7.3","3.7.2","3.7.1","3.7.0"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"YUI Mobile Widget","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},{"appTypeId":"web-layer","techGroupId":"html5","techVersions":["2.0.2","2.0.1","2.0.0","1.7.2","1.10.0"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Multichannel JQuery Widget","id":"tech-html5-jquery-widget","displayName":null,"description":null,"status":null},{"appTypeId":"web-layer","techGroupId":"html5","techVersions":["3.10.3","3.10.2","3.10.1","3.10.0","3.9.1","3.9.0","3.8.1","3.8.0","3.7.3","3.7.2","3.7.1","3.7.0"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Multichannel YUI Widget","id":"tech-html5","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","c32171c4-90e5-4ede-9c51-0ff370eae974","05c80933-95d4-46c8-a58d-ceceb4bcce48","0f3d8d9d-a7d0-49b8-b662-2cc25a5ee88b"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"HTML5","id":"html5","displayName":null,"description":null,"status":null}],"customerIds":["photon"],"used":false,"creationDate":1350460943296,"helpText":null,"system":true,"name":"Web Layer","id":"web-layer","displayName":null,"description":null,"status":null},{"techGroups":[{"appTypeId":"app-layer","techInfos":[{"appTypeId":"app-layer","techGroupId":"java","techVersions":["1.7","1.6","1.5"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","a41228d1-5e74-4c7e-ac2e-b80d946376fc"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Java Standalone","id":"tech-java-standalone","displayName":null,"description":null,"status":null},{"appTypeId":"app-layer","techGroupId":"java","techVersions":["1.7","1.6","1.5"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"J2EE","id":"tech-java-webservice","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","a41228d1-5e74-4c7e-ac2e-b80d946376fc","170a7a47-95f7-4829-84dc-e5cffab0cd7a"],"used":false,"creationDate":1350460943312,"helpText":null,"system":true,"name":"Java","id":"java","displayName":null,"description":null,"status":null},{"appTypeId":"app-layer","techInfos":[{"appTypeId":"app-layer","techGroupId":"nodejs","techVersions":["0.10.9","0.10.8","0.10.7","0.10.6","0.10.5","0.10.4","0.10.3","0.10.2","0.10.1","0.10.0","0.8.22","0.8.20"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Node JS","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989"],"used":false,"creationDate":1350460943312,"helpText":null,"system":true,"name":"NodeJs","id":"nodejs","displayName":null,"description":null,"status":null},{"appTypeId":"app-layer","techInfos":[{"appTypeId":"app-layer","techGroupId":"php","techVersions":["5.4.x","5.3.x","5.2.x","5.1.x","5.0.x"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","c32171c4-90e5-4ede-9c51-0ff370eae974"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"PHP","id":"tech-php","displayName":null,"description":null,"status":null},{"appTypeId":"app-layer","techGroupId":"php","techVersions":["6.3","6.25","6.19"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Drupal6","id":"tech-phpdru6","displayName":null,"description":null,"status":null},{"appTypeId":"app-layer","techGroupId":"php","techVersions":["7.8"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","a145ab5e-5eb6-4323-9c88-db61a79c9f3c"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},{"appTypeId":"app-layer","techGroupId":"php","techVersions":["3.3.1"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","c32171c4-90e5-4ede-9c51-0ff370eae974","a145ab5e-5eb6-4323-9c88-db61a79c9f3c"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"PHP","id":"php","displayName":null,"description":null,"status":null},{"appTypeId":"app-layer","techInfos":[{"appTypeId":"app-layer","techGroupId":"dotnet","techVersions":["3.5","3.0","2.0"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"ASP.NET","id":"tech-dotnet","displayName":null,"description":null,"status":null},{"appTypeId":"app-layer","techGroupId":"dotnet","techVersions":["3.5","3.0","2.0"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Sharepoint","id":"tech-sharepoint","displayName":null,"description":null,"status":null},{"appTypeId":"app-layer","techGroupId":"dotnet","techVersions":["3.5","3.0","2.0"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Site Core","id":"tech-sitecore","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Dot Net","id":"dotnet","displayName":null,"description":null,"status":null}],"customerIds":["photon"],"used":false,"creationDate":1350460943390,"helpText":null,"system":true,"name":"Application Layer","id":"app-layer","displayName":null,"description":null,"status":null},{"techGroups":[{"appTypeId":"mob-layer","techInfos":[{"appTypeId":"mob-layer","techGroupId":"windows","techVersions":null,"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Windows Metro","id":"tech-win-metro","displayName":null,"description":null,"status":null},{"appTypeId":"mob-layer","techGroupId":"windows","techVersions":null,"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Windows Phone","id":"tech-win-phone","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989"],"used":false,"creationDate":1350460943390,"helpText":null,"system":true,"name":"Windows","id":"windows","displayName":null,"description":null,"status":null},{"appTypeId":"mob-layer","techInfos":[{"appTypeId":"mob-layer","techGroupId":"bb","techVersions":null,"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","c32171c4-90e5-4ede-9c51-0ff370eae974"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Hybrid","id":"tech-blackberry-hybrid","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989"],"used":false,"creationDate":1350460943390,"helpText":null,"system":true,"name":"Black Berry","id":"bb","displayName":null,"description":null,"status":null},{"appTypeId":"mob-layer","techInfos":[{"appTypeId":"mob-layer","techGroupId":"android","techVersions":["4.1","4.1.2","4.2","4.0.3","2.3.3","2.2","2.1_r1","1.6"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Native","id":"tech-android-native","displayName":null,"description":null,"status":null},{"appTypeId":"mob-layer","techGroupId":"android","techVersions":["4.1","4.1.2","4.2","4.0.3","2.3.3","2.2","2.1_r1","1.6"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","c32171c4-90e5-4ede-9c51-0ff370eae974"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Hybrid","id":"tech-android-hybrid","displayName":null,"description":null,"status":null},{"appTypeId":"mob-layer","techGroupId":"android","techVersions":["4.1","4.1.2","4.2","4.0.3","2.3.3","2.2","2.1_r1","1.6"],"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Library","id":"tech-android-library","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","c32171c4-90e5-4ede-9c51-0ff370eae974"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Android","id":"android","displayName":null,"description":null,"status":null},{"appTypeId":"mob-layer","techInfos":[{"appTypeId":"mob-layer","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","170a7a47-95f7-4829-84dc-e5cffab0cd7a","a41228d1-5e74-4c7e-ac2e-b80d946376fc"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Native","id":"tech-iphone-native","displayName":null,"description":null,"status":null},{"appTypeId":"mob-layer","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","170a7a47-95f7-4829-84dc-e5cffab0cd7a","a41228d1-5e74-4c7e-ac2e-b80d946376fc"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Hybrid","id":"tech-iphone-hybrid","displayName":null,"description":null,"status":null},{"appTypeId":"mob-layer","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","170a7a47-95f7-4829-84dc-e5cffab0cd7a","a41228d1-5e74-4c7e-ac2e-b80d946376fc"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Library","id":"tech-iphone-library","displayName":null,"description":null,"status":null},{"appTypeId":"mob-layer","techGroupId":"iphone","techVersions":null,"version":null,"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","170a7a47-95f7-4829-84dc-e5cffab0cd7a","a41228d1-5e74-4c7e-ac2e-b80d946376fc"],"used":false,"creationDate":1350460943000,"helpText":null,"system":false,"name":"Workspace","id":"tech-iphone-workspace","displayName":null,"description":null,"status":null}],"customerIds":["photon","4ab793a9-99fc-45bb-8971-0fd2b26acf8e","878b124c-8dac-470e-92c2-7e975b6b2746","217bd500-a109-4c1d-bfa1-02c89652b989","b6b5b856-97d8-4e2a-8b42-a5a23568fe51","170a7a47-95f7-4829-84dc-e5cffab0cd7a","a41228d1-5e74-4c7e-ac2e-b80d946376fc","c32171c4-90e5-4ede-9c51-0ff370eae974","05c80933-95d4-46c8-a58d-ceceb4bcce48","0f3d8d9d-a7d0-49b8-b662-2cc25a5ee88b"],"used":false,"creationDate":1350460943000,"helpText":null,"system":true,"name":"Iphone","id":"iphone","displayName":null,"description":null,"status":null}],"customerIds":["photon"],"used":false,"creationDate":1350460943437,"helpText":null,"system":true,"name":"Mobile Layer","id":"mob-layer","displayName":null,"description":null,"status":null}]});
				  }
				});
				
				$("#addproject").click();
				setTimeout(function() {
					start();
					var techOption = $(commonVariables.contentPlaceholder).find("select.appln_technology").find("option:contains(PHP)").text();
					var weblayerOption = $(commonVariables.contentPlaceholder).find("select.weblayer").find("option:contains(HTML5)").text();
					var mobilelayerOption = $(commonVariables.contentPlaceholder).find("select.mobile_layer").find("option:contains(Android)").text();
					
					equal(techOption, "PHP", "Add Project - Applayer UI Tested");
					equal(weblayerOption, "HTML5", "Add Project - WebLayer UI Tested");
					equal(mobilelayerOption, "Android", "Add Project - Mobile Layer UI Tested");
					self.runChangeEventTest(addproject);
				}, 2500);
			});
		},
		
		runChangeEventTest : function(addproject) {
			var self=this;
			asyncTest("Add Project- Technolgy Version Change Event Test", function() {
				$("select[name='appln_technology']").val('tech-php');
				$("select[name='appln_technology']").change();
				$("select[name=weblayer]").val('html5');
				$("select[name=weblayer]").change();
				$("select[name='web_widget']").val('tech-html5-jquery-mobile-widget');
				$("select[name='web_widget']").change();
				$("select[name='mobile_layer']").val('android');
				$("select[name='mobile_layer']").change();
				$("select[name='mobile_types']").val('tech-android-native');
				$("select[name='mobile_types']").change();
				
				setTimeout(function() {
					start();
					var techVersionOption = $(commonVariables.contentPlaceholder).find("select.appln_version").find("option:contains(5.4.x)").text();
					var webTypeOption = $(commonVariables.contentPlaceholder).find("select.web_widget").find("option:contains(JQuery Mobile Widget)").text();
					var weblayerVersionOption = $(commonVariables.contentPlaceholder).find("select.web_version").find("option:contains(2.0.2)").text();
					var mobileTypeOption = $(commonVariables.contentPlaceholder).find("select.mobile_types").find("option:contains(Native)").text();
					var mobileVersionOption = $(commonVariables.contentPlaceholder).find("select.mobile_version").find("option:contains(4.1)").text();
					equal(techVersionOption, "5.4.x", "Add Project - Technolgy Version Change Event Tested Successfully");
					equal(webTypeOption, "JQuery Mobile Widget", "Add Project - weblayer Change Event Test");
					equal(weblayerVersionOption, "2.0.2", "Add Project - weblayer Version Change Event Test");
					equal(mobileTypeOption, "Native", "Add Project - Mobile Type Change Event Test");
					equal(mobileVersionOption, "4.14.1.2", "Add Project - Mobile Version Change Event Test");
					self.runProjectNameKeyupTest(addproject);
				}, 2500);
			});
		},
		
		runProjectNameKeyupTest : function(addproject) {
			var self=this;
			asyncTest("Add Project- ProjectName Keyup Test", function() {
				$("input[name='projectname']").val('Testcase');
				$("input[name='projectname']").keyup();
				setTimeout(function() {
					start();
					var projectCode = $("input[name='projectcode']").val();
					equal(projectCode, "Testcase", "Add Project - ProjectName Keyup Test");
					self.runCloseButtonTest(addproject);
				}, 1500);
			});
		},
		
		runCloseButtonTest : function(addproject) {
			var self=this;
			asyncTest("Add Project- Close Image Event Test", function() {
				$("img[name='close']").click();
				setTimeout(function() {
					start();
					var applnLayer = $("input[name=applicationlayer]").css('display');
					logs("applnLayerDisplay...."+ applnLayer);
					var webLayer = $("input[name=weblayer]").css('display');
					logs("webLayerDisplay...."+ webLayer);
					equal(applnLayer, "inline", "Add Project - Close Image Event Test for Application Layer");
					equal(webLayer, "inline", "Add Project - Close Image Event Test for web Layer");
					self.runInputBtnTest(addproject);
				}, 2500);
			});
		},
		
		runInputBtnTest : function(addproject) {
			var self = this;
			asyncTest("Add Project- Input button Event Test", function() {
				$(".flt_left input").click();
				setTimeout(function() {
					start();
					var applnLayerTrDisplay = $("tr[name=applicationlayer]").css('display');
					var applnLayerContentTrDisplay = $("tr[name=applicationlayercontent]").attr('display');
					equal(applnLayerTrDisplay, "table-row", "Add Project - Input button Event Test for Application Layer");
					equal(applnLayerContentTrDisplay, "table-row", "Add Project - Input button Event Test for Application Layer");
					self.runMultiModuleEventTest(addproject);
				}, 1500);
			});
		},
		
		runMultiModuleEventTest : function(addproject) {
			var self=this;
			asyncTest("Add Project- MultiModule CheckBox Event Test", function() {
				$("input[name='multimodule']").click();
				setTimeout(function() {
					start();
					var appdependency = $("span[name=appdependency]").css('display');
					equal(appdependency, "none", "Add Project - MultiModule CheckBox Event Test");
					self.runAddApplicationLayerEventTest(addproject);	
				}, 1500);
			});
		},
		
		runAddApplicationLayerEventTest : function(addproject) {
			var self=this;
			asyncTest("Add Project- Add Application Layer Event Test", function() {
				$("a[name=addApplnLayer]").click();
				setTimeout(function() {
					start();
					var addedTrName = $("a[name=addApplnLayer]").parents('tr.applnlayercontent:last').attr('name');
					equal(addedTrName, "staticApplnLayer", "Add Project- Add Application Layer Event Test");
					self.runAddWebLayerEventTest(addproject);
				}, 1500);
			});
		},
		
		runAddWebLayerEventTest : function(addproject) {
			var self=this;
			asyncTest("Add Project- Add Web Layer Event Test", function() {
				$("a[name=addWebLayer]").click();
				setTimeout(function() {
					start();
					var addedTrName = $("a[name=addWebLayer]").parents('tr.weblayercontent:last').attr('name');
					equal(addedTrName, "staticWebLayer", "Add Project- Add Web Layer Event Test");
					self.runAddMobileLayerEventTest(addproject);
				}, 1500);
			});
		},
		
		runAddMobileLayerEventTest : function(addproject) {
			var self=this;
			asyncTest("Add Project- Add Mobile Layer Event Test", function() {
				$("a[name=addMobileLayer]").click();
				setTimeout(function() {
					start();
					var addedTrName = $("a[name=addMobileLayer]").parents('tr.mobilelayercontent:last').attr('name');
					equal(addedTrName, "staticMobileLayer", "Add Project- Add Mobile Layer Event Test");
					self.showSuccessMessageAfterCreation(addproject);
				}, 1500);
			});
		}, 
		
		showSuccessMessageAfterCreation : function(addproject) {
			var self=this;
			asyncTest("Add Project- show SuccessMessage After Creation Test", function() {
				$("input[name=projectname]").val('Test');
				$("input[name=projectcode]").val('Test1');
				$("input[name=projectversion]").val('1.0');
				$("input[name=projectdescription]").val('Test Description');
				$("input[name=multimodule]").val('false');
				$("#appcode").val('app1');
				$("select[name=appln_technology]").val('tech-php');
				$("select[name=appln_version]").text('5.4.x');
				$("#webappcode").val('web1');
				$("select[name=weblayer]").val('html5');
				$("select[name=web_widget]").val('tech-html5-jquery-mobile-widget');
				$("select[name=widgetversion]").text('2.0.2');
				$("#mobileappcode").val('mob1');
				$("select[name=mobile_layer]").val('Android');
				$("select[name=mobile_types]").val('tech-android-native');
				$("select[name=mobile_version]").text('4.1');
				
				$.mockjax({
					  url: commonVariables.webserviceurl + 'project/create?userId=admin',
					  type:'POST',
					  contentType:'application/json',
					  status: 200,
					  response: function() {
						   this.responseText = JSON.stringify({"response":null,"message":"Project created Successfully","exception":null,"data":{"appInfos":[{"pomFile":null,"appDirName":"app1","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"5.4.x","customerIds":null,"used":false,"creationDate":1373455422125,"helpText":null,"system":false,"name":"PHP","id":"tech-php","displayName":null,"description":null,"status":null},"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"dependentModules":null,"version":"1.0","code":"app1","customerIds":null,"used":false,"creationDate":1373455422125,"helpText":null,"system":false,"name":"app1","id":"ae8afca9-09d8-4bc5-af2c-dac4ca71e25c","displayName":null,"description":null,"status":null},{"pomFile":null,"appDirName":"app2","techInfo":{"appTypeId":"web-layer","techGroupId":"HTML5","techVersions":null,"version":"2.0.2","customerIds":null,"used":false,"creationDate":1373455422125,"helpText":null,"system":false,"name":"JQuery Mobile Widget","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"dependentModules":null,"version":"1.0","code":"app2","customerIds":null,"used":false,"creationDate":1373455422125,"helpText":null,"system":false,"name":"app2","id":"0ddb01fd-3990-496f-baf2-0ecb35599acc","displayName":null,"description":null,"status":null},{"pomFile":null,"appDirName":"app3","techInfo":{"appTypeId":"mobile-layer","techGroupId":"Android","techVersions":null,"version":"4.1","customerIds":null,"used":false,"creationDate":1373455422125,"helpText":null,"system":false,"name":"Native","id":"tech-android-native","displayName":null,"description":null,"status":null},"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"dependentModules":null,"version":"1.0","code":"app3","customerIds":null,"used":false,"creationDate":1373455422125,"helpText":null,"system":false,"name":"app3","id":"64d522b2-001a-40db-9aed-38c00f1b0070","displayName":null,"description":null,"status":null}],"projectCode":"Test1","noOfApps":3,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"version":"1.0","customerIds":["photon"],"used":false,"creationDate":1373455422125,"helpText":null,"system":false,"name":"Test","id":"2695e566-5bff-44ec-933a-8cf81e55314c","displayName":null,"description":"","status":null}});	
					  }
				});	  
			
				$("input[name='Create']").click();
				
				setTimeout(function() {
					start();
					var Msg = $(".blinkmsg").hasClass('popsuccess');
					equal(Msg, false, "Add Project- show SuccessMessage After Creation Test");
				}, 1500);	
			});
		}
		
	};	
});