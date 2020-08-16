package com.toolsqa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.toolsqa.base.TestBase;
import com.toolsqa.utils.TestUtils;

public class PracticeFormPage extends TestBase {

	TestUtils utils = new TestUtils();

	// Page Objects
	@FindBy(xpath = "//img[@src='/images/Toolsqa.jpg']")
	private WebElement toolsqaLogo;

	@FindBy(id = "firstName")
	private WebElement firstNameTextBox;

	@FindBy(id = "lastName")
	private WebElement lastNameTextBox;

	@FindBy(id = "userEmail")
	private WebElement emailId;

	@FindBy(xpath = "//div[@id='genterWrapper']//label[@class='custom-control-label']")
	private List<WebElement> listOfGender;

	@FindBy(id = "userNumber")
	private WebElement mobileNo;

	@FindBy(id = "dateOfBirthInput")
	private WebElement dob;

	@FindBy(id = "subjectsInput")
	private WebElement subject;

	@FindBy(xpath = "//div[contains(@class,'subjects-auto-complete__menu')]")
	private List<WebElement> subjectDropDown;

	@FindBy(xpath = "//label[text()='Hobbies']//parent::div//following-sibling::div//label")
	private List<WebElement> listOfHobbies;

	@FindBy(id = "uploadPicture")
	private WebElement picture;

	@FindBy(id = "currentAddress")
	private WebElement address;

	@FindBy(id = "submit")
	private WebElement submitBtn;

	@FindBy(xpath = "//div[@class='modal-content']")
	private WebElement submittedForm;

	@FindBy(xpath = "//table[@class='table table-dark table-striped table-bordered table-hover']/tbody/tr/td[2]")
	private List<WebElement> formData;

	@FindBy(id = "closeLargeModal")
	private WebElement closeBtn;

	@FindBy(xpath = "//div[@class='left-pannel']//div[@class='header-text']")
	private List<WebElement> leftPanelMainMenu;

	@FindBy(xpath = "//div[text()='Book Store Application']//following-sibling::div//div[2]")
	private WebElement bookStoreMenu;

	@FindBy(xpath = "//span[text()='Login']")
	private WebElement loginBtn;

	// Initialise Page Objects
	public PracticeFormPage() {
		PageFactory.initElements(driver, this);
	}

	// Actions:
	public String getPracticeFormPageTitle() {
		return driver.getTitle();
	}

	public boolean getLogo() {
		return toolsqaLogo.isDisplayed();
	}

	public void enterFirstName(String name) {
		firstNameTextBox.sendKeys(name);

	}

	public void enterLastName(String name) {
		lastNameTextBox.sendKeys(name);

	}

	public void enterEmailId(String email) {
		emailId.sendKeys(email);

	}

	public void selectGender(String gender) {
		for (WebElement gendertype : listOfGender) {
			if (gendertype.getText().equalsIgnoreCase(gender)) {
				gendertype.click();

			}

		}
	}

	public void enterMobileNum(String mobileNum) {
		mobileNo.sendKeys(mobileNum);
	}

	public void enterDOB(String dateOfBirth) {
		if (dateOfBirth != null) {

			Actions action = new Actions(driver);
			action.moveToElement(dob).doubleClick().sendKeys(Keys.DELETE).build().perform();
			action.moveToElement(dob).doubleClick().sendKeys(Keys.DELETE).build().perform();
			action.moveToElement(dob).doubleClick().build().perform();
			action.sendKeys(dateOfBirth).build().perform();

			//dob.sendKeys(dateOfBirth);
		}
	}

	public void enterSubjects(String subjectChoice) {
		String sub[] = subjectChoice.split(",");
		if (sub[0] != null) {
			for (String choice : sub) {
				subject.sendKeys(choice);

				for (int i = 0; i < subjectDropDown.size(); i++) {
					if (subjectDropDown.get(i).getText().equalsIgnoreCase(choice.trim())) {
						subjectDropDown.get(i).click();
						break;
					}
				}
			}
		}

	}

	public void enterHobbies(String hobbiesChoice) {
		String hobby[] = hobbiesChoice.split(",");
		if (hobby.length > 0) {
			for (String choice : hobby) {

				for (int i = 0; i < listOfHobbies.size(); i++) {
					if (listOfHobbies.get(i).getText().equalsIgnoreCase(choice.trim())) {
						listOfHobbies.get(i).click();
						break;
					}
				}
			}
		}

	}

	public void uploadPicture(String imgPath) {
		picture.sendKeys(utils.getAbsoluteFilePath(imgPath));
	}

	public void enterAddress(String currentAdd) {
		address.sendKeys(currentAdd);
	}

	public void clickSubmitBtn() {
		submitBtn.submit();
	}

	public boolean whetherFormSubmitOrNot() {
		return submittedForm.isDisplayed();
	}

	public String[] getFormData() {
		String actual_data[] = new String[10];
		int i = 0;
		for (WebElement data : formData) {
			actual_data[i] = data.getText();
			i++;
		}
		return actual_data;
	}

	public void clickCloseBtn() {
		closeBtn.click();
	}

	public String[] getleftPanelMainMenuText() {
		String actualMainMenu[] = new String[leftPanelMainMenu.size()];
		int i = 0;
		for (WebElement element : leftPanelMainMenu) {
			actualMainMenu[i] = element.getText();
			i++;
		}
		return actualMainMenu;
	}

	public String[] getSubMenu(int i) {

		utils.clickThroughJs(driver,
				driver.findElement(By.xpath("//div[@class='element-group'][" + i + "]//div[@class='icon']")));

		List<WebElement> subMenu = driver
				.findElements(By.xpath("//div[@class='element-group'][" + i + "]//ul[@class='menu-list']//span"));
		utils.waitElementUntilVisible(driver, subMenu);

		String actualSubMenu[] = new String[subMenu.size()];
		int j = 0;
		for (WebElement element : subMenu) {
			actualSubMenu[j] = element.getText();
			j++;
		}
		return actualSubMenu;
	}

	public LoginPage clickOnLogin() {
		utils.clickThroughJs(driver, bookStoreMenu);
		utils.clickThroughJs(driver, loginBtn);

		return new LoginPage();
	}

}
