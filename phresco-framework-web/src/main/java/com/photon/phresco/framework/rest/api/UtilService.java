package com.photon.phresco.framework.rest.api;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.Technology;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path ("/util")
public class UtilService extends RestBase implements FrameworkConstants {
	
	@GET
	@Path ("/openFolder")
	@Produces(MediaType.APPLICATION_JSON)
	public Response openFolder(@QueryParam("appDirName") String appDirName, @QueryParam("type") String type) {
		ResponseInfo<String> responseData = new ResponseInfo<String>();
		try {
			if (Desktop.isDesktopSupported()) {
				File file = new File(getPath(appDirName, type));
				if (file.exists()) {
					Desktop.getDesktop().open(file);
				} else {
					StringBuilder sbOpenPath = new StringBuilder(Utility.getProjectHome());
					sbOpenPath.append(appDirName);
					Desktop.getDesktop().open(new File(sbOpenPath.toString()));
				}
	    	}
			ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, null, "Folder opened successfully", null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<ProjectInfo> finalOutput = responseDataEvaluation(responseData, e, "Open folder failed", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path ("/copyPath")
	@Produces(MediaType.APPLICATION_JSON)
	public Response copyPath(@QueryParam("appDirName") String appDirName, @QueryParam("type") String type) {
		ResponseInfo<String> responseData = new ResponseInfo<String>();
		try {
			File file = new File(getPath(appDirName, type));
			String pathToCopy = "";
	    	if (file.exists()) {
	    		pathToCopy = file.getPath();
	    	} else {
	    		StringBuilder sbCopyPath = new StringBuilder(Utility.getProjectHome());
				sbCopyPath.append(appDirName);
	    		pathToCopy = Utility.getProjectHome() + sbCopyPath.toString();
	    	}
	    	copyToClipboard(pathToCopy);
			ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, null, "Path copied successfully", null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<ProjectInfo> finalOutput = responseDataEvaluation(responseData, e, "Copy path failed", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/techOptions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTecnologyOptions(@QueryParam("userId") String userId, @QueryParam("techId") String techId) {
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			Technology technology = serviceManager.getTechnology(techId);
			List<String> archetypeFeatures = technology.getOptions();
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null, "Technology options fetched successfully", archetypeFeatures);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<ProjectInfo> finalOutput = responseDataEvaluation(responseData, e, "Failed to get Technology options", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	private String getPath(String appDirName, String type) throws PhrescoException {
		StringBuilder sb = new StringBuilder(Utility.getProjectHome())
		.append(appDirName)
		.append(File.separator);
		try {
			if (TEST_UNIT.equals(type)) {
				sb.append(FrameworkServiceUtil.getUnitTestDir(appDirName));
			} else if (TEST_FUNCTIONAL.equals(type)) {
				sb.append(FrameworkServiceUtil.getFunctionalTestDir(appDirName));
			} else if (TEST_COMPONENT.equals(type)) {
				sb.append(FrameworkServiceUtil.getComponentTestDir(appDirName));
			} else if (TEST_LOAD.equals(type)) {
				sb.append(FrameworkServiceUtil.getLoadTestDir(appDirName));
			} else if (TEST_MANUAL.equals(type)) {
				sb.append(FrameworkServiceUtil.getManualTestDir(appDirName));
			} else if (TEST_PERFORMANCE.equals(type)) {
				sb.append(FrameworkServiceUtil.getPerformanceTestDir(appDirName));
			} else if (BUILD.equals(type)) {
				sb = new StringBuilder(FrameworkServiceUtil.getBuildDir(appDirName));
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}

		return sb.toString(); 
	}
	
	private void copyToClipboard(String content) {
    	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    	clipboard.setContents(new StringSelection(content.replaceAll("(?m)^[ \t]*\r?\n", "")), null);
    }
}