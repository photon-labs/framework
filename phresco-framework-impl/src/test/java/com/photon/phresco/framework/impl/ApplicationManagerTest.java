package com.photon.phresco.framework.impl;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.ApplicationManager;

public class ApplicationManagerTest extends BaseTest{

	private static ApplicationManager applicationManager;
	
	@Before
	public void setUp() throws Exception {
		applicationManager = PhrescoFrameworkFactory.getApplicationManager();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void PerformAction() throws PhrescoException {
		ProjectManagerTest managerTest = new ProjectManagerTest();
		ProjectInfo projectInfo = managerTest.getProjectInfo();
		applicationManager.performAction(projectInfo, ActionType.BUILD);
		
	}

}
