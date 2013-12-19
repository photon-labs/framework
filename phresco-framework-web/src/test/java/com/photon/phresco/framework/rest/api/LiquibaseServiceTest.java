package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.model.LiquibaseDbDocInfo;
import com.photon.phresco.framework.model.LiquibaseDiffInfo;
import com.photon.phresco.framework.model.LiquibaseRollbackCountInfo;
import com.photon.phresco.framework.model.LiquibaseRollbackTagInfo;
import com.photon.phresco.framework.model.LiquibaseRollbackToDateInfo;
import com.photon.phresco.framework.model.LiquibaseStatusInfo;
import com.photon.phresco.framework.model.LiquibaseTagInfo;
import com.photon.phresco.framework.model.LiquibaseUpdateInfo;
import com.photon.phresco.framework.rest.api.util.ActionFunction;
import com.photon.phresco.framework.rest.api.util.ActionResponse;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;

public class LiquibaseServiceTest extends RestBaseTest{
	ParameterService parameterservice = new ParameterService();
	ActionService actionservice = new ActionService();
	LiquibaseService liquibaseService =new LiquibaseService();
	static String uniqueKey = "";
	
	ActionFunction actionFunction = new ActionFunction() {
		@Override
		public BufferedInputStream liquibaseDbdoc(LiquibaseDbDocInfo liquibaseDbDocInfo) {
			return null;
		}
		
		@Override
		public BufferedInputStream liquibaseUpdate(LiquibaseUpdateInfo liquibaseUpdateInfo) {
			return null;
		}
		
		@Override
		public BufferedInputStream liquibaseInstall() {
			return null;
		}
		
		@Override
		public BufferedInputStream liquibaseDiff(LiquibaseDiffInfo liquibaseDiffInfo) {
			return null;
		}
		
		@Override
		public BufferedInputStream liquibaseStatus(LiquibaseStatusInfo liquibaseStatusInfo) {
			return null;
		}
		
		@Override
		public BufferedInputStream liquibaseRollBackCount(LiquibaseRollbackCountInfo liquibaseRollbackCountInfo) {
			return null;
		}
		
		@Override
		public BufferedInputStream liquibaseRollbackToDate(LiquibaseRollbackToDateInfo liquibaseRollbackToDateInfo) {
			return null;
		}
		
		@Override
		public BufferedInputStream liquibaseRollbackTag(LiquibaseRollbackTagInfo liquibaseRollbackTagInfo) {
			return null;
		}
		
		@Override
		public BufferedInputStream liquibaseTag(LiquibaseTagInfo liquibaseTagInfo) {
			return null;
		}
	};
	
	@BeforeClass
	public static void upzip() throws PhrescoException {
		ArchiveUtil.extractArchive(new File(LiquibaseServiceTest.class.getResource("/liquibaseTest.zip").getFile()), new File(Utility.getProjectHome()), "liquibaseTest", ArchiveType.ZIP);
	}
	
