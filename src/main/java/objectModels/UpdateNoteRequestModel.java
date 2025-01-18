package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.*;

import java.util.Arrays;

import static yehiaEngine.managers.ApisManager.*;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;
import static yehiaEngine.utilities.RandomDataGenerator.*;
import static yehiaEngine.managers.ApisManager.ContentType.*;
import static yehiaEngine.managers.ApisManager.MethodType.*;
import static yehiaEngine.managers.ApisManager.AuthType.*;
import static yehiaEngine.managers.ApisManager.ParameterType.*;

public class UpdateNoteRequestModel {

    //Variables
    String updateNoteEndpoint = getPropertiesValue("baseUrlApi")+"notes/";
    Response response;

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
    public UpdateNoteRequestModel prepareUpdateNoteRequestFromJsonFile(String noteData) {
        updateNoteEndpoint = updateNoteEndpoint+noteID;

        requestObject = mapJsonStringToPojoClass(noteData,UpdateNoteRequestPojo.class);
        return this;
    }

    @Step("Prepare UpdateNote Request Dynamically With Random Values")
    //Method to set Request Body inputs from TimeStamp Dynamically
    public UpdateNoteRequestModel prepareUpdateNoteRequestWithRandomValues(){
        updateNoteEndpoint = updateNoteEndpoint+noteID;

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
    public UpdateNoteResponseModel sendUpdateNoteRequest() {

        response =MakeAuthRequest(PUT, updateNoteEndpoint,requestObject,URLENCODED
                ,XAuthToken,token,null,null);

        responseObject = mapResponseToPojoClass(response,UpdateNoteResponsePojo.class);

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
                .prepareGetNoteRequestWithNoteID()
                .sendGetNoteRequest()
                .validateStatusFromResponse("200")
                .validateTitleFromResponse(title)
                .validateDescriptionFromResponse(description)
                .validateCategoryFromResponse(category)
                .validateNoteStatusFromResponse(noteStatus);

        return new CreateNoteRequestModel(token);
    }

    @Step("Don't Update The Note")
    public CreateNoteRequestModel noUpdate() {
        return new CreateNoteRequestModel(token);
    }
}
