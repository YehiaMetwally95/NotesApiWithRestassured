package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.ChangePasswordRequestPojo;
import pojoClasses.ChangePasswordResponsePojo;
import pojoClasses.CreateNoteResponsePojo;
import pojoClasses.LoginRequestPojo;

import static yehiaEngine.managers.ApisManager.*;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;
import static yehiaEngine.utilities.RandomDataGenerator.generateStrongPassword;
import static yehiaEngine.managers.ApisManager.ContentType.*;
import static yehiaEngine.managers.ApisManager.MethodType.*;
import static yehiaEngine.managers.ApisManager.AuthType.*;
import static yehiaEngine.managers.ApisManager.ParameterType.*;

public class ChangePasswordRequestModel {
    //Variables
    String changePasswordEndpoint = getPropertiesValue("baseUrlApi")+"users/change-password";
    String responseBodyAsString;
    Response response;
    JsonMapper mapper;

    //Variables from LoginModel
    String userEmail;
    String userPassword;
    String token;

    //ObjectsFromPojoClasses
    ChangePasswordRequestPojo requestObject;
    ChangePasswordResponsePojo responseObject;

    //Constructor to pass Login Data into ChangePassword Model
    public ChangePasswordRequestModel(String token, String email,String password) {
        this.userEmail = email;
        this.userPassword = password;
        this.token = token;
    }

    @Step("Prepare ChangePassword Request")
    //Method to get Request Body of ChangePassword from Login Results
    public ChangePasswordRequestModel prepareChangePasswordRequestWithRandomPassword()
    {
        requestObject = ChangePasswordRequestPojo.builder()
                .currentPassword(userPassword)
                .newPassword(generateStrongPassword())
                .build();
        return this;
    }

    @Step("Send ChangePassword Request")
    //Method to Execute ChangePassword Request
    public ChangePasswordResponseModel sendChangePasswordRequest() {

        response = MakeAuthRequest(POST,changePasswordEndpoint,requestObject,URLENCODED,
                XAuthToken,token,null,null);

        responseObject = mapResponseToPojoClass(response, ChangePasswordResponsePojo.class);

        return new ChangePasswordResponseModel(requestObject, responseObject,userEmail);
    }

    //Facade Method
    @Step("Change User Password")
    public LoginRequestPojo changeUserPassword() {
        return prepareChangePasswordRequestWithRandomPassword()
                .sendChangePasswordRequest()
                .validateStatusFromResponse("200")
                .getUserCredentialsWithNewPassword()
                .prepareLoginRequest()
                .sendLoginRequest()
                .validateStatusFromResponse("200")
                .validateTokenExists()
                .getRequestPojoObject();
    }
}
