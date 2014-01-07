package com.photon.phresco.framework;

import org.codehaus.plexus.util.StringUtils;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.api.RepositoryManager;

public class RepositoryFactory {
	
	private final static String SVN_IMPL_CLASS = "com.photon.phresco.framework.repository.SvnRepositoryImpl";
	private final static String GIT_IMPL_CLASS = "com.photon.phresco.framework.repository.GitRepositoryImpl";
	
	public static RepositoryManager getRepository(String repoType) {
		RepositoryManager constructClass = null;
		try {
			String className = getClassName(repoType);
			if (StringUtils.isNotEmpty(className)) {
				constructClass = (RepositoryManager) constructClass(className);
			}
		} catch (PhrescoException e) {
			e.printStackTrace();
		}
		return constructClass;
	}
	
	 public static String getClassName(String repoType) throws PhrescoException {
	        if (repoType.equalsIgnoreCase("git")) {
	        	return GIT_IMPL_CLASS;
	        } else if (repoType.equalsIgnoreCase("svn")) {
	        	return SVN_IMPL_CLASS;
	        }
			return null;
	    }
	
	private static Object constructClass(String className) throws PhrescoException {
        try {
            @SuppressWarnings("rawtypes")
            Class clazz = Class.forName(className);
            return clazz.newInstance();
        } catch (ClassNotFoundException e) {
            throw new PhrescoException(e);
        } catch (InstantiationException e) {
            throw new PhrescoException(e);
        } catch (IllegalAccessException e) {
            throw new PhrescoException(e);
        }
    }
}
