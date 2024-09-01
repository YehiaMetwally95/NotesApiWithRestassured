package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class DropdownActions {

    public static void selectFromDropdownByValue(WebDriver driver, By dropdown , String value)
    {
        dropDownElement(driver,dropdown).selectByValue(value);
    }

    public static void selectFromDropdownByIndex(WebDriver driver, By dropdown , int index)
    {
        dropDownElement(driver,dropdown).selectByIndex(index);
    }

    public static void selectFromDropdownByText(WebDriver driver, By dropdown , String text)
    {
        dropDownElement(driver,dropdown).selectByVisibleText(text);
    }

    public static List<String> getAllOptionsAsString(WebDriver driver, By dropdown)
    {
        List<WebElement> options = dropDownElement(driver,dropdown).getOptions();
        return options.stream().map(e->e.getText()).collect(Collectors.toList());
    }

    public static String getSelectedOption(WebDriver driver, By dropdown)
    {
        return dropDownElement(driver,dropdown).getFirstSelectedOption().getText();
    }

    public static void deselectAllOptions(WebDriver driver, By dropdown)
    {
        dropDownElement(driver,dropdown).deselectAll();
    }

    private static Select dropDownElement(WebDriver driver , By dropdownLocator)
    {
        Waits.getFluentWait(driver).until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        return new Select(driver.findElement(dropdownLocator));
    }
}
