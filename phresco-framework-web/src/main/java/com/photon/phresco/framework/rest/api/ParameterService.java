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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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
import com.photon.phresco.util.PhrescoDynamicLoader;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/parameter")
public class ParameterService {

	@GET
	@Path("/dynamic")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getParameter(@QueryParam("appName") String appName, @QueryParam("goal") String goal) {
		ResponseInfo responseData = new ResponseInfo();
		try {
			List<Parameter> parameter = null;
			String filePath = Utility.getProjectHome() + appName + "/.phresco/phresco-"+ goal +"-info.xml";
			File file = new File(filePath);
			MojoProcessor mojo = new MojoProcessor(file);
			parameter = mojo.getParameters(goal);
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, null, "Parameter returned successfully", parameter);
			return Response.ok(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (Exception e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, e, "Parameter not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	@GET
	@Path("/file")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFileAsString(@QueryParam("appName") String appName, @QueryParam("goal") String goal) {
		ResponseInfo responseData = new ResponseInfo();
		try {
			String filePath = Utility.getProjectHome() + appName + "/.phresco/phresco-"+ goal +"-info.xml";
			File file = new File(filePath);
			String xml = IOUtils.toString(new FileInputStream(file));
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, null, "Parameter returned successfully", xml);
			return Response.status(200).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, e, "Parameter not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	@Path("/dependency")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getPossibleValue(@QueryParam("appName") String appName, @QueryParam("customerId") String customerId, @QueryParam("goal") String goal, 
			@QueryParam("key") String key) {
		ResponseInfo responseData = new ResponseInfo();
		PossibleValues possibleValues = null;
		try {
			String filePath = Utility.getProjectHome() + appName + "/.phresco/phresco-"+ goal +"-info.xml";
			MojoProcessor processor = new MojoProcessor(new File(filePath));
			Parameter parameter = processor.getParameter(goal, key);
			String dependency = parameter.getDependency();
			if (StringUtils.isNotEmpty(dependency)) {
				possibleValues = getPossibleValues(processor, goal, dependency, appName, customerId);
			}
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, null, "Dependency returned successfully", possibleValues);
			return Response.ok(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, e, "Dependency not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	private static PossibleValues getPossibleValues(MojoProcessor processor, String goal, String dependencyName,
			String appName, String customerId) throws PhrescoException {
		Parameter parameter = processor.getParameter(goal, dependencyName);
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
			ApplicationInfo appInfo = new ApplicationInfo();
			appInfo.setAppDirName(appName);
			map.put(DynamicParameter.KEY_APP_INFO, appInfo);
			map.put(DynamicParameter.KEY_MOJO, processor);
			map.put(DynamicParameter.KEY_CUSTOMER_ID, customerId);
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
}
