define(["framework/widget", "dynamicPage/api/dynamicPageAPI", "common/loading"], function() {

	Clazz.createPackage("com.components.dynamic.js.listener");

	Clazz.com.components.dynamic.js.listener.DynamicPageListener = Clazz.extend(Clazz.Widget, {
		localStorageAPI : null,
		loadingScreen : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
			this.dynamicPageAPI = new Clazz.com.components.dynamicPage.js.api.DynamicPageAPI();
		},
		
		/***
		 * Get dynamic page content from service
		 * 
		 * @header: constructed header for each call
		 */
		getServiceContent : function(params, callback) {
			try{
				
				var self = this, header = self.getRequestHeader();
			
				if(self.parameterValidation(params)){
					self.loadingScreen.showLoading();
					
					self.dynamicPageAPI.getContent(header, 
						function(response){
							if(response != undefined && response != null){
								self.constructHtml(response, callback);
							} else {
								//responce value failed
								callback("<div>Responce value failed</div>");
								self.loadingScreen.removeLoading();
							}
						}, 
						function(serviceError){
							//service access failed
							callback("<div>service access failed</div>");
							self.loadingScreen.removeLoading();
						}
					);
				}
			}catch(error){
				//Exception
				self.loadingScreen.removeLoading();
			}
		},

		parameterValidation : function(){
			var self = this, bCheck = false;
			return bCheck;
		},
		
		constructHtml : function(response, callback){
			var htmlTag = "";
			callback(htmlTag);
		},
		
		/***
		 * provides the request header
		 * @return: returns the contructed header
		 */
		getRequestHeader : function() {
			var header = {
				contentType: "application/json",
				requestMethod: "POST",
				dataType: "json",
				requestPostBody: JSON.stringify({}),
				webserviceurl: commonVariables.webserviceurl + commonVariables.dynamicPageContext
			}

			return header;
		}
	});

	return Clazz.com.components.dynamic.js.listener.DynamicPageListener;
});