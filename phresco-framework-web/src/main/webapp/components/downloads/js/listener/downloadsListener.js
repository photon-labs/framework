define([], function() {

	Clazz.createPackage("com.components.downloads.js.listener");

	Clazz.com.components.downloads.js.listener.DownloadsListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
		},
		
		getActionHeader : function(requestBody, action) {
			var self=this;
			var customerId = self.getCustomer();
			var data = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			var userId = data.id;
			var header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			};
			if (action === "getDownloads") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "util/downloads?customerId="+customerId+ "&userId="+userId;
			} 
			return header;
		},

		getdownloads : function(header, callback) {
			var self = this;
			try {
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if(callback !== undefined)
						{
							callback(response);
						}
					},

					function(textStatus) {
					}
				);
			} catch(exception) {
			}
		},
	});
	return Clazz.com.components.downloads.js.listener.DownloadsListener;
});