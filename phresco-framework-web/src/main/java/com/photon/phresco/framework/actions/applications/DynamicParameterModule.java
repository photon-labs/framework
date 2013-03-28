/**
 * Framework Web Archive
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.framework.actions.applications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.photon.phresco.api.DynamicPageParameter;
import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.api.DynamicParameterForModule;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.RepoInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.model.DependantParameters;
import com.photon.phresco.plugins.model.Module.Configurations.Configuration.Parameter;
import com.photon.phresco.plugins.model.Module.Configurations.Configuration.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Module.Configurations.Configuration.Parameter.PossibleValues.Value;
import com.photon.phresco.plugins.util.ModulesProcessor;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.PhrescoDynamicLoader;

public class DynamicParameterModule extends FrameworkBaseAction implements Constants {

	private static final long serialVersionUID = 1L;
	
    private String availableParams = "";
    
    private static Map<String, PhrescoDynamicLoader> pdlMap = new HashMap<String, PhrescoDynamicLoader>();
	
    protected Map<String, Object> getDynamicPageParameter(ApplicationInfo appInfo, Map<String, DependantParameters> watcherMap, Parameter parameter) throws PhrescoException {
		String parameterKey = parameter.getKey();
		Map<String, Object> paramsMap = constructMapForDynVals(appInfo, watcherMap, parameterKey);
		String className = parameter.getDynamicParameter().getClazz();
		DynamicPageParameter dynamicPageParameter;
		PhrescoDynamicLoader phrescoDynamicLoader = pdlMap.get(getCustomerId());
		if (MapUtils.isNotEmpty(pdlMap) && phrescoDynamicLoader != null) {
			dynamicPageParameter = phrescoDynamicLoader.getDynamicPageParameter(className);
		} else {
			//To get repo info from Customer object
			Customer customer = getServiceManager().getCustomer(getCustomerId());
			RepoInfo repoInfo = customer.getRepoInfo();
			//To set groupid,artfid,type infos to List<ArtifactGroup>
			List<ArtifactGroup> artifactGroups = new ArrayList<ArtifactGroup>();
			ArtifactGroup artifactGroup = new ArtifactGroup();
			artifactGroup.setGroupId(parameter.getDynamicParameter().getDependencies().getDependency().getGroupId());
			artifactGroup.setArtifactId(parameter.getDynamicParameter().getDependencies().getDependency().getArtifactId());
			artifactGroup.setPackaging(parameter.getDynamicParameter().getDependencies().getDependency().getType());
			//to set version
			List<ArtifactInfo> artifactInfos = new ArrayList<ArtifactInfo>();
	        ArtifactInfo artifactInfo = new ArtifactInfo();
	        artifactInfo.setVersion(parameter.getDynamicParameter().getDependencies().getDependency().getVersion());
			artifactInfos.add(artifactInfo);
	        artifactGroup.setVersions(artifactInfos);
			artifactGroups.add(artifactGroup);
			
			//dynamically loads specified Class
			phrescoDynamicLoader = new PhrescoDynamicLoader(repoInfo, artifactGroups);
			dynamicPageParameter = phrescoDynamicLoader.getDynamicPageParameter(className);
			pdlMap.put(getCustomerId(), phrescoDynamicLoader);
		}
		
		return dynamicPageParameter.getObjects(paramsMap);
	}
    
    private void addParentToWatcher(Map<String, DependantParameters> watcherMap, String parameterKey, String parameterValue) {
	    	
	    	DependantParameters dependantParameters;
	        if (watcherMap.containsKey(parameterKey)) {
	            dependantParameters = (DependantParameters) watcherMap.get(parameterKey);
	        } else {
	            dependantParameters = new DependantParameters();
	        }
	    	dependantParameters.setValue(parameterValue);
	    	watcherMap.put(parameterKey, dependantParameters);
	    }

    private void addWatcher(Map<String, DependantParameters> watcherMap, String dependency, String parameterKey, String parameterValue) {
        if (StringUtils.isNotEmpty(dependency)) {
            List<String> dependencyKeys = Arrays.asList(dependency.split(CSV_PATTERN));
            for (String dependentKey : dependencyKeys) {
            	DependantParameters dependantParameters;
                if (watcherMap.containsKey(dependentKey)) {
                    dependantParameters = (DependantParameters) watcherMap.get(dependentKey);
                } else {
                    dependantParameters = new DependantParameters();
                }
                dependantParameters.getParentMap().put(parameterKey, parameterValue);
                watcherMap.put(dependentKey, dependantParameters);
            }
        }
        addParentToWatcher(watcherMap, parameterKey, parameterValue);
    }
    
    private void addValueDependToWatcher(Map<String, DependantParameters> watcherMap, String parameterKey, List<Value> values, Parameter parameter) {
		for (Value value : values) {
		    if (StringUtils.isNotEmpty(parameter.getDependency())) {
		        addWatcher(watcherMap, parameter.getDependency(), parameterKey, value.getKey());
		    }
		}
	}
    
    private PossibleValues getDynamicValues(Map<String, Object> watcherMap, Parameter parameter) throws PhrescoException {
		try {
			String className = parameter.getDynamicParameter().getClazz();
			DynamicParameterForModule dynamicParameter;
			PhrescoDynamicLoader phrescoDynamicLoader = pdlMap.get(getCustomerId());
			if (MapUtils.isNotEmpty(pdlMap) && phrescoDynamicLoader != null) {
				dynamicParameter = phrescoDynamicLoader.getDynamicParameterModule(className);
			} else {
				//To get repo info from Customer object
				Customer customer = getServiceManager().getCustomer(getCustomerId());
				RepoInfo repoInfo = customer.getRepoInfo();
				//To set groupid,artfid,type infos to List<ArtifactGroup>
				List<ArtifactGroup> artifactGroups = new ArrayList<ArtifactGroup>();
				ArtifactGroup artifactGroup = new ArtifactGroup();
				artifactGroup.setGroupId(parameter.getDynamicParameter().getDependencies().getDependency().getGroupId());
				artifactGroup.setArtifactId(parameter.getDynamicParameter().getDependencies().getDependency().getArtifactId());
				artifactGroup.setPackaging(parameter.getDynamicParameter().getDependencies().getDependency().getType());
				//to set version
				List<ArtifactInfo> artifactInfos = new ArrayList<ArtifactInfo>();
		        ArtifactInfo artifactInfo = new ArtifactInfo();
		        artifactInfo.setVersion(parameter.getDynamicParameter().getDependencies().getDependency().getVersion());
				artifactInfos.add(artifactInfo);
		        artifactGroup.setVersions(artifactInfos);
				artifactGroups.add(artifactGroup);
				
				//dynamically loads specified Class
				phrescoDynamicLoader = new PhrescoDynamicLoader(repoInfo, artifactGroups);
				dynamicParameter = phrescoDynamicLoader.getDynamicParameterModule(className);
				pdlMap.put(getCustomerId(), phrescoDynamicLoader);
			}
			
			return dynamicParameter.getValues(watcherMap);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
    
    /**
     * To set List of Possible values as Dynamic parameter in request
     * @param watcherMap
     * @param parameter
     * @return
     * @throws PhrescoException
     */
    protected List<Value> getDynamicPossibleValues(Map<String, Object> watcherMap, Parameter parameter) throws PhrescoException {
        PossibleValues possibleValue = getDynamicValues(watcherMap, parameter);
        List<Value> possibleValues = (List<Value>) possibleValue.getValue();
        return possibleValues;
    }
    
    protected Map<String, Object> constructMapForDynVals(ApplicationInfo appInfo, Map<String, DependantParameters> watcherMap, String parameterKey) {
        Map<String, Object> paramMap = new HashMap<String, Object>(8);
        DependantParameters dependantParameters = watcherMap.get(parameterKey);
        if (dependantParameters != null) {
            paramMap.putAll(getDependantParameters(dependantParameters.getParentMap(), watcherMap));
        }
        paramMap.put(DynamicParameter.KEY_APP_INFO, appInfo);
        paramMap.put(REQ_CUSTOMER_ID, getCustomerId());
        
        return paramMap;
    }
    
    private Map<String, Object> getDependantParameters(Map<String, String> parentMap, Map<String, DependantParameters> watcherMap) {
        Map<String, Object> paramMap = new HashMap<String, Object>(8);
        Set<String> keySet = parentMap.keySet();
        for (String key : keySet) {
            if (watcherMap.get(key) != null) {
                String value = ((DependantParameters) watcherMap.get(key)).getValue();
                paramMap.put(key, value);
            }
        }

        return paramMap;
    }
    
    /**
     * To set List of parameters in request
     * @param module TODO
     * @param appInfo
     * @param goal
     * @throws PhrescoException
     */
    protected void setPossibleValuesInReq(ModulesProcessor module, ApplicationInfo appInfo, List<Parameter> parameters, 
    		Map<String, DependantParameters> watcherMap) throws PhrescoException {
        try {
            if (CollectionUtils.isNotEmpty(parameters)) {
                StringBuilder paramBuilder = new StringBuilder();
                for (Parameter parameter : parameters) {
                    String parameterKey = parameter.getKey();
                    if (TYPE_DYNAMIC_PARAMETER.equalsIgnoreCase(parameter.getType()) && parameter.getDynamicParameter() != null) { 
                    	//Dynamic parameter
                        Map<String, Object> constructMapForDynVals = constructMapForDynVals(appInfo, watcherMap, parameterKey);
                        
                        // Get the values from the dynamic parameter class
                        List<Value> dynParamPossibleValues = getDynamicPossibleValues(constructMapForDynVals, parameter);
                        addValueDependToWatcher(watcherMap, parameterKey, dynParamPossibleValues, parameter);
                        if (watcherMap.containsKey(parameterKey)) {
                            DependantParameters dependantParameters = (DependantParameters) watcherMap.get(parameterKey);
                            if (CollectionUtils.isNotEmpty(dynParamPossibleValues)) {
                                dependantParameters.setValue(dynParamPossibleValues.get(0).getValue());
                            }
                        }
                        
                        setReqAttribute(REQ_DYNAMIC_POSSIBLE_VALUES + parameter.getKey(), dynParamPossibleValues);
                        if (CollectionUtils.isNotEmpty(dynParamPossibleValues)) {
                        	addWatcher(watcherMap, parameter.getDependency(), parameterKey, dynParamPossibleValues.get(0).getValue());
                        }
                        if (StringUtils.isNotEmpty(paramBuilder.toString())) {
                            paramBuilder.append("&");
                        }
                        paramBuilder.append(parameterKey);
                        paramBuilder.append("=");
                        if (CollectionUtils.isNotEmpty(dynParamPossibleValues)) {
                            paramBuilder.append(dynParamPossibleValues.get(0).getValue());
                        }
                    } else if (parameter.getPossibleValues() != null) { 
                        List<Value> values = parameter.getPossibleValues().getValue();
                        
                        if (watcherMap.containsKey(parameterKey)) {
                            DependantParameters dependantParameters = (DependantParameters) watcherMap.get(parameterKey);
                            dependantParameters.setValue(values.get(0).getValue());
                        }
                        
                        addValueDependToWatcher(watcherMap, parameterKey, values, parameter);
                        if (CollectionUtils.isNotEmpty(values)) {
                            addWatcher(watcherMap, parameter.getDependency(), parameterKey, values.get(0).getKey());
                        }
                        
                        if (StringUtils.isNotEmpty(paramBuilder.toString())) {
                            paramBuilder.append("&");
                        }
                        paramBuilder.append(parameterKey);
                        paramBuilder.append("=");
                        paramBuilder.append("");
                    } else if (parameter.getType().equalsIgnoreCase(TYPE_BOOLEAN) && StringUtils.isNotEmpty(parameter.getDependency())) {
                    	//Checkbox
                        addWatcher(watcherMap, parameter.getDependency(), parameterKey, parameter.getValue());
                        if (StringUtils.isNotEmpty(paramBuilder.toString())) {
                            paramBuilder.append("&");
                        }
                        paramBuilder.append(parameterKey);
                        paramBuilder.append("=");
                        paramBuilder.append("");
                    } else if(TYPE_DYNAMIC_PAGE_PARAMETER.equalsIgnoreCase(parameter.getType())) {
            			setReqAttribute(REQ_CUSTOMER_ID, getCustomerId());
            			Map<String, Object> dynamicPageParameterMap = getDynamicPageParameter(appInfo, watcherMap, parameter);
            			List<? extends Object> dynamicPageParameter = (List<? extends Object>) dynamicPageParameterMap.get(REQ_VALUES_FROM_JSON);
            			String className = (String) dynamicPageParameterMap.get(REQ_CLASS_NAME);
            			setReqAttribute(REQ_CLASS_NAME, className);
            			setReqAttribute(REQ_DYNAMIC_PAGE_PARAMETER + parameter.getKey(), dynamicPageParameter);
            		}
                }
                setAvailableParams(paramBuilder.toString());
            }
        } catch (Exception e) {
            throw new PhrescoException(e);
        }
    }
    
    public String getAvailableParams() {
        return availableParams;
    }

    public void setAvailableParams(String availableParams) {
        this.availableParams = availableParams;
    }
}