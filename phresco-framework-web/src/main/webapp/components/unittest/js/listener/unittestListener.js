define(["framework/widget", "unittest/api/unittestAPI"], function() {

	Clazz.createPackage("com.components.unittest.js.listener");

	Clazz.com.components.unittest.js.listener.UnitTestlistener = Clazz.extend(Clazz.Widget, {
		
		unitTestAPI : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			self.unitTestAPI =  new Clazz.com.components.unittest.js.api.UnitTestAPI();
			self.loadingScreen = new Clazz.com.js.widget.common.Loading();
		},
		
		onUnitTestResult : function() {
			$("#uniTestResultTab").show();
			$("#unitTestTab").hide();
			$(".unit_view").css("display", "block");
		},
		
		onUnitTestDesc : function() {
			$("#uniTestResultTab").hide();
			$("#uniTestDesc").show();
		},
		
		onUnitTestGraph : function() {
			$("#uniTestResultTab").hide();
			$("#uniTestDesc").hide();
			$("#graphView").show();
			$("a[name=unitTestGraph]").html('')

			$("a[name=unitTestGraph]").html('<img src="themes/default/images/helios/quality_graph_on.png" width="25" height="25" border="0" alt=""><b>Graph View</b>');
		},
		
		/***
		 * provides the request header
		 *
		 * @synonymRequestBody: request body of synonym
		 * @return: returns the contructed header
		 */
		getActionHeader : function(requestBody, action) {
			var self=this, header, data = {}, userId;
			data = JSON.parse(self.unitTestAPI.localVal.getSession('userInfo'));
			userId = data.id;
			appDirName = self.unitTestAPI.localVal.getJson("appDirName");
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			}
					
			if(action == "get") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/" + commonVariables.unit + "?userId="+userId+"&appDirName="+appDirName;				
			}
			return header;
		},
		
		getUnitTestReportOptions : function(header, callback) {
			var self = this;
			try {
				self.loadingScreen.showLoading();
				self.unitTestAPI.unittest(header,
					function(response) {
						if (response !== null) {
							self.loadingScreen.removeLoading();
							callback(response);
						} else {
							self.loadingScreen.removeLoading();
							callback({ "status" : "service failure"});
						}

					},

					function(textStatus) {
						self.loadingScreen.removeLoading();
					}
				);
			} catch(exception) {
				self.loadingScreen.removeLoading();
			}

		},
	});

	return Clazz.com.components.unittest.js.listener.UnitTestlistener;
});