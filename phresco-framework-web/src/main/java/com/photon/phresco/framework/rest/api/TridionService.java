package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.util.FileUtils;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.configuration.ConfigReader;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.PublicationCreation;
import com.photon.phresco.framework.commons.PublicationsList;
import com.photon.phresco.framework.model.Publication;
import com.photon.phresco.framework.model.Publications;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;


/**
 *  Tridion Service
 * @author saravanan_na
 *
 */

@Path("/tridion")
public class TridionService extends RestBase implements FrameworkConstants, ServiceConstants, ResponseCodes  {
	@POST
	@Path(SAVE_CONFIG)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveCofigurations(Publication publicationConfig, @QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(REST_QUERY_MODULE_NAME) String module) throws PhrescoException {
		try {
			ResponseInfo<String> responseData = new ResponseInfo<String>();
			String rootModulePath = "";
			String subModuleName = "";
			if (StringUtils.isNotEmpty(module)) {
				rootModulePath = Utility.getProjectHome() + appDirName;
				subModuleName = module;
			} else {
				rootModulePath = Utility.getProjectHome() + appDirName;
			}
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, subModuleName);
			PublicationCreation publication = new PublicationCreation(dotPhrescoFolderPath);

			boolean fileSaved = publication.createPublicationXml(publicationConfig);
			if (fileSaved) {
				responseData = responseDataEvaluation(responseData, null, PUBLICATION_SUCCESS, RESPONSE_STATUS_SUCCESS, PHRSR1001);
			} else {
				responseData = responseDataEvaluation(responseData, null, PUBLICATION_FAILURE, RESPONSE_STATUS_SUCCESS, PHRSR1002);
			}
			return Response.ok(responseData).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (Exception e) {
			return Response.status(Status.EXPECTATION_FAILED).entity(PUBLICATION_FAILURE).header(ACCESS_CONTROL_ALLOW_ORIGIN,
					ALL_HEADER).build();	
		}
	}
	
