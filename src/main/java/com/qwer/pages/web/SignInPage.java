package com.qwer.pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qwer.base.BasePage;
import com.qwer.driver.DriverFactory;

public class SignInPage extends BasePage {

	@FindBy(xpath = "//span[contains(text(), 'Register')]/parent::button")
	private WebElement btnRegister;

	@FindBy(xpath = "//input[contains(@placeholder, 'Enter username')]")
	private WebElement txtUsername;

	@FindBy(xpath = "//input[contains(@placeholder, 'Enter email')]")
	private WebElement txtEmail;

	@FindBy(xpath = "//input[contains(@placeholder, 'Enter password')]")
	private WebElement txtPassword;

	@FindBy(xpath = "//form[@id = 'register-form']//button[text() = 'Register']")
	private WebElement btnRegisterX;

	@FindBy(xpath = "//input[@id = 'checkbox-signup']/parent::div")
	private WebElement clbCheckBoxSignUp;

	String toastMess = "//div[contains(@data-testid, 'toast-content')]";

	@FindBy(xpath = "//div[text() = 'Username']/following-sibling::div//div[contains(@class, '__message')]")
	private WebElement errMesInvalidUsername;

	@FindBy(xpath = "//div[text() = 'Email']/following-sibling::div//div[contains(@class, '__message')]")
	private WebElement errMesInvalidEmail;

	@FindBy(xpath = "//div[text() = 'Password']/following-sibling::div//div[@class = 'v-messages']")
	private WebElement errMesInvalidPassword;

	@FindBy(xpath = "//span[contains(@class, 'error-check-box')]")
	private WebElement errMesInvalidConditions;

	@FindBy(xpath = "//*[contains(text(), 'Verify Now')]")
	private WebElement verifyNowElement;

	@FindBy(xpath = "//*[contains(text(), 'Verify Now')]/following-sibling::p")
	private WebElement messVerifyElement;
	
	@FindBy(xpath = "//p[text() = 'One lower & upper case letter']")
	private WebElement textWarningPassword;


	public SignInPage() {
		this.driver = DriverFactory.getDriver();
		PageFactory.initElements(driver, this);
	}

	public void clickRegiter() {
		if (btnRegister.isDisplayed()) {
			btnRegister.click();
		}
	}

	public void register(String username, String email, String password) {
		setText(txtUsername, username);
		setText(txtEmail, email);
		setText(txtPassword, password);
	}

	public void clickRegisterX() {
		btnRegisterX.click();
	}

	public void clickCheckboxConfirm() {
		clbCheckBoxSignUp.click();
	}

	// strColor: text-red/text-primary
	// key: One numeric/8-32 characters/One lower & upper case letter
	public boolean isTextDisplayCorrectly(String strColor, String key) {
		wait.pause(1);		
		String lblText = String.format("//p[contains(@class, '%s') and contains(text(), '%s')]", strColor, key);				
		return isDisplayed(By.xpath(lblText));
	}

	public void scrollToElement() {
		scrollToElement(txtPassword);
	}
		
	public String getErrorMessValidateUsername() {
		return getText(errMesInvalidUsername);
	}

	public String getErrorMessValidateEmail() {
		return getText(errMesInvalidEmail);
	}

	public String getErrorMessValidatePassword() {	
		return getText(errMesInvalidPassword);
	}

	public String getErrorMessValidateConditions() {		
		return getText(errMesInvalidConditions);
	}

}
