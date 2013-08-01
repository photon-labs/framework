package com.photon.phresco.uiconstants;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.photon.phresco.selenium.util.ScreenException;

public class ReadXMLFile {

	private static Element eElement;
	private final Log log = LogFactory.getLog(getClass());
	private static final String heliosUiConst = "./src/main/resources/PhrescoUiConstants.xml";
	private static final String phrscEnv = "./src/main/resources/phresco-env-config.xml";
	private static final String heliosData = "./src/main/resources/PhrescoFrameworkData.xml";
	private static final String UserInfoConst = "./src/main/resources/UserInfoConstants.xml";
	private static final String phpConst = "./src/main/resources/PhpConstants.xml";
	private static final String drupal6Const = "./src/main/resources/Drupal6Constants.xml";
	private static final String drupal7Const = "./src/main/resources/Drupal7Constants.xml";
	private static final String wordPressConst = "./src/main/resources/WordPressConstants.xml";
	private static final String j2EEConst = "./src/main/resources/J2EEConstants.xml";
	private static final String javaStandaloneConst = "./src/main/resources/JavaStandaloneConstants.xml";
	private static final String siteCoreConst = "./src/main/resources/SitecoreConstants.xml";
	private static final String aspDotnetConst = "./src/main/resources/AspDotnetConstants.xml";
	private static final String sharePointConst = "./src/main/resources/SharepointConstants.xml";
	private static final String NodeJsConst = "./src/main/resources/NodeJsConstants.xml";
	private static final String jQueryMobileConst = "./src/main/resources/JQueryMobileWidgetConstants.xml";
	private static final String multiYuiConst = "./src/main/resources/MultiYuiWidgetConstants.xml";
	private static final String multiJQueryConst = "./src/main/resources/MultiJQueryWidgetConstants.xml";
	private static final String yuiMobileConst = "./src/main/resources/YuiMobileWidgetConstants.xml";
	private static final String iPhoneNativeConst = "./src/main/resources/IPhoneNativeConstants.xml";
	private static final String iPhoneHybridConst = "./src/main/resources/IPhoneHybridConstants.xml";
	private static final String iPhoneWorkspaceConst = "./src/main/resources/IPhoneWorkspaceConstants.xml";
	private static final String androidNativeConst = "./src/main/resources/AndroidNativeConstants.xml";
	private static final String androidHybridConst = "./src/main/resources/AndroidHybridConstants.xml";
	private static final String androidLibraryConst = "./src/main/resources/AndroidLibraryConstants.xml";
	private static final String iPhoneLibraryConst = "./src/main/resources/IPhoneLibraryConstants.xml";
	private static final String blackBerryHybridConst = "./src/main/resources/BlackberryHybridConstants.xml";
	private static final String windowsPhoneConst = "./src/main/resources/WindowsPhoneConstants.xml";
	private static final String windowsMetroConst = "./src/main/resources/WindowsMetroConstants.xml";
	private static final String scenariosConst = "./src/main/resources/Scenarios.xml";

	public ReadXMLFile() throws ScreenException {

		log.info("@ReadXMLFile Constructor::loading *****ReadXMLFile******");
	}

	public void loadHeliosConstansts(String properties) throws ScreenException {

		try {
			File fXmlFile = new File(properties);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			NodeList nList = doc.getElementsByTagName("environment");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					eElement = (Element) nNode;

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadUserInfoConstants() throws ScreenException {
		loadHeliosConstansts(UserInfoConst);
	}

	public void loadPhrescoEnvConstansts() throws ScreenException {
		loadHeliosConstansts(phrscEnv);
	}

	public void loadHeliosUiConstants() throws ScreenException {
		loadHeliosConstansts(heliosUiConst);
	}

	public void loadHeliosDataConstants() throws ScreenException {
		loadHeliosConstansts(heliosData);
	}

	public void loadPhpConstants() throws ScreenException {
		loadHeliosConstansts(phpConst);
	}

	public void loadDrupal6Constants() throws ScreenException {
		loadHeliosConstansts(drupal6Const);
	}

	public void loadDrupal7Constants() throws ScreenException {
		loadHeliosConstansts(drupal7Const);
	}

	public void loadWordPressConstants() throws ScreenException {
		loadHeliosConstansts(wordPressConst);
	}

	public void loadJavaWebServiceConstants() throws ScreenException {
		loadHeliosConstansts(j2EEConst);
	}

	public void loadJavaStandaloneConstants() throws ScreenException {
		loadHeliosConstansts(javaStandaloneConst);
	}

	public void loadSiteCoreConstants() throws ScreenException {
		loadHeliosConstansts(siteCoreConst);
	}

	public void loadAspDotNetConstants() throws ScreenException {
		loadHeliosConstansts(aspDotnetConst);
	}

	public void loadSharePointConstants() throws ScreenException {
		loadHeliosConstansts(sharePointConst);
	}

	public void loadNodeJsWebServiceConstants() throws ScreenException {
		loadHeliosConstansts(NodeJsConst);
	}

	public void loadJqueryWidgetConstants() throws ScreenException {
		loadHeliosConstansts(jQueryMobileConst);
	}

	public void loadMultiChannelYUIWidgetConstants() throws ScreenException {
		loadHeliosConstansts(multiYuiConst);
	}

	public void loadMultiChannelJQueryWidgetConstants() throws ScreenException {
		loadHeliosConstansts(multiJQueryConst);
	}

	public void loadYUIMobileWidgetConstants() throws ScreenException {
		loadHeliosConstansts(yuiMobileConst);
	}

	public void loadiPhoneNativeConstants() throws ScreenException {
		loadHeliosConstansts(iPhoneNativeConst);
	}

	public void loadiPhoneHybridConstants() throws ScreenException {
		loadHeliosConstansts(iPhoneHybridConst);
	}
	
	public void loadiPhoneWorkspaceConstants() throws ScreenException {
		loadHeliosConstansts(iPhoneWorkspaceConst);
	}

	public void loadAndroidNativeConstants() throws ScreenException {
		loadHeliosConstansts(androidNativeConst);
	}

	public void loadAndroidHybridConstants() throws ScreenException {
		loadHeliosConstansts(androidHybridConst);
	}

	public void loadAndroidLibraryConstants() throws ScreenException {
		loadHeliosConstansts(androidLibraryConst);
	}

	public void loadiPhoneLibraryConstants() throws ScreenException {
		loadHeliosConstansts(iPhoneLibraryConst);
	}

	public void loadBlackBerryHybridConstants() throws ScreenException {
		loadHeliosConstansts(blackBerryHybridConst);
	}

	public void loadWindowsMetroConstants() throws ScreenException {
		loadHeliosConstansts(windowsMetroConst);
	}

	public void loadWindowsPhoneConstants() throws ScreenException {
		loadHeliosConstansts(windowsPhoneConst);
	}

	public void loadScenariosConstants() throws ScreenException {
		loadHeliosConstansts(scenariosConst);
	}

	public String getValue(String elementName) {

		NodeList nlList = eElement.getElementsByTagName(elementName).item(0)
				.getChildNodes();
		Node nValue = nlList.item(0);
		if (nValue == null) {
			return null;
		}

		return nValue.getNodeValue();
	}

}
