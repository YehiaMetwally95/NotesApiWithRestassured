package objectModels;
import static yehiaEngine.managers.ApisManager.*;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;
import static yehiaEngine.utilities.RandomDataGenerator.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.LoginResponsePojo;
import pojoClasses.RegisterRequestPojo;
import pojoClasses.RegisterResponsePojo;
import static yehiaEngine.managers.ApisManager.ContentType.*;
import static yehiaEngine.managers.ApisManager.MethodType.*;
import static yehiaEngine.managers.ApisManager.AuthType.*;
import static yehiaEngine.managers.ApisManager.ParameterType.*;

public class RegisterRequestModel {

    //Variables
    String registerEndpoint = getPropertiesValue("baseUrlApi")+"users/register";
    Response response;

    //ObjectsFromPojoClasses
    RegisterRequestPojo requestObject;
    RegisterResponsePojo responseObject;

    @Step("Prepare Registration Request Statically From Json File")
    //Method to get Request Body inputs from Json File Statically
    public RegisterRequestModel prepareRegisterRequestFromJsonFile(String userData) {
        requestObject = mapJsonStringToPojoClass(userData, RegisterRequestPojo.class);
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
    public RegisterResponseModel sendRegisterRequest() {
        response = MakeRequest(POST, registerEndpoint,requestObject,URLENCODED);

        responseObject = mapResponseToPojoClass(response,RegisterResponsePojo.class);

        return new RegisterResponseModel(requestObject,responseObject);
    }

    //Facade Methods
    @Step("Register new User And Login")
    public LoginResponseModel registerNewUserWithRandomData() {

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
    public LoginResponseModel registerNewUser(String userJsonObject) {

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
