package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class WordPressConstants {
	private ReadXMLFile readXml; 
	
	public 	 String APPINFO_WORDPRESS_NAME_VALUE="appInfoWordPressNameValue";
	public   String APPINFO_WORDPRESS_NONEPROJ_NAME_VALUE="appInfoWordPressNoneNameValue";
	public   String APPINFO_WORDPRESS_DESCRIPTION_VALUE="appInfoWordPressDescValue"; 
	public   String WEBAPP_WORDPRESS_CLICK="appInfoTechWebappWordPressClick";
	public   String CREATEDPROJECT_WORDPRESS = "createdWordPressProject";
	public   String WORDPRESS_SERVER_CONFIGURATION_NAME_VALUE = "WordPressServNameValue";
	public   String WORDPRESS_SERVER_CONFIGURATION_DESCRIPTION_VALUE = "WordPressServDescValue";
    public   String WORDPRESS_SERVER_CONFIGURATION_TYPE="WordPressServTypeValue";
    public   String WORDPRESS_SERVER_CONFIGURATION_TYPE_CLICK= "WordPressServTypeClick";
    public   String WORDPRESS_SERVER_CONFIGURATION_HOST_NAME_VALUE="WordPressServHostValue";
    public   String WORDPRESS_SERVER_CONFIGURATION_PORT_VALUE="WordPressServPortValue";
    public   String WORDPRESS_SERVER_CONFIGURATION_CONTEXT_VALUE="WordPressServContextValue";
    public   String WORDPRESS_GENERATE_BUILD_WEBSERVICE ="WordPressGenBuildWebServ";
    public   String WORDPRESS_GENERATE_BUILD_WEBSERVICE_CLICK="WordPressGenBuildWebServClick";
    public   String GENERATE_BUILD_SDK="WordPressGenBuildSDK";  
    public   String WORDPRESS_GENERATE_BUILD_SIMULATOR ="WordPressGenBuildSDKSimul";
    public   String WORDPRESS_GENERATE_BUILD_SIMULATOR_CLICK="WordPressGenBuildSDKSimulClick";
    public   String WORDPRESS_GENERATE_BUILD_DEPLOY_SIMULATOR_CLICK="WordPressDeploySimulator";
    public String CONFIGURATIONS_WORDPRESS_SERVER_DEPLOYDIR_VALUE = "WordpressServerDeployDirValue";
    
    public String CONFIGURATIONS_WORDPRESS_DB_NAME_VALUE = "wordpressDbNameValue";
	public String CONFIGURATIONS_WORDPRESS_DB_DESCRIPTION_VALUE = "wordpressDbDescValue";
	public String CONFIGURATIONS_WORDPRESS_DB_HOST_VALUE = "wordpressDbHostValue";
	public String CONFIGURATIONS_WORDPRESS_DB_PORT_VALUE = "wordpressDbPortValue";
	public String CONFIGURATIONS_WORDPRESS_DB_USERNAME_VALUE = "wordpressDbUserNameValue";
//	public String CONFIGURATIONS_DRUPAL6_DB_PASSWORD_VALUE = "drupal6DbPasswordValue";
	public String CONFIGURATIONS_WORDPRESS_DB_DBNAME_VALUE = "wordpressDbdbNameValue";
	public String CONFIGURATIONS_WORDPRESS_DB_CONTEXT_VALUE = "wordpressDbContextValue";

	public WordPressConstants() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadWordPressConstants();
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
