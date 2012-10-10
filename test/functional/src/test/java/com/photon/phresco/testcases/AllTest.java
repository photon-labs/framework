package com.photon.phresco.testcases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.photon.phresco.preconditions.CleanUP;

@RunWith(Suite.class)
@SuiteClasses({CleanUP.class,PHP.class,Drupal6.class,Drupal7.class,sharePoint.class,Multichannel_YUI_Widget.class,Multichannel_Jquery_Widget.class,Jquery_Mobile_Widget.class,YUI_Mobile_Widget.class, ASP_DotNET.class,WordPress.class,JavaStandalone.class,Android_Native.class,Android_Hybrid.class,iPhone_Native.class,iPhone_Hybrid.class,Nodejs_Web_Service.class,Java_Web_Service.class})
public class AllTest {

}
