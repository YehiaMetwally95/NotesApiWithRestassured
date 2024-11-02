package objectModels;

import io.qameta.allure.Step;

import pojoClasses.UpdateNoteRequestPojo;
import pojoClasses.UpdateNoteResponsePojo;
import yehiaEngine.assertions.CustomAssert;

public class UpdateNoteResponseModel {
    //ObjectsFromPojoClasses
    UpdateNoteRequestPojo requestObject;
    UpdateNoteResponsePojo responseObject;

    //Variables
    String token;
    boolean noteStatus;

    //Constructor to pass the response from Request Model to Response Model
    public UpdateNoteResponseModel(UpdateNoteRequestPojo requestObject,
                                   UpdateNoteResponsePojo responseObject,String token) {
        this.requestObject = requestObject;
        this.responseObject= responseObject;
        this.token = token;
    }

    //Validation Methods
    @Step("validateMassageFromResponse")
    public UpdateNoteResponseModel validateMassageFromResponse(String message) {
        CustomAssert.assertEquals(responseObject.getMessage(),message);
        return this;
    }

    @Step("validateStatusFromResponse")
    public UpdateNoteResponseModel validateStatusFromResponse(String statusCode) {
        CustomAssert.assertEquals(responseObject.getStatus(),Integer.parseInt(statusCode));
        return this;
    }

    @Step("validateSuccessFromResponse")
    public UpdateNoteResponseModel validateSuccessFromResponse(String successFlag) {
        CustomAssert.assertEquals(responseObject.isSuccess(),Boolean.parseBoolean(successFlag));
        return this;
    }

    //Getter Methods
    public UpdateNoteRequestPojo getRequestPojoObject() {
        return requestObject;
    }

    public UpdateNoteResponsePojo getResponsePojoObject() {
        return responseObject;
    }

    //Get Needed Token & Note ID from UpdateNote Model and pass it to GetNote Model
    @Step("Get NoteID")
    public GetNoteRequestModel getNoteID()
    {
        return new GetNoteRequestModel(responseObject.getData().getId(),token);
    }
}
