package tests.UpdateNote;

import objectModels.RegisterRequestModel;
import org.testng.annotations.Test;
import yehiaEngine.managers.JsonManager;

import java.io.IOException;

public class UpdateNote {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/UpdateNoteTestData.json";
    JsonManager json;

    @Test
    public void updateExistingNote() throws IOException {
        json = new JsonManager(jsonFilePath);
        new RegisterRequestModel()

                //Prepare Register Request then Send it
                .prepareRegisterRequestWithRandomValues()
                .sendRegisterRequest()

                //Get User Credentials from Register to Pass it & Prepare Login Request then Send it
                .getNewUserCredentials()
                .prepareLoginRequest()
                .sendLoginRequest()

                //Get Token from Login to Pass it & Prepare CreateNote Request then Send it
                .navigateToCreateNote()
                .prepareCreateNoteRequestWithRandomValues()
                .sendCreateNoteRequest()

                //Get Note ID from CreateNote Request to Pass it & Prepare GetNote Request then Send it
                .getNoteId()
                .sendGetNoteRequest()

                //Get Note ID from GetNote Request to Pass it & Prepare UpdateNote Request then Send it
                .getNoteId()
                .prepareUpdateNoteRequestFromJsonFile(json.getData("NoteDataInputs[1]"))
                .sendUpdateNoteRequest()

                //Validations on UpdateNote Response
                .validateMassageFromResponse(json.getData("Messages.UpdateNote"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.UpdateNote"))
                .validateStatusFromResponse(json.getData("StatusCode.UpdateNote"))

                //Get Note ID from UpdateNote Request to Pass it & Prepare GetNote Request then Send it
                .getNoteID()
                .sendGetNoteRequest()

                //Validate Note Data are Updated Correctly
                .validateTitleFromResponse(json.getData("NoteDataInputs[1].Title"))
                .validateDescriptionFromResponse(json.getData("NoteDataInputs[1].Description"))
                .validateCategoryFromResponse(json.getData("NoteDataInputs[1].Category"))
                .validateNoteStatusFromResponse(json.getData("NoteDataInputs[1].NoteStatus"));
    }
}
