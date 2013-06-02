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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.framework.model.CIJob;
import com.photon.phresco.framework.model.CIJobTemplate;
import com.photon.phresco.service.client.api.ServiceManager;

public class CIManagerImplTest implements FrameworkConstants{

	private ServiceManager serviceManager = null;
	private CIManagerImpl ciManager = null;
	final String resourceName = "gitHubScm.xml";
	private Document document_ = null;
    private Element root_ = null;
    private String SvnType = "git"; //clonedWorkspace
    
    // Job template variable
    
	@Before
	public void setUp() throws Exception {
		try {
//			serviceManager = PhrescoFrameworkFactory.getServiceManager();
			ciManager = new CIManagerImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@After
	public void tearDown() throws Exception {
//	    if (serviceManager != null) {
//	        serviceManager = null;
//	    }
	    if (ciManager != null) {
	    	ciManager = null;
	    }
	}
	
	@Test
	public void testAddJobTemplates() throws Exception {
		System.out.println("Add job template ");
		CIJobTemplate ciJobTemplate = new CIJobTemplate();
		ciJobTemplate.setName("Test");
		ciJobTemplate.setType("build");
		
		List<String> appIds = new ArrayList<String>(2);
		appIds.add("a123");
		appIds.add("a456");
		ciJobTemplate.setAppIds(appIds);
		
		ciJobTemplate.setProjId("p123");
		
		ciJobTemplate.setEnableRepo(true);
		List<String> repoTypes = new ArrayList<String>(2);
		repoTypes.add("svn");
		ciJobTemplate.setRepoTypes(repoTypes);
		
		ciJobTemplate.setEnableSheduler(true);
		ciJobTemplate.setEnableEmailSettings(true);
		ciJobTemplate.setEnableUploadSettings(true);
		
		List<String> uploadTypes = new ArrayList<String>(2);
		uploadTypes.add("confluence");
		uploadTypes.add("insight");
		ciJobTemplate.setUploadTypes(uploadTypes);
		
		List<CIJobTemplate> ciJobTemplats = Arrays.asList(ciJobTemplate);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(ciJobTemplats);
		System.out.println(jsonStr);
		
		boolean createJobTemplates = ciManager.createJobTemplates(ciJobTemplats, true); // True should passed only for testing purpose
		Assert.assertTrue(createJobTemplates);
	}
	
	@Test
	public void testListAllJobTemplates() throws Exception {
		System.out.println("ListAll job template ");
		List<CIJobTemplate> jobTemplates = ciManager.getJobTemplates();
		if (CollectionUtils.isEmpty(jobTemplates)) {
			Assert.assertTrue(false);
		}
		int size = jobTemplates.size();
		System.out.println("Size => " + size);
		Assert.assertTrue(size > 0 && size < 2);
	}
	
	@Test
	public void testpListByNameJobTemplates() throws Exception {
		System.out.println("ListByName job template ");
		String name = "Test";
		CIJobTemplate jobTemplate = ciManager.getJobTemplateByName(name);
		System.out.println("jobTemplate.getName() name => " + jobTemplate.getName());
		Assert.assertTrue(name.equals(jobTemplate.getName()));
	}
	
//	@Test
//	public void testpListByTypeJobTemplates() throws Exception {
//		System.out.println("ListByType job template ");
//		String type = "build";
//	}
	
	@Test
	public void testpListByAppIdJobTemplates() throws Exception {
		System.out.println("ListByAppId job template ");
		String appId = "a123";
		List<CIJobTemplate> jobTemplatesByAppId = ciManager.getJobTemplatesByAppId(appId);
		System.out.println("jobTemplatesByAppId size " + jobTemplatesByAppId.size());
		Assert.assertTrue(jobTemplatesByAppId.size() > 0 && jobTemplatesByAppId.size() < 2);
		
	}
	
	@Test
	public void testpListByProjIdJobTemplates() throws Exception {
		System.out.println("ListByProjId job template ");
		String projId = "p123";
		List<CIJobTemplate> jobTemplatesByProjId = ciManager.getJobTemplatesByProjId(projId);
		Assert.assertTrue(jobTemplatesByProjId.size() > 0 && jobTemplatesByProjId.size() < 2);
	}
	
	
//	@Test
//	public void testpListByCustomerIdJobTemplates() throws Exception {
//		System.out.println("ListByCustomerId job template ");
//		
//	}
	
	
	@Test
	public void testUpdateJobTemplates() throws Exception {
		System.out.println("Update job template ");
		String name = "Test";
		CIJobTemplate jobTemplate = ciManager.getJobTemplateByName(name);
		jobTemplate.setEnableEmailSettings(false);
		boolean updateJobTemplate = ciManager.updateJobTemplate(jobTemplate);
//		Assert.assertTrue(updateJobTemplate);
		
		CIJobTemplate jobTemplateByName = ciManager.getJobTemplateByName(name);
		Assert.assertTrue(!jobTemplateByName.isEnableEmailSettings()); // retrive obje vallue and check
	}
	
	@Test
	public void testDeleteJobTemplates() throws Exception {
		System.out.println("Delete job template ");
		String name = "Test";
		boolean deleteJobTemplate = ciManager.deleteJobTemplate(name);
//		Assert.assertTrue(deleteJobTemplate);
		CIJobTemplate jobTemplateByName = ciManager.getJobTemplateByName(name);
		Assert.assertTrue(jobTemplateByName == null); // retrive obje vallue and check
	}

	//@Test
	public void getConfigPath() throws IOException {
		InputStream inStream = null;
		OutputStream ps = null;
		try {
//            String configPath = serviceManager.getCiConfigPath(SvnType);
			String configPath = "http://172.16.18.178:8080/nexus/content/groups/public/config/ci/git/config/0.3/config-0.3.xml";
            URL configUrl = new URL(configPath);
            // print the file
//            BufferedReader in = new BufferedReader(
//            new InputStreamReader(configUrl.openStream()));

//            String inputLine;
//            while ((inputLine = in.readLine()) != null)
//                System.out.println(inputLine);
//            in.close();
            
            
            System.out.println("configPath =======> " + configPath);
//            ConfigProcessor processor = new ConfigProcessor(new URL(configPath));
            CIJob job = createJob();
            CIJob job1 =  updateWithCollabNetFileRelease(job);
            
            CIJob job2 = updateWithClonnedWorkspace(job1);
            
            CIJob job3 = updateWithCloneTheWorkspace(job2);
            
            CIJob job4 = updateWithBuildOtherProjects(job3);
            // Operation
//            ciManager.customizeNodes(processor, job4);
            //success
//            processor.deleteNodesAtXpath();
//            processor.changeAttributeValue();
//            processor.insertNodesAtXpath();
            
            //Conver to file
            File dest = new File("/Users/kaleeswaran/Desktop/IphoneConfig.xml");
//            InputStream configAsStream = processor.getConfigAsStream();
//            ciManager.streamToFile(dest, configAsStream) ;
            System.out.println("configPath =======> " + configPath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				inStream.close();
			}

			if (ps != null) {
				ps.close();
			}
		}
	}

	
	public void generateXml() {
		String filePath = "http://172.16.18.178:8080/nexus/content/groups/public/config/ci/git/config/0.3/config-0.3.xml";
//		testXmlGeneration(filePath);
	}
	
	//@Test
	public void testXmlGeneration() {
        try {
        	System.out.println("kkkkkkkkkkkkkk");
        	String url = "http://172.16.18.178:8080/nexus/content/groups/public/config/ci/git/config/0.3/config-0.3.xml";
        	SAXBuilder builder = new SAXBuilder();
			document_ = builder.build(url);
			root_ = document_.getRootElement();
			System.out.println("test pomxml updation ............");
//			org.jdom.Element element = new Element("hudson.plugins.collabnet.filerelease.CNFileRelease");
//			element.addContent(createElement("override__auth", "true"));
//			element.addContent(createElement("url", "http://outside.out.com:8080/ce-soap50/services/CollabNet?wsdl"));
//			element.addContent(createElement("username", "kaleeswaran14"));
//			element.addContent(createElement("password", "U3VyZXNoQDEyMw=="));
//			element.addContent(createElement("project", "project"));
//			element.addContent(createElement("rpackage", "PackageRed"));
//			element.addContent(createElement("release", "release"));
//			element.addContent(createElement("overwrite", "false"));
//			element.addContent(createElement("file__patterns", null).addContent(createElement("hudson.plugins.collabnet.documentuploader.FilePattern", "do_not_checkin/build/*.zip")));
//			
//	    	XPath xpath = XPath.newInstance("publishers");
			
			org.jdom.Element element = new Element("rootPOM").addContent("kaleesPom.xml");
//			XPath xpath = XPath.newInstance("maven2-moduleset");
//	        xpath.addNamespace(root_.getNamespace());
//	        Element pullisherNode = (Element) xpath.selectSingleNode(root_);
//	        System.out.println("pullisherNode =====> " + pullisherNode);
	        root_.addContent(element); 
	        
	        
			File dest = new File("/Users/kaleeswaran/Desktop/gitHubConfig.xml");
            InputStream configAsStream = getConfigAsStream();
//            ciManager.streamToFile(dest, configAsStream) ;
            System.out.println("new value added =======> " + url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void getDownloadUrls() {
		try {
			System.out.println("getDownloadUrls!!!!!");
			CIJob createJob = createJob();
//			List<CIBuild> ciBuilds = ciManager.getCIBuilds(createJob);
//			System.out.println(ciBuilds.size());
//			for (CIBuild ciBuild : ciBuilds) {
//				System.out.println("download zip !!!!" + ciBuild.getDownload());
//			}
		} catch (Exception e) {
			e.printStackTrace();
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
    
	private CIJob createJob() {
		CIJob job = new CIJob();
		job.setName("Master2.0Check");
		job.setJenkinsUrl("172.16.29.161");
		job.setJenkinsPort("3579");
//		job.setSvnUrl("KaleesUrl");
//		job.setScheduleExpression("@@@@@@@@@@@@");
//		List<String> triggers = new ArrayList<String>();
//		triggers.add(TIMER_TRIGGER);
//		triggers.add("kalees_triggers");
//		job.setTriggers(triggers);
//		job.setMvnCommand("mvn kalees:kalees");
//		Map<String, String> emails = new HashMap<String, String>(2);
//		emails.put(REQ_KEY_SUCCESS_EMAILS, "muthu success!!!");
//		emails.put(REQ_KEY_FAILURE_EMAILS, "muthu failure!!!");
//		job.setEmail(emails);
//		// need to pass as clonned workspace
//		job.setRepoType(SvnType);
//		job.setBranch("kalees-boston");
		return job;
	}

	private CIJob updateWithCollabNetFileRelease(CIJob job) {
		job.setEnableBuildRelease(true);
		job.setCollabNetURL("http://CollabNet Url");
		job.setCollabNetusername("CollabNet username");
		job.setCollabNetpassword("U3VyZXNoQDEyMw==");
		job.setCollabNetProject("CollabNet project");
		job.setCollabNetPackage("CollabNet package!!!!");
		job.setCollabNetRelease("CollabNet File Release");
		boolean overwriteFiles = false;
		job.setCollabNetoverWriteFiles(overwriteFiles);
		return job;
	}
	
	// use clonned scm
	private CIJob updateWithClonnedWorkspace(CIJob job) {
		job.setUsedClonnedWorkspace("ClonnedJobkalees");
		return job;
	}
	
	private CIJob updateWithCloneTheWorkspace(CIJob job) {
		job.setCloneWorkspace(true);
		return job;
	}
	
	private CIJob updateWithBuildOtherProjects(CIJob job) {
		job.setDownStreamProject("kalees_DownstreamProject");
		return job;
	}
}
