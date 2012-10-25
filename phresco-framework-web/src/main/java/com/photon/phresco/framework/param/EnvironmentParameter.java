package com.photon.phresco.framework.param;

import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.MapUtils;
import org.xml.sax.SAXException;

import com.photon.phresco.commons.api.ConfigManager;
import com.photon.phresco.commons.impl.ConfigManagerImpl;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.param.api.DynamicParameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.photon.phresco.framework.actions.FrameworkBaseAction;

@SuppressWarnings("serial")
public class EnvironmentParameter extends FrameworkBaseAction implements DynamicParameter, Constants {
	@Override
	public PossibleValues getValues(Map<String, Object> map) throws IOException, ParserConfigurationException, SAXException, ConfigurationException {
    	PossibleValues possibleValues = new PossibleValues();
    	ApplicationInfo applicationInfo = (ApplicationInfo) map.get(KEY_APP_INFO);
    	
    	if (MapUtils.isNotEmpty(map)) {
    		String dependancyValue = (String) map.get(KEY_SETTINGS_ENV);
        	if (Boolean.parseBoolean(dependancyValue)) {
            	String settingsPath = getSettingsPath();
            	ConfigManager configManager = new ConfigManagerImpl(new File(settingsPath)); 
            	List<Environment> environments = configManager.getEnvironments();
            	for (Environment environment : environments) {
            		Value value = new Value();
            		value.setValue(environment.getName());
            		possibleValues.getValue().add(value);
        		}
        	}
    	}
    	
    	String projectDirectory = applicationInfo.getAppDirName();
    	String configPath = getConfigurationPath(projectDirectory).toString();
    	ConfigManager configManager = new ConfigManagerImpl(new File(configPath)); 
    	List<Environment> environments = configManager.getEnvironments();
    	for (Environment environment : environments) {
    		Value value = new Value();
    		value.setValue(environment.getName());
    		possibleValues.getValue().add(value);
		}
    	setSessionAttribute(REQ_SESSION_DYNAMIC_PARAM_MAP, map);
    	
    	return possibleValues;
    }
    
    private StringBuilder getConfigurationPath(String projectDirectory) {
		 StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		 builder.append(projectDirectory);
		 builder.append(File.separator);
		 builder.append(DOT_PHRESCO_FOLDER);
		 builder.append(File.separator);
		 builder.append(CONFIGURATION_INFO_FILE);
		 
		 return builder;
	 }
}
