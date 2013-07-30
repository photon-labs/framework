package com.photon.phresco.framework.param.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.photon.phresco.framework.impl.BuildInfoComparatorTest;
import com.photon.phresco.framework.impl.ConfigurationReaderTest;
import com.photon.phresco.framework.impl.EnvironmentComparatorTest;
import com.photon.phresco.framework.impl.ModuleComparatorTest;
import com.photon.phresco.framework.impl.ProjectManagerTest;
import com.photon.phresco.framework.impl.SettingsInfoComparatorTest;

@RunWith(Suite.class)
@SuiteClasses({ MacosSdkParameterImplTest.class, IosSdkParameterImplTest.class, IosSimSDKVersionsParameterImplTest.class, ConfigurationReaderTest.class, EnvironmentDatabaseImplTest.class, TechnologyVersionImplTest.class, ProjectManagerTest.class, ProjectModuleImplTest.class, WarProjectModuleImplTest.class, IosAndMacosSdkParameterImplTest.class, EnvironmentComparatorTest.class, SettingsInfoComparatorTest.class, ModuleComparatorTest.class,EnvironmentWebservicesImplTest.class, EnvironmentServersImplTest.class, BuildInfoComparatorTest.class})

public class AllTest {
	// intentionally blank. All tests were added via annotations
}
