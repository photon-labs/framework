package com.photon.phresco.framework.rest.api;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

public class AppInfoConfigsTest extends RestBaseTest {
	AppInfoConfigs appInfoConfigs = new AppInfoConfigs();
	public AppInfoConfigsTest() {
		super();
	}
	
	@Test
	public void fetchDownloadInfos() {
		Response downloadInfosLoginFail = appInfoConfigs.getDownloadInfos("photon", "tech-java-webservice", "SERVER", "Windows64", "sample");
		Assert.assertEquals(200, downloadInfosLoginFail.getStatus());
		Response downloadInfos = appInfoConfigs.getDownloadInfos("photon", "tech-java-webservice", "SERVER", "Windows64", userId);
		Assert.assertEquals(200, downloadInfos.getStatus());
//		Response downloadInfosFail = appInfoConfigs.getDownloadInfos("photon", "", "", "", "admin");
//		ResponseInfo<List<DownloadInfo>> d = (ResponseInfo<List<DownloadInfo>>)downloadInfosFail.getEntity();
//		System.out.println(d.getData());
//		Assert.assertEquals(400, downloadInfosFail.getStatus());
	}
	
	@Test
	public void fetchWebServices() {
		Response webServicesLoginFail = appInfoConfigs.getWebServices("sample");
		Assert.assertEquals(200, webServicesLoginFail.getStatus());
		Response webServices = appInfoConfigs.getWebServices(userId);
		Assert.assertEquals(200, webServices.getStatus());
	}
	
	@Test
	public void testGetFunctionalFrameworks() {
		appInfoConfigs.getFunctionalFrameworks(userId, "tech-php");
	}
	
}