	/**
	 * Read the Publication Configuration
	 * @param appDirName
	 * @param module
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Path(READ_CONFIG)
	@Produces(MediaType.APPLICATION_JSON)
	public Response readCofiguration(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,  @QueryParam(REST_QUERY_MODULE_NAME) String module) throws PhrescoException {
		try {
			ResponseInfo<List<Publication>> responseData = new ResponseInfo<List<Publication>>();
			String rootModulePath = "";
			String subModuleName = "";
			if (StringUtils.isNotEmpty(module)) {
				rootModulePath = Utility.getProjectHome() + appDirName;
				subModuleName = module;
			} else {
				rootModulePath = Utility.getProjectHome() + appDirName;
			}
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, subModuleName);
			File publicationConfigPath = new File(dotPhrescoFolderPath + File.separator + PUBLICATION_CONFIG_FILE);
			PublicationCreation publicationCreation = new PublicationCreation(publicationConfigPath.getPath());
			if (publicationConfigPath.exists()) {
				List<Publication> publications = publicationCreation.getConfiguration();
				responseData = responseDataEvaluation(responseData, null, publications, RESPONSE_STATUS_SUCCESS, PHRSR1001);
				return Response.ok(responseData).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
			} else {
				return Response.status(Status.EXPECTATION_FAILED).entity(FILE_NOT_FOUND).header(ACCESS_CONTROL_ALLOW_ORIGIN,
						ALL_HEADER).build();
			}
		} catch (Exception e) {
			return Response.status(Status.EXPECTATION_FAILED).entity(FILE_NOT_FOUND).header(ACCESS_CONTROL_ALLOW_ORIGIN,
					ALL_HEADER).build();
		}
	}
	
	/**
	 * Get the Publication List
	 * @param appDirName
	 * @param module
	 * @param type
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Path(GET_PUBLICATIONS)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPublicationsList(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(REST_QUERY_MODULE_NAME) String module, @QueryParam(REST_QUERY_PUBLICATION_TYPE)  String type) throws PhrescoException {
		ResponseInfo<List<Publications>> responseData = new ResponseInfo<List<Publications>>();
		try {
			PublicationsList publicationList =  null;
			List<Publications> publicationsList = new ArrayList<Publications>();
			List<Publications> publicationsTargetList = new ArrayList<Publications>();
			String rootModulePath = "";
			String subModuleName = "";
			String server = "";
			String username = "";
			String password = "";
			if (StringUtils.isNotEmpty(module)) {
				rootModulePath = Utility.getProjectHome() + appDirName;
				subModuleName = module;
			} else {
				rootModulePath = Utility.getProjectHome() + appDirName;
			}
			String appDirPath = Utility.getProjectHome() + appDirName;
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(appDirPath, subModuleName);
			Pattern p = Pattern.compile(PERCENT_TWENTY);          
			p.matcher(dotPhrescoFolderPath);    
			File path = new File (dotPhrescoFolderPath + File.separator);
			publicationList = new PublicationsList(path);
		
			Map<String, String> authenticationDetails = publicationList.getAuthenticationDetails(dotPhrescoFolderPath);
			if (MapUtils.isNotEmpty(authenticationDetails)) {
				server = authenticationDetails.get(CMS_SERVER);
				username = authenticationDetails.get(CMS_USERNAME);
				password = authenticationDetails.get(CMS_PASSWORD);
				if (type.equalsIgnoreCase(PUBLICATION_LIST)) {
					publicationsList = getPublishList(path.getPath(), TYPE_LIST, server, username, password);
					responseData = responseDataEvaluation(responseData, null, publicationsList, RESPONSE_STATUS_SUCCESS, PHRSR1001);
				} else if (type.equalsIgnoreCase(PUBLICATION_TARGET)) {
					publicationsTargetList = getPublishList(path.getPath(), TARGET_DIR, server, username, password);
					responseData = responseDataEvaluation(responseData, null, publicationsTargetList, RESPONSE_STATUS_SUCCESS, PHRSR1001);
				}
			}
		} catch (Exception e) {
			String message = e.getMessage();
			if (StringUtils.isNotEmpty(message)) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, e, message, RESPONSE_STATUS_ERROR, PHR510002);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
				.build();
			}
		}
		return  Response.ok(responseData).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
	}
	
	/**
	 * Create the Publications
	 * @param appDirName
	 * @param module
	 * @return
	 * @throws PhrescoException
	 */
	
