/**
 * Phresco Framework Implementation
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
package com.photon.phresco.framework.win8.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Constants;


public class ItemGroupUpdater implements FrameworkConstants, Constants {

	/**
	 * 
	 * @param args
	 * @throws PhrescoException 
	 */
	public static void update(ApplicationInfo info, File path) throws PhrescoException {
		try {
			path = new File(path + File.separator + SOURCE_DIR + File.separator + PROJECT_ROOT + File.separator + PROJECT_ROOT + CSPROJ_FILE);
			List<String> modules = info.getSelectedModules();
			if(!path.exists() && modules == null) {
				return;
			}
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(false);
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(path);
			boolean referenceCheck = referenceCheck(doc);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(path.toURI().getPath());
			transformer.transform(source, result);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (TransformerConfigurationException e) {
			throw new PhrescoException(e);
		} catch (TransformerException e) {
			throw new PhrescoException(e);
		} 
	}
	
	 //TODO: Need to handle the way of getting the modules
	private static void createNewItemGroup(Document doc, List<ArtifactGroup> modules) {
		Element project = doc.getDocumentElement();
		Element itemGroup = doc.createElement(ITEMGROUP);
		for (ArtifactGroup module : modules) {
			Element reference = doc.createElement(REFERENCE);
			reference.setAttribute(INCLUDE , module.getName());
			Element hintPath = doc.createElement(HINTPATH);
			hintPath.setTextContent(DOUBLE_DOT + COMMON + File.separator + module.getName()+ DLL);
			reference.appendChild(hintPath);
			itemGroup.appendChild(reference);
		}
		project.appendChild(itemGroup);
	}
	
	
	 //TODO: Need to handle the way of getting the modules
	private static void updateItemGroups(Document doc, List<ArtifactGroup> module) {
	   List<Node> itemGroup = getItemGroup(doc);
	   updateContent(doc, module, itemGroup, REFERENCE);
	}

	 //TODO: Need to handle the way of getting the modules
	private static void updateContent(Document doc, List<ArtifactGroup> modules,	List<Node> itemGroup, String elementName) {
		for (Node node : itemGroup) {
			NodeList childNodes = node.getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				Node item = childNodes.item(j);
				if (item.getNodeName().equals(elementName)) {
					Node parentNode = item.getParentNode();
					for (ArtifactGroup module : modules) {
						Element content = doc.createElement(elementName);
						if (elementName.equalsIgnoreCase(REFERENCE)) {
							content.setAttribute(INCLUDE, module.getName()+ DLL);
							Element hintPath = doc.createElement(HINTPATH);
							hintPath.setTextContent(DOUBLE_DOT + COMMON + File.separator + module.getName()+ DLL);
							content.appendChild(hintPath);
						} else {
							content.setAttribute(INCLUDE, module.getName()+ DLL);
						}
						parentNode.appendChild(content);
					}
					break;
				}
			}
		}
	}
	
	private static List<Node> getItemGroup(Document doc) {
		NodeList projects = doc.getElementsByTagName(PROJECT);
		List<Node> itemGroupList = new ArrayList<Node>();
		for (int i = 0; i < projects.getLength(); i++) {
			Element project = (Element) projects.item(i);
			NodeList itemGroups = project.getElementsByTagName(ITEMGROUP);
			for (int j = 0; j < itemGroups.getLength(); j++) {
				Element itemGroup = (Element) itemGroups.item(j);
				itemGroupList.add(itemGroup);
			}
		}
		return itemGroupList;
	}
	
	private static boolean referenceCheck(Document doc) {
		NodeList project = doc.getElementsByTagName(PROJECT);
		Boolean flag = false;
		for (int i = 0; i < project.getLength(); i++) {
			Element element = (Element) project.item(i);
			NodeList ITEMGROUPs = element.getElementsByTagName(ITEMGROUP);
			for (int j = 0; j < ITEMGROUPs.getLength(); j++) {
				Element itemGroup = (Element) ITEMGROUPs.item(j);
				NodeList references = itemGroup.getElementsByTagName(REFERENCE);
				for (int k = 0; k < references.getLength(); k++) {
					Element reference = (Element) references.item(k);
					if (reference.getTagName().equalsIgnoreCase(REFERENCE)) {
						flag = true;
					} else {
						flag = false;
					}
				}
			}
		}
		return flag;
	}
}
