package com.photon.phresco.framework.model;

import java.util.List;

public class CIJobTemplate {
	// Many customer can not have the same name - TBD
	private String name;
	private String type;

	// can be applied to many applicaions
	private List<String> appIds;
	// This template is assigned to many applications of the same project
	private String projId;

	private boolean enableRepo;
	private String repoTypes;
	private boolean enableSheduler;
	private boolean enableEmailSettings;
	private boolean enableUploadSettings;
	private List<String> uploadTypes;
	
	private String customerId;
	private String userId;
	
	public CIJobTemplate() {
		super();
	}

	public CIJobTemplate(String name, String type, List<String> appIds, String projId) {
		super();
		this.name = name;
		this.type = type;
		this.appIds = appIds;
		this.projId = projId;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 * the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the appIds
	 */
	public List<String> getAppIds() {
		return appIds;
	}

	/**
	 * @param appIds
	 *            the appIds to set
	 */
	public void setAppIds(List<String> appIds) {
		this.appIds = appIds;
	}

	/**
	 * @return the projId
	 */
	public String getProjId() {
		return projId;
	}

	/**
	 * @param projId
	 *            the projId to set
	 */
	public void setProjId(String projId) {
		this.projId = projId;
	}

	/**
	 * @return the enableRepo
	 */
	public boolean isEnableRepo() {
		return enableRepo;
	}

	/**
	 * @param enableRepo
	 *            the enableRepo to set
	 */
	public void setEnableRepo(boolean enableRepo) {
		this.enableRepo = enableRepo;
	}


	/**
	 * @return the repoTypes
	 */
	public String getRepoTypes() {
		return repoTypes;
	}

	/**
	 * @param repoTypes the repoTypes to set
	 */
	public void setRepoTypes(String repoTypes) {
		this.repoTypes = repoTypes;
	}

	/**
	 * @return the enableSheduler
	 */
	public boolean isEnableSheduler() {
		return enableSheduler;
	}

	/**
	 * @param enableSheduler
	 *            the enableSheduler to set
	 */
	public void setEnableSheduler(boolean enableSheduler) {
		this.enableSheduler = enableSheduler;
	}

	/**
	 * @return the enableEmailSettings
	 */
	public boolean isEnableEmailSettings() {
		return enableEmailSettings;
	}

	/**
	 * @param enableEmailSettings
	 *            the enableEmailSettings to set
	 */
	public void setEnableEmailSettings(boolean enableEmailSettings) {
		this.enableEmailSettings = enableEmailSettings;
	}

	/**
	 * @return the enableUploadSettings
	 */
	public boolean isEnableUploadSettings() {
		return enableUploadSettings;
	}

	/**
	 * @param enableUploadSettings
	 *            the enableUploadSettings to set
	 */
	public void setEnableUploadSettings(boolean enableUploadSettings) {
		this.enableUploadSettings = enableUploadSettings;
	}

	/**
	 * @return the uploadTypes
	 */
	public List<String> getUploadTypes() {
		return uploadTypes;
	}

	/**
	 * @param uploadTypes
	 *            the uploadTypes to set
	 */
	public void setUploadTypes(List<String> uploadTypes) {
		this.uploadTypes = uploadTypes;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CIJobTemplate [name=" + name + ", type=" + type + ", appIds="
				+ appIds + ", projId=" + projId + ", enableRepo=" + enableRepo
				+ ", repoTypes=" + repoTypes + ", enableSheduler="
				+ enableSheduler + ", enableEmailSettings="
				+ enableEmailSettings + ", enableUploadSettings="
				+ enableUploadSettings + ", uploadTypes=" + uploadTypes + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appIds == null) ? 0 : appIds.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((projId == null) ? 0 : projId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CIJobTemplate other = (CIJobTemplate) obj;
		if (appIds == null) {
			if (other.appIds != null)
				return false;
		} else if (!appIds.equals(other.appIds))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (projId == null) {
			if (other.projId != null)
				return false;
		} else if (!projId.equals(other.projId))
			return false;
		return true;
	}

}
