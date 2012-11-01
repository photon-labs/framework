package com.photon.phresco.framework.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.google.gson.Gson;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.api.SCMManager;
import com.photon.phresco.util.FileUtil;
import com.photon.phresco.util.Utility;
import com.phresco.pom.util.PomProcessor;

public class SCMManagerImpl implements SCMManager, FrameworkConstants {

	private static final Logger S_LOGGER = Logger.getLogger(SCMManagerImpl.class);
	private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();
	SVNClientManager cm = null;

	public boolean importProject(String type, String url, String username,
			String password, String branch, String revision, String projcode)
			throws PhrescoException {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.importProject()");
			S_LOGGER.debug("repoType " + type);
			S_LOGGER.debug("repositoryUrl " + url);
		}
		try {
			SVNURL svnURL = SVNURL.parseURIEncoded(url);
			DAVRepositoryFactory.setup();
			DefaultSVNOptions options = new DefaultSVNOptions();
			cm = SVNClientManager.newInstance(options, username, password);
			if (SVN.equals(type)) {
				if(debugEnabled){
					S_LOGGER.debug("SVN type");
				}
				boolean valid = checkOutFilter(url, username, password, revision, svnURL);
				if(debugEnabled){
					S_LOGGER.debug("Completed");
				}
				return valid;
			} else if (GIT.equals(type)) {
				if(debugEnabled){
					S_LOGGER.debug("GIT type");
				}
				String uuid = UUID.randomUUID().toString();
				File gitImportTemp = new File(Utility.getPhrescoTemp(), uuid);
				if(debugEnabled){
					S_LOGGER.debug("gitImportTemp " + gitImportTemp);
				}
				if (gitImportTemp.exists()) {
					if(debugEnabled){
					S_LOGGER.debug("Empty git directory need to be removed before importing from git ");
					}
					FileUtils.deleteDirectory(gitImportTemp);
				}
				if(debugEnabled){
					S_LOGGER.debug("gitImportTemp " + gitImportTemp);
				}
				importFromGit(url, gitImportTemp, username, password);
				if(debugEnabled){
					S_LOGGER.debug("Validating Phresco Definition");
				}
				boolean valid = cloneFilter(gitImportTemp, url, true);
				if (gitImportTemp.exists()) {
					if(debugEnabled){
					S_LOGGER.debug("Deleting ~Temp");
					}
					FileUtils.deleteDirectory(gitImportTemp);
				}
				if(debugEnabled){
					S_LOGGER.debug("Completed");
				}
				return valid;
			}
		} catch (SVNException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of importProject() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} catch (IOException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of importProject() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} catch (PhrescoException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of importProject() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} catch (Exception e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of importProject() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		}
		return false;
	}

	public boolean updateProject(String type, String url, String username,
			String password, String branch, String revision, String projcode)throws PhrescoException {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.updateproject()");
		}
		try {
			if ("svn".equals(type)) {
				if(debugEnabled){
					S_LOGGER.debug("SVN type");
				}
				DAVRepositoryFactory.setup();
				SVNURL svnURL = SVNURL.parseURIEncoded(url);
				DefaultSVNOptions options = new DefaultSVNOptions();
				cm = SVNClientManager.newInstance(options,
						username, password);
				if(debugEnabled){
					S_LOGGER.debug("update SCM Connection " + url);
					S_LOGGER.debug("userName " + username);
					S_LOGGER.debug("Repo type " + type);
				}
				// updateSCMConnection(projcode, url);
				// revision = HEAD_REVISION.equals(revision) ? revision
				// : revisionVal;
				File updateDir = new File(Utility.getProjectHome(), projcode);
				if(debugEnabled){
					S_LOGGER.debug("updateDir SVN... " + updateDir);
					S_LOGGER.debug("Updating...");
				}
				SVNUpdateClient uc = cm.getUpdateClient();
				uc.doUpdate(updateDir, SVNRevision.parse(revision),
						SVNDepth.UNKNOWN, true, true);
				if(debugEnabled){
					S_LOGGER.debug("Updated!");
				}
				return true;
			} else if ("git".equals(type)) {
				if(debugEnabled){
					S_LOGGER.debug("GIT type");
					S_LOGGER.debug("update SCM Connection " + url);
					S_LOGGER.debug("userName " + username);
					S_LOGGER.debug("Repo type " + type);
				}
				updateSCMConnection(projcode, url);
				File updateDir = new File(Utility.getProjectHome(), projcode); 
				if(debugEnabled){
					S_LOGGER.debug("Updating...");
					S_LOGGER.debug("updateDir GIT... " + updateDir);
				}
				Git git = Git.open(updateDir); // checkout is the folder with .git
				git.pull().call(); // succeeds
				if(debugEnabled){
					S_LOGGER.debug("Updated!");
				}
				return true;
			}
		} catch (SVNException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of updateProject() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} catch (IOException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of updateProject() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
			// Below Exceptions for GIT command
		} catch (WrongRepositoryStateException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of updateProject() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} catch (InvalidConfigurationException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of updateProject() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} catch (DetachedHeadException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of updateProject() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} catch (InvalidRemoteException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of updateProject() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} catch (CanceledException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of updateProject() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} catch (RefNotFoundException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of updateProject() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} catch (NoHeadException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of updateProject() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} catch (TransportException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of updateProject() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} catch (GitAPIException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of updateProject() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		}
		return false;
	}

	private void importToWorkspace(File gitImportTemp, String projectHome,String code) throws PhrescoException {
		try {
			if(debugEnabled){
				S_LOGGER.debug("Entering Method  SCMManagerImpl.importToWorkspace()");
			}
			File workspaceProjectDir = new File(projectHome + code);
			if(debugEnabled){
				S_LOGGER.debug("workspaceProjectDir " + workspaceProjectDir);
			}
			if (workspaceProjectDir.exists()) {
				if(debugEnabled){
					S_LOGGER.debug("workspaceProjectDir exists "+ workspaceProjectDir);
				}
				throw new PhrescoException("File already exists in workspace");
			}
			if(debugEnabled){
				S_LOGGER.debug("Copyin from Temp to workspace...");
				S_LOGGER.debug("gitImportTemp ====> " + gitImportTemp);
				S_LOGGER.debug("workspaceProjectDir ====> " + workspaceProjectDir);
			}
			FileUtils.copyDirectory(gitImportTemp, workspaceProjectDir);
			if(debugEnabled){
				S_LOGGER.debug("Deleting pack file");
			}
			FileUtils.deleteDirectory(gitImportTemp);
		} catch (IOException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of importToWorkspace() "+ e.getLocalizedMessage());
				S_LOGGER.error("pack file is not deleted ");
			}
			throw new PhrescoException(e.getLocalizedMessage());
		}
	}

	private void updateSCMConnection(String projCode, String repoUrl)throws PhrescoException {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.updateSCMConnection()");
		}
		try {
			PomProcessor processor = getPomProcessor(projCode);
			if (processor.getSCM() == null) {
				if(debugEnabled){
					S_LOGGER.debug("processor.getSCM() exists and repo url "+ repoUrl);
				}
				processor.setSCM(repoUrl, "", "", "");
				processor.save();
			}
		} catch (JAXBException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering catch block of updateSCMConnection()"+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		}
	}

	private PomProcessor getPomProcessor(String projCode)throws PhrescoException {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.getPomProcessor()");
		}
		try {
			StringBuilder builder = new StringBuilder(Utility.getProjectHome());
			builder.append(projCode);
			builder.append(File.separatorChar);
			builder.append(POM_XML);
			if(debugEnabled){
				S_LOGGER.debug("builder.toString() " + builder.toString());
			}
			File pomPath = new File(builder.toString());
			if(debugEnabled){
				S_LOGGER.debug("file exists " + pomPath.exists());
			}
			return new PomProcessor(pomPath);
		} catch (Exception e) {
			if(debugEnabled){
				S_LOGGER.error("Entring into catch block of getPomProcessor() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		}
	}

	private static void setupLibrary() {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.setupLibrary()");
		}
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
		FSRepositoryFactory.setup();
	}

	private boolean checkOutFilter(String url, String name, String password,String revision, SVNURL svnURL) throws PhrescoException {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.checkOutFilter()");
		}
		setupLibrary();
		SVNRepository repository = null;
		try {
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
		} catch (SVNException svne) {
			if(debugEnabled){
				S_LOGGER.error("error while creating an SVNRepository for location "+ url + "': " + svne.getMessage());
			}
			throw new PhrescoException(svne.getLocalizedMessage());
		}
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
		repository.setAuthenticationManager(authManager);
		try {
			SVNNodeKind nodeKind = repository.checkPath("", -1);
			if (nodeKind == SVNNodeKind.NONE) {
				if(debugEnabled){
					S_LOGGER.error("There is no entry at '" + url + "'.");
				}
			} else if (nodeKind == SVNNodeKind.FILE) {
				if(debugEnabled){
					S_LOGGER.error("The entry at '" + url + " is a file while a directory was expected.");
				}
			}
			if(debugEnabled){
				S_LOGGER.debug("Repository Root: " + repository.getRepositoryRoot(true));
				S_LOGGER.debug("Repository UUID: " + repository.getRepositoryUUID(true));
			}
			validateDir(repository, "", revision, svnURL, true);
			return true;
		} catch (SVNException svne) {
			if(debugEnabled){
				S_LOGGER.error("error while listing entries: " + svne.getMessage());
			}
			throw new PhrescoException(svne.getLocalizedMessage());
		}
	}

	private void validateDir(SVNRepository repository, String path,
			String revision, SVNURL svnURL, boolean recursive)throws PhrescoException {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.validateDir()");
		}
		// first level check
		try {
			Collection entries = repository.getDir(path, -1, null, (Collection) null);
			Iterator iterator = entries.iterator();
			if(debugEnabled){
				S_LOGGER.debug("Entry size ======> " + entries.size());
			}
			if (entries.size() != 0) {
				while (iterator.hasNext()) {
					SVNDirEntry entry = (SVNDirEntry) iterator.next();
					if ((entry.getName().equals(FOLDER_DOT_PHRESCO))
							&& (entry.getKind() == SVNNodeKind.DIR)) {
						if(debugEnabled){
							S_LOGGER.debug("Entry name " + entry.getName());
							S_LOGGER.debug("Entry Path " + entry.getURL());
						}
						ApplicationInfo appInfo = getSvnAppInfo(revision, svnURL);
						if(debugEnabled){
							S_LOGGER.debug("AppInfo " + appInfo);
						}
						SVNUpdateClient uc = cm.getUpdateClient();
						File file = new File(Utility.getProjectHome(), appInfo.getName());
						if(debugEnabled){
							S_LOGGER.debug("Checking out...");
						}
						uc.doCheckout(svnURL, file, SVNRevision.UNDEFINED,
								SVNRevision.parse(revision), SVNDepth.UNKNOWN,
								false);
						if(debugEnabled){
							S_LOGGER.debug("updating pom.xml");
						}
						// update connection url in pom.xml
						updateSCMConnection(appInfo.getName(),
								svnURL.toDecodedString());

					} else if (entry.getKind() == SVNNodeKind.DIR && recursive) {
						// second level check (only one iteration)
						SVNURL svnnewURL = svnURL.appendPath("/" + entry.getName(), true);
						if(debugEnabled){
							S_LOGGER.debug("Appended SVNURL for subdir---------> " + svnURL);
							S_LOGGER.debug("checking subdirectories");
						}
						validateDir(repository,(path.equals("")) ? entry.getName() : path
										+ "/" + entry.getName(), revision,svnnewURL, false);
					}
				}
			}
		} catch (Exception e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of validateDir() " + e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		}
	}

	private void importFromGit(String url, File gitImportTemp, String username, String password)throws PhrescoException {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.importFromGit()");
		}
		try {
			if(debugEnabled){
				S_LOGGER.debug("importing git " + url);
			}
			Git repo = null;
			CloneCommand cloneRepository = Git.cloneRepository();
			CloneCommand cloneCommand = null;
				if (username != null && password != null) {
					UsernamePasswordCredentialsProvider userCredential = new UsernamePasswordCredentialsProvider(username, password);
					cloneCommand = cloneRepository.setCredentialsProvider(userCredential);
				}
				CloneCommand callCommand = cloneRepository.setURI(url).setDirectory(gitImportTemp);
				repo = callCommand.call();
				for (Ref b : repo.branchList().setListMode(ListMode.ALL).call()) {
		            S_LOGGER.debug("(standard): cloned branch " + b.getName());
		        }
		        repo.getRepository().close();
		} catch (InvalidRemoteException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of importFromGit() " + e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} catch (GitAPIException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of importFromGit() " + e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		}
	}

	private boolean cloneFilter(File appDir, String url, boolean recursive)throws PhrescoException {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.cloneFilter()");
		}
		if (appDir.isDirectory()) {
			ApplicationInfo appInfo = getGitAppInfo(appDir);
			if(debugEnabled){
				S_LOGGER.debug("appInfo " + appInfo);
			}
			if (appInfo != null) {
				importToWorkspace(appDir, Utility.getProjectHome(),	appInfo.getName());
				if(debugEnabled){
					S_LOGGER.debug("updating pom.xml");
				}
				// update connection in pom.xml
				updateSCMConnection(appInfo.getName(), url);
			}
		}
		return true;
	}

	private ApplicationInfo getGitAppInfo(File directory)throws PhrescoException {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  Applications.getGitAppInfo()");
		}
		BufferedReader reader = null;
		try {
			File dotProjectFile = new File(directory, FOLDER_DOT_PHRESCO+ File.separator + PROJECT_INFO);
			if(debugEnabled){
				S_LOGGER.debug(dotProjectFile.getAbsolutePath());
				S_LOGGER.debug("dotProjectFile" + dotProjectFile);
			}
			if (!dotProjectFile.exists()) {
				return null;
			}
			reader = new BufferedReader(new FileReader(dotProjectFile));
			return new Gson().fromJson(reader, ApplicationInfo.class);
		} catch (FileNotFoundException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of getGitAppInfo() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} finally {
			Utility.closeStream(reader);
		}
	}

	private ApplicationInfo getSvnAppInfo(String revision, SVNURL svnURL)throws PhrescoException {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  Applications.getSvnAppInfo()");
		}
		BufferedReader reader = null;
		File tempDir = new File(Utility.getSystemTemp(), SVN_CHECKOUT_TEMP);
		if(debugEnabled){
			S_LOGGER.debug("temp dir : SVNAccessor " + tempDir);
		}
		try {
			SVNUpdateClient uc = cm.getUpdateClient();
			uc.doCheckout(svnURL.appendPath(PHRESCO, true), tempDir,
					SVNRevision.UNDEFINED, SVNRevision.parse(revision),
					SVNDepth.UNKNOWN, false);

			File dotProjectFile = new File(tempDir, PROJECT_INFO);
			if (!dotProjectFile.exists()) {
				throw new PhrescoException("Phresco Project definition not found");
			}

			reader = new BufferedReader(new FileReader(dotProjectFile));
			return new Gson().fromJson(reader, ApplicationInfo.class);
		} catch (SVNException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of getSvnAppInfo() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} catch (FileNotFoundException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of getSvnAppInfo() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(e.getLocalizedMessage());
		} finally {
			Utility.closeStream(reader);

			if (tempDir.exists()) {
				FileUtil.delete(tempDir);
			}
		}
	}
}