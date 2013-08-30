package com.photon.phresco.framework.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class MinifyInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String  compressName;
	private String  csvFileName;
	private String opFileLoc;
	private String fileType;
	
	public MinifyInfo() {
		super();
	}

	public String getCompressName() {
		return compressName;
	}

	public void setCompressName(String compressName) {
		this.compressName = compressName;
	}

	public String getCsvFileName() {
		return csvFileName;
	}

	public void setCsvFileName(String csvFileName) {
		this.csvFileName = csvFileName;
	}

	public String getOpFileLoc() {
		return opFileLoc;
	}

	public void setOpFileLoc(String opFileLoc) {
		this.opFileLoc = opFileLoc;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String toString() {
		 return new ToStringBuilder(this,
	                ToStringStyle.DEFAULT_STYLE)
	                .append(super.toString())
	                .append("compressName", getCompressName())
	                .append("csvFileName", getCsvFileName())
	                .append("opFileLoc", getOpFileLoc())
	                .append("fileType", getFileType())
	                .toString();
	}

}
