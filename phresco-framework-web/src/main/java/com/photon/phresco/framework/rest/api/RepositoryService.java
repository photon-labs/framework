package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileExistsException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNException;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.impl.SCMManagerImpl;
import com.photon.phresco.framework.model.RepoDetail;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.service.client.impl.ServiceManagerImpl;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/repository")
public class RepositoryService extends RestBase implements FrameworkConstants {

	@POST
	@Path("/importApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response importApplication(@QueryParam("type") String type, RepoDetail repodetail) {
		Response response = null;
		if(type.equals(SVN)){
			response = importSVNApplication(type, repodetail);
		} else if (type.equals(GIT)) {
			response = importGITApplication( type, repodetail);
		}else if(type.equals(BITKEEPER)){
			response = importBitKeeperApplication(type, repodetail);
		}
		return response;
	}

	private Response importSVNApplication(String type, RepoDetail repodetail) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		String revision = "";
		try {
			revision = !HEAD_REVISION.equals(repodetail.getRevision()) ? repodetail.getRevisionVal() : repodetail.getRevision();
			ApplicationInfo importProject = scmi.importProject(type, repodetail.getRepoUrl(), repodetail.getUserName(), repodetail.getPassword(), null, revision);
			if (importProject != null) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, IMPORT_SUCCESS_PROJECT, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, INVALID_FOLDER, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (SVNAuthenticationException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_CREDENTIALS, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (SVNException e) {
			if (e.getMessage().indexOf(SVN_FAILED) != -1) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_URL, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else if (e.getMessage().indexOf(SVN_INTERNAL) != -1) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_REVISION, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, IMPORT_PROJECT_FAIL, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (FileExistsException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, PROJECT_ALREADY, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, IMPORT_PROJECT_FAIL, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, IMPORT_PROJECT_FAIL, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	private Response importGITApplication(String type, RepoDetail repodetail) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
			ApplicationInfo importProject = scmi.importProject(type, repodetail.getRepoUrl(), repodetail.getUserName(), repodetail.getPassword(), MASTER ,repodetail.getRevision());
			if (importProject != null) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, IMPORT_SUCCESS_PROJECT, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, INVALID_FOLDER, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (SVNAuthenticationException e) {	
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_CREDENTIALS, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (SVNException e) {	
			if (e.getMessage().indexOf(SVN_FAILED) != -1) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_URL, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else if (e.getMessage().indexOf(SVN_INTERNAL) != -1) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_REVISION, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, IMPORT_PROJECT_FAIL, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
		}  catch (FileExistsException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, PROJECT_ALREADY, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}  catch (PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, IMPORT_PROJECT_FAIL, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, IMPORT_PROJECT_FAIL, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	private Response importBitKeeperApplication(String type, RepoDetail repodetail) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
			ApplicationInfo importProject = scmi.importProject(type, repodetail.getRepoUrl(), repodetail.getUserName(), repodetail.getPassword(), null , null);
			if (importProject != null) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, IMPORT_SUCCESS_PROJECT, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (Exception e) {
			if ("Project already imported".equals(e.getLocalizedMessage())) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, IMPORT_PROJECT_FAIL, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else  if ("Failed to import project".equals(e.getLocalizedMessage())) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, IMPORT_PROJECT_FAIL, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, IMPORT_PROJECT_FAIL, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
		}
		ResponseInfo finalOutput = responseDataEvaluation(responseData, null, IMPORT_SUCCESS_PROJECT, null);
		return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}


	@POST
	@Path("/updateImportedApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateImportedApplicaion(@QueryParam("type") String type, @QueryParam("appDirName") String appDirName, RepoDetail repodetail) {
		Response response = null;
		ResponseInfo responseData = new ResponseInfo();
		try {
			ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			if(type.equals(SVN)){
				response = updateSVNProject(responseData, type, applicationInfo, repodetail);
			} else if (type.equals(GIT)) {
				response =  updateGitProject(responseData, type, applicationInfo, repodetail);
			}else if(type.equals(BITKEEPER)){
				response = updateBitKeeperProject(type, applicationInfo, repodetail);
			}
			return response;
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, UPDATE_PROJECT_FAIL, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	private Response updateGitProject(ResponseInfo responseData, String type, ApplicationInfo applicationInfo, RepoDetail repodetail) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			scmi.updateProject(type, repodetail.getRepoUrl(), repodetail.getUserName(), repodetail.getPassword(), MASTER, null, applicationInfo);
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, SUCCESS_PROJECT_UPDATE, null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (InvalidRemoteException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_URL, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (TransportException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_URL, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (SVNAuthenticationException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_CREDENTIALS, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (SVNException e) {	
			if (e.getMessage().indexOf(SVN_FAILED) != -1) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_URL, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else if (e.getMessage().indexOf(SVN_INTERNAL) != -1) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_REVISION, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, IMPORT_PROJECT_FAIL, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (FileExistsException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, PROJECT_ALREADY, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, UPDATE_PROJECT_FAIL, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, UPDATE_PROJECT_FAIL, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}

	}

	private Response updateSVNProject(ResponseInfo responseData, String type, ApplicationInfo applicationInfo, RepoDetail repodetail) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		String revision = "";
		revision = ! HEAD_REVISION.equals(repodetail.getRevision()) ? repodetail.getRevisionVal() : repodetail.getRevision();
		try {
			scmi.updateProject(type, repodetail.getRepoUrl(), repodetail.getUserName(), repodetail.getPassword(), null, revision, applicationInfo);
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, "update svn project Successfully", null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (InvalidRemoteException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_URL, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (TransportException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_URL, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (SVNAuthenticationException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_CREDENTIALS, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (SVNException e) {
			if (e.getMessage().indexOf(SVN_FAILED) != -1) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_URL, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else if (e.getMessage().indexOf(SVN_INTERNAL) != -1) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_REVISION, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else if (e.getMessage().indexOf(SVN_IS_NOT_WORKING_COPY) != -1) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, NOT_WORKING_COPY, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, UPDATE_PROJECT_FAIL, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (FileExistsException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, PROJECT_ALREADY, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, INVALID_REVISION, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, UPDATE_PROJECT_FAIL, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	private Response updateBitKeeperProject(String type, ApplicationInfo applicationInfo , RepoDetail repodetail) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
			scmi.updateProject(type, repodetail.getRepoUrl(), repodetail.getUserName(), repodetail.getPassword(), null, repodetail.getRevision(), applicationInfo);
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, SUCCESS_PROJECT_UPDATE, null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (PhrescoException e) {
			if (e.getLocalizedMessage().contains("Nothing to pull")) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "No Files to update", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, UPDATE_PROJECT_FAIL, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (Exception e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, UPDATE_PROJECT_FAIL, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@POST
	@Path("/addProjectToRepo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProjectToRepo(@QueryParam("type") String type, @QueryParam("appDirName") String appDirName, RepoDetail repodetail, @QueryParam("userId") String userId, @QueryParam("projectId") String projectId, @QueryParam("appId") String appId) {
		Response response = null;
		if(type.equals(SVN)){
			response = addSVNProject(appDirName, repodetail, userId, projectId, appId, type);
		} else if (type.equals(GIT)) {
			response = addGitProject(userId, projectId, appId, appDirName, repodetail, type);
		}
		return response;
	}
	

	private Response addSVNProject(String appDirName, RepoDetail repodetail, String userId, String projectId, String appId, String type) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
			ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			scmi.importToRepo(type, repodetail.getRepoUrl(), repodetail.getUserName(), repodetail.getPassword(), null, null, applicationInfo, repodetail.getCommitMessage());
			User user = ServiceManagerImpl.USERINFO_MANAGER_MAP.get(userId);
			updateLatestProject(user, projectId, appId);
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, "SVN project added Successfully", null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, " SVN project add failed", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	private Response addGitProject(String userId, String projectId, String appId, String appDirName, RepoDetail repodetail, String type) {
		ResponseInfo responseData = new ResponseInfo();
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			scmi.importToRepo(type, repodetail.getRepoUrl(), repodetail.getUserName(), repodetail.getPassword(), null, null,	applicationInfo, repodetail.getCommitMessage());
			User user = ServiceManagerImpl.USERINFO_MANAGER_MAP.get(userId);
			updateLatestProject(user, projectId, appId);
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, "GIT project added Successfully", null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			if(e.getLocalizedMessage().contains("git-receive-pack not found")) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "this Git repository does not exist", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "Add Git Failed", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
		}
	}
	
	@POST
	@Path("/commitProjectToRepo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response commitImportedProject(RepoDetail repodetail, @QueryParam("type") String type, @QueryParam("appDirName") String appDirName) {
		Response response = null;
		if(type.equals(SVN)){
			response = commitSVNProject(repodetail);
		} else if (type.equals(GIT)) {
			response = commitGitProject(appDirName, repodetail, type);
		}else if(type.equals(BITKEEPER)){
			response = commitBitKeeperProject(appDirName, repodetail, type);
		}
		return response;
	}
	
	public Response commitSVNProject(RepoDetail repodetail) {
		ResponseInfo responseData = new ResponseInfo();
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			if (CollectionUtils.isNotEmpty(repodetail.getCommitableFiles())) {
				List<File> listModifiedFiles = new ArrayList<File>(repodetail.getCommitableFiles().size());
				for (String commitableFile : repodetail.getCommitableFiles()) {
					listModifiedFiles.add(new File(commitableFile));
				}
				scmi.commitSpecifiedFiles(listModifiedFiles, repodetail.getUserName(), repodetail.getPassword(), repodetail.getCommitMessage());
			}
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, "SVN project committed Successfully", null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "SVN project commit failed", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	public Response commitBitKeeperProject(String appDirName, RepoDetail repodetail, String type) {
		ResponseInfo responseData = new ResponseInfo();
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			File appDir = new File(Utility.getProjectHome() + appDirName);
			scmi.commitToRepo(type, repodetail.getRepoUrl(), repodetail.getUserName(), repodetail.getPassword(),  null, null, appDir, repodetail.getCommitMessage());
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, COMMIT_PROJECT_SUCCESS, null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			if (e.getLocalizedMessage().contains("Nothing to push")) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "No Files to commit", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, COMMIT_PROJECT_FAIL, null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (Exception e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, COMMIT_PROJECT_FAIL, null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	public Response commitGitProject(String appDirName, RepoDetail repodetail, String type) {
		ResponseInfo responseData = new ResponseInfo();
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			String applicationHome = Utility.getProjectHome() + appDirName;
			File appDir = new File(applicationHome);
			if (!repodetail.getCommitableFiles().isEmpty()) {
				scmi.commitToRepo(type, repodetail.getRepoUrl(), repodetail.getUserName(), repodetail.getPassword(), null, null, appDir, repodetail.getCommitMessage());
			}
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, COMMIT_PROJECT_SUCCESS, null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "Commit Git Failed", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	private void updateLatestProject(User user, String projectId, String appId) throws PhrescoException {
		try {
			File tempPath = new File(Utility.getPhrescoTemp() + File.separator + USER_PROJECT_JSON);
			JSONObject userProjJson = null;
			JSONParser parser = new JSONParser();
			if (tempPath.exists()) {
				FileReader reader = new FileReader(tempPath);
				userProjJson = (JSONObject)parser.parse(reader);
				reader.close();
			} else {
				userProjJson = new JSONObject();
			}

			userProjJson.put(user.getId(), projectId + Constants.STR_COMMA + appId);
			FileWriter  writer = new FileWriter(tempPath);
			writer.write(userProjJson.toString());
			writer.close();
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (ParseException e) {
			throw new PhrescoException(e);
		}
	}

}