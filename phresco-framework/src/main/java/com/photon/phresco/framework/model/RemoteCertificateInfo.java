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
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.photon.phresco.commons.model.CertificateInfo;
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoteCertificateInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean isCertificateAvailable;
	private String projectLocation;
	private List<CertificateInfo> certificates;
	
	public RemoteCertificateInfo() {
		super();
	}

	public RemoteCertificateInfo(boolean isCertificateAvailable, String projectLocation,
			List<CertificateInfo> certificates) {
		super();
		this.isCertificateAvailable = isCertificateAvailable;
		this.projectLocation = projectLocation;
		this.certificates = certificates;
	}

	public boolean isCertificateAvailable() {
		return isCertificateAvailable;
	}

	public void setCertificateAvailable(boolean isCertificateAvailable) {
		this.isCertificateAvailable = isCertificateAvailable;
	}

	public String getProjectLocation() {
		return projectLocation;
	}

	public void setProjectLocation(String projectLocation) {
		this.projectLocation = projectLocation;
	}

	public List<CertificateInfo> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<CertificateInfo> certificates) {
		this.certificates = certificates;
	}
	
	public String toString() {
		 return new ToStringBuilder(this,
	                ToStringStyle.DEFAULT_STYLE)
	                .append(super.toString())
	                .append("isCertificateAvailable", isCertificateAvailable())
	                .append("projectLocation", getProjectLocation())
	                .append("certificates", getCertificates())
	                .toString();
	}

}
