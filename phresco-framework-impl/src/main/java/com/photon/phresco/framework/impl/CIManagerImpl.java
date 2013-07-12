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
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.plexus.util.FileUtils;
import org.jdom.JDOMException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.api.CIManager;
import com.photon.phresco.framework.model.CIBuild;
import com.photon.phresco.framework.model.CIJob;
import com.photon.phresco.framework.model.CIJobStatus;
import com.photon.phresco.framework.model.CIJobTemplate;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientHandlerException;
import com.trilead.ssh2.crypto.Base64;

public class CIManagerImpl implements CIManager, FrameworkConstants {

	private static final Logger S_LOGGER = Logger.getLogger(CIManagerImpl.class);
	private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();
	private static boolean credExist = false;
	
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
	
	 public boolean createJob(ApplicationInfo appInfo, CIJob job) throws PhrescoException {
		 if (debugEnabled) {
			 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.createJob(Project project, CIJob job)");
		 }
		 FileWriter writer = null;
		 try {
			 CIJobStatus jobStatus = configureJob(job, FrameworkConstants.CI_CREATE_JOB_COMMAND);
			 if (jobStatus.getCode() == -1) {
				 throw new PhrescoException(jobStatus.getMessage());
			 }
			 if (debugEnabled) {
				 S_LOGGER.debug("ProjectInfo = " + appInfo);
			 }
			 writeJsonJobs(appInfo, Arrays.asList(job), CI_APPEND_JOBS);
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
		 return credExist;
	 }

	 public boolean updateJob(ApplicationInfo appInfo, CIJob job) throws PhrescoException {
		 if (debugEnabled) {
			 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.updateJob(Project project, CIJob job)");
		 }
		 FileWriter writer = null;
		 try {
			 CIJobStatus jobStatus = configureJob(job, FrameworkConstants.CI_UPDATE_JOB_COMMAND);
			 if (jobStatus.getCode() == -1) {
				 throw new PhrescoException(jobStatus.getMessage());
			 }
			 if (debugEnabled) {
				 S_LOGGER.debug("getCustomModules() ProjectInfo = "+ appInfo);
			 }
			 updateJsonJob(appInfo, job);
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
		 return credExist;
	 }
	
    private CIJobStatus configureJob(CIJob job, String jobType) throws PhrescoException {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method CIManagerImpl.createJob(CIJob job)");
    	}
    	try {
            cli = getCLI(job);
            List<String> argList = new ArrayList<String>();
            argList.add(jobType);
            argList.add(job.getName());
            
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
            	credExist = setSvnCredential(job);
            }
            
            return new CIJobStatus(result, message);
        } catch (IOException e) {
            throw new PhrescoException(e);
        } catch (JDOMException e) {
            throw new PhrescoException(e);
        } finally {
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
    
    public boolean setSvnCredential(CIJob job) throws JDOMException, IOException {
    	S_LOGGER.debug("Entering Method CIManagerImpl.setSvnCredential");
    	try {
    		String jenkinsJobHome = System.getenv(JENKINS_HOME);
    		StringBuilder jenkinsHome = new StringBuilder(jenkinsJobHome);
    		jenkinsHome.append(File.separator);
    		File file = new File(jenkinsHome.toString() + CI_CREDENTIAL_XML);
    		if(!file.exists()) {
    			String jenkinsTemplateDir = Utility.getJenkinsTemplateDir();
    			String credentialFilePath = jenkinsTemplateDir + job.getRepoType() + HYPHEN + CREDENTIAL_XML;
    			File credentialFile = new File(credentialFilePath);
    			FileUtils.copyFile(credentialFile, file);
    		}
    		String svnUrl = job.getSvnUrl();
    		String stringValue = getRealm(svnUrl);
    		SvnProcessor svn = new SvnProcessor(file);
    		return svn.writeSvnXml(stringValue, job);
    	} catch (Exception e) {
    		S_LOGGER.error("Entered into the catch block of CIManagerImpl.setSvnCredential " + e.getLocalizedMessage());
    		return false;
    	}
    }


    private String getRealm(String repoUrl) throws PhrescoException {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method CIManagerImpl.getRealm(String repoUrl)");
    	}
    	URLConnection openedUrl;
    	String stringValue = "";
    	try {
    		URL url = new URL(repoUrl);
    		String host = url.getHost();
    		openedUrl = url.openConnection();

    		Map<String, List<String>> hf = openedUrl.getHeaderFields();
    		for (String key : hf.keySet()) {
    			if (WWW_AUTHENTICATE.equalsIgnoreCase(key)) {
    				String headerField = openedUrl.getHeaderField(key);
    				String[] split = headerField.split("\"");
    				stringValue = LT_HTTPS_COLON_BACKSLASH+host+COLON_443_GT_SPACE+split[1];
    			}
    		}
    	} catch (IOException e) {
    		if (debugEnabled) {
    			S_LOGGER.error("Entered into the catch block of CIManagerImpl.getRealm " + e.getLocalizedMessage());
    		}
    		throw new PhrescoException(e);
    	}
    	return stringValue;
    }
    
    public void saveMailConfiguration(String jenkinsPort, String senderEmailId, String senderEmailPassword) throws PhrescoException {
        if (debugEnabled) {
        	S_LOGGER.debug("Entering Method CIManagerImpl.saveMailConfiguration");
        }
        try {
            String jenkinsTemplateDir = Utility.getJenkinsTemplateDir();
            String mailFilePath = jenkinsTemplateDir + MAIL + HYPHEN + CREDENTIAL_XML;
            if (debugEnabled) {
            	S_LOGGER.debug("configFilePath ... " + mailFilePath);
            }
            File mailFile = new File(mailFilePath);
            
            SvnProcessor processor = new SvnProcessor(mailFile);
            
			// Mail have to go with jenkins running email address
			InetAddress ownIP = InetAddress.getLocalHost();
			processor.changeNodeValue(CI_HUDSONURL, HTTP_PROTOCOL + PROTOCOL_POSTFIX + ownIP.getHostAddress() + COLON + jenkinsPort + FORWARD_SLASH + CI + FORWARD_SLASH);
            processor.changeNodeValue("smtpAuthUsername", senderEmailId);
            processor.changeNodeValue("smtpAuthPassword", encyPassword(senderEmailPassword));
            processor.changeNodeValue("adminAddress", senderEmailId);
            
            //jenkins home location
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
        	S_LOGGER.debug("Entering Method CIManagerImpl.saveMailConfiguration");
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
    		svn.writeConfluenceXml(confluenceUrl, confluenceUsername, encyPassword(confluencePassword));
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into the catch block of CIManagerImpl.saveConfluenceConfiguration " + e.getLocalizedMessage());
			}
       		throw new PhrescoException(e);
		}
    }
    
    
	public String encyPassword(String password) throws PhrescoException {
		if (debugEnabled) {
	        	S_LOGGER.debug("Entering Method CIManagerImpl.encyPassword");
	    }
		String encString = "";
		try {
			Cipher cipher = Cipher.getInstance(AES_ALGO);
			cipher.init(Cipher.ENCRYPT_MODE, getAes128Key(CI_SECRET_KEY));
			encString = new String(Base64.encode(cipher.doFinal((password+CI_ENCRYPT_MAGIC).getBytes(CI_UTF8))));
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into the catch block of CIManagerImpl.encyPassword " + e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
		return encString;
	}
	
	public String decyPassword(String encryptedText) throws PhrescoException {
	    	if (debugEnabled) {
				S_LOGGER.error("Entered Method SvnProcessor.decyPassword");
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
    		S_LOGGER.debug("Entering Method CIManagerImpl.clearConfluenceSitesNodes");
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
        	S_LOGGER.debug("Entering Method CIManagerImpl.getMailConfiguration");
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
    			return processor.readConfluenceXml();
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
    
    private CIJobStatus buildJob(CIJob job) throws PhrescoException {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method CIManagerImpl.buildJob(CIJob job)");
    	}
    	cli = getCLI(job);
        
        List<String> argList = new ArrayList<String>();
        argList.add(FrameworkConstants.CI_BUILD_JOB_COMMAND);
        argList.add(job.getName());
        try {
            int status = cli.execute(argList);
            String message = FrameworkConstants.CI_BUILD_STARTED;
            if (status == FrameworkConstants.JOB_STATUS_NOTOK) {
                message = FrameworkConstants.CI_BUILD_STARTING_ERROR;
            }
            return new CIJobStatus(status, message);
        } finally {
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
    		S_LOGGER.debug("Entering Method CIManagerImpl.getBuildsArray");
    	}
    	JsonArray jsonArray = null;
    	try {
        	String jenkinsUrl = "http://" + job.getJenkinsUrl() + ":" + job.getJenkinsPort() + "/ci/";
        	String jobNameUtf8 = job.getName().replace(" ", "%20");
        	String buildsJsonUrl = jenkinsUrl + "job/" + jobNameUtf8 + "/api/json";
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
    		S_LOGGER.debug("Entering Method CIManagerImpl.getCIBuilds(CIJob job)");
    	}
    	List<CIBuild> ciBuilds = null;
        try {
        	if (debugEnabled) {
        		S_LOGGER.debug("getCIBuilds()  JobName = "+ job.getName());
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
        		S_LOGGER.debug("Entering Method CIManagerImpl.getCIBuilds(CIJob job) " + e.getLocalizedMessage());
        	}
        }
        return ciBuilds;
    }
    
    private void setBuildStatus(CIBuild ciBuild, CIJob job) throws PhrescoException {
   		S_LOGGER.debug("Entering Method CIManagerImpl.setBuildStatus(CIBuild ciBuild)");
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
	        HttpGet httpget = new HttpGet(jsonUrl);
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
            return httpClient.execute(httpget, responseHandler);
        } catch (IOException e) {
            throw new PhrescoException(e);
        }
    }
    
    private CLI getCLI(CIJob job) throws PhrescoException {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method CIManagerImpl.getCLI()");
    	}
        String jenkinsUrl = HTTP_PROTOCOL + PROTOCOL_POSTFIX + job.getJenkinsUrl() + COLON + job.getJenkinsPort() + FORWARD_SLASH + CI + FORWARD_SLASH;
    	if (debugEnabled) {
    		S_LOGGER.debug("jenkinsUrl to get cli object " + jenkinsUrl);
    	}
        try {
            return new CLI(new URL(jenkinsUrl));
        } catch (MalformedURLException e) {
            throw new PhrescoException(e);
        } catch (IOException e) {
            throw new PhrescoException(e);
        } catch (InterruptedException e) {
            throw new PhrescoException(e);
        }
    }
    
