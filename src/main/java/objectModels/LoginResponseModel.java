package objectModels;

import io.qameta.allure.Step;
import org.testng.Assert;
import pojoClasses.LoginRequestPojo;
import pojoClasses.LoginResponsePojo;
import yehiaEngine.assertions.CustomAssert;

public class LoginResponseModel {

    //ObjectsFromPojoClasses
    LoginRequestPojo requestObject;
    LoginResponsePojo responseObject;

    //Variables

    //Constructor to pass the response from Request Model to Response Model
    public LoginResponseModel(LoginRequestPojo requestObject, LoginResponsePojo responseObject) {
        this.requestObject = requestObject;
        this.responseObject = responseObject;
    }

    //Validation Methods
    @Step("validate Massage From Response")
    public LoginResponseModel validateMassageFromResponse(String message) {
        CustomAssert.assertEquals(responseObject.getMessage(),message);
        return this;
    }

    @Step("validate Status From Response")
    public LoginResponseModel validateStatusFromResponse(String statusCode){
        CustomAssert.assertEquals(responseObject.getStatus(),Integer.parseInt(statusCode));
        return this;
    }

    @Step("validate Success From Response")
    public LoginResponseModel validateSuccessFromResponse(String successFlag) {
        CustomAssert.assertEquals(responseObject.isSuccess(),Boolean.parseBoolean(successFlag));
        return this;
    }

    @Step("Validate TokenExists ")
    public LoginResponseModel validateTokenExists() {
        CustomAssert.assertFalse(responseObject.getData().getToken().isEmpty());
        return this;
    }

    //Getter Methods
    public LoginRequestPojo getRequestPojoObject() {
        return requestObject;
    }

    public LoginResponsePojo getResponsePojoObject() {
        return responseObject;
    }

    @Step("Get Password")
    //Get Password from Request
    public String getPassword()
    {
        return requestObject.getPassword();
    }

    //Get Needed Token from Login Model and pass it to Note Model
    @Step("Navigate to CreateNote")
    public CreateNoteRequestModel navigateToCreateNote()
    {
        return new CreateNoteRequestModel(responseObject.getData().getToken());
    }

    //Get Needed Token and UserCredentials from Login Model and pass it to ChangePassword Model
    @Step("Navigate to ChangePassword")
    public ChangePasswordRequestModel navigateToChangePassword()
    {
        return new ChangePasswordRequestModel(
                responseObject.getData().getToken(),
                responseObject.getData().getEmail(),
                requestObject.getPassword()
        );
    }
}
