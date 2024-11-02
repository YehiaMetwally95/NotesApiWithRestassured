package objectModels;

import io.qameta.allure.Step;
import org.testng.Assert;
import pojoClasses.CreateNoteRequestPojo;
import pojoClasses.CreateNoteResponsePojo;
import yehiaEngine.assertions.CustomAssert;

public class CreateNoteResponseModel {

    //ObjectsFromPojoClasses
    CreateNoteRequestPojo requestObject;
    CreateNoteResponsePojo responseObject;

    //Variables
    String token;

    //Constructor to pass the response from Request Model to Response Model
    public CreateNoteResponseModel(CreateNoteRequestPojo requestObject,
                                   CreateNoteResponsePojo responseObject,
                                   String token) {
        this.requestObject = requestObject;
        this.responseObject= responseObject;
        this.token = token;
    }

    @Step("validateMassageFromResponse")
    public CreateNoteResponseModel validateMassageFromResponse(String message) {
        CustomAssert.assertEquals(responseObject.getMessage(),message);
        return this;
    }

    @Step("validateStatusFromResponse")
    public CreateNoteResponseModel validateStatusFromResponse(String statusCode) {
        CustomAssert.assertEquals(responseObject.getStatus(),Integer.parseInt(statusCode));
        return this;
    }

    @Step("validateSuccessFromResponse")
    public CreateNoteResponseModel validateSuccessFromResponse(String successFlag) {
        CustomAssert.assertEquals(responseObject.isSuccess(),Boolean.parseBoolean(successFlag));
        return this;
    }

    @Step("Validate Description From Response")
    public CreateNoteResponseModel validateDescriptionFromResponse() {
        CustomAssert.assertEquals(responseObject.getData().getDescription()
                ,requestObject.getDescription());
        return this;
    }

    @Step("Validate Title From Response")
    public CreateNoteResponseModel validateTitleFromResponse() {
        CustomAssert.assertEquals(responseObject.getData().getTitle()
                ,requestObject.getTitle());
        return this;
    }

    @Step("Validate Category From Response")
    public CreateNoteResponseModel validateCategoryFromResponse() {
        CustomAssert.assertEquals(responseObject.getData().getCategory()
                ,requestObject.getCategory());
        return this;
    }

    @Step("Validate NoteStatus From Response")
    public CreateNoteResponseModel validateNoteStatusFromResponse() {
        CustomAssert.assertEquals(responseObject.getData().isCompleted()
                ,requestObject.isCompleted());
        return this;
    }

    //Getter Methods
    public CreateNoteRequestPojo getRequestPojoObject() {
        return requestObject;
    }

    public CreateNoteResponsePojo getResponsePojoObject() {
        return responseObject;
    }

    @Step("Get Note ID")
    //Get Needed Token & Note ID from CreateNote Model and pass it to GetNote Model
    public GetNoteRequestModel getNoteId()
    {
       return new GetNoteRequestModel(responseObject.getData().getId(),token);
    }
}
