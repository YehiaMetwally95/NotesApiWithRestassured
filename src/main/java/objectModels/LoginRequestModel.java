package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.LoginRequestPojo;
import pojoClasses.LoginResponsePojo;
import yehiaEngine.managers.ApisManager;

import static yehiaEngine.loggers.LogHelper.logInfoStep;
import static yehiaEngine.managers.ApisManager.*;
import static yehiaEngine.managers.ApisManager.ContentType.*;
import static yehiaEngine.managers.ApisManager.MethodType.*;
import static yehiaEngine.managers.ApisManager.AuthType.*;
import static yehiaEngine.managers.ApisManager.ParameterType.*;

import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;

public class LoginRequestModel {

    //Variables
    String loginEndpoint = getPropertiesValue("baseUrlApi")+"users/login";
    Response response;

    //Variables from RegisterModel
    String userEmail;
    String userPassword;

    //ObjectsFromPojoClasses
    LoginRequestPojo requestObject;
    LoginResponsePojo responseObject;

    //Constructor to pass Registration Data into Login Model
    public LoginRequestModel(String email, String password) {
        this.userEmail = email;
        this.userPassword = password;
    }

    @Step("Prepare Login Request")
    //Method to get Request Body of Login from Registration Results
    public LoginRequestModel prepareLoginRequest()
    {
        requestObject = LoginRequestPojo.builder()
                .email(userEmail)
                .password(userPassword)
                .build();
        return this;
    }

    @Step("Send Login Request")
    //Method to Execute Login Request
    public LoginResponseModel sendLoginRequest() {

        response = MakeRequest(POST, loginEndpoint, requestObject,URLENCODED);

        responseObject = mapResponseToPojoClass(response,LoginResponsePojo.class);

        return new LoginResponseModel(requestObject, responseObject);
    }

   //Facade Method
    @Step("Login With Existing User")
    public LoginResponseModel loginWithExistingUser() {
        return
                new LoginRequestModel(userEmail,userPassword)
                        .prepareLoginRequest()
                        .sendLoginRequest()
                        .validateStatusFromResponse("200")
                        .validateTokenExists();
    }
}
