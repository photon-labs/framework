package com.photon.phresco.framework.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.MavenCommands.MavenCommand;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.plugins.util.MojoProcessor;

public class DynamicParameterUtil extends FrameworkBaseAction {

	private static final long serialVersionUID = 1L;
	
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
    
	
	 /**
	 * To set List of Possible values as Dynamic parameter in request
	 * @param applicationInfo
	 * @param parameters
	 * @throws PhrescoException
	 */
	protected List<Value> setDynamicPossibleValues(Map<String, Object> map, Parameter parameter) throws PhrescoException {
		PossibleValues possibleValue = getDynamicValues(map, parameter.getDynamicParameter().getClazz());
		List<Value> possibleValues = (List<Value>) possibleValue.getValue();
		
		return possibleValues;
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * To dynamically load class
	 */
	private PossibleValues getDynamicValues(Map<String, Object> map, String className) throws PhrescoException {
		try {
			Class<DynamicParameter> loadedClass = (Class<DynamicParameter>) Class.forName(className);
			DynamicParameter dynamicParameter = loadedClass.newInstance();
			return dynamicParameter.getValues(map);
		} catch (Exception e) {
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
