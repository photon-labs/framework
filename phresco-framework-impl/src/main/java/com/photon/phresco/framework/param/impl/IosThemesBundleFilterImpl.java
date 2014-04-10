/**
 * Phresco Framework Implementation
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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
package com.photon.phresco.framework.param.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class IosThemesBundleFilterImpl implements DynamicParameter, Constants {

	private static final long serialVersionUID = 1L;

	@Override
	public PossibleValues getValues(Map<String, Object> paramMap) throws PhrescoException {
		String rootModulePath = "";
		String subModuleName = "";
		PossibleValues possibleValues = new PossibleValues();
    	ApplicationInfo applicationInfo = (ApplicationInfo) paramMap.get(KEY_APP_INFO);
    	 String rootModule = (String) paramMap.get(KEY_ROOT_MODULE);
    	 if (StringUtils.isNotEmpty(rootModule)) {
 			rootModulePath = Utility.getProjectHome() + rootModule;
 			subModuleName = applicationInfo.getAppDirName();
 		} else {
 			rootModulePath = Utility.getProjectHome() + applicationInfo.getAppDirName();
 		}
    	 
    	File file = new File(getResourcesPath(rootModulePath, subModuleName).toString());
    	if (file.exists()) {
    	    File[] listFiles = file.listFiles(new BundleFileNameFilter(".bundle"));
    	    if (!ArrayUtils.isEmpty(listFiles)) {
    	        for (File listFile : listFiles) {
    	            Value value = new Value();
    	            value.setValue(listFile.getName());
    	            possibleValues.getValue().add(value);
                }
    	    }
    	}
    	
    	return possibleValues;
    }
	
	private StringBuilder getResourcesPath(String rootModulePath, String subModuleName) throws PhrescoException {
		ProjectInfo info = Utility.getProjectInfo(rootModulePath, subModuleName);
		File srcFolderLocation = Utility.getSourceFolderLocation(info, rootModulePath, subModuleName);
        StringBuilder builder = new StringBuilder(srcFolderLocation.toString());
        builder.append(File.separator);
        builder.append("source");
        builder.append(File.separator);
        builder.append("Resources");
        
        return builder;
    }
	
	private class BundleFileNameFilter implements FilenameFilter {
	    private String filter_;
	    public BundleFileNameFilter(String filter) {
	        filter_ = filter;
	    }
	    public boolean accept(File dir, String name) {
	        return name.endsWith(filter_);
	    }
	}
}