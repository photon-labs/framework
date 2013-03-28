/**
 * Phresco Framework Implementation
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
package com.photon.phresco.framework.param.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class IosThemesBundleFilterImpl implements DynamicParameter, Constants {

	private static final long serialVersionUID = 1L;

	@Override
	public PossibleValues getValues(Map<String, Object> paramMap) {
		PossibleValues possibleValues = new PossibleValues();
    	ApplicationInfo applicationInfo = (ApplicationInfo) paramMap.get(KEY_APP_INFO);
    	File file = new File(getResourcesPath(applicationInfo.getAppDirName()).toString());
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
	
	private StringBuilder getResourcesPath(String projectDirectory) {
        StringBuilder builder = new StringBuilder(Utility.getProjectHome());
        builder.append(projectDirectory);
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