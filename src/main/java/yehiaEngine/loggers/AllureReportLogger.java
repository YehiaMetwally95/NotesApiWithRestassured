package yehiaEngine.loggers;

import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static yehiaEngine.loggers.LogHelper.logErrorStep;
import static yehiaEngine.loggers.LogHelper.logInfoStep;

public class AllureReportLogger {
    public static void uploadLogFileIntoAllure(String methodName) throws IOException {
       if (methodName != null)
       {
           try {
               File file = new File("logs/"+methodName+".log");
               var byteArray = Files.readAllBytes(file.toPath());
               Allure.addAttachment("The Log File for ["+methodName+"]", new ByteArrayInputStream(byteArray));
           } catch (AssertionError e) {
               logErrorStep("Failed to Upload The Log File into Allure - ",e);
           }
       }
    }

    public static AllureRestAssured logApiRequestsToAllureReport(){
        try {
            return new AllureRestAssured()
                    .setRequestAttachmentName("Request Details")
                    .setResponseAttachmentName("Response Details");
        }catch (Exception e){
            logErrorStep("Failed to Log API Requests to Allure Report", e);
            return null;
        }
    }

    public static void logScreenshotIntoAllure(WebDriver driver, String screenshotName,String error)
    {
        try {
            var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshot));
            if (error != null)
                Allure.step(error);

        }catch (Exception e)
        {
        logErrorStep("Failed to Upload Screenshot Into Allure Report",e);
        }
    }

    public static void generateAllureReport() {
        String mavenCommand;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win"))
            mavenCommand = "mvn.cmd"; // Use mvn.cmd for Windows
        else
            mavenCommand = "mvn"; // Use mvn for Unix/Linux

        try {
            ProcessBuilder builder = new ProcessBuilder(mavenCommand, "allure:report");
            builder.inheritIO().start().waitFor();
            logInfoStep("Generating the Allure Report");
        } catch (Exception e) {
            Assert.fail("Failed to Generate The Allure Report",e);
        }
    }

}
