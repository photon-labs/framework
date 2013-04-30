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
import java.net.URL;

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
import com.photon.phresco.framework.model.CIJob;

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
    
    public boolean writeXml(String generatedStringValue, CIJob job) throws PhrescoException {
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
    		File file = new File(jenkinsHome.toString() + CI_CREDENTIAL_XML);
    		org.w3c.dom.Document doc = builder.parse(file);
    		XPathFactory xpathfactory = XPathFactory.newInstance();
    		javax.xml.xpath.XPath xpath = xpathfactory.newXPath();
    		XPathExpression expr = xpath.compile(CREDENTIAL_ENTRY_STRING);
    		Object result = expr.evaluate(doc, XPathConstants.NODESET);
    		NodeList nodes = (NodeList) result;
    		boolean flag = false;
    		if (nodes != null) {
    			for (int i = 0; i < nodes.getLength(); i++) {
    				String xmlStringValue = nodes.item(i).getTextContent();
    				//check already present?
    				if(xmlStringValue.equals(generatedStringValue)) {
    					flag = true;

    					Node firstSibling = nodes.item(i).getNextSibling().getNextSibling();
    					Node firstNode = firstSibling.getFirstChild().getNextSibling();
    					String firstNodeName = firstNode.getNodeName();
    					if(USERNAME.equals(firstNodeName)) {
    						if (firstNode.getTextContent().equals(job.getUserName())) {
    							credExist = true;
    						} else {
    							firstNode.setTextContent(job.getUserName());
    						}
    					} else if(PASSWORD.equals(firstNodeName)) {
    						firstNode.setTextContent(job.getPassword());
    					}

    					Node lastSibling = nodes.item(i).getNextSibling().getNextSibling();
    					Node lastNode = lastSibling.getLastChild().getPreviousSibling();
    					String lastNodeName = lastNode.getNodeName();
    					if(PASSWORD.equals(lastNodeName)) {
    						lastNode.setTextContent(job.getPassword());
    					} else if(USERNAME.equals(lastNodeName)) {
    						if (lastNode.getTextContent().equals(job.getUserName())) {
    							credExist = true;
    						} else {
    							lastNode.setTextContent(job.getUserName());
    						}
    					}
    				} 
    				if ( i == nodes.getLength()-1) {
    					//create new if not present
    					if (!flag) {
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
    						userName.setTextContent(job.getUserName());

    						org.w3c.dom.Element password = doc.createElement(PASSWORD);
    						PasswordCredential.appendChild(password);
    						password.setTextContent(job.getPassword());
    					}
    				}
    			}
    		}
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer;

    		transformer = transformerFactory.newTransformer();
    		transformer.setOutputProperty(OutputKeys.INDENT, YES);
    		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    		transformer.transform(new DOMSource(doc), new StreamResult(file.getPath()));
    		return credExist;
    	} catch (Exception e) {
    		if (debugEnabled) {
    			S_LOGGER.error("Entered into the catch block of SvnProcessor.writeXml " + e.getLocalizedMessage());
    		}
    		throw new PhrescoException(e);
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
