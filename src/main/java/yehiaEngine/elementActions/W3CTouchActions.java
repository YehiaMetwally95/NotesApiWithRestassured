package yehiaEngine.elementActions;/*
package engine.elementActions;

import io.appium.java_client.AppiumDriver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;
import static utils.PropertiesManager.getPropertiesValue;
import static utils.RandomDataGenerator.generateInteger;
import static utils.Screenshot.takeElementScreenshot;

public class W3CTouchActions {
    AppiumDriver driver;

    static String textBoxesPath = "src/test/resources/Screenshots/TextBoxes/";
    static String pressedButtonsPath ="src/test/resources/Screenshots/PressedButtons/";
    static String retrievedTextPath ="src/test/resources/Screenshots/RetrievedTexts/";

    @AllArgsConstructor
    @Getter
    public enum Direction {
        LEFT (-1, 0),
        RIGHT (1, 0),
        UP (0, 1),
        DOWN (0, -1);

        private int x;
        private int y;
    }

    public W3CTouchActions(AppiumDriver driver)
    {
        this.driver = driver;
    }

    //Pressing on Button or Link & Printing Button Name & Take Screenshot for Button
    public W3CTouchActions tab (By targetLocator)
    {
        WaitsManager.getSwipeWait(driver).until(f->{
            System.out.println("Clicking On " + driver.findElement(targetLocator).getText()+" Button");
            try {
                takeElementScreenshot(driver,targetLocator,pressedButtonsPath,generateInteger());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Point startPoint = getElementCenter(driver.findElement(targetLocator));
            Point endPoint = null;
            Sequence sequence = singleFingerSwipe("finger-1",startPoint,endPoint);
            driver.perform(List.of(sequence));
            return true;
        });
        return this;
    }

    //Long Pressing on Button or Link & Printing Button Name & Take Screenshot for Button
    public W3CTouchActions longTab (By targetLocator)
    {
        WaitsManager.getSwipeWait(driver).until(f->{

            System.out.println("LongClicking On " + driver.findElement(targetLocator).getText());
            try {
                takeElementScreenshot(driver,targetLocator,pressedButtonsPath,generateInteger());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Point start = getElementCenter(driver.findElement(targetLocator));
            PointerInput finger = new PointerInput(TOUCH,"finger-1");

            Sequence sequence = new Sequence(finger,0);
            sequence.addAction(finger.createPointerMove(Duration.ofMillis(0),viewport(),start.getX(),start.getY()));
            sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            sequence.addAction(new Pause(finger,Duration.ofMillis(1000)));
            sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(sequence));

            return true;
        });
        return this;
    }

    //Get Text from Element & Take Screenshot for Retrieved Text
    public String readText(By locator) throws IOException {
        WaitsManager.getFluentWait(driver).until(f -> {
            driver.findElement(locator).getText();
            return true;
        });

        takeElementScreenshot(driver,locator,retrievedTextPath,generateInteger());
        return driver.findElement(locator).getText().replace("\n","");
    }

    //Typing on TextBox & Printing Text & Take Screenshot for TextBox
    public W3CTouchActions type (By locator , String text) throws IOException {
        WaitsManager.getFluentWait(driver).until(f -> {
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(text);
            return true;
        });
        System.out.println("Typing " + text);
        takeElementScreenshot(driver,locator,textBoxesPath,generateInteger());
        return this;
    }

    //Verify Element is Displayed
    public boolean isElementDisplayed(By locator){
        try{
            WaitsManager.getFluentWait(driver).until(f -> {
                driver.findElement(locator).isDisplayed();
                return true;
            });
        }catch(TimeoutException e){
            return false;
        }
        return true;
    }

    //Swipe Into Mobile Screen with a given direction till reach the Target Element
    public W3CTouchActions swipeIntoScreen(By targetLocator, Direction direction)
    {
        Point startPoint;
        Point endPoint;
        Dimension screenSize = getScreenSize ();
        var x = screenSize.getWidth () / 2;
        var y = screenSize.getHeight () / 2;
        startPoint = new Point(x,y);

        var a = x+ direction.getX()*Integer.parseInt(getPropertiesValue("SwipeDistance"));
        var b = y+ direction.getY()*Integer.parseInt(getPropertiesValue("SwipeDistance"));
        endPoint = getCorrectedCoordinates(null,new Point(a,b));

       WaitsManager.getSwipeWait(driver).until(f->{
            Sequence sequence = singleFingerSwipe("finger-1",startPoint,endPoint);
            driver.perform(List.of(sequence));
            return driver.findElement(targetLocator).isDisplayed();
        });
        return this;
    }

    //Swipe Into a Scrollable Element with a given direction till reach the Target Element
    public W3CTouchActions swipeIntoElement(By swippedElementLocator, Direction direction, By targetLocator)
    {
        WaitsManager.getSwipeWait(driver).until(f->{
            Point startPoint;
            Point endPoint;

            startPoint = getElementCenter(driver.findElement(swippedElementLocator));

            var a = startPoint.getX()+ direction.getX()*Integer.parseInt(getPropertiesValue("SwipeDistance"));
            var b = startPoint.getY()+ direction.getY()*Integer.parseInt(getPropertiesValue("SwipeDistance"));
            endPoint = getCorrectedCoordinates(driver.findElement(swippedElementLocator),new Point(a,b));

            Sequence sequence = singleFingerSwipe("finger-1",startPoint,endPoint);
            driver.perform(List.of(sequence));
            return  driver.findElement(targetLocator).isDisplayed();
        });
        return this;
    }

    //Drag the Source Element then Drop it to the Destination Element
    public W3CTouchActions dragAndDrop(By sourceLocator , By destinationLocator)
    {
        WaitsManager.getSwipeWait(driver).until(f-> {

            Point startPoint = getElementCenter(driver.findElement(sourceLocator));
            Point endPoint = getElementCenter(driver.findElement(destinationLocator));

            Sequence sequence = singleFingerSwipe("finger-1",startPoint,endPoint);
            driver.perform(List.of(sequence));
            return true;
        });
        return this;
    }

    //Zoom In Element by given distance
    public W3CTouchActions zoomIn(By locator, int distance)
    {
        WaitsManager.getSwipeWait(driver).until(f-> {

            Point elementCenter = getElementCenter(driver.findElement(locator));
            Point start1 = new Point(elementCenter.getX()-50,elementCenter.getY());
            Point start2 = new Point(elementCenter.getX()+50,elementCenter.getY());

            var x = start1.getX()+ Direction.LEFT.getX()*distance;
            var y = start1.getY()+ Direction.LEFT.getY()*distance;
            Point end1 = new Point(x,y);

            var a = start1.getX()+ Direction.RIGHT.getX()*distance;
            var b = start1.getY()+ Direction.RIGHT.getY()*distance;
            Point end2 = new Point(a, b);

            Sequence sequence1 = singleFingerSwipe("finger-1",start1,end1);
            Sequence sequence2 = singleFingerSwipe("finger-2",start2,end2);

            driver.perform(List.of(sequence1,sequence2));
            return true;
        });
        return this;
    }

    //Zoom Out Element by given distance
    public W3CTouchActions zoomOut(By locator, int distance)
    {
        WaitsManager.getSwipeWait(driver).until(f-> {

            Point elementCenter = getElementCenter(driver.findElement(locator));
            Point start1 = new Point(elementCenter.getX()-50,elementCenter.getY());
            Point start2 = new Point(elementCenter.getX()+50,elementCenter.getY());

            var x = start1.getX()+ Direction.LEFT.getX()*distance;
            var y = start1.getY()+ Direction.LEFT.getY()*distance;
            Point end1 = new Point(x,y);

            var a = start1.getX()+ Direction.RIGHT.getX()*distance;
            var b = start1.getY()+ Direction.RIGHT.getY()*distance;
            Point end2 = new Point(a, b);

            Sequence sequence1 = singleFingerSwipe("finger-1",end1,start1);
            Sequence sequence2 = singleFingerSwipe("finger-2",end2,start2);

            driver.perform(List.of(sequence1,sequence2));
            return true;
        });
        return this;
    }

    //Search for the Element in the whole screen and return true if element presents
    public boolean isElementPresent(By targetLocator,Direction direction)
    {
        Point startPoint;
        Point endPoint;

        Dimension screenSize = getScreenSize ();
        var x = screenSize.getWidth () / 2;
        var y = screenSize.getHeight () / 2;
        startPoint = new Point(x,y);

        var a = x+ direction.getX()*Integer.parseInt(getPropertiesValue("SwipeDistance"));
        var b = y+ direction.getY()*Integer.parseInt(getPropertiesValue("SwipeDistance"));
        endPoint = getCorrectedCoordinates(null,new Point(a,b));

        try{
            WaitsManager.getSwipeWait(driver).until(g->{
                Sequence sequence = singleFingerSwipe("finger-1",startPoint,endPoint);
                driver.perform(List.of(sequence));
                return (driver.findElement(targetLocator).isDisplayed());
            });
        }catch (TimeoutException e){
            return false;
        }
        return true;
    }

    //Build the sequence of swipe by one finger according to W3c Touch Actions
    private Sequence singleFingerSwipe(String fingerName, Point start , Point end)
    {
        PointerInput finger = new PointerInput(TOUCH,fingerName);
        Sequence sequence = new Sequence(finger,0);

        sequence.addAction(finger.createPointerMove(Duration.ofMillis(0),viewport(),start.getX(),start.getY()));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        if (end != null)
        {
            sequence.addAction(new Pause(finger,Duration.ofMillis(500)));
            sequence.addAction(finger.createPointerMove(Duration.ofMillis(Long.parseLong(getPropertiesValue("SwipeRate"))),viewport(),end.getX(),end.getY()));
        }
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        return sequence;
    }

    //Get the Center Coordinate of the Element
    private Point getElementCenter (WebElement element) {
        Point location = element.getLocation ();
        Dimension size = element.getSize ();
        int x = (size.getWidth () / 2) + location.getX ();
        int y = (size.getHeight () / 2) + location.getY ();
        return getCorrectedCoordinates (element, new Point (x, y));
    }

    //Get the Center Coordinate of the Element and verify that it's not outside the screen
    private Point getCorrectedCoordinates (WebElement element, Point point) {
        Dimension screenSize = getScreenSize ();
        int x = point.getX ();
        int y = point.getY ();
        int w = screenSize.getWidth ();
        int h = screenSize.getHeight ();

        if (element != null) {
            final var size = element.getSize ();
            final var location = element.getLocation ();
            w = size.getWidth () + location.getX ();
            h = size.getHeight () + location.getY ();
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
        return new Point (x, y);
    }

    //Get the Dimensions of Screen Size
    private Dimension getScreenSize () {
        return driver.manage ()
                .window ()
                .getSize ();
    }
}
*/
