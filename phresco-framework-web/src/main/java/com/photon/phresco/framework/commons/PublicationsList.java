package com.photon.phresco.framework.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
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
import org.xml.sax.SAXException;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.configuration.Configuration;
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
		BufferedReader errorReader = null;
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

	/**
	 * Create the Virtual Config File
	 * @param type
	 */
	public static void createVirtualConfigFile(String type) {
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
	public static Map<String, String> getAuthenticationDetails(String dotPhrescoFolderPath) throws PhrescoException  {
		Map<String, String> credentials = new HashMap<String, String>();	
		try {
			File configFile = new File(dotPhrescoFolderPath, CONFIGURATION_INFO_FILE_NAME );
			ConfigurationReader configReader = new ConfigurationReader(configFile);
			Collection<String> environmentNames = configReader.getEnvironmentNames();
			boolean devEnvironmentExist = false;
			String environmentName = "";
			for (String env : environmentNames) {
				if (env.startsWith(DEV_SMALL) | env.startsWith(DEV_CAPS)) {
					devEnvironmentExist = true;
					environmentName = env;
				} 
			}
			if (!devEnvironmentExist) {
				throw new PhrescoException("Authentication is required and Dev Environment is need before futher proceed ");
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

}
