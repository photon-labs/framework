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

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

public class CIJobTemplateServiceTest {
	
	@Test
	public void getJobTemplatesByEnvironemnt() {
		CIJobTemplateService ciJobTemplateService = new CIJobTemplateService();
		String customerId = "photon";
		String projectId = "09369f3e-366e-40fa-a983-6d7c753bfcc1";
		String envName = "Production";
		Response response = ciJobTemplateService.getJobTemplatesByEnvironemnt(customerId, projectId, envName);
		System.out.println("Response : " + response.getEntity());
		Assert.assertEquals(200, response.getStatus());
	}
}
