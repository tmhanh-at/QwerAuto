package com.qwer.tests.ui.web;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qwer.apis.RegisterApi;
import com.qwer.base.BaseTest;
import com.qwer.pages.web.HomePage;
import com.qwer.pages.web.LoginPage;
import com.qwer.pages.web.RegisterPage;

public class LoginTest extends BaseTest {

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
		loginPage = new LoginPage();
		loginPage.clickLoginToOpenLoginScreen();
	}

	@Test(priority = 1, description = "Incase: input invalid value to email.")
	public void validateEmailInput() {
		loginPage.login("", "");
		loginPage.pause();
		Assert.assertEquals(loginPage.getErrMessInvalidEmail(), expectedMessRequired);

		loginPage.login("    ", "");
		loginPage.pause();
		Assert.assertEquals(loginPage.getErrMessInvalidEmail(), expectedMessRequired);

		loginPage.login("test@", "");
		loginPage.pause();
		Assert.assertEquals(loginPage.getErrMessInvalidEmail(), expectedMessInvalidEmail);

		loginPage.login("test@commmmmmm", "");
		loginPage.pause();
		Assert.assertEquals(loginPage.getErrMessInvalidEmail(), expectedMessInvalidEmail);
	}

	@Test(priority = 2, description = "Incase: input invalid value to password.")
	public void validatePasswordInput() {
		loginPage.login("tesst@gmail.com", "");
		loginPage.pause();
		Assert.assertEquals(loginPage.getErrMessInvalidPassword(), expectedMessRequired);

		loginPage.login("tesst@gmail.com", "      ");
		loginPage.pause();
		Assert.assertEquals(loginPage.getErrMessInvalidPassword(), expectedMessRequired);
	}

	@Test(priority = 3, description = "Incase: Login with wrong password")
	public void loginFailWithWrongPass() {
		loginPage.login("tmhanh@gmail.com", "123456a@Aa");

		Assert.assertEquals(loginPage.getToastMessage(), expectedMessWrongPass);
	}

	@Test(priority = 4, description = "Incase: Login with unregistered email")
	public void loginFailWithUnregisteredEmail() {
		loginPage.login("test@gmail.com", "123456a@A");

		Assert.assertEquals(loginPage.getToastMessage(), expectedMessWrongPass);
	}

	@Test(priority = 5, description = "Incase: Login with unverified email")
	public void loginFailNotVerifyEmailYet() {
		RegisterPage registerPage = new RegisterPage();
		registerPage.clearDataBeforeTest("testqwerx10@gmail.com");

		RegisterApi registerApi = new RegisterApi();
		registerApi.registerUserByApi("TestQwerX10", "testqwerx10@gmail.com", "123456a@A");
		loginPage.pause();
		loginPage.login("testqwerx10@gmail.com", "123456a@A");
		Assert.assertTrue(loginPage.isDisplayPopupVeriry());
		Assert.assertTrue(loginPage.isResendLinkDisplyed());
		Assert.assertEquals(loginPage.getMessVerifyAccount(), expectedMessVerified);

		loginPage.clickResendLink();
		Assert.assertEquals(loginPage.getToastMessage(), expectedMessResendLink);
		loginPage.clickIconClose();
	}

	// @Test(priority = 6)
	public void loginFailPolicy() {
		loginPage.login("tmhanh@gmail.com", "123456a@A1");
		// loginPage.clickResendLink();
		loginPage.pause();
		loginPage.clickLoginButton();
		loginPage.pause();
		loginPage.clickLoginButton();
		loginPage.pause();
		loginPage.clickLoginButton();

		loginPage.pause();

		loginPage.clickLoginButton();
		loginPage.pause();
		loginPage.clickLoginButton();
		loginPage.pause();
		loginPage.clickLoginButton();
		loginPage.pause();
		loginPage.clickLoginButton();
		loginPage.pause();
		System.out.println(loginPage.getToastMessage());
		Assert.assertEquals(loginPage.getToastMessage(), expectedMessClickMultible);
		Assert.assertTrue(loginPage.getToastMessage().contains(expectedMessClickMultible)
				|| loginPage.getToastMessage().contains(expectedMessClickMultibleMoreThan5));
		loginPage.clickLoginButton();
		loginPage.pause();

		loginPage.clickIconClose();
	}

	@Test(priority = 7, description = "Verify user can login succesfully with valid infor")
	public void loginSuccess() {
		loginPage.login("tmhanh@gmail.com", "123456a@A");
		HomePage homePage = new HomePage();
		Assert.assertTrue(homePage.isDisplayNameProfile());
	}

}
