package com.photon.phresco.framework.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.MavenCommands.MavenCommand;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.util.Utility;

public class DynamicParameterUtil extends FrameworkBaseAction {

	private static final long serialVersionUID = 1L;
	
	/**
     * To get path of phresco-plugin-info.xml file
     * @param applicationInfo
     * @return 
     */
    protected String getPhrescoPluginInfoFilePath(ApplicationInfo applicationInfo) {
		String filePath = Utility.getProjectHome() + FILE_SEPARATOR + applicationInfo.getAppDirName() + FILE_SEPARATOR + 
										FOLDER_DOT_PHRESCO + FILE_SEPARATOR + PHRESCO_PLUGIN_INFO_XML;
		
		return filePath;
	}
    
    
	/**
     * To get list of parameters from phresco-plugin-info.xml
     * @param applicationInfo
     * @param goal
     * @return List<Parameter>
     * @throws PhrescoException
     */
    protected List<Parameter> getMojoParameters(MojoProcessor mojo, String goal) throws PhrescoException {
		com.photon.phresco.plugins.model.Mojos.Mojo.Configuration mojoConfiguration = mojo.getConfiguration(goal);
		if (mojoConfiguration != null) {
		    return mojoConfiguration.getParameters().getParameter();
		}
		
		return null;
	}
    
