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

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.util.MojoProcessor;


public class BuildServiceTest {
	
	
	
	BuildService instance = new BuildService();
	List<Parameter> parameters=null;

    @Before
    public void setUp() {
    	instance.setAppId("83e1a937-cf66-4231-84c7-f40e9efdf80f");
    	instance.setProjectId("a20be474-0449-435d-8388-f459c96e9567");
    	instance.setCustomerId("photon");
    	Map  data = new HashMap();
    	data.put("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
    	data.put("buildName", "seconfwebcomet");
    	data.put("customerId", "photon");
    	data.put("logs", "showErrors");
    	data.put("projectId", "a20be474-0449-435d-8388-f459c96e9567");
    	data.put("resultJson", "");
    	data.put("selectedFileOrFolder", "");
    	data.put("selectedFiles", "");
    	data.put("stFileFunction", "");
    	data.put("targetFolder", "");
    	data.put("skipTest", "true");
    	data.put("showSettings", "true");
    	List env = new LinkedList();
    	env.add("Production");
    	env.add("Staging");
    	data.put("environmentName", env);
    	instance.setData(data);
    	
    }
	
	@Test
    public void testProjectInfo() throws PhrescoException {
        System.out.println("Executing testProjectInfo test");
        String expResult = "perfecttesting1";
        ProjectInfo result = instance.getProjectInfo();
        assertEquals("unable to get the actual project info",expResult, result.getProjectCode());
        
        
    }
	
	@Test
	public void testApplicationInfo() throws PhrescoException {
		
		System.out.println("Executing testApplicationInfo test");
		ApplicationInfo result = instance.getApplicationInfo();
		assertNotNull("unable to get the actual app info",result);
		
	}
	
	@Test
	public void testMojoProcessor() throws PhrescoException {
		
		System.out.println("Executing testMojoProcessor");
    	MojoProcessor mojo = new MojoProcessor(new File(instance.getPhrescoPluginInfoFilePath("package")));
    	parameters = instance.getMojoParameters(mojo, "package");
    	System.out.println("parameter value is :"+parameters.get(0));
		assertNotNull("unable to get the Mojo processor",parameters);
		
	}
	
	
	@Test
	public void testGetReqParameter() throws PhrescoException {
		
		System.out.println("Executing testGetParametervalues");
		String expected="83e1a937-cf66-4231-84c7-f40e9efdf80f";
		String result = instance.getReqParameter("appId");
		System.out.println("parameter value of appid is :"+result);
		assertEquals("Not getting the actual value from request", expected, result);
		
	}
	
	@Test
	public void testGetReqParameterValues() throws PhrescoException {
		
		System.out.println("Executing testGetReqParameterValues");
		String[] expected= {"Production","Staging"};
		String[] expected2= {"production"};
		String[] result = instance.getReqParameterValues("environmentName");
		System.out.println("parameter value of appid is :"+result[0]+result[1]);
		assertArrayEquals("not getting actual requestparametervalues",expected, result);
		//assertArrayEquals("not getting actual requestparametervalues",expected2, result);
		
	}
	
	@Test
	public void testMavenArgCommands() throws PhrescoException {
		
		System.out.println("Executing testMavenArgCommands");
		MojoProcessor mojo = new MojoProcessor(new File(instance.getPhrescoPluginInfoFilePath("package")));
		parameters = instance.getMojoParameters(mojo, "package");
		instance.persistValuesToXml(mojo, "package");
		List<Parameter> parameters = instance.getMojoParameters(mojo, "package");
		List<String> buildArgCmds = instance.getMavenArgCommands(parameters);
		System.out.println("buildArgCmds is :"+buildArgCmds);
		assertEquals("Not Getting the actual Mojo processor","-e",buildArgCmds.get(0));
		
	}
	
	@Test
	public void testGetAppDirectoryPath() throws PhrescoException {
		
		System.out.println("Executing testGetAppDirectoryPath");
		String workingDirectory = instance.getAppDirectoryPath(instance.getApplicationInfo());
		String expected ="C:\\Documents and Settings\\mohamed_as\\workspace\\projects\\2-javawebservice";
		System.out.println("workingDirectory : "+workingDirectory);
		assertEquals("Got the actual Mojo processor",expected,workingDirectory);
		
		
	}
	
	
	
	

}
