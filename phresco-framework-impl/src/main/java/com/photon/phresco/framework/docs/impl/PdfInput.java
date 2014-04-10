/**
 * Phresco Framework Implementation
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
package com.photon.phresco.framework.docs.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class PdfInput {

	private InputStream inputStream;
	private List<HashMap<String,Object>> bookmarks;

	public InputStream getInputStream() {
		return this.inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public List<HashMap<String, Object>> getBookmarks() {
		return this.bookmarks;
	}
	public void setBookmarks(List<HashMap<String, Object>> bookmarks) {
		this.bookmarks = bookmarks;
	}

}
