package library;

import enums.RequestType;
import enums.UserType;

public class Utils extends MyBaseTest
{
	public MyHttpRequest	httpRequest;
	public XLOps			xlops;
	public Common			common;

	public Utils(RequestType requestType, String requestURL, UserType userType, XLOps... xlops) throws Throwable
	{
		try
		{
			httpRequest = new MyHttpRequest(requestType, requestURL, userType.toString());
			if (xlops.length > 0)
			{
				this.xlops = xlops[0];
			}
			else
			{
				this.xlops = new XLOps();
			}
			common = new Common();
		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
	}

	public Utils(RequestType requestType, String requestURL, String userType, XLOps... xlops) throws Throwable
	{
		try
		{
			httpRequest = new MyHttpRequest(requestType, requestURL, userType);
			if (xlops.length > 0)
			{
				this.xlops = xlops[0];
			}
			else
			{
				this.xlops = new XLOps();
			}
			common = new Common();
		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
	}
}
