package com.photon.phresco.framework.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.framework.actions.FrameworkBaseAction;

public class SessionValidatorInterceptor extends FrameworkBaseAction implements Interceptor  {
	
	private static final long serialVersionUID = 1L;

	public void destroy() {  
    }  
  
    public void init() {  
    }
	    
	public String intercept(ActionInvocation invocation) throws Exception {
		User user = (User) getHttpSession().getAttribute(SESSION_USER_INFO);
		if (user == null) {
			getHttpSession().setAttribute(REQ_LOGIN_ERROR, getText(SESSION_EXPIRED));
			
			return LOGIN_SUCCESS;
		}
		
		return invocation.invoke();
	}
}