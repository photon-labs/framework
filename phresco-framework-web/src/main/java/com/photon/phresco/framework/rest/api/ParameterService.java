package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.RepoInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.model.CodeValidationReportType;
import com.photon.phresco.framework.param.impl.IosTargetParameterImpl;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.DynamicParameter.Dependencies.Dependency;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.PhrescoDynamicLoader;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/parameter")
public class ParameterService extends RestBase implements FrameworkConstants {

	@GET
	@Path("/dynamic")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getParameter(@QueryParam("appDirName") String appDirName, @QueryParam("goal") String goal, @QueryParam("phase") String phase) {
		ResponseInfo<List<Parameter>> responseData = new ResponseInfo<List<Parameter>>();
		try {
			List<Parameter> parameter = null;
			String filePath = getInfoFileDir(appDirName, goal, phase);
			MojoProcessor mojo = new MojoProcessor(new File(filePath));
			if (Constants.PHASE_FUNCTIONAL_TEST.equals(goal)) {
				String functionalTestFramework = FrameworkServiceUtil.getFunctionalTestFramework(appDirName);
				goal = goal + HYPHEN + functionalTestFramework;
			}
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
	public Response getFileAsString(@QueryParam("appDirName") String appDirName, @QueryParam("goal") String goal, @QueryParam("phase") String phase) {
		ResponseInfo responseData = new ResponseInfo();
		try {
			String filePath = getInfoFileDir(appDirName, goal, phase);
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
	public Response getPossibleValue(@QueryParam("appDirName") String appDirName, @QueryParam("customerId") String customerId, @QueryParam("goal") String goal, 
			@QueryParam("key") String key, @QueryParam("value") String value, @QueryParam("phase") String phase) {
		ResponseInfo<PossibleValues> responseData = new ResponseInfo<PossibleValues>();
		PossibleValues possibleValues = null;
		try {
			ApplicationInfo appInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			String filePath = getInfoFileDir(appDirName, goal, phase);
			MojoProcessor processor = new MojoProcessor(new File(filePath));
			possibleValues = getPossibleValues(processor, goal, key, value, appInfo, customerId, appDirName);
			ResponseInfo<PossibleValues> finalOutput = responseDataEvaluation(responseData, null, "Dependency returned successfully", possibleValues);
			return Response.ok(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<PossibleValues> finalOutput = responseDataEvaluation(responseData, e, "Dependency not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoPomException e) {
			ResponseInfo<PossibleValues> finalOutput = responseDataEvaluation(responseData, e, "Dependency not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/codeValidationReportTypes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCodeValidationReportTypes(@QueryParam("appDirName") String appDirName, @QueryParam("goal") String goal,
			@QueryParam("phase") String phase, @Context HttpServletRequest request) {
		ResponseInfo<PossibleValues> responseData = new ResponseInfo<PossibleValues>();
		try {
			int responseCode = setSonarServerStatus(request);
			if (responseCode != 200) {
				ResponseInfo<PossibleValues> finalOutput = responseDataEvaluation(responseData, null, "Sonar not yet Started", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			String infoFileDir = getInfoFileDir(appDirName, goal, phase);
			ApplicationInfo appInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			List<CodeValidationReportType> codeValidationReportTypes = new ArrayList<CodeValidationReportType>();

			//To get parameter values for Iphone technology
			PomProcessor pomProcessor = FrameworkServiceUtil.getPomProcessor(appDirName);
			String validateReportUrl = pomProcessor.getProperty(Constants.POM_PROP_KEY_VALIDATE_REPORT);
			if(StringUtils.isNotEmpty(validateReportUrl)) {
				CodeValidationReportType codeValidationReportType = new CodeValidationReportType();
				List<Value> clangReports = getClangReports(appInfo);
				for (Value value : clangReports) {
					codeValidationReportType.setValidateAgainst(value);
				}
				codeValidationReportTypes.add(codeValidationReportType);
			}
			MojoProcessor processor = new MojoProcessor(new File(infoFileDir));
			Parameter parameter = processor.getParameter(Constants.PHASE_VALIDATE_CODE, "sonar");
			PossibleValues possibleValues = parameter.getPossibleValues();
			List<Value> values = possibleValues.getValue();
			for (Value value : values) {
				CodeValidationReportType codeValidationReportType = new CodeValidationReportType();
				String key = value.getKey();
				Parameter depParameter = processor.getParameter(Constants.PHASE_VALIDATE_CODE, key);
				if (depParameter != null && depParameter.getPossibleValues() != null) {
					PossibleValues depPossibleValues = depParameter.getPossibleValues();
					List<Value> depValues = depPossibleValues.getValue();
					codeValidationReportType.setOptions(depValues);
				}
				codeValidationReportType.setValidateAgainst(value);
				codeValidationReportTypes.add(codeValidationReportType);
			}
			ResponseInfo<CodeValidationReportType> finalOutput = responseDataEvaluation(responseData, null, "Dependency returned successfully", codeValidationReportTypes);
			return Response.ok(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<PossibleValues> finalOutput = responseDataEvaluation(responseData, e, "Dependency not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoPomException e) {
			ResponseInfo<PossibleValues> finalOutput = responseDataEvaluation(responseData, e, "Dependency not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} 
	}
	
	@GET
	@Path("/iFrameReport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIframeReport(@QueryParam("customerId") String customerId, @QueryParam("userId") String userId,
			@QueryParam("appDirName") String appDirName, @QueryParam("validateAgainst") String validateAgainst,
			@Context HttpServletRequest request) {
		ResponseInfo<PossibleValues> responseData = new ResponseInfo<PossibleValues>();
		StringBuilder sb = new StringBuilder();
		try {
			ApplicationInfo appInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			PomProcessor processor = FrameworkServiceUtil.getPomProcessor(appDirName);
			String validateReportUrl = processor.getProperty(Constants.POM_PROP_KEY_VALIDATE_REPORT);
			FrameworkConfiguration frameworkConfig = PhrescoFrameworkFactory.getFrameworkConfig();
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			Customer customer = serviceManager.getCustomer(customerId);
			//Check whether iphone Technology or not
			Properties sysProps = System.getProperties();
			String phrescoFileServerNumber = sysProps.getProperty(PHRESCO_FILE_SERVER_PORT_NO);
			if (StringUtils.isNotEmpty(validateReportUrl)) {
				StringBuilder codeValidatePath = new StringBuilder(FrameworkServiceUtil.getApplicationHome(appDirName));
				codeValidatePath.append(validateReportUrl);
				codeValidatePath.append(validateAgainst);
				codeValidatePath.append(File.separatorChar);
				codeValidatePath.append(INDEX_HTML);
				File indexPath = new File(codeValidatePath.toString());
				if (indexPath.isFile() && StringUtils.isNotEmpty(phrescoFileServerNumber)) {
					sb.append(HTTP_PROTOCOL);
					sb.append(PROTOCOL_POSTFIX);
					InetAddress thisIp =InetAddress.getLocalHost();
					sb.append(thisIp.getHostAddress());
					sb.append(COLON);
					sb.append(phrescoFileServerNumber);
					sb.append(FORWARD_SLASH);
					sb.append(appDirName);
					sb.append(validateReportUrl);
					sb.append(validateAgainst);
					sb.append(FORWARD_SLASH);
					sb.append(INDEX_HTML);
					ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, null, "Dependency returned successfully", sb.toString());
					return Response.ok(finalOutput).header("Access-Control-Allow-Origin", "*").build();
				} else {
					ResponseInfo<PossibleValues> finalOutput = responseDataEvaluation(responseData, null, "Dependency not fetched", null);
					return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
				}
			}
			String serverUrl = "";
			FrameworkUtil frameworkUtil = new FrameworkUtil(request);
			serverUrl = frameworkUtil.getSonarHomeURL();	    		
			StringBuilder reportPath = new StringBuilder(FrameworkServiceUtil.getApplicationHome(appDirName));
//			if (StringUtils.isNotEmpty(getSelectedModule())) {
//				reportPath.append(File.separatorChar);
//				reportPath.append(getSelectedModule());
//			}
			if (StringUtils.isNotEmpty(validateAgainst) && FUNCTIONALTEST.equals(validateAgainst)) {
				reportPath.append(frameworkUtil.getFunctionalTestDir(appInfo));
			}
			reportPath.append(File.separatorChar);
			reportPath.append(POM_XML);
			File file = new File(reportPath.toString());
			processor = new PomProcessor(file);
			String groupId = processor.getModel().getGroupId();
			String artifactId = processor.getModel().getArtifactId();

			sb.append(serverUrl);
			sb.append(frameworkConfig.getSonarReportPath());
			sb.append(groupId);
			sb.append(COLON);
			sb.append(artifactId);

			if (StringUtils.isNotEmpty(validateAgainst) && !REQ_SRC.equals(validateAgainst)) {
				sb.append(COLON);
				sb.append(validateAgainst);
			}
			URL sonarURL = new URL(sb.toString());
			HttpURLConnection connection = (HttpURLConnection) sonarURL.openConnection();
			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				ResponseInfo<PossibleValues> finalOutput = responseDataEvaluation(responseData, null, "Report not available", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
            }
			Map<String, String> theme = customer.getFrameworkTheme();
			if (MapUtils.isNotEmpty(theme)) {
				sb.append("?");
				sb.append(CUST_BODY_BACK_GROUND_COLOR+"="+theme.get("bodyBackGroundColor"));
				sb.append("&"+CUST_BRANDING_COLOR+"="+theme.get("brandingColor"));
				sb.append("&"+CUST_MENU_BACK_GROUND+"="+theme.get("MenuBackGround"));
				sb.append("&"+CUST_MENUFONT_COLOR+"="+theme.get("MenufontColor"));
				sb.append("&"+CUST_DISABLED_LABEL_COLOR+"="+theme.get("DisabledLabelColor"));
			} 
		} catch (PhrescoException e) {
			ResponseInfo<PossibleValues> finalOutput = responseDataEvaluation(responseData, e, "Dependency not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoPomException e) {
			ResponseInfo<PossibleValues> finalOutput = responseDataEvaluation(responseData, e, "Dependency not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (UnknownHostException e) {
			ResponseInfo<PossibleValues> finalOutput = responseDataEvaluation(responseData, e, "Dependency not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (IOException e) {
			ResponseInfo<PossibleValues> finalOutput = responseDataEvaluation(responseData, e, "Dependency not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
		ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, null, "Dependency returned successfully", sb.toString());
		return Response.ok(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	private List<Value> getClangReports(ApplicationInfo appInfo) throws PhrescoException {
		try {
			IosTargetParameterImpl targetImpl = new IosTargetParameterImpl();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(FrameworkConstants.KEY_APP_INFO, appInfo);
			PossibleValues possibleValues = targetImpl.getValues(paramMap);
			List<Value> values = possibleValues.getValue();
			return values;
		} catch (IOException e) {
           throw new PhrescoException(e);
        } catch (ParserConfigurationException e) {
            throw new PhrescoException(e);
        } catch (SAXException e) {
            throw new PhrescoException(e);
        } catch (ConfigurationException e) {
            throw new PhrescoException(e);
        } catch (PhrescoException e) {
            throw new PhrescoException(e);
        }
	}
	
	private int setSonarServerStatus(HttpServletRequest request) throws PhrescoException {		
		FrameworkUtil frameworkUtil = new FrameworkUtil(request);
		int responseCode = 0;
		try {
			URL sonarURL = new URL(frameworkUtil.getSonarHomeURL());
			String protocol = sonarURL.getProtocol();
			HttpURLConnection connection = null;			
			if(protocol.equals("http")) {	
				connection = (HttpURLConnection) sonarURL.openConnection();
				responseCode = connection.getResponseCode();	
			} else {
				responseCode = FrameworkUtil.getHttpsResponse(frameworkUtil.getSonarURL());
			}			
			return responseCode;
		} catch(Exception e) {
			return responseCode;
		}
	}

	private static PossibleValues getPossibleValues(MojoProcessor processor, String goal, String key, String value,
			ApplicationInfo appInfo, String customerId, String appDirName) throws PhrescoException, PhrescoPomException {
		if (Constants.PHASE_FUNCTIONAL_TEST.equals(goal)) {
			String functionalTestFramework = FrameworkServiceUtil.getFunctionalTestFramework(appDirName);
			goal = goal + HYPHEN + functionalTestFramework;
		}
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
	
	private String getInfoFileDir(String appDirName, String goal, String phase) {
		StringBuilder sb = new StringBuilder();
		sb.append(Utility.getProjectHome())
		.append(appDirName)
		.append(File.separatorChar)
		.append(Constants.DOT_PHRESCO_FOLDER)
		.append(File.separatorChar);
		if (StringUtils.isNotEmpty(phase)) {
			sb.append(Constants.PHRESCO + HYPHEN + phase + Constants.INFO_XML);
		} else {
			sb.append(Constants.PHRESCO + HYPHEN + goal + Constants.INFO_XML);
		}
		return sb.toString();
	}
}
