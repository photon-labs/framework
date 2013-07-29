package com.photon.phresco.framework.param.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Model.Modules;
import com.phresco.pom.util.PomProcessor;

public class WarProjectModuleImpl implements DynamicParameter, Constants {

	@Override
	public PossibleValues getValues(Map<String, Object> paramMap)
			throws IOException, ParserConfigurationException, SAXException,
			ConfigurationException, PhrescoException {
		PossibleValues possibleValues = new PossibleValues();
		ApplicationInfo applicationInfo = (ApplicationInfo) paramMap.get(KEY_APP_INFO);
		List<String> warProjectModules = getWarProjectModules(applicationInfo.getAppDirName());
		if(warProjectModules != null) {
			for (String module : warProjectModules) {
				Value value = new Value();
				value.setValue(module);
				possibleValues.getValue().add(value);
			}
			return possibleValues;
		}
		return null;
	}

	private StringBuilder getPomPath(String appDirName) {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		 builder.append(appDirName);
		 builder.append(File.separator);
		 builder.append(POM_NAME);
		return builder;
	}
	
	protected List<String> getProjectModules(String appDirName) throws PhrescoException {
    	try {
    		 StringBuilder builder = getPomPath(appDirName);
     		File pomPath = new File(builder.toString());
     		if(pomPath.exists()) {
	     		PomProcessor processor = new PomProcessor(pomPath);
	    		Modules pomModule = processor.getPomModule();
	    		List<String> moduleList = new ArrayList<String>();
	    		if (pomModule != null) {
	    			List<String> modules = pomModule.getModule();
	    			for (String module : modules) {
						String[] split = module.split("/");
						if(split != null) {
							module = split[0];
						}
						moduleList.add(module);
					}
	    			return moduleList;
	    		}
     		}
    	} catch (PhrescoPomException e) {
    		 throw new PhrescoException(e);
    	}
    	return null;
    }
	
	private StringBuilder getProjectHome(String appDirName) {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		builder.append(appDirName);
		return builder;
	}
	
	protected List<String> getWarProjectModules(String appDirName) throws PhrescoException {
    	try {
			List<String> projectModules = getProjectModules(appDirName);
			List<String> warModules = new ArrayList<String>(5);
			if (CollectionUtils.isNotEmpty(projectModules)) {
				for (String projectModule : projectModules) {
					StringBuilder pathBuilder = getProjectHome(appDirName);
					pathBuilder.append(File.separatorChar);
					pathBuilder.append(projectModule);
					pathBuilder.append(File.separatorChar);
					pathBuilder.append(POM_NAME);
					PomProcessor processor = new PomProcessor(new File(pathBuilder.toString()));
					String packaging = processor.getModel().getPackaging();
					if (StringUtils.isNotEmpty(packaging) && FrameworkConstants.WAR.equalsIgnoreCase(packaging)) {
						warModules.add(projectModule);
					}
				}
			}
			return warModules;
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
    }
}
