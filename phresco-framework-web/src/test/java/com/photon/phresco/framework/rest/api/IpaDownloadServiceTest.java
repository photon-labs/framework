package com.photon.phresco.framework.rest.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.model.BuildInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;

public class IpaDownloadServiceTest {

	@Test
	public void testIpaDownloadService() throws PhrescoException, IOException {
		String osType = System.getProperty("os.name");
		if (isMac(osType)) {
			IpaDownloadService downloadService = new IpaDownloadService();
			String appDirectoryPath = FrameworkServiceUtil.getApplicationHome("PHONE-iphonenative");
			String buildDirectoryPath = FrameworkServiceUtil.getBuildInfosFilePath("PHONE-iphonenative");
			int buildNo = getBuildNo(buildDirectoryPath, "PHONE-iphonenative");
			File appDir = new File(appDirectoryPath);
			File buildDir = new File(buildDirectoryPath);
			Response response = null;
			if (appDir.exists() && buildDir.exists() && buildNo == 1) {
				response = downloadService.downloadIpa("PHONE-iphonenative", String.valueOf(buildNo), "");
			}
			Assert.assertEquals(200, response.getStatus());
		}
	}

	public static boolean isMac(String os) {
		return (os.indexOf("Mac") >= 0) ||(os.indexOf("mac") >= 0)  ;
	}
	
	public int getBuildNo(String buildFilePath, String appdir) throws PhrescoException {
		int buildNo = 0;
		try {
			Gson gson = new Gson();
			File buildInfoPath = new File(buildFilePath);
			if (buildInfoPath.exists()) {
				BufferedReader reader = new BufferedReader(new FileReader(buildInfoPath));
				Type type = new TypeToken<List<BuildInfo>>() {}  .getType();
				List<BuildInfo> buildInfos = (List<BuildInfo>)gson.fromJson(reader, type);
				if (CollectionUtils.isNotEmpty(buildInfos)) {
					for (BuildInfo buildInfo : buildInfos) {
						buildNo = buildInfo.getBuildNo();
					}
				}
			}
			return buildNo;
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		}
	}
	
}
