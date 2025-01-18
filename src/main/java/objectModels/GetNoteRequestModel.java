package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.GetNoteRequestPojo;
import pojoClasses.GetNoteResponsePojo;
import pojoClasses.LoginResponsePojo;
import pojoClasses.UpdateNoteRequestPojo;

import static yehiaEngine.managers.ApisManager.ContentType.*;
import static yehiaEngine.managers.ApisManager.MethodType.*;
import static yehiaEngine.managers.ApisManager.AuthType.*;
import static yehiaEngine.managers.ApisManager.ParameterType.*;
import java.util.Map;

import static yehiaEngine.managers.ApisManager.*;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;

public class GetNoteRequestModel {

    //Variables
    String getNoteEndpoint = getPropertiesValue("baseUrlApi")+"notes/{id}";
    Response response;

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

    @Step("Prepare Get Note Request With Note ID")
    //Method to get Request Body inputs from Json File Statically
    public GetNoteRequestModel prepareGetNoteRequestWithNoteID() {
        requestObject = GetNoteRequestPojo.builder()
                .id(noteID)
                .build();
        return this;
    }

    @Step("Send GetNote Request")
    //Method to Execute Get Note Request
    public GetNoteResponseModel sendGetNoteRequest() {

        response = GetAuthRequest(getNoteEndpoint,PATH,requestObject,
                XAuthToken,token,null,null);

        responseObject = mapResponseToPojoClass(response, GetNoteResponsePojo.class);

        return new GetNoteResponseModel(requestObject,responseObject,token);
    }
}
