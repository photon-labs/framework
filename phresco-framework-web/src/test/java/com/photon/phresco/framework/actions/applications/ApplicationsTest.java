package com.photon.phresco.framework.actions.applications;

import java.io.IOException;

import junit.framework.Assert;

import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tmatesoft.svn.core.SVNException;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.impl.SCMManagerImpl;

public class ApplicationsTest {
	SCMManagerImpl scmi = new SCMManagerImpl();

	@Before
	public void setUp() throws Exception {
		System.out.println("Set up!!!!");
	}

	@After
	public void tearDown() throws Exception {
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
		}catch(PhrescoException e){
				Assert.assertEquals(e.getLocalizedMessage() , "The system cannot find the path specified" );
			}
	}
	
//  @Test
	public void svnUpdateFails() throws Exception {
		try{
			 boolean valid = scmi.updateProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/phresco-test/sample applayer-tech-php/"
			 , "santhosh_ja" , "santJ!23" , null , "HEAD" , "sample applayer1");
				Assert.assertEquals(false, valid );
		}catch(PhrescoException e){
		Assert.assertEquals(e.getLocalizedMessage() , "svn: 'C:\\Documents and Settings\\santhosh_ja\\workspace\\projects\\sample applayer1' is not a working copy" );
		}
	}
	
//SVN can checkout git url!!!	
//  @Test
	public void svnNamedGitUrl() throws Exception {
		boolean valid = scmi.importProject("svn","https://github.com/santhosh-ja/Test2.git", null, null, null, "HEAD");
		Assert.assertEquals(true, valid );
	}
	
}
	
