package yehiaEngine.loggers;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
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
    public static void logErrorStep(String Error , Throwable e)
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

    public static void setLogFileName(String methodName) {
        // Dynamically set the log file name for each method
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();

        // Construct file path based on method name
        String logFilePath = "logs/" + methodName + ".log";

        // Remove existing FileAppender if needed
        LoggerConfig loggerConfig = config.getLoggerConfig(log.getName());
        if (loggerConfig.getAppenders().containsKey("FileAppender")) {
            loggerConfig.removeAppender("FileAppender");
        }

        // Create and add a new FileAppender with the specific log file path
        PatternLayout layout = PatternLayout.newBuilder()
                .withPattern("[%p] - %msg - [%d{HH:mm:ss}] - %c{1} %n")
                .withConfiguration(config)
                .build();

        FileAppender fileAppender = FileAppender.newBuilder()
                .withFileName(logFilePath)
                .withAppend(false)
                .withName("FileAppender")
                .withLayout(layout)
                .setConfiguration(config)
                .build();

        fileAppender.start();
        loggerConfig.addAppender(fileAppender, null, null);
        context.updateLoggers(); // Apply changes
    }
}
