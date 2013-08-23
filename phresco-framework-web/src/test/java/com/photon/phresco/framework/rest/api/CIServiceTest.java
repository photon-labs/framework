package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.opensymphony.xwork2.interceptor.annotations.Before;
import com.photon.phresco.commons.model.CIJob;
import com.photon.phresco.commons.model.ContinuousDelivery;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.model.RepoDetail;

public class CIServiceTest {

	CIService ciservice = new CIService();
	
	
	private String isJenkinsAlive() throws PhrescoException {
		Response localJenkinsLocalAlive = ciservice.localJenkinsLocalAlive();
		ResponseInfo<String> response = (ResponseInfo<String>) localJenkinsLocalAlive.getEntity();
		String data = response.getData();
		return data;
	}
	
	@Test
	public void createJobTest() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServletPath("/rest/api");
		request.setContextPath("/framework");
		request.setPathInfo("/ci/create");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		ContinuousDelivery continuousDelivery = continuousDeliveryInfo();
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response createJob = ciservice.createJob(httpServletRequest, continuousDelivery, "photon", "TestProject","","admin");
			Response createJobApp = ciservice.createJob(httpServletRequest, continuousDeliveryInfoAppLevel(), "photon", null,"TestProject","admin");
			Assert.assertEquals(200, createJob.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
		
	}

	private ContinuousDelivery continuousDeliveryInfoAppLevel() {
		ContinuousDelivery continuousDelivery = new ContinuousDelivery();
		continuousDelivery.setEnvName("Production");
		continuousDelivery.setName("testContinuousDeliveryApp");
		List<CIJob> jobs = new ArrayList<CIJob>();
		CIJob buildJob = getJob();
		buildJob.setJobName("testJobApp");
		buildJob.setOperation("build");
		buildJob.setTemplateName("build");
		buildJob.setDownstreamApplication("testDeployJobApp");
		jobs.add(buildJob);
		
		CIJob deployJob = getJob();
		deployJob.setJobName("testDeployJobApp");
		deployJob.setOperation("deploy");
		deployJob.setUpstreamApplication("testJobApp");
		deployJob.setDownstreamApplication("testCodeJobApp");
		deployJob.setTemplateName("");
		jobs.add(deployJob);
		
		CIJob codeJob = getJob();
		codeJob.setJobName("testCodeJobApp");
		codeJob.setUpstreamApplication("testDeployJobApp");
		codeJob.setDownstreamApplication("testUnitJobApp");
		codeJob.setOperation("codeValidation");
		codeJob.setTemplateName("");
		jobs.add(codeJob);

		CIJob unitJob = getJob();
		unitJob.setJobName("testUnitJobApp");
		unitJob.setOperation("unittest");
		unitJob.setUpstreamApplication("testCodeJobApp");
		unitJob.setDownstreamApplication("testComponentJobApp");
		unitJob.setTemplateName("");
		jobs.add(unitJob);
		
		CIJob componentJob = getJob();
		componentJob.setJobName("testComponentJobApp");
		componentJob.setUpstreamApplication("testUnitJobApp");
		componentJob.setDownstreamApplication("testFunctionalJob");
		componentJob.setOperation("componentTest");
		componentJob.setTemplateName("");
		jobs.add(componentJob);
		
		CIJob functionalJob = getJob();
		functionalJob.setJobName("testFunctionalJobApp");
		functionalJob.setUpstreamApplication("testComponentJobApp");
		functionalJob.setDownstreamApplication("testPdfJobApp");
		functionalJob.setOperation("functionalTest");
		functionalJob.setTemplateName("");
		jobs.add(functionalJob);
		
		CIJob pdfJob = getJob();
		pdfJob.setJobName("testPdfJobApp");
		pdfJob.setUpstreamApplication("testFunctionalJobApp");
		pdfJob.setDownstreamApplication("testPerformanceJobApp");
		pdfJob.setOperation("pdfReport");
		pdfJob.setTemplateName("");
		jobs.add(pdfJob);
		
		CIJob performanceJob = getJob();
		performanceJob.setJobName("testPerformanceJobApp");
		performanceJob.setUpstreamApplication("testPdfJobApp");
		performanceJob.setDownstreamApplication("testLoadJobApp");
		performanceJob.setOperation("performanceTest");
		performanceJob.setTemplateName("");
		jobs.add(performanceJob);
		
		CIJob loadJob = getJob();
		loadJob.setJobName("testLoadJobApp");
		loadJob.setUpstreamApplication("testPerformanceJobApp");
		loadJob.setOperation("loadTest");
		loadJob.setTemplateName("");
		jobs.add(loadJob);
		
		
		continuousDelivery.setJobs(jobs);
		return continuousDelivery;
	}
	
