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
	private String errMessUsedUsername = "The username has already been taken.";
	private String errMessInvalidMinLengthUsername = "Username must be at least 2 characters.";
	private String errMessInvalidMaxLengthUsername = "Username must be less than 30 characters.";

	private RegisterPage registerPage;

	@BeforeMethod
	public void initPage() {
		registerPage = new RegisterPage();
		registerPage.clickButtonRegisterToShowScreenRegister();
	}

	@Test(priority = 2, description = "Incase: validate email field.")
	public void validateEmail() {
		registerPage.setEmail("");
		Assert.assertEquals(registerPage.getErrorMessValidateEmail(), errMessRequired);

		registerPage.setEmail("     ");
		Assert.assertEquals(registerPage.getErrorMessValidateEmail(), errMessRequired);

		registerPage.setEmail("xxxxx");
		registerPage.pause();
		Assert.assertEquals(registerPage.getErrorMessValidateEmail(), errMessInvalidEmail);

		registerPage.setEmail("john.doe@.net");
		registerPage.pause();
		Assert.assertEquals(registerPage.getErrorMessValidateEmail(), errMessInvalidEmail);

		registerPage.setEmail("@domainsample.com");
		registerPage.pause();
		Assert.assertEquals(registerPage.getErrorMessValidateEmail(), errMessInvalidEmail);

		registerPage.setEmail(" john.doe43@domainsample");
		registerPage.pause();
		Assert.assertEquals(registerPage.getErrorMessValidateEmail(), errMessInvalidEmail);

		registerPage.setEmail("abc@abc@gmail.com");
		registerPage.pause();
		Assert.assertEquals(registerPage.getErrorMessValidateEmail(), errMessInvalidEmail);

		registerPage.register("Username", "tmhanh@gmail.com", "123456a@A");
		registerPage.clickCheckboxConfirm();
		registerPage.clickRegisterX();
		Assert.assertEquals(registerPage.getToastMessage(), errMessUsedEmail);
		registerPage.clickIconClose();
	}

	@Test(priority = 1, description = "Incase: validate Username field.")
	public void validateUserName() {
		registerPage.setUsername("");
		Assert.assertEquals(registerPage.getErrorMessValidateUsername(), errMessRequired);

		registerPage.setUsername("    ");
		Assert.assertEquals(registerPage.getErrorMessValidateUsername(), errMessRequired);

		registerPage.setUsername("U");
		registerPage.pause();
		Assert.assertEquals(registerPage.getErrorMessValidateUsername(), errMessInvalidMinLengthUsername);

		registerPage.setUsername("123456a@Aaaaaaaaaaaaaaaaaaaaaaa22");
		registerPage.pause();
		Assert.assertEquals(registerPage.getErrorMessValidateUsername(), errMessInvalidMaxLengthUsername);

		registerPage.register("tmhanh", "userA@gmail.com", "123456A@a"); // exist username in db
		registerPage.clickCheckboxConfirm();
		registerPage.clickRegisterX();
		Assert.assertEquals(registerPage.getToastMessage(), errMessUsedUsername);
		registerPage.clickIconClose();
	}

//	@Test(priority = 3, description = "Incase: validate Password field.")
	public void validatePassword() {
		registerPage.scrollToElement();
		registerPage.setPassword("");

		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-red", "One numeric"));
		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-red", "8-32 characters"));
		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-red", "One lower & upper case letter"));

		registerPage.setPassword("   ");
		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-red", "One numeric"));
		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-red", "8-32 characters"));
		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-red", "One lower & upper case letter"));

		registerPage.setPassword("abcAddddddddd");
		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-red", "One numeric"));
		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-primary", "8-32 characters"));
		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-primary", "One lower & upper case letter"));

		registerPage.setPassword("123a@Ae");
		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-red", "8-32 characters"));

		registerPage.setPassword("123456a@aaaaaaaaaaaaaaaaaaaaaa22");
		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-red", "One lower & upper case letter"));

		registerPage.setPassword("123456a@Aaaaaaaaaaaaaaaaaaaaaaa22");
		Assert.assertTrue(registerPage.isTextDisplayCorrectly("text-red", "8-32 characters"));
		registerPage.clickIconClose();
	}

	@Test(priority = 4, description = "Incase: validate do not click checkbox confirm.")
	public void validateCheckboxConfirm() {
		registerPage.register("NewUser111", "username111@gmail.com", "123456A@a");
		registerPage.clickRegisterX();
		registerPage.pause();
		Assert.assertEquals(registerPage.getErrorMessValidateConditions(), errMessNotConfirmCb);
	}

	@Test(priority = 5, description = "In case: Register successfully!")
	public void registerSuccess() {
		registerPage.clearDataBeforeTest("testqwer@gmail.com");
		registerPage.register("NewUser111", "username111@gmail.com", "123456A@a");

		registerPage.clickCheckboxConfirm();
		registerPage.clickRegisterX();

		Assert.assertTrue(registerPage.isDisplayedText("Check your email!"));
		Assert.assertTrue(registerPage.isDisplayedText("We have sent you an email to"));
		Assert.assertTrue(
				registerPage.isDisplayedText("Please verify your account now and start aiming for the moon!"));

		registerPage.clearDataBeforeTest("username111@gmail.com");
	}

}
