package com.juaracoding.pcmspringboot4.util;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

public class ExcelReader {
	
	private XSSFWorkbook wBook ;
	private XSSFSheet sheet ;	
	private String values ;
	private DataFormatter dFormatter ;
	private int intRowCount;
	private int intColCount;
	private String[][] strAllData;
	private String[][] arrWithoutHeader;
	private int loopRows;
	private FileInputStream excelFile;
	private BufferedInputStream inputBuff;
	private Map<String,String> map = new HashMap<>();
	public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	private List<Map<String,String>> list = new ArrayList<>();

	public static boolean hasWorkBookFormat(MultipartFile file) {
//		System.out.println("Mime Type Excel : "+file.getContentType());
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	/** kalau mau coba file langsung dari controller */
	public ExcelReader(InputStream inputStream, String sheetName) {
		try {
			inputBuff = new BufferedInputStream(inputStream);
			setDataFromExcel(inputBuff,sheetName);
		} catch (Exception e) {
//			LoggingFile.logException("ExcelReader","ExcelReader(InputStream inputStream, String sheetName) --> Line 46",e, OtherConfig.getEnableLog());
		}
		finally {
			try {
				inputBuff.close();
				wBook.close();
			} catch (IOException e) {
//				LoggingFile.logException("ExcelReader","ExcelReader(InputStream inputStream, String sheetName) --> Line 53",e, OtherConfig.getEnableLog());
			}
		}
	}

	/** kalau mau coba langsung dari file di active directory */
	public ExcelReader(String excelPath, String sheetName) {
		try {
			excelFile = new FileInputStream(new File(excelPath));
			inputBuff = new BufferedInputStream(excelFile);
			setDataFromExcel(inputBuff,sheetName);
		} catch (Exception e) {
//			LoggingFile.logException("ExcelReader","ExcelReader(String excelPath, String sheetName) --> Line 64",e, OtherConfig.getEnableLog());
		}
		finally {
			try {
				excelFile.close();
				inputBuff.close();
				wBook.close();
			} catch (IOException e) {
//				LoggingFile.logException("ExcelReader","ExcelReader(String excelPath, String sheetName) --> Line 72",e, OtherConfig.getEnableLog());
			}
		}
	}

	public void setDataFromExcel(BufferedInputStream inputBuff,String sheetName) throws IOException {
		wBook = new XSSFWorkbook(inputBuff);/**/
		sheet = wBook.getSheet(sheetName);
		getRowCount();/*Must Generated first*/
		getColCount();/*Must Generated first*/
		setData();/*SET ALL DATA*/
		setDataToMap();
	}
	
	/*
	 * if you want to handle manual the loops of all data in another method , you can use this method
	 */
	public Iterator<Row> getIter()
	{
		Iterator<Row> r = sheet.iterator();
		return r;
	}
	
	
	public void setData()
	{
		try
		{
			strAllData = new String[intRowCount][intColCount];		
			arrWithoutHeader = new String[intRowCount-1][intColCount];/*BECAUSE OF remove a Header so Row for this object must be minus 1 */
			loopRows =0;
			Iterator<Row> rX = sheet.iterator();
			
			while(rX.hasNext())
			{
				rX.next();//ini wajib ada sebagai penggeser cursor row di file excel
				for(int j=0;j<intColCount;j++)
				{
					if(loopRows!=0)
					{
						/*BECAUSE OF remove a Header so Row for this object must be minus 1 */
						arrWithoutHeader [loopRows-1][j] = getCellData(loopRows,j).toString();
					}
					strAllData[loopRows][j] = getCellData(loopRows,j).toString();
				}
				loopRows++;
			}
		}catch(Exception e)
		{
		}
		this.strAllData = strAllData;
		this.arrWithoutHeader = arrWithoutHeader;		
	}

	public void setDataToMap(){
		Iterator<Row> rX = sheet.iterator();
		String [] columnNameArr = new String[intColCount];
		loopRows =0;
		while(rX.hasNext()){
			rX.next();//ini wajib ada sebagai penggeser cursor row di file excel
			for (int i = 0; i < intColCount; i++) {
					switch (loopRows){
						case 0 : columnNameArr[i] = getCellData(loopRows,i).toString();break;
						default: map.put(columnNameArr[i],getCellData(loopRows,i).toString());
					}
			}
			if(loopRows!=0){
				list.add(map);
				map = new HashMap<>();
			}
			loopRows++;
		}
	}

	public List<Map<String, String>> getDataMap() {
		return list;
	}

	/*
	 * if you want to get all data and proceed it from two dimension String array object, using this method
	 */
	public String[][] getAllData()
	{
		return strAllData;
	}
	
	public String[][] getDataWithoutHeader()
	{
		return arrWithoutHeader;
	}
	
	/*GET SPECIFIC DATA USING ROW NUMBER AND COLUMN NUMBER*/
	public Object getCellData(int rowNum, int colNum)
	{
		dFormatter = new DataFormatter();
		values = dFormatter.formatCellValue(sheet.getRow(rowNum).getCell(colNum));

		return values;
	}
	
	public int getRowCount()
	{
		intRowCount = sheet.getPhysicalNumberOfRows();
		return intRowCount;
	}
	
	public int getColCount()
	{		
		intColCount = sheet.getRow(0).getPhysicalNumberOfCells();

		return intColCount;
	}
}