    private void customizeNodes(ConfigProcessor processor, CIJob job) throws JDOMException,PhrescoException {
    	
        //SVN url customization
    	if (SVN.equals(job.getRepoType())) {
    		S_LOGGER.debug("This is svn type project!!!!!");
    		processor.changeNodeValue(SCM_LOCATIONS_REMOTE, job.getSvnUrl());
    	} else if (GIT.equals(job.getRepoType())) {
    		S_LOGGER.debug("This is git type project!!!!!");
    		processor.changeNodeValue(SCM_USER_REMOTE_CONFIGS_URL, job.getSvnUrl());
    		processor.changeNodeValue(SCM_BRANCHES_NAME, job.getBranch());
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
        Map<String, String> emails = job.getEmail();
        processor.setEmailPublisher(emails, job.getAttachmentsPattern());
//        //Failure Reception list
//        processor.changeNodeValue(TRIGGER_FAILURE_EMAIL_RECIPIENT_LIST, (String)email.get(FAILURE_EMAILS));
//        
//        //Success Reception list
//        processor.changeNodeValue(TRIGGER_SUCCESS__EMAIL_RECIPIENT_LIST, (String)email.get(SUCCESS_EMAILS));
        
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
        if (StringUtils.isNotEmpty(job.getDownStreamProject())) {
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
            processor.buildOtherProjects(job.getDownStreamProject(), name, ordinal, color);
        }
        
        // pom location specifier 
        if (StringUtils.isNotEmpty(job.getPomLocation())) {
        	S_LOGGER.debug("POM location changing " + job.getPomLocation());
        	processor.updatePOMLocation(job.getPomLocation());
        }
        
        if (job.isEnablePostBuildStep()) {
//        	String mvnCommand = job.getMvnCommand();
        	List<String> postBuildStepCommands = job.getPostbuildStepCommands(); 
        	for (String postBuildStepCommand : postBuildStepCommands) {
        		processor.enablePostBuildStep(job.getPomLocation(), postBuildStepCommand);
        	}
        }
        
        if (job.isEnablePreBuildStep()) {
        	//iterate over loop
        	List<String> preBuildStepCommands = job.getPrebuildStepCommands(); // clean install, clean package
//        	shell#SEP#cmd, mvn#SEP#clean install
        	for (String preBuildStepCommand : preBuildStepCommands) {
        		processor.enablePreBuildStep(job.getPomLocation(), preBuildStepCommand); // shell#SEP#cmd, mvn#SEP#clean install
			}
        }
        
    }
    
    private CIJobStatus deleteCI(CIJob job, List<String> builds) throws PhrescoException {
    	S_LOGGER.debug("Entering Method CIManagerImpl.deleteCI(CIJob job)");
    	S_LOGGER.debug("Job name " + job.getName());
    	cli = getCLI(job);
        String deleteType = null;
        List<String> argList = new ArrayList<String>();
        S_LOGGER.debug("job name " + job.getName());
        S_LOGGER.debug("Builds " + builds);
        if(CollectionUtils.isEmpty(builds)) {	// delete job
        	S_LOGGER.debug("Job deletion started");
        	S_LOGGER.debug("Command " + FrameworkConstants.CI_JOB_DELETE_COMMAND);
        	deleteType = DELETE_TYPE_JOB;
        	argList.add(FrameworkConstants.CI_JOB_DELETE_COMMAND);
            argList.add(job.getName());
        } else {								// delete Build
        	S_LOGGER.debug("Build deletion started");
        	deleteType = DELETE_TYPE_BUILD;
        	argList.add(FrameworkConstants.CI_BUILD_DELETE_COMMAND);
            argList.add(job.getName());
    	    StringBuilder result = new StringBuilder();
    	    for(String string : builds) {
    	        result.append(string);
    	        result.append(",");
    	    }
    	    String buildNos = result.substring(0, result.length() - 1);
        	argList.add(buildNos);
    		S_LOGGER.debug("Command " + FrameworkConstants.CI_BUILD_DELETE_COMMAND);
    		S_LOGGER.debug("Build numbers " + buildNos);
        }
        try {
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
    
	 private CIJob getJob(ApplicationInfo appInfo) throws PhrescoException {
		 Gson gson = new Gson();
		 try {
			 BufferedReader br = new BufferedReader(new FileReader(getCIJobPath(appInfo)));
			 CIJob job = gson.fromJson(br, CIJob.class);
			 br.close();
			 return job;
		 } catch (FileNotFoundException e) {
			 S_LOGGER.debug(e.getLocalizedMessage());
			 return null;
		 } catch (com.google.gson.JsonParseException e) {
			 S_LOGGER.debug("it is already adpted project !!!!! " + e.getLocalizedMessage());
			 return null;
		 } catch (IOException e) {
			 S_LOGGER.debug(e.getLocalizedMessage());
			 return null;
		 }
	 }

	 private boolean adaptExistingJobs(ApplicationInfo appInfo) {
		try {
			 CIJob existJob = getJob(appInfo);
			 S_LOGGER.debug("Going to get existing jobs to relocate!!!!!");
			 if(existJob != null) {
				 S_LOGGER.debug("Existing job found " + existJob.getName());
				 boolean deleteExistJob = deleteCIJobFile(appInfo);
				 Gson gson = new Gson();
				 List<CIJob> existingJobs = new ArrayList<CIJob>();
				 existingJobs.addAll(Arrays.asList(existJob));
				 FileWriter writer = null;
				 File ciJobFile = new File(getCIJobPath(appInfo));
				 String jobJson = gson.toJson(existingJobs);
				 writer = new FileWriter(ciJobFile);
				 writer.write(jobJson);
				 writer.flush();
				 S_LOGGER.debug("Existing job moved to new type of project!!");
			 }
			 return true;
		} catch (Exception e) {
			S_LOGGER.debug("It is already adapted !!!!! ");
		}
		return false;
	 }
	 
	 public List<CIJob> getJobs(ApplicationInfo appInfo) throws PhrescoException {
		 S_LOGGER.debug("GetJobs Called!");
		 try {
			 boolean adaptedProject = adaptExistingJobs(appInfo);
			 S_LOGGER.debug("Project adapted for new feature => " + adaptedProject);
			 Gson gson = new Gson();
			 BufferedReader br = new BufferedReader(new FileReader(getCIJobPath(appInfo)));
			 Type type = new TypeToken<List<CIJob>>(){}.getType();
			 List<CIJob> jobs = gson.fromJson(br, type);
			 br.close();
			 return jobs;
		 } catch (FileNotFoundException e) {
			 S_LOGGER.debug("FileNotFoundException");
			 return null;
		 } catch (IOException e) {
			 S_LOGGER.debug("IOException");
			 throw new PhrescoException(e);
		 }
	 }
	 
	 public CIJob getJob(ApplicationInfo appInfo, String jobName) throws PhrescoException {
		 try {
			 S_LOGGER.debug("Search for jobName => " + jobName);
			 if (StringUtils.isEmpty(jobName)) {
				 return null;
			 }
			 List<CIJob> jobs = getJobs(appInfo);
			 if(CollectionUtils.isEmpty(jobs)) {
				 S_LOGGER.debug("job list is empty!!!!!!!!");
				 return null;
			 }
			 S_LOGGER.debug("Job list found!!!!!");
			 for (CIJob job : jobs) {
				 S_LOGGER.debug("job list job Names => " + job.getName());
				 if (job.getName().equals(jobName)) {
					 return job;
				 }
			 }
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
		 return null;
	 }
	 
	 private void writeJsonJobs(ApplicationInfo appInfo, List<CIJob> jobs, String status) throws PhrescoException {
		 try {
			 if (jobs == null) {
				 return;
			 }
			 Gson gson = new Gson();
			 List<CIJob> existingJobs = getJobs(appInfo);
			 if (CI_CREATE_NEW_JOBS.equals(status) || existingJobs == null) {
				 existingJobs = new ArrayList<CIJob>();
			 }
			 existingJobs.addAll(jobs);
			 FileWriter writer = null;
			 File ciJobFile = new File(getCIJobPath(appInfo));
			 String jobJson = gson.toJson(existingJobs);
			 writer = new FileWriter(ciJobFile);
			 writer.write(jobJson);
			 writer.flush();
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }
	 
	 private void deleteJsonJobs(ApplicationInfo appInfo, List<CIJob> selectedJobs) throws PhrescoException {
		 try {
			 if (CollectionUtils.isEmpty(selectedJobs)) {
				 return;
			 }
			 Gson gson = new Gson();
			 List<CIJob> jobs = getJobs(appInfo);
			 if (CollectionUtils.isEmpty(jobs)) {
				 return;
			 }
			//all values
			 Iterator<CIJob> iterator = jobs.iterator();
			//deletable values
			 for (CIJob selectedInfo : selectedJobs) {
				 while (iterator.hasNext()) {
					 CIJob itrCiJob = iterator.next();
					 if (itrCiJob.getName().equals(selectedInfo.getName())) {
						 iterator.remove();
						 break;
					 }
				 }
			 }
			 writeJsonJobs(appInfo, jobs, CI_CREATE_NEW_JOBS);
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }
	 
	 private void updateJsonJob(ApplicationInfo appInfo, CIJob job) throws PhrescoException {
		 try {
			 deleteJsonJobs(appInfo, Arrays.asList(job));
			 writeJsonJobs(appInfo, Arrays.asList(job), CI_APPEND_JOBS);
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }
	 
	 private String getCIJobPath(ApplicationInfo appInfo) {
		 StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		 builder.append(appInfo.getAppDirName());
		 builder.append(File.separator);
		 builder.append(FOLDER_DOT_PHRESCO);
		 builder.append(File.separator);
		 builder.append(CI_JOB_INFO_NAME);
		 return builder.toString();
	 }
	 
	 public boolean isJobTemplateNameExists(String jobTemplateName)  throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.isjobTemplateNameExists()");
		}
		try {
			List<CIJobTemplate> jobTemplates = getJobTemplates();
			if (CollectionUtils.isNotEmpty(jobTemplates)) {
				for (CIJobTemplate ciJobTemplate : jobTemplates) {
					if (ciJobTemplate.getName().equals(jobTemplateName)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of CI.isjobTemplateNameExists()" + e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
		return false;
	 }
	 
	public boolean createJobTemplates(List<CIJobTemplate> ciJobTemplates, boolean createNewFile) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.createJobTemplate()");
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File jobTemplateFile = getJobTemplateFile();
			if (ciJobTemplates == null) {
				return false;
			}

			List<CIJobTemplate> jobTemplates = getJobTemplates();
			if (CollectionUtils.isEmpty(jobTemplates) || createNewFile) {
				jobTemplates = new ArrayList<CIJobTemplate>();
			}
			jobTemplates.addAll(ciJobTemplates);

			Gson gson = new Gson();
			fw = new FileWriter(jobTemplateFile);
			bw = new BufferedWriter(fw);
			String templatesJson = gson.toJson(jobTemplates);
			bw.write(templatesJson);
			bw.flush();
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

	public List<CIJobTemplate> getJobTemplates() throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getJobTemplates()");
		}
		FileReader jobTemplateFileReader = null;
		BufferedReader br = null;
		List<CIJobTemplate> ciJobTemplates = null;
		try {
			File jobTemplateFile = getJobTemplateFile();
			if (!jobTemplateFile.exists()) {
				// If file not found return empty object
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

	public List<CIJobTemplate> getJobTemplatesByAppId(String appId) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getJobTemplatesByAppid()");
		}
		List<CIJobTemplate> ciJobTemplates = null;
		try {
			if (StringUtils.isEmpty(appId)) {
				return ciJobTemplates;
			}

			List<CIJobTemplate> jobTemplates = getJobTemplates();
			if (CollectionUtils.isEmpty(jobTemplates)) {
				return ciJobTemplates;
			}

			ciJobTemplates = new ArrayList<CIJobTemplate>(jobTemplates.size());

			for (CIJobTemplate ciJobTemplate : jobTemplates) {
				List<String> appIds = ciJobTemplate.getAppIds();
				for (String selAppId : appIds) {
					if (appId.equals(selAppId)) {
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

	public List<CIJobTemplate> getJobTemplatesByProjId(String projId)
			throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getJobTemplatesByProjId()");
		}
		List<CIJobTemplate> ciJobTemplates = null;
		try {
			if (StringUtils.isEmpty(projId)) {
				return ciJobTemplates;
			}

			List<CIJobTemplate> jobTemplates = getJobTemplates();
			if (CollectionUtils.isEmpty(jobTemplates)) {
				return ciJobTemplates;
			}

			ciJobTemplates = new ArrayList<CIJobTemplate>(jobTemplates.size());

			for (CIJobTemplate ciJobTemplate : jobTemplates) {
				String selProjId = ciJobTemplate.getProjectId();
				if (projId.equals(selProjId)) {
					ciJobTemplates.add(ciJobTemplate);
				}
			}
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of CI.getJobTemplatesByProjId()"
						+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
		return ciJobTemplates;
	}

	public CIJobTemplate getJobTemplateByName(String jobTemplateName)
			throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.getJobTemplate()");
		}
		CIJobTemplate ciJobTemplates = null;
		try {
			if (StringUtils.isEmpty(jobTemplateName)) {
				return ciJobTemplates;
			}

			List<CIJobTemplate> jobTemplates = getJobTemplates();
			if (CollectionUtils.isEmpty(jobTemplates)) {
				return ciJobTemplates;
			}

			for (CIJobTemplate ciJobTemplate : jobTemplates) {
				String selJobTemplateName = ciJobTemplate.getName();
				if (jobTemplateName.equals(selJobTemplateName)) {
					return ciJobTemplate;
				}
			}
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of CI.getJobTemplate()"
						+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
		return ciJobTemplates;
	}

	public boolean updateJobTemplate(CIJobTemplate ciJobTemplate, String oldName, String projId) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.updateJobTemplate()");
		}
		try {
			if (StringUtils.isEmpty(oldName)) {
				return false;
			}
			boolean deleteJobTemplate = deleteJobTemplate(oldName, projId);
			if (deleteJobTemplate) {
				boolean createJobTemplates = createJobTemplates(Arrays.asList(ciJobTemplate), false);
				return createJobTemplates;
			}
			return false;
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of CI.updateJobTemplate()"
						+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
	}

	public boolean deleteJobTemplates(List<CIJobTemplate> ciJobTemplates) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.deleteJobTemplates()");
		}
		try {
			if (CollectionUtils.isEmpty(ciJobTemplates)) {
				return false; // Empty collection received
			}
			
			List<CIJobTemplate> jobTemplates = getJobTemplates();
			Iterator<CIJobTemplate> jobTemplatesIterator = jobTemplates.iterator();
			
			for (CIJobTemplate ciJobTemplate : ciJobTemplates) {
				while (jobTemplatesIterator.hasNext()) {
					CIJobTemplate jobTemplate = (CIJobTemplate) jobTemplatesIterator.next();
					if (jobTemplate.getName().equals(ciJobTemplate.getName())) {
						jobTemplatesIterator.remove();
						break;
					}
				 }
			}
			
			boolean deleteJobTemplates = createJobTemplates(jobTemplates, true);
			return deleteJobTemplates;
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of CI.deleteJobTemplates()"
						+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
	}
	
	public boolean deleteJobTemplate(String jobTemplateName, String projId) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method CIManagerImpl.deleteJobTemplates()");
		}
		try {
			if (StringUtils.isEmpty(jobTemplateName)) {
				return false; // Empty collection received
			}
			
			List<CIJobTemplate> jobTemplates = getJobTemplates();
			Iterator<CIJobTemplate> jobTemplateIterator = jobTemplates.iterator();
			while (jobTemplateIterator.hasNext()) {
				CIJobTemplate jobTemplate = (CIJobTemplate) jobTemplateIterator.next();
				if (jobTemplate.getName().equals(jobTemplateName) && jobTemplate.getProjectId().equalsIgnoreCase(projId)) {
					jobTemplateIterator.remove();
					break;
				}
			}
			
			boolean deleteJobTemplates = createJobTemplates(jobTemplates, true);
			return deleteJobTemplates;
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of CI.deleteJobTemplates()"
						+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
	}

	private String getJobTemplatePath() {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		// builder.append(appInfo.getAppDirName());
		// builder.append(File.separator);
		// builder.append(FOLDER_DOT_PHRESCO);
		// builder.append(File.separator);
		builder.append(CI_JOB_TEMPLATE_NAME);
		return builder.toString();
	}
	 
	private File getJobTemplateFile() {
		File jobTemplateFile = null;
		String jobTemplatePath = getJobTemplatePath();
		jobTemplateFile = new File(jobTemplatePath);
		return jobTemplateFile;
	}
	 
	 public CIJobStatus buildJobs(ApplicationInfo appInfo, List<String> jobsName) throws PhrescoException {
		 try {
			 CIJobStatus jobStatus = null;
			 for (String jobName : jobsName) {
				 CIJob ciJob = getJob(appInfo, jobName);
				 jobStatus = buildJob(ciJob);
			 }
			 return jobStatus;
		 } catch (ClientHandlerException ex) {
			 S_LOGGER.error(ex.getLocalizedMessage());
			 throw new PhrescoException(ex);
		 }
	 }
	 
	 public CIJobStatus deleteBuilds(ApplicationInfo appInfo,  Map<String, List<String>> builds) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.deleteCI()");
		 	try {
				CIJobStatus deleteCI = null;
				Iterator iterator = builds.keySet().iterator();  
				while (iterator.hasNext()) {
				   String jobName = iterator.next().toString();  
				   List<String> deleteBuilds = builds.get(jobName);
				   S_LOGGER.debug("jobName " + jobName + " builds " + deleteBuilds);
				   CIJob ciJob = getJob(appInfo, jobName);
					 //job and build numbers
				   deleteCI = deleteCI(ciJob, deleteBuilds);
				}
			 return deleteCI;
		 } catch (ClientHandlerException ex) {
			 S_LOGGER.error("Entered into catch block of ProjectAdministratorImpl.deleteCI()" + ex.getLocalizedMessage());
			 throw new PhrescoException(ex);
		 }
	 }

	 public CIJobStatus deleteJobs(ApplicationInfo appInfo,  List<String> jobNames) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.deleteCI()");
		 try {
			CIJobStatus deleteCI = null;
			for (String jobName : jobNames) {
				S_LOGGER.debug(" Deleteable job name " + jobName);
				CIJob ciJob = getJob(appInfo, jobName);
				 //job and build numbers
				 deleteCI = deleteCI(ciJob, null);
				 S_LOGGER.debug("write back json data after job deletion successfull");
				 deleteJsonJobs(appInfo, Arrays.asList(ciJob));
			}
			 return deleteCI;
		 } catch (ClientHandlerException ex) {
			 S_LOGGER.error("Entered into catch block of ProjectAdministratorImpl.deleteCI()" + ex.getLocalizedMessage());
			 throw new PhrescoException(ex);
		 }
	 }
	 
	// When already existing adapted project is created , need to move to new adapted project
	private boolean deleteCIJobFile(ApplicationInfo appInfo) throws PhrescoException {
		S_LOGGER.debug("Entering Method ProjectAdministratorImpl.deleteCI()");
		try {
       	File ciJobInfo = new File(getCIJobPath(appInfo));
       	return ciJobInfo.delete();
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
		String jenkinsUrl = HTTP_PROTOCOL + PROTOCOL_POSTFIX + job.getJenkinsUrl() + COLON + job.getJenkinsPort() + FORWARD_SLASH + CI + FORWARD_SLASH;
		String isBuildingUrlUrl = BUSY_EXECUTORS;
		String jsonResponse = getJsonResponse(jenkinsUrl + isBuildingUrlUrl);
		int buidInProgress = Integer.parseInt(jsonResponse);
		S_LOGGER.debug("buidInProgress " + buidInProgress);
		return buidInProgress;
	}
	    
	public int getTotalBuilds(ApplicationInfo appInfo) throws PhrescoException {
		try {
			CIJob ciJob = getJob(appInfo);
			return getTotalBuilds(ciJob);
		} catch (ClientHandlerException ex) {
			S_LOGGER.error(ex.getLocalizedMessage());
			throw new PhrescoException(ex);
		}
	}
		 
	private int getTotalBuilds(CIJob job) throws PhrescoException {
		try {
			S_LOGGER.debug("Entering Method CIManagerImpl.getTotalBuilds(CIJob job)");
			S_LOGGER.debug("getCIBuilds()  JobName = " + job.getName());
			JsonArray jsonArray = getBuildsArray(job);
			Gson gson = new Gson();
			CIBuild ciBuild = null;
			if (jsonArray != null && jsonArray.size() > 0) { //not null check
				ciBuild = gson.fromJson(jsonArray.get(0), CIBuild.class);
				String buildUrl = ciBuild.getUrl();
				String jenkinsUrl = job.getJenkinsUrl() + ":" + job.getJenkinsPort();
				// display the jenkins running url in ci
				buildUrl = buildUrl.replaceAll("localhost:" + job.getJenkinsPort(), jenkinsUrl);
																			// list
				String response = getJsonResponse(buildUrl + API_JSON);
				JsonParser parser = new JsonParser();
				JsonElement jsonElement = parser.parse(response);
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				JsonElement resultJson = jsonObject.get(FrameworkConstants.CI_JOB_BUILD_RESULT);
				JsonArray asJsonArray = jsonObject.getAsJsonArray(FrameworkConstants.CI_JOB_BUILD_ARTIFACTS);
				// when build result is not known
				if (jsonObject.get(FrameworkConstants.CI_JOB_BUILD_RESULT).toString().equals(STRING_NULL)) {
					// it indicates the job is in progress and not yet completed
					return -1;
				// when build is success and build zip relative path is unknown 
				} else if (resultJson.getAsString().equals(CI_SUCCESS_FLAG) && asJsonArray.size() < 1) {
					return -1;
				} else {
					return jsonArray.size();
				}
			} else {
				return -1; // When the project is build first time,
			}
		} catch (ClientHandlerException ex) {
			S_LOGGER.error(ex.getLocalizedMessage());
			throw new PhrescoException(ex);
		}
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
}
