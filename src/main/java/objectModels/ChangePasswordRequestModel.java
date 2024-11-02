package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.ChangePasswordRequestPojo;
import pojoClasses.ChangePasswordResponsePojo;
import pojoClasses.LoginRequestPojo;

import static yehiaEngine.managers.ApisManager.MakeAuthRequest;
import static yehiaEngine.managers.ApisManager.getResponseBody;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;
import static yehiaEngine.utilities.RandomDataGenerator.generateStrongPassword;

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
    public ChangePasswordResponseModel sendChangePasswordRequest() throws JsonProcessingException {

        response =
                MakeAuthRequest("Post", changePasswordEndpoint,requestObject,
                        "application/json","X-Auth-Token",
                        null,null,token);
        responseBodyAsString = getResponseBody(response);
        mapper = new JsonMapper();

        responseObject = mapper.readValue(responseBodyAsString, ChangePasswordResponsePojo.class);

        return new ChangePasswordResponseModel(requestObject, responseObject,userEmail);
    }

    //Facade Method
    @Step("Change User Password")
    public LoginRequestPojo changeUserPassword() throws JsonProcessingException {
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
