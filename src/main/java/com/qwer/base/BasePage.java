package com.qwer.base;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.qwer.contants.CommonContants;
import com.qwer.driver.DriverFactory;
import com.qwer.utils.WaitHelper;

public class BasePage {

	protected WebDriver driver;
	protected WaitHelper wait;

	public BasePage() {
		this.driver = DriverFactory.getDriver();
		PageFactory.initElements(driver, this); // Khởi tạo @FindBy
		this.wait = new WaitHelper(driver, CommonContants.TIMEOUT);
	}

	// Click element
	protected void click(By locator) {
		wait.waitForElementClickable(locator).click();
	}

	// Nhập text
	protected void setText(By locator, String text) {
		WebElement element = wait.waitForElementPresent(locator);
		element.clear();
		element.sendKeys(text);
	}

	protected void setText(WebElement element, String text) {
		element.sendKeys(Keys.CONTROL + "a");
		element.sendKeys(Keys.DELETE);
		element.sendKeys(text);
	}

	// Lấy text element
	protected String getText(By locator) {
		return wait.waitForElementPresent(locator).getText();
	}

	// Kiểm tra element hiển thị
	protected boolean isDisplayed(By locator) {
		try {
			return wait.waitForElementPresent(locator).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	protected boolean isNotDisplayed(By locator) {
		try {
			return wait.waitForElementInvisible(locator);
		} catch (Exception e) {
			return false;
		}

	}	

	protected void click(WebElement element) {
		try {
			wait.waitForClickable(element).click();
		} catch (ElementClickInterceptedException e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		}
	}	

	protected String getText(WebElement element) {
		return wait.waitForVisible(element).getText();
	}

	protected void scrollToElement(WebElement element) {
	    ((JavascriptExecutor) DriverFactory.getDriver())
	            .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
	}
	
	protected void scrollToElement(By locator) {
	    WebElement element = DriverFactory.getDriver().findElement(locator);
	    ((JavascriptExecutor) DriverFactory.getDriver())
	            .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
	}
	
	public void scrollToTextActions(WebElement element) {	   
	    new Actions(DriverFactory.getDriver()).moveToElement(element).perform();
	}
	
	public String getToastMessage() {		
		By byErrMesWrongPass  = By.xpath("//div[contains(@data-testid, 'toast-content')]");
		Assert.assertTrue(wait.waitForElementInvisible(byErrMesWrongPass));
		
		wait.pause(1);
		return driver.findElement(byErrMesWrongPass).getText();
	}
	
	public void isToastMessNotDisplayed() {
		wait.waitForElementInvisible(By.xpath("//div[contains(@data-testid, 'toast-content')]"));
	}
	
	public boolean isDisplayedText(String text) {	
		return isDisplayed(By.xpath("//*[contains(text(), '" + text + "')]"));
	}
}
