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
import com.photon.phresco.service.client.impl.ServiceManagerImpl;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/repository")
public class RepositoryService extends RestBase implements FrameworkConstants {

	@GET
	@Path("/importApplication")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sampleTest(@QueryParam("type") String type, @QueryParam("revision") String revision, @QueryParam("revisionVal") String revisionVal, @QueryParam("repoUrl") String repoUrl, @QueryParam("userName") String userName, @QueryParam("password") String password) {
		Response response = null;
		if(type.equals(SVN)){
			response = importSVNApplication(type, revision,revisionVal, repoUrl, userName, password);
		} else if (type.equals(GIT)) {
			response = importGITApplication( type, revision, repoUrl, userName, password);
		}else if(type.equals(BITKEEPER)){
			response = importBitKeeperApplication(type, repoUrl, userName, password);
		}
		return response;
	}

	private Response importSVNApplication(String type, String revision, String revisionVal, String repoUrl, String userName, String password) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
			revision = !HEAD_REVISION.equals(revision) ? revisionVal : revision;
			ApplicationInfo importProject = scmi.importProject(type, repoUrl, userName, password, null, revision);
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

	private Response importGITApplication(String type, String revision, String repoUrl, String userName, String password) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
			ApplicationInfo importProject = scmi.importProject(type, repoUrl, userName, password, MASTER ,revision);
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

	private Response importBitKeeperApplication(String type, String repoUrl, String userName, String password) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
			ApplicationInfo importProject = scmi.importProject(type, repoUrl, userName, password, null , null);
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
	@Path("/updateApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sampleUpdate(ApplicationInfo applicationInfo,  @QueryParam("revision") String revision, @QueryParam("revisionVal") String revisionVal, @QueryParam("type") String type, @QueryParam("repoUrl") String repoUrl, @QueryParam("userName") String userName, @QueryParam("password") String password) {
		Response response = null;
		if(type.equals(SVN)){
			response = updateSVNProject(type, applicationInfo, revisionVal, repoUrl, userName, password);
		} else if (type.equals(GIT)) {
			response =  updateGitProject(type, applicationInfo,repoUrl, userName, password);
		}else if(type.equals(BITKEEPER)){
			response = updateBitKeeperProject(type, applicationInfo, repoUrl, userName, password, revision);
		}
		return response;
	}

	private Response updateGitProject(String type, ApplicationInfo applicationInfo, String repoUrl, String userName, String password) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
			scmi.updateProject(type, repoUrl, userName, password, MASTER, null, applicationInfo);
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

	private Response updateSVNProject(String type, ApplicationInfo applicationInfo, String revisionVal, String repoUrl, String userName, String password) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		String revision = "";

		revision = ! HEAD_REVISION.equals(revision) ? revisionVal : revision;
		try {
			scmi.updateProject(type, repoUrl, userName, password, null, revision, applicationInfo);
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

	private Response updateBitKeeperProject(String type, ApplicationInfo applicationInfo, String repoUrl, String userName, String password, String revision) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
			scmi.updateProject(type, repoUrl, userName, password, null, revision, applicationInfo);
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
	@Path("/addSvn")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSVNProject(ApplicationInfo applicationInfo, @QueryParam("userId") String userId, @QueryParam("projectId") String projectId, @QueryParam("appId") String appId, @QueryParam("appDirName") String appDirName, @QueryParam("repoUrl") String repoUrl, @QueryParam("userName") String userName, @QueryParam("password") String password, @QueryParam("commitMessage") String commitMessage) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
			scmi.importToRepo(SVN, repoUrl, userName, password, null, null, applicationInfo, commitMessage);
			User user = ServiceManagerImpl.USERINFO_MANAGER_MAP.get(userId);
			updateLatestProject(user, projectId, appId);
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, "SVN project added Successfully", null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, " SVN project add failed", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	@POST
	@Path("/commitSvn")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response commitSVNProject(List<String>commitableFiles, @QueryParam("userName") String userName, @QueryParam("password") String password, @QueryParam("commitMessage") String commitMessage ) {
		ResponseInfo responseData = new ResponseInfo();
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			if (CollectionUtils.isNotEmpty(commitableFiles)) {
				List<File> listModifiedFiles = new ArrayList<File>(commitableFiles.size());
				for (String commitableFile : commitableFiles) {
					listModifiedFiles.add(new File(commitableFile));
				}
				scmi.commitSpecifiedFiles(listModifiedFiles, userName, password, commitMessage);
			}
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, "SVN project committed Successfully", null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "SVN project commit failed", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	@POST
	@Path("/commitBitKeeper")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response commitBitKeeperProject(@QueryParam("appDirName") String appDirName, @QueryParam("userName") String userName, @QueryParam("password") String password, @QueryParam("commitMessage") String commitMessage, @QueryParam("repoUrl") String repoUrl ) {
		ResponseInfo responseData = new ResponseInfo();
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			File appDir = new File(Utility.getProjectHome() + appDirName);
			scmi.commitToRepo(BITKEEPER, repoUrl, userName, password,  null, null, appDir, commitMessage);
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
	
	@POST
	@Path("/addGit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addGitProject(ApplicationInfo appinfo, @QueryParam("userId") String userId, @QueryParam("projectId") String projectId, @QueryParam("appId") String appId, @QueryParam("appDirName") String appDirName, @QueryParam("repoUrl") String repoUrl, @QueryParam("userName") String userName, @QueryParam("password") String password, @QueryParam("commitMessage") String commitMessage) {
		ResponseInfo responseData = new ResponseInfo();
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			scmi.importToRepo(GIT, repoUrl, userName, password, null, null,	appinfo, commitMessage);
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
	@Path("/commitGit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response commitGitProject(List<String>commitableFiles, @QueryParam("appDirName") String appDirName, @QueryParam("userName") String userName, @QueryParam("password") String password, @QueryParam("repoUrl") String repoUrl, @QueryParam("commitMessage") String commitMessage) {
		ResponseInfo responseData = new ResponseInfo();
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			String applicationHome = Utility.getProjectHome() + appDirName;
			File appDir = new File(applicationHome);
			if (!commitableFiles.isEmpty()) {
				scmi.commitToRepo(GIT, repoUrl, userName, password, null, null, appDir, commitMessage);
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