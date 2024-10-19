package tests.UpdateNote;

import objectModels.RegisterRequestModel;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.JsonManager;

import java.io.IOException;

@Listeners(utils.TestNGListners.class)
public class UpdateNoteFacade {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/UpdateNoteTestData.json";
    JsonManager json;

    @Test
    public void updateExistingNoteFacade() throws IOException, ParseException {
        json = new JsonManager(jsonFilePath);

        new RegisterRequestModel()
                .registerNewUserWithRandomData()
                .navigateToCreateNote()
                .createNewNoteWithDynamicRandomValues()
                .updateTheNote(json.getData("NoteDataInputs[1]"),json.getData("NoteDataInputs[1].Title"), json.getData("NoteDataInputs[1].Description"),json.getData("NoteDataInputs[1].Category"), json.getData("NoteDataInputs[1]"));
    }

    @Test
    public void updateMultipleNotesFacade() throws IOException, ParseException {
        json = new JsonManager(jsonFilePath);

        new RegisterRequestModel()
                .registerNewUserWithRandomData()
                .navigateToCreateNote()
                .createNewNoteWithDynamicRandomValues()
                .updateTheNote(json.getData("NoteDataInputs[1]"),json.getData("NoteDataInputs[1].Title"), json.getData("NoteDataInputs[1].Description"),json.getData("NoteDataInputs[1].Category"), json.getData("NoteDataInputs[1]"))
                .createNewNoteWithDynamicRandomValues()
                .updateTheNote(json.getData("NoteDataInputs[0]"),json.getData("NoteDataInputs[0].Title"), json.getData("NoteDataInputs[0].Description"),json.getData("NoteDataInputs[0].Category"), json.getData("NoteDataInputs[0]"))
                .createNewNoteWithDynamicRandomValues()
                .noUpdate();
    }
}
