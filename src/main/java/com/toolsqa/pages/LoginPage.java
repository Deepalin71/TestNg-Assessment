package com.toolsqa.pages;


import com.toolsqa.base.TestBase;

public class LoginPage extends TestBase{
	
	//Actions
	public String getLoginPageUrl() {
		return driver.getCurrentUrl();
	}

}
