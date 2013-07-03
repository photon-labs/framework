package com.photon.phresco.framework.rest.api;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.json.simple.JSONObject;
import org.junit.Test;


public class CustomerServiceTest extends RestBaseTest implements RestTestConstants {

	
	@Test
	public void testCustomerService() {
		CustomerService customerService = new CustomerService();
		Response response = customerService.findCustomer(USER_ID, CUSTOMER_ID);
		ResponseInfo<JSONObject> responseInfo = (ResponseInfo<JSONObject>) response.getEntity();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("Customer theme fetched", responseInfo.getMessage());
	}
	
	@Test
	public void testCustomerServiceWithOutUserId() {
		CustomerService customerService = new CustomerService();
		Response response = customerService.findCustomer("", CUSTOMER_ID);
		ResponseInfo<JSONObject> responseInfo = (ResponseInfo<JSONObject>) response.getEntity();
		Assert.assertEquals(400, response.getStatus());
		Assert.assertEquals("UnAuthorized User", responseInfo.getMessage());
	}
	
	@Test
	public void testCustomerServiceWithOutCustomerId() {
		CustomerService customerService = new CustomerService();
		Response response = customerService.findCustomer(USER_ID, "");
		ResponseInfo<JSONObject> responseInfo = (ResponseInfo<JSONObject>) response.getEntity();
		Assert.assertEquals(400, response.getStatus());
		Assert.assertEquals("Customer theme is not fetched", responseInfo.getMessage());
	}
	
}
