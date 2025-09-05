package com.qwer.pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.qwer.base.BasePage;
import com.qwer.driver.DriverFactory;
import com.qwer.utils.WebElementUtils;

public class LoginPage extends BasePage {
	
	@FindBy(xpath = "//span[contains(text(), 'Login')]/parent::button") 
	private WebElement btnLogin;	
	
	@FindBy(xpath = "//span[contains(text(), 'Register')]/parent::button") 
	private WebElement btnRegister;
	
	@FindBy(xpath = "//input[contains(@placeholder, 'Enter email')]") 
	private WebElement txtEmail;
	
	@FindBy(xpath = "//input[contains(@placeholder, 'Enter password')]") 
	private WebElement txtPassword;
	
	@FindBy(xpath = "//input[contains(@placeholder, 'Enter username')]") 
	private WebElement txtUsername;
	
	@FindBy(id = "checkbox-signup") 
	private WebElement clbCheckBoxSignUp;	
	
	@FindBy(xpath = "//form[@id = 'login-form']//button[text() = 'Login']")
	private WebElement btnLoginF;
	
	String toastMess = "//div[contains(@data-testid, 'toast-content')]";
	
	@FindBy(xpath = "//div[contains(text(), 'Email')]/following-sibling::div//div[@class = 'v-messages']")
	private WebElement errMesInvalidEmail;
	
	@FindBy(xpath = "//div[contains(text(), 'Password')]/following-sibling::div//div[@class = 'v-messages']")
	private WebElement errMesInvalidPassword;
	
	@FindBy(xpath = "//*[contains(text(), 'Verify Now')]")
	private WebElement verifyNowElement;
	
	@FindBy(xpath = "//*[contains(text(), 'Verify Now')]/following-sibling::p")
	private WebElement messVerifyElement;
	
	@FindBy(xpath = "//button[text()= 'Resend Link']")
	private WebElement btnResendLink;
	
	@FindBy (xpath = "//label[@for = 'checkbox-signup']")
	private WebElement checkboxConfirm;
	
	public LoginPage(){
		this.driver = DriverFactory.getDriver();
		PageFactory.initElements(driver, this);		
	}
	
	public void login(String email, String password) {		
		setText(txtEmail, email);
		setText(txtPassword, password);
		btnLoginF.click();
	}	
	
	public void clickLoginButton() {
		if(btnLogin.isDisplayed()) {
			btnLogin.click();
		} 		
	}
	
	public String getToatMessage() {
		By byErrMesWrongPass  = By.xpath(toastMess);
		Assert.assertTrue(wait.waitForElementInvisible(byErrMesWrongPass));
		
		wait.pause(1);
		return driver.findElement(byErrMesWrongPass).getText();
	}
		
	public String getErrMessInvalidEmail() {
		return errMesInvalidEmail.getText();
	}
	
	public String getErrMessInvalidPassword() {
		return errMesInvalidPassword.getText();
	}
	
	public void setText(WebElement element, String text) {
		element.sendKeys(Keys.CONTROL + "a");
		element.sendKeys(Keys.DELETE);
		element.sendKeys(text);
	}
	
	public String getMessVerifyAccount() {		
		return getText(By.xpath("//*[contains(text(), 'Verify Now')]/following-sibling::p"));
	}	
	
	public boolean isDisplayPopupVeriry() {
		wait.pause(1);
		return isDisplayed(By.xpath("//*[contains(text(), 'Verify Now')]"));
	}
	
	public boolean isResendLinkDisplyed() {
		return isDisplayed(By.xpath("//button[text()= 'Resend Link']"));		
	}
	
	public void isToastMessNotDisplayed() {
		wait.waitForElementInvisible(By.xpath(toastMess));
	}
	
	public void clickResendLink() {
		WebElementUtils webElementUtils = new WebElementUtils(DriverFactory.getDriver());
		webElementUtils.jsClick(btnResendLink);
	}
	
	/***Register Area****************/
	public void clickRegiterButton() {
		btnRegister.click();
	}
	
	public void register(String username, String email, String password) {
		setText(txtUsername, username);
		setText(txtEmail, email);
		setText(txtPassword, password);
	}
	
	public void clickCheckboxConfirm() {
		checkboxConfirm.click();
	}
	
	//strColor: text-red/text-primary
	//key: One numeric/8-32 characters/One lower & upper case letter
	public boolean isTextDisplayCorrectly(String strColor, String key) {
		String lblText = "//p[contains(@class, '%s') and contains(text(), '%s')]";
		return isDisplayed(By.xpath(lblText));		
	}
	
	
}
