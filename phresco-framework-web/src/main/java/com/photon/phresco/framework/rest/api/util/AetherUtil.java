package com.photon.phresco.framework.rest.api.util;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.tools.ant.AntClassLoader;
import org.codehaus.plexus.DefaultContainerConfiguration;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusConstants;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.PlexusContainerException;
import org.codehaus.plexus.classworlds.ClassWorld;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.MavenArtifactResolver;
import com.photon.phresco.util.MavenEclipseAetherArtifactResolver;

public class AetherUtil {
	
	private boolean isEclipseAether = false;
	private boolean isSonatypeAether = false;
	private PlexusContainer plexusContainer;
	
	public AetherUtil() throws PhrescoException {
		ClassWorld world = new ClassWorld("plexus.core", Thread.currentThread()
				.getContextClassLoader());
		ClassRealm classRealm = buildClassRealm(
				new File(System.getenv("MAVEN_HOME")), world, Thread
						.currentThread().getContextClassLoader());
		DefaultContainerConfiguration conf = new DefaultContainerConfiguration();
		conf.setContainerConfiguration(PlexusConstants.SCANNING_INDEX)
				.setRealm(classRealm).setClassWorld(world)
				.setClassPathScanning(PlexusConstants.SCANNING_INDEX)
				.setComponentVisibility(PlexusConstants.REALM_VISIBILITY);
		DefaultPlexusContainer plexusContainer;
		try {
			plexusContainer = new DefaultPlexusContainer(conf);
			setPlexusContainer(plexusContainer);
			if (plexusContainer.hasComponent("org.sonatype.aether.RepositorySystem")) {
				isSonatypeAether = true;
			} else if (plexusContainer.hasComponent("org.eclipse.aether.RepositorySystem")) {
				isEclipseAether = true;
			}
		} catch (PlexusContainerException e) {
			throw new PhrescoException(e);
		}
	}
	
	public Artifact getArtifact(String appDirName, String version, String moduleName) throws PhrescoException {
		Artifact artifact = null;
		if (isSonatypeAether) {
			artifact = SonatypeAetherVersionResolver.readArtifact(appDirName, version, moduleName);
		} else if (isEclipseAether) {
			artifact = EclipseAetherVersionResolver.readArtifact(appDirName, version, moduleName);
		}
		return artifact;
	}
	
	public static ClassRealm buildClassRealm(File mavenHome, ClassWorld world,
			ClassLoader parentClassLoader) throws PhrescoException {
		if (mavenHome == null) {
			throw new IllegalArgumentException("mavenHome cannot be null");
		}
		if (!mavenHome.exists()) {
			throw new IllegalArgumentException(
					"mavenHome '"
							+ mavenHome.getPath()
							+ "' doesn't seem to exist on this node (or you don't have sufficient rights to access it)");
		}
		File libDirectory = new File(mavenHome, "lib");
		if (!libDirectory.exists()) {
			throw new IllegalArgumentException(
					mavenHome.getPath()
							+ " doesn't have a 'lib' subdirectory - thus cannot be a valid maven installation!");
		}
		File[] jarFiles = libDirectory.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});
		AntClassLoader antClassLoader = new AntClassLoader(Thread
				.currentThread().getContextClassLoader(), false);
		for (File jarFile : jarFiles) {
			antClassLoader.addPathComponent(jarFile);
		}
		if (world == null) {
			world = new ClassWorld();
		}
		ClassRealm classRealm = new ClassRealm(world, "plexus.core",
				parentClassLoader == null ? antClassLoader : parentClassLoader);
		for (File jarFile : jarFiles) {
			try {
				classRealm.addURL(jarFile.toURI().toURL());
			} catch (MalformedURLException e) {
				throw new PhrescoException(e);
			}
		}
		return classRealm;
	}
	
	public Document constructDomSource(Artifact artifact, String appDirName, 
			List<String> urls, String moduleName, Document document, Element appendItem) throws PhrescoException {
		Document doc = null;
		if (isSonatypeAether) {
			doc = SonatypeAetherVersionResolver.
				constructDomSource(plexusContainer, artifact, appDirName, urls, moduleName, document, appendItem);
		} else if (isEclipseAether) {
			doc = EclipseAetherVersionResolver.
				constructDomSource(plexusContainer, artifact, appDirName, urls, moduleName, document, appendItem);
		}
		return doc;
	}
	
	public URI getArtifactPath(Artifact artifact) {
		URI uri = null;
		if(isSonatypeAether) {
			uri = SonatypeAetherVersionResolver.getArtifactPath(artifact);
		} else if(isEclipseAether) {
			uri = EclipseAetherVersionResolver.getArtifactPath(artifact);
		}
		return uri;
	}
	
	public URL resolveArtifact(String url, String username, String password, List<Artifact> artifacts) throws PhrescoException {
		URL artifactUrl = null;
		if(isSonatypeAether) {
			try {
				artifactUrl = MavenArtifactResolver.resolveSingleArtifact(url, username, password, artifacts);
			} catch (Exception e) {
				throw new PhrescoException(e);
			}
		} else if(isEclipseAether) {
			try {
				artifactUrl = MavenEclipseAetherArtifactResolver.resolveSingleArtifact(url, username, password, artifacts);
			} catch (Exception e) {
				throw new PhrescoException(e);
			}
		}
		return artifactUrl;
	}

	public void setPlexusContainer(PlexusContainer plexusContainer) {
		this.plexusContainer = plexusContainer;
	}

	public PlexusContainer getPlexusContainer() {
		return plexusContainer;
	}
}
