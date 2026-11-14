package com.qaautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;

public class CartPage extends BasePage {

    private final By cartItemName =
            By.className("inventory_item_name");

    private final By removeBackpackButton =
            By.id("remove-sauce-labs-backpack");

    private final By proceedToCheckoutButton =
            By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Check whether cart item '{0}' is displayed")
    public boolean isCartItemDisplayed(String productName) {
        waitForElementToBeVisible(cartItemName);

        return driver.findElements(cartItemName)
                .stream()
                .anyMatch(element -> element.getText().equals(productName));
    }

    @Step("Remove the backpack from the cart")
    public void removeBackpackFromCart() {
        waitForElementToBeClickable(removeBackpackButton);
        driver.findElement(removeBackpackButton).click();
    }

    @Step("Check whether the cart is empty")
    public boolean isCartEmpty() {
        return wait.until(
                org.openqa.selenium.support.ui.ExpectedConditions
                        .invisibilityOfElementLocated(cartItemName)
        );
    }

    @Step("Proceed to checkout")
    public void proceedToCheckout() {
        waitForElementToBeClickable(proceedToCheckoutButton);
        driver.findElement(proceedToCheckoutButton).click();
    }

}
