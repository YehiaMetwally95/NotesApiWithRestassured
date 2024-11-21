package yehiaEngine.elementActions.Helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import yehiaEngine.loggers.LogHelper;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;
import static yehiaEngine.elementActions.Helpers.WaitsManager.getFluentWait;
import static yehiaEngine.elementActions.Helpers.WaitsManager.getSwipeWait;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;

public class W3CTouchActionsHelper  {

    /**
     * *********************************  Helper Methods  *************************************
     */
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

    // Write Text on TextBox and Log the Action
    public static void writeText(AppiumDriver driver,By locator, String elementName, String text) {
        // Write Text on TextBox Element using the Selenium sendKeys method
        try {
            getFluentWait(driver).until(f -> {
                driver.findElement(locator).sendKeys(text);
                return true;
            });
            LogHelper.logInfoStep("Typing [" + text + "] on Element [" + elementName + "]");

        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Type [" + text + "] on Element [" + elementName + "]", e);
        }
    }

    // Clear Text from TextBox and Log the Action
    public static void clearText(AppiumDriver driver,By locator, String elementName) {
        try {
            getFluentWait(driver).until(f -> {
                driver.findElement(locator).clear();
                return true;
            });
            LogHelper.logInfoStep("Clearing the Text from Element [" + elementName + "]");
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Clear the Text from Element [" + elementName + "]", e);
        }
    }

    /**
     * *********************************  Locating Element Methods  *************************************
     */
    // Check if Element is Displayed on Page
    public static void checkElementDisplayed(AppiumDriver driver,By locator) {
        try {
            getFluentWait(driver).until(f -> driver.findElement(locator).isDisplayed());
        } catch (TimeoutException e) {
            LogHelper.logErrorStep("The Element located by [" + locator.toString() + "] is not Displayed", e);
        }
    }

    // Check if Element is Displayed on Page with Swiping
    public static void swipeTillElementDisplayed(AppiumDriver driver,By locator, Point startPoint, Point endPoint) {
        // Swipe till Element is Displayed into View or till Timeout or till reach end of Page
        try {
            String[] previousPageSource = {""};
            getSwipeWait(driver).until(f -> {
                Sequence sequence = singleFingerSwipe("finger-1", startPoint, endPoint);
                driver.perform(List.of(sequence));

                String currentPageSource = driver.getPageSource();
                if (currentPageSource.equalsIgnoreCase(previousPageSource[0]))
                    // The page source hasn't changed, so we've reached the bottom
                    throw new TimeoutException();
                else
                    previousPageSource[0] = currentPageSource;

                return driver.findElement(locator).isDisplayed();
            });
        } catch (TimeoutException e) {
            // Scroll in Opposite Direction till Element is Displayed into View or till Timeout or till reach end of Page
            try {
                LogHelper.logInfoStep("Swiping in Opposite Direction");
                String[] previousPageSource = {""};
                getSwipeWait(driver).until(f -> {
                    Sequence sequence = singleFingerSwipe("finger-1", endPoint, startPoint);
                    driver.perform(List.of(sequence));

                    String currentPageSource = driver.getPageSource();
                    if (currentPageSource.equalsIgnoreCase(previousPageSource[0]))
                        // The page source hasn't changed, so we've reached the bottom
                        throw new TimeoutException();
                    else
                        previousPageSource[0] = currentPageSource;

                    return driver.findElement(locator).isDisplayed();
                });
            } catch (TimeoutException f) {
                LogHelper.logErrorStep("The Element located by [" + locator.toString() + "] is not Displayed", f);
            }
        }
    }

    //Check if Element is Enabled on Page
    public static void checkElementEnabled(AppiumDriver driver,By locator, String elementName) {
        try {
            getFluentWait(driver).until(f -> driver.findElement(locator).isEnabled());
        } catch (TimeoutException e) {
            LogHelper.logErrorStep("The Element [" + elementName + "] is not Enabled", e);
        }
    }

    // Get the Element Name
    public static String getElementName(AppiumDriver driver,By locator) {
        String appType = System.getProperty("appType");

        try {
            String elementName = getMainElementText(driver,locator);
            if (elementName != null && !elementName.isEmpty()) {
                return elementName;
            }

            // Classes Related to IOS
            if (appType.equalsIgnoreCase("NativeIOS"))
            {
                String[] elementTypes = {
                        ".//XCUIElementTypeTextField",
                        ".//XCUIElementTypeTextView",
//                        ".//XCUIElementTypeButton",
//                        ".//XCUIElementTypeStaticText",
//                        ".//XCUIElementTypeImage",
//                        ".//XCUIElementTypeTable",
//                        ".//XCUIElementTypeCollectionView",
//                        ".//XCUIElementTypeScrollView"
                };

                for (String elementType : elementTypes) {
                    elementName = getChildElementText(driver, locator, elementType);
                    if (elementName != null && !elementName.isEmpty()) {
                        return elementName;
                    }
                }
            }

            // Classes Related to Android
            if (appType.equalsIgnoreCase("NativeAndroid"))
            {
                String[] elementTypes = {
                        ".//android.widget.TextView",
                        ".//android.widget.EditText",
//                        ".//android.widget.Button",
//                        ".//android.widget.ImageView",
//                        ".//android.widget.LinearLayout",
//                        ".//android.widget.FrameLayout",
//                        ".//androidx.recyclerview.widget.RecyclerView",
//                        ".//androidx.constraintlayout.widget.ConstraintLayout",
//                        ".//androidx.cardview.widget.CardView",
//                        ".//android.webkit.WebView"
                };

                for (String elementType : elementTypes) {
                    elementName = getChildElementText(driver, locator, elementType);
                    if (elementName != null && !elementName.isEmpty()) {
                        return elementName;
                    }
                }
            }

        } catch (NoSuchElementException e) {
            // Log the exception and return the locator as fallback
            LogHelper.logErrorStep("Element not found: " + locator);
        }

        return locator.toString();
    }

    // Helper method to get the main element text
    private static String getMainElementText(AppiumDriver driver,By locator) {
        return driver.findElement(locator).getText();
    }

    // Helper method to get the child element text
    private static String getChildElementText(AppiumDriver driver,By locator, String childXPath) {
        try {
            WebElement element = driver.findElement(locator);
            return element.findElement(By.xpath(childXPath)).getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
