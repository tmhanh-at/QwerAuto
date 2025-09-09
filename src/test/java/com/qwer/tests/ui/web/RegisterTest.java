package com.qwer.tests.ui.web;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qwer.base.BaseTest;
import com.qwer.pages.web.RegisterPage;


public class RegisterTest extends BaseTest {
	private String errMessRequired = "Required";	
	private String errMessNotConfirmCb = "You need to accept the terms above.";
	private String errMessInvalidEmail = "Invalid Email.";
	private String errMessUsedEmail = "The email has already been taken.";
	private String errMessInvalidMinLengthUsername = "Username must be at least 2 characters.";
	private String errMessInvalidMaxLengthUsername = "Username must be less than 30 characters.";
	
	private RegisterPage registerPage;
	
	@BeforeMethod
    public void initPage() {
		registerPage = new RegisterPage();     
    }
	
	@Test (priority = 1)
	public void validateInputWithEmpty() {	
		registerPage.clickRegiter();
		registerPage.register("", "", "");	
		registerPage.clickRegisterX();
		
		Assert.assertEquals(registerPage.getErrorMessValidateUsername(), errMessRequired);
		Assert.assertEquals(registerPage.getErrorMessValidateEmail(), errMessRequired);		
		Assert.assertEquals(registerPage.getErrorMessValidateConditions(), errMessNotConfirmCb);
		
		
		registerPage.clickCheckboxConfirm();
		registerPage.scrollToElement();
//		registerPage.clickRegisterX();
		// strColor: text-red/text-primary
		// key: One numeric/8-32 characters/One lower & upper case letter
//		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-red", "One numeric"));
//		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-red", "8-32 characters"));
//		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-red", "One lower & upper case letter"));
	}
	
	@Test (priority = 2)
	public void validateEmail() {
		registerPage.clickRegiter();
		registerPage.register("Username", "xxxxx", "123456a@A");
		Assert.assertEquals(registerPage.getErrorMessValidateEmail(), errMessInvalidEmail);
		
		registerPage.register("Username", "john.doe@.net", "123456a@A");
		Assert.assertEquals(registerPage.getErrorMessValidateEmail(), errMessInvalidEmail);
		
		registerPage.register("Username", "@domainsample.com", "123456a@A");
		Assert.assertEquals(registerPage.getErrorMessValidateEmail(), errMessInvalidEmail);
		
		registerPage.register("Username", " john.doe43@domainsample", "123456a@A");
		Assert.assertEquals(registerPage.getErrorMessValidateEmail(), errMessInvalidEmail);
		
		registerPage.register("Username", "abc@abc@gmail.com", "123456a@A");
		Assert.assertEquals(registerPage.getErrorMessValidateEmail(), errMessInvalidEmail);	
		
		registerPage.register("Username", "tmhanh@gmail.com", "123456a@A");		
		registerPage.clickCheckboxConfirm();
		registerPage.clickRegisterX();
		Assert.assertEquals(registerPage.getToastMessage(), errMessUsedEmail);
	}
	
	@Test (priority = 3)
	public void validatePassword() {
		registerPage.clickRegiter();
		
		registerPage.register("U", "userA@gmail.com", "123456A@a");
		Assert.assertEquals(registerPage.getErrorMessValidateUsername(), errMessInvalidMinLengthUsername);
		
		registerPage.register("123456a@Aaaaaaaaaaaaaaaaaaaaaaa22", "userA@gmail.com", "123456A@a");
		Assert.assertEquals(registerPage.getErrorMessValidateUsername(), errMessInvalidMaxLengthUsername);
		
		registerPage.register("Username", "userA@gmail.com", "123456");
		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-red", "8-32 characters"));
		
		registerPage.register("Username", "userA@gmail.com", "123456a@Aaaaaaaaaaaaaaaaaaaaaaa22");
		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-red", "8-32 characters"));
	}
	
	
	@Test (priority = 3)
	public void registerSuccess() {
		registerPage.clickRegiter();
		
		registerPage.register("NewUser111", "username111@gmail.com", "123456A@a");		
		
		registerPage.clickCheckboxConfirm();
		registerPage.clickRegisterX();
		
		Assert.assertTrue(registerPage.isDisplayedText("Check your email!"));
		Assert.assertTrue(registerPage.isDisplayedText("We have sent you an email to"));
		Assert.assertTrue(registerPage.isDisplayedText("Please verify your account now and start aiming for the moon!"));
	}

}
