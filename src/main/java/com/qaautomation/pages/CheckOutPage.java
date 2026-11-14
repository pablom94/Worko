package com.qaautomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import io.qameta.allure.Step;

public class CheckOutPage extends BasePage {

    public CheckOutPage(WebDriver driver) {
        super(driver);
    }

    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By finishButton = By.id("finish");
    private final By checkoutCompleteHeader = By.className("complete-header");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    @Step("Enter customer information: first name '{0}', last name '{1}', postal code '{2}'")
    public void enterCustomerInformation(
            String firstName,
            String lastName,
            String postalCode) {

        waitForElementToBeVisible(firstNameInput);
        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(postalCodeInput).sendKeys(postalCode);
    }

    @Step("Check whether the checkout information page is displayed")
    public  boolean isAt() {
        return wait.until(
                org.openqa.selenium.support.ui.ExpectedConditions
                        .urlContains("checkout-step-one.html")
        );
    }

    @Step("Continue checkout")
    public void clickContinue() {
        waitForElementToBeClickable(continueButton);
        driver.findElement(continueButton).click();
    }

    @Step("Finish the order")
    public void clickFinish() {
        waitForElementToBeClickable(finishButton);
        driver.findElement(finishButton).click();
    }

    @Step("Get order confirmation message")
    public String getConfirmationMessage() {
        waitForElementToBeVisible(checkoutCompleteHeader);
        return driver.findElement(checkoutCompleteHeader).getText();
    }

    @Step("Get checkout error message")
    public String getErrorMessage() {
        waitForElementToBeVisible(errorMessage);
        return driver.findElement(errorMessage).getText();
    }
}
