import java.io.File;
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
	public void testCheckOut() throws Exception	{
		try{
			File file =new File("D:\\chkout","sample");
		SCMManagerImpl scmi=new SCMManagerImpl();
		RepoDirRecursiveImpl rdri=new RepoDirRecursiveImpl();
//		scmi.importProject("svn","https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/" , "santhosh_ja" , "santJ!23" , null , "HEAD" , "Test1");
		scmi.importProject("git","https://github.com/kaleeswaran14/PHR_Phpblog",null,null,"master",null, "GIT");
//		rdri.importGitProject("git", "https://github.com/phresco/widget-eshop", null,null, "master", null ,"samplegit");
		Applications ap=new Applications();
	 	ap.importFromGit("https://github.com/phresco/jquery-widget-eshop.git/",file);
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
//		 	scmi.updateProject("git","https://github.com/phresco/jquery-widget-eshop.git/",null,null,"master",null, "PHR_jquerywidget");
		 	
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
	
	



