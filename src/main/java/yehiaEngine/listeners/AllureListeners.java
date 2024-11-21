package yehiaEngine.listeners;

import io.qameta.allure.listener.FixtureLifecycleListener;
import io.qameta.allure.model.FixtureResult;
import org.testng.ITestResult;
import org.testng.Reporter;
import yehiaEngine.loggers.AllureReportLogger;

import static yehiaEngine.loggers.LogHelper.logErrorStep;

public class AllureListeners implements FixtureLifecycleListener {

    @Override
    public void beforeFixtureStart(FixtureResult result) {
        FixtureLifecycleListener.super.beforeFixtureStart(result);
    }

    @Override
    public void afterFixtureStart(FixtureResult result) {
        FixtureLifecycleListener.super.afterFixtureStart(result);

    }

    @Override
    public void beforeFixtureUpdate(FixtureResult result) {
        FixtureLifecycleListener.super.beforeFixtureUpdate(result);
    }

    @Override
    public void afterFixtureUpdate(FixtureResult result) {
        FixtureLifecycleListener.super.afterFixtureUpdate(result);
    }

    @Override
    public void beforeFixtureStop(FixtureResult result) {
        try{
            //Upload the Log Files to Allure Report
            ITestResult testResult = Reporter.getCurrentTestResult();
            String method = testResult.getMethod().getMethodName();
            var iTestNGMethod = testResult.getMethod();

            if(!(iTestNGMethod.isAfterMethodConfiguration() || iTestNGMethod.isBeforeMethodConfiguration()))
                AllureReportLogger.uploadLogFileIntoAllure("Configuration - " + method+"-"+testResult.getTestClass().getRealClass().getSimpleName());

            if (iTestNGMethod.isBeforeMethodConfiguration())
                AllureReportLogger.uploadLogFileIntoAllure("Configuration - " + method+"-"+testResult.getTestClass().getRealClass().getSimpleName()+"-"+MethodListeners.beforeMethodInvocationCount);


            if (iTestNGMethod.isAfterMethodConfiguration())
                AllureReportLogger.uploadLogFileIntoAllure("Configuration - " + method+"-"+testResult.getTestClass().getRealClass().getSimpleName()+"-"+MethodListeners.afterMethodInvocationCount);
        }catch (Exception e){
            logErrorStep("Failed to Upload Log Files to Allure Report",e);
        }

    }

    @Override
    public void afterFixtureStop(FixtureResult result) {
        FixtureLifecycleListener.super.afterFixtureStop(result);

    }
}
