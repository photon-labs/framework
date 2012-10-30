package com.photon.phresco.framework.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
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
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.api.SCMManager;
import com.photon.phresco.util.FileUtil;
import com.photon.phresco.util.Utility;
import com.phresco.pom.util.PomProcessor;

public class SCMManagerImpl implements SCMManager {

	private static final Logger S_LOGGER = Logger.getLogger(SCMManagerImpl.class);
	private static final String SVN_CHECKOUT_TEMP = "svn-checkout-temp";
	private static final String PROJECT_INFO = "project.info";
	private static final String PHRESCO = "/.phresco";
	String IMPORT_SUCCESS_PROJECT = "Project imported successfully";
	String HEAD_REVISION = "HEAD";
	private static final String FOLDER_DOT_PHRESCO = ".phresco";
	private static final String PROJECT_INFO_FILE = "project.info";
//	private String revisionVal = ""; TODO : need this validation in web
	private static boolean dotphresco;
	SVNClientManager cm = null;
	public void importProject(String type, String url, String username,
			String password, String branch, String revision, String projcode)
			throws Exception {
		S_LOGGER.debug("Entering Method  Applications.importApplication()");
		S_LOGGER.debug("repoType " + type);
		S_LOGGER.debug("repositoryUrl " + url);
		
		try {
			SVNURL svnURL = SVNURL.parseURIEncoded(url);
			DAVRepositoryFactory.setup();
			DefaultSVNOptions options = new DefaultSVNOptions();
			cm = SVNClientManager.newInstance(options, username, password);
			if ("svn".equals(type)) {
				System.out.println("calling validate");
				boolean validateURL = validateURL(url, username, password, revision, svnURL);
				if (validateURL) {
					System.out.println("Phresco found ");// check out whole dir
//					(File dstPath, String revision, boolean isRecursive, String projCode,SVNURL svnURL)
					checkout(new File(Utility.getProjectHome()), revision, false, "", svnURL);
				} else {
					System.out.println("Phresco Project definition not found");
				}
			} else if ("git".equals(type)) {
				String uuid = UUID.randomUUID().toString();
				File gitImportTemp = new File(Utility.getPhrescoTemp(), uuid);
				S_LOGGER.debug("gitImportTemp " + gitImportTemp);
				if (gitImportTemp.exists()) {
					S_LOGGER.debug("Empty git directory need to be removed before importing from git ");
					FileUtils.deleteDirectory(gitImportTemp);
				}
				S_LOGGER.debug("gitImportTemp " + gitImportTemp);
				System.out.println("Please wait while Cloning...");
				importFromGit(url, gitImportTemp);
				System.out.println("validating for .phresco");
				boolean valid = validPhrecoDirCheck(gitImportTemp);
				if (valid){
					System.out.println("Validation success!");
					importFromTemp(gitImportTemp);
					System.out.println("Cloning success");
//				// update connection in pom.xml
//				updateSCMConnection(appInfo.getCode(), url);
				} else {
					System.out.println("Phresco definition not found!!");
				}
				if(gitImportTemp.exists())
					FileUtils.deleteDirectory(gitImportTemp);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateProject(String type, String url, String username,
			String password, String branch, String revision, String projcode)
			throws Exception {
		try {
			if ("svn".equals(type)) {
				DAVRepositoryFactory.setup();
				SVNURL svnURL = SVNURL.parseURIEncoded(url);
				DefaultSVNOptions options = new DefaultSVNOptions();
				SVNClientManager cm = SVNClientManager.newInstance(options, username, password);
				S_LOGGER.debug("update SCM Connection " + url);
				S_LOGGER.debug("userName " + username);
				S_LOGGER.debug("Repo type " + type);
				S_LOGGER.debug("projectCode " + projcode);
				// updateSCMConnection(projcode, url);

//				revision = HEAD_REVISION.equals(revision) ? revision
//						: revisionVal;
				File updateDir = new File(Utility.getProjectHome(), projcode);
				S_LOGGER.debug("updateDir SVN... " + updateDir);
				System.out.println("Updating...");
				SVNUpdateClient uc = cm.getUpdateClient();
				uc.doUpdate(updateDir, SVNRevision.parse(revision), false);
				System.out.println("Updated!");

			} else if ("git".equals(type)) {
				S_LOGGER.debug("update SCM Connection " + url);
				S_LOGGER.debug("userName " + username);
				S_LOGGER.debug("Repo type " + type);
				S_LOGGER.debug("projectCode " + projcode);
				// updateSCMConnection(projcode, url);

				File updateDir = new File(Utility.getProjectHome(), projcode);
				System.out.println("Updating...");
				S_LOGGER.debug("updateDir GIT... " + updateDir);
				Git git = Git.open(updateDir); // checkout is the folder with
												// .git
				git.pull().call(); // succeeds
				System.out.println("Updated!");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void importToWorkspace(File gitImportTemp, String projectHome,String code) throws Exception {
		S_LOGGER.debug("Entering Method  Applications.importToWorkspace()");
		File workspaceProjectDir = new File(projectHome + code);
		S_LOGGER.debug("workspaceProjectDir " + workspaceProjectDir);
		if (workspaceProjectDir.exists()) {
			S_LOGGER.debug("workspaceProjectDir exists" + workspaceProjectDir);
			throw new org.apache.commons.io.FileExistsException();
		}
		S_LOGGER.debug("gitImportTemp ====> " + gitImportTemp);
		S_LOGGER.debug("workspaceProjectDir ====> " + workspaceProjectDir);
		FileUtils.copyDirectory(gitImportTemp, workspaceProjectDir);
		try {
			FileUtils.deleteDirectory(gitImportTemp);
		} catch (IOException e) {
			S_LOGGER.debug("pack file is not deleted " + e.getLocalizedMessage());
		}
	}

	private boolean importFromGit(String url, File gitImportTemp) throws Exception {
		S_LOGGER.debug("Entering Method  Applications.importFromGit()");
		S_LOGGER.debug("importing git " + url);
		Git repo = Git.cloneRepository().setURI(url).setDirectory(gitImportTemp).call();
		for (org.eclipse.jgit.lib.Ref b : repo.branchList().setListMode(ListMode.ALL).call()) {
			S_LOGGER.debug("(standard): cloned branch " + b.getName());
		}
		repo.getRepository().close();
		return true;

	}

	private void updateSCMConnection(String projCode, String repoUrl) throws Exception {
		PomProcessor processor = getPomProcessor(projCode);
		if (processor.getSCM() == null) {
			S_LOGGER.debug("processor.getSCM() exists and repo url " + repoUrl);
			processor.setSCM(repoUrl, "", "", "");
			processor.save();
		}
	}

	private PomProcessor getPomProcessor(String projCode) throws PhrescoException {
		try {
			StringBuilder builder = new StringBuilder(Utility.getProjectHome());
			builder.append(projCode);
			builder.append(File.separatorChar);
			builder.append("POM_XML");
			S_LOGGER.debug("builder.toString() " + builder.toString());
			File pomPath = new File(builder.toString());
			S_LOGGER.debug("file exists " + pomPath.exists());
			return new PomProcessor(pomPath);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	private static void setupLibrary() {
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
		FSRepositoryFactory.setup();
	}

	public boolean validateURL(String url, String name, String password, String revision, SVNURL svnURL) throws Exception {
		setupLibrary();
		SVNRepository repository = null;
		try {
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
		} catch (SVNException svne) {
			System.out.println("error while creating an SVNRepository for location '" + url + "': " + svne.getMessage());
		}
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
		repository.setAuthenticationManager(authManager);
		try {
			SVNNodeKind nodeKind = repository.checkPath("", -1);
			if (nodeKind == SVNNodeKind.NONE) {
				System.out.println("There is no entry at '" + url + "'.");
			} else if (nodeKind == SVNNodeKind.FILE) {
				System.out.println("The entry at '" + url + "' is a file while a directory was expected.");
			}
			System.out.println("Repository Root: " + repository.getRepositoryRoot(true));
			System.out.println("Repository UUID: " + repository.getRepositoryUUID(true));
			return validateRootDir(repository, "", revision, svnURL, true);
		} catch (SVNException svne) {
			System.out.println("error while listing entries: " + svne.getMessage());
		}
		return false;
	}
	
	public boolean validateRootDir(SVNRepository repository, String path, String revision, SVNURL svnURL, boolean recursive) throws Exception {
		// second leve check
		Collection entries = repository.getDir(path, -1, null, (Collection) null);
		Iterator iterator = entries.iterator();
		System.out.println("Entry size ======> " + entries.size());
		if (entries.size() != 0) {
			while (iterator.hasNext()) {
				SVNDirEntry entry = (SVNDirEntry) iterator.next();
				System.out.println("Entry name " + entry.getName());
				System.out.println("Entry Path " + entry.getURL());
				
				if(entry.getKind() == SVNNodeKind.DIR) {
					// root dir check
					boolean validSVN = isValidSVN(entry, revision, svnURL);
					if (validSVN) {
						System.out.println("valid SVn "); // handle here for checkout
						return validSVN;
					}
				}
				
				if(entry.getKind() == SVNNodeKind.DIR && recursive) {
					// recursive call should be false for 2 level check
					return validateRootDir(repository, (path.equals("")) ? entry.getName() : path + "/" + entry.getName(), revision, svnURL, false);
				}
			}
		}
		return false;
	}
	
	private boolean isValidSVN(SVNDirEntry entry, String revision, SVNURL svnURL) throws Exception {
		if((entry.getName().equals(".phresco")) && (entry.getKind() == SVNNodeKind.DIR) ){
			 System.out.println("phresco found");
			 return isValidSVN(revision, entry.getURL());
		 }
		return false;
	}
	
	private boolean isValidSVN(String revision, SVNURL svnURL) throws Exception {
		 ApplicationInfo appInfo = getappInfo(revision, svnURL); // handle here for checkout
		 if(appInfo != null) {
			 System.out.println("This is valid project ");
			 return true;
		 }
		return false;
	}
	private boolean isValidPhrescoDir(File appDir) {
		if (appDir.isDirectory()) { // Only check the folders not files
            File[] dotPhrescoFolders = appDir.listFiles(new PhrescoFileNameFilter(FOLDER_DOT_PHRESCO));
            for (File dotPhrescoFolder : dotPhrescoFolders) {
				System.out.println("dotPhrescoFolder ====> " + dotPhrescoFolder);
			}
            if (ArrayUtils.isEmpty(dotPhrescoFolders)) {
                return false;
            }
            File[] dotProjectFiles = dotPhrescoFolders[0].listFiles(new PhrescoFileNameFilter(PROJECT_INFO_FILE));
            if (ArrayUtils.isEmpty(dotProjectFiles)) {
            	return false;
            }
        }
		return true;
	}
	
	public ApplicationInfo getAppInfo(File directory) throws Exception {
        S_LOGGER.debug("Entering Method  Applications.getProjectInfo()");
        BufferedReader reader = null;
        try {
            File dotProjectFile = new File(directory, FOLDER_DOT_PHRESCO + File.separator + PROJECT_INFO);
            System.out.println(dotProjectFile.getAbsolutePath());
            S_LOGGER.debug("dotProjectFile" + dotProjectFile);
            if (!dotProjectFile.exists()) {
                return null;
            }
            reader = new BufferedReader(new FileReader(dotProjectFile));
            return new Gson().fromJson(reader, ApplicationInfo.class);
        } finally {
            Utility.closeStream(reader);
        }
    }
	
	public boolean validPhrecoDirCheck(File dir) {
		try {
			// root dir
			boolean validPhrescoDir = isValidPhrescoDir(dir);
			if (!validPhrescoDir) {
//				// sub dir check
				File[] listFiles = dir.listFiles();
				for (File listFile : listFiles) {
					if(isValidPhrescoDir(listFile)) {
						return true;
					}
				}
			}
			return validPhrescoDir;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void importFromTemp(File dir) {
		try {
			ApplicationInfo appInfo = null;
			// first level checking for .phresco folder
			appInfo = getAppInfo(dir);
			if (appInfo != null) {
				importToWorkspace(dir, Utility.getProjectHome(), appInfo.getName());
			} else {
				// second level check for .phresco folder
				File[] listFiles = dir.listFiles();
				for (File listFile : listFiles) {
					appInfo = getAppInfo(listFile);
					if (appInfo != null) {
						importToWorkspace(listFile, Utility.getProjectHome(), appInfo.getName());
					} else {
						importToWorkspace(listFile, Utility.getProjectHome(), listFile.getName());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 public ApplicationInfo getappInfo(String revision, SVNURL svnURL) { // throws Exception 
	        BufferedReader reader = null;
	        File tempDir = new File(Utility.getSystemTemp(), SVN_CHECKOUT_TEMP);
	        System.out.println("temp dir : SVNAccessor " +tempDir );
	        try {
	            SVNUpdateClient uc = cm.getUpdateClient();
	            System.out.println("SVNURL : " + svnURL.removePathTail());
	            uc.doCheckout(svnURL, tempDir, SVNRevision.UNDEFINED, SVNRevision.parse(revision), SVNDepth.UNKNOWN, false);
	            
	            File dotProjectFile = new File(tempDir, PROJECT_INFO);
	            if (!dotProjectFile.exists()) {
	                throw new PhrescoException("Phresco Project definition not found");
	            }

	            reader = new BufferedReader(new FileReader(dotProjectFile));
	            return new Gson().fromJson(reader, ApplicationInfo.class);
	        } catch(Exception e) {
	        	System.out.println("Exception caught ");
	        	e.printStackTrace();
	        } finally {
	            Utility.closeStream(reader);
	            if (tempDir.exists()) {
	                FileUtil.delete(tempDir);
	            }
	        }
	        return null;
	 }
	 
	public void checkout(File dstPath, String revision, boolean isRecursive, String projCode, SVNURL svnURL) throws Exception {
//	       File projectRoot = new File(dstPath, projCode);
//	       if (projectRoot.exists()) {
//	           throw new PhrescoException("Import Fails", "Project with the code " + projCode + " already present");
//	       }
		System.out.println(dstPath);
	       SVNUpdateClient uc = cm.getUpdateClient();
	       uc.doCheckout(svnURL, dstPath, SVNRevision.UNDEFINED, SVNRevision.parse(revision),
	               SVNDepth.UNKNOWN, false);
	}


//		private ProjectInfo getProjectInfo(String url, String username, String password, String revision) throws Exception {
//			BufferedReader reader = null;
//			File tempDir = new File(Utility.getSystemTemp(), SVN_CHECKOUT_TEMP);
//			System.out.println("temp dir : SVNAccessor " + tempDir);
//			try {
//				SVNURL svnURL = SVNURL.parseURIEncoded(url);
//				DefaultSVNOptions options = new DefaultSVNOptions();
//				SVNClientManager cm = SVNClientManager.newInstance(options, username, password);
//				
//				SVNUpdateClient uc = cm.getUpdateClient();
//				uc.doCheckout(svnURL.appendPath(PHRESCO, true), tempDir, SVNRevision.UNDEFINED, SVNRevision.parse(revision), SVNDepth.UNKNOWN, false);
	//
//				File dotProjectFile = new File(tempDir, PROJECT_INFO);
//				if (!dotProjectFile.exists()) {
//					throw new PhrescoException("Phresco Project definition not found");
//				}
	//
//				reader = new BufferedReader(new FileReader(dotProjectFile));
//				return new Gson().fromJson(reader, ProjectInfo.class);
//			} finally {
//				Utility.closeStream(reader);
//				if (tempDir.exists()) {
//					FileUtil.delete(tempDir);
//				}
//			}
//		}

//		private ProjectInfo getProjectInfo(String url, String username, String password, String revision) throws Exception {
//			BufferedReader reader = null;
//			File tempDir = new File(Utility.getSystemTemp(), SVN_CHECKOUT_TEMP);
//			System.out.println("temp dir : SVNAccessor " + tempDir);
//			try {
//				SVNURL svnURL = SVNURL.parseURIEncoded(url);
//				DefaultSVNOptions options = new DefaultSVNOptions();
//				SVNClientManager cm = SVNClientManager.newInstance(options, username, password);
//				
//				SVNUpdateClient uc = cm.getUpdateClient();
//				uc.doCheckout(svnURL.appendPath(PHRESCO, true), tempDir, SVNRevision.UNDEFINED, SVNRevision.parse(revision), SVNDepth.UNKNOWN, false);
	//
//				File dotProjectFile = new File(tempDir, PROJECT_INFO);
//				if (!dotProjectFile.exists()) {
//					throw new PhrescoException("Phresco Project definition not found");
//				}
	//
//				reader = new BufferedReader(new FileReader(dotProjectFile));
//				return new Gson().fromJson(reader, ProjectInfo.class);
//			} finally {
//				Utility.closeStream(reader);
//				if (tempDir.exists()) {
//					FileUtil.delete(tempDir);
//				}
//			}
//		}
}
class PhrescoFileNameFilter implements FilenameFilter {
	 private String filter_;
	 public PhrescoFileNameFilter(String filter) {
		 filter_ = filter;
	 }
	 public boolean accept(File dir, String name) {
		 return name.endsWith(filter_);
	 }
}
