package com.photon.phresco.framework.rest.api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.model.LockDetail;
import com.photon.phresco.util.Utility;
public class UtilServiceTest extends RestBaseTest {
	
	UtilService utilService = new UtilService();
	static StringBuilder sb = new StringBuilder(Utility.getPhrescoHome()).append(File.separator).append("workspace").append(
			File.separator).append("process.lock");
	static File processFile = new File(sb.toString());
	
	static StringBuilder sbs = new StringBuilder(Utility.getProjectHome()).append(File.separator).append("TestProject").append(
			File.separator).append("do_not_checkin").append(File.separator).append("process.json");
	static File processjson = new File(sbs.toString());
	
	
	@BeforeClass
	public static void setUp() {
		BufferedWriter out = null;
		FileWriter fstream = null;
		try {
			LockDetail lock = new LockDetail("TestProject", "update", "admin_user", "4321");
			processFile.createNewFile();
			Gson gson = new Gson();
			String infoJSON = gson.toJson(Arrays.asList(lock));
			fstream = new FileWriter(processFile);
			out = new BufferedWriter(fstream);
			out.write(infoJSON);
			
			JSONObject jsonObject = new JSONObject();
			JSONParser parser = new JSONParser();
			if(processjson.exists()) {
			FileReader reader = new FileReader(processjson);
			jsonObject = (JSONObject) parser.parse(reader);
			}
			jsonObject.put("build", "8765");
			FileWriter writer = new FileWriter(processjson);
			writer.write(jsonObject.toString());
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (fstream != null) {
					fstream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Test
	public void openFolder() {
		Response openFolderUnit = utilService.openFolder(appDirName, "unitTest", "");
		Assert.assertEquals(200, openFolderUnit.getStatus());
		Response openFolderFunc = utilService.openFolder(appDirName, "functionalTest", "");
		Assert.assertEquals(200, openFolderFunc.getStatus());
		Response openFolderComp = utilService.openFolder(appDirName, "componentTest", "");
		Assert.assertEquals(200, openFolderComp.getStatus());
		Response openFolderLoad = utilService.openFolder(appDirName, "loadTest", "");
		Assert.assertEquals(200, openFolderLoad.getStatus());
		Response openFolderManual = utilService.openFolder(appDirName, "manualTest", "");
		Assert.assertEquals(200, openFolderManual.getStatus());
		Response openFolderPerform = utilService.openFolder(appDirName, "performanceTest", "");
		Assert.assertEquals(200, openFolderPerform.getStatus());
		Response openFolderBuild = utilService.openFolder(appDirName, "build", "");
		Assert.assertEquals(200, openFolderBuild.getStatus());
		Response openFolderDirFail = utilService.openFolder(appDirName, null, "");
		Assert.assertEquals(200, openFolderDirFail.getStatus());
		Response openFolderFail = utilService.openFolder(appDirName, "", "");
		Assert.assertEquals(200, openFolderFail.getStatus());
		
	}

	@Test
	public void copyPath() {
		Response openFolderUnit = utilService.copyPath(appDirName, "unitTest", "");
		Assert.assertEquals(200, openFolderUnit.getStatus());
		Response openFolderFunc = utilService.copyPath(appDirName, "functionalTest", "");
		Assert.assertEquals(200, openFolderFunc.getStatus());
		Response openFolderComp = utilService.copyPath(appDirName, "componentTest", "");
		Assert.assertEquals(200, openFolderComp.getStatus());
		Response openFolderLoad = utilService.copyPath(appDirName, "loadTest", "");
		Assert.assertEquals(200, openFolderLoad.getStatus());
		Response openFolderManual = utilService.copyPath(appDirName, "manualTest", "");
		Assert.assertEquals(200, openFolderManual.getStatus());
		Response openFolderPerform = utilService.copyPath(appDirName, "performanceTest", "");
		Assert.assertEquals(200, openFolderPerform.getStatus());
		Response openFolderBuild = utilService.copyPath(appDirName, "build", "");
		Assert.assertEquals(200, openFolderBuild.getStatus());
		Response openFolderDirFail = utilService.copyPath(appDirName, null, "");
		Assert.assertEquals(200, openFolderDirFail.getStatus());
		Response openFolderFail = utilService.copyPath(appDirName, "", "");
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
	
	@Test
	public void checkLockTest() throws PhrescoException {
		
		Response checkForLock = utilService.checkForLock("code", "TestProject1", "");
		Assert.assertEquals(200, checkForLock.getStatus());
		Response checkForLock2 = utilService.checkForLock("build", "TestProject1", "");
		Assert.assertEquals(200, checkForLock2.getStatus());
		Response checkForLock3 = utilService.checkForLock("Start", "TestProject1", "");
		Assert.assertEquals(200, checkForLock3.getStatus());
		Response checkForLock4 = utilService.checkForLock("unit", "TestProject1", "");
		Assert.assertEquals(200, checkForLock4.getStatus());
		Response checkForLock5 = utilService.checkForLock("Deploy", "TestProject1", "");
		Assert.assertEquals(200, checkForLock5.getStatus());
		Response checkForLock6 = utilService.checkForLock("addToRepo", "TestProject1", "");
		Assert.assertEquals(200, checkForLock6.getStatus());
		Response checkForLock7 = utilService.checkForLock("commit", "TestProject1", "");
		Assert.assertEquals(200, checkForLock7.getStatus());
		
		Response checkForLock8 = utilService.checkForLock("create", "TestProject", "");
		Assert.assertEquals(200, checkForLock8.getStatus());
		
		Response checkForLock9 = utilService.checkForLock("update", "TestProject", "");
		Assert.assertEquals(200, checkForLock9.getStatus());
		processFile.delete();
		
	}
	
	@Test
	public void killprocessTest() {
		Response killProcess = utilService.killProcess("create", appDirName);
		Assert.assertEquals(200, killProcess.getStatus());
	}
	
	@Test
	public void killprocess() {
		Response killProcess = utilService.killProcess("build", appDirName);
		Assert.assertEquals(200, killProcess.getStatus());
	}

	@Test
	public void checkLockException() {
		 Response checkForLock = utilService.checkForLock("build", "TestProject", "");
		 Assert.assertEquals(200, checkForLock.getStatus());
	}
	
	@Test
	public void checkValidation() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("buildName", "sample");
		request.setParameter("environmentName", "Production");
		request.setParameter("logs", "showErrors");
		request.setParameter("skipTest", "true");
		request.setParameter("customerId", customerId);
		request.setParameter("projectId", "TestProject");
		request.setParameter("appId", "TestProject");
		request.setParameter("username", userId);
		request.setParameter("buildNumber", "");
		request.setParameter("testBasis", "parameters");
		request.setParameter("appDirName", "TestProject");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Response checkMandatory = utilService.checkMandatoryValidation(httpServletRequest, appDirName, "package", customerId, "");
		Assert.assertEquals(200, checkMandatory.getStatus());
		Response checkMandatoryValidation = utilService.checkMandatoryValidation(httpServletRequest, appDirName, "performance-test", customerId, "");
		Assert.assertEquals(200, checkMandatoryValidation.getStatus());
	}
	
	@Test
	public void getDownloads() {
		Response downloads = utilService.getDownloads(customerId, "sample");
		Assert.assertEquals(200, downloads.getStatus());
		Response downloadSuccess = utilService.getDownloads(customerId, "admin");
		Assert.assertEquals(200, downloadSuccess.getStatus());
		Response downloadFail = utilService.getDownloads("b6b5b856-97d8-4e2a-8b42-a5a23568fe51", "admin");
		Assert.assertEquals(200, downloadFail.getStatus());
		
		
	}
	
	@Test
	public void testGetMachine() throws PhrescoException, UnknownHostException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		InetAddress  ip = InetAddress.getLocalHost();
		request.setRemoteAddr(ip.getHostAddress());
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Response checkMachine = utilService.checkMachine(httpServletRequest);
		Assert.assertEquals(200, checkMachine.getStatus());
	}
	
	
	@Test
	public void testGetMachineFail() throws PhrescoException, UnknownHostException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr("172.16.98.26");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Response checkMachine = utilService.checkMachine(httpServletRequest);
		Assert.assertEquals(200, checkMachine.getStatus());
	}
	
	
}
