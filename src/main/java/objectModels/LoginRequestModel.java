package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.LoginRequestPojo;
import pojoClasses.LoginResponsePojo;

import static yehiaEngine.managers.ApisManager.MakeRequest;
import static yehiaEngine.managers.ApisManager.getResponseBody;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;

public class LoginRequestModel {

    //Variables
    String loginEndpoint = getPropertiesValue("baseUrlApi")+"users/login";
    String responseBodyAsString;
    Response response;
    JsonMapper mapper;

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
    public LoginResponseModel sendLoginRequest() throws JsonProcessingException {

        response =
                MakeRequest("Post", loginEndpoint, requestObject, "application/x-www-form-urlencoded");

        responseBodyAsString = getResponseBody(response);
        mapper = new JsonMapper();

        responseObject = mapper.readValue(responseBodyAsString, LoginResponsePojo.class);

        return new LoginResponseModel(requestObject, responseObject);
    }

   //Facade Method
    @Step("Login With Existing User")
    public LoginResponseModel loginWithExistingUser() throws JsonProcessingException {
        return
                new LoginRequestModel(userEmail,userPassword)
                        .prepareLoginRequest()
                        .sendLoginRequest()
                        .validateStatusFromResponse("200")
                        .validateTokenExists();
    }
}
