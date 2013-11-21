package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.omg.IOP.TAG_CODE_SETS;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.model.LiquibaseDbDocInfo;
import com.photon.phresco.framework.model.LiquibaseDiffInfo;
import com.photon.phresco.framework.model.LiquibaseRollbackCountInfo;
import com.photon.phresco.framework.model.LiquibaseRollbackTagInfo;
import com.photon.phresco.framework.model.LiquibaseRollbackToDateInfo;
import com.photon.phresco.framework.model.LiquibaseStatusInfo;
import com.photon.phresco.framework.model.LiquibaseTagInfo;
import com.photon.phresco.framework.model.LiquibaseUpdateInfo;
import com.photon.phresco.framework.rest.api.util.ActionFunction;
import com.photon.phresco.framework.rest.api.util.ActionResponse;
import com.photon.phresco.impl.ConfigManagerImpl;
import com.photon.phresco.plugins.util.MojoUtil;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class LiquibaseService.
 */
@Path ("/liquibase")
public class LiquibaseService extends RestBase implements FrameworkConstants, ResponseCodes, ServiceConstants {
	
	/** The Constant S_LOGGER. */
	private static final Logger S_LOGGER= Logger.getLogger(ActionService.class);
	
	/**
	 * LiquibaseDbDoc
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/dbdoc")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response liquibaseDbdoc(LiquibaseDbDocInfo liquibaseDbDocInfo, @Context HttpServletRequest request) {
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try {
			actionFunction.prePopulateLiquibaseData(request);
			response = actionFunction.liquibaseDbdoc(liquibaseDbDocInfo, request);
			response.setResponseCode(PHRL000001);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRL010001);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * LiquibaseUpdate
	 *
	 * @param liquibaseUpdateInfo object, the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response liquibaseUpdate(LiquibaseUpdateInfo liquibaseUpdateInfo, @Context HttpServletRequest request) {
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try {
			
			actionFunction.prePopulateLiquibaseData(request);
			response = actionFunction.liquibaseUpdate(liquibaseUpdateInfo, request);
			response.setResponseCode(PHRL000002);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRL010002);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * LiquibaseInstall
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/install")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response liquibaseInstall(@Context HttpServletRequest request) {
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try {
			actionFunction.prePopulateLiquibaseData(request);
			response = actionFunction.liquibaseInstall(request);
			response.setResponseCode(PHRL000003);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRL010003);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * LiquibaseDiff
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/liquibaseDiff")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	 public Response liquibaseDiff(LiquibaseDiffInfo liquibaseDiffInfo, @Context HttpServletRequest request) {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateLiquibaseData(request);
			response = actionFunction.liquibaseDiff(liquibaseDiffInfo,request);
			response.setResponseCode(PHRL000004);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRL010004);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	/**
	 * LiquibaseStatus
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/liquibaseStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	 public Response liquibaseStatus(LiquibaseStatusInfo liquibaseStatusInfo, @Context HttpServletRequest request) {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateLiquibaseData(request);
			response = actionFunction.liquibaseStatus(liquibaseStatusInfo,request);
			response.setResponseCode(PHRL000005);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRL010005);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	/**
	 * LiquibaseRollbackcount
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/liquibaseRollBackCount")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	 public Response liquibaseRollBackCount(LiquibaseRollbackCountInfo liquibaseRollbackCountInfo,@Context HttpServletRequest request) {
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateLiquibaseData(request);
			response = actionFunction.liquibaseRollbackCount(liquibaseRollbackCountInfo, request);
			response.setResponseCode(PHRL000006);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRL010006);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	/**
	 * LiquibaseRollbacktoDate
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/liquibaseRollBackToDate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	 public Response liquibaseRollBackToDate(LiquibaseRollbackToDateInfo liquibaseRollbackToDateInfo, @Context HttpServletRequest request) {
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateLiquibaseData(request);
			response = actionFunction.liquibaseRollbackToDate(liquibaseRollbackToDateInfo,request);
			response.setResponseCode(PHRL000007);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRL010007);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	/**
	 * LiquibaseRollbackTag
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/liquibaseRollBackTag")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	 public Response liquibaseRollBackTag(LiquibaseRollbackTagInfo liquibaseRollbackTagInfo, @Context HttpServletRequest request) {
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateLiquibaseData(request);
			response = actionFunction.liquibaseRollbackTag(liquibaseRollbackTagInfo, request);
			response.setResponseCode(PHRL000008);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRL010008);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * LiquibaseTag
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/liquibaseTag")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response liquibaseTag(LiquibaseTagInfo liquibaseTagInfo, @Context HttpServletRequest request) {
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateLiquibaseData(request);
			response = actionFunction.liquibaseTag(liquibaseTagInfo, request);
			response.setResponseCode(PHRL000009);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRL010009);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * GetEnvironments
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/getEnvironments")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getEnvironments(@Context HttpServletRequest request) {
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
		try	{
			String appConfigPath = getAppConfigPath(request.getParameter(REQ_APP_DIR_NAME));
			File configXML = new File(appConfigPath);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document configDoc = docBuilder.parse(configXML);
			List<String> environmentsList = new ArrayList<String>();
			NodeList environments = configDoc.getElementsByTagName(SESSION_ENV_NAME);
			for(int i = 0 ; i < environments.getLength(); i++) {
				//get the environment element
				Element environment = (Element) environments.item(i);
				String envName = environment.getAttribute(NAME);
				environmentsList.add(envName);
			}
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null,
					environmentsList, RESPONSE_STATUS_SUCCESS, PHRL000010);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER).build();
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, e,
					null, RESPONSE_STATUS_ERROR, PHRL010010);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
					.build();
		}
	}
	
	/**
	 * GetConfigs
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/getConfigs")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getConfigs(@Context HttpServletRequest request) {
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
		try	{
			String appConfigPath = getAppConfigPath(request.getParameter(REQ_APP_DIR_NAME));
			File configXML = new File(appConfigPath);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document configDoc = docBuilder.parse(configXML);
			List<String> configsList = new ArrayList<String>();
			NodeList environments = configDoc.getElementsByTagName(SESSION_ENV_NAME);
			for(int i = 0 ; i < environments.getLength(); i++) {
				//get the environment element
				Element environment = (Element) environments.item(i);
				String envName = environment.getAttribute(NAME);
				if (envName.equalsIgnoreCase(request.getParameter(ENV_NAME))) { 
					NodeList configs = environment.getElementsByTagName(FrameworkConstants.DATABASE);
					for(int j = 0 ; j < configs.getLength(); j++) {
						//get the environment element
						Element config = (Element) configs.item(j);
						String configName = config.getAttribute(NAME);
						configsList.add(configName);
					}
				}
			}
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null,
					configsList, RESPONSE_STATUS_SUCCESS, PHRL000011);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER).build();
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, e,
					null, RESPONSE_STATUS_ERROR, PHRL010011);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
					.build();
		}
	}
	
	
	/**
	 * GetTags
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/getTags")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTags(@Context HttpServletRequest request) {
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
		List<String> tagsList = new ArrayList<String>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try	{
			
			
			ConfigManager configManager = null;
			Configuration requiredConfig = new Configuration();
			File baseDir = new File(Utility.getProjectHome() + request.getParameter(REQ_APP_DIR_NAME));
			configManager = new ConfigManagerImpl(new File(baseDir.getPath() + File.separator + Constants.DOT_PHRESCO_FOLDER + File.separator + Constants.CONFIGURATION_INFO_FILE));
			List<Configuration> configurations = configManager.getConfigurations(request.getParameter(ENV_NAME), Constants.SETTINGS_TEMPLATE_DB);
			if (CollectionUtils.isNotEmpty(configurations)) {
				for(Configuration configuration : configurations) {
					if(configuration.getName().equalsIgnoreCase(request.getParameter(CONF_NAME))) {
						requiredConfig = configuration;
						break;
					}
				}
			}
			Properties dbProperties = requiredConfig.getProperties();
			String dbType = dbProperties.getProperty(CONFIG_TYPE);
			String username = dbProperties.getProperty(REQ_USER_NAME);
			String password = dbProperties.getProperty(REQ_PASSWORD);
			String url = dbProperties.getProperty(HOST);
			String port = dbProperties.getProperty(PORT);
			String dbname = dbProperties.getProperty(DBNAME);
			if(ORACLE.equalsIgnoreCase(dbType)){
				Class.forName(ORACLE_DRIVER);
			}else if(MYSQL.equalsIgnoreCase(dbType)){
				Class.forName(MYSQL_DRIVER);
			}else{
				//failure
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null,
						null, RESPONSE_STATUS_FAILURE, PHRL010012);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER).build();
			}
			String connectionString = dbURLConstruction(url, port, dbname, dbType);
			connection = null;
			stmt = null;
			String query = "select tag from databasechangelog where tag is not null";
			connection = DriverManager.getConnection(connectionString, username, password);
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			String tags = null;
			while (rs.next()) {
	            tags = rs.getString(TAG_COL);
	            tagsList.add(tags);
	        }
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null,
					tagsList, RESPONSE_STATUS_SUCCESS, PHRL000012);
			rs.close();
			stmt.close();
			connection.close();
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER).build();
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, e,
					null, RESPONSE_STATUS_ERROR, PHRL010013);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
					.build();
		}
	}
	
	private String getAppConfigPath(String directory) throws PhrescoException {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		builder.append(directory);
		builder.append(File.separator);
		builder.append(FOLDER_DOT_PHRESCO);
		builder.append(File.separator);
		builder.append(CONFIGURATION_INFO_FILE_NAME);
		return builder.toString();
	}
	
	private String dbURLConstruction(String url,String port,String dbname,String dbtype){

		if(ORACLE.equalsIgnoreCase(dbtype)){
			return "jdbc:oracle:thin:@"+url+":"+port+":"+dbname;
		}else if(MYSQL.equalsIgnoreCase(dbtype)){
			return "jdbc:mysql://"+url+":"+port+"/"+dbname;
		}else{
			return null;
		}

	}
}
