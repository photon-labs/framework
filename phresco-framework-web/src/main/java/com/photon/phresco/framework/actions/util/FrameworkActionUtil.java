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
package com.photon.phresco.framework.actions.util;

import com.opensymphony.xwork2.ActionSupport;
import com.photon.phresco.commons.FrameworkConstants;

public class FrameworkActionUtil implements FrameworkConstants {
	
	static ActionSupport actionSupport = new ActionSupport();
	
	public static String getTitle(String page, String from) {
		String propKey = getPropKey(LBL, page, from);
		return actionSupport.getText(propKey);
	}
	
	public static String getButtonLabel(String from) {
		String propKey = getPropKey(LBL_BTN, "", from);
		return actionSupport.getText(propKey);
	}
	
	public static String getPageUrl(String page, String from) {
		String propKey = getPropKey(LBL_URL, page, from);
		return actionSupport.getText(propKey);
	}
	
	public static String getProgressTxt(String page, String from) {
		String propKey = getPropKey(LBL_PROG_TXT, page, from);
		return actionSupport.getText(propKey);
	}
	
	private static String getPropKey(String label, String page, String from) {
		StringBuilder sb = new StringBuilder();
		sb.append(label);
		sb.append(page);
		sb.append(DOT);
		sb.append(from);
		return sb.toString();
	}
}
