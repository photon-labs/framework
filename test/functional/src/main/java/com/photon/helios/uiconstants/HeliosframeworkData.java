package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HeliosframeworkData {

	private Log log = LogFactory.getLog("HeliosframeworkData");

	/*private String webLayerAppInfoServerValue = "webLayerServerValue";
	private String webLayerAppInfoServerVersion = "webLayerServerVersion";
	private String webLayerAppInfoFunFrameWork = "webLayerFunctionalFrameWorkValue";
	private String webLayerAppInfoFunTool = "webLayerFunctionalToolValue";
	private String webLayerAppInfoFunToolVersion = "webLayerFunctionalToolVersion";
	private String webLayerAppInfoWebService = "webLayerWebServiceXpath";

	private String mobLayerServerValue = "mobLayerServerValue";
	private String mobLayerServerVersion = "mobLayerServerVersion";
	private String mobLayerWebService = "mobLayerWebService";*/

	public HeliosframeworkData() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadHeliosDataConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("HeliosframeworkData::" + localException.getMessage());
		}
	}

	}