	@GET
	@Path(CREATE_PUBLICATIONS)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createPublication(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(REST_QUERY_MODULE_NAME) String module) throws PhrescoException {
		ResponseInfo<String> responseData = new ResponseInfo<String>();
		try {
			String server = "";
			String username = "";
			String password = "";
			String environmentName = "";
			String rootModulePath = "";
			String subModuleName = "";
			if (StringUtils.isNotEmpty(module)) {
				rootModulePath = Utility.getProjectHome() + appDirName;
				subModuleName = module;
			} else {
				rootModulePath = Utility.getProjectHome() + appDirName;
			}
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, subModuleName);
			File publicationConfigPath = new File(dotPhrescoFolderPath + File.separator + PUBLICATION_CONFIG_FILE);
			File virtualConfigFile = new File(dotPhrescoFolderPath + File.separator + PUBLICATION_VIRTUAL_CONFIG_FILE);
			
			PublicationsList publicationList = new PublicationsList(new File(dotPhrescoFolderPath));
			FileUtils.copyFile(publicationConfigPath, virtualConfigFile);
			
			List<String> types = new ArrayList<String>();
			types.add(SCHEMA);
			types.add(TEMPLATE);
			types.add(CONTENTS);
			types.add(WEBSITE);
			
			for (String type : types) {
				publicationList.createVirtualConfigFile(type);
			}
			boolean publicationExists = publicationList.checkContentAndSite(virtualConfigFile);
			if (!publicationExists) {
				String status = RESPONSE_STATUS_FAILURE;
				String errorCode = PHRSR10007;
				String message = "Content and WebSite is need to create further proceed";
				Exception exception = new Exception(message);
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, exception, message, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER).build();
			}
			Map<String, String> authenticationDetails = publicationList.getAuthenticationDetails(dotPhrescoFolderPath);
			if (MapUtils.isNotEmpty(authenticationDetails)) {
				server = authenticationDetails.get(CMS_SERVER);
				username = authenticationDetails.get(CMS_USERNAME);
				password = authenticationDetails.get(CMS_PASSWORD);
				environmentName = authenticationDetails.get(ENVIRONMENT_NAME);
				publicationList.createPublication(server, username, password, environmentName);
			}
		} catch (PhrescoException e) {
			String message = e.getMessage();
			if (StringUtils.isNotEmpty(message)) {
				String status = RESPONSE_STATUS_FAILURE;
				String errorCode = PHRSR10007;
				Exception exception = new Exception(message);
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, exception, message, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER).build();
			}
		} catch (IOException e) {
			String message = e.getMessage();
			if (StringUtils.isNotEmpty(message)) {
				String status = RESPONSE_STATUS_FAILURE;
				String errorCode = PHRSR10007;
				Exception exception = new Exception(message);
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, exception, message, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER).build();
			}
		}
		responseData = responseDataEvaluation(responseData, null, "Publication created Successfully", RESPONSE_STATUS_SUCCESS, PHRTRP0001);
		return Response.ok(responseData).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
	}
	/**
	 * Get the Environment List
	 * @param appDirName
	 * @param module
	 * @return
	 * @throws PhrescoException
	 */
	
	@GET
	@Path(ENVIRONMENT_LIST)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getEnvironmentList(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(REST_QUERY_MODULE_NAME) String module) throws PhrescoException {
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
		List<String> environmentList = new ArrayList<String>();
		try {
			String rootModulePath = "";
			String subModuleName = "";
			if (StringUtils.isNotEmpty(module)) {
				rootModulePath = Utility.getProjectHome() + appDirName;
				subModuleName = module;
			} else {
				rootModulePath = Utility.getProjectHome() + appDirName;
			}
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, subModuleName);
			File ConfigFileConfigPath = new File(dotPhrescoFolderPath , CONFIGURATION_INFO_FILE_NAME);
			ConfigReader reader = new ConfigReader(ConfigFileConfigPath);
			List<Environment> allEnvironments = reader.getAllEnvironments();
			if (CollectionUtils.isNotEmpty(allEnvironments)) {
				for (Environment environment : allEnvironments) {
					environmentList.add(environment.getName());
				}
			}
		} catch (ConfigurationException e) {
			String message = e.getMessage();
			if (StringUtils.isNotEmpty(message)) {
				String status = RESPONSE_STATUS_FAILURE;
				String errorCode = PHRSR10007;
				Exception exception = new Exception(message);
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, exception, message, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER).build();
			}
		}
		responseData = responseDataEvaluation(responseData, null, environmentList, RESPONSE_STATUS_SUCCESS, PHRSR1001);
		return Response.ok(responseData).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
	}
	
	/**
	 * Get the Publication List
	 * @param configPath
	 * @param type
	 * @param server
	 * @param username
	 * @param password
	 * @return
	 * @throws PhrescoException
	 */
	private List<Publications> getPublishList(String configPath, String type, String server, String username, String password) throws PhrescoException {
		List<Publications> publicationList = new ArrayList<Publications>();
		try {
			PublicationsList list = new PublicationsList(new File(configPath));
			if (type.equalsIgnoreCase(TYPE_LIST)) {
				publicationList = list.getPublicationList(server, username, password);
			} else if (type.equalsIgnoreCase(TARGET_DIR)) {
				publicationList = list.getPublicationTarget(server,username, password);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return publicationList;
	}
	
}
