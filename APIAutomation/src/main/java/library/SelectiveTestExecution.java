package library;

import java.util.ArrayList;
import java.util.List;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;

public class SelectiveTestExecution implements IMethodInterceptor
{
	@Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context)
	{
		List<IMethodInstance> result = new ArrayList<IMethodInstance>();

		try
		{
			String modules[];
			String tests[];
			modules = XLOps.getElementsTOExecute("modules", "module_name");
			for (int i = 0; i < modules.length; i++)
			{
				tests = XLOps.getElementsTOExecute(modules[i], "test_name");
				for (IMethodInstance method : methods)
				{
					for (int j = 0; j < tests.length; j++)
					{

						String one = method.getInstance().getClass().getSimpleName() + "." + method.getMethod().getMethodName();
						String two = modules[i].trim() + "." + tests[j].trim();

						if (one.equalsIgnoreCase(two))
						{
							ITestNGMethod m = method.getMethod();
							result.add(method);
							break;
						}
						/*else
						{
							Logging.log.info("Method IGNORED for execution :" + method.getMethod().getMethodName().getClass().getSimpleName() + "." + method.getMethod().getMethodName());
						}*/
					}
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

}