    protected List<Parameter> setDynamicParametersInReq(ApplicationInfo appInfo, String goal) throws PhrescoException {
        MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(appInfo)));
        com.photon.phresco.plugins.model.Mojos.Mojo.Configuration mojoConfiguration = mojo.getConfiguration(goal);
        List<Parameter> parameters = null;
        if (mojoConfiguration != null) {
            parameters = mojoConfiguration.getParameters().getParameter();
        }
        
        return parameters;
    }
    
    /**
     * To set List of parameters in request
     * @param goal
     * @throws PhrescoException
     */
    protected void setPossibleValuesInReq(List<Parameter> parameters, Map<String, Object> watcherMap, List<String> controlsTobeShowed) throws PhrescoException {
        try {
            if (CollectionUtils.isNotEmpty(parameters)) {
                Map<String, String> valuesMap = new HashMap<String, String>();
                for (Parameter parameter : parameters) {
                    List<Value> dynParamPossibleValues = null;
                    //To process the dynamic parameter
                    if (parameter.getDynamicParameter() != null) {
                        dynParamPossibleValues = setDynamicPossibleValues(watcherMap, parameter);
                        setReqAttribute(REQ_DYNAMIC_POSSIBLE_VALUES + parameter.getKey(), dynParamPossibleValues);
                        //If dependency exist for current parameter then add its dependency and first possible value in watcher map
                        if (StringUtils.isNotEmpty(parameter.getDependency())) {
                            valuesMap.put(parameter.getDependency(), dynParamPossibleValues.get(0).getValue());
                            watcherMap.put(parameter.getKey(), valuesMap);
                            if (parameter.isShow()) {
                                controlsTobeShowed.add(parameter.getDependency());
                            }
                        }
                    }
                    
                    //To update the map with the dynamic parameters value
                    Set<String> keySet = watcherMap.keySet();
                    if (keySet != null) {
                        for (String key : keySet) {
                            if (key.equals(parameter.getKey())) {
                                valuesMap.put(key, dynParamPossibleValues.get(0).getValue());
                                watcherMap.put(parameter.getKey(), valuesMap);
                                break;
                            }
                        }
                    }
                    
                    if (parameter.getType().equalsIgnoreCase(TYPE_BOOLEAN) && StringUtils.isNotEmpty(parameter.getDependency())) {
                        //To add the boolean parameter's dependency and its value into the watcher
                        valuesMap.put(parameter.getDependency(), parameter.getValue());
                        watcherMap.put(parameter.getKey(), valuesMap);
                        //To add the current parameter's dependency into the controls to be showed collection
                        if (parameter.isShow()) {
                            controlsTobeShowed.add(parameter.getDependency());
                        }
                    }
                    
                    //To process the paramters if it has possible values
                    if (parameter.getPossibleValues() != null) {
                        List<Value> values = parameter.getPossibleValues().getValue();
                        //To add the possible values dependency and its key into the watcher
                        if (StringUtils.isNotEmpty(values.get(0).getDependency())) {
                            valuesMap.put(values.get(0).getDependency(), values.get(0).getKey());
                            watcherMap.put(parameter.getKey(), valuesMap);
                            //To add the current parameter's dependency into the controlsTobeShowed collection
                            if (parameter.isShow()) {
                                controlsTobeShowed.add(values.get(0).getDependency());
                            }
                        }
                    }
                    
                    //To change the show property of the parameter based on the controlsTobeShowed collection
                    if (controlsTobeShowed.contains(parameter.getKey())) {
                        parameter.setShow(true);
                    }
                }
            }
        } catch (PhrescoException e) {
            throw new PhrescoException(e);
        }
    }
    
	 /**
	 * To set List of Possible values as Dynamic parameter in request
	 * @param applicationInfo
	 * @param parameters
	 * @throws PhrescoException
	 */
	protected List<Value> setDynamicPossibleValues(Map<String, Object> map, Parameter parameter) throws PhrescoException {
		PossibleValues possibleValue = getDynamicValues(map, parameter);
		List<Value> possibleValues = (List<Value>) possibleValue.getValue();
		return possibleValues;
	}
	
	private PossibleValues getDynamicValues(Map<String, Object> map, Parameter parameter) throws PhrescoException {
		String className = "";
		try {
//			Map<String, PhrescoDynamicLoader> pdlMap = new HashMap<String, PhrescoDynamicLoader>();
//			String customerId = (String) map.get(REQ_CUSTOMER_ID);
			className = parameter.getDynamicParameter().getClazz();
			
			//To get repo info from Customer object
//			ServiceManager serviceManager = getServiceManager();
//			Customer customer = serviceManager.getCustomer(customerId);
//			RepoInfo repoInfo = customer.getRepoInfo();

			//To set groupid,artfid,type infos to List<ArtifactGroup>
//			List<ArtifactGroup> artifactGroups = new ArrayList<ArtifactGroup>();
//			ArtifactGroup artifactGroup = new ArtifactGroup();
//			artifactGroup.setGroupId(parameter.getDynamicParameter().getDependencies().getDependency().getGroupId());
//			artifactGroup.setArtifactId(parameter.getDynamicParameter().getDependencies().getDependency().getArtifactId());
//			artifactGroup.setPackaging(parameter.getDynamicParameter().getDependencies().getDependency().getType());
//
//			//to set version
//			List<ArtifactInfo> artifactInfos = new ArrayList<ArtifactInfo>();
//	        ArtifactInfo artifactInfo = new ArtifactInfo();
//	        artifactInfo.setVersion(parameter.getDynamicParameter().getDependencies().getDependency().getVersion());
//			artifactInfos.add(artifactInfo);
//	        artifactGroup.setVersions(artifactInfos);
//			artifactGroups.add(artifactGroup);
			
			
			//dynamically loads specified Class
//			PhrescoDynamicLoader pdl = new PhrescoDynamicLoader(repoInfo, artifactGroups);
//			DynamicParameter dynamicParameter = pdl.getDynamicParameter(className);
			
			Class<DynamicParameter> loadedClass = (Class<DynamicParameter>) Class.forName(className);
			DynamicParameter dynamicParameter = loadedClass.newInstance();
			
			return dynamicParameter.getValues(map);
		} catch (Exception e) {
		    e.printStackTrace();
			throw new PhrescoException(getText(EXCEPTION_LOAD_CLASS) + className);
		}
		
	}
	
	/**
	 * To persist entered values into phresco-plugin-info.xml
	 */
	 protected void persistValuesToXml(MojoProcessor mojo, String goal) throws PhrescoException {
		List<Parameter> parameters = getMojoParameters(mojo, goal);
		StringBuilder csParamVal = new StringBuilder();
		String sep = "";
		if (CollectionUtils.isNotEmpty(parameters)) {
    		for (Parameter parameter : parameters) {
    			if (Boolean.parseBoolean(parameter.getMultiple())) {
    				String[] parameterValues = getReqParameterValues(parameter.getKey());
    				for (String parameterValue : parameterValues) {
    					csParamVal.append(sep);
    					csParamVal.append(parameterValue);
    					sep = ",";
    				}
    				parameter.setValue(csParamVal.toString());
    			} else if (TYPE_BOOLEAN.equalsIgnoreCase(parameter.getType())){
    				if (getReqParameter(parameter.getKey()) != null) {
    					parameter.setValue(getReqParameter(parameter.getKey()));
    				} else {
    					parameter.setValue(Boolean.FALSE.toString());
    				}
    			} else {
    				parameter.setValue(getReqParameter(parameter.getKey()));
    			}
    		}
		}
		mojo.save();
	}

	/**
	 * To get list of maven command arguments
	 * @param parameters
	 * @return
	 */
	protected List<String> getMavenArgCommands(List<Parameter> parameters) {
		List<String> buildArgCmds = new ArrayList<String>();			
		for (Parameter parameter : parameters) {
			if (parameter.getPluginParameter()!= null && PLUGIN_PARAMETER_FRAMEWORK.equalsIgnoreCase(parameter.getPluginParameter())) {
				List<MavenCommand> mavenCommand = parameter.getMavenCommands().getMavenCommand();
				for (MavenCommand mavenCmd : mavenCommand) {
					if (parameter.getValue().equalsIgnoreCase(mavenCmd.getKey())) {
						buildArgCmds.add(mavenCmd.getValue());
					}
				}
			}
		}
		return buildArgCmds;
	}
	

}
