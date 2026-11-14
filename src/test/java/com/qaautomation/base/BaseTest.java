
package com.qaautomation.base;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.ByteArrayInputStream;

public class BaseTest {

    protected WebDriver driver;

    @RegisterExtension
    protected final AfterTestExecutionCallback screenshotOnFailure =
            context -> {
                if (context.getExecutionException().isPresent()) {
                    attachScreenshot();
                }
            };

    @BeforeEach
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-saved-password-bubble");
        options.addArguments("--disable-notifications");

        options.setExperimentalOption("prefs", java.util.Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false
        ));

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    private void attachScreenshot() {

        if (driver != null) {
            byte[] screenshot =
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(
                    "Screenshot on failure",
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    ".png"
            );
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
