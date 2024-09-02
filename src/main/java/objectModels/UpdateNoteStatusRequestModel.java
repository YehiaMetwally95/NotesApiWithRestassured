package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.*;

import static utils.ApiManager.*;
import static utils.ApiManager.getResponseBody;
import static utils.PropertiesManager.getPropertiesValue;

public class UpdateNoteStatusRequestModel {

    //Variables
    String updateNoteEndpoint = getPropertiesValue("baseUrlApi")+"notes/";
    String responseBodyAsString;
    Response response;
    JsonMapper mapper;

    String token;
    String noteID;

    //Constructor to pass NodeID & Token from CreateNote Model to UpdateNote Model
    public UpdateNoteStatusRequestModel(String noteID, String token)
    {
        this.noteID = noteID;
        this.token = token;
    }

    //ObjectsFromPojoClasses
    UpdateNoteStatusRequestPojo requestObject;
    UpdateNoteStatusResponsePojo responseObject;

    @Step("prepareUpdateNoteRequest")
    //Method to set Request Body of Update Note with New Note Status
    public UpdateNoteStatusRequestModel prepareUpdateNoteRequest() throws JsonProcessingException {
        requestObject = UpdateNoteStatusRequestPojo.builder()
                .completed(true)
                .build();
        return this;
    }

    @Step("updateNoteStatus")
    //Method to Execute UpdateNoteStatus Request
    public UpdateNoteStatusResponseModel updateNoteStatus() throws JsonProcessingException {

        response =
                MakeAuthRequest("Patch", updateNoteEndpoint+noteID
                        ,requestObject,"application/json","X-Auth-Token",
                        null,null,token);

        responseBodyAsString = getResponseBody(response);
        mapper = new JsonMapper();

        responseObject = mapper.readValue(responseBodyAsString, UpdateNoteStatusResponsePojo.class);
        return new UpdateNoteStatusResponseModel(requestObject,responseObject,token);
    }

    //Facade Method
    @Step("updateNote")
    public GetNoteRequestModel updateNote() throws JsonProcessingException {
        return prepareUpdateNoteRequest().updateNoteStatus().getNoteIdForRetrieve();
    }
}
