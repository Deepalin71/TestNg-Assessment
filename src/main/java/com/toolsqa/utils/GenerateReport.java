package com.toolsqa.utils;
import java.io.File;

import org.xml.sax.SAXException;

public class GenerateReport {
	public void generateExcelReport(String destFileName) {
		String path=GenerateReport.class.getClassLoader().getResource("./").getPath();
		path=path.replaceAll("bin", "src");
		File xmlFile=new File(path+"../test-output/testng-results.xml");
		System.out.println(xmlFile.isFile());
	}
	
	public static void main(String[] args) {
		new GenerateReport().generateExcelReport("");


	}

}
