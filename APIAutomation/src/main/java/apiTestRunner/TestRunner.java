package apiTestRunner;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import library.MyBaseTest;
import library.MyTokenGenerator;
import library.XLOps;

public abstract class TestRunner
{

	public static void run(String packageName) throws Throwable
	{
		try
		{

			if (System.getProperty("env") != null)
			{
				MyBaseTest.prop.setProperty("env", System.getProperty("env"));
			}

			if (System.getProperty("NLPServiceURL") != null)
			{
				MyBaseTest.prop.setProperty("NLPServiceURL", System.getProperty("NLPServiceURL"));
			}

			//MyTokenGenerator dummyObject = new MyTokenGenerator("", "");
			MyBaseTest baseTest = new MyBaseTest();
			XmlSuite suite = new XmlSuite();
			suite.setName("ezDIAPIAutomation");

			XmlTest test1 = new XmlTest(suite);
			test1.setName("");

			//-----------------------------------------------------------
			/*List<XmlClass> classes = new ArrayList<XmlClass>();
			String modules[] = null;
			
			modules = MyBaseTest.prop.getProperty("testClassesToExecute").split(",");
			for (int i = 0; i < modules.length; i++)
			{
				classes.add(new XmlClass("testScripts." + modules[i].trim()));
			}
			test1.setXmlClasses(classes);*/
			//---------------------------------------------------------------
			List<XmlClass> classes = new ArrayList<XmlClass>();
			String modules1[];
			modules1 = XLOps.getElementsTOExecute("modules", "module_name");
			for (int i = 0; i < modules1.length; i++)
			{
				classes.add(new XmlClass(packageName + "." + modules1[i].trim()));
			}
			test1.setXmlClasses(classes);

			String whatToRunINParallel = MyBaseTest.prop.getProperty("whatToRunINParallel");
			switch (whatToRunINParallel.toLowerCase().trim())
			{

				case "methods":
					suite.setParallel(ParallelMode.METHODS);
					//System.out.println("executing methods in parallel");
					break;
				case "classes":
					suite.setParallel(ParallelMode.CLASSES);
					//System.out.println("executing Classes in parallel");
					break;
				default:
					//System.out.println("No Parallel execution mode selected");
					break;
			}

			int numberOfThreads = Integer.parseInt(MyBaseTest.prop.getProperty("numberOfThreads"));
			suite.setThreadCount(numberOfThreads);
			suite.addListener("library.TestReporter");
			suite.addListener("library.SelectiveTestExecution");

			List<XmlSuite> suites = new ArrayList<XmlSuite>();
			suites.add(suite);
			//System.out.println(suite.toXml());
			TestNG tng = new TestNG();
			tng.setXmlSuites(suites);
			tng.run();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
