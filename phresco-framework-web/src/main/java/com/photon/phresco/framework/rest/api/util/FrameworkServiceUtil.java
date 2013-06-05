package com.photon.phresco.framework.rest.api.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Model.Modules;
import com.phresco.pom.util.PomProcessor;

public class FrameworkServiceUtil implements Constants, FrameworkConstants {
	
	/**
	 * To get the application info of the given appDirName
	 * @param appDirName
	 * @return
	 * @throws PhrescoException
	 */
	public static ApplicationInfo getApplicationInfo(String appDirName) throws PhrescoException {
		StringBuilder builder  = new StringBuilder();
		builder.append(Utility.getProjectHome())
		.append(appDirName)
		.append(File.separatorChar)
		.append(DOT_PHRESCO_FOLDER)
		.append(File.separatorChar)
		.append(PROJECT_INFO_FILE);
		ApplicationInfo applicationInfo;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(builder.toString()));
			Gson gson = new Gson();
			ProjectInfo projectInfo = gson.fromJson(bufferedReader, ProjectInfo.class);
			applicationInfo = projectInfo.getAppInfos().get(0);
			return applicationInfo;
		} catch (JsonSyntaxException e) {
			throw new PhrescoException(e);
		} catch (JsonIOException e) {
			throw new PhrescoException(e);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		}
	}
	
	/**
	 * To get the PomProcessor instance for the given application
	 * @param appDirName
	 * @return
	 * @throws PhrescoException
	 */
	public static PomProcessor getPomProcessor(String appDirName) throws PhrescoException {
		try {
			StringBuilder builder  = new StringBuilder();
			builder.append(Utility.getProjectHome())
			.append(appDirName)
			.append(File.separatorChar)
			.append(POM_NAME);
			return new PomProcessor(new File(builder.toString()));
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * To get the application home for the given appDirName
	 * @param appDirName
	 * @return
	 * @throws PhrescoException
	 */
	public static String getApplicationHome(String appDirName) throws PhrescoException {
        StringBuilder builder = new StringBuilder(Utility.getProjectHome());
        builder.append(appDirName);
        return builder.toString();
	}

	/**
	 * To get the modules of the given application
	 * @param appDirName
	 * @return
	 * @throws PhrescoException
	 */
	public static List<String> getProjectModules(String appDirName) throws PhrescoException {
    	try {
            PomProcessor processor = getPomProcessor(appDirName);
    		Modules pomModule = processor.getPomModule();
    		if (pomModule != null) {
    			return pomModule.getModule();
    		}
    	} catch (PhrescoPomException e) {
    		 throw new PhrescoException(e);
    	}
    	
    	return null;
    }
	
	/**
	 * To get the war project modules of the given application
	 * @param appDirName
	 * @return
	 * @throws PhrescoException
	 */
	public static List<String> getWarProjectModules(String appDirName) throws PhrescoException {
    	try {
			List<String> projectModules = getProjectModules(appDirName);
			List<String> warModules = new ArrayList<String>(5);
			if (CollectionUtils.isNotEmpty(projectModules)) {
				for (String projectModule : projectModules) {
					PomProcessor processor = getPomProcessor(appDirName);
					String packaging = processor.getModel().getPackaging();
					if (StringUtils.isNotEmpty(packaging) && WAR.equalsIgnoreCase(packaging)) {
						warModules.add(projectModule);
					}
				}
			}
			return warModules;
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
    }
	
	/**
	 * To ge the unit test directory
	 * @param appDirName
	 * @return
	 * @throws PhrescoException
	 * @throws PhrescoPomException
	 */
	public static String getUnitTestDir(String appDirName) throws PhrescoException, PhrescoPomException {
        return getPomProcessor(appDirName).getProperty(POM_PROP_KEY_UNITTEST_DIR);
    }
	
	/**
	 * To get the functional test directory
	 * @param appDirName
	 * @return
	 * @throws PhrescoException
	 * @throws PhrescoPomException
	 */
	public static String getFunctionalTestDir(String appDirName) throws PhrescoException, PhrescoPomException {
		return getPomProcessor(appDirName).getProperty(POM_PROP_KEY_FUNCTEST_DIR);
	}
	
	/**
	 * To get the component test directory
	 * @param appinfo
	 * @return
	 * @throws PhrescoException
	 * @throws PhrescoPomException
	 */
	public static String getComponentTestDir(String appDirName) throws PhrescoException, PhrescoPomException {
        return getPomProcessor(appDirName).getProperty(POM_PROP_KEY_COMPONENTTEST_DIR);
    }
	
	/**
	 * To get the load test directory
	 * @param appDirName
	 * @return
	 * @throws PhrescoException
	 * @throws PhrescoPomException
	 */
	public static String getLoadTestDir(String appDirName) throws PhrescoException, PhrescoPomException {
    	return getPomProcessor(appDirName).getProperty(POM_PROP_KEY_LOADTEST_DIR);
    }
	
	/**
	 * To get the performance test directory
	 * @param appDirName
	 * @return
	 * @throws PhrescoException
	 * @throws PhrescoPomException
	 */
	public static String getPerformanceTestDir(String appDirName) throws PhrescoException, PhrescoPomException {
        return getPomProcessor(appDirName).getProperty(POM_PROP_KEY_PERFORMANCETEST_DIR);
    }
	
	/**
	 * To get the manual test directory
	 * @param appDirName
	 * @return
	 * @throws PhrescoException
	 * @throws PhrescoPomException
	 */
	public static String getManualTestDir(String appDirName) throws PhrescoException, PhrescoPomException {
        return getPomProcessor(appDirName).getProperty(POM_PROP_KEY_MANUALTEST_RPT_DIR);
    }
	
	/**
	 * To get the build test directory
	 * @param appDirName
	 * @return
	 * @throws PhrescoException
	 */
	public static String getBuildDir(String appDirName) throws PhrescoException {
		StringBuilder builder = new StringBuilder(getApplicationHome(appDirName))
		.append(File.separator)
		.append(BUILD_DIR);
        return builder.toString();
    }
}