	private ContinuousDelivery continuousDeliveryInfo() {
		ContinuousDelivery continuousDelivery = new ContinuousDelivery();
		continuousDelivery.setEnvName("Production");
		continuousDelivery.setName("testContinuousDelivery");
		List<CIJob> jobs = new ArrayList<CIJob>();
		CIJob buildJob = getJob();
		buildJob.setJobName("testJob");
		buildJob.setOperation("build");
		buildJob.setTemplateName("buildJob");
		buildJob.setDownstreamApplication("testDeployJob");
		jobs.add(buildJob);
		
		CIJob deployJob = getJob();
		deployJob.setJobName("testDeployJob");
		deployJob.setOperation("deploy");
		deployJob.setUpstreamApplication("testJob");
		deployJob.setDownstreamApplication("testCodeJob");
		deployJob.setTemplateName("");
		jobs.add(deployJob);
		
		CIJob codeJob = getJob();
		codeJob.setJobName("testCodeJob");
		codeJob.setUpstreamApplication("testDeployJob");
		codeJob.setDownstreamApplication("testUnitJob");
		codeJob.setOperation("codeValidation");
		codeJob.setTemplateName("");
		jobs.add(codeJob);

		CIJob unitJob = getJob();
		unitJob.setJobName("testUnitJob");
		unitJob.setOperation("unittest");
		unitJob.setCoberturaPlugin(true);
		unitJob.setUpstreamApplication("testCodeJob");
		unitJob.setDownstreamApplication("testComponentJob");
		unitJob.setTemplateName("");
		jobs.add(unitJob);
		
		CIJob componentJob = getJob();
		componentJob.setJobName("testComponentJob");
		componentJob.setUpstreamApplication("testUnitJob");
		componentJob.setDownstreamApplication("testFunctionalJob");
		componentJob.setOperation("componentTest");
		componentJob.setTemplateName("");
		jobs.add(componentJob);
		
		CIJob functionalJob = getJob();
		functionalJob.setJobName("testFunctionalJob");
		functionalJob.setUpstreamApplication("testComponentJob");
		functionalJob.setDownstreamApplication("testPdfJob");
		functionalJob.setOperation("functionalTest");
		functionalJob.setTemplateName("");
		jobs.add(functionalJob);
		
		CIJob pdfJob = getJob();
		pdfJob.setJobName("testPdfJob");
		pdfJob.setUpstreamApplication("testFunctionalJob");
		pdfJob.setDownstreamApplication("testPerformanceJob");
		pdfJob.setOperation("pdfReport");
		pdfJob.setTemplateName("");
		jobs.add(pdfJob);
		
		CIJob performanceJob = getJob();
		performanceJob.setJobName("testPerformanceJob");
		performanceJob.setUpstreamApplication("testPdfJob");
		performanceJob.setDownstreamApplication("testLoadJob");
		performanceJob.setOperation("performanceTest");
		performanceJob.setTemplateName("");
		jobs.add(performanceJob);
		
		CIJob loadJob = getJob();
		loadJob.setJobName("testLoadJob");
		loadJob.setUpstreamApplication("testPerformanceJob");
		loadJob.setOperation("loadTest");
		loadJob.setTemplateName("");
		jobs.add(loadJob);
		
		
		continuousDelivery.setJobs(jobs);
		return continuousDelivery;
	}

