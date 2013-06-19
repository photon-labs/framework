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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class CronExpressionInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String cronBy;
	private String hours;
	private String minutes;
	private String every;
	private List<String> week;
	private List<String> month;
	private String day;
	private String cronExpression;
	private List<String> dates;
	
	public CronExpressionInfo() {
		super();
	}

	public CronExpressionInfo(String cronBy, String hours, String minutes, String every, List<String> week,
			List<String> month, String day, String cronExpression, List<String> dates) {
		super();
		this.cronBy = cronBy;
		this.hours = hours;
		this.minutes = minutes;
		this.every = every;
		this.week = week;
		this.month = month;
		this.day = day;
		this.cronExpression = cronExpression;
		this.dates = dates;
	}

	public String getCronBy() {
		return cronBy;
	}

	public void setCronBy(String cronBy) {
		this.cronBy = cronBy;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public String getEvery() {
		return every;
	}

	public void setEvery(String every) {
		this.every = every;
	}

	public List<String> getWeek() {
		return week;
	}

	public void setWeek(List<String> week) {
		this.week = week;
	}

	public List<String> getMonth() {
		return month;
	}

	public void setMonth(List<String> month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public List<String> getDates() {
		return dates;
	}

	public void setDates(List<String> dates) {
		this.dates = dates;
	}
	
}
