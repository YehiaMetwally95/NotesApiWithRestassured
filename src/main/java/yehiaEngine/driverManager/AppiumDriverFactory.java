package yehiaEngine.driverManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

import static yehiaEngine.loggers.LogHelper.logErrorStep;
import static yehiaEngine.loggers.LogHelper.logInfoStep;

public class AppiumDriverFactory {
    private static DesiredCapabilities cap;
    private static final String appType = System.getProperty("appType");
    private static final String browserName = System.getProperty("browserName");
    private static final String deviceName = System.getProperty("deviceName");
    private static final String deviceUdid = System.getProperty("deviceUdid");
    private static final String platformVersion = System.getProperty("platformVersion");
    private static final String nativeAutomationDriver = System.getProperty("nativeAutomationDriver");
    private static final String appName = System.getProperty("appName");
    private static final String appActivity = System.getProperty("appActivity");

    public static AppiumDriver openApp() throws MalformedURLException {
        AppiumDriver driver = null;
        if (appType.equalsIgnoreCase("NativeAndroid")  || appType.equalsIgnoreCase("WebAppAndroid") )
        {
            driver =  new AndroidDriver(getAppiumServerURL(), getAndroidCapabilities());
            logInfoStep("Starting "+ appName +" ............");
        }

        else if (appType.equalsIgnoreCase("NativeIOS")  || appType.equalsIgnoreCase("WebAppIOS") )
        {
            driver =  new AndroidDriver(getAppiumServerURL(), getIOSCapabilities());
            logInfoStep("Starting "+ appName +" ............");
        }

        else
        {
            logErrorStep("Failed to Start the Application, The Input App Type is Incorrect");
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
            //Device Capabilities
        cap.setCapability("appium:deviceName",deviceName);
        cap.setCapability("appium:udid",deviceUdid);

            //Platform Capabilities
        cap.setCapability("appium:platformName","Android");
        cap.setCapability("appium:platformVersion",platformVersion);
        cap.setCapability("appium:automationName",nativeAutomationDriver);

            //Application Capabilities for Native App
        cap.setCapability("appium:app", System.getProperty("user.dir")+"\\src\\main\\resources\\apps\\"+appName);
        cap.setCapability("appium:appActivity",appActivity);

            //Browser Capabilities for Web-Based App
        if (appType.equalsIgnoreCase("WebAppAndroid"))
            cap.setCapability(CapabilityType.BROWSER_NAME,browserName);

        return cap;
    }

    private static DesiredCapabilities getIOSCapabilities()
    {
        cap = new DesiredCapabilities();
        //Device Capabilities
        cap.setCapability("appium:deviceName",deviceName);
        cap.setCapability("appium:udid",deviceUdid);

        //Platform Capabilities
        cap.setCapability("appium:platformName","Ios");
        cap.setCapability("appium:platformVersion",platformVersion);
        cap.setCapability("appium:automationName",nativeAutomationDriver);

        //Application Capabilities for Native App
        cap.setCapability("appium:app", System.getProperty("user.dir")+"\\src\\main\\resources\\apps\\"+appName);
        cap.setCapability("appium:appActivity",appActivity);

        //Browser Capabilities for Web-Based App
        if (appType.equalsIgnoreCase("WebAppIOS"))
            cap.setCapability(CapabilityType.BROWSER_NAME,browserName);

        return cap;
    }

    private static URL getAppiumServerURL () throws MalformedURLException {
        return new URL("http://127.0.0.1:4723");
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
