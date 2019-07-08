package library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.testng.Assert;

import enums.RequestType;

public class Common extends MyBaseTest
{
	//public MyBaseTest myBaseTest = new MyBaseTest();
	//public final String facilityFullURL = MyBaseTest.prop.getProperty("facilityServiceURL") + "/facility";

	public synchronized String randomTextDigit(int length) throws Throwable
	{
		String strrandomDigit = "";
		try
		{
			Logging.log.info("--------------- Started :: randomTextDigit ---------------");
			Random random = new Random((new Date()).getTime());

			char[] values = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

			for (int i = 0; i < length; i++)
			{
				int idx = random.nextInt(values.length);
				strrandomDigit += values[idx];
			}

			Logging.log.info("---------------- Ending - randomTextDigit ----------------");
			return strrandomDigit;

		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
	}

	public synchronized String randomTextGenerator(int length) throws Throwable
	{
		String strrandomTest = "";
		try
		{
			Logging.log.info("--------------- Started :: randomTextGenerator ---------------");

			Random ran = new Random();
			int top = length;
			char data = ' ';
			for (int i = 0; i < top; i++)
			{
				data = (char) (ran.nextInt(25) + 97);
				strrandomTest = data + strrandomTest;
			}
			Logging.log.info("---------------- Ending - randomTextGenerator ----------------");

			return strrandomTest;

		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}

	}

	public synchronized List<Integer> randomListGenerateBetweenGivenRange(int rangeMax, int count) throws Throwable
	{
		List<Integer> lst_int = new ArrayList<>();

		try
		{
			Logging.log.info("--------------- Started :: randomListGenerateBetweenGivenRange ---------------");

			int countLocal;
			int tmp;
			if (rangeMax < count)
			{
				countLocal = rangeMax;
			}
			else
			{
				countLocal = count;
			}

			Random random = new Random();
			for (int i = 0; i < countLocal;)
			{
				//tmp = random.nextInt((rangeMax) + 1);
				tmp = random.nextInt((rangeMax));
				if (!lst_int.contains(tmp))
				{
					lst_int.add(tmp);
					i++;
				}
			}
			Logging.log.info("---------------- Ending - randomListGenerateBetweenGivenRange ----------------");

			return lst_int;

		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
	}

	public synchronized String getFileData(String partialPath) throws Throwable
	{
		String st = "";
		StringBuffer finalValue = new StringBuffer("");
		try
		{
			File file = new File("src/main/resources/properties/psiData/" + partialPath + ".txt");

			BufferedReader br = new BufferedReader(new FileReader(file));

			while ((st = br.readLine()) != null)
			{
				finalValue.append(st);
			}
			br.close();
		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
		return finalValue.toString();
	}

	public synchronized int createEncoderForTrueCode(String testMethodName, String user, String testCaseId) throws Throwable
	{

		try
		{
			Utils utils = new Utils(RequestType.POST, MyBaseTest.facilityServiceURL + "/facilityEncoder", this.getUserTypeByEnumID("FACILITY1_ADMIN"));
			String strJson = utils.xlops.getCellValue("testData.xlsx", "TestFacilityEncoder", "CheckAPIwithValidValuesforallParametersforTrucode", "requestPayload", "test_data");
			String text = utils.common.randomTextGenerator(5);
			strJson = strJson.replaceAll("\\bstrEncGrantType\\b", text).replaceAll("\\bstrEncClientSecret\\b", text).replaceAll("\\bstrEncCustomerId\\b", text).replaceAll("\\bstrEncUrlMethod\\b", text).replaceAll("\\bstrEncEncoderName\\b", text).replaceAll("\\bstrEncUrlToken\\b", text)
					.replaceAll("\\bstrEncClientId\\b", text).replaceAll("\\bstrEncHost\\b", text);
			JSONObject requestPayloadJSONObj = new JSONObject();
			requestPayloadJSONObj.put("encoderMasterId", Integer.parseInt("1"));
			InputParametersRequest data = new InputParametersRequest(strJson);
			utils.httpRequest.setPayLoad(data.getJsonForRequest().toString());
			utils.httpRequest.buildAndExecute();
			Logging.log.info("Response for " + testMethodName + ": " + utils.httpRequest.getResponseBody());
			Logging.log.info("Response Code: " + utils.httpRequest.getStatusCode());
			Assert.assertEquals(utils.httpRequest.getStatusCode(), "201");
			JSONObject actualJSON = new JSONObject(utils.httpRequest.getResponseBody());
			int id = actualJSON.getInt("id");
			return id;
		}
		catch (Exception e)
		{
			Logging.log.info("Error in TestMethod " + testMethodName + " " + this.getStaktrace(e));
			Assert.fail();
			int id = 0;
			return id;
		}
	}

	//This method will create FacilityEncoder with given parameter
	public synchronized int createEncoder(String testMethodName, String user, String testCaseId, int encoderTypeId) throws Throwable
	{

		try
		{
			//CheckAPIwithValidValuesforallParametersfor3M - For 3M
			//CheckAPIwithValidValuesforallParametersforTrucode - For Truecode
			// "encoderMasterId" - This is common name
			String strJson;
			Utils utils = new Utils(RequestType.POST, MyBaseTest.facilityServiceURL + "/facilityEncoder", this.getUserTypeByEnumID("FACILITY1_ADMIN"));
			if (encoderTypeId == 1)
			{
				strJson = utils.xlops.getCellValue("testData.xlsx", "TestFacilityEncoder", "CheckAPIwithValidValuesforallParametersforTrucode", "requestPayload", "test_data");
				JSONObject requestPayloadJSONObj = new JSONObject(strJson);
				requestPayloadJSONObj.put("encoderMasterId", encoderTypeId);
				//System.out.println("HERE IS THE INPUT JSON:***************************" + requestPayloadJSONObj.toString());
			}
			else
			{
				strJson = utils.xlops.getCellValue("testData.xlsx", "TestFacilityEncoder", "CheckAPIwithValidValuesforallParametersfor3M", "requestPayload", "test_data");
				JSONObject requestPayloadJSONObj = new JSONObject(strJson);
				requestPayloadJSONObj.put("encoderMasterId", encoderTypeId);
				//System.out.println("HERE IS THE INPUT JSON:***************************" + requestPayloadJSONObj.toString());
			}
			InputParametersRequest data = new InputParametersRequest(strJson);
			utils.httpRequest.setPayLoad(data.getJsonForRequest().toString());
			utils.httpRequest.buildAndExecute();
			Logging.log.info("Response for " + testMethodName + ": " + utils.httpRequest.getResponseBody());
			Logging.log.info("Response Code: " + utils.httpRequest.getStatusCode());
			Assert.assertEquals(utils.httpRequest.getStatusCode(), "201");
			JSONObject actualJSON = new JSONObject(utils.httpRequest.getResponseBody());
			int facilityEncoderId = actualJSON.getInt("id");
			return facilityEncoderId;
		}
		catch (Exception e)
		{
			Logging.log.info("Error in TestMethod " + testMethodName + " " + this.getStaktrace(e));
			Assert.fail();
			int eId = 0;
			return eId;
		}
	}

	private class InputParametersRequest
	{
		private String		strJson;
		private JSONObject	jsonObj;

		public InputParametersRequest(String strJson)
		{
			this.strJson = strJson;
			jsonObj = new JSONObject(this.strJson.trim());

		}

		public synchronized JSONObject getJsonForRequest()
		{

			return jsonObj;
		}
	}

}
