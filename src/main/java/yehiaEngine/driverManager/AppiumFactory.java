package yehiaEngine.driverManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.net.MalformedURLException;
import java.net.URL;

import static yehiaEngine.loggers.LogHelper.logErrorStep;
import static yehiaEngine.loggers.LogHelper.logInfoStep;

public class AppiumFactory {
    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    private static final String executionType = System.getProperty("appiumExecutionType");
    private static final String appType = System.getProperty("appType");
    private static final String browserName = System.getProperty("browserName");
    private static final String browserVersion = System.getProperty("browserVersion");
    private static final String deviceName = System.getProperty("deviceName");
    private static final String deviceUdid = System.getProperty("deviceUdid");
    private static final String platformVersion = System.getProperty("platformVersion");
    private static final String nativeAutomationDriver = System.getProperty("nativeAutomationDriver");
    private static final String appName = System.getProperty("appName");
    private static final String appActivity = System.getProperty("appActivity");
    private static final String appiumURL = System.getProperty("AppiumServerURL");

    private static final String username = System.getProperty("username");
    private static final String accessKey = System.getProperty("accessKey");
    private static final String build = System.getProperty("build");
    private static final String testName = System.getProperty("testName");
    private static final String deviceOrientation= System.getProperty("deviceOrientation");


    public static ThreadLocal<AppiumDriver> openApp() throws MalformedURLException {
        try {
            ITestResult result = Reporter.getCurrentTestResult();
            ITestContext context = result.getTestContext();

            if (appType.equalsIgnoreCase("NativeAndroid") || appType.equalsIgnoreCase("WebAppAndroid")) {
                driver.set(new AndroidDriver(getAppiumServerURL(), getAndroidCapabilities()));
                logInfoStep("Starting [" + appName + "] ............");
            } else if (appType.equalsIgnoreCase("NativeIOS") || appType.equalsIgnoreCase("WebAppIOS")) {
                driver.set(new AndroidDriver(getAppiumServerURL(), getIOSCapabilities()));
                logInfoStep("Starting [" + appName + "] ............");
            } else {
                logErrorStep("Failed to Start the Application, The Input App Type is Incorrect");
            }

            //Set the Logger Classes with the driver
            context.setAttribute("isolatedAppiumDriver", driver);
        }catch (Exception e){
            logErrorStep("Failed to Start [" + appName + "]",e);
        }

        return driver;
    }

    public static void closeApp(AppiumDriver driver)
    {
        String appID =
                (String)driver.getCapabilities().getCapability("appium:appPackage");
        ((InteractsWithApps)driver).terminateApp(appID);
        driver.quit();
        logInfoStep("Terminating "+ appName +" ............");
    }

    private static DesiredCapabilities getAndroidCapabilities()
    {
        DesiredCapabilities cap;
        cap = new DesiredCapabilities();
        cap.setCapability("appium:newCommandTimeout",600);

            //Device Capabilities
        cap.setCapability("appium:deviceName",deviceName);
        cap.setCapability("appium:udid",deviceUdid);

            //Platform Capabilities
        cap.setCapability("appium:automationName",nativeAutomationDriver);

        if (executionType.equalsIgnoreCase("Local"))
        {
            cap.setCapability("appium:platformName","Android");
            cap.setCapability("appium:platformVersion",platformVersion+".0");

            //Application Capabilities for Native App
            if (appType.equalsIgnoreCase("NativeAndroid"))
            {
                cap.setCapability("appium:app", System.getProperty("user.dir")+"\\src\\main\\resources\\apps\\"+appName);
                cap.setCapability("appium:appActivity",appActivity);
            }

            //Browser Capabilities for Web-Based App
            else if (appType.equalsIgnoreCase("WebAppAndroid"))
            {
                cap.setCapability("appium:chromedriverAutodownload","true");
                cap.setCapability("appium:autoWebview","false");
                cap.setCapability(CapabilityType.BROWSER_NAME,browserName);
                cap.setCapability(CapabilityType.BROWSER_VERSION,browserVersion);
            }
        }

        else if (executionType.equalsIgnoreCase("SauceLabs"))
        {
            cap.setCapability("appium:platformName","Android");
            cap.setCapability("appium:platformVersion",platformVersion);

            //Application Capabilities for Native App
            if (appType.equalsIgnoreCase("NativeAndroid"))
            {
                cap.setCapability("appium:app","storage:filename="+appName);
                cap.setCapability("sauce:options",getSauceLabsCapabilities());
            }

            //Browser Capabilities for Web-Based App
            else if (appType.equalsIgnoreCase("WebAppAndroid"))
            {
                cap.setCapability("appium:chromedriverAutodownload","true");
                cap.setCapability("appium:autoWebview","false");
                cap.setCapability(CapabilityType.BROWSER_NAME,browserName);
                cap.setCapability(CapabilityType.BROWSER_VERSION,browserVersion);
                cap.setCapability("sauce:options",getSauceLabsCapabilities());
            }
        }

        else if (executionType.equalsIgnoreCase("BrowserStack"))
        {
            cap.setCapability("appium:platformName","android");
            cap.setCapability("appium:platformVersion",platformVersion+".0");
            //Application Capabilities for Native App
            if (appType.equalsIgnoreCase("NativeAndroid"))
            {
                cap.setCapability("appium:app","bs://"+appName);
                cap.setCapability("bstack:options",getBrowserStackCapabilities());
            }

            //Browser Capabilities for Web-Based App
            else if (appType.equalsIgnoreCase("WebAppAndroid"))
            {
                cap.setCapability("appium:chromedriverAutodownload","true");
                cap.setCapability("appium:autoWebview","false");
                cap.setCapability(CapabilityType.BROWSER_NAME,browserName);
                cap.setCapability(CapabilityType.BROWSER_VERSION,browserVersion);
                cap.setCapability("bstack:options",getBrowserStackCapabilities());
            }
        }

        return cap;
    }

