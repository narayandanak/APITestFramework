package library;

import java.util.ArrayList;
import java.util.List;

public class CodesSelection extends Common
{
	//
	private int				noOfCodes;
	private String			category;
	private List<String>	lstCodes	= new ArrayList<>();

	public CodesSelection(int noOfCodes, String category) throws Throwable
	{
		this.noOfCodes = noOfCodes;
		this.category = category;
		try
		{
			String codes[] = this.getFileData(this.category).split(",");
			List<Integer> codeIndexes = this.randomListGenerateBetweenGivenRange(codes.length, this.noOfCodes);
			for (int i = 0; i < codeIndexes.size(); i++)
			{
				lstCodes.add(codes[codeIndexes.get(i)]);
			}
		}
		catch (Exception e)
		{
			throw (new Throwable(e));
		}
	}

	public List<String> getListOfCodes() throws Throwable
	{
		return this.lstCodes;
	}

}
