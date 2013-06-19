package com.photon.phresco.framework.rest.api;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

import com.photon.phresco.commons.model.User;
import com.photon.phresco.util.Credentials;

public class LoginServiceTest extends RestBaseTest {
	
	@Test
	public void loginTest() {
		LoginService service = new LoginService();
		Credentials credentials = new Credentials(userId, password);
		Response response = service.authenticate(credentials);
		ResponseInfo<User> responseInfo = (ResponseInfo<User>) response.getEntity();
		User user = responseInfo.getData();
		serviceManager  = CONTEXT_MANAGER_MAP.get(userId);
		Assert.assertNotNull(user);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(userId, user.getId());
	}
	
}
