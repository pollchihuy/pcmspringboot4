package com.juaracoding.pcmspringboot4.util;

import com.juaracoding.pcmspringboot4.model.KategoriProduk;
import com.juaracoding.pcmspringboot4.model.Produk;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

public class UploadExcel {

	private XSSFWorkbook wBook ;
	private XSSFSheet sheet ;
	private String values ;
	private DataFormatter dFormatter ;
	private int intRowCount;
	private int intColCount;
	private int loopRows;
	private BufferedInputStream inputBuff;
	private Map<String,String> map = new HashMap<>();
	public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	private List<Map<String,String>> list = new ArrayList<>();

	public static boolean hasWorkBookFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public List<KategoriProduk> dataKategoriProduk(InputStream inputStream, String sheetName,Long userId) {
		List<KategoriProduk> list = new ArrayList<>();
		try {
			inputBuff = new BufferedInputStream(inputStream);
			wBook = new XSSFWorkbook(inputBuff);/**/
			sheet = wBook.getSheet(sheetName);
			getRowCount();/*Must Generated first*/
			getColCount();/*Must Generated first*/
			loopRows =0;
			Iterator<Row> rX = sheet.iterator();

			while(rX.hasNext())
			{
				rX.next();//ini wajib ada sebagai penggeser cursor row di file excel

				KategoriProduk k = new KategoriProduk();
				if(loopRows!=0)//memastikan yang diinput bukanlah nama kolom di file excel nya
				{
					/*BECAUSE OF remove a Header so Row for this object must be minus 1 */
					k.setNama(getCellData(loopRows,0).toString());
					k.setDeskripsi(getCellData(loopRows,1).toString());
					k.setNotes(getCellData(loopRows,2).toString());
					k.setCreatedBy(userId);
					list.add(k);
				}
				loopRows++;
			}
		} catch (Exception e) {
			LoggingFile.logException("ExcelReader","ExcelReader(InputStream inputStream, String sheetName) Exception ",e);
		}
		finally {
			try {
				inputBuff.close();
				wBook.close();
			} catch (IOException e) {
				LoggingFile.logException("ExcelReader","ExcelReader(InputStream inputStream, String sheetName) IOException",e);
			}
		}
		return list;
	}
	public List<Produk> dataProduk(InputStream inputStream, String sheetName,Long userId) {
		List<Produk> list = new ArrayList<>();
		try {
			inputBuff = new BufferedInputStream(inputStream);
			wBook = new XSSFWorkbook(inputBuff);/**/
			sheet = wBook.getSheet(sheetName);
			getRowCount();/*Must Generated first*/
			getColCount();/*Must Generated first*/
			loopRows =0;
			Iterator<Row> rX = sheet.iterator();

			while(rX.hasNext())
			{
				rX.next();//ini wajib ada sebagai penggeser cursor row di file excel

				Produk p = new Produk();
				if(loopRows!=0)//memastikan yang diinput bukanlah nama kolom di file excel nya
				{
					/*BECAUSE OF remove a Header so Row for this object must be minus 1 */
					p.setNama(getCellData(loopRows,0).toString());
					p.setDeskripsi(getCellData(loopRows,1).toString());
					p.setMerk(getCellData(loopRows,2).toString());
					p.setModel(getCellData(loopRows,3).toString());
					p.setStok(Integer.parseInt(getCellData(loopRows,4).toString()));
					KategoriProduk kp = new KategoriProduk();
					kp.setId(Long.parseLong(getCellData(loopRows,2).toString()));
					p.setKategoriProduk(kp);
					list.add(p);
				}
				loopRows++;
			}
		} catch (Exception e) {
			LoggingFile.logException("ExcelReader","ExcelReader(InputStream inputStream, String sheetName) Exception ",e);
		}
		finally {
			try {
				inputBuff.close();
				wBook.close();
			} catch (IOException e) {
				LoggingFile.logException("ExcelReader","ExcelReader(InputStream inputStream, String sheetName) IOException",e);
			}
		}
		return list;
	}

	/*
	 * if you want to handle manual the loops of all data in another method , you can use this method
	 */
	public Iterator<Row> getIter()
	{
		Iterator<Row> r = sheet.iterator();
		return r;
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