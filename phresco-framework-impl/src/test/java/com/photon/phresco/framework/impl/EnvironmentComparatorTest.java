package com.photon.phresco.framework.impl;

import org.junit.Assert;
import org.junit.Test;

import com.photon.phresco.configuration.Environment;

public class EnvironmentComparatorTest {

	EnvironmentComparator environmentComparator = new EnvironmentComparator();
	
	@Test
	public void compareTest() {
		Environment info1 = new Environment();
		info1.setName("Production");
		Environment info2  = new Environment();
		info2.setName("Production");
		int compare = environmentComparator.compare(info1, info2);
		Assert.assertEquals(0, compare);
	}
}
