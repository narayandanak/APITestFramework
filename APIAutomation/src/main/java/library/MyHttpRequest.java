package library;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import enums.RequestType;

public class MyHttpRequest

{

	//required parameters
	private String			RequestType;
	private String			partialRequestURL;

	//optional Parameter
	private Header[]		headers;
	private String			payload				= null;
	private Object			formdata			= null;
	private String			responseBody		= null;

	//General Variables
	private HttpResponse	response			= null;
	private CookieStore		httpCookieStore		= new BasicCookieStore();
	private HttpClient		httpClient			= HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).setDefaultCookieStore(httpCookieStore).build();

	private HttpPost		httpPostRequest		= null;
	private HttpGet			httpGetRequest		= null;
	private HttpPut			httpPutRequest		= null;
	private HttpDelete		httpDeleteRequest	= null;
	private String			csrf				= "";
	private String			authToken			= "";
	private String			userName			= "";
	private boolean			isEntitySet			= false;

	public MyHttpRequest(RequestType requestType, String partialRequestURL, String userType) throws Throwable
	{
		try
		{

			this.userName = userType;

			for (String userType1 : MyBaseTest.hashMapOfUsers.keySet())
			{
				if (userType != null && userType1.trim().equalsIgnoreCase(userType.toString()))
				{
					Users user = MyBaseTest.hashMapOfUsers.get(userType1);
					authToken = user.getToken();
					break;
				}
			}

			this.RequestType = requestType.toString();
			//this.partialRequestURL = MyBaseTest.prop.getProperty("URL") + partialRequestURL;
			this.partialRequestURL = partialRequestURL;
			//Header[] headers = { new BasicHeader("x-api-key",  authToken)};
			Header[] headers = { new BasicHeader("Content-Type",  "application/json")};
			this.headers = headers;

			httpPostRequest = new HttpPost(this.partialRequestURL);
			httpGetRequest = new HttpGet(this.partialRequestURL);
			httpPutRequest = new HttpPut(this.partialRequestURL);
			httpDeleteRequest = new HttpDelete(this.partialRequestURL);

		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
		//this.httpCookieStore = new BasicCookieStore();
		//this.httpClient = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).setDefaultCookieStore(httpCookieStore).build();

	}

	public HttpResponse getResponseObject()
	{
		return this.response;
	}

	public void setFormEntity(HttpEntity e)
	{
		this.httpPostRequest.setEntity(e);
		this.httpPutRequest.setEntity(e);
		isEntitySet = true;
	}

	private synchronized void addHeader(BasicHeader basicHeader)
	{
		Header[] header = this.headers;
		Header[] newHeader = new Header[header.length + 1];
		newHeader[header.length] = basicHeader;
		this.headers = newHeader;
	}

	public synchronized void replaceOrAddHeader(BasicHeader basicHeader)
	{
		Header[] header = this.headers;
		Header[] newHeader = header;
		boolean isHeaderFound = false;
		for (int i = 0; i < newHeader.length; i++)
		{
			if (newHeader[i].getName().equals(basicHeader.getName()))
			{
				newHeader[i] = basicHeader;
				isHeaderFound = true;
			}
		}

		if (isHeaderFound == false)
			this.addHeader(basicHeader);

		this.headers = newHeader;
	}

	public synchronized void setContentType(String contentType) throws Throwable
	{
		try
		{
			Header[] headers = { new BasicHeader("x-api-key", authToken), new BasicHeader("Content-Type", contentType), new BasicHeader("Authorization", authToken) };
			this.headers = headers;
		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
	}

	public void removeHeader(String keyOfHeader)
	{
		Header[] header = this.headers;
		Header[] newHeader;
		ArrayList<BasicHeader> listOfBasicHeders = new ArrayList<BasicHeader>();

		for (int i = 0; i < header.length; i++)
		{
			if (header[i].getName().equals(keyOfHeader))
			{
				header[i] = null;
			}
			else
			{
				listOfBasicHeders.add((BasicHeader) header[i]);
			}
		}
		newHeader = new Header[listOfBasicHeders.size()];

		for (int i = 0; i < listOfBasicHeders.size(); i++)
		{

			newHeader[i] = listOfBasicHeders.get(i);
		}

		this.headers = newHeader;

	}

	public MyHttpRequest setHeader(Header[] headers)
	{
		this.headers = headers;
		return this;
	}

	public MyHttpRequest setPayLoad(String payload)
	{
		this.payload = payload;
		return this;
	}

	public void setFormData(HttpEntity formdata)
	{
		this.formdata = formdata;

	}

	public void setFormData(List<NameValuePair> formdata)
	{
		this.formdata = formdata;

	}

	public String getUrl()
	{
		return partialRequestURL;
	}

	public String getUserName()
	{
		return userName;
	}

	public synchronized void buildAndExecute() throws Throwable
	{
		try
		{
			httpCookieStore.clear();
			if (RequestType.equalsIgnoreCase("POST"))
			{

				httpPostRequest.setHeaders(headers);

				if (payload != null)
				{
					httpPostRequest.setEntity(new StringEntity(payload));
				}
				if (formdata != null)
				{
					try
					{

						if (isEntitySet == false)
						{
							List<NameValuePair> o = (List<NameValuePair>) formdata;
							httpPostRequest.setEntity(new UrlEncodedFormEntity(o));
						}
					}
					catch (Exception e)
					{
						//when List<NameValuePari>
						Logging.log.info(MyBaseTest.getStaktrace(e));
					}
					//httpPostRequest.setEntity(formdata);

				}

				this.response = httpClient.execute(httpPostRequest);
				this.setResponseBody();
				//this.responseHeader = this.response.getAllHeaders();
			}
			else if (RequestType.equalsIgnoreCase("GET"))
			{

				httpGetRequest.setHeaders(headers);
				
				this.response = httpClient.execute(httpGetRequest);
				this.setResponseBody();
				//this.responseHeader = this.response.getAllHeaders();
			}
			else if (RequestType.equalsIgnoreCase("PUT"))
			{

				httpPutRequest.setHeaders(headers);
				if (payload != null)
				{
					httpPutRequest.setEntity(new StringEntity(payload));
				}
				if (formdata != null)
				{
					try
					{

						if (isEntitySet == false)
						{
							List<NameValuePair> o = (List<NameValuePair>) formdata;
							httpPostRequest.setEntity(new UrlEncodedFormEntity(o));
						}
					}
					catch (Exception e)
					{
						//when List<NameValuePari>
						Logging.log.info(MyBaseTest.getStaktrace(e));
					}
					//httpPostRequest.setEntity(formdata);
				}

				this.response = httpClient.execute(httpPutRequest);
				this.setResponseBody();
				//this.responseHeader = this.response.getAllHeaders();
			}
			else if (RequestType.equalsIgnoreCase("DELETE"))
			{
				httpDeleteRequest.setHeaders(headers);
				this.response = httpClient.execute(httpDeleteRequest);
				this.setResponseBody();
				//this.responseHeader = this.response.getAllHeaders();
			}
			else
			{
				throw new Throwable("Improper Request Type");
			}

		}
		catch (Exception e)
		{
			System.out.println(MyBaseTest.getStaktrace(e));
			throw (new Throwable(e));
		}

	}

	public synchronized String getStatusCode()
	{
		return Integer.toString(this.response.getStatusLine().getStatusCode());
	}

	public synchronized String getResponseBody() throws Throwable
	{
		try
		{
			//System.out.println(EntityUtils.toString(response.getEntity()));
			return this.responseBody;
		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}

	}

	public synchronized String getResponseHeaderValue(String headerKey) throws Throwable
	{
		try
		{

			return this.response.getFirstHeader(headerKey).getValue();
		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
	}

	private synchronized void setResponseBody() throws Throwable
	{
		try
		{
			if (!this.getStatusCode().equals("204")) {
				this.responseBody = EntityUtils.toString(this.response.getEntity());
			}
		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
	}

	public synchronized String getCookieValue(String cookieName) throws Throwable
	{
		try
		{
			List<Cookie> lst_cookie = httpCookieStore.getCookies();
			String sessionid = null;
			for (int i = 0; i < lst_cookie.size(); i++)
			{
				if (lst_cookie.get(i).getName().equalsIgnoreCase(cookieName))
				{
					sessionid = lst_cookie.get(i).getValue();
					break;

				}
			}

			return sessionid;

		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}

	}

	public synchronized String getSha256Hex(String text) throws Throwable
	{
		String shaHex = "";
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			md.update(text.getBytes("UTF-8"));
			byte[] digest = md.digest();

			shaHex = DatatypeConverter.printHexBinary(digest);
		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
		return shaHex.toLowerCase();
	}

}
