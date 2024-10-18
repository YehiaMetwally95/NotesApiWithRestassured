package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class BrowserFactory {

    private static String browserType = System.getProperty("browserType");
    private static String isHeadless = System.getProperty("isHeadless");
    private static String isMaximized = System.getProperty("isMaximized");
    private static String executionType = System.getProperty("executionType");
    private static String remoteExecutionHost = System.getProperty("remoteExecutionHost");
    private static String remoteExecutionPort = System.getProperty("remoteExecutionPort");

    public static WebDriver openBrowser(String browserType) throws MalformedURLException {
        RemoteWebDriver driver = null;

        if (executionType.equalsIgnoreCase("Local"))
        {
            switch (browserType)
            {
                case "Chrome" :
                    driver = new ChromeDriver(getChromeOptions());
                    break;

                case "Firefox" :
                    driver = new FirefoxDriver(getFireFoxOptions());
                    break;

                case "Edge" :
                    driver = new EdgeDriver(getEdgeOptions());
                    break;
                default:
                    System.out.println("Wrong driver name");
            }
        }

        else if (executionType.equalsIgnoreCase("Remote"))
        {
            switch (browserType)
            {
                case "Chrome" :
                    driver = new RemoteWebDriver(
                            new URL("http://" + remoteExecutionHost + ":" + remoteExecutionPort + "/wd/hub")
                            ,getChromeOptions());
                    driver.setFileDetector(new LocalFileDetector());
                    break;

                case "Firefox" :
                    driver = new RemoteWebDriver(
                            new URL("http://" + remoteExecutionHost + ":" + remoteExecutionPort + "/wd/hub")
                            ,getFireFoxOptions());
                    driver.setFileDetector(new LocalFileDetector());
                    break;

                case "Edge" :
                    driver = new RemoteWebDriver(
                            new URL("http://" + remoteExecutionHost + ":" + remoteExecutionPort + "/wd/hub")
                            ,getEdgeOptions());
                    driver.setFileDetector(new LocalFileDetector());
                    break;
                default:
                    System.out.println("Wrong driver name");
            }
        }
        return driver;
    }

    public static WebDriver openBrowser() throws MalformedURLException {
        RemoteWebDriver driver = null;

        if (executionType.equalsIgnoreCase("Local"))
        {
            switch (browserType)
            {
                case "Chrome" :
                    driver = new ChromeDriver(getChromeOptions());
                    break;

                case "Firefox" :
                    driver = new FirefoxDriver(getFireFoxOptions());
                    break;

                case "Edge" :
                    driver = new EdgeDriver(getEdgeOptions());
                    break;
                default:
                    System.out.println("Wrong driver name");
            }
        }

        else if (executionType.equalsIgnoreCase("Remote"))
        {
            switch (browserType)
            {
                case "Chrome" :
                    driver = new RemoteWebDriver(
                            new URL("http://" + remoteExecutionHost + ":" + remoteExecutionPort + "/wd/hub")
                            ,getChromeOptions());
                    driver.setFileDetector(new LocalFileDetector());
                    break;

                case "Firefox" :
                    driver = new RemoteWebDriver(
                            new URL("http://" + remoteExecutionHost + ":" + remoteExecutionPort + "/wd/hub")
                            ,getFireFoxOptions());
                    driver.setFileDetector(new LocalFileDetector());
                    break;

                case "Edge" :
                    driver = new RemoteWebDriver(
                            new URL("http://" + remoteExecutionHost + ":" + remoteExecutionPort + "/wd/hub")
                            ,getEdgeOptions());
                    driver.setFileDetector(new LocalFileDetector());
                    break;
                default:
                    System.out.println("Wrong driver name");
            }
        }
        return driver;
    }

    public static ChromeOptions getChromeOptions()
    {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--disable-infobars");
        option.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        if (isMaximized.equalsIgnoreCase("true"))
            option.addArguments("--start-maximized");
        if (isHeadless.equalsIgnoreCase("true"))
            option.addArguments("--headless");

        return option;
    }

    public static EdgeOptions getEdgeOptions()
    {
        EdgeOptions option = new EdgeOptions();
        option.addArguments("--disable-infobars");
        option.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        if (isMaximized.equalsIgnoreCase("true"))
            option.addArguments("--start-maximized");
        if (isHeadless.equalsIgnoreCase("true"))
            option.addArguments("--headless");

        return option;
    }

    public static FirefoxOptions getFireFoxOptions()
    {
        FirefoxOptions option = new FirefoxOptions();
        if (isMaximized.equalsIgnoreCase("true"))
            option.addArguments("--start-minimized");
        if (isHeadless.equalsIgnoreCase("true"))
            option.addArguments("--headless");
        return option;
    }
}
