package tests.CreateNewNote;

import objectModels.RegisterRequestModel;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.JsonManager;

import java.io.IOException;

@Listeners(utils.TestNGListners.class)
public class CreateNewNote {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/CreateNewNoteTestData.json";
    JsonManager json;

    @Test
    public void createNewNoteByStaticDataFromJsonFile() throws IOException, ParseException {
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
                .prepareCreateNoteRequestFromJsonFile(json.getData("NoteDataInputs[0]"))
                .sendCreateNoteRequest()

                //Validations on CreateNote Response
                .validateMassageFromResponse(json.getData("Messages.CreateNote"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.CreateNote"))
                .validateStatusFromResponse(json.getData("StatusCode.CreateNote"))

                //Get Note ID from CreateNote Request to Pass it & Prepare GetNote Request then Send it
                .getNoteId()
                .sendGetNoteRequest()

                //Validations on GetNote Response
                .validateMassageFromResponse(json.getData("Messages.GetNote"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.GetNote"))
                .validateStatusFromResponse(json.getData("StatusCode.GetNote"))

                //Validate Note Data are Added Correctly
                .validateTitleFromResponse(json.getData("NoteDataInputs[0].Title"))
                .validateDescriptionFromResponse(json.getData("NoteDataInputs[0].Description"))
                .validateCategoryFromResponse(json.getData("NoteDataInputs[0].Category"))
                .validateNoteStatusFromResponse(json.getData("NoteDataInputs[0].NoteStatus"));
    }

    @Test
    public void createNewNoteByDynamicRandomData() throws IOException, ParseException {
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

                //Validations on CreateNote Response
                .validateMassageFromResponse(json.getData("Messages.CreateNote"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.CreateNote"))
                .validateStatusFromResponse(json.getData("StatusCode.CreateNote"))

                //Validate Note Data are Added Correctly
                .validateTitleFromResponse()
                .validateCategoryFromResponse()
                .validateDescriptionFromResponse()
                .validateNoteStatusFromResponse();
    }
}
