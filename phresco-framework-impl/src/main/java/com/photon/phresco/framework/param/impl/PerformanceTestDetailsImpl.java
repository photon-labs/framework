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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.photon.phresco.api.DynamicPageParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.model.PerformanceDetails;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class PerformanceTestDetailsImpl implements DynamicPageParameter, Constants {
    
	private static final String VALUES_FROM_JSON = "valuesFromJson";

	@Override
    public Map<String, Object> getObjects(Map<String, Object> paramsMap) throws PhrescoException {
		 Reader read = null;
        try {
        	Map<String, Object> resultMap = new HashMap<String, Object>();
            ApplicationInfo applicationInfo = (ApplicationInfo) paramsMap.get(KEY_APP_INFO);
            String testAgainst = (String) paramsMap.get(KEY_TEST_AGAINST);
            String testResultName = (String) paramsMap.get(KEY_TEST_RESULT_NAME);
            boolean isMultiModule = (Boolean) paramsMap.get(KEY_MULTI_MODULE);
        	String rootModule = (String) paramsMap.get(KEY_ROOT_MODULE);
            String testResultJsonFile = testResultJsonFile(applicationInfo, testAgainst, testResultName, isMultiModule, rootModule);
            Gson gson = new Gson();
            File file = new File(testResultJsonFile);
            List<PerformanceDetails> performanceDetails = new ArrayList<PerformanceDetails>();
            PerformanceDetails performanceDetail = new PerformanceDetails();
            if (file.exists()) {
            	read = new InputStreamReader(new FileInputStream(testResultJsonFile));
            	performanceDetail = gson.fromJson(read, PerformanceDetails.class);
            	if (performanceDetail != null) {
            		performanceDetails.add(performanceDetail);
            	}
            }
            resultMap.put("className", performanceDetail.getClass().getName());
            resultMap.put(VALUES_FROM_JSON, performanceDetails);

            return resultMap;
		 } catch(Exception e){
		 } finally {
			 if (read != null) {
				 try {
					read.close();
				} catch (IOException e) {
				}
			 }
		 }

		 return null;
    }
    
    private String testResultJsonFile(ApplicationInfo appInfo, String testAgainst, String testResultName, boolean isMultiModule, String rootModule) throws PhrescoPomException {
        StringBuilder builder = new StringBuilder(Utility.getProjectHome());
        if (isMultiModule) {
        	builder.append(rootModule + File.separator);
        }
        builder.append(appInfo.getAppDirName());
        PomProcessor processor = new PomProcessor(getPOMFile(appInfo, isMultiModule, rootModule));
        String performDir = processor.getProperty(POM_PROP_KEY_PERFORMANCETEST_DIR);
        builder.append(performDir);
        builder.append(File.separator);
        builder.append(testAgainst.toLowerCase());
        builder.append(File.separator);
        builder.append(FOLDER_JSON);
        builder.append(File.separator);
        builder.append(testResultName + DOT_JSON);
        
        return builder.toString();
    }
    
    private File getPOMFile(ApplicationInfo appInfo, boolean isMultiModule, String rootModule) {
        StringBuilder builder = new StringBuilder(Utility.getProjectHome());
        if (isMultiModule) {
        	builder.append(rootModule + File.separator);
        }
        builder.append(appInfo.getAppDirName());
        String pomFile = Utility.getPhrescoPomFromWorkingDirectory(appInfo, new File(builder.toString()));
        builder.append(File.separatorChar)
        .append(pomFile);
        
        return new File(builder.toString());
    }
}