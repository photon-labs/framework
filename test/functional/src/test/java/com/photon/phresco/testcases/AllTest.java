/**
 * Phresco Framework Root
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.testcases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.photon.phresco.preconditions.CleanUP;

@RunWith(Suite.class)
@SuiteClasses({CleanUP.class,PHP.class,Drupal6.class,Drupal7.class,sharePoint.class,Multichannel_YUI_Widget.class,Multichannel_Jquery_Widget.class,Jquery_Mobile_Widget.class,YUI_Mobile_Widget.class, ASP_DotNET.class,WordPress.class,JavaStandalone.class,Android_Native.class,Android_Hybrid.class,iPhone_Native.class,iPhone_Hybrid.class,Nodejs_Web_Service.class,Java_Web_Service.class})
public class AllTest {

}
