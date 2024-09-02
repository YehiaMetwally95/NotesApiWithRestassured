package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.CreateNoteRequestPojo;
import pojoClasses.CreateNoteResponsePojo;

import static utils.ApiManager.*;
import static utils.PropertiesManager.getPropertiesValue;

public class CreateNoteRequestModel {

    //Variables
    String createNoteEndpoint = getPropertiesValue("baseUrlApi")+"notes/";
    String mockServerUrl = getPropertiesValue("baseUrlMockServer")+"Notes/";
    String responseBodyAsString;
    Response response;
    JsonMapper mapper;

    String token;

    //Constructor to pass Auth Token from Login into CreateNote Model
    public CreateNoteRequestModel(String token) {
        this.token = token;
    }

    //ObjectsFromPojoClasses
    CreateNoteRequestPojo requestObject;
    CreateNoteResponsePojo responseObject;

    @Step("prepareNewNoteRequestFromMockServer")
    //Method to get Request Body inputs from Mock Server Statically
    public CreateNoteRequestModel prepareNewNoteRequestFromMockServer(int userID) throws JsonProcessingException {
        response =
                GetRequest(mockServerUrl +userID,null);
        responseBodyAsString = getResponseBody(response);

        mapper = new JsonMapper();
        requestObject = mapper.readValue(responseBodyAsString, CreateNoteRequestPojo.class);
        return this;
    }

    @Step("createNewNote")
    //Method to Execute CreateNode Request
    public CreateNoteResponseModel createNewNote() throws JsonProcessingException {

        response =
                MakeAuthRequest("Post", createNoteEndpoint,requestObject,
                        "application/json","X-Auth-Token",
                        null,null,token);

        responseBodyAsString = getResponseBody(response);
        mapper = new JsonMapper();

        responseObject = mapper.readValue(responseBodyAsString, CreateNoteResponsePojo.class);
        return new CreateNoteResponseModel(requestObject,responseObject,token);
    }

    //Facade Method
    @Step("createNote")
    public UpdateNoteStatusRequestModel createNote(int userID) throws JsonProcessingException {
        return prepareNewNoteRequestFromMockServer(userID).createNewNote().getNoteIdForUpdate();
    }
}
