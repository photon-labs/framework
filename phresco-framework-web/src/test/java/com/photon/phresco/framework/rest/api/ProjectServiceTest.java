package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.opensymphony.xwork2.interceptor.annotations.Before;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroupInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.SelectedFeature;
import com.photon.phresco.commons.model.TechnologyInfo;

public class ProjectServiceTest extends LoginServiceTest {
	
	@Test
	public void createProjectTest() {
		ProjectInfo projectInfo = createProjectInfo();
		ProjectService projectService = new ProjectService();
		Response response = projectService.createProject(projectInfo, userId);
		
		assertEquals(200 , response.getStatus());
	}
	
	@Test
	public void listProjectsTest() {
		ProjectService projectService = new ProjectService();
		Response response = projectService.list(customerId);
		ResponseInfo<ProjectInfo> responseInfo =  (ResponseInfo<ProjectInfo>) response.getEntity();
		
		assertEquals(200, response.getStatus());
		assertEquals("Project List Successfully", responseInfo.getMessage());
	}
	
	@Test
	public void editProjectTest() {
		ProjectService projectService = new ProjectService();
		Response response = projectService.editProject("TestProject", customerId);
		ResponseInfo<ProjectInfo> entity = (ResponseInfo<ProjectInfo>) response.getEntity();
		
		assertEquals(200 , response.getStatus());
		assertEquals("Project edited Successfully", entity.getMessage());
	}
	
	@Test
	public void updateProjectTest() {
		ProjectService projectService = new ProjectService();
		ProjectInfo projectInfo = createProjectInfo();
		Response response = projectService.updateProject(projectInfo, userId);
		ResponseInfo<ProjectInfo> resopnseInfo = (ResponseInfo<ProjectInfo>) response.getEntity();
		assertEquals(200 , response.getStatus());
		assertEquals("Project updated Successfully", resopnseInfo.getMessage());
	}
	
	@Test
	public void updateApplicationTest() {
		ProjectService service = new ProjectService();
		ApplicationInfo applicationInfo = createProjectInfo().getAppInfos().get(0);
		List<ArtifactGroupInfo> selectedServers = new ArrayList<ArtifactGroupInfo>();
		ArtifactGroupInfo info = new ArtifactGroupInfo();
		info.setArtifactGroupId("downloads_apache-tomcat");
		info.setArtifactInfoIds(Arrays.asList("0e34ab53-1b9e-493d-aa72-6ecacddc5338", "146829e4-87bd-4b1b-9811-d3faa91009e5"));
		selectedServers.add(info);
		applicationInfo.setSelectedServers(selectedServers);
		Response response = service.updateApplication(appDirName, applicationInfo, userId, customerId);
		ResponseInfo<ApplicationInfo> responseInfo = (ResponseInfo<ApplicationInfo>) response.getEntity();
		assertEquals(200 , response.getStatus());
		assertEquals("Application updated successfully", responseInfo.getMessage());
	}
	
	@Test
	public void editApplicationTest() {
		ProjectService service = new ProjectService();
		Response response = service.editApplication(appDirName);
		ResponseInfo<ProjectInfo> resopnseInfo = (ResponseInfo<ProjectInfo>) response.getEntity();
		assertEquals(appDirName, resopnseInfo.getData().getAppInfos().get(0).getAppDirName());
		assertEquals("Application edited Successfully", resopnseInfo.getMessage());
		assertEquals(200 , response.getStatus());
	}
	
//	@Test
	public void deleteprojectTest() {
		ProjectService service = new ProjectService();
		Response response = service.deleteproject(Arrays.asList(appDirName));
		ResponseInfo responseInfo = (ResponseInfo) response.getEntity();
		
		assertEquals(200, response.getStatus());
		assertEquals("Application deleted Successfully", responseInfo.getMessage());
	}
	
	private ProjectInfo createProjectInfo() {
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setCustomerIds(getCustomer());
		projectInfo.setId("TestProject");
		projectInfo.setName("TestProject");
		projectInfo.setNoOfApps(1);
		projectInfo.setProjectCode("TestProject");
		projectInfo.setVersion("1.0");
		projectInfo.setProjectCode("TestProject");
		projectInfo.setAppInfos(Collections.singletonList(createAppInfo()));
		return projectInfo;
	}
	
	private ApplicationInfo createAppInfo() {
		ApplicationInfo appInfo = new ApplicationInfo();
		appInfo.setAppDirName(appDirName);
		appInfo.setCode(appCode);
		appInfo.setId(projectId);
		appInfo.setName("TestProject");
		appInfo.setVersion("1.0");
		TechnologyInfo info = new TechnologyInfo();
		info.setAppTypeId("web-layer");
		info.setId(techId);
		info.setVersion("tech-java-webservice");
		appInfo.setTechInfo(info);
		return appInfo;
	}
	
	private List<SelectedFeature> getSelectedFeatures() {
		List<SelectedFeature> features = new ArrayList<SelectedFeature>();
		SelectedFeature feature = new SelectedFeature();
		feature.setArtifactGroupId("mod_spring-aop");
		feature.setModuleId("mod_spring-aop");
		feature.setVersionID("mod_spring-aop_tech_java_webservice3.0.5.RELEASE");
		feature.setPackaging("jar");
		feature.setType("FEATURE");
		features.add(feature);
		feature = new SelectedFeature();
		feature.setArtifactGroupId("jslib_lawnchair");
		feature.setModuleId("jslib_lawnchair");
		feature.setVersionID("jslib_lawnchair");
		feature.setPackaging("js");
		feature.setType("JAVASCRIPT");
		features.add(feature);
		return features;
	}
}
