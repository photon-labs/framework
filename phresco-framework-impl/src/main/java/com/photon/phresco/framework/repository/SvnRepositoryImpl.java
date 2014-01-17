package com.photon.phresco.framework.repository;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.api.RepositoryManager;
import com.photon.phresco.framework.impl.util.FrameworkUtil;
import com.phresco.pom.util.PomProcessor;
import com.photon.phresco.util.Utility;
import java.io.File;
import org.apache.commons.lang.StringUtils;
import com.phresco.pom.exception.PhrescoPomException;

public class SvnRepositoryImpl implements RepositoryManager, FrameworkConstants {

	public  Document getSource(String appDirName, String username, String password, String srcRepoUrl) throws PhrescoException {
		List<String> documents = new ArrayList<String>();
		String repoUrl = "";
		Document document = null;
		try {
			document = getSvnSourceRepo(username, password, srcRepoUrl, appDirName);
		} 
		catch (PhrescoException e) {
			throw new PhrescoException(e);
		} 
		return document;
	}
	
	public Document getSvnSourceRepo(String username, String password, String url, String appDirName) throws PhrescoException {
		Document document = null;
		try {
			Map<String, List<String>> paths = new HashMap<String, List<String>>();
			if (url.endsWith(TRUNK) || url.endsWith(TRUNK + FrameworkConstants.FORWARD_SLASH)) {
				url = url.substring(0, url.lastIndexOf(TRUNK));
			}
			String trunkUrl = url + TRUNK;
			List<String> trunks = new ArrayList<String>();
			List<String> trunksList = getSvnData(trunkUrl, trunks, username, password);
			paths.put(TRUNK, trunksList);

			String branchUrl = url + BRANCHES;
			List<String> branches = new ArrayList<String>();
			List<String> branchesList = getSvnData(branchUrl, branches, username, password);
			paths.put(BRANCHES, branchesList);

			String tagUrl = url + TAGS;
			List<String> tags = new ArrayList<String>();
			List<String> tagsList = getSvnData(tagUrl, tags, username, password);
			paths.put(TAGS, tagsList);
			document = constructSvnTree(paths, url, appDirName);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
		return document;
	}
	
	private static Document constructSvnTree(Map<String , List<String>> list,  String url, String appDirName) throws PhrescoException {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document doc = documentBuilder.newDocument();

			Element rootElement = doc.createElement(ROOT);
			doc.appendChild(rootElement);

			Element rootItem = doc.createElement(ITEM);
			rootItem.setAttribute(TYPE, FOLDER);
			rootItem.setAttribute(NAME, ROOT_ITEM);

			rootElement.appendChild(rootItem);

			Element urlItem = doc.createElement(ITEM);
			urlItem.setAttribute(TYPE, FOLDER);
			urlItem.setAttribute(NAME, url);

			rootItem.appendChild(urlItem);

			Element branchItem = doc.createElement(ITEM);
			branchItem.setAttribute(TYPE, FOLDER);
			branchItem.setAttribute(NAME, BRANCHES);
			branchItem.setAttribute(URL, url);
			urlItem.appendChild(branchItem);

			Element tagItem = doc.createElement(ITEM);
			tagItem.setAttribute(TYPE, FOLDER);
			tagItem.setAttribute(NAME, TAGS);
			tagItem.setAttribute(URL, url);
			urlItem.appendChild(tagItem);


			Element trunkItem = doc.createElement(ITEM);
			trunkItem.setAttribute(TYPE, FOLDER);
			trunkItem.setAttribute(NAME, TRUNK);
			trunkItem.setAttribute(NATURE, TRUNK);
			trunkItem.setAttribute(URL, url);
			trunkItem.setAttribute(REQ_APP_DIR_NAME, appDirName);
			urlItem.appendChild(trunkItem);
	

			List<String> branchList = list.get(BRANCHES);
			for (String branch: branchList) {
				Element branchItems = doc.createElement(ITEM);
				branchItems.setAttribute(NAME, branch);
				branchItems.setAttribute(URL, url);
				branchItems.setAttribute(REQ_APP_DIR_NAME, appDirName);
				branchItems.setAttribute(NATURE, BRANCHES);
				branchItem.appendChild(branchItems);
			}

			List<String> tagList = list.get(TAGS);
			for (String tag: tagList) {
				Element tagItems = doc.createElement(ITEM);
				tagItems.setAttribute(NAME, tag);
				tagItems.setAttribute(URL, url);
				tagItems.setAttribute(REQ_APP_DIR_NAME, appDirName);
				tagItems.setAttribute(NATURE, TAGS);
				tagItem.appendChild(tagItems);
			}

			return doc;
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		}
	}
	
	private List<String> getSvnData(String url, List<String> paths, String username, String password) throws PhrescoException {
		try {
			getSVNClientManager(username, password);
			SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
			repository.setAuthenticationManager(authManager);
			SVNNodeKind nodeKind = repository.checkPath("", -1);
			if (nodeKind == SVNNodeKind.NONE) {
				return paths;
			}
			Collection entries = repository.getDir("", -1, null, (Collection) null);
			SVNURL svnURL = getSVNURL(url);
			Iterator iterator = entries.iterator();
			if (entries.size() != 0) {
				while (iterator.hasNext()) {
					SVNDirEntry entry = (SVNDirEntry) iterator.next();
					if ((entry.getKind() == SVNNodeKind.DIR)) {
						SVNURL svnnewURL = svnURL.appendPath("/" + entry.getName(), true);
						String urls = svnnewURL.toString();
						String path = urls.substring(urls.lastIndexOf("/")+1, urls.length());
						paths.add(path);
					}
				}
			}
		} catch (SVNException e) {
			throw new PhrescoException("url", url);
		}
		return paths;
	}
	
	private static SVNClientManager getSVNClientManager(String userName, String password) {
		DAVRepositoryFactory.setup();
		DefaultSVNOptions options = new DefaultSVNOptions();
		return SVNClientManager.newInstance(options, userName, password);
	}

	private static SVNURL getSVNURL(String repoURL) throws PhrescoException { 
		SVNURL svnurl = null;
		try {
			svnurl = SVNURL.parseURIEncoded(repoURL);
		} catch (SVNException e) {
			throw new PhrescoException(e);
		}
		return svnurl;
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
	

}
