/**
 * Framework Web Archive
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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
package com.photon.phresco.framework.actions;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.util.Credentials;

public class LoginTest implements FrameworkConstants{

	//@Test
	public void test() {
        try {
        	User userInfo = null;
            String username = "kaleeswaran_s";
			String password = "Suresh@123";
			Credentials credentials = new Credentials(username, password);
//            userInfo = administrator.doLogin(credentials);
            
//            if (!userInfo.isLoginValidation()) {
//            	System.out.println("Login failure1!!!!!!");
//            }
            if (!userInfo.isPhrescoEnabled()) {
            	System.out.println("Login failure2!!!!!!");
            	
            } else {
            	System.out.println("Login SUCCESS!!!!!!" + userInfo.getDisplayName());
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("Login failure3!!!!!!");
        }
	}

//	@Test
	public void copyFileAndFolder() {
		try {
			File srcDir = new File("/Users/kaleeswaran/workspace/projects/PHR_Walgreens_latest/make/2012-09-17-1");
			File destDir = new File("/Users/kaleeswaran/workspace/projects/PHR_Walgreens_latest/do_not_checkin/static-analysis-report");
			FileUtils.copyDirectory(srcDir, destDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
