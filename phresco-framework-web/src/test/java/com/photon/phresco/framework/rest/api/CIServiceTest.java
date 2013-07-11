package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.model.CIJob;
import com.photon.phresco.framework.model.ContinuousDelivery;

public class CIServiceTest {

	CIService ciservice = new CIService();
	
	
	
	@Test
	public void creaJobTest() throws PhrescoException {
		ContinuousDelivery continuousDelivery = continuousDeliveryInfo();
		Response createJob = ciservice.createJob(continuousDelivery, "photon", "testProjectId","","");
		Assert.assertEquals(200, createJob.getStatus());
	}

	private ContinuousDelivery continuousDeliveryInfo() {
		ContinuousDelivery continuousDelivery = new ContinuousDelivery();
		continuousDelivery.setEnvironment("Production");
		continuousDelivery.setName("testContinuousDelivery");
		List<CIJob> jobs = new ArrayList<CIJob>();
		CIJob cijob = new CIJob();
		cijob.setJobName("testJob");
		cijob.setUrl("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/php1-php5.4.x/");
		cijob.setUsername("admin");
		cijob.setPassword("manage");
		cijob.setRepoType("svn");
		cijob.setCloneWorkspace(true);
		cijob.setDownStreamCriteria("success");
		cijob.setOperation("build");
		cijob.setBuildNumber("1");
		cijob.setBuildName("testBuild");
		cijob.setEnvironmentName("Production");
		cijob.setAppName("testApp");
		cijob.setJenkinsUrl("localhost");
		cijob.setJenkinsPort("3579");
		jobs.add(cijob);
		continuousDelivery.setJobs(jobs);
		return continuousDelivery;
	}
	
	@Test
	public void createBuildTest() throws PhrescoException {
		Response build = ciservice.build("testJob", "testProjectId", null);
		ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) build.getEntity();
		Assert.assertEquals(200, build.getStatus());
		assertEquals("Build successfully", responseInfo.getMessage());
	}
	
	@Test
	public void getContinuousDeliveryTest() throws PhrescoException {
		Response continuousDeliveryJob = ciservice.getContinuousDeliveryJob("testProjectId", "");
		ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) continuousDeliveryJob.getEntity();
		Assert.assertEquals(200, continuousDeliveryJob.getStatus());
		assertEquals("Continuous Delivery List Successfully", responseInfo.getMessage());
	}
	
	@Test
	public void getBuildsTest() throws PhrescoException {
		Response builds = ciservice.getBuilds("testProjectId", null, "testJob");
		ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) builds.getEntity();
		Assert.assertEquals(200, builds.getStatus());
		assertEquals("Builds returned successfully", responseInfo.getMessage());
	}
	
	@Test
	public void deleteBuildsTest() throws PhrescoException {
		Response deleteBuilds = ciservice.deleteBuilds("1", "testJob", "photon", "testProjectId", null);
		ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) deleteBuilds.getEntity();
		Assert.assertEquals(200, deleteBuilds.getStatus());
		assertEquals("Build deleted successfully", responseInfo.getMessage());
	}
	
	@Test
	public void deleteContinuousDeliveryTest() throws PhrescoException {
		Response delete = ciservice.delete("testContinuousDelivery", "photon", "testProjectId", "");
		Assert.assertEquals(200, delete.getStatus());
		ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) delete.getEntity();
		assertEquals("Job deleted successfully", responseInfo.getMessage());
	}
}
