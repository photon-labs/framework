package com.photon.phresco.framework.actions.applications;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	public void gitPrivateRepoClone() throws PhrescoException {
		boolean valid = scmi.importProject("git","https://github.com/bestbuymobileapps/bby-digital-deals.git",
				"nikhilkumar-a", "phresco123", "master", null, "create");
		Assert.assertEquals(true, valid );
	}
	
//	@Test
	public void gitPublicRepoClone() throws PhrescoException {
		boolean valid = scmi.importProject("git","https://github.com/santhosh-ja/Test2.git", null, null, "master", null, "create");
		Assert.assertEquals(true, valid );
		}
	
//  @Test
	public void svnSingleFileCheckout() throws PhrescoException {
		boolean valid = scmi.importProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/phresco-test/sample applayer-tech-php/", "santhosh_ja", "santJ!23", null, "HEAD", "SVN");
		Assert.assertEquals(true, valid );
	}
	
//  @Test
	public void svnmultipleFileCheckout() throws PhrescoException {
		boolean valid = scmi.importProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/phresco-test/", "santhosh_ja", "santJ!23", null, "HEAD", "SVN");
		Assert.assertEquals(true, valid);
	}

//  @Test
	public void gitUpdate() throws PhrescoException {
			boolean valid = scmi.updateProject("git","https://github.com/santhosh-ja/Test2.git", null, null, "master", null, "create");
			Assert.assertEquals(true, valid );
	}
	
//  @Test
	public void svnUpdate() throws PhrescoException {
			 boolean valid = scmi.updateProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/phresco-test/sample applayer-tech-php/"
			 , "santhosh_ja" , "santJ!23" , null , "HEAD" , "sample applayer");
				Assert.assertEquals(true, valid );
	}
	
	//Negative Scenarios
    //Clone/Checkout a non phresco-compliance projects
//	@Test
	public void gitClonePhrescoComplianceFail() throws PhrescoException {
		boolean valid = scmi.importProject("git","https://github.com/santhosh-ja/Test.git", null, null, "master", null, "create");
		Assert.assertEquals(false, valid);
	}
	
//  @Test
	public void svnCheckoutPhrescoComplianceFail() throws PhrescoException {
		boolean valid = scmi.importProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/", "santhosh_ja", "santJ!23", null, "HEAD", "SVN");
		Assert.assertEquals(false, valid );
		}
	
	// Updating non existing files
	
//	@Test
	public void gitUpdateFails() throws PhrescoException {
		try{
			boolean valid = scmi.updateProject("git","https://github.com/santhosh-ja/Test2.git", null, null, "master", null, "create1");
		}catch(PhrescoException e){
				Assert.assertEquals(e.getLocalizedMessage() , "The system cannot find the path specified" );
			}
	}
	
//  @Test
	public void svnUpdateFails() throws PhrescoException {
		try{
			 boolean valid = scmi.updateProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/phresco-test/sample applayer-tech-php/"
			 , "santhosh_ja" , "santJ!23" , null , "HEAD" , "sample applayer1");
				Assert.assertEquals(false, valid );
		}catch(PhrescoException e){
		Assert.assertEquals(e.getLocalizedMessage() , "svn: 'C:\\Documents and Settings\\santhosh_ja\\workspace\\projects\\sample applayer1' is not a working copy" );
		}
	}
	
}
	
