package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserInfoConstants {

	/*--------------------USER LOGIN INFORMATION-------------------*/
	private Log log = LogFactory.getLog("UserInfoConstants");
	private String loginUserName = "loginUserName";
	private String loginPassword = "loginPassword";
	//private String customerSelect = "customerSelect";

	public UserInfoConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadUserInfoConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in UserInfoConstants::"+localException.getMessage());
		}
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	
}
