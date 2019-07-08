package library.facilityService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import enums.RequestType;
import enums.UserType;
import library.Common;
import library.InputParametersRequest;
import library.MyBaseTest;
import library.MyTokenGenerator;
import library.Utils;

public class BasicFacilityEntities
{
	private Common	common					= new Common();
	private String	facilityId				= "";
	private String	facilityName			= "";
	private String	facilityAdminUserName	= "";

	public String getFacilityId()
	{
		return this.facilityId;
	}

	private void setFacilityId(String id)
	{
		this.facilityId = id;
	}

	public String getFacilityName()
	{
		return this.facilityName;
	}

	private void setFacilityName(String facilityName)
	{
		// TODO Auto-generated method stubs
		this.facilityName = facilityName;

	}

	private void setFacilityAdminUsername(String facilityAdminUsername)
	{
		// TODO Auto-generated method stub
		this.facilityAdminUserName = facilityAdminUsername;

	}

	public String getFacilityAdminUsername()
	{
		// TODO Auto-generated method stub
		return this.facilityAdminUserName;
	}

	public synchronized HashMap<String, String> createFacility(String userId) throws Throwable
	{
		try
		{
			Utils utils = new Utils(RequestType.POST, MyBaseTest.facilityServiceURL + "/facility", userId);
			String facilityName = common.randomTextGenerator(4);
			String strJson = "{\"name\": \"strName\"}";
			strJson = strJson.replaceAll("\\bstrName\\b", facilityName);

			InputParametersRequest data = new InputParametersRequest(strJson);
			utils.httpRequest.setPayLoad(data.getJsonForRequest().toString());
			utils.httpRequest.buildAndExecute();
			MyBaseTest.deleteHttpRequestMap.put(utils.httpRequest, "id");

			//			Assert.assertEquals(utils.httpRequest.getStatusCode(), "201");
			JSONObject actualJSON = new JSONObject(utils.httpRequest.getResponseBody());
			int id = actualJSON.getInt("id");
			String facilityId = String.valueOf(id);
			HashMap<String, String> basicFacilityEntities = new HashMap<String, String>();
			basicFacilityEntities.put("facilityId", facilityId);
			basicFacilityEntities.put("facilityName", facilityName);
			return basicFacilityEntities;
		}
		/*	catch (JSONException e)
			{
				throw (new Throwable(e));
			}*/
		catch (Exception e)
		{
			Throwable t = new Throwable(e);
			throw (new Exception(e));
		}

	}

	public synchronized void assignProducts(String facilityId) throws Throwable
	{
		try
		{
			//BasicFacilityEntities basicFacilityEntities = createFacility();
			//String facilityId = basicFacilityEntities.getFacilityId();
			Utils utils = new Utils(RequestType.POST, MyBaseTest.loginURL + "/facility/product", UserType.EZDI_ADMIN_USER);
			for (int j = 0; j < MyBaseTest.prop.getProperty("productIds").split(",").length; j++)
			{
				utils.httpRequest.setPayLoad("{\"facilityId\" : " + facilityId + ", \"productId\" : " + MyBaseTest.prop.getProperty("productIds").split(",")[j] + "}");
				utils.httpRequest.buildAndExecute();
				MyBaseTest.deleteHttpRequestMap.put(utils.httpRequest, "id");
			}
		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
	}

	public synchronized String createFacilityAdminUser(String facilityId) throws Throwable
	{
		try
		{
			//BasicFacilityEntities basicFacilityEntities = createFacility();
			//String facilityId = basicFacilityEntities.getFacilityId();
			Utils utils = new Utils(RequestType.POST, MyBaseTest.loginURL + "/admin/user", UserType.EZDI_ADMIN_USER);

			String facilityAdminUsername = common.randomTextGenerator(6);
			String requestJson = "{\"firstName\":\"" + common.randomTextGenerator(4) + "\",\"middleName\":null,\"lastName\":\"" + common.randomTextGenerator(4) + "\",\"username\":\"" + facilityAdminUsername + "\",\"emailId\":\"test@ezdi.us\"}";

			JSONObject jsonobj = new JSONObject(requestJson);
			jsonobj.put("tenantId", facilityId);
			utils.httpRequest.setPayLoad(jsonobj.toString());
			//System.out.println(jsonobj.toString());
			utils.httpRequest.buildAndExecute();
			MyBaseTest.deleteHttpRequestMap.put(utils.httpRequest, "userId");

			/*System.out.println(utils.httpRequest.getStatusCode());
			System.out.println(utils.httpRequest.getResponseBody());*/

			String resetToken = new JSONObject(utils.httpRequest.getResponseBody()).get("userResetUrl").toString().split("token/")[1];

			utils = new Utils(RequestType.POST, MyBaseTest.prop.getProperty("loginURL") + "/admin/resetPassword", UserType.NULL);

			List<NameValuePair> urlParameters = new ArrayList<>();
			urlParameters.add(new BasicNameValuePair("username", facilityAdminUsername));
			urlParameters.add(new BasicNameValuePair("randomToken", resetToken));
			urlParameters.add(new BasicNameValuePair("newPassword", MyBaseTest.prop.getProperty("defaultPassword")));

			utils.httpRequest.setFormData(urlParameters);
			utils.httpRequest.setContentType("application/x-www-form-urlencoded");

			utils.httpRequest.buildAndExecute();

			/*System.out.println(utils.httpRequest.getStatusCode());
			System.out.println(utils.httpRequest.getResponseBody());*/

			//login fac admin user
			String password = MyBaseTest.prop.getProperty("defaultPassword");
			/*
			 * MyTokenGenerator tokenObject = new MyTokenGenerator(facilityAdminUsername,
			 * password); MyBaseTest.hashMapOfUsersAndUserTokens.put(facilityAdminUsername,
			 * tokenObject);
			 */
			return facilityAdminUsername;
		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}

	}

	public synchronized BasicFacilityEntities createBasicFacilityEntities(String EzdiAdminUserName) throws Throwable
	{
		BasicFacilityEntities basicFacilityEntities = new BasicFacilityEntities();
		try
		{
			HashMap<String, String> basicFacilityHashMap = createFacility(EzdiAdminUserName);
			basicFacilityEntities.setFacilityId(basicFacilityHashMap.get("facilityId"));
			basicFacilityEntities.setFacilityName(basicFacilityHashMap.get("facilityName"));
			assignProducts(basicFacilityEntities.getFacilityId());
			basicFacilityEntities.setFacilityAdminUsername(createFacilityAdminUser(basicFacilityEntities.getFacilityId()));
		}
		catch (Exception e)
		{
			throw (e);
		}
		//this = basicFacilityEntities;
		return basicFacilityEntities;
	}

}
