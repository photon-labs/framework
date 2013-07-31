package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.opensymphony.xwork2.interceptor.annotations.Before;
import com.photon.phresco.commons.model.CIJob;
import com.photon.phresco.commons.model.ContinuousDelivery;
import com.photon.phresco.exception.PhrescoException;

public class CIServiceTest {

	CIService ciservice = new CIService();
	
	
	private String isJenkinsAlive() throws PhrescoException {
		Response localJenkinsLocalAlive = ciservice.localJenkinsLocalAlive();
		ResponseInfo<String> response = (ResponseInfo<String>) localJenkinsLocalAlive.getEntity();
		String data = response.getData();
		return data;
	}
	
	@Test
	public void creaJobTest() throws PhrescoException {
		ContinuousDelivery continuousDelivery = continuousDeliveryInfo();
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response createJob = ciservice.createJob(continuousDelivery, "photon", "TestProject","");
			Assert.assertEquals(200, createJob.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
		
	}

	private ContinuousDelivery continuousDeliveryInfo() {
		ContinuousDelivery continuousDelivery = new ContinuousDelivery();
		continuousDelivery.setEnvName("Production");
		continuousDelivery.setName("testContinuousDelivery");
		List<CIJob> jobs = new ArrayList<CIJob>();
		CIJob cijob = new CIJob();
		cijob.setJobName("testJob");
		cijob.setUrl("gosdghsdah");
		cijob.setUsername("admin");
		cijob.setPassword("manage");
		//cijob.setMvnCommand("-Pci clean phresco:package -N");
		//cijob.setEnableBuildRelease(false);
		//cijob.setPomLocation("pom.xml");
		//cijob.setEnablePostBuildStep(false);
		//cijob.setEnablePreBuildStep(true);
		//List<String> prebuildStepCommands = new ArrayList<String>();
		//prebuildStepCommands.add("phresco:ci-prestep -DjobName\u003d${env.JOB_NAME} -Dgoal\u003dci -Dphase\u003dpackage -DcreationType\u003dglobal -Did\u003da9c3885c-381a-42aa-88ac-03debca283be -DcontinuousDeliveryName\u003dTestPh -N");
		
		//cijob.setPrebuildStepCommands(prebuildStepCommands);
		cijob.setRepoType("svn");
		cijob.setCloneWorkspace(true);
		cijob.setDownStreamCriteria("success");
		cijob.setOperation("build");
		//cijob.setBuildNumber("1");
		cijob.setBuildName("testBuild");
		cijob.setEnvironmentName("Production");
		cijob.setAppDirName("TestProject");
		//cijob.setJenkinsUrl("localhost");
		//cijob.setJenkinsPort("3579");
		jobs.add(cijob);
		
		CIJob cijob1 = new CIJob();
		cijob1.setJobName("testJob1");
		cijob1.setUrl("gosdghsdah");
		cijob1.setUsername("admin");
		cijob1.setPassword("manage");
		cijob1.setRepoType("svn");
		cijob1.setCloneWorkspace(true);
		cijob1.setDownStreamCriteria("success");
		cijob1.setOperation("build");
		//cijob.setBuildNumber("1");
		cijob1.setBuildName("testBuild");
		cijob1.setEnvironmentName("Production");
		cijob1.setAppDirName("TestProject");
		//cijob.setJenkinsUrl("localhost");
		//cijob.setJenkinsPort("3579");
		jobs.add(cijob1);
		
		continuousDelivery.setJobs(jobs);
		return continuousDelivery;
	}
	
	@Test
	public void createBuildTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response build = ciservice.build("testJob", "TestProject", "", "testContinuousDelivery");
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) build.getEntity();
			Assert.assertEquals(200, build.getStatus());
			assertEquals("Build Triggered success", responseInfo.getMessage());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void getContinuousDeliveryTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response continuousDeliveryJob = ciservice.getContinuousDeliveryJob("TestProject", "");
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) continuousDeliveryJob.getEntity();
			Assert.assertEquals(200, continuousDeliveryJob.getStatus());
			assertEquals("Continuous Delivery List Successfully", responseInfo.getMessage());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void getBuildsTest() throws PhrescoException {
		
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response builds = ciservice.getBuilds("TestProject", "", "testJob", "testContinuousDelivery");
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) builds.getEntity();
			Assert.assertEquals(200, builds.getStatus());
			assertEquals("Builds returned successfully", responseInfo.getMessage());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void deleteBuildsTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response deleteBuilds = ciservice.deleteBuilds("1", "testJob", "photon", "TestProject", "", "testContinuousDelivery");
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) deleteBuilds.getEntity();
			Assert.assertEquals(200, deleteBuilds.getStatus());
			assertEquals("Build deleted successfully", responseInfo.getMessage());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void cloneTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response createClone = ciservice.createClone("testContinuousDelivery1", "Testing", "testContinuousDelivery", "Photon", "TestProject", "");
			Assert.assertEquals(200, createClone.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void updateJob() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			ContinuousDelivery continuousDelivery = continuousDeliveryInfoUpdate();
			Response updateJob = ciservice.updateJob(continuousDelivery, "Photon", "TestProject", "");
			Assert.assertEquals(200, updateJob.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	private ContinuousDelivery continuousDeliveryInfoUpdate() {
		ContinuousDelivery continuousDelivery = new ContinuousDelivery();
		continuousDelivery.setEnvName("Production");
		continuousDelivery.setName("testContinuousDelivery");
		List<CIJob> jobs = new ArrayList<CIJob>();
		CIJob cijob = new CIJob();
		cijob.setJobName("testJob");
		cijob.setUrl("ggggg");
		cijob.setUsername("admin");
		cijob.setPassword("manage");
		cijob.setRepoType("svn");
		cijob.setCloneWorkspace(true);
		cijob.setDownStreamCriteria("success");
		cijob.setOperation("build");
		//cijob.setBuildNumber("1");
		cijob.setBuildName("testBuild");
		cijob.setEnvironmentName("Production");
		cijob.setAppDirName("TestProject");
		//cijob.setJenkinsUrl("localhost");
		//cijob.setJenkinsPort("3579");
		jobs.add(cijob);
		
		CIJob cijob1 = new CIJob();
		cijob1.setJobName("testJob2");
		cijob1.setUrl("gosdghsdah");
		cijob1.setUsername("admin");
		cijob1.setPassword("manage");
		cijob1.setRepoType("svn");
		cijob1.setCloneWorkspace(true);
		cijob1.setDownStreamCriteria("success");
		cijob1.setOperation("build");
		//cijob.setBuildNumber("1");
		cijob1.setBuildName("testBuild");
		cijob1.setEnvironmentName("Production");
		cijob1.setAppDirName("TestProject");
		//cijob.setJenkinsUrl("localhost");
		//cijob.setJenkinsPort("3579");
		jobs.add(cijob1);
		
		continuousDelivery.setJobs(jobs);
		return continuousDelivery;
	}
	
	@Test
	public void editContinuousDeliveryTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response editContinuousView = ciservice.editContinuousView("TestProject", "testContinuousDelivery", "");
			Assert.assertEquals(200, editContinuousView.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void getStatusTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			ciservice.getStatus("testJob", "testContinuousDelivery", "TestProject", "");
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void deleteContinuousDeliveryTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response delete = ciservice.delete("testContinuousDelivery", "photon", "TestProject", "");
			ciservice.delete("testContinuousDelivery1", "photon", "TestProject", "");
			Assert.assertEquals(200, delete.getStatus());
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) delete.getEntity();
			assertEquals("Job deleted successfully", responseInfo.getMessage());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
}
