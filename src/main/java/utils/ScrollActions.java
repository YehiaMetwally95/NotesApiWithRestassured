package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;

public class ScrollActions {

    public static void scrollDownToElement (WebDriver driver, By elementLocator) {
        WebElement table = driver.findElement(elementLocator);
        new Actions(driver).scrollToElement(table).perform();
    }

    public static void scrollByGivenAmountFromElement(WebDriver driver,By elementLocator , int deltaX, int deltaY)
    {
        WheelInput.ScrollOrigin myOrigin =
                WheelInput.ScrollOrigin.fromElement(driver.findElement(elementLocator));
        new Actions(driver).scrollFromOrigin(myOrigin,deltaX,deltaY).perform();
    }

    public static void scrollDownTillElementDisplayed (WebDriver driver,By targetElement , int scrollStep) {
       Waits.getFluentWait(driver).until(f->{
           new Actions(driver).scrollByAmount(0,scrollStep).perform();
           driver.findElement(targetElement).isDisplayed();
           return true;
       });
    }
}
