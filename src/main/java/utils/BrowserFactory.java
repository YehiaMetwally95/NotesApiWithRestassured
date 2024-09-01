package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Arrays;

import static utils.PropertiesManager.getPropertiesValue;

public class BrowserFactory {

    public static WebDriver openBrowser()
    {
        WebDriver driver = null;
        switch (getPropertiesValue("browserType"))
        {
            case "Chrome" :
                driver= new ChromeDriver(BrowserFactory.getChromeOptions());
                break;

            case "Firefox" :
                driver= new FirefoxDriver(BrowserFactory.getFireFoxOptions());
                break;

            case "Edge" :
                driver= new EdgeDriver(BrowserFactory.getEdgeOptions());

                break;
            default:
                System.out.println("Wrong driver name");
        }
        return driver;
    }

    public static ChromeOptions getChromeOptions()
    {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--disable-infobars");
        option.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));

        if (getPropertiesValue("windowMaximized").equalsIgnoreCase("true"))
            option.addArguments("--start-maximized");
        if (getPropertiesValue("headless").equalsIgnoreCase("true"))
            option.addArguments("--headless");
        return option;
    }

    public static EdgeOptions getEdgeOptions()
    {
        EdgeOptions option = new EdgeOptions();
        option.addArguments("--disable-infobars");
        option.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        option.addArguments("--guest");

        if (getPropertiesValue("windowMaximized").equalsIgnoreCase("true"))
            option.addArguments("--start-maximized");
        if (getPropertiesValue("headless").equalsIgnoreCase("true"))
            option.addArguments("--headless");
        return option;
    }

    public static FirefoxOptions getFireFoxOptions()
    {
        FirefoxOptions option = new FirefoxOptions();
        if (getPropertiesValue("windowMaximized").equalsIgnoreCase("false"))
            option.addArguments("--start-minimized");
        if (getPropertiesValue("headless").equalsIgnoreCase("true"))
            option.addArguments("--headless");
        return option;
    }




}
