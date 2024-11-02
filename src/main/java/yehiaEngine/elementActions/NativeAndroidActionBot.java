package yehiaEngine.elementActions;/*
package engine.elementActions;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static engine.elementActions.WaitsManager.getFluentWait;
import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;


public class NativeAndroidActionBot {

    AppiumDriver driver;

    static String textBoxesPath = "src/test/resources/Screenshots/TextBoxes/";
    static String pressedButtonsPath ="src/test/resources/Screenshots/PressedButtons/";
    static String retrievedTextPath ="src/test/resources/Screenshots/RetrievedTexts/";

    public enum ScrollDirection {
        HORIZONTAL,VERTICAL
    }
    public enum LocatorType {
        TEXT,RESOURCE_ID,ACCESSIBILITY_ID,ClASS_NAME
    }

    public NativeAndroidActionBot(AppiumDriver driver)
    {
        this.driver = driver;
    }

    //Scroll Into Screen to Button or Link in a given direction then Press on it Using locator as a text and type of By
    public NativeAndroidActionBot tab (LocatorType type, String locator, ScrollDirection direction)
    {
        getFluentWait(driver).until(f->
        {
            new Actions(driver).moveToElement(driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(type,locator,direction)))).click().perform();
            return true;
        });
        return this;
    }

    //Scroll Into Screen to Button or Link in a given direction then Press on it Using By locator
    public NativeAndroidActionBot tab (By locator)
    {
        getFluentWait(driver).until(f->
        {
            new Actions(driver).moveToElement(driver.findElement(locator)).click().perform();
            return true;
        });
        return this;
    }

    //Scroll Into Screen to Button or Link in a given direction then Long Press on it Using locator as a text and type of By
    public NativeAndroidActionBot longTab (LocatorType type, String locator, ScrollDirection direction)
    {
        getFluentWait(driver).until(f->
        {
            new Actions(driver).moveToElement(driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(type,locator,direction)))).clickAndHold().perform();
            return true;
        });
        return this;
    }

    //Scroll Into Screen to Button or Link in a given direction then Long Press on it Using By locator
    public NativeAndroidActionBot longTab (By locator)
    {
        getFluentWait(driver).until(f->
        {
            new Actions(driver).moveToElement(driver.findElement(locator)).clickAndHold().perform();
            return true;
        });
        return this;
    }

    //Scroll Into Screen to Button or Link in a given direction then Double Press on it Using locator as a text and type of By
    public NativeAndroidActionBot doubleTab (LocatorType type, String locator, ScrollDirection direction)
    {
        getFluentWait(driver).until(f->
        {
            new Actions(driver).moveToElement(driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(type,locator,direction)))).doubleClick().perform();
            return true;
        });
        return this;
    }

    //Scroll Into Screen to Button or Link in a given direction then Double Press on it Using By locator
    public NativeAndroidActionBot doubleTab (By locator)
    {
        getFluentWait(driver).until(f->
        {
            new Actions(driver).moveToElement(driver.findElement(locator)).doubleClick().perform();
            return true;
        });
        return this;
    }

    //Scroll Into Screen to Button or Link in a given direction then type text on it Using locator as a text and type of By
    public NativeAndroidActionBot type (LocatorType type, String locator, ScrollDirection direction, String text) throws IOException {
        getFluentWait(driver).until(f->
        {
            driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(type,locator,direction))).clear();
            driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(type,locator,direction))).sendKeys(text);
            return true;
        });
        return this;
    }

    //Scroll Into Screen to Button or Link in a given direction then type text on it Using By locator
    public NativeAndroidActionBot type (By locator,String text) throws IOException {
        getFluentWait(driver).until(f -> {
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(text);
            return true;
        });
        return this;
    }

    //Scroll Into Screen to Button or Link in a given direction then read text from it Using locator as a text and type of By
    public String readText (LocatorType type,String locator,ScrollDirection direction) throws IOException {
        getFluentWait(driver).until(f->
        {
            driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(type,locator,direction))).getText();
            return true;
        });
        return driver.findElement(AppiumBy.androidUIAutomator(
                getLocatorQuery(type,locator,direction))).getText();
    }

    //Scroll Into Screen to Button or Link in a given direction then read the text in its child Class "Can be TextView or EditText"
    public String readChildText (LocatorType type,String locator,ScrollDirection direction,String widgetClassName) throws IOException {
        getFluentWait(driver).until(f->
        {
            driver.findElement(AppiumBy.androidUIAutomator(
                    getChildTextQuery(type,locator,direction,widgetClassName))).getText();
            return true;
        });
        return driver.findElement(AppiumBy.androidUIAutomator(
                getChildTextQuery(type,locator,direction,widgetClassName))).getText();
    }

    //Scroll Into Screen to Button or Link in a given direction then read text from it Using By locator
    public String readText (By locator) throws IOException {
        getFluentWait(driver).until(f -> {
            driver.findElement(locator).getText();
            return true;
        });
        return driver.findElement(locator).getText().replace("\n","");
    }

    //Scroll Into Screen till reach a Button or Link in a given direction
    public NativeAndroidActionBot swipeIntoScreen (LocatorType type, String targetLocator, ScrollDirection direction)
    {
        getFluentWait(driver).until(f->
        {
            driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(type,targetLocator,direction)));
            return true;
        });
        return this;
    }

    //Scroll Into an Element till reach a Button or Link in a given direction
    public NativeAndroidActionBot swipeIntoElement(LocatorType scrollableType, String scrollableLocator, LocatorType targetType, String targetLocator, ScrollDirection direction)
    {
        getFluentWait(driver).until(f->
        {
            driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(scrollableType,scrollableLocator,targetType,targetLocator,direction)));
            return true;
        });
        return this;
    }

    //Scroll Into an Element till reach a Button or Link in a given direction then click on it
    public NativeAndroidActionBot swipeIntoElementAndTap(LocatorType scrollableType, String scrollableLocator, LocatorType targetType, String targetLocator, ScrollDirection direction)
    {
        getFluentWait(driver).until(f->
        {
            driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(scrollableType,scrollableLocator,targetType,targetLocator,direction)));

            new Actions(driver).moveToElement(driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(scrollableType,scrollableLocator,targetType,targetLocator,direction)))).click().perform();
            return true;
        });
        return this;
    }

    //Scroll Into an Element till reach a Button or Link in a given direction then type on it
    public NativeAndroidActionBot swipeIntoElementAndType(LocatorType scrollableType, String scrollableLocator, LocatorType targetType, String targetLocator, ScrollDirection direction, String text) throws IOException {
        getFluentWait(driver).until(f->
        {
            driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(scrollableType,scrollableLocator,targetType,targetLocator,direction))).clear();
            new Actions(driver).moveToElement(driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(scrollableType,scrollableLocator,targetType,targetLocator,direction)))).sendKeys(text).perform();
            return true;
        });
        return this;
    }

    //Scroll Into an Element till reach a Button or Link in a given direction then read text from it
    public String swipeIntoElementAndReadText(LocatorType scrollableType, String scrollableLocator, LocatorType targetType, String targetLocator, ScrollDirection direction) throws IOException {
        getFluentWait(driver).until(f->
        {
            driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(scrollableType,scrollableLocator,targetType,targetLocator,direction))).getText();
            return true;
        });
        return driver.findElement(AppiumBy.androidUIAutomator(
                getLocatorQuery(scrollableType,scrollableLocator,targetType,targetLocator,direction))).getText();
    }

    //Scroll Into Screen in a given direction till reach a Button then zoom it in
    public NativeAndroidActionBot zoomIn (LocatorType type, String locator, ScrollDirection direction, int distance)
    {
        getFluentWait(driver).until(f->
        {
            Point elementCenter = getElementCenter(driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(type,locator,direction))));
            Point start1 = new Point(elementCenter.getX()-50,elementCenter.getY());
            Point start2 = new Point(elementCenter.getX()+50,elementCenter.getY());

            var x = start1.getX()+ W3CTouchActions.Direction.LEFT.getX()*distance;
            var y = start1.getY()+ W3CTouchActions.Direction.LEFT.getY()*distance;
            Point end1 = new Point(x,y);

            var a = start1.getX()+ W3CTouchActions.Direction.RIGHT.getX()*distance;
            var b = start1.getY()+ W3CTouchActions.Direction.RIGHT.getY()*distance;
            Point end2 = new Point(a, b);

            Sequence sequence1 = singleFingerSwipe("finger-1",start1,end1);
            Sequence sequence2 = singleFingerSwipe("finger-2",start2,end2);

            driver.perform(List.of(sequence1,sequence2));
            return true;
        });
        return this;
    }

    //Scroll Into Screen in a given direction till reach a Button then zoom it out
    public NativeAndroidActionBot zoomOut (LocatorType type, String locator, ScrollDirection direction, int distance)
    {
        getFluentWait(driver).until(f->
        {
            Point elementCenter = getElementCenter(driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(type,locator,direction))));
            Point start1 = new Point(elementCenter.getX()-50,elementCenter.getY());
            Point start2 = new Point(elementCenter.getX()+50,elementCenter.getY());

            var x = start1.getX()+ W3CTouchActions.Direction.LEFT.getX()*distance;
            var y = start1.getY()+ W3CTouchActions.Direction.LEFT.getY()*distance;
            Point end1 = new Point(x,y);

            var a = start1.getX()+ W3CTouchActions.Direction.RIGHT.getX()*distance;
            var b = start1.getY()+ W3CTouchActions.Direction.RIGHT.getY()*distance;
            Point end2 = new Point(a, b);

            Sequence sequence1 = singleFingerSwipe("finger-1",end1,start1);
            Sequence sequence2 = singleFingerSwipe("finger-2",end2,start2);

            driver.perform(List.of(sequence1,sequence2));
            return true;
        });
        return this;
    }

    //Scroll Into Screen in a given direction till reach a source Button then drag it then Scroll Into Screen in a given direction till reach the destination button to drop it
    public NativeAndroidActionBot dragAndDrop(LocatorType sourceLocatorType, String sourceLocator, ScrollDirection sourceDirection, LocatorType destinationLocatorType, String destinationLocator, ScrollDirection destinationDirection)
    {
        getFluentWait(driver).until(f-> {
            Point startPoint = getElementCenter(driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(sourceLocatorType,sourceLocator,sourceDirection))));
            Point endPoint = getElementCenter(driver.findElement(AppiumBy.androidUIAutomator(
                    getLocatorQuery(destinationLocatorType,destinationLocator,destinationDirection))));

            Sequence sequence = singleFingerSwipe("finger-1",startPoint,endPoint);
            driver.perform(List.of(sequence));
            return true;
        });
        return this;
    }

    //Drag and drop an element on the view port without Scrolling
    public NativeAndroidActionBot dragAndDrop(By sourceLocator, By destinationLocator)
    {
        getFluentWait(driver).until(f-> {
            Point startPoint = getElementCenter(driver.findElement(sourceLocator));
            Point endPoint = getElementCenter(driver.findElement(destinationLocator));

            Sequence sequence = singleFingerSwipe("finger-1",startPoint,endPoint);
            driver.perform(List.of(sequence));
            return true;
        });
        return this;
    }

    //Check if the element is displayed on the view port without Scrolling
    public boolean isElementDisplayed(By locator){
        try{
            getFluentWait(driver).until(f -> {
                driver.findElement(locator).isDisplayed();
                return true;
            });
        }catch(TimeoutException e){
            return false;
        }
        return true;
    }

    //Check if the element present in the whole page by scrolling in a given direction (Up/Down or Left/Right)
    public boolean isElementPresent(LocatorType type,String locator,ScrollDirection direction)
    {
        try{
            getFluentWait(driver).until(f->
            {
                driver.findElement(AppiumBy.androidUIAutomator(getLocatorQuery(type,locator,direction)));
                return true;
            });

        }catch (TimeoutException e)
        {
            return false;
        }
        return true;
    }

    //Get the Locator text that will be used in AppiumBy.AndroidUIAutomator to perform scrolling into screen to the target element locator before finding it
    private String getLocatorQuery(LocatorType type, String locator, ScrollDirection direction)
    {
        String query = null;
        switch(type)
        {
            case LocatorType.TEXT:
            {
                if(direction ==ScrollDirection.VERTICAL)
                {
                    query = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" +
                            "new UiSelector().textContains(\"" + locator + "\"))";
                }

                else if(direction==ScrollDirection.HORIZONTAL)
                {

                    query = "new UiScrollable(new UiSelector()).setAsHorizontalList().scrollIntoView("
                            + "new UiSelector().textContains(\"" + locator + "\"))";
                }
                break;
            }

            case LocatorType.ACCESSIBILITY_ID:
            {
                if(direction ==ScrollDirection.VERTICAL)
                {
                    query = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" +
                            "new UiSelector().description(\"" + locator + "\"))";
                }

                else if(direction==ScrollDirection.HORIZONTAL)
                {

                    query = "new UiScrollable(new UiSelector()).setAsHorizontalList().scrollIntoView("
                            + "new UiSelector().description(\"" + locator + "\"))";
                }
                break;
            }

            case LocatorType.RESOURCE_ID:
            {
                if(direction ==ScrollDirection.VERTICAL)
                {
                    query = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" +
                            "new UiSelector().resourceId(\"" + locator + "\"))";
                }

                else if(direction==ScrollDirection.HORIZONTAL)
                {

                    query = "new UiScrollable(new UiSelector()).setAsHorizontalList().scrollIntoView("
                            + "new UiSelector().resourceId(\"" + locator + "\"))";
                }
                break;
            }

            case LocatorType.ClASS_NAME:
            {
                if(direction ==ScrollDirection.VERTICAL)
                {
                    query = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" +
                            "new UiSelector().className(\"" + locator + "\"))";
                }

                else if(direction==ScrollDirection.HORIZONTAL)
                {

                    query = "new UiScrollable(new UiSelector()).setAsHorizontalList().scrollIntoView("
                            + "new UiSelector().className(\"" + locator + "\"))";
                }
                break;
            }

        }
        return query;
    }

    //Get the Locator text that will be used in AppiumBy.AndroidUIAutomator to perform scrolling into a scrollable element to the target element locator before finding it
    private String getLocatorQuery(LocatorType scrollableType, String scrollableLocator, LocatorType targetType, String targetLocator, ScrollDirection direction)
    {
        String query = null;
        if (direction==ScrollDirection.VERTICAL)
        {
            switch (scrollableType)
            {
                case TEXT:
                {
                    if(targetType==LocatorType.TEXT)
                    {
                        query = "new UiScrollable(new UiSelector().textMatches(\".*"+scrollableLocator+"\").scrollable(true)).scrollIntoView(new UiSelector().textContains(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.ACCESSIBILITY_ID)
                    {
                        query = "new UiScrollable(new UiSelector().textMatches(\".*"+scrollableLocator+"\").scrollable(true)).scrollIntoView(new UiSelector().description(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.RESOURCE_ID)
                    {
                        query = "new UiScrollable(new UiSelector().textMatches(\".*"+scrollableLocator+"\").scrollable(true)).scrollIntoView(new UiSelector().resourceId(\""+targetLocator+"\"))";
                    }
                    break;
                }

                case ACCESSIBILITY_ID:
                {
                    if(targetType==LocatorType.TEXT)
                    {
                        query = "new UiScrollable(new UiSelector().descriptionMatches(\".*"+scrollableLocator+"\").scrollable(true)).scrollIntoView(new UiSelector().textContains(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.ACCESSIBILITY_ID)
                    {
                        query = "new UiScrollable(new UiSelector().descriptionMatches(\".*"+scrollableLocator+"\").scrollable(true)).scrollIntoView(new UiSelector().description(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.RESOURCE_ID)
                    {
                        query = "new UiScrollable(new UiSelector().descriptionMatches(\".*"+scrollableLocator+"\").scrollable(true)).scrollIntoView(new UiSelector().resourceId(\""+targetLocator+"\"))";
                    }
                    break;
                }

                case RESOURCE_ID:
                {
                    if(targetType==LocatorType.TEXT)
                    {
                        query = "new UiScrollable(new UiSelector().resourceIdMatches(\".*"+scrollableLocator+"\").scrollable(true)).scrollIntoView(new UiSelector().textContains(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.ACCESSIBILITY_ID)
                    {
                        query = "new UiScrollable(new UiSelector().resourceIdMatches(\".*"+scrollableLocator+"\").scrollable(true)).scrollIntoView(new UiSelector().description(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.RESOURCE_ID)
                    {
                        query = "new UiScrollable(new UiSelector().resourceIdMatches(\".*"+scrollableLocator+"\").scrollable(true)).scrollIntoView(new UiSelector().resourceId(\""+targetLocator+"\"))";
                    }
                    break;
                }

                case ClASS_NAME:
                {
                    if(targetType==LocatorType.TEXT)
                    {
                        query = "new UiScrollable(new UiSelector().classNameMatches(\".*"+scrollableLocator+"\").scrollable(true)).scrollIntoView(new UiSelector().textContains(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.ACCESSIBILITY_ID)
                    {
                        query = "new UiScrollable(new UiSelector().classNameMatches(\".*"+scrollableLocator+"\").scrollable(true)).scrollIntoView(new UiSelector().description(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.RESOURCE_ID)
                    {
                        query = "new UiScrollable(new UiSelector().classNameMatches(\".*"+scrollableLocator+"\").scrollable(true)).scrollIntoView(new UiSelector().resourceId(\""+targetLocator+"\"))";
                    }
                    break;
                }

            }
        }

        else if(direction==ScrollDirection.HORIZONTAL)
        {
            switch (scrollableType)
            {
                case TEXT:
                {
                    if(targetType==LocatorType.TEXT)
                    {

                        query = "new UiScrollable(new UiSelector().textMatches(\".*"+scrollableLocator+"\")).setAsHorizontalList().scrollIntoView(new UiSelector().textContains(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.ACCESSIBILITY_ID)
                    {
                        query = "new UiScrollable(new UiSelector().textMatches(\".*"+scrollableLocator+"\")).setAsHorizontalList().scrollIntoView(new UiSelector().description(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.RESOURCE_ID)
                    {
                        query = "new UiScrollable(new UiSelector().textMatches(\".*"+scrollableLocator+"\")).setAsHorizontalList().scrollIntoView(new UiSelector().resourceID(\""+targetLocator+"\"))";
                    }
                    break;
                }

                case ACCESSIBILITY_ID:
                {
                    if(targetType==LocatorType.TEXT)
                    {

                        query = "new UiScrollable(new UiSelector().descriptionMatches(\".*"+scrollableLocator+"\")).setAsHorizontalList().scrollIntoView(new UiSelector().textContains(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.ACCESSIBILITY_ID)
                    {
                        query = "new UiScrollable(new UiSelector().descriptionMatches(\".*"+scrollableLocator+"\")).setAsHorizontalList().scrollIntoView(new UiSelector().description(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.RESOURCE_ID)
                    {
                        query = "new UiScrollable(new UiSelector().descriptionMatches(\".*"+scrollableLocator+"\")).setAsHorizontalList().scrollIntoView(new UiSelector().resourceID(\""+targetLocator+"\"))";
                    }
                    break;
                }

                case RESOURCE_ID:
                {
                    if(targetType==LocatorType.TEXT)
                    {

                      query = "new UiScrollable(new UiSelector().resourceIdMatches(\".*"+scrollableLocator+"\")).setAsHorizontalList().scrollIntoView(new UiSelector().textContains(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.ACCESSIBILITY_ID)
                    {
                        query = "new UiScrollable(new UiSelector().resourceIdMatches(\".*"+scrollableLocator+"\")).setAsHorizontalList().scrollIntoView(new UiSelector().description(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.RESOURCE_ID)
                    {
                        query = "new UiScrollable(new UiSelector().resourceIdMatches(\".*"+scrollableLocator+"\")).setAsHorizontalList().scrollIntoView(new UiSelector().resourceID(\""+targetLocator+"\"))";
                    }
                    break;
                }

                case ClASS_NAME:
                {
                    if(targetType==LocatorType.TEXT)
                    {

                        query = "new UiScrollable(new UiSelector().classNameMatches(\".*"+scrollableLocator+"\")).setAsHorizontalList().scrollIntoView(new UiSelector().textContains(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.ACCESSIBILITY_ID)
                    {
                        query = "new UiScrollable(new UiSelector().classNameMatches(\".*"+scrollableLocator+"\")).setAsHorizontalList().scrollIntoView(new UiSelector().description(\""+targetLocator+"\"))";
                    }
                    else if(targetType==LocatorType.RESOURCE_ID)
                    {
                        query = "new UiScrollable(new UiSelector().classNameMatches(\".*"+scrollableLocator+"\")).setAsHorizontalList().scrollIntoView(new UiSelector().resourceID(\""+targetLocator+"\"))";
                    }
                    break;
                }
            }
        }

        return query;
    }

    //Get the Locator text that will be used in AppiumBy.AndroidUIAutomator to perform scrolling into screen to the child class of the target element locator before finding it
    private String getChildTextQuery(LocatorType type, String locator, ScrollDirection direction,String widgetClassName)
    {
        String query = null;
        switch(type)
        {
            case LocatorType.TEXT:
            {
                if(direction ==ScrollDirection.VERTICAL)
                {
                    query = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" +
                            "new UiSelector().textContains(\"" + locator + "\").childSelector(new UiSelector().className(android.widget."+widgetClassName+")))";
                }

                else if(direction==ScrollDirection.HORIZONTAL)
                {

                    query = "new UiScrollable(new UiSelector()).setAsHorizontalList.scrollIntoView(" +
                            "new UiSelector().textContains(\"" + locator + "\").childSelector(new UiSelector().className(android.widget."+widgetClassName+")))";
                }
                break;
            }

            case LocatorType.ACCESSIBILITY_ID:
            {
                if(direction ==ScrollDirection.VERTICAL)
                {
                    query = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" +
                            "new UiSelector().description(\"" + locator + "\").childSelector(new UiSelector().className(android.widget."+widgetClassName+")))";
                }

                else if(direction==ScrollDirection.HORIZONTAL)
                {

                    query = "new UiScrollable(new UiSelector()).setAsHorizontalList.scrollIntoView(" +
                            "new UiSelector().description(\"" + locator + "\").childSelector(new UiSelector().className(android.widget."+widgetClassName+")))";
                }
                break;
            }

            case LocatorType.RESOURCE_ID:
            {
                if(direction ==ScrollDirection.VERTICAL)
                {
                    query = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" +
                            "new UiSelector().resourceId(\"" + locator + "\").childSelector(new UiSelector().className(android.widget."+widgetClassName+")))";
                }

                else if(direction==ScrollDirection.HORIZONTAL)
                {

                    query = "new UiScrollable(new UiSelector()).setAsHorizontalList.scrollIntoView(" +
                            "new UiSelector().resourceId(\"" + locator + "\").childSelector(new UiSelector().className(android.widget."+widgetClassName+")))";
                }
                break;
            }

            case LocatorType.ClASS_NAME:
            {
                if(direction ==ScrollDirection.VERTICAL)
                {
                    query = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" +
                            "new UiSelector().className(\"" + locator + "\").childSelector(new UiSelector().className(android.widget."+widgetClassName+")))";
                }

                else if(direction==ScrollDirection.HORIZONTAL)
                {

                    query = "new UiScrollable(new UiSelector()).setAsHorizontalList.scrollIntoView(" +
                            "new UiSelector().className(\"" + locator + "\").childSelector(new UiSelector().className(android.widget."+widgetClassName+")))";
                }
                break;
            }

        }
        return query;
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
