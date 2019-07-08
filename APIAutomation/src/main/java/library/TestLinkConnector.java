package library;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.model.Build;
import br.eti.kinoshita.testlinkjavaapi.model.ReportTCResultResponse;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.model.TestProject;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;

public class TestLinkConnector
{

	private static final String	DEVKEY			= MyBaseTest.prop.getProperty("testLinkAPIAccessKey");
	private static final String	URL				= "http://" + MyBaseTest.prop.getProperty("testLinkServerIp") + "/testlink/lib/api/xmlrpc/v1/xmlrpc.php";
	//private TestLinkConnector	testLinkConnector	= new TestLinkConnector();

	private static final String	testProjectName	= MyBaseTest.prop.getProperty("testLinkTestProjectName");
	private String				testSuite		= null;
	private String				testCaseID		= null;
	private static String		buildName;
	private String				notes			= "";
	private ExecutionStatus		result			= null;
	private static final String	testPlanName	= MyBaseTest.prop.getProperty("testLinkTestPlanName");
	//private static TestCase[]	tcArray;
	private static TestLinkAPI	tlapi;
	private static Build		testLinkBuild;
	private static TestPlan		testPlan;
	private static TestProject	testProject;

	static
	{
		try
		{
			Calendar calendar = Calendar.getInstance();
			TimeZone toTimeZone = TimeZone.getTimeZone("IST");
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			formatter.setTimeZone(toTimeZone);
			buildName = "IST_" + formatter.format(calendar.getTime());
			tlapi = new TestLinkAPI(new java.net.URL(URL), DEVKEY);
			testProject = tlapi.getTestProjectByName(testProjectName);
			testPlan = tlapi.getTestPlanByName(testPlanName, testProject.getName());
			TestSuite ts;
			//tcArray = tlapi.getTestCasesForTestPlan(tl.getId(), null, null, null, null, null, null, null, null, null, null);
			testLinkBuild = tlapi.createBuild(tlapi.getTestPlanByName(testPlanName, testProjectName).getId(), buildName, "");
		}
		catch (br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException | MalformedURLException e)
		{
			// TODO Auto-generated catch block
			Logging.log.info("catched br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException | MalformedURLException e");
			e.printStackTrace();
		}

	}

	public TestLinkConnector(String testSuiteName, String testCaseID, String notes, ExecutionStatus result)
	{
		this.testSuite = testSuiteName;
		this.testCaseID = testCaseID;
		this.notes = notes;
		this.result = result;
	}

	public void setTestSuite(String testSuiteName)
	{
		this.testSuite = testSuiteName;
	}

	public void setTestCaseID(String testCaseID)
	{
		this.testCaseID = testCaseID;
	}

	public void setTestCaseResult(ExecutionStatus result)
	{
		this.result = result;
	}

	public void reportResult()
	{
		/*TestLinkAPI tlapi = new TestLinkAPI(new java.net.URL(URL), DEVKEY);
		for (int i = 0; i < tcArray.length; i++)
		{
			String currentTCExternalId = tcArray[i].getFullExternalId();
			System.out.println("");
		}*/
		ReportTCResultResponse res = tlapi.reportTCResult(tlapi.getTestCaseByExternalId(this.testCaseID, null).getId(), null, testPlan.getId(), this.result, null, this.testLinkBuild.getId(), null, this.notes, null, null, null, null, null, null);
		System.out.println("");
		//tlapi.reportTCResult(testCaseId, testCaseExternalId, testPlanId, status, steps, buildId, buildName, notes, guess, bugId, platformId, platformName, customFields, overwrite)
		//tlapi.reportTCResult(tcArray, testCaseExternalId, testPlanId, status, steps, buildId, buildName, notes, guess, bugId, platformId, platformName, customFields, overwrite)
		/*int testCaseId = tlapi.getTestCaseIDByName(this.testCaseID, this.testSuite, this.testProject, null);
		//tlapi.reportTCResult(tlapi.getTestCaseIDByName(this.testCaseID, this.testSuite, this.testProject, this.notes), testCaseExternalId, testPlanId, status, steps, buildId, buildName, notes, guess, bugId, platformId, platformName, customFields, overwrite)
		TestLinkAPIClient api = new TestLinkAPIClient(DEVKEY, URL);
		api.reportTestCaseResult(this.testProject, this.testPlanName, this.testCaseID, this.buildName, this.notes, this.result);*/
	}

}
