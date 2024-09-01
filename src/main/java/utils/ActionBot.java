package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.time.Duration;

import static utils.Screenshot.takeElementScreenshot;
import static utils.RandomDataGenerator.generateInteger;

public class ActionBot {

    static String textBoxesPath = "src/test/resources/Screenshots/TextBoxes/";
    static String pressedButtonsPath ="src/test/resources/Screenshots/PressedButtons/";
    static String retrievedTextPath ="src/test/resources/Screenshots/RetrievedTexts/";

    //ActionBot1 for Typing on TextBox
    public static void type (WebDriver driver , By locator , String text) throws IOException {
        Waits.getFluentWait(driver).until(f -> {
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(text);
            return true;
        });

        System.out.println("Typing " + text);

        takeElementScreenshot(driver,locator,textBoxesPath,generateInteger());
    }

    //ActionBot2 for Pressing on Button or Link
    public static void press(WebDriver driver,By locator) throws IOException {

        Waits.getFluentWait(driver).until(f -> {
            System.out.println("Clicking On " + driver.findElement(locator).getText());
            try {
                takeElementScreenshot(driver,locator,pressedButtonsPath,generateInteger());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            driver.findElement(locator).click();
            return true;
         });

    }

    //ActionBot3 for Get Text from Element
    public static String readText(WebDriver driver,By locator) throws IOException {
        Waits.getFluentWait(driver).until(f -> {
          driver.findElement(locator).getText();
            return true;
        });

        takeElementScreenshot(driver,locator,retrievedTextPath,generateInteger());

        return driver.findElement(locator).getText().replace("\n","");

    }

    //ActionBot4 for Verify Element is Displayed
    public static boolean isElementDisplayed(WebDriver driver,By locator){
        Waits.getFluentWait(driver).until(f -> {
            driver.findElement(locator).isDisplayed();
            return true;
        });
        return true;
    }

    //ActionBot5 for Verify Element is Enabled
    public static boolean isElementEnabled(WebDriver driver,By locator){
        Waits.getFluentWait(driver).until(f -> {
            driver.findElement(locator).isEnabled();
            return true;
        });
        return true;
    }

    //ActionBot6 for Verify Element is Selected
    public static boolean isElementSelected(WebDriver driver,By locator){
        Waits.getFluentWait(driver).until(f -> {
            driver.findElement(locator).isSelected();
            return true;
        });
        return true;
    }

    //ActionBot7 for Hover on Main-menu & Sub-menu
    public static void hoverToSubMenu(WebDriver driver,By mainMenuLocator , By subMenuLocator) {
        Waits.getFluentWait(driver).until(f -> {
            new Actions(driver)
                    .moveToElement(driver.findElement(mainMenuLocator))
                    .pause(Duration.ofMillis(100))
                    .moveToElement(driver.findElement(subMenuLocator))
                    .click()
                    .perform();
            return true;
        });
    }
}
