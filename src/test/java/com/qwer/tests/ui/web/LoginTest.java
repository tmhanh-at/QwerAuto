package com.qwer.tests.ui.web;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qwer.base.BaseTest;
import com.qwer.pages.web.HomePage;
import com.qwer.pages.web.LoginPage;

public class LoginTest extends BaseTest{
	
	private String expectedMessWrongPass = "Email or password is incorrect.";
	private String expectedMessInvalidEmail = "Invalid Email.";
	private String expectedMessRequired = "Required";
	private String expectedMessVerified = "Your account has not been verified yet, please verify before logging in.";	
	private String expectedMessResendLink = "We just resent. Please check your email!";
	private String expectedMessClickMultible = "Failed with too many attempts. Try again after 1 minutes.";
	private String expectedMessClickMultibleMoreThan5 = "Failed with too many login attempts. Try again after 5 minutes.";
	
	private LoginPage loginPage;	
	
	@BeforeMethod
    public void initPage() {
        loginPage = new LoginPage(); // ✅ nhận driver từ BaseTest
       
    }
		
	@Test (priority = 1)
	public void loginFailWithInvalidInfo() {	
		loginPage.clickLogin();
		loginPage.login("", "");
		
		Assert.assertEquals(loginPage.getErrMessInvalidEmail(), expectedMessRequired);
		Assert.assertEquals(loginPage.getErrMessInvalidPassword(), expectedMessRequired);
	}
	
	@Test (priority = 2)
	public void loginFailWithWrongPass() {		
		loginPage.login("tmhanh@gmail.com", "123456a@Aa");	
		
		Assert.assertEquals(loginPage.getToastMessage(), expectedMessWrongPass);
	}
	
	@Test (priority = 3)
	public void loginFailWithInvalidEmail() {		
		loginPage.pause(5);
		loginPage.login("12345", "123456a@A");
		
		Assert.assertEquals(loginPage.getErrMessInvalidEmail(), expectedMessInvalidEmail);
	}	
	
	@Test (priority = 4)
	public void loginFailNotVerifyEmailYet() {
		//send api register account -> login -> verify popup show and message on popup
		loginPage.pause(10);
		loginPage.login("nttien@gmail.com", "123456a@A");
		
		Assert.assertTrue(loginPage.isDisplayPopupVeriry());
		Assert.assertTrue(loginPage.isResendLinkDisplyed());
		Assert.assertEquals(loginPage.getMessVerifyAccount() ,expectedMessVerified);	
		
		loginPage.clickResendLink();
		Assert.assertEquals(loginPage.getToastMessage(), expectedMessResendLink);
	}
	
	@Test (priority = 4)
	public void loginFailPolicy() {
		//send api dang ky tk -> login -> verify popup show and message on popup
		 
//		loginPage.pause(10);
		loginPage.login("nttien@gmail.com", "123456a@A");				
		loginPage.clickResendLink();
		loginPage.isToastMessNotDisplayed();
		
		loginPage.login("nttien@gmail.com", "123456a@A");				
		loginPage.clickResendLink();
		Assert.assertTrue(loginPage.getToastMessage().contains(expectedMessClickMultible) 
				|| loginPage.getToastMessage().contains(expectedMessClickMultibleMoreThan5));
		
	}
	
	@Test (priority = 6)
	public void loginSuccess() {				
		loginPage.login("tmhanh@gmail.com", "123456a@A");
		HomePage homePage = new HomePage();
		Assert.assertTrue(homePage.isDisplayNameProfile());	
	}
	
}
