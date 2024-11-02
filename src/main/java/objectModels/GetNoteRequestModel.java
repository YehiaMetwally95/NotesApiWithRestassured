package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.GetNoteRequestPojo;
import pojoClasses.GetNoteResponsePojo;

import static yehiaEngine.managers.ApisManager.*;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;

public class GetNoteRequestModel {

    //Variables
    String getNoteEndpoint = getPropertiesValue("baseUrlApi")+"notes/";
    String responseBodyAsString;
    Response response;
    JsonMapper mapper;

    String noteID;
    String token;

    //Constructor to pass NodeID & Token from CreateNote Model to GetNote Model
    public GetNoteRequestModel(String noteID,String token)
    {
        this.noteID = noteID;
        this.token = token;
    }

    //ObjectsFromPojoClasses
    GetNoteRequestPojo requestObject;
    GetNoteResponsePojo responseObject;

    @Step("Send GetNote Request")
    //Method to Execute Get Note Request
    public GetNoteResponseModel sendGetNoteRequest() throws JsonProcessingException {

        response =
                GetAuthRequest(getNoteEndpoint+noteID,null,
                        "X-Auth-Token",null,null,token);

        responseBodyAsString = getResponseBody(response);
        mapper = new JsonMapper();

        responseObject = mapper.readValue(responseBodyAsString, GetNoteResponsePojo.class);

        return new GetNoteResponseModel(requestObject,responseObject,token);
    }
}
