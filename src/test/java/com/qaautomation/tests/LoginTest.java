package com.qaautomation.tests;

import com.qaautomation.base.BaseTest;
import com.qaautomation.pages.LoginPage;
import org.junit.jupiter.api.Test;
import com.qaautomation.pages.ProductsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {

    @Test
    public void LoginAndDisplayProducts() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver);

        assertTrue(
                productsPage.isInventoryContainerDisplayed(),
                "The inventory container should be displayed after successful login"
        );

        assertTrue(
                productsPage.isAt(),
                "The user should be redirected to the inventory page"
        );
    }

    @Test
    public void shouldDisplayErrorWithInvalidCredentials() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.open();
        loginPage.login("wrong_user", "wrong_password");

        assertTrue(
                loginPage.getErrorMessage().contains(
                        "Username and password do not match"
                ),
                "An error message should be displayed for invalid credentials"
        );

        assertTrue(
                driver.getCurrentUrl().contains("saucedemo.com"),
                "The user should remain on the login page"
        );
    }
}
