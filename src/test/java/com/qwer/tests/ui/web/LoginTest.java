package com.qwer.tests.ui.web;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qwer.base.BaseTest;
import com.qwer.pages.web.HomePage;

public class LoginTest extends BaseTest{
	
	private String expectedMessWrongPass = "Email or password is incorrect.";
	private String expectedMessInvalidEmail = "Invalid Email.";
	private String expectedMessRequired = "Required";
	private String expectedMessVerified = "Your account has not been verified yet, please verify before logging in.";	
	private String expectedMessResendLink = "We just resent. Please check your email!";
	private String expectedMessClickMultible = "Failed with too many attempts. Try again after 1 minutes.";
	
	@Test (priority = 1)
	public void loginFailWithInvalidInfo() {		
		loginPage.login("", "");
		
		Assert.assertEquals(loginPage.getErrMessInvalidEmail(), expectedMessRequired);
		Assert.assertEquals(loginPage.getErrMessInvalidPassword(), expectedMessRequired);
	}
	
	@Test (priority = 2)
	public void loginFailWithWrongPass() {		
		loginPage.login("tmhanh@gmail.com", "123456a@Aa");	
		
		Assert.assertEquals(loginPage.getToatMessage(), expectedMessWrongPass);
	}
	
	@Test (priority = 3)
	public void loginFailWithInvalidEmail() {		
		loginPage.login("12345", "123456a@A");
		
		Assert.assertEquals(loginPage.getErrMessInvalidEmail(), expectedMessInvalidEmail);
	}	
	
	@Test (priority = 4)
	public void loginFailNotVerifyEmailYet() {
		//send api dang ky tk -> login -> verify popup show and message on popup
		loginPage.login("nttien@gmail.com", "123456a@A");
		
		Assert.assertTrue(loginPage.isDisplayPopupVeriry());
		Assert.assertTrue(loginPage.isResendLinkDisplyed());
		Assert.assertEquals(loginPage.getMessVerifyAccount() ,expectedMessVerified);	
		
		loginPage.clickResendLink();
		Assert.assertEquals(loginPage.getToatMessage(), expectedMessResendLink);
	}
	
	@Test (priority = 4)
	public void loginFailPolicy() {
		//send api dang ky tk -> login -> verify popup show and message on popup
		loginPage.login("nttien@gmail.com", "123456a@A");				
		loginPage.clickResendLink();
		loginPage.isToastMessNotDisplayed();
		loginPage.login("nttien@gmail.com", "123456a@A");				
		loginPage.clickResendLink();
		Assert.assertEquals(loginPage.getToatMessage(), expectedMessClickMultible);
	}
	
	@Test (priority = 6)
	public void loginSuccess() {				
		loginPage.login("tmhanh@gmail.com", "123456a@A");
		HomePage homePage = new HomePage();
		Assert.assertTrue(homePage.isDisplayNameProfile());	
	}
	
}
