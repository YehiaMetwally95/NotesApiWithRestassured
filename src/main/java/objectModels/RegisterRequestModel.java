package objectModels;
import static utils.ApiManager.*;

import static utils.PropertiesManager.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.restassured.response.Response;
import pojoClasses.RegisterRequestPojo;
import pojoClasses.RegisterResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequestModel {

    //Variables
    String mockServerBaseURL = getPropertiesValue("baseUrlMockServer");
    String notesAPIEndpoint = getPropertiesValue("baseUrlApi")+"users/register";
    String responseBodyAsString;
    Response response;
    JsonMapper mapper;

    //ObjectsFromPojoClasses
    RegisterRequestPojo requestObject;
    RegisterResponsePojo responseObject;

    //Method to get Request Body inputs from Mock Server
    public RegisterRequestModel getRequestInputsFromMockServer(int userID) throws JsonProcessingException {
        response =
                GetRequest(mockServerBaseURL +userID,null);
        responseBodyAsString = getResponseBody(response);

        mapper = new JsonMapper();
        requestObject = mapper.readValue(responseBodyAsString, RegisterRequestPojo.class);
        return this;
    }

    //Method to Execute Registration Request
    public RegisterResponseModel registerUser() throws JsonProcessingException {

        Map map = new HashMap<>();
        map.put("name",requestObject.getName());
        map.put("email",requestObject.getEmail());
        map.put("password",requestObject.getPassword());
        response =
                MakeRequest("Post",notesAPIEndpoint,map,"application/json");

        responseBodyAsString = getResponseBody(response);
        mapper = new JsonMapper();

        responseObject = mapper.readValue(responseBodyAsString, RegisterResponsePojo.class);
        return new RegisterResponseModel(requestObject,responseObject);
    }
}
