package com.qwer.base;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;

import com.qwer.contants.CommonContants;
import com.qwer.driver.DriverFactory;
import com.qwer.pages.web.LoginPage;

public class BaseTest {
	
	 	protected LoginPage loginPage;
	 	
		@BeforeClass
	    public void setUp(@Optional("chrome") String browser) {
	        DriverFactory.initDriver(browser);
//	        DriverFactory.getDriver().get(CommonContants.BASE_URL);	     
	        DriverFactory.getDriver().get("https://crypto_n1:crypto@123@qwer.kingshaper.com");
	        
	        loginPage = new LoginPage();
	        loginPage.clickLoginButton();
	    }		 
	    
	    @AfterClass
	    public void tearDown() {
	        DriverFactory.quitDriver();
	    }
}
