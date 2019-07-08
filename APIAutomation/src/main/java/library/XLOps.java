package library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLOps
{
	private String			fileName	= "";
	private String			sheetName	= "";
	private String			testName	= "";
	private String			dataID		= "";
	private String			columnName	= "";

	private File			file;
	FileInputStream			fis;
	XSSFWorkbook			workbook;
	private XSSFSheet		sheet;
	private Row				header;
	private Row				dataRow;
	private Cell			cell;
	private Iterator<Row>	rowIterator;
	private Iterator<Cell>	cellIterator;

	public synchronized String getCellValue(String fileName, String sheetName, String testName, String dataID, String column) throws CustomException
	{
		String cellValue = null;
		this.setXLParam(fileName, sheetName, testName, dataID, column);
		this.setXLParamHeader(this.getHeader());
		try
		{
			this.setXLParam(fileName, sheetName, testName, dataID, column);
			header = getHeader(fileName, sheetName);
			dataRow = getDataRow(fileName, sheetName, testName, dataID);
			int intendedColIndex = getColIndex(header, column);
			cellValue = dataRow.getCell(intendedColIndex).toString();

			checkIfCellValueIsNull(cellValue);
			Logging.log.info("XLOps Found intended data cell identified by " + fileName + "." + sheetName + "." + testName + "." + dataID + "." + column);
		}
		catch (CustomException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			Logging.log.info("XLOpsError Unknown error occurred Failed inside " + new Object()
			{
			}.getClass().getEnclosingMethod().getName() + " Unknown error occurred - Could not get File Name : " + fileName + " Sheet : " + sheetName + " Test Name : " + testName + " Data ID : " + dataID + " Column : " + column);
			throw new CustomException("XLOpsError Unknown error occurred Failed inside " + new Object()
			{
			}.getClass().getEnclosingMethod().getName() + " Unknown error occurred - Could not get File Name : " + fileName + " Sheet : " + sheetName + " Test Name : " + testName + " Data ID : " + dataID + " Column : " + column);
		}
		return cellValue;
	}

	public synchronized String getCellValue(String testName, String dataID, String column) throws CustomException
	{
		String cellValue = null;
		this.setXLParam(testName, dataID, column);
		this.setXLParamHeader(this.getHeader());
		try
		{
			this.setXLParam(testName, dataID, column);
			header = getHeader(fileName, sheetName);
			dataRow = getDataRow(fileName, sheetName, testName, dataID);
			int intendedColIndex = getColIndex(header, column);
			cellValue = dataRow.getCell(intendedColIndex).toString();

			checkIfCellValueIsNull(cellValue);
			Logging.log.info("XLOps Found intended data cell identified by " + fileName + "." + sheetName + "." + testName + "." + dataID + "." + column);
		}
		catch (CustomException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			Logging.log.info("XLOpsError Unknown error occurred Failed inside " + new Object()
			{
			}.getClass().getEnclosingMethod().getName() + " Unknown error occurred - Could not get File Name : " + fileName + " Sheet : " + sheetName + " Test Name : " + testName + " Data ID : " + dataID + " Column : " + column);
			throw new CustomException("XLOpsError Unknown error occurred Failed inside " + new Object()
			{
			}.getClass().getEnclosingMethod().getName() + " Unknown error occurred - Could not get File Name : " + fileName + " Sheet : " + sheetName + " Test Name : " + testName + " Data ID : " + dataID + " Column : " + column);
		}
		return cellValue;
	}

	private synchronized Row getDataRow(String fileName, String sheetName, String testName, String dataID) throws CustomException
	{
		this.setXLParam(fileName, sheetName, testName, dataID);
		boolean success = false;
		try
		{
			this.setXLParam(fileName, sheetName, testName, dataID);
			file = new File("src/main/resources/properties/" + fileName);
			fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			dataRow = null;
			sheet = workbook.getSheet(sheetName);
			if (sheet == null)
			{
				Logging.log.info("XLOpsError Could not find the Sheet for the file " + sheetName);
				throw new CustomException("XLOpsError Could not find the Sheet for the file " + sheetName);
			}
			rowIterator = sheet.iterator();

			while (rowIterator.hasNext())
			{
				dataRow = rowIterator.next();
				if (!dataRow.getCell(0).toString().toLowerCase().equals("") && !dataRow.getCell(1).toString().toLowerCase().equals("") && dataRow.getCell(0).toString().toLowerCase().equals(testName.toLowerCase()) && dataRow.getCell(1).toString().toLowerCase().equals(dataID.toLowerCase()))
				{
					success = true;
					break;
				}
			}
			//throw new CustomException("XLOpsError data not found " + fileName + "." + sheetName + "." + testName + "." + dataID);

		}
		catch (FileNotFoundException e)
		{
			Logging.log.info("XLOpsError FileNotFoundException Custom Exception in " + new Object()
			{
			}.getClass().getEnclosingMethod().getName() + " File : " + System.getProperty("user.dir") + "//TestData//" + fileName + " not found");
			throw new CustomException("XLOpsError FileNotFoundException Custom Exception in " + new Object()
			{
			}.getClass().getEnclosingMethod().getName() + " File : " + System.getProperty("user.dir") + "//TestData//" + fileName + " not found");
		}
		catch (IOException e)
		{
			Logging.log.info("XLOpsError Sheet not found " + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
			throw new CustomException("XLOpsError Sheet not found " + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
		}
		catch (CustomException e)
		{
			//Logging.log.info(e);
			throw e;
		}
		catch (SecurityException e)
		{
			Logging.log.info("XLOpsError SecurityException Sheet not found " + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
			throw new CustomException("XLOpsError SecurityException Sheet not found " + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
		}
		catch (Exception e)
		{
			Logging.log.info("XLOpsError Unknown error occured inside " + new Object()
			{
			}.getClass().getEnclosingMethod().getName());
			throw new CustomException("XLOpsError Unknown error occured inside " + new Object()
			{
			}.getClass().getEnclosingMethod().getName());
		}

		if (!success)
		{
			Logging.log.info("XLOpsError could not find  data row specified by File Name : " + fileName + " Sheet : " + sheetName + " Test Name : " + testName + " Data ID : " + dataID);
			throw new CustomException("XLOpsError could not find  data row specified by File Name : " + fileName + " Sheet : " + sheetName + " Test Name : " + testName + " Data ID : " + dataID);
		}
		Logging.log.info("XLOps found intended data row identified by " + testName + "." + dataID);
		return dataRow;
	}

	private synchronized Row getDataRow() throws CustomException
	{
		boolean success = false;
		try
		{
			//this.setXLParam(fileName, sheetName, testName, dataID);
			file = new File("src/main/resources/properties/" + fileName);
			fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			dataRow = null;
			sheet = workbook.getSheet(sheetName);
			if (sheet == null)
			{
				Logging.log.info("XLOpsError Could not find the Sheet for the file " + sheetName);
				throw new CustomException("XLOpsError Could not find the Sheet for the file " + sheetName);
			}
			rowIterator = sheet.iterator();

			while (rowIterator.hasNext())
			{
				dataRow = rowIterator.next();
				if (!dataRow.getCell(0).toString().toLowerCase().equals("") && !dataRow.getCell(1).toString().toLowerCase().equals("") && dataRow.getCell(0).toString().toLowerCase().equals(testName.toLowerCase()) && dataRow.getCell(1).toString().toLowerCase().equals(dataID.toLowerCase()))
				{
					success = true;
					break;
				}
				//Logging.log.info("XLOpsError data not found " + fileName + "." + sheetName + "." + testName + "." + dataID);
				//throw new CustomException("XLOpsError data not found " + fileName + "." + sheetName + "." + testName + "." + dataID);
			}

		}
		catch (FileNotFoundException e)
		{
			Logging.log.info("XLOpsError FileNotFoundException Custom Exception in " + new Object()
			{
			}.getClass().getEnclosingMethod().getName() + " File : " + System.getProperty("user.dir") + "//TestData//" + fileName + " not found");
			throw new CustomException("XLOpsError FileNotFoundException Custom Exception in " + new Object()
			{
			}.getClass().getEnclosingMethod().getName() + " File : " + System.getProperty("user.dir") + "//TestData//" + fileName + " not found");
		}
		catch (IOException e)
		{
			Logging.log.info("XLOpsError Sheet not found " + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
			throw new CustomException("XLOpsError Sheet not found " + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
		}
		catch (CustomException e)
		{
			throw e;
		}
		catch (SecurityException e)
		{
			Logging.log.info("XLOpsError SecurityException Sheet not found " + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
			throw new CustomException("XLOpsError SecurityException Sheet not found " + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
		}
		catch (Exception e)
		{
			Logging.log.info("XLOpsError Unknown error occured inside " + new Object()
			{
			}.getClass().getEnclosingMethod().getName());
			throw new CustomException("XLOpsError Unknown error occured inside " + new Object()
			{
			}.getClass().getEnclosingMethod().getName());
		}

		if (!success)
		{
			Logging.log.info("XLOpsError could not find  data row specified by File Name : " + fileName + " Sheet : " + sheetName + " Test Name : " + testName + " Data ID : " + dataID);
			throw new CustomException("XLOpsError could not find  data row specified by File Name : " + fileName + " Sheet : " + sheetName + " Test Name : " + testName + " Data ID : " + dataID);
		}
		Logging.log.info("XLOps found intended data row identified by " + testName + "." + dataID);
		return dataRow;
	}

	private synchronized Row getHeader(String fileName, String sheetName) throws CustomException
	{
		this.setXLParamFileAndSheet(fileName, sheetName);
		try
		{
			header = null;
			file = new File("src/main/resources/properties/" + fileName);
			fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);

			sheet = workbook.getSheet(sheetName);
			if (sheet == null)
			{
				Logging.log.info("XLOpsError Could not find the Sheet for the file " + sheetName);
				throw new CustomException("XLOpsError Could not find the Sheet for the file " + sheetName);
			}

			rowIterator = sheet.iterator();
			if (!rowIterator.hasNext())
			{
				Logging.log.info("XLOpsError Header/Data is blank for" + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
				throw new CustomException("XLOpsError Header/Data is blank for" + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
			}

			header = sheet.iterator().next();
			Logging.log.info("Header found for : " + fileName + "." + sheetName);
		}
		catch (FileNotFoundException e)
		{
			Logging.log.info("XLOpsError FileNotFoundException Custom Exception in " + new Object()
			{
			}.getClass().getEnclosingMethod().getName() + " File : " + System.getProperty("user.dir") + "//TestData//" + fileName + " not found");
			throw new CustomException("XLOpsError FileNotFoundException Custom Exception in " + new Object()
			{
			}.getClass().getEnclosingMethod().getName() + " File : " + System.getProperty("user.dir") + "//TestData//" + fileName + " not found");
		}
		catch (IOException e)
		{
			Logging.log.info("XLOpsError Sheet not found " + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
			throw new CustomException("XLOpsError Sheet not found " + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
		}
		catch (CustomException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			Logging.log.info("XLOpsError Unknown error occured inside " + new Object()
			{
			}.getClass().getEnclosingMethod().getName() + MyBaseTest.getStaktrace(e));
			throw new CustomException("XLOpsError Unknown error occured inside " + new Object()
			{
			}.getClass().getEnclosingMethod().getName());
		}
		Logging.log.info("XLOps found intended XL data Header identified by " + fileName + "." + sheetName);
		return header;
	}

	private synchronized Row getHeader() throws CustomException
	{
		try
		{
			header = null;
			file = new File("src/main/resources/properties/" + fileName);
			fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);

			sheet = workbook.getSheet(sheetName);
			if (sheet == null)
			{
				Logging.log.info("XLOpsError Could not find the Sheet for the file " + sheetName);
				throw new CustomException("XLOpsError Could not find the Sheet for the file " + sheetName);
			}

			rowIterator = sheet.iterator();
			if (!rowIterator.hasNext())
			{
				Logging.log.info("XLOpsError Header/Data is blank for" + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
				throw new CustomException("XLOpsError Header/Data is blank for" + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
			}

			header = sheet.iterator().next();

		}
		catch (FileNotFoundException e)
		{
			Logging.log.info("XLOpsError FileNotFoundException Custom Exception in " + new Object()
			{
			}.getClass().getEnclosingMethod().getName() + " File : " + System.getProperty("user.dir") + "//TestData//" + fileName + " not found");
			throw new CustomException("XLOpsError FileNotFoundException Custom Exception in " + new Object()
			{
			}.getClass().getEnclosingMethod().getName() + " File : " + System.getProperty("user.dir") + "//TestData//" + fileName + " not found");
		}
		catch (IOException e)
		{
			Logging.log.info("XLOpsError Sheet not found " + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
			throw new CustomException("XLOpsError Sheet not found " + System.getProperty("user.dir") + "//TestData//" + fileName + "." + sheetName);
		}
		catch (CustomException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			Logging.log.info("XLOpsError Unknown error occured inside " + new Object()
			{
			}.getClass().getEnclosingMethod().getName() + MyBaseTest.getStaktrace(e));

			throw new CustomException("XLOpsError Unknown error occured inside " + new Object()
			{
			}.getClass().getEnclosingMethod().getName());
		}
		Logging.log.info("XLOps found intended XL data Header identified by " + fileName + "." + sheetName);
		return header;
	}

	private synchronized int getColIndex(Row header, String column) throws CustomException
	{
		int index = 0;
		boolean success = false;

		try
		{
			cellIterator = header.cellIterator();
			while (cellIterator.hasNext())
			{
				cell = cellIterator.next();
				if (cell.getStringCellValue().equals(column))
				{
					success = true;
					break;
				}
				index++;
			}
			if (success == false)
			{
				Logging.log.info("XLOpsError Could not find column " + column);
				throw new CustomException("XLOpsError Could not find column " + column);
			}
		}
		catch (CustomException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			Logging.log.info("XLOpsError Unknown error occurred - Could not get column index - failed inside method " + new Object()
			{
			}.getClass().getEnclosingMethod().getName());
			throw new CustomException("XLOpsError Unknown error occurred - Could not get column index - failed inside method " + new Object()
			{
			}.getClass().getEnclosingMethod().getName());
		}
		Logging.log.info("XLOps found intended index of the data needed for the column " + column + "==>" + index);
		return index;
	}

	public synchronized String getCellValue(Row header, Row dataRow, String column) throws CustomException
	{
		String cellValue = null;
		this.setXLParamHeader(header);
		this.setXLParamDataRow(dataRow);
		this.setXLParamColumn(column);
		//this.setXLParam(header, "header");
		try
		{
			cellValue = dataRow.getCell(this.getColIndex(header, column)).toString();
			checkIfCellValueIsNull(cellValue);
		}
		catch (CustomException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new CustomException("XLOpsError Unknown error occurred - Could not get cellValue - failed inside method " + new Object()
			{
			}.getClass().getEnclosingMethod().getName());
		}
		if (cellValue.toString().toLowerCase().equals("null"))
			return "";
		return cellValue;
	}

	public String getCellValue(String column) throws CustomException
	{
		String cellValue = null;
		this.setXLParamColumn(column);
		this.setXLParamHeader(this.getHeader());
		this.setXLParamDataRow(this.getDataRow());
		try
		{
			cellValue = dataRow.getCell(this.getColIndex(header, column)).toString();
			checkIfCellValueIsNull(cellValue);
		}
		catch (CustomException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new CustomException("XLOpsError Unknown error occurred - Could not get cellValue - failed inside method " + new Object()
			{
			}.getClass().getEnclosingMethod().getName());
		}
		if (cellValue.toString().toLowerCase().equals("null"))
			return "";
		return cellValue;
	}

	public synchronized void checkIfCellValueIsNull(String cellValue) throws CustomException
	{
		try
		{
			if (cellValue.equals(""))
			{
				Logging.log.info("XLOpsError Found data returned NULL cell - File Name : " + fileName + " Sheet : " + sheetName + " Test Name : " + this.testName + " Data ID : " + this.dataID + " Column : " + this.columnName);
				throw new CustomException("XLOpsError Found data returned NULL cell - File Name : " + this.fileName + " Sheet : " + this.sheetName + " Test Name : " + this.testName + " Data ID : " + this.dataID + " Column : " + this.columnName);
			}
			//Logging.log.info("XLOpsError Found Cell Value: " + cellValue + " - File Name : "+ fileName + " Sheet : "+ sheetName + " Test Name : "+ this.testName + " Data ID : "+ this.dataID + " Column : "+ column);
		}
		catch (CustomException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new CustomException("XLOpsError Unknown error occurred - Could not get cellValue - failed inside method " + new Object()
			{
			}.getClass().getEnclosingMethod().getName());
		}
	}

	public synchronized void setXLParamColumn(String column)
	{
		this.columnName = column;
	}

	public synchronized void setXLParam(String testName, String dataID, String column)
	{
		this.testName = testName;
		this.dataID = dataID;
		this.columnName = column;
	}

	public synchronized void setXLParamTestAndDataID(String testName, String dataID)
	{
		this.testName = testName;
		this.dataID = dataID;
	}

	public synchronized void setXLParam(String fileName, String sheetName, String testName, String dataID, String column)
	{
		this.fileName = fileName;
		this.sheetName = sheetName;
		this.testName = testName;
		this.dataID = dataID;
		this.columnName = column;
	}

	public synchronized void setXLParam(String fileName, String sheetName, String testName, String dataID)
	{
		try
		{
			//System.out.println("INNNNN" + fileName +"."+ sheetName +"."+ testName+"."+ dataID);
			this.fileName = fileName;
			this.sheetName = sheetName;
			this.testName = testName;
			this.dataID = dataID;

			//System.out.println(fileName +"."+ sheetName +"."+ testName+"."+ dataID);
		}
		catch (Exception e)
		{
			Logging.log.info(e.getStackTrace().toString());
			//e.printStackTrace();
		}
	}

	public synchronized void setXLParamFileAndSheet(String fileName, String sheetName)
	{
		this.fileName = fileName;
		this.sheetName = sheetName;
	}

	public synchronized void setXLParamSheet(String sheetName)
	{
		this.sheetName = sheetName;
	}

	public synchronized void setXLParamHeader(Row header)
	{
		this.header = header;
	}

	public synchronized void setXLParamDataRow(Row dataRow)
	{
		this.dataRow = dataRow;
	}

	/*
	public static String[] getUserList(String moduleName, String columnTOFetch) throws CustomException
	{
		String elementsTOExecute[] = null;
	
		File file = null;
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		Iterator<Row> rowIterator = null;
	
		Row row;
		file = new File("src/main/resources/properties/" + "login.xlsx");
		int indexOfTestName = 0;
		int counter = 0;
	
		try
		{
			fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(moduleName);
	
			rowIterator = sheet.iterator();
			XLOps xl = new XLOps();
			xl.header = xl.getHeader("login.xlsx", moduleName.toLowerCase());
			indexOfTestName = xl.getColIndex(xl.header, columnTOFetch.toLowerCase());
			//System.out.println(columnTOFetch + " has index " + indexOfTestName);
			rowIterator = sheet.rowIterator();
			int indexOFExecute = xl.getColIndex(xl.header, "isActive");
	
			while (rowIterator.hasNext())
			{
				row = rowIterator.next();
				if (row.getCell(indexOFExecute).toString().toLowerCase().equals("y"))
				{
					counter++;
				}
			}
	
			elementsTOExecute = new String[counter];
	
			counter = 0;
			rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext())
			{
				row = rowIterator.next();
				if (row.getCell(indexOFExecute).toString().toLowerCase().equals("y"))
				{
					//System.out.println(row.getCell(indexOFExecute).toString().toLowerCase());
					elementsTOExecute[counter] = row.getCell(indexOfTestName, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
					counter++;
				}
			}
		}
		catch (CustomException e)
		{
			e.printStackTrace();
			Logging.log.info(MyBaseTest.getStaktrace(e));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			Logging.log.info(MyBaseTest.getStaktrace(e));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Logging.log.info(MyBaseTest.getStaktrace(e));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Logging.log.info(MyBaseTest.getStaktrace(e));
			throw new CustomException("Error inside " + new Object()
			{
			}.getClass().getEnclosingMethod().getName());
		}
		//returns an str[] with modules/tests to execute
		if (elementsTOExecute == null)
		{
			//System.out.println("is null");
		}
		//System.out.println("ele to exe length "+ columnTOFetch+ " " + elementsTOExecute.length);
		return elementsTOExecute;
	}
	*/
	public static String[] getElementsTOExecute(String moduleName, String columnTOFetch) throws CustomException
	{
		String elementsTOExecute[] = null;

		File file = null;
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		Iterator<Row> rowIterator = null;

		Row row;
		file = new File("src/main/resources/properties/" + "main.xlsx");
		int indexOfTestName = 0;
		int counter = 0;

		try
		{
			fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(moduleName);

			rowIterator = sheet.iterator();
			XLOps xl = new XLOps();
			xl.header = xl.getHeader("main.xlsx", moduleName.toLowerCase());
			indexOfTestName = xl.getColIndex(xl.header, columnTOFetch.toLowerCase());
			//System.out.println(columnTOFetch + " has index " + indexOfTestName);
			rowIterator = sheet.rowIterator();
			int indexOFExecute = xl.getColIndex(xl.header, "execute?");

			while (rowIterator.hasNext())
			{
				row = rowIterator.next();
				if (row.getCell(indexOFExecute).toString().toLowerCase().equals("y"))
				{
					counter++;
				}
			}

			elementsTOExecute = new String[counter];

			counter = 0;
			rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext())
			{
				row = rowIterator.next();
				if (row.getCell(indexOFExecute).toString().toLowerCase().equals("y"))
				{
					//System.out.println(row.getCell(indexOFExecute).toString().toLowerCase());
					elementsTOExecute[counter] = row.getCell(indexOfTestName, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
					counter++;
				}
			}
		}
		catch (CustomException e)
		{
			e.printStackTrace();
			Logging.log.info(MyBaseTest.getStaktrace(e));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			Logging.log.info(MyBaseTest.getStaktrace(e));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Logging.log.info(MyBaseTest.getStaktrace(e));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Logging.log.info(MyBaseTest.getStaktrace(e));
			throw new CustomException("Error inside " + new Object()
			{
			}.getClass().getEnclosingMethod().getName());
		}
		//returns an str[] with modules/tests to execute
		if (elementsTOExecute == null)
		{
			//System.out.println("is null");
		}
		//System.out.println("ele to exe length "+ columnTOFetch+ " " + elementsTOExecute.length);
		return elementsTOExecute;
	}

	public Map<String, String> getTestCaseIdAndResultMap() throws Exception
	{
		Map<String, String> testCaseIdResultMap = new LinkedHashMap<>();

		try
		{

			File csvFile = new File(System.getProperty("user.dir") + "//results//results.csv");
			BufferedReader br = new BufferedReader(new FileReader(csvFile));
			String line = "";
			int columnNoOfTestResult = 0;
			int columnNoOfTestCaseId = 0;
			line = br.readLine();

			String[] arr = line.split(",");

			for (int i = 0; i < arr.length; i++)
			{
				if (arr[i].equalsIgnoreCase("Result"))
				{
					columnNoOfTestResult = i;
				}
				if (arr[i].equalsIgnoreCase("TestCaseId"))
				{
					columnNoOfTestCaseId = i;
				}
			}

			while ((line = br.readLine()) != null)
			{
				arr = line.split(",");
				testCaseIdResultMap.put(arr[columnNoOfTestCaseId], arr[columnNoOfTestResult]);
			}

			//System.out.println(testCaseIdResultMap);

			br.close();
		}
		catch (Exception e)
		{
			throw e;
		}

		return testCaseIdResultMap;

	}

}
