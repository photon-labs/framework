

package com.photon.helios.preconditions;

import java.io.IOException;

import junit.framework.TestCase;

import org.testng.annotations.Test;


@SuppressWarnings("unused")
public class CleanUp extends TestCase{

	String methodName;

	@Test
	public void testPhpHelloWorldProjectDatabase() throws InterruptedException, IOException, Exception{


		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("methodName = " + methodName);
		DeleteDbsql dbsql = new DeleteDbsql(methodName);

	}

	@Test
	public void testPhpBlogProjectDatabase() throws InterruptedException, IOException, Exception{


		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("methodName = " + methodName);
		DeleteDbsql dbsql = new DeleteDbsql(methodName);
	}
	
	@Test
	public void testDrupal6HelloWorldProjectDatabase() throws InterruptedException, IOException, Exception{


		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("methodName = " + methodName);
		DeleteDbsql dbsql = new DeleteDbsql(methodName);
	}

	@Test
	public void testDrupal7HelloWorldProjectDatabase() throws InterruptedException, IOException, Exception{


		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("methodName = " + methodName);
		DeleteDbsql dbsql = new DeleteDbsql(methodName);
	}		


	@Test
	public void testDrupal7EshopProjectDatabase() throws InterruptedException, IOException, Exception{


		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("methodName = " + methodName);
		DeleteDbsql dbsql = new DeleteDbsql(methodName);
	}
	@Test
	public void testWordPressHelloWorldProjectDatabase() throws InterruptedException, IOException, Exception{


		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("methodName = " + methodName);
		DeleteDbsql dbsql = new DeleteDbsql(methodName);
	}

	@Test
	public void testJavaWebServiceHelloWorldProjectDatabase() throws InterruptedException, IOException, Exception{


		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("methodName = " + methodName);
		DeleteDbsql dbsql = new DeleteDbsql(methodName);
	}

	@Test
	public void testJavaWebServiceEshopProjectDatabase() throws InterruptedException, IOException, Exception{


		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("methodName = " + methodName);
		DeleteDbsql dbsql = new DeleteDbsql(methodName);
	}	


	@Test
	public void testNodeJsHelloWorldProjectDatabase() throws InterruptedException, IOException, Exception{


		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("methodName = " + methodName);
		DeleteDbsql dbsql = new DeleteDbsql(methodName);
	}

	@Test
	public void testNodeJsEshopProjectDatabase() throws InterruptedException, IOException, Exception{


		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("methodName = " + methodName);
		DeleteDbsql dbsql = new DeleteDbsql(methodName);
	}		

	
    
}





