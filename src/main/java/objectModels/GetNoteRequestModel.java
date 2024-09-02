package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.GetNoteRequestPojo;
import pojoClasses.GetNoteResponsePojo;
import pojoClasses.UpdateNoteStatusRequestPojo;
import pojoClasses.UpdateNoteStatusResponsePojo;

import static utils.ApiManager.*;
import static utils.PropertiesManager.getPropertiesValue;

public class GetNoteRequestModel {

    //Variables
    String getNoteEndpoint = getPropertiesValue("baseUrlApi")+"notes/";
    String responseBodyAsString;
    Response response;
    JsonMapper mapper;

    String noteID;
    String token;
    boolean noteStatus;

    //Constructor to pass NodeID & Token from UpdateNote Model to GetNote Model
    //Constructor to pass noteStatus from UpdateNote Model to GetNote Model
    public GetNoteRequestModel(String noteID,String token,boolean noteStatus)
    {
        this.noteID = noteID;
        this.token = token;
        this.noteStatus = noteStatus;
    }

    //ObjectsFromPojoClasses
    GetNoteRequestPojo requestObject;
    GetNoteResponsePojo responseObject;

    @Step("retrieveNoteDetails")
    //Method to Execute Get Note Request
    public GetNoteResponseModel retrieveNoteDetails() throws JsonProcessingException {

        response =
                GetAuthRequest(getNoteEndpoint+noteID,null,
                        "X-Auth-Token",null,null,token);

        responseBodyAsString = getResponseBody(response);
        mapper = new JsonMapper();

        responseObject = mapper.readValue(responseBodyAsString, GetNoteResponsePojo.class);
        return new GetNoteResponseModel(requestObject,responseObject,token);
    }

    //Facade Method
    @Step("retrieveNote")
    public boolean retrieveNote() throws JsonProcessingException {
        return retrieveNoteDetails().isNoteStatusCompleted();
    }
}
