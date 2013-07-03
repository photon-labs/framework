package com.photon.phresco.framework.rest.api;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

public class TechnologyServiceTest extends RestBaseTest {
	TechnologyService technologyService = new TechnologyService();
	
	public TechnologyServiceTest() {
		super();
	}
	
//	@Test
	public void fetchListAppTypes() {
		Response listApptypesLoginFail = technologyService.listApptypes("sample", "photon");
		Assert.assertEquals(400, listApptypesLoginFail.getStatus());
		Response listApptypes = technologyService.listApptypes("admin", "photon");
		Assert.assertEquals(200, listApptypes.getStatus());
//		Response listApptypesFail = technologyService.listApptypes("admin", "");
//		ResponseInfo<List<ApplicationType>> e = (ResponseInfo<List<ApplicationType>> )listApptypesFail.getEntity();
//		System.out.println(e.getData());
//		Assert.assertEquals(400, listApptypesFail.getStatus());
	}
	
	@Test
	public void fetchListAppTypeTechInfos() {
		Response listApptypeTechInfo = technologyService.listApptypeTechInfo("Photon", "admin");
		Assert.assertEquals(200, listApptypeTechInfo.getStatus());
		Response customer = technologyService.listApptypeTechInfo("Walgreens", "admin");
		Assert.assertEquals(200, customer.getStatus());
//		Response customerFail = technologyService.listApptypeTechInfo("Johnson", "admin");
//		Assert.assertEquals(400, customerFail.getStatus());
		
	}
	
	@Test
	public void fetchListTechnologyGroups() {
		Response listtechnologyGroupLoginFail = technologyService.listtechnologyGroup("photon", "app-layer", "sample");
		Assert.assertEquals(400, listtechnologyGroupLoginFail.getStatus());
//		Response listtechnologyGroup = technologyService.listtechnologyGroup("photon", "web-layer", "admin");
//		Assert.assertEquals(200, listtechnologyGroup.getStatus());
//		Response listtechnologyGroupFail = technologyService.listtechnologyGroup("photon", "app-laye", "admin");
//		Assert.assertEquals(400, listtechnologyGroupFail.getStatus());
	}
}
