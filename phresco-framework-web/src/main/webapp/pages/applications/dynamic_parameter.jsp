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

<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.antlr.stringtemplate.StringTemplate" %>

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
<script src="js/select-envs.js"></script>

<%
    /* String defaultEnv = "";
   	String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
   	String importSqlPro  = (String) request.getAttribute(FrameworkConstants.REQ_IMPORT_SQL);
   	String finalName = (String) request.getAttribute(FrameworkConstants.FINAL_NAME);
   	String mainClassValue = (String) request.getAttribute(FrameworkConstants.MAIN_CLASS_VALUE);
   	String checkImportSql = "";
   	if (importSqlPro != null && Boolean.parseBoolean(importSqlPro)) {
   	    checkImportSql = "checked";
   	} */
   	
   	//xcode targets
   	/* List<PBXNativeTarget> xcodeConfigs = (List<PBXNativeTarget>) request.getAttribute(FrameworkConstants.REQ_XCODE_CONFIGS);
   	List<String> buildInfoEnvs = (List<String>) request.getAttribute(FrameworkConstants.BUILD_INFO_ENVS);
   	List<Environment> environments = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS); */
   	// mac sdks
   /* 	List<String> macSdks = (List<String>) request.getAttribute(FrameworkConstants.REQ_IPHONE_SDKS);
   	
   	Map<String, String> jsMap = (Map<String, String>) request.getAttribute(FrameworkConstants.REQ_MINIFY_MAP);
   	String fileLoc = (String) request.getAttribute("fileLocation"); */
   	
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
    String appId  = applicationInfo.getId();
%>

<form autocomplete="off" class="build_form form-horizontal" id="generateBuildForm">
<div id="generateBuild_Modal">
	<%
	    if (CollectionUtils.isNotEmpty(projectModules)) {
	%>
		<div class="control-group">
			<label class="control-label labelbold popupLbl">
				<s:text name='lbl.name' />
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
					if (StringUtils.isNotEmpty(parameter.getDependency())) {
						//If current control has dependancy value 
						onClickFunction = "dependancyChckBoxEvent(this, '"+ parameter.getKey() +"');";
					} else {
						onClickFunction = "changeChckBoxValue(this);";
					}
					
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
					} else if (CollectionUtils.isNotEmpty(psblValues) && psblValues.get(0).getDependency() != null) {
						onChangeFunction = "selectBoxOnChangeEvent(this,  '"+ parameter.getKey() +"')";
					}
					
					parameterModel.setOnChangeFunction(onChangeFunction);
					parameterModel.setObjectValue(psblValues);
					parameterModel.setMultiple(Boolean.parseBoolean(parameter.getMultiple()));
					parameterModel.setDependency(parameter.getDependency());
					
					StringTemplate selectElmnt = FrameworkUtil.constructSelectElement(parameterModel);
	%>
					<%= selectElmnt %>
	<% 			
				} else if (FrameworkConstants.TYPE_DYNAMIC_PARAMETER.equalsIgnoreCase(parameter.getType()) && (!parameter.isSort())) {
					//To dynamically load values into select box for environmet
					List<com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value> dynamicPsblValues = (List<com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value>) request.getAttribute(FrameworkConstants.REQ_DYNAMIC_POSSIBLE_VALUES + parameter.getKey());
					if (StringUtils.isNotEmpty(parameter.getValue())) {
						List<String> selectedValList = Arrays.asList(parameter.getValue().split(FrameworkConstants.CSV_PATTERN));	
						parameterModel.setSelectedValues(selectedValList);
					}
					String onChangeFunction = ""; 
					if(!Boolean.parseBoolean(parameter.getMultiple()) && StringUtils.isNotEmpty(parameter.getDependency())) {
					    onChangeFunction = "selectBoxOnChangeEvent(this, '"+ parameter.getKey() +"')";
					} else {
					    onChangeFunction = "";
					}
					
					parameterModel.setOnChangeFunction(onChangeFunction);
					parameterModel.setObjectValue(dynamicPsblValues);
					parameterModel.setMultiple(Boolean.parseBoolean(parameter.getMultiple()));
					parameterModel.setDependency(parameter.getDependency());
					
					StringTemplate selectDynamicElmnt = FrameworkUtil.constructSelectElement(parameterModel);
	%>				
		    		<%= selectDynamicElmnt %>
	<%
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
					
					StringTemplate txtMultiInputElement = FrameworkUtil.constructMapElement(parameterModel);
	%>
					<%= txtMultiInputElement %>
	<%
				}
	%>
			<script type="text/javascript">
				<%-- $('input[name="<%= parameter.getKey() %>"]').live('input propertychange',function(e) {
					var name = $(this).val();
					var type = '<%= parameter.getType() %>';
					var txtBoxName = '<%= parameter.getKey() %>';
					validateInput(name, type, txtBoxName);
				}); --%>
			</script>
	<% 
			}
		}
	%>
	<!-- dynamic parameters ends -->
