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


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class QualityServiceTest extends RestBaseTest {
	
//	@Test
	public void performanceResultAvailTest() {
		QualityService qualitService = new QualityService();
		Response response = qualitService.performance("MergePerformanceAndLoad1-php5.4.x");
		assertEquals(200 , response.getStatus());
	}
	
//	@Test
	public void loadResultAvailTest() {
		QualityService qualitService = new QualityService();
		Response response = qualitService.load("MergePerformanceAndLoad1-php5.4.x");
		assertEquals(200 , response.getStatus());
	}
	
//	@Test
	public void performancetTestResultsTest() {
		QualityService qualityService = new QualityService();
		Response response = qualityService.performanceTestResults("MergePerformanceAndLoad1-php5.4.x", "server", "testServer.jtl", "", "responseTime");
		assertEquals(200, response.getStatus());
	}

//	@Test
	public void performancetTestResultsFailureTest() {
		QualityService qualityService = new QualityService();
		Response response = qualityService.performanceTestResults("MergePerformanceAndLoad1-php5.4.x", "server", "noFile.jtl", "", "responseTime");
		assertEquals(400, response.getStatus());
	}
	
//	@Test
	public void loadTestResultsTest() {
		QualityService qualityService = new QualityService();
		Response response = qualityService.loadTestResults("MergePerformanceAndLoad1-php5.4.x", "server", "testLoadServer.jtl");
		assertEquals(200, response.getStatus());
	}
	
//	@Test
	public void loadTestResultsFailureTest() {
		QualityService qualityService = new QualityService();
		Response response = qualityService.loadTestResults("MergePerformanceAndLoad1-php5.4.x", "server", "noFile.jtl");
		assertEquals(400, response.getStatus());;
	}
	
//	@Test
	public void fetchTestResultFiles() {
		QualityService qualityService = new QualityService();
		List<String> testAgainst = new ArrayList<String>();
		testAgainst.add("server");
		Response response = qualityService.getTypeFiles("MergePerformanceAndLoad1-php5.4.x", "performance-test", testAgainst);
		assertEquals(200, response.getStatus());;
	}
}
