package extra;

import org.testng.Assert;
import org.testng.annotations.Test;

import enums.RequestType;
import library.Logging;
import library.MyBaseTest;
import library.Utils;

public class APITestingPOC extends MyBaseTest
{

	public APITestingPOC() throws Throwable
	{
	}

	@Test(enabled = true, dataProvider = "dataProvider")
	public void getDRGTrendDetails(String testMethodName, String user)
	{
		try
		{
			String partialURL = "https://qaezcac.ezdicloud.com/ezCACWeb/worklist/drgTrend";
			String queryParams = "itemUniqueKey $~$ 21543-Coder";
			Utils utils = new Utils(RequestType.POST, partialURL, getUserTypeByEnumID(user));
			utils.httpRequest.setFormData(this.getNameValuePairList(queryParams));
			utils.httpRequest.buildAndExecute();
			Logging.log.info("Response for " + user + " :" + utils.httpRequest.getResponseBody());
			Assert.assertEquals(utils.httpRequest.getStatusCode(), "200");

		}
		catch (Throwable e)
		{
			Logging.log.info("Error in TestMethod" + testMethodName + " " + this.getStaktrace(e));
			Assert.fail();
		}

	}

	@Test(enabled = true, dataProvider = "dataProvider")
	public void getNavigationDetails(String testMethodName, String user)
	{
		try
		{
			String partialURL = "https://qaezcac.ezdicloud.com/ezCACWeb/getNavigationDetails";
			Utils utils = new Utils(RequestType.GET, partialURL, getUserTypeByEnumID(user));
			utils.httpRequest.setContentType("application/json");
			utils.httpRequest.buildAndExecute();
			Logging.log.info("Response for " + user + " :" + utils.httpRequest.getResponseBody());

			Assert.assertEquals(utils.httpRequest.getStatusCode(), "200");
		}
		catch (Throwable e)
		{
			Logging.log.info("Error in TestMethod" + testMethodName + " " + this.getStaktrace(e));
			Assert.fail();
		}
	}

}
