/*
 * ###
 * Phresco Framework Implemenation
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
/*******************************************************************************
 * Copyright (c) 2011 Photon.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Photon Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.photon.in/legal/ppl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *     Photon - initial API and implementation
 ******************************************************************************/
package com.photon.phresco.framework.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.pdf.PdfCopy;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.Technology;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.api.DocumentGenerator;
import com.photon.phresco.framework.docs.impl.DocumentUtil;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Utility;

/**
 *
 * @author arunachalam_l
 *
 */
public class DocumentGeneratorImpl implements DocumentGenerator {
	private static final Logger S_LOGGER= Logger.getLogger(DocumentGeneratorImpl.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
    @Override
    public void generate(ApplicationInfo info, File filePath, List<ArtifactGroup> artifacts, ServiceManager serviceManager)
            throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entering Method DocumentGeneratorImpl.generate(ProjectInfo info, File filePath)");
        }
        OutputStream os = null, fos = null;
        try {
            if (isDebugEnabled) {
                S_LOGGER.debug("generate() Filepath=" + filePath.getPath());
            }
            String folderPath = filePath.toString() + File.separator + "docs";
            File docsFolder = new File(folderPath);
            if (!docsFolder.exists()) {
                docsFolder.mkdirs();
            }
            if (isDebugEnabled) {
                S_LOGGER.debug("generate() ProjectCode=" + info.getCode());
            }
            String path = folderPath + File.separator + info.getName()
                    + "_doc.pdf";
            os = new FileOutputStream(new File(path));

            com.itextpdf.text.Document docu = new com.itextpdf.text.Document();

            PdfCopy pdfCopy = new PdfCopy(docu, os);
            docu.open();
            InputStream titleSection = DocumentUtil.getTitleSection(info);
            DocumentUtil.addPages(titleSection, pdfCopy);
            String techId = info.getTechInfo().getId();
            Technology technology = serviceManager.getTechnology(techId);
            InputStream stringAsPDF = DocumentUtil.getStringAsPDF(technology.getDescription());
            DocumentUtil.addPages(stringAsPDF, pdfCopy);
            
            if(CollectionUtils.isNotEmpty(artifacts)) {
            	DocumentUtil.addPages(artifacts, pdfCopy);
            }

            docu.close();

            //generate index.html
            String indexHtml = DocumentUtil.getIndexHtml(docsFolder);
            File indexPath = new File(docsFolder,"index.html");
            fos = new FileOutputStream(indexPath);
            fos.write(indexHtml.getBytes());
        } catch (IOException e) {
        	e.printStackTrace();
        	if(isDebugEnabled){
        		S_LOGGER.debug("(The process cannot access the file because it is being used by another process");
        	}
            throw new PhrescoException(e);
        } catch (com.itextpdf.text.DocumentException e) {
        	e.printStackTrace();
        	if(isDebugEnabled){
        		S_LOGGER.debug("(The process cannot access the file because it is being used by another process");
        	}
            throw new PhrescoException(e);
        } finally {
            Utility.closeStream(os);
            Utility.closeStream(fos);
        }
    }

}
