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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactGroupInfo;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.CoreOption;
import com.photon.phresco.commons.model.DownloadInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.SelectedFeature;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.commons.model.UserPermissions;
import com.photon.phresco.commons.model.WebService;
import com.photon.phresco.configuration.ConfigurationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.exception.PhrescoWebServiceException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.plugins.model.Mojos.ApplicationHandler;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.impl.ServiceManagerImpl;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class ProjectService.
 */
@Path("/project")
public class ProjectService extends RestBase implements FrameworkConstants, ServiceConstants {

	/**
	 * To return the List of available projects.
	 *
	 * @param customerId the customer id
	 * @return the response
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@QueryParam(REST_QUERY_CUSTOMERID) String customerId) {
		ResponseInfo<List<ProjectInfo>> responseData = new ResponseInfo<List<ProjectInfo>>();
		try {
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			List<ProjectInfo> projects = projectManager.discover(customerId);
			if (CollectionUtils.isNotEmpty(projects)) {
				Collections.sort(projects, sortByDateToLatest());
			}
			ResponseInfo<List<ProjectInfo>> finalOutput = responseDataEvaluation(responseData, null,
					"Project List Successfully", projects);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<ProjectInfo>> finalOutput = responseDataEvaluation(responseData, e,
					"Project List failed", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Appinfo list of a specific project .
	 *
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @return the response
	 */
	@GET
	@Path("/appinfos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response appinfoList(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId) {
		ResponseInfo<List<ApplicationInfo>> responseData = new ResponseInfo<List<ApplicationInfo>>();
		try {
			List<ApplicationInfo> appInfos = FrameworkServiceUtil.getAppInfos(customerId, projectId);
			if (CollectionUtils.isNotEmpty(appInfos)) {
				ResponseInfo<List<ProjectInfo>> finalOutput = responseDataEvaluation(responseData, null,
						"Application infos returned Successfully", appInfos);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
						.build();
			}
		} catch (PhrescoException e) {
			ResponseInfo<List<ProjectInfo>> finalOutput = responseDataEvaluation(responseData, e,
					"Application info failed", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
		ResponseInfo<List<ProjectInfo>> finalOutput = responseDataEvaluation(responseData, null,
				"No application to return", null);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Creates the project.
	 *
	 * @param projectinfo the projectinfo
	 * @param userId the user id
	 * @return the response
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProject(ProjectInfo projectinfo, @QueryParam(REST_QUERY_USERID) String userId) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<ProjectInfo> finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User",
						null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			ProjectInfo projectInfo = PhrescoFrameworkFactory.getProjectManager().create(projectinfo, serviceManager);
			ResponseInfo<ProjectInfo> finalOutput = responseDataEvaluation(responseData, null,
					"Project created Successfully", projectInfo);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<ProjectInfo> finalOutput = responseDataEvaluation(responseData, e, "Project creation failed",
					null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Edits the project.
	 *
	 * @param projectId the project id
	 * @param customerId the customer id
	 * @return the response
	 */
	@GET
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editProject(@QueryParam(REST_QUERY_PROJECTID) String projectId,
			@QueryParam(REST_QUERY_CUSTOMERID) String customerId) {
		ProjectInfo projectInfo = null;
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			projectInfo = projectManager.getProject(projectId, customerId);
			ResponseInfo<ProjectInfo> finalOutput = responseDataEvaluation(responseData, null,
					"Project edited Successfully", projectInfo);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<ProjectInfo> finalOutput = responseDataEvaluation(responseData, e, "Project edit failed", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Update project.
	 *
	 * @param projectinfo the projectinfo
	 * @param userId the user id
	 * @return the response
	 */
	@PUT
	@Path("/updateproject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProject(ProjectInfo projectinfo, @QueryParam(REST_QUERY_USERID) String userId) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<ProjectInfo> finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User",
						null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			ProjectInfo projectInfo = PhrescoFrameworkFactory.getProjectManager().update(projectinfo, serviceManager,
					null);
			ResponseInfo<ProjectInfo> finalOutput = responseDataEvaluation(responseData, null,
					"Project updated Successfully", projectInfo);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<ProjectInfo> finalOutput = responseDataEvaluation(responseData, e, "Project update failed",
					null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Update application features.
	 *
	 * @param selectedFeaturesFromUI the selected features from ui
	 * @param appDirName the app dir name
	 * @param userId the user id
	 * @param customerId the customer id
	 * @return the response
	 */
	@PUT
	@Path("/updateFeature")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateApplicationFeatures(List<SelectedFeature> selectedFeaturesFromUI,
			@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(REST_QUERY_USERID) String userId,
			@QueryParam(REST_QUERY_CUSTOMERID) String customerId) {
		File filePath = null;
		BufferedReader bufferedReader = null;
		Gson gson = new Gson();
		ResponseInfo responseData = new ResponseInfo();
		List<String> selectedFeatures = new ArrayList<String>();
		List<String> selectedJsLibs = new ArrayList<String>();
		List<String> selectedComponents = new ArrayList<String>();
		List<ArtifactGroup> listArtifactGroup = new ArrayList<ArtifactGroup>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			StringBuilder sbs = null;
			if (StringUtils.isNotEmpty(appDirName)) {
				sbs = new StringBuilder(Utility.getProjectHome()).append(appDirName).append(File.separator).append(
						Constants.DOT_PHRESCO_FOLDER).append(File.separator).append(PROJECT_INFO);
			}
			bufferedReader = new BufferedReader(new FileReader(sbs.toString()));
			Type type = new TypeToken<ProjectInfo>() {
			}.getType();
			ProjectInfo projectinfo = gson.fromJson(bufferedReader, type);
			ApplicationInfo applicationInfo = projectinfo.getAppInfos().get(0);
			if (CollectionUtils.isNotEmpty(selectedFeaturesFromUI)) {
				for (SelectedFeature selectedFeatureFromUI : selectedFeaturesFromUI) {
					String artifactGroupId = selectedFeatureFromUI.getModuleId();
					ArtifactGroup artifactGroup = serviceManager.getArtifactGroupInfo(artifactGroupId);
					ArtifactInfo artifactInfo = serviceManager.getArtifactInfo(selectedFeatureFromUI.getVersionID());
					artifactInfo.setScope(selectedFeatureFromUI.getScope());
					if (artifactInfo != null) {
						artifactGroup.setVersions(Collections.singletonList(artifactInfo));
					}
					List<CoreOption> appliesTo = artifactGroup.getAppliesTo();
					if (CollectionUtils.isNotEmpty(appliesTo)) {
						for (CoreOption coreOption : appliesTo) {
							if (coreOption.getTechId().equals(applicationInfo.getTechInfo().getId())) {
								artifactGroup.setAppliesTo(Collections.singletonList(coreOption));
								listArtifactGroup.add(artifactGroup);
								break;
							}
						}
					}
					if (selectedFeatureFromUI.getType().equals(ArtifactGroup.Type.FEATURE.name())) {
						selectedFeatures.add(selectedFeatureFromUI.getVersionID());
					}
					if (selectedFeatureFromUI.getType().equals(ArtifactGroup.Type.JAVASCRIPT.name())) {
						selectedJsLibs.add(selectedFeatureFromUI.getVersionID());
					}
					if (selectedFeatureFromUI.getType().equals(ArtifactGroup.Type.COMPONENT.name())) {
						selectedComponents.add(selectedFeatureFromUI.getVersionID());
					}
				}
			}
			if (StringUtils.isNotEmpty(appDirName)) {
				StringBuilder sb = new StringBuilder(Utility.getProjectHome()).append(appDirName)
						.append(File.separator).append(Constants.DOT_PHRESCO_FOLDER).append(File.separator).append(
								Constants.APPLICATION_HANDLER_INFO_FILE);
				filePath = new File(sb.toString());
			}
			MojoProcessor mojo = new MojoProcessor(filePath);
			ApplicationHandler applicationHandler = mojo.getApplicationHandler();
			// To write selected Features into
			// phresco-application-Handler-info.xml
			String artifactGroup = gson.toJson(listArtifactGroup);
			applicationHandler.setSelectedFeatures(artifactGroup);

			// To write Deleted Features into
			// phresco-application-Handler-info.xml
			List<ArtifactGroup> removedModules = getRemovedModules(applicationInfo, selectedFeaturesFromUI,
					serviceManager);
			Type jsonType = new TypeToken<Collection<ArtifactGroup>>() {
			}.getType();
			String deletedFeatures = gson.toJson(removedModules, jsonType);
			applicationHandler.setDeletedFeatures(deletedFeatures);

			mojo.save();

			applicationInfo.setSelectedModules(selectedFeatures);
			applicationInfo.setSelectedJSLibs(selectedJsLibs);
			applicationInfo.setSelectedComponents(selectedComponents);

			projectinfo.setAppInfos(Collections.singletonList(applicationInfo));
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			projectManager.update(projectinfo, serviceManager, appDirName);
		} catch (FileNotFoundException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "update Feature failed", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "update Feature failed", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (IOException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "update Feature failed", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
		ResponseInfo finalOutput = responseDataEvaluation(responseData, null, "Features updated successfully", null);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

	}

	/**
	 * Update application.
	 *
	 * @param oldAppDirName the old app dir name
	 * @param appInfo the app info
	 * @param userId the user id
	 * @param customerId the customer id
	 * @return the response
	 */
	@PUT
	@Path("/updateApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateApplication(@QueryParam(REST_QUERY_OLD_APPDIR_NAME) String oldAppDirName,
			ApplicationInfo appInfo, @QueryParam(REST_QUERY_USERID) String userId,
			@QueryParam(REST_QUERY_CUSTOMERID) String customerId) {
		ResponseInfo<ApplicationInfo> responseData = new ResponseInfo<ApplicationInfo>();
		BufferedReader bufferedReader = null;
		File filePath = null;
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<ApplicationInfo> finalOutput = responseDataEvaluation(responseData, null,
						"UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			List<DownloadInfo> selectedServerGroup = new ArrayList<DownloadInfo>();
			List<DownloadInfo> selectedDatabaseGroup = new ArrayList<DownloadInfo>();

			Gson gson = new Gson();

			StringBuilder sb = new StringBuilder(Utility.getProjectHome()).append(oldAppDirName).append(File.separator)
					.append(Constants.DOT_PHRESCO_FOLDER).append(File.separator).append(
							Constants.APPLICATION_HANDLER_INFO_FILE);
			filePath = new File(sb.toString());
			MojoProcessor mojo = new MojoProcessor(filePath);
			ApplicationHandler applicationHandler = mojo.getApplicationHandler();
			// To write selected Database into phresco-application-Handler-info.xml
			List<ArtifactGroupInfo> selectedDatabases = appInfo.getSelectedDatabases();
			if (CollectionUtils.isNotEmpty(selectedDatabases)) {
				for (ArtifactGroupInfo selectedDatabase : selectedDatabases) {
					DownloadInfo downloadInfo = serviceManager.getDownloadInfo(selectedDatabase.getArtifactGroupId());
					String id = downloadInfo.getArtifactGroup().getId();
					ArtifactGroup artifactGroupInfo = serviceManager.getArtifactGroupInfo(id);
					List<ArtifactInfo> dbVersionInfos = artifactGroupInfo.getVersions();
					// for selected version infos from ui
					List<ArtifactInfo> selectedDBVersionInfos = new ArrayList<ArtifactInfo>();
					for (ArtifactInfo versionInfo : dbVersionInfos) {
						String versionId = versionInfo.getId();
						if (selectedDatabase.getArtifactInfoIds().contains(versionId)) {
							// Add selected version infos to list
							selectedDBVersionInfos.add(versionInfo);
						}
					}
					downloadInfo.getArtifactGroup().setVersions(selectedDBVersionInfos);
					selectedDatabaseGroup.add(downloadInfo);
				}
				if (CollectionUtils.isNotEmpty(selectedDatabaseGroup)) {
					String databaseGroup = gson.toJson(selectedDatabaseGroup);
					applicationHandler.setSelectedDatabase(databaseGroup);
				}
			} else {
				applicationHandler.setSelectedDatabase(null);
			}

			// To write selected Servers into phresco-application-Handler-info.xml
			List<ArtifactGroupInfo> selectedServers = appInfo.getSelectedServers();
			if (CollectionUtils.isNotEmpty(selectedServers)) {
				for (ArtifactGroupInfo selectedServer : selectedServers) {
					DownloadInfo downloadInfo = serviceManager.getDownloadInfo(selectedServer.getArtifactGroupId());
					String id = downloadInfo.getArtifactGroup().getId();
					ArtifactGroup artifactGroupInfo = serviceManager.getArtifactGroupInfo(id);
					List<ArtifactInfo> serverVersionInfos = artifactGroupInfo.getVersions();
					List<ArtifactInfo> selectedServerVersionInfos = new ArrayList<ArtifactInfo>();
					for (ArtifactInfo versionInfo : serverVersionInfos) {
						String versionId = versionInfo.getId();
						if (selectedServer.getArtifactInfoIds().contains(versionId)) {
							selectedServerVersionInfos.add(versionInfo);
						}
					}
					downloadInfo.getArtifactGroup().setVersions(selectedServerVersionInfos);
					selectedServerGroup.add(downloadInfo);
				}
				if (CollectionUtils.isNotEmpty(selectedServerGroup)) {
					String serverGroup = gson.toJson(selectedServerGroup);
					applicationHandler.setSelectedServer(serverGroup);
				}
			} else {
				applicationHandler.setSelectedServer(null);
			}

			// To write selected WebServices info to phresco-plugin-info.xml
			List<String> selectedWebservices = appInfo.getSelectedWebservices();
			List<WebService> webServiceList = new ArrayList<WebService>();
			if (CollectionUtils.isNotEmpty(selectedWebservices)) {
				for (String selectedWebService : selectedWebservices) {
					WebService webservice = serviceManager.getWebService(selectedWebService);
					webServiceList.add(webservice);
				}
				if (CollectionUtils.isNotEmpty(webServiceList)) {
					String serverGroup = gson.toJson(webServiceList);
					applicationHandler.setSelectedWebService(serverGroup);
				}
			} else {
				applicationHandler.setSelectedWebService(null);
			}

			mojo.save();
			StringBuilder sbs = null;
			if (StringUtils.isNotEmpty(oldAppDirName)) {
				sbs = new StringBuilder(Utility.getProjectHome()).append(oldAppDirName).append(File.separator).append(
						Constants.DOT_PHRESCO_FOLDER).append(File.separator).append("project.info");
			}
			bufferedReader = new BufferedReader(new FileReader(sbs.toString()));
			Type type = new TypeToken<ProjectInfo>() {
			}.getType();
			ProjectInfo projectInfo = gson.fromJson(bufferedReader, type);
			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);

			bufferedReader.close();
			deleteSqlFolder(applicationInfo, selectedDatabases, serviceManager, oldAppDirName);

			projectInfo.setAppInfos(Collections.singletonList(appInfo));
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			projectManager.update(projectInfo, serviceManager, oldAppDirName);
			List<ProjectInfo> projects = projectManager.discover(customerId);
			if (CollectionUtils.isNotEmpty(projects)) {
				Collections.sort(projects, sortByDateToLatest());
			}
		} catch (PhrescoException e) {
			ResponseInfo<ApplicationInfo> finalOutput = responseDataEvaluation(responseData, e,
					"Application update Failed", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin",
					"*").build();
		} catch (FileNotFoundException e) {
			ResponseInfo<ApplicationInfo> finalOutput = responseDataEvaluation(responseData, e,
					"Application update Failed", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin",
					"*").build();
		} catch (IOException e) {
			ResponseInfo<ApplicationInfo> finalOutput = responseDataEvaluation(responseData, e,
					"Application update Failed", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin",
					"*").build();
		} finally {
			Utility.closeReader(bufferedReader);
		}
		ResponseInfo<ApplicationInfo> finalOutput = responseDataEvaluation(responseData, null,
				"Application updated successfully", appInfo);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Edits the application.
	 *
	 * @param appDirName the app dir name
	 * @return the response
	 */
	@GET
	@Path("/editApplication")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editApplication(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName) {
		File projectInfoFile = new File(Utility.getProjectHome() + appDirName + File.separator + ".phresco"
				+ File.separator + "project.info");
		BufferedReader reader = null;
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			reader = new BufferedReader(new FileReader(projectInfoFile));
			ProjectInfo projectInfo = (ProjectInfo) new Gson().fromJson(reader, ProjectInfo.class);
			List<ApplicationInfo> appInfos = projectInfo.getAppInfos();
			for (ApplicationInfo applicationInfo : appInfos) {
				if (applicationInfo.getAppDirName().equals(appDirName)) {
					ResponseInfo<ProjectInfo> finalOutput = responseDataEvaluation(responseData, null,
							"Application edited Successfully", projectInfo);
					return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
							.build();
				}
			}
		} catch (FileNotFoundException e) {
			ResponseInfo<ProjectInfo> finalOutput = responseDataEvaluation(responseData, e, "Application edit Failed",
					null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
		return null;
	}

	/**
	 * Deleteproject.
	 *
	 * @param appDirnames the app dirnames
	 * @return the response
	 */
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteproject(List<String> appDirnames) {
		BufferedReader reader = null;
		ResponseInfo responseData = new ResponseInfo();
		try {
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			if (CollectionUtils.isNotEmpty(appDirnames)) {
				for (String appDirName : appDirnames) {
					StringBuilder sb = new StringBuilder(Utility.getProjectHome()).append(appDirName).append(
							File.separator).append(FOLDER_DOT_PHRESCO).append(File.separator).append(
							RUNAGNSRC_INFO_FILE);
					File file = new File(sb.toString());
					if (file.exists()) {
						Gson gson = new Gson();
						reader = new BufferedReader(new FileReader(file));
						ConfigurationInfo configInfo = gson.fromJson(reader, ConfigurationInfo.class);
						int port = Integer.parseInt(configInfo.getServerPort());
						boolean connectionAlive = Utility.isConnectionAlive(HTTP_PROTOCOL, LOCALHOST, port);
						if (connectionAlive) {
							ResponseInfo finalOutput = responseDataEvaluation(responseData, null,
									"Unable to delete the Application", null);
							return Response.status(Status.BAD_REQUEST).entity(finalOutput).header(
									"Access-Control-Allow-Origin", "*").build();
						}
					}
				}
			}
			projectManager.delete(appDirnames);
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "Unable to delete the Application", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin",
					"*").build();
		} catch (FileNotFoundException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "Unable to delete the Application", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin",
					"*").build();
		}
		ResponseInfo finalOutput = responseDataEvaluation(responseData, null, "Application deleted Successfully", null);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Gets the permission.
	 *
	 * @param userId the user id
	 * @return the permission
	 */
	@GET
	@Path("/getPermission")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPermission(@QueryParam(REST_QUERY_USERID) String userId) {
		ResponseInfo<UserPermissions> responseData = new ResponseInfo<UserPermissions>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			User user = ServiceManagerImpl.USERINFO_MANAGER_MAP.get(userId);
			FrameworkUtil futil = new FrameworkUtil();
			UserPermissions userPermissions = futil.getUserPermissions(serviceManager, user);
			ResponseInfo<UserPermissions> finalOutput = responseDataEvaluation(responseData, null,
					"Permission for user returned Successfully", userPermissions);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoWebServiceException e) {
			ResponseInfo<UserPermissions> finalOutput = responseDataEvaluation(responseData, e,
					"Permission for user not fetched", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin",
					"*").build();
		} catch (PhrescoException e) {
			ResponseInfo<UserPermissions> finalOutput = responseDataEvaluation(responseData, e,
					"Permission for user not fetched", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin",
					"*").build();
		}
	}

	/**
	 * Sort by date to latest.
	 *
	 * @return the comparator
	 */
	public Comparator sortByDateToLatest() {
		return new Comparator() {
			public int compare(Object firstObject, Object secondObject) {
				ProjectInfo projectInfo1 = (ProjectInfo) firstObject;
				ProjectInfo projectInfo2 = (ProjectInfo) secondObject;
				return projectInfo1.getCreationDate().compareTo(projectInfo2.getCreationDate()) * -1;
			}
		};
	}

	/**
	 * Delete sql folder.
	 *
	 * @param applicationInfo the application info
	 * @param selectedDatabases the selected databases
	 * @param serviceManager the service manager
	 * @param oldAppDirName the old app dir name
	 * @throws PhrescoException the phresco exception
	 */
	public void deleteSqlFolder(ApplicationInfo applicationInfo, List<ArtifactGroupInfo> selectedDatabases,
			ServiceManager serviceManager, String oldAppDirName) throws PhrescoException {
		try {
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			List<String> dbListToDelete = new ArrayList<String>();
			List<ArtifactGroupInfo> existingDBList = applicationInfo.getSelectedDatabases();
			if (CollectionUtils.isEmpty(existingDBList)) {
				return;
			}
			for (ArtifactGroupInfo artifactGroupInfo : existingDBList) {
				String oldArtifactGroupId = artifactGroupInfo.getArtifactGroupId();
				for (ArtifactGroupInfo newArtifactGroupInfo : selectedDatabases) {
					String newArtifactid = newArtifactGroupInfo.getArtifactGroupId();
					if (newArtifactid.equals(oldArtifactGroupId)) {
						checkForVersions(newArtifactid, oldArtifactGroupId, oldAppDirName, serviceManager);
						break;
					} else {
						DownloadInfo downloadInfo = serviceManager.getDownloadInfo(oldArtifactGroupId);
						dbListToDelete.add(downloadInfo.getName());
					}
				}
			}
			File sqlPath = null;
			if (StringUtils.isNotEmpty(oldAppDirName)) {
				sqlPath = new File(Utility.getProjectHome() + File.separator + oldAppDirName
						+ frameworkUtil.getSqlFilePath(oldAppDirName));
			} else {
				sqlPath = new File(Utility.getProjectHome() + File.separator + applicationInfo.getAppDirName()
						+ frameworkUtil.getSqlFilePath(applicationInfo.getAppDirName()));
			}
			for (String dbVersion : dbListToDelete) {
				File dbVersionFolder = new File(sqlPath, dbVersion.toLowerCase());
				FileUtils.deleteDirectory(dbVersionFolder.getParentFile());
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Check for versions.
	 *
	 * @param newArtifactid the new artifactid
	 * @param oldArtifactGroupId the old artifact group id
	 * @param oldAppDirName the old app dir name
	 * @param serviceManager the service manager
	 * @throws PhrescoException the phresco exception
	 */
	private void checkForVersions(String newArtifactid, String oldArtifactGroupId, String oldAppDirName,
			ServiceManager serviceManager) throws PhrescoException {
		try {
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			File sqlPath = new File(Utility.getProjectHome() + File.separator + oldAppDirName
					+ frameworkUtil.getSqlFilePath(oldAppDirName));
			DownloadInfo oldDownloadInfo = serviceManager.getDownloadInfo(oldArtifactGroupId);
			DownloadInfo newDownloadInfo = serviceManager.getDownloadInfo(newArtifactid);
			List<ArtifactInfo> oldVersions = oldDownloadInfo.getArtifactGroup().getVersions();
			List<ArtifactInfo> newVersions = newDownloadInfo.getArtifactGroup().getVersions();
			for (ArtifactInfo artifactInfo : oldVersions) {
				for (ArtifactInfo newartifactInfo : newVersions) {
					if (!newartifactInfo.getVersion().equals(artifactInfo.getVersion())) {
						String deleteVersion = "/" + oldDownloadInfo.getName() + "/" + artifactInfo.getVersion();
						FileUtils.deleteDirectory(new File(sqlPath, deleteVersion));
					}
				}
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the removed modules.
	 *
	 * @param appInfo the app info
	 * @param jsonData the json data
	 * @param serviceManager the service manager
	 * @return the removed modules
	 * @throws PhrescoException the phresco exception
	 */
	private List<ArtifactGroup> getRemovedModules(ApplicationInfo appInfo, List<SelectedFeature> jsonData,
			ServiceManager serviceManager) throws PhrescoException {
		List<String> selectedFeaturesId = appInfo.getSelectedModules();
		List<String> selectedJSLibsId = appInfo.getSelectedJSLibs();
		List<String> selectedComponentsId = appInfo.getSelectedComponents();
		Gson gson = new Gson();
		List<String> newlySelectedModuleGrpIds = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(jsonData)) {
			for (SelectedFeature obj : jsonData) {
				newlySelectedModuleGrpIds.add(obj.getModuleId());
			}
		}
		List<ArtifactGroup> artifactGroups = new ArrayList<ArtifactGroup>();
		if (CollectionUtils.isNotEmpty(selectedFeaturesId)) {
			addArtifactGroups(selectedFeaturesId, gson, newlySelectedModuleGrpIds, artifactGroups, serviceManager);
		}
		if (CollectionUtils.isNotEmpty(selectedJSLibsId)) {
			addArtifactGroups(selectedJSLibsId, gson, newlySelectedModuleGrpIds, artifactGroups, serviceManager);
		}
		if (CollectionUtils.isNotEmpty(selectedComponentsId)) {
			addArtifactGroups(selectedComponentsId, gson, newlySelectedModuleGrpIds, artifactGroups, serviceManager);
		}
		return artifactGroups;
	}

	/**
	 * Adds the artifact groups.
	 *
	 * @param selectedFeaturesIds the selected features ids
	 * @param gson the gson
	 * @param newlySelectedModuleGrpIds the newly selected module grp ids
	 * @param artifactGroups the artifact groups
	 * @param serviceManager the service manager
	 * @throws PhrescoException the phresco exception
	 */
	private void addArtifactGroups(List<String> selectedFeaturesIds, Gson gson, List<String> newlySelectedModuleGrpIds,
			List<ArtifactGroup> artifactGroups, ServiceManager serviceManager) throws PhrescoException {
		for (String selectedfeatures : selectedFeaturesIds) {
			ArtifactInfo artifactInfo = serviceManager.getArtifactInfo(selectedfeatures);
			if (!newlySelectedModuleGrpIds.contains(artifactInfo.getArtifactGroupId())) {
				ArtifactGroup artifactGroupInfo = serviceManager
						.getArtifactGroupInfo(artifactInfo.getArtifactGroupId());
				artifactGroups.add(artifactGroupInfo);
			}
		}
	}
}
