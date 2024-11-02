package objectModels;

import io.qameta.allure.Step;
import org.testng.Assert;
import pojoClasses.ChangePasswordRequestPojo;
import pojoClasses.ChangePasswordResponsePojo;
import yehiaEngine.assertions.CustomAssert;

public class ChangePasswordResponseModel {
    //ObjectsFromPojoClasses
    ChangePasswordRequestPojo requestObject;
    ChangePasswordResponsePojo responseObject;

    //Variables
    String userEmail;

    //Constructor to pass the response from Request Model to Response Model
    //Constructor to pass userEmail from Login Model into ChangePassword Model
    public ChangePasswordResponseModel(ChangePasswordRequestPojo requestObject,ChangePasswordResponsePojo responseObject,
                                       String userEmail) {
        this.requestObject = requestObject;
        this.responseObject = responseObject;
        this.userEmail = userEmail;
    }

    //Validation Methods
    @Step("validateMassageFromResponse")
    public ChangePasswordResponseModel validateMassageFromResponse(String message) {
        CustomAssert.assertEquals(responseObject.getMessage(),message);
        return this;
    }

    @Step("validateStatusFromResponse")
    public ChangePasswordResponseModel validateStatusFromResponse(String statusCode) {
        CustomAssert.assertEquals(responseObject.getStatus(),Integer.parseInt(statusCode));
        return this;
    }

    @Step("validateSuccessFromResponse")
    public ChangePasswordResponseModel validateSuccessFromResponse(String successFlag) {
        CustomAssert.assertEquals(responseObject.isSuccess(),Boolean.parseBoolean(successFlag));
        return this;
    }

    //Getter Methods
    public ChangePasswordRequestPojo getRequestPojoObject() {
        return requestObject;
    }

    public ChangePasswordResponsePojo getResponsePojoObject() {
        return responseObject;
    }

    //Get Needed Data from ChangePassword Model and pass it to Login Model
    @Step("Get New Credentials")
    public LoginRequestModel getUserCredentialsWithNewPassword() {
        return new LoginRequestModel(userEmail, requestObject.getNewPassword());
    }
}
