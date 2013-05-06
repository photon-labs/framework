define(["framework/widget", "features/api/featuresAPI", "features/features",  "application/application",  "projectlist/projectList"], function() {

	Clazz.createPackage("com.components.features.js.listener");

	Clazz.com.components.features.js.listener.FeaturesListener = Clazz.extend(Clazz.Widget, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		featuresAPI : null,
		appinfoContent : null,
		projectListContent : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			self = this;	
			self.featuresAPI = new Clazz.com.components.features.js.api.FeaturesAPI();
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
		},
		
		cancelUpdate : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.projectListContent = new Clazz.com.components.projectlist.js.ProjectList();
			Clazz.navigationController.push(self.projectListContent, true);
		},

		search : function (txtSearch, divId){
       		var txtSearch = txtSearch.toLowerCase();           		
			if (txtSearch != "") {
				$("#"+divId+" li").hide();//To hide all the ul
				var hasRecord = false;				
				var i=0;
				$("#"+divId+" li").each(function() {//To search for the txtSearch and search option thru all td
					var tdText = $(this).text().toLowerCase();
					if (tdText.match(txtSearch)) {
						$(this).show();
						hasRecord = true;
						i++;
					}
				});					
			}
			else {
				
				$("#"+divId+" li").show();
			}
       	},

       	getFeaturesList : function(header, callback) {
			var self = this;
			try {

				self.featuresAPI.features(header,
					function(response) {
						if (response !== null) {
							callback(response);
						} else {
							callback({ "status" : "service failure"});
						}

					},

					function(textStatus) {

					}
				);
			} catch(exception) {
				
			}

		},

		showLoad : function(){
			var self = this;
			self.loadingScreen.showLoading();
		},

		hideLoad : function(){
			var self = this;
			self.loadingScreen.removeLoading();
		},

		/***
		 * provides the request header
		 *
		 * @synonymRequestBody: request body of synonym
		 * @return: returns the contructed header
		 */
		getRequestHeader : function(projectRequestBody, type) {
			if(type == "features"){
				var type = "FEATURE";
			}
			if(type == "jsibraries"){
				var type = "JSLIBRARIES";
			}
			if(type == "components"){
				var type = "COMPONENTS";
			}
			var userId = JSON.parse(self.featuresAPI.localVal.getSession("userInfo"));
			var header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: commonVariables.webserviceurl+commonVariables.featurePageContext+"/list?customerId=photon&techId=tech-java-webservice&type="+type+"&userId="+userId.id
			};

			return header;
		}
		
	});

	return Clazz.com.components.features.js.listener.FeaturesListener;
});