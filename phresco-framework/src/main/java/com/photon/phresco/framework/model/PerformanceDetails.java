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
package com.photon.phresco.framework.model;

import java.util.List;

public class PerformanceDetails
{

	private String testAgainst;
	private String showSettings;
	private String setting;
	private String testName;
	private List<ContextUrls> contextUrls;
	private List<DbContextUrls> dbContextUrls;
	private int noOfUsers;
	private int rampUpPeriod;
	private int loopCount;
	private String authorizationUrl;
	private String authorizationUserName;
	private String authorizationPassword;
	private String authorizationDomain;
	private String authorizationRealm;

	public PerformanceDetails()
	{

	}

	public PerformanceDetails(String testAgainst, String showSettings, String setting, String testName, List<ContextUrls> contextUrls,
			List<DbContextUrls> dbContextUrls, int noOfUsers, int rampUpPeriod, int loopCount, String authorizationUrl,
			String authorizationUserName, String authorizationPassword, String authorizationDomain, String authorizationRealm) {
		this.testAgainst = testAgainst;
		this.showSettings = showSettings;
		this.setting = setting;
		this.testName = testName;
		this.contextUrls = contextUrls;
		this.dbContextUrls = dbContextUrls;
		this.noOfUsers = noOfUsers;
		this.rampUpPeriod = rampUpPeriod;
		this.loopCount = loopCount;
		this.authorizationUrl = authorizationUrl;
		this.authorizationUserName = authorizationUserName;
		this.authorizationPassword = authorizationPassword;
		this.authorizationDomain = authorizationDomain;
		this.authorizationRealm = authorizationRealm;
	}

	public String getTestAgainst() {
		return testAgainst;
	}

	public void setTestAgainst(String testAgainst) {
		this.testAgainst = testAgainst;
	}

	public String getShowSettings() {
		return showSettings;
	}

	public void setShowSettings(String showSettings) {
		this.showSettings = showSettings;
	}

	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public int getNoOfUsers() {
		return noOfUsers;
	}

	public void setNoOfUsers(int noOfUsers) {
		this.noOfUsers = noOfUsers;
	}

	public int getRampUpPeriod() {
		return rampUpPeriod;
	}

	public void setRampUpPeriod(int rampUpPeriod) {
		this.rampUpPeriod = rampUpPeriod;
	}

	public int getLoopCount() {
		return loopCount;

	}
	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}

	public void setContextUrls(List<ContextUrls> contextUrls) {
		this.contextUrls = contextUrls;
	}

	public List<ContextUrls> getContextUrls() {
		return contextUrls;
	}

	public List<DbContextUrls> getDbContextUrls() {
		return dbContextUrls;
	}

	public void setDbContextUrls(List<DbContextUrls> dbContextUrls) {
		this.dbContextUrls = dbContextUrls;
	}

	public String getAuthorizationUrl() {
		return authorizationUrl;
	}

	public void setAuthorizationUrl(String authorizationUrl) {
		this.authorizationUrl = authorizationUrl;
	}

	public String getAuthorizationUserName() {
		return authorizationUserName;
	}

	public void setAuthorizationUserName(String authorizationUserName) {
		this.authorizationUserName = authorizationUserName;
	}

	public String getAuthorizationPassword() {
		return authorizationPassword;
	}

	public void setAuthorizationPassword(String authorizationPassword) {
		this.authorizationPassword = authorizationPassword;
	}

	public String getAuthorizationDomain() {
		return authorizationDomain;
	}

	public void setAuthorizationDomain(String authorizationDomain) {
		this.authorizationDomain = authorizationDomain;
	}

	public String getAuthorizationRealm() {
		return authorizationRealm;
	}

	public void setAuthorizationRealm(String authorizationRealm) {
		this.authorizationRealm = authorizationRealm;
	}

	/*public String toString() {
		return String.format("testAgainst:%s,showSettings:%s,setting:%s,testName:%s,name:%s,context:%s,contextType:%s,contextPostData:%s,encodingType:%s,noOfUsers:%d,rampUpPeriod:%d,loopCount:%d",testAgainst,showSettings,setting,testName,noOfUsers,rampUpPeriod,loopCount);
	}*/
}

