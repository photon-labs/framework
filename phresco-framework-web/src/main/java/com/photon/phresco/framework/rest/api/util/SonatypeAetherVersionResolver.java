package com.photon.phresco.framework.rest.api.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.PlexusContainerException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.VersionRangeRequest;
import org.sonatype.aether.resolution.VersionRangeResolutionException;
import org.sonatype.aether.resolution.VersionRangeResult;
import org.sonatype.aether.util.DefaultRepositorySystemSession;
import org.sonatype.aether.util.layout.MavenDefaultLayout;
import org.sonatype.aether.version.Version;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class SonatypeAetherVersionResolver implements FrameworkConstants, ServiceConstants {
	
	public static Artifact readArtifact(String appDirName, String version, String moduleName) throws PhrescoException {
		Artifact artifact = null;
		try {
			String appDirPath = Utility.getProjectHome() + appDirName;
			ProjectInfo projectInfo = Utility.getProjectInfo(appDirPath, "");
			File sourceFolderLocation = Utility.getSourceFolderLocation(projectInfo, appDirPath, moduleName);
			File pomFile = new File (sourceFolderLocation, POM_FILE);
			if (pomFile.exists()) {
				PomProcessor processor = new PomProcessor(pomFile);
				if (StringUtils.isNotEmpty(version)) {
					artifact = createArtifact(processor.getGroupId(), processor.getArtifactId(), version, processor.getPackage());
				} else {
					artifact = createArtifact(processor.getGroupId(), processor.getArtifactId(), "", processor.getPackage());
				}
			}
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
		return artifact;
	}
	
	public static Document constructDomSource(PlexusContainer plexusContainer, Artifact artifacts, String appDirName, List<String> urls, String moduleName, Document doc, Element appendItem) throws PhrescoException {
		List<UUID> randomIds = new ArrayList<UUID>();
		try {
			Element snapshot = doc.createElement(ITEM);
			snapshot.setAttribute(TYPE, FOLDER);
			snapshot.setAttribute(PATH, "");
			snapshot.setAttribute(NAME, SNAPSHOT);
			appendItem.appendChild(snapshot);


			Element release = doc.createElement(ITEM);
			release.setAttribute(TYPE, FOLDER);
			release.setAttribute(PATH, "");
			release.setAttribute(NAME, RELEASE);
			appendItem.appendChild(release);
			RepositorySystem system;
			try {
				system = plexusContainer.lookup(org.sonatype.aether.RepositorySystem.class);
			} catch (ComponentLookupException e) {
				e.printStackTrace();
				throw new PhrescoException(e);
			}
			org.sonatype.aether.artifact.Artifact artifact = new 
				org.sonatype.aether.util.artifact.DefaultArtifact(artifacts.getGroupId() + ":" + artifacts.getArtifactId()+ ":" + artifacts.getType() + ":" + "[,)");
			for (String url : urls) {
				UUID randomUUID = UUID.randomUUID();
				randomIds.add(randomUUID);
				RepositorySystemSession session = newRepositorySystemSession( system , randomUUID, "");
				RemoteRepository repo = new RemoteRepository("", DEFAULT, url);
				VersionRangeRequest rangeRequest = new VersionRangeRequest();
				rangeRequest.setArtifact(artifact);
				rangeRequest.addRepository(repo);
				VersionRangeResult rangeResult = system.resolveVersionRange( session, rangeRequest);
				List<Version> versions = rangeResult.getVersions();
				if (versions.size() > 10) {
					versions = versions.subList(versions.size() - 10, versions.size());
				}
				constructArtifactItem(versions, artifact, repo, doc, appDirName, moduleName);
				clearCache(randomUUID);
			}
			urls.clear();
		} catch (DOMException e) {
			throw new PhrescoException(e);
		} catch (VersionRangeResolutionException e) {
			throw new PhrescoException(e);
		} finally {
			if (CollectionUtils.isNotEmpty(randomIds)) {
				for (UUID uuid : randomIds) {
					clearCache(uuid);
				}
			}
		}
		return doc;
	}

	private static void constructArtifactItem(List<Version> versions, org.sonatype.aether.artifact.Artifact artifactInfo, 
			RemoteRepository repo, Document doc, String appDirName, String moduleName) throws PhrescoException {
		try {
			Element versionItem = null;
			Element element =  null;
			if (CollectionUtils.isNotEmpty(versions)) {
				for (Version vers : versions) {
					String version = vers.toString();
					org.sonatype.aether.artifact.Artifact repoArtifact = new 
						org.sonatype.aether.util.artifact.DefaultArtifact(artifactInfo.getGroupId(), artifactInfo.getArtifactId(), artifactInfo.getExtension(), version);
					MavenDefaultLayout defaultLayout = new MavenDefaultLayout();
					URI Paths = defaultLayout.getPath(repoArtifact);
					String artifactPath = Paths.getPath();
					String pathString = Paths.toString();
					String fileName = pathString.substring(pathString.lastIndexOf("/") + 1);

					Element jarItem = doc.createElement(ITEM);
					jarItem.setAttribute(TYPE, FILE);
					jarItem.setAttribute(NAME, fileName);
					jarItem.setAttribute(REQ_APP_DIR_NAME, appDirName);
					jarItem.setAttribute(MODULE_NAME, moduleName);

					versionItem = doc.createElement(ITEM);
					versionItem.setAttribute(TYPE, FOLDER);
					versionItem.setAttribute(NAME, version);
					versionItem.setAttribute(PATH, artifactPath);

					versionItem.appendChild(jarItem);
					String expression = "";
					if (StringUtils.isNotEmpty(moduleName)) {
						expression = ROOT_ITEM_XPATH + moduleName + NAME_FILTER_SUFIX;
					}
					
					if (version.contains(SNAPSHOT)) {
						jarItem.setAttribute(NATURE, SNAPSHOT);
						XPath xpath = XPathFactory.newInstance().newXPath();
						expression = expression + SNAPSHOT_ITEM;
						Node node = (Node) xpath.compile(expression).evaluate(doc, XPathConstants.NODE);
						if (node != null) {
							element = (Element) node;
							element.appendChild(versionItem);
						}
					} else {
						jarItem.setAttribute(NATURE, RELEASE);
						XPath xpath = XPathFactory.newInstance().newXPath();
						expression = expression + RELEASE_ITEM;
						Node node = (Node) xpath.compile(expression).evaluate(doc, XPathConstants.NODE);
						if (node != null) {
							element = (Element) node;
							element.appendChild(versionItem);
						}
					}
				}
			}
		} catch (DOMException e) {
			throw new PhrescoException(e);
		} catch (XPathExpressionException e) {
			throw new PhrescoException(e);
		}
	}

	private static RepositorySystem newRepositorySystem() throws PhrescoException {
		try {
			return new DefaultPlexusContainer().lookup(RepositorySystem.class);
		} catch (ComponentLookupException e) {
			throw new PhrescoException(e);
		} catch (PlexusContainerException e) {
			throw new PhrescoException(e);
		}
	}

	private static RepositorySystemSession newRepositorySystemSession(RepositorySystem system, UUID randomUUID, String localPath) {
		DefaultRepositorySystemSession session = new DefaultRepositorySystemSession();
		 
        LocalRepository localRepo = null;
		if (randomUUID != null) {
			localRepo = new LocalRepository(Utility.getPhrescoTemp() + File.separator + randomUUID);
		} else if (StringUtils.isNotEmpty(localPath)) {
			localRepo = new LocalRepository(localPath);
		}
		session.setLocalRepositoryManager(system.newLocalRepositoryManager(localRepo));
		return session;
	}
	
	private static void clearCache(UUID randomUUID)  throws PhrescoException {
		try {
			File path = new File(Utility.getPhrescoTemp() + File.separator + randomUUID);
			if (path.exists()) {
				FileUtils.deleteDirectory(path);
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}
	
	private static Artifact createArtifact(String groupId, String artifactId, String version, String packaging) {
		return new DefaultArtifact(groupId, artifactId, version, "", packaging, "", null);
	}
	
	public static URI getArtifactPath(Artifact artifact) {
		MavenDefaultLayout defaultLayout = new MavenDefaultLayout();
		org.sonatype.aether.artifact.Artifact createArtifact = new 
			org.sonatype.aether.util.artifact.DefaultArtifact(artifact.getGroupId(), artifact.getArtifactId(), artifact.getType(), artifact.getVersion());
		URI path = defaultLayout.getPath(createArtifact);
		return path;
	}
}
