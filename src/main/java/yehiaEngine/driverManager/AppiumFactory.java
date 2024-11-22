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
    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();;

    private static DesiredCapabilities cap;
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
        logInfoStep("Terminating "+ appName +" ............");
    }

    private static DesiredCapabilities getAndroidCapabilities()
    {
        cap = new DesiredCapabilities();
        cap.setCapability("appium:newCommandTimeout",600);

        //Device Capabilities
        cap.setCapability("appium:deviceName",deviceName);
        cap.setCapability("appium:udid",deviceUdid);

            //Platform Capabilities
        cap.setCapability("appium:platformName","Android");
        cap.setCapability("appium:platformVersion",platformVersion);
        cap.setCapability("appium:automationName",nativeAutomationDriver);

        //Application Capabilities for Native App
        if (appType.equalsIgnoreCase("NativeAndroid"))
        {
            cap.setCapability("appium:app", System.getProperty("user.dir")+"\\src\\main\\resources\\apps\\"+appName);
            cap.setCapability("appium:appActivity",appActivity);
        }

            //Browser Capabilities for Web-Based App
        if (appType.equalsIgnoreCase("WebAppAndroid"))
        {
            cap.setCapability(CapabilityType.BROWSER_NAME,browserName);
            cap.setCapability(CapabilityType.BROWSER_VERSION,browserVersion);
        }

        return cap;
    }

    private static DesiredCapabilities getIOSCapabilities()
    {
        cap = new DesiredCapabilities();
        cap.setCapability("appium:newCommandTimeout",600);

        //Device Capabilities
        cap.setCapability("appium:deviceName",deviceName);
        cap.setCapability("appium:udid",deviceUdid);

        //Platform Capabilities
        cap.setCapability("appium:platformName","Ios");
        cap.setCapability("appium:platformVersion",platformVersion);
        cap.setCapability("appium:automationName",nativeAutomationDriver);

        //Application Capabilities for Native App
        if (appType.equalsIgnoreCase("NativeIOS"))
        {
            cap.setCapability("appium:app", System.getProperty("user.dir")+"\\src\\main\\resources\\apps\\"+appName);
            cap.setCapability("appium:appActivity",appActivity);
        }

        //Browser Capabilities for Web-Based App
        if (appType.equalsIgnoreCase("WebAppIOS"))
        {
            cap.setCapability(CapabilityType.BROWSER_NAME,browserName);
            cap.setCapability(CapabilityType.BROWSER_VERSION,browserVersion);
        }

        return cap;
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
