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
import java.util.Collections;
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
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroupInfo;
import com.photon.phresco.commons.model.CIJob;
import com.photon.phresco.commons.model.CIJobTemplate;
import com.photon.phresco.commons.model.TechnologyInfo;
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
	
//	@Test
	public void testAddJobTemplates() throws Exception {
		System.out.println("Add job template ");
		CIJobTemplate ciJobTemplate = new CIJobTemplate();
		ciJobTemplate.setName("Test");
		ciJobTemplate.setType("build");
		
		List<String> appIds = new ArrayList<String>(2);
		appIds.add("a123");
		appIds.add("a456");
		ciJobTemplate.setAppIds(appIds);
		
		
		ciJobTemplate.setEnableRepo(true);
		List<String> repoTypes = new ArrayList<String>(2);
		repoTypes.add("svn");
		ciJobTemplate.setRepoTypes("svn");
		
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
		
		boolean createJobTemplates = ciManager.createJobTemplates(ciJobTemplats, true, Arrays.asList(getApplicationInfo())); // True should passed only for testing purpose
		Assert.assertTrue(createJobTemplates);
	}
	
	private static ApplicationInfo getApplicationInfo() {
		ApplicationInfo info = new ApplicationInfo();
		info.setAppDirName("TestProject");
		info.setCode("TestProject");
		info.setId("TestProject");
		info.setCustomerIds(getCollections("photon"));
		info.setEmailSupported(false);
		info.setPhoneEnabled(false);
		info.setTabletEnabled(false);
		info.setDescription("Simple java web service Project");
		info.setHelpText("Help");
		info.setName("TestProject");
		info.setPilot(false);
		info.setUsed(false);
		info.setDisplayName("TestProject");
		info.setSelectedJSLibs(getCollections("99aa3901-a088-4142-8158-000f1e80f1bf"));
		info.setVersion("1.0");

		// TechnologyInfo

		TechnologyInfo techInfo = new TechnologyInfo();
		techInfo.setAppTypeId("web-layer");
		techInfo.setVersion("1.6");
		techInfo.setId("tech-java-webservice");
		techInfo.setSystem(false);
		info.setTechInfo(techInfo);

		// selected Modules

		List<String> selectedModules = new ArrayList<String>();
		selectedModules.add("a69c6875-0bb0-462c-86d5-e361d02157cc");
		info.setSelectedModules(selectedModules);


		// server 

		List<ArtifactGroupInfo> servers =  new ArrayList<ArtifactGroupInfo>();
		ArtifactGroupInfo serverArtifactGroupInfo = new ArtifactGroupInfo();
		serverArtifactGroupInfo.setArtifactGroupId("downloads_apache-tomcat");
		serverArtifactGroupInfo.setDescription("Apache Tomcat");
		serverArtifactGroupInfo.setDisplayName("Tomcat");
		serverArtifactGroupInfo.setId("523c8806-86a8-4e61-937f-f27c8b32aa5c");
		serverArtifactGroupInfo.setName("Eshop");
		serverArtifactGroupInfo.setSystem(false);

		List<String> serverArtifactInfoId = new ArrayList<String>();
		serverArtifactInfoId.add("apachetomcat");
		serverArtifactGroupInfo.setArtifactInfoIds(serverArtifactInfoId);

		servers.add(serverArtifactGroupInfo);
		info.setSelectedServers(servers);

		// database

		List<ArtifactGroupInfo> databases =  new ArrayList<ArtifactGroupInfo>();
		ArtifactGroupInfo databaseArtifactGroupInfos = new ArtifactGroupInfo();
		databaseArtifactGroupInfos.setArtifactGroupId("downloads_mysql");
		databaseArtifactGroupInfos.setDescription("MYSQl");
		databaseArtifactGroupInfos.setDisplayName("MySql");
		databaseArtifactGroupInfos.setId("downloads_mysql");
		databaseArtifactGroupInfos.setName("MySQL");
		databaseArtifactGroupInfos.setSystem(false);
		//		databaseArtifactGroupInfos.setArtifactGroupId("downloads.files");


		List<String> databaseArtifactInfoId = new ArrayList<String>();
		databaseArtifactInfoId.add("26bb9f28-e847-4099-b255-429706ceb7b9");
		databaseArtifactGroupInfos.setArtifactInfoIds(databaseArtifactInfoId);

		databases.add(databaseArtifactGroupInfos);
		info.setSelectedDatabases(databases);

		
		// webService

		List<String> webServices = new ArrayList<String>();
		webServices.add("restjson");
		info.setSelectedWebservices(webServices);
		
		return info;
	}

	private static List<String> getCollections(String value) {
		return Collections.singletonList(value);
	} 
	
