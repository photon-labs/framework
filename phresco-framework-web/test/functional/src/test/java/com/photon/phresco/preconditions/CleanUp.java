

package com.photon.phresco.preconditions;

import java.io.IOException;

import junit.framework.TestCase;

import org.testng.annotations.Test;

import com.photon.phresco.uiconstants.Drupal6Constants;
import com.photon.phresco.uiconstants.Drupal7Constants;
import com.photon.phresco.uiconstants.PhpConstants;
import com.photon.phresco.uiconstants.WordpressConstants;

@SuppressWarnings("unused")
public class CleanUp {
	private static PhpConstants phpConst = new PhpConstants();;
	private static Drupal6Constants drupal6Const = new Drupal6Constants();;
	private static Drupal7Constants drupal7Const = new Drupal7Constants();;
	private static WordpressConstants wordPressConst = new WordpressConstants();
	
	
	@Test
	public void testPhpArchetypeDatabase() throws InterruptedException, IOException, Exception{
		DeleteDbsql dbsql = new DeleteDbsql(phpConst.getConfigDbName());
	}

	@Test
	public void testDrupal6ArchetypeDatabase() throws InterruptedException, IOException, Exception{
		DeleteDbsql dbsql = new DeleteDbsql(drupal6Const.getConfigDbName());
	}

	@Test
	public void testDrupal7ArchetypeDatabase() throws InterruptedException, IOException, Exception{
		DeleteDbsql dbsql = new DeleteDbsql(drupal7Const.getConfigDbName());
	}
	
	@Test
	public void testWordpressArchetypeDatabase() throws InterruptedException, IOException, Exception{
		DeleteDbsql dbsql = new DeleteDbsql(wordPressConst.getConfigDbName());
	}
	


	
    
}





