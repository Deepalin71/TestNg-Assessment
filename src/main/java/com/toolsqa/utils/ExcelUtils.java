package com.toolsqa.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtils {
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	private HSSFRow row = null;
	private HSSFCell cell = null;

	public ExcelUtils(String path, int sheetnum) {

		try {
			fis = new FileInputStream(path);
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheetAt(sheetnum);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCellData(String colName, int rowNum) {

		int col_num = -1;
		row = sheet.getRow(0);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			// System.out.println(row.getCell(i).getStringCellValue().trim());
			if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
				col_num = i;
		}
		if (col_num == -1)
			return "";

		try {

			cell = sheet.getRow(rowNum).getCell(col_num);

			String CellData = cell.getStringCellValue();

			return CellData;

		} catch (Exception e) {

			return "";

		}

	}
	
	public ArrayList<Object[]> getFormData(){
		ArrayList<Object[]> myData=new ArrayList<Object[]>();
		
		for(int rowNum=1;rowNum<=sheet.getLastRowNum();rowNum++) {
			String firstName=getCellData("First Name",rowNum);
			String lastName=getCellData("Last Name",rowNum);
			String emailId=getCellData("Email Id",rowNum);
			String gender=getCellData("Gender",rowNum);
			String mobileNum=getCellData("Mobile Num",rowNum);
			String dob=getCellData("Date of Birth",rowNum);
			String sub=getCellData("Subjects",rowNum);
			String hobbies=getCellData("Hobbies",rowNum);
			String picture=getCellData("Picture",rowNum);
			String address=getCellData("Address",rowNum);
			
			Object ob[]= {firstName,lastName,emailId,gender,mobileNum,dob,sub,hobbies,picture,address};
			myData.add(ob);			
			
			
		}
		return myData;
		
		
		
	}

}
