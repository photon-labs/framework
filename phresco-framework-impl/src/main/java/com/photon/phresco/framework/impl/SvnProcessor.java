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
package com.photon.phresco.framework.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.commons.model.CIJob;
import com.trilead.ssh2.crypto.Base64;

public class SvnProcessor implements FrameworkConstants{
    private static final Logger S_LOGGER = Logger.getLogger(SvnProcessor.class);
    private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();
    private Document document_ = null;
    private Element root_ = null;
    
    private static final String SVN_CONFIG = "http://172.16.18.174:9090/nexus/service/local/repositories/dev-binaries/content/config/ci/credential/1.0/credential-1.0.xml";
    private static String FINAL_PATH = "C:\\download\\workspace\\tools\\jenkins\\data\\jobs\\jenkinsdemo";
    
    public SvnProcessor(URL credentialUrl) throws JDOMException, IOException {
    	if (debugEnabled) {
    		S_LOGGER.debug("SvnProcessor constructor : " + credentialUrl);
    	}
        SAXBuilder builder = new SAXBuilder();       
        document_ = builder.build(credentialUrl);
        root_ = document_.getRootElement();
    }
    
    public SvnProcessor(InputStream credentialXml) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();       
        document_ = builder.build(credentialXml);
        root_ = document_.getRootElement();
    }
    
    public SvnProcessor(File csvFile) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        if (debugEnabled) {
           S_LOGGER.debug("SvnProcessor constructor : " + csvFile);
        }
        
        document_ = builder.build(csvFile);
        root_ = document_.getRootElement();
    }
    
    public void changeNodeValue(String nodePath, String nodeValue) throws JDOMException {
        if (debugEnabled) {
           S_LOGGER.debug("Entering Method ConfigProcessor.changeNodeValue: " + nodePath + ", " + nodeValue);
        }
        
        XPath xpath = XPath.newInstance(nodePath);
        xpath.addNamespace(root_.getNamespace());
        Element scmNode = (Element) xpath.selectSingleNode(root_);
        scmNode.setText(nodeValue);
    }
    
    public String getNodeValue(String nodePath) throws JDOMException {
        if (debugEnabled) {
           S_LOGGER.debug("Entering Method ConfigProcessor.getNodeValue: " + nodePath);
        }
        
        XPath xpath = XPath.newInstance(nodePath);
        xpath.addNamespace(root_.getNamespace());
        Element element = (Element) xpath.selectSingleNode(root_);
        return element.getText();
    }
    
    public InputStream getConfigAsStream() throws IOException {
        XMLOutputter xmlOutput = new XMLOutputter();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        xmlOutput.output(document_, outputStream);
        
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    
    public void writeStream(File path) throws IOException {
        XMLOutputter xmlOutput = new XMLOutputter();
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        xmlOutput.output(document_, writer);
    }
    
    public boolean writeSvnXml(String generatedStringValue, CIJob job) throws PhrescoException {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method SvnProcessor.writeXml(String generatedStringValue, CIJob job)");
    	}

    	boolean credExist = false;
    	try {
    		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    		domFactory.setNamespaceAware(true); 
    		DocumentBuilder builder = domFactory.newDocumentBuilder();
    		String jenkinsJobHome = System.getenv(JENKINS_HOME);
    		StringBuilder jenkinsHome = new StringBuilder(jenkinsJobHome);
    		jenkinsHome.append(File.separator);
    		File svnCredentialXml = new File(jenkinsHome.toString() + CI_CREDENTIAL_XML);
    		org.w3c.dom.Document doc = builder.parse(svnCredentialXml);
    		XPathFactory xpathfactory = XPathFactory.newInstance();
    		javax.xml.xpath.XPath xpath = xpathfactory.newXPath();
    		XPathExpression expr = xpath.compile(CREDENTIAL_ENTRY_STRING);
    		Object result = expr.evaluate(doc, XPathConstants.NODESET);
    		NodeList nodes = (NodeList) result;
    		boolean nodeExistFlag = false;
    		if (nodes != null) {
    			for (int i = 0; i < nodes.getLength(); i++) {
    				String xmlStringValue = nodes.item(i).getTextContent();
    				//check already present?
    				if (xmlStringValue.equals(generatedStringValue)) {
    					nodeExistFlag = true;

    					Node firstSibling = nodes.item(i).getNextSibling().getNextSibling();
    					Node firstNode = firstSibling.getFirstChild().getNextSibling();
    					String firstNodeName = firstNode.getNodeName();
    					if (USERNAME.equals(firstNodeName)) {
    						if (firstNode.getTextContent().equals(job.getUsername())) {
    							credExist = true;
    						} else {
    							firstNode.setTextContent(job.getUsername());
    						}
    					} else if (PASSWORD.equals(firstNodeName)) {
    						firstNode.setTextContent(job.getPassword());
    					}

    					Node lastSibling = nodes.item(i).getNextSibling().getNextSibling();
    					Node lastNode = lastSibling.getLastChild().getPreviousSibling();
    					String lastNodeName = lastNode.getNodeName();
    					if (PASSWORD.equals(lastNodeName)) {
    						lastNode.setTextContent(job.getPassword());
    					} else if (USERNAME.equals(lastNodeName)) {
    						if (lastNode.getTextContent().equals(job.getUsername())) {
    							credExist = true;
    						} else {
    							lastNode.setTextContent(job.getUsername());
    						}
    					}
    				} 
    				if ( i == nodes.getLength()-1) {
    					//create new nodes if not present already.
    					if (!nodeExistFlag) {
    						Node credentialsNode = nodes.item(i).getParentNode().getParentNode();
    						org.w3c.dom.Element entry = doc.createElement(ENTRY_TAG);
    						credentialsNode.appendChild(entry);

    						org.w3c.dom.Element newString = doc.createElement(STRING_TAG);
    						entry.appendChild(newString);
    						newString.setTextContent(generatedStringValue);

    						org.w3c.dom.Element PasswordCredential = doc.createElement(HYPEN_PASSWORDCREDENIAL_TAG);
    						entry.appendChild(PasswordCredential);

    						org.w3c.dom.Element userName = doc.createElement(USERNAME);
    						PasswordCredential.appendChild(userName);
    						userName.setTextContent(job.getUsername());

    						org.w3c.dom.Element password = doc.createElement(PASSWORD);
    						PasswordCredential.appendChild(password);
    						password.setTextContent(job.getPassword());
    					}
    				}
    			}
    		}
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		transformer.setOutputProperty(OutputKeys.INDENT, YES);
    		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    		transformer.transform(new DOMSource(doc), new StreamResult(svnCredentialXml.getPath()));
    		return credExist;
    	} catch (Exception e) {
    		if (debugEnabled) {
    			S_LOGGER.error("Entered into the catch block of SvnProcessor.writeXml " + e.getLocalizedMessage());
    		}
    		throw new PhrescoException(e);
    	} 
    }
    
    public void writeConfluenceXml(String confUrl, String confUsername, String confPassword) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.error("Entered Method SvnProcessor.writeConfluenceXml");
		}
    	try {
    		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    		domFactory.setNamespaceAware(true); 
    		DocumentBuilder builder;

    		builder = domFactory.newDocumentBuilder();

    		String jenkinsJobHome = System.getenv(JENKINS_HOME);
    		StringBuilder jenkinsHome = new StringBuilder(jenkinsJobHome);
    		jenkinsHome.append(File.separator);
    		File confluenceHomeXml = new File(jenkinsHome.toString() + CI_CONFLUENCE_XML);
    		org.w3c.dom.Document doc = builder.parse(confluenceHomeXml);
    		XPathFactory xpathfactory = XPathFactory.newInstance();
    		javax.xml.xpath.XPath xpath = xpathfactory.newXPath();
    		
    		Node siteNode = (Node) xpath.evaluate(SITES_CONFLUENCE, doc, XPathConstants.NODE);
    		org.w3c.dom.Element confluenceSiteNode = doc.createElement(CONFLUENCE_SITE_NODE);
    		siteNode.appendChild(confluenceSiteNode);
			
			org.w3c.dom.Element confluenceSiteUrl = doc.createElement(CONFLUENCE_SITE_URL);
			confluenceSiteNode.appendChild(confluenceSiteUrl);
			confluenceSiteUrl.setTextContent(confUrl);
			
			org.w3c.dom.Element confluenceUserName = doc.createElement(CONFLUENCE_USERNAME);
			confluenceSiteNode.appendChild(confluenceUserName);
			confluenceUserName.setTextContent(confUsername);
			
			org.w3c.dom.Element confluencePassword = doc.createElement(PASSWORD);
			confluenceSiteNode.appendChild(confluencePassword);
			confluencePassword.setTextContent(confPassword);
    		
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		transformer.setOutputProperty(OutputKeys.INDENT, YES);
    		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    		transformer.transform(new DOMSource(doc), new StreamResult(confluenceHomeXml.getPath()));
    	} catch (Exception e) {
    		if (debugEnabled) {
    			S_LOGGER.error("Entered into the catch block of SvnProcessor.writeConfluenceXml " + e.getLocalizedMessage());
    		}
    		throw new PhrescoException(e);
    	} 
    }
    
    public JSONArray readConfluenceXml() throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.error("Entered Method SvnProcessor.readConfluenceXml");
		}
    	try {
    		String jenkinsJobHome = System.getenv(JENKINS_HOME);
    		StringBuilder jenkinsHome = new StringBuilder(jenkinsJobHome);
    		jenkinsHome.append(File.separator);
    		File confluenceHomeXml = new File(jenkinsHome.toString() + CI_CONFLUENCE_XML);
    		if (!confluenceHomeXml.exists()) {
    			return null;
    		} else {
    			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    			domFactory.setNamespaceAware(true); 
    			DocumentBuilder builder = domFactory.newDocumentBuilder();
    			org.w3c.dom.Document doc = builder.parse(confluenceHomeXml);
    			XPathFactory xpathfactory = XPathFactory.newInstance();
    			javax.xml.xpath.XPath xpath = xpathfactory.newXPath();
    			XPathExpression expr = xpath.compile(SITES_CONFLUENCESITE_URL);
    			Object result = expr.evaluate(doc, XPathConstants.NODESET);
    			NodeList nodes = (NodeList) result;
    			if (nodes.getLength() == 0) {
    				return null;
    			}
    			if (nodes != null) {
    				JSONArray jsonarray = new JSONArray();
    				for (int i = 0; i < nodes.getLength(); i++) {
    					JSONObject json = new JSONObject();
    					String url = "";
    					String userName = "";
    					String password = "";
    					String urlNode = nodes.item(i).getNodeName();
    					if (CONFLUENCE_SITE_URL.equalsIgnoreCase(urlNode)) {
    						url = nodes.item(i).getTextContent();
    					} else if (CONFLUENCE_USERNAME.equalsIgnoreCase(urlNode)) {
    						userName = nodes.item(i).getTextContent();
    					} else if (PASSWORD.equalsIgnoreCase(urlNode)) {
    						password = nodes.item(i).getTextContent();
    					}

    					String nameNode = nodes.item(i).getNextSibling().getNextSibling().getNodeName();
    					if (CONFLUENCE_SITE_URL.equalsIgnoreCase(nameNode)) {
    						url = nodes.item(i).getNextSibling().getNextSibling().getTextContent();
    					} else if (CONFLUENCE_USERNAME.equalsIgnoreCase(nameNode)) {
    						userName = nodes.item(i).getNextSibling().getNextSibling().getTextContent();
    					} else if (PASSWORD.equalsIgnoreCase(nameNode)) {
    						password = nodes.item(i).getNextSibling().getNextSibling().getTextContent();
    					}

    					String passNode = nodes.item(i).getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNodeName();
    					if (CONFLUENCE_SITE_URL.equalsIgnoreCase(passNode)) {
    						url = nodes.item(i).getNextSibling().getNextSibling().getNextSibling().getNextSibling().getTextContent();
    					} else if (CONFLUENCE_USERNAME.equalsIgnoreCase(passNode)) {
    						userName = nodes.item(i).getNextSibling().getNextSibling().getNextSibling().getNextSibling().getTextContent();
    					} else if (PASSWORD.equalsIgnoreCase(passNode)) {
    						password = nodes.item(i).getNextSibling().getNextSibling().getNextSibling().getNextSibling().getTextContent();
    					}
    					json.put(CONFLUENCE_SITE_URL, url);
    					json.put(CONFLUENCE_USERNAME, userName);
    					json.put(PASSWORD, decyPassword(password));
    					jsonarray.put(json);
    				}

    				return jsonarray;
    			}
    		}
    	} catch (Exception e) {
    		if (debugEnabled) {
    			S_LOGGER.error("Entered into the catch block of SvnProcessor.readConfluenceXml "+e.getLocalizedMessage());
    		}
    		throw new PhrescoException(e);
    	}
    	
		return null;
    }
    
    public List<String> readConfluenceSites() throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.error("Entered Method SvnProcessor.readConfluenceSites");
		}
    	try {
    		List<String> confluenceSites = new ArrayList<String>();
    		String jenkinsJobHome = System.getenv(JENKINS_HOME);
    		StringBuilder jenkinsHome = new StringBuilder(jenkinsJobHome);
    		jenkinsHome.append(File.separator);
    		File confluenceHomeXml = new File(jenkinsHome.toString() + CI_CONFLUENCE_XML);
    		if (!confluenceHomeXml.exists()) {
    			return null;
    		} else {
    			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    			DocumentBuilder db = dbf.newDocumentBuilder();
    			org.w3c.dom.Document dom = db.parse(confluenceHomeXml);
    			javax.xml.xpath.XPath xpath = XPathFactory.newInstance().newXPath();

    			XPathExpression thingExpr = xpath.compile(SITES_CONFLUENCESITE_URL);
    			NodeList siteUrls = (NodeList)thingExpr.evaluate(dom, XPathConstants.NODESET);
    			for(int count = 0; count < siteUrls.getLength(); count++) {
    				confluenceSites.add(getHost(siteUrls.item(count).getTextContent()));
    			}
    			return confluenceSites;
    		}
    	} catch(Exception e) {
    		if (debugEnabled) {
    			S_LOGGER.error("Entered catch block of SvnProcessor.readConfluenceSites "+e.getLocalizedMessage());
    		}
    		throw new PhrescoException(e);	
    	}
    }
    
    private String getHost(String url) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.error("Entered Method SvnProcessor.getHost");
		}
    	try {
			URL host = new URL(url);
			return host.getHost();
		} catch (MalformedURLException e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered catch block of SvnProcessor.getHost "+e.getLocalizedMessage());
			}
			throw new PhrescoException(e);
		}
    }
    
    private String decyPassword(String encryptedText) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.error("Entered Method SvnProcessor.decyPassword");
		}
        String plainText = "";
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, getAes128Key(CI_SECRET_KEY));
            byte[] decode = Base64.decode(encryptedText.toCharArray());
            plainText = new String(cipher.doFinal(decode));
            plainText = plainText.replace(CI_ENCRYPT_MAGIC, "");
        } catch (Exception e) {
        	if (debugEnabled) {
    			S_LOGGER.error("Entered catch block of SvnProcessor.decyPassword "+e.getLocalizedMessage());
    		}
            throw new PhrescoException(e);
        }
        return plainText;
    }
    
    private static SecretKey getAes128Key(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_ALGO);
            digest.reset();
            digest.update(s.getBytes(CI_UTF8));
            return new SecretKeySpec(digest.digest(),0,128/8, AES_ALGO);
        } catch (NoSuchAlgorithmException e) {
            throw new Error(e);
        } catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }
    }
    
    public void clearSitesNodes() throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.error("Entered Method SvnProcessor.clearSitesNodes");
		}
    	try {
    		String jenkinsJobHome = System.getenv(JENKINS_HOME);
    		StringBuilder jenkinsHome = new StringBuilder(jenkinsJobHome);
    		jenkinsHome.append(File.separator);
    		File confluenceHomeXml = new File(jenkinsHome.toString() + CI_CONFLUENCE_XML);
    		if (confluenceHomeXml.exists()) {
    			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    			domFactory.setNamespaceAware(true);
    			DocumentBuilder builder = domFactory.newDocumentBuilder();
    			org.w3c.dom.Document doc = builder.parse(confluenceHomeXml);
    			removeAll(doc, Node.ELEMENT_NODE, CONFLUENCE_SITE_NODE);
    			TransformerFactory transformerFactory = TransformerFactory.newInstance();
    			Transformer transformer = transformerFactory.newTransformer();
    			transformer.setOutputProperty(OutputKeys.INDENT, YES);
    			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    			transformer.transform(new DOMSource(doc), new StreamResult(confluenceHomeXml.getPath()));
    		}
    	} catch (Exception e) {
    		if (debugEnabled) {
    			S_LOGGER.error("Entered catch block of SvnProcessor.clearSitesNodes "+e.getLocalizedMessage());
    		}
    		throw new PhrescoException(e);
    	}
    }

    private void removeAll(Node node, short nodeType, String name) {
    	if (node.getNodeType() == nodeType && (name == null || node.getNodeName().equals(name))) {
    		node.getParentNode().removeChild(node);
    	} else {
    		NodeList list = node.getChildNodes();
    		for (int i = 0; i < list.getLength(); i++) {
    			removeAll(list.item(i), nodeType, name);
    		}
    	}
    }
    
    
//    public static void main(String[] args) {
//        try {
//            SvnProcessor processor = new SvnProcessor(new URL(SVN_CONFIG));
//            processor.changeNodeValue("credentials/entry//userName", "****");
//            processor.changeNodeValue("credentials/entry//password", "****");
//            processor.writeStream(new File(FINAL_PATH + File.separator + "hudson.scm.SubversionSCM.xml"));
//        } catch (JDOMException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
