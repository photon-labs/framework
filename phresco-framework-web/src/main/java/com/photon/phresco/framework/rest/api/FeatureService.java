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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactElement;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.CoreOption;
import com.photon.phresco.commons.model.RequiredOption;
import com.photon.phresco.commons.model.SelectedFeature;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.plugins.model.Mojos.ApplicationHandler;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class FeatureService.
 */
@Path("/features")
public class FeatureService extends RestBase implements ServiceConstants, Constants, FrameworkConstants, ResponseCodes {
	String status;
	String errorCode;
	String successCode;
	/**
	 * List the entire features.
	 *
	 * @param customerId the customer id
	 * @param techId the tech id
	 * @param type the type
	 * @param userId the user id
	 * @return the response
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_TECHID) String techId, @QueryParam(REST_QUERY_TYPE) String type,
			@QueryParam(REST_QUERY_USERID) String userId) {
		ResponseInfo<List<ArtifactGroup>> responseData = new ResponseInfo<List<ArtifactGroup>>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR410001;
				ResponseInfo<List<ArtifactGroup>> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			List<ArtifactGroup> features = serviceManager.getFeatures(customerId, techId, type);
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR400001;
			ResponseInfo<List<ArtifactGroup>> finalOutput = responseDataEvaluation(responseData, null,
					features, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR410002;
			ResponseInfo<List<ArtifactGroup>> finalOutput = responseDataEvaluation(responseData, e,
					null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Gets the description of a feature.
	 *
	 * @param artifactGroupId the artifact group id
	 * @param userId the user id
	 * @return the description
	 */
	@GET
	@Path("/desc")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDescription(@QueryParam(DB_COLUMN_ARTIFACT_GROUP_ID) String artifactGroupId,
			@QueryParam(REST_QUERY_USERID) String userId) {
		ResponseInfo<ArtifactElement> responseData = new ResponseInfo<ArtifactElement>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR410001;
				ResponseInfo<ArtifactElement> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR400002;
			ArtifactElement artifactElement = serviceManager.getArtifactDescription(artifactGroupId);
			ResponseInfo<ArtifactElement> finalOutput = responseDataEvaluation(responseData, null,
					artifactElement.getDescription(), status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR410003;
			ResponseInfo<ArtifactElement> finalOutput = responseDataEvaluation(responseData, e,
					null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Gets the dependency of a feature.
	 *
	 * @param userId the user id
	 * @param artifactInfo the artifact info
	 * @return the dependency feature
	 */
	@GET
	@Path("/dependencyFeature")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDependencyFeature(@QueryParam(REST_QUERY_USERID) String userId, @QueryParam("versionId") String versionId) {
		ResponseInfo<Map<String,String>> responseData = new ResponseInfo<Map<String,String>>();
		Map<String,String> map = new HashMap<String, String>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR410001;
				ResponseInfo<Map<String,String>> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			ArtifactInfo artifactInfo = serviceManager.getArtifactInfo(versionId);
			List<String> dependencyIds = artifactInfo.getDependencyIds();
			if (CollectionUtils.isNotEmpty(dependencyIds)) {
				for (String dependencyId : dependencyIds) {
					ArtifactInfo artifact = serviceManager.getArtifactInfo(dependencyId);
					String depeVersionId = artifact.getId();
					map.put(artifactInfo.getId(), depeVersionId);
				}
				status = RESPONSE_STATUS_SUCCESS;
				successCode = PHR400003;
				ResponseInfo<Map<String,String>> finalOutput = responseDataEvaluation(responseData, null,
						map, status, successCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR410004;
			ResponseInfo<Map<String,String>> finalOutput = responseDataEvaluation(responseData, null,
					null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR410005;
			ResponseInfo<Map<String,String>> finalOutput = responseDataEvaluation(responseData, e,
					null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Selected features.
	 *
	 * @param userId the user id
	 * @param appDirName the app dir name
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@GET
	@Path("/selectedFeature")
	@Produces(MediaType.APPLICATION_JSON)
	public Response selectedFeatures(@QueryParam(REST_QUERY_USERID) String userId,
			@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName) throws PhrescoException {
		ResponseInfo<List<SelectedFeature>> responseData = new ResponseInfo<List<SelectedFeature>>();
		List<SelectedFeature> listFeatures = new ArrayList<SelectedFeature>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR410001;
				ResponseInfo<List<SelectedFeature>> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			ApplicationInfo appInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			String selectedTechId = appInfo.getTechInfo().getId();
			List<String> selectedModules = appInfo.getSelectedModules();
			if (CollectionUtils.isNotEmpty(selectedModules)) {
				for (String selectedModule : selectedModules) {
					SelectedFeature selectFeature = createArtifactInformation(selectedModule, selectedTechId, appInfo,
							serviceManager);
					listFeatures.add(selectFeature);
				}
			}

			List<String> selectedJSLibs = appInfo.getSelectedJSLibs();
			if (CollectionUtils.isNotEmpty(selectedJSLibs)) {
				for (String selectedJSLib : selectedJSLibs) {
					SelectedFeature selectFeature = createArtifactInformation(selectedJSLib, selectedTechId, appInfo,
							serviceManager);
					listFeatures.add(selectFeature);
				}
			}

			List<String> selectedComponents = appInfo.getSelectedComponents();
			if (CollectionUtils.isNotEmpty(selectedComponents)) {
				for (String selectedComponent : selectedComponents) {
					SelectedFeature selectFeature = createArtifactInformation(selectedComponent, selectedTechId,
							appInfo, serviceManager);
					listFeatures.add(selectFeature);
				}
			}
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR400004;
			ResponseInfo<List<SelectedFeature>> finalOutput = responseDataEvaluation(responseData, null,
					listFeatures, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR410006;
			ResponseInfo<List<SelectedFeature>> finalOutput = responseDataEvaluation(responseData, e,
					null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Creates the artifact information.
	 *
	 * @param selectedModule the selected module
	 * @param techId the tech id
	 * @param appInfo the app info
	 * @param serviceManager the service manager
	 * @return the selected feature
	 * @throws PhrescoException the phresco exception
	 */
	private SelectedFeature createArtifactInformation(String selectedModule, String techId, ApplicationInfo appInfo,
			ServiceManager serviceManager) throws PhrescoException {
		SelectedFeature slctFeature = new SelectedFeature();
		ArtifactInfo artifactInfo = serviceManager.getArtifactInfo(selectedModule);

		slctFeature.setDispValue(artifactInfo.getVersion());
		slctFeature.setVersionID(artifactInfo.getId());
		slctFeature.setModuleId(artifactInfo.getArtifactGroupId());

		String artifactGroupId = artifactInfo.getArtifactGroupId();
		ArtifactGroup artifactGroupInfo = serviceManager.getArtifactGroupInfo(artifactGroupId);
		slctFeature.setName(artifactGroupInfo.getName());
		slctFeature.setDispName(artifactGroupInfo.getDisplayName());
		slctFeature.setType(artifactGroupInfo.getType().name());
		slctFeature.setArtifactGroupId(artifactGroupInfo.getId());
		slctFeature.setPackaging(artifactGroupInfo.getPackaging());
		getScope(appInfo, artifactInfo.getId(), slctFeature);
		List<CoreOption> appliesTo = artifactGroupInfo.getAppliesTo();
		for (CoreOption coreOption : appliesTo) {
			if (coreOption.getTechId().equals(techId) && !coreOption.isCore()
					&& !slctFeature.getType().equals(REQ_JAVASCRIPT_TYPE_MODULE)
					&& artifactGroupInfo.getPackaging().equalsIgnoreCase(ZIP_FILE)) {
				slctFeature.setCanConfigure(true);
			} else {
				slctFeature.setCanConfigure(false);
			}
		}
		List<RequiredOption> appliesToReqird = artifactInfo.getAppliesTo();
		if (CollectionUtils.isNotEmpty(appliesToReqird)) {
			for (RequiredOption requiredOption : appliesToReqird) {
				if (requiredOption.isRequired() && requiredOption.getTechId().equals(techId)) {
					slctFeature.setDefaultModule(true);
				}
			}
		}

		return slctFeature;

	}

	/**
	 * Gets the scope.
	 *
	 * @param appInfo the app info
	 * @param id the id
	 * @param selectFeature the select feature
	 * @return the scope
	 * @throws PhrescoException the phresco exception
	 */
	private void getScope(ApplicationInfo appInfo, String id, SelectedFeature selectFeature) throws PhrescoException {
		StringBuilder dotPhrescoPathSb = new StringBuilder(Utility.getProjectHome());
		dotPhrescoPathSb.append(appInfo.getAppDirName());
		dotPhrescoPathSb.append(File.separator);
		dotPhrescoPathSb.append(Constants.DOT_PHRESCO_FOLDER);
		dotPhrescoPathSb.append(File.separator);
		String pluginInfoFile = dotPhrescoPathSb.toString() + APPLICATION_HANDLER_INFO_FILE;
		MojoProcessor mojoProcessor = new MojoProcessor(new File(pluginInfoFile));
		ApplicationHandler applicationHandler = mojoProcessor.getApplicationHandler();
		String selectedFeatures = applicationHandler.getSelectedFeatures();
		if (StringUtils.isNotEmpty(selectedFeatures)) {
			Gson gson = new Gson();
			Type jsonType = new TypeToken<Collection<ArtifactGroup>>() {
			}.getType();
			List<ArtifactGroup> artifactGroups = gson.fromJson(selectedFeatures, jsonType);
			for (ArtifactGroup artifactGroup : artifactGroups) {
				for (ArtifactInfo artifactInfo : artifactGroup.getVersions()) {
					if (artifactInfo.getId().equals(id)) {
						selectFeature.setScope(artifactInfo.getScope());
					}
				}
			}
		}
	}

}
