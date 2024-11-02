package yehiaEngine.elementActions;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import yehiaEngine.loggers.LogHelper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class WebElementsActionBot {
    //Variables
    WebDriver driver;

    //Constructor
    public WebElementsActionBot(WebDriver driver) {
        this.driver = driver;
    }

    //************************    Basic Interactions    ************************//
    //ActionBot1 for Clear TextBox then Typing on it & Log Typing Action
    public WebElementsActionBot type (By locator , String text) throws IOException {
        String elementName =
                //Locate Element and Check if its present on DOM
                locateElement(locator)
                //Check if Element is Displayed and Visible on Page
                .checkElementDisplayed(locator)
                //Get Element AccessibleName
                .getElementName(locator);
                //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(locator,elementName);
                //Take Action on Element
        clearText(locator,elementName);
        writeText(locator,elementName,text);
        return this;
    }

    //ActionBot2 for Pressing on Button or Link & Log CLicking Action
    public WebElementsActionBot press(By locator) throws IOException {
        String elementName =
                //Locate Element and Check if its present on DOM
                locateElement(locator)
                //Check if Element is Displayed and Visible on Page
                .checkElementDisplayed(locator)
                //Get Element AccessibleName
                .getElementName(locator);
                //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(locator,elementName);
                //Take Action on Element
        try {
            WaitsManager.getFluentWait(driver).until(f -> {
                new Actions(driver).moveToElement(driver.findElement(locator)).perform();
                driver.findElement(locator).click();
                return true;
            });
        }catch (ElementNotInteractableException e)
        //If Webdriver Click fails and fluent wait throw Timeout Exception, Try to click using JS
        {
            try {
                ((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(locator));
                LogHelper.logInfoStep("Clicking on Element ["+elementName+"]");
            }catch (Exception i)
            {
                LogHelper.logErrorStep("Failed to Click on Element ["+elementName+"]",i);
            }
        }
        LogHelper.logInfoStep("Clicking on Element ["+elementName+"]");
        return this;
    }

    //ActionBot3 for Pressing on Button or Link (By WebElement) & Log CLicking Action
    public WebElementsActionBot press(WebElement element) throws IOException {
        String elementName = element.getAccessibleName();
        checkElementEnabled(element,elementName);
        try {
            new Actions(driver).moveToElement(element).perform();
            element.click();

        }catch (ElementNotInteractableException e)
        //If Webdriver Click fails and fluent wait throw Timeout Exception, Try to click using JS
        {
            try {
                ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
                LogHelper.logInfoStep("Clicking on Element ["+elementName+"]");
            }catch (Exception i)
            {
                LogHelper.logErrorStep("Failed to Click on Element ["+elementName+"]",i);
            }
        }
        LogHelper.logInfoStep("Clicking on Element ["+elementName+"]");
        return this;
    }

    //ActionBot4 for Verify Element is Displayed on Page Without Failing the Test if not displayed
    public boolean isElementDisplayed(By locator){
        try{
            //Wait until Element is Displayed on Page
            WaitsManager.getExplicitWait(driver).until(f -> driver.findElement(locator).isDisplayed());
            //Get Element Accessible Name
            String elementName = getElementName(locator);
            LogHelper.logInfoStep("The Element [" + elementName + "] is Displayed");

        }catch(TimeoutException e){
            LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is not Displayed");
            return false;
        }
        return true;
    }

    //ActionBot5 for Get Text from Element (with Locator) & Log the Text
    public String readText(By locator) throws IOException {
        //Locate Element and Check if its present on DOM
        locateElement(locator);
        //Check if Element is Displayed and Visible on Page
        checkElementDisplayed(locator);
        //Read Text from Element on Page
        try {
            String text = WaitsManager.getFluentWait(driver).until(f -> driver.findElement(locator).getText());
            LogHelper.logInfoStep("Getting Text " + "[" + text + "] from Element located by [" + locator.toString() +"]");
            return text.replace("\n","");
        }catch (Exception e)
        {
            LogHelper.logErrorStep("Cannot Get Text from Element located by [" + locator+"]",e);
            return null;
        }
    }

    //ActionBot6 for Get Text from Element (with WebElement) & Log the Text
    public String readText(WebElement element) throws IOException {
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

    //ActionBot7 for Long Pressing on Button or Link & Log CLicking Action
    public WebElementsActionBot longPress(By locator) throws IOException {
        String elementName =
                //Locate Element and Check if its present on DOM
                locateElement(locator)
                        //Check if Element is Displayed and Visible on Page
                        .checkElementDisplayed(locator)
                        //Get Element AccessibleName
                        .getElementName(locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(locator,elementName);
        //Take Action on Element
        try {
            WaitsManager.getFluentWait(driver).until(f -> {
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

    //ActionBot8 for Hover on Element & Log Hovering Action
    public WebElementsActionBot hoverOnElement (By locator) throws IOException {
        String elementName =
                //Locate Element and Check if its present on DOM
                locateElement(locator)
                        //Check if Element is Displayed and Visible on Page
                        .checkElementDisplayed(locator)
                        //Get Element AccessibleName
                        .getElementName(locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(locator,elementName);
        //Take Action on Element
        try {
            WaitsManager.getFluentWait(driver).until(f -> {
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

    //ActionBot9 for Get All Matched Elements
    public List<WebElement> getAllMatchedElements(By locator){
        //Locate Element and Check if its present on DOM
        locateElement(locator);
        //Check if Element is Displayed and Visible on Page
        checkElementDisplayed(locator);
        //Read Text from Element on Page
        try {
            List<WebElement> list
                    = WaitsManager.getFluentWait(driver).until(f -> driver.findElements(locator));
            LogHelper.logInfoStep("Retrieving All Matched Elements from the Element located by [" + locator + "]");
            return list;
        }catch (Exception e)
        {
            LogHelper.logErrorStep("Cannot Retrieve All Matched Elements from the Element located by " + locator,e);
            return null;
        }
    }

    //************************    Interactions with DropDowns    ************************//

    public WebElementsActionBot selectFromDropdownByValue(By dropdownLocator , String value)
    {
        String elementName = getElementName(dropdownLocator);
        try {
            dropDownElement(dropdownLocator).selectByValue(value);
            LogHelper.logInfoStep("Selecting ["+ value +"] from" + elementName);
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Select ["+ value +"] from" + elementName,e);
        }
        return this;
    }

    public WebElementsActionBot selectFromDropdownByIndex(By dropdownLocator , int index)
    {
        String elementName = getElementName(dropdownLocator);
        try {
            dropDownElement(dropdownLocator).selectByIndex(index);
            LogHelper.logInfoStep("Selecting ["+ index +"] from" + elementName);
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Select ["+ index +"] from" + elementName,e);
        }
        return this;
    }

    public WebElementsActionBot selectFromDropdownByText(By dropdownLocator , String text)
    {
        String elementName = getElementName(dropdownLocator);
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
        String elementName = getElementName(dropdownLocator);
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
        String elementName = getElementName(dropdownLocator);
        try {
            String text = dropDownElement(dropdownLocator).getFirstSelectedOption().getText();
            LogHelper.logInfoStep("Retrieving Selected Option ["+text+"] from List of " + elementName);
            return text;
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Retrieve Selected Option from List of " + elementName,e);
            return null;
        }
    }

    public WebElementsActionBot deselectAllOptions(By dropdownLocator)
    {
        String elementName = getElementName(dropdownLocator);
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
        String elementName =
                //Locate Element and Check if its present on DOM
                locateElement(dropdownLocator)
                        //Check if Element is Displayed and Visible on Page
                        .checkElementDisplayed(dropdownLocator)
                        //Get Element AccessibleName
                        .getElementName(dropdownLocator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(dropdownLocator,elementName);
        return new Select(driver.findElement(dropdownLocator));
    }

    //************************    Interactions with Scrolling    ************************//

    public WebElementsActionBot scrollToElementIntoView(By elementLocator) {
        String elementName =
                //Locate Element and Check if its present on DOM
                locateElement(elementLocator)
                //Get Element AccessibleName
                .getElementName(elementLocator);
        try{
            new Actions(driver).scrollToElement(driver.findElement(elementLocator)).perform();
            LogHelper.logInfoStep("Scrolling to element ["+elementName+"]");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Scroll to element ["+elementName+"]",e);
        }
        return this;
    }

    public WebElementsActionBot scrollByGivenAmountFromElement(By elementLocator , int deltaX, int deltaY)
    {
        String elementName =
                //Locate Element and Check if its present on DOM
                locateElement(elementLocator)
                        //Get Element AccessibleName
                        .getElementName(elementLocator);
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

    public WebElementsActionBot scrollTillElementDisplayed (By targetElement , int scrollStep) {
        String elementName =
                //Locate Element and Check if its present on DOM
                locateElement(targetElement)
                        //Get Element AccessibleName
                        .getElementName(targetElement);
        try{
            WaitsManager.getFluentWait(driver).until(f->{
                new Actions(driver).scrollByAmount(0,scrollStep).perform();
                driver.findElement(targetElement).isDisplayed();
                return true;
            });
            LogHelper.logInfoStep("Scrolling Into Page with till Element ["+elementName+"] is Displayed");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Scroll Into Page with till Element ["+elementName+"] is Displayed",e);
        }
        return this;
    }

    //************************    Interactions with Alerts    ************************//

    public void acceptAlert()
    {
        try{
            WaitsManager.getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
            LogHelper.logInfoStep("Accepting the Alert");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to accept the Alert",e);
        }
    }

    public void dismissAlert()
    {
        try{
            WaitsManager.getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().dismiss();
            LogHelper.logInfoStep("Dismissing the Alert");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to dismiss the Alert",e);
        }
    }

    public void typeTextInAlert(String text)
    {
        try{
            WaitsManager.getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().sendKeys(text);
            LogHelper.logInfoStep("Typing ["+text+"] in the Alert");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Type ["+text+"] in the Alert",e);
        }
    }

    public void typeTextInAlert(String text1,String text2)
    {
        try{
            WaitsManager.getFluentWait(driver).until(ExpectedConditions.alertIsPresent());

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
            WaitsManager.getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
            String text = driver.switchTo().alert().getText();
            LogHelper.logInfoStep("Retrieving ["+text+"] from Alert");
            return text;
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Retrieve the text from Alert",e);
            return null;
        }
    }

    //************************    Locating The Element    ************************//
    //Check if Element is Present on DOM
    private WebElementsActionBot  locateElement(By locator) {
        try{
            //Wait for the element to be Present on DOM
            WaitsManager.getFluentWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));

        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Locate the element by Locator ["+locator+"]",e);
        }
        return this;
    }

    //Check if Element is Displayed on Page With Failing the Test if not displayed
    private WebElementsActionBot checkElementDisplayed (By elementLocator){
        // Scroll the element into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);",
                driver.findElement(elementLocator));

        //Wait for the element to be Displayed on Page
        try{
            WaitsManager.getFluentWait(driver).until(f -> driver.findElement(elementLocator).isDisplayed());

        }catch(TimeoutException e){
            LogHelper.logErrorStep("The Element located by [" + elementLocator.toString() + "] is not Displayed",e);
        }
        return this;
    }

    //Get The Element Accessible Name
    private String getElementName (By locator) {
        String elementName = driver.findElement(locator).getAccessibleName();
        if ((elementName != null && !elementName.isEmpty()))
            return elementName;
        else
            return locator.toString();
    }

    //Check if Element is Enabled on Page (By Locator)
    private WebElementsActionBot checkElementEnabled(By locator,String elementName){
        try{
            WaitsManager.getFluentWait(driver).until(f -> driver.findElement(locator).isEnabled());
        }catch(TimeoutException e){
            LogHelper.logErrorStep("The Element [" + elementName + "] is not Enabled",e);
        }
        return this;
    }

    //Check if Element is Enabled on Page (By WebElement)
    private WebElementsActionBot checkElementEnabled(WebElement element,String elementName){
        try{
            element.isEnabled();
        }catch(TimeoutException e){
            LogHelper.logErrorStep("The Element [" + elementName + "] is not Enabled",e);
        }
        return this;
    }

    //************************    Clear & Write Text On Element    ************************//
    private void writeText(By locator, String elementName, String text)
    {
        // Write Text on TextBox Element using the Selenium sendKeys method
        try{
            WaitsManager.getFluentWait(driver).until(f -> {
                driver.findElement(locator).sendKeys(text);
                return true;
            });
        //  Write Text using JavascriptExecutor in case of the data is not typed successfully
        if (!driver.findElement(locator).getAttribute("value").equals(text)) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + text + "')",
                        driver.findElement(locator));
            }
        LogHelper.logInfoStep("Typing ["+text+"] on Element ["+elementName+"]");

        }catch (Exception e)
        {
            LogHelper.logErrorStep("Failed to Type ["+text+"] on Element ["+elementName+"]",e);
        }
    }

    private void clearText(By locator , String elementName)
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
