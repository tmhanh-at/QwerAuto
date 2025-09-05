package com.qwer.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qwer.contants.CommonContants;
import com.qwer.driver.DriverFactory;
import com.qwer.utils.WaitHelper;

public class BasePage {
	
	
	
	protected WebDriver driver;
    protected WaitHelper wait;
    
    
    public BasePage() {
        this.driver = DriverFactory.getDriver();
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

    // Mở URL
    public void openUrl(String url) {
        driver.get(url);
    }

    // Lấy title trang
    public String getPageTitle() {
        return driver.getTitle();
    }
}
