package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;
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
import com.photon.phresco.framework.model.GlobalSettings;
import com.photon.phresco.framework.model.RepoDetail;
import com.photon.phresco.framework.model.TestFlight;
import com.phresco.pom.exception.PhrescoPomException;

public class CIServiceTest extends RestBaseTest {

	CIService ciservice = new CIService();
	
	
	private String isJenkinsAlive() throws PhrescoException {
		Response localJenkinsLocalAlive = ciservice.localJenkinsLocalAlive();
		ResponseInfo<String> response = (ResponseInfo<String>) localJenkinsLocalAlive.getEntity();
		String data = response.getData();
		return data;
	}
	
	@Test
	public void getJenkinsUrl() throws PhrescoException, UnknownHostException {
		Response build = ciservice.getJenkinsUrl();
	}
	
	@Test
	public void createJobTest() throws PhrescoException, PhrescoPomException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServletPath("/rest/api");
		request.setContextPath("/framework");
		request.setPathInfo("/ci/create");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		ContinuousDelivery continuousDelivery = continuousDeliveryInfo();
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response createJob = ciservice.createJob(httpServletRequest, continuousDelivery, "photon", "TestProject","", userId,"");
			Response createJobApp = ciservice.createJob(httpServletRequest, continuousDeliveryInfoAppLevel(), "photon", null,"TestProject",userId,"");
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
		cijob.setUsername(userId);
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
			Response build = ciservice.build("testJob", "TestProject", "", "", "testContinuousDelivery", "photon");
			Response buildApp = ciservice.build("testJobApp", null, "TestProject", "", "testContinuousDeliveryApp", "photon");
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
			Response isJobAvailable = ciservice.jobNameValidataion("testJob", "add", userId);
			Response jobAvailable = ciservice.jobNameValidataion("testJobbuild", "add", userId);
			Response jobAvailable1 = ciservice.jobNameValidataion("", "add", userId);
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
			Response continuousDeliveryJob = ciservice.getContinuousDeliveryJob("TestProject", "", "", "photon");
			Response continuousDeliveryJobApp = ciservice.getContinuousDeliveryJob("", "TestProject", "","photon");
//			Response cdJob = ciservice.getContinuousDeliveryJob("", "", "photon");
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
			Response builds = ciservice.getBuilds("TestProject", "", "testJob", "testContinuousDelivery", "photon","");
			Response buildsApp = ciservice.getBuilds("", "TestProject", "testJobApp", "testContinuousDeliveryApp", "photon","");
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) builds.getEntity();
			Assert.assertEquals(200, builds.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void getBuildStatusTest() throws PhrescoException {
		
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response lastBuildStatus = ciservice.getLastBuildStatus("testJob", "testContinuousDelivery", "TestProject", "", "", "photon");
			Response buildsApp = ciservice.getBuilds("", "TestProject", "testJobApp", "testContinuousDeliveryApp","","");
//			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) builds.getEntity();
			Assert.assertEquals(200, lastBuildStatus.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	
	@Test
	public void getDownloadBuildTest() throws PhrescoException {
		String url = "do_not_checkin/build/PHR1_07-Aug-2013-11-49-40.zip";	
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response builds = ciservice.CIBuildDownload(url, "testJob", "photon", "TestProject", "", "testContinuousDelivery","");
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
			GlobalSettings gs = new GlobalSettings();
			List<RepoDetail> repodetails = new ArrayList<RepoDetail>();
			RepoDetail repo = new RepoDetail();
			repo.setRepoUrl("test");
			repo.setUserName(userId);
			repo.setPassword(password);
			repodetails.add(repo);
			
			RepoDetail repoDetail = new RepoDetail();
			repoDetail.setRepoUrl("test1");
			repoDetail.setUserName(userId);
			repoDetail.setPassword(password);
			repodetails.add(repoDetail);
			
			gs.setRepoDetails(repodetails);
			
			List<TestFlight> testFlightConfigs = new ArrayList<TestFlight>();
			TestFlight testFlight = new TestFlight();
			testFlight.setTokenPairName("test");
			testFlight.setApiToken("testApiToken");
			testFlight.setTeamToken("testTeamToken");
			testFlightConfigs.add(testFlight);
			gs.setTestFlight(testFlightConfigs);
			Response builds = ciservice.setGlobalConfiguration(gs, "test@gmail.com", "manage", "", "", "", "tfsUrl");
//			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) builds.getEntity();
//			Assert.assertEquals(200, builds.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void getEmailConfigurationTest() throws PhrescoException {
		
			Response emailConfig = ciservice.getEmailConfiguration();
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) emailConfig.getEntity();
			Assert.assertEquals(200, emailConfig.getStatus());
	}
	
	@Test
	public void getTfsConfigurationTest() throws PhrescoException {
		
			 Response tfsSystemConfig = ciservice.getTfsSystemConfig();
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) tfsSystemConfig.getEntity();
			Assert.assertEquals(200, tfsSystemConfig.getStatus());
	}
	
	@Test
	public void getConfluenceTest() throws PhrescoException {
			Response confluenceConfig = ciservice.getConfluenceConfiguration();
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) confluenceConfig.getEntity();
			Assert.assertEquals(200, confluenceConfig.getStatus());
	}
	

	@Test
	public void getTestFlightConfigurationTest() throws PhrescoException {
			Response testFlightConfig = ciservice.getTestFlightConfiguration();
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) testFlightConfig.getEntity();
			Assert.assertEquals(200, testFlightConfig.getStatus());
	}
	
	@Test
	public void deleteBuildsTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response deleteBuilds = ciservice.deleteBuilds("1", "testJob", "photon", "TestProject", "", "", "testContinuousDelivery");
			Response deleteBuildsApp = ciservice.deleteBuilds("1", "testJobApp", "photon", "", "TestProject", "", "testContinuousDeliveryApp");
			ResponseInfo<CIService> responseInfo =  (ResponseInfo<CIService>) deleteBuilds.getEntity();
			Assert.assertEquals(200, deleteBuilds.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void pipeLineValidateTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			ciservice.pipeLineValidation("testContinuousDelivery1","TestProject", "","photon","");
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
			Response createClone = ciservice.createClone(httpServletRequest, "testContinuousDelivery1", "Testing","", "testContinuousDelivery", "Photon", "TestProject", "", userId,"");
			ciservice.createClone(httpServletRequest, "testContinuousDeliveryApp", "Testing", "","testContinuousDeliveryApp", "Photon", "", "TestProject", userId,"");
			Assert.assertEquals(200, createClone.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void updateJob() throws PhrescoException, ClientProtocolException, IOException, JSONException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServletPath("/rest/api");
		request.setContextPath("/framework");
		request.setPathInfo("/ci/update");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			ContinuousDelivery continuousDelivery = continuousDeliveryInfoUpdate();
			Response updateJob = ciservice.updateJob(httpServletRequest, continuousDelivery, "Photon", "TestProject", "", userId, "testContinuousDelivery","","");
//			ciservice.updateJob(httpServletRequest, continuousDeliveryInfoUpdateApp(), "Photon", "", "TestProject", "admin","","");
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
		cijob.setUsername(userId);
		cijob.setPassword(password);
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
		cijob1.setUsername(userId);
		cijob1.setPassword(password);
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
		cijob.setUsername(userId);
		cijob.setPassword(password);
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
		cijob1.setUsername(userId);
		cijob1.setPassword(password);
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
			Response editContinuousView = ciservice.editContinuousView("TestProject", "testContinuousDelivery", "", "photon","");
			Response editContinuousViewApp = ciservice.editContinuousView("", "testContinuousDeliveryApp", "TestProject", "photon","");
			Assert.assertEquals(200, editContinuousView.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void getStatusTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			ciservice.getStatus("testJob", "testContinuousDelivery", "TestProject", "", "", "photon");
			ciservice.getStatus("testJobApp", "testContinuousDeliveryApp", "", "TestProject", "", "photon");
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
	@Test
	public void deleteContinuousDeliveryTest() throws PhrescoException {
		String jenkinsAlive = isJenkinsAlive();
		if(jenkinsAlive.equals("200")) {
			Response delete = ciservice.delete("testContinuousDelivery", "photon", "TestProject", "","");
//			ciservice.delete("testContinuousDelivery1", "photon", "TestProject", "","");
//			ciservice.delete("testContinuousDeliveryApp", "photon", "", "TestProject","");
//			Response deleteApp = ciservice.delete("testContinuousDeliveryApp", "photon", null, "TestProject","");
			
			Assert.assertEquals(200, delete.getStatus());
		} else {
			Assert.assertNotSame("200", jenkinsAlive);
		}
	}
	
}
