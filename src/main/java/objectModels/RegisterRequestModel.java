package objectModels;
import static utils.ApiManager.*;
import static utils.RandomDataGenerator.*;

import static utils.PropertiesManager.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.RegisterRequestPojo;
import pojoClasses.RegisterResponsePojo;


public class RegisterRequestModel {

    //Variables
    String mockServerUrl = getPropertiesValue("baseUrlMockServer")+"Users/";
    String registerEndpoint = getPropertiesValue("baseUrlApi")+"users/register";
    String responseBodyAsString;
    Response response;
    JsonMapper mapper;

    //ObjectsFromPojoClasses
    RegisterRequestPojo requestObject;
    RegisterResponsePojo responseObject;

    @Step("prepareRegistrationRequestFromMockServer")
    //Method to get Request Body inputs from Mock Server Statically
    public RegisterRequestModel prepareRegistrationRequestFromMockServer(int userID) throws JsonProcessingException {
        response =
                GetRequest(mockServerUrl +userID,null);
        responseBodyAsString = getResponseBody(response);

        mapper = new JsonMapper();
        requestObject = mapper.readValue(responseBodyAsString, RegisterRequestPojo.class);
        return this;
    }

    @Step("prepareRegistrationRequestWithRandomValues")
    //Method to set Request Body inputs from TimeStamp Dynamically
    public RegisterRequestModel prepareRegistrationRequestWithRandomValues(){
        requestObject = RegisterRequestPojo.builder()
                .name(generateName())
                .email(generateEmail())
                .password(generateStrongPassword())
                .build();
        return this;
    }

    @Step("registerNewUser")
    //Method to Execute Registration Request
    public RegisterResponseModel registerNewUser() throws JsonProcessingException {
        response =
                MakeRequest("Post", registerEndpoint,requestObject, "application/json");

        responseBodyAsString = getResponseBody(response);
        mapper = new JsonMapper();

        responseObject = mapper.readValue(responseBodyAsString, RegisterResponsePojo.class);
        return new RegisterResponseModel(requestObject,responseObject);
    }

    //Facade Method
    @Step("register")
    public LoginRequestModel register() throws JsonProcessingException {
        prepareRegistrationRequestWithRandomValues().registerNewUser().getNewUserCredentials();
        return new LoginRequestModel(
                requestObject.getEmail(),
                requestObject.getPassword(),
                responseObject.getData().getId());
    }

}
