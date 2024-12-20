package objectModels;

import io.qameta.allure.Step;
import pojoClasses.RegisterRequestPojo;
import pojoClasses.RegisterResponsePojo;
import yehiaEngine.assertions.CustomAssert;

public class RegisterResponseModel {

    //ObjectsFromPojoClasses
    RegisterRequestPojo requestObject;
    RegisterResponsePojo responseObject;

    //Variables

    //Constructor to pass the response from Request Model to Response Model
    public RegisterResponseModel(RegisterRequestPojo requestObject , RegisterResponsePojo responseObject) {
        this.requestObject = requestObject;
        this.responseObject = responseObject;
    }

    //Validation Methods
    @Step("validateMassageFromResponse")
    public RegisterResponseModel validateMassageFromResponse(String message) {
        CustomAssert.assertEquals(responseObject.getMessage(),message);
        return this;
    }

    @Step("validateStatusFromResponse")
    public RegisterResponseModel validateStatusFromResponse(String statusCode) {
        CustomAssert.assertEquals(responseObject.getStatus(),Integer.parseInt(statusCode));
        return this;
    }

    @Step("validateSuccessFromResponse")
    public RegisterResponseModel validateSuccessFromResponse(String successFlag) {
        CustomAssert.assertEquals(responseObject.isSuccess(),Boolean.parseBoolean(successFlag));
        return this;
    }

    @Step("validateNameFromResponse")
    public RegisterResponseModel validateNameFromResponse() {
        CustomAssert.assertEquals(responseObject.getData().getName(),requestObject.getName());
        return this;
    }

    @Step("validateEmailFromResponse")
    public RegisterResponseModel validateEmailFromResponse() {
        CustomAssert.assertEquals(responseObject.getData().getEmail(),requestObject.getEmail());
        return this;
    }

    //Getter Methods
    public RegisterRequestPojo getRequestPojoObject() {
        return requestObject;
    }

    public RegisterResponsePojo getResponsePojoObject() {
        return responseObject;
    }

    //Get Needed Data from Registration Model and pass it to Login Model
    @Step("Get New User Credentials")
    public LoginRequestModel getNewUserCredentials() {

        return new LoginRequestModel(
                requestObject.getEmail(),
                requestObject.getPassword()
        );
    }
}
