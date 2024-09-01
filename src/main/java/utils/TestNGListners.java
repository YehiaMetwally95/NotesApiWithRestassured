package utils;

import org.testng.*;

import java.io.IOException;

public class TestNGListners implements ITestListener , IInvokedMethodListener , ISuiteListener {
    String propertiesFilePath = "src/main/resources/Configurations.properties";

    public void onTestStart(ITestResult result) {
        // not implemented
    }

    public void onTestSuccess(ITestResult result) {
    }

    public void onTestFailure(ITestResult result) {
    }

    public void onTestSkipped(ITestResult result) {
        // not implemented
    }

    public void onStart(ITestContext context) {
        PropertiesManager.filePath = propertiesFilePath;
        try {
            PropertiesManager.loadPropertiesIntoSystem();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onFinish(ITestContext context) {
        // not implemented
    }

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // not implemented
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    }
}

