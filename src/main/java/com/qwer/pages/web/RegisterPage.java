package com.qwer.pages.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qwer.base.BasePage;
import com.qwer.driver.DriverFactory;
import com.qwer.utils.DatabaseUtils;
import com.qwer.utils.WaitHelper;

public class RegisterPage extends BasePage {
	@FindBy(xpath = "//span[contains(text(), 'Register')]/parent::button")
	private WebElement btnRegister;

	@FindBy(xpath = "//input[contains(@placeholder, 'Enter username')]")
	private WebElement txtUsername;

	@FindBy(xpath = "//input[contains(@placeholder, 'Enter email')]")
	private WebElement txtEmail;

	@FindBy(xpath = "//input[contains(@placeholder, 'Enter password')]")
	private WebElement txtPassword;

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

	String xpathBtnRegister = "//form[@id = 'register-form']//button[text() = 'Register']";

	public RegisterPage() {
		this.driver = DriverFactory.getDriver();
		PageFactory.initElements(driver, this);
	}

	public void clickButtonRegisterToShowScreenRegister() {
//		if (btnRegister.isDisplayed() && isNotDisplayed(By.xpath(xpathBtnRegister))) {
//			btnRegister.click();
//		}
		pause();
		click(btnRegister);
	}

	public void setUsername(String username) {
		setText(txtUsername, username);
	}

	public void setEmail(String email) {
		setText(txtEmail, email);
	}

	public void setPassword(String password) {
		setText(txtPassword, password);
	}

	public void register(String username, String email, String password) {
		setText(txtUsername, username);
		setText(txtEmail, email);
		setText(txtPassword, password);
	}

	public void clickRegisterX() {
		click(By.xpath(xpathBtnRegister));
	}

	public void clickCheckboxConfirm() {
		if (clbCheckBoxSignUp.isEnabled()) {
			clbCheckBoxSignUp.click();
		}
	}

	// strColor values: text-red/text-primary
	// key values: One numeric/8-32 characters/One lower & upper case letter
	public boolean isTextDisplayCorrectly(String strColor, String key) {
		WaitHelper.pause(1);
		String lblText = String.format("//p[contains(@class, '%s') and contains(text(), '%s')]", strColor, key);
		return isDisplayed(By.xpath(lblText));
	}

	public void scrollToElement() {
		txtPassword.click();
		scrollToElement(textWarningPassword);
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

	public void clearDataBeforeTest(String strEmail) {

		DatabaseUtils dbUtils = new DatabaseUtils();

		List<String> lstTables = Arrays.asList("users", "player_information", "segment_users", "wallets",
				"user_fingerprints", "generate_user_bonuses");

		try {
			dbUtils.connect();

			List<Map<String, String>> dataRows = dbUtils
					.getDataRows("Select id from USERS where email = '" + strEmail + "'");

			for (int i = 0; i < dataRows.size(); i++) {
				Map<String, String> row = dataRows.get(i);

				for (Map.Entry<String, String> entry : row.entrySet()) {
					String key = entry.getKey();
					if (key.equalsIgnoreCase("id")) {
						String value = entry.getValue();

						for (String table : lstTables) {
							String sqlQuery = "delete from ";
							if (table.equals("users")) {
								sqlQuery = sqlQuery + table + " where id = '" + value + "'";
							} else {
								sqlQuery = sqlQuery + table + " where user_id = '" + value + "'";
							}
							System.out.println("Rows delete: " + sqlQuery);
							dbUtils.executeQuery(sqlQuery);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtils.disconnect();
		}
	}

	@Override
	public void pause() {
		WaitHelper.pause(1);
	}

}
