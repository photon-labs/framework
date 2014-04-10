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
package com.photon.phresco.framework.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestResultInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer sample;
	private Float average;
	private Integer min;
	private Integer max;
	private Float stdDev;
	private String error;
	private Float throughput;
	private Float kb;
	private Float avgBytes;
	
	public TestResultInfo() {
		super();
	}

	public TestResultInfo(Integer sample, Float average, Integer min, Integer max, Float stdDev, String error,
			Float throughput, Float kb, Float avgBytes) {
		super();
		this.sample = sample;
		this.average = average;
		this.min = min;
		this.max = max;
		this.stdDev = stdDev;
		this.error = error;
		this.throughput = throughput;
		this.kb = kb;
		this.avgBytes = avgBytes;
	}

	public Integer getSample() {
		return sample;
	}

	public void setSample(Integer sample) {
		this.sample = sample;
	}

	public Float getAverage() {
		return average;
	}

	public void setAverage(Float average) {
		this.average = average;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Float getStdDev() {
		return stdDev;
	}

	public void setStdDev(Float stdDev) {
		this.stdDev = stdDev;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Float getThroughput() {
		return throughput;
	}

	public void setThroughput(Float throughput) {
		this.throughput = throughput;
	}

	public Float getKb() {
		return kb;
	}

	public void setKb(Float kb) {
		this.kb = kb;
	}

	public Float getAvgBytes() {
		return avgBytes;
	}

	public void setAvgBytes(Float avgBytes) {
		this.avgBytes = avgBytes;
	}
	
	public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append("sample", getSample())
                .append("average", getAverage())
                .append("min", getMin())
                .append("max", getMax())
                .append("stdDev", getStdDev())
                .append("error", getError())
                .append("throughput", getThroughput())
                .append("kb", getKb())
                .append("avgBytes", getAvgBytes())
                .toString();
    }
}