</div>
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
		
		// accodion for advanced issue
// 		accordion();
		
		$('#importSql').click(function() {
			var isChecked = $('#importSql').is(":checked");
			if (isChecked) {
<%-- 				$("#errMsg").html('<%= FrameworkConstants.EXEC_SQL_MSG %>'); --%>
				// getting database list based on environment and and execute sqk checkbox
		    } else {
		    	$('#DbWithSqlFiles').val("");
		    	$("#errMsg").empty();
		    }
			// show hide sql execution fieldset
			executeSqlShowHide();
		});
		
		$('#hideLog').change(function() {
			var isChecked = $('#hideLog').is(":checked");
			if (isChecked) {
				$("#errMsg").html('<%= FrameworkConstants.HIDE_LOG_MSG %>');
		    } else {
		    	$("#errMsg").empty();
		    }
		});
		
		$('#androidSigning').click(function() {
// 			if ($(this).is(':checked')) {
				// remove existing duplicate div
				$('#advancedSettingsBuildForm').remove();
				showAdvSettingsConfigure();
// 			} else {
// 				removeAdvSettings();
// 			}
		})
		
		$('#environments').change(function() {
			if ($("#from").val() != "generateBuild") {
				
				$('#DbWithSqlFiles').val("");
				executeSqlShowHide();
			}
		});
		
		//execute Sql script
		executeSqlShowHide();
