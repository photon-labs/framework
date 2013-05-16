<%--

    Framework Web Archive

    Copyright (C) 1999-2013 Photon Infotech Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

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
   	if (FrameworkConstants.REQ_DEPLOY.equals(from) || FrameworkConstants.REQ_PROCESS_BUILD.equals(from)) {
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
					FrameworkConstants.TYPE_HIDDEN.equalsIgnoreCase(parameter.getType())) {
					
					parameterModel.setInputType(parameter.getType());
					parameterModel.setValue(StringUtils.isNotEmpty(parameter.getValue()) ? parameter.getValue():"");
					
					StringTemplate txtInputElement = FrameworkUtil.constructInputElement(parameterModel);
	%> 	
					<%= txtInputElement %>
	<%
				} else if (FrameworkConstants.TYPE_PASSWORD.equalsIgnoreCase(parameter.getType())) {
					
					parameterModel.setInputType(parameter.getType());
					parameterModel.setValue("");
					
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
						$("#" + '<%= parameter.getKey() %>').focus(function() {
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
					parameterModel.setValue(parameter.getValue());
					StringTemplate fieldSetElement = FrameworkUtil.constructFieldSetElement(parameterModel);
	%>
					<%= fieldSetElement %>
					<script type="text/javascript">
					// selected source scripts
					addSelectedSourceScripts();
					</script>
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
					parameterModel.setValue(parameter.getValue());
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
				} else if (FrameworkConstants.TYPE_PACKAGE_FILE_BROWSE.equalsIgnoreCase(parameter.getType())) {
				    StringTemplate template = frameworkUtil.constructFileBrowseForPackage(parameterModel);
    %>
    				<%= template %>
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
		$('#executeSql').click(function(){
			sqlEvent();
		});
		$('#buildName').live('input propertychange', function(e) {
			var str = $(this).val();
			str = checkForSplChrExceptDot(str);
			str = removeSpaces(str);
			$(this).val(str);
		});	

	});
	
	//To focus first control in popup
	<% if (CollectionUtils.isNotEmpty(parameters)) { 
		String focusKey = parameters.get(0).getKey();
	%>
		$("#"+'<%= focusKey %>').focus();
	<% } %>
	
	//to update build number in hidden field for deploy popup
	<% if (FrameworkConstants.REQ_DEPLOY.equals(from) || FrameworkConstants.REQ_PROCESS_BUILD.equals(from)) { %>
		$("input[name=buildNumber]").val('<%= buildNumber %>');
	<% } %>
	
	function jecOptionChange() {
		 $('.jecEditableOption').text("Type or select from the list");
	}
	
	function buildValidateSuccess(lclURL, lclReaderSession) {
		url = lclURL;
		readerSession = lclReaderSession;
		successEnvValidation();
	}
	
	function successEnvValidation(data) {
		$('.build_cmd_div').css("display", "block");
		$("#console_div").empty();
		var params = getBasicParams();
		if(url == "build") {
			var fileOrFolders = [];
			var targetFolders = [];
			$('input[name=selectedFileOrFolder]').each(function(i) {
				fileOrFolders.push($(this).val());
			});
			
			$('input[name=targetFolder]').each(function(i) {
				targetFolders.push($(this).val());
			});
			
			var selectedFiles = [];
			for (i in targetFolders) {
				if ((!isBlank(targetFolders[i]) && isBlank(fileOrFolders[i])) || 
						(!isBlank(fileOrFolders[i]) && isBlank(targetFolders[i]))
						|| (!isBlank(targetFolders[i]) && !isBlank(fileOrFolders[i]))) {
					selectedFiles.push(targetFolders[i] + "#FILESEP#" + fileOrFolders[i]);
				}
			}
			params = params.concat("&selectedFiles=");
			params = params.concat(selectedFiles.join("#SEP#"));
			
			enableDivCtrls($('#packageFileBrowseControl :input'));
			$("#popupPage").modal('hide');
			$("#warningmsg").show();
			$("#console_div").html("Generating build...");
		} else if(url == "deploy") {
			$("#popupPage").modal('hide');
			$("#console_div").html("Deploying project...");
		}
		readerHandlerSubmit(url, '<%= appId %>', readerSession, $("#generateBuildForm"), true, params, $("#console_div"));
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
			
			//If the dependent child is select box, hide controls based on selected options - for on change event
			for (var i = 0; i < dependencyArr.length; i+=1) {
				var curId = $.trim(dependencyArr[i]);
				var dependentCtrl = $("#"+curId).prop('tagName');
					if (dependentCtrl === 'SELECT') {
						var hideOptionDependency = $('#'+curId).find(":selected").attr('hide');
						if (hideOptionDependency !== undefined && !isBlank(hideOptionDependency)) {
							var hideOptionDependencyArr = new Array();
							hideOptionDependencyArr = hideOptionDependency.split(',');
							hideControl(hideOptionDependencyArr);
						}
					}
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
				
				//If the dependent child is select box, hide controls based on selected options - while popup loading
				var hideOptionDependency = this.options[this.selectedIndex].getAttribute('hide');
				if (hideOptionDependency !== undefined && !isBlank(hideOptionDependency)) {
					var hideOptionDependencyArr = new Array();
					hideOptionDependencyArr = hideOptionDependency.split(',');
					hideControl(hideOptionDependencyArr);
				}
			} else if(currentObjType === "SELECT" && multipleAttr === undefined && $(this).attr('dependencyAttr') != undefined) {
				var dependencyAttr =  $(this).attr('dependencyAttr');
				if (dependencyAttr != null && !isBlank(dependencyAttr)) {
					var csvDependencies = getAllDependencies(dependencyAttr);
					var dependencyArr = new Array();
					dependencyArr = csvDependencies.split(',');
					showControl(dependencyArr);					
				}
			}
		});
		
		<% 
			if (FrameworkConstants.REQ_DEPLOY.equals(from) || FrameworkConstants.PHASE_RUNGAINST_SRC.equals(from)) {
		%>
			sqlEvent();
		<%
			}
		%>
		
		<% if (FrameworkConstants.PACKAGE.equals(from)) {%>
			singingEvent();
		<% } %>
	}
	
	$("#zipAlign").bind('click', function() {
		zipAlignEvent();
	});
	
	
	$("#signing").bind('click', function() {
		singingEvent();
	});
	
	function sqlEvent() {
		if($('#executeSql').is(":checked")) {
			$('#dataBaseControl').show();
			$('#fetchSqlControl').show();
		} else {
			$('#dataBaseControl').hide();
			$('#fetchSqlControl').hide();
		}
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
		params = params.concat('<%= phase %>');
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
			var isMultiple = $('#' + data.dependency).attr("isMultiple");
			var controlType = $('#' + data.dependency).attr('type');
			constructElements(data.dependentValues, data.dependency, isMultiple, controlType, data.parameterType);
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
				if (additionalParam === undefined) {
					additionalParam = $('#' + $.trim(keyArr[i])).attr('dependencyAttr');
				}
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
		var params = "fileOrFolder=All";
		if (fileTypes != undefined && !isBlank(fileTypes)) {
			params = params.concat("&fileType=");
			params = params.concat(fileTypes);
		}
		var okUrl = "jstd";
		var fromPage = $(obj).attr("fromPage");
		if (fromPage != undefined && !isBlank(fromPage) && fromPage == "package") {
			params = params.concat("&fromPage=");
			params = params.concat(fromPage);
			okUrl = fromPage;
			$('input[name=selectedFileOrFolder]').each(function() {
				$(this).removeClass('currentTextBox');
			});
			$(obj).closest('td').prev('td').find('input').addClass('currentTextBox');
		}
		
		additionalPopup('openBrowseFileTree', 'Browse', okUrl, '', '', params, true);
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

		var j = 1;
		$("#"+contextUrlsRowId).find('.parameterRow').each(function() {
			if (j != 1) {
				$(this).remove();
			}	
			j++;
		});
		
		$("#"+contextUrlsRowId).find('.redirectAutomatically').prop('checked', true);
		
		showHideMinusIcon($("#"+contextUrlsRowId).find('.parameterPlus'), 1);
		// when adding, if the checkbox is checked on first Context URLs checkbox, enable delete button
		enableDelBtn($('.ctxUrlCheck'));
		enableDelBtn($('.dbCheck'));
	}
	
	//To enable the delete btn when any context url check box is checked
	function enableDelBtn(checkBoxObj) {
		var delChkBoxClass = $(checkBoxObj).attr("class");
		var hasChecked = false;
		$('.'+delChkBoxClass).each(function() {
			if ($(this).is(':checked')) {
				hasChecked = true;
				return false;
			}
		});
		
		if (hasChecked && $('.'+delChkBoxClass).size() != 1 && $('.'+delChkBoxClass).size() > $('.'+delChkBoxClass+':checked').length) {
			$('#'+delChkBoxClass+'Del').addClass("btn-primary");
			$('#'+delChkBoxClass+'Del').removeAttr("disabled");
		} else {
			$('#'+delChkBoxClass+'Del').removeClass("btn-primary");
			$('#'+delChkBoxClass+'Del').attr("disabled", true);
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
		enableDelBtn($('.'+checkBoxClass));
	}

	function addHeader(obj) {
		var	key = $(obj).parents('fieldset').find($('input[class*=key]')).val();
		var	value = $(obj).parents('fieldset').find($('input[class*=value]')).val();
		if ((key != undefined && !isBlank(key)) && (value != undefined && !isBlank(value))) {
			$(obj).closest('fieldset').append('<div id="headerkeyId" class="headers" style="background-color: #bbbbbb; width: 52%; margin-left: 2px; margin-bottom:2px; height: auto; border-radius: 6px; '+
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
	
	function appendParameterRow(obj) {
		$(obj).closest('tbody').append('<tr class="parameterRow"><td class="noBorder"><input type="text" style="width:130px;"  class="parameterName" name="parameterName"></td>' + 
		'<td class="noBorder"><textarea type="text" style="width:130px; height:20px; resize:none;" class="parameterValue" name="parameterValue"></textarea></td>' +
		'<td class="noBorder"><input type="checkbox" class="parameterEncode" name="parameterEncode"></td>' + 
		'<td class="noBorder"><a><img src="images/icons/add_icon.png" id="parameterPlus" class="parameterPlus" onclick="appendParameterRow(this);"></a>' + 
		' <a><img class="del imagealign hide parameterMinus" src="images/icons/minus_icon.png" onclick="removeParameterRow(this);">' +
		'</a></td></tr>');
		
		var size = $(obj).closest('tbody').find('.parameterPlus').size();
		showHideMinusIcon(obj, size);
	}
	
	function removeParameterRow(obj) {
		var size = $(obj).closest('tbody').find('.parameterPlus').size() - 1;
		showHideMinusIcon($(obj).closest('tbody').find('.parameterPlus'), size);
		$(obj).closest('.parameterRow').remove();
	}
	
	function showHideMinusIcon(obj, size) {
		if (size > 1) {
			$(obj).closest('tbody').find($(".parameterMinus")).show();	
		} else if (size === 1) {
			$(obj).closest('tbody').find($(".parameterMinus")).hide();	
		}
	}
	
	function checkUncheck(obj) {
		var redirectAutomatically = $(obj).closest('tr').find($(".redirectAutomatically")).is(':checked');	  
		var followRedirects = $(obj).closest('tr').find($(".followRedirects")).is(':checked');
		var currentObjClass = $(obj).attr('class');
		if (currentObjClass == 'redirectAutomatically' && redirectAutomatically) {
			$(obj).closest('tr').find($(".followRedirects")).prop('checked', false);
		} else if (followRedirects) {
			$(obj).closest('tr').find($(".redirectAutomatically")).prop('checked', false);
		}		
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
	
	function performanceTemplateMandatoryVal(showIcon) {		
		var testAgainst = $("#testAgainst").val();
		var redirect = false;		
		if (testAgainst != undefined && (testAgainst == "server" || testAgainst == "webservice")) {
			redirect = contextUrlsMandatoryVal();
		} else if (testAgainst != undefined && testAgainst == "database") {
			redirect = dbContextUrlsMandatoryVal();
		} else if (testAgainst == undefined) { 			
			redirect = true; // added for CI android performance return			
		}
	
		if (redirect) {
			$('.yesNoPopupErr').empty();
			runLoadOrPerformanceTest(showIcon, 'performance');
		}		
		return redirect;
	} 
	
	function loadTemplateMandatoryVal(showIcon) {
		var testAgainst = $("#testAgainst").val();
		var redirect = false;		
		if (testAgainst != undefined && (testAgainst == "server" || testAgainst == "webservice")) {
			redirect = loadContextUrlMandatoryVal();
		} else if (testAgainst == undefined) { 			
			redirect = true; // added for CI android performance return			
		}
	
		if (redirect) {
			$('.yesNoPopupErr').empty();
			runLoadOrPerformanceTest(showIcon, 'load');
		}		
		return redirect;
	}
	
	function runLoadOrPerformanceTest(showIcon, from) {		
		var formJsonObject = $('#generateBuildForm').toJSON();
		var formJsonStr = JSON.stringify(formJsonObject);
		var templateFunction = new Array();
		var templateCsvFn = $("#stFileFunction").val();
		var jsonStr = "";
		var templJsonStr = "";
		var params = getBasicParams();
		var sep = "";
		var testAgainst = $("#testAgainst").val();
		if (templateCsvFn != undefined && !isBlank(templateCsvFn)) {
			templateFunction = templateCsvFn.split(",");
			for (i = 0; i < templateFunction.length; ++i) {
				jsonStr = window[templateFunction[i]]();	
				templJsonStr = templJsonStr + sep + jsonStr;
				sep = ",";
			}
		}		
		formJsonStr = formJsonStr.slice(0,formJsonStr.length-1);
		formJsonStr = formJsonStr + ',' + templJsonStr + '}';
		$("#resultJson").val(formJsonStr);
		var fromCi = $('#isFromCI').val();
		var ciOperation = $('#operation').val();		
		
		if ('load' == from) {//for load
			params = params.concat("&testAction=load");
			if(fromCi && testAgainst != undefined) {
				loadContent('loadPerformanceJsonWriter', $('#generateBuildForm'), '', params, false, true);
			} else if (fromCi && testAgainst == undefined) {
	 			// ci performance for android technology
	 		} else {
	 			$('#popupPage').modal('hide');
				progressPopupAsSecPopup('runLoadTest', '<%= appId %>', "load", $('#generateBuildForm'), params, '', '', showIcon);
			}
		} else {//for performance
			params = params.concat("&testAction=performance");
			if (fromCi && testAgainst != undefined) {
	 			//write json for performance in ci		
	 			loadContent('loadPerformanceJsonWriter', $('#generateBuildForm'), '', params, false, true);
	 		} else if (fromCi && testAgainst == undefined) {
	 			// ci performance for android technology
	 		} else {
	 			//performance test
	 			$('#popupPage').modal('hide');
				progressPopupAsSecPopup('runPerformanceTest', '<%= appId %>', "performance-test", $('#generateBuildForm'), params, '', '', showIcon);
	 		}			
		}
	}
	
	function addRowInPackageBrowse(obj) {
		var newDiv = $(document.createElement('div')).attr("class", "bldBrowseFileDiv");
		var existingDiv = $(obj).parent().parent().html();
		newDiv.append(existingDiv);
		$('.bldBrowseFilePrntDiv').append(newDiv);
		$(".minus_icon").show();
	}

	function removeRowInPackageBrowse(obj) {
		$(obj).parent().parent().remove();
		var noOfRows = $('div .bldBrowseFileDiv').size();
		if (noOfRows > 1) {
			$(".minus_icon").show();
		} else {
			$(".minus_icon").hide();
		}
	}
	
</script>