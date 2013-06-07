package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;


public class PilotServiceTest extends RestBaseTest {
	
	public PilotServiceTest() {
		super();
	}
	
	@Test
	public void testPreBuiltProjects() throws PhrescoException {
		List<ProjectInfo>  prebuilts = serviceManager.getPrebuiltProjects("photon");
		Assert.assertNotNull(prebuilts);
		assertEquals(20 , prebuilts.size());
	}
}
