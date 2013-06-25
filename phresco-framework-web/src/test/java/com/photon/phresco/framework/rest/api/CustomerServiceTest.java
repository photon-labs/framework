package com.photon.phresco.framework.rest.api;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;


public class CustomerServiceTest extends RestBaseTest implements RestTestConstants {

	@Test
	public void testCustomerService() {
		CustomerService customerService = new CustomerService();
		Response response = customerService.findCustomer(USER_ID, CUSTOMER_ID);
		Assert.assertEquals(STATUS, response.getStatus());
	}
}
