package com.photon.phresco.framework.rest.api;

import com.photon.phresco.commons.model.Dashboard;
import com.photon.phresco.commons.model.DashboardInfo;
import com.photon.phresco.commons.model.Dashboards;
import com.photon.phresco.commons.model.Widget;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;
import com.photon.phresco.util.DashboardSearchInfo;
import com.photon.phresco.util.Utility;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.ws.rs.core.Response;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.google.gson.Gson;


public class DashboardServiceTest  {
	DashboardService dashboardService = new DashboardService();
	Gson gson = new Gson();
	String json = null;
	static MockServer mockServer;
	
	@BeforeClass
	public static void beforeClass() throws IOException {
		mockServer = new MockServer(8089, "172.16.26.67");
		mockServer.startServer();
	}
	@org.junit.Before
	public void setUp() throws IOException, PhrescoException {
		ArchiveUtil.extractArchive(new File(this.getClass().getResource("/test_566.zip").getFile()), new File(Utility.getProjectHome()), "test_566", ArchiveType.ZIP);
	}
	
	@Test
	public void configureDashboard()  {
		System.out.println("configureDashboard test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		Assert.assertNotNull(responseInfo.getData());
	}
	@Test
	public void configureDashboardExc()  {
		System.out.println("configureDashboardExc test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("4353434534634");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		Assert.assertEquals(responseInfo.getStatus(),"error");
	}
	

	@Test
	public void updateDashboardConfigureinfo() throws IOException {
		System.out.println("updateDashboardConfigureinfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData().toString());
		Response updDashboard = dashboardService.updateDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )updDashboard.getEntity();
		Assert.assertEquals(responseInfo1.getStatus().toString(),"success");
	}
	@Test
	public void updateDashboardConfigureinfoExc() throws IOException {
		System.out.println("updateDashboardConfigureinfoExc test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid("sdsfertfgt2342345");
		Response updDashboard = dashboardService.updateDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )updDashboard.getEntity();
		Assert.assertEquals(responseInfo1.getStatus().toString(),"failure");
	}
	
	@Test
	public void updateDashboardConfigureinfoExc1() throws IOException {
		System.out.println("updateDashboardConfigureinfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData().toString());
		dashboardInfo.setAppdirname("435sefsd");
		Response updDashboard = dashboardService.updateDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )updDashboard.getEntity();
		Assert.assertEquals(responseInfo1.getStatus().toString(),"error");
	}
	
	@Test
	public void getDashboardConfigureinfo() throws IOException {
		System.out.println("getDashboardConfigureinfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		Response getDashboard = dashboardService.getDashboardInfo("a98827a3-cfe8-4d57-a682-31cdc7fb4af2", "test_566",responseInfo.getData().toString());
		ResponseInfo<Dashboard> responseInfo1 = (ResponseInfo<Dashboard> )getDashboard.getEntity();
		Assert.assertEquals(responseInfo1.getData().getDatatype(),"splunk");

	}
	@Test
	public void getDashboardConfigureinfoExc() throws IOException {
		System.out.println("getDashboardConfigureinfoExc test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		Response getDashboard = dashboardService.getDashboardInfo("a98827a3-cfe8-4d57-a682-31cdc7fb4af2", "56567567",responseInfo.getData().toString());
		ResponseInfo<Dashboard> responseInfo1 = (ResponseInfo<Dashboard> )getDashboard.getEntity();
		Assert.assertEquals(responseInfo1.getStatus().toString(),"error");
	}
	@Test
	public void getDashboardConfigureinfoExc1() throws IOException {
		System.out.println("getDashboardConfigureinfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		Response getDashboard = dashboardService.getDashboardInfo("a9dfdfdfd3535235sfdgdgd", "test_566","34sdfsdt");
		ResponseInfo<Dashboard> responseInfo1 = (ResponseInfo<Dashboard> )getDashboard.getEntity();
		Assert.assertEquals(responseInfo1.getStatus().toString(),"failure");
	}
	
	@Test
	public void listAllDashboard()  {
		System.out.println("listAllDashboard test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		Response listDashboard = dashboardService.listAllDashboardInfo("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		ResponseInfo<HashMap<String, Dashboards>> responseInfo1 = (ResponseInfo<HashMap<String, Dashboards>>)listDashboard.getEntity();
		Assert.assertEquals(responseInfo1.getData().containsKey("test_566"),true);
	}
	
	@Test
	public void listAllDashboardExc()  {
		System.out.println("listAllDashboardExc test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		Response listDashboard = dashboardService.listAllDashboardInfo("454574746");
		ResponseInfo<HashMap<String, Dashboards>> responseInfo1 = (ResponseInfo<HashMap<String, Dashboards>>)listDashboard.getEntity();
		Assert.assertEquals(responseInfo1.getStatus(),"failure");
	}
	
	@Test
	public void listAllDashboardExc1() throws IOException, PhrescoException  {
		System.out.println("listAllDashboardExc1 test...");
		ArchiveUtil.extractArchive(new File(this.getClass().getResource("/test_5661.zip").getFile()), new File(Utility.getProjectHome()), "test_5661", ArchiveType.ZIP);
		Response listDashboard = dashboardService.listAllDashboardInfo("454574746dddd");
		ResponseInfo<HashMap<String, Dashboards>> responseInfo1 = (ResponseInfo<HashMap<String, Dashboards>>)listDashboard.getEntity();
		FileUtils.deleteDirectory(new File(Utility.getProjectHome()+"/test_5661"));
		Assert.assertEquals(responseInfo1.getStatus(),"failure");
	}
	
	@Test
	public void deleteDashboardConfigureinfo() throws IOException {
		System.out.println("updateDashboardConfigureinfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData().toString());
		Response delDashboard = dashboardService.deleteDashboardInfo(dashboardInfo);
		ResponseInfo<Dashboard> responseInfo1 = (ResponseInfo<Dashboard> )delDashboard.getEntity();
		Assert.assertEquals(responseInfo1.getStatus().toString(),"success");
	}
	

	@Test
	public void deleteDashboardConfigureinfoExc() throws IOException {
		System.out.println("updateDashboardConfigureinfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData().toString());
		dashboardInfo.setAppdirname("dgdfg34435esrfs");
		Response delDashboard = dashboardService.deleteDashboardInfo(dashboardInfo);
		ResponseInfo<Dashboard> responseInfo1 = (ResponseInfo<Dashboard> )delDashboard.getEntity();
		Assert.assertEquals(responseInfo1.getStatus().toString(),"error");
	}
	
	@Test
	public void deleteDashboardConfigureinfoNull() throws IOException {
		System.out.println("updateDashboardConfigureinfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid("rerere34343");
		Response delDashboard = dashboardService.deleteDashboardInfo(dashboardInfo);
		ResponseInfo<Dashboard> responseInfo1 = (ResponseInfo<Dashboard> )delDashboard.getEntity();
		Assert.assertEquals(responseInfo1.getStatus().toString(),"failure");
	}
	
	@Test
	public void addDashboardWidgetConfigureInfo() throws IOException {
		System.out.println("updateDashboardWidgetConfigureInfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		dashboardInfo.setName("wid1");
		dashboardInfo.setQuery("query");
		dashboardInfo.setAutorefresh("true");
		dashboardInfo.setStarttime("time");
		dashboardInfo.setEndtime("time");
		HashMap<String, String[]> properties = new HashMap<String, String[]>();
		String arg[]={"prop1", "prop2"};
		properties.put("barchart", arg);
		dashboardInfo.setProperties(properties);
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData());
		Response addWid = dashboardService.addDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )addWid.getEntity();
		Assert.assertNotNull(responseInfo1.getData());
	}
	@Test
	public void addDashboardWidgetConfigureInfoExc() throws IOException {
		System.out.println("updateDashboardWidgetConfigureInfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		dashboardInfo.setName("wid1");
		dashboardInfo.setQuery("query");
		dashboardInfo.setAutorefresh("true");
		dashboardInfo.setStarttime("time");
		dashboardInfo.setEndtime("time");
		HashMap<String, String[]> properties = new HashMap<String, String[]>();
		String arg[]={"prop1", "prop2"};
		properties.put("barchart", arg);
		dashboardInfo.setProperties(properties);
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid("235235wrsdf");
		Response addWid = dashboardService.addDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )addWid.getEntity();
		Assert.assertEquals(responseInfo1.getStatus(),"failure");	
		}
	
	@Test
	public void addDashboardWidgetConfigureInfoExc1() throws IOException {
		System.out.println("updateDashboardWidgetConfigureInfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("erdr234");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		dashboardInfo.setName("wid1");
		dashboardInfo.setQuery("query");
		dashboardInfo.setAutorefresh("true");
		dashboardInfo.setStarttime("time");
		dashboardInfo.setEndtime("time");
		HashMap<String, String[]> properties = new HashMap<String, String[]>();
		String arg[]={"prop1", "prop2"};
		properties.put("barchart", arg);
		dashboardInfo.setProperties(properties);
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid("235235wrsdf");
		Response addWid = dashboardService.addDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )addWid.getEntity();
		Assert.assertEquals(responseInfo1.getStatus(),"error");	
		}

	@Test
	public void updateDashboardWidgetConfigureInfo() throws IOException {
		System.out.println("updateDashboardWidgetConfigureInfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		dashboardInfo.setName("wid1");
		dashboardInfo.setQuery("query");
		dashboardInfo.setAutorefresh("true");
		dashboardInfo.setStarttime("time");
		dashboardInfo.setEndtime("time");
		HashMap<String, String[]> properties = new HashMap<String, String[]>();
		String arg[]={"prop1", "prop2"};
		properties.put("barchart", arg);
		dashboardInfo.setProperties(properties);
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData());
		Response addWid = dashboardService.addDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )addWid.getEntity();
		dashboardInfo.setWidgetid(responseInfo1.getData());
		Response updWid = dashboardService.updateDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo2 = (ResponseInfo<String> )updWid.getEntity();
		Assert.assertEquals(responseInfo2.getStatus(),"success");
	}
	
	@Test
	public void updateDashboardWidgetConfigureInfoExc() throws IOException {
		System.out.println("updateDashboardWidgetConfigureInfoExc test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		dashboardInfo.setName("wid1");
		dashboardInfo.setQuery("query");
		dashboardInfo.setAutorefresh("true");
		dashboardInfo.setStarttime("time");
		dashboardInfo.setEndtime("time");
		HashMap<String, String[]> properties = new HashMap<String, String[]>();
		String arg[]={"prop1", "prop2"};
		properties.put("barchart", arg);
		dashboardInfo.setProperties(properties);
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData());
		Response addWid = dashboardService.addDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )addWid.getEntity();
		dashboardInfo.setWidgetid("45345sfsd");
		Response updWid = dashboardService.updateDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo2 = (ResponseInfo<String> )updWid.getEntity();
		Assert.assertEquals(responseInfo2.getStatus(),"failure");
	}
	
	@Test
	public void updateDashboardWidgetConfigureInfoExc1() throws IOException {
		System.out.println("updateDashboardWidgetConfigureInfoExc1 test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("rtdrgtdf2343");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		dashboardInfo.setName("wid1");
		dashboardInfo.setQuery("query");
		dashboardInfo.setAutorefresh("true");
		dashboardInfo.setStarttime("time");
		dashboardInfo.setEndtime("time");
		HashMap<String, String[]> properties = new HashMap<String, String[]>();
		String arg[]={"prop1", "prop2"};
		properties.put("barchart", arg);
		dashboardInfo.setProperties(properties);
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData());
		Response addWid = dashboardService.addDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )addWid.getEntity();
		dashboardInfo.setWidgetid(responseInfo1.getData());
		Response updWid = dashboardService.updateDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo2 = (ResponseInfo<String> )updWid.getEntity();
		Assert.assertEquals(responseInfo2.getStatus(),"error");
	}
	
	@Test
	public void getDashboardWidgetConfigureInfo() throws IOException {
		System.out.println("getDashboardWidgetConfigureInfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		dashboardInfo.setName("wid1");
		dashboardInfo.setQuery("query");
		dashboardInfo.setAutorefresh("true");
		dashboardInfo.setStarttime("time");
		dashboardInfo.setEndtime("time");
		HashMap<String, String[]> properties = new HashMap<String, String[]>();
		String arg[]={"prop1", "prop2"};
		properties.put("barchart", arg);
		dashboardInfo.setProperties(properties);
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData());
		Response addWid = dashboardService.addDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )addWid.getEntity();
		dashboardInfo.setWidgetid(responseInfo1.getData());
		Response updWid = dashboardService.getDashboardWidgetInfo("a98827a3-cfe8-4d57-a682-31cdc7fb4af2", "test_566", responseInfo.getData(), responseInfo1.getData());
		ResponseInfo<Widget> responseInfo2 = (ResponseInfo<Widget> )updWid.getEntity();
		Assert.assertEquals(responseInfo2.getData().getName(),"wid1");
	}
	@Test
	public void getDashboardWidgetConfigureInfoExc() throws IOException {
		System.out.println("getDashboardWidgetConfigureInfoExc test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		dashboardInfo.setName("wid1");
		dashboardInfo.setQuery("query");
		dashboardInfo.setAutorefresh("true");
		dashboardInfo.setStarttime("time");
		dashboardInfo.setEndtime("time");
		HashMap<String, String[]> properties = new HashMap<String, String[]>();
		String arg[]={"prop1", "prop2"};
		properties.put("barchart", arg);
		dashboardInfo.setProperties(properties);
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData());
		Response addWid = dashboardService.addDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )addWid.getEntity();
		dashboardInfo.setWidgetid(responseInfo1.getData());
		Response updWid = dashboardService.getDashboardWidgetInfo("a98827a3-cfe8-4d57-a682-31cdc7fb4af2", "test_566", responseInfo.getData(), "erere23434");
		ResponseInfo<Widget> responseInfo2 = (ResponseInfo<Widget> )updWid.getEntity();
		Assert.assertEquals(responseInfo2.getStatus(),"failure");
	}
	
	@Test
	public void getDashboardWidgetConfigureInfoExc1() throws IOException {
		System.out.println("getDashboardWidgetConfigureInfoExc test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		dashboardInfo.setName("wid1");
		dashboardInfo.setQuery("query");
		dashboardInfo.setAutorefresh("true");
		dashboardInfo.setStarttime("time");
		dashboardInfo.setEndtime("time");
		HashMap<String, String[]> properties = new HashMap<String, String[]>();
		String arg[]={"prop1", "prop2"};
		properties.put("barchart", arg);
		dashboardInfo.setProperties(properties);
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData());
		Response addWid = dashboardService.addDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )addWid.getEntity();
		dashboardInfo.setWidgetid(responseInfo1.getData());
		Response updWid = dashboardService.getDashboardWidgetInfo("a98827a3-cfe8-4d57-a682-31cdc7fb4af2", "esdrferf", responseInfo.getData(), "erere23434");
		ResponseInfo<Widget> responseInfo2 = (ResponseInfo<Widget> )updWid.getEntity();
		Assert.assertEquals(responseInfo2.getStatus(),"error");
	}
	
	@Test
	public void listAllDashboardWidgetConfigureInfo() throws IOException {
		System.out.println("listAllDashboardWidgetConfigureInfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		dashboardInfo.setName("wid1");
		dashboardInfo.setQuery("query");
		dashboardInfo.setAutorefresh("true");
		dashboardInfo.setStarttime("time");
		dashboardInfo.setEndtime("time");
		HashMap<String, String[]> properties = new HashMap<String, String[]>();
		String arg[]={"prop1", "prop2"};
		properties.put("barchart", arg);
		dashboardInfo.setProperties(properties);
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData());
		Response addWid = dashboardService.addDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )addWid.getEntity();
		dashboardInfo.setWidgetid(responseInfo1.getData());
		Response updWid = dashboardService.listDashboardWidgetInfo("a98827a3-cfe8-4d57-a682-31cdc7fb4af2", "test_566");
		ResponseInfo<Dashboards> responseInfo2 = (ResponseInfo<Dashboards> )updWid.getEntity();
		Assert.assertNotNull(responseInfo2.getData());
	}
	
	@Test
	public void listAllDashboardWidgetConfigureInfoExc() throws IOException {
		System.out.println("listAllDashboardWidgetConfigureInfoExc test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		dashboardInfo.setName("wid1");
		dashboardInfo.setQuery("query");
		dashboardInfo.setAutorefresh("true");
		dashboardInfo.setStarttime("time");
		dashboardInfo.setEndtime("time");
		HashMap<String, String[]> properties = new HashMap<String, String[]>();
		String arg[]={"prop1", "prop2"};
		properties.put("barchart", arg);
		dashboardInfo.setProperties(properties);
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData());
		Response addWid = dashboardService.addDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )addWid.getEntity();
		dashboardInfo.setWidgetid(responseInfo1.getData());
		Response updWid = dashboardService.listDashboardWidgetInfo("a98827a3-cfe8-4d57-a682-31cdc7fb4af2", "3434");
		ResponseInfo<Dashboards> responseInfo2 = (ResponseInfo<Dashboards> )updWid.getEntity();
		Assert.assertEquals(responseInfo2.getStatus(),"error");
	}
	
	@Test
	public void listAllDashboardWidgetConfigureInfoExc1() throws IOException {
		System.out.println("listAllDashboardWidgetConfigureInfoExc1 test...");
		Response updWid = dashboardService.listDashboardWidgetInfo("a98827a3-cfe8-4d57-a682-31cdc7fb4af2", "test_566");
		ResponseInfo<Dashboards> responseInfo2 = (ResponseInfo<Dashboards> )updWid.getEntity();
		Assert.assertEquals(responseInfo2.getStatus(),"failure");
	}

	@Test
	public void deleteDashboardWidgetConfigureInfo() throws IOException {
		System.out.println("updateDashboardWidgetConfigureInfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		dashboardInfo.setName("wid1");
		dashboardInfo.setQuery("query");
		dashboardInfo.setAutorefresh("true");
		dashboardInfo.setStarttime("time");
		dashboardInfo.setEndtime("time");
		HashMap<String, String[]> properties = new HashMap<String, String[]>();
		String arg[]={"prop1", "prop2"};
		properties.put("barchart", arg);
		dashboardInfo.setProperties(properties);
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData());
		Response addWid = dashboardService.addDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )addWid.getEntity();
		dashboardInfo.setWidgetid(responseInfo1.getData());
		Response updWid = dashboardService.deleteDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo2 = (ResponseInfo<String> )updWid.getEntity();
		Assert.assertEquals(responseInfo2.getStatus(),"success");
	}
	
