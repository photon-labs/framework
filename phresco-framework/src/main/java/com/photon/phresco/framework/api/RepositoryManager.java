package com.photon.phresco.framework.api;

import java.util.List;
import org.w3c.dom.Document;
import com.photon.phresco.exception.PhrescoException;

public interface RepositoryManager {

	Document getSource(String appDirName, String username, String password, String srcRepoUrl) throws PhrescoException  ;
	
	
}
