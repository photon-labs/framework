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

import java.util.List;

public class ContextUrls {
	private String name;
    private String context;
    private String contextType;
    private String contextPostData;
    private String encodingType;
    private boolean redirectAutomatically;
    private boolean followRedirects;
	private boolean keepAlive;
    private boolean multipartData;
    private boolean compatibleHeaders;
    private List<Headers> headers;
    private List<Parameters> parameters;
    
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

	public boolean isRedirectAutomatically() {
		return redirectAutomatically;
	}

	public void setRedirectAutomatically(boolean redirectAutomatically) {
		this.redirectAutomatically = redirectAutomatically;
	}

	public boolean isFollowRedirects() {
		return followRedirects;
	}

	public void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	public boolean isMultipartData() {
		return multipartData;
	}

	public void setMultipartData(boolean multipartData) {
		this.multipartData = multipartData;
	}

	public boolean isCompatibleHeaders() {
		return compatibleHeaders;
	}

	public void setCompatibleHeaders(boolean compatibleHeaders) {
		this.compatibleHeaders = compatibleHeaders;
	}

	public List<Parameters> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameters> parameters) {
		this.parameters = parameters;
	}
}
