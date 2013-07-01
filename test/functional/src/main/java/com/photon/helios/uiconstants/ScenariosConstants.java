package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScenariosConstants {

	private final Log log = LogFactory.getLog("ScenariosConstants");
	private String androidNativeName = "androidNativeName";
	private String androidNativeDesc = "androidNativeDesc";
	private String androidNativeAppCode = "androidNativeAppCode";
	private String nodeJsValue = "nodeJsValue";
	private String j2EEValue = "j2EEValue";
	private String androidValue = "androidValue";
	private String iPhoneValue = "iPhoneValue";
	private String yuiMobileWidgetValue = "yuiMobileWidgetValue";
	private String multiJqueryWidgetValue = "multiJqueryWidgetValue";
	private String androidNativeMobCode = "androidNativeMobCode";
	private String hybridValue = "hybridValue";
	private String nativeValue = "nativeValue";
	private String html5Value = "html5Value";
	private String iPhoneNativeName = "iPhoneNativeName";
	private String iPhoneNativeDesc = "iPhoneNativeDesc";
	private String iPhoneNativeAppCode = "iPhoneNativeAppCode";
	private String iPhoneNativeMobCode = "iPhoneNativeMobCode";

	private String iPhoneHybridName = "iPhoneHybridName";
	private String iPhoneHybridDesc = "iPhoneHybridDesc";
	private String iPhoneHybridAppCode = "iPhoneHybridAppCode";
	private String iPhoneHybridWebCode = "iPhoneHybridWebCode";
	private String iPhoneHybridMobCode = "iPhoneHybridMobCode";
	private String androidHybridName = "androidHybridName";
	private String androidHybridDesc = "androidHybridDesc";
	private String androidHybridAppCode = "androidHybridAppCode";
	private String androidHybridWebCode = "androidHybridWebCode";
	private String androidHybridMobCode = "androidHybridMobCode";
	
	private String mobileWidgetName = "mobileWidgetName";
	private String mobileWidgetDesc = "mobileWidgetDesc";
	private String mobileWidgetAppcode = "mobileWidgetAppcode";
	private String mobileWidgetWebcode = "mobileWidgetWebcode";
	private String jQueryWidgetName = "jQueryWidgetName";
	private String jQueryWidgetDesc = "jQueryWidgetDesc";
	private String jQueryWidgetAppcode = "jQueryWidgetAppcode";
	private String jQueryWidgetWebcode = "jQueryWidgetWebcode";

	public ScenariosConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadScenariosConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("ScenariosConstants::" + localException.getMessage());
		}
	}

	public String getAndroidNativeName() {
		return androidNativeName;
	}

	public String getAndroidNativeDesc() {
		return androidNativeDesc;
	}

	public String getAndroidNativeAppCode() {
		return androidNativeAppCode;
	}

	public String getNodeJsValue() {
		return nodeJsValue;
	}

	public String getJ2EEValue() {
		return j2EEValue;
	}

	public String getAndroidValue() {
		return androidValue;
	}

	public String getiPhoneValue() {
		return iPhoneValue;
	}

	public String getYuiMobileWidgetValue() {
		return yuiMobileWidgetValue;
	}

	public String getMultiJqueryWidgetValue() {
		return multiJqueryWidgetValue;
	}

	public String getAndroidNativeMobCode() {
		return androidNativeMobCode;
	}

	public String getHybridValue() {
		return hybridValue;
	}

	public String getNativeValue() {
		return nativeValue;
	}

	public String getHtml5Value() {
		return html5Value;
	}

	public String getiPhoneNativeName() {
		return iPhoneNativeName;
	}

	public String getiPhoneNativeDesc() {
		return iPhoneNativeDesc;
	}

	public String getiPhoneNativeAppCode() {
		return iPhoneNativeAppCode;
	}

	public String getiPhoneNativeMobCode() {
		return iPhoneNativeMobCode;
	}

	public String getiPhoneHybridName() {
		return iPhoneHybridName;
	}

	public String getiPhoneHybridDesc() {
		return iPhoneHybridDesc;
	}

	public String getiPhoneHybridAppCode() {
		return iPhoneHybridAppCode;
	}

	public String getiPhoneHybridWebCode() {
		return iPhoneHybridWebCode;
	}

	public String getiPhoneHybridMobCode() {
		return iPhoneHybridMobCode;
	}

	public String getAndroidHybridName() {
		return androidHybridName;
	}

	public String getAndroidHybridDesc() {
		return androidHybridDesc;
	}

	public String getAndroidHybridAppCode() {
		return androidHybridAppCode;
	}

	public String getAndroidHybridWebCode() {
		return androidHybridWebCode;
	}

	public String getAndroidHybridMobCode() {
		return androidHybridMobCode;
	}

	public String getMobileWidgetName() {
		return mobileWidgetName;
	}

	public String getMobileWidgetDesc() {
		return mobileWidgetDesc;
	}

	public String getMobileWidgetAppcode() {
		return mobileWidgetAppcode;
	}

	public String getMobileWidgetWebcode() {
		return mobileWidgetWebcode;
	}

	public String getjQueryWidgetName() {
		return jQueryWidgetName;
	}

	public String getjQueryWidgetDesc() {
		return jQueryWidgetDesc;
	}

	public String getjQueryWidgetAppcode() {
		return jQueryWidgetAppcode;
	}

	public String getjQueryWidgetWebcode() {
		return jQueryWidgetWebcode;
	}

	public void setAndroidNativeName(String androidNativeName) {
		this.androidNativeName = androidNativeName;
	}

	public void setAndroidNativeDesc(String androidNativeDesc) {
		this.androidNativeDesc = androidNativeDesc;
	}

	public void setAndroidNativeAppCode(String androidNativeAppCode) {
		this.androidNativeAppCode = androidNativeAppCode;
	}

	public void setNodeJsValue(String nodeJsValue) {
		this.nodeJsValue = nodeJsValue;
	}

	public void setJ2EEValue(String j2eeValue) {
		j2EEValue = j2eeValue;
	}

	public void setAndroidValue(String androidValue) {
		this.androidValue = androidValue;
	}

	public void setiPhoneValue(String iPhoneValue) {
		this.iPhoneValue = iPhoneValue;
	}

	public void setYuiMobileWidgetValue(String yuiMobileWidgetValue) {
		this.yuiMobileWidgetValue = yuiMobileWidgetValue;
	}

	public void setMultiJqueryWidgetValue(String multiJqueryWidgetValue) {
		this.multiJqueryWidgetValue = multiJqueryWidgetValue;
	}

	public void setAndroidNativeMobCode(String androidNativeMobCode) {
		this.androidNativeMobCode = androidNativeMobCode;
	}

	public void setHybridValue(String hybridValue) {
		this.hybridValue = hybridValue;
	}

	public void setNativeValue(String nativeValue) {
		this.nativeValue = nativeValue;
	}

	public void setHtml5Value(String html5Value) {
		this.html5Value = html5Value;
	}

	public void setiPhoneNativeName(String iPhoneNativeName) {
		this.iPhoneNativeName = iPhoneNativeName;
	}

	public void setiPhoneNativeDesc(String iPhoneNativeDesc) {
		this.iPhoneNativeDesc = iPhoneNativeDesc;
	}

	public void setiPhoneNativeAppCode(String iPhoneNativeAppCode) {
		this.iPhoneNativeAppCode = iPhoneNativeAppCode;
	}

	public void setiPhoneNativeMobCode(String iPhoneNativeMobCode) {
		this.iPhoneNativeMobCode = iPhoneNativeMobCode;
	}

	public void setiPhoneHybridName(String iPhoneHybridName) {
		this.iPhoneHybridName = iPhoneHybridName;
	}

	public void setiPhoneHybridDesc(String iPhoneHybridDesc) {
		this.iPhoneHybridDesc = iPhoneHybridDesc;
	}

	public void setiPhoneHybridAppCode(String iPhoneHybridAppCode) {
		this.iPhoneHybridAppCode = iPhoneHybridAppCode;
	}

	public void setiPhoneHybridWebCode(String iPhoneHybridWebCode) {
		this.iPhoneHybridWebCode = iPhoneHybridWebCode;
	}

	public void setiPhoneHybridMobCode(String iPhoneHybridMobCode) {
		this.iPhoneHybridMobCode = iPhoneHybridMobCode;
	}

	public void setAndroidHybridName(String androidHybridName) {
		this.androidHybridName = androidHybridName;
	}

	public void setAndroidHybridDesc(String androidHybridDesc) {
		this.androidHybridDesc = androidHybridDesc;
	}

	public void setAndroidHybridAppCode(String androidHybridAppCode) {
		this.androidHybridAppCode = androidHybridAppCode;
	}

	public void setAndroidHybridWebCode(String androidHybridWebCode) {
		this.androidHybridWebCode = androidHybridWebCode;
	}

	public void setAndroidHybridMobCode(String androidHybridMobCode) {
		this.androidHybridMobCode = androidHybridMobCode;
	}

	public void setMobileWidgetName(String mobileWidgetName) {
		this.mobileWidgetName = mobileWidgetName;
	}

	public void setMobileWidgetDesc(String mobileWidgetDesc) {
		this.mobileWidgetDesc = mobileWidgetDesc;
	}

	public void setMobileWidgetAppcode(String mobileWidgetAppcode) {
		this.mobileWidgetAppcode = mobileWidgetAppcode;
	}

	public void setMobileWidgetWebcode(String mobileWidgetWebcode) {
		this.mobileWidgetWebcode = mobileWidgetWebcode;
	}

	public void setjQueryWidgetName(String jQueryWidgetName) {
		this.jQueryWidgetName = jQueryWidgetName;
	}

	public void setjQueryWidgetDesc(String jQueryWidgetDesc) {
		this.jQueryWidgetDesc = jQueryWidgetDesc;
	}

	public void setjQueryWidgetAppcode(String jQueryWidgetAppcode) {
		this.jQueryWidgetAppcode = jQueryWidgetAppcode;
	}

	public void setjQueryWidgetWebcode(String jQueryWidgetWebcode) {
		this.jQueryWidgetWebcode = jQueryWidgetWebcode;
	}
	
	

}
