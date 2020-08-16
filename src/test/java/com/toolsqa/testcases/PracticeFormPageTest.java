package com.toolsqa.testcases;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.toolsqa.config.Constants;
import com.toolsqa.base.TestBase;
import com.toolsqa.pages.LoginPage;
import com.toolsqa.pages.PracticeFormPage;
import com.toolsqa.utils.ExcelUtils;
import com.toolsqa.utils.TestUtils;
import static io.restassured.RestAssured.given;


public class PracticeFormPageTest extends TestBase {

	PracticeFormPage formPage;
	LoginPage loginPage;
	TestUtils utils = new TestUtils();
	SoftAssert softAssert=new SoftAssert();

	public PracticeFormPageTest() {
		super();
	}

	@Parameters("browser")
	@BeforeMethod
	public void setup(String browserName) {
		initialization(browserName);
		formPage = new PracticeFormPage();

	}

	@Test (description = "Verify Practice Form Page Title",priority=1)
	public void validatePracticeFormPageTitle() {
		String title = formPage.getPracticeFormPageTitle();
		Assert.assertEquals(title, prop.getProperty("formPageTitle"), "Practice form page title is not correct");

	}

	@Test (description = "Verify Logo At Practice Form Page",priority=2)
	public void validatePageLogo() {

		Assert.assertTrue(formPage.getLogo(), "Logo not displayed at Practice form page");
	}
	
	@DataProvider
	public Iterator<Object[]> getTestData(){
		ExcelUtils excel=new ExcelUtils(utils.getAbsoluteFilePath("src/main/java/com/toolsqa/testdata/TestData.xls"),0);
		ArrayList<Object[]> testData=excel.getFormData();
		return testData.iterator();
		
	}
	
	@Test(dataProvider="getTestData",description = "Get Test Data And Fill And Verify Form",priority=3)
	public void submitFormAndAssertData(String firstName, String lastName, String emailId, String gender, String mobileNum,
			String dob, String subjects, String hobbies, String imgPath,String address) {
		
		//Fill the form
		formPage.enterFirstName(firstName);
		formPage.enterLastName(lastName);
		formPage.enterEmailId(emailId);
		formPage.selectGender(gender);
		formPage.enterMobileNum(mobileNum);
		formPage.enterDOB(dob);
		formPage.enterSubjects(subjects);
		formPage.enterHobbies(hobbies);
		formPage.uploadPicture(imgPath);
		formPage.clickSubmitBtn();
		
		//Check whether Form submitted or not
		Assert.assertTrue(formPage.whetherFormSubmitOrNot(),"Form doesn't submit");

		String data[]=formPage.getFormData();  
	    
		//Verify submitted form
		softAssert.assertEquals(data[0], firstName+' '+lastName,"Student name doesnt match");
		softAssert.assertEquals(data[1], emailId,"EmailId doesnt match");
		softAssert.assertEquals(data[2], gender,"Gender doesnt match");
		softAssert.assertEquals(data[3], mobileNum,"Mobile number doesnt match");
		softAssert.assertEquals(data[4], utils.dateFormat("dd MMM yyyy","dd MMMM,yyyy", dob),"Date of Birth doesnt match");
		softAssert.assertEquals(data[5], subjects,"Subjects doesnt match");
		softAssert.assertEquals(data[6], hobbies,"Hobbies doesnt match");
		softAssert.assertTrue(imgPath.contains(data[7]),"Image doesnt match");
		
		formPage.clickCloseBtn();
		
		softAssert.assertAll();
		
	}
	
	@Test(description = "Verify Main Menu and Submenu Of Left Panel",priority=4)
	public void verifyLeftPanel() {
		
		//Verify Main Menu
		String expectedMainColumns[] = prop.getProperty("leftMainCol").split(Constants.REGEX_FOR_PIPE);
		String actualMainColumns[]=formPage.getleftPanelMainMenuText();
		Assert.assertEquals(actualMainColumns.length , expectedMainColumns.length,
				"Number of main columns doesnot match");

		for (int i = 0; i < expectedMainColumns.length; i++) {
			softAssert.assertEquals(actualMainColumns[i],expectedMainColumns[i],
					"Name of main columns doesnot match");

		}
		softAssert.assertAll();
		
		//Verify Sub Menu
		for (int i = 0; i < expectedMainColumns.length; i++) {
			
			String expectedSubColumns[] = prop.getProperty(expectedMainColumns[i].split(" |,")[0]).split(Constants.REGEX_FOR_PIPE);
			String actualSubColumns[] =formPage.getSubMenu(i+1);
			
			Assert.assertEquals( actualSubColumns.length,expectedSubColumns.length,
					"Number of sub columns doesn't match for "+expectedMainColumns[i]);
			
			for (int j = 0; j < expectedSubColumns.length; j++) {
				softAssert.assertEquals(actualSubColumns[j],expectedSubColumns[j],
						"Name of sub columns doesnot match for "+expectedMainColumns[i]);

			}
			

		}
		softAssert.assertAll();

	}
	
	@Test(description = "Navigate to Login Page and Verify Its Status Code",priority=5,groups="First")
	public void validateLoginPageStatusCode()  {
		loginPage=formPage.clickOnLogin();
		given().when().get(loginPage.getLoginPageUrl()).then().statusCode(200);
	}
	
	@Test(description = "Negative TestCase for status code",priority=6)
	public void negativevalidationForLoginPageStatusCode()  {
		loginPage=formPage.clickOnLogin();
		given().when().get(loginPage.getLoginPageUrl()).then().statusCode(201);
	}

	@AfterMethod
	public void tearDown() {
		closeDriver(driver);

	}

}