	@Test
	public void liquibaseUpdate() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName", "Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseUpdateInfo updateInfo = new LiquibaseUpdateInfo();
		updateInfo.setAuthor("testAuthor");
		updateInfo.setDbVersion("0");
		updateInfo.setFileName("testChange1");
		List<String> sqlStatements = new ArrayList<String>();
		sqlStatements.add("create table Persons(PersonID int,Name varchar(255));");
		updateInfo.setSqlStatements(sqlStatements);
		List<String> rollbackStatements = new ArrayList<String>();
		rollbackStatements.add("drop table Persons;");
		updateInfo.setRollbackStatements(rollbackStatements);
		Response build = liquibaseService.liquibaseUpdate(updateInfo, httpServletRequest);
		ActionResponse entity = (ActionResponse) build.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("STARTED", entity.getStatus());
		assertEquals(true, readLog());
	}
	
	@Test
	public void liquibaseUpdateFileNotFound() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liq");
		request.setParameter("environmentName", "Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseUpdateInfo updateInfo = new LiquibaseUpdateInfo();
		updateInfo.setAuthor("testAuthor");
		updateInfo.setDbVersion("0");
		updateInfo.setFileName("testChange1");
		List<String> sqlStatements = new ArrayList<String>();
		sqlStatements.add("create table Persons(PersonID int,Name varchar(255));");
		updateInfo.setSqlStatements(sqlStatements);
		List<String> rollbackStatements = new ArrayList<String>();
		rollbackStatements.add("drop table Persons;");
		updateInfo.setRollbackStatements(rollbackStatements);
		Response build = liquibaseService.liquibaseUpdate(updateInfo, httpServletRequest);
		ActionResponse entity = (ActionResponse) build.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseUpdateWithoutNewChangesets() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName", "Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseUpdateInfo updateInfo = new LiquibaseUpdateInfo();
		updateInfo.setAuthor(null);
		updateInfo.setDbVersion(null);
		updateInfo.setFileName(null);
		updateInfo.setSqlStatements(null);
		updateInfo.setRollbackStatements(null);
		Response build = liquibaseService.liquibaseUpdate(updateInfo, httpServletRequest);
		ActionResponse entity = (ActionResponse) build.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("STARTED", entity.getStatus());
		assertEquals(true, readLog());
	}
	
	@Test
	public void liquibaseUpdateError() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName", "Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseUpdateInfo updateInfo = new LiquibaseUpdateInfo();
		updateInfo.setAuthor("testAuthor");
		updateInfo.setDbVersion("0");
		updateInfo.setFileName("testChange1");
		List<String> sqlStatements = new ArrayList<String>();
		sqlStatements.add("create table Wrong(PersonID int,Name varchar(255));");
		updateInfo.setSqlStatements(sqlStatements);
		List<String> rollbackStatements = new ArrayList<String>();
		rollbackStatements.add("drop table Persons;");
		updateInfo.setRollbackStatements(rollbackStatements);
		Response build = liquibaseService.liquibaseUpdate(updateInfo, httpServletRequest);
		ActionResponse entity = (ActionResponse) build.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseDbDoc() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName", "Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseDbDocInfo dbdocInfo = new LiquibaseDbDocInfo();
		dbdocInfo.setChangelogFileForDbDoc("src/main/resources/liquibase/update.xml");
		Response build = liquibaseService.liquibaseDbdoc(dbdocInfo, httpServletRequest);
		ActionResponse entity = (ActionResponse) build.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("STARTED", entity.getStatus());
		assertEquals(true, readLog());
	}
	
	@Test
	public void liquibaseDbDocError() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("environmentName", "Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseDbDocInfo dbdocInfo = new LiquibaseDbDocInfo();
		dbdocInfo.setChangelogFileForDbDoc("src/main/resources/liquibase/update.xml");
		Response build = liquibaseService.liquibaseDbdoc(dbdocInfo, httpServletRequest);
		ActionResponse entity = (ActionResponse) build.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseDbDocFileNotFound() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");
		request.setParameter("appDirName", "doesNotExist");
		request.setParameter("environmentName", "Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseDbDocInfo dbdocInfo = new LiquibaseDbDocInfo();
		dbdocInfo.setChangelogFileForDbDoc("src/main/resources/liquibase/update.xml");
		Response build = liquibaseService.liquibaseDbdoc(dbdocInfo, httpServletRequest);
		ActionResponse entity = (ActionResponse) build.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseTag() throws PhrescoException {		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseTagInfo tagInfo = new LiquibaseTagInfo();		
		tagInfo.setTag("master");
		Response liquibaseTag = liquibaseService.liquibaseTag(tagInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseTag.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("STARTED", entity.getStatus());
		assertEquals(true, readLog());
	}
	
	@Test
	public void liquibaseTagError() throws PhrescoException {		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseTagInfo tagInfo = new LiquibaseTagInfo();		
		tagInfo.setTag("master");
		Response liquibaseTag = liquibaseService.liquibaseTag(tagInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseTag.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseTagFileNotFound() throws PhrescoException {		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liq");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseTagInfo tagInfo = new LiquibaseTagInfo();		
		tagInfo.setTag("master");
		Response liquibaseTag = liquibaseService.liquibaseTag(tagInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseTag.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseUpdateSecond() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName", "Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseUpdateInfo updateInfo = new LiquibaseUpdateInfo();
		updateInfo.setAuthor("testAuthor");
		updateInfo.setDbVersion("0");
		updateInfo.setFileName("testChange2");
		List<String> sqlStatements = new ArrayList<String>();
		sqlStatements.add("create table People(PersonID int,Name varchar(255));");
		updateInfo.setSqlStatements(sqlStatements);
		List<String> rollbackStatements = new ArrayList<String>();
		rollbackStatements.add("drop table People;");
		updateInfo.setRollbackStatements(rollbackStatements);
		Response build = liquibaseService.liquibaseUpdate(updateInfo, httpServletRequest);
		ActionResponse entity = (ActionResponse) build.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("STARTED", entity.getStatus());
		assertEquals(true, readLog());
	}
		
	@Test
	public void liquibaseDiff() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseDiffInfo diffInfo = new LiquibaseDiffInfo();
		diffInfo.setSrcConfigurationName("testConf");
		diffInfo.setDestConfigurationName("conf");
		diffInfo.setSrcEnvironmentName("Production");
		diffInfo.setDestEnvironmentName("Testing");
		Response liquibaseDiff = liquibaseService.liquibaseDiff(diffInfo, httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseDiff.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("STARTED", entity.getStatus());
		assertEquals(true, readLog());
	}
	
	@Test
	public void liquibaseDiffError() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseDiffInfo diffInfo = new LiquibaseDiffInfo();
		diffInfo.setSrcConfigurationName("testConf");
		diffInfo.setDestConfigurationName("conf");
		diffInfo.setSrcEnvironmentName("Production");
		diffInfo.setDestEnvironmentName("Testing");
		Response liquibaseDiff = liquibaseService.liquibaseDiff(diffInfo, httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseDiff.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseDiffFileNotFound() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liq");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseDiffInfo diffInfo = new LiquibaseDiffInfo();
		diffInfo.setSrcConfigurationName("testConf");
		diffInfo.setDestConfigurationName("conf");
		diffInfo.setSrcEnvironmentName("Production");
		diffInfo.setDestEnvironmentName("Testing");
		Response liquibaseDiff = liquibaseService.liquibaseDiff(diffInfo, httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseDiff.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}

	@Test
	public void liquibaseStatus() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseStatusInfo statusInfo = new LiquibaseStatusInfo();
		statusInfo.setChangeLogPathForStatus("src/main/resources/liquibase/update.xml");
		statusInfo.setVerbose(true);
		Response liquibaseStatus = liquibaseService.liquibaseStatus(statusInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseStatus.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("STARTED", entity.getStatus());
		assertEquals(true, readLog());
	}
	
	@Test
	public void liquibaseStatusError() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseStatusInfo statusInfo = new LiquibaseStatusInfo();
		statusInfo.setChangeLogPathForStatus("src/main/resources/liquibase/update.xml");
		statusInfo.setVerbose(true);
		Response liquibaseStatus = liquibaseService.liquibaseStatus(statusInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseStatus.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseStatusFileNotFound() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");
		request.setParameter("appDirName", "liq");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseStatusInfo statusInfo = new LiquibaseStatusInfo();
		statusInfo.setChangeLogPathForStatus("src/main/resources/liquibase/update.xml");
		statusInfo.setVerbose(true);
		Response liquibaseStatus = liquibaseService.liquibaseStatus(statusInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseStatus.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseSelectedRollback() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseRollbackCountInfo rollbackCountInfo = new LiquibaseRollbackCountInfo();
		rollbackCountInfo.setChangeLogPathForRollbackCount("src/main/resources/liquibase/update.xml");
		rollbackCountInfo.setRollbackCountValue("1");
		Response liquibaseRollbackCount = liquibaseService.liquibaseRollBackCount(rollbackCountInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseRollbackCount.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("STARTED", entity.getStatus());
		assertEquals(true, readLog());
	}
	
	@Test
	public void liquibaseSelectedRollbackError() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseRollbackCountInfo rollbackCountInfo = new LiquibaseRollbackCountInfo();
		rollbackCountInfo.setChangeLogPathForRollbackCount("src/main/resources/liquibase/update.xml");
		rollbackCountInfo.setRollbackCountValue("1");
		Response liquibaseRollbackCount = liquibaseService.liquibaseRollBackCount(rollbackCountInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseRollbackCount.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseSelectedRollbackFileNotFound() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");
		request.setParameter("appDirName", "liq");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseRollbackCountInfo rollbackCountInfo = new LiquibaseRollbackCountInfo();
		rollbackCountInfo.setChangeLogPathForRollbackCount("src/main/resources/liquibase/update.xml");
		rollbackCountInfo.setRollbackCountValue("1");
		Response liquibaseRollbackCount = liquibaseService.liquibaseRollBackCount(rollbackCountInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseRollbackCount.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseRollBackTag() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseRollbackTagInfo rollbackTagInfo = new LiquibaseRollbackTagInfo();
		rollbackTagInfo.setChangeLogPathForRollbackTag("src/main/resources/liquibase/update.xml");
		rollbackTagInfo.setTag("master");
		Response liquibaseRollbackTag = liquibaseService.liquibaseRollBackTag(rollbackTagInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseRollbackTag.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("STARTED", entity.getStatus());
		assertEquals(true, readLog());
	}
	
	@Test
	public void liquibaseRollBackTagError() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseRollbackTagInfo rollbackTagInfo = new LiquibaseRollbackTagInfo();
		rollbackTagInfo.setChangeLogPathForRollbackTag("src/main/resources/liquibase/update.xml");
		rollbackTagInfo.setTag("master");
		Response liquibaseRollbackTag = liquibaseService.liquibaseRollBackTag(rollbackTagInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseRollbackTag.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseRollBackTagFileNotFound() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");
		request.setParameter("appDirName", "liq");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseRollbackTagInfo rollbackTagInfo = new LiquibaseRollbackTagInfo();
		rollbackTagInfo.setChangeLogPathForRollbackTag("src/main/resources/liquibase/update.xml");
		rollbackTagInfo.setTag("master");
		Response liquibaseRollbackTag = liquibaseService.liquibaseRollBackTag(rollbackTagInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseRollbackTag.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseRollbackToDate() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseRollbackToDateInfo rollbackToDateInfo = new LiquibaseRollbackToDateInfo();
		rollbackToDateInfo.setChangeLogPathForRollbackDate("src/main/resources/liquibase/update.xml");
		rollbackToDateInfo.setRollbackDate("23-10-2013 21:13:10");
		Response liquibaseRollbackToDate = liquibaseService.liquibaseRollBackToDate(rollbackToDateInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseRollbackToDate.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("STARTED", entity.getStatus());
		assertEquals(true, readLog());
	}
	
	@Test
	public void liquibaseRollbackToDateError() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseRollbackToDateInfo rollbackToDateInfo = new LiquibaseRollbackToDateInfo();
		rollbackToDateInfo.setChangeLogPathForRollbackDate("src/main/resources/liquibase/update.xml");
		rollbackToDateInfo.setRollbackDate("23-10-2013 21:13:10");
		Response liquibaseRollbackToDate = liquibaseService.liquibaseRollBackToDate(rollbackToDateInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseRollbackToDate.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseRollbackToDateFileNotFound() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");
		request.setParameter("appDirName", "liq");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseRollbackToDateInfo rollbackToDateInfo = new LiquibaseRollbackToDateInfo();
		rollbackToDateInfo.setChangeLogPathForRollbackDate("src/main/resources/liquibase/update.xml");
		rollbackToDateInfo.setRollbackDate("23-10-2013 21:13:10");
		Response liquibaseRollbackToDate = liquibaseService.liquibaseRollBackToDate(rollbackToDateInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseRollbackToDate.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseInstall() throws PhrescoException, ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo("liquibaseTest");
		ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
		File install = new File(Utility.getProjectHome() + applicationInfo.getAppDirName()+FrameworkConstants.PATH_TO_LIQUIBASE+FrameworkConstants.INSTALL_XML);
		Document installXML = docBuilder.parse(install);
		Element databaseChangeLogForMaster = installXML.getDocumentElement();
		Element include = installXML.createElement(FrameworkConstants.INCLUDE_TAG);
		include.setAttribute(FrameworkConstants.FILE_ATTR, FrameworkConstants.PATH_TO_LIQUIBASE.substring(1, FrameworkConstants.PATH_TO_LIQUIBASE.length())+"0/master"+FrameworkConstants.DOT+FrameworkConstants.XML);
		databaseChangeLogForMaster.appendChild(include);
		DOMSource sourceMaster = new DOMSource(installXML);
		StreamResult resultMaster = new StreamResult(install.toURI().getPath());
		transformer.transform(sourceMaster, resultMaster);
		
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName", "Production");
		Response build = liquibaseService.liquibaseInstall(request);
		ActionResponse entity = (ActionResponse) build.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("STARTED", entity.getStatus());
		assertEquals(true, readLog());
	}
	
	@Test
	public void liquibaseInstallError() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("environmentName", "Production");
		Response build = liquibaseService.liquibaseInstall(request);
		ActionResponse entity = (ActionResponse) build.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseInstallFileNotFound() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liq");
		request.setParameter("environmentName", "Production");
		Response build = liquibaseService.liquibaseInstall(request);
		ActionResponse entity = (ActionResponse) build.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test(expected=InvocationTargetException.class)
	public void liquibasePersistExceptionTest() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		ActionFunction actionFunction = new ActionFunction();
		Class[] cArg = new Class[4];
		cArg[0] = MojoProcessor.class;
		cArg[1] = String.class;
		cArg[2] = Properties.class;
		cArg[3] = String.class;
		Method persist = ActionFunction.class.
		        getDeclaredMethod("persistLiquibaseValuesToXml", cArg);

		persist.setAccessible(true);
		Object[] args = new Object[4];
		args[0] = null;
		args[1] = null;
		args[2] = null;
		args[3] = null;
		persist.invoke(actionFunction, args);
	}
	
	@Test(expected=InvocationTargetException.class)
	public void liquibasePersistForDiffExceptionTest() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		ActionFunction actionFunction = new ActionFunction();
		Class[] cArg = new Class[4];
		cArg[0] = MojoProcessor.class;
		cArg[1] = String.class;
		cArg[2] = Properties.class;
		cArg[3] = Properties.class;
		Method persist = ActionFunction.class.
		        getDeclaredMethod("persistLiquibaseDiffValuesToXml", cArg);

		persist.setAccessible(true);
		Object[] args = new Object[4];
		args[0] = null;
		args[1] = null;
		args[2] = null;
		args[3] = null;
		persist.invoke(actionFunction, args);
	}
	
	@Test
	public void getDbConfigExceptionTest() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		LiquibaseDbDocInfo dbdocInfo = new LiquibaseDbDocInfo();
		dbdocInfo.setChangelogFileForDbDoc("src/main/resources/liquibase/update.xml");
		HttpServletRequest mockRequest = EasyMock.createNiceMock(HttpServletRequest.class);
		EasyMock.expect(mockRequest.getParameter("appDirName")).andReturn("liquibaseTest");
		EasyMock.expect(mockRequest.getParameter("environmentName")).andReturn("Production");
		EasyMock.expect(mockRequest.getParameter("configurationName")).andThrow(new RuntimeException());
		EasyMock.replay(mockRequest);
		liquibaseService.liquibaseDbdoc(dbdocInfo, mockRequest);
	}
	
	@Test
	public void liquibaseDiffReturnNull() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseDiffInfo diffInfo = new LiquibaseDiffInfo();
		diffInfo.setSrcConfigurationName("tonf");
		diffInfo.setDestConfigurationName("cnf");
		diffInfo.setSrcEnvironmentName("Production");
		diffInfo.setDestEnvironmentName("Testing");
		Response liquibaseDiff = liquibaseService.liquibaseDiff(diffInfo, httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseDiff.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("error", entity.getStatus());
	}
	
	@Test
	public void liquibaseDiffConfigException() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseDiffInfo diffInfo = EasyMock.createNiceMock(LiquibaseDiffInfo.class);
		EasyMock.expect(diffInfo.getSrcConfigurationName()).andThrow(new RuntimeException());
		EasyMock.replay(diffInfo);
		liquibaseService.liquibaseDiff(diffInfo, httpServletRequest);
	}
	
	@Test
	public void liquibaseGetDbConfigNullTest() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		ActionFunction actionFunction = new ActionFunction();
		Class[] cArg = new Class[2];
		cArg[0] = String.class;
		cArg[1] = String.class;
		Method persist = ActionFunction.class.
		        getDeclaredMethod("getDbConfiguration", cArg);
		persist.setAccessible(true);
		Object[] args = new Object[2];
		args[0] = null;
		args[1] = "dshfjsd";
		persist.invoke(actionFunction, args);
	}
	
	@Test
	public void liquibaseGetDbConfigExceptionTest() throws PhrescoException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseStatusInfo statusInfo = new LiquibaseStatusInfo();
		statusInfo.setChangeLogPathForStatus("src/main/resources/liquibase/update.xml");
		statusInfo.setVerbose(true);
		Response liquibaseStatus = liquibaseService.liquibaseStatus(statusInfo,httpServletRequest);
		ActionResponse entity = (ActionResponse) liquibaseStatus.getEntity();
		uniqueKey = entity.getUniquekey();
	}
	
	@Test
	public void liquibaseGetEnvironmentsTest() throws PhrescoException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("appDirName", "liquibaseTest");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Response response = liquibaseService.getEnvironments(httpServletRequest);
		ResponseInfo responseInfo = (ResponseInfo) response.getEntity();
		assertEquals("success",responseInfo.getStatus());
		
	}
	
	@Test
	public void liquibaseGetEnvironmentsErrorTest() throws PhrescoException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Response response = liquibaseService.getEnvironments(httpServletRequest);
		ResponseInfo responseInfo = (ResponseInfo) response.getEntity();
		assertEquals("error",responseInfo.getStatus());
		
	}
	
	@Test
	public void liquibaseGetConfigsTest() throws PhrescoException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName", "Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Response response = liquibaseService.getConfigs(httpServletRequest);
		ResponseInfo responseInfo = (ResponseInfo) response.getEntity();
		assertEquals("success",responseInfo.getStatus());	
	}
	
	@Test
	public void liquibaseGetConfigsErrorTest() throws PhrescoException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("environmentName", "Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Response response = liquibaseService.getConfigs(httpServletRequest);
		ResponseInfo responseInfo = (ResponseInfo) response.getEntity();
		assertEquals("error",responseInfo.getStatus());	
	}
	
	@Test
	public void liquibaseGetTags() throws PhrescoException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Response response = liquibaseService.getTags(httpServletRequest);
		ResponseInfo responseInfo = (ResponseInfo) response.getEntity();
		assertEquals("success",responseInfo.getStatus());
	}
	
	@Test
	public void liquibaseGetTagsError() throws PhrescoException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Response response = liquibaseService.getTags(httpServletRequest);
		ResponseInfo responseInfo = (ResponseInfo) response.getEntity();
		assertEquals("error",responseInfo.getStatus());
	}
	
	@Test(expected=PhrescoException.class)
	public void liquibaseDbDocNoLogs() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName", "Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseDbDocInfo dbdocInfo = new LiquibaseDbDocInfo();
		dbdocInfo.setChangelogFileForDbDoc("src/main/resources/liquibase/update.xml");
		actionFunction.liquibaseDbdoc(dbdocInfo, httpServletRequest);
	}
	
	@Test(expected=PhrescoException.class)
	public void liquibaseUpdateNoLogs() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName", "Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseUpdateInfo updateInfo = new LiquibaseUpdateInfo();
		updateInfo.setAuthor("testAuthor");
		updateInfo.setDbVersion("0");
		updateInfo.setFileName("testChange1");
		List<String> sqlStatements = new ArrayList<String>();
		sqlStatements.add("create table Persons(PersonID int,Name varchar(255));");
		updateInfo.setSqlStatements(sqlStatements);
		List<String> rollbackStatements = new ArrayList<String>();
		rollbackStatements.add("drop table Persons;");
		updateInfo.setRollbackStatements(rollbackStatements);
		actionFunction.liquibaseUpdate(updateInfo, httpServletRequest);
	}
	
	@Test(expected=PhrescoException.class)
	public void liquibaseTagNoLogs() throws PhrescoException {		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseTagInfo tagInfo = new LiquibaseTagInfo();		
		tagInfo.setTag("master");
		actionFunction.liquibaseTag(tagInfo,httpServletRequest);
	}
	
	@Test(expected=PhrescoException.class)
	public void liquibaseDiffNoLogs() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseDiffInfo diffInfo = new LiquibaseDiffInfo();
		diffInfo.setSrcConfigurationName("testConf");
		diffInfo.setDestConfigurationName("conf");
		diffInfo.setSrcEnvironmentName("Production");
		diffInfo.setDestEnvironmentName("Testing");
		actionFunction.liquibaseDiff(diffInfo, httpServletRequest);
	}
	
	@Test(expected=PhrescoException.class)
	public void liquibaseStatusNoLogs() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseStatusInfo statusInfo = new LiquibaseStatusInfo();
		statusInfo.setChangeLogPathForStatus("src/main/resources/liquibase/update.xml");
		statusInfo.setVerbose(true);
		actionFunction.liquibaseStatus(statusInfo,httpServletRequest);
	}
	
	@Test(expected=PhrescoException.class)
	public void liquibaseSelectedRollbackNoLogs() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseRollbackCountInfo rollbackCountInfo = new LiquibaseRollbackCountInfo();
		rollbackCountInfo.setChangeLogPathForRollbackCount("src/main/resources/liquibase/update.xml");
		rollbackCountInfo.setRollbackCountValue("1");
		actionFunction.liquibaseRollbackCount(rollbackCountInfo,httpServletRequest);
	}
	
	@Test(expected=PhrescoException.class)
	public void liquibaseRollBackTagNoLogs() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseRollbackTagInfo rollbackTagInfo = new LiquibaseRollbackTagInfo();
		rollbackTagInfo.setChangeLogPathForRollbackTag("src/main/resources/liquibase/update.xml");
		rollbackTagInfo.setTag("master");
		actionFunction.liquibaseRollbackTag(rollbackTagInfo,httpServletRequest);
	}
	
	@Test(expected=PhrescoException.class)
	public void liquibaseRollbackToDateNoLogs() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName","Production");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		LiquibaseRollbackToDateInfo rollbackToDateInfo = new LiquibaseRollbackToDateInfo();
		rollbackToDateInfo.setChangeLogPathForRollbackDate("src/main/resources/liquibase/update.xml");
		rollbackToDateInfo.setRollbackDate("23-10-2013 21:13:10");
		actionFunction.liquibaseRollbackToDate(rollbackToDateInfo,httpServletRequest);
	}
	
	@Test(expected=PhrescoException.class)
	public void liquibaseInstallNoLogs() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("configurationName", "testConf");		
		request.setParameter("appDirName", "liquibaseTest");
		request.setParameter("environmentName", "Production");
		actionFunction.liquibaseInstall(request);
	}
	
	@Test
	public void liquibaseDbUrlMySql() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		LiquibaseService liquibaseService = new LiquibaseService();
		Class[] cArg = new Class[4];
		cArg[0] = String.class;
		cArg[1] = String.class;
		cArg[2] = String.class;
		cArg[3] = String.class;
		Method persist = LiquibaseService.class.
		        getDeclaredMethod("dbURLConstruction", cArg);

		persist.setAccessible(true);
		Object[] args = new Object[4];
		args[0] = "testURL";
		args[1] = "testPort";
		args[2] = "testDbname";
		args[3] = "mysql";
		persist.invoke(liquibaseService, args);
	}
	
	@Test
	public void liquibaseDbUrlNull() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		LiquibaseService liquibaseService = new LiquibaseService();
		Class[] cArg = new Class[4];
		cArg[0] = String.class;
		cArg[1] = String.class;
		cArg[2] = String.class;
		cArg[3] = String.class;
		Method persist = LiquibaseService.class.
		        getDeclaredMethod("dbURLConstruction", cArg);

		persist.setAccessible(true);
		Object[] args = new Object[4];
		args[0] = "testURL";
		args[1] = "testPort";
		args[2] = "testDbname";
		args[3] = "testDbtype";
		persist.invoke(liquibaseService, args);
	}
	
	@BeforeClass
	public static void delete() throws IOException {
		FileUtils.deleteDirectory(new File(Utility.getProjectHome()+"/liquibaseTest"));
	}
	
	public Boolean readLog() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("uniquekey", uniqueKey);
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Response build = actionservice.read(httpServletRequest);
		ActionResponse output = (ActionResponse) build.getEntity();
		System.out.println(output.getLog());
		if (output.getLog() != null) {

			if (output.getLog().contains("BUILD FAILURE")) {
				fail("Error occured ");
			}
			
			if (output.getLog().contains("Starting Coyote")) {
				return true;
			}
			
			if ("INPROGRESS".equalsIgnoreCase(output.getStatus())) {
				readLog();
				return true;
			} else if ("COMPLETED".equalsIgnoreCase(output.getStatus())) {
				System.out.println("***** Log finished ***********");
				return true;
			} else if ("ERROR".equalsIgnoreCase(output.getStatus())) {
				fail("Error occured while retrieving the logs");
				return false;
			}
		}
		return false;
	}
}