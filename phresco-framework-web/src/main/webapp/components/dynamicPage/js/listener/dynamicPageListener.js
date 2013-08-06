define(["framework/widgetWithTemplate", "common/loading", "lib/customcombobox-1.0", "lib/fileuploader-2.0"], function() {

    Clazz.createPackage("com.components.dynamicPage.js.listener");

    Clazz.com.components.dynamicPage.js.listener.DynamicPageListener = Clazz.extend(Clazz.WidgetWithTemplate, {
        localStorageAPI : null,
        appDirName : null,
        goal : null,
        responseData : null,
        projectRequestBody : {},
        i : 1,
        contextUrlsRowId : "",
        dynamicParameters : [],
        formObj : "",

        /***
         * Called in initialization time of this class 
         *
         * @config: configurations for this listener
         */
        initialize : function(config) {
            var self = this;
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
                    commonVariables.api.ajaxRequest(header, 
                        function(response){
                            self.responseData = response.data;
                            if(response !== undefined && response !== null){
                                if (response.data === null || response.data.length === 0) {
                                    callback("No parameters available");
                                } else {
                                    self.constructHtml(response, whereToRender, btnObj, openccObj, goal, callback);
                                }
                            } else {
                                //responce value failed
                                callback("Responce value failed");
                            }
                        }, 
                        function(serviceError){
                            //service access failed
                            callback("service access failed");
                        }
                    );
                }
            }catch(error){
                //Exception
            }
        },

        parameterValidation : function(appDirName, goal){
            var self = this, bCheck = true;
            if(appDirName === ""){
                return false;
            } 
            
            if(goal === ""){
                return false;
            }
            
            return bCheck;
        },
        
        /***
         * Constructs dynamic controls 
         * 
         */
        constructHtml : function(response, whereToRender, btnObj, openccObj, goal, callback){
            var self = this, show = "",  required = "", editable = "", multiple = "", sort = "", checked = "", additionalParam = "",
                additionalparamSel = "", dependencyVal = "", psblDependency = "", showFlag = "", enableOnchangeFunction = "", columnClass = "";
            self.dynamicParameters =  $.merge([], response.data);
            if (!self.isBlank(goal) && "validate-code" === goal) {
                columnClass = "singleColumn";
            } else {
                columnClass = "doubleColumn";
            }
            
            if(response.data.length !== 0) {
                $(whereToRender).empty();
                $(whereToRender).parent().find('.templates').empty();
                self.formObj = $(whereToRender).closest('form');
                $.each(response.data, function(index, parameter) {
                    var type = parameter.type.toLowerCase();
                    if(type === "string" || type === "number" || type === "password"){
                        self.constructTxtCtrl(parameter, columnClass, whereToRender);
                    } else if (type === "hidden") {
                        self.constructHiddenCtrl(parameter, columnClass, whereToRender);
                    } else if(type === "boolean"){
                        self.constructCheckboxCtrl(parameter, columnClass, whereToRender);
                    } else if(type === "list"){
                        self.constructListCtrl(parameter, columnClass, parameter.possibleValues, whereToRender);
                    } else if(type === "dynamicparameter" && !parameter.sort){
                        self.constructDynamicCtrl(parameter, columnClass, parameter.possibleValues, whereToRender);
                    } else if (type === "dynamicparameter" && parameter.sort) {
                        // execute sql template
						self.consDragnDropcnt(parameter, columnClass, whereToRender);
                    } else if (type === "packagefilebrowse") {
                        // package file browse template
                    } else if (type === "map") {
                    	self.constructMapControls(parameter, whereToRender);
                    } else if (type === "dynamicpageparameter") {
                        self.getDynamicTemplate(parameter, whereToRender);
                    } else if (type === "filetype") {
                        self.constructFileBrowseCtrl(parameter, whereToRender, goal);
                    }
                });
                whereToRender.append('<li></li>');
                if (!self.isBlank(btnObj) && openccObj !== 'jobConfigure') {
                    self.opencc(btnObj, openccObj);
                } else if(!self.isBlank(btnObj) && openccObj === 'jobConfigure') {
					self.popForCi(btnObj, openccObj);
				}
                whereToRender.find(".selectpicker").selectpicker();
                self.controlEvent();
                self.showParameters();
                self.applyEditableComboBox(whereToRender.find('.editableComboBox'));
            }
			
			if(callback !== undefined && callback !== null){
				callback(true);
			}
        },
        
        /********************* Controls construction methods starts**********************************/
		
		consDragnDropcnt : function(parameter, columnClass, whereToRender){
			var self = this;
			if(parameter != null && parameter != ""){
				var sortable1Val = "", sortable2Val = "", sort2 ={};
				
				if(parameter.value != null && parameter.value != ""){
					$.each(JSON.parse(parameter.value), function(key, currentDbList){
						sort2[key] = [];
						$.each(currentDbList, function(index, current){
							sortable2Val += '<li class="ui-state-default '+ ($("#dataBase").val() == key ? "" : "ui-state-disabled")+'" path="'+ current +'" dbName="' + key + '">'+ current.split('/').pop() +'</li>';
							sort2[key].push(current);
						});
					});
				} 

				if(parameter.possibleValues != null && parameter.possibleValues != "" &&
					parameter.possibleValues.value != null && parameter.possibleValues.value != ""){
					$.each(parameter.possibleValues.value, function(index, current){
					
						if(!$.isEmptyObject(sort2)){
							var exist = false;
							$.each(sort2, function(key, path){
								if(current.key == key && $.inArray(current.value, path) >= 0){
									exist = true;
								}
							});

							if(!exist){
								sortable1Val += '<li class="ui-state-default" path="'+ current.value +'" dbName="' + current.key + '">'+ current.value.split('/').pop() +'</li>';
							}
						}else{
							sortable1Val += '<li class="ui-state-default" path="'+ current.value +'" dbName="' + current.key + '">'+ current.value.split('/').pop() +'</li>';
						}
					})
				}
				whereToRender.append('<table name="'+ parameter.key +'_table" class="table table-striped table_border table-bordered fetchSql_table" cellpadding="0" cellspacing="0" border="0"><thead><tr><th colspan="2">DB Script Execution</th></tr></thead><tbody><tr><td><ul name="sortable1" class="sortable1 connectedSortable">' + sortable1Val + '</ul></td><td><ul name="sortable2" class="sortable2 connectedSortable">' + sortable2Val + '</ul></td></tr></tbody></table>');
			}
		},
		
		updateLeftSideContent : function(paramData){
			var sortable1Val = "";

			if(!$.isEmptyObject(paramData)){
				$.each(paramData, function(index, current){
					if($('ul[name=sortable2] li').length > 0){
						var exist = false;
						$.each($('ul[name=sortable2] li'), function(num, liVal){
							if($(liVal).attr('dbName') == $("#dataBase").val()){
								$(liVal).removeClass('ui-state-disabled');
							}else{
								$(liVal).addClass('ui-state-disabled');
							}
							if($(liVal).attr('dbName') == current.key && $(liVal).attr('path') == current.value){exist = true;}
						});
						
						if(!exist){
							sortable1Val += '<li class="ui-state-default" path="'+ current.value +'" dbName="' + current.key + '">'+ current.value.split('/').pop() +'</li>';
						}
					}else{
						sortable1Val += '<li class="ui-state-default" path="'+ current.value +'" dbName="' + current.key + '">'+ current.value.split('/').pop() +'</li>';
					}
				});
			}

			$('ul[name=sortable1]').html(sortable1Val);
		}, 
		
        constructFileBrowseCtrl : function (parameter, whereToRender, goal) {
            var self = this, allowedExtensions = [];
            whereToRender.append('<li id="'+parameter.key+'Li" class="ctrl"><label>&nbsp;</label><div id="'+parameter.key+'" class="'+parameter.key+'-file-uploader"><noscript><p>Please enable JavaScript to use file uploader.</p></noscript></div></li>');
            allowedExtensions = parameter.fileType.split(',');
            self.createFileUploader(parameter, goal, allowedExtensions); 
        },

        createFileUploader : function (parameter, goal, allowedExtensions) {
            var self = this, appDirName, dependency = "";
            appDirName = commonVariables.api.localVal.getSession("appDirName");
            if (!self.isBlank(parameter.dependency)) {
                dependency = parameter.dependency;
            }
            var uploader = new qq.FileUploader({
                element: document.getElementById(parameter.key),
                action: commonVariables.webserviceurl + commonVariables.paramaterContext + "/dynamicUpload",
                actionType : "dynamicUpload",
                appDirName : appDirName,
                multiple: false,
                key : parameter.key,
                allowedExtensions : allowedExtensions,
                buttonLabel: parameter.name.value[0].value,
                dependency: dependency,
                goal: goal, 
                dynamicPageObject: self,
                debug: true
            });
        },

        constructTxtCtrl : function(parameter, columnClass, whereToRender) {
            var self = this, inputType = self.getInputType(parameter.type), textBoxValue = "", optionalAttrs = {};
            if (!self.isBlank(parameter.value)) {
                textBoxValue = parameter.value;
            } 
            optionalAttrs = self.getOptionalAttr(parameter, optionalAttrs);
            whereToRender.append('<li class="ctrl textCtrl '+columnClass+'" ' +optionalAttrs.show+'  id="'+parameter.key+'Li"><label>'+parameter.name.value[0].value+optionalAttrs.required+'</label>' + 
                                 '<input type="'+inputType+'" parameterType="'+ parameter.type+'" showHide="'+parameter.show+'" name="'+ parameter.key +'" value="'+textBoxValue+'" id="'+parameter.key+'" ' +optionalAttrs.editable+' /></li>');
        },
        
        constructHiddenCtrl : function(parameter, columnClass, whereToRender) {
            var self = this, textBoxValue = "";
            if (!self.isBlank(parameter.value)) {
                textBoxValue = parameter.value;
            } 
            whereToRender.parent().find('.hiddenControls').append('<input type="hidden" name="'+ parameter.key +'" value="'+textBoxValue+'" id="'+parameter.key+'"/>');
        },
        
        constructCheckboxCtrl : function(parameter, columnClass, whereToRender) {
            var self = this, optionalAttrs = {}, liLastPrevId = whereToRender.find("li:last").prev("li").attr("class"), additionalparam = "", dependency = "", checked;
            if(!self.isBlank(parameter.dependency)){
                dependency = parameter.dependency;
                additionalparam = "dependency="+ dependency +"";
            } else {
                dependency = "";
                additionalparam = "";
            }
            optionalAttrs = self.getOptionalAttr(parameter, optionalAttrs);
            checked = parameter.value === "true" ? "checked" : "";
            
            if (liLastPrevId !== undefined && liLastPrevId.indexOf('checkCtrl') !== -1) {
                whereToRender.append('<li type="checkbox" class="ctrl checkCtrl '+columnClass+'" ' +optionalAttrs.show+' id="'+parameter.key+'Li"><input type="checkbox" showHide="'+parameter.show+'" parameterType="'+ parameter.type+'" name="'+ parameter.key +'" '+checked+' additionalparam="'+additionalparam+'" dependency="'+dependency+'" id="'+parameter.key+'" ' +optionalAttrs.editable+' > '+parameter.name.value[0].value+optionalAttrs.required+'</li>');
            } else {
                whereToRender.append('<li type="checkbox" class="ctrl checkCtrl '+columnClass+'" ' +optionalAttrs.show+' id="'+parameter.key+'Li"><input type="checkbox" showHide="'+parameter.show+'" parameterType="'+ parameter.type+'" name="'+ parameter.key +'" '+checked+' additionalparam="'+additionalparam+'" dependency="'+dependency+'" id="'+parameter.key+'" ' +optionalAttrs.editable+' > '+parameter.name.value[0].value+optionalAttrs.required+'</li>');
            }
        },
        
        constructListCtrl : function(parameter, columnClass, possibleValues, whereToRender) {
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
            
            self.constructSelectCtrl(parameter, columnClass, possibleValues, whereToRender, parameterDependency, enableOnchangeFunction);
        },
        
        constructDynamicCtrl : function(parameter, columnClass, possibleValues, whereToRender) {
            var self = this, parameterDependency = "", enableOnchangeFunction = false;
            if (parameter.possibleValues !== null && parameter.possibleValues.value !== null) {
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
            self.constructSelectCtrl(parameter, columnClass, possibleValues, whereToRender, parameterDependency, enableOnchangeFunction);
        },
        
        constructSelectCtrl : function (parameter, columnClass, possibleValues, whereToRender, parameterDependency, enableOnchangeFunction) {
            var self=this, selectBoxClass = "", selectWidth = "", optionalAttrs = {}, multiple = "", selection, li = "", select = "", isMultiple = parameter.multiple === "true" ? true : false;
            if (isMultiple) {
                multiple = "multiple=multiple";
                selection = "selection=multiple";
            } else {
                multiple = "";
                selection = "selection=single";
            }
            optionalAttrs = self.getOptionalAttr(parameter, optionalAttrs);

            if ("edit" === parameter.editable) {
                selectBoxClass = "editableComboBox", selectWidth = "style=width:100px;";
            } else {
                selectBoxClass = "selectpicker", selectWidth = "";
            }

            li = $('<li type="selectbox" class="ctrl selectCtrl '+columnClass+'" ' +optionalAttrs.show+'  id="'+parameter.key+'Li"><label>'+parameter.name.value[0].value+optionalAttrs.required+'</label></li>');
            select = $('<select data-selected-text-format="count>1" '+selection+' id="'+parameter.key+'" '+selectWidth+' showHide="'+parameter.show+'" dependencyAttr="'+parameterDependency+'" name="'+parameter.key+'" parameterEditable="'+parameter.editable+'" parameterType="'+ parameter.type+'" class="'+selectBoxClass+'" '+multiple+' ' + 
                     'enableOnchangeFunction="'+ enableOnchangeFunction +'"  data-selected-text-format="count>2" ' +optionalAttrs.editable+' ></select>');
            li.append(select);
            whereToRender.append(li);
            
            if (possibleValues !== null && possibleValues.value !== null) {
                self.constructOptions(whereToRender.find('#'+parameter.key), possibleValues.value, parameter.value, isMultiple, parameterDependency);
            }
        },
        
        //constructs select box option elements
        constructOptions : function(selectCtrl, possibleValues, previousValue, isMultiSelect, parameterDependency) {
            var self = this, selected;
            $.each(possibleValues, function(index, value){
                var optionValue = "", additionalParam = "";
                optionValue = !self.isBlank(value.key) ? value.key : value.value;
                selected = isMultiSelect ? self.getMultiSelectedStr(previousValue, value.key, value.value) : self.getSelectedString(previousValue, value.key, value.value);
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
        
        //To construct map of controls. Key and value can be in the combination of List/String or vice versa
        constructMapControls : function(parameter, whereToRender) {
            var self = this;
            var childs = parameter.childs.child;
            var mapCtrl = '<table class="table table-striped table_border table-bordered browser_table" cellpadding="0" cellspacing="0" border="0">'+
                        '<thead><tr><th>'+childs[0].name.value.value+'</th><th>'+childs[1].name.value.value+'</th></tr></thead><tbody><tr>';
            if ("List" === childs[0].type) {
                mapCtrl =  mapCtrl.concat('<td><select name="'+childs[0].key+'" class="selectpicker">');
                var possibleValues = childs[0].possibleValues.value;
                $.each(possibleValues, function(index, value) {
                    mapCtrl =  mapCtrl.concat('<option value="'+value.key+'">'+value.value+'</option>');
                });
                mapCtrl =  mapCtrl.concat('</select></td>');
            } else if ("String" === childs[0].type) {
                mapCtrl =  mapCtrl.concat('<td><input type="text" name="'+childs[0].key+'"></td>');
            }
            
            if ("List" === childs[1].type) {
                mapCtrl =  mapCtrl.concat('<td><select name="'+childs[1].key+'" class="selectpicker">');
                var possibleValues = childs[1].possibleValues.value;
                $.each(possibleValues, function(index, value){
                    mapCtrl =  mapCtrl.concat('<option value="'+value.key+'">'+value.value+'</option>');
                });
                mapCtrl =  mapCtrl.concat('</select></td>');
            } else if ("String" === childs[1].type) {
                mapCtrl =  mapCtrl.concat('<td><input type="text" name="'+childs[1].key+'">');
                mapCtrl =  mapCtrl.concat('<a href="#" class="addBrowserInfo"><img src="themes/default/images/helios/plus_icon.png" alt=""></a>');
                mapCtrl =  mapCtrl.concat('<a href="#" class="removeBrowserInfo hideContent"><img src="themes/default/images/helios/minus_icon.png" alt=""></a>');
                mapCtrl =  mapCtrl.concat('</td>');
            }
            mapCtrl =  mapCtrl.concat('</tr></tbody></table>');
            whereToRender.append(mapCtrl);
            self.bindMapCtrlClickEvents();
        },

		getDynamicTemplate : function (parameter, whereToRender) {
            var templateDiv =  $('<div class="'+parameter.key+'_Template"></div>'), self = this;
            templateDiv.empty();
            whereToRender.parent().find('.templates').append(templateDiv);
            self.queryDynamicTemplate(self.getRequestHeader("", parameter.key, "", "template"), parameter, templateDiv, function(response) {
                self.hideDynamicPopupLoading();
                templateDiv.append(response.data);
                 self.showParameters();
                 self.bindTemplateEvents();
            });
        },
        
        /********************* Controls construction methods ends**********************************/
        getOptionalAttr : function(parameter, optionalAttrs) {
            if(!parameter.show || parameter.type.toLowerCase() === "hidden"){
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

            return optionalAttrs;
        },

        bindMapCtrlClickEvents : function() {
        	var self = this;
        	$('.addBrowserInfo').unbind('click');
    		$('.addBrowserInfo').click(function() {
    			var ctrl = '<tr><td><select class="selectpicker">' + $(this).parent().prev().children().filter(":first").html() + '</select></td>';
    			var ctrl = ctrl.concat('<td>' + $(this).parent().html() + '</td></tr>');
    			$(this).parent().parent().parent().append(ctrl);
    			$(ctrl).find('input').empty();
    			$(ctrl).find('select option:first').attr('selected', 'selected');
    			$(this).parent().parent().parent().find('.removeBrowserInfo').show();
    			$(this).parent().parent().parent().find(".selectpicker").selectpicker();
    			self.bindMapCtrlClickEvents();
    		});
    		
    		$('.removeBrowserInfo').unbind('click');
    		$('.removeBrowserInfo').click(function() {
    			$(this).parent().parent().remove();
				if ($('.browser_table tbody').find('tr').length === 1) {
					$('.browser_table tbody').find('.removeBrowserInfo').hide();
    			}
				self.bindMapCtrlClickEvents();
    		});
        },
        getParameterByKey : function (parameters, key) {
            var requestedParameter = "";
            $.each(parameters, function(index, parameter) {
                if (key === parameter.key) {
                    requestedParameter = parameter;
                    return false;
                }
            }); 

            return requestedParameter;
        },
        //returns textbox type
        getInputType : function (type) {
            if (type === "Password") {
                return "password";
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
            var self = this,previousValArray = [], type = $.type(previousValue);
            if ("string" === type) {
                previousValArray = previousValue.split(",");
            } else if("array" === type) {
                $.merge(previousValArray, previousValue);
            }
            
            if (previousValArray.length !== 0 && ((!self.isBlank(valueKey)  && $.inArray(valueKey, previousValArray) !== -1)
                    || (!self.isBlank(value)  && $.inArray(value, previousValArray) !== -1))) {
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
        
        applyEditableComboBox : function (obj) {
			if (obj !== undefined && obj !== null && obj.length > 0) {
				obj.customComboBox({
					tipText : "Type or select from the list",
					allowed : /[A-Za-z0-9\$\._\-\s]/,
					notallowed : /[\<\>\$]/,
					index : 'first'
				});
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
            commonVariables.hideloading = true;
            if (!self.isBlank(dependency)) {
                self.dynamicControlEvents($(checkBoxCtrl), key, showFlag);
            }
			self.chkSQLCheck();
			commonVariables.hideloading = false;
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
            if (obj.find('option').size() > 1) {
                jecClass = obj.find(":selected").attr("class");
            } 

            if (jecClass !== "jecEditableOption") {
                var selectedOption = $(obj).val();
                $(obj).blur();//To remove the focus from the current element
                var dependencyAttr;
                
                var controlType = $(obj).prop('tagName');
                if (controlType === 'INPUT') {
                    selectedOption = $(obj).is(':checked');
                    dependencyAttr = $(obj).attr('additionalParam');
                } else if (controlType === 'SELECT') {
                    var previousDependencyAttr = $(obj).attr('additionalparam');    //get the previvous dependency keys from additionalParam attr
                    dependencyAttr =  obj.find(":selected").attr('additionalparam');
                    self.hideSelectBoxDependencies(jecClass, previousDependencyAttr, dependencyAttr);
                } 
                
                self.changeEveDependancyListener(selectedOption, currentParamKey);  // update the watcher while changing the drop down
                self.getDynamicValues(dependencyAttr);
                self.hideCheckboxDependencyCntrls($(obj).attr("type"), selectedOption, dependencyAttr);
                self.removeFormOverflowHidden();
            }
        },

        removeFormOverflowHidden : function () {
            var self = this;

			if(self.formObj.length > 0){
				var formHeight = self.formObj.css('height').split('px')[0];
				if (!self.isBlank(formHeight) && formHeight < 150) {
					self.formObj.css("overflow", "inherit");
					self.formObj.find('.mCustomScrollBox').css("overflow", "");
				} else {
					self.formObj.css("overflow", "hidden");
					self.formObj.find('.mCustomScrollBox').css("overflow", "hidden");
				}
			}
        },

        hideCheckboxDependencyCntrls : function (controlType, isChecked, dependencyAttr) {
            var self = this;
            if (controlType === 'checkbox' && !isChecked && dependencyAttr !== undefined && dependencyAttr !== null) {
                csvDependencies = dependencyAttr.substring(dependencyAttr.indexOf('=') + 1);

                var parameterDependencies = [];
                parameterDependencies = csvDependencies.split(',');
                for (var i = 0;  i < parameterDependencies.length ; i++) {
                   var currentParameter = self.getParameterByKey(self.dynamicParameters, parameterDependencies[i]);
                   if (!self.isBlank(currentParameter) && !currentParameter.show) {
                        $("#" + currentParameter.key + "Li").hide();
                   }
                }
            }    
        },

        getDynamicValues : function(dependencyAttr) {
            var self = this, csvDependencies = "";
            if (!self.isBlank(dependencyAttr)) {
                csvDependencies = dependencyAttr.substring(dependencyAttr.indexOf('=') + 1);
                csvDependencies = self.getAllDependencies(csvDependencies);
                var dependencyArr = [];
                dependencyArr = csvDependencies.split(',');
                
                for (var i = 0; i < dependencyArr.length; i+=1) {
                    self.updateDependancy(dependencyArr[i]);
                }
                self.showControl(dependencyArr);
                
                //If the dependent child is select box, hide controls based on selected options - for on change event
                for (var i = 0; i < dependencyArr.length; i+=1) {
                    var curId = $.trim(dependencyArr[i]);
                    var dependentCtrl = $("#"+curId).prop('tagName');
                    var ctrlType = $("#"+curId).attr('type');
                    if (dependentCtrl === 'SELECT') {
                        var hideOptionDependency = $('#'+curId).find(":selected").attr('hide');
                        if (!self.isBlank(hideOptionDependency)) {
                            var hideOptionDependencyArr = [];
                            hideOptionDependencyArr = hideOptionDependency.split(',');
                            self.hideControl(hideOptionDependencyArr);
                        }
                    } 
                }   
            }
        },

        hideSelectBoxDependencies : function (jecClass, previousDependencyAttr, dependencyAttr) {
            var self = this;
            if (!self.isBlank(previousDependencyAttr)) {
                var csvPreviousDependency = previousDependencyAttr.substring(previousDependencyAttr.indexOf('=') + 1);
                if (!self.isBlank(csvPreviousDependency) && csvPreviousDependency !==  dependencyAttr) {          //hide event of all the dependencies of the previuos dependencies
                    var csvDependencies = self.getAllDependencies(csvPreviousDependency);
                    var previousDependencyArr = [];
                    previousDependencyArr = csvDependencies.split(',');
                    if (jecClass !== 'jecEditableOption') {
                        self.hideControl(previousDependencyArr); 
                        self.hideChildChckBoxDependencies(previousDependencyArr);                   
                    }
                }
            }

        },
        
        hideChildChckBoxDependencies : function (previousDependencyArr) {
            var self = this;
            for (var i = 0; i < previousDependencyArr.length; i+=1) {
                var controlTag = $("#" + previousDependencyArr[i]).prop('tagName');
                var controlType = $("#" + previousDependencyArr[i]).attr('type');
                //iterate previous dependecies & if its checkbox, then hide checkbox's dependency
                if (controlTag === 'INPUT' && controlType === 'checkbox') {
                    var id = $("#" + previousDependencyArr[i]).attr('id');
                    if ("authManager" === id) {
                        $("#" + previousDependencyArr[i]).val(false);
                        $("#" + previousDependencyArr[i]).prop("checked", false);
                    }
                    var checkBoxDependencies = $("#" + previousDependencyArr[i]).attr('additionalparam');
                    if (!self.isBlank(checkBoxDependencies)) {
                        var csvDep = checkBoxDependencies.substring(checkBoxDependencies.indexOf('=') + 1);
                        var csvDepArr = [];
                        csvDepArr = csvDep.split(',');
                        self.hideControl(csvDepArr);
                    }
                }
            }
        },

        //To update watcher map
        changeEveDependancyListener : function(selectedOption, currentParamKey) {
            var self = this;
            commonVariables.api.ajaxRequest(self.getRequestHeader(self.projectRequestBody, currentParamKey, selectedOption, "updateWatcher"), function(response) {
            });
        },
        
        //To get dynamic values while change or click events
        updateDependancy : function(dependency) {
            var self = this;
            self.showDynamicPopupLoading();
            commonVariables.api.ajaxRequest(self.getRequestHeader(self.projectRequestBody, dependency, "", "dependency"), function(response) {
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
                var currentParameter = self.getParameterByKey(self.dynamicParameters, dependency);
                if ("DynamicPageParameter" === currentParameter.type) {
                    self.appendDynamicTemplateDesign($("." + dependency + "_Template"), data);
                } else {
                    self.constructElements(data,  $('#' + dependency), isMultiple, controlType, controlTag);    
                }
            } else {
                self.hideDynamicPopupLoading();
            }
        },
        
        appendDynamicTemplateDesign : function (templateContainer, templateDesign) {
            var self = this;
            templateContainer.empty();
            templateContainer.append(templateDesign);
            self.hideDynamicPopupLoading();
            self.bindTemplateEvents();
        },

        //to dynamically update dependancy data into controls 
        constructElements : function(data, pushToElement, isMultiple, controlType, controlTag) {
            var self=this, previousValue = $(pushToElement).val(), parameterType = pushToElement.attr("parameterType");
            
            if ('SELECT' === controlTag && 'List' !== parameterType) {
                self.updateSelectCtrl(pushToElement, data, previousValue);
            }else if(!isMultiple && !controlType && !controlTag){
				self.updateLeftSideContent(data);
			}
            self.hideDynamicPopupLoading();
        },
        
        updateSelectCtrl : function (pushToElement, data, previousValue) {
            var self = this, id = pushToElement.attr("id"), editbleComboClass = $('#'+ id + ' option').attr('class');
            pushToElement.empty();
            //convert to editable combobox
            if (editbleComboClass === "jecEditableOption") {
                var editableComboOption = "<option class='jecEditableOption'>Type or select from the list</option>";
                pushToElement.append(editableComboOption);
            }
            //to construct options
            if (!$.isEmptyObject(data)) {
                var parameterDependency = pushToElement.attr("dependencyAttr");
                self.constructOptions(pushToElement, data, previousValue, true, parameterDependency);
            }

            if (pushToElement.attr("parameterEditable") !== "edit") {
                //refresh custom selectbox
                 pushToElement.selectpicker('refresh');
            } else if (editbleComboClass === "jecEditableOption" && !$.isEmptyObject(data)) {
                $('#'+ id + ' option[value="'+ data[0].key +'"]').prop("selected","selected");
            }
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
        showParameters : function() {
            var self=this;
            $(':input, .dynamicControls > li').each(function(index, value) {
                var currentObjType = $(this).prop('tagName');
                var multipleAttr = $(this).attr('multiple');
                var id = $(this).attr('id');
                var block = $("#" + id + "Li").css("display");
                if (currentObjType === "SELECT" && multipleAttr === undefined && this.options[this.selectedIndex] !== undefined && "block" === block) {
                    var dependencyAttr =  this.options[this.selectedIndex].getAttribute('additionalparam');
                    
                    if (!self.isBlank(dependencyAttr)) {
                        var csvDependencies = dependencyAttr.substring(dependencyAttr.indexOf('=') + 1);
                        csvDependencies = self.getAllDependencies(csvDependencies);
                        var dependencyArr = [];
                        dependencyArr = csvDependencies.split(',');
                        self.showControl(dependencyArr);    
                        self.showCheckBoxDependencies(dependencyArr);
                    }
                    
                    //If the dependent child is select box, hide controls based on selected options - while popup loading
                    var hideOptionDependency = this.options[this.selectedIndex].getAttribute('hide');
                    if (!self.isBlank(hideOptionDependency)) {
                        var hideOptionDependencyArr = [];
                        hideOptionDependencyArr = hideOptionDependency.split(',');
                        self.hideControl(hideOptionDependencyArr);
                    } 
                    
                } else if(currentObjType === "SELECT" && multipleAttr === undefined && $(this).attr('dependencyAttr') !== undefined  && "block" === block) {
                    var dependencyAttr =  $(this).attr('dependencyAttr');
                    if (!self.isBlank(dependencyAttr)) {
                        var csvDependencies = self.getAllDependencies(dependencyAttr);
                        var dependencyArr = [];
                        dependencyArr = csvDependencies.split(',');
                        self.showControl(dependencyArr);                    
                    }
                }
            });
			
			self.chkSQLCheck();
            self.removeFormOverflowHidden();
        },
		
		
		chkSQLCheck : function(){
			if(!$('#executeSql').is(':checked')){
				$('table[name=fetchSql_table]').hide();
				$('#dataBaseLi').hide();
			}else{
				$('table[name=fetchSql_table]').show();
				$('#dataBaseLi').show();
			}
		},
		
        
        showCheckBoxDependencies : function (dependencyArr) {
            var self = this;
            //To show checkbox's dependencies
            for (var i = 0; i < dependencyArr.length; i+=1) {
                var controlTag = $("#" + dependencyArr[i]).prop('tagName');
                var controlType = $("#" + dependencyArr[i]).attr('type');
                if (controlTag === 'INPUT' && controlType === 'checkbox') {
                    var selectedOption = $("#" + dependencyArr[i]).is(':checked');
                    if (selectedOption) {
                        var c = $("#" + dependencyArr[i]).attr('additionalparam');
                        if (!self.isBlank(c)) {
                            var csvDep = c.substring(c.indexOf('=') + 1);
                            var csvDepArr = [];
                            csvDepArr = csvDep.split(',');
                            for (var j = 0; j < csvDepArr.length; j+=1) {
                                var showHide = $("#" + csvDepArr[j]).attr('showHide');
                                if (showHide === 'false') {
                                    $("#" + csvDepArr[j] + "Li").show();
                                } 
                            }
                        }
                    } 
                }
            }
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
                if (!self.isBlank(additionalParam)) {
                    dependencies = dependencies.concat(comma);
                    dependencies = dependencies.concat(additionalParam.substring(additionalParam.indexOf('=') + 1));            
                }
            }
            
            return dependencies; 
        },
        
        isBlank : function(str) {
            return (!str || /^\s*$/.test(str));
        },
        
        //To show controls
        showControl : function(controls) {
            for (var i = 0; i < controls.length; i++) {
                $('#' + controls[i] + 'Li').show();
                $('.' + controls[i] + '_Template').show();
            }
        },
        
        //To hide controls
        hideControl : function(controls) {
            for (var i = 0; i < controls.length; i++) {
                $('#' + controls[i] + 'Li').hide();
                $('.' + controls[i] + '_Template').hide();
            }
        },
        
        isBlankObject : function(obj) {
            if (obj !== null && obj !== undefined) {
                return false;
            }
            return true;
        }, 

        queryDynamicTemplate : function (header, parameter, whereToRender, callback) {
            var self = this;

            try {
                commonVariables.api.ajaxRequest(header, function(response) {
                    if (response !== null)   {
                        callback(response, whereToRender);
                    } else {
                        callback({"status" : "service failure"}, whereToRender);
                    }
                });
            } catch (exception) {
            }
        },
        
        /********************************** TEMPLATE METHODS STARTS ***********************************************/
         bindTemplateEvents : function () {
            var self = this, i = 1, contextUrlsRowId = "";
            $("#contextAdd").unbind("click");
            $("#contextAdd").click(function() {
                self.addContext('contextDiv');
            }) ;

            $("#dbContextAdd").unbind("click");
            $("#dbContextAdd").click(function() {
                self.addContext('dbContextDiv');
            }) ;

            self.bindContextEvents();
            self.bindRemoveContextEvent();
            self.enableDisableDeleteContext('contextDivClass', 'removeContext');
            self.enableDisableDeleteContext('dbContextDivClass', 'removeDBContext');
        },

        bindContextEvents : function () {
            var self = this;
            $(".headerKeyAdd").unbind("click");
            $(".headerKeyAdd").click(function() {
                self.headerKeyAdd($(this));
            }) ;

            
            $(".redirectAutomatically").unbind("click");
            $(".redirectAutomatically").click(function() {
                self.checkUnCheck($(this));
            }) ;

            $(".followRedirects").unbind("click");
            $(".followRedirects").click(function() {
                self.checkUnCheck($(this));
            }) ;

            $(".addParameter").unbind("click");
            $(".addParameter").click(function() {
                self.addParameterRow($(this));
            }) ;

            $(".removeParamter").unbind("click");
            $(".removeParamter").click(function() {
                self.removeParameterRow($(this));
            }) ;
        },

        addContext : function (contextObj) {
            var self = this, html = $('.'+contextObj+'Class').html();
            contextUrlsRowId = contextObj + self.i;
            var contextUrlRow = $(document.createElement('div')).attr("id", contextUrlsRowId).attr("class", contextObj+'Class').css('margin-bottom','5px');
            contextUrlRow.html(html);
            $("#" + contextObj + "Parent").append(contextUrlRow);//append to a parent div
            $(':input:not(:button)', $("#"+contextUrlsRowId)).val('');//to clear already inputed value
            self.i++;
            $("#"+contextUrlsRowId).find('.redirectAutomatically').prop('checked', true);
            $("#"+contextUrlsRowId).find('.add_test').empty();

            var j = 1;
            $("#"+contextUrlsRowId).find('.parameterRow').each(function() {
                if (j !== 1) {
                    $(this).remove();
                }   
                j++;
            });
            self.showHideMinus($("#"+contextUrlsRowId).find('.addParameter'), 1);
            self.bindRemoveContextEvent();      
            self.bindContextEvents();
            self.enableDisableDeleteContext('contextDivClass', 'removeContext');
            self.enableDisableDeleteContext('dbContextDivClass', 'removeDBContext');
        },

        removeContext : function (obj, divClass, delIcon) {
            var imgClass = obj.find('img').attr("class");
            if (imgClass === "removeContextImg") {
                var self = this;
                obj.closest('.'+divClass).remove();
                self.enableDisableDeleteContext(divClass, delIcon);
            }
        },

        bindRemoveContextEvent : function() {
            var self = this;
            $(".removeContext").unbind("click");
            $(".removeContext").click(function() {
                self.removeContext($(this), 'contextDivClass', 'removeContext');
            }); 
            $(".removeDBContext").unbind("click");
            $(".removeDBContext").click(function() {
                self.removeContext($(this), 'dbContextDivClass', 'removeDBContext');
            });  
        },

        enableDisableDeleteContext : function (divClass, delIcon) {
            var urlCount = $('.' + divClass).size();
            if (urlCount === 1) {
                $("." + delIcon).find('img').attr("src", "themes/default/images/helios/delete_row_off.png");
                $("." + delIcon).find('img').removeClass("removeContextImg");
            } else {
                $("." +delIcon).find('img').attr("src", "themes/default/images/helios/delete_row.png");
                $("." +delIcon).find('img').addClass("removeContextImg");
            }
        },

        headerKeyAdd : function (btnObj) {
            var self = this, key = btnObj.parent().find('.headerKey').val(), value = btnObj.parent().find('.headerValue').val();
            if (!self.isBlank(key) && !self.isBlank(value)) {
                var constructHeaders = '<div class="add_remove_test headers">' + key + ' : '+ value+'<input type="hidden" name="headerKey" value="'+key+'"> <input type="hidden" name="headerValue" value="'+value+'"> <img class="removeHeaders" src="themes/default/images/helios/remove_test.png"></div>';
                btnObj.closest('tbody').find(".headerContent").show();
                btnObj.closest('tbody').find(".add_test").append(constructHeaders);
                $(".headerKey").val("");
                $(".headerValue").val("");

                $(".removeHeaders").unbind("click");
                $(".removeHeaders").click(function() {
                    self.removeHeaders($(this));
                }) ;  
            }
        },

        removeHeaders : function (obj) {
            var headerCount = obj.parent().parent().find('.headers').size();
            if (headerCount - 1 === 0) {
                obj.closest('.headerContent').hide();
            }
            obj.parent().remove();
        },

        addParameterRow : function (thisObj) {
            var self = this, parameterRow = '<tr class="parameterRow"><td><input type="text" class="parameterName" name="parameterName" placeholder="Name"><input type="text" class="parameterValue" name="parameterValue" placeholder="Value"><input class="parameterEncode" name="parameterEncode" type="checkbox">Encode '+
                        '<img class="add_test_icon removeParamter" src="themes/default/images/helios/minus_icon.png">'+
                        '<img src="themes/default/images/helios/plus_icon.png" class="add_test_icon addParameter"></td></tr>';
            thisObj.closest('tbody').append(parameterRow);     

            $(".addParameter").unbind("click");
            $(".addParameter").click(function() {
                self.addParameterRow($(this));
            }) ;     

            var size = thisObj.closest('tbody').find('.addParameter').size();
            self.showHideMinus(thisObj, size);
            $(".removeParamter").unbind("click");
            $(".removeParamter").click(function() {
                self.removeParameterRow($(this));
            }) ;
        },

        removeParameterRow : function(obj) {
            var self = this;
            var size = obj.closest('tbody').find('.addParameter').size() - 1; 
            self.showHideMinus(obj, size);
            obj.closest('tr').remove();
           
        },

        showHideMinus : function (obj, size) {
            if (size > 1) {
             obj.closest('tbody').find($(".removeParamter")).show();  
            } else if (size === 1) {
                obj.closest('tbody').find($(".removeParamter")).hide();  
            }
        },

        checkUnCheck : function (checkBoxObj) {
            var redirectAutomatically = $(checkBoxObj).closest('tr').find($(".redirectAutomatically")).is(':checked');    
            var followRedirects = $(checkBoxObj).closest('tr').find($(".followRedirects")).is(':checked');
            var currentObjClass = $(checkBoxObj).attr('class');
            if (currentObjClass === 'redirectAutomatically' && redirectAutomatically) {
                $(checkBoxObj).closest('tr').find($(".followRedirects")).prop('checked', false);
            } else if (followRedirects) {
                $(checkBoxObj).closest('tr').find($(".redirectAutomatically")).prop('checked', false);
            }       
        },
        /********************************** TEMPLATE METHODS ENDS ***********************************************/

        /***
         * provides the request header
         * @return: returns the contructed header
         */
        getRequestHeader : function(projectRequestBody, key, selectedOption, action) {
            var self = this;
            var appDirName = commonVariables.appDirName;
            var goal = commonVariables.goal;
            var phase = commonVariables.phase;
            var userId = commonVariables.api.localVal.getSession('username');
            var customerId = self.getCustomer();
            var header = {
                contentType: "application/json",
                dataType: "json",
                webserviceurl: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName="+appDirName+"&goal="+ goal
            };
            
            if(action === "parameter"){
                header.requestMethod = "GET";
				var buildNumber = "";
				
				if(goal === "deploy"){
					buildNumber = "&buildNumber="+ commonVariables.buildNo+"&iphoneDeploy=" + (commonVariables.iphoneDeploy == null ? "" : commonVariables.iphoneDeploy);
				} 
				
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName="+appDirName+"&goal="+ goal+"&phase="+phase+"&customerId="+customerId+"&userId="+userId+buildNumber;
            } else if (action === "updateWatcher") {
                header.requestMethod = "POST";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + "updateWatcher" + "?appDirName="+appDirName+"&goal="+ goal+"&key="+key+"&value="+selectedOption;
            } else if(action === "dependency" && key !== ""){
                header.requestMethod = "POST";
                header.requestPostBody = projectRequestBody;
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dependencyContext + "?appDirName="+appDirName+"&goal="+ goal+"&phase="+phase+"&customerId="+customerId+"&userId="+userId+"&key="+ key;
            } else if (action === "template") {
                header.requestMethod = "GET";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.templateContext + "?appDirName="+appDirName+"&goal="+ goal+"&phase="+phase+"&customerId="+customerId+"&userId="+userId+"&parameterKey="+ key;                
            }
            
            return header;
        }
    });

    return Clazz.com.components.dynamicPage.js.listener.DynamicPageListener;
});