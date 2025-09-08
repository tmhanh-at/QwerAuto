package com.qwer.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;

import com.qwer.driver.DriverFactory;

public class BaseTest {

		protected WebDriver driver;
	
		@BeforeClass
	    public void setUp(@Optional("chrome") String browser) {
	        DriverFactory.initDriver(browser);
//	        DriverFactory.getDriver().get(CommonContants.BASE_URL);	     
	        driver = DriverFactory.getDriver();
	        DriverFactory.getDriver().get("https://crypto_n1:crypto@123@qwer.kingshaper.com");
	    }		 
	    
	    @AfterClass
	    public void tearDown() {
	        DriverFactory.quitDriver();
	    }
}
