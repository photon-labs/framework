package com.photon.phresco.framework.impl;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.*;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;

import com.google.gson.Gson;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.api.SCMManager;
import com.photon.phresco.util.FileUtil;
import com.photon.phresco.util.Utility;
import com.phresco.pom.util.PomProcessor;

public class SCMManagerImpl implements SCMManager, FrameworkConstants {

	private static final Logger S_LOGGER = Logger.getLogger(SCMManagerImpl.class);
	private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();
	boolean dotphresco ;
	SVNClientManager cm = null;

	public boolean importProject(String type, String url, String username,
			String password, String branch, String revision) throws Exception {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.importProject()");
		}
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
		return false;
	}

	public boolean updateProject(String type, String url, String username ,
			String password, String branch, String revision, String projcode) throws Exception  {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.updateproject()");
		}
		if (SVN.equals(type)) {
			if(debugEnabled){
				S_LOGGER.debug("SVN type");
			}
			DAVRepositoryFactory.setup();
			SVNURL svnURL = SVNURL.parseURIEncoded(url);
			DefaultSVNOptions options = new DefaultSVNOptions();
			cm = SVNClientManager.newInstance(options, username, password);
			if(debugEnabled){
				S_LOGGER.debug("update SCM Connection " + url);
			}
			 updateSCMConnection(projcode, url);
				// revision = HEAD_REVISION.equals(revision) ? revision
				// : revisionVal;
			File updateDir = new File(Utility.getProjectHome(), projcode);
			if(debugEnabled){
				S_LOGGER.debug("updateDir SVN... " + updateDir);
				S_LOGGER.debug("Updating...");
			}
			SVNUpdateClient uc = cm.getUpdateClient();
			uc.doUpdate(updateDir, SVNRevision.parse(revision), SVNDepth.UNKNOWN, true, true);
			if(debugEnabled){
				S_LOGGER.debug("Updated!");
			}
			return true;
		} else if (GIT.equals(type)) {
			if(debugEnabled){
				S_LOGGER.debug("GIT type");
			}
			updateSCMConnection(projcode, url);
			File updateDir = new File(Utility.getProjectHome(), projcode); 
			if(debugEnabled){
				S_LOGGER.debug("updateDir GIT... " + updateDir);
			}
			Git git = Git.open(updateDir); // checkout is the folder with .git
			git.pull().call(); // succeeds
			if(debugEnabled){
				S_LOGGER.debug("Updated!");
			}
			return true;
		}

		return false;
	}

	private void importToWorkspace(File gitImportTemp, String projectHome,String code) throws Exception {
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
				throw new PhrescoException(PROJECT_ALREADY);
			}
			if(debugEnabled){
				S_LOGGER.debug("Copyin from Temp to workspace...");
				S_LOGGER.debug("gitImportTemp " + gitImportTemp);
				S_LOGGER.debug("workspaceProjectDir " + workspaceProjectDir);
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
		}
	}

	private void updateSCMConnection(String projCode, String repoUrl)throws Exception {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method SCMManagerImpl.updateSCMConnection()");
		}
		try {
			PomProcessor processor = getPomProcessor(projCode);
				if(debugEnabled){
					S_LOGGER.debug("processor.getSCM() exists and repo url "+ repoUrl);
				}
				processor.setSCM(repoUrl, "", "", "");
				processor.save();
		} catch (Exception e) {
			if(debugEnabled){
				S_LOGGER.error("Entering catch block of updateSCMConnection()"+ e.getLocalizedMessage());
			}
			throw new PhrescoException(POM_URL_FAIL);
		}
	}

	private PomProcessor getPomProcessor(String projCode)throws Exception {
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
			throw new PhrescoException(NO_POM_XML);
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

	private boolean checkOutFilter(String url, String name, String password,String revision, SVNURL svnURL) throws Exception {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.checkOutFilter()");
		}
		setupLibrary();
		SVNRepository repository = null;
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
		repository.setAuthenticationManager(authManager);
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
			boolean valid = validateDir(repository, "", revision, svnURL, true);
			return valid;
	}

	private boolean validateDir(SVNRepository repository, String path,
			String revision, SVNURL svnURL, boolean recursive)throws Exception {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.validateDir()");
		}
		// first level check
			Collection entries = repository.getDir(path, -1, null, (Collection) null);
			Iterator iterator = entries.iterator();
			if(debugEnabled){
				S_LOGGER.debug("Entry size " + entries.size());
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
						ProjectInfo projectInfo = getSvnAppInfo(revision, svnURL);
						if(debugEnabled){
							S_LOGGER.debug("AppInfo " + projectInfo);
						}
						SVNUpdateClient uc = cm.getUpdateClient();
						ApplicationInfo appInfo = projectInfo.getAppInfos().get(0);
						
						File file = new File(Utility.getProjectHome(), appInfo.getAppDirName());
						if (file.exists()) {
							throw new PhrescoException(PROJECT_ALREADY);
			            }
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
						updateSCMConnection(appInfo.getAppDirName(),
								svnURL.toDecodedString());
						dotphresco = true;
						return dotphresco;
					} else if (entry.getKind() == SVNNodeKind.DIR && recursive) {
						// second level check (only one iteration)
						SVNURL svnnewURL = svnURL.appendPath("/" + entry.getName(), true);
						if(debugEnabled){
							S_LOGGER.debug("Appended SVNURL for subdir " + svnURL);
							S_LOGGER.debug("checking subdirectories");
						}
						validateDir(repository,(path.equals("")) ? entry.getName() : path
										+ "/" + entry.getName(), revision,svnnewURL, false);
					}
				}
			}
		return dotphresco;
	}

	private void importFromGit(String url, File gitImportTemp, String username, String password)throws Exception {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.importFromGit()");
		}
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
	}

	private boolean cloneFilter(File appDir, String url, boolean recursive)throws Exception {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.cloneFilter()");
		}
		if (appDir.isDirectory()) {
			ProjectInfo projectInfo = getGitAppInfo(appDir);
			if(debugEnabled){
				S_LOGGER.debug("appInfo " + projectInfo);
			}
			ApplicationInfo appInfo = projectInfo.getAppInfos().get(0);
			if (appInfo != null) {
				importToWorkspace(appDir, Utility.getProjectHome(),	appInfo.getAppDirName());
				if(debugEnabled){
					S_LOGGER.debug("updating pom.xml");
				}
				// update connection in pom.xml
				updateSCMConnection(appInfo.getAppDirName(), url);
				return true;
			}
		}
		return false;
	}

	private ProjectInfo getGitAppInfo(File directory)throws PhrescoException {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.getGitAppInfo()");
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
			return new Gson().fromJson(reader, ProjectInfo.class);
		} catch (FileNotFoundException e) {
			if(debugEnabled){
				S_LOGGER.error("Entering into catch block of getGitAppInfo() "+ e.getLocalizedMessage());
			}
			throw new PhrescoException(INVALID_FOLDER);
		} finally {
			Utility.closeStream(reader);
		}
	}

	private ProjectInfo getSvnAppInfo(String revision, SVNURL svnURL) throws Exception {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.getSvnAppInfo()");
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
				throw new PhrescoException(INVALID_FOLDER);
			}
			reader = new BufferedReader(new FileReader(dotProjectFile));
			return new Gson().fromJson(reader, ProjectInfo.class);
		} finally {
			Utility.closeStream(reader);

			if (tempDir.exists()) {
				FileUtil.delete(tempDir);
			}
		}
	}

	public boolean importToRepo(String type, String url, String username,
			String password, String branch, String revision, File dir, String commitMessage) throws Exception {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.importToRepo()");
		}
		try {
			if (SVN.equals(type)) {
				importDirectoryContentToSubversion(url, dir.getPath(), username, password, commitMessage);
				// checkout to get .svn folder
				checkoutImportedApp(url, dir.getPath(), username, password);
			} else if (GIT.equals(type)) {
				importToGITRepo();
			}
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	private SVNCommitInfo importDirectoryContentToSubversion(final String repositoryURL, final String subVersionedDirectory, final String userName, final String hashedPassword, final String commitMessage) throws SVNException {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.importDirectoryContentToSubversion()");
		}
		setupLibrary();
        final SVNClientManager cm = SVNClientManager.newInstance(new DefaultSVNOptions(), userName, hashedPassword);
        return cm.getCommitClient().doImport(new File(subVersionedDirectory), SVNURL.parseURIEncoded(repositoryURL), commitMessage, null, true, true, SVNDepth.fromRecurse(true));
    }
	
	private void checkoutImportedApp(String repositoryURL, String subVersionedDirectory, String userName, String password) throws Exception {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.checkoutImportedApp()");
		}
		DefaultSVNOptions options = new DefaultSVNOptions();
		SVNClientManager cm = SVNClientManager.newInstance(options, userName, password);
		SVNUpdateClient uc = cm.getUpdateClient();
		SVNURL svnURL = SVNURL.parseURIEncoded(repositoryURL);
		if(debugEnabled){
			S_LOGGER.debug("Checking out...");
		}
		File subVersDir = new File(subVersionedDirectory);
		uc.doCheckout(SVNURL.parseURIEncoded(repositoryURL), subVersDir, SVNRevision.UNDEFINED, SVNRevision.parse(HEAD_REVISION), SVNDepth.INFINITY, true);
		if(debugEnabled){
			S_LOGGER.debug("updating pom.xml");
		}
		// update connection url in pom.xml
		updateSCMConnection(subVersDir.getName(), svnURL.toDecodedString());
	}
	
	private void importToGITRepo() throws Exception {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.importToGITRepo()");
		}
		// TODO :: Need to implement
	}

	public boolean commitToRepo(String type, String url, String username, String password, String branch, String revision, File dir, String commitMessage) throws Exception {
		if(debugEnabled) {
			S_LOGGER.debug("Entering Method  SCMManagerImpl.commitToRepo()");
		}
		try {
			if (SVN.equals(type)) {
				commitDirectoryContentToSubversion(url, dir.getPath(), username, password, commitMessage);
			} else if (GIT.equals(type)) {
				importToGITRepo();
			}
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	private SVNCommitInfo commitDirectoryContentToSubversion(String repositoryURL, String subVersionedDirectory, String userName, String hashedPassword, String commitMessage) throws SVNException {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.commitDirectoryContentToSubversion()");
		}
		setupLibrary();
		
		final SVNClientManager cm = SVNClientManager.newInstance(new DefaultSVNOptions(), userName, hashedPassword);
		SVNWCClient wcClient = cm.getWCClient();
		File subVerDir = new File(subVersionedDirectory);
		// This one recursively adds an existing local item under version control (schedules for addition)
		wcClient.doAdd(subVerDir, true, false, false, SVNDepth.INFINITY, false, false);
		return cm.getCommitClient().doCommit(new File[]{subVerDir}, false, commitMessage, null, null, false, true, SVNDepth.INFINITY);
    }
	
	public SVNCommitInfo deleteDirectoryInSubversion(String repositoryURL, String subVersionedDirectory, String userName, String password, String commitMessage) throws SVNException, IOException {
		if(debugEnabled){
			S_LOGGER.debug("Entering Method  SCMManagerImpl.commitDirectoryContentToSubversion()");
		}
		setupLibrary();
		
		File subVerDir = new File(subVersionedDirectory);		
		final SVNClientManager cm = SVNClientManager.newInstance(new DefaultSVNOptions(), userName, password);
		
		SVNWCClient wcClient = cm.getWCClient();
		//wcClient.doDelete(subVerDir, true, false);
		FileUtils.listFiles(subVerDir, TrueFileFilter.TRUE, FileFilterUtils.makeSVNAware(null));
		for (File child : subVerDir.listFiles()) {
			if (!(DOT+SVN).equals(child.getName())) {
				wcClient.doDelete(child, true, true, false);
			}
		}
		
		return cm.getCommitClient().doCommit(new File[]{subVerDir}, false, commitMessage, null, null, false, true, SVNDepth.INFINITY);
    }
}