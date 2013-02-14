<%--
  ###
  Framework Web Archive
  
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
       http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ###
  --%>

<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.io.File"%>

<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.antlr.stringtemplate.StringTemplate" %>

<%@ page import="com.photon.phresco.plugins.util.MojoProcessor"%>
<%@ page import="com.photon.phresco.framework.actions.applications.DynamicParameterAction"%>

<%@ page import="com.photon.phresco.configuration.Environment"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.framework.commons.FrameworkUtil" %>
<%@ page import="com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter"%>
<%@ page import="com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.Name.Value"%>
<%@ page import="com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.Childs.Child"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.framework.commons.ParameterModel"%>
<%@ page import="com.photon.phresco.framework.commons.BasicParameterModel"%>

<script src="js/reader.js" ></script>

<%
   	String from = (String) request.getAttribute(FrameworkConstants.REQ_BUILD_FROM);
   	String buildNumber = "";
   	if (FrameworkConstants.REQ_DEPLOY.equals(from)) {
    	buildNumber = (String) request.getAttribute(FrameworkConstants.REQ_DEPLOY_BUILD_NUMBER);
    }
   	
   	//To read parameter list from phresco-plugin-info.xml
   	List<Parameter> parameters = (List<Parameter>) request.getAttribute(FrameworkConstants.REQ_DYNAMIC_PARAMETERS);
   	
   	List<String> projectModules = (List<String>) request.getAttribute(FrameworkConstants.REQ_PROJECT_MODULES);
    
    ApplicationInfo applicationInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_APP_INFO);
    
    String goal = (String) request.getAttribute(FrameworkConstants.REQ_GOAL);
    String phase = (String) request.getAttribute(FrameworkConstants.REQ_PHASE);
    String appId  = applicationInfo.getId();
    String customerId = (String) request.getAttribute(FrameworkConstants.REQ_CUSTOMER_ID);
    String className = "";//For performance
    FrameworkUtil frameworkUtil = new FrameworkUtil();
    DynamicParameterAction dpm = new DynamicParameterAction();
    MojoProcessor mojo = new MojoProcessor(new File(dpm.getPhrescoPluginInfoXmlFilePath(phase, applicationInfo)));
    StringBuilder stFileFunction = new StringBuilder();
    String sep = "";
%>

