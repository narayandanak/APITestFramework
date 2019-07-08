package enums;

import java.util.HashMap;
import java.util.Map;

public enum UserType
{
	RUMC_CODER("RUMC_CODER"), RUMC_REVIEWER("RUMC_REVIEWER"), RUMC_SUPERVISOR("RUMC_SUPERVISOR"), RUMC_CODERSUPERVISOR("RUMC_CODERSUPERVISOR"), RUMC_AUDITOR("RUMC_AUDITOR"), HRMC_CODER("HRMC_CODER"), HRMC_REVIEWER("HRMC_REVIEWER"), HRMC_SUPERVISOR("HRMC_SUPERVISOR"), HRMC_CODERSUPERVISOR(
			"HRMC_CODERSUPERVISOR"), HRMC_CDI("HRMC_CDI"), HRMC_CDISUPERVISOR("HRMC_CDISUPERVISOR"), NULL("NULL"), ROOT("ROOT"), FACILITY_ADMIN("test_fac"), EZDI_ADMIN_USER(
					"ezdi.admin"), FACILITY_USER("test_user"), FACILITY1_ADMIN("FACILITY1_ADMIN"), FACILITY2_ADMIN("FACILITY2_ADMIN"), FACILITY3_ADMIN("FACILITY3_ADMIN"), FACILITY1_USER1("FACILITY1_USER1"), FACILITY2_USER1("FACILITY2_USER1"), FACILITY3_USER1("FACILITY3_USER1"),
	
	CAT_API_USER1("CAT_API_USER1"),
	CAT_API_USER2("CAT_API_USER2"),
	CAT_API_USER3("CAT_API_USER3"),
	
	GMAIL_USER_1("1"),
	GMAIL_USER_2("GMAIL_USER_2");
	/*
	 * Metadata - global metadata - EZDI_ADMIN_USER
	 * Metadata - other -  FACILITY_ADMIN
	 * Facility - creation - Root = EZDI_ADMIN_USER
	 * Facility - update - grouper config - encoder config all config - FACILITY_ADMIN
	 * */
	private UserType(String abbreviation)
	{
		BootstrapSingleton.lookup.put(abbreviation.toUpperCase(), this);
	}

	public static UserType getUserType(String abbreviation) throws Throwable
	{
		try
		{
			if (BootstrapSingleton.lookup.get(abbreviation) != null)
			{
				return BootstrapSingleton.lookup.get(abbreviation.toUpperCase());
			}
			else
			{
				return NULL;
			}
		}
		catch (Exception e)
		{
			throw (new Throwable(e));

		}
	}

	public static void setLookupMapForAllUsers(String name, UserType t)
	{
		BootstrapSingleton.lookup.put(name, t);
	}
}

class BootstrapSingleton {

	public static Map<String, UserType> lookup = new HashMap<String, UserType>();
}
