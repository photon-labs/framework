package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class DotNetConstants {
	private ReadXMLFile readXml; 
	
	public 	 String APPINFO_DOTNET_NAME_VALUE="appInfoDotNetNameValue";
	public   String APPINFO_DOTNET_NONEPROJ_NAME_VALUE="appInfoDotNetNoneNameValue";
	public   String APPINFO_DOTNET_DESCRIPTION_VALUE="appInfoDotNetDescValue"; 
	public   String WEBAPP_DOTNET_CLICK="appInfoTechWebappDotNetClick";
	public   String CREATEDPROJECT_DOTNET = "createdDotNetProject";
	public   String DOTNET_SERVER_CONFIGURATION_NAME_VALUE = "DotNetServNameValue";
	public   String DOTNET_SERVER_CONFIGURATION_DESCRIPTION_VALUE = "DotNetServDescValue";
    public   String DOTNET_SERVERE_CONFIGURATION_TYPE="DotNetServTypeValue";
    public   String DOTNET_SERVER_CONFIGURATION_TYPE_CLICK= "DotNetServTypeClick";
    public   String DOTNET_SERVER_CONFIGURATION_HOST_NAME_VALUE="DotNetServHostValue";
    public   String DOTNET_SERVER_CONFIGURATION_PORT_VALUE="DotNetServPortValue";
    public   String DOTNET_SERVER_CONFIGURATION_CONTEXT_VALUE="DotNetServContextValue";
    public   String DOTNET_GENERATE_BUILD_WEBSERVICE ="DotNetGenBuildWebServ";
    public   String DOTNET_GENERATE_BUILD_WEBSERVICE_CLICK="DotNetGenBuildWebServClick";
    public   String GENERATE_BUILD_SDK="DotNetGenBuildSDK";  
    public   String DOTNET_GENERATE_BUILD_SIMULATOR ="DotNetGenBuildSDKSimul";
    public   String DOTNET_GENERATE_BUILD_SIMULATOR_CLICK="DotNetGenBuildSDKSimulClick";
    public   String DOTNET_GENERATE_BUILD_DEPLOY_SIMULATOR_CLICK="DotNetDeploySimulator";
    public   String DOTNET_SETTINGS_SERVER_DEPLOYDIR_VALUE="DotNetDeployDirectory";
    public   String DOTNET_CONFIGURATIONS_SERVER_SITENAME="DotNetServSitename";
    public   String DOTNET_SERVER_CONFIGURATION_SITENAME_VALUE="DotNetServSitenameValue";
    public   String DOTNET_CONFIGURATIONS_SERVER_APPNAME="DotNetServAppName";
    public   String DOTNET_SERVER_CONFIGURATION_APPNAME_VALUE="DotNetServAppNameValue";

	public DotNetConstants() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadDotNetConstants();
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