	@Test
	public void deleteDashboardWidgetConfigureInfoExc() throws IOException {
		System.out.println("updateDashboardWidgetConfigureInfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		dashboardInfo.setName("wid1");
		dashboardInfo.setQuery("query");
		dashboardInfo.setAutorefresh("true");
		dashboardInfo.setStarttime("time");
		dashboardInfo.setEndtime("time");
		HashMap<String, String[]> properties = new HashMap<String, String[]>();
		String arg[]={"prop1", "prop2"};
		properties.put("barchart", arg);
		dashboardInfo.setProperties(properties);
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData());
		Response addWid = dashboardService.addDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )addWid.getEntity();
		dashboardInfo.setWidgetid(responseInfo1.getData());
		dashboardInfo.setAppdirname("wrw3545t232323");
		Response updWid = dashboardService.deleteDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo2 = (ResponseInfo<String> )updWid.getEntity();
		Assert.assertEquals(responseInfo2.getStatus(),"error");
	}
	
	@Test
	public void deleteDashboardWidgetConfigureInfoNull() throws IOException {
		System.out.println("updateDashboardWidgetConfigureInfo test...");
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setProjectid("a98827a3-cfe8-4d57-a682-31cdc7fb4af2");
		dashboardInfo.setAppcode("test_566");
		dashboardInfo.setAppdirname("test_566");
		dashboardInfo.setAppname("test_566");
		dashboardInfo.setDatatype("splunk");
		dashboardInfo.setUsername("admin");
		dashboardInfo.setPassword("pass");
		dashboardInfo.setUrl("http://abc.com");
		dashboardInfo.setName("wid1");
		dashboardInfo.setQuery("query");
		dashboardInfo.setAutorefresh("true");
		dashboardInfo.setStarttime("time");
		dashboardInfo.setEndtime("time");
		HashMap<String, String[]> properties = new HashMap<String, String[]>();
		String arg[]={"prop1", "prop2"};
		properties.put("barchart", arg);
		dashboardInfo.setProperties(properties);
		Response confDashboard = dashboardService.configureDashboardInfo(dashboardInfo);
		ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
		dashboardInfo.setDashboardid(responseInfo.getData());
		Response addWid = dashboardService.addDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo1 = (ResponseInfo<String> )addWid.getEntity();
		dashboardInfo.setWidgetid("erer454545efdsfdf");
		Response updWid = dashboardService.deleteDashboardWidgetInfo(dashboardInfo);
		ResponseInfo<String> responseInfo2 = (ResponseInfo<String> )updWid.getEntity();
		Assert.assertEquals(responseInfo2.getStatus(),"failure");
	}
	
	
	@Test
	public void searchDataNull() throws IOException, JSONException {
		System.out.println("searchDataNull test...");
		DashboardSearchInfo dashboardSearchInfo = new DashboardSearchInfo();
		String search  = dashboardService.searchData(dashboardSearchInfo);
		System.out.println(search);
		Assert.assertNotNull(search);

	}
	
	@Test
	public void searchDataExc() throws IOException, JSONException, PhrescoException {
		System.out.println("searchDataExc test...");
		DashboardSearchInfo dashboardSearchInfo = new DashboardSearchInfo();
		dashboardSearchInfo.setApplicationname("test");
		dashboardSearchInfo.setDashboardname("test");
		dashboardSearchInfo.setDatatype("splunk");
		dashboardSearchInfo.setUrl("http://abc.com");
		dashboardSearchInfo.setPassword("devsplunk");
		dashboardSearchInfo.setQuery("search source=\"D:\\\\Demo\\\\for file transfer\\\\Production\\\\Production\\\\felix-framework-4.0.2\\\\logger.log\" | top remoteAddress");
		dashboardSearchInfo.setUsername("admin");
		dashboardSearchInfo.setWidgetname("test");
		String search  = dashboardService.searchData(dashboardSearchInfo);
		Assert.assertNotNull(search);

	}
	
	@Test
	public void searchData() throws IOException, JSONException, PhrescoException {
		System.out.println("searchData test...");
		DashboardSearchInfo dashboardSearchInfo = new DashboardSearchInfo();
		dashboardSearchInfo.setApplicationname("test");
		dashboardSearchInfo.setDashboardname("test");
		dashboardSearchInfo.setDatatype("splunk");
		dashboardSearchInfo.setUrl("http://172.16.26.67:8089/");
		dashboardSearchInfo.setPassword("devsplunk");
		dashboardSearchInfo.setQuery("search host=\"MOHAMED_AS\" | stats count(eval(method=\"POST\")) as total, count(eval(status=\"success\")) as success, count(eval(status=\"failure\")) as failure by serviceName | head 7");
		dashboardSearchInfo.setUsername("test");
		dashboardSearchInfo.setWidgetname("test");
		String search  = dashboardService.searchData(dashboardSearchInfo);
		Assert.assertNotNull(search);
	}

	
	@org.junit.After
	public void finishTest() throws IOException {
		FileUtils.deleteDirectory(new File(Utility.getProjectHome()+"/test_566"));
	}
	
	@AfterClass
	public static void afterClass() throws IOException {
		mockServer.stopServer();
	}
}
