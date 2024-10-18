package utils;

import lombok.SneakyThrows;
import org.testng.*;

import java.io.File;
import java.io.IOException;

import static utils.DeleteDirectoryFiles.deleteFiles;

public class TestNGListners implements ITestListener , IInvokedMethodListener , ISuiteListener {
    static String propertiesFilePath = "src/main/resources/Configurations.properties";

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

    @SneakyThrows
    public void onStart (ISuite suite) {
        //Load Properties File
        PropertiesManager.filePath = propertiesFilePath;
        try {
            PropertiesManager.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Clear Allure Results before Every Run
        File file1 = new File("allure-results");
        deleteFiles(file1);

    }

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // not implemented
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    }
}

