package yehiaEngine.elementActions.Helpers;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import yehiaEngine.elementActions.NativeAndroidActions;
import yehiaEngine.loggers.LogHelper;

import java.time.Duration;

import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;
import static yehiaEngine.elementActions.Helpers.WaitsManager.getFluentWait;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;

public class NativeAndroidActionsHelper {

    /**
     * *********************************  Locating Element Methods  *************************************
     */
    // Check if Element is Displayed on Page
    public static void checkElementDisplayed(AppiumDriver driver,NativeAndroidActions.LocatorType locatorType, String locatorValue)
    {
        try {
            getFluentWait(driver).until(f -> driver.findElement(AppiumBy.androidUIAutomator(
                    getUiAutomatorQuery(locatorType, locatorValue, null))).isDisplayed());
        } catch (TimeoutException e) {
            LogHelper.logErrorStep("The Element located by [" + locatorType + " - " + locatorValue + "] is not Displayed", e);
        }
    }

    // Check if Element is Displayed on Page with Swiping
    public static void swipeTillElementDisplayed (AppiumDriver driver,NativeAndroidActions.LocatorType locatorType, String locatorValue, NativeAndroidActions.ScrollDirection direction)
    {
        try {
            getFluentWait(driver).until(f -> driver.findElement(AppiumBy.androidUIAutomator(
                    getUiAutomatorQuery(locatorType, locatorValue, direction))).isDisplayed());
        } catch (TimeoutException e) {
            LogHelper.logErrorStep("The Element located by [" + locatorType + " - " + locatorValue + "] is not Displayed", e);
        }
    }

    // Check if Element is Displayed on Page with Swiping
    public static void swipeTillElementDisplayed (AppiumDriver driver,NativeAndroidActions.LocatorType locatorType, String locatorValue, NativeAndroidActions.ScrollDirection direction, NativeAndroidActions.LocatorType swipedElementLocatorType, String swipedElementLocatorValue)
    {
        try {
            getFluentWait(driver).until(
                    f -> driver.findElement(AppiumBy.androidUIAutomator(
                    getUiAutomatorQuery(locatorType, locatorValue, direction,swipedElementLocatorType,swipedElementLocatorValue))).isDisplayed()
            );
        } catch (TimeoutException e) {
            LogHelper.logErrorStep("The Element located by [" + locatorType + " - " + locatorValue + "] is not Displayed", e);
        }
    }

    //Check if Element is Enabled on Page
    public static void checkElementEnabled(AppiumDriver driver,NativeAndroidActions.LocatorType locatorType, String locatorValue, String elementName)
    {
        try {
            getFluentWait(driver).until(f -> driver.findElement(AppiumBy.androidUIAutomator(
                    getUiAutomatorQuery(locatorType, locatorValue, null))).isEnabled());
        } catch (TimeoutException e) {
            LogHelper.logErrorStep("The Element [" + elementName + "] is not Enabled", e);
        }
    }

    // Get the Element Name
    public static String getElementName(AppiumDriver driver,NativeAndroidActions.LocatorType locatorType, String locatorValue) {
        try {
            String elementName = getMainElementText(driver,locatorType,locatorValue);
            if (elementName != null && !elementName.isEmpty()) {
                return elementName;
            }

            //Classes Related to Android
            String[] elementTypes = {
                    ".//android.widget.TextView",
                    ".//android.widget.EditText",
//                    ".//android.widget.Button",
//                    ".//android.widget.ImageView",
//                    ".//android.widget.LinearLayout",
//                    ".//android.widget.FrameLayout",
//                    ".//androidx.recyclerview.widget.RecyclerView",
//                    ".//androidx.constraintlayout.widget.ConstraintLayout",
//                    ".//androidx.cardview.widget.CardView",
//                    ".//android.webkit.WebView"
            };

            for (String elementType : elementTypes) {
                elementName = getChildElementText(driver, locatorType, locatorValue, elementType);
                if (elementName != null && !elementName.isEmpty()) {
                    return elementName;
                }
            }

        } catch (NoSuchElementException e) {
            // Log the exception and return the locator as fallback
            LogHelper.logErrorStep("Element not found: " + locatorType+"-"+locatorValue);
        }

        return locatorType + " - " + locatorValue;
    }

