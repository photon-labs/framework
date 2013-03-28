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
package com.photon.phresco.framework.actions.forum;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.photon.phresco.commons.model.Property;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.commons.FrameworkUtil;

public class Forum extends FrameworkBaseAction {
	private static final long serialVersionUID = -4282767788002019870L; 
	
	private static final Logger S_LOGGER 	= Logger.getLogger(Forum.class);
	private static Boolean debugEnabled  = S_LOGGER.isDebugEnabled();
	
	public String forum() {
		if (S_LOGGER.isDebugEnabled()) {
			S_LOGGER.debug("entered forumIndex()");
		}
		
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method Forum.forum()");
		}

		try {
			List<Property> properties = getServiceManager().getProperties();
			String forumUrl = "";
			for (Property property : properties) {
				forumUrl = property.getValue();
			}
			
			User sessionUserInfo = (User) getSessionAttribute(SESSION_USER_INFO);
			String encodedPwd = (String) getSessionAttribute(SESSION_USER_PASSWORD);
			
			//get the user credentials from session and encoded by Base64
			String username = sessionUserInfo.getName();
			byte[] userNameEncoded = Base64.encodeBase64(username.getBytes());
			String encodedUsername = new String(userNameEncoded);
			
			setReqAttribute(REQ_USER_NAME, encodedUsername);
			setReqAttribute(REQ_PASSWORD, encodedPwd);
			
			URL sonarURL = new URL(forumUrl);
			HttpURLConnection connection = (HttpURLConnection) sonarURL.openConnection();
			int responseCode = connection.getResponseCode();
			if(responseCode != 200) {
			    getHttpRequest().setAttribute(REQ_ERROR, "Help is not available");
			    return HELP;
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append(forumUrl);
			sb.append(JFORUM_PARAMETER_URL);
			sb.append(JFORUM_USERNAME);
			sb.append(encodedUsername);
			sb.append(JFORUM_PASSWORD);
			sb.append(encodedPwd);
			setReqAttribute(REQ_JFORUM_URL, sb.toString());
			
		} catch (Exception e) {
        	if (debugEnabled) {
        		S_LOGGER.error("Entered into catch block of Forum.forum()" + FrameworkUtil.getStackTraceAsString(e));
    		}
		} 
		return HELP;
	}
}
