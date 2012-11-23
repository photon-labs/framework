package com.photon.phresco.framework.param.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class mainClassNameImpl implements DynamicParameter, Constants {

	@Override
	public PossibleValues getValues(Map<String, Object> map) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException {
		PossibleValues possibleValues = new PossibleValues();
		try {
			ApplicationInfo applicationInfo = (ApplicationInfo) map.get(KEY_APP_INFO);
			String pomPath = Utility.getProjectHome() + File.separator + applicationInfo.getAppDirName() + File.separator + FrameworkConstants.POM_FILE;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File(pomPath));
			document.getDocumentElement().normalize();
			NodeList nodeList = document.getElementsByTagName(FrameworkConstants.JAVA_POM_MANIFEST);
			String mainClassValue = "";
			for (int temp = 0; temp < nodeList.getLength(); temp++) {
				Node node = nodeList.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element mainClassElement = (Element) node;
					mainClassValue = mainClassElement.getElementsByTagName(FrameworkConstants.JAVA_POM_MAINCLASS).item(0).getTextContent();
					Value value = new Value();
					value.setValue(mainClassValue);
		    		possibleValues.getValue().add(value);
					break;
				}
			}
		} catch (Exception e) {
		}
		
		return possibleValues;
	}

}