<form autocomplete="off" class="build_form form-horizontal" id="generateBuildForm">
<div id="generateBuild_Modal">
	<%
	    if (CollectionUtils.isNotEmpty(projectModules)) {
	%>
		<div class="control-group">
			<label class="control-label labelbold popupLbl">
				<s:text name='lbl.module' />
			</label>
			<div class="controls">
				<select name="projectModule" class="xlarge" >
					<%
						for(String projectModule : projectModules) {
					%>
							<option value="<%= projectModule %>"><%= projectModule %></option>
					<%
						}
					%>
				</select>
			</div>
		</div>
	<%
		}
	%>

	<!-- dynamic parameters starts -->
	<%	
		if (CollectionUtils.isNotEmpty(parameters)) {
			for (Parameter parameter: parameters) {
				ParameterModel parameterModel = new ParameterModel();
	   			Boolean mandatory = false;
	   			String lableTxt = "";
	   			String labelClass = "";
				if (Boolean.parseBoolean(parameter.getRequired())) {
					mandatory = true;
	 			}
				
				if (!FrameworkConstants.TYPE_HIDDEN.equalsIgnoreCase(parameter.getType())) {
					List<Value> values = parameter.getName().getValue();						
					for(Value value : values) {
						if (value.getLang().equals("en")) {	//to get label of parameter
							labelClass = "popupLbl";
							lableTxt = value.getValue();
				   		    break;
						}
					}
				}
				parameterModel.setMandatory(mandatory);
				parameterModel.setLableText(lableTxt);
				parameterModel.setLableClass(labelClass);
				parameterModel.setName(parameter.getKey());
				parameterModel.setId(parameter.getKey());
				parameterModel.setControlGroupId(parameter.getKey() + "Control");
				parameterModel.setControlId(parameter.getKey() + "Error");
				parameterModel.setShow(parameter.isShow());
				
				// load input text box
				if (FrameworkConstants.TYPE_STRING.equalsIgnoreCase(parameter.getType()) || FrameworkConstants.TYPE_NUMBER.equalsIgnoreCase(parameter.getType()) || 
					FrameworkConstants.TYPE_PASSWORD.equalsIgnoreCase(parameter.getType()) || FrameworkConstants.TYPE_HIDDEN.equalsIgnoreCase(parameter.getType())) {
					
					parameterModel.setInputType(parameter.getType());
					parameterModel.setValue(StringUtils.isNotEmpty(parameter.getValue()) ? parameter.getValue():"");
					
					StringTemplate txtInputElement = FrameworkUtil.constructInputElement(parameterModel);
	%> 	
					<%= txtInputElement %>
	<%
				} else if (FrameworkConstants.TYPE_BOOLEAN.equalsIgnoreCase(parameter.getType())) {
					String cssClass = "chckBxAlign";
					String onClickFunction = "";
					String onChangeFunction = "";
					if (StringUtils.isNotEmpty(parameter.getDependency())) {
						//If current control has dependancy value 
						List<String> dependancyList = Arrays.asList(parameter.getDependency().split(FrameworkConstants.CSV_PATTERN));
						Parameter param = mojo.getParameter(goal, dependancyList.get(0));
						onClickFunction = "dependancyChckBoxEvent(this, '"+ parameter.getKey() +"', '"+ param.isShow() +"');";
						onChangeFunction = "changeChckBoxValue(this);";
					} else {
						onClickFunction = "changeChckBoxValue(this);";
					}
					
					parameterModel.setOnChangeFunction(onChangeFunction);
					parameterModel.setOnClickFunction(onClickFunction);
					parameterModel.setCssClass(cssClass);
					parameterModel.setValue(parameter.getValue());
					parameterModel.setDependency(parameter.getDependency());
					
					StringTemplate chckBoxElement = FrameworkUtil.constructCheckBoxElement(parameterModel);
	%>
					<%= chckBoxElement%>	
	<%
				} else if (FrameworkConstants.TYPE_LIST.equalsIgnoreCase(parameter.getType()) && parameter.getPossibleValues() != null) { //load select list box
					//To construct select box element if type is list and if possible value exists
			    	List<com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value> psblValues = parameter.getPossibleValues().getValue();
			    	if (StringUtils.isNotEmpty(parameter.getValue())) {//To get the previously selected value from the phresco-plugin-info
						List<String> selectedValList = Arrays.asList(parameter.getValue().split(FrameworkConstants.CSV_PATTERN));	
						parameterModel.setSelectedValues(selectedValList);
					}
			    	
					String onChangeFunction = "";
					if (StringUtils.isNotEmpty(parameter.getDependency())) {
						onChangeFunction = "selectBoxOnChangeEvent(this,  '"+ parameter.getKey() +"', '"+ parameter.getDependency() +"')";
					} else if (CollectionUtils.isNotEmpty(psblValues)) {
						boolean addOnChangeEvent = false;
						for (com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value psblValue : psblValues) {
							if (psblValue.getDependency() != null) {
								addOnChangeEvent = true;
								break;
							}
						}
						if (addOnChangeEvent) {
							onChangeFunction = "selectBoxOnChangeEvent(this,  '"+ parameter.getKey() +"')";							
						}
					}
			
					if ("edit".equalsIgnoreCase(parameter.getEditable())) {
						parameterModel.setOptionOnclickFunction("jecOptionChange();");
					}
					parameterModel.setOnChangeFunction(onChangeFunction);
					parameterModel.setObjectValue(psblValues);
					parameterModel.setMultiple(Boolean.parseBoolean(parameter.getMultiple()));
					parameterModel.setDependency(parameter.getDependency());
					StringTemplate selectElmnt = FrameworkUtil.constructSelectElement(parameterModel);
	%>
					<%= selectElmnt %>
	<%				if ("edit".equalsIgnoreCase(parameter.getEditable())) {
	%>
					<script type="text/javascript">
						$("#" + '<%= parameter.getKey() %>').jec();
						$('.jecEditableOption').text("Type or select from the list");
						<%-- $("#"+'<%= parameter.getKey() %>'+" .jecEditableOption").prop("selected", true); --%>
						$("#" + '<%= parameter.getKey() %>').click(function() {
							var optionClass = $("#"+'<%= parameter.getKey() %>'+" :selected").attr("class");
							if (optionClass != undefined && optionClass == "jecEditableOption") {
								 $('.jecEditableOption').text("");
							}
						});
					</script>
		
	<%				}
				}  else if (FrameworkConstants.TYPE_DYNAMIC_PARAMETER.equalsIgnoreCase(parameter.getType()) && (!parameter.isSort())) {
					//To dynamically load values into select box for environmet
					List<com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value> dynamicPsblValues = (List<com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value>) request.getAttribute(FrameworkConstants.REQ_DYNAMIC_POSSIBLE_VALUES + parameter.getKey());
					if (StringUtils.isNotEmpty(parameter.getValue())) {
						List<String> selectedValList = Arrays.asList(parameter.getValue().split(FrameworkConstants.CSV_PATTERN));	
						parameterModel.setSelectedValues(selectedValList);
					}
					
					// when atleast there is a dependency option available, enable on change event
					boolean enableOnchangeFunction = false;
					if (CollectionUtils.isNotEmpty(dynamicPsblValues)) {
						for (com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value dynamicPsblValue : dynamicPsblValues) {
							String psblValDependency = dynamicPsblValue.getDependency();
							if (StringUtils.isNotEmpty(psblValDependency)) {
								enableOnchangeFunction = true;
								break;
							}
						}
					}
					
					String onChangeFunction = ""; 
					if (CollectionUtils.isNotEmpty(dynamicPsblValues) && StringUtils.isNotEmpty(dynamicPsblValues.get(0).getDependency())) {
						onChangeFunction = "selectBoxOnChangeEvent(this, '"+ parameter.getKey() +"')";
					} else if(!Boolean.parseBoolean(parameter.getMultiple()) && StringUtils.isNotEmpty(parameter.getDependency())) {
					    onChangeFunction = "selectBoxOnChangeEvent(this, '"+ parameter.getKey() +"')";
					} else if(enableOnchangeFunction) {
					    onChangeFunction = "selectBoxOnChangeEvent(this, '"+ parameter.getKey() +"')";
					} else {
					    onChangeFunction = "";
					}
					if ("edit".equalsIgnoreCase(parameter.getEditable())) {
						parameterModel.setOptionOnclickFunction("jecOptionChange();");
					}
					parameterModel.setOnChangeFunction(onChangeFunction);
					parameterModel.setObjectValue(dynamicPsblValues);
					parameterModel.setMultiple(Boolean.parseBoolean(parameter.getMultiple()));
					parameterModel.setDependency(parameter.getDependency());
					
					StringTemplate selectDynamicElmnt = FrameworkUtil.constructSelectElement(parameterModel);
	%>				
		    		<%= selectDynamicElmnt %>
	<%				if ("edit".equalsIgnoreCase(parameter.getEditable())) {
	%>
					<script type="text/javascript">
						$("#" + '<%= parameter.getKey() %>').jec();
						$('.jecEditableOption').text("Type or select from the list");
						<%-- $("#"+'<%= parameter.getKey() %>'+" .jecEditableOption").prop("selected", true); --%>
						$("#" + '<%= parameter.getKey() %>').click(function() {
							var optionClass = $("#"+'<%= parameter.getKey() %>'+" :selected").attr("class");
							if (optionClass != undefined && optionClass == "jecEditableOption") {
								 $('.jecEditableOption').text("");
							}
						});
					</script>
		
	<%				}
				} else if (FrameworkConstants.TYPE_DYNAMIC_PARAMETER.equalsIgnoreCase(parameter.getType()) && (parameter.isSort())) {
					List<com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value> dynamicPsblValues = (List<com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value>) request.getAttribute(FrameworkConstants.REQ_DYNAMIC_POSSIBLE_VALUES + parameter.getKey());
					parameterModel.setObjectValue(dynamicPsblValues);
					StringTemplate fieldSetElement = FrameworkUtil.constructFieldSetElement(parameterModel);
	%>
					<%= fieldSetElement %>
	<%
			    } else if (FrameworkConstants.TYPE_MAP.equalsIgnoreCase(parameter.getType())) {
				    List<Child> paramChilds = parameter.getChilds().getChild();
				    List<BasicParameterModel> childs = new ArrayList<BasicParameterModel>();
				    for (Child paramChild : paramChilds) {
				        BasicParameterModel child = new BasicParameterModel();
				        child.setInputType(paramChild.getType());
				        Child.Name.Value value = paramChild.getName().getValue();						
						if (value.getLang().equals("en")) {	//to get label of parameter
							lableTxt = value.getValue();
						}
				        child.setLableText(lableTxt);
				        child.setName(paramChild.getKey());
				        child.setMandatory(Boolean.valueOf(paramChild.getRequired()));
				        
				        if (paramChild.getPossibleValues() != null) {
				            child.setObjectValue(paramChild.getPossibleValues().getValue());
				        }
				        child.setPlaceHolder(paramChild.getDescription());
				        childs.add(child);
				    }
					parameterModel.setChilds(childs);
					parameterModel.setValue(parameter.getValue());
					
					StringTemplate txtMultiInputElement = FrameworkUtil.constructMapElement(parameterModel);
	%>
					<%= txtMultiInputElement %>
	<%
				} else if (FrameworkConstants.TYPE_FILE_BROWSE.equalsIgnoreCase(parameter.getType())) {
					parameterModel.setFileType(parameter.getFileType());
					StringTemplate browseFileElement = FrameworkUtil.constructBrowseFileTreeElement(parameterModel);
	%>
					<%= browseFileElement %>
	<%			
				} else if (FrameworkConstants.TYPE_DYNAMIC_PAGE_PARAMETER.equalsIgnoreCase(parameter.getType())) {
					stFileFunction.append(sep);
					stFileFunction.append(parameter.getKey());
					sep = ",";
					List<? extends Object> obj = (List<? extends Object>) request.getAttribute(FrameworkConstants.REQ_DYNAMIC_PAGE_PARAMETER + parameter.getKey());
					className = (String) request.getAttribute(FrameworkConstants.REQ_CLASS_NAME);
					StringTemplate dynamicPageTemplate = frameworkUtil.constructDynamicTemplate(customerId, parameter, parameterModel, obj, className);
	%>
					<%= dynamicPageTemplate %>
	<% 
				}
	%>
			<script type="text/javascript">
				$('input[name="<%= parameter.getKey() %>"]').live('input propertychange',function(e) {
					var value = $(this).val();
					var type = '<%= parameter.getType() %>';
					var txtBoxId = '<%= parameter.getKey() %>';
					validateInput(value, type, txtBoxId);
				});
			</script>
	<% 
			}
		}
	%>
	<!-- dynamic parameters ends -->
