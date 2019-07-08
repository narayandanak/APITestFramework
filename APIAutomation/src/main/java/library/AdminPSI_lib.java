package library;

import org.json.JSONArray;
import org.json.JSONObject;

import enums.RequestType;

public class AdminPSI_lib extends MyBaseTest
{
	public int createVersion(String versionName, int qualityIndicatorId, String user) throws Throwable
	{
		int versionid = 0;
		try
		{
			String inputJson = "{\"name\": \"" + versionName + "\",\"qualityIndicatorId\":" + qualityIndicatorId + "}";
			Utils utils = new Utils(RequestType.POST, MyBaseTest.algoServiceURL + "/version", getUserTypeByEnumID(user));
			utils.httpRequest.setContentType("application/json");
			utils.httpRequest.setPayLoad(inputJson);
			utils.httpRequest.buildAndExecute();
			if (utils.httpRequest.getStatusCode().equalsIgnoreCase("200"))
			{
				versionid = new JSONObject(new JSONObject(utils.httpRequest.getResponseBody()).get("data").toString()).getInt(("id"));
			}
			else
			{
				Logging.log.error("Error in Version Creation");
			}

		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
		return versionid;
	}

	public void deleteVersion(int versionId, String user) throws Throwable
	{
		try
		{
			Utils utils = new Utils(RequestType.DELETE, MyBaseTest.algoServiceURL + "/version/" + versionId, getUserTypeByEnumID(user));

			utils.httpRequest.buildAndExecute();
		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
	}

	public void deleteVersions(int versionIds[], String user) throws Throwable
	{
		try
		{

			for (int i = 0; i < versionIds.length; i++)
			{
				deleteVersion(versionIds[i], user);
			}
		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
	}

	public int createCodeGroup(int versionId, String codeGroupName, String codeGroupDescription, String user) throws Throwable
	{
		JSONArray arrCodeGroup = null;
		int codeGroupId = 0;
		try
		{
			String inputJson = "[{\"name\": \"" + codeGroupName + "\",\"versionId\":" + versionId + ",\"description\":\"" + codeGroupDescription + "\"}]";
			Utils utils = new Utils(RequestType.POST, MyBaseTest.algoServiceURL + "/code-group", getUserTypeByEnumID(user));
			utils.httpRequest.setContentType("application/json");
			utils.httpRequest.setPayLoad(inputJson);
			utils.httpRequest.buildAndExecute();
			if (utils.httpRequest.getStatusCode().equalsIgnoreCase("200"))
			{
				arrCodeGroup = new JSONObject(utils.httpRequest.getResponseBody()).getJSONArray("data");
				codeGroupId = new JSONObject(arrCodeGroup.get(0).toString()).getInt("id");
			}

			return codeGroupId;

		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
	}

	public int createCodeGroupCode(int codeGroupId, String Code, String codeDescription, String codeType, String user) throws Throwable
	{
		JSONArray arrCodeGroupCodes = null;
		int codeGroupCodeId = 0;
		try
		{
			String inputJson = "[{\"code\": \"" + Code + "\",\"codeGroupId\":" + codeGroupId + ",\"codeDescription\":\"" + codeDescription + "\",\"codeType\":\"" + codeType + "\"}]";
			Utils utils = new Utils(RequestType.POST, MyBaseTest.algoServiceURL + "/code-group/code", getUserTypeByEnumID(user));
			utils.httpRequest.setContentType("application/json");
			utils.httpRequest.setPayLoad(inputJson);
			utils.httpRequest.buildAndExecute();
			if (utils.httpRequest.getStatusCode().equalsIgnoreCase("200"))
			{
				arrCodeGroupCodes = new JSONObject(utils.httpRequest.getResponseBody()).getJSONArray("data");
				codeGroupCodeId = new JSONObject(arrCodeGroupCodes.get(0).toString()).getInt("id");
			}

			return codeGroupCodeId;

		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
	}

	public int createAppendix(int versionId, String appendixName, String appendixDescription, String user) throws Throwable
	{
		int AppendixId = 0;
		try
		{
			String inputJson = " { \"description\": \"" + appendixDescription + "\",  \"name\": \"" + appendixName + "\", \"versionId\": " + versionId + " } ";
			Utils utils = new Utils(RequestType.POST, MyBaseTest.algoServiceURL + "/appendix", getUserTypeByEnumID(user));
			utils.httpRequest.setContentType("application/json");
			utils.httpRequest.setPayLoad(inputJson);
			utils.httpRequest.buildAndExecute();
			if (utils.httpRequest.getStatusCode().equalsIgnoreCase("200"))
			{

				AppendixId = new JSONObject(new JSONObject(utils.httpRequest.getResponseBody()).get("data").toString()).getInt("id");
			}
		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
		return AppendixId;
	}

	public int createAppendixCodeGroup(int appendixId, String appendixCodeGroupName, String appendixCodeGroupDescription, String user) throws Throwable
	{
		JSONArray arrCodeGroup = null;
		int codeGroupId = 0;
		try
		{
			String inputJson = "[{\"name\": \"" + appendixCodeGroupName + "\",\"appendixId\":" + appendixId + ",\"description\":\"" + appendixCodeGroupDescription + "\"}]";
			Utils utils = new Utils(RequestType.POST, MyBaseTest.algoServiceURL + "/appendix/code-group", getUserTypeByEnumID(user));
			utils.httpRequest.setContentType("application/json");
			utils.httpRequest.setPayLoad(inputJson);
			utils.httpRequest.buildAndExecute();
			if (utils.httpRequest.getStatusCode().equalsIgnoreCase("200"))
			{
				arrCodeGroup = new JSONObject(utils.httpRequest.getResponseBody()).getJSONArray("data");
				codeGroupId = new JSONObject(arrCodeGroup.get(0).toString()).getInt("id");
			}

			return codeGroupId;

		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
	}
}
