package com.qwer.pages.web;

import org.openqa.selenium.By;

import com.qwer.base.BasePage;

public class HomePage extends BasePage {

	

	String xpathName = "//p[contains(text(), '%s')]";
	
	public boolean isDisplayNameProfile() {
		return isDisplayed(By.xpath(String.format(xpathName, "tmhanh")));
	}
	
	public HomePage() {
		
	}
			
}
