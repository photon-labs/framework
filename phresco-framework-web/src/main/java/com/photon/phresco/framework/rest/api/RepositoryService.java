/**
 * Framework Web Archive
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainerException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Config;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.VersionRangeRequest;
import org.sonatype.aether.resolution.VersionRangeResolutionException;
import org.sonatype.aether.resolution.VersionRangeResult;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.util.layout.MavenDefaultLayout;
import org.sonatype.aether.version.Version;
import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.perforce.p4java.exception.ConnectionException;
import com.perforce.p4java.exception.RequestException;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.LockUtil;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.impl.SCMManagerImpl;
import com.photon.phresco.framework.model.RepoDetail;
import com.photon.phresco.framework.model.RepoFileInfo;
import com.photon.phresco.framework.model.RepoInfo;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.impl.ServiceManagerImpl;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Scm;
import com.phresco.pom.util.PomProcessor;
import com.sun.jersey.api.client.ClientResponse.Status;


/**
 * The Class RepositoryService.
 */
@Path("/repository")
public class RepositoryService extends RestBase implements FrameworkConstants, ServiceConstants, ResponseCodes {
	
	String status;
	String errorCode;
	String successCode;
	
	/**
	 * Import application from repository.
	 *
	 * @param repoInfo the repodetail
	 * @return the response
	 * @throws Exception 
	 */
	@POST
	@Path("/importApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response importApplication(RepoInfo repoInfo, @QueryParam("displayName") String displayName) throws Exception {
		Response response = null;
		response = importSVNApplication(repoInfo, displayName);
		return response;
	}

