package objectModels;

import io.qameta.allure.Step;
import org.testng.Assert;
import pojoClasses.GetNoteRequestPojo;
import pojoClasses.GetNoteResponsePojo;
import pojoClasses.UpdateNoteStatusRequestPojo;
import pojoClasses.UpdateNoteStatusResponsePojo;

public class GetNoteResponseModel {
    //ObjectsFromPojoClasses
    GetNoteRequestPojo requestObject;
    GetNoteResponsePojo responseObject;

    //Variables
    boolean expectedSuccess = true;
    int expectedStatus = 200;
    String expectedMassage = "Successful Request";
    String token;
    boolean noteStatus;

    //Constructor to pass the response from Request Model to Response Model
    public GetNoteResponseModel(GetNoteRequestPojo requestObject,
                                GetNoteResponsePojo responseObject,
                                         String token) {
        this.requestObject = requestObject;
        this.responseObject= responseObject;
        this.token = token;
    }

    //Validation Methods
    @Step("validateMassageFromResponse")
    public GetNoteResponseModel validateMassageFromResponse() {
        Assert.assertEquals(responseObject.getMessage(),expectedMassage);
        return this;
    }

    @Step("validateStatusFromResponse")
    public GetNoteResponseModel validateStatusFromResponse() {
        Assert.assertEquals(responseObject.getStatus(),expectedStatus);
        return this;
    }

    @Step("validateSuccessFromResponse")
    public GetNoteResponseModel validateSuccessFromResponse() {
        Assert.assertEquals(responseObject.isSuccess(),expectedSuccess);
        return this;
    }

    //Getter Methods
    @Step("isNoteStatusCompleted")
    public boolean isNoteStatusCompleted()
    {
        noteStatus = responseObject.getData().isCompleted();
        return noteStatus;
    }
}
