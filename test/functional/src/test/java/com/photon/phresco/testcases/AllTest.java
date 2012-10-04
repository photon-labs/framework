package com.photon.phresco.testcases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.photon.phresco.preconditions.CleanUP;

@RunWith(Suite.class)
@SuiteClasses({CleanUP.class,Create_NoneProjNone.class,Create_Config_None.class,Build_None.class,Deploy_None.class, Create_ProjectEshop.class,Create_Config_Eshop.class,Build_EShop.class,Deploy_EShop.class})
public class AllTest {

}
