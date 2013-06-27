define(["framework/widgetWithTemplate", "dynamicPage/api/dynamicPageAPI", "common/loading"], function() {

	Clazz.createPackage("com.components.dynamicPage.js.listener");

	Clazz.com.components.dynamicPage.js.listener.DynamicPageListener = Clazz.extend(Clazz.WidgetWithTemplate, {
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
		getServiceContent : function(whereToRender, btnObj, openccObj, callback) {
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
								if (response.data == null || response.data.length == 0) {
									callback("No parameters available");
								} else {
									self.constructHtml(response, whereToRender, btnObj, openccObj, goal);
									self.loadingScreen.removeLoading();
								}
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
		
		/***
		 * Constructs dynamic controls 
		 * 
		 */
		constructHtml : function(response, whereToRender, btnObj, openccObj, goal){
			var self = this, show = "",  required = "", editable = "", multiple = "", sort = "", checked = "", additionalParam = "",
				additionalparamSel = "", dependencyVal = "", psblDependency = "", showFlag = "", enableOnchangeFunction = "", columnClass = "";
			
            if (!self.isBlank(goal) && "validate-code" == goal) {
            	columnClass = "singleColumn";
            } else {
            	columnClass = "doubleColumn";
            }
            
			if(response.data.length !== 0) {
				
				/*function showStatus(key){
					$.each(response.data, function(index, value){
						if(value.key === key){
							 showFlag = value.show;
						}
					});
					return showFlag;
				}*/
				
                $(whereToRender).empty();
                $.each(response.data, function(index, parameter) {
                    var type = parameter.type, optionalAttrs={};
                    if(parameter.show === 'false' || parameter.type === "Hidden"){
                        optionalAttrs.show = ' style="display:none;"';
                    } else {
                        optionalAttrs.show = "";
                    }
                    
                    if(parameter.required === 'true'){
                        optionalAttrs.required = "<sup>*</sup>";
                    } else {
                        optionalAttrs.required = "";
                    }

                    if(parameter.editable === 'false'){
                        optionalAttrs.editable = " readonly=readonly";
                    } else {
                        optionalAttrs.editable = "";
                    }
                    
                    if(type === "String" || type === "Number" || type === "Password"){
                        self.constructTxtCtrl(parameter, columnClass, optionalAttrs, whereToRender);
                    } else if (type === "Hidden") {
						self.constructHiddenCtrl(parameter, columnClass, optionalAttrs, whereToRender);
					} else if(type === "Boolean"){
                        self.constructCheckboxCtrl(parameter, columnClass, optionalAttrs, whereToRender);
                    } else if(type === "List"){
                        self.constructListCtrl(parameter, columnClass, parameter.possibleValues, optionalAttrs, whereToRender);
                    } else if(type === "DynamicParameter" && !parameter.sort){
                    	self.constructDynamicCtrl(parameter, columnClass, parameter.possibleValues, optionalAttrs, whereToRender);
                    } else if (type === "DynamicParameter" && parameter.sort) {
                    	// execute sql template
                    } else if (type === "packageFileBrowse") {
                    	// package file browse template
                    } 
                });
                whereToRender.append('<li></li>');
                self.opencc(btnObj, openccObj);
                whereToRender.find(".selectpicker").selectpicker();
                self.controlEvent();
                self.showParameters();
			}
		},
		
		/********************* Controls construction methods starts**********************************/
        constructTxtCtrl : function(parameter, columnClass, optionalAttrs, whereToRender) {
        	var self = this, inputType = self.getInputType(parameter.type), textBoxValue = "";
        	if (!self.isBlank(parameter.value)) {
        		textBoxValue = parameter.value;
        	} 
            whereToRender.append('<li class="ctrl textCtrl '+columnClass+'" ' +optionalAttrs.show+'  id="'+parameter.key+'Li"><label>'+parameter.name.value[0].value+optionalAttrs.required+'</label>' + 
        						 '<input type="'+inputType+'" parameterType="'+ parameter.type+'" showHide="'+parameter.show+'" name="'+ parameter.key +'" value="'+textBoxValue+'" id="'+parameter.key+'" ' +optionalAttrs.editable+' /></li>');
        },
        
		 constructHiddenCtrl : function(parameter, columnClass, optionalAttrs, whereToRender) {
        	var self = this, textBoxValue = "";
        	if (!self.isBlank(parameter.value)) {
        		textBoxValue = parameter.value;
        	} 
			whereToRender.parent().find('.hiddenControls').append('<input type="hidden" name="'+ parameter.key +'" value="'+textBoxValue+'" id="'+parameter.key+'"/>');
        },
		
        constructCheckboxCtrl : function(parameter, columnClass, optionalAttrs, whereToRender) {
            var liLastPrevId = whereToRender.find("li:last").prev("li").attr("class"), additionalparam = "", dependency = "", checked;
            if(parameter.dependency !== undefined && parameter.dependency !== null){
				dependency = parameter.dependency;
				additionalparam = "dependency="+ dependency +"";
			} else {
				dependency = "";
				additionalparam = ""
			}
            
            checked = parameter.value == "true" ? "checked" : "";
            
            if (liLastPrevId != undefined && liLastPrevId.indexOf('checkCtrl') !== -1) {
                whereToRender.append('<li type="checkbox" class="ctrl checkCtrl '+columnClass+'" ' +optionalAttrs.show+' id="'+parameter.key+'Li"><input type="checkbox" showHide="'+parameter.show+'" parameterType="'+ parameter.type+'" name="'+ parameter.key +'" '+checked+' additionalparam="'+additionalparam+'" dependency="'+dependency+'" id="'+parameter.key+'" ' +optionalAttrs.editable+' > '+parameter.name.value[0].value+optionalAttrs.required+'</li>');
            } else {
                whereToRender.append('<li type="checkbox" class="ctrl checkCtrl '+columnClass+'" ' +optionalAttrs.show+' id="'+parameter.key+'Li"><input type="checkbox" showHide="'+parameter.show+'" parameterType="'+ parameter.type+'" name="'+ parameter.key +'" '+checked+' additionalparam="'+additionalparam+'" dependency="'+dependency+'" id="'+parameter.key+'" ' +optionalAttrs.editable+' > '+parameter.name.value[0].value+optionalAttrs.required+'</li>');
            }
        },
        
        constructListCtrl : function(parameter, columnClass, possibleValues, optionalAttrs, whereToRender) {
        	var self = this, parameterDependency = "", dependencyAttr = "", additionalParam = "", enableOnchangeFunction = false;
        	
        	if(!self.isBlank(parameter.dependency)) {
        		parameterDependency = parameter.dependency;
			} else if(!self.isBlank(parameter.possibleValues)) {
				$.each(parameter.possibleValues.value, function(index, value) {
					if(!self.isBlank(value.dependency)) {
						enableOnchangeFunction = true;
						return false;
					} 
				});
			}
        	
        	self.constructSelectCtrl(parameter, columnClass, possibleValues, optionalAttrs, whereToRender, parameterDependency, enableOnchangeFunction);
        },
        
        constructDynamicCtrl : function(parameter, columnClass, possibleValues, optionalAttrs, whereToRender) {
        	var self = this, parameterDependency = "", enableOnchangeFunction = false;
        	if (parameter.possibleValues != null && parameter.possibleValues.value != null) {
            	$.each(parameter.possibleValues.value, function(index, value) {
    				if (!self.isBlank(value.dependency)) {
    					enableOnchangeFunction = true;
    					return false;
    				}
    			});
            }
            
            if (!self.isBlank(parameter.dependency) && !enableOnchangeFunction) {
            	enableOnchangeFunction = true;
            	parameterDependency = parameter.dependency;
            }
            self.constructSelectCtrl(parameter, columnClass, possibleValues, optionalAttrs, whereToRender, parameterDependency, enableOnchangeFunction);
        },
        
        constructSelectCtrl : function (parameter, columnClass, possibleValues, optionalAttrs, whereToRender, parameterDependency, enableOnchangeFunction) {
        	var self=this, multiple = "", selection, li = "", select = "", isMultiple = parameter.multiple == "true" ? true : false;
            if (isMultiple) {
            	multiple = "multiple=multiple";
            	selection = "selection=multiple";
            } else {
            	multiple = "";
            	selection = "selection=single";
            }
            
            li = $('<li type="selectbox" class="ctrl selectCtrl '+columnClass+'" ' +optionalAttrs.show+'  id="'+parameter.key+'Li"><label>'+parameter.name.value[0].value+optionalAttrs.required+'</label></li>');
            select = $('<select data-selected-text-format="count>1" '+selection+' id="'+parameter.key+'" showHide="'+parameter.show+'" dependencyAttr="'+parameterDependency+'" name="'+parameter.key+'" parameterType="'+ parameter.type+'" class="selectpicker" '+multiple+' ' + 
            		 'enableOnchangeFunction="'+ enableOnchangeFunction +'"  data-selected-text-format="count>2" ' +optionalAttrs.editable+' ></select>');
            li.append(select);
            whereToRender.append(li);
            
            if (possibleValues !== null && possibleValues.value !== null) {
            	self.constructOptions($('#'+parameter.key), possibleValues.value, parameter.value, isMultiple, parameterDependency);
            }
        },
        
        //constructs select box option elements
        constructOptions : function(selectCtrl, possibleValues, previousValue, isMultiSelect, parameterDependency) {
        	var self = this, selected;
            $.each(possibleValues, function(index, value){
            	var optionValue = "", additionalParam = "";
            	if (!self.isBlank(value.key)) {
            		optionValue = value.key;
            	} else {
            		optionValue = value.value;
            	}
            	
            	if (isMultiSelect) {
            		selected = self.getMultiSelectedStr(previousValue, value.key, value.value);
            	} else {
            		selected = self.getSelectedString(previousValue, value.key, value.value);
            	}
            	var params = self.getAdditionalParamForOptions(parameterDependency, value);
            	if (!self.isBlank(params)) {
            		additionalParam = "dependency=" + params;
            	}
            	var hideCtrls = self.getHideControls(possibleValues, value);
            	
            	var hide = "";
            	if (!self.isBlank(hideCtrls) && hideCtrls !== params) {
            		hide = hideCtrls;
            	}
            	
                selectCtrl.append('<option value="'+optionValue+'" hide="'+hide+'" additionalParam="' + additionalParam + '" '+selected+'>'+value.value+'</option>');
            });
        },
        
        /********************* Controls construction methods ends**********************************/
        
        
        //returns textbox type
        getInputType : function (type) {
	        if (type === "Password") {
	    		return "password"
	    	} else if (type === "Hidden") {
	    		return "hidden";
	    	} else {
	    		return "text";
	    	}
        }, 
        
        //returns parameter's dependency or possible value's dependency as additionalParam
        getAdditionalParamForOptions : function (parameterDependency, value) {
        	var self = this,additionalParam = "";
        	if (!self.isBlank(parameterDependency)) {
				additionalParam += parameterDependency;
   		 	}
			if (!self.isBlank(value.dependency)) {
				if (!self.isBlank(additionalParam)) {
					additionalParam += ",";
				}
				additionalParam += value.dependency;
   		 	} 
			
			return additionalParam;
        }, 
        
        //returns controls to be hide
        getHideControls : function (values, currentValue) {
        	var self = this, hideControls = "", comma = "";
            $.each(values, function(index, value) {
            	if (!self.isBlank(value.dependency) && !self.isBlank(value.key) &&  !self.isBlank(currentValue.key) 
						 &&  value.key !== currentValue.key && value.dependency !== currentValue.dependency) {
            		
            		if (!self.isBlank(hideControls)) {
            			hideControls += ",";
            		}
            		hideControls += value.dependency;
            		comma = ",";
            	}
            });	
            
            return hideControls;
        },
        
        //to make single select box option as selected
        getSelectedString : function (previousValue, valueKey, value) {
        	var self = this;
        	if (!self.isBlank(previousValue) && ((!self.isBlank(valueKey)  && valueKey === previousValue) || (previousValue === value))) {
        		return "selected";
        	} else {
        		return "";
        	}
        },
        
        //to make multi select box option as selected
        getMultiSelectedStr : function(previousValue, valueKey, value) {
        	var self = this,previousValArray = new Array(), type = $.type(previousValue);
        	if ("string" === type) {
        		previousValArray = previousValue.split(",");
        	} else if("array" === type) {
        		$.merge(previousValArray, previousValue);
        	}
        	
    		if (previousValArray.length != 0 && ((!self.isBlank(valueKey)  && $.inArray(valueKey, previousValArray) != -1)
    				|| (!self.isBlank(value)  && $.inArray(value, previousValArray) != -1))) {
        		return "selected";
        	} else {
        		return "";
        	}
        },
        
		changeChckBoxValue : function(obj) {
			if (obj.is(':checked')) {
				obj.val("true");
			} else {
				obj.val("false");
			} 
		},
		
		//to bind events for dynamic controls
		controlEvent : function(){
			var self = this;
			$("li[type=checkbox]").find('input[type=checkbox]').click(function(){
				self.checkBoxClickEvent($(this));
			});
			
			$("li[type=selectbox]").find('select[selection=single]').change(function() {
				self.selectBoxChangeEvent($(this));
			});
			
			$("li[type=selectbox]").find('.bootstrap-select').click(function() {
				self.registerOnFocusEvent($(this).prev());
			});
			
		},
		
		checkBoxClickEvent : function(checkBoxCtrl) {
			var self = this, dependency, key, showFlag;
			self.changeChckBoxValue(checkBoxCtrl);
			dependency = $(checkBoxCtrl).attr('dependency');
			key = $(checkBoxCtrl).attr('id');
			showFlag = $(checkBoxCtrl).attr('showFlag');
			
			if (!self.isBlank(dependency)) {
				self.dynamicControlEvents($(checkBoxCtrl), key, showFlag);
			} 
		},
		
		selectBoxChangeEvent : function(control) {
			var self = this;
			var dependencyAttr = $(control).attr('dependencyattr');
			var key = $(control).attr('id');
			var enableOnchangeFunction = $(control).attr('enableOnchangeFunction');
			
			if(!self.isBlank(dependencyAttr) || enableOnchangeFunction === "true"){
				self.dynamicControlEvents($(control), key);
			}  
		},
		
		registerOnFocusEvent : function (control) {
			var self = this;
			var controlType = $(control).prop('tagName');
			if (controlType === 'SELECT') {
				self.setPreviousDependent($(control));
			}
		},
		
		dynamicControlEvents : function(obj, currentParamKey, showHideFlag) {
			var self = this;
			var jecClass = "";
			if (obj.options != undefined || obj.options != null) {
				jecClass = obj.options[obj.selectedIndex].getAttribute('class');
			}
			
			if (jecClass != "jecEditableOption") {
				var selectedOption = $(obj).val();
				$(obj).blur();//To remove the focus from the current element
				var dependencyAttr;
				
				var controlType = $(obj).prop('tagName');
				if (controlType === 'INPUT') {
					selectedOption = $(obj).is(':checked');
					dependencyAttr = $(obj).attr('additionalParam');
				} else if (controlType === 'SELECT') {
					var previousDependencyAttr = $(obj).attr('additionalparam');    //get the previvous dependency keys from additionalParam attr
					dependencyAttr =  obj.find(":selected").attr('additionalparam'); //$('option:selected', obj).attr('additionalParam');
					if (!self.isBlank(previousDependencyAttr)) {
						var csvPreviousDependency = previousDependencyAttr.substring(previousDependencyAttr.indexOf('=') + 1);
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
				} 
				
				var csvDependencies;
				self.changeEveDependancyListener(selectedOption, currentParamKey);  // update the watcher while changing the drop down
				if (!self.isBlank(dependencyAttr)) {
					csvDependencies = dependencyAttr.substring(dependencyAttr.indexOf('=') + 1);
					csvDependencies = self.getAllDependencies(csvDependencies);
					var dependencyArr = new Array();
					dependencyArr = csvDependencies.split(',');
					
					for (var i = 0; i < dependencyArr.length; i+=1) {
						self.updateDependancy(dependencyArr[i]);
					}
					self.showControl(dependencyArr);
					
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
		
		//To update watcher map
		changeEveDependancyListener : function(selectedOption, currentParamKey) {
			var self = this;
			self.dynamicPageAPI.getContent(self.getRequestHeader(self.projectRequestBody, currentParamKey, selectedOption, "updateWatcher"), function(response) {
			});
		},
		
		//To get dynamic values while change or click events
		updateDependancy : function(dependency) {
			var self = this;
			self.showDynamicPopupLoading();
			self.dynamicPageAPI.getContent(self.getRequestHeader(self.projectRequestBody, dependency, "", "dependency"), function(response) {
				self.updateDependancySuccEvent(response.data, dependency);
			});
		},
		
		//To load response dynamic values to respective controls
		updateDependancySuccEvent : function(data, dependency) {
			var self = this;
			if (!self.isBlank(dependency)) {
				var isMultiple = $('#' + dependency).attr("multiple");
				var controlType = $('#' + dependency).attr('type');
				var controlTag = $('#' + dependency).prop('tagName');
				self.constructElements(data,  $('#' + dependency), isMultiple, controlType, controlTag);
			} else {
				self.hideDynamicPopupLoading();
			}
		},
		
		//to dynamically update dependancy data into controls 
		constructElements : function(data, pushToElement, isMultiple, controlType, controlTag) {
			var self=this, previousValue = $(pushToElement).val(), parameterType = pushToElement.attr("parameterType");
			
			if ('SELECT' === controlTag && 'List' !== parameterType) {
				pushToElement.empty();
				self.updateSelectCtrl(pushToElement, data, previousValue);
			}
			self.hideDynamicPopupLoading();
		},
		
		updateSelectCtrl : function (pushToElement, data, previousValue) {
			var self = this;
			if (!$.isEmptyObject(data)) {
				self.constructOptions(pushToElement, data, previousValue, true);
			}
			pushToElement.selectpicker('refresh');
		},
		
		//To set option's additionalParam to select box attr
		setPreviousDependent : function(obj) {
			var self = this;
			var additionalParam = $('option:selected', obj).attr('additionalParam');
			if (!self.isBlank(additionalParam)) {
				$(obj).attr('additionalParam', 'previous' +  additionalParam.charAt(0).toUpperCase() + additionalParam.slice(1));
			}
		},
		
		//To show or hide controls while popup loads
		showParameters : function(){
			var self=this;
			
			$(':input, .dynamicControls > li').each(function(index, value) {
				var currentObjType = $(this).prop('tagName');
				var multipleAttr = $(this).attr('multiple');
				var id = $(this).attr('id');
				var block = $("#" + id + "Li").css("display");
				if (currentObjType === "SELECT" && multipleAttr === undefined && this.options[this.selectedIndex] !== undefined && "block" == block) {
					var dependencyAttr =  this.options[this.selectedIndex].getAttribute('additionalparam');
					
					if (!self.isBlank(dependencyAttr)) {
						var csvDependencies = dependencyAttr.substring(dependencyAttr.indexOf('=') + 1);
						csvDependencies = self.getAllDependencies(csvDependencies);
						var dependencyArr = new Array();
						dependencyArr = csvDependencies.split(',');
						self.showControl(dependencyArr);	
						
						//To show checkbox's dependencies
						for (var i = 0; i < dependencyArr.length; i+=1) {
							var controlTag = $("#" + dependencyArr[i]).prop('tagName');
							var controlType = $("#" + dependencyArr[i]).attr('type');
							if (controlTag === 'INPUT' && controlType === 'checkbox') {
								var selectedOption = $("#" + dependencyArr[i]).is(':checked');
								if (selectedOption) {
									var c = $("#" + dependencyArr[i]).attr('additionalparam');
									if (!isBlank(c)) {
										var csvDep = c.substring(c.indexOf('=') + 1);
										var csvDepArr = new Array();
										csvDepArr = csvDep.split(',');
										for (var j = 0; j < csvDepArr.length; j+=1) {
											var showHide = $("#" + csvDepArr[j]).attr('showHide');
											if (showHide == 'false') {
												$("#" + csvDepArr[j] + "Li").show();
											} 
										}
									}
								} 
							}
						}
					}
					
					//If the dependent child is select box, hide controls based on selected options - while popup loading
					var hideOptionDependency = this.options[this.selectedIndex].getAttribute('hide');
					if (!self.isBlank(hideOptionDependency)) {
						var hideOptionDependencyArr = new Array();
						hideOptionDependencyArr = hideOptionDependency.split(',');
						self.hideControl(hideOptionDependencyArr);
					} 
					
				} else if(currentObjType === "SELECT" && multipleAttr === undefined && $(this).attr('dependencyAttr') != undefined  && "block" == block) {
					var dependencyAttr =  $(this).attr('dependencyAttr');
					if (!self.isBlank(dependencyAttr)) {
						var csvDependencies = self.getAllDependencies(dependencyAttr);
						var dependencyArr = new Array();
						dependencyArr = csvDependencies.split(',');
						self.showControl(dependencyArr);					
					}
				}
			});
		},
		
		//To get all dependencies
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
		
		//To show controls
		showControl : function(controls) {
			for (i in controls) {
				$('#' + controls[i] + 'Li').show();
			}
		},
		
		//To hide controls
		hideControl : function(controls) {
			for (i in controls) {
				$('#' + controls[i] + 'Li').hide();
			}
		},
		
		isBlankObject : function(obj) {
			if (obj !== null && obj !== undefined) {
				return false;
			}
			return true;
		}, 
		
		/***
		 * provides the request header
		 * @return: returns the contructed header
		 */
		getRequestHeader : function(projectRequestBody, key, selectedOption, action) {
			var self = this;
			var appDirName = commonVariables.appDirName;
			var goal = commonVariables.goal;
			var phase = commonVariables.phase;
			var userId = self.dynamicPageAPI.localVal.getSession('username');
			var customerId = self.getCustomer();
			var header = {
				contentType: "application/json",
				dataType: "json",
				webserviceurl: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName="+appDirName+"&goal="+ goal
			}
			
			if(action === "parameter"){
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName="+appDirName+"&goal="+ goal+"&phase="+phase+"&customerId="+customerId+"&userId="+userId
			} else if (action == "updateWatcher") {
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + "updateWatcher" + "?appDirName="+appDirName+"&goal="+ goal+"&key="+key+"&value="+selectedOption
			} else if(action === "dependency" && key !== ""){
				header.requestMethod = "POST";
				header.requestPostBody = projectRequestBody;
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dependencyContext + "?appDirName="+appDirName+"&goal="+ goal+"&phase="+phase+"&customerId="+customerId+"&userId="+userId+"&key="+ key
			}
			
			return header;
		}
	});

	return Clazz.com.components.dynamicPage.js.listener.DynamicPageListener;
});