	/**
	 * Update imported applicaion  from repository.
	 *
	 * @param appDirName the app dir name
	 * @param repoInfo the repodetail
	 * @return the response
	 */
	@POST
	@Path("/updateImportedApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateImportedApplicaion(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam("displayName") String displayName,
			RepoInfo repoInfo) {
		Response response = null;
		ResponseInfo responseData = new ResponseInfo();
		try {
			UUID uniqueKey = UUID.randomUUID();
			String unique_key = uniqueKey.toString();
			ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			response = updateSVNProject(responseData, applicationInfo, appDirName, repoInfo, unique_key, displayName);
			return response;
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Adds the project to repository.
	 * @param appDirName
	 * @param repoInfo
	 * @param userId
	 * @param projectId
	 * @param appId
	 * @param displayName
	 * @return
	 */
	@POST
	@Path("/addProjectToRepo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProjectToRepo(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, RepoInfo repoInfo,
			@QueryParam(REST_QUERY_USERID) String userId, @QueryParam(REST_QUERY_PROJECTID) String projectId,
			@QueryParam(REST_QUERY_APPID) String appId, @QueryParam("displayName") String displayName) {
		RepoDetail srcRepoDetail = repoInfo.getSrcRepoDetail();
		String type = srcRepoDetail.getType();
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		return addToRepo(appDirName, repoInfo, userId, projectId, appId, type, unique_key, displayName);
	}

	/**
	 * Commit imported project to repository.
	 *
	 * @param repoInfo the repodetail
	 * @param appDirName the app dir name
	 * @return the response
	 */
	@POST
	@Path("/commitProjectToRepo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response commitImportedProject(RepoInfo repoInfo, @QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,  @QueryParam("displayName") String displayName) {
		ResponseInfo responseData = null;
		Response response = null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		try {
			ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			RepoDetail srcRepoDetail = repoInfo.getSrcRepoDetail();
			RepoDetail phrescoRepoDetail = repoInfo.getPhrescoRepoDetail();
			RepoDetail testRepoDetail = repoInfo.getTestRepoDetail();
			StringBuilder rootDir = new StringBuilder(Utility.getProjectHome()).append(appDirName);
			File phrescoDir = new File(rootDir.toString(), appDirName + Constants.SUFFIX_PHRESCO);
			if(phrescoRepoDetail != null) {
				if(phrescoDir.exists() && phrescoDir.isDirectory()) {
					response = commitProject(phrescoRepoDetail, applicationInfo, displayName, unique_key, phrescoDir);
				}
			}
			File srcDir = new File(rootDir.toString(), appDirName);
			if(!srcDir.exists()) {
				srcDir = new File(rootDir.toString());
			}
			if(srcRepoDetail != null) {
				response = commitProject(srcRepoDetail, applicationInfo, displayName, unique_key, srcDir);
			}
			
			if(testRepoDetail != null) {
				File pomFile = null;
				if(StringUtils.isNotEmpty(applicationInfo.getPhrescoPomFile())) {
					pomFile = new File(phrescoDir, applicationInfo.getPhrescoPomFile());
				} else {
					pomFile = new File(srcDir, applicationInfo.getPomFile());
				}
				PomProcessor processor = new PomProcessor(pomFile);
				String testDirName = processor.getProperty("phresco.split.test.dir");
				File testDir = new File(rootDir.toString(), testDirName);
				response = commitProject(testRepoDetail, applicationInfo, displayName, unique_key, testDir);
			}
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoPomException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
		return response;
	}
	
	private Response commitProject(RepoDetail repoDetail, ApplicationInfo applicationInfo, 
			String displayName, String unique_key, File workingDir) {
		Response response = null;
		String type = repoDetail.getType();
		if (type.equals(SVN)) {
			response = commitSVNProject(repoDetail, applicationInfo, unique_key, displayName);
		} else if (type.equals(GIT)) {
			response = commitGitProject(applicationInfo, repoDetail, type, unique_key, displayName, workingDir);
		} else if (type.equals(BITKEEPER)) {
			response = commitBitKeeperProject(applicationInfo, repoDetail, type, unique_key, displayName, workingDir);
		}
		return response;
	}
	/**
	 * Fetch pop up values.
	 *
	 * @param appDirName the app dir name
	 * @param action the action
	 * @param userId the user id
	 * @return the response
	 */
	@GET
	@Path("/popupValues")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchPopUpValues(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,
			@QueryParam(REST_QUERY_ACTION) String action, @QueryParam(REST_QUERY_USERID) String userId) {
		Response response = null;
		if (action.equals(COMMIT)) {
			response = repoExistCheckForCommit(appDirName, action, userId);
		} else if (action.equals(FrameworkConstants.UPDATE)) {
			response = repoExistCheckForUpdate(appDirName, action, userId);
		}
		return response;
	}

	/**
	 * Fetch log messages.
	 *
	 * @param repodetail the repodetail
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/logMessages")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchLogMessages(RepoDetail repodetail) throws PhrescoException {
		List<String> restrictedLogs = null;
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
		try {
			SCMManagerImpl scmi = new SCMManagerImpl();
			List<String> svnLogMessages = scmi.getSvnLogMessages(repodetail.getRepoUrl(), repodetail.getUserName(),
					repodetail.getPassword());
			restrictedLogs = restrictLogs(svnLogMessages);
		} catch (PhrescoException e) {
			if (e.getLocalizedMessage().contains("Authorization Realm")) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210023;
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()),
						null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			} else if (e.getLocalizedMessage().contains("OPTIONS request failed on")
					|| (e.getLocalizedMessage().contains("PROPFIND") && e.getLocalizedMessage().contains(
							"405 Method Not Allowed"))
					|| e.getLocalizedMessage().contains("Repository moved temporarily to")) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210038;
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()),
						null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210039;
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()),
						null, status ,errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
		}
		status = RESPONSE_STATUS_SUCCESS;
		successCode = PHR200023;
		ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null,
				restrictedLogs, status, successCode);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Restrict logs.
	 *
	 * @param svnLogMessages the svn log messages
	 * @return the list
	 */
	private List<String> restrictLogs(List<String> svnLogMessages) {
		List<String> Messages = new ArrayList<String>();
		if (svnLogMessages.size() > 5) {
			for (int i = svnLogMessages.size() - 5; i <= svnLogMessages.size() - 1; i++) {
				Messages.add(svnLogMessages.get(i));
			}
		} else {
			for (int i = 0; i <= svnLogMessages.size() - 1; i++) {
				Messages.add(svnLogMessages.get(i));
			}
		}
		return Messages;
	}

	/**
	 * Import svn application.
	 *
	 * @param type the type
	 * @param repoInfo the repodetail
	 * @return the response
	 */
	private Response importSVNApplication(RepoInfo repoInfo, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		String revision = "";
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		try {
			ApplicationInfo importProject = scmi.importProject(repoInfo, displayName, unique_key);
			if(repoInfo.isSplitTest()) {
				scmi.importTest(importProject, repoInfo);
				}
			if(repoInfo.isSplitPhresco()) {
				scmi.importPhresco(importProject, repoInfo);
			}
			scmi.updateSCMConnection(repoInfo, importProject);
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200017;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (SVNAuthenticationException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210023;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (SVNException e) {
			if (e.getMessage().indexOf(SVN_FAILED) != -1) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210024;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			} else if (e.getMessage().indexOf(SVN_INTERNAL) != -1) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210025;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210026;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
		} catch (FileExistsException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210027;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210026;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210026;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} finally {
			try {
				LockUtil.removeLock(unique_key);
			} catch (PhrescoException e) {
			}
		}
	}

	/**
	 * Import git application.
	 *
	 * @param type the type
	 * @param repodetail the repodetail
	 * @return the response
	 */
	private Response importGITApplication(String type, RepoInfo repodetail, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		try {
			ApplicationInfo importProject = scmi.importProject(repodetail, displayName, unique_key);
			if (importProject != null) {
				status = RESPONSE_STATUS_SUCCESS;
				successCode = PHR200017;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
						.build();
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210022;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
		} catch (FileExistsException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210027;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210026;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210026;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} finally {
			try {
				LockUtil.removeLock(unique_key);
			} catch (PhrescoException e) {
			}
		}
	}

	/**
	 * Import bit keeper application.
	 *
	 * @param type the type
	 * @param repodetail the repodetail
	 * @return the response
	 */
	private Response importBitKeeperApplication(String type, RepoInfo repodetail, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		try {
			ApplicationInfo importProject = scmi.importProject(repodetail, displayName, unique_key);
			if (importProject != null) {
				status = RESPONSE_STATUS_SUCCESS;
				successCode = PHR200017;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
						.build();
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210022;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
		} catch (Exception e) {
			if ("Project already imported".equals(e.getLocalizedMessage())) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210027;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			} else if ("Failed to import project".equals(e.getLocalizedMessage())) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210026;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210026;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
		} finally {
			try {
				LockUtil.removeLock(unique_key);
			} catch (PhrescoException e) {
			}
		}
	}
	
	
	
	/**
	 * Fetch pop up values.
	 *
	 * @param appDirName the app dir name
	 * @param action the action
	 * @param userId the user id
	 * @return the response
	 */
	@GET
	@Path("/browseBuildRepo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFolderStructure(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_USERID) String userId, @QueryParam(REST_QUERY_PROJECTID) String projectId,
			@QueryParam(REST_QUERY_MODULE_NAME) String moduleName) {
		Response response = null;
		ResponseInfo<List<DOMSource>> responseData = new ResponseInfo<List<DOMSource>>();
		JSONObject  domSourceObject = new JSONObject();
		List<String> urls = new ArrayList<String>();
		Document document = null;
		DOMSource domSource = null;
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager != null) {
				com.photon.phresco.commons.model.RepoInfo repo = serviceManager.getCustomer(customerId).getRepoInfo();
				String releaseRepoURL = repo.getReleaseRepoURL();
				String snapshotRepoURL = repo.getSnapshotRepoURL();
				List<ApplicationInfo> appInfos = FrameworkServiceUtil.getAppInfos(customerId, projectId);
				for (ApplicationInfo applicationInfo : appInfos) {
					Artifact artifact = readArtifact(applicationInfo, moduleName);
					if (StringUtils.isNotEmpty(releaseRepoURL)) {
						urls.add(releaseRepoURL);
					} 
					if (StringUtils.isNotEmpty(snapshotRepoURL)) {
						urls.add(snapshotRepoURL);
					}
					document = constructDomSource(artifact, applicationInfo.getAppDirName(), urls);
					domSource = new DOMSource();
					domSource.setNode(document);
					domSourceObject.put(applicationInfo.getAppDirName(), domSource);
				}
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, domSourceObject, status, successCode);
				response = Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
				.build();
			}
		}  catch (PhrescoException e) {
			ResponseInfo<String> finalOuptut = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR610020);
			return Response.status(Status.OK).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		}
		return response;
	}
	
	private Artifact readArtifact(ApplicationInfo appInfo, String moduleName) {
		Artifact artifact = null;
		try {
			String appDirPath = Utility.getProjectHome() + File.separator + appInfo.getAppDirName();
			String pomPath = Utility.getpomFileLocation(appDirPath, moduleName);
			File pomFile = new File (pomPath);
			if (pomFile.exists()) {
				PomProcessor processor = new PomProcessor(pomFile);
				String version = "[" + processor.getVersion() + ",)";
				artifact = new DefaultArtifact(processor.getGroupId(), processor.getArtifactId(), "", processor.getPackage(), version);
			}
		} catch (PhrescoPomException e) {
			e.printStackTrace();
		} catch (PhrescoException e) {
			e.printStackTrace();
		}
		return artifact;
	}

	private Response importPerforceApplication(String type, RepoDetail repodetail, String displayName) throws Exception {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		try {
			ApplicationInfo importProject=scmi.importFromPerforce(repodetail, new File(""));
			if (importProject != null) {
				status = RESPONSE_STATUS_SUCCESS;
				successCode = PHR200017;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
						.build();
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210022;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
		} catch (FileExistsException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210027;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210026;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (ConnectionException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210049;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} 
		catch (RequestException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210050;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} 
		catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210026;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} finally {
			try {
				LockUtil.removeLock(unique_key);
			} catch (PhrescoException e) {
			}
		}
	
			
	}
	
	/**
	 * Update git project.
	 *
	 * @param responseData the response data
	 * @param type the type
	 * @param applicationInfo the application info
	 * @param repodetail the repodetail
	 * @return the response
	 */
	private Response updateGitProject(ResponseInfo responseData, String type, ApplicationInfo applicationInfo,
			RepoDetail repodetail, String uniqueKey, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), FrameworkConstants.UPDATE, displayName, uniqueKey)), true);
			
			scmi.updateProject(repodetail, new File(""));
			
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200018;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (InvalidRemoteException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210024;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (TransportException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210024;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (SVNAuthenticationException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210023;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (SVNException e) {
			if (e.getMessage().indexOf(SVN_FAILED) != -1) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210024;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			} else if (e.getMessage().indexOf(SVN_INTERNAL) != -1) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210025;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210028;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
		} catch (FileExistsException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210027;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}

	/**
	 * Update svn project.
	 *
	 * @param responseData the response data
	 * @param type the type
	 * @param applicationInfo the application info
	 * @param repodetail the repodetail
	 * @return the response
	 */
	private Response updateSVNProject(ResponseInfo responseData, ApplicationInfo applicationInfo,
			String appDirName, RepoInfo repoInfo, String uniqueKey, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		String revision = "";
//		revision = !HEAD_REVISION.equals(repoInfo.getRevision()) ? repoInfo.getRevisionVal() : repoInfo
//				.getRevision();
		try {
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), 
					FrameworkConstants.UPDATE, displayName, uniqueKey)), true);
			RepoDetail srcRepoDetail = repoInfo.getSrcRepoDetail();
			RepoDetail testRepoDetail = repoInfo.getTestRepoDetail();
			RepoDetail phrescoRepoDetail = repoInfo.getPhrescoRepoDetail();
			StringBuilder workingDir = new StringBuilder(Utility.getProjectHome()).append(appDirName);
			String sourceFolderName = "";
			String testFolderName = "";
			String phrescoPomFileName = applicationInfo.getPhrescoPomFile();
			File splitPhrescoDir = new File(workingDir.toString(), appDirName + "-phresco");
			if(splitPhrescoDir.exists() && StringUtils.isNotEmpty(phrescoPomFileName)) {
				File phrescoPomFile = new File(splitPhrescoDir, phrescoPomFileName);
				PomProcessor processor = new PomProcessor(phrescoPomFile);
				sourceFolderName = processor.getProperty("phresco.src.dir");
				processor.getProperty("phresco.test.dir");
			}
			if(phrescoRepoDetail != null) {
				scmi.updateProject(phrescoRepoDetail, splitPhrescoDir);
			}
			if(srcRepoDetail != null) {
				if(StringUtils.isNotEmpty(sourceFolderName)) {
					workingDir.append(File.separator).append(sourceFolderName);
				}
				scmi.updateProject(srcRepoDetail, new File(workingDir.toString()));
			}
			if(testRepoDetail != null) {
				StringBuilder testDir = null;
				if(StringUtils.isNotEmpty(testFolderName)) {	
					testDir = new StringBuilder(Utility.getProjectHome()).append(appDirName);
					testDir.append(File.separator).append(testFolderName);
				}
				scmi.updateProject(srcRepoDetail, new File(testDir.toString()));
			}
			scmi.updateSCMConnection(repoInfo, applicationInfo);	
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200018;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null,
					null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (InvalidRemoteException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210024;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (TransportException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210024;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (SVNAuthenticationException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210023;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (SVNException e) {
			if (e.getMessage().indexOf(SVN_FAILED) != -1) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210024;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			} else if (e.getMessage().indexOf(SVN_INTERNAL) != -1) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210025;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			} else if (e.getMessage().indexOf(SVN_IS_NOT_WORKING_COPY) != -1) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210029;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210028;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
		} catch (FileExistsException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210027;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}

	/**
	 * Update bit keeper project.
	 *
	 * @param type the type
	 * @param applicationInfo the application info
	 * @param repodetail the repodetail
	 * @return the response
	 */
	private Response updateBitKeeperProject(String type, ApplicationInfo applicationInfo, RepoDetail repodetail, String uniqueKey, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
	    	//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), FrameworkConstants.UPDATE, displayName, uniqueKey)), true);
			scmi.updateProject(repodetail, new File(""));
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200018;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (PhrescoException e) {
			if (e.getLocalizedMessage().contains("Nothing to pull")) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210030;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210028;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}
	
	private Response updatePerforceProject(String type, ApplicationInfo applicationInfo, RepoDetail repodetail, String uniqueKey, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
	    	//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), FrameworkConstants.UPDATE, displayName, uniqueKey)), true);
			scmi.updateProject(repodetail, new File(""));
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200018;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (PhrescoException e) {
			if (e.getLocalizedMessage().contains("Nothing to pull")) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210030;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210028;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
		} catch (ConnectionException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210049;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (RequestException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210050;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}
	/**
	 * Adds the svn project.
	 *
	 * @param appDirName the app dir name
	 * @param repodetail the repodetail
	 * @param userId the user id
	 * @param projectId the project id
	 * @param appId the app id
	 * @param type the type
	 * @return the response
	 */
	private Response addToRepo(String appDirName, RepoInfo repoInfo, String userId, String projectId,
			String appId, String type, String uniqueKey, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
			ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), ADD_TO_REPO, displayName, uniqueKey)), true);
			scmi.importToRepo(repoInfo, applicationInfo);
			User user = ServiceManagerImpl.USERINFO_MANAGER_MAP.get(userId);
			// updateLatestProject(user, projectId, appId);
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200019;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null,
					null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("git-receive-pack not found")) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210032;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()),
						null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210031;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}

	/**
	 * Commit svn project.
	 *
	 * @param repodetail the repodetail
	 * @return the response
	 */
	public Response commitSVNProject(RepoDetail repodetail, ApplicationInfo applicationInfo, String uniqueKey, String displayName) {
		ResponseInfo responseData = new ResponseInfo();
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			if (CollectionUtils.isNotEmpty(repodetail.getCommitableFiles())) {
				List<File> listModifiedFiles = new ArrayList<File>(repodetail.getCommitableFiles().size());
				for (String commitableFile : repodetail.getCommitableFiles()) {
					listModifiedFiles.add(new File(commitableFile));
				}
				//To generate the lock for the particular operation
				LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), COMMIT, displayName, uniqueKey)), true);
				
				scmi.commitSpecifiedFiles(listModifiedFiles, repodetail.getUserName(), repodetail.getPassword(),
						repodetail.getCommitMessage());
			}
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200020;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null,
					null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210033;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}

	/**
	 * Commit bit keeper project.
	 *
	 * @param appDirName the app dir name
	 * @param repodetail the repodetail
	 * @param type the type
	 * @return the response
	 */
	public Response commitBitKeeperProject(ApplicationInfo applicationInfo, RepoDetail repodetail, 
			String type, String uniqueKey, String displayName, File workingDir) {
		ResponseInfo responseData = new ResponseInfo();
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), COMMIT , displayName, uniqueKey)), true);
			scmi.commitToRepo(repodetail, workingDir);
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200020;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			if (e.getLocalizedMessage().contains("Nothing to push")) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210034;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210033;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), COMMIT_PROJECT_FAIL, null);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210033;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}

	/**
	 * Commit git project.
	 *
	 * @param appDirName the app dir name
	 * @param repodetail the repodetail
	 * @param type the type
	 * @return the response
	 */
	public Response commitGitProject(ApplicationInfo applicationInfo, RepoDetail repodetail, String type, 
			String uniqueKey, String displayName, File workingDir) {
		ResponseInfo responseData = new ResponseInfo();
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), COMMIT , displayName, uniqueKey)), true);
			if (!repodetail.getCommitableFiles().isEmpty()) {
				scmi.commitToRepo(repodetail, workingDir);
			}
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200020;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210033;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}

	/**
	 * Gets the repo type.
	 *
	 * @param repoUrl the repo url
	 * @return the repo type
	 */
	private String getRepoType(String repoUrl) {
		String repoType = "";
		if (repoUrl.startsWith("bk")) {
			repoType = BITKEEPER;
		} else if (repoUrl.endsWith(".git") || repoUrl.contains("gerrit") || repoUrl.startsWith("ssh")) {
			repoType = GIT;
		} else if (repoUrl.contains("svn")) {
			repoType = SVN;
		}
		return repoType;
	}

	/**
	 * Repo exist check for commit.
	 *
	 * @param appDirName the app dir name
	 * @param action the action
	 * @param userId the user id
	 * @return the response
	 */
	private Response repoExistCheckForCommit(String appDirName, String action, String userId) {
		RepoDetail repodetail = new RepoDetail();
		boolean setRepoExistForCommit = false;
		ResponseInfo<RepoDetail> responseData = new ResponseInfo<RepoDetail>();
		try {
			ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			File pomFile = getPomFromWrokDir(applicationInfo);
			PomProcessor processor = new PomProcessor(pomFile);
			String srcRepoURL = processor.getProperty(Constants.POM_PROP_KEY_SRC_REPO_URL);
			String dotPhresoRepoURL = processor.getProperty(Constants.POM_PROP_KEY_PHRESCO_REPO_URL);
			String testRepoURL = processor.getProperty(Constants.POM_PROP_KEY_TEST_REPO_URL);
			if(StringUtils.isEmpty(srcRepoURL)) {
				srcRepoURL = getConnectionUrl(applicationInfo);
			}
			StringBuilder rootDir = new StringBuilder(Utility.getProjectHome()).append(appDirName);
			File phrescoDir = new File(rootDir.toString(), appDirName + Constants.SUFFIX_PHRESCO);
			if(phrescoDir.exists() && StringUtils.isNotEmpty(dotPhresoRepoURL)) {
				RepoDetail phrescoRepoDetail = createRepoDetail(dotPhresoRepoURL, userId, action, phrescoDir);
			}
			File srcDir = new File(rootDir.toString(), appDirName);
			if(!srcDir.exists()) {
				srcDir = new File(rootDir.toString());
			}
			if(srcDir.exists() && StringUtils.isNotEmpty(srcRepoURL)) {
				RepoDetail srcRepoDetail = createRepoDetail(srcRepoURL, userId, action, srcDir);
			}
			String testDirName = processor.getProperty("phresco.split.test.dir");
			File testDir = new File(rootDir.toString(), testDirName);
			if(testDir.exists() && StringUtils.isNotEmpty(testRepoURL)) {
				RepoDetail testRepoDetail = createRepoDetail(testRepoURL, userId, action, testDir);
			}
			
			
			
//			if (!repodetail.isRepoExist()) {
//				status = RESPONSE_STATUS_FAILURE;
//				errorCode = PHR210035;
//				ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, null,
//						repodetail, status, errorCode);
//				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
//						.build();
//			}

		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210036;
			ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoPomException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210036;
			ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
		status = RESPONSE_STATUS_SUCCESS;
		successCode = PHR200021;
		ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, null,
				repodetail, status, successCode);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}
	
	private RepoDetail createRepoDetail(String repoUrl, String userId, String action, File workingDir) throws PhrescoException {
		RepoDetail repodetail = new RepoDetail();
		repodetail.setRepoUrl(repoUrl);
		boolean setRepoExistForCommit = false;
		try {
			if (repoUrl.startsWith("bk")) {
				repodetail.setRepoExist(true);
			} else if (repoUrl.endsWith(".git") || repoUrl.contains("gerrit") || repoUrl.startsWith("ssh")) {
				setRepoExistForCommit  = checkGitProject(workingDir, setRepoExistForCommit);
				repodetail.setRepoExist(setRepoExistForCommit);
				repodetail = updateProjectPopup(workingDir, action, repodetail);
			} else if (!setRepoExistForCommit) {
				repodetail = updateProjectPopup(workingDir, action, repodetail);
			}
			repodetail.setUserName(userId);
			repodetail.setType(getRepoType(repoUrl));
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
		return repodetail;
	}
	
	/**
	 * Repo exist check for update.
	 *
	 * @param appDirName the app dir name
	 * @param action the action
	 * @param userId the user id
	 * @return the response
	 */
	private Response repoExistCheckForUpdate(String appDirName, String action, String userId) {
		RepoInfo repoInfo = new RepoInfo();
		ResponseInfo<RepoDetail> responseData = new ResponseInfo<RepoDetail>();
		try {
			ApplicationInfo appInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			File pomFile = getPomFromWrokDir(appInfo);
			PomProcessor processor = new PomProcessor(pomFile);
			String srcRepoURL = processor.getProperty(Constants.POM_PROP_KEY_SRC_REPO_URL);
			String dotPhresoRepoURL = processor.getProperty(Constants.POM_PROP_KEY_PHRESCO_REPO_URL);
			String testRepoURL = processor.getProperty(Constants.POM_PROP_KEY_TEST_REPO_URL);
			RepoDetail sourceRepoDetail = new RepoDetail();
			if(StringUtils.isNotEmpty(srcRepoURL)) {
				fillRepoDetail(sourceRepoDetail, srcRepoURL, userId, true);
			} else {
				srcRepoURL = getConnectionUrl(appInfo);
				if(StringUtils.isNotEmpty(srcRepoURL)) {
					fillRepoDetail(sourceRepoDetail, srcRepoURL, userId, true);
				} else {
					sourceRepoDetail.setRepoExist(false);
				}
			}
			RepoDetail dotPhrescoRepoDetail = new RepoDetail();
			if(StringUtils.isNotEmpty(dotPhresoRepoURL)) {
				fillRepoDetail(dotPhrescoRepoDetail, dotPhresoRepoURL, userId, true);
			} else {
				dotPhrescoRepoDetail.setRepoExist(false);
			}
			RepoDetail testRepoDetail = new RepoDetail();
			if(StringUtils.isNotEmpty(testRepoURL)) {
				fillRepoDetail(testRepoDetail, testRepoURL, userId, true);
			} else {
				testRepoDetail.setRepoExist(false);
			}
			repoInfo.setSrcRepoDetail(sourceRepoDetail);
			repoInfo.setPhrescoRepoDetail(dotPhrescoRepoDetail);
			repoInfo.setTestRepoDetail(testRepoDetail);
//			if (!repoInfo.isRepoExist()) {
//				status = RESPONSE_STATUS_FAILURE;
//				errorCode = PHR210037;
//				ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, null,
//						repoInfo, status, errorCode);
//				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
//						.build();
//			}

		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210036;
			ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoPomException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210036;
			ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
		status = RESPONSE_STATUS_SUCCESS;
		successCode = PHR200022;
		ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, null,
				repoInfo, status, successCode);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}
	
	private void fillRepoDetail(RepoDetail repoDetail, String url, String userId, boolean exist) {
		repoDetail.setUserName(userId);
		repoDetail.setRepoUrl(url);
		repoDetail.setRepoExist(exist);
		repoDetail.setType(getRepoType(url));
	}
	
	private File getPomFromWrokDir(ApplicationInfo appInfo) {
		String appDirName = appInfo.getAppDirName();
		StringBuilder filePath = new StringBuilder(Utility.getProjectHome()).append(appDirName);
		File dotPhresoFile = new File(filePath.toString(), appDirName + Constants.SUFFIX_PHRESCO);
		if(dotPhresoFile.exists() && dotPhresoFile.isDirectory()) {
			if(StringUtils.isNotEmpty(appInfo.getAppDirName())) {
				filePath.append(File.separator).append(appDirName + Constants.SUFFIX_PHRESCO).append(File.separator).append(appInfo.getPhrescoPomFile());
				return new File(filePath.toString());
			}
		}
		File sourceFile = new File(filePath.toString(), appDirName);
		if(sourceFile.exists() && sourceFile.isDirectory()) {
			filePath.append(File.separator).append(appDirName).append(File.separator).append(appInfo.getPomFile());
			return new File(filePath.toString());
		}
		
		return new File(filePath.toString(), appInfo.getPomFile());
	}

	/**
	 * Update project popup.
	 *
	 * @param appDirName the app dir name
	 * @param action the action
	 * @param repodetail the repodetail
	 * @return the repo detail
	 */
	private RepoDetail updateProjectPopup(File workingDir, String action, RepoDetail repodetail) {
		String repoUrl = repodetail.getRepoUrl();
		boolean setRepoExistForCommit = false;
		ResponseInfo<List<RepoFileInfo>> responseData = new ResponseInfo<List<RepoFileInfo>>();
		List<RepoFileInfo> commitableFiles = null;
		try {
			setRepoExistForCommit = true;
			// getting commitable files for SVN repo
			if (COMMIT.equals(action) && !repoUrl.contains(BITKEEPER)
					&& !repoUrl.contains(GIT)) {
				commitableFiles = svnCommitableFiles(workingDir);

				// getting commitable files for Git repo
			} else if (COMMIT.equals(action) && !repoUrl.contains(BITKEEPER)
					&& !repoUrl.contains(SVN)) {
				commitableFiles = gitCommitableFiles(workingDir);
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

	/**
	 * Gets the connection url.
	 *
	 * @param applicationInfo the application info
	 * @return the connection url
	 * @throws PhrescoException the phresco exception
	 */
	private String getConnectionUrl(ApplicationInfo applicationInfo) throws PhrescoException {
		try {
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			PomProcessor processor = frameworkUtil.getPomProcessor(applicationInfo);
			Scm scm = processor.getSCM();
			if (scm != null && !scm.getConnection().isEmpty()) {
				return scm.getConnection();
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}

		return "";
	}

	/**
	 * Check git project.
	 *
	 * @param applicationInfo the application info
	 * @param setRepoExistForCommit the set repo exist for commit
	 * @return true, if successful
	 * @throws PhrescoException the phresco exception
	 */
	private boolean checkGitProject(File  workingDir, boolean setRepoExistForCommit)
			throws PhrescoException {
		setRepoExistForCommit = true;
		String url = "";
		InitCommand initCommand = Git.init();
		initCommand.setDirectory(workingDir);
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

	/**
	 * Svn commitable files.
	 *
	 * @param appDirName the app dir name
	 * @return the list
	 * @throws PhrescoException the phresco exception
	 */
	private List<RepoFileInfo> svnCommitableFiles(File workingDir) throws PhrescoException {
		List<RepoFileInfo> commitableFiles = null;
		String revision = "";
		try {
			SCMManagerImpl scmi = new SCMManagerImpl();
			revision = HEAD_REVISION;
			commitableFiles = scmi.getCommitableFiles(workingDir, revision);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return commitableFiles;
	}

	/**
	 * Git commitable files.
	 *
	 * @param appDirName the app dir name
	 * @return the list
	 * @throws PhrescoException the phresco exception
	 */
	private List<RepoFileInfo> gitCommitableFiles(File workingDir) throws PhrescoException {
		List<RepoFileInfo> gitCommitableFiles = null;
		try {
			SCMManagerImpl scmi = new SCMManagerImpl();
			gitCommitableFiles = scmi.getGITCommitableFiles(workingDir);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return gitCommitableFiles;
	}

	/**
	 * Update latest project.
	 *
	 * @param user the user
	 * @param projectId the project id
	 * @param appId the app id
	 * @throws PhrescoException the phresco exception
	 */
	private void updateLatestProject(User user, String projectId, String appId) throws PhrescoException {
		try {
			File tempPath = new File(Utility.getPhrescoTemp() + File.separator + USER_PROJECT_JSON);
			JSONObject userProjJson = null;
			JSONParser parser = new JSONParser();
			if (tempPath.exists()) {
				FileReader reader = new FileReader(tempPath);
				userProjJson = (JSONObject) parser.parse(reader);
				reader.close();
			} else {
				userProjJson = new JSONObject();
			}

			userProjJson.put(user.getId(), projectId + Constants.STR_COMMA + appId);
			FileWriter writer = new FileWriter(tempPath);
			writer.write(userProjJson.toString());
			writer.close();
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (ParseException e) {
			throw new PhrescoException(e);
		}
	}
	
	private static Document constructDomSource(Artifact artifactInfo, String appDirName, List<String> urls) throws PhrescoException {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(ROOT);
			doc.appendChild(rootElement);

			Element rootItem = doc.createElement(ITEM);
			rootItem.setAttribute(TYPE, FOLDER);
			rootItem.setAttribute(PATH, "");
			rootItem.setAttribute(NAME, ROOT_ITEM);

			Element applicationItem = doc.createElement(ITEM);
			applicationItem.setAttribute(TYPE, FOLDER);
			applicationItem.setAttribute(PATH, "");
			applicationItem.setAttribute(NAME, appDirName);

			Element snapshot = doc.createElement(ITEM);
			snapshot.setAttribute(TYPE, FOLDER);
			snapshot.setAttribute(PATH, "");
			snapshot.setAttribute(NAME, SNAPSHOT);
			applicationItem.appendChild(snapshot);


			Element release = doc.createElement(ITEM);
			release.setAttribute(TYPE, FOLDER);
			release.setAttribute(PATH, "");
			release.setAttribute(NAME, RELEASE);
			applicationItem.appendChild(release);

			rootElement.appendChild(rootItem);

			rootItem.appendChild(applicationItem);

			RepositorySystem system = newRepositorySystem();
			RepositorySystemSession session = newRepositorySystemSession( system );
			Artifact artifact = new DefaultArtifact(artifactInfo.getGroupId(), artifactInfo.getArtifactId(), "", artifactInfo.getExtension(), artifactInfo.getVersion());
			for (String url : urls) {
				RemoteRepository repo =  new RemoteRepository("", DEFAULT, url);
				VersionRangeRequest rangeRequest = new VersionRangeRequest();
				rangeRequest.setArtifact( artifact );
				rangeRequest.addRepository( repo );
				VersionRangeResult rangeResult = system.resolveVersionRange( session, rangeRequest);
				List<Version> versions = rangeResult.getVersions();
				constructArtifactItem(versions, applicationItem, artifact, repo, doc);
				clearCache(artifact);
			}
			return doc;
		} catch (VersionRangeResolutionException e) {
			throw new PhrescoException(e);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}


	private static void constructArtifactItem(List<Version> versions, Element applicationItem, Artifact artifactInfo, RemoteRepository repo, Document doc) throws PhrescoException {
		try {
			Element versionItem = null;
			Element element =  null;
			for (Version vers : versions) {
				String version = vers.toString();
				Artifact repoArtifact = new DefaultArtifact(artifactInfo.getGroupId(), artifactInfo.getArtifactId(), artifactInfo.getExtension(), version);
				MavenDefaultLayout defaultLayout = new MavenDefaultLayout();
				URI Paths = defaultLayout.getPath(repoArtifact);
				String artifactPath = (repo.getUrl() + repo.getId() + File.separator + Paths).replace("\\", "/");
				String pathString = Paths.toString();
				String fileName = pathString.substring(pathString.lastIndexOf("/") + 1);

				Element jarItem = doc.createElement(ITEM);
				jarItem.setAttribute(TYPE, FILE);
				jarItem.setAttribute(NAME, fileName);
				jarItem.setAttribute(PATH, artifactPath);

				versionItem = doc.createElement(ITEM);
				versionItem.setAttribute(TYPE, FOLDER);
				versionItem.setAttribute(NAME, version);
				versionItem.setAttribute(PATH, artifactPath);

				versionItem.appendChild(jarItem);
				
				if (version.contains(SNAPSHOT)) {
					XPath xpath = XPathFactory.newInstance().newXPath();
					String expression = SNAPSHOT_ITEM;
					Node node = (Node) xpath.compile(expression).evaluate(doc, XPathConstants.NODE);
					if (node != null) {
						element = (Element) node;
						element.appendChild(versionItem);
					}
				} else {
					XPath xpath = XPathFactory.newInstance().newXPath();
					String expression = RELEASE_ITEM;
					Node node = (Node) xpath.compile(expression).evaluate(doc, XPathConstants.NODE);
					if (node != null) {
						element = (Element) node;
						element.appendChild(versionItem);
					}
				}
			}
		} catch (DOMException e) {
			throw new PhrescoException(e);
		} catch (XPathExpressionException e) {
			throw new PhrescoException(e);
		}
	}
	
	private static RepositorySystem newRepositorySystem() throws PhrescoException {
		try {
			return new DefaultPlexusContainer().lookup(RepositorySystem.class);
		} catch (ComponentLookupException e) {
			throw new PhrescoException(e);
		} catch (PlexusContainerException e) {
			throw new PhrescoException(e);
		}
	}

	private static RepositorySystemSession newRepositorySystemSession(RepositorySystem system) {
		MavenRepositorySystemSession session = new MavenRepositorySystemSession();
		LocalRepository localRepo = new LocalRepository(Utility.getPhrescoTemp() + File.separator + REPO);
		session.setLocalRepositoryManager(system.newLocalRepositoryManager(localRepo));
		return session;
	}

	private static void clearCache(Artifact artifact) {
		String groupIds = artifact.getGroupId();
		StringBuffer pathBuilder = new StringBuffer();
		String[] pathSplited = groupIds.split(SEPARATOR_CONSTANT);
		for (String path : pathSplited) {
			pathBuilder.append(path);
			pathBuilder.append(File.separator);
		}
		File path = new File(Utility.getPhrescoTemp() + File.separator + REPO + File.separator + pathBuilder + artifact.getArtifactId() + File.separator +  "maven-metadata-.xml");
		if (path.exists()) {
			boolean delete = path.delete();
		}
	}
	
}