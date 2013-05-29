define(["framework/widget", "codequality/api/codequalityAPI"], function() {

	Clazz.createPackage("com.components.codequality.js.listener");

	Clazz.com.components.codequality.js.listener.CodequalityListener = Clazz.extend(Clazz.Widget, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		codequalityAPI : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			this.codequalityAPI = new Clazz.com.components.codequality.js.api.CodeQualityAPI();
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
		},
		
		codeValidate : function() {
			var self = this;
			var appdetails = self.codequalityAPI.localVal.getJson('appdetails');
			self.codequalityAPI.codequality(self.getRequestHeader(appdetails , "validate-code"), function(response) {
				$('#logDiv').html('');
				$('#logDiv').append(response.log);
				$('#logDiv').append("<br>") ;
				if(response.status == "STARTED"){
					self.readLog(response);
				}			
				else if(response.status == 'ERROR'){
					$('#logDiv').append(response.service_exception) ;
				}
			});
		},
		
		readLog : function(resDatt){
			var self = this;
			self.codequalityAPI.codequality(self.getRequestHeader(resDatt , "readlog"), function(response) {
				$('#logDiv').append(response.log) ;
				$('#logDiv').append("<br>") ;
				if(response.status == 'INPROGRESS'){
					self.readLog(resDatt);
				}			
			});
		},
		
		getRequestHeader : function(inputData, action) {
			var self=this, header, username, appId, customerId, projectId;
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: '',
				data: ''
			}
			if(action == "validate-code"){
				var appdetails = self.codequalityAPI.localVal.getJson('appdetails');
				appId = appdetails.data.appInfos[0].id;
				projectId = appdetails.data.id;
				customerId = appdetails.data.customerIds[0];
				username = self.codequalityAPI.localVal.getSession('username');

				header.requestMethod ="POST";
				header.webserviceurl = commonVariables.webserviceurl+"app/codeValidate?username="+username+"&appId="+appId+"&customerId="+customerId+"&goal=validate-code&phase=validate-code&projectId="+projectId+"&resultJson=&sonar=src&stFileFunction=";
//header.webserviceurl =   "http://localhost:8080/framework/rest/api/app/codeValidate?username=sudhakar_rag&appId=5e579649-0971-4d67-87db-15d9b91e0eff&customerId=photon&goal=validate-code&phase=validate-code&projectId=df807574-879e-4547-9236-551b7daf723c&resultJson=&resultJson=&sonar=src&sonar=src&stFileFunction=&stFileFunction=";
			}
			if(action == "readlog"){
				var uniquekey = inputData.uniquekey;
				header.webserviceurl = commonVariables.webserviceurl+"app/readlog?uniquekey="+uniquekey;
			}

			return header;
		},
		
		onProjects : function() {
		
		}
		
	});

	return Clazz.com.components.codequality.js.listener.CodequalityListener;
});