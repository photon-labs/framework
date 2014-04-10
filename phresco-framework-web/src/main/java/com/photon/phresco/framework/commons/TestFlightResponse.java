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
package com.photon.phresco.framework.commons;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestFlightResponse {


	String bundle_version;
	String install_url;
	String config_url;
	String created_at;
	String device_family;
	Boolean notify;
	String team;
	String minimum_os_version;
	String release_notes;
	Integer binary_size;
	
	public String getBundle_version() {
		return bundle_version;
	}
	public void setBundle_version(String bundle_version) {
		this.bundle_version = bundle_version;
	}
	public String getInstall_url() {
		return install_url;
	}
	public void setInstall_url(String install_url) {
		this.install_url = install_url;
	}
	public String getConfig_url() {
		return config_url;
	}
	public void setConfig_url(String config_url) {
		this.config_url = config_url;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getDevice_family() {
		return device_family;
	}
	public void setDevice_family(String device_family) {
		this.device_family = device_family;
	}
	public Boolean getNotify() {
		return notify;
	}
	public void setNotify(Boolean notify) {
		this.notify = notify;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getMinimum_os_version() {
		return minimum_os_version;
	}
	public void setMinimum_os_version(String minimum_os_version) {
		this.minimum_os_version = minimum_os_version;
	}
	public String getRelease_notes() {
		return release_notes;
	}
	public void setRelease_notes(String release_notes) {
		this.release_notes = release_notes;
	}
	public Integer getBinary_size() {
		return binary_size;
	}
	public void setBinary_size(Integer binary_size) {
		this.binary_size = binary_size;
	}
	
	

}