    // Helper method to get the main element text
    private static String getMainElementText(AppiumDriver driver,NativeAndroidActions.LocatorType locatorType, String locatorValue) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                getUiAutomatorQuery(locatorType, locatorValue, null))).getText();
    }

    // Helper method to get the child element text
    private static String getChildElementText(AppiumDriver driver,NativeAndroidActions.LocatorType locatorType, String locatorValue, String childXPath) {
        try {
            WebElement element = driver.findElement(AppiumBy.androidUIAutomator(
                    getUiAutomatorQuery(locatorType, locatorValue, null)));
            return element.findElement(By.xpath(childXPath)).getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * *********************************  Helper Methods  *************************************
     */
    //Get the Locator query that will be used in AppiumBy.AndroidUIAutomator to perform scrolling into screen till find the element locator
    public static String getUiAutomatorQuery(NativeAndroidActions.LocatorType locatorType, String locatorValue, NativeAndroidActions.ScrollDirection direction) {
        String query = null;
        switch (locatorType) {
            case TEXT: {
                if (direction == NativeAndroidActions.ScrollDirection.VERTICAL)
                    query = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().textContains(\"" + locatorValue + "\"))";

                else if (direction == NativeAndroidActions.ScrollDirection.HORIZONTAL)
                    query = "new UiScrollable(new UiSelector()).setAsHorizontalList().scrollIntoView(" + "new UiSelector().textContains(\"" + locatorValue + "\"))";

                else if (direction == null)
                    query = "new UiSelector().textContains(\"" + locatorValue + "\")";
                break;
            }

            case ACCESSIBILITY_ID: {
                if (direction == NativeAndroidActions.ScrollDirection.VERTICAL)
                    query = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().description(\"" + locatorValue + "\"))";

                else if (direction == NativeAndroidActions.ScrollDirection.HORIZONTAL)
                    query = "new UiScrollable(new UiSelector()).setAsHorizontalList().scrollIntoView(" + "new UiSelector().description(\"" + locatorValue + "\"))";

                else if (direction == null)
                    query = "new UiSelector().description(\"" + locatorValue + "\")";
                break;
            }

            case RESOURCE_ID: {
                if (direction == NativeAndroidActions.ScrollDirection.VERTICAL)
                    query = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().resourceId(\"" + locatorValue + "\"))";

                else if (direction == NativeAndroidActions.ScrollDirection.HORIZONTAL)
                    query = "new UiScrollable(new UiSelector()).setAsHorizontalList().scrollIntoView(" + "new UiSelector().resourceId(\"" + locatorValue + "\"))";

                else if (direction == null)
                    query = "new UiSelector().resourceId(\"" + locatorValue + "\")";
                break;
            }

            case CLASS_NAME: {
                if (direction == NativeAndroidActions.ScrollDirection.VERTICAL)
                    query = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().className(\"" + locatorValue + "\"))";

                else if (direction == NativeAndroidActions.ScrollDirection.HORIZONTAL)
                    query = "new UiScrollable(new UiSelector()).setAsHorizontalList().scrollIntoView(" + "new UiSelector().className(\"" + locatorValue + "\"))";

                else if (direction == null)
                    query = "new UiSelector().className(\"" + locatorValue + "\")";
                break;
            }

            default: {
                LogHelper.logErrorStep("Locator Type is not Correct");
                break;
            }
        }
        return query;
    }

    //Get the Locator query that will be used in AppiumBy.AndroidUIAutomator to perform scrolling into swiped element till find the element locator
    public static String getUiAutomatorQuery(NativeAndroidActions.LocatorType locatorType, String locatorValue, NativeAndroidActions.ScrollDirection direction, NativeAndroidActions.LocatorType swipedElementLocatorType, String swipedElementLocatorValue) {
        String query = null;
        if (direction == NativeAndroidActions.ScrollDirection.VERTICAL) {
            switch (swipedElementLocatorType) {
                case TEXT: {
                    if (locatorType == NativeAndroidActions.LocatorType.TEXT)
                        query = "new UiScrollable(new UiSelector().textMatches(\".*" + swipedElementLocatorValue + "\").scrollable(true)).scrollIntoView(new UiSelector().textContains(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.ACCESSIBILITY_ID)
                        query = "new UiScrollable(new UiSelector().textMatches(\".*" + swipedElementLocatorValue + "\").scrollable(true)).scrollIntoView(new UiSelector().description(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.RESOURCE_ID)
                        query = "new UiScrollable(new UiSelector().textMatches(\".*" + swipedElementLocatorValue + "\").scrollable(true)).scrollIntoView(new UiSelector().resourceId(\"" + locatorValue + "\"))";
                    break;
                }

                case ACCESSIBILITY_ID: {
                    if (locatorType == NativeAndroidActions.LocatorType.TEXT)
                        query = "new UiScrollable(new UiSelector().descriptionMatches(\".*" + swipedElementLocatorValue + "\").scrollable(true)).scrollIntoView(new UiSelector().textContains(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.ACCESSIBILITY_ID)
                        query = "new UiScrollable(new UiSelector().descriptionMatches(\".*" + swipedElementLocatorValue + "\").scrollable(true)).scrollIntoView(new UiSelector().description(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.RESOURCE_ID)
                        query = "new UiScrollable(new UiSelector().descriptionMatches(\".*" + swipedElementLocatorValue + "\").scrollable(true)).scrollIntoView(new UiSelector().resourceId(\"" + locatorValue + "\"))";
                    break;
                }

                case RESOURCE_ID: {
                    if (locatorType == NativeAndroidActions.LocatorType.TEXT)
                        query = "new UiScrollable(new UiSelector().resourceIdMatches(\".*" + swipedElementLocatorValue + "\").scrollable(true)).scrollIntoView(new UiSelector().textContains(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.ACCESSIBILITY_ID)
                        query = "new UiScrollable(new UiSelector().resourceIdMatches(\".*" + swipedElementLocatorValue + "\").scrollable(true)).scrollIntoView(new UiSelector().description(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.RESOURCE_ID)
                        query = "new UiScrollable(new UiSelector().resourceIdMatches(\".*" + swipedElementLocatorValue + "\").scrollable(true)).scrollIntoView(new UiSelector().resourceId(\"" + locatorValue + "\"))";
                    break;
                }

                case CLASS_NAME: {
                    if (locatorType == NativeAndroidActions.LocatorType.TEXT)
                        query = "new UiScrollable(new UiSelector().classNameMatches(\".*" + swipedElementLocatorValue + "\").scrollable(true)).scrollIntoView(new UiSelector().textContains(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.ACCESSIBILITY_ID)
                        query = "new UiScrollable(new UiSelector().classNameMatches(\".*" + swipedElementLocatorValue + "\").scrollable(true)).scrollIntoView(new UiSelector().description(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.RESOURCE_ID)
                        query = "new UiScrollable(new UiSelector().classNameMatches(\".*" + swipedElementLocatorValue + "\").scrollable(true)).scrollIntoView(new UiSelector().resourceId(\"" + locatorValue + "\"))";
                    break;
                }

            }
        } else if (direction == NativeAndroidActions.ScrollDirection.HORIZONTAL) {
            switch (swipedElementLocatorType) {
                case TEXT: {
                    if (locatorType == NativeAndroidActions.LocatorType.TEXT)
                        query = "new UiScrollable(new UiSelector().textMatches(\".*" + swipedElementLocatorValue + "\")).setAsHorizontalList().scrollIntoView(new UiSelector().textContains(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.ACCESSIBILITY_ID)
                        query = "new UiScrollable(new UiSelector().textMatches(\".*" + swipedElementLocatorValue + "\")).setAsHorizontalList().scrollIntoView(new UiSelector().description(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.RESOURCE_ID)
                        query = "new UiScrollable(new UiSelector().textMatches(\".*" + swipedElementLocatorValue + "\")).setAsHorizontalList().scrollIntoView(new UiSelector().resourceID(\"" + locatorValue + "\"))";
                    break;
                }

                case ACCESSIBILITY_ID: {
                    if (locatorType == NativeAndroidActions.LocatorType.TEXT)
                        query = "new UiScrollable(new UiSelector().descriptionMatches(\".*" + swipedElementLocatorValue + "\")).setAsHorizontalList().scrollIntoView(new UiSelector().textContains(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.ACCESSIBILITY_ID)
                        query = "new UiScrollable(new UiSelector().descriptionMatches(\".*" + swipedElementLocatorValue + "\")).setAsHorizontalList().scrollIntoView(new UiSelector().description(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.RESOURCE_ID)
                        query = "new UiScrollable(new UiSelector().descriptionMatches(\".*" + swipedElementLocatorValue + "\")).setAsHorizontalList().scrollIntoView(new UiSelector().resourceID(\"" + locatorValue + "\"))";
                    break;
                }

                case RESOURCE_ID: {
                    if (locatorType == NativeAndroidActions.LocatorType.TEXT)
                        query = "new UiScrollable(new UiSelector().resourceIdMatches(\".*" + swipedElementLocatorValue + "\")).setAsHorizontalList().scrollIntoView(new UiSelector().textContains(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.ACCESSIBILITY_ID)
                        query = "new UiScrollable(new UiSelector().resourceIdMatches(\".*" + swipedElementLocatorValue + "\")).setAsHorizontalList().scrollIntoView(new UiSelector().description(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.RESOURCE_ID)
                        query = "new UiScrollable(new UiSelector().resourceIdMatches(\".*" + swipedElementLocatorValue + "\")).setAsHorizontalList().scrollIntoView(new UiSelector().resourceID(\"" + locatorValue + "\"))";
                    break;
                }

                case CLASS_NAME: {
                    if (locatorType == NativeAndroidActions.LocatorType.TEXT)
                        query = "new UiScrollable(new UiSelector().classNameMatches(\".*" + swipedElementLocatorValue + "\")).setAsHorizontalList().scrollIntoView(new UiSelector().textContains(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.ACCESSIBILITY_ID)
                        query = "new UiScrollable(new UiSelector().classNameMatches(\".*" + swipedElementLocatorValue + "\")).setAsHorizontalList().scrollIntoView(new UiSelector().description(\"" + locatorValue + "\"))";
                    else if (locatorType == NativeAndroidActions.LocatorType.RESOURCE_ID)
                        query = "new UiScrollable(new UiSelector().classNameMatches(\".*" + swipedElementLocatorValue + "\")).setAsHorizontalList().scrollIntoView(new UiSelector().resourceID(\"" + locatorValue + "\"))";
                    break;
                }
            }
        }
        return query;
    }

    // Write Text on TextBox and Log the Action
    public static void writeText(AppiumDriver driver,NativeAndroidActions.LocatorType locatorType, String locatorValue, String elementName, String text) {
        // Write Text on TextBox Element using the Selenium sendKeys method
        try {
            getFluentWait(driver).until(f -> {
                driver.findElement(AppiumBy.androidUIAutomator(
                        getUiAutomatorQuery(locatorType, locatorValue, null))).sendKeys(text);
                return true;
            });
            LogHelper.logInfoStep("Typing [" + text + "] on Element [" + elementName + "]");

        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Type [" + text + "] on Element [" + elementName + "]", e);
        }
    }

    // Clear Text from TextBox and Log the Action
    public static void clearText(AppiumDriver driver,NativeAndroidActions.LocatorType locatorType, String locatorValue, String elementName) {
        // Clear Text on TextBox Element using the Selenium sendKeys method
        try {
            getFluentWait(driver).until(f -> {
                driver.findElement(AppiumBy.androidUIAutomator(
                        getUiAutomatorQuery(locatorType, locatorValue, null))).clear();
                return true;
            });
            LogHelper.logInfoStep("Clearing Text from Element [" + elementName + "]");

        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Clear Text from Element [" + elementName + "]", e);
        }
    }

    //Build the sequence of swipe by one finger according to W3c Touch Actions
    public static Sequence singleFingerSwipe(String fingerName, Point start, Point end) {
        PointerInput finger = new PointerInput(TOUCH, fingerName);
        Sequence sequence = new Sequence(finger, 0);

        sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), viewport(), start.getX(), start.getY()));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        if (end != null) {
            sequence.addAction(new Pause(finger, Duration.ofMillis(500)));
            sequence.addAction(finger.createPointerMove(Duration.ofMillis(Long.parseLong(getPropertiesValue("SwipeRate"))), viewport(), end.getX(), end.getY()));
        }
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        return sequence;
    }

    //Get the Center Coordinate of the Element
    public static Point getElementCenter(AppiumDriver driver,WebElement element) {
        Point location = element.getLocation();
        Dimension size = element.getSize();
        int x = (size.getWidth() / 2) + location.getX();
        int y = (size.getHeight() / 2) + location.getY();
        return getCorrectedCoordinates(driver,element, new Point(x, y));
    }

    //Get the Center Coordinate of the Element and verify that it's not outside the screen
    public static Point getCorrectedCoordinates(AppiumDriver driver,WebElement element, Point point) {
        Dimension screenSize = getScreenSize(driver);
        int x = point.getX();
        int y = point.getY();
        int w = screenSize.getWidth();
        int h = screenSize.getHeight();

        if (element != null) {
            final var size = element.getSize();
            final var location = element.getLocation();
            w = size.getWidth() + location.getX();
            h = size.getHeight() + location.getY();
        }

        if (x >= w) {
            x = w - 5;
        }
        if (y >= h) {
            y = h - 5;
        }
        if (x < 0) {
            x = 5;
        }
        if (y < 0) {
            y = 5;
        }
        return new Point(x, y);
    }

    //Get the Dimensions of Screen Size
    public static Dimension getScreenSize(AppiumDriver driver) {
        return driver.manage()
                .window()
                .getSize();
    }
}
