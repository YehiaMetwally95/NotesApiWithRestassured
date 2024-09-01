package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AlertsActions {

    public static void acceptAlert(WebDriver driver)
    {
        Waits.getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public static void dismissAlert(WebDriver driver)
    {
        Waits.getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }

    public static void typeTextInAlert(WebDriver driver,String text)
    {
        Waits.getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().sendKeys(text);
    }

    public static String getTextInAlert(WebDriver driver)
    {
        Waits.getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert().getText();
    }
}
