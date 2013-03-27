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
package com.photon.phresco.framework.actions.applications;

import org.apache.log4j.Logger;

import com.photon.phresco.commons.model.VersionInfo;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.UpgradeManager;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.util.ServiceConstants;

public class VersionUpdate extends FrameworkBaseAction {
	private static final long serialVersionUID = 1L;

	private static final Logger S_LOGGER = Logger.getLogger(VersionUpdate.class);
	private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();
	// current version
	private String latestVersion = "";
	private String currentVersion = "";
		
	private boolean updateVersionStatus = false;
	private String updateVersionMessage = "";
	private String message = "";
	private boolean updateAvail;

	public String about() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method VersionUpdate.about()");
		}
		return ABOUT;
	}

	public String versionInfo() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method VersionUpdate.versionInfo()");
		}
		try {
			UpgradeManager updateManager = PhrescoFrameworkFactory.getUpdateManager();
			setCurrentVersion(updateManager.getCurrentVersion());
			VersionInfo versionInfo = updateManager.checkForUpdate(getServiceManager(), getCurrentVersion());
			setMessage(versionInfo.getMessage());
			setLatestVersion(versionInfo.getFrameworkVersion());
			setUpdateAvail(versionInfo.isUpdateAvailable());
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of VersionUpdate.versionInfo()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
		}
		
		return SUCCESS;
	}

	public String update() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method VersionUpdate.update()");
		}
		try {
			UpgradeManager updateManager = PhrescoFrameworkFactory.getUpdateManager();
			updateManager.doUpdate(getServiceManager(), getLatestVersion(), ServiceConstants.DEFAULT_CUSTOMER_NAME);
			setUpdateVersionStatus(true);
			setUpdateVersionMessage(getText(ABOUT_SUCCESS_UPDATE));
		} catch (Exception e) {
			setUpdateVersionStatus(false);
			setUpdateVersionMessage(getText(ABOUT_FAILURE_FAILURE));
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of VersionUpdate.update()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
		}
		return SUCCESS;
	}

	public String getLatestVersion() {
		return latestVersion;
	}

	public void setLatestVersion(String latestVersion) {
		this.latestVersion = latestVersion;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isUpdateAvail() {
		return updateAvail;
	}

	public void setUpdateAvail(boolean updateAvail) {
		this.updateAvail = updateAvail;
	}

	public void setUpdateVersionStatus(boolean updateVersionStatus) {
		this.updateVersionStatus = updateVersionStatus;
	}

	public boolean getUpdateVersionStatus() {
		return updateVersionStatus;
	}

	public void setUpdateVersionMessage(String updateVersionMessage) {
		this.updateVersionMessage = updateVersionMessage;
	}

	public String getUpdateVersionMessage() {
		return updateVersionMessage;
	}
}