	private CIJob getJob() {
		CIJob cijob = new CIJob();
		cijob.setUrl("gosdghsdah");
		cijob.setUsername("admin");
		cijob.setPassword("manage");
		cijob.setRepoType("svn");
		cijob.setCloneWorkspace(true);
		cijob.setDownStreamCriteria("success");
		cijob.setOperation("build");
		cijob.setBuildName("testBuild");
		cijob.setEnvironmentName("Production");
		cijob.setAppDirName("TestProject");
		return cijob;
	}
	
	@Test
	public void createBuildTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response build = ciservice.build("testJob", "TestProject", "", "testContinuousDelivery");
			Response buildApp = ciservice.build("testJobApp", null, "TestProject", "testContinuousDeliveryApp");
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) build.getEntity();
			Assert.assertEquals(200, build.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void jobValidationTest() throws PhrescoException, ClientProtocolException, JSONException, IOException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response isJobAvailable = ciservice.jobNameValidataion("testJob", "add", "admin");
			Response jobAvailable = ciservice.jobNameValidataion("testJobbuild", "add", "admin");
			Response jobAvailable1 = ciservice.jobNameValidataion("", "add", "admin");
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) isJobAvailable.getEntity();
			Assert.assertEquals(200, isJobAvailable.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void getContinuousDeliveryTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response continuousDeliveryJob = ciservice.getContinuousDeliveryJob("TestProject", "");
			Response continuousDeliveryJobApp = ciservice.getContinuousDeliveryJob("", "TestProject");
			Response cdJob = ciservice.getContinuousDeliveryJob("", "");
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) continuousDeliveryJob.getEntity();
			Assert.assertEquals(200, continuousDeliveryJob.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void getBuildsTest() throws PhrescoException {
		
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response builds = ciservice.getBuilds("TestProject", "", "testJob", "testContinuousDelivery");
			Response buildsApp = ciservice.getBuilds("", "TestProject", "testJobApp", "testContinuousDeliveryApp");
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) builds.getEntity();
			Assert.assertEquals(200, builds.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void getDownloadBuildTest() throws PhrescoException {
		String url = "do_not_checkin/build/PHR1_07-Aug-2013-11-49-40.zip";	
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response builds = ciservice.CIBuildDownload(url, "testJob", "photon", "TestProject", "", "testContinuousDelivery");
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) builds.getEntity();
			Assert.assertEquals(200, builds.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void setConfluenceConfigTest() throws PhrescoException {
		
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			List<RepoDetail> repodetails = new ArrayList<RepoDetail>();
			RepoDetail repo = new RepoDetail();
			repo.setRepoUrl("test");
			repo.setUserName("admin");
			repo.setPassword("manage");
			repodetails.add(repo);
			
			RepoDetail repoDetail = new RepoDetail();
			repoDetail.setRepoUrl("test1");
			repoDetail.setUserName("admin");
			repoDetail.setPassword("manage");
			repodetails.add(repoDetail);
			
			Response builds = ciservice.setGlobalConfiguration(repodetails, "test@gmail.com", "manage");
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) builds.getEntity();
			Assert.assertEquals(200, builds.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void getEmailConfigurationTest() throws PhrescoException {
		
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response emailConfig = ciservice.getEmailConfiguration();
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) emailConfig.getEntity();
			Assert.assertEquals(200, emailConfig.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void getConfluenceTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response confluenceConfig = ciservice.getConfluenceConfiguration();
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) confluenceConfig.getEntity();
			Assert.assertEquals(200, confluenceConfig.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void deleteBuildsTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response deleteBuilds = ciservice.deleteBuilds("1", "testJob", "photon", "TestProject", "", "testContinuousDelivery");
			Response deleteBuildsApp = ciservice.deleteBuilds("1", "testJobApp", "photon", "", "TestProject", "testContinuousDeliveryApp");
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) deleteBuilds.getEntity();
			Assert.assertEquals(200, deleteBuilds.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void cloneTest() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServletPath("/rest/api");
		request.setContextPath("/framework");
		request.setPathInfo("/ci/clone");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response createClone = ciservice.createClone(httpServletRequest, "testContinuousDelivery1", "Testing", "testContinuousDelivery", "Photon", "TestProject", "", "admin");
			ciservice.createClone(httpServletRequest, "testContinuousDeliveryApp", "Testing", "testContinuousDeliveryApp", "Photon", "", "TestProject", "admin");
			Assert.assertEquals(200, createClone.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void updateJob() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServletPath("/rest/api");
		request.setContextPath("/framework");
		request.setPathInfo("/ci/update");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			ContinuousDelivery continuousDelivery = continuousDeliveryInfoUpdate();
			Response updateJob = ciservice.updateJob(httpServletRequest, continuousDelivery, "Photon", "TestProject", "", "admin");
			ciservice.updateJob(httpServletRequest, continuousDeliveryInfoUpdateApp(), "Photon", "", "TestProject", "admin");
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
		cijob.setTemplateName("buildJob");
		cijob.setBuildName("testBuild");
		cijob.setEnvironmentName("Production");
		cijob.setAppDirName("TestProject");
		jobs.add(cijob);
		
		CIJob cijob1 = new CIJob();
		cijob1.setJobName("testJobPdf");
		cijob1.setUrl("gosdghsdah");
		cijob1.setUsername("admin");
		cijob1.setPassword("manage");
		cijob1.setRepoType("svn");
		cijob1.setCloneWorkspace(true);
		cijob1.setDownStreamCriteria("success");
		cijob1.setOperation("pdfReport");
		cijob1.setBuildName("testBuild");
		cijob1.setTemplateName("pdfJob");
		cijob1.setEnvironmentName("Production");
		cijob1.setAppDirName("TestProject");
		jobs.add(cijob1);
		
		continuousDelivery.setJobs(jobs);
		return continuousDelivery;
	}
	
	private ContinuousDelivery continuousDeliveryInfoUpdateApp() {
		ContinuousDelivery continuousDelivery = new ContinuousDelivery();
		continuousDelivery.setEnvName("Production");
		continuousDelivery.setName("testContinuousDeliveryApp");
		List<CIJob> jobs = new ArrayList<CIJob>();
		CIJob cijob = new CIJob();
		cijob.setJobName("testJobApp");
		cijob.setUrl("ggggg");
		cijob.setUsername("admin");
		cijob.setPassword("manage");
		cijob.setRepoType("svn");
		cijob.setCloneWorkspace(true);
		cijob.setDownStreamCriteria("success");
		cijob.setOperation("build");
		cijob.setTemplateName("buildJob");
		cijob.setBuildName("testBuild");
		cijob.setEnvironmentName("Production");
		cijob.setAppDirName("TestProject");
		jobs.add(cijob);
		
		CIJob cijob1 = new CIJob();
		cijob1.setJobName("testJobPdfApp");
		cijob1.setUrl("gosdghsdah");
		cijob1.setUsername("admin");
		cijob1.setPassword("manage");
		cijob1.setRepoType("svn");
		cijob1.setCloneWorkspace(true);
		cijob1.setDownStreamCriteria("success");
		cijob1.setOperation("pdfReport");
		cijob1.setBuildName("testBuild");
		cijob1.setTemplateName("pdfJob");
		cijob1.setEnvironmentName("Production");
		cijob1.setAppDirName("TestProject");
		jobs.add(cijob1);
		
		continuousDelivery.setJobs(jobs);
		return continuousDelivery;
	}
	
	@Test
	public void editContinuousDeliveryTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response editContinuousView = ciservice.editContinuousView("TestProject", "testContinuousDelivery", "");
			Response editContinuousViewApp = ciservice.editContinuousView("", "testContinuousDeliveryApp", "TestProject");
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
			ciservice.getStatus("testJobApp", "testContinuousDeliveryApp", "", "TestProject");
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
			ciservice.delete("testContinuousDeliveryApp", "photon", "", "TestProject");
			Response deleteApp = ciservice.delete("testContinuousDeliveryApp", "photon", null, "TestProject");
			
			Assert.assertEquals(200, delete.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
}
