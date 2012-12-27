package com.photon.phresco.framework.model;

import java.util.List;

public class ContextUrls {
	private String name;
    private String context;
    private String contextType;
    private String contextPostData;
    private String encodingType;
    private List<Headers> headers;
    
	public ContextUrls() {
	}
	
	public ContextUrls(String name, String context, String contextType,
			String contextPostData, String encodingType, List<Headers> headers) {
		super();
		this.name = name;
		this.context = context;
		this.contextType = contextType;
		this.contextPostData = contextPostData;
		this.encodingType = encodingType;
		this.headers = headers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getContextType() {
		return contextType;
	}

	public void setContextType(String contextType) {
		this.contextType = contextType;
	}

	public String getContextPostData() {
		return contextPostData;
	}

	public void setContextPostData(String contextPostData) {
		this.contextPostData = contextPostData;
	}

	public String getEncodingType() {
		return encodingType;
	}

	public void setEncodingType(String encodingType) {
		this.encodingType = encodingType;
	}

	public void setHeaders(List<Headers> headers) {
		this.headers = headers;
	}

	public List<Headers> getHeaders() {
		return headers;
	}
}
