package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.CreateNoteRequestPojo;
import pojoClasses.CreateNoteResponsePojo;
import pojoClasses.RegisterRequestPojo;

import java.util.Arrays;

import static utils.ApiManager.*;
import static utils.PropertiesManager.getPropertiesValue;
import static utils.RandomDataGenerator.*;

public class CreateNoteRequestModel {

    //Variables
    String createNoteEndpoint = getPropertiesValue("baseUrlApi")+"notes/";
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

    @Step("Prepare CreateNote Request Statically from Json File")
    //Method to get Request Body inputs from Json File Statically
    public CreateNoteRequestModel prepareCreateNoteRequestFromJsonFile(String noteData) throws JsonProcessingException {
        mapper = new JsonMapper();
        requestObject = mapper.readValue(noteData, CreateNoteRequestPojo.class);
        return this;
    }

    @Step("Prepare CreateNote Request Dynamically With Random Values")
    //Method to set Request Body inputs from TimeStamp Dynamically
    public CreateNoteRequestModel prepareCreateNoteRequestWithRandomValues(){
        requestObject = CreateNoteRequestPojo.builder()
                .title(generateUniqueName())
                .description(generateDescription())
                .category(generateItemFromList(Arrays.asList("Home","Work","Personal")))
                .build();
        return this;
    }

    @Step("Send CreateNote Request")
    //Method to Execute CreateNode Request
    public CreateNoteResponseModel sendCreateNoteRequest() throws JsonProcessingException {

        response =
                MakeAuthRequest("Post", createNoteEndpoint,requestObject,
                        "application/x-www-form-urlencoded","X-Auth-Token",
                        null,null,token);

        responseBodyAsString = getResponseBody(response);
        mapper = new JsonMapper();

        responseObject = mapper.readValue(responseBodyAsString, CreateNoteResponsePojo.class);

        logRequestBody(requestObject);
        logResponseBody(responseObject);
        return new CreateNoteResponseModel(requestObject,responseObject,token);
    }

    //Facade Method
    @Step("Create new Note")
    public UpdateNoteRequestModel createNewNote(String noteJsonObject,String title,
                                                String description,String category,String noteStatus) throws JsonProcessingException {
        return prepareCreateNoteRequestFromJsonFile(noteJsonObject)
                .sendCreateNoteRequest()
                .getNoteId()
                .sendGetNoteRequest()
                .validateTitleFromResponse(title)
                .validateDescriptionFromResponse(description)
                .validateCategoryFromResponse(category)
                .validateNoteStatusFromResponse(noteStatus)
                .getNoteId();
    }

    @Step("Create new Note")
    public UpdateNoteRequestModel createNewNoteWithDynamicRandomValues() throws JsonProcessingException {
        return prepareCreateNoteRequestWithRandomValues()
                .sendCreateNoteRequest()
                .validateStatusFromResponse("200")
                .validateTitleFromResponse()
                .validateDescriptionFromResponse()
                .validateCategoryFromResponse()
                .validateNoteStatusFromResponse()
                .getNoteId()
                .sendGetNoteRequest()
                .validateStatusFromResponse("200")
                .getNoteId();
    }
}
