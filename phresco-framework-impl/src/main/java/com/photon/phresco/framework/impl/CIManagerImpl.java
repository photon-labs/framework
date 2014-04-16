/**
 * Phresco Framework Implementation
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
package com.photon.phresco.framework.impl;

import hudson.cli.CLI;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.plexus.util.FileUtils;
import org.jdom.JDOMException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.CIBuild;
import com.photon.phresco.commons.model.CIJob;
import com.photon.phresco.commons.model.CIJobStatus;
import com.photon.phresco.commons.model.CIJobTemplate;
import com.photon.phresco.commons.model.ContinuousDelivery;
import com.photon.phresco.commons.model.ModuleInfo;
import com.photon.phresco.commons.model.ProjectDelivery;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.api.CIManager;
import com.photon.phresco.framework.impl.util.FrameworkUtil;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.sun.jersey.api.client.ClientHandlerException;
import com.trilead.ssh2.crypto.Base64;

public class CIManagerImpl implements CIManager, FrameworkConstants {

	private static final Logger S_LOGGER = Logger.getLogger(CIManagerImpl.class);
	private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();

	private CLI cli = null;

	public BufferedInputStream setup(ProjectInfo projectInfo, ActionType action, List<String> buildArgCmds, String workingDirectory) throws PhrescoException {
		try {
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			BufferedInputStream reader = applicationManager.performAction(projectInfo, action, buildArgCmds, workingDirectory);
			return reader;
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	public BufferedInputStream start(ProjectInfo projectInfo, ActionType action, List<String> buildArgCmds, String workingDirectory) throws PhrescoException {
		try {
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			BufferedInputStream reader = applicationManager.performAction(projectInfo, action, buildArgCmds, workingDirectory);
			return reader;
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	public BufferedInputStream stop(ProjectInfo projectInfo, ActionType action, List<String> buildArgCmds, String workingDirectory) throws PhrescoException {
		try {
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			BufferedInputStream reader = applicationManager.performAction(projectInfo, action, buildArgCmds, workingDirectory);
			return reader;
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	} 

	public boolean createJob(CIJob job) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.createJob(CIJob job)");
		}
		FileWriter writer = null;
		try {
			CIJobStatus jobStatus = configureJob(job, FrameworkConstants.CI_CREATE_JOB_COMMAND);
			if (jobStatus.getCode() == -1) {
				throw new PhrescoException(jobStatus.getMessage());
			}

		} catch (ClientHandlerException ex) {
			if (debugEnabled) {
				S_LOGGER.error(ex.getLocalizedMessage());
			}
			throw new PhrescoException(ex);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					S_LOGGER.error(e.getLocalizedMessage());
				}
			}
		}
		return true;
	}

	public boolean updateJob(CIJob job) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.updateJob(CIJob job)");
		}
		FileWriter writer = null;
		try {
			CIJobStatus jobStatus = configureJob(job, FrameworkConstants.CI_UPDATE_JOB_COMMAND);
			if (jobStatus.getCode() == -1) {
				throw new PhrescoException(jobStatus.getMessage());
			}
		} catch (ClientHandlerException ex) {
			if (debugEnabled) {
				S_LOGGER.error(ex.getLocalizedMessage());
			}
			throw new PhrescoException(ex);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					if (debugEnabled) {
						S_LOGGER.error(e.getLocalizedMessage());
					}
				}
			}
		}
		return true;
	}

	private CIJobStatus configureJob(CIJob job, String jobType) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.configureJob(CIJob job, String jobType)");
		}
		try {
			cli = getCLI(job);
			List<String> argList = new ArrayList<String>();
			argList.add(jobType);
			argList.add(job.getJobName());
			String jenkinsTemplateDir = Utility.getJenkinsTemplateDir();
			String configFilePath = jenkinsTemplateDir + job.getRepoType() + HYPHEN + CONFIG_XML;
			if (debugEnabled) {
				S_LOGGER.debug("configFilePath ...  " + configFilePath);
			}
			File configFile = new File(configFilePath);
			ConfigProcessor processor = new ConfigProcessor(configFile);
			customizeNodes(processor, job);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (debugEnabled) {
				S_LOGGER.debug("argList " + argList.toString());
			}
			login(cli);
			int result = cli.execute(argList, processor.getConfigAsStream(), System.out, baos);
			String message = "Job created successfully";
			if (result == -1) { 
				byte[] byteArray = baos.toByteArray();
				message = new String(byteArray);
			}
			if (debugEnabled) {
				S_LOGGER.debug("message " + message);
			}
			//when svn is selected credential value has to set
			if(SVN.equals(job.getRepoType())) {
				String jenkinsUrl = FrameworkUtil.getJenkinsUrl(job);
				StringBuilder ciPath = new StringBuilder(jenkinsUrl);
				ciPath.append(SCM_SUBVERSION_SCM_POST_CREDENTIAL);
				setSVNCredentials(ciPath.toString(), job.getUrl(), job.getUsername(), job.getPassword());
				if (StringUtils.isNotEmpty(job.getPhrescoUrl())) {
					setSVNCredentials(ciPath.toString(), job.getPhrescoUrl(), job.getPhrescoUsername(), job.getPhrescoPassword());
				}
				if (StringUtils.isNotEmpty(job.getTestUrl())) {
					setSVNCredentials(ciPath.toString(), job.getTestUrl(), job.getTestUsername(), job.getTestPassword());
				}
			}
			return new CIJobStatus(result, message);
		} catch (JDOMException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		} finally {
			if (cli != null) {
				logout(cli);
				try {
					cli.close();
				} catch (IOException e) {
					if (debugEnabled) {
						S_LOGGER.error(e.getLocalizedMessage());
					}
				} catch (InterruptedException e) {
					if (debugEnabled) {
						S_LOGGER.error(e.getLocalizedMessage());
					}
				}
			}
		}
	}

	public void saveMailConfiguration(String jenkinsPort, String senderEmailId, String senderEmailPassword) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.saveMailConfiguration(String jenkinsPort, String senderEmailId, String senderEmailPassword)");
		}
		try {
			String jenkinsTemplateDir = Utility.getJenkinsTemplateDir();
			String mailFilePath = jenkinsTemplateDir + MAIL + HYPHEN + CREDENTIAL_XML;
			if (debugEnabled) {
				S_LOGGER.debug("configFilePath ... " + mailFilePath);
			}
			File mailFile = new File(mailFilePath);

			SvnProcessor processor = new SvnProcessor(mailFile);

			InetAddress ownIP = InetAddress.getLocalHost();
			processor.changeNodeValue(CI_HUDSONURL, FrameworkUtil.getLocaJenkinsUrl());
			processor.changeNodeValue("smtpAuthUsername", senderEmailId);
			processor.changeNodeValue("smtpAuthPassword", (senderEmailPassword));
			processor.changeNodeValue("adminAddress", senderEmailId);

			String jenkinsJobHome = System.getenv(JENKINS_HOME);
			StringBuilder builder = new StringBuilder(jenkinsJobHome);
			builder.append(File.separator);

			processor.writeStream(new File(builder.toString() + CI_MAILER_XML));
		} catch (Exception e) {
			S_LOGGER.error("Entered into the catch block of CIManagerImpl.saveMailConfiguration " + e.getLocalizedMessage());
		}
	}

	public void saveConfluenceConfiguration(String confluenceUrl, String confluenceUsername, String confluencePassword) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.saveConfluenceConfiguration(String confluenceUrl, String confluenceUsername, String confluencePassword)");
		}
		try {
			String jenkinsJobHome = System.getenv(JENKINS_HOME);
			StringBuilder jenkinsHome = new StringBuilder(jenkinsJobHome);
			jenkinsHome.append(File.separator);
			File conluenceHomeXml = new File(jenkinsHome.toString() + CI_CONFLUENCE_XML);
			if (!conluenceHomeXml.exists()) {
				String jenkinsTemplateDir = Utility.getJenkinsTemplateDir();
				String confluenceTemplateXml = jenkinsTemplateDir + CI_CONFLUENCE_XML;
				File confluenceFile = new File(confluenceTemplateXml);
				FileUtils.copyFile(confluenceFile, conluenceHomeXml);
			}
			SvnProcessor svn = new SvnProcessor(conluenceHomeXml);
			svn.writeConfluenceXml(confluenceUrl, confluenceUsername, (confluencePassword));
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into the catch block of CIManagerImpl.saveConfluenceConfiguration " + e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
	}


	public String decyPassword(String encryptedText) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.error("Entered Method SvnProcessor.decyPassword(String encryptedText)");
		}
		String plainText = "";
		try {
			Cipher cipher = Cipher.getInstance(AES_ALGO);
			cipher.init(Cipher.DECRYPT_MODE, getAes128Key(CI_SECRET_KEY));
			byte[] decode = Base64.decode(encryptedText.toCharArray());
			plainText = new String(cipher.doFinal(decode));
			plainText = plainText.replace(CI_ENCRYPT_MAGIC, "");
		} catch (javax.crypto.IllegalBlockSizeException e) {
			return encryptedText;
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered catch block of SvnProcessor.decyPassword "+e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
		return plainText;
	}

	private static SecretKey getAes128Key(String s) {
		try {
			MessageDigest digest = MessageDigest.getInstance(SHA_ALGO);
			digest.reset();
			digest.update(s.getBytes(CI_UTF8));
			return new SecretKeySpec(digest.digest(),0,128/8, AES_ALGO);
		} catch (NoSuchAlgorithmException e) {
			throw new Error(e);
		} catch (UnsupportedEncodingException e) {
			throw new Error(e);
		}
	}

	public void clearConfluenceSitesNodes() throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.clearConfluenceSitesNodes()");
		}
		String jenkinsJobHome = System.getenv(JENKINS_HOME);
		StringBuilder jenkinsHome = new StringBuilder(jenkinsJobHome);
		jenkinsHome.append(File.separator);
		File confluenceHomeXml = new File(jenkinsHome.toString() + CI_CONFLUENCE_XML);
		SvnProcessor svn;
		try {
			svn = new SvnProcessor(confluenceHomeXml);
			svn.clearSitesNodes();
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into the catch block of CIManagerImpl.clearConfluenceSitesNodes " + e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
	}

	public String getMailConfiguration(String tag) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getMailConfiguration(String tag)");
		}
		try {
			String jenkinsTemplateDir = Utility.getJenkinsTemplateDir();
			String mailFilePath = jenkinsTemplateDir + MAIL + HYPHEN + CREDENTIAL_XML;

			File ciMailerXml = new File(Utility.getJenkinsHome() + File.separator + CI_MAILER_XML);
			File mailFile = null;
			if (ciMailerXml.exists()) {
				mailFile = ciMailerXml;
			} else {
				mailFile = new File(mailFilePath);
			}
			if (debugEnabled) {
				S_LOGGER.debug("configFilePath ... " + mailFilePath);
			}
			SvnProcessor processor = new SvnProcessor(mailFile);
			return processor.getNodeValue(tag);
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into the catch block of CIManagerImpl.saveMailConfiguration " + e.getLocalizedMessage());
			}
		}
		return null;
	}
	
	public String getTfsConfiguration() throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getTfsConfiguration()");
		}
		try {
			File ciTfsXml = new File(Utility.getJenkinsHome() + File.separator + CI_TFS_XML);
			if (!ciTfsXml.exists()) {
				return null;
			}
			if (debugEnabled) {
				S_LOGGER.debug("TFSconfigFilePath ... " + ciTfsXml.getPath());
			}
			SvnProcessor processor = new SvnProcessor(ciTfsXml);
			return processor.getNodeValue(TFS_EXECUTABLE);
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into the catch block of CIManagerImpl.saveMailConfiguration " + e.getLocalizedMessage());
			}
		}
		return null;
	}

	//Confluence plugin
	public JSONArray getConfluenceConfiguration() throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getConfluenceConfiguration()");
		}
		try {
			String jenkinsJobHome = System.getenv(JENKINS_HOME);
			StringBuilder jenkinsHome = new StringBuilder(jenkinsJobHome);
			jenkinsHome.append(File.separator);
			File confluenceHomeXml = new File(jenkinsHome.toString() + CI_CONFLUENCE_XML);
			if (confluenceHomeXml.exists()) {
				SvnProcessor processor = new SvnProcessor(confluenceHomeXml);
				return processor.getXml(confluenceHomeXml.getPath());
			}
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into the catch block of CIManagerImpl.getConfluenceConfiguration " + e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
		return null;
	}

	//TestFlight plugin
	public JSONArray getTestFlightConfiguration() throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getTestFlightConfiguration()");
		}
		try {
			String jenkinsJobHome = System.getenv(JENKINS_HOME);
			StringBuilder jenkinsHome = new StringBuilder(jenkinsJobHome);
			jenkinsHome.append(File.separator);
			File testFlightHomeXml = new File(jenkinsHome.toString() + CI_TESTFLIGHT_XML);
			if (testFlightHomeXml.exists()) {
				SvnProcessor processor = new SvnProcessor(testFlightHomeXml);
				return processor.getXml(testFlightHomeXml.getPath());
			}
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into the catch block of CIManagerImpl.getTestFlightConfiguration " + e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
		return null;
	}
	
	public JSONArray getKeyChains() throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getKeychains()");
		}
		try {
			String jenkinsJobHome = System.getenv(JENKINS_HOME);
			StringBuilder jenkinsHome = new StringBuilder(jenkinsJobHome);
			jenkinsHome.append(File.separator);
			File xcodeHomeXml = new File(jenkinsHome.toString() + CI_XCODE_XML);
			if (xcodeHomeXml.exists()) {
				SvnProcessor processor = new SvnProcessor(xcodeHomeXml);
				return processor.getXml(xcodeHomeXml.getPath());
			}
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into the catch block of CIManagerImpl.getConfluenceConfiguration " + e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
		return null;
	}
		
	public List<String> getConfluenceSites() throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getConfluenceSites()");
		}
		try {
			String jenkinsJobHome = System.getenv(JENKINS_HOME);
			StringBuilder jenkinsHome = new StringBuilder(jenkinsJobHome);
			jenkinsHome.append(File.separator);
			File confluenceHomeXml = new File(jenkinsHome.toString() + CI_CONFLUENCE_XML);
			if (confluenceHomeXml.exists()) {
				SvnProcessor processor = new SvnProcessor(confluenceHomeXml);
				return processor.readConfluenceSites();
			} 
			return null;
		} catch(Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into the catch block of CIManagerImpl.getConfluenceSites " + e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
	}

	private String getStatus(CIJob job) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getStatus(CIJob job)");
		}
		String jsonResponse = null;
		String color = null;
		try {
			String jenkinsUrl = FrameworkUtil.getJenkinsUrl(job) + FORWARD_SLASH + CI_JOB + FORWARD_SLASH + job.getJobName()+ FORWARD_SLASH +  "api/json/color";
			jsonResponse = getJsonResponse(jenkinsUrl);

			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(jsonResponse);
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			color = jsonObject.get("color").toString();

		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.debug("Entering catch block of CIManagerImpl.getBuildsArray "+e.getLocalizedMessage());
			}
		}
		return color;
	}
	
	public CIBuild getStatusInfo(CIJob job) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getStatus(CIJob job)");
		}
		String jsonResponse = null;
		CIBuild ciBuild = null;
		try {
			String jenkinsUrl = FrameworkUtil.getJenkinsUrl(job) + FORWARD_SLASH + CI_JOB + FORWARD_SLASH + job.getJobName()+ FORWARD_SLASH +  "api/json";
			jsonResponse = getJsonResponse(jenkinsUrl);
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(jsonResponse);
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			JsonElement element = jsonObject.get(FrameworkConstants.LAST_BUILD);
			if (element != null) {
				Gson gson = new Gson();
				ciBuild = gson.fromJson(element, CIBuild.class);
				setBuildStatus(ciBuild, job);
				String buildUrl = ciBuild.getUrl();
				String jenkinUrl = job.getJenkinsUrl() + ":" + job.getJenkinsPort();
				buildUrl = buildUrl.replaceAll("localhost:" + job.getJenkinsPort(), jenkinUrl); // when displaying url it should display setup machine ip
				ciBuild.setUrl(buildUrl);
			}
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.debug("Entering catch block of CIManagerImpl.getBuildsArray "+e.getLocalizedMessage());
			}
		}
		return ciBuild;
	}
	
	private CIJobStatus buildJob(CIJob job) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.buildJob(CIJob job)");
		}
		cli = getCLI(job);

		List<String> argList = new ArrayList<String>();
		argList.add(FrameworkConstants.CI_BUILD_JOB_COMMAND);
		argList.add(job.getJobName());
		try {
			login(cli);
			int status = cli.execute(argList);
			String message = FrameworkConstants.CI_BUILD_STARTED;
			if (status == FrameworkConstants.JOB_STATUS_NOTOK) {
				message = FrameworkConstants.CI_BUILD_STARTING_ERROR;
			}
			return new CIJobStatus(status, message);
		} finally {
			logout(cli);
			if (cli != null) {
				try {
					cli.close();
				} catch (IOException e) {
					if (debugEnabled) {
						S_LOGGER.error(e.getLocalizedMessage());
					}
				} catch (InterruptedException e) {
					if (debugEnabled) {
						S_LOGGER.error(e.getLocalizedMessage());
					}
				}
			}
		}
	}

	private JsonArray getBuildsArray(CIJob job) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getBuildsArray(CIJob job)");
		}
		JsonArray jsonArray = null;
		try {
			String jenkinsUrl = FrameworkUtil.getJenkinsUrl(job);
			String jobNameUtf8 = job.getJobName().replace(" ", "%20");
			String buildsJsonUrl = jenkinsUrl + "/job/" + jobNameUtf8 + "/api/json";
			String jsonResponse = getJsonResponse(buildsJsonUrl);

			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(jsonResponse);
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			JsonElement element = jsonObject.get(FrameworkConstants.CI_JOB_JSON_BUILDS);

			jsonArray = element.getAsJsonArray();

		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.debug("Entering catch block of CIManagerImpl.getBuildsArray "+e.getLocalizedMessage());
			}
		}
		return jsonArray;
	}

	public List<CIBuild> getBuilds(CIJob job) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getBuilds(CIJob job)");
		}
		List<CIBuild> ciBuilds = null;
		try {
			if (debugEnabled) {
				S_LOGGER.debug("getCIBuilds()  JobName = "+ job.getJobName());
			}
			JsonArray jsonArray = getBuildsArray(job);
			if (jsonArray != null) {
				ciBuilds = new ArrayList<CIBuild>(jsonArray.size());
				Gson gson = new Gson();
				CIBuild ciBuild = null;
				for (int i = 0; i < jsonArray.size(); i++) {
					ciBuild = gson.fromJson(jsonArray.get(i), CIBuild.class);
					setBuildStatus(ciBuild, job);
					String buildUrl = ciBuild.getUrl();
					String jenkinUrl = job.getJenkinsUrl() + ":" + job.getJenkinsPort();
					buildUrl = buildUrl.replaceAll("localhost:" + job.getJenkinsPort(), jenkinUrl); // when displaying url it should display setup machine ip
					ciBuild.setUrl(buildUrl);
					ciBuilds.add(ciBuild);
				}
			}
		} catch(Exception e) {
			if (debugEnabled) {
				S_LOGGER.debug("Entering Method CIManagerImpl.getBuilds(CIJob job) " + e.getLocalizedMessage());
			}
		}
		return ciBuilds;
	}

	private void setBuildStatus(CIBuild ciBuild, CIJob job) throws PhrescoException {
		S_LOGGER.debug("Entering Method CIManagerImpl.setBuildStatus(CIBuild ciBuild, CIJob job)");
		S_LOGGER.debug("setBuildStatus()  url = "+ ciBuild.getUrl());
		String buildUrl = ciBuild.getUrl();
		String jenkinsUrl = job.getJenkinsUrl() + ":" + job.getJenkinsPort();
		buildUrl = buildUrl.replaceAll("localhost:" + job.getJenkinsPort(), jenkinsUrl); // display the jenkins running url in ci list
		String response = getJsonResponse(buildUrl + API_JSON);
		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(response);
		JsonObject jsonObject = jsonElement.getAsJsonObject();

		JsonElement resultJson = jsonObject.get(FrameworkConstants.CI_JOB_BUILD_RESULT);
		JsonElement idJson = jsonObject.get(FrameworkConstants.CI_JOB_BUILD_ID);
		JsonElement timeJson = jsonObject.get(FrameworkConstants.CI_JOB_BUILD_TIME_STAMP);
		JsonArray asJsonArray = jsonObject.getAsJsonArray(FrameworkConstants.CI_JOB_BUILD_ARTIFACTS);

		if(jsonObject.get(FrameworkConstants.CI_JOB_BUILD_RESULT).toString().equals(STRING_NULL)) { // when build is result is not known
			ciBuild.setStatus(INPROGRESS);
		} else if(BUILD.equals(job.getOperation()) && resultJson.getAsString().equals(CI_SUCCESS_FLAG) && asJsonArray.size() < 1) { // when build is success and zip relative path is not added in json
			ciBuild.setStatus(INPROGRESS);
		} else {
			ciBuild.setStatus(resultJson.getAsString());
			//download path
			for (JsonElement jsonArtElement : asJsonArray) {
				String buildDownloadZip = jsonArtElement.getAsJsonObject().get(FrameworkConstants.CI_JOB_BUILD_DOWNLOAD_PATH).toString();
				if (BUILD.equals(job.getOperation()) && buildDownloadZip.endsWith(CI_ZIP)) {
					if (debugEnabled) {
						S_LOGGER.debug("download artifact " + buildDownloadZip);
					}
					ciBuild.setDownload(buildDownloadZip);
				} else if (PDF_REPORT.equals(job.getOperation()) && buildDownloadZip.endsWith(CI_PDF)) {
					if (debugEnabled) {
						S_LOGGER.debug("download artifact " + buildDownloadZip);
					}
					ciBuild.setDownload(buildDownloadZip);
					// for testing alone 
				/*} else {
					if (debugEnabled) {
						S_LOGGER.debug("download artifact " + buildDownloadZip);
					}
					ciBuild.setDownload(buildDownloadZip);*/
				}
			}
		}

		ciBuild.setId(idJson.getAsString());
		String dispFormat = DD_MM_YYYY_HH_MM_SS;
		ciBuild.setTimeStamp(getDate(timeJson.getAsString(), dispFormat));
	}

	private String getDate(String timeStampStr, String format) {
		DateFormat formatter = new SimpleDateFormat(format);
		long timeStamp = Long.parseLong(timeStampStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeStamp);
		return formatter.format(calendar.getTime());
	}

	private String getJsonResponse(String jsonUrl) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getJsonResponse(String jsonUrl)");
			S_LOGGER.debug("getJsonResponse() JSonUrl = "+jsonUrl);
		}
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext httpContext = new BasicHttpContext();
			getAuthenticatedHttpClient(httpClient, httpContext);
			HttpGet httpget = new HttpGet(jsonUrl);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpClient.execute(httpget, responseHandler);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}

	private CLI getCLI(CIJob job) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getCLI(CIJob job)");
		}
		String jenkinsUrl = FrameworkUtil.getJenkinsUrl(job);
		if (debugEnabled) {
			S_LOGGER.debug("jenkinsUrl to get cli object " + jenkinsUrl);
		}
		try {
			URL jenkins = new URL(jenkinsUrl);
			return new CLI(jenkins);
		} catch (MalformedURLException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (InterruptedException e) {
			throw new PhrescoException(e);
		}
	}
	
	public void login(CLI cli) throws PhrescoException {
		String username = FrameworkUtil.getJenkinsUsername();
		String password = FrameworkUtil.getJenkinsPassword();
		if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
			List<String> argList = new ArrayList<String>();
			argList.add("login");

			argList.add("--username");
			argList.add(username);

			argList.add("--password");
			argList.add(password);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int result = cli.execute(argList, null, System.out, baos);
			String message = "Login successfull";
			if (result == -1) {
				byte[] byteArray = baos.toByteArray();
				message = new String(byteArray);
			}
		}
	}

	public void logout(CLI cli) throws PhrescoException {
		List<String> argList = new ArrayList<String>();
		argList.add("logout");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int result = cli.execute(argList, null, System.out, baos);
		String message = "logged out successfully";
		if (result == -1) {
			byte[] byteArray = baos.toByteArray();
			message = new String(byteArray);
		}
	}

	public void getAuthenticatedHttpClient(HttpClient client, HttpContext httpContext) throws PhrescoException {
		try {
			String jenkinsUsername = FrameworkUtil.getJenkinsUsername();
			if (StringUtils.isNotEmpty(jenkinsUsername)) {
				String url = FrameworkUtil.getJenkinsUrl() + "/j_acegi_security_check";
				HttpPost post = new HttpPost(url);
				String jenkinsPassword = FrameworkUtil.getJenkinsPassword();

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("j_username", jenkinsUsername));
				nameValuePairs.add(new BasicNameValuePair("j_password", jenkinsPassword));
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse response = client.execute(post, httpContext);
				EntityUtils.consume(response.getEntity());
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	/*private static void printResponse(HttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		System.out.println("Reading " + rd);
		StringBuffer result = new StringBuffer();
		String line = "";
		if (rd != null) {
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		}
		System.out.println(result.toString());
	}*/
	
	private String scramble(String secret) {
        if(secret==null) {    
        	return null;
        }
        try {
            return new String(Base64.encode(secret.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            throw new Error(e); 
        }
    }

	private void customizeNodes(ConfigProcessor processor, CIJob job) throws JDOMException,PhrescoException, FileNotFoundException, PhrescoPomException {
		//SVN url customization
		if (SVN.equals(job.getRepoType())) {
			S_LOGGER.debug("This is svn type project!!!!!");
			processor.addSvnRepos(job);
		//GIT url customization
		} else if (GIT.equals(job.getRepoType())) {
			S_LOGGER.debug("This is git type project!!!!!");
			processor.addGitRepos(job);
		//TFS url customization
		} else if (TFS.equals(job.getRepoType())) {
			S_LOGGER.debug("This is tfs type project!!!!!");
			processor.changeNodeValue(TFS_URL, job.getUrl());
			processor.changeNodeValue(TFS_USERNAME, job.getUsername());
			processor.changeNodeValue(TFS_PASSWORD, scramble(job.getPassword()));
			processor.changeNodeValue(TFS_PROJECTPATH, job.getProjectPath());
		// cloned workspace
		} else if (CLONED_WORKSPACE.equals(job.getRepoType())) {
			S_LOGGER.debug("Clonned workspace selected!!!!!!!!!!");
			processor.useClonedScm(job.getUsedClonnedWorkspace(), SUCCESSFUL);
		}
		//Schedule expression customization
		processor.changeNodeValue(TRIGGERS_SPEC, job.getScheduleExpression());
		//Triggers Implementation
		List<String> triggers = job.getTriggers();
		processor.createTriggers(TRIGGERS, triggers, job.getScheduleExpression());
		//if the technology is java stanalone and functional test , goal have to specified in post build step only
		if(job.isEnablePostBuildStep() && FUNCTIONAL_TEST.equals(job.getOperation())) {
			//Maven command customization
			processor.changeNodeValue(GOALS, CI_FUNCTIONAL_ADAPT.trim());
		} else {
			//Maven command customization
			processor.changeNodeValue(GOALS, job.getMvnCommand());
		}
		// Artifact archiver - archive do_not_checkin
		processor.setArtifactArchiver(job.isEnableArtifactArchiver(), job.getCollabNetFileReleasePattern());
		//Recipients customization
		Map<String, String> emails = new HashMap<String, String>();
		emails.put(FAILURE_EMAILS, job.getFailureEmailIds());
		emails.put(SUCCESS_EMAILS, job.getSuccessEmailIds());
		processor.setEmailPublisher(emails, job.getAttachmentsPattern());
		//Failure Reception list
		if (StringUtils.isNotEmpty(job.getFailureEmailIds())) {
			processor.changeNodeValue(TRIGGER_FAILURE_EMAIL_RECIPIENT_LIST, job.getFailureEmailIds());
		}
		//Success Reception list
		if (StringUtils.isNotEmpty(job.getSuccessEmailIds())) {
			processor.changeNodeValue(TRIGGER_SUCCESS__EMAIL_RECIPIENT_LIST, job.getSuccessEmailIds());
		}
		//enable collabnet file release plugin integration
		if (job.isEnableBuildRelease()) {
			S_LOGGER.debug("Enablebling collabnet file release plugin ");
			processor.enableCollabNetBuildReleasePlugin(job);
		}
		//enable Cobertura file release plugin integration
		if (job.isCoberturaPlugin()) {
			S_LOGGER.debug("Enablebling cobertura report file release plugin ");
			processor.enableCoberturaReleasePlugin(job);
		}
		//enable confluence file release plugin integration
		if (job.isEnableConfluence()) {
			S_LOGGER.debug("Enablebling confluence file release plugin ");
			processor.enableConfluenceReleasePlugin(job);
		}
		//enable testFlight file release plugin integration
		if (job.isEnableTestFlight()) {
			S_LOGGER.debug("Enablebling testFlight file release plugin ");
			processor.enableTestFlightReleasePlugin(job);
		}
		
		// use clonned scm
		if(CLONED_WORKSPACE.equals(job.getRepoType())) {
			S_LOGGER.debug("using cloned workspace ");
			processor.useClonedScm(job.getUsedClonnedWorkspace(), CRITERIA_ANY);
		}
		// clone workspace for future use
		if (job.isCloneWorkspace()) { 
			S_LOGGER.debug("Clonning the workspace ");
			processor.cloneWorkspace(ALL_FILES, CRITERIA_ANY, TAR);
		}
		// Build Other projects
		if (StringUtils.isNotEmpty(job.getDownstreamApplication())) {
			S_LOGGER.debug("Enabling downstream project!!!!!!");
			String name = "";
			String ordinal = "";
			String color = "";
			if (CI_SUCCESS_FLAG.equals(job.getDownStreamCriteria())) {
				name = CI_SUCCESS_FLAG;
				ordinal = ZERO;
				color = BLUE;
			} else if (CI_UNSTABLE_FLAG.equals(job.getDownStreamCriteria())) {
				name = CI_UNSTABLE_FLAG;
				ordinal = ONE;
				color = YELLOW;
				// failure
			} else {
				name = CI_FAILURE_FLAG;
				ordinal = TWO;
				color = RED;
			}
			processor.buildOtherProjects(job.getDownstreamApplication(), name, ordinal, color);
		}
		// pom location specifier 
		if (StringUtils.isNotEmpty(job.getPomLocation())) {
			S_LOGGER.debug("POM location changing " + job.getPomLocation());
			processor.updatePOMLocation(job.getPomLocation());
		}
		if (job.isEnablePostBuildStep()) {
			String mvnCommand = job.getMvnCommand();
			if(CollectionUtils.isNotEmpty(job.getPostbuildStepCommands())) {
				List<String> postBuildStepCommands = job.getPostbuildStepCommands(); 
				for (String postBuildStepCommand : postBuildStepCommands) {
					processor.enablePostBuildStep(job.getPomLocation(), postBuildStepCommand);
				}
			}
			
			if(DEVICE_BUILD.equalsIgnoreCase(job.getOperation())) {
				processor.enableXcode(job);
			}
			
		}
		if (job.isEnablePreBuildStep()) {
			//iterate over loop
			List<String> preBuildStepCommands = job.getPrebuildStepCommands(); 
			//shell#SEP#cmd, mvn#SEP#clean install
			for (String preBuildStepCommand : preBuildStepCommands) {
				String pomLocation = job.getPomLocation();
				boolean flag = false;
				if (StringUtils.isNotEmpty(job.getUrl())) {
					flag = true;
				}
				if (StringUtils.isNotEmpty(Utility.constructSubPath(job.getAppDirName(), flag, job.getRepoType()))) {
					pomLocation = Utility.constructSubPath(job.getAppDirName(), flag, job.getRepoType());
				}
				
				processor.enablePreBuildStep(pomLocation, preBuildStepCommand); 
			}
		}

	}
	
	private CIJobStatus deleteCI(CIJob job, String builds) throws PhrescoException {
		S_LOGGER.debug("Entering Method CIManagerImpl.deleteCI(CIJob job, String builds)");
		S_LOGGER.debug("Job name " + job.getJobName());
		cli = getCLI(job);
		String deleteType = null;
		List<String> argList = new ArrayList<String>();
		S_LOGGER.debug("job name " + job.getJobName());
		S_LOGGER.debug("Builds " + builds);
		if(StringUtils.isEmpty(builds)) {	
			S_LOGGER.debug("Job deletion started");
			S_LOGGER.debug("Command " + FrameworkConstants.CI_JOB_DELETE_COMMAND);
			deleteType = DELETE_TYPE_JOB;
			argList.add(FrameworkConstants.CI_JOB_DELETE_COMMAND);
			argList.add(job.getJobName());
		} else {								
			S_LOGGER.debug("Build deletion started");
			deleteType = DELETE_TYPE_BUILD;
			argList.add(FrameworkConstants.CI_BUILD_DELETE_COMMAND);
			argList.add(job.getJobName());
			argList.add(builds);
			S_LOGGER.debug("Command " + FrameworkConstants.CI_BUILD_DELETE_COMMAND);
			S_LOGGER.debug("Build numbers " + builds);
		}
		try {
			login(cli);
			int status = cli.execute(argList);
			String message = deleteType + " has successfully deleted in jenkins";
			if (status == FrameworkConstants.JOB_STATUS_NOTOK) {
				deleteType = deleteType.substring(0, 1).toLowerCase() + deleteType.substring(1);
				message = "Error while deleting " + deleteType +" in jenkins";
			}
			S_LOGGER.debug("Delete CI Status " + status);
			S_LOGGER.debug("Delete CI Message " + message);
			return new CIJobStatus(status, message);
		} finally {
			logout(cli);
			if (cli != null) {
				try {
					cli.close();
				} catch (IOException e) {
					if (debugEnabled) {
						S_LOGGER.error("Entered into catch block of CIManagerImpl.deleteCI(CIJob job) " + e.getLocalizedMessage());
					}
				} catch (InterruptedException e) {
					if (debugEnabled) {
						S_LOGGER.error("Entered into catch block of CIManagerImpl.deleteCI(CIJob job) " + e.getLocalizedMessage());
					}
				}
			}
		}
	}

	private ProjectDelivery createNewProjectDelivery(String name, String environment, List<CIJob> jobs, String projId) {
		ProjectDelivery updateProjectDeliveries = new ProjectDelivery();
		ContinuousDelivery updateContinuousDeliveries = new ContinuousDelivery();
		updateContinuousDeliveries.setName(name);
		updateContinuousDeliveries.setEnvName(environment);
		updateContinuousDeliveries.setJobs(jobs);
		updateProjectDeliveries.setId(projId);
		updateProjectDeliveries.setContinuousDeliveries(Arrays.asList(updateContinuousDeliveries));
		return updateProjectDeliveries;
	}

	private ContinuousDelivery createNewContinuousDelivery(String name, String environment, List<CIJob> jobs, String version) {
		ContinuousDelivery updateContinuousDeliveries = new ContinuousDelivery();
		updateContinuousDeliveries.setName(name);
		updateContinuousDeliveries.setEnvName(environment);
		updateContinuousDeliveries.setJobs(jobs);
		updateContinuousDeliveries.setVersion(version);
		return updateContinuousDeliveries;
	}

	public boolean createJsonJobs(ContinuousDelivery continuousDelivery, List<CIJob> jobs, String projId, String appDir, String globalInfo, String status) throws PhrescoException {
		try {
			String ciJobInfoPath = Utility.getCiJobInfoPath(appDir, globalInfo, status);
			File infoFile = new File(ciJobInfoPath);

			//create new info file if not exists
			if (!infoFile.exists()) {
				infoFile.createNewFile();
			}
			List<ProjectDelivery> projectDeliveries = Utility.getProjectDeliveries(infoFile);

			List<ProjectDelivery> newProjectDelivery = new ArrayList<ProjectDelivery>(); 
			ProjectDelivery createNewProjectDelivery = createNewProjectDelivery(continuousDelivery.getName(), continuousDelivery.getEnvName(), jobs, projId);
			newProjectDelivery.add(createNewProjectDelivery);

			ContinuousDelivery createNewContinuousDelivery = createNewContinuousDelivery(continuousDelivery.getName(), continuousDelivery.getEnvName(), jobs, continuousDelivery.getVersion());

			if (CollectionUtils.isEmpty(projectDeliveries)) {
				projectDeliveries = newProjectDelivery;
			} else {
				ProjectDelivery projectDelivery = Utility.getProjectDelivery(projId, projectDeliveries);
				if (projectDelivery != null) {
					List<ContinuousDelivery> continuousDeliveries = projectDelivery.getContinuousDeliveries(); 	
					if(CollectionUtils.isNotEmpty(continuousDeliveries)) {
						continuousDeliveries.add(createNewContinuousDelivery);
					} else {
						projectDelivery.setContinuousDeliveries(Arrays.asList(createNewContinuousDelivery));
					}
				} else {
					projectDeliveries.add(createNewProjectDelivery);
				}
			}
			return ciInfoFileWriter(infoFile.getPath(), projectDeliveries);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	private boolean ciInfoFileWriter(String filePath, Object object ) throws PhrescoException {
		Gson gson = new Gson();
		FileWriter writer = null;
		try {
			writer = new FileWriter(filePath);
			String json = gson.toJson(object);
			writer.write(json);
			writer.flush();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public CIJob getJob(String jobName, String projectId, List<ProjectDelivery> projectDeliveries, String continuousName) {
		ContinuousDelivery continuousDelivery = Utility.getContinuousDelivery(projectId, continuousName, projectDeliveries);
		List<CIJob> jobs = continuousDelivery.getJobs();
		for (CIJob ciJob : jobs) {
			if(ciJob.getJobName().equals(jobName)) {
				return ciJob;
			}
		}
		return null;
	}

	public List<CIJob> getOldJobs(String projectId, String name, String appDirName, String globalInfo, String status) throws PhrescoException {
		List<ProjectDelivery> ciJobInfo = getCiJobInfo(appDirName, globalInfo, status);
		if (CollectionUtils.isNotEmpty(ciJobInfo)) {
			return Utility.getJobs(name, projectId, ciJobInfo);
		}
		return null;
	}

	public boolean clearContinuousDelivery(String continuousDeliveryName, String projId, String appDir, String globalInfo, String status) throws PhrescoException {
		try {
			List<ProjectDelivery> ciJobInfo = getCiJobInfo(appDir, globalInfo, status);
			String ciJobInfoPath = Utility.getCiJobInfoPath(appDir, globalInfo, status);
			if (CollectionUtils.isNotEmpty(ciJobInfo)) {
				ProjectDelivery projectDelivery = Utility.getProjectDelivery(projId, ciJobInfo);
				if (projectDelivery != null) {
					List<ContinuousDelivery> continuousDeliveries = projectDelivery.getContinuousDeliveries();
					if (CollectionUtils.isNotEmpty(continuousDeliveries)) { 
						Iterator<ContinuousDelivery> continuousDeliveryIterator = continuousDeliveries.iterator();
						while (continuousDeliveryIterator.hasNext()) {
							ContinuousDelivery continuousDelivery = (ContinuousDelivery) continuousDeliveryIterator.next();
							if (continuousDelivery.getName().equals(continuousDeliveryName)) {
								continuousDeliveryIterator.remove();
								break;
							}
						}
					}
				}
			}
			return ciInfoFileWriter(ciJobInfoPath, ciJobInfo);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	public void deleteJsonJobs(String appDir, List<CIJob> selectedJobs, String projectId, String name, String status) throws PhrescoException {
		try {
			if (CollectionUtils.isEmpty(selectedJobs)) {
				return;
			}
			List<CIJob> jobs = new ArrayList<CIJob>();
			List<ProjectDelivery> ciJobInfo = getCiJobInfo(appDir, "", status);
			ProjectDelivery projectDelivery = Utility.getProjectDelivery(projectId, ciJobInfo);
			List<ContinuousDelivery> continuousDeliveries = projectDelivery.getContinuousDeliveries();
			ContinuousDelivery continuousDelivery = Utility.getContinuousDelivery(name, continuousDeliveries);
			jobs = continuousDelivery.getJobs();
			if(jobs.size() == selectedJobs.size()) {
				continuousDeliveries.remove(continuousDelivery);
			} else {
				Iterator<CIJob> iterator = jobs.iterator();
				for (CIJob selectedInfo : selectedJobs) {
					while (iterator.hasNext()) {
						CIJob itrCiJob = iterator.next();
						if (itrCiJob.getJobName().equals(selectedInfo.getJobName())) {
							iterator.remove();
							break;
						}
					}
				}
			}
			String ciJobInfoPath = Utility.getCiJobInfoPath(appDir, "", status);
			ciInfoFileWriter(ciJobInfoPath, ciJobInfo);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	public boolean createJobTemplates(List<CIJobTemplate> ciJobTemplates, boolean createNewFile, List<ApplicationInfo> appInfos) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.createJobTemplate(List<CIJobTemplate> ciJobTemplates, boolean createNewFile)");
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			List<String> appIds = ciJobTemplates.get(0).getAppIds();
			for (ApplicationInfo applicationInfo : appInfos) {
				for (String appId : appIds) {
					if(appId.equals(applicationInfo.getName())) {
						if(CollectionUtils.isNotEmpty(applicationInfo.getModules())) {
							List<ModuleInfo> modules = applicationInfo.getModules();
							for (ModuleInfo moduleInfo : modules) {
								File jobTemplateFile = getJobTemplateFile(applicationInfo.getAppDirName(), moduleInfo.getCode());
								List<CIJobTemplate> jobTemplates = getJobTemplates(applicationInfo.getAppDirName(), moduleInfo.getCode());
								if (CollectionUtils.isEmpty(jobTemplates) || createNewFile) {
									jobTemplates = new ArrayList<CIJobTemplate>();
								}
								
								ciJobTemplates.get(0).setAppIds(Arrays.asList(""));
								if(ciJobTemplates.get(0).getModule().equalsIgnoreCase(moduleInfo.getCode())) {
									jobTemplates.addAll(ciJobTemplates);
								}
								Gson gson = new Gson();
								fw = new FileWriter(jobTemplateFile);
								bw = new BufferedWriter(fw);
								String templatesJson = gson.toJson(jobTemplates);
								bw.write(templatesJson);
								bw.flush();
								bw.close();
							}
							
						} else {
							File jobTemplateFile = getJobTemplateFile(applicationInfo.getAppDirName(), null);
							List<CIJobTemplate> jobTemplates = getJobTemplates(applicationInfo.getAppDirName(), null);
							if (CollectionUtils.isEmpty(jobTemplates) || createNewFile) {
								jobTemplates = new ArrayList<CIJobTemplate>();
							}
							
							ciJobTemplates.get(0).setAppIds(Arrays.asList(""));
							jobTemplates.addAll(ciJobTemplates);
			
							Gson gson = new Gson();
							fw = new FileWriter(jobTemplateFile);
							bw = new BufferedWriter(fw);
							String templatesJson = gson.toJson(jobTemplates);
							bw.write(templatesJson);
							bw.flush();
							bw.close();
						}
						
					}
				}
			}
			return true;
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of CI.createJobTemplate()"
						+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
			Utility.closeStream(fw);
		}
	}

	public List<CIJobTemplate> getJobTemplates(String appId, String moduleName) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getJobTemplates()");
		}
		FileReader jobTemplateFileReader = null;
		BufferedReader br = null;
		List<CIJobTemplate> ciJobTemplates = null;
		try {
			File jobTemplateFile = getJobTemplateFile(appId, moduleName);
			if (!jobTemplateFile.exists()) {
				return ciJobTemplates;
			}

			jobTemplateFileReader = new FileReader(jobTemplateFile);
			br = new BufferedReader(jobTemplateFileReader);

			Type type = new TypeToken<List<CIJobTemplate>>() {
			}.getType();
			Gson gson = new Gson();
			ciJobTemplates = gson.fromJson(br, type);
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of CI.getJobTemplates()"
						+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		} finally {
			Utility.closeStream(br);
			Utility.closeStream(jobTemplateFileReader);
		}
		return ciJobTemplates;
	}

	public List<ProjectDelivery> getCiJobInfo(String appDir, String globalInfo, String status) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getCiJobInfo(String appDir)");
		}
		List<ProjectDelivery> projectDelivery = new ArrayList<ProjectDelivery>();
		String ciJobInfoPath = Utility.getCiJobInfoPath(appDir, globalInfo, status);
		File ciJobInfoFile = new File(ciJobInfoPath);
		if(!ciJobInfoFile.exists()) {
			return new ArrayList<ProjectDelivery>();
		}
		projectDelivery = Utility.getProjectDeliveries(ciJobInfoFile);
		return projectDelivery;
	}

	public List<CIJobTemplate> getJobTemplatesByAppId(String appDirName, String appName, String moduleName) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getJobTemplatesByAppid(String appDir)");
		}
		List<CIJobTemplate> ciJobTemplates = null;
		try {
			if (StringUtils.isEmpty(appDirName)) {
				return ciJobTemplates;
			}

			List<CIJobTemplate> jobTemplates = getJobTemplates(appDirName, moduleName);
			if (CollectionUtils.isEmpty(jobTemplates)) {
				return ciJobTemplates;
			}
			
			for (CIJobTemplate ciJobTemplate : jobTemplates) {
				ciJobTemplate.setAppIds(Arrays.asList(appName));
			}
			ciJobTemplates = new ArrayList<CIJobTemplate>(jobTemplates.size());

			for (CIJobTemplate ciJobTemplate : jobTemplates) {
				List<String> appIds = ciJobTemplate.getAppIds();
				for (String selAppId : appIds) {
					if (appDirName.equals(selAppId) || appName.equals(selAppId)) {
						ciJobTemplates.add(ciJobTemplate);
					}
				}
			}
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of CI.getJobTemplatesByAppid()"
						+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
		return ciJobTemplates;
	}


	public List<CIJobTemplate> getJobTemplatesByProjId(String projId, List<ApplicationInfo> appInfos)
			throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getJobTemplatesByProjId(String projId)");
		}
		List<CIJobTemplate> jobTemplates = new ArrayList<CIJobTemplate>();
		List<CIJobTemplate> templates = new ArrayList<CIJobTemplate>();
		try {
			if (StringUtils.isEmpty(projId)) {
				return jobTemplates;
			}
			for (ApplicationInfo appInfo : appInfos) {
				if(CollectionUtils.isNotEmpty(appInfo.getModules())) {
					//read jobTemplates in multi module application
					List<ModuleInfo> modules = appInfo.getModules();
					for (ModuleInfo moduleInfo : modules) {
						List<CIJobTemplate> appJobTemplates = getJobTemplates(appInfo.getAppDirName(), moduleInfo.getCode());
						if (CollectionUtils.isNotEmpty(appJobTemplates)) {
							for (CIJobTemplate ciJobTemplate : appJobTemplates) {
								ciJobTemplate.setAppIds(Arrays.asList(appInfo.getName()));
								ciJobTemplate.setModule(moduleInfo.getCode());
							}
							jobTemplates.addAll(appJobTemplates);
						}
						
					}
					
				} else {
					//read jobtemplates in single module application
					List<CIJobTemplate> appJobTemplates = getJobTemplates(appInfo.getAppDirName(), null);
					
					if (CollectionUtils.isNotEmpty(appJobTemplates)) {
						
						for (CIJobTemplate ciJobTemplate : appJobTemplates) {
							ciJobTemplate.setAppIds(Arrays.asList(appInfo.getName()));
						}
						if (CollectionUtils.isEmpty(jobTemplates)) {
							jobTemplates = new ArrayList<CIJobTemplate>();
							jobTemplates.addAll(appJobTemplates);
						} else {
							
							for (CIJobTemplate appJobTemplate : appJobTemplates) { 
	
								boolean isFound = false;
								for (CIJobTemplate jobTemplate : jobTemplates) {
									if (appJobTemplate.getName().equals(jobTemplate.getName())) {
										appJobTemplate.setAppIds(Arrays.asList(appInfo.getName()));
										String appIdOfAppJobTemplate = appJobTemplate.getAppIds().get(0);
										List<String> appids = new ArrayList<String>();
										List<String> jobTemplateAppIds = jobTemplate.getAppIds();
										appids.add(appIdOfAppJobTemplate);
										appids.addAll(jobTemplateAppIds);
										jobTemplate.setAppIds(appids);
										isFound = true;
										break;
									}
								}
								// if application job template is not found need to add it
								if (!isFound) {
									jobTemplates.add(appJobTemplate);
								}
	
							}
						}
	
					}
				}
			}

		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of CI.getJobTemplatesByProjId()"
						+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
		return jobTemplates;
	}

	public boolean updateJobTemplate(CIJobTemplate ciJobTemplate, String oldName, String projId,  List<ApplicationInfo> appInfos) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.updateJobTemplate(CIJobTemplate ciJobTemplate, String oldName, String projId)");
		}
		try {
			if (StringUtils.isEmpty(oldName)) {
				return false;
			}
			boolean deleteJobTemplate = deleteJobTemplate(oldName, projId, appInfos);//need to handle
			if (deleteJobTemplate) {
				boolean createJobTemplates = createJobTemplates(Arrays.asList(ciJobTemplate), false, appInfos);
				return createJobTemplates;
			}
			return false;
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of CI.updateJobTemplate(CIJobTemplate ciJobTemplate, String oldName, String projId)"
						+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
	}

	public boolean deleteJobTemplate(String jobTemplateName, String projId, List<ApplicationInfo> appInfos) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.deleteJobTemplates()");
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			if (StringUtils.isEmpty(jobTemplateName)) {
				return false; 
			}

			for (ApplicationInfo applicationInfo : appInfos) {
				if(CollectionUtils.isNotEmpty(applicationInfo.getModules())) {
					List<ModuleInfo> modules = applicationInfo.getModules();
					for (ModuleInfo moduleInfo : modules) {
						File jobTemplateFile = getJobTemplateFile(applicationInfo.getAppDirName(), moduleInfo.getCode());
						List<CIJobTemplate> jobTemplates = getJobTemplates(applicationInfo.getAppDirName(), moduleInfo.getCode());
						if(CollectionUtils.isNotEmpty(jobTemplates)) {
							Iterator<CIJobTemplate> jobTemplateIterator = jobTemplates.iterator();
							while (jobTemplateIterator.hasNext()) {
								CIJobTemplate jobTemplate = (CIJobTemplate) jobTemplateIterator.next();
								if (jobTemplate.getName().equals(jobTemplateName)) {
									jobTemplateIterator.remove();
									break;
								}
							}

							Gson gson = new Gson();
							fw = new FileWriter(jobTemplateFile);
							bw = new BufferedWriter(fw);
							String templatesJson = gson.toJson(jobTemplates);
							bw.write(templatesJson);
							bw.flush();
							bw.close();
						}
					}
				} else {
					File jobTemplateFile = getJobTemplateFile(applicationInfo.getAppDirName(), null);
					List<CIJobTemplate> jobTemplates = getJobTemplates(applicationInfo.getAppDirName(), null);
					if(CollectionUtils.isNotEmpty(jobTemplates)) {
						Iterator<CIJobTemplate> jobTemplateIterator = jobTemplates.iterator();
						while (jobTemplateIterator.hasNext()) {
							CIJobTemplate jobTemplate = (CIJobTemplate) jobTemplateIterator.next();
							if (jobTemplate.getName().equals(jobTemplateName)) {
								jobTemplateIterator.remove();
								break;
							}
						}

						Gson gson = new Gson();
						fw = new FileWriter(jobTemplateFile);
						bw = new BufferedWriter(fw);
						String templatesJson = gson.toJson(jobTemplates);
						bw.write(templatesJson);
						bw.flush();
						bw.close();
					}
				}
				
			}
			return true;
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of CI.deleteJobTemplates()"
						+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
			Utility.closeStream(fw);
		}
	}

	private String getJobTemplatePath(String appDirName, String moduleName) throws PhrescoException, PhrescoPomException {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		String splitPath = Utility.splitPathConstruction(appDirName);
		builder.append(splitPath);
		if(StringUtils.isNotEmpty(moduleName)) {
			builder.append(File.separator);
			builder.append(moduleName);
		}
		builder.append(PHRESCO);
		builder.append(File.separator);
		builder.append(CI_JOB_TEMPLATE_NAME);
		return builder.toString();
	}

	private File getJobTemplateFile(String appDirName, String moduleName) throws PhrescoException {
		File jobTemplateFile = null;
		String jobTemplatePath;
		try {
			jobTemplatePath = getJobTemplatePath(appDirName, moduleName);
			jobTemplateFile = new File(jobTemplatePath);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return jobTemplateFile;
	}

	public CIJobStatus generateBuild(CIJob ciJob) throws PhrescoException {
		try {
			CIJobStatus jobStatus = buildJob(ciJob);
			return jobStatus;
		} catch (ClientHandlerException ex) {
			S_LOGGER.error(ex.getLocalizedMessage());
			throw new PhrescoException(ex);
		}
	}

	public String getJobStatus(CIJob ciJob) throws PhrescoException {
		try {
			String status = getStatus(ciJob);
			return status;
		} catch (ClientHandlerException ex) {
			S_LOGGER.error(ex.getLocalizedMessage());
			throw new PhrescoException(ex);
		}
	}

	public CIJobStatus deleteBuilds(CIJob ciJob,  String buildNumber) throws PhrescoException {
		S_LOGGER.debug("Entering Method ProjectAdministratorImpl.deleteCI()");
		try {
			CIJobStatus deleteCI = null;
			deleteCI = deleteCI(ciJob, buildNumber);
			return deleteCI;
		} catch (ClientHandlerException ex) {
			S_LOGGER.error("Entered into catch block of ProjectAdministratorImpl.deleteCI()" + ex.getLocalizedMessage());
			throw new PhrescoException(ex);
		}
	}

	public CIJobStatus deleteJobs(String appDir, List<CIJob> ciJobs, String projectId, String continuousName) throws PhrescoException {
		S_LOGGER.debug("Entering Method ProjectAdministratorImpl.deleteCI()");
		try {
			CIJobStatus deleteCI = null;
			for (CIJob ciJob : ciJobs) {
				S_LOGGER.debug(" Deleteable job name " + ciJob.getJobName());
				deleteCI = deleteCI(ciJob, null);
				S_LOGGER.debug("write back json data after job deletion successfull");
			}
			//clearContinuousDelivery(continuousName, projectId, appDir);
			return deleteCI;
		} catch (ClientHandlerException ex) {
			S_LOGGER.error("Entered into catch block of ProjectAdministratorImpl.deleteCI()" + ex.getLocalizedMessage());
			throw new PhrescoException(ex);
		}
	}

	public boolean isJobCreatingBuild(CIJob ciJob) throws PhrescoException {
		S_LOGGER.debug("Entering Method ProjectAdministratorImpl.isBuilding()");
		try {
			int isBuilding = getProgressInBuild(ciJob);
			if (isBuilding > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	private int getProgressInBuild(CIJob job) throws PhrescoException {
		S_LOGGER.debug("Entering Method CIManagerImpl.isBuilding(CIJob job)");
		String jenkinsUrl = FrameworkUtil.getJenkinsUrl(job);
		String isBuildingUrlUrl = FORWARD_SLASH + BUSY_EXECUTORS;
		String jsonResponse = getJsonResponse(jenkinsUrl + isBuildingUrlUrl);
		int buidInProgress = Integer.parseInt(jsonResponse);
		S_LOGGER.debug("buidInProgress " + buidInProgress);
		return buidInProgress;
	}

	public void streamToFile(File dest, InputStream ip) throws PhrescoException {
		try {
			OutputStream out;
			out = new FileOutputStream(dest);
			byte buf[] = new byte[5024];
			int len;
			while ((len = ip.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			ip.close();
		} catch (Exception e) {
			S_LOGGER.error("Entered into the catch block of CIManagerImpl.streamToFile" + e.getLocalizedMessage());
			throw new PhrescoException(e);
		}
	}

	public boolean setSVNCredentials(String submitUrl, String svnUrl, String username, String password) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.setSVNCredentials()");
		}
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(submitUrl);
			HttpContext httpContext = new BasicHttpContext();
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ciManager.getAuthenticatedHttpClient(client, httpContext);
			
			BasicCookieStore cookieStore =  new BasicCookieStore();
			httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

			String json = "";
			String boundary="---------------------------28244134517666";
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, boundary, Charset.forName("UTF-8"));
			reqEntity.addPart("url", new StringBody(svnUrl));
			reqEntity.addPart("kind", new StringBody("password"));
			reqEntity.addPart("username1", new StringBody(username));
			reqEntity.addPart("password1", new StringBody(password));
			reqEntity.addPart("username2", new StringBody(""));
			reqEntity.addPart("password2", new StringBody(""));
			reqEntity.addPart("privateKey", new StringBody("Content-Type: application/octet-stream"));
			reqEntity.addPart("certificate", new StringBody("Content-Type: application/octet-stream"));
			reqEntity.addPart("password3", new StringBody(""));
			reqEntity.addPart("json", new StringBody(json));
			reqEntity.addPart("Submit", new StringBody("OK"));

			post.setEntity(reqEntity);
			HttpResponse response = client.execute(post, httpContext);
			int statusCode = response.getStatusLine().getStatusCode();
			// 302 success
			if (302 == statusCode) {
				return true;
			}
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into the catch block of CIManagerImpl.setSVNCredentials" + e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
		return false;
	}

	public boolean setGlobalConfiguration(String jenkinsUrl, String submitUrl, String confluenceUrl, String confluenceUsername, String confluencePassword, String emailAddress, String emailPassword, org.json.JSONArray testFlightJSONarray, String tfsUrl) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.setGlobalConfiguration()");
		}
		try {
			// TODO : jenkinsUrl should end with /
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(submitUrl);
			HttpContext httpContext = new BasicHttpContext();
			//Configuring global configuration in remote jenkins
//			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
//			ciManager.getAuthenticatedHttpClient(client, httpContext);
			
			CookieStore cookieStore =  new BasicCookieStore();
			httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

			String globalConfig = "/ciGlobalConfig.json";
			InputStream resourceAsStream = CIManagerImpl.class.getResourceAsStream(globalConfig);
			String json = IOUtils.toString(resourceAsStream);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			JSONObject jsonObj = new JSONObject(json);

			// Email address
			if(StringUtils.isNotEmpty(emailAddress)) {
				String smtpServer="smtp.gmail.com";
				JSONObject mailConfiguration = new JSONObject();
				// Mail config
				mailConfiguration.put("url", jenkinsUrl);
				mailConfiguration.put("smtpServer", smtpServer);
				mailConfiguration.put("defaultSuffix", "");
				mailConfiguration.put("adminAddress", emailAddress); 
				// UseSMTPAuth
				JSONObject emailCrdential = new JSONObject();
				emailCrdential.put("smtpAuthUserName", emailAddress); 
				emailCrdential.put("smtpAuthPassword", emailPassword);
				mailConfiguration.put("useSMTPAuth", emailCrdential);
				// Basic
				mailConfiguration.put("useSsl", true);
				mailConfiguration.put("smtpPort", "465");
				mailConfiguration.put("charset", "UTF-8");

				jsonObj.put("hudson-tasks-Mailer", mailConfiguration);
				nameValuePairs.add(new BasicNameValuePair("_.smtpServer", smtpServer));
				nameValuePairs.add(new BasicNameValuePair("_.adminAddress", emailAddress));
			}

			// Confluence list need to be handled
			if(StringUtils.isNotEmpty(confluenceUrl)) {
				JSONObject confluenceConfig = new JSONObject();
				confluenceConfig.put("url", confluenceUrl);
				confluenceConfig.put("username", confluenceUsername);
				confluenceConfig.put("password", confluencePassword);

				JSONObject confluenceSites = new JSONObject();
				confluenceSites.put("sites", confluenceConfig); 
				jsonObj.put("com-myyearbook-hudson-plugins-confluence-ConfluencePublisher", confluenceSites);

				// array
				nameValuePairs.add(new BasicNameValuePair("_.url", confluenceUrl));
				nameValuePairs.add(new BasicNameValuePair("_.username", confluenceUsername));
				nameValuePairs.add(new BasicNameValuePair("_.password", confluencePassword));
			}

			return postConfigData(jenkinsUrl, client, post, httpContext, nameValuePairs, jsonObj, tfsUrl);
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into the catch block of CIManagerImpl.setGlobalConfiguration" + e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
	}

	public boolean setGlobalConfiguration(String jenkinsUrl, String submitUrl,	org.json.JSONArray confluenceObj, String emailAddress, String emailPassword, org.json.JSONArray testFlightJSONarray, org.json.JSONArray keyChainJSONarray, String tfsUrl) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.setGlobalConfiguration()");
		}
		try {
			// TODO : jenkinsUrl should end with /
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(submitUrl);
			HttpContext httpContext = new BasicHttpContext();
			CookieStore cookieStore =  new BasicCookieStore();
			httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			//Configuring global configuration in remote jenkins
//			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
//			ciManager.getAuthenticatedHttpClient(client, httpContext);
			
			String globalConfig = "/ciGlobalConfig.json";
			InputStream resourceAsStream = CIManagerImpl.class.getResourceAsStream(globalConfig);
			String json = IOUtils.toString(resourceAsStream);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			JSONObject jsonObj = new JSONObject(json);

			// Email address
			if(StringUtils.isNotEmpty(emailAddress)) {
				String smtpServer="smtp.gmail.com";
				JSONObject mailConfiguration = new JSONObject();
				// Mail config
				//				String string = "http://localhost:3579/ci/"; jenkins url
				mailConfiguration.put("url", jenkinsUrl);
				mailConfiguration.put("smtpServer", smtpServer);
				mailConfiguration.put("defaultSuffix", "");
				mailConfiguration.put("adminAddress", emailAddress); 
				// UseSMTPAuth
				JSONObject emailCrdential = new JSONObject();
				emailCrdential.put("smtpAuthUserName", emailAddress); 
				emailCrdential.put("smtpAuthPassword", emailPassword);
				mailConfiguration.put("useSMTPAuth", emailCrdential);
				// Basic
				mailConfiguration.put("useSsl", true);
				mailConfiguration.put("smtpPort", "465");
				mailConfiguration.put("charset", "UTF-8");

				jsonObj.put("hudson-tasks-Mailer", mailConfiguration);
				nameValuePairs.add(new BasicNameValuePair("_.smtpServer", smtpServer));
				nameValuePairs.add(new BasicNameValuePair("_.adminAddress", emailAddress));
			}

			// Confluence list need to be handled
			if(confluenceObj != null) {
				JSONObject confluenceSites = new JSONObject();
				confluenceSites.put("sites", confluenceObj);
				jsonObj.put("com-myyearbook-hudson-plugins-confluence-ConfluencePublisher", confluenceSites);
			}
			
			if(testFlightJSONarray != null) {
				JSONObject testFlights = new JSONObject();
				testFlights.put("tokenPairs", testFlightJSONarray);
//				jsonObj.put("testflight.TestflightRecorder", testFlights);
			 	for (int j = 0; j<testFlightJSONarray.length(); j++) {
			 		JSONObject jsonObject = testFlightJSONarray.getJSONObject(j);
					nameValuePairs.add(new BasicNameValuePair("tokenPair.tokenPairName", jsonObject.getString("tokenPairName")));
					nameValuePairs.add(new BasicNameValuePair("tokenPair.apiToken", jsonObject.getString("apiToken")));
					nameValuePairs.add(new BasicNameValuePair("tokenPair.teamToken", jsonObject.getString("teamToken")));
			 	}
			}
			
			if(keyChainJSONarray != null) {
				JSONObject keyChains = new JSONObject();
				keyChains.put("xcodebuildPath", "/usr/bin/xcodebuild");
				keyChains.put("agvtoolPath", "/usr/bin/agvtool");
				keyChains.put("xcrunPath", "/usr/bin/xcrun");
				keyChains.put("keychain", keyChainJSONarray);
				keyChains.put("defaultKeychain", "");
				
				jsonObj.put("au-com-rayh-GlobalConfigurationImpl", keyChains);
				nameValuePairs.add(new BasicNameValuePair("_.xcodebuildPath", "/usr/bin/xcodebuild"));
				nameValuePairs.add(new BasicNameValuePair("_.agvtoolPath", "/usr/bin/agvtool"));
				nameValuePairs.add(new BasicNameValuePair("_.xcrunPath", "/usr/bin/xcrun"));
				nameValuePairs.add(new BasicNameValuePair("_.defaultKeychain", ""));
			 	for (int j = 0; j<keyChainJSONarray.length(); j++) {
			 		JSONObject jsonObject = keyChainJSONarray.getJSONObject(j);
					nameValuePairs.add(new BasicNameValuePair("keychain.keychainName", jsonObject.getString("keychainName")));
					nameValuePairs.add(new BasicNameValuePair("keychain.keychainPath", jsonObject.getString("keychainPath")));
					nameValuePairs.add(new BasicNameValuePair("keychain.keychainPassword", jsonObject.getString("keychainPassword")));
					nameValuePairs.add(new BasicNameValuePair("keychain.inSearchPath", "true"));
			 	}
			}
			
			return postConfigData(jenkinsUrl, client, post, httpContext, nameValuePairs, jsonObj, tfsUrl);
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into the catch block of CIManagerImpl.setGlobalConfiguration" + e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
	}

	private boolean postConfigData(String jenkinsUrl, HttpClient client, HttpPost post, HttpContext httpContext, List<NameValuePair> nameValuePairs, JSONObject jsonObj, String tfsUrl) throws Exception {
		// Default values
		nameValuePairs.add(new BasicNameValuePair("_.rawWorkspaceDir","${JENKINS_HOME}/workspace/${ITEM_FULLNAME}"));
		nameValuePairs.add(new BasicNameValuePair("_.rawBuildsDir","${ITEM_ROOTDIR}/builds"));
		//system_message
		nameValuePairs.add(new BasicNameValuePair("_.numExecutors","2"));
		nameValuePairs.add(new BasicNameValuePair("_.quietPeriod","5"));
		nameValuePairs.add(new BasicNameValuePair("_.scmCheckoutRetryCount","0"));
		nameValuePairs.add(new BasicNameValuePair("slaveAgentPortType","random"));
		nameValuePairs.add(new BasicNameValuePair("stapler-class","hudson.markup.RawHtmlMarkupFormatter"));
		nameValuePairs.add(new BasicNameValuePair("stapler-class","hudson.plugins.collabnet.auth.CollabNetSecurityRealm"));
		// collabnet url
		nameValuePairs.add(new BasicNameValuePair("_.enableSSOAuthFromCTF","on"));
		nameValuePairs.add(new BasicNameValuePair("_.enableSSOAuthToCTF","on"));
		nameValuePairs.add(new BasicNameValuePair("stapler-class","hudson.security.LegacySecurityRealm"));
		nameValuePairs.add(new BasicNameValuePair("stapler-class","hudson.security.HudsonPrivateSecurityRealm"));
		nameValuePairs.add(new BasicNameValuePair("privateRealm.allowsSignup","on"));
		nameValuePairs.add(new BasicNameValuePair("stapler-class","hudson.security.LDAPSecurityRealm"));
		//ldap.server
		nameValuePairs.add(new BasicNameValuePair("authorization","0"));
		nameValuePairs.add(new BasicNameValuePair("stapler-class","hudson.security.AuthorizationStrategy$Unsecured"));
		nameValuePairs.add(new BasicNameValuePair("stapler-class","hudson.plugins.collabnet.auth.CNAuthorizationStrategy"));
		nameValuePairs.add(new BasicNameValuePair("_.authCacheTimeoutMin","5"));
		nameValuePairs.add(new BasicNameValuePair("stapler-class","hudson.security.LegacyAuthorizationStrategy"));
		nameValuePairs.add(new BasicNameValuePair("stapler-class","hudson.security.FullControlOnceLoggedInAuthorizationStrategy"));
		nameValuePairs.add(new BasicNameValuePair("stapler-class","hudson.security.GlobalMatrixAuthorizationStrategy"));
		nameValuePairs.add(new BasicNameValuePair("stapler-class","hudson.security.ProjectMatrixAuthorizationStrategy"));
		nameValuePairs.add(new BasicNameValuePair("stapler-class","hudson.security.csrf.DefaultCrumbIssuer"));
		nameValuePairs.add(new BasicNameValuePair("_.name","JAVA_HOME"));
		nameValuePairs.add(new BasicNameValuePair("_.home","${JAVA_HOME}"));
		nameValuePairs.add(new BasicNameValuePair("stapler-class-bag","true"));
		nameValuePairs.add(new BasicNameValuePair("_.name","Default"));
		//	nameValuePairs.add(new BasicNameValuePair("_.home","git.exe")); // git home
		nameValuePairs.add(new BasicNameValuePair("_.home","git"));
		nameValuePairs.add(new BasicNameValuePair("stapler-class-bag","true"));
		nameValuePairs.add(new BasicNameValuePair("_.name","MAVEN_HOME"));
		nameValuePairs.add(new BasicNameValuePair("_.home","${M2_HOME}"));
		nameValuePairs.add(new BasicNameValuePair("stapler-class-bag","true"));
		nameValuePairs.add(new BasicNameValuePair("_.usageStatisticsCollected","on"));
		//TFS
		nameValuePairs.add(new BasicNameValuePair("tfs.tfExecutable",tfsUrl));
		nameValuePairs.add(new BasicNameValuePair("svn.workspaceFormat","8"));
		nameValuePairs.add(new BasicNameValuePair("svn.storeAuthToDisk","on"));
		nameValuePairs.add(new BasicNameValuePair("ext_mailer_admin_address","address not configured yet <nobody>"));
		nameValuePairs.add(new BasicNameValuePair("ext_mailer_hudson_url", jenkinsUrl)); //"http://localhost:3579/ci/"
		nameValuePairs.add(new BasicNameValuePair("ext_mailer_default_content_type","text/plain"));
		nameValuePairs.add(new BasicNameValuePair("ext_mailer_default_subject","$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!"));
		nameValuePairs.add(new BasicNameValuePair("ext_mailer_default_body","$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:\nCheck console output at $BUILD_URL to view the results."));
		nameValuePairs.add(new BasicNameValuePair("_.url", jenkinsUrl)); //"http://localhost:3579/ci/"
		nameValuePairs.add(new BasicNameValuePair("_.charset","UTF-8"));
		nameValuePairs.add(new BasicNameValuePair("url", jenkinsUrl + "github-webhook/")); //"http://localhost:3579/ci/github-webhook/"
		nameValuePairs.add(new BasicNameValuePair("hookMode","none"));
		nameValuePairs.add(new BasicNameValuePair("json", jsonObj.toString()));
		nameValuePairs.add(new BasicNameValuePair("Submit", "Save"));

		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = client.execute(post,httpContext);
		int statusCode = response.getStatusLine().getStatusCode();
		if (302 == statusCode) {
			return true;
		}
		return false;
	}
}
