package com.photon.phresco.framework.impl;

import junit.framework.Assert;

import org.junit.Test;

import com.photon.phresco.commons.model.BuildInfo;

public class BuildInfoComparatorTest {
	BuildInfoComparator buildInfoComparator = new BuildInfoComparator();
	@Test
	public void testCompareWithEqualValue() {
		BuildInfo info1 = new BuildInfo();
		info1.setBuildNo(1);
		BuildInfo info2 = new BuildInfo();
		info2.setBuildNo(1);
		int compare = buildInfoComparator.compare(info1, info2);
		Assert.assertEquals(0, compare);
	}
	
	@Test
	public void testCompareWithUnEqualValue() {
		BuildInfo info1 = new BuildInfo();
		info1.setBuildNo(1);
		BuildInfo info2 = new BuildInfo();
		info2.setBuildNo(5);
		int compare = buildInfoComparator.compare(info1, info2);
		Assert.assertEquals(1, compare);
	}
	
	@Test
	public void testCompareWithEqualNegativeValue() {
		BuildInfo info1 = new BuildInfo();
		info1.setBuildNo(-1);
		BuildInfo info2 = new BuildInfo();
		info2.setBuildNo(-1);
		int compare = buildInfoComparator.compare(info1, info2);
		Assert.assertEquals(0, compare);
	}
	
	@Test
	public void testCompareWithUnequalNegativeValue() {
		BuildInfo info1 = new BuildInfo();
		info1.setBuildNo(-1);
		BuildInfo info2 = new BuildInfo();
		info2.setBuildNo(-2);
		int compare = buildInfoComparator.compare(info1, info2);
		Assert.assertEquals(-1, compare);
	}
}
