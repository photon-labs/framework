package com.photon.phresco.framework.actions.applications;

import java.io.*;
import java.util.*;

import junit.framework.Assert;

import org.junit.*;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.*;
import org.tmatesoft.svn.core.internal.io.dav.*;
import org.tmatesoft.svn.core.internal.io.fs.*;
import org.tmatesoft.svn.core.internal.io.svn.*;
import org.tmatesoft.svn.core.internal.wc.*;
import org.tmatesoft.svn.core.io.*;
import org.tmatesoft.svn.core.io.diff.*;
import org.tmatesoft.svn.core.wc.*;

import com.photon.phresco.exception.*;
import com.photon.phresco.framework.impl.*;

public class ApplicationsTest {
	SCMManagerImpl scmi = null;

	@Before
	public void setUp() throws Exception {
		scmi = new SCMManagerImpl();
		System.out.println("Set up!!!!");
	}

	@After
	public void tearDown() throws Exception {
		if (scmi != null) {
			scmi = null;
		}
		System.out.println("tear down!!!!");
	}

//  @Test
	public void gitPrivateRepoClone() throws Exception {
		boolean valid = scmi.importProject("git","https://github.com/bestbuymobileapps/bby-digital-deals.git",
				"nikhilkumar-a", "phresco123", "master", null);
		Assert.assertEquals(true, valid );
	}
	
//	@Test
	public void gitPublicRepoClone() throws Exception {
		boolean valid = scmi.importProject("git","https://github.com/santhosh-ja/Test2.git", null, null, "master", null);
		Assert.assertEquals(true, valid );
		}
	
//  @Test
	public void svnSingleFileCheckout() throws Exception {
		boolean valid = scmi.importProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/phresco-test/sample applayer-tech-php/", "santhosh_ja", "santJ!23", null, "HEAD");
		Assert.assertEquals(true, valid );
	}
	
//  @Test
	public void svnMultipleFileCheckout() throws Exception {
		boolean valid = scmi.importProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/phresco-test/", "santhosh_ja", "santJ!23", null, "HEAD");
		Assert.assertEquals(true, valid);
	}

//  @Test
	public void gitUpdate() throws Exception {
			boolean valid = scmi.updateProject("git","https://github.com/santhosh-ja/Test2.git", null, null, "master", null, "create");
			Assert.assertEquals(true, valid );
	}
	
//  @Test
	public void svnUpdate() throws Exception {
			 boolean valid = scmi.updateProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/phresco-test/sample applayer-tech-php/"
			 , "santhosh_ja" , "santJ!23" , null , "HEAD" , "sample applayer");
				Assert.assertEquals(true, valid );
	}
	
	//Negative Scenarios
    //Clone/Checkout a non phresco-compliance projects
//	@Test
	public void gitClonePhrescoComplianceFail() throws Exception {
		boolean valid = scmi.importProject("git","https://github.com/santhosh-ja/Test.git", null, null, "master", null);
		Assert.assertEquals(false, valid);
	}
	
//  @Test
	public void svnCheckoutPhrescoComplianceFail() throws Exception {
		boolean valid = scmi.importProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/", "santhosh_ja", "santJ!23", null, "HEAD");
		Assert.assertEquals(false, valid );
		}
	
	// Updating non existing files
	
//	@Test
	public void gitUpdateFails() throws Exception {
		try{
			boolean valid = scmi.updateProject("git","https://github.com/santhosh-ja/Test2.git", null, null, "master", null, "create1");
		} catch(PhrescoException e){
				Assert.assertEquals(e.getLocalizedMessage() , "The system cannot find the path specified" );
		}
	}
	
//  @Test
	public void svnUpdateFails() throws Exception {
		try{
			 boolean valid = scmi.updateProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/phresco-test/sample applayer-tech-php/"
			 , "santhosh_ja" , "santJ!23" , null , "HEAD" , "sample applayer1");
				Assert.assertEquals(false, valid );
		} catch(PhrescoException e){
			Assert.assertEquals(e.getLocalizedMessage() , "svn: 'C:\\Documents and Settings\\santhosh_ja\\workspace\\projects\\sample applayer1' is not a working copy" );
		}
	}
	
	// import newly created projects to the repo
//	@Test
	public void importProjectToRepo() throws Exception {
		try {
			String svnUrl = "https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/CREATE2/svnDirCheck/do_not_checkin/build/";
			File dirToBeCheckedIn = new File("/Users/kaleeswaran/workspace/projects/ScannerComponent-iphone");
			String username = "kaleeswaran_s";
			String password = "Suresh@123";
			String commitMessage = "[artf590603] directory and file added";
			scmi.importToRepo("svn", svnUrl, username, password, null, null, dirToBeCheckedIn, commitMessage);
		} catch (Exception e) {
			Assert.assertEquals(e.getLocalizedMessage() , "svn: 'importToRepo failed" );			
		}
	}
	
	// commit newly added and modified files to repo
//	@Test
	public void commitProjectToRepo() throws Exception {
		try {
			String svnUrl = "https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/CREATE2/svnDirCheck/do_not_checkin/build/";
			File dirToBeCheckedIn = new File("/Users/kaleeswaran/workspace/projects/ScannerComponent-iphone");
			String username = "kaleeswaran_s";
			String password = "Suresh@123";
			String commitMessage = "[artf590603] directory and file added";
			scmi.commitToRepo("svn", svnUrl, username, password, null, null, dirToBeCheckedIn, commitMessage);
		} catch (Exception e) {
			Assert.assertEquals(e.getLocalizedMessage() , "svn: 'importToRepo failed" );			
		}
	}
	
