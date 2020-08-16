package com.toolsqa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.toolsqa.base.TestBase;
import com.toolsqa.pages.LoginPage;
import com.toolsqa.pages.PracticeFormPage;


public class LoginPageTest extends TestBase {
	
	PracticeFormPage formPage;
	LoginPage loginPage;
	
	public LoginPageTest() {
		super();
	}
	
	@Parameters("browser")
	@BeforeMethod
	public void setup(String browserName) {
		initialization(browserName);
		formPage = new PracticeFormPage();
		loginPage=formPage.clickOnLogin();

	}
	
	@Test(description = "Verify Login Page Title",dependsOnGroups = "First")
	public void validateLoginPageTitle() {
		String actualUrl = loginPage.getLoginPageUrl();
		Assert.assertEquals(actualUrl, prop.getProperty("loginPageUrl"), "Login page URL is not correct");

	}
	
	
	@AfterMethod
	public void tearDown() {
		closeDriver(driver);

	}
	

}
