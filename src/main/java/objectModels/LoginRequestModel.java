package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.LoginRequestPojo;
import pojoClasses.LoginResponsePojo;

import static utils.ApiManager.MakeRequest;
import static utils.ApiManager.getResponseBody;
import static utils.PropertiesManager.getPropertiesValue;

public class LoginRequestModel {

    //Variables
    String loginEndpoint = getPropertiesValue("baseUrlApi")+"users/login";
    String responseBodyAsString;
    Response response;
    JsonMapper mapper;

    String userEmail;
    String userPassword;
    String userID;

    //ObjectsFromPojoClasses
    LoginRequestPojo requestObject;
    LoginResponsePojo responseObject;

    //Constructor to pass Registration Data into Login Model
    public LoginRequestModel(String email, String password, String id) {
        this.userEmail = email;
        this.userPassword = password;
        this.userID = id;
    }

    @Step("prepareLoginRequest")
    //Method to get Request Body of Login from Registration Results
    public LoginRequestModel prepareLoginRequest()
    {
        requestObject = LoginRequestPojo.builder()
                .email(userEmail)
                .password(userPassword)
                .build();
        return this;
    }

    @Step("loginWithExistingUser")
    //Method to Execute Login Request
    public LoginResponseModel loginWithExistingUser() throws JsonProcessingException {

        response =
                MakeRequest("Post", loginEndpoint,requestObject, "application/json");

        responseBodyAsString = getResponseBody(response);
        mapper = new JsonMapper();

        responseObject = mapper.readValue(responseBodyAsString, LoginResponsePojo.class);
        return new LoginResponseModel(requestObject,responseObject,userID);
    }

    //Facade Method
    @Step("login")
    public CreateNoteRequestModel login() throws JsonProcessingException {
        return prepareLoginRequest().loginWithExistingUser().getAuthTokenForNotes();
    }
}
