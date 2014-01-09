package com.photon.phresco.framework.impl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.CIJob;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.framework.model.RepoDetail;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.util.PomProcessor;

public class FrameworkUtil implements Constants, FrameworkConstants{
	
	public static Document getDocument(File file) throws PhrescoException {
		InputStream fis = null;
        DocumentBuilder builder = null;
        try {
            fis = new FileInputStream(file);
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(false);
            builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(fis);
            return doc;
        } catch (FileNotFoundException e) {
        	throw new PhrescoException(e);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
        	if (fis != null) {
        		try {
					fis.close();
				} catch (IOException e) {
					throw new PhrescoException(e);
				}
            }
        }
	}
	
	/**
	 * Gets the port no.
	 *
	 * @param path the path
	 * @return the port no
	 * @throws PhrescoException the phresco exception
	 */
	public static String getJenkinsPortNo() throws PhrescoException {
		
		String portNo = "";
		try {
			String jenkinsHome = Utility.getJenkinsHome();
			StringBuilder path = new StringBuilder(jenkinsHome);
			Document document = getDocument(new File(path.toString() + File.separator + POM_FILE));
			String portNoNode = CI_TOMCAT_HTTP_PORT;
			NodeList nodelist = org.apache.xpath.XPathAPI.selectNodeList(document, portNoNode);
			portNo = nodelist.item(0).getTextContent();
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return portNo;
	}

	public static String getJenkinsPOMFilePath() throws PhrescoException {
		String jenkinsHome = Utility.getJenkinsHome();
		StringBuilder path = new StringBuilder(jenkinsHome);
		path.append(File.separator + POM_FILE);
		return path.toString();
	}

	private static String getPropertyValue(String propName)
			throws PhrescoException {
		String jenkinsPOMFilePath = getJenkinsPOMFilePath();
		File jenkinsPOMFile = new File(jenkinsPOMFilePath);
		if (!jenkinsPOMFile.exists()) {
			throw new PhrescoException("Jenkins pom.xml is not available");
		}
		try {
			PomProcessor pomProcessor = new PomProcessor(jenkinsPOMFile);
			return pomProcessor.getProperty(propName);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	public static String getJenkinsHost() throws PhrescoException {
		try {
			String jenkinsUrl = getJenkinsUrl();
			URL jenkinsURL = new URL(jenkinsUrl);
			return jenkinsURL.getHost();
		} catch (MalformedURLException e) {
			throw new PhrescoException("Jenkins host url is malformed url");
		}
	}

	public static String getJenkinsPort() throws PhrescoException {
		try {
			String jenkinsUrl = getJenkinsUrl();
			URL jenkinsURL = new URL(jenkinsUrl);
			return Integer.toString(jenkinsURL.getPort());
		} catch (MalformedURLException e) {
			throw new PhrescoException("Jenkins port url is malformed url");
		}
	}

	public static String getJenkinsProtocol() throws PhrescoException {
		try {
			String jenkinsUrl = getJenkinsUrl();
			URL jenkinsURL = new URL(jenkinsUrl);
			return jenkinsURL.getProtocol();
		} catch (MalformedURLException e) {
			throw new PhrescoException("Jenkins protocol url is malformed url");
		}
	}

	public static String getJenkinsPath() throws PhrescoException {
		try {
			String jenkinsUrl = getJenkinsUrl();
			URL jenkinsURL = new URL(jenkinsUrl);
			return jenkinsURL.getPath();
		} catch (MalformedURLException e) {
			throw new PhrescoException("Jenkins path url is malformed url");
		}
	}

	public static String getJenkinsUrl() throws PhrescoException {
		String url = getPropertyValue(URL);
		if (StringUtils.isNotEmpty(url)) {
			return url;
		}
		StringBuilder jenkinsUrl = new StringBuilder(getJenkinsDefaultProtocol());
		jenkinsUrl.append(PROTOCOL_POSTFIX);
		jenkinsUrl.append(getJenkinsDefaultHost());
		jenkinsUrl.append(COLON);
		jenkinsUrl.append(getJenkinsDefaultPort());
		jenkinsUrl.append(FORWARD_SLASH);
		jenkinsUrl.append(getJenkinsDefaultPath());
		return jenkinsUrl.toString();
	}
	
	public static String getLocaJenkinsUrl() throws PhrescoException {
		StringBuilder jenkinsUrl = new StringBuilder(getJenkinsDefaultProtocol());
		jenkinsUrl.append(PROTOCOL_POSTFIX);
		jenkinsUrl.append(getJenkinsDefaultHost());
		jenkinsUrl.append(COLON);
		jenkinsUrl.append(getJenkinsDefaultPort());
		jenkinsUrl.append(FORWARD_SLASH);
		jenkinsUrl.append(getJenkinsDefaultPath());
		return jenkinsUrl.toString();
	}

	public static String getJenkinsUrl(String protocol, String host,
			String port, String context) throws PhrescoException {
		StringBuilder jenkinsUrl = new StringBuilder(protocol);
		jenkinsUrl.append(PROTOCOL_POSTFIX);
		jenkinsUrl.append(host);
		jenkinsUrl.append(COLON);
		jenkinsUrl.append(port);
		jenkinsUrl.append(FORWARD_SLASH);
		jenkinsUrl.append(context);
		return jenkinsUrl.toString();
	}

	public static String getJenkinsUrl(CIJob job) throws PhrescoException {
		String protocol = job.getJenkinsProtocol();
		StringBuilder jenkinsUrl = new StringBuilder();
		jenkinsUrl.append(protocol);
		jenkinsUrl.append(PROTOCOL_POSTFIX);
		jenkinsUrl.append(job.getJenkinsUrl());
		jenkinsUrl.append(COLON);
		jenkinsUrl.append(job.getJenkinsPort());
		String path = job.getJenkinsPath();
		jenkinsUrl.append(path);
		return jenkinsUrl.toString();
	}

	public static String getJenkinsUsername() throws PhrescoException {
		return getPropertyValue("username");
	}

	public static String getJenkinsPassword() throws PhrescoException {
		return getPropertyValue("password");
	}

	public static int getJenkinsDefaultPort() throws PhrescoException {
		try {
			String jenkinsPortNo = getJenkinsPortNo();
			return Integer.parseInt(jenkinsPortNo);
		} catch (Exception e) {
			throw new PhrescoException("Jenkins default port value can not be a string");
		}
	}

	public static String getJenkinsDefaultPath() throws PhrescoException {
		return CI;
	}

	public static String getJenkinsDefaultHost() throws PhrescoException {
		try {
			InetAddress thisIp = InetAddress.getLocalHost();
			return thisIp.getHostAddress();
			// return LOCALHOST;
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	public static String getJenkinsDefaultProtocol() throws PhrescoException {
		return HTTP_PROTOCOL;
	}
	
	public static String convertDocumentToString(Document doc) throws PhrescoException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString();
			return output;
		} catch (TransformerException e) {
			throw new PhrescoException(e);
		}
	}
	
	public static List<ApplicationInfo> getAppInfos(String customerId,
			String projectId) throws PhrescoException {
		try {
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			ProjectInfo projectInfo = projectManager.getProject(projectId,
					customerId);
			if (projectInfo != null) {
				return projectInfo.getAppInfos();
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
		return new ArrayList<ApplicationInfo>();
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	public static Map<String, String> getCredential(String url) throws PhrescoException {
		Map<String, String> credentialMap = new HashMap<String, String>();
		try {
			File userPath = new File(Utility.getPhrescoTemp() + File.separator + CREADENTIAL_JSON);
			if (!userPath.exists()) {
				return null;
			}
			JSONObject userjson = null;
			JSONParser parser = new JSONParser();
			if (userPath.exists()) {
				FileReader reader = new FileReader(userPath);
				userjson = (JSONObject)parser.parse(reader);
				reader.close();
			} else {
				userjson = new JSONObject();
			}
			JSONObject credential = (JSONObject) userjson.get(url);
			credentialMap = getCredentialMap(credential);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (ParseException e) {
			throw new PhrescoException(e);
		}
		return credentialMap;
	}

	@SuppressWarnings("unchecked")
	public static void saveCredential(String url, String username, String decrypted) throws PhrescoException {
		try {
			File userPath = new File(Utility.getPhrescoTemp() + File.separator + CREADENTIAL_JSON);
			JSONObject userjson = null;
			JSONParser parser = new JSONParser();
			if (userPath.exists()) {
				FileReader reader = new FileReader(userPath);
				userjson = (JSONObject)parser.parse(reader);
				reader.close();
			} else {
				userjson = new JSONObject();
			}
			JSONObject credentialObject = new JSONObject();
			credentialObject.put(username, decrypted);
			userjson.put(url, credentialObject);
			FileWriter writer = new FileWriter(userPath);
			writer.write(userjson.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (ParseException e) {
			throw new PhrescoException(e);
		}
	}
	
	public static  String getEncryptedPassword(String password) throws PhrescoException {
		try {
			String passPharse = PASS_PHARASE;
			SecretKeyFactory factory = SecretKeyFactory.getInstance(DESAL);
			SecretKey key = factory.generateSecret(new DESedeKeySpec(passPharse.getBytes()));
			Cipher cipher = Cipher.getInstance(DESAL);

			cipher.init(Cipher.ENCRYPT_MODE, key);
			@SuppressWarnings("restriction")
			String str = DatatypeConverter.printBase64Binary(cipher.doFinal(password.getBytes()));
			return str;
		} catch (InvalidKeyException e) {
			throw new PhrescoException(e);
		} catch (NoSuchPaddingException e) {
			throw new PhrescoException(e);
		} catch (IllegalBlockSizeException e) {
			throw new PhrescoException(e);
		} catch (BadPaddingException e) {
			throw new PhrescoException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new PhrescoException(e);
		} catch (InvalidKeySpecException e) {
			throw new PhrescoException(e);
		}
	}

	public static  String getdecryptedPassword(String password) throws PhrescoException {
		String str = "";
		try {
			String passPharse = PASS_PHARASE;
			SecretKeyFactory factory = SecretKeyFactory.getInstance(DESAL);
			SecretKey key = factory.generateSecret(new DESedeKeySpec(passPharse.getBytes()));
			Cipher cipher = Cipher.getInstance(DESAL);
			cipher.init(Cipher.DECRYPT_MODE, key);
			str = new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(password)));
		}  catch (NoSuchAlgorithmException e) {
			throw new PhrescoException(e);
		}  catch (InvalidKeyException e) {
			throw new PhrescoException(e);
		} catch (NoSuchPaddingException e) {
			throw new PhrescoException(e);
		} catch (IllegalBlockSizeException e) {
			throw new PhrescoException(e);
		} catch (BadPaddingException e) {
			throw new PhrescoException(e);
		} catch (InvalidKeySpecException e) {
			throw new PhrescoException(e);
		}
		return str;
	}
	
	private static Map<String, String> getCredentialMap(JSONObject credential) {
		Map<String, String> credentialMap = new HashMap<String, String>();
		if (credential != null) {
			Set entrySet = credential.keySet();
			for (Object object : entrySet) {
				String username = (String)object;
				String password = (String) credential.get(username);
				credentialMap.put(REQ_USER_NAME, username);
				credentialMap.put(REQ_PASSWORD, password);
			}
		}
		return credentialMap;
	}
	
	public static void saveCredential(RepoDetail srcRepoDetail, StringBuilder appendedSrcUrl) throws PhrescoException {
		try {
			String encryptedPassword = getEncryptedPassword(srcRepoDetail.getPassword());
			String url = "";
			if (appendedSrcUrl != null)  {
				url = appendedSrcUrl.toString();
			}
			if (StringUtils.isNotEmpty(url)) {
				saveCredential(url, srcRepoDetail.getUserName(), encryptedPassword);
			} else {
				saveCredential(srcRepoDetail.getRepoUrl(), srcRepoDetail.getUserName(), encryptedPassword);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
}
