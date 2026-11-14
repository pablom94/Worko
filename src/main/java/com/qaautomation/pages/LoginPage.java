package com.qaautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver){
        super(driver);
    }

    //Agregar @Step annotation to the methods for Allure reporting
    @Step("Open the login page")
    public void open() {
        driver.get("https://www.saucedemo.com/");
    }

    @Step("log in with username '{0}' and password '{1}'")
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    @Step("Enter username")
    public void enterUsername(String username) {
        waitForElementToBeVisible(usernameInput);
        driver.findElement(usernameInput).sendKeys(username);
    }

    @Step("Enter password")
    public void enterPassword(String password) {
        waitForElementToBeVisible(passwordInput);
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Click login button")
    public void clickLogin() {
        waitForElementToBeClickable(loginButton);
        driver.findElement(loginButton).click();
    }

    @Step("Get login error message")
    public String getErrorMessage() {
        waitForElementToBeVisible(errorMessage);
        return driver.findElement(errorMessage).getText();
    }
}
