package com.photon.phresco.framework.model;

import java.io.Serializable;

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
	private String week;
	private String month;
	private String day;
	
	public CronExpressionInfo() {
		super();
	}

	public CronExpressionInfo(String cronBy, String hours, String minutes, String every, String week, String month,
			String day) {
		super();
		this.cronBy = cronBy;
		this.hours = hours;
		this.minutes = minutes;
		this.every = every;
		this.week = week;
		this.month = month;
		this.day = day;
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

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
}
