define(["downloads/listener/downloadsListener"], function() {
	Clazz.createPackage("com.components.downloads.js");

	Clazz.com.components.downloads.js.Downloads = Clazz.extend(Clazz.WidgetWithTemplate, {
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/downloads/template/downloads.tmp",
		configUrl: "components/downloads/config/config.json",
		name : commonVariables.downloads,
		downloadsListener : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(){
			var self = this;
			if (self.downloadsListener === null) {
				self.downloadsListener = new Clazz.com.components.downloads.js.listener.DownloadsListener();
			}
			
			self.registerEvents();
		},
		
		registerEvents : function () {
			var self=this;
		},

		/***
		 * Called in once the login is success
		 *
		 */
		loadPage :function() {
			Clazz.navigationController.push(this);
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
			var requestBody = {};
			self.downloadsListener.getdownloads(self.downloadsListener.getActionHeader(requestBody, "getDownloads"), function(response) {
				var responseData = response.data;
				var downloads = [];
				for (var key in responseData) {
					var download = {};
					download.category = key;
					download.downloadInfos = responseData[key];
					downloads.push(download);
				}
				var data = {};
				data.downloads = downloads;
				renderFunction(data, whereToRender);
			});
		},

		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self = this;
			commonVariables.navListener.showHideControls(commonVariables.downloads);
		},
		
		registerEvents : function(unitTestListener) {
			var self = this;
			Handlebars.registerHelper('getfilesize', function(fileSize) {
				var returnVal = "";
				if (fileSize > 1048576) {
					returnVal = parseFloat(fileSize/1048576).toFixed(2) + commonVariables.megabyte;
				} else {
					returnVal = parseFloat(fileSize/1024).toFixed(2) + commonVariables.kilobyte;
				}
				return returnVal;
			});
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			self.tableScrollbar();
			this.customScroll($(".cus_themes"));
		}
	});

	return Clazz.com.components.downloads.js.Downloads;
});