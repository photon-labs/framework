/**
 * Framework Web Archive
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */package com.photon.phresco.framework.rest.api;

import java.util.Arrays;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

import com.photon.phresco.commons.model.CIJobTemplate;

public class CIJobTemplateServiceTest extends RestBaseTest {
	CIJobTemplateService ciJobTemplateService = new CIJobTemplateService();
	
	@Test
	public void addTemplate() {
		CIJobTemplate ciTemplate = new CIJobTemplate();
		ciTemplate.setCustomerId(customerId);
		ciTemplate.setEnableEmailSettings(true);
		ciTemplate.setEnableRepo(true);
		ciTemplate.setName("buildJob");
		ciTemplate.setProjectId(projectId);
		ciTemplate.setAppIds(Arrays.asList(appId));
		ciTemplate.setRepoTypes("svn");
		ciTemplate.setType("build");
		ciTemplate.setUploadTypes(Arrays.asList("collabnet"));
		Response add = ciJobTemplateService.add(ciTemplate,projectId,customerId);
		Assert.assertEquals(200, add.getStatus());
		
		CIJobTemplate ciTemplate1 = new CIJobTemplate();
		ciTemplate1.setCustomerId(customerId);
		ciTemplate1.setEnableEmailSettings(true);
		ciTemplate1.setEnableRepo(true);
		ciTemplate1.setEnableUploadSettings(false);
		ciTemplate1.setName("deployJob");
		ciTemplate1.setProjectId(projectId);
		ciTemplate1.setAppIds(Arrays.asList(appId));
		ciTemplate1.setRepoTypes("git");
		ciTemplate1.setType("deploy");
		ciTemplate1.setUploadTypes(Arrays.asList("confluence"));
		Response addnew = ciJobTemplateService.add(ciTemplate1,projectId,customerId);
		Assert.assertEquals(200, addnew.getStatus());
		
		CIJobTemplate ciTemplate2 = new CIJobTemplate();
		ciTemplate2.setCustomerId(customerId);
		ciTemplate2.setEnableEmailSettings(true);
		ciTemplate2.setEnableRepo(true);
		ciTemplate2.setName("pdf");
		ciTemplate2.setProjectId(projectId);
		ciTemplate2.setAppIds(Arrays.asList(appId));
		ciTemplate2.setRepoTypes("svn");
		ciTemplate2.setType("pdfReport");
		ciTemplate2.setUploadTypes(Arrays.asList("collabnet"));
		Response add1 = ciJobTemplateService.add(ciTemplate2,projectId,customerId);
		Assert.assertEquals(200, add1.getStatus());
	}

	@Test
	public void list() {
		Response list = ciJobTemplateService.list(projectId,customerId);
		Assert.assertEquals(200, list.getStatus());
		Response listFail = ciJobTemplateService.list("projectId","");
//		Assert.assertEquals(417, listFail.getStatus());
	}

	@Test
	public void listByName() {
		Response list = ciJobTemplateService.list("buildJob", appId, projectId, customerId, userId);
		Assert.assertEquals(200, list.getStatus());
		Response listEmpty = ciJobTemplateService.list("sampleJob", appId, projectId, customerId, userId);
		Assert.assertEquals(200, listEmpty.getStatus());
		Response listFail = ciJobTemplateService.list("", "", "sample", "tt", userId);
//		Assert.assertEquals(417, listFail.getStatus());
	}

	@Test
	public void validateName() {
		Response validateName = ciJobTemplateService.validateName(projectId, customerId, "buildJob", "buildJob");
		Assert.assertEquals(200, validateName.getStatus());
		Response validateNameEmpty = ciJobTemplateService.validateName("sample", "tt", "", "");
//		Assert.assertEquals(200, validateNameEmpty.getStatus());
	}

	
	@Test
	public void updateTemplate() {
		CIJobTemplate ciTemplate = new CIJobTemplate();
		ciTemplate.setCustomerId(customerId);
		ciTemplate.setEnableEmailSettings(true);
		ciTemplate.setEnableRepo(true);
		ciTemplate.setEnableUploadSettings(false);
		ciTemplate.setEnableSheduler(true);
		ciTemplate.setName("deployJob");
		ciTemplate.setProjectId(projectId);
		ciTemplate.setAppIds(Arrays.asList(appId));
		ciTemplate.setRepoTypes("git");
		ciTemplate.setType("deploy");
		ciTemplate.setUploadTypes(Arrays.asList("confluence","collabnet"));
		
		Response update = ciJobTemplateService.update(ciTemplate, "deployJob", projectId, customerId);
		Assert.assertEquals(200, update.getStatus());
		Response updateFail = ciJobTemplateService.update(ciTemplate, "", "sample", "tt");
//		Assert.assertEquals(200, updateFail.getStatus());
	}

	@Test
	public void getJobTemplatesByEnvironemnt() {
		Response jobTemplate = ciJobTemplateService.getJobTemplatesByEnvironemnt(customerId, projectId, "Production","");
		Assert.assertEquals(200, jobTemplate.getStatus());
		Response jobTemplateFail = ciJobTemplateService.getJobTemplatesByEnvironemnt("", "sample",  "tt","");
//		Assert.assertEquals(417, jobTemplateFail.getStatus());
	}

	@Test
	public void delete() {
		Response delete = ciJobTemplateService.delete("deployJob", projectId, customerId);
		Assert.assertEquals(200, delete.getStatus());
		Response deleteFail = ciJobTemplateService.delete("", "", customerId);
//		Assert.assertEquals(200, deleteFail.getStatus());
		Response deleteBuildJob = ciJobTemplateService.delete("buildJob", projectId, customerId);
		
		Response deleteCodeJob = ciJobTemplateService.delete("pdf", projectId, customerId);
	}
}
