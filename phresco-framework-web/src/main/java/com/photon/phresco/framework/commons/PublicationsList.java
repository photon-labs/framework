package com.photon.phresco.framework.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.impl.ConfigurationReader;
import com.photon.phresco.framework.model.Publications;

/**
 * 
 * @author saravanan_na
 * 
 */

/**
 * Get the Publication List 
 */
public class PublicationsList implements FrameworkConstants {
	private static File configPath;
	private static Map<String, String> statusMap = new HashMap<String, String>();
	private static Map<String, String> actionMap = new HashMap<String, String>();

	public PublicationsList(File configPath) {
		this.configPath = configPath;
	}

	/**
	 * Get the Publication List
	 * @param server
	 * @param username
	 * @param password
	 * @return
	 * @throws PhrescoException
	 */
	public List<Publications> getPublicationList(String server, String username, String password) throws PhrescoException {
		BufferedReader inputReader = null;
		int totalPublications = 0;
		List<Publications> publicationList = new ArrayList<Publications>();
		try {
			StringBuilder builder = new  StringBuilder();
			builder.append(POWER_SHELL);
			builder.append(SPACE);
			builder.append(INPUTFORMAT_NONE);
			builder.append(SPACE);
			builder.append(EXECUTION_POLICY_BYPASS);
			builder.append(SPACE);
			builder.append(PUBLICATION_COMMAND);
			builder.append(SPACE);
			builder.append(PUBLICATION_CONSTANT);
			builder.append(SPACE);
			builder.append(configPath.getPath());
			builder.append(File.separator);
			builder.append(PUBLICATION_SCRIPT);
			builder.append(SPACE);
			builder.append(GET_PUBLICATIONLIST);
			builder.append(SPACE);
			builder.append("\""+ server+"\"");
			builder.append(SPACE);
			builder.append("\""+ username+"\"");
			builder.append(SPACE);
			builder.append("\""+password+"\"");
			builder.append(PUBLICATION_CLOSE);
			String command = builder.toString();
			System.out.println("command = " + command);
			Commandline commandline = new Commandline(command);
			commandline.setWorkingDirectory(configPath);
			System.out.println(" Executing............");
			Process process = commandline.execute();
			String line = "";
			StringBuilder errorBuilder = new StringBuilder();
			boolean errorOccured = false;
			inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = inputReader.readLine())!= null) {
				if (line.startsWith(DOLLAR)) {
					String length = line.substring(1, line.length()-1);
					totalPublications = Integer.parseInt(length);
				}
				StringTokenizer token = new StringTokenizer(line, ",");
				if (line.startsWith(TCM)) {
					Publications publications = new Publications();
					while (token.hasMoreTokens()) {
						publications.setId(token.nextToken());
						publications.setItem(token.nextToken());
					}
					publicationList.add(publications);
				}
				if (line.startsWith(PUBLICATION_ERROR) || errorOccured ) {
					errorBuilder.append(line);
					errorOccured = true;
				}
			}
			if (errorOccured) {
				throw new PhrescoException(errorBuilder.toString());
			}
		} catch (CommandLineException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
		List<Publications> newPublicationList = getPublicationsFromFile();
		publicationList.addAll(newPublicationList);
		return publicationList;
	}
	
	
	/**
	 * Get Publication from File
	 * @return List<Publications>
	 */
	private List<Publications> getPublicationsFromFile() {
		List<Publications> publicationsList = new ArrayList<Publications>();
		try {
			File publicationConfigFile = new File(configPath + File.separator + PUBLICATION_CONFIG_FILE);
			if (publicationConfigFile.exists()) {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();

				Document doc = documentBuilder.parse(publicationConfigFile);

				List<String> publicationsTypes = new ArrayList<String>();
				publicationsTypes.add(SCHEMA);
				publicationsTypes.add(TEMPLATE);
				publicationsTypes.add(CONTENT);
				publicationsTypes.add(GLOBAL_LANGUAGE_CONTENT);
				String publicationName = "";
				String tcmId = "";
				if (CollectionUtils.isNotEmpty(publicationsTypes)) {
					for (String type : publicationsTypes) {
						Publications publicationlist = new Publications();
						
						StringBuilder expBuilder = new StringBuilder();
						expBuilder.append("/Publications/publication[@type='"); 
						expBuilder.append(type);
						expBuilder.append("']");	
						expBuilder.append("/target/environment");
						
						XPathFactory xPathFactory = XPathFactory.newInstance();
						XPath newXPath = xPathFactory.newXPath();

						XPathExpression xPathExpression = newXPath.compile(expBuilder.toString());
						Node node = (Node) xPathExpression.evaluate(doc, XPathConstants.NODE);
						if (node != null) {
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								Element ele = (Element) node; 
								tcmId = ele.getAttribute(TCM_ID);
								if (tcmId.startsWith(TCM)) {
									publicationlist.setId(tcmId);
								}
								Node parentNode = node.getParentNode().getParentNode();
								if (node.getNodeType() == Node.ELEMENT_NODE) {
									Element publicationEle = (Element) parentNode; 
									publicationName = publicationEle.getAttribute(NAME);
								}
							}
							if (publicationName != null && tcmId != null) {
								publicationlist.setItem(publicationName);
								publicationlist.setId(tcmId);
							}
						}
						if (publicationlist != null) {
							publicationsList.add(publicationlist);
						}
					}
				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return publicationsList;
	}

	/**
	 * Get the Publication Target
	 * @param server
	 * @param username
	 * @param password
	 * @return List<publications>
	 * @throws PhrescoException
	 */
	public List<Publications> getPublicationTarget(String server, String username, String password) throws PhrescoException {
		BufferedReader rd = null;
		int totalPublications = 0;
		List<Publications> publicationList = new ArrayList<Publications>();
		try {
			StringBuilder builder = new  StringBuilder();
			builder.append(POWER_SHELL);
			builder.append(SPACE);
			builder.append(INPUTFORMAT_NONE);
			builder.append(SPACE);
			builder.append(EXECUTION_POLICY_BYPASS);
			builder.append(SPACE);
			builder.append(PUBLICATION_COMMAND);
			builder.append(SPACE);
			builder.append(PUBLICATION_CONSTANT);
			builder.append(SPACE);
			builder.append(configPath.getPath());
			builder.append(File.separator);
			builder.append(PUBLICATION_SCRIPT);
			builder.append(SPACE);
			builder.append(GET_PUBLICATION_TARGET);
			builder.append(SPACE);
			builder.append("\""+ server+"\"");
			builder.append(SPACE);
			builder.append("\""+ username+"\"");
			builder.append(SPACE);
			builder.append("\""+ password+"\"");
			builder.append(PUBLICATION_CLOSE);
			String command = builder.toString();
			System.out.println(command);
			System.out.println("command = " + command);

			Commandline commandline = new Commandline(command);
			commandline.setWorkingDirectory(configPath);
			System.out.println(" Executing............");
			Process process = commandline.execute();
			String line = "";
			
			StringBuilder errorBuilder = new StringBuilder();
			boolean errorOccured = false;
			
			rd = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = rd.readLine())!= null) {
				if (line.startsWith(DOLLAR)) {
					String length = line.substring(1, line.length()-1);
					totalPublications = Integer.parseInt(length);
				}
				StringTokenizer token = new StringTokenizer(line, ",");
				if (line.startsWith(TCM)) {
					Publications publications = new Publications();
					while (token.hasMoreTokens()) {
						publications.setId(token.nextToken());
						publications.setItem(token.nextToken());
					}
					publicationList.add(publications);
				}
				if (line.startsWith(PUBLICATION_ERROR) || errorOccured ) {
					errorBuilder.append(line);
					errorOccured = true;
				}
			}
			if (errorOccured) {
				throw new PhrescoException(errorBuilder.toString());
			}
		} catch (CommandLineException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
		return publicationList;
	}	

    /**
     * Create the Publication 
     * @param server
     * @param username
     * @param password
     * @param environmentName
     * @throws PhrescoException
     */
	public static void createPublication(String server, String username, String password, String environmentName) throws PhrescoException {
		BufferedReader rd = null;
		try {
			StringBuilder builder = new  StringBuilder();
			builder.append(POWER_SHELL);
			builder.append(SPACE);
			builder.append(INPUTFORMAT_NONE);
			builder.append(SPACE);
			builder.append(EXECUTION_POLICY_BYPASS);
			builder.append(SPACE);
			builder.append(PUBLICATION_COMMAND);
			builder.append(SPACE);
			builder.append(PUBLICATION_CONSTANT);
			builder.append(SPACE);
			builder.append(configPath.getPath());
			builder.append(File.separator);
			builder.append(PUBLICATION_SCRIPT);
			builder.append(SPACE);
			builder.append(CREATE_PUBLICATION);
			builder.append(SPACE);
			builder.append("\""+ server+"\"");
			builder.append(SPACE);
			builder.append("\""+ username+"\"");
			builder.append(SPACE);
			builder.append("\""+ password+"\"");
			builder.append(SPACE);
			builder.append(configPath.getPath());
			builder.append(FILE_SEPARATOR);
			builder.append(PUBLICATION_VIRTUAL_CONFIG_FILE);
			builder.append(PUBLICATION_CLOSE);
			String command = builder.toString();
			System.out.println(command);
			Commandline commandline = new Commandline(command);
			commandline.setWorkingDirectory(configPath);
			System.out.println(" Executing............");
			Process process = commandline.execute();
			String line = "";
			StringBuilder errorBuilder = new StringBuilder();
			boolean errorOccured = false;
			String publicationName = "";
			String tcmId = "";
			rd = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = rd.readLine())!= null) {
				if (line.startsWith("publicationName")) {
					publicationName = line.substring(line.indexOf(":")+1, line.indexOf("$"));
					tcmId = line.substring(line.lastIndexOf("$")+1, line.length());
					updateTcmId(publicationName, tcmId, environmentName);
				}
				if (line.startsWith(PUBLICATION_ERROR) || errorOccured ) {
					errorBuilder.append(line);
					errorOccured = true;
				}
			}
			if (errorOccured) {
				throw new PhrescoException(errorBuilder.toString());
			}
		}
		catch (CommandLineException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}
	
	public static String publishPagesToserver(String server, String username, String password, String targetId, String publishSiteId, String type) throws PhrescoException {
		BufferedReader rd = null;
		String transactionId = "";
		try {
			StringBuilder builder = new  StringBuilder();
			builder.append(POWER_SHELL);
			builder.append(SPACE);
			builder.append(INPUTFORMAT_NONE);
			builder.append(SPACE);
			builder.append(EXECUTION_POLICY_BYPASS);
			builder.append(SPACE);
			builder.append(PUBLICATION_COMMAND);
			builder.append(SPACE);
			builder.append(PUBLICATION_CONSTANT);
			builder.append(SPACE);
			builder.append(configPath.getPath());
			builder.append(File.separator);
			builder.append(PUBLICATION_SCRIPT);
			builder.append(SPACE);
			builder.append(type);
			builder.append(SPACE);
			builder.append("\"" + server + "\"");
			builder.append(SPACE);
			builder.append("\"" + username + "\"");
			builder.append(SPACE);
			builder.append("\"" + password + "\"");
			builder.append(SPACE);
			builder.append("\"" + publishSiteId + "\"");
			builder.append(SPACE);
			builder.append("\""+ targetId +"\"");
			builder.append(PUBLICATION_CLOSE);
			String command = builder.toString();
			System.out.println(command);
			Commandline commandline = new Commandline(command);
			commandline.setWorkingDirectory(configPath);
			System.out.println(" Executing............");
			Process process = commandline.execute();
			String line = "";
			StringBuilder errorBuilder = new StringBuilder();
			boolean errorOccured = false;
			rd = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = rd.readLine())!= null) {
				if (line.startsWith("Id")) {
					String[] splits = line.split(":" , 2);
					transactionId = splits[1];
					}
				if (line.startsWith(PUBLICATION_ERROR) || line.startsWith("Warning") || errorOccured ) {
					errorBuilder.append(line);
					errorOccured = true;
				}
			}
			if (errorOccured) {
				throw new PhrescoException(errorBuilder.toString());
			}
		}
		catch (CommandLineException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
		return transactionId;
	}
	
	
	public static Map<String, String> getPublishQueue(String server, String username, String password, String publishSiteId) throws PhrescoException {
		BufferedReader rd = null;
		Map<String, String> publicationQueue = new HashMap<String, String>();
		try {
			StringBuilder builder = new  StringBuilder();
			builder.append(POWER_SHELL);
			builder.append(SPACE);
			builder.append(INPUTFORMAT_NONE);
			builder.append(SPACE);
			builder.append(EXECUTION_POLICY_BYPASS);
			builder.append(SPACE);
			builder.append(PUBLICATION_COMMAND);
			builder.append(SPACE);
			builder.append(PUBLICATION_CONSTANT);
			builder.append(SPACE);
			builder.append(configPath.getPath());
			builder.append(File.separator);
			builder.append(PUBLICATION_SCRIPT);
			builder.append(SPACE);
			builder.append("Get-PublishingQueue");
			builder.append(SPACE);
			builder.append("\"" + server + "\"");
			builder.append(SPACE);
			builder.append("\"" + username + "\"");
			builder.append(SPACE);
			builder.append("\"" + password + "\"");
			builder.append(SPACE);
			builder.append("\"" + publishSiteId + "\"");
			builder.append(PUBLICATION_CLOSE);
			
			String command = builder.toString();
			System.out.println(command);
			Commandline commandline = new Commandline(command);
			commandline.setWorkingDirectory(configPath);
			System.out.println(" Executing............");
			Process process = commandline.execute();
			String line = "";
			StringBuilder errorBuilder = new StringBuilder();
			boolean errorOccured = false;
			rd = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = rd.readLine())!= null) {
				System.out.println(line);
				if (line.contains("State")) {
					Map<String, String> populateStatus = populateStatus();
					String[] split = line.split(":");
					for (String string : split) {
						if(string.trim().contains("State") && string.trim().equals("State")) {
							publicationQueue.put("state",populateStatus.get(StringUtils.strip(split[1])));
						}
						if(string.trim().contains("StateChangeDate") && string.trim().equals("StateChangeDate")) {
							publicationQueue.put("Time", split[1]);
						}
					}
				} else if (line.contains("Publication")) {
					String[] split = line.split(":");
					for (String string : split) {
						if(string.trim().contains("Publication") && string.trim().equals("Publication")) {
							publicationQueue.put("Publication", split[1]);
						}
						if(string.trim().contains("PublicationTarget") && string.trim().equals("PublicationTarget")) {
							publicationQueue.put("target", split[1]);
						}
					}
				} else if (line.startsWith("ItemPath")) {
					String[] splits = line.split(":" , 2);
					String itemPath = splits[1];
					publicationQueue.put("Path", itemPath);
				} else if (line.startsWith("Action")) {
					String[] splits = line.split(":");
					if (StringUtils.strip(splits[1]).equals("0")) {
						publicationQueue.put("Action", "Publish");
					} else {
						publicationQueue.put("Action", "UnPublish");
					}
				} else if (line.startsWith("Priority")) {
					String[] splits = line.split(":" , 2);
					String Priority = splits[1];
					publicationQueue.put("Priority", Priority);
				}  else if (line.startsWith("User")) {
					String[] splits = line.split(":" , 2);
					String user = splits[1];
					publicationQueue.put("User", user);
				} else if (line.startsWith("Title")) {
					String[] splits = line.split(":" , 2);
					String name = splits[1];
					publicationQueue.put("Name", name);
				} else if (line.startsWith(PUBLICATION_ERROR) || line.startsWith("Warning") || errorOccured ) {
					errorBuilder.append(line);
					errorOccured = true;
				}
			}
			if (errorOccured) {
				throw new PhrescoException(errorBuilder.toString());
			}
		}
		catch (CommandLineException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (rd != null) {
					rd.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
		return publicationQueue;
	}
	
	private static Map<String, String> populateStatus() {
		statusMap.put("1", "Scheduled");
		statusMap.put("2", "Waiting for Publish");
		statusMap.put("3", "In Progress");
		statusMap.put("4", "Waiting");
		statusMap.put("5", "Failed");
		statusMap.put("6", "Waiting for Deployment");
		statusMap.put("7", "In Progress");
		statusMap.put("8", "Transport is not successfull");
		statusMap.put("9", "Deployment is Successfull");
		statusMap.put("11", "Ready for Transport");
		return statusMap;
	}
	private static Map<String, String> populateAction() {
		actionMap.put("0", "Publish");
		actionMap.put("1", "UnPublish");
		return actionMap;
	}

	/**
	 * Update the TcmID
	 * @param name
	 * @param tcmId
	 * @param environmentName
	 */
	private static void updateTcmId(String name, String tcmId, String environmentName) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
			File path = new File(configPath + File.separator + PUBLICATION_CONFIG_FILE);
			Document doc = documentBuilder.parse(path);
			StringBuilder expBuilder = new StringBuilder();
			expBuilder.append("/Publications/publication[@name='"); 
			expBuilder.append(name);
			expBuilder.append("']");	
			expBuilder.append("/target/environment");
			
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath newXPath = xPathFactory.newXPath();

			XPathExpression xPathExpression = newXPath.compile(expBuilder.toString());
			Node node = (Node) xPathExpression.evaluate(doc, XPathConstants.NODE);
			if (node != null) {
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element environmentElement = (Element) node; 
					environmentElement.setAttribute(NAME, environmentName);
					environmentElement.setAttribute(TCM_ID, tcmId);
				}
			}
			
			StringBuilder expBuilder1 = new StringBuilder();
			expBuilder1.append("/Publications/PublicationTransaction/environment[@name='"); 
			expBuilder1.append(environmentName);
			expBuilder1.append("']");	
			XPathFactory xPathFactory1 = XPathFactory.newInstance();
			XPath newXPath1 = xPathFactory1.newXPath();

			XPathExpression xPathExpression1 = newXPath1.compile(expBuilder1.toString());
			Node node1 = (Node) xPathExpression1.evaluate(doc, XPathConstants.NODE);
			if (node1 != null) {
				if (node1.getNodeType() == Node.ELEMENT_NODE) {
					Element PublicationTransaction = (Element) node1;
					String id = PublicationTransaction.getAttribute("tcmId");
					if (StringUtils.isNotEmpty(id)) {
						PublicationTransaction.setAttribute("tcmId", tcmId);
					}
				}
			} else {
				StringBuilder expBuilder2 = new StringBuilder();
				expBuilder2.append("//Publications//PublicationTransaction"); 
				
				XPathFactory xPathFactory2 = XPathFactory.newInstance();
				XPath newXPath2 = xPathFactory2.newXPath();

				XPathExpression xPathExpression2 = newXPath2.compile(expBuilder2.toString());
				Node node2 = (Node) xPathExpression2.evaluate(doc, XPathConstants.NODE);
				
				Element createElement = doc.createElement("environment");
				createElement.setAttribute("name", environmentName);
				createElement.setAttribute("tcmId", tcmId);
				node2.appendChild(createElement);
				
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
			transformer.setOutputProperty(OutputKeys.INDENT, YES);

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(path.toString());
			transformer.transform(source, result);
			
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public String getPublicationSiteId(String envname) throws PhrescoException {
		String attribute = "";
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
			File virtualConfigFile = new File(configPath + File.separator + PUBLICATION_CONFIG_FILE);

			Document doc = documentBuilder.parse(virtualConfigFile);
			StringBuilder expBuilder = new StringBuilder();
			expBuilder.append("/Publications/PublicationTransaction/environment[@name='"); 
			expBuilder.append(envname);
			expBuilder.append("']");	

			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath newXPath = xPathFactory.newXPath();

			XPathExpression xPathExpression = newXPath.compile(expBuilder.toString());
			Node node = (Node) xPathExpression.evaluate(doc, XPathConstants.NODE);
			if (node != null) {
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element ele = (Element) node; 
					attribute = ele.getAttribute(TCM_ID);
					if (StringUtils.isNotEmpty(attribute)) {
						return attribute;
					}
				}
			}
		} catch (XPathExpressionException e) {
			throw new PhrescoException(e);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
		return attribute;

	}

	/**
	 * Create the Virtual Config File
	 * @param type
	 * @throws PhrescoException 
	 */
	public static void createVirtualConfigFile(String type) throws PhrescoException {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
			File virtualConfigFile = new File(configPath + File.separator + PUBLICATION_VIRTUAL_CONFIG_FILE);
			if (!virtualConfigFile.exists() || virtualConfigFile.length() == 0) {
				return;
			}
			Document doc = documentBuilder.parse(virtualConfigFile);
			StringBuilder expBuilder = new StringBuilder();
			expBuilder.append("/Publications/publication[@type='"); 
			expBuilder.append(type);
			expBuilder.append("']");	
			expBuilder.append("/target/environment");
			
			Element publications = (Element) doc.getFirstChild();

			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath newXPath = xPathFactory.newXPath();

			XPathExpression xPathExpression = newXPath.compile(expBuilder.toString());
			Node node = (Node) xPathExpression.evaluate(doc, XPathConstants.NODE);
			if (node != null) {
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element ele = (Element) node; 
					String attribute = ele.getAttribute(TCM_ID);
					if (StringUtils.isNotEmpty(attribute)) {
						publications.removeChild(node.getParentNode().getParentNode());
					}
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
			transformer.setOutputProperty(OutputKeys.INDENT, YES);

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(virtualConfigFile.toString());
			transformer.transform(source, result);
			
			
		} catch (XPathExpressionException e) {
			throw new PhrescoException(e);
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
	
	/**
	 * Clone the Publication
	 * @param environmentName
	 * @param type
	 * @throws PhrescoException
	 */
	public static void clonePublication(String environmentName, String type) throws PhrescoException {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
			File virtualConfigFile = new File(configPath + File.separator + PUBLICATION_VIRTUAL_CONFIG_FILE);
			
			Document doc = documentBuilder.parse(virtualConfigFile);
			StringBuilder expBuilder = new StringBuilder();
			expBuilder.append("/Publications/publication[@type='"); 
			expBuilder.append(type);
			expBuilder.append("']");	
			expBuilder.append("/target/environment");

			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath newXPath = xPathFactory.newXPath();

			XPathExpression xPathExpression = newXPath.compile(expBuilder.toString());
			Node node = (Node) xPathExpression.evaluate(doc, XPathConstants.NODE);
			if (node != null) {
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node; 
					element.setAttribute(TCM_ID, "");
					element.setAttribute(NAME, environmentName);
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
			transformer.setOutputProperty(OutputKeys.INDENT, YES);

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(virtualConfigFile.toString());
			transformer.transform(source, result);
			
			
		} catch (XPathExpressionException e) {
			throw new PhrescoException(e);
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
	
	/**
	 * Check the Content and Website 
	 * @param virtualConfigFile
	 * @return
	 * @throws PhrescoException
	 */
	public boolean checkContentAndSite(File virtualConfigFile) throws PhrescoException {
		boolean publicationExists = false;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();

			Document doc = documentBuilder.parse(virtualConfigFile);

			List<String> publicationsTypes = new ArrayList<String>();
			publicationsTypes.add(CONTENT);
			publicationsTypes.add(WEBSITE);

			for (String type : publicationsTypes) {
				StringBuilder expBuilder = new StringBuilder();
				expBuilder.append("/Publications/publication[@type='"); 
				expBuilder.append(type);
				expBuilder.append("']");	

				XPathFactory xPathFactory = XPathFactory.newInstance();
				XPath newXPath = xPathFactory.newXPath();

				XPathExpression xPathExpression = newXPath.compile(expBuilder.toString());
				Node node = (Node) xPathExpression.evaluate(doc, XPathConstants.NODE);
				if (node != null) {
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;
						String name = element.getAttribute(NAME);
						if (StringUtils.isNotEmpty(name)) {
							publicationExists = true;
						} else {
							publicationExists = false;
						}
					}
				}
			}

		} catch (XPathExpressionException e) {
			throw new PhrescoException(e);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
		return publicationExists;
	}

	/**
	 * Get the Authentication Details
	 * @param dotPhrescoFolderPath
	 * @return
	 * @throws PhrescoException
	 */
	public static Map<String, String> getAuthenticationDetails(String dotPhrescoFolderPath, String envName) throws PhrescoException  {
		Map<String, String> credentials = new HashMap<String, String>();	
		try {
			File configFile = new File(dotPhrescoFolderPath, CONFIGURATION_INFO_FILE_NAME );
			ConfigurationReader configReader = new ConfigurationReader(configFile);
			List<Environment> allEnvironments = configReader.getAllEnvironments();
			String environmentName = "";
			boolean devEnvironmentExists = false;
			if (CollectionUtils.isNotEmpty(allEnvironments)) {
				for (Environment environment : allEnvironments) {
					if(StringUtils.isNotEmpty(envName) && envName.equalsIgnoreCase(environment.getName())) {
						environmentName = environment.getName();
						devEnvironmentExists = true;
					} else if(environment.isDefaultEnv()) {
						environmentName = environment.getName();
						devEnvironmentExists = true;
					}
				}
			}
			if (!devEnvironmentExists) {
				throw new PhrescoException(" No environment configuration found - authentication missing ");
			}
			List<Configuration> configs = configReader.getConfigurations(environmentName, REQ_DEPLOY_SERVER);
			for (Configuration configuration : configs) {
				Properties properties = configuration.getProperties();
				String server = (String) properties.get(SERVER_HOST);
				String username = (String) properties.get(ADMIN_USERNAME);
				String password = (String) properties.get(ADMIN_PASSWORD);
				credentials.put(CMS_USERNAME, username);
				credentials.put(CMS_PASSWORD, password);
				credentials.put(CMS_SERVER, server);	
				credentials.put(ENVIRONMENT_NAME, environmentName);	
			}
		} catch (Exception e) { 
			throw new PhrescoException(e);
		}
		return credentials;
	}

	public String updateTransactionId(String transactionId, String environmentName) throws PhrescoException {
		String transactionIds = "";
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
			File path = new File(configPath + File.separator + PUBLICATION_CONFIG_FILE);
			Document doc = documentBuilder.parse(path);

			StringBuilder expBuilder1 = new StringBuilder();
			expBuilder1.append("/Publications/Publishedtransaction/environment[@name='"); 
			expBuilder1.append(environmentName);
			expBuilder1.append("']");	
			XPathFactory xPathFactory1 = XPathFactory.newInstance();
			XPath newXPath1 = xPathFactory1.newXPath();

			XPathExpression xPathExpression1 = newXPath1.compile(expBuilder1.toString());
			Node node1 = (Node) xPathExpression1.evaluate(doc, XPathConstants.NODE);
			if (node1 != null) {
				if (node1.getNodeType() == Node.ELEMENT_NODE) {
					Element PublicationTransaction = (Element) node1;
					String id = PublicationTransaction.getAttribute("tcmId");
					if (StringUtils.isNotEmpty(id)) {
						PublicationTransaction.setAttribute("tcmId", transactionId);
					}
				}
			} else {
				StringBuilder newExpressBuilder = new StringBuilder();
				newExpressBuilder.append("//Publications//Publishedtransaction"); 

				XPathFactory newxPathFactory = XPathFactory.newInstance();
				XPath newXPaths = newxPathFactory.newXPath();

				XPathExpression xPathExpression2 = newXPaths.compile(newExpressBuilder.toString());
				Node node2 = (Node) xPathExpression2.evaluate(doc, XPathConstants.NODE);

				Element createElement = doc.createElement("environment");
				createElement.setAttribute("name", environmentName);
				createElement.setAttribute("tcmId", transactionId);
				node2.appendChild(createElement);

			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
			transformer.setOutputProperty(OutputKeys.INDENT, YES);

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(path.toString());
			transformer.transform(source, result);

		} catch (XPathExpressionException e) {
			throw new PhrescoException(e);
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
		return transactionIds;
	}

	public Map<String, String> checkPublished(String environmentName) throws PhrescoException {
		Map<String, String> transactionDetails = new HashMap<String, String>();
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
			File path = new File(configPath + File.separator + PUBLICATION_CONFIG_FILE);
			Document doc = documentBuilder.parse(path);
			StringBuilder expBuilder = new StringBuilder();
			expBuilder.append("/Publications/Publishedtransaction/environment[@name='"); 
			expBuilder.append(environmentName);
			expBuilder.append("']");	
			
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath newXPath = xPathFactory.newXPath();
			
			XPathExpression xPathExpression = newXPath.compile(expBuilder.toString());
			Node node = (Node) xPathExpression.evaluate(doc, XPathConstants.NODE);
			if (node != null) {
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String id = element.getAttribute("tcmId");
					transactionDetails.put("tcmId", id);
					transactionDetails.put("envName", environmentName);
				}
				
			}
		} catch (XPathExpressionException e) {
			throw new PhrescoException(e);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
		return transactionDetails;
	}
	
	public Map<String, String> checkPublication() throws PhrescoException {
		Map<String, String> transactionDetails = new HashMap<String, String>();
		List<String> envNames = new ArrayList<String>();
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
			File path = new File(configPath + File.separator + PUBLICATION_CONFIG_FILE);
			Document doc = documentBuilder.parse(path);
			StringBuilder expBuilder = new StringBuilder();
			expBuilder.append("//Publications//PublicationTransaction"); 

			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath newXPath = xPathFactory.newXPath();

			XPathExpression xPathExpression = newXPath.compile(expBuilder.toString());
			Node node = (Node) xPathExpression.evaluate(doc, XPathConstants.NODE);
			if (node != null) {
				NodeList childNodes = node.getChildNodes();
				for (int temp = 0; temp < childNodes.getLength(); temp++) {
					Node nNode = childNodes.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element ele = (Element) nNode;
						String name = ele.getAttribute("name");
						envNames.add(name);
					}
				}
				String names = "";
				if (CollectionUtils.isNotEmpty(envNames)) {
					for (String string : envNames) {
						names = string + "," + names; 
					}
					if (StringUtils.isNotEmpty(names)) {
						String val = names.substring(0, names.length()-1);
						transactionDetails.put("envName", val);
					}
				}
			}
		} catch (XPathExpressionException e) {
			throw new PhrescoException(e);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
		return transactionDetails;
	}

}
