package extra;

import org.json.JSONObject;

public class JIRA
{
	public static void main(String ar[])
	{
		JIRA j = new JIRA();
		try
		{
			j.jiraSendRequest();
		}
		catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void jiraSendRequest() throws Throwable
	{
		try
		{

			/*MyHttpRequest m = new MyHttpRequest(RequestType.POST, "https://bhavin16692.atlassian.net/""https://ezdiprojects.atlassian.net/rest/api/2/issue/", "");
			URL url = new URL("https://ezdiprojects.atlassian.net/rest/api/2/issue/");
			Header[] headers = { new BasicHeader("Authorization", "Basic " + "cHJhbmF2Lm1AZXpkaS51czpUNDRjaHQyYkNhdVBvbUZoc0FHVzYwNTY=") };
			m.setHeader(headers);
			m.setPayLoad(getJSON_Body());
			m.setContentType("application/json");
			System.out.println(getJSON_Body());
			m.buildAndExecute();
			System.out.println("");
			*//*
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setDoInput(true);
				
				String encodedData = getJSON_Body();
				System.out.println(encodedData);
				
				System.out.println(getJSON_Body());
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Authorization", "Basic " + "cHJhbmF2Lm1AZXpkaS51czpUNDRjaHQyYkNhdVBvbUZoc0FHVzYwNTY=");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.getOutputStream().write(encodedData.getBytes());
				
				try
				{
				InputStream inputStream = conn.getInputStream();
				System.out.println(inputStream);
				}
				catch (IOException e)
				{
				System.out.println(e.getMessage());
				}
				*/ }
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static String getJSON_Body()
	{
		JSONObject createIssue = new JSONObject();
		createIssue.append("key", "xxxx").append("summary", "Test issue").append("description", "Test Issue").append("issuetype", new JSONObject().append("name", "Bug"));

		/*.add("fields",
		        Json.createObjectBuilder().add("project",
		                Json.createObjectBuilder().add("key", "xxxx"))
		                .add("summary", "Test issue")
		                .add("description", "Test Issue")
		                .add("issuetype",
		                        Json.createObjectBuilder().add("name", "Bug"))
		).build();*/

		return createIssue.toString();
	}
}
