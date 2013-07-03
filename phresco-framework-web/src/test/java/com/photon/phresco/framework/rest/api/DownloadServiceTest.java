package com.photon.phresco.framework.rest.api;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

public class DownloadServiceTest extends RestBaseTest {
	DownloadService downloadService = new DownloadService();
	

	@Test
	public void getDownloadsList() {
		Response loginFail = downloadService.list(customerId, "sample");
		Assert.assertEquals(400, loginFail.getStatus());
		Response response = downloadService.list(customerId, userId);
		Assert.assertEquals(200, response.getStatus());
	}
	

	/*@Test
	public void getDownloadsListFailure() {
		Response response = downloadService.list("", userId);
		Assert.assertEquals(400, response.getStatus());
	}*/
}
