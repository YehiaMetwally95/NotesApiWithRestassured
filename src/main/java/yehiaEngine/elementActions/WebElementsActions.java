package yehiaEngine.elementActions;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import yehiaEngine.loggers.LogHelper;

import java.util.List;
import java.util.stream.Collectors;

import static yehiaEngine.elementActions.Helpers.WaitsManager.getFluentWait;
import static yehiaEngine.elementActions.Helpers.WebElementActionsHelper.*;


public class WebElementsActions {
    //Variables
    WebDriver driver;

    //Constructor
    public WebElementsActions(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * *********************************  Basic Interactions *************************************
     */
    // Clear TextBox then Typing on it & Log Typing Action
    public WebElementsActions type (By locator , String text) {
        //Locate Element and Check if its present on DOM
        locateElement(driver,locator);
        //Check if Element is Displayed and Visible on Page
        checkElementDisplayed(driver,locator);
        //Get Element AccessibleName
        String elementName = getElementName(driver,locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver,locator,elementName);
        //Take Action on Element
        clearText(driver,locator,elementName);
        writeText(driver,locator,elementName,text);
        return this;
    }

    //Pressing on Button or Link & Log CLicking Action
    public WebElementsActions press(By locator) {
        //Locate Element and Check if its present on DOM
        locateElement(driver,locator);
        //Check if Element is Displayed and Visible on Page
        checkElementDisplayed(driver,locator);
        //Get Element AccessibleName
        String elementName = getElementName(driver,locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver,locator,elementName);
        //Take Action on Element
        try {
            if (driver instanceof AppiumDriver appiumDriver)
            {
                getFluentWait(appiumDriver).until(f -> {
                    new Actions(appiumDriver).moveToElement(appiumDriver.findElement(locator)).perform();
                    appiumDriver.findElement(locator).click();
                    return true;
                });
            }
            else
            {
                getFluentWait(driver).until(f -> {
                    new Actions(driver).moveToElement(driver.findElement(locator)).perform();
                    driver.findElement(locator).click();
                    return true;
                });
            }
            LogHelper.logInfoStep("Clicking on Element ["+elementName+"]");
        }catch (ElementNotInteractableException | TimeoutException e)
        //If Webdriver Click fails and fluent wait throw Timeout Exception, Try to click using JS
        {
            try {
                ((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(locator));
                LogHelper.logInfoStep("Clicking using JS on Element ["+elementName+"]");
            }catch (Exception i)
            {
                LogHelper.logErrorStep("Failed to Click on Element ["+elementName+"]",i);
            }
        }
        return this;
    }

    //Pressing on Button or Link (By WebElement) & Log CLicking Action
    public WebElementsActions press(WebElement element) {
        String elementName = element.getText();
        checkElementEnabled(element,elementName);
        try {
            if (driver instanceof AppiumDriver appiumDriver)
            {
                getFluentWait(appiumDriver).until(f -> {
                    new Actions(appiumDriver).moveToElement(element).perform();
                    element.click();
                    return true;
                });
            }
            else
            {
                getFluentWait(driver).until(f -> {
                    new Actions(driver).moveToElement(element).perform();
                    element.click();
                    return true;
                });
                LogHelper.logInfoStep("Clicking on Element ["+elementName+"]");
            }
        }catch (ElementNotInteractableException | TimeoutException e)
        //If Webdriver Click fails and fluent wait throw Timeout Exception, Try to click using JS
        {
            try {
                ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
                LogHelper.logInfoStep("Clicking using JS on Element ["+elementName+"]");
            }catch (Exception i)
            {
                LogHelper.logErrorStep("Failed to Click on Element ["+elementName+"]",i);
            }
        }
        return this;
    }

    //Verify Element is Displayed on Page
    public boolean isElementDisplayed(By locator){
        try{
            //Wait until Element is Displayed on Page
            getFluentWait(driver).until(f -> driver.findElement(locator).isDisplayed());
            //Get Element Accessible Name
            String elementName = getElementName(driver,locator);
            LogHelper.logInfoStep("The Element [" + elementName + "] is Displayed");

        }catch(TimeoutException e){
            LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is not Displayed");
            return false;
        }
        return true;
    }

    //Verify Element is Displayed on Page
    public boolean isElementNotDisplayed(By locator){
        try{
            //Wait until Element is Displayed on Page
            getFluentWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
            LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is Not Displayed");

        }catch(TimeoutException e){
            LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is Displayed");
            return false;
        }
        return true;
    }

    //Get Text from Element (with Locator) & Log the Text
    public String readText(By locator) {
        //Locate Element and Check if its present on DOM
        locateElement(driver,locator);
        //Check if Element is Displayed and Visible on Page
        checkElementDisplayed(driver,locator);
        //Read Text from Element on Page
        try {
            String text = getFluentWait(driver).until(f -> driver.findElement(locator).getText());
            LogHelper.logInfoStep("Getting Text " + "[" + text + "] from Element located by [" + locator.toString() +"]");
            return text.replace("\n","");
        }catch (Exception e)
        {
            LogHelper.logErrorStep("Cannot Get Text from Element located by [" + locator+"]",e);
            return null;
        }
    }

    // Get Text from Element (with WebElement) & Log the Text
    public String readText(WebElement element) {
        try {
            String text = element.getText();
            LogHelper.logInfoStep("Getting Text " + "[" + text + "] from Web Element");
            return text.replace("\n","");
        }catch (Exception e)
        {
            LogHelper.logErrorStep("Cannot Get Text from Web Element",e);
            return null;
        }
    }

    //Long Pressing on Button or Link & Log CLicking Action
    public WebElementsActions longPress(By locator) {
        //Locate Element and Check if its present on DOM
        locateElement(driver,locator);
        //Check if Element is Displayed and Visible on Page
        checkElementDisplayed(driver,locator);
        //Get Element AccessibleName
        String elementName = getElementName(driver,locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver,locator,elementName);
        //Take Action on Element
        try {
            getFluentWait(driver).until(f -> {
                new Actions(driver).moveToElement(driver.findElement(locator)).doubleClick()
                        .perform();
                return true;
            });
        }catch (Exception e)
        {
            LogHelper.logErrorStep("Failed to Long Click on Element ["+elementName+"]",e);
        }
        LogHelper.logInfoStep("Long Clicking on Element ["+elementName+"]");
        return this;
    }

    //Hover on Element & Log Hovering Action
    public WebElementsActions hoverOnElement (By locator) {
        //Locate Element and Check if its present on DOM
        locateElement(driver,locator);
        //Check if Element is Displayed and Visible on Page
        checkElementDisplayed(driver,locator);
        //Get Element AccessibleName
        String elementName = getElementName(driver,locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver,locator,elementName);
        //Take Action on Element
        try {
            getFluentWait(driver).until(f -> {
                new Actions(driver).moveToElement(driver.findElement(locator)).perform();
                return true;
            });
        }catch (Exception e)
        {
            LogHelper.logErrorStep("Failed to Hover on Element ["+elementName+"]",e);
        }
        LogHelper.logInfoStep("Hovering on Element ["+elementName+"]");
        return this;
    }

    //Get All Matched Elements
    public List<WebElement> getAllMatchedElements(By locator){
        //Locate Element and Check if its present on DOM
        locateElement(driver,locator);
        //Check if Element is Displayed and Visible on Page
        checkElementDisplayed(driver,locator);
        //Read Text from Element on Page
        try {
            List<WebElement> list
                    = getFluentWait(driver).until(f -> driver.findElements(locator));
            LogHelper.logInfoStep("Retrieving All Matched Elements from the Element located by [" + locator + "]");
            return list;
        }catch (Exception e)
        {
            LogHelper.logErrorStep("Cannot Retrieve All Matched Elements from the Element located by " + locator,e);
            return null;
        }
    }

    /**
     * *********************************  Interactions with DropDowns  *************************************
     */
    public WebElementsActions selectFromDropdownByValue(By dropdownLocator , String value)
    {
        String elementName = getElementName(driver,dropdownLocator);
        try {
            dropDownElement(dropdownLocator).selectByValue(value);
            LogHelper.logInfoStep("Selecting ["+ value +"] from" + elementName);
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Select ["+ value +"] from" + elementName,e);
        }
        return this;
    }

    public WebElementsActions selectFromDropdownByIndex(By dropdownLocator , int index)
    {
        String elementName = getElementName(driver,dropdownLocator);
        try {
            dropDownElement(dropdownLocator).selectByIndex(index);
            LogHelper.logInfoStep("Selecting ["+ index +"] from" + elementName);
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Select ["+ index +"] from" + elementName,e);
        }
        return this;
    }

    public WebElementsActions selectFromDropdownByText(By dropdownLocator , String text)
    {
        String elementName = getElementName(driver,dropdownLocator);
        try {
            dropDownElement(dropdownLocator).selectByVisibleText(text);
            LogHelper.logInfoStep("Selecting ["+ text +"] from" + elementName);
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Select ["+ text +"] from" + elementName,e);
        }
        return this;
    }

    public List<String> getAllOptionsAsString(By dropdownLocator)
    {
        String elementName = getElementName(driver,dropdownLocator);
        try {
            List<WebElement> options = dropDownElement(dropdownLocator).getOptions();
            LogHelper.logInfoStep("Retrieving All Options from List of " + elementName);
            return options.stream().map(e->e.getText()).collect(Collectors.toList());
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Retrieve All Options from List of " + elementName,e);
            return null;
        }
    }

    public String getSelectedOption(By dropdownLocator)
    {
        String elementName = getElementName(driver,dropdownLocator);
        try {
            String text = dropDownElement(dropdownLocator).getFirstSelectedOption().getText();
            LogHelper.logInfoStep("Retrieving Selected Option ["+text+"] from List of " + elementName);
            return text;
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Retrieve Selected Option from List of " + elementName,e);
            return null;
        }
    }

    public WebElementsActions deselectAllOptions(By dropdownLocator)
    {
        String elementName = getElementName(driver,dropdownLocator);
        try {
            dropDownElement(dropdownLocator).deselectAll();
            LogHelper.logInfoStep("Deselecting All Options from List of " + elementName);
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Deselect All Options from List of "+ elementName,e);
        }
        return this;
    }

    private Select dropDownElement(By dropdownLocator)
    {
        //Locate Element and Check if its present on DOM
        locateElement(driver,dropdownLocator);
        //Check if Element is Displayed and Visible on Page
        checkElementDisplayed(driver,dropdownLocator);
        //Get Element AccessibleName
        String elementName = getElementName(driver,dropdownLocator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver,dropdownLocator,elementName);
        return new Select(driver.findElement(dropdownLocator));
    }

    /**
     * *********************************  Interactions with Scrolling  *************************************
     */
    public WebElementsActions scrollToElementIntoView(By elementLocator) {
        //Locate Element and Check if its present on DOM
        locateElement(driver,elementLocator);
        //Get Element AccessibleName
        String elementName = getElementName(driver,elementLocator);
        try{
            new Actions(driver).scrollToElement(driver.findElement(elementLocator)).perform();
            LogHelper.logInfoStep("Scrolling to element ["+elementName+"]");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Scroll to element ["+elementName+"]",e);
        }
        return this;
    }

    public WebElementsActions scrollByGivenAmountFromElement(By elementLocator , int deltaX, int deltaY)
    {
        //Locate Element and Check if its present on DOM
        locateElement(driver,elementLocator);
        //Get Element AccessibleName
        String elementName = getElementName(driver,elementLocator);
        try{
            WheelInput.ScrollOrigin myOrigin =
                    WheelInput.ScrollOrigin.fromElement(driver.findElement(elementLocator));
            new Actions(driver).scrollFromOrigin(myOrigin,deltaX,deltaY).perform();
            LogHelper.logInfoStep("Scrolling with " +deltaX +" & "+deltaY+ "from Element ["+elementName+"]");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Scroll with " +deltaX +" & "+deltaY+ "from Element ["+elementName+"]",e);
        }
        return this;
    }

    public WebElementsActions scrollTillElementDisplayed (By elementLocator , int scrollStep) {
        //Locate Element and Check if its present on DOM
        locateElement(driver,elementLocator);
        //Get Element AccessibleName
        String elementName = getElementName(driver,elementLocator);
        try{
            getFluentWait(driver).until(f->{
                new Actions(driver).scrollByAmount(0,scrollStep).perform();
                driver.findElement(elementLocator).isDisplayed();
                return true;
            });
            LogHelper.logInfoStep("Scrolling Into Page with till Element ["+elementName+"] is Displayed");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Scroll Into Page with till Element ["+elementName+"] is Displayed",e);
        }
        return this;
    }

    /**
     * *********************************  Interactions with Alerts  *************************************
     */
    public void acceptAlert()
    {
        try{
            getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
            LogHelper.logInfoStep("Accepting the Alert");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to accept the Alert",e);
        }
    }

    public void dismissAlert()
    {
        try{
            getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().dismiss();
            LogHelper.logInfoStep("Dismissing the Alert");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to dismiss the Alert",e);
        }
    }

    public void typeTextInAlert(String text)
    {
        try{
            getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().sendKeys(text);
            LogHelper.logInfoStep("Typing ["+text+"] in the Alert");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Type ["+text+"] in the Alert",e);
        }
    }

    public void typeTextInAlert(String text1,String text2)
    {
        try{
            getFluentWait(driver).until(ExpectedConditions.alertIsPresent());

            Actions action = new Actions(driver);
            driver.switchTo().alert().sendKeys(text1);
            action.keyDown(Keys.TAB).keyUp(Keys.TAB).perform();
            driver.switchTo().alert().sendKeys(text2);

            LogHelper.logInfoStep("Typing ["+text1+" & "+text2+"] in the Alert");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Type ["+text1+" & "+text2+"] in the Alert",e);
        }
    }

    public String getTextInAlert()
    {
        try{
            getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
            String text = driver.switchTo().alert().getText();
            LogHelper.logInfoStep("Retrieving ["+text+"] from Alert");
            return text;
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Retrieve the text from Alert",e);
            return null;
        }
    }


}
