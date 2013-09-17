package com.photon.phresco.framework.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckLockInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean lock;
	private String lockedBy;
	private String lockedDate;
	private String lockActionCode;
	
	public CheckLockInfo() {
		super();
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public String getLockedBy() {
		return lockedBy;
	}

	public void setLockedBy(String lockedBy) {
		this.lockedBy = lockedBy;
	}

	public String getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(String lockedDate) {
		this.lockedDate = lockedDate;
	}
	
	public void setLockActionCode(String lockActionCode) {
		this.lockActionCode = lockActionCode;
	}

	public String getLockActionCode() {
		return lockActionCode;
	}

	public String toString() {
		 return new ToStringBuilder(this,
	                ToStringStyle.DEFAULT_STYLE)
	                .append(super.toString())
	                .append("lock", isLock())
	                .append("lockedBy", getLockedBy())
	                .append("lockedDate", getLockedDate())
	                .toString();
	}

}
