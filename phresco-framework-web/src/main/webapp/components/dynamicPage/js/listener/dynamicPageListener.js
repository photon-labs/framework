define(["framework/widgetWithTemplate", "dynamicPage/api/dynamicPageAPI", "common/loading"], function() {

    Clazz.createPackage("com.components.dynamicPage.js.listener");

    Clazz.com.components.dynamicPage.js.listener.DynamicPageListener = Clazz.extend(Clazz.WidgetWithTemplate, {
        localStorageAPI : null,
        //loadingScreen : null,
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
            //this.loadingScreen = new Clazz.com.js.widget.common.Loading();
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
                    //self.loadingScreen.showLoading();
                    self.dynamicPageAPI.getContent(header, 
                        function(response){
                            self.responseData = response.data;
                            if(response !== undefined && response !== null){
                                if (response.data === null || response.data.length === 0) {
                                    callback("No parameters available");
                                    //self.loadingScreen.removeLoading();
                                } else {
                                    self.constructHtml(response, whereToRender, btnObj, openccObj, goal);
                                    //self.loadingScreen.removeLoading();
                                }
                            } else {
                                //responce value failed
                                callback("Responce value failed");
                                //self.loadingScreen.removeLoading();
                            }
                        }, 
                        function(serviceError){
                            //service access failed
                            callback("service access failed");
                            //self.loadingScreen.removeLoading();
                        }
                    );
                }
            }catch(error){
                //Exception
                //self.loadingScreen.removeLoading();
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
        constructHtml : function(response, whereToRender, btnObj, openccObj, goal){
            var self = this, show = "",  required = "", editable = "", multiple = "", sort = "", checked = "", additionalParam = "",
                additionalparamSel = "", dependencyVal = "", psblDependency = "", showFlag = "", enableOnchangeFunction = "", columnClass = "";
            
            if (!self.isBlank(goal) && "validate-code" === goal) {
                columnClass = "singleColumn";
            } else {
                columnClass = "doubleColumn";
            }
            
            if(response.data.length !== 0) {
                $(whereToRender).empty();
                $.each(response.data, function(index, parameter) {
                    var type = parameter.type;
                    if(type === "String" || type === "Number" || type === "Password"){
                        self.constructTxtCtrl(parameter, columnClass, whereToRender);
                    } else if (type === "Hidden") {
                        self.constructHiddenCtrl(parameter, columnClass, whereToRender);
                    } else if(type === "Boolean"){
                        self.constructCheckboxCtrl(parameter, columnClass, whereToRender);
                    } else if(type === "List"){
                        self.constructListCtrl(parameter, columnClass, parameter.possibleValues, whereToRender);
                    } else if(type === "DynamicParameter" && !parameter.sort){
                        self.constructDynamicCtrl(parameter, columnClass, parameter.possibleValues, whereToRender);
                    } else if (type === "DynamicParameter" && parameter.sort) {
                        // execute sql template
                    } else if (type === "packageFileBrowse") {
                        // package file browse template
                    } else if (type === "map") {
                    	self.constructMapControls(parameter, whereToRender);
                    }
                });
                whereToRender.append('<li></li>');
                if (!self.isBlank(btnObj)) {
                    self.opencc(btnObj, openccObj);
                }
                whereToRender.find(".selectpicker").selectpicker();
                self.controlEvent();
                self.showParameters();
            }
        },
        
        /********************* Controls construction methods starts**********************************/
        getOptionalAttr : function(parameter, optionalAttrs) {
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

            return optionalAttrs;
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
            var self=this, optionalAttrs = {}, multiple = "", selection, li = "", select = "", isMultiple = parameter.multiple === "true" ? true : false;
            if (isMultiple) {
                multiple = "multiple=multiple";
                selection = "selection=multiple";
            } else {
                multiple = "";
                selection = "selection=single";
            }
            optionalAttrs = self.getOptionalAttr(parameter, optionalAttrs);

            li = $('<li type="selectbox" class="ctrl selectCtrl '+columnClass+'" ' +optionalAttrs.show+'  id="'+parameter.key+'Li"><label>'+parameter.name.value[0].value+optionalAttrs.required+'</label></li>');
            select = $('<select data-selected-text-format="count>1" '+selection+' id="'+parameter.key+'" showHide="'+parameter.show+'" dependencyAttr="'+parameterDependency+'" name="'+parameter.key+'" parameterType="'+ parameter.type+'" class="selectpicker" '+multiple+' ' + 
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
    			for (i in possibleValues) {
    				mapCtrl =  mapCtrl.concat('<option value="'+possibleValues[i].key+'">'+possibleValues[i].value+'</option>');
    			}
    			mapCtrl =  mapCtrl.concat('</select></td>');
        	} else if ("String" === childs[0].type) {
        		mapCtrl =  mapCtrl.concat('<td><input type="text" name="'+childs[0].key+'"></td>');
        	}
            
        	if ("List" === childs[1].type) {
        		mapCtrl =  mapCtrl.concat('<td><select name="'+childs[1].key+'" class="selectpicker">');
    			var possibleValues = childs[1].possibleValues.value;
    			for (i in possibleValues) {
    				mapCtrl =  mapCtrl.concat('<option value="'+possibleValues[i].key+'">'+possibleValues[i].value+'</option>');
    			}
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
        
        /********************* Controls construction methods ends**********************************/
        
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
				if ($('.browser_table tbody').find('tr').length == 1) {
					$('.browser_table tbody').find('.removeBrowserInfo').hide();
    			}
				self.bindMapCtrlClickEvents();
    		});
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
            if (!self.isBlank(obj.options)) {
                jecClass = obj.options[obj.selectedIndex].getAttribute('class');
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
                
                if ($(obj).attr("type") === 'checkbox' && showHideFlag === "false") {
                    if (!selectedOption) {
                        var previousDependencyArr = [];
                        previousDependencyArr = csvDependencies.split(',');
                        self.hideControl(previousDependencyArr);
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
                var parameterDependency = pushToElement.attr("dependencyAttr");
                self.constructOptions(pushToElement, data, previousValue, true, parameterDependency);
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
                        if (!isBlank(c)) {
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
            };
            
            if(action === "parameter"){
                header.requestMethod = "GET";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName="+appDirName+"&goal="+ goal+"&phase="+phase+"&customerId="+customerId+"&userId="+userId;
            } else if (action === "updateWatcher") {
                header.requestMethod = "POST";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + "updateWatcher" + "?appDirName="+appDirName+"&goal="+ goal+"&key="+key+"&value="+selectedOption;
            } else if(action === "dependency" && key !== ""){
                header.requestMethod = "POST";
                header.requestPostBody = projectRequestBody;
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dependencyContext + "?appDirName="+appDirName+"&goal="+ goal+"&phase="+phase+"&customerId="+customerId+"&userId="+userId+"&key="+ key;
            }
            
            return header;
        }
    });

    return Clazz.com.components.dynamicPage.js.listener.DynamicPageListener;
});