package objectModels;

import io.qameta.allure.Step;
import org.testng.Assert;
import pojoClasses.UpdateNoteStatusRequestPojo;
import pojoClasses.UpdateNoteStatusResponsePojo;

public class UpdateNoteStatusResponseModel {
    //ObjectsFromPojoClasses
    UpdateNoteStatusRequestPojo requestObject;
    UpdateNoteStatusResponsePojo responseObject;

    //Variables
    boolean expectedSuccess = true;
    int expectedStatus = 200;
    String expectedMassage = "Note successfully updated";
    String token;
    boolean noteStatus;

    //Constructor to pass the response from Request Model to Response Model
    public UpdateNoteStatusResponseModel(UpdateNoteStatusRequestPojo requestObject,
                                   UpdateNoteStatusResponsePojo responseObject,
                                   String token) {
        this.requestObject = requestObject;
        this.responseObject= responseObject;
        this.token = token;
    }

    //Validation Methods
    @Step("validateMassageFromResponse")
    public UpdateNoteStatusResponseModel validateMassageFromResponse() {
        Assert.assertEquals(responseObject.getMessage(),expectedMassage);
        return this;
    }

    @Step("validateStatusFromResponse")
    public UpdateNoteStatusResponseModel validateStatusFromResponse() {
        Assert.assertEquals(responseObject.getStatus(),expectedStatus);
        return this;
    }

    @Step("validateSuccessFromResponse")
    public UpdateNoteStatusResponseModel validateSuccessFromResponse() {
        Assert.assertEquals(responseObject.isSuccess(),expectedSuccess);
        return this;
    }

    //Getter Methods

    //Get Needed Token & Note ID & Note Status from UpdateNote Model and pass it to GetNote Model
    @Step("getNoteIdForRetrieve")
    public GetNoteRequestModel getNoteIdForRetrieve()
    {
        return new GetNoteRequestModel(
                responseObject.getData().getId(),
                token,
                noteStatus);
    }
}
