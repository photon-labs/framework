define(["framework/widget", "dynamicPage/api/dynamicPageAPI", "common/loading"], function() {

	Clazz.createPackage("com.components.dynamicPage.js.listener");

	Clazz.com.components.dynamicPage.js.listener.DynamicPageListener = Clazz.extend(Clazz.Widget, {
		localStorageAPI : null,
		loadingScreen : null,
		dynamicPageAPI : null,
		appDirName : null,
		goal : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
			self.dynamicPageAPI = new Clazz.com.components.dynamicPage.js.api.DynamicPageAPI();
		},
		
		/***
		 * Get dynamic page content from service
		 * 
		 * @header: constructed header for each call
		 */
		getServiceContent : function(appDirName, goal, callback) {
			try{
				
				var self = this, header = self.getRequestHeader(appDirName, goal, "parameter");
				self.appDirName = appDirName;
				self.goal = goal;
				
				if(self.parameterValidation(appDirName, goal)){
					self.loadingScreen.showLoading();
					
					self.dynamicPageAPI.getContent(header, 
						function(response){
							if(response != undefined && response != null){
								self.constructHtml(response, callback);
								self.loadingScreen.removeLoading();
							} else {
								//responce value failed
								callback("Responce value failed");
								self.loadingScreen.removeLoading();
							}
						}, 
						function(serviceError){
							//service access failed
							callback("service access failed");
							self.loadingScreen.removeLoading();
						}
					);
				}
			}catch(error){
				//Exception
				self.loadingScreen.removeLoading();
			}
		},

		parameterValidation : function(appDirName, goal){
			var self = this, bCheck = true;
			if(appDirName == ""){
				return false;
			} 
			
			if(goal == ""){
				return false;
			}
			
			return bCheck;
		},
		
		constructHtml : function(response, callback){
			
			var htmlTag = "";
			var key = "";
			var self=this;
			var show = "";
			var required = "";
			var editable = "";
			var multiple = "";
			var sort = "";
			var checked = "";
			
			if(response.data.length !== 0) {
			
				$.each(response.data, function(index, value) {
					
						var type = value.type;
						var name = "";

						function getName() {
							$.each(value.name.value, function(index, value){
								name = value.value;
							});
							
							return name;
						}
						
						if(type !== "") {
							
							if(type === "String"){
								
								if(value.show === false){
									show = ' style="display:none;"';
								} else {
									show = "";
								}
								
								if(value.required === true){
									required = "<sup>*</sup>";
								} else {
									required = "";
								}
								
								if(value.editable === "false"){
									editable = " readonly=readonly";
								} else {
									editable = "";
								}
								
								if(value.multiple === "true"){
									multiple = "multiple=true";
								} else {
									multiple = ""
								}
								
								htmlTag += '<tr'+ show +'><td>'+getName()+''+ required +'</td><td><input type="textbox" name="'+ value.key +'" id="'+value.key+'"'+ editable +''+ multiple +'></td></tr>';
							}
							
							if(type === "List"){
								var option = '';
								$.each(value.possibleValues.value, function(index, value){
									option += '<option value='+value.key+'>'+value.value+'</option>';
								});
							
								if(value.show === false){
									show = ' style="display:none;"';
								} else {
									show = "";
								}
								
								if(value.required === "true"){
									required = "<sup>*</sup>";
								} else {
									required = "";
								}
								
								if(value.editable === "false"){
									editable = " readonly=readonly";
								} else {
									editable = "";
								}
								
								if(value.multiple === "true"){
									multiple = "multiple=true";
								} else {
									multiple = ""
								}
								
								htmlTag += '<tr'+ show +'><td>'+getName()+''+ required +'</td><td><select name="'+ value.key +'" id="'+ value.key +'"'+ editable +''+ multiple +'>'+option+'</select></td></tr>';
								
							} 
							
							
							if(type === "Boolean"){
								if(value.show === false){
									show = ' style="display:none;"';
								} else {
									show = "";
								} 
								
								if(value.required === "true"){
									required = "<sup>*</sup>";
								} else {
									required = "";
								}
								
								if(value.editable === "false"){
									editable = "readonly=readonly";
								} else {
									editable = "";
								}
								
								if(value.value === true){
									checked = "checked=checked";
								} else {
									checked = "";
								}
								
								
								htmlTag += '<tr'+ show +'><td>'+getName()+''+ required +'</td><td><input type="checkbox" name="'+ value.key +'" id="'+ value.key +'"'+ checked +'></td></tr>';
								
							} 	
							
							
							if(type === "DynamicParameter"){
								if(value.show === false){
									show = ' style="display:none;"';
								} else {
									show = "";
								} 
								
								if(value.required === "true"){
									required = "<sup>*</sup>";
								} else {
									required = "";
								}
								
								if(value.editable === "false"){
									editable = "readonly=readonly";
								} else {
									editable = "";
								}
								
								if(value.multiple === "true"){
									multiple = "multiple=true";
								} else {
									multiple = ""
								}
								
								var option = '';
								
								self.dynamicPageAPI.getContent(self.getRequestHeader(self.appDirName, self.goal, value.key, "dependency"), function(response) {
								
									$.each(response.data.value, function(index, value){
										console.info("value", value.value);
										option += '<option value='+value.key+'>'+value.value+'</option>';
									}); 
									
									$('select[name='+ value.key+']').html(option);
									
								}); 
								
								htmlTag += '<tr'+ show +'><td>'+getName()+''+ required +'</td><td><select name="'+ value.key +'" id="'+ value.key +'"'+ editable +''+ multiple +'></select></td></tr>';
								
							} 
							
							if(type === "Hidden"){
								if(value.show === false){
									show = ' style="display:none;"';
								} else {
									show = " ";
								}
								
								if(value.required === "true"){
									required = "<sup>*</sup>";
								} else {
									required = "";
								}
								
								if(value.editable === "false"){
									editable = "readonly=readonly";
								} else {
									editable = "";
								}
								
								if(value.multiple === "true"){
									multiple = "multiple=true";
								} else {
									multiple = ""
								}						
								
								htmlTag += '<tr'+ show +'><td>'+getName()+''+ required +'</td><td><input type="hidden" name="'+ value.key +'" id="'+ value.key +'" value='+ value.value +''+ editable +''+ multiple +'></td></tr>';
								
							}
							
							if(type === "packageFileBrowse"){
								if(value.show === false){
									show = ' style="display:none;"';
								} else {
									show = "";
								}
								
								if(value.required === "true"){
									required = "<sup>*</sup>";
								} else {
									required = "";
								}
								
								if(value.editable === "false"){
									editable = "readonly=readonly";
								} else {
									editable = "";
								}
								
								if(value.multiple === "true"){
									multiple = "multiple=true";
								} else {
									multiple = ""
								}
								
								htmlTag += '<table class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0"><thead><tr><th>Target Folder</th><th>File/Folder</th></tr><tbody><tr><td><input type="text"></td><td><input type="text"><input type="button" value="Browse" class="btn btn_style"><a href="#"><img src="../themes/default/images/helios/plus_icon.png" alt=""></a></td></tr></tbody></table>';
								
							}
							
							if(type === "EditableList"){
							}
							
							if(type === "Number"){
								if(value.show === false){
									show = ' style="display:none;"';
								} else {
									show = "";
								}
								
								if(value.required === true){
									required = "<sup>*</sup>";
								} else {
									required = "";
								}
								
								if(value.editable === "false"){
									editable = "readonly=readonly";
								} else {
									editable = "";
								}
								
								htmlTag += '<tr'+show+'><td>'+getName()+''+ required +'</td><td><input type="textbox" name="'+ value.key +'" id="'+ value.key +'"'+ editable +'></td></tr>';
							} 
							
							if(type === "map"){
							}
							
							if(type === "DynamicPageParameter"){
							}
						}
				});
			}
			
			callback(htmlTag);
		},
		
		/***
		 * provides the request header
		 * @return: returns the contructed header
		 */
		getRequestHeader : function(appDirName, goal, key, action) {
			var header = {
				contentType: "application/json",
				dataType: "json",
				webserviceurl: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName="+appDirName+"&goal="+ goal
			}
			
			if(action === "parameter"){
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName="+appDirName+"&goal="+ goal
			}
			
			if(action === "dependency" && key !== ""){
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dependencyContext + "?appDirName="+appDirName+"&goal="+ goal+"&customerId=photon"+"&key="+ key;
			}
			
			return header;
		}
	});

	return Clazz.com.components.dynamicPage.js.listener.DynamicPageListener;
});