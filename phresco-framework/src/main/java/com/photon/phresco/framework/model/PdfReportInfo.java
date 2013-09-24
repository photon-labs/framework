package com.photon.phresco.framework.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class PdfReportInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String  time;
	private String  type;
	private String fileName;
	
	public PdfReportInfo() {
		super();
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String toString() {
		 return new ToStringBuilder(this,
	                ToStringStyle.DEFAULT_STYLE)
	                .append(super.toString())
	                .append("time", getTime())
	                .append("type", getType())
	                .append("fileName", getFileName())
	                .toString();
	}
}
