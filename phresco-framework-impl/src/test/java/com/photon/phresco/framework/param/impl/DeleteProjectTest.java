package com.photon.phresco.framework.param.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.framework.model.DeleteProjectInfo;

public class DeleteProjectTest {
	private static ProjectManager projectManager = null;

	@Before
	public void setUp() throws PhrescoException {
		projectManager = PhrescoFrameworkFactory.getProjectManager();
	}
	@Test
	public void deleteProject() throws PhrescoException {
		List<String> appDirNames = new ArrayList<String>();
		appDirNames.add("testPhp");
		DeleteProjectInfo deleteProjectInfo = new DeleteProjectInfo();
		deleteProjectInfo.setAppDirNames(appDirNames);
		projectManager.delete(deleteProjectInfo);
//		Assert.assertTrue(delete);
	}
}
