package utils;

import org.openqa.selenium.WebDriver;

public class WindowManager {

    public static void navigateForward(WebDriver driver) {driver.navigate().forward();}

    public static void navigateBackward(WebDriver driver)
    {
        driver.navigate().back();
    }

    public static void refreshWindow(WebDriver driver)
    {
        driver.navigate().refresh();
    }

    public static void navigateToURL(WebDriver driver,String url)
    {
        driver.navigate().to(url);
    }

    public static void maximizeWindow(WebDriver driver)
    {
        driver.manage().window().maximize();
    }

    public static void minimizeWindow(WebDriver driver)
    {
        driver.manage().window().minimize();
    }

    public static void fullScreenWindow(WebDriver driver)
    {
        driver.manage().window().fullscreen();
    }

    public static void closeCurrentWindow(WebDriver driver)
    {
        driver.close();
    }

    public static void closeAllWindows(WebDriver driver)
    {
        driver.quit();
    }

}