// 		showHideMinusIcon();
	});
	
	function executeSqlShowHide() {
		if($('#importSql').is(":checked")) {
			loadingIconShow();
			$('#sqlExecutionContain').show();
			// after fieldset completed, we have to load db and sql files
		getDatabases();
		} else {
			$('#sqlExecutionContain').hide();
		}
	}
	
	function getDatabases() {
		if (!isBlank($("#environments").val())) { 
			var params = 'environments=';
		    params = params.concat($("#environments").val());
		    <%-- params = params.concat("&projectCode=");
		    params = params.concat('<%= projectCode %>'); --%>
			performAction("getSqlDatabases", params, '', true);
		}
	}
	
	$("#databases").change(function() {
		loadingIconShow();
		getSQLFiles();
	});
	
	function getSQLFiles() {
		if(!isBlank($("#databases").val())) {
			var params = 'selectedDb=';
		    params = params.concat($("#databases").val());
		    params =  params.concat('&environments=');
		    params = params.concat($("#environments").val());
		   <%--  params = params.concat("&projectCode=");
		    params = params.concat('<%= projectCode %>'); --%>
			performAction("fetchSQLFiles", params, '', true);
		}
	}

	function loadingIconShow() {
		$('.popupLoadingIcon').show();
		getCurrentCSS();
	}
	
	function hideLoadingIcon() {
		$('.popupLoadingIcon').hide();
	}
	
	function checkObj(obj) {
		if(obj == "null" || obj == undefined) {
			return "";
		} else {
			return obj;
		}
	}
	
	function buildValidateSuccess(lclURL, lclReaderSession) {
		url = lclURL;
		readerSession = lclReaderSession;
		checkForConfig();
	}
	
	function checkForConfig() {
		loadContent('checkForConfiguration', $("#generateBuildForm"), '', getBasicParams(), true);
	}
	
	function successEnvValidation(data) {
		if(data.hasError == true) {
			showError(data);
		} else {
			$('.build_cmd_div').css("display", "block");
			$("#console_div").empty();
			//showParentPage();
			if(url == "build") {
				$("#warningmsg").show();
				$("#console_div").html("Generating build...");
			} else if(url == "deploy") {
				$("#console_div").html("Deploying project...");
			}
			performUrlActions(url, readerSession);
		}
	}
	
	function performUrlActions(url, actionType) {
		<%-- var params = "";
		params = params.concat("&environments=");
		params = params.concat(getSelectedEnvs());
		params = params.concat("&DbWithSqlFiles=");
		params = params.concat($('#DbWithSqlFiles').val()); --%>
		readerHandlerSubmit(url, '<%= appId %>', actionType, $("#generateBuildForm"), true, getBasicParams());
	}
	
	function showAdvSettingsConfigure() {
		showPopup();
		popup('advancedBuildSettings', '', $('#popup_div'), '', true);
	}
	
	//to update build number in hidden field for deploy popup
	<% if (FrameworkConstants.REQ_DEPLOY.equals(from)) { %>
		$("input[name=buildNumber]").val('<%= buildNumber %>');
	<% } %>
	
	var pushToElement = "";
	var isMultiple = "";
	var controlType = "";
	function dependancyChckBoxEvent(obj, currentParamKey) {
		selectBoxOnChangeEvent(obj, currentParamKey);
	}
	
	function updateDependantValue(data) {
		constructElements(data, pushToElement, isMultiple, controlType);
	}
	
	function selectBoxOnChangeEvent(obj, currentParamKey) {
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
				hideControl(previousDependencyArr);
			}
		}
		var csvDependencies;
		changeEveDependancyListener(selectedOption, currentParamKey); // update the watcher while changing the drop down
		
		if (dependencyAttr !== undefined) {
			csvDependencies = dependencyAttr.substring(dependencyAttr.indexOf('=') + 1);
			csvDependencies = getAllDependencies(csvDependencies);
			var dependencyArr = new Array();
			dependencyArr = csvDependencies.split(',');
			for (var i = 0; i < dependencyArr.length; i+=1) {
				$('#' + $.trim(dependencyArr[i]) + 'Control').show();
				updateDependancy(dependencyArr[i]);
			}
		}
		
		if ($(obj).attr("type") === 'checkbox') {
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
			if (currentObjType === "SELECT") {
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
		params = params.concat("&dependency=");
		params = params.concat(dependency);
		
		loadContent('updateDependancy', '', '', params, true, false, 'updateDependancySuccEvent');
	}
	
	function updateDependancySuccEvent(data) {
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
	
	function changeChckBoxValue(obj) {
		if ($(obj).is(':checked')) {
			$(obj).val("true");
		} else {
			$(obj).val("false");
		}
	}
	
	function addRow(obj) {
		var removeIconTd = $(document.createElement('td')).attr("class", "borderForLoad");
		var removeIconAnchr = $(document.createElement('a'));
		var removeIcon = $(document.createElement('img')).attr("class", "add imagealign").attr("src", "images/icons/minus_icon.png").attr("onclick", "removeRow(this)");
		removeIconAnchr.append(removeIcon);
		removeIconTd.append(removeIconAnchr);
		var columns = $(obj).closest('table').children('tbody').children('tr:first').html();
		var newRow = $(document.createElement('tr')).attr("class", "borderForLoad");
		newRow.append(columns);
		newRow.append(removeIconTd);
		newRow.appendTo("#propTempTbodyForHeader");
	}
	
	function removeRow(obj) {
		$(obj).closest('tr').remove();
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
</script>