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
 */
package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;


public class PilotServiceTest extends RestBaseTest {
	
	PilotService pilotservice = new PilotService();
	
	public PilotServiceTest() {
		super();
	}
	
	@Test
	public void listPilots() {
		Response listLoginFail = pilotservice.list(customerId, techId, "sample");
		assertEquals(400 , listLoginFail.getStatus());
		Response list = pilotservice.list(customerId, techId, userId);
		assertEquals(200 , list.getStatus());
//		Response listFail = pilotservice.list(null, null, userId);
//		ResponseInfo<List<ApplicationInfo>> e = (ResponseInfo<List<ApplicationInfo>>)listFail.getEntity();
//		System.out.println(e.getData());
//		assertEquals(204 , listFail.getStatus());
	}
	
	@Test
	public void dependentPilots() throws PhrescoException {
		
		Response dependentServers = pilotservice.getDependentPilot(userId, "d17d8389-3201-4a5f-888a-e5c0a0c68e18", "servers");
		assertEquals(200 , dependentServers.getStatus());
		Response dependentDb = pilotservice.getDependentPilot(userId, "d17d8389-3201-4a5f-888a-e5c0a0c68e18", "databases");
		assertEquals(200 , dependentDb.getStatus());
		Response dependentWebservices = pilotservice.getDependentPilot(userId, "PHTN_html5_jquery_mobilewidget_eshop", "webservices");
		assertEquals(200 , dependentWebservices.getStatus());
		Response listFail = pilotservice.getDependentPilot(userId, "d17d8389-3201-4a5f-888a-e5c0a0c68e18", "rest");
		assertEquals(400 , listFail.getStatus());
	}
	
	@Test
	public void testPreBuiltProjects() throws PhrescoException {
		Response preBuiltProjectsLoginFail = pilotservice.preBuiltProjects("sample","photon");
		assertEquals(200 , preBuiltProjectsLoginFail.getStatus());
		Response preBuiltProjects = pilotservice.preBuiltProjects("admin","photon");
		assertEquals(200 , preBuiltProjects.getStatus());
	}
}
