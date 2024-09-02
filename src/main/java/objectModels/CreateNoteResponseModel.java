package objectModels;

import io.qameta.allure.Step;
import org.testng.Assert;
import pojoClasses.CreateNoteRequestPojo;
import pojoClasses.CreateNoteResponsePojo;

public class CreateNoteResponseModel {

    //ObjectsFromPojoClasses
    CreateNoteRequestPojo requestObject;
    CreateNoteResponsePojo responseObject;

    //Variables
    boolean expectedSuccess = true;
    int expectedStatus = 200;
    String expectedMassage = "Successful Request";
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
    //Validation Methods
    public CreateNoteResponseModel validateMassageFromResponse() {
        Assert.assertEquals(responseObject.getMessage(),expectedMassage);
        return this;
    }

    @Step("validateStatusFromResponse")
    public CreateNoteResponseModel validateStatusFromResponse() {
        Assert.assertEquals(responseObject.getStatus(),expectedStatus);
        return this;
    }

    @Step("validateSuccessFromResponse")
    public CreateNoteResponseModel validateSuccessFromResponse() {
        Assert.assertEquals(responseObject.isSuccess(),expectedSuccess);
        return this;
    }

    @Step("getNoteID")
    //Getter Methods
    public String getNoteID()
    {
        return responseObject.getData().getId();
    }

    @Step("getNoteIdForUpdate")
    //Get Needed Token & Note ID from CreateNote Model and pass it to UpdateNote Model
    public UpdateNoteStatusRequestModel getNoteIdForUpdate()
    {
       return new UpdateNoteStatusRequestModel(
                responseObject.getData().getId(),
                token);
    }
}
