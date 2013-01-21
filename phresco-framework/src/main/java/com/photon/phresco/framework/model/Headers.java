package com.photon.phresco.framework.model;

public class Headers {
	private String key;
    private String value;
   
    public Headers() {
		super();
	}
	
    public Headers(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
