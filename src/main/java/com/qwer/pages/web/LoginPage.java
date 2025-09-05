package com.qwer.pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.qwer.base.BasePage;
import com.qwer.driver.DriverFactory;

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
	
	String errMesWrongPass = "//div[contains(@data-testid, 'toast-content')]";
	
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
	
	public LoginPage(){
		this.driver = DriverFactory.getDriver();
		PageFactory.initElements(driver, this);		
	}
	
	public void login(String email, String password) {
		txtEmail.clear();
		txtEmail.sendKeys(email);
		txtPassword.clear();
		txtPassword.sendKeys(password);
//		setText(txtEmail, email);
//		setText(txtPassword, password);
		btnLoginF.click();
	}	
	
	public void clickLoginButton() {
		if(btnLogin.isDisplayed()) {
			btnLogin.click();
		} 		
	}
	
	public String getErrMessWrongPass() {
		By byErrMesWrongPass  = By.xpath(errMesWrongPass);
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
		element.clear();
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
	
	
}
