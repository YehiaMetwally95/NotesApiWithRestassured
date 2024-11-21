package yehiaEngine.listeners;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.*;
import yehiaEngine.assertions.SoftAssertHelper;
import yehiaEngine.driverManager.AppiumFactory;
import yehiaEngine.driverManager.BrowserFactory;
import yehiaEngine.loggers.AllureReportLogger;

import java.io.IOException;

import static yehiaEngine.loggers.LogHelper.setLogFileName;
import static yehiaEngine.loggers.Screenshot.captureFailure;
import static yehiaEngine.loggers.Screenshot.captureSuccess;

public class MethodListeners implements IInvokedMethodListener , IConfigurationListener {
    protected static int beforeMethodInvocationCount = 0;
    protected static int afterMethodInvocationCount = 0;

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        //Set Log File for Test Methods
        if(method.isTestMethod())
            setLogFileName("Test - "+method.getTestMethod().getMethodName());

        //Set Log File for Configurations Methods
        if(method.isConfigurationMethod())
        {
            if(!(method.getTestMethod().isAfterMethodConfiguration() || method.getTestMethod().isBeforeMethodConfiguration()))
                setLogFileName("Configuration - "+method.getTestMethod().getMethodName()+"-"+testResult.getTestClass().getRealClass().getSimpleName());

            else if (method.getTestMethod().isBeforeMethodConfiguration())
            {
                beforeMethodInvocationCount++;
                setLogFileName("Configuration - "+method.getTestMethod().getMethodName()+"-"+testResult.getTestClass().getRealClass().getSimpleName() +"-"+beforeMethodInvocationCount);
            }

            else if (method.getTestMethod().isAfterMethodConfiguration())
            {
                afterMethodInvocationCount++;
                setLogFileName("Configuration - "+method.getTestMethod().getMethodName()+"-"+testResult.getTestClass().getRealClass().getSimpleName() +"-"+afterMethodInvocationCount);
            }
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        if(method.isTestMethod())
        {
            //Log Screenshots for Successful and Failed Tests
            ThreadLocal<RemoteWebDriver> webDriver = (ThreadLocal<RemoteWebDriver>) context.getAttribute("isolatedWebDriver");
            ThreadLocal<AppiumDriver> appiumDriver = (ThreadLocal<AppiumDriver>) context.getAttribute("isolatedAppiumDriver");

            //Take Screenshot after every succeeded test
            if (ITestResult.SUCCESS == testResult.getStatus() && webDriver != null)
                captureSuccess(BrowserFactory.getDriver(webDriver),testResult);

            else if (ITestResult.SUCCESS == testResult.getStatus() && appiumDriver != null)
                captureSuccess(AppiumFactory.getDriver(appiumDriver),testResult);

                //Take Screenshot after every failed test
            else if (ITestResult.FAILURE == testResult.getStatus() && webDriver != null)
                captureFailure(BrowserFactory.getDriver(webDriver),testResult);

            else if (ITestResult.FAILURE == testResult.getStatus() && appiumDriver != null)
                captureFailure(AppiumFactory.getDriver(appiumDriver),testResult);

            //Log Summery Report for Soft Assertion Errors after Every Run
            SoftAssertHelper.reportSoftAssertionErrors(method);

            //Upload the Log Files to Allure Report
            try {
                AllureReportLogger.uploadLogFileIntoAllure("Test - "+method.getTestMethod().getMethodName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
