package com.toolsqa.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.toolsqa.config.Constants;
import com.toolsqa.utils.TestUtils;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	InputStream inputStream;
	TestUtils utils=new TestUtils();
	int i=0;

	/**
	 * Read CONFIG file
	 * 
	 * @param key
	 * @return
	 */
	public TestBase() {
		try {

			prop = new Properties();
			String config = "com/toolsqa/config/CONFIG.properties";
			inputStream=getClass().getClassLoader().getResourceAsStream(config);

			prop.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void initialization(String browserName) {

		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver");
			driver = new ChromeDriver();

		} else if (browserName.equalsIgnoreCase("FF") || browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "Drivers/geckodriver");
			driver = new FirefoxDriver();
		} else {
			System.out.println("Unsupported Browser");
			System.exit(1);

		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);

		driver.get(prop.getProperty("Url"));
	}

	public void failed(String testMethodName) {
		File fc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destination = new File(utils.getAbsoluteFilePath("test-output/screenshots/"+testMethodName+"_"+i+".png"));
		try {
			FileUtils.copyFile(fc, destination);
		} catch (IOException e) {
			e.getMessage();
		}
		i++;

	}
	
	public  void closeDriver(WebDriver driver) {

		if (driver != null) {

			driver.close();

		}
	}

}
