package com.photon.phresco.framework.rest.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
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
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactElement;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.CoreOption;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.RequiredOption;
import com.photon.phresco.commons.model.SelectedFeature;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.ApplicationHandler;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/features")
public class FeatureService extends RestBase implements ServiceConstants,Constants,FrameworkConstants {
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@QueryParam("customerId") String customerId, @QueryParam("techId") String techId,
			@QueryParam("type") String type, @QueryParam("userId") String userId) {
		ResponseInfo<List<ArtifactGroup>> responseData = new ResponseInfo<List<ArtifactGroup>>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if(serviceManager == null) {
				ResponseInfo<List<ArtifactGroup>> finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User", null);
	        	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			List<ArtifactGroup> features = serviceManager.getFeatures(customerId, techId, type);
			ResponseInfo<List<ArtifactGroup>> finalOutput = responseDataEvaluation(responseData, null, "Application Features listed successfully", features);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<ArtifactGroup>> finalOutput = responseDataEvaluation(responseData, e, "Application Features not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/desc")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDescription(@QueryParam(DB_COLUMN_ARTIFACT_GROUP_ID) String artifactGroupId, @QueryParam("userId") String userId) {
		ResponseInfo<ArtifactElement> responseData = new ResponseInfo<ArtifactElement>();
		try {
			ServiceManager serviceManager =  CONTEXT_MANAGER_MAP.get(userId);
			if(serviceManager == null) {
				ResponseInfo<ArtifactElement> finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User", null);
	        	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			ArtifactElement artifactElement = serviceManager.getArtifactDescription(artifactGroupId);
			ResponseInfo<ArtifactElement> finalOutput = responseDataEvaluation(responseData, null, 
					"Application Features listed successfully", artifactElement.getDescription());
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<ArtifactElement> finalOutput = responseDataEvaluation(responseData, e, "Application Features not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@POST
	@Path("/dependencyFeature")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDependencyFeature(@QueryParam("userId") String userId, ArtifactInfo artifactInfo) {
		List<String> dependencyIds = artifactInfo.getDependencyIds();
		ResponseInfo<List<ArtifactGroup>> responseData = new ResponseInfo<List<ArtifactGroup>>();
		try {
			List<ArtifactGroup> atArtifactGroups = new ArrayList<ArtifactGroup>();
			ServiceManager serviceManager =  CONTEXT_MANAGER_MAP.get(userId);
			if(serviceManager == null) {
				ResponseInfo<List<ArtifactGroup>> finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			for (String dependencyId : dependencyIds) {
				ArtifactInfo artifact = serviceManager.getArtifactInfo(dependencyId);
				String artifactGroupId = artifact.getArtifactGroupId();
				ArtifactGroup artifactGroupInfo = serviceManager.getArtifactGroupInfo(artifactGroupId);
				atArtifactGroups.add(artifactGroupInfo);
			}
			ResponseInfo<List<ArtifactGroup>> finalOutput = responseDataEvaluation(responseData, null, " Dependency Features listed successfully", atArtifactGroups);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<ArtifactGroup>> finalOutput = responseDataEvaluation(responseData, e, "Dependency Features not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/selectedFeature")
	@Produces(MediaType.APPLICATION_JSON)
	public Response selectedFeatures(@QueryParam("userId") String userId, @QueryParam("appDirName") String appDirName) throws PhrescoException {
		System.out.println("inside sercvice ...");
		ResponseInfo<List<SelectedFeature>> responseData = new ResponseInfo<List<SelectedFeature>>();
		List<SelectedFeature> listFeatures = new ArrayList<SelectedFeature>();
		BufferedReader bufferedReader = null;
		Gson gson = new Gson();
		try {
			ServiceManager serviceManager =  CONTEXT_MANAGER_MAP.get(userId);
			if(serviceManager == null) {
				ResponseInfo<List<SelectedFeature>> finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			StringBuilder sbs = null;
			if(StringUtils.isNotEmpty(appDirName)) {
				sbs = new StringBuilder(Utility.getProjectHome()).append(appDirName).append(
						File.separator).append(Constants.DOT_PHRESCO_FOLDER).append(File.separator).append(PROJECT_INFO);
			} 
			bufferedReader = new BufferedReader(new FileReader(sbs.toString()));
			Type type = new TypeToken<ProjectInfo>() {}.getType();
			ProjectInfo projectinfo = gson.fromJson(bufferedReader, type);
			ApplicationInfo appInfo = projectinfo.getAppInfos().get(0);
		    String selectedTechId = appInfo.getTechInfo().getId();
			List<String> selectedModules = appInfo.getSelectedModules();
			if (CollectionUtils.isNotEmpty(selectedModules)) {
				for (String selectedModule : selectedModules) {
					SelectedFeature selectFeature = createArtifactInformation(selectedModule, selectedTechId, appInfo, serviceManager);
					listFeatures.add(selectFeature);
				}
			}
			
			List<String> selectedJSLibs = appInfo.getSelectedJSLibs();
			if (CollectionUtils.isNotEmpty(selectedJSLibs)) {
				for (String selectedJSLib : selectedJSLibs) {
					SelectedFeature selectFeature = createArtifactInformation(selectedJSLib, selectedTechId, appInfo, serviceManager);
					listFeatures.add(selectFeature);
				}
			}
			
			List<String> selectedComponents = appInfo.getSelectedComponents();
			if (CollectionUtils.isNotEmpty(selectedComponents))	{
				for (String selectedComponent : selectedComponents) {
					SelectedFeature selectFeature = createArtifactInformation(selectedComponent, selectedTechId, appInfo, serviceManager);
					listFeatures.add(selectFeature);
				}
			}
			
			ResponseInfo<List<SelectedFeature>> finalOutput = responseDataEvaluation(responseData, null, " Selected Features listed successfully", listFeatures);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			
		} catch (Exception e) {
			ResponseInfo<List<SelectedFeature>> finalOutput = responseDataEvaluation(responseData, e, "Selected Features not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	private SelectedFeature createArtifactInformation(String selectedModule, String techId, ApplicationInfo appInfo, ServiceManager serviceManager) throws PhrescoException {
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
		System.out.println("atrifact group info ::: " + artifactGroupInfo.getAppliesTo());
		List<CoreOption> appliesTo = artifactGroupInfo.getAppliesTo();
		for (CoreOption coreOption : appliesTo) {
		    if (coreOption.getTechId().equals(techId) && !coreOption.isCore() && !slctFeature.getType().equals(REQ_JAVASCRIPT_TYPE_MODULE) && artifactGroupInfo.getPackaging().equalsIgnoreCase(ZIP_FILE)) {
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
		if(StringUtils.isNotEmpty(selectedFeatures)) {
			Gson gson = new Gson();
			Type jsonType = new TypeToken<Collection<ArtifactGroup>>(){}.getType();
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
