package com.photon.phresco.framework.rest.api.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class FrameworkServiceUtil implements Constants {
	
	public static ApplicationInfo getApplivationInfo(String appDirName) throws PhrescoException {
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

}
