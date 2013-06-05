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
			self.scrollbarUpdate();
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
		
		getFeaturesUpdate : function(header, callback) {
			var self = this;
			try {
				self.loadingScreen.showLoading();
				self.featuresAPI.features(header,
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
		
		showLoad : function(){
			var self = this;
			self.loadingScreen.showLoading();
		},
		
		scrollbarEnable : function(){
			$("#content_1").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				updateOnContentResize: true
			});
			
			$("#content_2").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin"
			});
			
			$("#content_3").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin"
			});
		},
		scrollbarUpdate : function(){
			$("#content_1").mCustomScrollbar("update"); 
			$("#content_2").mCustomScrollbar("update"); 
			$("#content_3").mCustomScrollbar("update"); 
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
		 //header.webserviceurl: commonVariables.webserviceurl+commonVariables.featurePageContext+"//desc?customerId=photon&artifactGroupId="+type+"&userId="+userId.id*/
		getRequestHeader : function(projectRequestBody, type, descid) {
			var url;
			var userId = JSON.parse(self.featuresAPI.localVal.getSession("userInfo"));
			var appDirName = self.featuresAPI.localVal.getSession("appDirName");
			var techId = commonVariables.techId;
			var header = {
				contentType: "application/json",
				dataType: "json"
			};
			if(type === "FEATURE" || type === "JAVASCRIPT" || type === "COMPONENT"){
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.featurePageContext+"/list?customerId=photon&techId="+ techId +"&type="+type+"&userId="+userId.id;
			} else if (type === "desc") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.featurePageContext+"/desc?&artifactGroupId="+descid+"&userId="+userId.id;
			} else {
				header.requestMethod = "PUT";
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.projectlistContext + "/updateFeature?customerId=photon&artifactGroupId="+descid+"&userId="+userId.id+"&appDirName="+appDirName;
			}
			return header;
		}
		
	});

	return Clazz.com.components.features.js.listener.FeaturesListener;
});