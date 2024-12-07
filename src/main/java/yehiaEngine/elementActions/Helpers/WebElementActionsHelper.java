package yehiaEngine.elementActions.Helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import yehiaEngine.loggers.LogHelper;

public class WebElementActionsHelper {


    /**
     * *********************************  Locating Element Methods  *************************************
     */
    //Check if Element is Present on DOM
    public static void locateElement(WebDriver driver,By locator) {
        try{
            //Wait for the element to be Present on DOM
            WaitsManager.getFluentWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));

        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Locate the element by Locator ["+locator+"]",e);
        }
    }

    //Check if Element is Displayed on Page With Failing the Test if not displayed
    public static void checkElementDisplayed (WebDriver driver,By elementLocator){
        // Scroll the element into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);",
                driver.findElement(elementLocator));

        //Wait for the element to be Displayed on Page
        try{
            WaitsManager.getFluentWait(driver).until(f -> driver.findElement(elementLocator).isDisplayed());

        }catch(TimeoutException e){
            LogHelper.logErrorStep("The Element located by [" + elementLocator.toString() + "] is not Displayed",e);
        }
    }

    //Get The Element Accessible Name
    public static String getElementName (WebDriver driver,By locator) {
        String elementName;

        if (driver instanceof AppiumDriver appiumDriver)
            elementName = appiumDriver.findElement(locator).getText();
        else
            elementName = driver.findElement(locator).getAccessibleName();

        if ((elementName != null && !elementName.isEmpty()))
            return elementName;
        else
            return locator.toString();
    }

    //Check if Element is Enabled on Page (By Locator)
    public static void checkElementEnabled(WebDriver driver,By locator, String elementName){
        try{
            WaitsManager.getFluentWait(driver).until(f -> driver.findElement(locator).isEnabled());
        }catch(TimeoutException e){
            LogHelper.logErrorStep("The Element [" + elementName + "] is not Enabled",e);
        }
    }

    //Check if Element is Enabled on Page (By WebElement)
    public static void checkElementEnabled(WebElement element, String elementName){
        try{
            element.isEnabled();
        }catch(TimeoutException e){
            LogHelper.logErrorStep("The Element [" + elementName + "] is not Enabled",e);
        }
    }

    /**
     * *********************************  Clear & Write Text On Element *************************************
     */
    public static void writeText(WebDriver driver,By locator, String elementName, String text)
    {
        // Write Text on TextBox Element using the Selenium sendKeys method
        try{
            WaitsManager.getFluentWait(driver).until(f -> {
                driver.findElement(locator).sendKeys(text);
                return true;
            });
            //  Write Text using JavascriptExecutor in case of the data is not typed successfully
            if (driver.findElement(locator).getAttribute("value") != null && !driver.findElement(locator).getAttribute("value").equals(text)) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + text + "')",
                        driver.findElement(locator));
            }
            LogHelper.logInfoStep("Typing ["+text+"] on Element ["+elementName+"]");

        }catch (Exception e)
        {
            LogHelper.logErrorStep("Failed to Type ["+text+"] on Element ["+elementName+"]",e);
        }
    }

    public static void clearText(WebDriver driver,By locator , String elementName)
    {
        try{
            WaitsManager.getFluentWait(driver).until(f -> {
                driver.findElement(locator).clear();
                return true;
            });
            LogHelper.logInfoStep("Clearing the Text on Element ["+elementName+"]");
        }catch (Exception e)
        {
            LogHelper.logErrorStep("Failed to Clear the Text on Element ["+elementName+"]",e);
        }
    }
}
