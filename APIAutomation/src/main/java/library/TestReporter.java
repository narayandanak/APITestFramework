package library;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.apache.http.message.BasicHeader;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import enums.RequestType;

public class TestReporter extends TestListenerAdapter
{
	PrintWriter	resultWriter;
	static int	serialNumber	= 0;

	@Override
	public void onStart(ITestContext context)
	{
		super.onStart(context);
		try
		{

			this.makeDirIfNotExists(System.getProperty("user.dir") + "//results");
			resultWriter = new PrintWriter(new File(System.getProperty("user.dir") + "//results//results.csv"), "UTF-8");
			resultWriter.println("#,ClassName,TestMethodName,Users,TimeTaken,FailureReason,Result,corelId,TestCaseId,BugId");
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		//System.out.println("On start");
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result)
	{

		super.onTestSuccess(result);
		try
		{
			String resultLine = getResultLine(result, "PASS");
			resultWriter.println(resultLine);
			if (MyBaseTest.prop.getProperty("testLinkConnect").equals("true"))
			{
				TestLinkConnector testLinkConnector = new TestLinkConnector(((String) result.getParameters()[2]).split(":")[0], ((String) result.getParameters()[2]).split(":")[1], resultLine, ExecutionStatus.PASSED);
				testLinkConnector.reportResult();
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println("On Success");

	}

	private String createJIRABug(String testCaseId) throws Throwable
	{

		MyHttpRequest m = new MyHttpRequest(RequestType.POST, MyBaseTest.prop.getProperty("JIRARESTURL"), "");
		String userpass = MyBaseTest.prop.getProperty("JIRAUserName") + ":" + MyBaseTest.prop.getProperty("JIRAPassword");
		String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
		m.replaceOrAddHeader(new BasicHeader("Authorization", basicAuth));

		m.setPayLoad("{ \"fields\":  { \"project\": { \"key\": \"" + MyBaseTest.prop.getProperty("JIRAProjectKey") + "\" }, \"labels\":[\"Automation\"], \"summary\": \"Automation Failure\", \"description\": \"Test Case ID: " + testCaseId + "\", \"issuetype\": { \"id\": \"1\" } } }");
		m.buildAndExecute();
		String bugId = new JSONObject(m.getResponseBody()).get("key").toString();
		return bugId;

	}

	@Override
	public synchronized void onTestFailure(ITestResult result)
	{
		super.onTestFailure(result);
		try
		{
			String resultLine = getResultLine(result, "FAIL");
			if (MyBaseTest.prop.getProperty("JIRAConnect").toLowerCase().equals("true"))
			{
				String bugId = this.createJIRABug((String) result.getParameters()[2]);
				resultLine = resultLine + "," + bugId;
				resultWriter.println(resultLine + "," + bugId);
			}
			else
			{
				resultWriter.println(resultLine);
			}

			if (MyBaseTest.prop.getProperty("testLinkConnect").toLowerCase().equals("true"))
			{
				TestLinkConnector testLinkConnector = new TestLinkConnector(((String) result.getParameters()[2]).split(":")[0], ((String) result.getParameters()[2]).split(":")[1], resultLine, ExecutionStatus.FAILED);

				testLinkConnector.reportResult();
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println("On Failure");
	}

	@Override
	public void onTestSkipped(ITestResult result)
	{
		super.onTestSkipped(result);
		try
		{
			resultWriter.println(getResultLine(result, "SKIP"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onFinish(ITestContext testContext)
	{
		super.onFinish(testContext);
		try
		{
			resultWriter.close();

			if (!MyBaseTest.deleteHttpRequestMap.isEmpty() && MyBaseTest.deleteHttpRequestMap != null)
			{
				Logging.log.info("************** Process of deleting Object Started **************");
				List<MyHttpRequest> hashMapKeys = new ArrayList<MyHttpRequest>(MyBaseTest.deleteHttpRequestMap.keySet());

				Collections.reverse(hashMapKeys);

				for (MyHttpRequest httpRequest : hashMapKeys)
				{

					try
					{
						String url = httpRequest.getUrl();

						String idIdentifier = MyBaseTest.deleteHttpRequestMap.get(httpRequest);

						JSONObject json = new JSONObject(httpRequest.getResponseBody());
						String id = json.get(idIdentifier).toString();

						MyHttpRequest requestForDelete = new MyHttpRequest(RequestType.DELETE, url + "/" + id, httpRequest.getUserName());
						requestForDelete.buildAndExecute();
						//System.out.println(requestForDelete.getStatusCode());
						//System.out.println(requestForDelete.getResponseBody());
					}
					catch (Throwable e)
					{
						System.out.println("On finish Inner catch block Error:");
						/*new Common().getStaktrace(e);*/
					}

				}
				Logging.log.info("************** Process of deleting Object Ended **************");

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		//System.out.println("On Finish");
	}

	private String getResultLine(ITestResult result, String isPass)
	{
		String returnResult = "";
		try
		{
			String className = result.getTestClass().getRealClass().getSimpleName();
			serialNumber++;
			//Exception e = new Exception(result.getThrowable());
			Throwable th = result.getThrowable();
			String corelId = (String) result.getTestContext().getAttribute(result.getParameters()[0].toString() + result.getParameters()[1].toString());
			//String tcID = result.getMethod().getDescription(); //Get the description of test which we defined in @Test annotation
			String tcID = (String) result.getParameters()[2];
			
			if (th != null)
			{
				//returnResult = serialNumber + "," + className + "," + result.getMethod().getMethodName() + "," + getMethodParameters(result) + "," + ((result.getEndMillis() - result.getStartMillis()) / 1000 + "," + (new Exception(e)).getMessage() + isPass);

				returnResult = serialNumber + "," + className + "," + result.getMethod().getMethodName() + "," + getMethodParameters(result) + ","
						+ ((result.getEndMillis() - result.getStartMillis()) / 1000 + "," + "\"" + th.getMessage().replaceAll("\n", "").replaceAll("\r", "") + "\"" + "," + isPass + "," + corelId + "," + tcID);
				result.setThrowable(null);
			}
			else
			{
				returnResult = serialNumber + "," + className + "," + result.getMethod().getMethodName() + "," + getMethodParameters(result) + "," + ((result.getEndMillis() - result.getStartMillis()) / 1000 + "," + " " + "," + isPass + "," + corelId + "," + tcID);
			}

		}
		catch (Exception e)
		{
			//System.out.println("In catch block");
			//e.printStackTrace();
		}
		return returnResult;
	}

	private String getMethodParameters(ITestResult result)
	{
		String returnMethodParameters = "";
		try
		{
			Object[] methodParams = result.getParameters();
			//int size = methodParams.length;
			int i = 0;
			for (Object methodparam : methodParams)
			{
				if (i == 1)
				{
					returnMethodParameters = returnMethodParameters + (String) methodparam;
				}
				i++;
				/*if (i != 1)
				{
					i++;
				}
				else
				{
					returnMethodParameters = returnMethodParameters + (String) methodparam;
					if ((size - 1) != i)
					{
						returnMethodParameters = returnMethodParameters + " || ";
					}
				
					i++;
				}*/
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnMethodParameters;
	}

	private void makeDirIfNotExists(String fullyQualifiedPath)
	{
		File theDir = new File(fullyQualifiedPath);
		// if the directory does not exist, create it
		if (!theDir.exists())
		{
			boolean result = false;

			try
			{
				theDir.mkdir();
				result = true;
			}
			catch (Exception e)
			{
				/*System.out.println("Could not create dir: " + fullyQualifiedPath);
				System.out.println("System terminating abnormally in Reporting.TestReporter.makeDirIfNotExists");
				*/System.exit(1);
			}
			if (result)
			{
				//System.out.println("DIR created");
			}
		}
	}

	public static String getTestMethodName(ITestResult result)
	{
		try
		{
			return result.getMethod().getConstructorOrMethod().getName();
		}
		catch (Exception e)
		{
			Logging.log.info(e);
			// e.printStackTrace();
		}
		return result.getMethod().getConstructorOrMethod().getName();
	}

}