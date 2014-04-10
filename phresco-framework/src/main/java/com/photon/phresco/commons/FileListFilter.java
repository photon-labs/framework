/**
 * Phresco Framework
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
package com.photon.phresco.commons;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class FileListFilter implements FilenameFilter {

	private String name;
	private List<String> extension;

	public FileListFilter(String name, String... extension) {
		this.name = name;
		this.extension = Arrays.asList(extension);
	}

	public boolean accept(File directory, String filename) {
		boolean fileOK = true;

		if (name != null) {
			fileOK &= filename.startsWith(name);
		}

		if (extension != null) {
		    int mid = filename.lastIndexOf(".");
		    String ext = filename.substring(mid + 1, filename.length());
			fileOK &= extension.contains(ext);  // ext.endsWith('.' + extension);
		}
		return fileOK;
	}
}
