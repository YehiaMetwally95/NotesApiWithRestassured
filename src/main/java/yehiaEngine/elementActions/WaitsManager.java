package yehiaEngine.elementActions;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;

public class WaitsManager {

    public static void getImplicitWait(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(Integer.parseInt(getPropertiesValue("implicitWaitTimeout"))
                ));
    }

    public static Wait<WebDriver> getExplicitWait(WebDriver driver) {
        return new WebDriverWait(driver,
                Duration.ofSeconds(Integer.parseInt(getPropertiesValue("ExplicitWaitTimeout"))
        ));
    }

    public static Wait<WebDriver> getFluentWait(WebDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(
                        Integer.parseInt(getPropertiesValue("FluentWaitTimeout")
                )))
                .pollingEvery(Duration.ofMillis(
                        Integer.parseInt(getPropertiesValue("FluentWaitPolling")
                )))
                .ignoring(NotFoundException.class)
                .ignoring(AssertionError.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(JavascriptException.class);
    }

    public static Wait<WebDriver> getSwipeWait(WebDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(
                        Integer.parseInt(getPropertiesValue("SwipeTimeout")
                        )))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NotFoundException.class)
                .ignoring(ElementNotInteractableException.class)
                .ignoring(AssertionError.class)
                .ignoring(StaleElementReferenceException.class);
    }
}
