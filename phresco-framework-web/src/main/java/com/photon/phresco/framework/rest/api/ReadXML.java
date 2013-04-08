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
import org.codehaus.jettison.json.JSONObject;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
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

@Path("/read")
public class ReadXML {

	@GET
	@Path("/params")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getJson(@QueryParam("appName") String appName) throws PhrescoException {
		try {
			List<Parameter> parameter = null;
			String filePath = Utility.getProjectHome() + appName + "/.phresco/phresco-package-info.xml";
			File file = new File(filePath);
			MojoProcessor mojo = new MojoProcessor(file);
			parameter = mojo.getParameters("package");
			Gson gson = new Gson();
			String json = gson.toJson(parameter);
			json = "{"+"\"row\""+":"+json + "}";
//			JSONObject finalOp = json.put("row", gson.toJson(parameter));
			return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();

		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	@GET
	@Path("/file")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFileAsString(@QueryParam("appName") String appName) throws PhrescoException {
		try {
			String filePath = Utility.getProjectHome() + appName + "/.phresco/phresco-package-info.xml";
			File file = new File(filePath);
			String xml = IOUtils.toString(new FileInputStream(file));
			return Response.status(200).entity(xml).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	@Path("/possibleValues")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getPossibleValue(@QueryParam("appName") String appName, @QueryParam("customerId") String customerId)
	throws PhrescoException {
		PossibleValues possibleValues = null;
		String filePath = Utility.getProjectHome() + appName + "/.phresco/phresco-package-info.xml";
		MojoProcessor processor = new MojoProcessor(new File(filePath));
		Parameter parameter = processor.getParameter("package", "showSettings");
		String dependency = parameter.getDependency();
		if (StringUtils.isNotEmpty(dependency)) {
			possibleValues = getPossibleValues(processor, "package", dependency, appName, customerId);
		}
		Gson gson = new Gson();
		String json = gson.toJson(possibleValues);
		json = "{"+"\"row\""+":"+json + "}";
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
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
			map.put("applicationInfo", appInfo);
			map.put("mojo", processor);
			map.put("customerId", customerId);
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
