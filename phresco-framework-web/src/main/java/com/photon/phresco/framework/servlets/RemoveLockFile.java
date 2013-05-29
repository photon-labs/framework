/**
 * Service Web Archive
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
package com.photon.phresco.framework.servlets;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class RemoveLockFile extends HttpServlet implements Constants, FrameworkConstants {
	
	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException {
		super.init();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		StringBuilder sb = new StringBuilder(Utility.getPhrescoHome())
		.append(File.separator)
		.append(PROJECTS_WORKSPACE)
		.append(File.separator)
    	.append(LOCK_FILE);
		File file = new File(sb.toString());
		file.delete();
	}
}
