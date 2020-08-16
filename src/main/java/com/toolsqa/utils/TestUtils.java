package com.toolsqa.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.toolsqa.config.Constants;


public class TestUtils {
	
	WebDriverWait wait;
	File file;
	JavascriptExecutor executor;

	public void waitElementUntilClickable(WebDriver driver, WebElement element) {

		 wait = new WebDriverWait(driver, Constants.EXPLICIT_WAIT);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void waitElementUntilVisible(WebDriver driver, List<WebElement> subjectDropDown) {

		wait = new WebDriverWait(driver, Constants.EXPLICIT_WAIT);
		wait.until(ExpectedConditions.visibilityOfAllElements(subjectDropDown));
		
	}
	
	public String getAbsoluteFilePath(String relativePath) {
		file = new File(relativePath);
		return file.getAbsolutePath();
	}
	
	public String dateFormat(String original_format,String req_format,String dob) {
		SimpleDateFormat format = new SimpleDateFormat(original_format);
		Date date=null;
		
		try {
			
			date = format.parse(dob);
		} 
		catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		return new SimpleDateFormat(req_format).format(date);

				
	}
	
	public void clickThroughJs(WebDriver driver, WebElement element) {
		executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}
	

}
