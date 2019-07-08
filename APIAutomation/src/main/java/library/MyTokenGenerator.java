package library;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

import enums.RequestType;
import enums.UserType;

public class MyTokenGenerator implements Cloneable {
	public String authToken;
	private static String Base64AuthTokenForLogin = null;
	/*
	 * private String getAccessToken(String userId) { try { Map<String,Object>
	 * params = new LinkedHashMap<>(); params.put("grant_type","refresh_token");
	 * params.put("client_id", userId); params.put("client_secret",[YOUR CLIENT
	 * SECRET]); params.put("refresh_token",[YOUR REFRESH TOKEN]);
	 * 
	 * StringBuilder postData = new StringBuilder(); for(Map.Entry<String,Object>
	 * param : params.entrySet()) { if(postData.length() != 0) {
	 * postData.append('&'); }
	 * postData.append(URLEncoder.encode(param.getKey(),"UTF-8"));
	 * postData.append('=');
	 * postData.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
	 * } byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	 * 
	 * URL url = new URL("https://accounts.google.com/o/oauth2/token");
	 * HttpURLConnection con = (HttpURLConnection)url.openConnection();
	 * con.setDoOutput(true); con.setUseCaches(false); con.setRequestMethod("POST");
	 * con.getOutputStream().write(postDataBytes);
	 * 
	 * BufferedReader reader = new BufferedReader(new
	 * InputStreamReader(con.getInputStream())); StringBuffer buffer = new
	 * StringBuffer(); for (String line = reader.readLine(); line != null; line =
	 * reader.readLine()) { buffer.append(line); }
	 * 
	 * JSONObject json = new JSONObject(buffer.toString()); String accessToken =
	 * json.getString("access_token"); return accessToken; } catch (Exception ex) {
	 * ex.printStackTrace(); } return null; }
	 */
	static {

		try {

			if (Logging.log == null) {
				new Logging();
			}
			MyBaseTest.setTestProperties();

			/*
			 * String ClientId = MyBaseTest.prop.getProperty("clientId"); String
			 * ClientSecret = MyBaseTest.prop.getProperty("clientSecret"); String finalKey =
			 * ClientId + ":" + ClientSecret; Base64AuthTokenForLogin = new
			 * String(Base64.getEncoder().encode(finalKey.getBytes()));
			 */

			/*
			 * Base64AuthTokenForLogin =
			 * generateToken(MyBaseTest.prop.getProperty("clientId"),
			 * MyBaseTest.prop.getProperty("clientSecret")); String id = "test_fac"; String
			 * password = MyBaseTest.prop.getProperty("ezdiFacilityAdminPassword");
			 * MyTokenGenerator tokenObject = new MyTokenGenerator(id, password);
			 * MyBaseTest.hashMapOfUsersAndUserTokens.put("FACILITY_ADMIN", tokenObject);
			 * 
			 * id = "Facility.admin"; password =
			 * MyBaseTest.prop.getProperty("ezdiFacilityPassword"); MyBaseTest.tokenObject =
			 * new MyTokenGenerator(id, password);
			 * MyBaseTest.hashMapOfUsersAndUserTokens.put("FACILITY",
			 * MyBaseTest.tokenObject);
			 * 
			 * id = "test_user"; password =
			 * MyBaseTest.prop.getProperty("ezdiFacilityUserPassword"); tokenObject = new
			 * MyTokenGenerator(id, password);
			 * MyBaseTest.hashMapOfUsersAndUserTokens.put("FACILITY_USER", tokenObject);
			 * 
			 * id = "ezdi.admin"; password =
			 * MyBaseTest.prop.getProperty("ezdiRootAdminPassword"); tokenObject = new
			 * MyTokenGenerator(id, password);
			 * MyBaseTest.hashMapOfUsersAndUserTokens.put("EZDI_ADMIN_USER", tokenObject);
			 * System.out.println(tokenObject.authToken);
			 */
			// ================cat cat
			MyTokenGenerator tokenObject = new MyTokenGenerator();
			
			
			tokenObject.authToken = "1f9dca37-1dee-48c6-967d-939013946e1d";
			
			Users user = new Users("CAT_API_USER1", "CAT_API_USER1", "1f9dca37-1dee-48c6-967d-939013946e1d");
			MyBaseTest.hashMapOfUsers.put(user.getIdentifier(), user);
			
			user = new Users("CAT_API_USER2", "CAT_API_USER2", "1f9dca37-1dee-48c6-967d-939013946e1d");
			MyBaseTest.hashMapOfUsers.put(user.getIdentifier(), user);
			
			user = new Users("CAT_API_USER3", "CAT_API_USER3", "1f9dca37-1dee-48c6-967d-939013946e1d" + "Making it invalid");
			MyBaseTest.hashMapOfUsers.put(user.getIdentifier(), user);
			
			
			/*
			 * MyBaseTest.hashMapOfUsersAndUserTokens.put("CAT_API_USER1",
			 * (MyTokenGenerator)tokenObject.clone());
			 * MyBaseTest.hashMapOfUsersAndUserTokens.put("CAT_API_USER2",
			 * (MyTokenGenerator)tokenObject.clone()); tokenObject.authToken =
			 * tokenObject.authToken + "makingItInvalid";
			 * MyBaseTest.hashMapOfUsersAndUserTokens.put("CAT_API_USER3",
			 * (MyTokenGenerator)tokenObject.clone()); tokenObject.authToken =
			 * "1f9dca37-1dee-48c6-967d-939013946e1d";
			 * 
			 * Users user = new Users("GMAIL_USER_1", "narayan.danak@gmail.com",
			 * "Bearer ya29.GmKnBpOUGE4ufZ2k-YFMqYYVTkQHnj1qN0GSMkrMgrE1Q-ahfyda5S89tCkStNOcGTbja_U03gV99dBEegw_0UkHHbjmob8gUjleiayjMHNcrsvc05ptebXOZOsD0CCNe8YsvA"
			 * ); MyBaseTest.hashMapOfUsers.put(user.getIdentifier(), user);
			 * 
			 * user = new Users("GMAIL_USER_2", "narayan.catalyst@gmail.com",
			 * "Bearer ya29.GmOnBtoO0Z_qfArJOdHW3Gouvkmxz4PbwQDQ1JTEyjLZi7O62nIpZQQ2O-q9jF319Cl3fwjTWg9bf8Ewe9Ai67PtQzgKrTlQgAsFPvfCieAbsTkU3oqchWOharoIRjjoTbmwnnQ"
			 * ); MyBaseTest.hashMapOfUsers.put(user.getIdentifier(), user.clone());
			 */
			
				  
			
			for (int i = 1; i <= 2; i++) {

				// fac admin creates fac user

				/*
				 * utils = new Utils(RequestType.POST, MyBaseTest.prop.getProperty("loginURL") +
				 * "/product/user", new MyBaseTest().getUserTypeByEnumID("FACILITY" + i +
				 * "_ADMIN"));
				 * 
				 * 
				 * String facilityUsername = common.randomTextGenerator(6); requestJson =
				 * "{\"firstName\":\"" + common.randomTextGenerator(4) +
				 * "\",\"middleName\":null,\"lastName\":\"" + common.randomTextGenerator(4) +
				 * "\",\"username\":\"" + facilityUsername +
				 * "\",\"emailId\":\"test@ezdi.us\",\"mnemonic\":\"Q\",\"speciality\":\"Q\"}";
				 * utils.httpRequest.setPayLoad(requestJson);
				 * 
				 * utils.httpRequest.buildAndExecute();
				 * System.out.println(utils.httpRequest.getStatusCode());
				 * System.out.println(utils.httpRequest.getResponseBody());
				 * 
				 * String userId = new
				 * JSONObject(utils.httpRequest.getResponseBody()).get("userId").toString();
				 * resetToken = new
				 * JSONObject(utils.httpRequest.getResponseBody()).get("userResetUrl").toString(
				 * ).split("token/")[1];
				 * 
				 * utils = new Utils(RequestType.POST, MyBaseTest.prop.getProperty("loginURL") +
				 * "/admin/resetPassword", UserType.NULL);
				 * 
				 * urlParameters = new ArrayList<>(); urlParameters.add(new
				 * BasicNameValuePair("username", facilityUsername)); urlParameters.add(new
				 * BasicNameValuePair("randomToken", resetToken)); urlParameters.add(new
				 * BasicNameValuePair("newPassword",
				 * MyBaseTest.prop.getProperty("defaultPassword")));
				 * 
				 * utils.httpRequest.setFormData(urlParameters).setContentType(
				 * "application/x-www-form-urlencoded");
				 * 
				 * utils.httpRequest.buildAndExecute();
				 * 
				 * System.out.println(utils.httpRequest.getStatusCode());
				 * System.out.println(utils.httpRequest.getResponseBody());
				 * UserType.setLookupMapForAllUsers(facilityAdminUsername,
				 * UserType.FACILITY1_USER1); //set enum username password =
				 * MyBaseTest.prop.getProperty("defaultPassword"); MyBaseTest.tokenObject = new
				 * MyTokenGenerator(facilityAdminUsername, password);
				 * MyBaseTest.hashMapOfUsersAndUserTokens.put("FACILITY1_USER1",
				 * MyBaseTest.tokenObject);
				 * 
				 * utils = new Utils(RequestType.POST, MyBaseTest.prop.getProperty("loginURL") +
				 * "/role", new MyBaseTest().getUserTypeByEnumID("FACILITY" + i + "_ADMIN"));
				 * String roleName = utils.common.randomTextGenerator(4); productId = "2";
				 * //fetch from main.properties requestJson = "{\"name\" : \"" + roleName +
				 * "\",\"productId\" : " + productId + "}";
				 * utils.httpRequest.setPayLoad(requestJson);
				 * 
				 * utils.httpRequest.buildAndExecute();
				 * System.out.println(utils.httpRequest.getStatusCode());
				 * System.out.println(utils.httpRequest.getResponseBody()); String roleId = new
				 * JSONObject(utils.httpRequest.getResponseBody()).get("id").toString();
				 * 
				 * utils = new Utils(RequestType.POST, MyBaseTest.prop.getProperty("loginURL") +
				 * "/user/role", new MyBaseTest().getUserTypeByEnumID("FACILITY" + i +
				 * "_ADMIN"));
				 * 
				 * requestJson = "{\"userId\": " + userId + ",\"roleId\":" + roleId + "}";
				 * utils.httpRequest.setPayLoad(requestJson);
				 * 
				 * utils.httpRequest.buildAndExecute();
				 * System.out.println(utils.httpRequest.getStatusCode());
				 * System.out.println(utils.httpRequest.getResponseBody());
				 */

			}
		} catch (Throwable e) {

			try {
				throw (new Throwable(e));
			} catch (Throwable e1) {
				e1.printStackTrace();
				// TestRunner testRunner = new TestRunner();
			}

		}

	}

