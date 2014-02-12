package com.photon.phresco.framework.commons;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.model.Publication;
/**
 * Publication Configuration Creation 
 * @author saravanan_na
 *
 */
public class PublicationCreation implements FrameworkConstants {
	static String publicationconfig ;

	public PublicationCreation(String dotPhrescoFolderPath) {
		publicationconfig = dotPhrescoFolderPath;
	}

	/**
	 * Create the Publication Configuration file
	 * @param publicationConfig
	 * @return
	 * @throws PhrescoException
	 */
	public static boolean createPublicationXml(Publication publicationConfig) throws PhrescoException {
		try {
			Document doc = null;
			Element publications = null;
			Element publication = null;
			File publicationConfigFile = new File(publicationconfig + File.separator + PUBLICATION_CONFIG_FILE);
			if (publicationConfigFile.exists()) {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
				
				doc = documentBuilder.parse(publicationConfigFile);
				publications = (Element) doc.getFirstChild();
				Element publicationElement = null;
				StringBuilder expBuilder = new StringBuilder();
				expBuilder.append("/Publications/publication[@type='"); 
				expBuilder.append(publicationConfig.getPublicationType());
				expBuilder.append("']");	
				
				XPathFactory xPathFactory = XPathFactory.newInstance();
				XPath newXPath = xPathFactory.newXPath();

				XPathExpression xPathExpression = newXPath.compile(expBuilder.toString());
				Node node = (Node) xPathExpression.evaluate(doc, XPathConstants.NODE);
				if (node != null) {
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						publicationElement = (Element) node; 
						publication = publicationElement;
						String attribute = publication.getAttribute(CONFIG_TYPE);
						if (attribute.equalsIgnoreCase(publicationConfig.getPublicationType())) {
							publication.setAttribute(NAME, publicationConfig.getPublicationName());		
							publication = publicationGenerate(publication, publicationConfig, doc);
						}
					}
				}
			} 
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
			transformer.setOutputProperty(OutputKeys.INDENT, YES);

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(publicationConfigFile.getPath());
			transformer.transform(source, result);

		} catch (Exception e) {	
			throw new PhrescoException(e);
		}
		return true;
	}

	/**
	 * Generate the Publication Element
	 * @param publication
	 * @param publicationConfig
	 * @param doc
	 * @return
	 */
	private static Element publicationGenerate(Element publication, Publication publicationConfig, Document doc) {
		try {
			if (publication != null) {
				NodeList childNodes = publication.getChildNodes();
				if (childNodes.getLength() > 0) {
					for (int i = 0; i < childNodes.getLength(); i++) {
						Node node = childNodes.item(i);
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							if (node.getNodeName().equalsIgnoreCase(PUBLICATION_PATH)) {
								node.setTextContent(publicationConfig.getPublicationPath());
							}else if (node.getNodeName().equalsIgnoreCase(PUBLICATION_URL)) {
								node.setTextContent(publicationConfig.getPublicationUrl());
							}else if (node.getNodeName().equalsIgnoreCase(IMAGE_URL)) {
								node.setTextContent(publicationConfig.getImageUrl());
							}else if (node.getNodeName().equalsIgnoreCase(IMAGE_PATH)) {
								node.setTextContent(publicationConfig.getImagePath());
							}else if (node.getNodeName().equalsIgnoreCase(ENVIRONMENT)) {
								node.setTextContent(publicationConfig.getEnvironment());
							}else if (node.getNodeName().equalsIgnoreCase(PUBLICATION_KEY)) {
								node.setTextContent(publicationConfig.getPublicationKey());
							}else if (node.getNodeName().equalsIgnoreCase(PARENT_PUBLICATIONS)) {
								NodeList existingParentPublications = node.getChildNodes();
								for (int j = 0; j < existingParentPublications.getLength(); j++) {
									Node existingParentNode = existingParentPublications.item(j);
									if (existingParentNode.getNodeType() == Node.ELEMENT_NODE) {
										Element existingParentElement = (Element) existingParentNode;
										node.removeChild(existingParentElement);
									}
								}
								List<Map<String,String>> parentPublicationsList = publicationConfig.getParentPublications();
								for (Map<String, String> newParentMap : parentPublicationsList) {
									String newParentName = newParentMap.get(NAME);
									String newParentPriority = newParentMap.get(PRIORITY);
									Element element = doc.createElement(PARENT_SUB_PUBLICATION);
									element.setAttribute(NAME, newParentName);
									element.setAttribute(PRIORITY, newParentPriority);
									element.setTextContent(newParentName);
									node.appendChild(element);
								}
							}
						}
					}
				}
			}
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}
		return publication;
	}
	
	/**
	 * Get the Publication configuration
	 * @return
	 * @throws PhrescoException
	 */
	public  List<Publication> getConfiguration() throws PhrescoException {
		List<Publication> publications = new ArrayList<Publication>();
		try {
			Pattern p = Pattern.compile(PERCENT_TWENTY);          
			p.matcher(publicationconfig);     

			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(publicationconfig));
			doc.getDocumentElement().normalize();	

			NodeList publicationsNodeList = doc.getElementsByTagName(PUBLICATIONS);
			for (int i = 0; i < publicationsNodeList.getLength(); i++) {
				Node publicationNodeItem = publicationsNodeList.item(i);
				if (publicationNodeItem.getNodeType() == Node.ELEMENT_NODE) {
					Element publicationElement = (Element) publicationNodeItem;
					NodeList publicationNodesList = publicationElement.getChildNodes();
					for (int j = 0; j < publicationNodesList.getLength(); j++) {
						Node publicationNode = publicationNodesList.item(j);
						Publication config = new Publication();
						if (publicationNode.getNodeType() == Node.ELEMENT_NODE) {
							Element publishElement = (Element) publicationNode;
							if (publishElement.getNodeName().equalsIgnoreCase(PUBLICATION)) {
								config.setPublicationType(publishElement.getAttributes().getNamedItem(CONFIG_TYPE).getNodeValue());
								config.setPublicationName(publishElement.getAttributes().getNamedItem(NAME).getNodeValue());
							}
							NodeList childNodes = publishElement.getChildNodes();
							for (int k = 0; k < childNodes.getLength(); k++) {
								Node publicationSubNode = childNodes.item(k);
								if (publicationSubNode.getNodeType() == Node.ELEMENT_NODE) {
									Element publicationsElement = (Element) publicationSubNode;
									String nodeName = publicationsElement.getNodeName();
									if (nodeName.equalsIgnoreCase(PUBLICATION_PATH)) {
										config.setPublicationPath(publicationsElement.getTextContent());
									}else if (nodeName.equalsIgnoreCase(PUBLICATION_URL)) {
										config.setPublicationUrl(publicationsElement.getTextContent());
									}else if (nodeName.equalsIgnoreCase(IMAGE_URL)) {
										config.setImageUrl(publicationsElement.getTextContent());
									}else if (nodeName.equalsIgnoreCase(IMAGE_PATH)) {
										config.setImagePath(publicationsElement.getTextContent());
									}else if (nodeName.equalsIgnoreCase(PUBLICATION_KEY)) {
										config.setPublicationKey(publicationsElement.getTextContent());
									}else if (nodeName.equalsIgnoreCase(PARENT_PUBLICATIONS)) {
										List<Map<String, String>>  parentList = new ArrayList<Map<String,String>>();
										NodeList parentPublicationChildNodeList = publicationSubNode.getChildNodes();
										for (int l = 0; l < parentPublicationChildNodeList.getLength(); l++) {
											Node parentPublicaionNode = parentPublicationChildNodeList.item(l);
											if (parentPublicaionNode.getNodeType() == Node.ELEMENT_NODE) {
												Map<String, String> parentMap = new HashMap<String, String>();
												Element parentPublicationElement = (Element) parentPublicaionNode;
												parentMap.put(PARENT_NAME, parentPublicationElement.getAttribute(NAME));
												parentMap.put(PRIORITY, parentPublicationElement.getAttribute(PRIORITY));
												parentList.add(parentMap);
											}
										}
										config.setParentPublications(parentList);
									}
								}
							}
							publications.add(config);
						}
					}
				}
			}
		}
		catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return publications;
	}
}
