/**
 * Phresco Framework
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
package com.photon.phresco.commons;


public interface ResponseCodes {
	
	/*
	 * Login page
	 */
	String PHR100001 = "PHR100001"; // Login Success
	String PHR110001 = "PHR110001"; // 'user' object does not exist or is empty
	String PHR110002 = "PHR110002"; // unauthorized user for phresco
	String PHR110003 = "PHR110003"; // invalid credentials or auth service is down
	String PHR110004 = "PHR110004"; // auth service is down
	String PHR110005 = "PHR110005"; // 'user.json' file not found
	String PHR110006 = "PHR110006"; // parsing failed for file 'user.json'
	String PHR110007 = "PHR110007"; // user doesn't have valid permissions
	
	/*
	 * Project Page
	 */
	String PHR200001 = "PHR200001"; // projects listed successfully
	String PHR210001 = "PHR210001"; // failed to list projects
	String PHR200002 = "PHR200002"; // application infos returned successfully
	String PHR210002 = "PHR210002"; // failed to get application infos
	String PHR200003 = "PHR200003"; // no application info available
}