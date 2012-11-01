package com.photon.phresco.framework.actions.applications;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
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
		try{
		boolean valid = scmi.importProject("git","https://github.com/bestbuymobileapps/bby-digital-deals.git",
				"nikhilkumar-a", "phresco123", "master", null, "create");
		Assert.assertEquals(true, valid );
		}catch(PhrescoException e){
			e.printStackTrace();
		}
	}
	
//	@Test
	public void gitPublicRepoClone() throws Exception {
		try{
		boolean valid = scmi.importProject("git","https://github.com/santhosh-ja/Test2.git", null, null, "master", null, "create");
		Assert.assertEquals(true, valid );
		}catch(PhrescoException e){
			e.printStackTrace();
		}
	}
	
//  @Test
	public void svnSingleFileCheckout() throws Exception {
		try{
		boolean valid = scmi.importProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/phresco-test/sample applayer-tech-php/", "santhosh_ja", "santJ!23", null, "HEAD", "SVN");
		Assert.assertEquals(true, valid );
		}catch(PhrescoException e){
			e.printStackTrace();
		}
	}
	
//  @Test
	public void svnmultipleFileCheckout() throws Exception {
	  try{
		boolean valid = scmi.importProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/phresco-test/", "santhosh_ja", "santJ!23", null, "HEAD", "SVN");
		Assert.assertEquals(true, valid );
	  }catch(PhrescoException e){
		  e.printStackTrace();
		}
	}

//  @Test
	public void gitUpdate() throws Exception {
		try {
			boolean valid = scmi.updateProject("git","https://github.com/santhosh-ja/Test2.git", null, null, "master", null, "create");
			Assert.assertEquals(true, valid );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//  @Test
	public void svnUpdate() throws Exception {
		try {
			 boolean valid = scmi.updateProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/phresco-test/sample applayer-tech-php/"
			 , "santhosh_ja" , "santJ!23" , null , "HEAD" , "sample applayer");
				Assert.assertEquals(true, valid );

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
