package com.photon.phresco.framework.param.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.TechnologyInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.impl.ServiceManagerImpl;

public class IosSdkParameterImplTest {

	private static final String MAC = "mac";
	private static final String OS_NAME = "os.name";
	boolean isMacOS;
	DynamicParameter dynamicParameter = null;
	WarProjectModuleImpl war = null;
	private static ProjectManager projectManager = null;
	public static ServiceManager serviceManager = null;
	
	@Before
	public void setUp() throws Exception {
		String osType = System.getProperty(OS_NAME).toLowerCase();
		isMacOS = osType.indexOf(MAC) >= 0;
		dynamicParameter = new IosSdkParameterImpl();
		war = new WarProjectModuleImpl();
		String serviceURL = "http://172.16.17.117:7070/service-3/rest/api";
		String userName = "phresco";
        String password = "Phresco@123";
		projectManager = PhrescoFrameworkFactory.getProjectManager();
		
		ServiceContext context = new ServiceContext();
		context.put("phresco.service.url", serviceURL);
		context.put("phresco.service.username", userName);
		context.put("phresco.service.password", password);
		if (serviceManager == null) {
			serviceManager = new ServiceManagerImpl(context);
		}
		if (projectManager == null) {
			projectManager = PhrescoFrameworkFactory.getProjectManager();
		}
	}

	@After
	public void tearDown() throws Exception {
		isMacOS = false;
		if (dynamicParameter != null) {
			dynamicParameter = null;
		} 
	}
	
	@Test
	public void testCreateProject() throws PhrescoException {
		ProjectInfo projectInfo = getProjectInfo();
		ProjectInfo create = projectManager.create(projectInfo, serviceManager);
		Assert.assertEquals("testPhp", create.getAppInfos().get(0).getAppDirName());
	}
	

	@Test
	public void testGetValues() throws PhrescoException {
		try {
			Map<String, Object> map = null;
			PossibleValues values = dynamicParameter.getValues(map);
			if (isMacOS) {
				Assert.assertTrue(values.getValue().size() > 0);
			} else {
				Assert.assertTrue(values.getValue().size() == 0);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	private ProjectInfo getProjectInfo() {
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setId("PHR_Test");
		projectInfo.setVersion("1.0.0-SNAPSHOT");
		projectInfo.setProjectCode("testPhp");
		projectInfo.setName("testPhp");
		projectInfo.setIntegrationTest(false);
		projectInfo.setGroupId("com.photon.phresco");
		List<String> customerIds = new ArrayList<String>();
		customerIds.add("photon");
		projectInfo.setCustomerIds(customerIds);
		List<ApplicationInfo> appInfos = new ArrayList<ApplicationInfo>();
		appInfos.add(getAppInfo());
		projectInfo.setAppInfos(appInfos);
		projectInfo.setNoOfApps(appInfos.size());
		projectInfo.setPreBuilt(false);
		projectInfo.setMultiModule(false);
		return projectInfo;
	}

	private ApplicationInfo getAppInfo() {
		ApplicationInfo applicationInfo = new ApplicationInfo();
		applicationInfo.setId("PHR_Test");
		applicationInfo.setCode("testPhp");
		applicationInfo.setName("testPhp");
		List<String> customerIds = new ArrayList<String>();
		customerIds.add("photon");
		applicationInfo.setCustomerIds(customerIds);
		TechnologyInfo techInfo = new TechnologyInfo();
		techInfo.setId("tech-php");
		techInfo.setName("php-raw");
		techInfo.setVersion("5.4.x");
		techInfo.setTechGroupId("PHP");
		techInfo.setAppTypeId("e1af3f5b-7333-487d-98fa-46305b9dd6ee");
		applicationInfo.setTechInfo(techInfo);
		applicationInfo.setAppDirName("testPhp");
		return applicationInfo;
	}

}
