package com.photon.phresco.framework.param.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class Html5ThemesListImpl implements DynamicParameter, Constants {

	private static final long serialVersionUID = 1L;

	@Override
	public PossibleValues getValues(Map<String, Object> paramMap) throws PhrescoException {
	    try {
	        PossibleValues possibleValues = new PossibleValues();
	        ApplicationInfo applicationInfo = (ApplicationInfo) paramMap.get(KEY_APP_INFO);
	        String csvParam = (String) paramMap.get("themes");
	        List<String> selectedThemes = new ArrayList<String>();
	        if (StringUtils.isNotEmpty(csvParam)) {
	            selectedThemes = Arrays.asList(csvParam.split(","));
	        }
	        File file = new File(getResourcesPath(applicationInfo.getAppDirName()).toString());
	        if (file.exists()) {
	            File[] listFiles = file.listFiles();
	            if (!ArrayUtils.isEmpty(listFiles)) {
	                for (File listFile : listFiles) {
	                    if (CollectionUtils.isNotEmpty(selectedThemes) && selectedThemes.contains(listFile.getName())) {
	                        Value value = new Value();
	                        value.setValue(listFile.getName());
	                        possibleValues.getValue().add(value);
	                    } else if (CollectionUtils.isEmpty(selectedThemes)) {
	                        Value value = new Value();
	                        value.setValue(listFile.getName());
	                        value.setDependency("deafultTheme");
	                        possibleValues.getValue().add(value);
	                    }
	                }
	            }
	        }
	        return possibleValues;
	    } catch (Exception e) {
	        throw new PhrescoException(e);
	    }
	}
	
	private StringBuilder getResourcesPath(String projectDirectory) {
        StringBuilder builder = new StringBuilder(Utility.getProjectHome());
        builder.append(projectDirectory);
        builder.append(File.separator);
        builder.append("src");
        builder.append(File.separator);
        builder.append("main");
        builder.append(File.separator);
        builder.append("webapp");
        builder.append(File.separator);
        builder.append("themes");
        
        return builder;
    }
}