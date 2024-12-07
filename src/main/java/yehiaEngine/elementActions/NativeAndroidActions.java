package yehiaEngine.elementActions;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import yehiaEngine.elementActions.Helpers.W3CTouchActionsHelper;
import yehiaEngine.loggers.LogHelper;

import java.util.List;

import static yehiaEngine.elementActions.Helpers.NativeAndroidActionsHelper.*;
import static yehiaEngine.elementActions.Helpers.W3CTouchActionsHelper.singleFingerSwipe;
import static yehiaEngine.elementActions.Helpers.WaitsManager.getFluentWait;
import static yehiaEngine.elementActions.Helpers.WaitsManager.getSwipeWait;


public class NativeAndroidActions {

    AppiumDriver driver;

    public enum ScrollDirection {
        HORIZONTAL, VERTICAL
    }

    public enum LocatorType {
        TEXT, RESOURCE_ID, ACCESSIBILITY_ID, CLASS_NAME
    }

    public NativeAndroidActions(AppiumDriver driver) {
        this.driver = driver;
    }

    /**
     * *********************************  Tap Actions  ****************************************
     */
    // Tap on Button or Link by Swiping into Screen & Log Tapping Action
    public NativeAndroidActions tap(LocatorType locatorType, String locatorValue, ScrollDirection direction) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(locatorType, locatorValue, direction);
        // Get Element Name
        String elementName = getElementName(driver, locatorType, locatorValue);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locatorType, locatorValue, elementName);
        //Execute the Tap Action
        try {
            new Actions(driver).moveToElement(driver.findElement(AppiumBy.androidUIAutomator(
                    getUiAutomatorQuery(locatorType, locatorValue, null)))).click().perform();
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Tap on Element [" + elementName + "]", e);
        }
        LogHelper.logInfoStep("Tapping on Element [" + elementName + "]");
        return this;
    }

    // Tap on Button or Link by Swiping into Screen & Log Tapping Action
    public NativeAndroidActions tap(By locator, ScrollDirection direction) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(locator, direction);
        // Get Element Name
        String elementName = getElementName(driver, locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locator, elementName);

        //Execute the Tap Action
        try {
            new Actions(driver).moveToElement(driver.findElement(locator)).click().perform();
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Tap on Element [" + elementName + "]", e);
        }
        LogHelper.logInfoStep("Tapping on Element [" + elementName + "]");
        return this;
    }

    //Tap on Button or Link by Swiping into swiped element & Log Tapping Action
    public NativeAndroidActions tap(LocatorType locatorType, String locatorValue, ScrollDirection direction, LocatorType swipedElementLocatorType, String swipedElementLocatorValue) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(locatorType, locatorValue, direction, swipedElementLocatorType, swipedElementLocatorValue);
        // Get Element Name
        String elementName = getElementName(driver, locatorType, locatorValue);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locatorType, locatorValue, elementName);
        //Execute the Tap Action
        try {
            new Actions(driver).moveToElement(driver.findElement(AppiumBy.androidUIAutomator(
                    getUiAutomatorQuery(locatorType, locatorValue, null)))).click().perform();

        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Tap on Element [" + elementName + "]", e);
        }
        LogHelper.logInfoStep("Tapping on Element [" + elementName + "]");
        return this;
    }

    // Tap on Button or Link by Swiping into Screen & Log Tapping Action
    public NativeAndroidActions tap(By locator, ScrollDirection direction, By swipedElementLocator) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(locator, direction, swipedElementLocator);
        // Get Element Name
        String elementName = getElementName(driver, locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locator, elementName);

        //Execute the Tap Action
        try {
            new Actions(driver).moveToElement(driver.findElement(locator)).click().perform();
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Tap on Element [" + elementName + "]", e);
        }
        LogHelper.logInfoStep("Tapping on Element [" + elementName + "]");
        return this;
    }

    //Tap on Button or Link without Swipe & Log Tapping Action
    public NativeAndroidActions tap(By locator) {
        new W3CTouchActions(driver).tap(locator);
        return this;
    }

    /**
     * *********************************  Long Tap Actions  ****************************************
     */
    // Long Tap on Button or Link by Swiping into Screen & Log Tapping Action
    public NativeAndroidActions longTap(LocatorType locatorType, String locatorValue, ScrollDirection direction) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(locatorType, locatorValue, direction);
        // Get Element Name
        String elementName = getElementName(driver, locatorType, locatorValue);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locatorType, locatorValue, elementName);
        //Execute the Long Tap Action
        try {
            getFluentWait(driver).until(f ->
            {
                new Actions(driver).moveToElement(driver.findElement(AppiumBy.androidUIAutomator(
                        getUiAutomatorQuery(locatorType, locatorValue, direction)))).clickAndHold().perform();
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Long Tap on Element [" + elementName + "]", e);
        }
        LogHelper.logInfoStep("Long Tapping on Element [" + elementName + "]");
        return this;
    }

    // Long Tap on Button or Link by Swiping into Screen & Log Tapping Action
    public NativeAndroidActions longTap(By locator, ScrollDirection direction) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(locator, direction);
        // Get Element Name
        String elementName = getElementName(driver, locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locator, elementName);
        //Execute the Long Tap Action
        try {
            getFluentWait(driver).until(f ->
            {
                new Actions(driver).moveToElement(driver.findElement(locator)).clickAndHold().perform();
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Long Tap on Element [" + elementName + "]", e);
        }
        LogHelper.logInfoStep("Long Tapping on Element [" + elementName + "]");
        return this;
    }

    //Long Tap on Button or Link by Swiping into swiped element & Log Tapping Action
    public NativeAndroidActions longTap(LocatorType locatorType, String locatorValue, ScrollDirection direction, LocatorType swipedElementLocatorType, String swipedElementLocatorValue) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(locatorType, locatorValue, direction, swipedElementLocatorType, swipedElementLocatorValue);
        // Get Element Name
        String elementName = getElementName(driver, locatorType, locatorValue);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locatorType, locatorValue, elementName);
        //Execute the Long Tap Action
        try {
            getFluentWait(driver).until(f ->
            {
                new Actions(driver).moveToElement(driver.findElement(AppiumBy.androidUIAutomator(
                        getUiAutomatorQuery(locatorType, locatorValue, direction)))).clickAndHold().perform();
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Long Tap on Element [" + elementName + "]", e);
        }
        LogHelper.logInfoStep("Long Tapping on Element [" + elementName + "]");
        return this;
    }

    //Long Tap on Button or Link by Swiping into swiped element & Log Tapping Action
    public NativeAndroidActions longTap(By locator, ScrollDirection direction, By swipedElementLocator) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(locator, direction, swipedElementLocator);
        // Get Element Name
        String elementName = getElementName(driver, locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locator, elementName);
        //Execute the Long Tap Action
        try {
            getFluentWait(driver).until(f ->
            {
                new Actions(driver).moveToElement(driver.findElement(locator)).clickAndHold().perform();
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Long Tap on Element [" + elementName + "]", e);
        }
        LogHelper.logInfoStep("Long Tapping on Element [" + elementName + "]");
        return this;
    }

    //Long Tap on Button or Link without Swipe & Log Tapping Action
    public NativeAndroidActions longTap(By locator) {
        new W3CTouchActions(driver).longTab(locator);
        return this;
    }

    /**
     * *********************************  Type Actions  *************************************
     */
    //Clear TextBox then Typing on it & Log Typing Action "Swipe Into Screen"
    public NativeAndroidActions type(LocatorType locatorType, String locatorValue, ScrollDirection direction, String text) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(locatorType, locatorValue, direction);
        // Get Element Name
        String elementName = getElementName(driver, locatorType, locatorValue);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locatorType, locatorValue, elementName);

        //Execute the Typing Tap Action
        clearText(driver, locatorType, locatorValue, elementName);
        writeText(driver, locatorType, locatorValue, elementName, text);
        return this;
    }

    //Clear TextBox then Typing on it & Log Typing Action "Swipe Into Screen"
    public NativeAndroidActions type(By locator, ScrollDirection direction, String text) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(locator, direction);
        // Get Element Name
        String elementName = getElementName(driver, locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locator, elementName);

        //Execute the Typing Tap Action
        clearText(driver, locator, elementName);
        writeText(driver, locator, elementName, text);
        return this;
    }

    //Clear TextBox then Typing on it & Log Typing Action "Swipe Into Element"
    public NativeAndroidActions type(LocatorType locatorType, String locatorValue, ScrollDirection direction, String text, LocatorType swipedElementLocatorType, String swipedElementLocatorValue) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(locatorType, locatorValue, direction, swipedElementLocatorType, swipedElementLocatorValue);
        // Get Element Name
        String elementName = getElementName(driver, locatorType, locatorValue);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locatorType, locatorValue, elementName);

        //Execute the Typing Tap Action
        clearText(driver, locatorType, locatorValue, elementName);
        writeText(driver, locatorType, locatorValue, elementName, text);
        return this;
    }

    //Clear TextBox then Typing on it & Log Typing Action "Swipe Into Element"
    public NativeAndroidActions type(By locator, ScrollDirection direction, String text, By swipedElementLocator) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(locator, direction, swipedElementLocator);
        // Get Element Name
        String elementName = getElementName(driver, locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locator, elementName);

        //Execute the Typing Tap Action
        clearText(driver, locator, elementName);
        writeText(driver, locator, elementName, text);
        return this;
    }

    //Clear TextBox then Typing on it & Log Typing Action "Without Swiping"
    public NativeAndroidActions type(By locator, String text) {
        new W3CTouchActions(driver).type(locator, text);
        return this;
    }

    /**
     * *********************************  Read Text Actions  *************************************
     */
    //Get Text from Element by Swiping into Screen & Log the Text
    public String readText(LocatorType locatorType, String locatorValue, ScrollDirection direction) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(locatorType, locatorValue, direction);

        //Execute the Read Text Action
        try {
            String text = getElementName(driver, locatorType, locatorValue);

            LogHelper.logInfoStep("Getting Text " + "[" + text + "] from Element located by [" + locatorType + " - " + locatorValue + "]");
            return text.replace("\n", "");
        } catch (Exception e) {
            LogHelper.logErrorStep("Cannot Get Text from Element located by [" + locatorType + " - " + locatorValue + "]", e);
            return null;
        }
    }

    //Get Text from Element by Swiping into Screen & Log the Text
    public String readText(By locator, ScrollDirection direction) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(locator, direction);

        //Execute the Read Text Action
        try {
            String text = getElementName(driver, locator);

            LogHelper.logInfoStep("Getting Text " + "[" + text + "] from Element located by [" + locator.toString() + "]");
            return text.replace("\n", "");
        } catch (Exception e) {
            LogHelper.logErrorStep("Cannot Get Text from Element located by [" + locator.toString() + "]", e);
            return null;
        }
    }

    //Get Text from Element by Swiping into Element & Log the Text
    public String readText(LocatorType locatorType, String locatorValue, ScrollDirection direction, LocatorType swipedElementLocatorType, String swipedElementLocatorValue) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(locatorType, locatorValue, direction, swipedElementLocatorType, swipedElementLocatorValue);

        //Execute the Read Text Action
        try {
            String text = getElementName(driver, locatorType, locatorValue);

            LogHelper.logInfoStep("Getting Text " + "[" + text + "] from Element located by [" + locatorType + " - " + locatorValue + "]");
            return text.replace("\n", "");
        } catch (Exception e) {
            LogHelper.logErrorStep("Cannot Get Text from Element located by [" + locatorType + " - " + locatorValue + "]", e);
            return null;
        }
    }

    //Get Text from Element by Swiping into Element & Log the Text
    public String readText(By locator, ScrollDirection direction, By swipedElementLocator) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(locator, direction, swipedElementLocator);

        //Execute the Read Text Action
        try {
            String text = getElementName(driver, locator);

            LogHelper.logInfoStep("Getting Text " + "[" + text + "] from Element located by [" + locator.toString() + "]");
            return text.replace("\n", "");
        } catch (Exception e) {
            LogHelper.logErrorStep("Cannot Get Text from Element located by [" + locator.toString() + "]", e);
            return null;
        }
    }

    //Get Text from Element Without Swiping & Log the Text
    public String readText(By locator) {
        return new W3CTouchActions(driver).readText(locator);
    }

    /**
     * *********************************  Check Element Displayed Actions  *************************************
     */
    // Verify Element is Displayed on Page With swipe into screen
    public boolean isElementDisplayed(LocatorType locatorType, String locatorValue, ScrollDirection direction) {
        try {
            LogHelper.logInfoStep("Swiping (" + direction + ") into Screen");
            By locator = AppiumBy.androidUIAutomator(getUiAutomatorQuery(locatorType, locatorValue, direction));
            //Wait until Element is Displayed on Page
            getFluentWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
            //Get Element Name
            String elementName = getElementName(driver, locatorType, locatorValue);
            LogHelper.logInfoStep("The Element [" + elementName + "] is Displayed");
        } catch (TimeoutException e) {
            LogHelper.logInfoStep("The Element located by [" + locatorType + " - " + locatorValue + "] is not Displayed");
            return false;
        }
        return true;
    }

    // Verify Element is Displayed on Page With swipe into screen
    public boolean isElementDisplayed(By locator, ScrollDirection direction) {
        try {
            LogHelper.logInfoStep("Swiping (" + direction + ") into Screen");
            swipeTillElementDisplayed(driver, locator, direction);
            //Wait until Element is Displayed on Page
            getFluentWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
            //Get Element Name
            String elementName = getElementName(driver, locator);
            LogHelper.logInfoStep("The Element [" + elementName + "] is Displayed");
        } catch (TimeoutException e) {
            LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is not Displayed");
            return false;
        }
        return true;
    }

    // Verify Element is Displayed on Page With swipe into Element
    public boolean isElementDisplayed(LocatorType locatorType, String locatorValue, ScrollDirection direction, LocatorType swipedElementLocatorType, String swipedElementLocatorValue) {
        try {
            String swipedElementName = getElementName(driver, swipedElementLocatorType, swipedElementLocatorValue);
            LogHelper.logInfoStep("Swiping (" + direction + ") into Element [" + swipedElementName + "]");
            By locator = AppiumBy.androidUIAutomator(getUiAutomatorQuery(locatorType, locatorValue, direction, swipedElementLocatorType, swipedElementLocatorValue));
            //Wait until Element is Displayed on Page
            getFluentWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
            //Get Element Name
            String elementName = getElementName(driver, locatorType, locatorValue);
            LogHelper.logInfoStep("The Element [" + elementName + "] is Displayed");
        } catch (TimeoutException e) {
            LogHelper.logInfoStep("The Element located by [" + locatorType + " - " + locatorValue + "] is not Displayed");
            return false;
        }
        return true;
    }

    // Verify Element is Displayed on Page With swipe into Element
    public boolean isElementDisplayed(By locator, ScrollDirection direction, By swipedElementLocator) {
        try {
            String swipedElementName = getElementName(driver, swipedElementLocator);
            LogHelper.logInfoStep("Swiping (" + direction + ") into Element [" + swipedElementName + "]");
            swipeTillElementDisplayed(driver, locator, direction, swipedElementLocator);
            //Wait until Element is Displayed on Page
            getFluentWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
            //Get Element Name
            String elementName = getElementName(driver, locator);
            LogHelper.logInfoStep("The Element [" + elementName + "] is Displayed");
        } catch (TimeoutException e) {
            LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is not Displayed");
            return false;
        }
        return true;
    }

    // Verify Element is Displayed on Page Without Swipe
    public boolean isElementDisplayed(By locator) {
        try {
            //Wait until Element is Displayed on Page
            getFluentWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
            //Get Element Name
            String elementName = W3CTouchActionsHelper.getElementName(driver, locator);
            LogHelper.logInfoStep("The Element [" + elementName + "] is Displayed");
        } catch (TimeoutException e) {
            LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is not Displayed");
            return false;
        }
        return true;
    }

    /**
     * *********************************  Check Element Not Displayed Actions  *************************************
     */
    // Verify Element is Not Displayed on Page With swipe into screen
    public boolean isElementNotDisplayed(LocatorType locatorType, String locatorValue, ScrollDirection direction) {
        try {
            LogHelper.logInfoStep("Swiping (" + direction + ") into Screen");
            By locator = AppiumBy.androidUIAutomator(getUiAutomatorQuery(locatorType, locatorValue, direction));
            //Wait until Element is Displayed on Page
            getFluentWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
            LogHelper.logInfoStep("The Element located by [" + locatorType + " - " + locatorValue + "] is Not Displayed");
        } catch (TimeoutException e) {
            LogHelper.logInfoStep("The Element located by [" + locatorType + " - " + locatorValue + "] is Displayed");
            return false;
        }
        return true;
    }

    // Verify Element is Not Displayed on Page With swipe into screen
    public boolean isElementNotDisplayed(By locator, ScrollDirection direction) {
        // Swipe till Element is Displayed into View or till Timeout or till Reach end of Page
        try {
            String[] previousPageSource = {""};
            final int[] flag = {0};
            getSwipeWait(driver).until(f -> {
                if (flag[0] == 1) {
                    driver.findElement(AppiumBy.androidUIAutomator(getUiAutomatorQueryForward(direction)));
                }

                String currentPageSource = driver.getPageSource();
                if (currentPageSource.equalsIgnoreCase(previousPageSource[0]))
                    // The page source hasn't changed, so we've reached the bottom
                    throw new TimeoutException();
                else
                    previousPageSource[0] = currentPageSource;

                flag[0] = 1;
                return driver.findElement(locator).isDisplayed();
            });
            LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is Displayed");
            return false;
        } catch (TimeoutException e) {
            // Scroll in Opposite Direction till Element is Displayed into View or till Timeout or till Reach end of Page
            try {
                String[] previousPageSource = {""};
                getSwipeWait(driver).until(f -> {
                    driver.findElement(AppiumBy.androidUIAutomator(getUiAutomatorQueryBackward(direction)));

                    String currentPageSource = driver.getPageSource();
                    if (currentPageSource.equalsIgnoreCase(previousPageSource[0]))
                        // The page source hasn't changed, so we've reached the bottom
                        throw new TimeoutException();
                    else
                        previousPageSource[0] = currentPageSource;

                    return driver.findElement(locator).isDisplayed();
                });
                LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is Displayed");
                return false;
            } catch (TimeoutException f) {
                LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is not Displayed");
                return true;
            }
        }
    }

    // Verify Element is Not Displayed on Page With swipe into Element
    public boolean isElementNotDisplayed(LocatorType locatorType, String locatorValue, ScrollDirection direction, LocatorType swipedElementLocatorType, String swipedElementLocatorValue) {
        try {
            String swipedElementName = getElementName(driver, swipedElementLocatorType, swipedElementLocatorValue);
            LogHelper.logInfoStep("Swiping (" + direction + ") into Element [" + swipedElementName + "]");
            By locator = AppiumBy.androidUIAutomator(getUiAutomatorQuery(locatorType, locatorValue, direction, swipedElementLocatorType, swipedElementLocatorValue));
            //Wait until Element is Displayed on Page
            getFluentWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
            LogHelper.logInfoStep("The Element located by [" + locatorType + " - " + locatorValue + "] is Not Displayed");
        } catch (TimeoutException e) {
            LogHelper.logInfoStep("The Element located by [" + locatorType + " - " + locatorValue + "] is Displayed");
            return false;
        }
        return true;
    }

    // Verify Element is Not Displayed on Page With swipe into Element
    public boolean isElementNotDisplayed(By locator, ScrollDirection direction,By swipedElementLocator) {
        // Swipe till Element is Displayed into View or till Timeout or till Reach end of Page
        try {
            String[] previousPageSource = {""};
            final int[] flag = {0};
            getSwipeWait(driver).until(f -> {
                if (flag[0] == 1) {
                    driver.findElement(AppiumBy.androidUIAutomator(getUiAutomatorQueryForward(direction,driver,swipedElementLocator)));
                }

                String currentPageSource = driver.getPageSource();
                if (currentPageSource.equalsIgnoreCase(previousPageSource[0]))
                    // The page source hasn't changed, so we've reached the bottom
                    throw new TimeoutException();
                else
                    previousPageSource[0] = currentPageSource;

                flag[0] = 1;
                return driver.findElement(locator).isDisplayed();
            });
            LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is Displayed");
            return false;
        } catch (TimeoutException e) {
            // Scroll in Opposite Direction till Element is Displayed into View or till Timeout or till Reach end of Page
            try {
                String[] previousPageSource = {""};
                getSwipeWait(driver).until(f -> {
                    driver.findElement(AppiumBy.androidUIAutomator(getUiAutomatorQueryBackward(direction,driver,swipedElementLocator)));

                    String currentPageSource = driver.getPageSource();
                    if (currentPageSource.equalsIgnoreCase(previousPageSource[0]))
                        // The page source hasn't changed, so we've reached the bottom
                        throw new TimeoutException();
                    else
                        previousPageSource[0] = currentPageSource;

                    return driver.findElement(locator).isDisplayed();
                });
                LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is Displayed");
                return false;
            } catch (TimeoutException f) {
                LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is not Displayed");
                return true;
            }
        }
    }

    // Verify Element is Not Displayed on Page Without Swipe
    public boolean isElementNotDisplayed(By locator) {
        try {
            //Wait until Element is Displayed on Page
            getFluentWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
            LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is Not Displayed");
        } catch (TimeoutException e) {
            LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is Displayed");
            return false;
        }
        return true;
    }

    /**
     * *********************************  Zoom In Actions  *************************************
     */
    //Zoom In Element by given distance "Swipe into Screen"
    public NativeAndroidActions zoomIn(LocatorType locatorType, String locatorValue, ScrollDirection direction, int zoomingDistance) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(locatorType, locatorValue, direction);
        // Get Element Name
        String targetElementName = getElementName(driver, locatorType, locatorValue);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locatorType, locatorValue, targetElementName);

        //Execute the Zoom In Action
        try {
            getFluentWait(driver).until(f -> {
                Point elementCenter = getElementCenter(driver, driver.findElement(AppiumBy.androidUIAutomator(
                        getUiAutomatorQuery(locatorType, locatorValue, direction))));
                Point start1 = new Point(elementCenter.getX() - 50, elementCenter.getY());
                Point start2 = new Point(elementCenter.getX() + 50, elementCenter.getY());

                var x = start1.getX() + W3CTouchActions.Direction.LEFT.getX() * zoomingDistance;
                var y = start1.getY() + W3CTouchActions.Direction.LEFT.getY() * zoomingDistance;
                Point end1 = new Point(x, y);

                var a = start1.getX() + W3CTouchActions.Direction.RIGHT.getX() * zoomingDistance;
                var b = start1.getY() + W3CTouchActions.Direction.RIGHT.getY() * zoomingDistance;
                Point end2 = new Point(a, b);

                Sequence sequence1 = singleFingerSwipe("finger-1", start1, end1);
                Sequence sequence2 = singleFingerSwipe("finger-2", start2, end2);

                driver.perform(List.of(sequence1, sequence2));
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Zoom In the Element [" + targetElementName + "]", e);
        }
        LogHelper.logInfoStep("Zooming In the Element [" + targetElementName + "]");
        return this;
    }

    //Zoom In Element by given distance "Swipe into Screen"
    public NativeAndroidActions zoomIn(By locator, ScrollDirection direction, int zoomingDistance) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(locator, direction);
        // Get Element Name
        String targetElementName = getElementName(driver, locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locator, targetElementName);

        //Execute the Zoom In Action
        try {
            getFluentWait(driver).until(f -> {
                Point elementCenter = getElementCenter(driver, driver.findElement(locator));
                Point start1 = new Point(elementCenter.getX() - 50, elementCenter.getY());
                Point start2 = new Point(elementCenter.getX() + 50, elementCenter.getY());

                var x = start1.getX() + W3CTouchActions.Direction.LEFT.getX() * zoomingDistance;
                var y = start1.getY() + W3CTouchActions.Direction.LEFT.getY() * zoomingDistance;
                Point end1 = new Point(x, y);

                var a = start1.getX() + W3CTouchActions.Direction.RIGHT.getX() * zoomingDistance;
                var b = start1.getY() + W3CTouchActions.Direction.RIGHT.getY() * zoomingDistance;
                Point end2 = new Point(a, b);

                Sequence sequence1 = singleFingerSwipe("finger-1", start1, end1);
                Sequence sequence2 = singleFingerSwipe("finger-2", start2, end2);

                driver.perform(List.of(sequence1, sequence2));
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Zoom In the Element [" + targetElementName + "]", e);
        }
        LogHelper.logInfoStep("Zooming In the Element [" + targetElementName + "]");
        return this;
    }

    //Zoom In Element by given distance "Swipe into Element"
    public NativeAndroidActions zoomIn(LocatorType locatorType, String locatorValue, ScrollDirection direction, int zoomingDistance, LocatorType swipedElementLocatorType, String swipedElementLocatorValue) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(locatorType, locatorValue, direction, swipedElementLocatorType, swipedElementLocatorValue);
        // Get Element Name
        String targetElementName = getElementName(driver, locatorType, locatorValue);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locatorType, locatorValue, targetElementName);

        //Execute the Zoom In Action
        try {
            getFluentWait(driver).until(f -> {
                Point elementCenter = getElementCenter(driver, driver.findElement(AppiumBy.androidUIAutomator(
                        getUiAutomatorQuery(locatorType, locatorValue, direction))));
                Point start1 = new Point(elementCenter.getX() - 50, elementCenter.getY());
                Point start2 = new Point(elementCenter.getX() + 50, elementCenter.getY());

                var x = start1.getX() + W3CTouchActions.Direction.LEFT.getX() * zoomingDistance;
                var y = start1.getY() + W3CTouchActions.Direction.LEFT.getY() * zoomingDistance;
                Point end1 = new Point(x, y);

                var a = start1.getX() + W3CTouchActions.Direction.RIGHT.getX() * zoomingDistance;
                var b = start1.getY() + W3CTouchActions.Direction.RIGHT.getY() * zoomingDistance;
                Point end2 = new Point(a, b);

                Sequence sequence1 = singleFingerSwipe("finger-1", start1, end1);
                Sequence sequence2 = singleFingerSwipe("finger-2", start2, end2);

                driver.perform(List.of(sequence1, sequence2));
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Zoom In the Element [" + targetElementName + "]", e);
        }
        LogHelper.logInfoStep("Zooming In the Element [" + targetElementName + "]");
        return this;
    }

    //Zoom In Element by given distance "Swipe into Element"
    public NativeAndroidActions zoomIn(By locator, ScrollDirection direction, int zoomingDistance, By swipedElementLocator) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(locator, direction, swipedElementLocator);
        // Get Element Name
        String targetElementName = getElementName(driver, locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locator, targetElementName);

        //Execute the Zoom In Action
        try {
            getFluentWait(driver).until(f -> {
                Point elementCenter = getElementCenter(driver, driver.findElement(locator));
                Point start1 = new Point(elementCenter.getX() - 50, elementCenter.getY());
                Point start2 = new Point(elementCenter.getX() + 50, elementCenter.getY());

                var x = start1.getX() + W3CTouchActions.Direction.LEFT.getX() * zoomingDistance;
                var y = start1.getY() + W3CTouchActions.Direction.LEFT.getY() * zoomingDistance;
                Point end1 = new Point(x, y);

                var a = start1.getX() + W3CTouchActions.Direction.RIGHT.getX() * zoomingDistance;
                var b = start1.getY() + W3CTouchActions.Direction.RIGHT.getY() * zoomingDistance;
                Point end2 = new Point(a, b);

                Sequence sequence1 = singleFingerSwipe("finger-1", start1, end1);
                Sequence sequence2 = singleFingerSwipe("finger-2", start2, end2);

                driver.perform(List.of(sequence1, sequence2));
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Zoom In the Element [" + targetElementName + "]", e);
        }
        LogHelper.logInfoStep("Zooming In the Element [" + targetElementName + "]");
        return this;
    }

    //Zoom In Element by given distance Without Swipe
    public NativeAndroidActions zoomIn(By locator, int zoomingDistance) {
        new W3CTouchActions(driver).zoomIn(locator, zoomingDistance);
        return this;
    }

    /**
     * *********************************  Zoom Out Actions  *************************************
     */
    //Zoom Out Element by given distance "Swipe into Screen"
    public NativeAndroidActions zoomOut(LocatorType locatorType, String locatorValue, ScrollDirection direction, int zoomingDistance) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(locatorType, locatorValue, direction);
        // Get Element Name
        String targetElementName = getElementName(driver, locatorType, locatorValue);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locatorType, locatorValue, targetElementName);

        //Execute the Zoom Out Action
        try {
            getFluentWait(driver).until(f -> {
                Point elementCenter = getElementCenter(driver, driver.findElement(AppiumBy.androidUIAutomator(
                        getUiAutomatorQuery(locatorType, locatorValue, direction))));
                Point start1 = new Point(elementCenter.getX() - 50, elementCenter.getY());
                Point start2 = new Point(elementCenter.getX() + 50, elementCenter.getY());

                var x = start1.getX() + W3CTouchActions.Direction.LEFT.getX() * zoomingDistance;
                var y = start1.getY() + W3CTouchActions.Direction.LEFT.getY() * zoomingDistance;
                Point end1 = new Point(x, y);

                var a = start1.getX() + W3CTouchActions.Direction.RIGHT.getX() * zoomingDistance;
                var b = start1.getY() + W3CTouchActions.Direction.RIGHT.getY() * zoomingDistance;
                Point end2 = new Point(a, b);

                Sequence sequence1 = singleFingerSwipe("finger-1", end1, start1);
                Sequence sequence2 = singleFingerSwipe("finger-2", end2, start2);

                driver.perform(List.of(sequence1, sequence2));
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Zoom Out the Element [" + targetElementName + "]", e);
        }
        LogHelper.logInfoStep("Zooming Out the Element [" + targetElementName + "]");
        return this;
    }

    //Zoom Out Element by given distance "Swipe into Screen"
    public NativeAndroidActions zoomOut(By locator, ScrollDirection direction, int zoomingDistance) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(locator, direction);
        // Get Element Name
        String targetElementName = getElementName(driver, locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locator, targetElementName);

        //Execute the Zoom Out Action
        try {
            getFluentWait(driver).until(f -> {
                Point elementCenter = getElementCenter(driver, driver.findElement(locator));
                Point start1 = new Point(elementCenter.getX() - 50, elementCenter.getY());
                Point start2 = new Point(elementCenter.getX() + 50, elementCenter.getY());

                var x = start1.getX() + W3CTouchActions.Direction.LEFT.getX() * zoomingDistance;
                var y = start1.getY() + W3CTouchActions.Direction.LEFT.getY() * zoomingDistance;
                Point end1 = new Point(x, y);

                var a = start1.getX() + W3CTouchActions.Direction.RIGHT.getX() * zoomingDistance;
                var b = start1.getY() + W3CTouchActions.Direction.RIGHT.getY() * zoomingDistance;
                Point end2 = new Point(a, b);

                Sequence sequence1 = singleFingerSwipe("finger-1", end1, start1);
                Sequence sequence2 = singleFingerSwipe("finger-2", end2, start2);

                driver.perform(List.of(sequence1, sequence2));
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Zoom Out the Element [" + targetElementName + "]", e);
        }
        LogHelper.logInfoStep("Zooming Out the Element [" + targetElementName + "]");
        return this;
    }

    //Zoom Out Element by given distance "Swipe into Element"
    public NativeAndroidActions zoomOut(LocatorType locatorType, String locatorValue, ScrollDirection direction, int zoomingDistance, LocatorType swipedElementLocatorType, String swipedElementLocatorValue) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(locatorType, locatorValue, direction, swipedElementLocatorType, swipedElementLocatorValue);
        // Get Element Name
        String targetElementName = getElementName(driver, locatorType, locatorValue);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locatorType, locatorValue, targetElementName);

        //Execute the Zoom Out Action
        try {
            getFluentWait(driver).until(f -> {
                Point elementCenter = getElementCenter(driver, driver.findElement(AppiumBy.androidUIAutomator(
                        getUiAutomatorQuery(locatorType, locatorValue, direction))));
                Point start1 = new Point(elementCenter.getX() - 50, elementCenter.getY());
                Point start2 = new Point(elementCenter.getX() + 50, elementCenter.getY());

                var x = start1.getX() + W3CTouchActions.Direction.LEFT.getX() * zoomingDistance;
                var y = start1.getY() + W3CTouchActions.Direction.LEFT.getY() * zoomingDistance;
                Point end1 = new Point(x, y);

                var a = start1.getX() + W3CTouchActions.Direction.RIGHT.getX() * zoomingDistance;
                var b = start1.getY() + W3CTouchActions.Direction.RIGHT.getY() * zoomingDistance;
                Point end2 = new Point(a, b);

                Sequence sequence1 = singleFingerSwipe("finger-1", end1, start1);
                Sequence sequence2 = singleFingerSwipe("finger-2", end2, start2);

                driver.perform(List.of(sequence1, sequence2));
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Zoom Out the Element [" + targetElementName + "]", e);
        }
        LogHelper.logInfoStep("Zooming Out the Element [" + targetElementName + "]");
        return this;
    }

    //Zoom Out Element by given distance "Swipe into Element"
    public NativeAndroidActions zoomOut(By locator, ScrollDirection direction, int zoomingDistance, By swipedElementLocator) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(locator, direction, swipedElementLocator);
        // Get Element Name
        String targetElementName = getElementName(driver, locator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, locator, targetElementName);

        //Execute the Zoom Out Action
        try {
            getFluentWait(driver).until(f -> {
                Point elementCenter = getElementCenter(driver, driver.findElement(locator));
                Point start1 = new Point(elementCenter.getX() - 50, elementCenter.getY());
                Point start2 = new Point(elementCenter.getX() + 50, elementCenter.getY());

                var x = start1.getX() + W3CTouchActions.Direction.LEFT.getX() * zoomingDistance;
                var y = start1.getY() + W3CTouchActions.Direction.LEFT.getY() * zoomingDistance;
                Point end1 = new Point(x, y);

                var a = start1.getX() + W3CTouchActions.Direction.RIGHT.getX() * zoomingDistance;
                var b = start1.getY() + W3CTouchActions.Direction.RIGHT.getY() * zoomingDistance;
                Point end2 = new Point(a, b);

                Sequence sequence1 = singleFingerSwipe("finger-1", end1, start1);
                Sequence sequence2 = singleFingerSwipe("finger-2", end2, start2);

                driver.perform(List.of(sequence1, sequence2));
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Zoom Out the Element [" + targetElementName + "]", e);
        }
        LogHelper.logInfoStep("Zooming Out the Element [" + targetElementName + "]");
        return this;
    }

    //Zoom Out Element by given distance Without Swipe
    public NativeAndroidActions zoomOut(By locator, int zoomingDistance) {
        new W3CTouchActions(driver).zoomOut(locator, zoomingDistance);
        return this;
    }

    /**
     * *********************************  Drag & Drop Actions  *************************************
     */
    //Drag the Source Element then Drop it to the Destination Element "Swipe into Screen"
    public NativeAndroidActions dragAndDrop(By targetLocator, By destinationLocator, ScrollDirection direction) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(targetLocator, direction);
        // Get Element Name
        String targetElementName = getElementName(driver, targetLocator);
        String destinationElementName = getElementName(driver, destinationLocator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, targetLocator, targetElementName);

        //Execute the Drag&Drop Action
        try {
            getFluentWait(driver).until(f -> {
                Point startPoint = getElementCenter(driver, driver.findElement(targetLocator));
                Point endPoint = getElementCenter(driver, driver.findElement(destinationLocator));

                Sequence sequence = singleFingerSwipe("finger-1", startPoint, endPoint);
                driver.perform(List.of(sequence));
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Drag the Element [" + targetElementName + "] then Drop it to Element [" + destinationElementName + "]", e);
        }
        LogHelper.logInfoStep("Dragging the Element [" + targetElementName + "] then Dropping it to Element [" + destinationElementName + "]");
        return this;
    }

    //Drag the Source Element then Drop it to the Destination Element "Swipe into Screen"
    public NativeAndroidActions dragAndDrop(LocatorType targetLocatorType, String targetLocatorValue, LocatorType destinationLocatorType, String destinationLocatorValue, ScrollDirection direction) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(targetLocatorType, targetLocatorValue, direction);
        // Get Element Name
        String targetElementName = getElementName(driver, targetLocatorType, targetLocatorValue);
        String destinationElementName = getElementName(driver, destinationLocatorType, destinationLocatorValue);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, targetLocatorType, targetLocatorValue, targetElementName);

        //Execute the Drag&Drop Action
        try {
            getFluentWait(driver).until(f -> {
                Point startPoint = getElementCenter(driver, driver.findElement(AppiumBy.androidUIAutomator(
                        getUiAutomatorQuery(targetLocatorType, targetLocatorValue, null))));
                Point endPoint = getElementCenter(driver, driver.findElement(AppiumBy.androidUIAutomator(
                        getUiAutomatorQuery(destinationLocatorType, destinationLocatorValue, null))));

                Sequence sequence = singleFingerSwipe("finger-1", startPoint, endPoint);
                driver.perform(List.of(sequence));
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Drag the Element [" + targetElementName + "] then Drop it to Element [" + destinationElementName + "]", e);
        }
        LogHelper.logInfoStep("Dragging the Element [" + targetElementName + "] then Dropping it to Element [" + destinationElementName + "]");
        return this;
    }

    //Drag the Source Element then Drop it to the Destination Element "Swipe into Element"
    public NativeAndroidActions dragAndDrop(LocatorType targetLocatorType, String targetLocatorValue, LocatorType destinationLocatorType, String destinationLocatorValue, ScrollDirection direction, LocatorType swipedElementLocatorType, String swipedElementLocatorValue) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(targetLocatorType, targetLocatorValue, direction, swipedElementLocatorType, swipedElementLocatorValue);
        // Get Element Name
        String targetElementName = getElementName(driver, targetLocatorType, targetLocatorValue);
        String destinationElementName = getElementName(driver, destinationLocatorType, destinationLocatorValue);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, targetLocatorType, targetLocatorValue, targetElementName);

        //Execute the Drag&Drop Action
        try {
            getFluentWait(driver).until(f -> {
                Point startPoint = getElementCenter(driver, driver.findElement(AppiumBy.androidUIAutomator(
                        getUiAutomatorQuery(targetLocatorType, targetLocatorValue, null))));
                Point endPoint = getElementCenter(driver, driver.findElement(AppiumBy.androidUIAutomator(
                        getUiAutomatorQuery(destinationLocatorType, destinationLocatorValue, null))));

                Sequence sequence = singleFingerSwipe("finger-1", startPoint, endPoint);
                driver.perform(List.of(sequence));
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Drag the Element [" + targetElementName + "] then Drop it to Element [" + destinationElementName + "]", e);
        }
        LogHelper.logInfoStep("Dragging the Element [" + targetElementName + "] then Dropping it to Element [" + destinationElementName + "]");
        return this;
    }

    //Drag the Source Element then Drop it to the Destination Element "Swipe into Element"
    public NativeAndroidActions dragAndDrop(By targetLocator, By destinationLocator, ScrollDirection direction, By swipedElementLocator) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(targetLocator, direction, swipedElementLocator);
        // Get Element Name
        String targetElementName = getElementName(driver, targetLocator);
        String destinationElementName = getElementName(driver, destinationLocator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, targetLocator, targetElementName);

        //Execute the Drag&Drop Action
        try {
            getFluentWait(driver).until(f -> {
                Point startPoint = getElementCenter(driver, driver.findElement(targetLocator));
                Point endPoint = getElementCenter(driver, driver.findElement(destinationLocator));

                Sequence sequence = singleFingerSwipe("finger-1", startPoint, endPoint);
                driver.perform(List.of(sequence));
                return true;
            });
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Drag the Element [" + targetElementName + "] then Drop it to Element [" + destinationElementName + "]", e);
        }
        LogHelper.logInfoStep("Dragging the Element [" + targetElementName + "] then Dropping it to Element [" + destinationElementName + "]");
        return this;
    }

    //Drag the Source Element then Drop it to the Destination Element Without Swipe
    public NativeAndroidActions dragAndDrop(By targetLocator, By destinationLocator) {
        new W3CTouchActions(driver).dragAndDrop(targetLocator, destinationLocator);
        return this;
    }

    /**
     * *********************************  Swiping Actions  *************************************
     */
    //Scroll Into Screen till reach a Button or Link in a given direction
    public NativeAndroidActions swipeIntoScreen(LocatorType locatorType, String locatorValue, ScrollDirection direction) {
        // If No Swipe Needed, Check if Element is Displayed into View
        if (direction == null)
            checkElementDisplayed(driver, locatorType, locatorValue);

            //If Swipe is Needed, Execute the Swipe Actions
        else {
            // Check Element is Displayed into View, Otherwise Swipe till Displayed into View
            LogHelper.logInfoStep("Swiping (" + direction + ") into Screen");
            swipeTillElementDisplayed(driver, locatorType, locatorValue, direction);
        }
        return this;
    }


    //Scroll Into Screen till reach a Button or Link in a given direction
    public NativeAndroidActions swipeIntoScreen(By locator, ScrollDirection direction) {
        // If No Swipe Needed, Check if Element is Displayed into View
        if (direction == null)
            checkElementDisplayed(driver, locator);

            //If Swipe is Needed, Execute the Swipe Actions
        else {
            // Check Element is Displayed into View, Otherwise Swipe till Displayed into View
            LogHelper.logInfoStep("Swiping (" + direction + ") into Screen");
            swipeTillElementDisplayed(driver, locator, direction);
        }
        return this;
    }

    //Scroll Into an Element till reach a Button or Link in a given direction
    public NativeAndroidActions swipeIntoElement(LocatorType locatorType, String locatorValue, ScrollDirection direction, LocatorType swipedElementLocatorType, String swipedElementLocatorValue) {
        // If No Swipe Needed, Check if Element is Displayed into View
        if (direction == null)
            checkElementDisplayed(driver, locatorType, locatorValue);

            //If Swipe is Needed, Execute the Swipe Actions
        else {
            // Check Element is Displayed into View, Otherwise Swipe till Displayed into View
            String swipedElementName = getElementName(driver, swipedElementLocatorType, swipedElementLocatorValue);
            LogHelper.logInfoStep("Swiping (" + direction + ") into Element [" + swipedElementName + "]");
            swipeTillElementDisplayed(driver, locatorType, locatorValue, direction, swipedElementLocatorType, swipedElementLocatorValue);
        }
        return this;
    }

    //Scroll Into an Element till reach a Button or Link in a given direction
    public NativeAndroidActions swipeIntoElement(By locator, ScrollDirection direction, By swipedElementLocator) {
        // If No Swipe Needed, Check if Element is Displayed into View
        if (direction == null)
            checkElementDisplayed(driver, locator);

            //If Swipe is Needed, Execute the Swipe Actions
        else {
            // Check Element is Displayed into View, Otherwise Swipe till Displayed into View
            String swipedElementName = getElementName(driver, swipedElementLocator);
            LogHelper.logInfoStep("Swiping (" + direction + ") into Element [" + swipedElementName + "]");
            swipeTillElementDisplayed(driver, locator, direction, swipedElementLocator);
        }
        return this;
    }
}