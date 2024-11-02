package yehiaEngine.listeners;

import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;
import yehiaEngine.loggers.LogHelper;
import yehiaEngine.managers.PropertiesManager;

import java.util.List;

public class ParallelExecutionListener implements IAlterSuiteListener {


    public void alter (List<XmlSuite> suites) {
        //Load Properties File
        PropertiesManager.loadProperties();

        //Configure Parallel mode and thread count
        try {
            String parallel_Mode = System.getProperty("parallelMode");
            String thread_count = System.getProperty("threadCount");

            for (XmlSuite suite : suites) {
                suite.setParallel(XmlSuite.ParallelMode.valueOf(parallel_Mode.toUpperCase()));
                suite.setThreadCount(Integer.parseInt(thread_count));
            }
            LogHelper.logInfoStep("Running Tests with Parallel Mode = ["+parallel_Mode+"]");
            if (!parallel_Mode.equalsIgnoreCase("None"))
                LogHelper.logInfoStep("Thread Count = ["+thread_count+"]");

        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Run Tests, Please Check Value of Parallel Mode or Thread Count",e);
        }
    }
}
