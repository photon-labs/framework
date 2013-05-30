define(["framework/widget", "dynamicPage/api/dynamicPageAPI", "common/loading"], function() {

	Clazz.createPackage("com.components.dynamicPage.js.listener");

	Clazz.com.components.dynamicPage.js.listener.DynamicPageListener = Clazz.extend(Clazz.Widget, {
		localStorageAPI : null,
		loadingScreen : null,
		dynamicPageAPI : null,
		appDirName : null,
		goal : null,
		responseData : null,
		projectRequestBody : {},

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
		getServiceContent : function(callback) {
			try{
				
				var self = this, header = self.getRequestHeader(self.projectRequestBody, "", "", "parameter");
				var appDirName = commonVariables.appDirName;
				var goal = commonVariables.goal;
				
				if(self.parameterValidation(appDirName, goal)){
					self.loadingScreen.showLoading();
					
					self.dynamicPageAPI.getContent(header, 
						function(response){
							self.responseData = response.data;
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
			
			var self = this;
			var htmlTag = "";
			var show = "";
			var required = "";
			var editable = "";
			var multiple = "";
			var sort = "";
			var checked = "";
			var additionalParam = "";
			var additionalparamSel = "";
			var dependencyVal = "";
			var psblDependency = "";
			var showFlag = "";
			var enableOnchangeFunction = "";
			
			if(response.data.length !== 0) {
				
				function showStatus(key){
					$.each(response.data, function(index, value){
						if(value.key === key){
							 showFlag = value.show;
						}
					});
					return showFlag;
				}
				
			
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
							
							if(type === "String" || type === "Number"){
								
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
								
								htmlTag += '<tr'+ show +' id="'+value.key+'Control" name="chkCnt"><td>'+getName()+''+ required +'</td><td><input type="textbox" name="'+ value.key +'" id="'+value.key+'"'+ editable +''+ multiple +'></td></tr>';
							}
							
							if(type === "List"){
							
								var option = '';
								var dependency = '';
								var psblValueArray = [];
								
								if(value.possibleValues !== undefined && value.possibleValues !== null) {
									var key = value.key;
									function getHideControls(psblArray, currentValue){
										var deps = "";
										$.each(psblArray, function(index, value){
											if(value.dependency !== null && value.key !== null && currentValue.key !== null && !(value.key === currentValue.key) && !(value.dependency === currentValue.dependency)){
												deps = value.dependency;
											}
										});
										
										return deps;
									}
									
									psblValueArray = value.possibleValues.value;
									
									$.each(value.possibleValues.value, function(index, value){
										hideobj = getHideControls(psblValueArray, value);
										if(value.dependency !== undefined && value.dependency !== null){
											dependency = value.dependency;
											additionalparam = "dependency="+ dependency +"";
										} else {
											additionalparam = "";
										}
										
										if(hideobj !== "" && hideobj !== null && hideobj !== undefined) {
											option += '<option value='+value.key+' hide="'+ hideobj +'">'+value.value+'</option>';
										} else {
											option += '<option value='+value.key+' additionalparam="'+additionalparam+'">'+value.value+'</option>';
										}
									});
								}
								
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
									
								} else if(value.editable === "edit") {
									
									$("#" + '<%= value.key %>').customComboBox({
										tipText : "Type or select from the list",
										allowed : /[A-Za-z0-9\$\._\-\s]/,
										notallowed : /[\<\>\$]/,
										index : 'first',
									});
									
								} else {
									editable = "";
								}
								
								if(value.multiple === "true"){
									multiple = "ismultiple=true";
								} else {
									multiple = "ismultiple=false";
								}
								
								if(value.dependency !== undefined && value.dependency !== null) {
									dependencyVal = value.dependency;
								} else if(value.possibleValues !== undefined && value.possibleValues !== null) {
									var addOnChangeEvent = false;
									$.each(value.possibleValues.value, function(index, value){
										if(value.dependency !== undefined && value.dependency !== null){
											psblDependency = value.dependency;
											additionalparamSel = "previousDependency="+ value.dependency +"";
											return false;
										} 
									});
									
								}
								
								htmlTag += '<tr'+ show +' id="'+value.key+'Control" name="chkCnt"><td>'+getName()+''+ required +'</td><td><select name="'+ value.key +'" id="'+ value.key +'"'+ editable +''+ multiple +' additionalparam = "'+ additionalparamSel +'" dependencyAttr="'+dependencyVal+'" psblDependency="'+ psblDependency +'">'+option+'</select></td></tr>';
								
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
								
								if(value.dependency !== undefined && value.dependency !== null){
									dependency = value.dependency;
									additionalparam = "dependency="+ dependency +"";
								} else {
									dependency = "";
									additionalparam = ""
								}
								
								htmlTag += '<tr'+ show +' id="'+value.key+'Control" name="chkCnt"><td>'+getName()+''+ required +'</td><td><input type="checkbox" name="'+ value.key +'" id="'+ value.key +'"'+ checked +' additionalparam="'+additionalparam+'" dependency="'+dependency+'" showFlag="'+showStatus(dependency)+'"></td></tr>';
								
							} 	
							
							
							if(type === "DynamicParameter"){
							
								if(!value.sort) {
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
									self.dynamicPageAPI.getContent(self.getRequestHeader(self.projectRequestBody, value.key, "", "dependency"), function(response) {
									
										$.each(response.data.value, function(index, value){
											console.info("value", value);
											if(value !== undefined && value !== null) {
												option += '<option value='+value.key+'>'+value.value+'</option>';
											}
										}); 
										
										$('select[name='+ value.key+']').html(option);
										
									});
									
									/* if(value.possibleValues !== undefined && value.possibleValues !== null){
										$.each(value.possibleValues.value, function(index, value){
											if(value.dependency !== undefined && value.dependency !== null){
												psblDependency = value.dependency;
												return false;
											} 
										});
									} */

									htmlTag += '<tr'+ show +' id="'+value.key+'Control" name="chkCnt"><td>'+getName()+''+ required +'</td><td><select name="'+ value.key +'" id="'+ value.key +'"'+ editable +''+ multiple +'></select></td></tr>';	
							   } else {
							   
							   }
								
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
								
								htmlTag += '<tr'+ show +' id="'+value.key+'Control" name="chkCnt"><td>'+getName()+''+ required +'</td><td><input type="hidden" name="'+ value.key +'" id="'+ value.key +'" value='+ value.value +''+ editable +''+ multiple +'></td></tr>';
								
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
								
								htmlTag += '<tr id="'+value.key+'Control" name="chkCnt"><td colspan="2"><table class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0"><thead><tr><th>Target Folder</th><th>File/Folder</th></tr><tbody><tr><td><input type="text"></td><td><input type="text"><input type="button" value="Browse" class="btn btn_style"><a href="#"><img src="../themes/default/images/helios/plus_icon.png" alt=""></a></td></tr></tbody></table></td></tr>';
								
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
		
		changeChckBoxValue : function(obj) {
			if ($(obj).is(':checked')) {
				$(obj).val("true");
			} else {
				$(obj).val("false");
			} 
		},
		
		controlEvent : function(){
			var self = this;
			$("tr[name='chkCnt']").click(function(){
				var controls = $(this).children().eq(1).children();
				var controlType = $(controls).prop('tagName');
				var type = $(controls).attr('type');
				var dependency = $(controls).attr('dependency');
				var key = $(controls).attr('id');
				var showFlag = $(controls).attr('showFlag');
				
				if (controlType === 'INPUT' && type === "checkbox") {
					if(dependency !== undefined && dependency !== null && dependency !== ''){
						self.dependancyChckBoxEvent($(controls), key, showFlag);
					} else {
						self.changeChckBoxValue($(controls));
					}
				}
			});
			
			$("tr[name='chkCnt']").change(function(){
				var controls = $(this).children().eq(1).children();
				var controlType = $(controls).prop('tagName');
				var type = $(controls).attr('type');
				var dependency = $(controls).attr('dependency');
				var key = $(controls).attr('id');
				var psblValues = $(controls).attr('psblDependency');
				
				if (controlType === 'INPUT' && type === "checkbox") {
					if(dependency !== undefined && dependency !== null && dependency !== ''){
						self.changeChckBoxValue($(controls));
					}
				} else if(controlType === 'SELECT') {
					if(dependency !== undefined && dependency !== null && dependency !== ''){
						self.selectBoxOnChangeEvent($(controls), key, dependency);
					} else if(psblValues !== undefined && psblValues !== null && psblValues !== ''){
						self.selectBoxOnChangeEvent($(controls), key);
					}
				}
			});
			
			$("tr[name='chkCnt']").focus(function(){
				var controls = $(this).children().eq(1).children();
				var controlType = $(controls).prop('tagName');
				
				if (controlType === 'SELECT') {
					self.setPreviousDependent($(controls));
				}
			});
		},
		
		dependancyChckBoxEvent : function(obj, currentParamKey, showHideFlag) {
			var self=this;
			self.selectBoxOnChangeEvent(obj, currentParamKey, showHideFlag);
		},
		
		selectBoxOnChangeEvent : function(obj, currentParamKey, showHideFlag) {
			var self = this;
			var jecClass = "";
			if (obj.options != undefined || obj.options != null) {
				jecClass = obj.options[obj.selectedIndex].getAttribute('class');
			}
			
			if (jecClass != "jecEditableOption") {
				var selectedOption = $(obj).val();
				$(obj).blur();						//To remove the focus from the current element
				var dependencyAttr;
				
				var controlType = $(obj).prop('tagName');
				if (controlType === 'INPUT') {
					selectedOption = $(obj).is(':checked');
					dependencyAttr = $(obj).attr('additionalParam');
				} else if (controlType === 'SELECT') {
					var previousDependencyAttr = $(obj).attr('additionalparam');    //get the previvous dependency keys from additionalParam attr
					var csvPreviousDependency = previousDependencyAttr.substring(previousDependencyAttr.indexOf('=') + 1);
					dependencyAttr =  obj.find(":selected").attr('additionalparam'); 			//$('option:selected', obj).attr('additionalParam'); 
					
					if (csvPreviousDependency !== undefined && !self.isBlank(csvPreviousDependency) && 
							csvPreviousDependency !==  dependencyAttr) {          //hide event of all the dependencies of the previuos dependencies
						var csvDependencies = self.getAllDependencies(csvPreviousDependency);
						var previousDependencyArr = new Array();
						previousDependencyArr = csvDependencies.split(',');
						if (jecClass != 'jecEditableOption') {
							self.hideControl(previousDependencyArr);					
						}
					}
				} 
				
				var csvDependencies;
				self.changeEveDependancyListener(selectedOption, currentParamKey);  // update the watcher while changing the drop down
				if (dependencyAttr !== undefined && dependencyAttr != null) {
					csvDependencies = dependencyAttr.substring(dependencyAttr.indexOf('=') + 1);
					csvDependencies = self.getAllDependencies(csvDependencies);
					var dependencyArr = new Array();
					dependencyArr = csvDependencies.split(',');
					for (var i = 0; i < dependencyArr.length; i+=1) {
						$('#' + $.trim(dependencyArr[i]) + 'Control').show();
						$('.' + $.trim(dependencyArr[i]) + 'PerformanceDivClass').show();     //for performance context urls
							
						//self.updateDependancy(dependencyArr[i]);
					}
					
					//If the dependent child is select box, hide controls based on selected options - for on change event
					for (var i = 0; i < dependencyArr.length; i+=1) {
						var curId = $.trim(dependencyArr[i]);
						var dependentCtrl = $("#"+curId).prop('tagName');
							if (dependentCtrl === 'SELECT') {
								var hideOptionDependency = $('#'+curId).find(":selected").attr('hide');
								if (hideOptionDependency !== undefined && !self.isBlank(hideOptionDependency)) {
									var hideOptionDependencyArr = new Array();
									hideOptionDependencyArr = hideOptionDependency.split(',');
									self.hideControl(hideOptionDependencyArr);
								}
							}
						}	
					}
				
				if ($(obj).attr("type") === 'checkbox' && showHideFlag === "false") {
					if (!selectedOption) {
						var previousDependencyArr = new Array();
						previousDependencyArr = csvDependencies.split(',');
						self.hideControl(previousDependencyArr);
					}	
				}
			}
		},
		
		changeEveDependancyListener : function(selectedOption, currentParamKey) {
			var self = this;
			self.dynamicPageAPI.getContent(self.getRequestHeader(self.projectRequestBody, currentParamKey, selectedOption, "dependency"), function(response) {
			});
		},
		
		setPreviousDependent : function(self) {
			var additionalParam = $('option:selected', self).attr('additionalParam');
			if (additionalParam != undefined) {
				$(self).attr('additionalParam', 'previous' +  additionalParam.charAt(0).toUpperCase() + additionalParam.slice(1));
			}
		},
		
		showParameters : function(){
		
			var self=this;
			
			$(':input, #dynamicContent > tr').each(function(index, value) {
			
				var currentObjType = $(this).prop('tagName');
				var multipleAttr = $(this).attr('multiple');
				
				if (currentObjType === "SELECT" && multipleAttr === undefined && this.options[this.selectedIndex] !== undefined ) {
					
					var dependencyAttr =  this.options[this.selectedIndex].getAttribute('additionalparam');
					
					if (dependencyAttr !== null && dependencyAttr !== "") {
						var csvDependencies = dependencyAttr.substring(dependencyAttr.indexOf('=') + 1);
						csvDependencies = self.getAllDependencies(csvDependencies);
						var dependencyArr = new Array();
						dependencyArr = csvDependencies.split(',');
						self.showControl(dependencyArr);					
					}
					
					//If the dependent child is select box, hide controls based on selected options - while popup loading
					var hideOptionDependency = this.options[this.selectedIndex].getAttribute('hide');
					if (hideOptionDependency !== undefined && hideOptionDependency !== "" && !self.isBlank(hideOptionDependency)) {
						var hideOptionDependencyArr = new Array();
						hideOptionDependencyArr = hideOptionDependency.split(',');
						self.hideControl(hideOptionDependencyArr);
					} 
					
				} else if(currentObjType === "SELECT" && multipleAttr === undefined && $(this).attr('dependencyAttr') != undefined) {
					var dependencyAttr =  $(this).attr('dependencyAttr');
					if (dependencyAttr != null && dependencyAttr != "" && !self.isBlank(dependencyAttr)) {
						var csvDependencies = self.getAllDependencies(dependencyAttr);
						var dependencyArr = new Array();
						dependencyArr = csvDependencies.split(',');
						self.showControl(dependencyArr);					
					}
				}
			});
		},
		
		getAllDependencies : function(keys) {
		
			var self=this;
			var dependencies = keys;
			var comma = ',';
			var keyArr = self.CSVToArray(keys);
			
			for (var i=0; i < keyArr.length; i+=1) {
				var controlType = $('#' + $.trim(keyArr[i])).prop('tagName');
				var additionalParam;
				if (controlType === 'SELECT') {
					additionalParam = $('#' + $.trim(keyArr[i]) + ' option:selected').attr('additionalParam');
					if (additionalParam === undefined) {
						additionalParam = $('#' + $.trim(keyArr[i])).attr('dependencyAttr');
					}
				} else if (controlType === 'INPUT') {
					additionalParam = $('#' + $.trim(keyArr[i])).attr('additionalParam');
				}
				if (additionalParam != undefined && !self.isBlank(additionalParam)) {
					dependencies = dependencies.concat(comma);
					dependencies = dependencies.concat(additionalParam.substring(additionalParam.indexOf('=') + 1));			
				}
			}
			
			return dependencies; 
		},
		
		CSVToArray : function(strData, strDelimiter) {
			// Check to see if the delimiter is defined. If not,
			// then default to comma.
			strDelimiter = (strDelimiter || ",");

			// Create a regular expression to parse the CSV values.
			var objPattern = new RegExp(
				(
					// Delimiters.
					"(\\" + strDelimiter + "|\\r?\\n|\\r|^)" +

					// Quoted fields.
					"(?:\"([^\"]*(?:\"\"[^\"]*)*)\"|" +

					// Standard fields.
					"([^\"\\" + strDelimiter + "\\r\\n]*))"
				),
				"gi"
				);


			// Create an array to hold our data. Give the array
			// a default empty first row.
			var arrData = [[]];

			// Create an array to hold our individual pattern
			// matching groups.
			var arrMatches = null;


			// Keep looping over the regular expression matches
			// until we can no longer find a match.
			while (arrMatches = objPattern.exec( strData )){

				// Get the delimiter that was found.
				var strMatchedDelimiter = arrMatches[ 1 ];

				// Check to see if the given delimiter has a length
				// (is not the start of string) and if it matches
				// field delimiter. If id does not, then we know
				// that this delimiter is a row delimiter.
				if (
					strMatchedDelimiter.length &&
					(strMatchedDelimiter != strDelimiter)
					){

					// Since we have reached a new row of data,
					// add an empty row to our data array.
					arrData.push( [] );

				}


				// Now that we have our delimiter out of the way,
				// let's check to see which kind of value we
				// captured (quoted or unquoted).
				if (arrMatches[ 2 ]){

					// We found a quoted value. When we capture
					// this value, unescape any double quotes.
					var strMatchedValue = arrMatches[ 2 ].replace(
						new RegExp( "\"\"", "g" ),
						"\""
						);

				} else {

					// We found a non-quoted value.
					var strMatchedValue = arrMatches[ 3 ];

				}


				// Now that we have our value string, let's add
				// it to the data array.
				arrData[ arrData.length - 1 ].push( strMatchedValue );
			}

			// Return the parsed data.
			return( arrData );
		},
		
		isBlank : function(str) {
			return (!str || /^\s*$/.test(str));
		},
		
		showControl : function(controls) {
			for (i in controls) {
				$('#' + controls[i] + 'Control').show();
				//$('.' + controls[i] + 'PerformanceDivClass').show();//for performance context urls
			}
		},
		
		hideControl : function(controls) {
			for (i in controls) {
				$('#' + controls[i] + 'Control').hide();
				//$('.' + controls[i] + 'PerformanceDivClass').hide();
			}
		},
		
		/***
		 * provides the request header
		 * @return: returns the contructed header
		 */
		getRequestHeader : function(projectRequestBody, key, selectedOption, action) {
			var appDirName = commonVariables.appDirName;
			var goal = commonVariables.goal;
			
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
				header.requestMethod = "POST";
				header.requestPostBody = projectRequestBody;
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dependencyContext + "?appDirName="+appDirName+"&goal="+ goal+"&customerId=photon"+"&key="+ key+"&value="+selectedOption;
			}
			
			return header;
		}
	});

	return Clazz.com.components.dynamicPage.js.listener.DynamicPageListener;
});