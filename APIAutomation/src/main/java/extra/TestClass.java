package extra;

import org.testng.annotations.Test;

import library.Common;

public class TestClass
{

	@Test
	public void Test() throws Throwable
	{
		try
		{

			Common com = new Common();
			//System.out.println(com.randomTextDigit(12));

			/*
			
			//MyHttpRequest req = new MyHttpRequest(RequestType.GET, "https://", UserType.HRMC_CODERSUPERVISOR);
			
			
						AdminPSI_lib aa = new AdminPSI_lib();
						for (int i = 61; i < 152; i++)
						{
							aa.deleteVersion(i, "RUMC_CODER");
						}
			XLOps ops = new XLOps();
			try
			{
			
				String a = "dd";
				String c = "aa" + "\"" + a + "\"" + "ss";
				System.out.println(c);
			
				FileWriter fw = new FileWriter("D:\\myfile.txt", false);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw);
			
				String fileName = "D:\\test.txt";
			
				// This will reference one line at a time
				String line = null;
			
				// FileReader reads text files in the default encoding.
				FileReader fileReader = new FileReader(fileName);
			
				// Always wrap FileReader in BufferedReader.
				BufferedReader bufferedReader = new BufferedReader(fileReader);
			
				while ((line = bufferedReader.readLine()) != null)
				{
					//	out.println(line);
						}
					
						// Always close files.
						bufferedReader.close();
					
						for (int i = 1; i <= 46; i++)
						{
					
					String input = "@Test(dataProvider = \"dataProvider\")\r\n" + "	public void strMethodName(String testMethodName, String user) throws Throwable\r\n" + "	{\r\n" + "		try\r\n" + "		{\r\n"
							+ "			Utils utils = new Utils(RequestType.POST, fullURL, getUserType(user));\r\n" + "\r\n" + "			String strJson = utils.xlops.getCellValue(\"testData.xlsx\", this.getClass().getSimpleName(), testMethodName, \"1\", \"test_data\");\r\n"
							+ "			InputParametersPSIRequest data = new InputParametersPSIRequest(strJson);\r\n" + "			utils.httpRequest.setPayLoad(data.getJsonForPSIRequest().toString()).setContentType(\"application/json\");\r\n" + "			utils.httpRequest.buildAndExecute();\r\n"
							+ "			Logging.log.info(\"Response for \" + testMethodName + \": \" + utils.httpRequest.getResponseBody());\r\n" + "			Logging.log.info(\"Response Code: \" + utils.httpRequest.getStatusCode());\r\n" + "\r\n"
							+ "			Assert.assertEquals(utils.httpRequest.getStatusCode(), \"200\");\r\n" + "			Assert.assertEquals(new JSONObject(utils.httpRequest.getResponseBody()).get(\"message\"), \"Success\");\r\n" + "\r\n"
							+ "			String actualJson = utils.httpRequest.getResponseBody();\r\n" + "			String expectedJson = utils.xlops.getCellValue(\"testData.xlsx\", this.getClass().getSimpleName(), testMethodName, \"2\", \"test_data\");\r\n" + "\r\n"
							+ "			JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.LENIENT);\r\n" + "\r\n" + "		}\r\n" + "		catch (Exception e)\r\n" + "		{\r\n"
							+ "			Logging.log.info(\"Error in TestMethod \" + testMethodName + \" \" + MyBaseTest.common.getStaktrace(e));\r\n" + "			Assert.fail();\r\n" + "		}\r\n" + "\r\n" + "	}\r\n" + "";
					input = input.replaceAll("strMethodName", line);
					out.println(input);
			
				}
				out.close();
				bufferedReader.close();
			}
			catch (IOException e)
			{
				//exception handling left as an exercise for the reader
			}
			
			*/}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Throwable
	{

		Common com = new Common();
		//System.out.println(com.randomTextDigit(12));

		/*String text = "DG1|1|I10|I50.21|Acute systolic (congestive) heart failure||A|^MCC|||||||||||||||||||Y";
		String patternString = "(DG1)(\\|)(1)(\\|)(I10)(\\|)(I50.21)(\\|)([A-Z a-z \\( \\)])*((\\|)[A-Z a-z 0-9 . ^]*){2,}(A)(\\|)(CCMCC)((\\|)[A-Z a-z 0-9 . ^]*){19,}(Y)";
		patternString = patternString.replaceAll("\\bCCMCC\\b", "\\\\^MCC");
		System.out.println(patternString);
		Pattern pattern = Pattern.compile(patternString);
		
		Matcher matcher = pattern.matcher(text);
		boolean matches = matcher.matches();
		System.out.println(matches);
		
		JSONObject jsonobj = new JSONObject();
		
		JSONArray jsonarray = new JSONArray();
		JSONObject minijson = new JSONObject();
		minijson.put("denominatorExclusionReason", "denominatorExclusionReason").put("denominatorInclusionReason", "denominatorInclusionReason").put("numeratorExclusionReason", "numeratorExclusionReason").put("numeratorInclusionReason", "numeratorInclusionReason").put("description", "description");
		minijson.put("denominatorExclusion", true).put("denominatorInclusion", true).put("numeratorExclusion", true).put("numeratorInclusion", true);
		minijson.put("measureCategory", "PSI2");
		jsonarray.put(minijson);
		
		jsonobj.put("measureDetailResponseList", jsonarray);
		jsonobj.put("patientId", "123");
		jsonobj.put("version", "1.0");
		
		System.out.println(jsonobj.toString());*/
		try
		{

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
