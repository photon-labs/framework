package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.ArrayUtil;
import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.RepoInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.DynamicParameter.Dependencies.Dependency;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.PhrescoDynamicLoader;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/parameter")
public class ParameterService extends RestBase {

	@GET
	@Path("/dynamic")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getParameter(@QueryParam("appDirName") String appDirName, @QueryParam("goal") String goal) {
		ResponseInfo<List<Parameter>> responseData = new ResponseInfo<List<Parameter>>();
		try {
			List<Parameter> parameter = null;
			String filePath = getInfoFileDir(appDirName, goal);
			File file = new File(filePath);
			MojoProcessor mojo = new MojoProcessor(file);
			parameter = mojo.getParameters(goal);
			ResponseInfo<List<Parameter>> finalOutput = responseDataEvaluation(responseData, null, "Parameter returned successfully", parameter);
			return Response.ok(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (Exception e) {
			ResponseInfo<List<Parameter>> finalOutput = responseDataEvaluation(responseData, e, "Parameter not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	@GET
	@Path("/file")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFileAsString(@QueryParam("appDirName") String appDirName, @QueryParam("goal") String goal) {
		ResponseInfo responseData = new ResponseInfo();
		try {
			String filePath = getInfoFileDir(appDirName, goal);
			File file = new File(filePath);
			String xml = IOUtils.toString(new FileInputStream(file));
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, "Parameter returned successfully", xml);
			return Response.status(200).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "Parameter not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@POST
	@Path("/dependency")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getPossibleValue(ApplicationInfo appInfo, @QueryParam("customerId") String customerId, @QueryParam("goal") String goal, 
			@QueryParam("key") String key, @QueryParam("value") String value) {
		ResponseInfo<PossibleValues> responseData = new ResponseInfo<PossibleValues>();
		PossibleValues possibleValues = null;
		try {
			String filePath = getInfoFileDir(appInfo.getAppDirName(), goal);
			MojoProcessor processor = new MojoProcessor(new File(filePath));
			possibleValues = getPossibleValues(processor, goal, key, value, appInfo, customerId);
			ResponseInfo<PossibleValues> finalOutput = responseDataEvaluation(responseData, null, "Dependency returned successfully", possibleValues);
			return Response.ok(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<PossibleValues> finalOutput = responseDataEvaluation(responseData, e, "Dependency not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	private static PossibleValues getPossibleValues(MojoProcessor processor, String goal, String key, String value,
			ApplicationInfo appInfo, String customerId) throws PhrescoException {
		Parameter parameter = processor.getParameter(goal, key);
		if(StringUtils.isNotEmpty(parameter.getDependency())) {
			parameter = processor.getParameter(goal, parameter.getDependency());
		}
		if ("DynamicParameter".equals(parameter.getType())) {
			Dependency dependency = parameter.getDynamicParameter().getDependencies().getDependency();
			String clazz = parameter.getDynamicParameter().getClazz();
			List<ArtifactGroup> plugins = new ArrayList<ArtifactGroup>();
			ArtifactGroup artifactGroup = new ArtifactGroup();
			artifactGroup.setGroupId(dependency.getGroupId());
			artifactGroup.setArtifactId(dependency.getArtifactId());
			ArtifactInfo artifactInfos = new ArtifactInfo();
			artifactInfos.setVersion(dependency.getVersion());
			artifactGroup.setVersions(Arrays.asList(artifactInfos));
			plugins.add(artifactGroup);
			RepoInfo repoInfo = new RepoInfo();
			PhrescoDynamicLoader loader = new PhrescoDynamicLoader(repoInfo, plugins);
			DynamicParameter dynamicParameter = loader.getDynamicParameter(clazz);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(DynamicParameter.KEY_APP_INFO, appInfo);
			map.put(DynamicParameter.KEY_MOJO, processor);
			map.put(DynamicParameter.KEY_CUSTOMER_ID, customerId);
			map.put(key, value);
			try {
				PossibleValues values = dynamicParameter.getValues(map);
				return values;

			} catch (IOException e) {
				throw new PhrescoException(e);
			} catch (ParserConfigurationException e) {
				throw new PhrescoException(e);
			} catch (SAXException e) {
				throw new PhrescoException(e);
			} catch (ConfigurationException e) {
				throw new PhrescoException(e);
			}
		}
		return null;
	}
	
	private String getInfoFileDir(String appDirName, String goal) {
		StringBuilder sb = new StringBuilder();
		sb.append(Utility.getProjectHome())
		.append(appDirName)
		.append(File.separatorChar)
		.append(Constants.DOT_PHRESCO_FOLDER)
		.append(File.separatorChar)
		.append("phresco-"+ goal +"-info.xml");
		return sb.toString();
	}
}
