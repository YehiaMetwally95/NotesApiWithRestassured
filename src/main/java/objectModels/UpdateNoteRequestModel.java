package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.*;

import java.util.Arrays;

import static utils.ApiManager.*;
import static utils.ApiManager.getResponseBody;
import static utils.PropertiesManager.getPropertiesValue;
import static utils.RandomDataGenerator.*;

public class UpdateNoteRequestModel {

    //Variables
    String updateNoteEndpoint = getPropertiesValue("baseUrlApi")+"notes/";
    String responseBodyAsString;
    Response response;
    JsonMapper mapper;

    String token;
    String noteID;

    //Constructor to pass NodeID & Token from GetNote Model to UpdateNote Model
    public UpdateNoteRequestModel(String noteID, String token)
    {
        this.noteID = noteID;
        this.token = token;
    }

    //ObjectsFromPojoClasses
    UpdateNoteRequestPojo requestObject;
    UpdateNoteResponsePojo responseObject;

    @Step("Prepare UpdateNote Request Statically from Json File")
    //Method to get Request Body inputs from Json File Statically
    public UpdateNoteRequestModel prepareUpdateNoteRequestFromJsonFile(String noteData) throws JsonProcessingException {
        mapper = new JsonMapper();
        requestObject = mapper.readValue(noteData, UpdateNoteRequestPojo.class);
        return this;
    }

    @Step("Prepare UpdateNote Request Dynamically With Random Values")
    //Method to set Request Body inputs from TimeStamp Dynamically
    public UpdateNoteRequestModel prepareUpdateNoteRequestWithRandomValues(){
        requestObject = UpdateNoteRequestPojo.builder()
                .title(generateUniqueName())
                .description(generateDescription())
                .category(generateItemFromList(Arrays.asList("Home","Work","Personal")))
                .completed(false)
                .build();
        return this;
    }

    @Step("Send UpdateNote Request")
    //Method to Execute UpdateNote Request
    public UpdateNoteResponseModel sendUpdateNoteRequest() throws JsonProcessingException {

        response =
                MakeAuthRequest("Put", updateNoteEndpoint+noteID
                        ,requestObject,"application/x-www-form-urlencoded","X-Auth-Token",
                        null,null,token);

        responseBodyAsString = getResponseBody(response);
        mapper = new JsonMapper();

        responseObject = mapper.readValue(responseBodyAsString, UpdateNoteResponsePojo.class);

        logRequestBody(requestObject);
        logResponseBody(responseObject);
        return new UpdateNoteResponseModel(requestObject,responseObject,token);
    }

    //Facade Method
    @Step("Update The Note")
    public CreateNoteRequestModel updateTheNote(String noteJsonObject, String title,
                                                String description, String category, String noteStatus) throws JsonProcessingException {
        prepareUpdateNoteRequestFromJsonFile(noteJsonObject)
                .sendUpdateNoteRequest()
                .validateStatusFromResponse("200")
                .getNoteID()
                .sendGetNoteRequest()
                .validateCategoryFromResponse("200")
                .validateTitleFromResponse(title)
                .validateDescriptionFromResponse(description)
                .validateCategoryFromResponse(category)
                .validateNoteStatusFromResponse(noteStatus);

        return new CreateNoteRequestModel(token);
    }

    @Step("Don't Update The Note")
    public CreateNoteRequestModel noUpdate() throws JsonProcessingException {
        return new CreateNoteRequestModel(token);
    }
}
