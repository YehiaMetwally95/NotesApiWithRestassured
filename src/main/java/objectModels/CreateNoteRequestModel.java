package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.CreateNoteRequestPojo;
import pojoClasses.CreateNoteResponsePojo;
import pojoClasses.LoginResponsePojo;
import pojoClasses.UpdateNoteRequestPojo;

import java.util.Arrays;

import static yehiaEngine.managers.ApisManager.*;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;
import static yehiaEngine.utilities.RandomDataGenerator.*;
import static yehiaEngine.managers.ApisManager.ContentType.*;
import static yehiaEngine.managers.ApisManager.MethodType.*;
import static yehiaEngine.managers.ApisManager.AuthType.*;
import static yehiaEngine.managers.ApisManager.ParameterType.*;

public class CreateNoteRequestModel {

    //Variables
    String createNoteEndpoint = getPropertiesValue("baseUrlApi")+"notes/";
    Response response;

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
    public CreateNoteRequestModel prepareCreateNoteRequestFromJsonFile(String noteData) {
        requestObject = mapJsonStringToPojoClass(noteData, CreateNoteRequestPojo.class);
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
    public CreateNoteResponseModel sendCreateNoteRequest() {

        response = MakeAuthRequest(POST, createNoteEndpoint,requestObject,URLENCODED,
                XAuthToken,token,null,null);

        responseObject = mapResponseToPojoClass(response, CreateNoteResponsePojo.class);

        return new CreateNoteResponseModel(requestObject,responseObject,token);
    }

    //Facade Method
    @Step("Create new Note")
    public UpdateNoteRequestModel createNewNote(String noteJsonObject,String title,
                                                String description,String category,String noteStatus) {
        return prepareCreateNoteRequestFromJsonFile(noteJsonObject)
                .sendCreateNoteRequest()
                .getNoteId()
                .prepareGetNoteRequestWithNoteID()
                .sendGetNoteRequest()
                .validateTitleFromResponse(title)
                .validateDescriptionFromResponse(description)
                .validateCategoryFromResponse(category)
                .validateNoteStatusFromResponse(noteStatus)
                .getNoteId();
    }

    @Step("Create new Note")
    public UpdateNoteRequestModel createNewNoteWithDynamicRandomValues() {
        return prepareCreateNoteRequestWithRandomValues()
                .sendCreateNoteRequest()
                .validateStatusFromResponse("200")
                .validateTitleFromResponse()
                .validateDescriptionFromResponse()
                .validateCategoryFromResponse()
                .validateNoteStatusFromResponse()
                .getNoteId()
                .prepareGetNoteRequestWithNoteID()
                .sendGetNoteRequest()
                .validateStatusFromResponse("200")
                .getNoteId();
    }
}
