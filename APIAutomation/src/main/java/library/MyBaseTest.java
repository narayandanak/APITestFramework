package library;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.DataProvider;

import enums.UserType;

public class MyBaseTest
{
	public static String NLPServiceURL = null;
	public static String							tokenString					= "";
	public static List<MyTokenGenerator>			lst_tokens					= new ArrayList<>();
	public static HashMap<String, MyTokenGenerator>	hashMapOfUsersAndUserTokens	= new HashMap<>();
	public static Properties						prop;
	//public static Common							common						= new Common();
	//public static String							strAppedixECodes;
	public static String							algoServiceURL				= null;
	public static String							adminServiceURL				= null;
	public static String							mtServiceURL				= null;
	public static String							ctServiceURL				= null;
	public static String							dpServiceURL				= null;
	public XLOps									xlops						= new XLOps();
	public static Map<MyHttpRequest, String>		deleteHttpRequestMap		= new LinkedHashMap<>();
	public static String							facilityServiceURL = null;
	public static String							metaDataServiceURL;
	public static String							loginURL;
	public static String							groupingServiceURL			= null;
	public static String catAPIBaseURL = null;
	public static HashMap<String, Users>	hashMapOfUsers	= new HashMap<>();
	
	static
	{
		try
		{
			setTestProperties();

		}
		catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MyBaseTest()
	{
		facilityServiceURL = prop.getProperty("facilityServiceURL");
		metaDataServiceURL = prop.getProperty("metaDataServiceURL");
		loginURL = prop.getProperty("loginURL");
		groupingServiceURL = prop.getProperty("groupingServiceURL");
		catAPIBaseURL = prop.getProperty("catAPIBaseURL");
		//System.out.println("prop object set");
	}

	public static void setTestProperties() throws Throwable
	{
		try
		{
			File propFile = new File("src/main/resources/properties/" + "main.properties");
			FileInputStream is = new FileInputStream(propFile);
			prop = new Properties();
			prop.load(is);

		}
		catch (Exception e)
		{
			throw (new Throwable(e));

		}
	}

	@DataProvider(name = "dataProvider")
	protected synchronized Object[][] dataProviderForSRTest(Method method) throws Throwable
	{
		int numberOFParameters = 3;
		Object[][] returnObjArray = null;
		try
		{
			//String className = method.getDeclaringClass().getTypeName().split("com\\.ezdi\\.testScripts\\.")[1];
			String className = method.getDeclaringClass().getSimpleName();

			String arrayOfUsers[] = XLOps.getElementsTOExecute(className, "tenant");
			String arrayOfTestNames[] = XLOps.getElementsTOExecute(className, "test_name");
			String arrayTcIds[] = XLOps.getElementsTOExecute(className, "testcase_id");

			for (int i = 0; i < arrayOfTestNames.length; i++)
			{
				if ((className + "." + method.getName().toLowerCase().trim()).equals(className + "." + arrayOfTestNames[i].toLowerCase().trim()))
				{
					String arrayOfUsersSplit[] = arrayOfUsers[i].toLowerCase().trim().split(",");
					returnObjArray = new Object[arrayOfUsersSplit.length][numberOFParameters];
					//int counter = 0;
					for (int j = 0; j < arrayOfUsersSplit.length; j++)
					{
						Object singleUser = new String(arrayOfUsersSplit[j].toLowerCase().trim());
						returnObjArray[j][0] = method.getName();
						returnObjArray[j][1] = singleUser;
						returnObjArray[j][2] = arrayTcIds[i];
						//	counter++;
						//return returnObjArray;
					}
					//return returnObjArray;
				}

			}
			//return returnObjArray;
		}
		catch (Exception e)
		{
			throw (new Throwable(e));

		}
		return returnObjArray;

	}

	public synchronized UserType getUserTypeByEnumID(String userEnum) throws Throwable
	{
		try
		{

			UserType t = UserType.valueOf(userEnum.toUpperCase());
			return t;

		}
		catch (Exception e)
		{
			throw (new Throwable(e));

		}
	}

	protected synchronized List<NameValuePair> getNameValuePairList(String values) throws Throwable
	{
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		try
		{

			String[] listString = values.split("\\|\\|");

			for (int i = 0; i < listString.length; i++)
			{
				urlParameters.add(new BasicNameValuePair(listString[i].split("\\$~\\$")[0].trim(), listString[i].split("\\$~\\$")[1].trim()));
			}
		}
		catch (Exception e)
		{
			throw (new Throwable(e));

		}

		return urlParameters;
	}

	public synchronized String getCSRF(UserType userType)
	{
		String csrf = "";
		for (String userType1 : hashMapOfUsersAndUserTokens.keySet())
		{
			if (userType != null && userType1.trim().equalsIgnoreCase(userType.toString()))
			{
				MyTokenGenerator object = hashMapOfUsersAndUserTokens.get(userType1);
				csrf = object.getAuthTokenString();
				break;
			}
		}
		return csrf;
	}

	public synchronized String getAuthToken(UserType userType)
	{
		String authToken = "";
		for (String userType1 : hashMapOfUsersAndUserTokens.keySet())
		{
			if (userType != null && userType1.trim().equalsIgnoreCase(userType.toString()))
			{
				MyTokenGenerator object = hashMapOfUsersAndUserTokens.get(userType1);
				authToken = object.getAuthToken();
				break;
			}
		}
		return authToken;
	}

	public static synchronized String getStaktrace(Throwable e)
	{
		String result = "";
		try
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			result = sw.toString();
			Logging.log.error(result);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
			//Logging.log.error(getStaktrace(e1));
		}

		return result;
	}
}