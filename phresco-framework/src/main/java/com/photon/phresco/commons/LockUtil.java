package com.photon.phresco.commons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.model.LockDetail;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class LockUtil implements Constants {
	public static LockDetail getLockDetail(String appid, String actionType, String displayName, String uniqueKey) throws PhrescoException {
		try {
			LockDetail lockDetail = new LockDetail(appid, actionType, displayName, uniqueKey);
			return lockDetail;
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	 public static void generateLock(List<LockDetail> lockDetails, boolean toGenerate) throws PhrescoException {
	    	BufferedWriter out = null;
			FileWriter fstream = null;
			try {
				List<LockDetail> newLockDetails = new ArrayList<LockDetail>();
				newLockDetails.addAll(lockDetails);
				List<LockDetail> availableLockDetails = getLockDetails();
				if (toGenerate && CollectionUtils.isNotEmpty(availableLockDetails)) {
					newLockDetails.addAll(availableLockDetails);
				}
				
				Gson gson = new Gson();
				String infoJSON = gson.toJson(newLockDetails);
				fstream = new FileWriter(getLockFilePath());
				out = new BufferedWriter(fstream);
				out.write(infoJSON);
			} catch (IOException e) {
				throw new PhrescoException(e);
			} finally {
				try {
					if (out != null) {
						out.close();
					}
					if (fstream != null) {
						fstream.close();
					}
				} catch (IOException e) {
					throw new PhrescoException(e);
				}
			}
	    }
	
	/**
	 * To remove the lock once the initiated operation has been completed
	 * @throws PhrescoException
	 */
	public  static void removeLock(String uniqueKey) throws PhrescoException {
		try {
			List<LockDetail> lockDetails = getLockDetails();
			if (CollectionUtils.isNotEmpty(lockDetails)) {
				List<LockDetail> availableLockDetails = new ArrayList<LockDetail>();
				for (LockDetail lockDetail : lockDetails) {
					if (!lockDetail.getUniqueKey().equalsIgnoreCase(uniqueKey)) {
						availableLockDetails.add(lockDetail);
					} 
				}
				generateLock(availableLockDetails, false);
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}
	
	 public static List<LockDetail> getLockDetails() throws PhrescoException {
			FileReader reader = null;
			try {
				File file = new File(getLockFilePath());
				if (file.exists()) {
					reader = new FileReader(file);
					Gson gson = new Gson();
					List<LockDetail> lockDetails = gson.fromJson(reader, new TypeToken<List<LockDetail>>(){}.getType());
					return lockDetails;
				}
			} catch (FileNotFoundException e) {
				throw new PhrescoException(e);
			} finally {
				Utility.closeStream(reader);
			}
			return null;
		}
	 
	    private static String getLockFilePath() {
	    	StringBuilder sb = new StringBuilder(Utility.getPhrescoHome())
			.append(File.separator)
			.append(PROJECTS_WORKSPACE)
			.append(File.separator)
	    	.append(FrameworkConstants.LOCK_FILE);
	    	return sb.toString();
	    }
}
