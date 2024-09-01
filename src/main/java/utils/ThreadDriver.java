package utils;

import org.openqa.selenium.WebDriver;

public class ThreadDriver {

    public static WebDriver getIsolatedDriver (ThreadLocal<WebDriver> threadDriver)
    {
        return threadDriver.get();
    }

    public static void setIsolatedDriver(ThreadLocal<WebDriver> threadDriver, WebDriver driver)
    {
        threadDriver.set(driver);
    }

    public static void removeIsolatedDriver (ThreadLocal<WebDriver> threadDriver)
    {
        threadDriver.remove();
    }
}
