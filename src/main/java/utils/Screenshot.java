package utils;

import io.qameta.allure.restassured.AllureRestAssured;

public class Screenshot {

    public static AllureRestAssured logApiRequestsToAllureReport(){
        return new AllureRestAssured()
                .setRequestAttachmentName("Request Details")
                .setResponseAttachmentName("Response Details");
    }
}
