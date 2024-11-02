package yehiaEngine.listeners;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import yehiaEngine.assertions.SoftAssertHelper;
import yehiaEngine.loggers.AllureReportLogger;

import static yehiaEngine.driverManager.BrowserFactory.getDriver;
import static yehiaEngine.loggers.Screenshot.captureFailure;
import static yehiaEngine.loggers.Screenshot.captureSuccess;

public class MethodListeners implements IInvokedMethodListener {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if(method.isTestMethod())
        {
            //Log Screenshots for Successful and Failed Tests
            ITestContext context = testResult.getTestContext();
            ThreadLocal<RemoteWebDriver> driver = (ThreadLocal<RemoteWebDriver>) context.getAttribute("isolatedDriver");
            //Take Screenshot after every succeeded test
            if (ITestResult.SUCCESS == testResult.getStatus() && driver != null)
                captureSuccess(getDriver(driver),testResult);

                //Take Screenshot after every failed test
            else if (ITestResult.FAILURE == testResult.getStatus() && driver != null)
                captureFailure(getDriver(driver),testResult);

            //Log Summery Report for Soft Assertion Errors after Every Run
            SoftAssertHelper.reportSoftAssertionErrors(method);

            //Upload the Log File to Allure Report
            AllureReportLogger.uploadLogFileIntoAllure();
        }
    }
}
