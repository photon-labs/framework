/*
 * ###
 * Framework Web Archive
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.framework.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.photon.phresco.commons.model.LogInfo;
import com.photon.phresco.exception.PhrescoException;
import com.sun.jersey.api.client.ClientResponse;

public class ErrorReport extends FrameworkBaseAction {
	private static final long serialVersionUID = 1L;
	
	private static final Logger S_LOGGER = Logger.getLogger(ErrorReport.class);
	private static Boolean debugEnabled  = S_LOGGER.isDebugEnabled();
	
	private String message = "";
	private String action = "";
	private String userid = "";
	private String trace = "";
	private boolean sendReportStatus; 
	private String sendReportMsg = "";
	
	public String sendReport() throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  ErrorReport.sendReport() message" + message + " action" + action + " userid" + userid );
		}
        try {
        	LogInfo loginfo = new LogInfo(message, trace, action, userid);
        	List<LogInfo> infos = new ArrayList<LogInfo>();
        	infos.add(loginfo);
        	if (debugEnabled) {
        		S_LOGGER.debug("Going to send error report to service ");
    		}
        	ClientResponse reportStatus = getServiceManager().sendErrorReport(infos);
        	if (reportStatus.getStatus()== 200) {
	        	setSendReportStatus(true);
	        	setSendReportMsg(SUCCESS_SEND_ERROR_REPORT);
        	}
        } catch (Exception e) {
        	throw new PhrescoException(e);
        }

        return SUCCESS;
	}
	
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @return the trace
	 */
	public String getTrace() {
		return trace;
	}
	/**
	 * @param trace the trace to set
	 */
	public void setTrace(String trace) {
		this.trace = trace;
	}

	public String getSendReportMsg() {
		return sendReportMsg;
	}


	public void setSendReportMsg(String sendReportMsg) {
		this.sendReportMsg = sendReportMsg;
	}


	public boolean getSendReportStatus() {
		return sendReportStatus;
	}


	public void setSendReportStatus(boolean sendReportStatus) {
		this.sendReportStatus = sendReportStatus;
	}

}
