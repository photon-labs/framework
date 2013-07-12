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

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.BuildInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path(ServiceConstants.REST_API_DOWNLOADIPA)
public class IpaDownloadService extends RestBase implements ServiceConstants {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadIpa(@QueryParam(ServiceConstants.REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(ServiceConstants.REST_QUERY_BUILD_NUMBER) String buildNumber) 
		throws PhrescoException {
		ResponseInfo responseData = new ResponseInfo();
		try {
			Gson gson = new Gson();
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			File projectInfoPath = new File(getAppDirectoryPath(appDirName) + File.separator + FrameworkConstants.FOLDER_DOT_PHRESCO + 
					File.separator + FrameworkConstants.PROJECT_INFO);
			BufferedReader reader = new BufferedReader(new FileReader(projectInfoPath));
			ProjectInfo info = gson.fromJson(reader, ProjectInfo.class);
			
			ApplicationInfo applicationInfo = info.getAppInfos().get(0);
			
			if (StringUtils.isEmpty(buildNumber)) {
				throw new PhrescoException("Build Number is empty ");
			}
			String ipaFileName = applicationInfo.getName();
			String buildName = getBuildName(appDirName, buildNumber).getDeployLocation();
			String workingDirectory = getAppDirectoryPath(appDirName);
			String buildNameSubstring = buildName.substring(0, buildName.lastIndexOf( File.separator));
			String appBuildName = buildNameSubstring.substring(buildNameSubstring.lastIndexOf( File.separator) + 1);
			List<String> buildArgCmds = new ArrayList<String>();
			buildArgCmds.add("-Dapplication.name=" + ipaFileName);
			buildArgCmds.add("-Dapp.path=" + buildName);
			buildArgCmds.add("-Dbuild.name=" + appBuildName);
			BufferedInputStream readers = applicationManager.performAction(info, ActionType.IPA_DOWNLOAD, buildArgCmds, workingDirectory);
			
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
			
			String ipaPath = getBuildName(appDirName, buildNumber).getDeployLocation();
			ipaPath = ipaPath.substring(0, ipaPath.lastIndexOf( File.separator)) +  File.separator + ipaFileName + ".ipa";
			InputStream fileInputStream = new FileInputStream(new File(ipaPath));
			return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(fileInputStream).build();
		} catch (FileNotFoundException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "IPA Download Failed", null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (IOException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "IPA Download Failed", null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	
	private BuildInfo getBuildName(String appdir, String buildNo) throws PhrescoException {
		try {
			Gson gson = new Gson();
			File buildInfoPath = new File(getBuildInfosFilePath(appdir));
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

	
	public String getAppDirectoryPath(String appdir) {
		return Utility.getProjectHome() + File.separator +  appdir;
	}

	public  String getBuildInfosFilePath(String appdir) {
		File buildInfoPath = new File(getAppDirectoryPath(appdir) + File.separator + FrameworkConstants.DO_NOT_CHECKIN_DIR + 
				File.separator +  FrameworkConstants.BUILD  + File.separator+ FrameworkConstants.BUILD_INFO_FILE_NAME);
		return buildInfoPath.getPath();
	}

	
}
