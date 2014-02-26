package com.photon.phresco.framework.model;


public class Keychain {
	private String keychainName;
	private String keychainPath;
	private String keychainPassword;
	private boolean defaultKeychain;
	private boolean inSearchPath;
	public String getKeychainName() {
		return keychainName;
	}
	public void setKeychainName(String keychainName) {
		this.keychainName = keychainName;
	}
	public String getKeychainPath() {
		return keychainPath;
	}
	public void setKeychainPath(String keychainPath) {
		this.keychainPath = keychainPath;
	}
	public String getKeychainPassword() {
		return keychainPassword;
	}
	public void setKeychainPassword(String keychainPassword) {
		this.keychainPassword = keychainPassword;
	}
	public boolean isDefaultKeychain() {
		return defaultKeychain;
	}
	public void setDefaultKeychain(boolean defaultKeychain) {
		this.defaultKeychain = defaultKeychain;
	}
		public boolean isInSearchPath() {
		return inSearchPath;
	}
	public void setInSearchPath(boolean inSearchPath) {
		this.inSearchPath = inSearchPath;
	}
	@Override
	public String toString() {
		return "Keychain [keychainName=" + keychainName + ", keychainPath="
				+ keychainPath + ", keychainPassword=" + keychainPassword
				+ ", defaultKeychain=" + defaultKeychain + ", inSearchPath="
				+ inSearchPath + "]";
	}
	
}
	
