package com.photon.phresco.framework.rest.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.BuildInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path(ServiceConstants.REST_API_DOWNLOADIPA)
public class IpaDownloadService extends RestBase implements ServiceConstants, FrameworkConstants, ResponseCodes {
	
	@GET
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response downloadIpa(@QueryParam(ServiceConstants.REST_QUERY_MODULE_NAME) String moduleName, @QueryParam(ServiceConstants.REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(ServiceConstants.REST_QUERY_BUILD_NUMBER) String buildNumber) 
		throws PhrescoException {
		ResponseInfo responseData = new ResponseInfo();
		try {
			
			String rootModulePath = "";
			String subModuleName = "";
			if (StringUtils.isNotEmpty(moduleName)) {
				rootModulePath = Utility.getProjectHome() + appDirName;
				subModuleName = moduleName;
			} else {
				rootModulePath = Utility.getProjectHome() + appDirName;
			}
			
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo info = Utility.getProjectInfo(rootModulePath, subModuleName);
			ApplicationInfo applicationInfo = info.getAppInfos().get(0);
			
			if (StringUtils.isEmpty(buildNumber)) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, RESPONSE_STATUS_FAILURE, PHR710012);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			String ipaFileName = applicationInfo.getName();
			File pomFile = Utility.getpomFileLocation(rootModulePath, subModuleName);
			String buildName = getBuildName(rootModulePath,subModuleName, buildNumber).getDeployLocation();
//			String workingDirectory = FrameworkServiceUtil.getApplicationHome(appDirName);
			String buildNameSubstring = buildName.substring(0, buildName.lastIndexOf( File.separator));
			String appBuildName = buildNameSubstring.substring(buildNameSubstring.lastIndexOf( File.separator) + 1);
			List<String> buildArgCmds = new ArrayList<String>();
			buildArgCmds.add("-Dapplication.name=" + ipaFileName);
			buildArgCmds.add("-Dapp.path=" + buildName);
			buildArgCmds.add("-Dbuild.name=" + appBuildName);
			BufferedInputStream readers = applicationManager.performAction(info, ActionType.IPA_DOWNLOAD, buildArgCmds, pomFile.getParent());
			
			int available = readers.available();
			while (available != 0) {
				byte[] buf = new byte[available];
                int read = readers.read(buf);
                if (read == -1 ||  buf[available-1] == -1) {
                	break;
                } else {
                	System.out.println(new String(buf));
                }
                available = readers.available();
			}
			
			String ipaPath = getBuildName(rootModulePath,subModuleName, buildNumber).getDeployLocation();
			ipaPath = ipaPath.substring(0, ipaPath.lastIndexOf( File.separator)) +  File.separator + ipaFileName + ".ipa";
			InputStream fileInputStream = new FileInputStream(new File(ipaPath));
			return Response.status(Status.OK).entity(fileInputStream).header("Content-Disposition", "attachment; filename=" + new File(ipaPath).getName())
			.build();
		} catch (FileNotFoundException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR710013);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (IOException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR710014);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	
	private BuildInfo getBuildName(String rootModulePath, String subModuleName, String buildNo) throws PhrescoException {
		try {
			Gson gson = new Gson();
			File buildInfoPath = new File(FrameworkServiceUtil.getBuildInfosFilePath(rootModulePath, subModuleName));
			BufferedReader reader = new BufferedReader(new FileReader(buildInfoPath));
			Type type = new TypeToken<List<BuildInfo>>() {}  .getType();
			List<BuildInfo> buildInfos = (List<BuildInfo>)gson.fromJson(reader, type);
			BuildInfo info = null;
			int buildNum = Integer.parseInt(buildNo);
			for (BuildInfo buildInfo : buildInfos) {
				if (buildInfo.getBuildNo() == buildNum) {
					info = buildInfo;
				}
			}
			return info;
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		}
	}
}
