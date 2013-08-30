package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.easymock.EasyMock;
import org.junit.Test;


import com.photon.phresco.commons.model.VersionInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.api.UpgradeManager;
import com.photon.phresco.service.client.api.ServiceManager;


public class UpgradeServiceTest extends RestBaseTest {
	UpgradeService upgradeservice = new UpgradeService();
	
	@Test
	public void updateExistsCheckFailure() {
		Response updateExistsCheck = upgradeservice.updateExistsCheck("sample");
		assertEquals(200, updateExistsCheck.getStatus());
	}

	@Test
	public void updateExistsCheck() throws PhrescoException {
		VersionInfo versioninfo = new VersionInfo();
		versioninfo.setMessage("update Available");
		versioninfo.setFrameworkVersion("3.0.0.23002");
		versioninfo.setUpdateAvailable(true);
		UpgradeManager up = EasyMock.createNiceMock(UpgradeManager.class);
		EasyMock.expect(up.getCurrentVersion()).andReturn("3.0.0.23000");
		EasyMock.expect(up.checkForUpdate(EasyMock.isA(ServiceManager.class), EasyMock.anyString())).andReturn(new VersionInfo());					
		EasyMock.replay(up);
		Response updateExistsCheck = upgradeservice.updateExistsCheck(userId);
		assertEquals(200, updateExistsCheck.getStatus());
	}
	
	@Test
	public void doUpdateFailure() {
		Response updateExistsCheck = upgradeservice.doUpdate("", "sample", "photon");
		assertEquals(200, updateExistsCheck.getStatus());
	}

	@Test
	public void doUpdate() throws PhrescoException {
		Response doUpdate = upgradeservice.doUpdate("3.0.0.23002", userId, customerId);
		assertEquals(200, doUpdate.getStatus());
	}
	
}
