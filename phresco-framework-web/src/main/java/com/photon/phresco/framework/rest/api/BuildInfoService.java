package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.BuildInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/buildinfo")
public class BuildInfoService implements FrameworkConstants {
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@QueryParam("appDirName") String appDirName) {
		ResponseInfo responseData = new ResponseInfo();
		try {
			File buildInfoFile = new File(Utility.getProjectHome() + appDirName + File.separator + BUILD_DIR
					+ File.separator + BUILD_INFO_FILE_NAME);
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			List<BuildInfo> builds = applicationManager.getBuildInfos(buildInfoFile);
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, null,"Buildinfo return Successfully", builds);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, e,"Buildinfo return Failed", null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		}
	}

	@POST
	@Path("/buildfile")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buildInfoZip(@QueryParam("appDirName") String appDirName, @QueryParam("buildNumber") int buildNumber) {
		InputStream fileInputStream = null;
		ResponseInfo responseData = new ResponseInfo();
		try {
			File buildInfoFile = new File(Utility.getProjectHome() + appDirName + 
					File.separator + BUILD_DIR +  File.separator + BUILD_INFO_FILE_NAME);
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			BuildInfo buildInfo = applicationManager.getBuildInfo(buildNumber, buildInfoFile.toString());
			if (buildInfo.getBuildNo() == buildNumber) {
				String deliverables = buildInfo.getDeliverables();
				StringBuilder builder = new StringBuilder();
				String fileName = buildInfo.getBuildName();
				if (StringUtils.isEmpty(deliverables)) {
					builder.append(Utility.getProjectHome() + appDirName);
					builder.append(File.separator);
					String moduleName = buildInfo.getModuleName();
					if (StringUtils.isNotEmpty(moduleName)) {
						builder.append(moduleName);
						builder.append(File.separator);
					}
					builder.append(BUILD_DIR);
					builder.append(File.separator);
					builder.append(buildInfo.getBuildName());
				} else {
					builder.append(buildInfo.getDeliverables());
					fileName = fileName.substring(fileName.lastIndexOf(FORWARD_SLASH) + 1);
					boolean status = fileName.endsWith(APKLIB) || fileName.endsWith(APK);
					if (status) {
						fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ARCHIVE_FORMAT;
					} else {
						fileName = FilenameUtils.removeExtension(fileName) + ARCHIVE_FORMAT;
					}
				}
				fileInputStream = new FileInputStream(new File(builder.toString()));
//			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, null, "Zip Download successfully", fileInputStream);
				return Response.status(Status.OK).entity(fileInputStream).header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (FileNotFoundException e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, e, "Zip Download Failed", null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, e, "Zip Download Failed", null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
		return null;
	}

	@DELETE
	@Path("/deletebuild")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBuild(String[] buildNumbers, @QueryParam("projectId") String projectId, @QueryParam("customerId") String customerId,  @QueryParam("appId") String appId ) {
		ResponseInfo responseData = new ResponseInfo();
		try {
			int[] buildInts = new int[buildNumbers.length];
			for (int i = 0; i < buildNumbers.length; i++) {
				buildInts[i] = Integer.parseInt(buildNumbers[i]);
			}
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			ProjectInfo project = projectManager.getProject(projectId, customerId, appId);
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			applicationManager.deleteBuildInfos(project, buildInts);
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, e, "Build delete Failed", null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
		ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, null, "Build deleted Successfully", null);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}
}
