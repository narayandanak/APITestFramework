package library;

import org.json.JSONObject;

public class InputParametersRequest
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