    private static DesiredCapabilities getIOSCapabilities()
    {
        DesiredCapabilities cap;
        cap = new DesiredCapabilities();
        cap.setCapability("appium:newCommandTimeout",600);

        //Device Capabilities
        cap.setCapability("appium:deviceName",deviceName);
        cap.setCapability("appium:udid",deviceUdid);

        //Platform Capabilities
        cap.setCapability("appium:platformVersion",platformVersion);
        cap.setCapability("appium:automationName",nativeAutomationDriver);

        if (executionType.equalsIgnoreCase("Local"))
        {
            //Application Capabilities for Native App
            if (appType.equalsIgnoreCase("NativeIOS"))
            {
                cap.setCapability("appium:platformName","Ios");
                cap.setCapability("appium:app", System.getProperty("user.dir")+"\\src\\main\\resources\\apps\\"+appName);
                cap.setCapability("appium:appActivity",appActivity);
            }

            //Browser Capabilities for Web-Based App
            else if (appType.equalsIgnoreCase("WebAppIOS"))
            {
                cap.setCapability("appium:platformName","Ios");
                cap.setCapability(CapabilityType.BROWSER_NAME,browserName);
                cap.setCapability(CapabilityType.BROWSER_VERSION,browserVersion);
            }
        }

        else if (executionType.equalsIgnoreCase("SauceLabs"))
        {
            //Application Capabilities for Native App
            if (appType.equalsIgnoreCase("NativeIOS"))
            {
                cap.setCapability("appium:platformName","Ios");
                cap.setCapability("appium:app","storage:filename="+appName);
                cap.setCapability("sauce:options",getSauceLabsCapabilities());
            }

            //Browser Capabilities for Web-Based App
            else if (appType.equalsIgnoreCase("WebAppIOS"))
            {
                cap.setCapability("appium:platformName","Ios");
                cap.setCapability(CapabilityType.BROWSER_NAME,browserName);
                cap.setCapability(CapabilityType.BROWSER_VERSION,browserVersion);
                cap.setCapability("sauce:options",getSauceLabsCapabilities());
            }
        }

        else if (executionType.equalsIgnoreCase("BrowserStack"))
        {
            //Application Capabilities for Native App
            if (appType.equalsIgnoreCase("NativeIOS"))
            {
                cap.setCapability("platformName","Ios");
                cap.setCapability("appium:app","bs://"+appName);
                cap.setCapability("bstack:options",getBrowserStackCapabilities());
            }

            //Browser Capabilities for Web-Based App
            else if (appType.equalsIgnoreCase("WebAppIOS"))
            {
                cap.setCapability("platformName","Ios");
                cap.setCapability(CapabilityType.BROWSER_NAME,browserName);
                cap.setCapability(CapabilityType.BROWSER_VERSION,browserVersion);
                cap.setCapability("bstack:options",getBrowserStackCapabilities());
            }
        }

        return cap;
    }

    private static DesiredCapabilities getSauceLabsCapabilities()
    {
        DesiredCapabilities sauceOptions;
        sauceOptions = new DesiredCapabilities();
        sauceOptions.setCapability("appiumVersion", "latest");
        sauceOptions.setCapability("username", username);
        sauceOptions.setCapability("accessKey", accessKey);
        sauceOptions.setCapability("build", build);
        sauceOptions.setCapability("name", testName);
        sauceOptions.setCapability("deviceOrientation", deviceOrientation);
        return sauceOptions;
    }

    private static DesiredCapabilities getBrowserStackCapabilities()
    {
        DesiredCapabilities browserStackOptions;
        browserStackOptions = new DesiredCapabilities();
  //      browserStackOptions.setCapability("appiumVersion", "2.6.0");
        browserStackOptions.setCapability("userName", username);
        browserStackOptions.setCapability("accessKey", accessKey);
        browserStackOptions.setCapability("buildName", build);
        browserStackOptions.setCapability("sessionName", testName);
        browserStackOptions.setCapability("deviceOrientation", deviceOrientation);
        browserStackOptions.setCapability("debug", "true");
        browserStackOptions.setCapability("networkLogs", "true");

        return browserStackOptions;
    }

    private static URL getAppiumServerURL () throws MalformedURLException {
        return new URL(appiumURL);
    }

    //ThreadLocal Driver
    public static AppiumDriver getDriver(ThreadLocal<AppiumDriver> isolatedDriver)
    {
        return isolatedDriver.get();
    }

    public static void isolateWebDriver(AppiumDriver driver , ThreadLocal<AppiumDriver> isolatedDriver)
    {
        isolatedDriver.set(driver);
    }

    public static void removeIsolatedDriver (ThreadLocal<AppiumDriver> isolatedDriver)
    {
        isolatedDriver.remove();
    }

}
