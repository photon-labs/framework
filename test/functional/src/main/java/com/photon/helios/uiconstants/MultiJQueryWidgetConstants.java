package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MultiJQueryWidgetConstants {

	private Log log = LogFactory.getLog("MultiJQueryWidgetConstants");

	private String multiJQueryArchetypeName = "multiJQueryArchetypeName";
    private String multiJQueryArchetypeDesc = "multiJQueryArchetypeDesc";
    private String multiJQueryArchetypeAppCode = "multiJQueryArchetypeAppCode";
    private String webLayerValue = "webLayerValue";
    private String widgetValue = "widgetValue";
    private String multiJQueryArchetypeEditLink = "multiJQueryArchetypeEditLink";
    
	public MultiJQueryWidgetConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadMultiChannelJQueryWidgetConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("MultiJQueryWidgetConstants::"+localException.getMessage());
		}
	}


	public String getMultiJQueryArchetypeName() {
		return multiJQueryArchetypeName;
	}


	public void setMultiJQueryArchetypeName(String multiJQueryArchetypeName) {
		this.multiJQueryArchetypeName = multiJQueryArchetypeName;
	}


	public String getMultiJQueryArchetypeDesc() {
		return multiJQueryArchetypeDesc;
	}


	public void setMultiJQueryArchetypeDesc(String multiJQueryArchetypeDesc) {
		this.multiJQueryArchetypeDesc = multiJQueryArchetypeDesc;
	}


	public String getMultiJQueryArchetypeAppCode() {
		return multiJQueryArchetypeAppCode;
	}


	public void setMultiJQueryArchetypeAppCode(String multiJQueryArchetypeAppCode) {
		this.multiJQueryArchetypeAppCode = multiJQueryArchetypeAppCode;
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


	public String getMultiJQueryArchetypeEditLink() {
		return multiJQueryArchetypeEditLink;
	}


	public void setMultiJQueryArchetypeEditLink(String multiJQueryArchetypeEditLink) {
		this.multiJQueryArchetypeEditLink = multiJQueryArchetypeEditLink;
	}
	
	

	}
