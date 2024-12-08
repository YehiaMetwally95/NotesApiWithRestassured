package yehiaEngine.elementActions;

import io.appium.java_client.AppiumDriver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import yehiaEngine.loggers.LogHelper;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;
import static yehiaEngine.elementActions.Helpers.W3CTouchActionsHelper.*;
import static yehiaEngine.elementActions.Helpers.WaitsManager.getFluentWait;
import static yehiaEngine.elementActions.Helpers.WaitsManager.getSwipeWait;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;

public class W3CTouchActions {
    AppiumDriver driver;

    @AllArgsConstructor
    @Getter
    public enum Direction {
        LEFT(-1, 0),
        RIGHT(1, 0),
        UP(0, 1),
        DOWN(0, -1);

        private int x;
        private int y;
    }

    public W3CTouchActions(AppiumDriver driver) {
        this.driver = driver;
    }

    /**
     * *********************************  Tap Actions  ****************************************
     */
    // Tap on Button or Link by Swiping into Screen & Log Tapping Action
    public W3CTouchActions tap(By targetLocator, Direction direction) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(targetLocator, direction);
        // Get Element Name
        String elementName = getElementName(driver, targetLocator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, targetLocator, elementName);
        //Execute the Tap Action
        try {
            getFluentWait(driver).until(f -> {
                new Actions(driver).moveToElement(driver.findElement(targetLocator)).perform();
                driver.findElement(targetLocator).click();
                return true;
            });
        } catch (ElementNotInteractableException e) {
            try {
                getFluentWait(driver).until(f -> {
                    Point startPoint = getElementCenter(driver, driver.findElement(targetLocator));
                    Point endPoint = null;
                    Sequence sequence = singleFingerSwipe("finger-1", startPoint, endPoint);
                    driver.perform(List.of(sequence));
                    return true;
                });
            } catch (Exception ex) {
                LogHelper.logErrorStep("Failed to Tap on Element [" + elementName + "]", ex);
            }
        }
        LogHelper.logInfoStep("Tapping on Element [" + elementName + "]");
        return this;
    }

    //Tap on Button or Link by Swiping into swiped element & Log Tapping Action
    public W3CTouchActions tap(By targetLocator, Direction direction, By swipedElementLocator) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(targetLocator, direction, swipedElementLocator);
        // Get Element Name
        String elementName = getElementName(driver, targetLocator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, targetLocator, elementName);

        //Execute the Tap Action
        try {
            getFluentWait(driver).until(f -> {
                new Actions(driver).moveToElement(driver.findElement(targetLocator)).perform();
                driver.findElement(targetLocator).click();
                return true;
            });
        } catch (ElementNotInteractableException e) {
            try {
                getFluentWait(driver).until(f -> {
                    Point startPoint = getElementCenter(driver, driver.findElement(targetLocator));
                    Point endPoint = null;
                    Sequence sequence = singleFingerSwipe("finger-1", startPoint, endPoint);
                    driver.perform(List.of(sequence));
                    return true;
                });
            } catch (Exception ex) {
                LogHelper.logErrorStep("Failed to Tap on Element [" + elementName + "]", ex);
            }
        }
        LogHelper.logInfoStep("Tapping on Element [" + elementName + "]");
        return this;
    }

    //Tap on Button or Link without Swipe & Log Tapping Action
    public W3CTouchActions tap(By targetLocator) {
        tap(targetLocator, null);
        return this;
    }

    /**
     * *********************************  Long Tab Actions  *************************************
     */
    //Long Tap on Button or Link by Swiping into Screen & Log Tapping Action
    public W3CTouchActions longTab(By targetLocator, Direction direction) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(targetLocator, direction);
        // Get Element Name
        String elementName = getElementName(driver, targetLocator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, targetLocator, elementName);

        //Execute the Long Tap Action
        try {
            getFluentWait(driver).until(f -> {
                Point start = getElementCenter(driver, driver.findElement(targetLocator));
                PointerInput finger = new PointerInput(TOUCH, "finger-1");
                Sequence sequence = new Sequence(finger, 0);
                sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), viewport(), start.getX(), start.getY()));
                sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                sequence.addAction(new Pause(finger, Duration.ofMillis(1000)));
                sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                driver.perform(List.of(sequence));
                return true;
            });
        } catch (ElementNotInteractableException e) {
            try {
                getFluentWait(driver).until(f -> {
                    new Actions(driver).moveToElement(driver.findElement(targetLocator)).clickAndHold().perform();
                    return true;
                });
            } catch (Exception ex) {
                LogHelper.logErrorStep("Failed to Long Tap on Element [" + elementName + "]", ex);
            }
        }
        LogHelper.logInfoStep("Long Tapping on Element [" + elementName + "]");
        return this;
    }

    //Long Tap on Button or Link by Swiping into Element & Log Tapping Action
    public W3CTouchActions longTab(By targetLocator, Direction direction, By swipedElementLocator) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(targetLocator, direction, swipedElementLocator);
        // Get Element Name
        String elementName = getElementName(driver, targetLocator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, targetLocator, elementName);

        //Execute the Long Tap Action
        try {
            getFluentWait(driver).until(f -> {
                Point start = getElementCenter(driver, driver.findElement(targetLocator));
                PointerInput finger = new PointerInput(TOUCH, "finger-1");
                Sequence sequence = new Sequence(finger, 0);
                sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), viewport(), start.getX(), start.getY()));
                sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                sequence.addAction(new Pause(finger, Duration.ofMillis(1000)));
                sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                driver.perform(List.of(sequence));
                return true;
            });
        } catch (ElementNotInteractableException e) {
            try {
                getFluentWait(driver).until(f -> {
                    new Actions(driver).moveToElement(driver.findElement(targetLocator)).clickAndHold().perform();
                    return true;
                });
            } catch (Exception ex) {
                LogHelper.logErrorStep("Failed to Long Tap on Element [" + elementName + "]", ex);
            }
        }
        LogHelper.logInfoStep("Long Tapping on Element [" + elementName + "]");
        return this;
    }

    //Long Tap on Button or Link without Swiping & Log Tapping Action
    public W3CTouchActions longTab(By targetLocator) {
        longTab(targetLocator, null);
        return this;
    }

    /**
     * *********************************  Read Text Actions  *************************************
     */
    //Get Text from Element by Swiping into Screen & Log the Text
    public String readText(By targetLocator, Direction direction) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(targetLocator, direction);

        //Execute the Read Text Action
        try {
            String text = getElementName(driver, targetLocator);
            LogHelper.logInfoStep("Getting Text " + "[" + text + "] from Element located by [" + targetLocator.toString() + "]");
            return text.replace("\n", "");
        } catch (Exception e) {
            LogHelper.logErrorStep("Cannot Get Text from Element located by [" + targetLocator + "]", e);
            return null;
        }
    }

    //Get Text from Element by Swiping into Element & Log the Text
    public String readText(By targetLocator, Direction direction, By swipedElementLocator) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(targetLocator, direction, swipedElementLocator);

        //Execute the Read Text Action
        try {
            String text = getElementName(driver, targetLocator);
            LogHelper.logInfoStep("Getting Text " + "[" + text + "] from Element located by [" + targetLocator.toString() + "]");
            return text.replace("\n", "");
        } catch (Exception e) {
            LogHelper.logErrorStep("Cannot Get Text from Element located by [" + targetLocator + "]", e);
            return null;
        }
    }

    //Get Text from Element Without Swiping & Log the Text
    public String readText(By targetLocator) {
        return readText(targetLocator, null);
    }

    /**
     * *********************************  Type Actions  *************************************
     */
    //Clear TextBox then Typing on it & Log Typing Action "Swipe Into Screen"
    public W3CTouchActions type(By targetLocator, Direction direction, String text) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(targetLocator, direction);
        // Get Element Name
        String elementName = getElementName(driver, targetLocator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, targetLocator, elementName);

        //Execute the Type Action
        clearText(driver, targetLocator, elementName);
        writeText(driver, targetLocator, elementName, text);
        return this;
    }

    //Clear TextBox then Typing on it & Log Typing Action "Swipe Into Element"
    public W3CTouchActions type(By targetLocator, Direction direction, String text, By swipedElementLocator) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(targetLocator, direction, swipedElementLocator);
        // Get Element Name
        String elementName = getElementName(driver, targetLocator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, targetLocator, elementName);

        //Execute the Type Action
        clearText(driver, targetLocator, elementName);
        writeText(driver, targetLocator, elementName, text);
        return this;
    }

    //Clear TextBox then Typing on it & Log Typing Action Without Swipe
    public W3CTouchActions type(By targetLocator, String text) {
        type(targetLocator, null, text);
        return this;
    }

    /**
     * *********************************  CheckElementDisplayed Actions  *************************************
     */
    // Verify Element is Displayed on Page With swipe into screen
    public boolean isElementDisplayed(By locator, Direction direction) {
        if (direction == null) {
            try {
                //Wait until Element is Displayed on Page
                getFluentWait(driver).until(f -> driver.findElement(locator).isDisplayed());
                //Get Element Accessible Name
                String elementName = getElementName(driver, locator);
                LogHelper.logInfoStep("The Element [" + elementName + "] is Displayed");

            } catch (TimeoutException e) {
                LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is not Displayed");
                return false;
            }
            return true;
        } else {
            //Create 2 Coordinate Points to Swipe between them
            Point startPoint;
            Point endPoint;
            Dimension screenSize = getScreenSize(driver);
            var x = screenSize.getWidth() / 2;
            var y = screenSize.getHeight() / 2;
            startPoint = new Point(x, y);
            var a = x + direction.getX() * Integer.parseInt(getPropertiesValue("SwipeDistance"));
            var b = y + direction.getY() * Integer.parseInt(getPropertiesValue("SwipeDistance"));
            endPoint = getCorrectedCoordinates(driver, null, new Point(a, b));
            // Swipe till Element is Displayed into View or till Timeout or till Reach end of Page
            try {
                String[] previousPageSource = {""};
                final int[] flag = {0};
                getSwipeWait(driver).until(f -> {
                    if (flag[0] ==1)
                    {
                        Sequence sequence = singleFingerSwipe("finger-1", startPoint, endPoint);
                        driver.perform(List.of(sequence));
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
                //Get Element Accessible Name
                String elementName = getElementName(driver, locator);
                LogHelper.logInfoStep("The Element [" + elementName + "] is Displayed");
                return true;
            } catch (TimeoutException e) {
                // Scroll in Opposite Direction till Element is Displayed into View or till Timeout or till Reach end of Page
                try {
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
                    //Get Element Accessible Name
                    String elementName = getElementName(driver, locator);
                    LogHelper.logInfoStep("The Element [" + elementName + "] is Displayed");
                    return true;
                } catch (TimeoutException f) {
                    LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is not Displayed");
                    return false;
                }
            }
        }
    }

    // Verify Element is Displayed on Page Without swipe
    public boolean isElementDisplayed(By locator) {
        return isElementDisplayed(locator, null);
    }

    /**
     * *********************************  CheckElementNotDisplayed Actions  *************************************
     */
    // Verify Element is Not Displayed on Page With swipe into screen
    public boolean isElementNotDisplayed(By locator, Direction direction) {
        if (direction == null) {
            try {
                //Wait until Element is Displayed on Page
                getFluentWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));

                LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is not Displayed");

            } catch (TimeoutException e) {
                LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is Displayed");
                return false;
            }
            return true;
        } else {
            //Create 2 Coordinate Points to Swipe between them
            Point startPoint;
            Point endPoint;
            Dimension screenSize = getScreenSize(driver);
            var x = screenSize.getWidth() / 2;
            var y = screenSize.getHeight() / 2;
            startPoint = new Point(x, y);
            var a = x + direction.getX() * Integer.parseInt(getPropertiesValue("SwipeDistance"));
            var b = y + direction.getY() * Integer.parseInt(getPropertiesValue("SwipeDistance"));
            endPoint = getCorrectedCoordinates(driver, null, new Point(a, b));

            // Swipe till Element is Displayed into View or till Timeout or till Reach end of Page
            try {
                String[] previousPageSource = {""};
                final int[] flag = {0};
                getSwipeWait(driver).until(f -> {
                    if (flag[0] ==1)
                    {
                        Sequence sequence = singleFingerSwipe("finger-1", startPoint, endPoint);
                        driver.perform(List.of(sequence));
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
                    LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is Displayed");
                    return false;
                } catch (TimeoutException f) {
                    LogHelper.logInfoStep("The Element located by [" + locator.toString() + "] is not Displayed");
                    return true;
                }
            }
        }
    }

    // Verify Element is Not Displayed on Page Without swipe
    public boolean isElementNotDisplayed(By locator) {
        return isElementNotDisplayed(locator, null);
    }

    /**
     * *********************************  Drag & Drop Actions  *************************************
     */
    //Drag the Source Element then Drop it to the Destination Element "Swipe into Screen"
    public W3CTouchActions dragAndDrop(By targetLocator, By destinationLocator, Direction direction) {
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

    //Drag the Source Element then Drop it to the Destination Element "Swipe into Element"
    public W3CTouchActions dragAndDrop(By targetLocator, By destinationLocator, Direction direction, By swipedElementLocator) {
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
    public W3CTouchActions dragAndDrop(By targetLocator, By destinationLocator) {
        dragAndDrop(targetLocator, destinationLocator, null);
        return this;
    }

    /**
     * *********************************  Zoom In Actions  *************************************
     */
    //Zoom In Element by given distance "Swipe into Screen"
    public W3CTouchActions zoomIn(By targetLocator, Direction direction, int zoomingDistance) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(targetLocator, direction);
        // Get Element Name
        String targetElementName = getElementName(driver, targetLocator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, targetLocator, targetElementName);

        //Execute the Zoom In Action
        try {
            getFluentWait(driver).until(f -> {
                Point elementCenter = getElementCenter(driver, driver.findElement(targetLocator));
                Point start1 = new Point(elementCenter.getX() - 50, elementCenter.getY());
                Point start2 = new Point(elementCenter.getX() + 50, elementCenter.getY());

                var x = start1.getX() + Direction.LEFT.getX() * zoomingDistance;
                var y = start1.getY() + Direction.LEFT.getY() * zoomingDistance;
                Point end1 = new Point(x, y);

                var a = start1.getX() + Direction.RIGHT.getX() * zoomingDistance;
                var b = start1.getY() + Direction.RIGHT.getY() * zoomingDistance;
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
    public W3CTouchActions zoomIn(By targetLocator, Direction direction, int zoomingDistance, By swipedElementLocator) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(targetLocator, direction, swipedElementLocator);
        // Get Element Name
        String targetElementName = getElementName(driver, targetLocator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, targetLocator, targetElementName);

        //Execute the Zoom In Action
        try {
            getFluentWait(driver).until(f -> {
                Point elementCenter = getElementCenter(driver, driver.findElement(targetLocator));
                Point start1 = new Point(elementCenter.getX() - 50, elementCenter.getY());
                Point start2 = new Point(elementCenter.getX() + 50, elementCenter.getY());

                var x = start1.getX() + Direction.LEFT.getX() * zoomingDistance;
                var y = start1.getY() + Direction.LEFT.getY() * zoomingDistance;
                Point end1 = new Point(x, y);

                var a = start1.getX() + Direction.RIGHT.getX() * zoomingDistance;
                var b = start1.getY() + Direction.RIGHT.getY() * zoomingDistance;
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
    public W3CTouchActions zoomIn(By targetLocator, int zoomingDistance) {
        zoomIn(targetLocator, null, zoomingDistance);
        return this;
    }

    /**
     * *********************************  Zoom Out Actions  *************************************
     */
    //Zoom Out Element by given distance "Swipe into Screen"
    public W3CTouchActions zoomOut(By targetLocator, Direction direction, int zoomingDistance) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoScreen(targetLocator, direction);
        // Get Element Name
        String targetElementName = getElementName(driver, targetLocator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, targetLocator, targetElementName);

        //Execute the Zoom Out Action
        try {
            getFluentWait(driver).until(f -> {
                Point elementCenter = getElementCenter(driver, driver.findElement(targetLocator));
                Point start1 = new Point(elementCenter.getX() - 50, elementCenter.getY());
                Point start2 = new Point(elementCenter.getX() + 50, elementCenter.getY());

                var x = start1.getX() + Direction.LEFT.getX() * zoomingDistance;
                var y = start1.getY() + Direction.LEFT.getY() * zoomingDistance;
                Point end1 = new Point(x, y);

                var a = start1.getX() + Direction.RIGHT.getX() * zoomingDistance;
                var b = start1.getY() + Direction.RIGHT.getY() * zoomingDistance;
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
    public W3CTouchActions zoomOut(By targetLocator, Direction direction, int zoomingDistance, By swipedElementLocator) {
        //Swipe "if needed" Till Element is Displayed into View
        swipeIntoElement(targetLocator, direction, swipedElementLocator);
        // Get Element Name
        String targetElementName = getElementName(driver, targetLocator);
        //Check if Element is Enabled on Page (Not Disabled)
        checkElementEnabled(driver, targetLocator, targetElementName);

        //Execute the Zoom Out Action
        try {
            getFluentWait(driver).until(f -> {
                Point elementCenter = getElementCenter(driver, driver.findElement(targetLocator));
                Point start1 = new Point(elementCenter.getX() - 50, elementCenter.getY());
                Point start2 = new Point(elementCenter.getX() + 50, elementCenter.getY());

                var x = start1.getX() + Direction.LEFT.getX() * zoomingDistance;
                var y = start1.getY() + Direction.LEFT.getY() * zoomingDistance;
                Point end1 = new Point(x, y);

                var a = start1.getX() + Direction.RIGHT.getX() * zoomingDistance;
                var b = start1.getY() + Direction.RIGHT.getY() * zoomingDistance;
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

    //Zoom In Element by given distance Without Swipe
    public W3CTouchActions zoomOut(By targetLocator, int zoomingDistance) {
        zoomOut(targetLocator, null, zoomingDistance);
        return this;
    }

    /**
     * *********************************  Swiping Actions  *************************************
     */
    //Swipe Into Mobile Screen with a given direction till reach the Target Element
    public W3CTouchActions swipeIntoScreen(By locator, Direction direction) {
        // If No Swipe Needed, Check if Element is Displayed into View
        if (direction == null)
            checkElementDisplayed(driver, locator);

            //If Swipe is Needed, Execute the Swipe Actions
        else {
            //Create 2 Coordinate Points to Swipe between them
            Point startPoint;
            Point endPoint;
            Dimension screenSize = getScreenSize(driver);
            var x = screenSize.getWidth() / 2;
            var y = screenSize.getHeight() / 2;
            startPoint = new Point(x, y);
            var a = x + direction.getX() * Integer.parseInt(getPropertiesValue("SwipeDistance"));
            var b = y + direction.getY() * Integer.parseInt(getPropertiesValue("SwipeDistance"));
            endPoint = getCorrectedCoordinates(driver, null, new Point(a, b));

            // Check Element is Displayed into View, Otherwise Swipe till Displayed into View
            LogHelper.logInfoStep("Swiping (" + direction + ") into Screen");
            swipeTillElementDisplayed(driver, locator, startPoint, endPoint);
        }
        return this;
    }

    //Swipe Into a Scrollable Element with a given direction till reach the Target Element
    public W3CTouchActions swipeIntoElement(By targetLocator, Direction direction, By swipedElementLocator) {
        // If No Swipe Needed, Check if Element is Displayed into View
        if (direction == null)
            checkElementDisplayed(driver, targetLocator);

            //If Swipe is Needed, Execute the Swipe Actions
        else {
            Point startPoint;
            Point endPoint;
            startPoint = getElementCenter(driver, driver.findElement(swipedElementLocator));
            var a = startPoint.getX() + direction.getX() * Integer.parseInt(getPropertiesValue("SwipeDistance"));
            var b = startPoint.getY() + direction.getY() * Integer.parseInt(getPropertiesValue("SwipeDistance"));
            endPoint = getCorrectedCoordinates(driver, driver.findElement(swipedElementLocator), new Point(a, b));

            // Swipe till Element is Displayed into View
            String swipedElementname = getElementName(driver, swipedElementLocator);
            LogHelper.logInfoStep("Swiping (" + direction + ") into Element [" + swipedElementname + "]");
            swipeTillElementDisplayed(driver, targetLocator, startPoint, endPoint);
        }
        return this;
    }
}