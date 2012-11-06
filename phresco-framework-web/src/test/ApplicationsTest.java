import java.io.File;

import com.itextpdf.text.log.SysoLogger;
import com.photon.phresco.framework.impl.SCMManagerImpl;
import com.photon.phresco.framework.actions.applications.Applications;

import com.photon.phresco.framework.impl.RepoDirRecursiveImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.SVNAccessor;
import com.photon.phresco.framework.actions.applications.Applications;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.phresco.pom.util.PomProcessor;


public class ApplicationsTest {
	
	@Before
	public void setUp() throws Exception {
		System.out.println("Set up!!!!");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tear down!!!!");
	}
	
//	@Test
	public void isValidDirCheck() {
		try {
			File dir = new File("C:\\Documents and Settings\\santhosh_ja\\workspace\\temp\\19880bb7-80a6-4635-8526-72602d032b5d");
//			File dir = new File("C:\\Documents and Settings\\santhosh_ja\\workspace\\temp\\DrupalEshop");
			SCMManagerImpl scmi = new SCMManagerImpl();
			boolean status = scmi.validPhrecoDirCheck(dir);
			if (status) {
				System.out.println("definition found... coping to workspace ");
				scmi.importFromTemp(dir);
			} else {
				System.out.println("Definition not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testCheckOut() throws Exception	{
		try{
		File file =new File("D:\\chkout","sample");
		SCMManagerImpl scmi=new SCMManagerImpl();
//		RepoDirRecursiveImpl rdri=new RepoDirRecursiveImpl();
		scmi.importProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/phresco-test" , "santhosh_ja" , "santJ!23" , null , "HEAD" , "SVN1");
//		scmi.importProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/2.0/phresco-test/sample applayer-tech-php" , "santhosh_ja" , "santJ!23" , null , "HEAD" , "SVN1");
//		scmi.importProject("git","https://github.com/santhosh-ja/Test.git",null,null,"master",null, "GIT");
//		scmi.importProject("git", "https://github.com/phresco/drupal7-eshop.git", null,null, "master", null ,"samplegit");
//		Applications ap=new Applications();
//	 	ap.importFromGit("https://github.com/phresco/jquery-widget-eshop.git/",file);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
		
//		@Test
		public void testupdate() throws Exception	{
			try{
				
			SCMManagerImpl scmi=new SCMManagerImpl();
//			scmi.updateProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/1.0.0/PHR_AndroidHybrid_PilotProject/" , "santhosh_ja" , "santJ!23" , null , "HEAD" , "Sample");
		 	scmi.updateProject("git","https://github.com/santhosh-ja/Test.git",null,null,"master",null, "sample applayer-tech-php");
		 	
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
//		String repositoryUrl="https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/1.2.3.4002/PHR_Drupal7eshop/";
//		File checkOutDir=new File("D:\\ckhout");
//		SVNAccessor svnAccessor=new SVNAccessor(repositoryUrl,"santhosh_ja","santJ!23");
//		svnAccessor.checkout(checkOutDir, null, true, "PHPDrupal");
//		updateSCMConnection("PHPDrupal", repositoryUrl);
	}
	
//	private void updateSCMConnection(String projCode, String repoUrl) throws Exception {
////        S_LOGGER.debug("Entering Method  Applications.updateSCMConnection()");
//        FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
//        PomProcessor processor = frameworkUtil.getPomProcessor(projCode);
//        if (processor.getSCM() == null) {
////            S_LOGGER.debug("processor.getSCM() exists and repo url " + repoUrl);
//            processor.setSCM(repoUrl, "", "", "");
//            processor.save();
//            
//        }
    }
	
	



