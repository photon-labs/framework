package com.photon.phresco.framework.rest.api;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

public class UtilServiceTest extends RestBaseTest {
	
	UtilService utilService = new UtilService();
	
	@Test
	public void openFolder() {
		Response openFolderUnit = utilService.openFolder(appDirName, "unitTest");
		Assert.assertEquals(200, openFolderUnit.getStatus());
		Response openFolderFunc = utilService.openFolder(appDirName, "functionalTest");
		Assert.assertEquals(200, openFolderFunc.getStatus());
		Response openFolderComp = utilService.openFolder(appDirName, "componentTest");
		Assert.assertEquals(200, openFolderComp.getStatus());
		Response openFolderLoad = utilService.openFolder(appDirName, "loadTest");
		Assert.assertEquals(200, openFolderLoad.getStatus());
		Response openFolderManual = utilService.openFolder(appDirName, "manualTest");
		Assert.assertEquals(200, openFolderManual.getStatus());
		Response openFolderPerform = utilService.openFolder(appDirName, "performanceTest");
		Assert.assertEquals(200, openFolderPerform.getStatus());
		Response openFolderBuild = utilService.openFolder(appDirName, "build");
		Assert.assertEquals(200, openFolderBuild.getStatus());
		Response openFolderDirFail = utilService.openFolder(appDirName, null);
		Assert.assertEquals(200, openFolderDirFail.getStatus());
		Response openFolderFail = utilService.openFolder("", "");
		Assert.assertEquals(200, openFolderFail.getStatus());
		
	}

	@Test
	public void copyPath() {
		Response openFolderUnit = utilService.copyPath(appDirName, "unitTest");
		Assert.assertEquals(200, openFolderUnit.getStatus());
		Response openFolderFunc = utilService.copyPath(appDirName, "functionalTest");
		Assert.assertEquals(200, openFolderFunc.getStatus());
		Response openFolderComp = utilService.copyPath(appDirName, "componentTest");
		Assert.assertEquals(200, openFolderComp.getStatus());
		Response openFolderLoad = utilService.copyPath(appDirName, "loadTest");
		Assert.assertEquals(200, openFolderLoad.getStatus());
		Response openFolderManual = utilService.copyPath(appDirName, "manualTest");
		Assert.assertEquals(200, openFolderManual.getStatus());
		Response openFolderPerform = utilService.copyPath(appDirName, "performanceTest");
		Assert.assertEquals(200, openFolderPerform.getStatus());
		Response openFolderBuild = utilService.copyPath(appDirName, "build");
		Assert.assertEquals(200, openFolderBuild.getStatus());
		Response openFolderDirFail = utilService.copyPath(appDirName, null);
		Assert.assertEquals(200, openFolderDirFail.getStatus());
		Response openFolderFail = utilService.copyPath("", "");
		Assert.assertEquals(200, openFolderFail.getStatus());
		
	}
	
	@Test
	public void copyLogToClipboard() {
		Response copyLogToClipboard = utilService.copyLogToClipboard("[INFO] BUILD SUCCESS");
		Assert.assertEquals(200, copyLogToClipboard.getStatus());
		Response copyLogToClipboardFail = utilService.copyLogToClipboard(null);
		Assert.assertEquals(200, copyLogToClipboardFail.getStatus());
	}
	
	@Test
	public void getTecnologyOptions() {
		Response copyLogToClipboard = utilService.getTecnologyOptions(userId, techId);
		Assert.assertEquals(200, copyLogToClipboard.getStatus());
		Response copyLogToClipboardLoginFail = utilService.getTecnologyOptions("sample", techId);
		Assert.assertEquals(200, copyLogToClipboardLoginFail.getStatus());
//		Response copyLogToClipboardFail = utilService.getTecnologyOptions(userId, "");
//		Assert.assertEquals(400, copyLogToClipboardFail.getStatus());
	}
}
