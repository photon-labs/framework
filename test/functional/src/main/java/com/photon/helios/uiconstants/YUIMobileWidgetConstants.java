package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class YUIMobileWidgetConstants {

	private Log log = LogFactory.getLog("YUIMobileWidgetConstants");

	private String YUIMobileArchetypeName = "YUIMobileArchetypeName";
	private String YUIMobileArchetypeDesc = "YUIMobileArchetypeDesc";
	private String YUIMobileArchetypeAppCode = "YUIMobileArchetypeAppCode";
	private String webLayerValue = "webLayerValue";
	private String widgetValue = "widgetValue";
	private String YUIMobileArchetypeEditLink = "YUIMobileArchetypeEditLink";




	public YUIMobileWidgetConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadYUIMobileWidgetConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("YUIMobileWidgetConstants::"+localException.getMessage());
		}
	}

	public String getYUIMobileArchetypeEditLink() {
		return YUIMobileArchetypeEditLink;
	}


	public void setYUIMobileArchetypeEditLink(String yUIMobileArchetypeEditLink) {
		YUIMobileArchetypeEditLink = yUIMobileArchetypeEditLink;
	}

	public String getYUIMobileArchetypeName() {
		return YUIMobileArchetypeName;
	}

	public void setYUIMobileArchetypeName(String yUIMobileArchetypeName) {
		YUIMobileArchetypeName = yUIMobileArchetypeName;
	}

	public String getYUIMobileArchetypeDesc() {
		return YUIMobileArchetypeDesc;
	}

	public void setYUIMobileArchetypeDesc(String yUIMobileArchetypeDesc) {
		YUIMobileArchetypeDesc = yUIMobileArchetypeDesc;
	}

	public String getYUIMobileArchetypeAppCode() {
		return YUIMobileArchetypeAppCode;
	}

	public void setYUIMobileArchetypeAppCode(String yUIMobileArchetypeAppCode) {
		YUIMobileArchetypeAppCode = yUIMobileArchetypeAppCode;
	}

	public String getWebLayerValue() {
		return webLayerValue;
	}

	public void setWebLayerValue(String webLayerValue) {
		this.webLayerValue = webLayerValue;
	}

	public String getWidgetValue() {
		return widgetValue;
	}

	public void setWidgetValue(String widgetValue) {
		this.widgetValue = widgetValue;
	}

}
