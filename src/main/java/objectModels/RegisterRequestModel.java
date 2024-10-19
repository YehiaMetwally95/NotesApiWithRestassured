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
    String registerEndpoint = getPropertiesValue("baseUrlApi")+"users/register";
    String responseBodyAsString;
    Response response;
    JsonMapper mapper;

    //ObjectsFromPojoClasses
    RegisterRequestPojo requestObject;
    RegisterResponsePojo responseObject;

    @Step("Prepare Registration Request Statically From Json File")
    //Method to get Request Body inputs from Json File Statically
    public RegisterRequestModel prepareRegisterRequestFromJsonFile(String userData) throws JsonProcessingException {
        mapper = new JsonMapper();
        requestObject = mapper.readValue(userData, RegisterRequestPojo.class);
        return this;
    }

    @Step("Prepare Registration Request Dynamically With Random Values")
    //Method to set Request Body inputs from TimeStamp Dynamically
    public RegisterRequestModel prepareRegisterRequestWithRandomValues(){
        requestObject = RegisterRequestPojo.builder()
                .name(generateUniqueName())
                .email(generateUniqueEmail())
                .password(generateStrongPassword())
                .build();
        return this;
    }

    @Step("Send Register Request")
    //Method to Execute Registration Request
    public RegisterResponseModel sendRegisterRequest() throws JsonProcessingException {
        response =
                MakeRequest("Post", registerEndpoint,requestObject, "application/x-www-form-urlencoded");

        responseBodyAsString = getResponseBody(response);
        mapper = new JsonMapper();

        responseObject = mapper.readValue(responseBodyAsString, RegisterResponsePojo.class);

        logRequestBody(requestObject);
        logResponseBody(responseObject);
        return new RegisterResponseModel(requestObject,responseObject);
    }

    //Facade Methods
    @Step("Register new User And Login")
    public LoginResponseModel registerNewUserWithRandomData() throws JsonProcessingException {

        return prepareRegisterRequestWithRandomValues()
                .sendRegisterRequest()
                .validateStatusFromResponse("201")
                .getNewUserCredentials()
                .prepareLoginRequest()
                .sendLoginRequest()
                .validateStatusFromResponse("200")
                .validateTokenExists();
    }

    @Step("Register new User And Login")
    public LoginResponseModel registerNewUser(String userJsonObject) throws JsonProcessingException {

        return prepareRegisterRequestFromJsonFile(userJsonObject)
                .sendRegisterRequest()
                .validateStatusFromResponse("201")
                .getNewUserCredentials()
                .prepareLoginRequest()
                .sendLoginRequest()
                .validateStatusFromResponse("200")
                .validateTokenExists();
    }
}
