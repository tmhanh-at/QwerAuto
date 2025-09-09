package com.qwer.pages.web;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qwer.base.BasePage;

public class LoginPage extends BasePage {
	
	@FindBy(xpath = "//span[contains(text(), 'Login')]/parent::button") 
	private WebElement btnLogin;		
	
	@FindBy(xpath = "//input[contains(@placeholder, 'Enter email')]") 
	private WebElement txtEmail;
	
	@FindBy(xpath = "//input[contains(@placeholder, 'Enter password')]") 
	private WebElement txtPassword;
	
	@FindBy(xpath = "//input[contains(@placeholder, 'Enter username')]") 
	private WebElement txtUsername;	
	
	@FindBy(xpath = "//form[@id = 'login-form']//button[text() = 'Login']")
	private WebElement btnLoginF;	
	
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
	
//	public LoginPage(WebDriver driver){
////		this.driver = DriverFactory.getDriver();
////		PageFactory.initElements(driver, this);			
//		super(driver);
//	}
	
	public void login(String email, String password) {			
		setText(txtEmail, email);
		setText(txtPassword, password);
		btnLoginF.click();
	}	
	
	public void clickLogin() {
		if(btnLogin.isDisplayed()) {
			btnLogin.click();
		}
	}
	
	public boolean waitAndClickIfVisible(By locator, int timeoutInSeconds) {
        try {           
            WebElement element = wait.waitForElementClickable(locator);            
            element.click();
            System.out.println("Clicked element: " + locator.toString());
            return true;
        } catch (TimeoutException e) {
            System.out.println("Element not visible within " + timeoutInSeconds + "s: " + locator.toString());
            return false;
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + locator.toString());
            return false;
        }
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
	
	
	
	public void clickResendLink() {
//		WebElementUtils webElementUtils = new WebElementUtils(DriverFactory.getDriver());
//		webElementUtils.jsClick(btnResendLink);
	}
	
	
	public void pause(int time) {
		wait.pause(time);
	}
	
}
