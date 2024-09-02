package objectModels;

import io.qameta.allure.Step;
import org.testng.Assert;
import pojoClasses.RegisterRequestPojo;
import pojoClasses.RegisterResponsePojo;

public class RegisterResponseModel {

    //ObjectsFromPojoClasses
    RegisterRequestPojo requestObject;
    RegisterResponsePojo responseObject;

    //Variables
    boolean expectedSuccess = true;
    int expectedStatus = 201;
    String expectedMassage = "User account created successfully";

    //Constructor to pass the response from Request Model to Response Model
    public RegisterResponseModel(RegisterRequestPojo requestObject , RegisterResponsePojo responseObject) {
        this.requestObject = requestObject;
        this.responseObject = responseObject;
    }

    //Validation Methods
    @Step("validateMassageFromResponse")
    public RegisterResponseModel validateMassageFromResponse() {
        Assert.assertEquals(responseObject.getMessage(),expectedMassage);
        return this;
    }

    @Step("validateStatusFromResponse")
    public RegisterResponseModel validateStatusFromResponse() {
        Assert.assertEquals(responseObject.getStatus(),expectedStatus);
        return this;
    }

    @Step("validateSuccessFromResponse")
    public RegisterResponseModel validateSuccessFromResponse() {
        Assert.assertEquals(responseObject.isSuccess(),expectedSuccess);
        return this;
    }

    @Step("validateNameFromResponse")
    public RegisterResponseModel validateNameFromResponse() {
        Assert.assertEquals(responseObject.getData().getName(),requestObject.getName());
        return this;
    }

    @Step("validateEmailFromResponse")
    public RegisterResponseModel validateEmailFromResponse() {
        Assert.assertEquals(responseObject.getData().getEmail(),requestObject.getEmail());
        return this;
    }

    //Getter Methods
    @Step("getUserID")
    public String getUserID()
    {
        return responseObject.getData().getId();
    }

    @Step("getNewUserCredentials")
    //Get Needed Data from Registration Model and pass it to Login Model
    public LoginRequestModel getNewUserCredentials() {

        return new LoginRequestModel(
                requestObject.getEmail(),
                requestObject.getPassword(),
                responseObject.getData().getId());
    }
}
