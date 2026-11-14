
package com.qaautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;

public class ProductsPage extends BasePage {

    private final By inventoryContainer =
            By.id("inventory_container");

    private final By addBackpackButton =
            By.id("add-to-cart-sauce-labs-backpack");

    private final By shoppingCartLink =
            By.className("shopping_cart_link");

    private final By shoppingCartBadge =
            By.className("shopping_cart_badge");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify that the inventory container is displayed")
    public boolean isInventoryContainerDisplayed() {
        waitForElementToBeVisible(inventoryContainer);
        return driver.findElement(inventoryContainer).isDisplayed();
    }

    @Step("Verify that the user is on the inventory page")
    public boolean isAt() {
        return wait.until(
                org.openqa.selenium.support.ui.ExpectedConditions
                        .urlContains("inventory.html")
        );
    }

    @Step("Add Sauce Labs Backpack to cart")
    public void addBackpackToCart() {
        waitForElementToBeClickable(addBackpackButton);
        driver.findElement(addBackpackButton).click();
    }

    @Step("Open shopping cart")
    public void openCart() {
        waitForElementToBeClickable(shoppingCartLink);
        driver.findElement(shoppingCartLink).click();
    }

    @Step("Get shopping cart badge count")
    public String getCartBadgeCount() {
        waitForElementToBeVisible(shoppingCartBadge);
        return driver.findElement(shoppingCartBadge).getText();
    }
}
