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
package com.photon.phresco.framework.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerformancResultInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String graphData;
	private String label;
	private String graphAlldata;
	private List<String> images;
	private List<PerformanceTestResult> perfromanceTestResult;
	private TestResultInfo aggregateResult;
	private double totalStdDev;
	private double totalThroughput;
	private String graphFor;
	
	public PerformancResultInfo(String graphData, String label, String graphAlldata,
			List<PerformanceTestResult> perfromanceTestResult, TestResultInfo aggregateResult, double totalStdDev,
			double totalThroughput, String graphFor) {
		super();
		this.graphData = graphData;
		this.label = label;
		this.graphAlldata = graphAlldata;
		this.perfromanceTestResult = perfromanceTestResult;
		this.aggregateResult = aggregateResult;
		this.totalStdDev = totalStdDev;
		this.totalThroughput = totalThroughput;
		this.graphFor = graphFor;
	}
	
	public PerformancResultInfo() {
		super();
	}

	public String getGraphData() {
		return graphData;
	}

	public void setGraphData(String graphData) {
		this.graphData = graphData;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getGraphAlldata() {
		return graphAlldata;
	}

	public void setGraphAlldata(String graphAlldata) {
		this.graphAlldata = graphAlldata;
	}

	public List<PerformanceTestResult> getPerfromanceTestResult() {
		return perfromanceTestResult;
	}

	public void setPerfromanceTestResult(List<PerformanceTestResult> perfromanceTestResult) {
		this.perfromanceTestResult = perfromanceTestResult;
	}

	public TestResultInfo getAggregateResult() {
		return aggregateResult;
	}

	public void setAggregateResult(TestResultInfo aggregateResult) {
		this.aggregateResult = aggregateResult;
	}

	public Double getTotalStdDev() {
		return totalStdDev;
	}

	public void setTotalStdDev(Double totalStdDev) {
		this.totalStdDev = totalStdDev;
	}

	public double getTotalThroughput() {
		return totalThroughput;
	}

	public void setTotalThroughput(double totalThroughput) {
		this.totalThroughput = totalThroughput;
	}

	public String getGraphFor() {
		return graphFor;
	}

	public void setGraphFor(String graphFor) {
		this.graphFor = graphFor;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<String> getImages() {
		return images;
	}

	public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append("graphData", getGraphData())
                .append("label", getLabel())
                .append("graphAlldata", getGraphAlldata())
                .append("perfromanceTestResult", getPerfromanceTestResult())
                .append("aggregateResult", getAggregateResult())
                .append("totalStdDev", getTotalStdDev())
                .append("totalThroughput", getTotalThroughput())
                 .append("graphFor", getGraphFor())
                .toString();
	}
}
