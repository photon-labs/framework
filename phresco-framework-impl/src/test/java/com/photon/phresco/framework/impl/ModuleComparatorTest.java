package com.photon.phresco.framework.impl;

import org.junit.Assert;
import org.junit.Test;

import com.photon.phresco.commons.model.ArtifactGroup;

public class ModuleComparatorTest {

	ModuleComparator moduleComparator = new ModuleComparator();
	
	@Test
	public void compareTest() {
		ArtifactGroup artifactGroup = new ArtifactGroup();
		artifactGroup.setName("Login");
		ArtifactGroup artifactGroup1 = new ArtifactGroup();
		artifactGroup1.setName("Login");
		int compare = moduleComparator.compare(artifactGroup, artifactGroup1);
		Assert.assertEquals(0, compare);
	}
}
