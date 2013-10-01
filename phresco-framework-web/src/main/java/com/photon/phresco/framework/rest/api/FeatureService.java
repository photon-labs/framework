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

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactElement;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.CoreOption;
import com.photon.phresco.commons.model.FeatureConfigure;
import com.photon.phresco.commons.model.PropertyTemplate;
import com.photon.phresco.commons.model.RequiredOption;
import com.photon.phresco.commons.model.SelectedFeature;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
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
		Map<String,List<String>> map = new HashMap<String, List<String>>();
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
				List<String> depVersionIdList = new ArrayList<String>();
				for (String dependencyId : dependencyIds) {
					ArtifactInfo artifact = serviceManager.getArtifactInfo(dependencyId);
					String depeVersionId = artifact.getId();
					depVersionIdList.add(depeVersionId);
				}
				map.put(artifactInfo.getId(), depVersionIdList);
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
	 * To get the dependent to Features
	 * @param userId
	 * @param versionId
	 * @param techId
	 * @return
	 */
	@GET
	@Path("/dependentToFeatures")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDependentToFeatures(@QueryParam(REST_QUERY_USERID) String userId, @QueryParam("versionId") String versionId, @QueryParam("techId") String techId) {
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR410010;
				ResponseInfo<Map<String,String>> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			}

			List<ArtifactGroup> artifactGroups = serviceManager.getFeaturesByTechId(techId);
			List<String> artifactInfoIds = new ArrayList<String>();
			if (CollectionUtils.isNotEmpty(artifactGroups)) {
				for (ArtifactGroup artifactGroup : artifactGroups) {
				
					List<ArtifactInfo> versions = artifactGroup.getVersions();
					for (ArtifactInfo artifactInfo : versions) {
						if (CollectionUtils.isNotEmpty(artifactInfo.getDependencyIds()) && artifactInfo.getDependencyIds().contains(versionId)) {
						System.out.println("name===> " + artifactGroup.getName());
							artifactInfoIds.add(artifactInfo.getId());
						}
					}
				}
				status = RESPONSE_STATUS_SUCCESS;
				successCode = PHR400007;
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null,
						artifactInfoIds, status, successCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			status = RESPONSE_STATUS_SUCCESS;
			errorCode = PHR400008;
			ResponseInfo<Map<String,String>> finalOutput = responseDataEvaluation(responseData, null,
					null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR410011;
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
	 * Populates the feature configuration pop-up
	 *
	 * @param userId the user id
	 * @param customerId the customer id
	 * @param featureName the feature name
	 * @param appDirName the app directory name
	 * @return the FeatureConfigure object
	 */
	@GET
	@Path("/populate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showFeatureConfigPopup(@QueryParam(REST_QUERY_USERID) String userId, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_FEATURENAME) String featureName, @QueryParam(REST_QUERY_APPDIR_NAME) String appDirName) {
		ResponseInfo<FeatureConfigure> responseData = new ResponseInfo<FeatureConfigure>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<FeatureConfigure> finalOutput = responseDataEvaluation(responseData, null,
						null, RESPONSE_STATUS_FAILURE, PHR410001);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			FeatureConfigure featureConfigure = new FeatureConfigure();
			featureConfigure = getTemplateConfigFile(appDirName, customerId, serviceManager, featureName);
			ResponseInfo<FeatureConfigure> finalOutput = responseDataEvaluation(responseData, null,
					featureConfigure, RESPONSE_STATUS_SUCCESS, PHR400005);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<FeatureConfigure> finalOutput = responseDataEvaluation(responseData, e,
					null, RESPONSE_STATUS_ERROR, PHR410007);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
	
	private FeatureConfigure getTemplateConfigFile(String appDirName, String customerId, ServiceManager serviceManager, String featureName) throws PhrescoException {
	    List<PropertyTemplate> propertyTemplates = new ArrayList<PropertyTemplate>();
	    try {
	    	FeatureConfigure featureConfigure = new FeatureConfigure();
	        FrameworkServiceUtil frameworkServiceUtil = new FrameworkServiceUtil();
	    	List<Configuration> featureConfigurations = frameworkServiceUtil.getApplicationProcessor(appDirName, customerId, serviceManager).preFeatureConfiguration(FrameworkServiceUtil.getApplicationInfo(appDirName), featureName);
	    	Properties properties = null;
	        boolean hasCustomProperty = false;
	        if (CollectionUtils.isNotEmpty(featureConfigurations)) {
	            for (Configuration featureConfiguration : featureConfigurations) {
	                properties = featureConfiguration.getProperties();
	                String expandableProp = properties.getProperty("expandable");
	                if (StringUtils.isEmpty(expandableProp)) {
	                	hasCustomProperty = true;
	                } else {
	                	hasCustomProperty = Boolean.valueOf(expandableProp);
	                }
                	if (properties.containsKey("expandable")) {
                		properties.remove("expandable");
                	}
	                Set<Object> keySet = properties.keySet();
	                for (Object key : keySet) {
	                    String keyStr = (String) key;
	                    if (!"expandable".equalsIgnoreCase(keyStr)) {
	                    	String dispName = keyStr.replace(".", " ");
	                    	PropertyTemplate propertyTemplate = new PropertyTemplate();
	                    	propertyTemplate.setKey(keyStr);
	                    	propertyTemplate.setName(dispName);
	                    	propertyTemplates.add(propertyTemplate);
	                    }
	                }
	            }
	        }
	        featureConfigure.setHasCustomProperty(hasCustomProperty);
	        featureConfigure.setProperties(properties);
	        featureConfigure.setPropertyTemplates(propertyTemplates);
	        return featureConfigure;
	    } catch (PhrescoException e) {
	        throw new PhrescoException(e);
	    }
	}
	
	/**
	 * Updates the feature configurations
	 *
	 * @param request the request
	 * @param userId the user id
	 * @param customerId the customer id
	 * @param featureName the feature name
	 * @param featureType the feature type
	 * @param appDirName the app directory name
	 */	
	@POST
	@Path("/configureFeature")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configureFeature(@Context HttpServletRequest request, @QueryParam(REST_QUERY_USERID) String userId, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_FEATURENAME) String featureName, @QueryParam(REST_QUERY_APPDIR_NAME) String appDirName) {
		ResponseInfo<FeatureConfigure> responseData = new ResponseInfo<FeatureConfigure>();
	    try {
	    	ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<FeatureConfigure> finalOutput = responseDataEvaluation(responseData, null,
						null, RESPONSE_STATUS_FAILURE, PHR410001);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
	    	ApplicationInfo appInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
	    	FeatureConfigure featureConfigure = new FeatureConfigure();
			featureConfigure = getTemplateConfigFile(appDirName, customerId, serviceManager, featureName);
            Properties properties = new Properties();
            if (CollectionUtils.isNotEmpty(featureConfigure.getPropertyTemplates())) {
            	for (PropertyTemplate propertyTemplate : featureConfigure.getPropertyTemplates()) {
            		String key  = propertyTemplate.getKey();
            		String value = request.getParameter(key);
            		properties.setProperty(key, escapeHtml(value));
            	}
            }
            String[] keys = request.getParameterValues(REQ_KEY);
            String[] values = request.getParameterValues(REQ_VALUE);
            if (!ArrayUtils.isEmpty(keys) && !ArrayUtils.isEmpty(values)) {
                for (int i = 0; i < keys.length; i++) {
                    if (StringUtils.isNotEmpty(keys[i]) && StringUtils.isNotEmpty(values[i])) {
                        properties.setProperty(keys[i], escapeHtml(values[i]));
                    }
                }
            }
            Configuration configuration = new Configuration();
            configuration.setName(featureName);
            List<Environment> allEnvironments = getAllEnvironments(appInfo);
	        for (Environment environment : allEnvironments) {
	        	if(environment.isDefaultEnv()) {
	        		configuration.setEnvName(environment.getName());
	        	}
	        }
            configuration.setProperties(properties);
            List<Configuration> configs = new ArrayList<Configuration>();
            configs.add(configuration);
            FrameworkServiceUtil frameworkServiceUtil = new FrameworkServiceUtil();
	    	frameworkServiceUtil.getApplicationProcessor(appDirName, customerId, serviceManager).postFeatureConfiguration(FrameworkServiceUtil.getApplicationInfo(appDirName), configs, featureName);
	    	ResponseInfo<FeatureConfigure> finalOutput = responseDataEvaluation(responseData, null,
					null, RESPONSE_STATUS_SUCCESS, PHR400006);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
        } catch (PhrescoException  e) {
        	ResponseInfo<FeatureConfigure> finalOutput = responseDataEvaluation(responseData, e,
					null, RESPONSE_STATUS_ERROR, PHR410008);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
        } catch (ConfigurationException e) {
        	ResponseInfo<FeatureConfigure> finalOutput = responseDataEvaluation(responseData, e,
					null, RESPONSE_STATUS_ERROR, PHR410009);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	private List<Environment> getAllEnvironments(ApplicationInfo appInfo) throws PhrescoException, ConfigurationException {
		String configPath = Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + FOLDER_DOT_PHRESCO + File.separator + PHRESCO_ENV_CONFIG_FILE_NAME ;
		ConfigManager configManager = getConfigManager(configPath);
		return configManager.getEnvironments();
	}
	
	private ConfigManager getConfigManager(String configPath) throws PhrescoException {
        File appDir = new File(configPath);
        return PhrescoFrameworkFactory.getConfigManager(appDir);
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
