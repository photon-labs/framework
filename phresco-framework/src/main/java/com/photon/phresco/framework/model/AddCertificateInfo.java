/**
 * Phresco Framework
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

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddCertificateInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String customerId;
	private String host;
	private String port;
	private String appDirName;
	private String certificateName;
	private String fromPage;
	private String environmentName;
	private String configName;
	private String propValue;
	
	public AddCertificateInfo() {
		super();
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getAppDirName() {
		return appDirName;
	}

	public void setAppDirName(String appDirName) {
		this.appDirName = appDirName;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}
	
	public String toString() {
		 return new ToStringBuilder(this,
	                ToStringStyle.DEFAULT_STYLE)
	                .append(super.toString())
	                .append("customerId", getCustomerId())
	                .append("host", getHost())
	                .append("port", getPort())
	                 .append("appDirName", getAppDirName())
	                .append("certificateName", getCertificateName())
	                .append("fromPage", getFromPage())
	                 .append("environmentName", getEnvironmentName())
	                .append("configName", getConfigName())
	                .append("propValue", getPropValue())
	                .toString();
	}
}
