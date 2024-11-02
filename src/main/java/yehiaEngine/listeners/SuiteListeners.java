package yehiaEngine.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import yehiaEngine.loggers.AllureReportLogger;
import yehiaEngine.utilities.DeleteDirectoryFiles;

import java.io.File;

public class SuiteListeners implements ISuiteListener {

    public void onStart (ISuite suite) {

        //Clear Old Screenshots & Allure Results before Every Run
        File file1 = new File("src/main/resources/screenshots");
        File file2 = new File("target/allure-results");
        DeleteDirectoryFiles.deleteFiles(file1);
        DeleteDirectoryFiles.deleteFiles(file2);
    }

    public void onFinish (ISuite suite) {
        //Genarate the Allure Report after Suite Run
        AllureReportLogger.generateAllureReport();
    }

}
