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
package com.photon.phresco.framework.commons;

import java.util.List;
import com.photon.phresco.framework.model.PerformanceTestResult;

public class JmeterReport {
	private String fileName;
	// for each file there will be n number of testResult(There will be many keys)
	private List<PerformanceTestResult> jmeterTestResult;
	// Need to be calculated
	private String totalThroughput;
	private String totalStdDev;
	private String totalNoOfSample;
	private String totalAvg;
	private String min;
	private String max;
	private String totalErr;
	private String totalKbPerSec;
	private String totalAvgBytes;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<PerformanceTestResult> getJmeterTestResult() {
		return jmeterTestResult;
	}
	public void setJmeterTestResult(List<PerformanceTestResult> jmeterTestResult) {
		this.jmeterTestResult = jmeterTestResult;
	}
	public String getTotalThroughput() {
		return totalThroughput;
	}
	public void setTotalThroughput(String totalThroughput) {
		this.totalThroughput = totalThroughput;
	}
	public String getTotalStdDev() {
		return totalStdDev;
	}
	public void setTotalStdDev(String totalStdDev) {
		this.totalStdDev = totalStdDev;
	}
	public String getTotalNoOfSample() {
		return totalNoOfSample;
	}
	public void setTotalNoOfSample(String totalNoOfSample) {
		this.totalNoOfSample = totalNoOfSample;
	}
	public String getTotalAvg() {
		return totalAvg;
	}
	public void setTotalAvg(String totalAvg) {
		this.totalAvg = totalAvg;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getTotalErr() {
		return totalErr;
	}
	public void setTotalErr(String totalErr) {
		this.totalErr = totalErr;
	}
	public String getTotalKbPerSec() {
		return totalKbPerSec;
	}
	public void setTotalKbPerSec(String totalKbPerSec) {
		this.totalKbPerSec = totalKbPerSec;
	}
	public String getTotalAvgBytes() {
		return totalAvgBytes;
	}
	public void setTotalAvgBytes(String totalAvgBytes) {
		this.totalAvgBytes = totalAvgBytes;
	}


}