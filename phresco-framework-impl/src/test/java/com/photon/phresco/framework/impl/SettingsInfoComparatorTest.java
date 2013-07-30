package com.photon.phresco.framework.impl;

import org.junit.Assert;
import org.junit.Test;

import com.photon.phresco.framework.model.SettingsInfo;

public class SettingsInfoComparatorTest {

	SettingsInfoComparator settingsInfoComparator =  new SettingsInfoComparator();
	
	@Test
	public void compareTest() {
		SettingsInfo info1 = new SettingsInfo("Production", "test", "SERVER");
		SettingsInfo info2 = new SettingsInfo("Production", "test", "SERVER");
		int compare = settingsInfoComparator.compare(info1 , info2);
		Assert.assertEquals(0, compare);
	}
}
