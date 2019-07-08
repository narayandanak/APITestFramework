package library;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logging
{
	public static Logger log = LogManager.getLogger("product");

	public Logging()
	{
		String logDir = System.getProperty("user.dir") + "\\logs\\";
		System.setProperty("logFileAbsolutePath", logDir + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
		//Domcon.configure(logDir + "log4j.properties");
		//DOMConfigurator.configure("src/main/resources/Properties/log4j.properties");

	}

}
