package com.qwer.tests.ui.web;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qwer.base.BaseTest;
import com.qwer.pages.web.LoginPage;
import com.qwer.pages.web.SignInPage;

public class SignInTest extends BaseTest {	

	private String errMessRequired = "Required";	
	private String errMessNotConfirmCb = "You need to accept the terms above.";
	private String errMessInvalidEmail = "Invalid Email.";
	private String errMessUsedEmail = "The email has already been taken.";
	private String errMessInvalidMinLengthUsername = "Username must be at least 2 characters.";
	private String errMessInvalidMaxLengthUsername = "Username must be less than 30 characters.";
	
	private SignInPage signInPage;
	
	@BeforeMethod
    public void initPage() {
		signInPage = new SignInPage(); // ✅ nhận driver từ BaseTest       
    }
	
	@Test (priority = 1)
	public void validateInputWithEmpty() {	
		signInPage.clickRegiter();
		signInPage.register("", "", "");	
		signInPage.clickRegisterX();
		
		Assert.assertEquals(signInPage.getErrorMessValidateUsername(), errMessRequired);
		Assert.assertEquals(signInPage.getErrorMessValidateEmail(), errMessRequired);		
		Assert.assertEquals(signInPage.getErrorMessValidateConditions(), errMessNotConfirmCb);
		
		
		signInPage.clickCheckboxConfirm();
		signInPage.scrollToElement();
//		signInPage.clickRegisterX();
		// strColor: text-red/text-primary
		// key: One numeric/8-32 characters/One lower & upper case letter
//		Assert.assertTrue(signInPage.isTextDisplayCorrectly("text-red", "One numeric"));
//		Assert.assertTrue(signInPage.isTextDisplayCorrectly("text-red", "8-32 characters"));
//		Assert.assertTrue(signInPage.isTextDisplayCorrectly("text-red", "One lower & upper case letter"));
	}
	
	@Test (priority = 2)
	public void validateEmail() {
		signInPage.clickRegiter();
		signInPage.register("Username", "xxxxx", "123456a@A");
		Assert.assertEquals(signInPage.getErrorMessValidateEmail(), errMessInvalidEmail);
		
		signInPage.register("Username", "john.doe@.net", "123456a@A");
		Assert.assertEquals(signInPage.getErrorMessValidateEmail(), errMessInvalidEmail);
		
		signInPage.register("Username", "@domainsample.com", "123456a@A");
		Assert.assertEquals(signInPage.getErrorMessValidateEmail(), errMessInvalidEmail);
		
		signInPage.register("Username", " john.doe43@domainsample", "123456a@A");
		Assert.assertEquals(signInPage.getErrorMessValidateEmail(), errMessInvalidEmail);
		
		signInPage.register("Username", "abc@abc@gmail.com", "123456a@A");
		Assert.assertEquals(signInPage.getErrorMessValidateEmail(), errMessInvalidEmail);	
		
		signInPage.register("Username", "tmhanh@gmail.com", "123456a@A");		
		signInPage.clickCheckboxConfirm();
		signInPage.clickRegisterX();
		Assert.assertEquals(signInPage.getToastMessage(), errMessUsedEmail);
	}
	
	@Test (priority = 3)
	public void validatePassword() {
		signInPage.clickRegiter();
		
		signInPage.register("U", "userA@gmail.com", "123456A@a");
		Assert.assertEquals(signInPage.getErrorMessValidateUsername(), errMessInvalidMinLengthUsername);
		
		signInPage.register("123456a@Aaaaaaaaaaaaaaaaaaaaaaa22", "userA@gmail.com", "123456A@a");
		Assert.assertEquals(signInPage.getErrorMessValidateUsername(), errMessInvalidMaxLengthUsername);
		
		signInPage.register("Username", "userA@gmail.com", "123456");
		Assert.assertTrue(signInPage.isTextDisplayCorrectly("text-red", "8-32 characters"));
		
		signInPage.register("Username", "userA@gmail.com", "123456a@Aaaaaaaaaaaaaaaaaaaaaaa22");
		Assert.assertTrue(signInPage.isTextDisplayCorrectly("text-red", "8-32 characters"));
	}
	
	
	@Test (priority = 3)
	public void registerSuccess() {
		signInPage.clickRegiter();
		
		signInPage.register("NewUser111", "username111@gmail.com", "123456A@a");		
		
		signInPage.clickCheckboxConfirm();
		signInPage.clickRegisterX();
		
		Assert.assertTrue(signInPage.isDisplayedText("Check your email!"));
		Assert.assertTrue(signInPage.isDisplayedText("We have sent you an email to"));
		Assert.assertTrue(signInPage.isDisplayedText("Please verify your account now and start aiming for the moon!"));
	}
	
}
