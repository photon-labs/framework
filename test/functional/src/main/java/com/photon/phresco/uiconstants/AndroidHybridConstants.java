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
package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class AndroidHybridConstants {

	private ReadXMLFile readXml;
	
	public 	 String APPINFO_ANDROIDHYBRID_NAME_VALUE="appInfoAndroidHybridNameValue";
	public 	 String APPINFO_ANDROIDHYBRID_NONE_NAME_VALUE="appInfoAndroidHybridNoneNameValue";
	public   String APPINFO_ANDROIDHYBRID_DESCRIPTION_VALUE="appInfoAndroidHybridDescValue"; 
	public   String MOBILEAPP_ANDROIDHYBRID_CLICK="appInfoTechMobappAndroidHybridClick";
	public   String CREATEDPROJECT_ANDROIDHYBRID = "createdAndroidHybridProject";
	public   String ANDROIDHYBRID="appInfoTechMobappAndroidHybrid";
	public   String CREATEDPROJECT_ANDROIDHYBRID_NONE= "createdAndroidHybridNoneProject";
	//public   String MOBILEAPP_SHOPPINGCART="appInfoTechMobappAndroidHybridShoppinCart";
	
	/*public   String ANDROIDHYBRID_WEBSERVICE_CONFIGURATION_NAME_VALUE = "AndroidHybridWebServNameValue";
	public   String ANDROIDHYBRID_WEBSERVICE_CONFIGURATION_DESCRIPTION_VALUE = "AndroidHybridWebServDescValue";
    public   String ANDROIDHYBRID_WEBSERVICE_CONFIGURATION_TYPE="AndroidHybridWebServTypeValue";
    public   String ANDROIDHYBRID_WEBSERVICE_CONFIGURATION_TYPE_CLICK= "AndroidHybridWebServTypeClick";
    public   String ANDROIDHYBRID_WEBSERVICE_CONFIGURATION_HOST_NAME_VALUE="AndroidHybridWebServHostValue";
    public   String ANDROIDHYBRID_WEBSERVICE_CONFIGURATION_PORT_VALUE="AndroidHybridWebServPortValue";
    public   String ANDROIDHYBRID_WEBSERVICE_CONFIGURATION_CONTEXT_VALUE="AndroidHybridWebServContextValue";
    public   String ANDROIDHYBRID_GENERATE_BUILD_WEBSERVICE ="AndroidHybridGenBuildWebServ";
    public   String ANDROIDHYBRID_GENERATE_BUILD_WEBSERVICE_CLICK="AndroidHybridGenBuildWebServClick";
    public   String GENERATE_BUILD_SDK="AndroidHybridGenBuildSDK";  
    public   String ANDROIDHYBRID_GENERATE_BUILD_SIMULATOR ="AndroidHybridGenBuildSDKSimul";
    public   String ANDROIDHYBRID_GENERATE_BUILD_SIMULATOR_CLICK="AndroidHybridGenBuildSDKSimulClick";
    public   String ANDROIDHYBRID_GENERATE_BUILD_DEPLOY_SIMULATOR_CLICK="AndroidHybridDeploySimulator";*/
	

	public AndroidHybridConstants() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadAndroidHybridConstants();
			Field[] arrayOfField1 = super.getClass().getFields();
			Field[] arrayOfField2 = arrayOfField1;
			int i = arrayOfField2.length;
			for (int j = 0; j < i; ++j) {
				Field localField = arrayOfField2[j];
				Object localObject = localField.get(this);
				if (localObject instanceof String)
					localField
							.set(this, readXml.getValue((String) localObject));

			}
		} catch (Exception localException) {
			throw new RuntimeException("Loading "
					+ super.getClass().getSimpleName() + " failed",
					localException);
		}
	}
}
