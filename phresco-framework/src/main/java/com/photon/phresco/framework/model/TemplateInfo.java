package com.photon.phresco.framework.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean envSpecific;
	private boolean favourite;
	private String templateName;

	public TemplateInfo() {
		super();
	}

	public boolean isEnvSpecific() {
		return envSpecific;
	}

	public void setEnvSpecific(boolean envSpecific) {
		this.envSpecific = envSpecific;
	}

	public boolean isFavourite() {
		return favourite;
	}

	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	public String toString() {
		 return new ToStringBuilder(this,
	                ToStringStyle.DEFAULT_STYLE)
	                .append(super.toString())
	                .append("envSpecific", isEnvSpecific())
	                .append("favourite", isFavourite())
	                .append("templateName", getTemplateName())
	                .toString();
	}
}
