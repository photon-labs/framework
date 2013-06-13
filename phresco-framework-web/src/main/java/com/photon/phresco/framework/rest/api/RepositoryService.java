package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Config;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNException;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.impl.SCMManagerImpl;
import com.photon.phresco.framework.model.RepoDetail;
import com.photon.phresco.framework.model.RepoFileInfo;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.service.client.impl.ServiceManagerImpl;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.model.Scm;
import com.phresco.pom.util.PomProcessor;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/repository")
public class RepositoryService extends RestBase implements FrameworkConstants {
	@POST
	@Path("/importApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response importApplication(RepoDetail repodetail) {
		Response response = null;
		String type = repodetail.getType();
		if(type.equals(SVN)){
			response = importSVNApplication(type, repodetail);
		} else if (type.equals(GIT)) {
			response = importGITApplication( type, repodetail);
		}else if(type.equals(BITKEEPER)){
			response = importBitKeeperApplication(type, repodetail);
		}
		return response;
	}

	@POST
	@Path("/updateImportedApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateImportedApplicaion(@QueryParam("appDirName") String appDirName, RepoDetail repodetail) {
		Response response = null;
		ResponseInfo responseData = new ResponseInfo();
		try {
			ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			String type = repodetail.getType();
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

	@POST
	@Path("/addProjectToRepo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProjectToRepo(@QueryParam("appDirName") String appDirName, RepoDetail repodetail, @QueryParam("userId") String userId, @QueryParam("projectId") String projectId, @QueryParam("appId") String appId) {
		Response response = null;
		String type = repodetail.getType();
		if(type.equals(SVN)){
			response = addSVNProject(appDirName, repodetail, userId, projectId, appId, type);
		} else if (type.equals(GIT)) {
			response = addGitProject(userId, projectId, appId, appDirName, repodetail, type);
		}
		return response;
	}

	@POST
	@Path("/commitProjectToRepo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response commitImportedProject(RepoDetail repodetail, @QueryParam("appDirName") String appDirName) {
		Response response = null;
		String type = repodetail.getType();
		if(type.equals(SVN)){
			response = commitSVNProject(repodetail);
		} else if (type.equals(GIT)) {
			response = commitGitProject(appDirName, repodetail, type);
		}else if(type.equals(BITKEEPER)){
			response = commitBitKeeperProject(appDirName, repodetail, type);
		}
		return response;
	}

	@GET
	@Path("/popupValues")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchPopUpValues(@QueryParam("appDirName") String appDirName, @QueryParam("action") String action, @QueryParam("userId") String userId) {
		Response response = null;
		if(action.equals(COMMIT)) {
			response = repoExistCheckForCommit(appDirName, action, userId);
		} else if(action.equals("update")) {
			response = repoExistCheckForUpdate(appDirName, action, userId);
		}
		return response;
	}
	
	@POST
	@Path("/logMessages")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchLogMessages(RepoDetail repodetail) throws PhrescoException {
		List<String> restrictedLogs = null;
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
		try {
			SCMManagerImpl scmi = new SCMManagerImpl();
			List<String> svnLogMessages = scmi.getSvnLogMessages(repodetail.getRepoUrl(), repodetail.getUserName(), repodetail.getPassword());
			restrictedLogs = restrictLogs(svnLogMessages);
		} catch (PhrescoException e) {
			if (e.getLocalizedMessage().contains("Authorization Realm")) {
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, e, "Invalid Credentials", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else if (e.getLocalizedMessage().contains("OPTIONS request failed on") || 
					(e.getLocalizedMessage().contains("PROPFIND") && e.getLocalizedMessage().contains("405 Method Not Allowed")) 
					|| e.getLocalizedMessage().contains("Repository moved temporarily to") ) {
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, e, "Invalid Url or Repository moved temproarily", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else {
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, e, "Fetch log message Failure", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
		}
		ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null, "Log messages returned successfully", restrictedLogs);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}
	
	private List<String> restrictLogs(List<String> svnLogMessages) {
		List<String> Messages = new ArrayList<String>();
		if (svnLogMessages.size() > 5) {
			for(int i = svnLogMessages.size()-5; i<= svnLogMessages.size()-1; i++) {
				Messages.add(svnLogMessages.get(i));
			} 
		} else {
			for(int i = 0; i<= svnLogMessages.size()-1; i++) {
				Messages.add(svnLogMessages.get(i));
			} 
		}
		return Messages;
	}

	private Response importSVNApplication(String type, RepoDetail repodetail) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		String revision = "";
		try {
			ApplicationInfo importProject = scmi.importProject(type, repodetail.getRepoUrl(), repodetail.getUserName(), repodetail.getPassword(), null, repodetail.getRevision());
			if (importProject != null) {
				if (repodetail.isTestCheckOut()) {
					String path = Utility.getProjectHome() + File.separator + importProject.getAppDirName() + File.separator;
					File testFolder = new File(path, TEST);
					if (!testFolder.exists()) {
						testFolder.mkdirs();
					}
					FileUtils.cleanDirectory(testFolder);
					String testSvnCheckout = scmi.svnCheckout(repodetail.getTestUserName(), repodetail.getTestPassword(), repodetail.getTestRepoUrl(), testFolder.getPath(), repodetail.getTestRevision());
				}
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, IMPORT_SUCCESS_PROJECT, null);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
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
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
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
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
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

	private Response addSVNProject(String appDirName, RepoDetail repodetail, String userId, String projectId, String appId, String type) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
			ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			scmi.importToRepo(type, repodetail.getRepoUrl(), repodetail.getUserName(), repodetail.getPassword(), null, null, applicationInfo, repodetail.getCommitMessage());
			User user = ServiceManagerImpl.USERINFO_MANAGER_MAP.get(userId);
//			updateLatestProject(user, projectId, appId);
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
//			updateLatestProject(user, projectId, appId);
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

	private String getRepoType(String repoUrl) {
		String repoType = "";
		if(repoUrl.startsWith("bk")) {
			repoType = BITKEEPER;
		} else if(repoUrl.endsWith(".git")) {	
			repoType = GIT;
		} else if (repoUrl.contains("svn")) {
			repoType = SVN;
		}
		return repoType;
	}

	private Response repoExistCheckForCommit( String appDirName, String action, String userId) {
		RepoDetail repodetail = new RepoDetail();
		boolean setRepoExistForCommit = false;
		ResponseInfo<RepoDetail> responseData = new ResponseInfo<RepoDetail>();
		try {
			ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			String repoUrl = getConnectionUrl(applicationInfo);
			if(repoUrl.startsWith("bk")) {
				setRepoExistForCommit = true;
				repodetail.setRepoExist(setRepoExistForCommit);
			} else if(repoUrl.endsWith(".git")) {
				setRepoExistForCommit = checkGitProject(applicationInfo, setRepoExistForCommit);
				repodetail.setRepoExist(setRepoExistForCommit);
				repodetail = updateProjectPopup(appDirName, action, repodetail);
			} else if (!setRepoExistForCommit) {
				repodetail = updateProjectPopup(appDirName, action, repodetail);
			}
			
			repodetail.setUserName(userId);
			repodetail.setType(getRepoType(repoUrl));
			repodetail.setRepoUrl(repoUrl);
			if(!repodetail.isRepoExist()) {
				ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, null, "Repo Doesnot Exist for commit", repodetail);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}

		} catch (PhrescoException e) {
			ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, e, "Repo Doesnot Exist", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
		ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, null, "Repo Exist for commit", repodetail);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}

	private Response repoExistCheckForUpdate(String appDirName, String action, String userId) {
		RepoDetail repodetail = new RepoDetail();
		ResponseInfo<RepoDetail> responseData = new ResponseInfo<RepoDetail>();
		try {
			ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			String repoUrl = getConnectionUrl(applicationInfo);
			repodetail = updateProjectPopup(appDirName, action, repodetail);
			repodetail.setType(getRepoType(repoUrl));
			repodetail.setRepoUrl(repoUrl);
			repodetail.setUserName(userId);
			if(!repodetail.isRepoExist()) {
				ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, null, "Repo Doesnot Exist for update", repodetail);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			
		} catch (PhrescoException e) {
			ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, e, "Repo Doesnot Exist", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}

		ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, null, "Repo Exist for update", repodetail);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}

	private RepoDetail updateProjectPopup(String appDirName, String action, RepoDetail repodetail) {
		boolean setRepoExistForCommit = false;
		ResponseInfo<List<RepoFileInfo>> responseData = new ResponseInfo<List<RepoFileInfo>>();
		List<RepoFileInfo> commitableFiles = null;
		try {
			ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			setRepoExistForCommit = true;

			//getting commitable files for SVN repo
			if (COMMIT.equals(action)
					&& !getConnectionUrl(applicationInfo).contains(BITKEEPER)
					&& !getConnectionUrl(applicationInfo).contains(GIT)) {
				commitableFiles = svnCommitableFiles(appDirName);

				//getting commitable files for Git repo
			} else if (COMMIT.equals(action)
					&& !getConnectionUrl(applicationInfo).contains(BITKEEPER)
					&& !getConnectionUrl(applicationInfo).contains(SVN)) {
				commitableFiles = gitCommitableFiles(appDirName);
			}
		} catch (PhrescoException e) {
			if (e.getLocalizedMessage().contains(IS_NOT_WORKING_COPY)) {
				setRepoExistForCommit = false;
			}
		}
		repodetail.setRepoInfoFile(commitableFiles);
		repodetail.setRepoExist(setRepoExistForCommit);
		return repodetail;
	}

	private String getConnectionUrl(ApplicationInfo applicationInfo) throws PhrescoException {
		try {
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			PomProcessor processor = frameworkUtil.getPomProcessor(applicationInfo.getAppDirName());
			Scm scm = processor.getSCM();
			if (scm != null && !scm.getConnection().isEmpty()) {
				return scm.getConnection();
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}

		return "";
	}

	private boolean checkGitProject(ApplicationInfo applicationInfo, boolean setRepoExistForCommit) throws PhrescoException {
		setRepoExistForCommit =true;
		String url = "";
		String Path = "";
		if (applicationInfo != null) {
			String appDirName = applicationInfo.getAppDirName();
			Path = Utility.getProjectHome() + appDirName;
		}
		File projectPath = new File(Path);
		InitCommand initCommand = Git.init();
		initCommand.setDirectory(projectPath);
		Git git = null;
		try {
			git = initCommand.call();
		} catch (GitAPIException e) {
			throw new PhrescoException(e);
		}

		Config storedConfig = git.getRepository().getConfig();
		url = storedConfig.getString(REMOTE, ORIGIN, URL);
		if (StringUtils.isEmpty(url)) {
			File toDelete = git.getRepository().getDirectory();
			try {
				FileUtils.deleteDirectory(toDelete);
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
			setRepoExistForCommit = false;

		}
		git.getRepository().close();

		return setRepoExistForCommit;
	}

	private List<RepoFileInfo> svnCommitableFiles(String appDirName) throws PhrescoException {
		List<RepoFileInfo> commitableFiles = null;
		String revision = "";
		try {

			SCMManagerImpl scmi = new SCMManagerImpl();
			String applicationHome = FrameworkServiceUtil.getApplicationHome(appDirName);
			File appDir = new File(applicationHome);
			revision = HEAD_REVISION;
			commitableFiles = scmi.getCommitableFiles(appDir, revision);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return commitableFiles;
	}

	private List<RepoFileInfo> gitCommitableFiles(String appDirName) throws PhrescoException {
		List<RepoFileInfo> gitCommitableFiles = null;
		try {
			SCMManagerImpl scmi = new SCMManagerImpl();
			String applicationHome = FrameworkServiceUtil.getApplicationHome(appDirName);
			File appDir = new File(applicationHome);
			gitCommitableFiles = scmi.getGITCommitableFiles(appDir);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return gitCommitableFiles;
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