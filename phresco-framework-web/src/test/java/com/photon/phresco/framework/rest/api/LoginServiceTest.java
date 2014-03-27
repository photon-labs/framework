package com.photon.phresco.framework.rest.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.client.impl.ServiceManagerImpl;
import com.photon.phresco.util.Credentials;

public class LoginServiceTest extends RestBaseTest {
	
	
	@org.junit.Before
	public  void setUp() throws PhrescoException {
		ServiceManagerImpl impl= (ServiceManagerImpl) getServiceManager("phresco", "Phresco@123");
		List<User> users = new ArrayList<User>();
		User user = new User();
		user.setId("dummy");
		user.setPassword("2284e85c761030af467677d8bc8404d");//"dummy"
		user.setFirstName("dummy");
		user.setLastName("dummy");
		user.setEmail("dummy@photoninfot.net");
		user.setName("dummy");
		users.add(user);
		List<User> users1 = new ArrayList<User>();
		User user1 = new User();
		user1.setId("dummy1");
		user1.setPassword("2284e85c761030af467677d8bc8404d");//"dummy"
		user1.setFirstName("dummy1");
		user1.setLastName("dummy1");
		user1.setName("dummy1");
		users1.add(user1);
		
		impl.createUsers(users1, "dummy1", "");
		impl.createUsers(users, "dummy", "");
	}
	
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
	
	/*@Test
	public void changePasswordTest() throws PhrescoException {
		
		LoginService service = new LoginService();
		Credentials credentials = new Credentials("dummy", "dummy");
		Response response1 = service.authenticate(credentials);
		ResponseInfo<User> responseInfo1 = (ResponseInfo<User>) response1.getEntity();
		User user = responseInfo1.getData();
		Credentials oldCred = new Credentials("dummy", "dummy");
		Credentials newCred = new Credentials("dummy", "dummy1");
		List<Credentials> creds = new ArrayList<Credentials>();
		creds.add(oldCred);
		creds.add(newCred);
		Response response = service.changePassword(creds);
		
		ResponseInfo<Boolean> responseInfo = (ResponseInfo<Boolean>) response.getEntity();
		System.out.println("changePasswordTest ...response info :"+responseInfo);
		Boolean result = responseInfo.getData();
		System.out.println("changePasswordTest ...result:"+result);
		Assert.assertEquals(true, result);
		Assert.assertEquals(200, response.getStatus());
	}*/
	
	/*@Test
	public void changePasswordFailureTest() throws PhrescoException {
		
		LoginService service = new LoginService();
		Credentials credentials = new Credentials("2432323", "43434");
		Response response1 = service.authenticate(credentials);
		ResponseInfo<User> responseInfo1 = (ResponseInfo<User>) response1.getEntity();
		User user = responseInfo1.getData();
		Credentials oldCred = new Credentials("dummy11", "dummy11");
		Credentials newCred = new Credentials("dummy", "dummy1");
		List<Credentials> creds = new ArrayList<Credentials>();
		creds.add(oldCred);
		creds.add(newCred);
		Response response = service.changePassword(creds);
		ResponseInfo<Boolean> responseInfo = (ResponseInfo<Boolean>) response.getEntity();
		Boolean result = responseInfo.getData();
		Assert.assertEquals(null, result);
	}*/
	
//	@Test
//	public void changePasswordFailure1Test() throws PhrescoException {
//		
//		LoginService service = new LoginService();
//		Credentials credentials = new Credentials("dummy", "dummy");
//		Response response1 = service.authenticate(credentials);
//		ResponseInfo<User> responseInfo1 = (ResponseInfo<User>) response1.getEntity();
//		User user = responseInfo1.getData();
//		Credentials oldCred = new Credentials("dummy333", "dummy333");
//		Credentials newCred = new Credentials("dummy", "dummy1");
//		List<Credentials> creds = new ArrayList<Credentials>();
//		creds.add(oldCred);
//		creds.add(newCred);
//		Response response = service.changePassword(creds);
//		ResponseInfo<Boolean> responseInfo = (ResponseInfo<Boolean>) response.getEntity();
//		Boolean result = responseInfo.getData();
//		System.out.println("result "+ result);
//		Assert.assertEquals(null, result);
//	}
	
	@Test
	public void forgotPasswordTest() throws PhrescoException {
		LoginService service = new LoginService();
		Response response = service.forgotPassword("dummy","photon");
		Credentials credentials = new Credentials("dummy", "dummy");
		Response response1 = service.authenticate(credentials);
		ResponseInfo<User> responseInfo1 = (ResponseInfo<User>) response1.getEntity();
		User user = responseInfo1.getData();
		Assert.assertEquals(null, user);
	}
	
	/*@Test
	public void forgotPasswordFailureTest() throws PhrescoException {
		LoginService service = new LoginService();
		Response response = service.forgotPassword("dummy11","photon");
		Credentials credentials = new Credentials("dummy", "dummy");
		Response response1 = service.authenticate(credentials);
		ResponseInfo<User> responseInfo1 = (ResponseInfo<User>) response1.getEntity();
		System.out.println("forgotPasswordFailureTest ...responseInfo1:"+responseInfo1);
		User user = responseInfo1.getData();
		System.out.println("forgotPasswordFailureTest ...user:"+user);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void forgotPasswordFailure1Test() throws PhrescoException {
		LoginService service = new LoginService();
		Response response = service.forgotPassword("dummy1","photon");
		Credentials credentials = new Credentials("dummy", "dummy");
		Response response1 = service.authenticate(credentials);
		ResponseInfo<User> responseInfo1 = (ResponseInfo<User>) response1.getEntity();
		System.out.println("forgotPasswordFailure1Test ...responseInfo1:"+responseInfo1);
		User user = responseInfo1.getData();
		System.out.println("user:"+user);
		Assert.assertNotNull(user);
	}
	*/
	
	@After
	public void finish() throws PhrescoException {
		ServiceManagerImpl impl= (ServiceManagerImpl) getServiceManager(userId, password);
		impl.deleteUser("dummy");
		impl.deleteUser("dummy1");

	}
}
