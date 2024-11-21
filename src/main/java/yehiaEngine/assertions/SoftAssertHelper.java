package yehiaEngine.assertions;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IInvokedMethod;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;
import yehiaEngine.driverManager.AppiumFactory;
import yehiaEngine.driverManager.BrowserFactory;
import yehiaEngine.loggers.LogHelper;
import yehiaEngine.loggers.Screenshot;

import java.util.ArrayList;
import java.util.List;

import static yehiaEngine.loggers.LogHelper.logErrorStep;

public class SoftAssertHelper extends SoftAssert {
    // Use ThreadLocal to store errors separately for each test thread
    private final static ThreadLocal<List<String>> errors = ThreadLocal.withInitial(ArrayList::new);
    protected static final SoftAssertHelper softAssert = new SoftAssertHelper();

    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        ITestResult result = Reporter.getCurrentTestResult();
        ITestContext context = result.getTestContext();

        ThreadLocal<RemoteWebDriver> webDriver = (ThreadLocal<RemoteWebDriver>) context.getAttribute("isolatedWebDriver");
        ThreadLocal<AppiumDriver> appiumDriver = (ThreadLocal<AppiumDriver>) context.getAttribute("isolatedAppiumDriver");

        String errorMessage = "Soft Assertion Failed: " + ex.getMessage();

        if (webDriver != null)
            Screenshot.captureSoftFailure(BrowserFactory.getDriver(webDriver), assertCommand, errorMessage);

        else if (appiumDriver != null)
            Screenshot.captureSoftFailure(AppiumFactory.getDriver(appiumDriver), assertCommand, errorMessage);
        errors.get().add(errorMessage); // Add error to the ThreadLocal list for this thread
    }

    public static void reportSoftAssertionErrors(IInvokedMethod method) {
        try {
            List<String> threadErrors = errors.get();  // Get the thread-local errors list
            if (!threadErrors.isEmpty()) {
                String combinedError = String.join("\n", threadErrors);
                LogHelper.logWarningStep("Soft Assertions Summary:\n" + combinedError);
                Allure.step("Soft Assertions Summary for " + method.getTestMethod().getMethodName() + ": \n", () -> {
                    threadErrors.forEach(Allure::step);
                });
            }
        } catch (Exception e) {
            logErrorStep("Failed to Log the Soft Assertion Summery Report", e);
        } finally {
            errors.remove();  // Clear the ThreadLocal list after reporting
        }
    }
}
