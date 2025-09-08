package com.qwer.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class WebElementUtils {
	
	private WebDriver driver;
	private Actions actions;
	private JavascriptExecutor js;

	public WebElementUtils(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(driver);
		this.js = (JavascriptExecutor) driver;
	}

	// 🔹 Click bằng JavaScript (trường hợp element bị che)
	public void jsClick(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}

	// 🔹 Scroll đến element
	public void scrollToElement(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	// 🔹 Highlight element (debugging)
	public void highlightElement(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	// 🔹 Chọn dropdown theo visible text
	public void selectDropdownByText(WebElement element, String text) {
		new Select(element).selectByVisibleText(text);
	}

	// 🔹 Chọn dropdown theo value
	public void selectDropdownByValue(WebElement element, String value) {
		new Select(element).selectByValue(value);
	}

	// 🔹 Chọn dropdown theo index
	public void selectDropdownByIndex(WebElement element, int index) {
		new Select(element).selectByIndex(index);
	}

	// 🔹 Double click
	public void doubleClick(WebElement element) {
		actions.doubleClick(element).perform();
	}

	// 🔹 Right click (context click)
	public void rightClick(WebElement element) {
		actions.contextClick(element).perform();
	}

	// 🔹 Drag & drop
	public void dragAndDrop(WebElement source, WebElement target) {
		actions.dragAndDrop(source, target).perform();
	}


	public void hover(WebElement element) {
		actions.moveToElement(element).perform();
	}


	public void jsSetText(WebElement element, String text) {
		js.executeScript("arguments[0].value='" + text + "';", element);
	}


	public String getAttribute(WebElement element, String attrName) {
		return element.getAttribute(attrName);
	}


	public void jsClear(WebElement element) {
		js.executeScript("arguments[0].value='';", element);
	}
}