//	@Test
	public void testListAllJobTemplates() throws Exception {
		System.out.println("ListAll job template ");
		List<CIJobTemplate> jobTemplates = ciManager.getJobTemplates("TestProject");
		if (CollectionUtils.isEmpty(jobTemplates)) {
			Assert.assertTrue(false);
		}
		int size = jobTemplates.size();
		System.out.println("Size => " + size);
		Assert.assertTrue(size > 0 && size < 2);
	}
	
//	@Test
//	public void testpListByTypeJobTemplates() throws Exception {
//		System.out.println("ListByType job template ");
//		String type = "build";
//	}
	
//	@Test
	public void testpListByAppIdJobTemplates() throws Exception {
		System.out.println("ListByAppId job template ");
		String appId = "a123";
		List<CIJobTemplate> jobTemplatesByAppId = ciManager.getJobTemplatesByAppId(appId, "TestProject");
		System.out.println("jobTemplatesByAppId size " + jobTemplatesByAppId.size());
		Assert.assertTrue(jobTemplatesByAppId.size() > 0 && jobTemplatesByAppId.size() < 2);
		
	}
	
//	@Test
	public void testpListByProjIdJobTemplates() throws Exception {
		System.out.println("ListByProjId job template ");
		String projId = "p123";
		List<CIJobTemplate> jobTemplatesByProjId = ciManager.getJobTemplatesByProjId(projId,Arrays.asList(getApplicationInfo()));
		Assert.assertTrue(jobTemplatesByProjId.size() > 0 && jobTemplatesByProjId.size() < 2);
	}
	
	
//	@Test
//	public void testpListByCustomerIdJobTemplates() throws Exception {
//		System.out.println("ListByCustomerId job template ");
//		
//	}
	
//	
////	@Test
//	public void testUpdateJobTemplates() throws Exception {
//		System.out.println("Update job template ");
//		String name = "Test";
//		String projId = ""; //ur projId
//		String oldName = ""; //ur oldName here
//		CIJobTemplate jobTemplate = ciManager.getJobTemplateByName(name);
//		jobTemplate.setEnableEmailSettings(false);
//		boolean updateJobTemplate = ciManager.updateJobTemplate(jobTemplate, oldName, projId);
////		Assert.assertTrue(updateJobTemplate);
//		
//		CIJobTemplate jobTemplateByName = ciManager.getJobTemplateByName(name);
//		Assert.assertTrue(!jobTemplateByName.isEnableEmailSettings()); // retrive obje vallue and check
//	}
//	
//	@Test
	public void testDeleteJobTemplates() throws Exception {
		System.out.println("Delete job template ");
		String name = "Test";
		String projId = ""; //ur projId
		boolean deleteJobTemplate = ciManager.deleteJobTemplate(name, projId,Arrays.asList(getApplicationInfo()));
//		Assert.assertTrue(deleteJobTemplate);
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
	
//	@Test
	public void objectCompareTest() {
		CIJob job1 = new CIJob();
		CIJob job2 = new CIJob();
		job1.setJobName("name");
		job1.setRepoType("svn");
		job2.setJobName("name");
		job2.setRepoType("svn1");
		boolean equals = job1.equals(job2);	
		System.out.println("equals "+equals);
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
		job.setJobName("Master2.0Check");
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
}
