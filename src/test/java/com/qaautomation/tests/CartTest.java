package com.qaautomation.tests;

import com.qaautomation.base.BaseTest;
import com.qaautomation.pages.CartPage;
import com.qaautomation.pages.CheckOutPage;
import com.qaautomation.pages.LoginPage;
import com.qaautomation.pages.ProductsPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartTest extends BaseTest {

    private ProductsPage loginAndOpenInventory() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        assertTrue(productsPage.isAt(),
                "The inventory page should be displayed after successful login");

        return productsPage;
    }

    @Epic("E-commerce")
    @Feature("Checkout")
    @Story("Successful purchase")
    @DisplayName("Should complete a purchase with a backpack")
    @Test
    public void shouldCompletePurchaseWithBackpack() {

       ProductsPage productsPage = loginAndOpenInventory();
       CartPage cartPage = new CartPage(driver);
       CheckOutPage checkOutPage = new CheckOutPage(driver);

        productsPage.addBackpackToCart();

        assertEquals("1", productsPage.getCartBadgeCount(),
                "The cart badge should display one product in the cart");

        productsPage.openCart();

        assertTrue(cartPage.isCartItemDisplayed("Sauce Labs Backpack"),
                "The cart should display the added backpack item");

        cartPage.proceedToCheckout();

        assertTrue(checkOutPage.isAt(),
                "The checkout page should be displayed after proceeding to checkout");

        checkOutPage.enterCustomerInformation(
                "Pablo",
                "Martinez",
                "12345"
        );

        checkOutPage.clickContinue();

        //Finish order
        checkOutPage.clickFinish();

        assertEquals("Thank you for your order!",
                checkOutPage.getConfirmationMessage(),
                "The order confirmation message should be displayed after finishing the order"
        );

    }


    @Epic("E-commerce")
    @Feature("Cart")
    @Story("Remove item from cart")
    @DisplayName("Should remove an item from the cart")
    @Test
    public void shouldRemoveItemFromCart() {

        ProductsPage productsPage = loginAndOpenInventory();
        CartPage cartPage = new CartPage(driver);

        productsPage.addBackpackToCart();
        productsPage.openCart();

        assertTrue(cartPage.isCartItemDisplayed("Sauce Labs Backpack"),
                "The cart should display the added backpack item");

        cartPage.removeBackpackFromCart();

        assertTrue(cartPage.isCartEmpty(),
                "The cart should be empty after removing the backpack item");
    }

    @Epic("E-commerce")
    @Feature("Checkout")
    @Story("Display error for incomplete postal code")
    @DisplayName("Should display an error when the postal code is incomplete")
    @Test
    public void shouldDisplayErrorWhenPostalCodeIsIncomplete() {

        ProductsPage productsPage = loginAndOpenInventory();
        CartPage cartPage = new CartPage(driver);
        CheckOutPage checkOutPage = new CheckOutPage(driver);

        productsPage.addBackpackToCart();
        productsPage.openCart();

        assertTrue(cartPage.isCartItemDisplayed("Sauce Labs Backpack"),
                "The cart should display the added backpack item"
        );

        cartPage.proceedToCheckout();

        assertTrue(checkOutPage.isAt(),
                "The checkout page should be displayed after proceeding to checkout");

        // Enter incomplete customer information (missing postal code)
        checkOutPage.enterCustomerInformation(
                "Pablo",
                "Martinez",
                ""
        );

        checkOutPage.clickContinue();

        assertEquals("Error: Postal Code is required",
                checkOutPage.getErrorMessage(),
                "An error message should be displayed when the postal code is missing"
        );

        assertTrue(checkOutPage.isAt(),
                "The user should remain on the checkout page after an error"
        );
    }
}