</div>
<input type="hidden" name="stFileFunction" id="stFileFunction" value="<%= StringUtils.isNotEmpty(stFileFunction.toString()) ? stFileFunction.toString() : "" %>"/>
<input type="hidden" name="resultJson" id="resultJson" value=""/>
</form>

<script type="text/javascript">
	//To check whether the device is ipad or not and then apply jquery scrollbar
	if(!isiPad()) {
		$(".generate_build").scrollbars();
	}

	var url = "";
	var readerSession = "";
	$(document).ready(function() {
		showParameters();//To show the parameters based on the dependency
		hidePopuploadingIcon();
		$('.jecEditableOption').click(function() {
	       $('.jecEditableOption').text("");
	    });
	});
	
	//To focus first control in popup
	<% if (CollectionUtils.isNotEmpty(parameters)) { 
		String focusKey = parameters.get(0).getKey();
	%>
		$("#"+'<%= focusKey %>').focus();
	<% } %>
	
	//to update build number in hidden field for deploy popup
	<% if (FrameworkConstants.REQ_DEPLOY.equals(from)) { %>
		$("input[name=buildNumber]").val('<%= buildNumber %>');
	<% } %>
	
	function jecOptionChange() {
		 $('.jecEditableOption').text("Type or select from the list");
	}
	
	function buildValidateSuccess(lclURL, lclReaderSession) {
		url = lclURL;
		readerSession = lclReaderSession;
		checkForConfig();
	}
	
	function checkForConfig() {
		loadContent('checkForConfiguration', $("#generateBuildForm"), '', getBasicParams(), true, true);
	}
	
	function successEnvValidation(data) {
		if(data.hasError == true) {
			showError(data);
		} else {
			$('.build_cmd_div').css("display", "block");
			$("#console_div").empty();
			//showParentPage();
			if(url == "build") {
				$("#popupPage").modal('hide');
				$("#warningmsg").show();
				$("#console_div").html("Generating build...");
			} else if(url == "deploy") {
				$("#popupPage").modal('hide');
				$("#console_div").html("Deploying project...");
			}
			performUrlActions(url, readerSession);
		}
	}
	
	function performUrlActions(url, actionType) {
		readerHandlerSubmit(url, '<%= appId %>', actionType, $("#generateBuildForm"), true, getBasicParams(), $("#console_div"));
	}
	
	function dependancyChckBoxEvent(obj, currentParamKey, showHideFlag) {
		selectBoxOnChangeEvent(obj, currentParamKey, showHideFlag);
	}
	
	var pushToElement = "";
	var isMultiple = "";
	var controlType = "";
	
	function updateDependantValue(data) {
		constructElements(data, pushToElement, isMultiple, controlType);
	}
	
	function selectBoxOnChangeEvent(obj, currentParamKey, showHideFlag) {
		var jecClass = "";
		if (obj.options != undefined || obj.options != null) {
			jecClass = obj.options[obj.selectedIndex].getAttribute('class');
		}
		var selectedOption = $(obj).val();
		$(obj).blur();//To remove the focus from the current element
		var dependencyAttr;
		
		var controlType = $(obj).prop('tagName');
		if (controlType === 'INPUT') {
			selectedOption = $(obj).is(':checked');
			dependencyAttr = $(obj).attr('additionalParam');
		} else if (controlType === 'SELECT') {
			var previousDependencyAttr = $(obj).attr('additionalparam');//get the previvous dependency keys from additionalParam attr
			var csvPreviousDependency = previousDependencyAttr.substring(previousDependencyAttr.indexOf('=') + 1);
			dependencyAttr =  obj.options[obj.selectedIndex].getAttribute('additionalparam'); //$('option:selected', obj).attr('additionalParam'); 

			if (csvPreviousDependency !== undefined && !isBlank(csvPreviousDependency) && 
					csvPreviousDependency !==  dependencyAttr) {//hide event of all the dependencies of the previuos dependencies
				var csvDependencies = getAllDependencies(csvPreviousDependency);
				var previousDependencyArr = new Array();
				previousDependencyArr = csvDependencies.split(',');
				if (jecClass != 'jecEditableOption') {
					hideControl(previousDependencyArr);					
				}
			}
		}
		var csvDependencies;
		changeEveDependancyListener(selectedOption, currentParamKey); // update the watcher while changing the drop down
		
		if (dependencyAttr !== undefined && dependencyAttr != null) {
			csvDependencies = dependencyAttr.substring(dependencyAttr.indexOf('=') + 1);
			csvDependencies = getAllDependencies(csvDependencies);
			var dependencyArr = new Array();
			dependencyArr = csvDependencies.split(',');
			for (var i = 0; i < dependencyArr.length; i+=1) {
				$('#' + $.trim(dependencyArr[i]) + 'Control').show();
				$('.' + $.trim(dependencyArr[i]) + 'PerformanceDivClass').show();//for performance context urls
				updateDependancy(dependencyArr[i]);
			}
		}
		
		if ($(obj).attr("type") === 'checkbox' && showHideFlag === "false") {
			if (!selectedOption) {
				var previousDependencyArr = new Array();
				previousDependencyArr = csvDependencies.split(',');
				hideControl(previousDependencyArr);
			}	
		}
	}
	
	//Iterates all the elements in the build form and show the dependency elements
	function showParameters() {
		$(':input', '#generateBuildForm').each(function() {
			var currentObjType = $(this).prop('tagName');
			var multipleAttr = $(this).attr('multiple');
			if (currentObjType === "SELECT" && multipleAttr === undefined && this.options[this.selectedIndex] !== undefined ) {
				var dependencyAttr =  this.options[this.selectedIndex].getAttribute('additionalparam');
				if (dependencyAttr !== null) {
					var csvDependencies = dependencyAttr.substring(dependencyAttr.indexOf('=') + 1);
					csvDependencies = getAllDependencies(csvDependencies);
					var dependencyArr = new Array();
					dependencyArr = csvDependencies.split(',');
					showControl(dependencyArr);					
				}
			}
		});
	}
	
	function changeEveDependancyListener(selectedOption, currentParamKey) {
		var params = getBasicParams();
		params = params.concat("&goal=");
		params = params.concat('<%= goal%>');
		params = params.concat("&phase=");
		params = params.concat('<%= phase%>');
		params = params.concat("&currentParamKey=");
		params = params.concat(currentParamKey);
		params = params.concat("&selectedOption=");
		params = params.concat(selectedOption);
		
		loadContent('changeEveDependancyListener', '', '', params, true, false);
	}
	
	function updateDependancy(dependency) {
		var params = getBasicParams();
		params = params.concat("&goal=");
		params = params.concat('<%= goal%>');
		params = params.concat("&phase=");
		params = params.concat('<%= phase%>');
		params = params.concat("&dependency=");
		params = params.concat(dependency);
		
		loadContent('updateDependancy', '', '', params, true, false, 'updateDependancySuccEvent');
	}
	
	function updateDependancySuccEvent(data) {
		if (data.dynamicPageParameterDesign != undefined && !isBlank(data.dynamicPageParameterDesign)) {
			$('.' + data.dependency + "PerformanceDivClass").empty();
			$('.' + data.dependency + "PerformanceDivClass").append(data.dynamicPageParameterDesign);
		}
		
		if (data.dependency != undefined && !isBlank(data.dependency)) {
			
			if (data.dependentValues != undefined && !isBlank(data.dependentValues)) {
				var isMultiple = $('#' + data.dependency).attr("isMultiple");
				var controlType = $('#' + data.dependency).attr('type');
				constructElements(data.dependentValues, data.dependency, isMultiple, controlType);
			}
		}
	}
	
	function getAllDependencies(keys) {
		var dependencies = keys;
		var comma = ',';
		var keyArr = CSVToArray(keys);
		for (var i=0; i < keyArr.length; i+=1) {
			var controlType = $('#' + $.trim(keyArr[i])).prop('tagName');
			var additionalParam;
			if (controlType === 'SELECT') {
				additionalParam = $('#' + $.trim(keyArr[i]) + ' option:selected').attr('additionalParam');
			} else if (controlType === 'INPUT') {
				additionalParam = $('#' + $.trim(keyArr[i])).attr('additionalParam');
			}
			if (additionalParam != undefined && !isBlank(additionalParam)) {
				dependencies = dependencies.concat(comma);
				dependencies = dependencies.concat(additionalParam.substring(additionalParam.indexOf('=') + 1));			
			}
		}
		return dependencies;
	}
	
	function getTextElement(id, name) {
		var element = "<input type='text' class='input-mini' id='"+id+"' name='"+name+"' value=''>";
		element = element.concat("<span class='help-inline' id=''></span>");
		return element;
	}
	
	function getSelectElement(id, name) {
		var element = "<select class='input-medium' id='"+id+"' name='"+name+"'>";
		var length = $('#'+ name + ' option').length;
		$('#'+ name + ' option').each(function() {
			var text = $(this).text();
			var value = $(this).val();
			element = element.concat("<option value='"+value+"'>"+text+"</option>");
		});
		element = element.concat("</select>");
		return element;
	}
	
	function browseFiles(obj) {
		$('#popupPage').modal('hide');
		var fileTypes = $(obj).attr("fileTypes");
		var params = "";
		params = params.concat("&fileType=");
		params = params.concat(fileTypes);
		params = params.concat("&fileOrFolder=All");
		additionalPopup('openBrowseFileTree', 'Browse', 'jstd', '', '', params, true);
	}

	//To add the contexts and the details in the performance test
	var i = 1;
	var contextUrlsRowId = "";
	function addContext(contextObj) {
		var html = $('.'+contextObj+'Class').html();//to get designs
		contextUrlsRowId = contextObj + i;
		var contextUrlRow = $(document.createElement('div')).attr("id", contextUrlsRowId).attr("class", contextObj+'Class').css('margin-bottom','5px');
		contextUrlRow.html(html);
		$("#" + contextObj + "Parent").append(contextUrlRow);//append to a parent div
		$(':input:not(:button)', $("#"+contextUrlsRowId)).val('');//to clear already inputed value
		$("#"+contextUrlsRowId).find('div[id=headerkeyId]').remove();//to clear header key value design
		i++;
	}
	
	//To enable the delete btn when any context url check box is checked
	function enableDelBtn(checkBoxClass) {
		var hasChecked = false;
		$('.'+checkBoxClass).each(function() {
			if ($(this).is(':checked')) {
				hasChecked = true;
				return false;
			}
		});
		if (hasChecked && $('.'+checkBoxClass).size() != 1) {
			$('#'+checkBoxClass+'Del').addClass("btn-primary");
			$('#'+checkBoxClass+'Del').removeAttr("disabled");
		} else {
			$('#'+checkBoxClass+'Del').removeClass("btn-primary");
			$('#'+checkBoxClass+'Del').attr("disabled", true);
		}
	}
	
	//To remove the selected conext urls
	function deleteContextUrl(checkBoxClass) {
		$('.'+checkBoxClass).each(function() {
			var size = $('.'+checkBoxClass).size();
			if (size > 1 && $(this).is(':checked')) {
				$(this).closest('fieldset').parent().remove();
			} 
		});
		enableDelBtn(checkBoxClass);
	}

	function addHeader(obj) {
		var	key = $(obj).parents('fieldset').find($('input[class*=key]')).val();
		var	value = $(obj).parents('fieldset').find($('input[class*=value]')).val();
		if ((key != undefined && !isBlank(key)) && (value != undefined && !isBlank(value))) {
			$(obj).closest('fieldset').append('<div id="headerkeyId" class="headers" style="background-color: #bbbbbb; width: 40%; margin-bottom:2px; height: auto; border-radius: 6px; '+
						'padding: 0 0 0 10px; position: relative"><a href="#" style="text-decoration: none; margin-right: 10px; color: #000000; '+
						'margin-left: 92%;" onclick="removeHeader(this);">&times;</a><div style="cursor: pointer; color: #000000; height: auto; '+
						'position: relative; width: 90%; line-height: 17px; margin-top: -14px; padding: 0 0 6px 1px;">'+ key + " : " + value + 
						'</div><input type="hidden" name="headerKey" value="'+key+'"/><input type="hidden" name="headerValue" value="'+value+'"/></div>');
			$(obj).parents('fieldset').find($('input[class*=key]')).val("");
			$(obj).parents('fieldset').find($('input[class*=value]')).val("");
		}
	}
	
	function removeHeader(obj) {
		$(obj).parent('div').remove();
	}
	
	function updateDepdForMultSelect(obj) {
		var checkBoxName = $(obj).attr("name");
		var dependencyAttr = $(obj).attr("additionalParam").split('=');
		var dependency = dependencyAttr[1];
		var csvValue = "";
		$('input[name='+ checkBoxName +']:checked').each(function() {
			csvValue = $(this).val() + "," + csvValue;
		});
		csvValue = csvValue.substring(0, csvValue.lastIndexOf(","));
		changeEveDependancyListener(csvValue, checkBoxName);
		updateDependancy(dependency);
	}
	
	function validateInput(value, type, txtBoxId) {
		if (type == "Number") {
			$("#" + txtBoxId).val(removeSpaces(checkForNumber(value)));
		}
	}
</script>