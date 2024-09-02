package objectModels;

import io.qameta.allure.Step;
import org.testng.Assert;
import pojoClasses.LoginRequestPojo;
import pojoClasses.LoginResponsePojo;

public class LoginResponseModel {

    //ObjectsFromPojoClasses
    LoginRequestPojo requestObject;
    LoginResponsePojo responseObject;

    //Variables
    boolean expectedSuccess = true;
    int expectedStatus = 200;
    String expectedMassage = "Login successful";
    String expectedID;

    //Constructor to pass the response from Request Model to Response Model
    //Constructor to pass userID from Registration Model into Login Model
    public LoginResponseModel(LoginRequestPojo requestObject, LoginResponsePojo responseObject,
                              String userID) {
        this.requestObject = requestObject;
        this.responseObject = responseObject;
        this.expectedID = userID;
    }

    //Validation Methods
    @Step("validateMassageFromResponse")
    public LoginResponseModel validateMassageFromResponse() {
        Assert.assertEquals(responseObject.getMessage(),expectedMassage);
        return this;
    }

    @Step("validateStatusFromResponse")
    public LoginResponseModel validateStatusFromResponse(){
        Assert.assertEquals(responseObject.getStatus(),expectedStatus);
        return this;
    }

    @Step("validateSuccessFromResponse")
    public LoginResponseModel validateSuccessFromResponse() {
        Assert.assertEquals(responseObject.isSuccess(),expectedSuccess);
        return this;
    }

    @Step("validateUserIDFromResponse")
    public LoginResponseModel validateUserIDFromResponse() {
        Assert.assertEquals(responseObject.getData().getId(),expectedID);
        return this;
    }

    @Step("validateTokenExists")
    public LoginResponseModel validateTokenExists() {
        Assert.assertFalse(responseObject.getData().getToken().isEmpty());
        return this;
    }

    @Step("getToken")
    //Getter Methods
    public String getToken()
    {
        return responseObject.getData().getToken();
    }

    @Step("getAuthTokenForNotes")
    //Get Needed Token from Login Model and pass it to Note Model
    public CreateNoteRequestModel getAuthTokenForNotes()
    {
        return new CreateNoteRequestModel(responseObject.getData().getToken());
    }
}
