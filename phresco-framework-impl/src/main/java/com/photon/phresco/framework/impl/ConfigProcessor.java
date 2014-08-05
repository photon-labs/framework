/**
 * Phresco Framework Implementation
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.CIJob;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;
import com.trilead.ssh2.crypto.Base64;

public class ConfigProcessor implements FrameworkConstants {
	private static final Logger S_LOGGER = Logger.getLogger(ConfigProcessor.class);
	private static Boolean DebugEnabled = S_LOGGER.isDebugEnabled();
	private Document document_ = null;
    private Element root_ = null;
    
    public static final String CONFIG_PATH = "http://172.16.18.178:8080/nexus/content/groups/public//config/ci/config/0.2/config-0.2.xml";
    
    public ConfigProcessor(File configFile) throws JDOMException, IOException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.ConfigProcessor(URL url)");
		}
        SAXBuilder builder = new SAXBuilder();
        if (DebugEnabled) {
        	S_LOGGER.debug("ConfigProcessor(() PATH = "+ configFile.getCanonicalPath());
		}
        document_ = builder.build(configFile);
        root_ = document_.getRootElement();
    }
    
    public void changeNodeValue(String nodePath, String nodeValue) throws JDOMException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.changeNodeValue(String nodePath, String nodeValue)");
		}
    	if (DebugEnabled) {
    		S_LOGGER.debug("changeNodeValue() NodePath ="+ nodePath);
		}
    	XPath xpath = XPath.newInstance(nodePath);
        xpath.addNamespace(root_.getNamespace());
        
        Element scmNode = (Element) xpath.selectSingleNode(root_);
        if (DebugEnabled) {
        	S_LOGGER.debug("changeNodeValue() NodeValue ="+ nodeValue);
		}
        scmNode.setText(nodeValue);
    }
    
    private String getSplitTags(String appDirName, String dir) throws FileNotFoundException, PhrescoException, PhrescoPomException {
	
    	File pomFileLocation = Utility.getPomFileLocation(Utility.getProjectHome() + File.separator + appDirName, "");
		PomProcessor pom = new PomProcessor(pomFileLocation);
		
		String splitDir = pom.getProperty(PHRESCO_SPLIT_PHRESCO_DIR);
		String srcDir = pom.getProperty(PHRESCO_SPLIT_SRC_DIR);
		String testDir = pom.getProperty(PHRESCO_SPLIT_TEST_DIR);
		
		if (dir.equals(PHRESCO_FLAG) && StringUtils.isNotEmpty(splitDir)) {
			return splitDir;
		}
		
		if (dir.equals(SRC_FLAG) && StringUtils.isNotEmpty(srcDir)) {
			return srcDir;
		}
		
		if (dir.equals(TEST_FLAG) && StringUtils.isNotEmpty(testDir)) {
			return testDir;
		}
		
    	return appDirName;
    }
    
    
    public void addSvnRepos(CIJob job) throws JDOMException, PhrescoException, FileNotFoundException, PhrescoPomException {

    	S_LOGGER.debug("Entering Method ConfigProcessor.createTriggers()");
    	XPath xpath = XPath.newInstance(SCM_LOCATIONS);
    	xpath.addNamespace(root_.getNamespace());
    	Element locations = (Element) xpath.selectSingleNode(root_);
    	locations.removeContent();

    	String local = ".";
    	if (!POM_XML.equalsIgnoreCase(job.getPomLocation())) {
    		if (StringUtils.isNotEmpty(job.getPhrescoUrl())) {
    			local = getSplitTags(job.getAppDirName(), PHRESCO_FLAG);
    			locations.addContent(createSvnRepoElement(job.getPhrescoUrl(), local)); 
    		}
    	} 

    	local = ".";
    	if (StringUtils.isNotEmpty(job.getTestUrl()) || StringUtils.isNotEmpty(job.getPhrescoUrl())) {
    		local = getSplitTags(job.getAppDirName(), SRC_FLAG);
    	}
    	locations.addContent(createSvnRepoElement(job.getUrl(), local));

    	if (POM_XML.equalsIgnoreCase(job.getPomLocation())) {
    		if (StringUtils.isNotEmpty(job.getPhrescoUrl())) {
    			local = getSplitTags(job.getAppDirName(), PHRESCO_FLAG);
    			locations.addContent(createSvnRepoElement(job.getPhrescoUrl(), local)); 
    		}
    	} 

    	if (StringUtils.isNotEmpty(job.getTestUrl())) {
    		local = getSplitTags(job.getAppDirName(), TEST_FLAG);
    		locations.addContent(createSvnRepoElement(job.getTestUrl(), local)); 
    	}
    }
    
    public void addGitRepos(CIJob job) throws JDOMException, PhrescoException {
    	try {
    		XPath xpath = XPath.newInstance(SCM);
            xpath.addNamespace(root_.getNamespace());
            Element scm = (Element) xpath.selectSingleNode(root_);
            scm.removeAttribute(CI_CLASS);
			scm.setAttribute(CI_CLASS, ORG_JENKINSCI_PLUGINS_MULTIPLESCMS_MULTI_SCM);
            
			scm.removeContent();
            
            org.jdom.Element scms = new Element(SCMS);
			
			String subMod = "";
	    	if (!POM_XML.equalsIgnoreCase(job.getPomLocation())) {
	    		if (StringUtils.isNotEmpty(job.getPhrescoUrl())) {
	    			subMod = getSplitTags(job.getAppDirName(), PHRESCO_FLAG);
	    			scms.addContent(constructScms(job.getPhrescoUrl(), job.getPhrescobranch(), subMod)); 
	    		}
	    	} 

	    	subMod = "";
	    	if (StringUtils.isNotEmpty(job.getTestUrl()) || StringUtils.isNotEmpty(job.getPhrescoUrl())) {
	    		subMod = getSplitTags(job.getAppDirName(), SRC_FLAG);
	    	}
	    	scms.addContent(constructScms(job.getUrl(), job.getBranch(), subMod));

	    	if (POM_XML.equalsIgnoreCase(job.getPomLocation())) {
	    		if (StringUtils.isNotEmpty(job.getPhrescoUrl())) {
	    			subMod = getSplitTags(job.getAppDirName(), PHRESCO_FLAG);
	    			scms.addContent(constructScms(job.getPhrescoUrl(), job.getPhrescobranch(), subMod)); 
	    		}
	    	} 

	    	if (StringUtils.isNotEmpty(job.getTestUrl())) {
	    		subMod = getSplitTags(job.getAppDirName(), TEST_FLAG);
	    		scms.addContent(constructScms(job.getTestUrl(), job.getTestbranch(), subMod)); 
	    	}
	    	
	    	scm.addContent(scms);
    	} catch (Exception e) {
    		if (DebugEnabled) {
        		S_LOGGER.debug("Entering catch block of ConfigProcessor.enableCoberturaReleasePlugin() "+e.getLocalizedMessage());
        	}
    		e.printStackTrace();
			throw new PhrescoException(e);
		}
    }
    
    private Element constructScms(String url, String branch, String subModule) throws PhrescoException {
		
		//1
		org.jdom.Element gitGitSCM = new Element(HUDSON_PLUGINS_GIT_GIT_SCM);
		
		gitGitSCM.addContent(createElement(CONFIG_VERSION, "2"));
		
		org.jdom.Element userRemoteConfigs = new Element(USER_REMOTE_CONFIGS);
		
		org.jdom.Element gitUserRemoteConfig = new Element(HUDSON_PLUGINS_GIT_USER_REMOTE_CONFIG);
		gitUserRemoteConfig.addContent(createElement(URL, url));
		userRemoteConfigs.addContent(gitUserRemoteConfig);
		gitGitSCM.addContent(userRemoteConfigs);
		
		//2
		org.jdom.Element branches = new Element(BRANCHES);
		
		org.jdom.Element gitBranchSpec = new Element(HUDSON_PLUGINS_GIT_BRANCH_SPEC);
		gitBranchSpec.addContent(createElement(NAME, branch));
		branches.addContent(gitBranchSpec);
		gitGitSCM.addContent(branches);
		
		//3
		gitGitSCM.addContent(createElement(DO_GENERATE_SUBMODULE_CONFIGURATIONS, "false"));
		
		//4
		org.jdom.Element submoduleCfg = new Element(SUBMODULE_CFG);
		submoduleCfg.setAttribute(CI_CLASS, "list");
		gitGitSCM.addContent(submoduleCfg);
		
		//5
//		org.jdom.Element extensions = new Element(EXTENSIONS);
		
//		org.jdom.Element RelativeTargetDirectory = new Element(HUDSON_PLUGINS_GIT_EXTENSIONS_IMPL_RELATIVE_TARGET_DIRECTORY);
//		RelativeTargetDirectory.addContent(createElement(RELATIVE_TARGET_DIR, subModule));
//		extensions.addContent(RelativeTargetDirectory);
//		gitGitSCM.addContent(extensions);
		
		// relativeTargetDir - for setting - "Local subdirectory for repo"
		gitGitSCM.addContent(createElement(RELATIVE_TARGET_DIR, subModule));
		
		return gitGitSCM;
    }
    
    private org.jdom.Element createSvnRepoElement(String Url, String local) {
    	org.jdom.Element element = new Element(HUDSON_SCM_SUBVERSION_SCM_MODULE_LOCATION);
		element.addContent(createElement(REMOTE, Url));
		element.addContent(createElement(LOCAL2, local));
		return element;
    }
    
    public void createTriggers(String nodePath, List<String> triggers, String cronExpression) throws JDOMException {
    	S_LOGGER.debug("Entering Method ConfigProcessor.createTriggers()");
    	XPath xpath = XPath.newInstance(nodePath);
        xpath.addNamespace(root_.getNamespace());
        Element triggerNode = (Element) xpath.selectSingleNode(root_);
        triggerNode.removeContent();
        if (triggers != null) {
        	for (String trigger : triggers) {
    			if(TIMER_TRIGGER.equals(trigger)) {
    				triggerNode.addContent(createElement(HUDSON_TRIGGER_TIMER, null).addContent(createElement(SPEC, cronExpression)));
    			} else {
    				triggerNode.addContent(createElement(HUDSON_TRIGGER_SCMTRIGGER, null).addContent(createElement(SPEC, cronExpression)));
    			}
    		}
        }
    }
    
    public void enableCollabNetBuildReleasePlugin(CIJob job) throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.enableCollabNetBuildReleasePlugin()");
    	}
    	try {
			org.jdom.Element element = new Element(CI_FILE_RELEASE_NODE);
			element.addContent(createElement(CI_FILE_RELEASE_OVERRIDE_AUTH_NODE, TRUE));
			element.addContent(createElement(CI_FILE_RELEASE_URL, job.getCollabNetURL()));
			element.addContent(createElement(CI_FILE_RELEASE_USERNAME, job.getCollabNetusername()));
			element.addContent(createElement(CI_FILE_RELEASE_PASSWORD, encyPassword(job.getCollabNetpassword())));
			element.addContent(createElement(CI_FILE_RELEASE_PROJECT, job.getCollabNetProject()));
			element.addContent(createElement(CI_FILE_RELEASE_PACKAGE, job.getCollabNetPackage()));
			element.addContent(createElement(CI_FILE_RELEASE_RELEASE, job.getCollabNetRelease()));
			element.addContent(createElement(CI_FILE_RELEASE_OVERWRITE, job.isCollabNetoverWriteFiles()+""));
			element.addContent(createElement(CI_FILE_RELEASE_FILE_PATTERN, null).addContent(createElement(CI_FILE_RELEASE_FILE_PATTERN_NODE, job.getCollabNetFileReleasePattern())));
			
	    	XPath xpath = XPath.newInstance(CI_FILE_RELEASE_PUBLISHER_NODE);
	        xpath.addNamespace(root_.getNamespace());
	        Element publisherNode = (Element) xpath.selectSingleNode(root_);
	        publisherNode.getContent().add(3, element);
    	} catch (Exception e) {
    		if (DebugEnabled) {
        		S_LOGGER.debug("Entering catch block of ConfigProcessor.enableCollabNetBuildReleasePlugin() "+e.getLocalizedMessage());
        	}
			throw new PhrescoException(e);
		}
    }
    
    public void enableCoberturaReleasePlugin(CIJob job) throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.enableCoberturaReleasePlugin()");
    	}
    	try {
			org.jdom.Element element = new Element(HUDSON_COBERTURA_PUBLISHER);
		
			element.addContent(createElement(COBERTURA_REPORT_FILE, COVERAGE_XML));
			
			element.addContent(createElement(ONLY_STABLE, FALSE));
			element.addContent(createElement(FAIL_UNHEALTHY, FALSE));
			element.addContent(createElement(FAIL_UNSTABLE, FALSE));
			element.addContent(createElement(AUTO_UPDATE_HEALTH, FALSE));
			element.addContent(createElement(AUTO_UPDATE_STABILITY, FALSE));
			element.addContent(createElement(ZOOM_COVERAGE_CHART, FALSE));
			element.addContent(createElement(FAIL_NO_REPORTS, TRUE));
			
			//healthyTarget
			org.jdom.Element healthyTarget = new Element(HEALTHY_TARGET);
			
			org.jdom.Element target_Healthy = new Element(TARGETS);
			
			target_Healthy.setAttribute(CI_CLASS, ENUM_MAP);
			target_Healthy.setAttribute(ENUM_TYPE, HUDSON_COBERTURA_TARGETS_COVERAGEMETRIC);
			org.jdom.Element entry_first_healthy = new Element(ENTRY_TAG);
			entry_first_healthy.addContent(createElement(HUDSON_COBERTURA_TARGETS_COVERAGEMETRIC, CONDITIONAL));
			entry_first_healthy.addContent(createElement(INT, "7000000"));
			org.jdom.Element entry_second_healthy = new Element(ENTRY_TAG);
			entry_second_healthy.addContent(createElement(HUDSON_COBERTURA_TARGETS_COVERAGEMETRIC, LINE));
			entry_second_healthy.addContent(createElement(INT, "8000000"));
			org.jdom.Element entry_third_healthy = new Element(ENTRY_TAG);
			entry_third_healthy.addContent(createElement(HUDSON_COBERTURA_TARGETS_COVERAGEMETRIC, METHOD));
			entry_third_healthy.addContent(createElement(INT, "8000000"));
			target_Healthy.addContent(entry_first_healthy);
			target_Healthy.addContent(entry_second_healthy);
			target_Healthy.addContent(entry_third_healthy);
			healthyTarget.addContent(target_Healthy);
			element.addContent(healthyTarget);
			
			//unhealthyTarget
			org.jdom.Element unhealthyTarget = new Element(UNHEALTHY_TARGET);
			
			org.jdom.Element target_Unhealthy = new Element(TARGETS);
			
			target_Unhealthy.setAttribute(CI_CLASS, ENUM_MAP);
			target_Unhealthy.setAttribute(ENUM_TYPE, HUDSON_COBERTURA_TARGETS_COVERAGEMETRIC);
			org.jdom.Element entry_first_unhealthy = new Element(ENTRY_TAG);
			entry_first_unhealthy.addContent(createElement(HUDSON_COBERTURA_TARGETS_COVERAGEMETRIC, CONDITIONAL));
			entry_first_unhealthy.addContent(createElement(INT, "0"));
			org.jdom.Element entry_second_unhealthy = new Element(ENTRY_TAG);
			entry_second_unhealthy.addContent(createElement(HUDSON_COBERTURA_TARGETS_COVERAGEMETRIC, LINE));
			entry_second_unhealthy.addContent(createElement(INT, "0"));
			org.jdom.Element entry_third_unhealthy = new Element(ENTRY_TAG);
			entry_third_unhealthy.addContent(createElement(HUDSON_COBERTURA_TARGETS_COVERAGEMETRIC, METHOD));
			entry_third_unhealthy.addContent(createElement(INT, "0"));
			target_Unhealthy.addContent(entry_first_unhealthy);
			target_Unhealthy.addContent(entry_second_unhealthy);
			target_Unhealthy.addContent(entry_third_unhealthy);
			unhealthyTarget.addContent(target_Unhealthy);
			element.addContent(unhealthyTarget);
			
			//failingTarget
			org.jdom.Element failingTarget = new Element(FAILING_TARGET);
			
			org.jdom.Element target_Failing = new Element(TARGETS);
			
			target_Failing.setAttribute(CI_CLASS, ENUM_MAP);
			target_Failing.setAttribute(ENUM_TYPE, HUDSON_COBERTURA_TARGETS_COVERAGEMETRIC);
			org.jdom.Element entry_first_failing = new Element(ENTRY_TAG);
			entry_first_failing.addContent(createElement(HUDSON_COBERTURA_TARGETS_COVERAGEMETRIC, CONDITIONAL));
			entry_first_failing.addContent(createElement(INT, "0"));
			org.jdom.Element entry_second_failing = new Element(ENTRY_TAG);
			entry_second_failing.addContent(createElement(HUDSON_COBERTURA_TARGETS_COVERAGEMETRIC, LINE));
			entry_second_failing.addContent(createElement(INT, "0"));
			org.jdom.Element entry_third_failing = new Element(ENTRY_TAG);
			entry_third_failing.addContent(createElement(HUDSON_COBERTURA_TARGETS_COVERAGEMETRIC, METHOD));
			entry_third_failing.addContent(createElement(INT, "0"));
			target_Failing.addContent(entry_first_failing);
			target_Failing.addContent(entry_second_failing);
			target_Failing.addContent(entry_third_failing);
			failingTarget.addContent(target_Failing);
			element.addContent(failingTarget);
			
			element.addContent(createElement(SOURCE_ENCODING, ASCII));
			
	    	XPath xpath = XPath.newInstance(CI_FILE_RELEASE_PUBLISHER_NODE);
	        xpath.addNamespace(root_.getNamespace());
	        Element publisherNode = (Element) xpath.selectSingleNode(root_);
	        publisherNode.getContent().add(element);
    	} catch (Exception e) {
    		if (DebugEnabled) {
        		S_LOGGER.debug("Entering catch block of ConfigProcessor.enableCoberturaReleasePlugin() "+e.getLocalizedMessage());
        	}
			throw new PhrescoException(e);
		}
    }
    
    public void enableConfluenceReleasePlugin(CIJob job) throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.enableConfluenceReleasePlugin()");
    	}
    	try {
    		org.jdom.Element element = new Element("com.myyearbook.hudson.plugins.confluence.ConfluencePublisher");
    		element.addContent(createElement(SITE_NAME, job.getConfluenceSite()));
    		element.addContent(createElement(ATTACH_ARCHIVED_ARTIFACTS, String.valueOf(job.isConfluenceArtifacts())));
    		element.addContent(createElement(BUILD_IF_UNSTABLE, String.valueOf(job.isConfluencePublish())));
    		element.addContent(createElement(FILE_SET, job.getConfluenceOther()));
    		element.addContent(createElement(SPACE_NAME, job.getConfluenceSpace()));
    		element.addContent(createElement(PAGE_NAME, job.getConfluencePage()));
    		element.addContent(createElement(EDITORS, job.getConfluenceOther()));
    		XPath  xpath = XPath.newInstance(CI_FILE_RELEASE_PUBLISHER_NODE);
    		xpath.addNamespace(root_.getNamespace());
    		Element publisherNode = (Element) xpath.selectSingleNode(root_);
    		publisherNode.getContent().add(element);
    	} catch (JDOMException e) {
    		if (DebugEnabled) {
        		S_LOGGER.debug("Entering catch block of ConfigProcessor.enableConfluenceReleasePlugin() "+e.getLocalizedMessage());
        	}
    		throw new PhrescoException(e);
    	}
    }
    
    public void enableTestFlightReleasePlugin(CIJob job) throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.enableTestFlightReleasePlugin()");
    	}
    	try {
    		org.jdom.Element element = new Element("testflight.TestflightRecorder");
    		element.addContent(createElement(TESTFLIGHT_TOKEN_NAME, job.getTokenPairName()));
    		element.addContent(createElement(NOTIFYTEAM, FALSE));
    		element.addContent(createElement(BUILD_NOTES, job.getBuildNotes()));
    		element.addContent(createElement(APPEND_CHANGE_LOG, FALSE));
    		element.addContent(createElement(REPLACE, FALSE));
    		element.addContent(createElement(DEBUG, FALSE));
    		XPath  xpath = XPath.newInstance(CI_FILE_RELEASE_PUBLISHER_NODE);
    		xpath.addNamespace(root_.getNamespace());
    		Element publisherNode = (Element) xpath.selectSingleNode(root_);
    		publisherNode.getContent().add(element);
    	} catch (JDOMException e) {
    		if (DebugEnabled) {
        		S_LOGGER.debug("Entering catch block of ConfigProcessor.enableConfluenceReleasePlugin() "+e.getLocalizedMessage());
        	}
    		throw new PhrescoException(e);
    	}
    }
    
    public void useClonedScm(String parentJobName, String criteria) throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.useClonedScm()");
    	}
    	try {
            if(StringUtils.isEmpty(criteria)) {
            	criteria = ANY;
            }
        	XPath xpath = XPath.newInstance(CI_SCM);
            xpath.addNamespace(root_.getNamespace());
            Element scmNode = (Element) xpath.selectSingleNode(root_);
            scmNode.removeContent();
            scmNode.setAttribute(CI_CLASS, CLONE_WORKSPACE_SCM);
            scmNode.addContent(createElement(PARENT_JOB_NAME, parentJobName));
            scmNode.addContent(createElement(CI_CRITERIA, criteria));
		} catch (Exception e) {
			if (DebugEnabled) {
	    		S_LOGGER.debug("Entering catch block of ConfigProcessor.useClonedScm() "+e.getLocalizedMessage());
	    	}
			throw new PhrescoException(e);
		}
    }
    
    public void cloneWorkspace(String clonePattern, String criteria, String archiveMethod) throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.cloneWorkspace()");
    	}
    	try {
        	XPath xpath = XPath.newInstance(PUBLISHERS_NODE);
            xpath.addNamespace(root_.getNamespace());
            Element publisherNode = (Element) xpath.selectSingleNode(root_);
            org.jdom.Element element = new Element("hudson.plugins.cloneworkspace.CloneWorkspacePublisher");
            if(StringUtils.isEmpty(clonePattern)) {
            	clonePattern = ALL_FILES;
            }
            if(StringUtils.isEmpty(criteria)) {
            	criteria = SUCCESSFUL;
            }
            if(StringUtils.isEmpty(archiveMethod)) {
            	archiveMethod = TAR;
            }
            element.addContent(createElement(WORKSPACE_GLOB, clonePattern));
            element.addContent(createElement(CI_CRITERIA, criteria));
            element.addContent(createElement(ARCHIVE_METHOD, archiveMethod));
            publisherNode.addContent(element);
		} catch (Exception e) {
			if (DebugEnabled) {
	    		S_LOGGER.debug("Entering catch block of ConfigProcessor.cloneWorkspace() "+e.getLocalizedMessage());
	    	}
			throw new PhrescoException(e);
		}
    }
    
    public void buildOtherProjects(String childProjects, String name, String ordinal, String color)  throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.buildOtherProjects()");
    	}
    	try {
        	XPath xpath = XPath.newInstance(PUBLISHERS_NODE);
            xpath.addNamespace(root_.getNamespace());
            Element publisherNode = (Element) xpath.selectSingleNode(root_);
            org.jdom.Element element = new Element(HUDSON_TASKS_BUILD_TRIGGER_NODE);
            element.addContent(createElement(CHILD_PROJECTS, childProjects));
            element.addContent(createElement(THRESHOLD, null).addContent(createElement(NAME, name)).addContent(createElement(ORDINAL, ordinal)).addContent(createElement(COLOR, color)));
            publisherNode.addContent(element);
		} catch (Exception e) {
			if (DebugEnabled) {
	    		S_LOGGER.debug("Entering catch block of ConfigProcessor.buildOtherProjects() " +e.getLocalizedMessage());
	    	}
			throw new PhrescoException(e);
		}
    }
    
    public void updatePOMLocation(String pomLocation)  throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.updatePOMLocation()");
    	}
    	try {
            root_.addContent(createElement(ROOT_POM, pomLocation));
		} catch (Exception e) {
			if (DebugEnabled) {
	    		S_LOGGER.debug("Entering catch block of ConfigProcessor.updatePOMLocation() "+e.getLocalizedMessage());
	    	}
			throw new PhrescoException(e);
		}
    }
    
    public void enablePostBuildStep(String pomLocation, String postBuildStepCommand)  throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.enablePostBuildStep()");
    	}
    	try {
    		String[] postBuildCommands = postBuildStepCommand.split(SEPARATOR_SEP);
    		XPath xpath = XPath.newInstance(POST_BUILDERS_NODE);
    		xpath.addNamespace(root_.getNamespace());
    		Element postBuildNode = (Element) xpath.selectSingleNode(root_);

    		S_LOGGER.debug("CI adapted value " + postBuildStepCommand);
    		S_LOGGER.debug("CI pomLocation " + pomLocation);
    		org.jdom.Element element = null;
    		if(postBuildCommands[0].equalsIgnoreCase(MAVEN)) {
    			element = new Element(HUDSON_TASKS_MAVEN_NODE);
    			element.addContent(createElement(TARGETS_NODE, postBuildCommands[1]));
    			element.addContent(createElement(MAVEN_NAME_NODE, MAVEN_HOME_ENV));
    			if (!StringUtils.isEmpty(pomLocation)) {
    				element.addContent(createElement(POM_NODE, pomLocation));
    			} 
    			element.addContent(createElement(USE_PRIVATE_REPOSITORY_NODE, FALSE));
    		} else if (postBuildCommands[0].equalsIgnoreCase(SHELL)) {
    			element = new Element(HUDSON_TASKS_SHELL);
    			element.addContent(createElement(COMMAND, postBuildCommands[1]));
    		}
    		if (element != null) {
    			postBuildNode.addContent(element);
    		}
    		S_LOGGER.debug("publisherNode " + postBuildNode);

    	} catch (Exception e) {
    		if (DebugEnabled) {
        		S_LOGGER.debug("Entering catch block of ConfigProcessor.enablePostBuildStep() "+e.getLocalizedMessage());
        	}
    		throw new PhrescoException(e);
    	}
    }
    
    public void enableXcode(CIJob job)  throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.enableXcode()");
    	}
    	try {
    		XPath xpath = XPath.newInstance(POST_BUILDERS_NODE);
    		xpath.addNamespace(root_.getNamespace());
    		Element postBuildNode = (Element) xpath.selectSingleNode(root_);
			String symroot_path = Utility.getJenkinsHome() + WORKSPACE_DIR + File.separator + job.getJobName() + File.separator + DO_NOT_CHECKIN_DIR + File.separator + TARGET;
    		org.jdom.Element element = null;
    			element = new Element(XCODE_BUILDER);
    			
    			if (job.isCleanBeforeBuild()) {
    				element.addContent(createElement(CLEAN_BEFORE_BUILD, TRUE));
    			} else {
    				element.addContent(createElement(CLEAN_BEFORE_BUILD, FALSE));
    			}
    			if (job.isCleanTestReports()) {
    				element.addContent(createElement(CLEAN_TEST_REPORTS, TRUE));
    			} else {
    				element.addContent(createElement(CLEAN_TEST_REPORTS, FALSE));
    			}
    			element.addContent(createElement(CONFIGURATION, job.getXcodeconfiguration()));
    			element.addContent(createElement(TARGET, job.getXcodeTarget()));
    			element.addContent(createElement(SDK, job.getSdk()));
    			element.addContent(createElement(SYMROOT, symroot_path));
    			element.addContent(createElement(XCODE_PROJECT_PATH, job.getXcodeProjectPath()));
    			if(job.isBuildIpa()) {
    				element.addContent(createElement(BUILD_IPA, TRUE));
    			} else {
    				element.addContent(createElement(BUILD_IPA, FALSE));
    			}
    			element.addContent(createElement(CODE_SIGNING_IDENTITY, job.getCodeSigningIdentity()));
    			element.addContent(createElement(CONFIGURATIONBUILDDIR , job.getConfigurationBuildDir()));
    			element.addContent(createElement(XCODEPROJECTFILE , job.getXcodeProjectFile()));
    			element.addContent(createElement(XCODEBUILDARGS , job.getXcodebuildArguments()));
    			element.addContent(createElement(XCODESCHEMA , job.getXcodeSchema()));
    			element.addContent(createElement(XCODEWORSPACEFILE , job.getXcodeWorkspaceFile()));
    			element.addContent(createElement(EMBEDDEDPROFILE , job.getEmbeddedProfileFile()));
    			element.addContent(createElement(CFBUNDLEVERSIONVALUE , job.getCfBundleVersionValue()));
    			element.addContent(createElement(CFBUNDLESHORTVERSION , job.getCfBundleVersionValue()));
    			if (job.isGenerateArchive()) {
    				element.addContent(createElement(GENERATIVE_ARCHIVE, TRUE));
    			} else {
    				element.addContent(createElement(GENERATIVE_ARCHIVE, FALSE));
    			}
    			if (job.isUnlockKeychain()) {
    				element.addContent(createElement(UNLOCK_KEYCHAIN, TRUE));
    			} else {
    				element.addContent(createElement(UNLOCK_KEYCHAIN, FALSE));
    			}
    			element.addContent(createElement(KEYCHAINNAME , job.getKeychainName()));
    			element.addContent(createElement(KEYCHAINPATH , job.getKeychainPath()));
    			
    			element.addContent(createElement(KEYCHAINPWD , job.getKeychainPwd()));
    			if (job.isAllowFailingBuildResults()) {
    				element.addContent(createElement(ALLOWFAILINGBUILDRESULTS, TRUE));
    			} else {
    				element.addContent(createElement(ALLOWFAILINGBUILDRESULTS , FALSE));
    			}
    			element.addContent(createElement(IPANAME , job.getIpaName()));
    			element.addContent(createElement(IPAOUTPUTDIR , job.getIpaOutputDirectory()));
    			if (job.isProvideApplicationVersion()) {
    				element.addContent(createElement(PROVIDEAPPLVERSION, TRUE));
    			} else {
    				element.addContent(createElement(PROVIDEAPPLVERSION , FALSE));
    			}
    			
    		if (element != null) {
    			postBuildNode.addContent(element);
    		}
    		
    		XPath buildWrapers = XPath.newInstance(BUILD_WRAPPERS);
    		buildWrapers.addNamespace(root_.getNamespace());
    		Element buildWraper = (Element) buildWrapers.selectSingleNode(root_);

    		org.jdom.Element buildElement = null;
    			buildElement = new Element(XCODE_KEYCHAIN_BUILDWRAPPER);
    		if (buildElement != null) {
    			buildWraper.addContent(buildElement);
    		}
    		
    		S_LOGGER.debug("publisherNode " + postBuildNode);

    	} catch (Exception e) {
    		if (DebugEnabled) {
        		S_LOGGER.debug("Entering catch block of ConfigProcessor.enableXcode() "+e.getLocalizedMessage());
        	}
    		throw new PhrescoException(e);
    	}
    }
    
    public void enablePreBuildStep(String pomLocation, String mvnCommand) throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.enablePreBuildStep");
    	}
    	try {
	    	XPath xpath = XPath.newInstance(PRE_BUILDERS_NODE);
	        xpath.addNamespace(root_.getNamespace());
	        Element postBuildNode = (Element) xpath.selectSingleNode(root_);
	        
    		org.jdom.Element element = new Element(HUDSON_TASKS_MAVEN_NODE);
			element.addContent(createElement(TARGETS_NODE, mvnCommand));
			element.addContent(createElement(MAVEN_NAME_NODE, MAVEN_HOME_ENV));
			if (!StringUtils.isEmpty(pomLocation)) {
				element.addContent(createElement(POM_NODE, pomLocation));
			}
			element.addContent(createElement(USE_PRIVATE_REPOSITORY_NODE, FALSE));
			S_LOGGER.debug("publisherNode " + postBuildNode);
	        postBuildNode.addContent(element);
		} catch (Exception e) {
			if (DebugEnabled) {
	    		S_LOGGER.debug("Entering catch block of ConfigProcessor.enablePreBuildStep "+e.getLocalizedMessage());
	    	}
			throw new PhrescoException(e);
		}
    }
    
    public void setArtifactArchiver(boolean enableArtifactArchiver, String artifactArchiverLocation) throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.enableArtifactArchiver");
    	}
    	try {
    		if (enableArtifactArchiver) {
    			changeNodeValue(ARTIFACT_ARCHIVER_VALUE_NODE, artifactArchiverLocation);
    			// empty the artifact archiver when it is not enabled
    		} else {
    			deleteElement(CI_FILE_RELEASE_PUBLISHER_NODE, HUDSON_TASKS_ARTIFACT_ARCHIVER_NODE);
    		}
    	} catch (Exception e) {
    		if (DebugEnabled) {
        		S_LOGGER.debug("Entering catch block of ConfigProcessor.enableArtifactArchiver "+e.getLocalizedMessage());
        	}
			throw new PhrescoException(e);
		}
    }
    
    public void setEmailPublisher(Map<String, String> emails, String attachmentsPattern) throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.enableEmailPublisher");
    	}
    	try {
    		String failureEmails = (String)emails.get(FAILURE_EMAILS);
    		String successEmails = (String)emails.get(SUCCESS_EMAILS);
    		if (StringUtils.isEmpty(failureEmails) && StringUtils.isEmpty(successEmails)) {
    			deleteElement(CI_FILE_RELEASE_PUBLISHER_NODE, EXTENDED_EMAIL_PUBLISHER_NODE);
    		} else {
    			//Failure Reception list
    	        changeNodeValue(TRIGGER_FAILURE_EMAIL_RECIPIENT_LIST, failureEmails);
    	        //Success Reception list
    	        changeNodeValue(TRIGGER_SUCCESS__EMAIL_RECIPIENT_LIST, successEmails);
    	        
    	        // when atleast on mailids is available and attachmentsPattern is specified
    	        if (StringUtils.isNotEmpty(attachmentsPattern)) {
    	        	changeNodeValue(ATTACHEMENT_PATTERN, attachmentsPattern);
    	        }
    		}
    	} catch (Exception e) {
    		if (DebugEnabled) {
        		S_LOGGER.debug("Entering catch block of ConfigProcessor.enableEmailPublisher "+e.getLocalizedMessage());
        	}
			throw new PhrescoException(e);
		}
    }
    
    public static Element createElement(String nodeName, String NodeValue) {
    	org.jdom.Element element = new Element(nodeName);
    	if (NodeValue != null) {
    		element.addContent(NodeValue);
    	}
    	return element;
    }
    
    public InputStream getConfigAsStream() throws IOException {
        XMLOutputter xmlOutput = new XMLOutputter();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        xmlOutput.output(document_, outputStream);
        
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    
	public String encyPassword(String password) throws PhrescoException {
		if (DebugEnabled) {
    		S_LOGGER.debug("Entering method ConfigProcessor.encyPassword ");
    	}
		String encString = "";
		try {
			Cipher cipher = Cipher.getInstance(AES_ALGO);
			cipher.init(Cipher.ENCRYPT_MODE, getAes128Key(CI_SECRET_KEY));
			encString = new String(Base64.encode(cipher.doFinal((password+CI_ENCRYPT_MAGIC).getBytes(CI_UTF8))));
		} catch (Exception e) {
			if (DebugEnabled) {
	    		S_LOGGER.debug("Entering catch block of ConfigProcessor.encyPassword "+e.getLocalizedMessage());
	    	}
			throw new PhrescoException(e);
		}
		return encString;
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
	
    public void deleteElement(String xpathRootNodePath, String xpathDeleteNode) throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.deleteElement()");
    	}
    	try {
        	XPath xpath = XPath.newInstance(xpathRootNodePath);
            xpath.addNamespace(root_.getNamespace());
            Element xpathRootNode = (Element) xpath.selectSingleNode(root_); // if it is null then element is not present
            if (StringUtils.isNotEmpty(xpathDeleteNode)) {
            	xpathRootNode.removeChild(xpathDeleteNode);
            } else {
            	xpathRootNode.removeContent();
            }
		} catch (Exception e) {
			if (DebugEnabled) {
	    		S_LOGGER.debug("Entering catch block of ConfigProcessor.deleteElement() "+e.getLocalizedMessage());
	    	}
			throw new PhrescoException(e);
		}
    }
    
    public void saveStreamAsFile(File destFile) throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering method ConfigProcessor.saveStreamAsFile");
    	}
    	try {
            InputStream configAsStream = getConfigAsStream();
            CIManagerImpl cmi = new CIManagerImpl();
            cmi.streamToFile(destFile, configAsStream) ;
		} catch (Exception e) {
			if (DebugEnabled) {
	    		S_LOGGER.debug("Entering catch block of ConfigProcessor.saveStreamAsFile "+e.getLocalizedMessage());
	    	}
			throw new PhrescoException(e);
		}
    }
    
//    public static void main(String[] args) {
//        try {
//            File configFile = new File("/Users/kaleeswaran/Tried/POC/JenkinsConfigXml/template/svn-config.xml");
//			ConfigProcessor processor = new ConfigProcessor(configFile);
//			// 1) enable artifactArchiver with this value(do_not_checkin)
//			// 2) enable email publisher When success and failure emails are empty empty the tag
//			// 3) enable Attachement pattern (this method should be inside enable email publisher) for report mailing
//			
//			// Delete element check
////            processor.deleteElement("publishers//hudson.tasks.ArtifactArchiver", "");
////			processor.changeNodeValue("publishers//hudson.tasks.ArtifactArchiver//artifacts", "do_not_checkin/archives/cumulativeReports/*.pdf");
//			
//            // Attachement pattern value insert and empty (When the value is not empty, we have to insert the text in that node)
////            processor.changeNodeValue("publishers//hudson.plugins.emailext.ExtendedEmailPublisher//attachmentsPattern", "do_not_checkin/archives/cumulativeReports/*.pdf");
//			
//			// Delete email tags
////			processor.deleteElement("publishers//hudson.plugins.emailext.ExtendedEmailPublisher", "");
//			processor.deleteElement("publishers", "hudson.plugins.emailext.ExtendedEmailPublisher");
//            processor.saveStreamAsFile(configFile);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}