//	@Test
	public void deleteProjectToRepo() throws Exception {
		try {
			String svnUrl = "https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/CREATE1";
			File dirToBeCheckedIn = new File("/Users/kaleeswaran/projects/2.0Projects/CREATE1");
			String username = "kaleeswaran_s";
			String password = "Suresh@123";
			String commitMessage = "[artf590603] directory and file added";
			scmi.deleteDirectoryInSubversion(svnUrl, dirToBeCheckedIn.getPath(), username, password, commitMessage);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertEquals(e.getLocalizedMessage() , "svn: 'importToRepo failed" );			
		}
	}
	
//	@Test
	public void getLocallyModifiedFile() throws Exception {
		try {
			String svnUrl = "https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/DotnetTest-tech-dotnet";
			File dirToBeChecked = new File("/Users/kaleeswaran/projects/2.0Projects/DotnetTest-tech-dotnet");
			String username = "kaleeswaran_s";
			String password = "Suresh@123";
			String commitMessage = "[artf590603] directory and file added";
			List<File> listModifiedFiles = listModifiedFiles(dirToBeChecked, SVNRevision.parse("HEAD"));
//			List<File> listModifiedFiles = new ArrayList<File>();
//			listModifiedFiles.add(new File("/Users/kaleeswaran/projects/2.0Projects/DotnetTest-tech-dotnet/pom copy 4.xml"));
//			commitSpecifiedFiles(listModifiedFiles, dirToBeChecked, username, password, commitMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<File> listModifiedFiles(File path, SVNRevision revision) throws SVNException {
		System.out.println("getting modified files !!!! ");
	    SVNClientManager svnClientManager = SVNClientManager.newInstance();
	    final List<File> fileList = new ArrayList<File>();
	    final List<SVNStatus> AllSVNStatus = new ArrayList<SVNStatus>();
	    svnClientManager.getStatusClient().doStatus(path, revision, SVNDepth.INFINITY, false, false, false, false, new ISVNStatusHandler() {
	        public void handleStatus(SVNStatus status) throws SVNException {
	            SVNStatusType statusType = status.getContentsStatus();
	            AllSVNStatus.add(status);
	            if (statusType != SVNStatusType.STATUS_NONE && statusType != SVNStatusType.STATUS_NORMAL
	                    && statusType != SVNStatusType.STATUS_IGNORED) {
	            	System.out.println("getting status !!!!!! ");
		            System.out.println("Status File => " + status.getFile());
		            System.out.println("Status Type => " + statusType);
	                fileList.add(status.getFile());
	            }
	        }
	    }, null);
	    
	    return fileList;
	}
	
	private void commitSpecifiedFiles(List<File> listModifiedFiles, File rootDir, String username, String password, String commitMessage) throws Exception {
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
		FSRepositoryFactory.setup();
		
		final SVNClientManager cm = SVNClientManager.newInstance(new DefaultSVNOptions(), username, password);
		SVNWCClient wcClient = cm.getWCClient();
		
		for (File lastModifiedFile : listModifiedFiles) {
			System.out.println("lastModifiedFile => " + lastModifiedFile);
			wcClient.doAdd(lastModifiedFile, true, false, false, SVNDepth.INFINITY, false, false);
			
			SVNCommitInfo commitInfo = cm.getCommitClient().doCommit(new File[]{lastModifiedFile}, false, commitMessage, null, null, false, true, SVNDepth.INFINITY);
			System.out.println("commitInfo => " + commitInfo.getAuthor());
			System.out.println("commitInfo revision => " + commitInfo.getNewRevision());
			break;
		}
	}
	
//	@Test
	public void commitSpecifiedFiles()  throws Exception {
		try {
			String svnUrl = "https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/CREATE1";
			File dirToBeChecked = new File("/Users/kaleeswaran/projects/2.0Projects/DotnetTest-tech-dotnet");
			String username = "kaleeswaran_s";
			String password = "Suresh@123";
			String commitMessage = "[artf590603] directory and file added";
			
			DAVRepositoryFactory.setup();
			SVNRepositoryFactoryImpl.setup();
			FSRepositoryFactory.setup();
			
			final SVNClientManager cm = SVNClientManager.newInstance(new DefaultSVNOptions(), username, password);
			SVNWCClient wcClient = cm.getWCClient();
			// This one recursively adds an existing local item under version control (schedules for addition)
			wcClient.doAdd(dirToBeChecked, true, false, false, SVNDepth.INFINITY, false, false);
			SVNCommitInfo commitInfo = cm.getCommitClient().doCommit(new File[]{dirToBeChecked}, false, commitMessage, null, null, false, true, SVNDepth.INFINITY);
			System.out.println("commitInfo => " + commitInfo.getAuthor());
			System.out.println("commitInfo revision => " + commitInfo.getNewRevision());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void convertListFilesToArray() throws Exception {
		try {
			List<String> files = new ArrayList<String>();
			files.add("/Users/kaleeswaran/workspace/projects/DrupaTest-tech-phpdru7/pom copy 2.xml");
			files.add("/Users/kaleeswaran/workspace/projects/DrupaTest-tech-phpdru7/pom copy 3.xml");
			List<File> listModifiedFiles = new ArrayList<File>(files.size());
			for (String file : files) {
				listModifiedFiles.add(new File(file));
			}
			System.out.println("Files ==> " + listModifiedFiles);
			
			File[] comittableFiles = listModifiedFiles.toArray(new File[listModifiedFiles.size()]);
			System.out.println("Array of Files ==> " + comittableFiles);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void checkString()  throws Exception {
		try {
			String msg = "svn: '/Users/kaleeswaran/workspace/projects/php-project-tech-php' is not a working copy";
			System.out.println("msg => " + msg);
			if (msg.indexOf("is not a working copy") != -1) {
				System.out.println("Found msg");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
	
