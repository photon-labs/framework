define(["jquery", "dashboard/dashboard"], function($, Dashboard) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { 
		runTests: function () {
		module("Dashboard.js;Dashboard");
		var dashboard = new Dashboard();  
		var self = this, editpro,listalldash;
		commonVariables.projectId = "a58a5358-fa43-4fac-9b98-9bf94b7c4d1f";
		asyncTest("Dashboard page render test case", function() {
				editpro = $.mockjax({
				  url: commonVariables.webserviceurl + "project/edit?customerId=photon&projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f",
				  type:'GET',
				  contentType:'application/json',
				  status: 200,
				  response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200005","data":{"appInfos":[{"modules":null,"pomFile":null,"appDirName":"app1","techInfo":{"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"HTML5","techVersions":null,"version":"3.9.1","customerIds":null,"used":false,"creationDate":1378288615000,"helpText":null,"system":false,"name":"mobile-web-yui","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":["a12d56f9-ff9e-4a3f-b01a-d8cb3dfd1278","2d41a182-85f1-42a3-a67c-a0836792ba02"],"selectedJSLibs":["c7008489-b264-442c-ad8c-2c422284d171","ceb6006b-b7aa-4600-9cdb-d52f5ad724ff","6afdf1d3-80f0-44a5-a9f5-843ce3db7ea0","deda98f8-c350-47f1-8b22-a0816a695127","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85"],"selectedComponents":[],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"app1","customerIds":null,"used":false,"creationDate":1378288615000,"helpText":null,"system":false,"name":"app1","id":"b4a5fa8e-cb7b-4ae9-800b-16e833a23a35","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":"mob1","techInfo":{"appTypeId":"99d55693-dacd-4f77-994a-f02a66176ff9","techGroupId":"CQ5","techVersions":null,"version":"5.6","customerIds":null,"used":false,"creationDate":1378288615000,"helpText":null,"system":false,"name":"AEM CQ","id":"0101a91b-559f-4b1b-9e30-a24ed5760d02","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":[],"selectedJSLibs":[],"selectedComponents":[],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"mob1","customerIds":null,"used":false,"creationDate":1378288615000,"helpText":null,"system":false,"name":"mob1","id":"0e9eb83e-478d-489f-8eca-bb1bfc2277af","displayName":null,"description":null,"status":null},{"modules":null,"pomFile":null,"appDirName":"web1","techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"Java","techVersions":null,"version":"1.7","customerIds":null,"used":false,"creationDate":1378288615000,"helpText":null,"system":false,"name":"java-standalone","id":"tech-java-standalone","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":["2d41a182-85f1-42a3-a67c-a0836792ba02"],"selectedJSLibs":[],"selectedComponents":[],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"web1","customerIds":null,"used":false,"creationDate":1378288615000,"helpText":null,"system":false,"name":"web1","id":"81b94a21-bc8a-4c3c-9bf8-3a4e4e98bb37","displayName":null,"description":null,"status":null}],"projectCode":"Test1","noOfApps":3,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"version":"1.0","customerIds":["photon"],"used":false,"creationDate":1378288615000,"helpText":null,"system":false,"name":"Test","id":"fb248f36-6b64-4f96-8cda-79663a36db91","displayName":null,"description":"Test Description","status":null},"status":"success"});	
				  }
				});	
			
			
			
			  $.mockjax({
				  url: commonVariables.webserviceurl+"dashboard?projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRD000008","data":{"java_31":{"appname":"java_31","appid":"5bb1db2f-ce28-4132-8395-2f83420c2594","projectid":"e575b42d-a5bd-4071-a4e8-eee82040ccd0","appcode":"java_31","dashboards":{"3ec61321-1c93-483d-8f8b-5d3522b1f884":{"url":"http://172.16.8.250:8089","username":"admin","widgets":{"a1ce7b0d-5b28-4aab-ab63-57be56fbed83":{"autorefresh":null,"starttime":"","endtime":"","colorcodes":{},"name":"ttabb","properties":{"type":["piechart"],"y":["serviceName"],"x":["Avg_ms"]},"query":"search host=\"MOHAMED_AS\"  | stats count as Count, min(presentation_service_response_time) as \"Min ms\", max(presentation_service_response_time) as \"Max ms\", avg(presentation_service_response_time) as \"Avg_ms\"  by serviceName | eval \"Avg_ms\"=round(Avg_ms,2)"},"1bdf254a-2dff-48eb-afc6-914c98aa168f":{"autorefresh":null,"starttime":"","endtime":"","colorcodes":{"y":{"Count":"#FFFF00","Minms":"#00FF00","Maxms":"#FF0000"}},"name":"barchart123","properties":{"color":["#CE0000"],"type":["table"],"y":["Count"],"x":["Count"]},"query":"search host=\"MOHAMED_AS\"  | stats count as Count, min(presentation_service_response_time) as \"Min ms\", max(presentation_service_response_time) as \"Max ms\", avg(presentation_service_response_time) as \"Avg_ms\"  by serviceName | eval \"Avg_ms\"=round(Avg_ms,2)"}},"dashboardname":"new","datatype":"Splunk","password":"devsplunk"},"a34e9fa0-6c44-4bc6-b6cd-89ffe61f2617":{"url":"http://172.16.8.250:8089","username":"admin","widgets":{"d418e5dd-6d5b-4c51-9442-1261ce5ec856":{"autorefresh":null,"starttime":"","endtime":"","colorcodes":{},"name":"line","properties":{"color":[""],"type":["table"],"y":["Count"],"x":["Count"]},"query":"search host=\"MOHAMED_AS\"  | stats count as Count, min(presentation_service_response_time) as \"Min ms\", max(presentation_service_response_time) as \"Max ms\", avg(presentation_service_response_time) as \"Avg_ms\"  by serviceName | eval \"Avg_ms\"=round(Avg_ms,2)"},"c3f36abc-f102-4145-b51b-a4a2e028c011":{"autorefresh":null,"starttime":"","endtime":"","colorcodes":{},"name":"s","properties":{"type":["table"],"y":["serviceName"],"x":["Count"]},"query":"search host=\"MOHAMED_AS\"  | stats count as Count, min(presentation_service_response_time) as \"Min ms\", max(presentation_service_response_time) as \"Max ms\", avg(presentation_service_response_time) as \"Avg_ms\"  by serviceName | eval \"Avg_ms\"=round(Avg_ms,2)"},"030160df-bb9f-46b6-a617-31a1bfe0758a":{"autorefresh":null,"starttime":"","endtime":"","colorcodes":{},"name":"s","properties":{"type":["piechart"],"y":["serviceName"],"x":["Count"]},"query":"search host=\"MOHAMED_AS\"  | stats count as Count, min(presentation_service_response_time) as \"Min ms\", max(presentation_service_response_time) as \"Max ms\", avg(presentation_service_response_time) as \"Avg_ms\"  by serviceName | eval \"Avg_ms\"=round(Avg_ms,2)"},"21332d4b-c949-4ac1-9c70-b1d03f68428e":{"autorefresh":null,"starttime":"","endtime":"","colorcodes":{"y":{"Count":""}},"name":"bar","properties":{"type":["barchart"],"y":["Count"],"x":["serviceName"]},"query":"search host=\"MOHAMED_AS\"  | stats count as Count, min(presentation_service_response_time) as \"Min ms\", max(presentation_service_response_time) as \"Max ms\", avg(presentation_service_response_time) as \"Avg_ms\"  by serviceName | eval \"Avg_ms\"=round(Avg_ms,2)"},"3cba3256-d953-47d0-937c-8a8374219c42":{"autorefresh":null,"starttime":"","endtime":"","colorcodes":{},"name":"tabbbbbb","properties":{"type":["table"],"y":["Count"],"x":["totalCount"]},"query":"search host=\"MOHAMED_AS\"  | stats count as Count, min(presentation_service_response_time) as \"Min ms\", max(presentation_service_response_time) as \"Max ms\", avg(presentation_service_response_time) as \"Avg_ms\"  by serviceName | eval \"Avg_ms\"=round(Avg_ms,2)"}},"dashboardname":"new123","datatype":"Splunk","password":"devsplunk"}}},"nodejs_31":{"appname":"nodejs_31","appid":"cc0bf1e0-3911-4b8c-acab-42cfdd985226","projectid":"e575b42d-a5bd-4071-a4e8-eee82040ccd0","appcode":"nodejs_31","dashboards":{}}},"status":"success"});
					  
				  }
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/search",	
				 type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"data":{"preview":false,"init_offset":0,"messages":[{"type":"DEBUG","text":"base lispy: [ AND host::mohamed_as ]"},{"type":"DEBUG","text":"search context: user=\"admin\", app=\"search\", bs-pathname=\"\/opt\/splunk\/etc\""}],"results":[{"serviceName":"CheckCaptcha","Count":"3","Min ms":"1142","Max ms":"2801","Avg_ms":"1710.33"},{"serviceName":"CreateSession","Count":"47","Min ms":"94","Max ms":"4420","Avg_ms":"1903.30"},{"serviceName":"GetBanks","Count":"3","Min ms":"923","Max ms":"3252","Avg_ms":"1923.67"},{"serviceName":"GetCaptcha","Count":"3","Min ms":"829","Max ms":"3052","Avg_ms":"2144.00"},{"serviceName":"GetCreditCards","Count":"3","Min ms":"2332","Max ms":"3036","Avg_ms":"2701.67"},{"serviceName":"GetCustomerProfile","Count":"4","Min ms":"641","Max ms":"2598","Avg_ms":"1447.50"},{"serviceName":"GetUserChallenges","Count":"3","Min ms":"11221","Max ms":"11478","Avg_ms":"11374.67"},{"serviceName":"Login","Count":"238","Min ms":"562","Max ms":"19913","Avg_ms":"1179.98"},{"serviceName":"RegisterCustomer","Count":"4","Min ms":"6573","Max ms":"16178","Avg_ms":"11976.75"},{"serviceName":"RegisterCustomerValidation","Count":"4","Min ms":"1094","Max ms":"2378","Avg_ms":"1517.50"},{"serviceName":"SendMoney","Count":"6","Min ms":"860","Max ms":"4017","Avg_ms":"1800.17"},{"serviceName":"SessionKeepAlive","Count":"3","Min ms":"720","Max ms":"1563","Avg_ms":"1115.67"},{"serviceName":"SignOff","Count":"3","Min ms":"986","Max ms":"1971","Avg_ms":"1460.67"},{"serviceName":"TerminateSession","Count":"3","Min ms":"813","Max ms":"2392","Avg_ms":"1856.00"},{"serviceName":"UpdateCustomerProfile","Count":"4","Min ms":"438","Max ms":"798","Avg_ms":"645.25"},{"serviceName":"VerifySession","Count":"6","Min ms":"704","Max ms":"3521","Avg_ms":"1961.33"}]},"exception":"null","responseCode":"PHRD000009","status":"success"});
				  }
				});
			
			
			var dash = new Clazz.com.components.dashboard.js.Dashboard();	
				Clazz.navigationController.push(dash, commonVariables.animation);
				setTimeout(function() {
					start();
					var count = $('ul.dashboardslist').children('li').length;
					equal(count, 2, "Dashboards Listed successfully");
				}, 4000);
		
			});
			self.createdashboardnamevalidation(dashboard,editpro,listalldash);
		},
		
		
		createdashboardnamevalidation : function(dashboard,editpro,listalldash) {
			var self = this;
			asyncTest("Test for Dashboard name validation", function() {
				$("#dashboard_name").val('');
				setTimeout(function() {
					start();
					$("#configure_widget").click();
					var b = $("#dashboard_name").attr('class');
					equal('errormessage', b, "Name validation for dashboard create done successfully");
					self.createdashboardusernamevalidation(dashboard,editpro,listalldash);
				}, 500);
			});
		},
		
		createdashboardusernamevalidation : function(dashboard,editpro,listalldash) {
			var self = this;
			asyncTest("Test for Dashboard username validation", function() {
				$("#dashboard_name").val('abc');
				$("#conf_username").val('');
				setTimeout(function() {
					start();
					$("#configure_widget").click();
					var b = $("#conf_username").attr('class');
					equal('errormessage', b, "Username validation for dashboard create done successfully");
					self.createdashboardpasswordvalidation(dashboard,editpro,listalldash);
				}, 500);
			});
		},
		
		createdashboardpasswordvalidation : function(dashboard,editpro,listalldash) {
			var self = this;
			asyncTest("Test for Dashboard password validation", function() {
				$("#dashboard_name").val('abc');
				$("#conf_username").val('admin');
				$("#conf_password").val('');
				setTimeout(function() {
					start();
					$("#configure_widget").click();
					var b = $("#conf_password").attr('class');
					equal('errormessage', b, "Password validation for dashboard create done successfully");
					self.createdashboardurlvalidation(dashboard,editpro,listalldash);
				}, 500);
			});
		},
		
		
		createdashboardurlvalidation : function(dashboard,editpro,listalldash) {
			var self = this;
			asyncTest("Test for Dashboard url validation", function() {
				$("#dashboard_name").val('abc');
				$("#conf_username").val('admin');
				$("#conf_password").val('manage');
				$("#config_url").val('');
				$("#configure_widget").click();
				setTimeout(function() {
					start();
					var b = $("#config_url").attr('class');
					equal('errormessage', b, "URL validation for dashboard create done successfully");
					self.deletedashboard(dashboard,editpro,listalldash);
				}, 500);
			});
		},
		
		deletedashboard : function(dashboard,editpro,listalldash) {
			var self = this;
			asyncTest("Test for Deleting Dashboard", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"dashboard",
				  type: "DELETE",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRD000012","data":null,"status":"success"});
				  }
				});
				var beforedel = $("ul.dashboardslist").children('li').length;
				$("ul.dashboardslist").children(0).find(".dashboard_delete").click();
				setTimeout(function() {
					start();
					$('input[name="deldashboard"]').click();
					var afterdel = $("ul.dashboardslist").children('li').length;
					equal(beforedel-1, afterdel, "Dashboard deleted successfully");
					self.createdashboard(dashboard,editpro,listalldash);
				}, 1000);
			});
		},
		
		createdashboard : function(dashboard,editpro,listalldash) {
			var self = this;
			asyncTest("Test for Creating Dashboard", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"dashboard",
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRD000001","data":"042b5de0-13fc-4c89-a01e-038d6969b028","status":"success"});
				  }
				});
				$("#dashboard_name").val('abc');
				$("#conf_username").val('admin');
				$("#conf_password").val('devsplunk');
				$("#config_url").val('http://172.16.8.250:8089');
				$("#configure_widget").click();
				setTimeout(function() {
					start();
					var b = $("#add_wid").attr('disabled');
					equal(undefined, b, "Dashboard created successfully");
					self.addwidgettable(dashboard,editpro,listalldash);
				}, 500);
			});
		},
		
		addwidgettable : function(dashboard,editpro,listalldash) {
			var self = this;
			asyncTest("Test for Creating Table", function() {
				var addwid = $.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/widget",
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRD000004","data":"c3f36abc-f102-4145-b51b-a4a2e028c011","status":"success"});
				  }
				});
				
				var get1 = $.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/widget/c3f36abc-f102-4145-b51b-a4a2e028c011?projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f&appDirName=app&dashboardid=042b5de0-13fc-4c89-a01e-038d6969b028",	
				 type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRD000005","data":{"autorefresh":null,"starttime":"","endtime":"","colorcodes":{},"name":"we","properties":{"type":["table"]},"query":"search host=\"MOHAMED_AS\"  | stats count as Count, min(presentation_service_response_time) as \"Min ms\", max(presentation_service_response_time) as \"Max ms\", avg(presentation_service_response_time) as \"Avg_ms\"  by serviceName | eval \"Avg_ms\"=round(Avg_ms,2)"},"status":"success"});
				  }
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/search",	
				 type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"data":{"preview":false,"init_offset":0,"messages":[{"type":"DEBUG","text":"base lispy: [ AND host::mohamed_as ]"},{"type":"DEBUG","text":"search context: user=\"admin\", app=\"search\", bs-pathname=\"\/opt\/splunk\/etc\""}],"results":[{"serviceName":"CheckCaptcha","Count":"3","Min ms":"1142","Max ms":"2801","Avg_ms":"1710.33"},{"serviceName":"CreateSession","Count":"47","Min ms":"94","Max ms":"4420","Avg_ms":"1903.30"},{"serviceName":"GetBanks","Count":"3","Min ms":"923","Max ms":"3252","Avg_ms":"1923.67"},{"serviceName":"GetCaptcha","Count":"3","Min ms":"829","Max ms":"3052","Avg_ms":"2144.00"},{"serviceName":"GetCreditCards","Count":"3","Min ms":"2332","Max ms":"3036","Avg_ms":"2701.67"},{"serviceName":"GetCustomerProfile","Count":"4","Min ms":"641","Max ms":"2598","Avg_ms":"1447.50"},{"serviceName":"GetUserChallenges","Count":"3","Min ms":"11221","Max ms":"11478","Avg_ms":"11374.67"},{"serviceName":"Login","Count":"238","Min ms":"562","Max ms":"19913","Avg_ms":"1179.98"},{"serviceName":"RegisterCustomer","Count":"4","Min ms":"6573","Max ms":"16178","Avg_ms":"11976.75"},{"serviceName":"RegisterCustomerValidation","Count":"4","Min ms":"1094","Max ms":"2378","Avg_ms":"1517.50"},{"serviceName":"SendMoney","Count":"6","Min ms":"860","Max ms":"4017","Avg_ms":"1800.17"},{"serviceName":"SessionKeepAlive","Count":"3","Min ms":"720","Max ms":"1563","Avg_ms":"1115.67"},{"serviceName":"SignOff","Count":"3","Min ms":"986","Max ms":"1971","Avg_ms":"1460.67"},{"serviceName":"TerminateSession","Count":"3","Min ms":"813","Max ms":"2392","Avg_ms":"1856.00"},{"serviceName":"UpdateCustomerProfile","Count":"4","Min ms":"438","Max ms":"798","Avg_ms":"645.25"},{"serviceName":"VerifySession","Count":"6","Min ms":"704","Max ms":"3521","Avg_ms":"1961.33"}]},"exception":"null","responseCode":"PHRD000009","status":"success"});
				  }
				});
				
				
				dashboard.dashboardListener.currentdashboardid = '042b5de0-13fc-4c89-a01e-038d6969b028';
				dashboard.dashboardListener.currentappname = 'app';
				$("#nameofwidget").val('newwidget');
				$("#query_add").val('search host="MOHAMED_AS"  | stats count as Count, min(presentation_service_response_time) as "Min ms", max(presentation_service_response_time) as "Max ms", avg(presentation_service_response_time) as "Avg_ms"  by serviceName | eval "Avg_ms"=round(Avg_ms,2)');
				dashboard.dashboardListener.createwidgetfunction();
				setTimeout(function() {
					start();
					var b = $(".noc_view").length;
					equal(1, b, "Table created successfully");
					self.addwidgetline(dashboard,editpro,listalldash,addwid,get1);
				}, 1000);
			});
		},
		
		addwidgetline : function(dashboard,editpro,listalldash,addwid,get1) {
			$.mockjaxClear(addwid);
			$.mockjaxClear(get1);
			var self = this;
			asyncTest("Test for Creating Line Chart", function() {
				var addwid2 = $.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/widget",
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRD000004","data":"d418e5dd-6d5b-4c51-9442-1261ce5ec856","status":"success"});
				  }
				});
				
				var get2 = $.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/widget/d418e5dd-6d5b-4c51-9442-1261ce5ec856?projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f&appDirName=app&dashboardid=042b5de0-13fc-4c89-a01e-038d6969b028",	
				 type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRD000005","data":{"autorefresh":null,"starttime":"","endtime":"","colorcodes":{},"name":"line","properties":{"color":[""],"type":["linechart"],"y":["Count"],"x":["Count"]},"query":"search host=\"MOHAMED_AS\"  | stats count as Count, min(presentation_service_response_time) as \"Min ms\", max(presentation_service_response_time) as \"Max ms\", avg(presentation_service_response_time) as \"Avg_ms\"  by serviceName | eval \"Avg_ms\"=round(Avg_ms,2)"},"status":"success"});
				  }
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/search",	
				 type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"data":{"preview":false,"init_offset":0,"messages":[{"type":"DEBUG","text":"base lispy: [ AND host::mohamed_as ]"},{"type":"DEBUG","text":"search context: user=\"admin\", app=\"search\", bs-pathname=\"\/opt\/splunk\/etc\""}],"results":[{"serviceName":"CheckCaptcha","Count":"3","Min ms":"1142","Max ms":"2801","Avg_ms":"1710.33"},{"serviceName":"CreateSession","Count":"47","Min ms":"94","Max ms":"4420","Avg_ms":"1903.30"},{"serviceName":"GetBanks","Count":"3","Min ms":"923","Max ms":"3252","Avg_ms":"1923.67"},{"serviceName":"GetCaptcha","Count":"3","Min ms":"829","Max ms":"3052","Avg_ms":"2144.00"},{"serviceName":"GetCreditCards","Count":"3","Min ms":"2332","Max ms":"3036","Avg_ms":"2701.67"},{"serviceName":"GetCustomerProfile","Count":"4","Min ms":"641","Max ms":"2598","Avg_ms":"1447.50"},{"serviceName":"GetUserChallenges","Count":"3","Min ms":"11221","Max ms":"11478","Avg_ms":"11374.67"},{"serviceName":"Login","Count":"238","Min ms":"562","Max ms":"19913","Avg_ms":"1179.98"},{"serviceName":"RegisterCustomer","Count":"4","Min ms":"6573","Max ms":"16178","Avg_ms":"11976.75"},{"serviceName":"RegisterCustomerValidation","Count":"4","Min ms":"1094","Max ms":"2378","Avg_ms":"1517.50"},{"serviceName":"SendMoney","Count":"6","Min ms":"860","Max ms":"4017","Avg_ms":"1800.17"},{"serviceName":"SessionKeepAlive","Count":"3","Min ms":"720","Max ms":"1563","Avg_ms":"1115.67"},{"serviceName":"SignOff","Count":"3","Min ms":"986","Max ms":"1971","Avg_ms":"1460.67"},{"serviceName":"TerminateSession","Count":"3","Min ms":"813","Max ms":"2392","Avg_ms":"1856.00"},{"serviceName":"UpdateCustomerProfile","Count":"4","Min ms":"438","Max ms":"798","Avg_ms":"645.25"},{"serviceName":"VerifySession","Count":"6","Min ms":"704","Max ms":"3521","Avg_ms":"1961.33"}]},"exception":"null","responseCode":"PHRD000009","status":"success"});
				  }
				});
				dashboard.dashboardListener.currentdashboardid = '042b5de0-13fc-4c89-a01e-038d6969b028';
				dashboard.dashboardListener.dashboardname = 'abc';
				dashboard.dashboardListener.currentappname = 'app';
				$("#nameofwidget").val('newwidget4');
				$("#query_add").val('search host="MOHAMED_AS"  | stats count as Count, min(presentation_service_response_time) as "Min ms", max(presentation_service_response_time) as "Max ms", avg(presentation_service_response_time) as "Avg_ms"  by serviceName | eval "Avg_ms"=round(Avg_ms,2)');
				$('#widgetType option:selected').val('linechart');
				$("img[name='execute_query']").click();
				$("#content_d418e5dd-6d5b-4c51-9442-1261ce5ec856").attr('widgetid','d418e5dd-6d5b-4c51-9442-1261ce5ec856');
				$("#content_d418e5dd-6d5b-4c51-9442-1261ce5ec856").attr('widgetname','newwidget4');
				$("select.xaxis option:selected").val('Count');
				$("select.yaxis option:selected").val('Count');
				setTimeout(function() {
					start();
					dashboard.dashboardListener.createwidgetfunction();
					var b = $(".noc_view").length;
					equal(2, b, "Line Chart created successfully");
					self.addwidgetbar(dashboard,editpro,listalldash,addwid2,get2);
				}, 1000);
			});
		},
		
		addwidgetbar : function(dashboard,editpro,listalldash,addwid2,get2) {
			$.mockjaxClear(addwid2);
			$.mockjaxClear(get2);
			var self = this;
			asyncTest("Test for Creating Bar Chart", function() {
				var addwid3 = $.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/widget",
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRD000004","data":"21332d4b-c949-4ac1-9c70-b1d03f68428e","status":"success"});
				  }
				});
				
				var get3 = $.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/widget/21332d4b-c949-4ac1-9c70-b1d03f68428e?projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f&appDirName=app&dashboardid=042b5de0-13fc-4c89-a01e-038d6969b028",	
				 type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRD000005","data":{"autorefresh":null,"starttime":"","endtime":"","colorcodes":{"y":{"Count":""}},"name":"bar","properties":{"type":["barchart"],"y":["Count"],"x":["serviceName"]},"query":"search host=\"MOHAMED_AS\"  | stats count as Count, min(presentation_service_response_time) as \"Min ms\", max(presentation_service_response_time) as \"Max ms\", avg(presentation_service_response_time) as \"Avg_ms\"  by serviceName | eval \"Avg_ms\"=round(Avg_ms,2)"},"status":"success"});
				  }
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/search",	
				 type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"data":{"preview":false,"init_offset":0,"messages":[{"type":"DEBUG","text":"base lispy: [ AND host::mohamed_as ]"},{"type":"DEBUG","text":"search context: user=\"admin\", app=\"search\", bs-pathname=\"\/opt\/splunk\/etc\""}],"results":[{"serviceName":"CheckCaptcha","Count":"3","Min ms":"1142","Max ms":"2801","Avg_ms":"1710.33"},{"serviceName":"CreateSession","Count":"47","Min ms":"94","Max ms":"4420","Avg_ms":"1903.30"},{"serviceName":"GetBanks","Count":"3","Min ms":"923","Max ms":"3252","Avg_ms":"1923.67"},{"serviceName":"GetCaptcha","Count":"3","Min ms":"829","Max ms":"3052","Avg_ms":"2144.00"},{"serviceName":"GetCreditCards","Count":"3","Min ms":"2332","Max ms":"3036","Avg_ms":"2701.67"},{"serviceName":"GetCustomerProfile","Count":"4","Min ms":"641","Max ms":"2598","Avg_ms":"1447.50"},{"serviceName":"GetUserChallenges","Count":"3","Min ms":"11221","Max ms":"11478","Avg_ms":"11374.67"},{"serviceName":"Login","Count":"238","Min ms":"562","Max ms":"19913","Avg_ms":"1179.98"},{"serviceName":"RegisterCustomer","Count":"4","Min ms":"6573","Max ms":"16178","Avg_ms":"11976.75"},{"serviceName":"RegisterCustomerValidation","Count":"4","Min ms":"1094","Max ms":"2378","Avg_ms":"1517.50"},{"serviceName":"SendMoney","Count":"6","Min ms":"860","Max ms":"4017","Avg_ms":"1800.17"},{"serviceName":"SessionKeepAlive","Count":"3","Min ms":"720","Max ms":"1563","Avg_ms":"1115.67"},{"serviceName":"SignOff","Count":"3","Min ms":"986","Max ms":"1971","Avg_ms":"1460.67"},{"serviceName":"TerminateSession","Count":"3","Min ms":"813","Max ms":"2392","Avg_ms":"1856.00"},{"serviceName":"UpdateCustomerProfile","Count":"4","Min ms":"438","Max ms":"798","Avg_ms":"645.25"},{"serviceName":"VerifySession","Count":"6","Min ms":"704","Max ms":"3521","Avg_ms":"1961.33"}]},"exception":"null","responseCode":"PHRD000009","status":"success"});
				  }
				});
				dashboard.dashboardListener.currentdashboardid = '042b5de0-13fc-4c89-a01e-038d6969b028';
				dashboard.dashboardListener.dashboardname = 'abc';
				dashboard.dashboardListener.currentappname = 'app';
				$("#nameofwidget").val('newwidget3');
				$("#query_add").val('search host="MOHAMED_AS"  | stats count as Count, min(presentation_service_response_time) as "Min ms", max(presentation_service_response_time) as "Max ms", avg(presentation_service_response_time) as "Avg_ms"  by serviceName | eval "Avg_ms"=round(Avg_ms,2)');
				$('#widgetType option:selected').val('barchart');
				$("img[name='execute_query']").click();
				$("#content_21332d4b-c949-4ac1-9c70-b1d03f68428e").attr('widgetid','21332d4b-c949-4ac1-9c70-b1d03f68428e');
				$("#content_21332d4b-c949-4ac1-9c70-b1d03f68428e").attr('widgetname','newwidget3');
				$("select.baraxis option:selected").val('Count');
				$("#tabforbar").find('ul').children(1).find('input[type="checkbox"]').prop('checked', true);
				setTimeout(function() {
					start();
					dashboard.dashboardListener.createwidgetfunction();
					var b = $(".noc_view").length;
					equal(3, b, "Bar Chart created successfully");
					self.addwidgetpie(dashboard,editpro,listalldash,addwid3,get3);
				}, 1000);
			});
		},
		
		addwidgetpie : function(dashboard,editpro,listalldash,addwid3,get3) {
			$.mockjaxClear(addwid3);
			$.mockjaxClear(get3);
			var self = this;
			asyncTest("Test for Creating Pie Chart", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/widget",
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRD000004","data":"030160df-bb9f-46b6-a617-31a1bfe0758a","status":"success"});
				  }
				});
				
				var get4 = $.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/widget/030160df-bb9f-46b6-a617-31a1bfe0758a?projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f&appDirName=app&dashboardid=042b5de0-13fc-4c89-a01e-038d6969b028",	
				 type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRD000005","data":{"autorefresh":null,"starttime":"","endtime":"","colorcodes":{},"name":"s","properties":{"type":["piechart"],"y":["serviceName"],"x":["Count"]},"query":"search host=\"MOHAMED_AS\"  | stats count as Count, min(presentation_service_response_time) as \"Min ms\", max(presentation_service_response_time) as \"Max ms\", avg(presentation_service_response_time) as \"Avg_ms\"  by serviceName | eval \"Avg_ms\"=round(Avg_ms,2)"},"status":"success"});
				  }
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/search",	
				 type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"data":{"preview":false,"init_offset":0,"messages":[{"type":"DEBUG","text":"base lispy: [ AND host::mohamed_as ]"},{"type":"DEBUG","text":"search context: user=\"admin\", app=\"search\", bs-pathname=\"\/opt\/splunk\/etc\""}],"results":[{"serviceName":"CheckCaptcha","Count":"3","Min ms":"1142","Max ms":"2801","Avg_ms":"1710.33"},{"serviceName":"CreateSession","Count":"47","Min ms":"94","Max ms":"4420","Avg_ms":"1903.30"},{"serviceName":"GetBanks","Count":"3","Min ms":"923","Max ms":"3252","Avg_ms":"1923.67"},{"serviceName":"GetCaptcha","Count":"3","Min ms":"829","Max ms":"3052","Avg_ms":"2144.00"},{"serviceName":"GetCreditCards","Count":"3","Min ms":"2332","Max ms":"3036","Avg_ms":"2701.67"},{"serviceName":"GetCustomerProfile","Count":"4","Min ms":"641","Max ms":"2598","Avg_ms":"1447.50"},{"serviceName":"GetUserChallenges","Count":"3","Min ms":"11221","Max ms":"11478","Avg_ms":"11374.67"},{"serviceName":"Login","Count":"238","Min ms":"562","Max ms":"19913","Avg_ms":"1179.98"},{"serviceName":"RegisterCustomer","Count":"4","Min ms":"6573","Max ms":"16178","Avg_ms":"11976.75"},{"serviceName":"RegisterCustomerValidation","Count":"4","Min ms":"1094","Max ms":"2378","Avg_ms":"1517.50"},{"serviceName":"SendMoney","Count":"6","Min ms":"860","Max ms":"4017","Avg_ms":"1800.17"},{"serviceName":"SessionKeepAlive","Count":"3","Min ms":"720","Max ms":"1563","Avg_ms":"1115.67"},{"serviceName":"SignOff","Count":"3","Min ms":"986","Max ms":"1971","Avg_ms":"1460.67"},{"serviceName":"TerminateSession","Count":"3","Min ms":"813","Max ms":"2392","Avg_ms":"1856.00"},{"serviceName":"UpdateCustomerProfile","Count":"4","Min ms":"438","Max ms":"798","Avg_ms":"645.25"},{"serviceName":"VerifySession","Count":"6","Min ms":"704","Max ms":"3521","Avg_ms":"1961.33"}]},"exception":"null","responseCode":"PHRD000009","status":"success"});
				  }
				});
				dashboard.dashboardListener.currentdashboardid = '042b5de0-13fc-4c89-a01e-038d6969b028';
				dashboard.dashboardListener.dashboardname = 'abc';
				dashboard.dashboardListener.currentappname = 'app';
				
				$("#nameofwidget").val('');
				dashboard.dashboardListener.createwidgetfunction();
				
				$("#nameofwidget").val('newwidget2');
				$("#query_add").val('');
				dashboard.dashboardListener.createwidgetfunction();
				
				$("#nameofwidget").val('newwidget2');
				$("#query_add").val('search host="MOHAMED_AS"  | stats count as Count, min(presentation_service_response_time) as "Min ms", max(presentation_service_response_time) as "Max ms", avg(presentation_service_response_time) as "Avg_ms"  by serviceName | eval "Avg_ms"=round(Avg_ms,2)');
				$('#widgetType option:selected').val('piechart');
				$("img[name='execute_query']").click();
				$("#content_030160df-bb9f-46b6-a617-31a1bfe0758a").attr('widgetid','030160df-bb9f-46b6-a617-31a1bfe0758a');
				$("#content_030160df-bb9f-46b6-a617-31a1bfe0758a").attr('widgetname','newwidget2');
				$("select.percentval option:selected").val('Count');
				$("select.legendval option:selected").val('serviceName');
				setTimeout(function() {
					start();
					dashboard.dashboardListener.createwidgetfunction();
					var b = $(".noc_view").length;
					equal(4, b, "Pie Chart created successfully");
					self.updatetable(dashboard,editpro,listalldash,get4);
				}, 1000);
			});
		},
		
		updatetable : function(dashboard,editpro,listalldash,get4) {
			var self = this;
			$.mockjaxClear(get4);
			asyncTest("Test for Updating Table", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/widget",
				  type: "PUT",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRD000006","data":null,"status":"success"});
				  }
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/widget/030160df-bb9f-46b6-a617-31a1bfe0758a?projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f&appDirName=app&dashboardid=042b5de0-13fc-4c89-a01e-038d6969b028",	
				 type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRD000005","data":{"autorefresh":null,"starttime":"","endtime":"","colorcodes":{"y":{"Count":"#FFFF00","Minms":"#00FF00","Maxms":"#FF0000"}},"name":"barchart123","properties":{"color":["#CE0000"],"type":["table"],"y":["Count"],"x":["Count"]},"query":"search host=\"MOHAMED_AS\"  | stats count as Count, min(presentation_service_response_time) as \"Min ms\", max(presentation_service_response_time) as \"Max ms\", avg(presentation_service_response_time) as \"Avg_ms\"  by serviceName | eval \"Avg_ms\"=round(Avg_ms,2)"},"status":"success"});
				  }
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/search",	
				 type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"data":{"preview":false,"init_offset":0,"messages":[{"type":"DEBUG","text":"base lispy: [ AND host::mohamed_as ]"},{"type":"DEBUG","text":"search context: user=\"admin\", app=\"search\", bs-pathname=\"\/opt\/splunk\/etc\""}],"results":[{"serviceName":"CheckCaptcha","Count":"3","Min ms":"1142","Max ms":"2801","Avg_ms":"1710.33"},{"serviceName":"CreateSession","Count":"47","Min ms":"94","Max ms":"4420","Avg_ms":"1903.30"},{"serviceName":"GetBanks","Count":"3","Min ms":"923","Max ms":"3252","Avg_ms":"1923.67"},{"serviceName":"GetCaptcha","Count":"3","Min ms":"829","Max ms":"3052","Avg_ms":"2144.00"},{"serviceName":"GetCreditCards","Count":"3","Min ms":"2332","Max ms":"3036","Avg_ms":"2701.67"},{"serviceName":"GetCustomerProfile","Count":"4","Min ms":"641","Max ms":"2598","Avg_ms":"1447.50"},{"serviceName":"GetUserChallenges","Count":"3","Min ms":"11221","Max ms":"11478","Avg_ms":"11374.67"},{"serviceName":"Login","Count":"238","Min ms":"562","Max ms":"19913","Avg_ms":"1179.98"},{"serviceName":"RegisterCustomer","Count":"4","Min ms":"6573","Max ms":"16178","Avg_ms":"11976.75"},{"serviceName":"RegisterCustomerValidation","Count":"4","Min ms":"1094","Max ms":"2378","Avg_ms":"1517.50"},{"serviceName":"SendMoney","Count":"6","Min ms":"860","Max ms":"4017","Avg_ms":"1800.17"},{"serviceName":"SessionKeepAlive","Count":"3","Min ms":"720","Max ms":"1563","Avg_ms":"1115.67"},{"serviceName":"SignOff","Count":"3","Min ms":"986","Max ms":"1971","Avg_ms":"1460.67"},{"serviceName":"TerminateSession","Count":"3","Min ms":"813","Max ms":"2392","Avg_ms":"1856.00"},{"serviceName":"UpdateCustomerProfile","Count":"4","Min ms":"438","Max ms":"798","Avg_ms":"645.25"},{"serviceName":"VerifySession","Count":"6","Min ms":"704","Max ms":"3521","Avg_ms":"1961.33"}]},"exception":"null","responseCode":"PHRD000009","status":"success"});
				  }
				});
				dashboard.dashboardListener.currentdashboardid = '042b5de0-13fc-4c89-a01e-038d6969b028';
				dashboard.dashboardListener.dashboardname = 'abc';
				dashboard.dashboardListener.currentappname = 'app';
				$("#content_030160df-bb9f-46b6-a617-31a1bfe0758a").attr('dynid','030160df-bb9f-46b6-a617-31a1bfe0758a');
				$("#content_030160df-bb9f-46b6-a617-31a1bfe0758a").find('.settings_img').click();
				$("#nameofwidget").val('newwidget2');
				$("#query_add").val('search host="MOHAMED_AS"  | stats count as Count, min(presentation_service_response_time) as "Min ms", max(presentation_service_response_time) as "Max ms", avg(presentation_service_response_time) as "Avg_ms"  by serviceName | eval "Avg_ms"=round(Avg_ms,2)');
				$('#widgetType option:selected').val('table');
				$("img[name='execute_query']").click();
				$("#content_030160df-bb9f-46b6-a617-31a1bfe0758a").attr('widgetid','030160df-bb9f-46b6-a617-31a1bfe0758a');
				$("#content_030160df-bb9f-46b6-a617-31a1bfe0758a").attr('widgetname','newwidget2');
				
				setTimeout(function() {
					start();
					$('#update_tab').click();
					var flag;
					if($("#content_030160df-bb9f-46b6-a617-31a1bfe0758a").find('.minitable').children('table').hasClass('tablesorter')) 
						flag = 1;
					else 
						flag = 0;
					equal(1, flag, "Table updated successfully");
					self.deletewidget(dashboard,editpro,listalldash);
				}, 3000);
			});
		},
		
		deletewidget : function(dashboard,editpro,listalldash) {
			var self = this;
			asyncTest("Test for Deleting Widget", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/widget",
				  type: "DELETE",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRD000012","data":null,"status":"success"});
				  }
				});
				$("#content_030160df-bb9f-46b6-a617-31a1bfe0758a").attr('widgetid','030160df-bb9f-46b6-a617-31a1bfe0758a');
				var beforedel = $(".noc_view").length;
				$('input[name="close_widget"]').click();
				setTimeout(function() {
					start();
					$('input[name="delwidget"]').click();
					var afterdel = $(".noc_view").length;
					equal(beforedel-1, afterdel, "Widget deleted successfully");
					self.popuprender(dashboard);
				}, 1000);
			});
		},
		
		popuprender : function(dashboard) {
			var self = this;
			asyncTest("Test for Popup Render", function() {
			
				$("#dashboard_name").addClass('errormessage');
				$('#config_noc').click();
				$("#timeout").prop('checked',true);
				$("#timeout").change();
				setTimeout(function() {
					start();
					var a = $("#noc_config").css('display');
					equal(a,'block', "Configure Popup rendered successfully");
					$('#add_wid').click();
					var b = $("#add_widget").css('display');
					equal(b,'block', "Add Widget Popup rendered successfully");
					self.changeevent(dashboard);
				}, 1000);
			});
		},
		
		changeevent : function(dashboard) {
			var self = this;
			asyncTest("Test for Change Event", function() {
				$("#widgetType").children('option:selected').val('table');
				setTimeout(function() {
					start();
					$("#widgetType").change();
					var a = $(".filter-option").text();
					equal(a,'Table', "Change event done successfully");
					$("#nameofwidget").val('');
					$("img[name='execute_query']").click();
					
					$("#nameofwidget").val('abc');
					$("#query_add").val('');
					$("img[name='execute_query']").click();
					self.dashboardget(dashboard);
				}, 1000);
			});
		},
		
		dashboardget : function(dashboard) {
			var self = this;
			asyncTest("Test for Getting Dashboard", function() {
				 $.mockjax({
				  url: commonVariables.webserviceurl+"dashboard/3ec61321-1c93-483d-8f8b-5d3522b1f884?projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f&appDirName=java_31",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRD000002","data":{"url":"http://172.16.8.250:8089","username":"admin","datatype":"Splunk","dashboardname":"new","widgets":{"1bdf254a-2dff-48eb-afc6-914c98aa168f":{"autorefresh":null,"starttime":"","endtime":"","colorcodes":{"y":{"Count":"#FFFF00","Minms":"#00FF00","Maxms":"#FF0000"}},"name":"barchart123","properties":{"color":["#CE0000"],"type":["table"],"y":["Count"],"x":["Count"]},"query":"search host=\"MOHAMED_AS\"  | stats count as Count, min(presentation_service_response_time) as \"Min ms\", max(presentation_service_response_time) as \"Max ms\", avg(presentation_service_response_time) as \"Avg_ms\"  by serviceName | eval \"Avg_ms\"=round(Avg_ms,2)"}},"password":"devsplunk"},"status":"success"});
				  }
				}); 
				$(".dashboardslist li a").click();
				setTimeout(function() {
					start();
					var a = $('.noc_view').length;
					equal(1,a, "Dashboard get done successfully");
					$('.enlarge_btn').click();
					$('.enlarge_btn').click();
					//self.listalldashboards(dashboard,editpro,listalldash);
				}, 1000);
			});
		}
		
	}

});	
		
		
		
			
	