	public void MyTokenGenerator1(String id, String password) throws Throwable {
		// super(id, password);
		try {

			// String id = "ezdi.admin";
			// String password = MyBaseTest.prop.getProperty("password");;
			// String cookie;
			MyBaseTest btst = new MyBaseTest();
			Utils utils = new Utils(RequestType.POST,
					MyBaseTest.prop.getProperty("loginURL") + "" + "/oauth/token?grant_type=password", UserType.NULL);
			// System.out.println("id : " + id);
			// System.out.println("password : " + password);
			List<NameValuePair> urlParameters = new ArrayList<>();
			urlParameters.add(new BasicNameValuePair("username", id));
			urlParameters.add(new BasicNameValuePair("password", password));

			utils.httpRequest.setFormData(urlParameters);

			Header[] headers = { new BasicHeader("Content-Type", "application/x-www-form-urlencoded"),
					new BasicHeader("Authorization", "Basic " + Base64AuthTokenForLogin) };
			utils.httpRequest.setHeader(headers);
			utils.httpRequest.buildAndExecute();
			String accessToken = "";
			if (utils.httpRequest.getStatusCode().equalsIgnoreCase("200")) {
				Logging.log.info("User logged into System");
				JSONObject json = new JSONObject(utils.httpRequest.getResponseBody());

				if (json.has("access_token")) {
					accessToken = json.get("access_token").toString();
				} else {
					accessToken = "";
				}
			} else {
				Logging.log.error(
						"Error in User Login(" + id + "): Response Body=>" + utils.httpRequest.getResponseBody());
			}

			MyBaseTest.tokenString = "bearer " + accessToken;
			// System.out.println("Auth Token For =>" + id + " is =>" +
			// MyBaseTest.tokenString);
			this.authToken = MyBaseTest.tokenString;

		} catch (Exception e) {
			throw (new Throwable(e));
		}

	}

	public MyTokenGenerator() {
		// TODO Auto-generated constructor stub
	}

	public String getAuthTokenString() {
		return this.authToken;
	}

	public String getAuthToken() {
		return this.authToken;
	}

	private static synchronized String generateToken(String clientId, String clientSecret) {
		String returnValue = "";

		String ClientId = clientId;
		String ClientSecret = clientSecret;
		String finalKey = ClientId + ":" + ClientSecret;
		String Base64AuthTokenForLogin = new String(Base64.getEncoder().encode(finalKey.getBytes()));
		returnValue = Base64AuthTokenForLogin;
		return returnValue;
	}

	public synchronized String generateTokenFromClientIdAndClientSecret(String clientId, String clientSecret) {
		String returnValue = "";

		String ClientId = clientId;
		String ClientSecret = clientSecret;
		String finalKey = ClientId + ":" + ClientSecret;
		String Base64AuthTokenForLogin = new String(Base64.getEncoder().encode(finalKey.getBytes()));
		returnValue = Base64AuthTokenForLogin;
		return returnValue;
	}
}
