package yehiaEngine.loggers;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class LogHelper {

    static Logger log = LogManager.getLogger();

    public static void logInfoStep(String Step)
    {
        log.info(Step + " -");
    }

    public static void logWarningStep(String Warning)
    {
        log.warn(Warning + " -");
    }

    @Step("{Error}")
    public static void logErrorStep(String Error , Exception e)
    {
        log.error(Error + " -");
        Assert.fail(Error,e);
    }

    @Step("{Error}")
    public static void logErrorStep(String Error)
    {
        log.error(Error + " -");
        Assert.fail(Error);
    }

    @Step("{Error}")
    public static void logErrorStep(String Error , AssertionError e)
    {
        log.error(Error + " -");
        Assert.fail(Error,e);
    }

}
