package com.qwer.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;

import com.qwer.driver.DriverFactory;
import com.qwer.contants.Contants;

public class BaseTest {

		protected WebDriver driver;
	
		@BeforeClass
	    public void setUp(@Optional("chrome") String browser) {
	        DriverFactory.initDriver(browser);
//	        DriverFactory.getDriver().get(CommonContants.BASE_URL);	     
	        driver = DriverFactory.getDriver();
	        DriverFactory.getDriver().get("https://"+ Contants.USERNAME_AUTH + ":"+ Contants.PASSWORD_AUTH +"@qwer.kingshaper.com");
	    }		 
	    
	    @AfterClass
	    public void tearDown() {
	        DriverFactory.quitDriver();
	    }
}
