package testScripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import library.Logging;
import library.MyBaseTest;
import library.Utils;

public class MyTests extends MyBaseTest{
	private String fullURL = MyBaseTest.NLPServiceURL + "/encounter/";
	
	MyTests(){
		this.xlops.setXLParamFileAndSheet(this.getClass().getSimpleName(), "");
	}
	
	@Test(enabled = true, dataProvider = "dataProvider")
	public void myTest1(String testMethodName, String user, String testId) {
		try {
			Utils utils = new Utils(null, testId, testId, null);
		} catch (Throwable e) {
			Logging.log.info(testMethodName + "failed" + "\n" + getStaktrace(e));
			Assert.fail();
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	@Test(enabled = true, dataProvider = "dataProvider")
	public void myTest2(String testMethodName, String user, String testId) {
		
	}
}
