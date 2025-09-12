package com.qwer.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;

import com.qwer.contants.Contants;
import com.qwer.driver.DriverFactory;

public class BaseTest {

	protected WebDriver driver;

	@BeforeTest
	public void setUp(@Optional("chrome") String browser) {
		DriverFactory.initDriver(browser);
//	        DriverFactory.getDriver().get(CommonContants.BASE_URL);	     
		driver = DriverFactory.getDriver();
		DriverFactory.getDriver()
				.get("https://" + Contants.USERNAME_AUTH + ":" + Contants.PASSWORD_AUTH + "@qwer.kingshaper.com");
	}

	@AfterTest
	public void tearDown() {
		DriverFactory.quitDriver();
	}
}
