package com.photon.phresco.framework.rest.api;

import java.util.List;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

import com.photon.phresco.commons.model.BuildInfo;

public class BuildInfoTest {

	BuildInfoService buildInfoService = new BuildInfoService();
	
	@Test
	public void  listBuilds() {
		String appDirName = "xxxtest2java1-javawebservice1.6";
		Response buildInfoList = buildInfoService.list(appDirName);
		ResponseInfo<List<BuildInfo>> entity = (ResponseInfo<List<BuildInfo>>) buildInfoList.getEntity();
		Assert.assertEquals(2, entity.getData().size());
	}

	@Test
	public void  deleteBuild() {
		String projectId = "a2f50f3c-69c6-46c0-9270-730ee8977279";
		String appId = "023ab93e-c217-485f-8f1d-28827d0a66e5";
		String customerId = "photon";
		String[] buildnums = {"1"};
		Response buildInfoList = buildInfoService.deleteBuild(buildnums, projectId, customerId, appId);
		Assert.assertEquals(200, buildInfoList.getStatus());
	}
	
	@Test
	public void  downloadBuild() {
		String appDirName = "xxxtest2java1-javawebservice1.6";
		int buildnum = 3;
		Response buildInfoList = buildInfoService.buildInfoZip(appDirName, buildnum);
		Assert.assertEquals(200, buildInfoList.getStatus());
	}
}
