package tests.CreateNewNote;

import objectModels.RegisterRequestModel;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.JsonManager;

import java.io.IOException;

@Listeners(utils.TestNGListners.class)
public class CreateNewNoteFacade {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/CreateNewNoteTestData.json";
    JsonManager json;

    @Test
    public void createNewNoteFacade() throws IOException, ParseException {
        json = new JsonManager(jsonFilePath);
        new RegisterRequestModel()
                .registerNewUserWithRandomData()
                .navigateToCreateNote()
                .createNewNote(json.getData("NoteDataInputs[1]"),
                        json.getData("NoteDataInputs[1].Title"),json.getData("NoteDataInputs[1].Description"),
                        json.getData("NoteDataInputs[1].Category"), json.getData("NoteDataInputs[1].NoteStatus"));
    }

    @Test
    public void createMultipleNotesFacade() throws IOException, ParseException {
        json = new JsonManager(jsonFilePath);
        new RegisterRequestModel()
                .registerNewUserWithRandomData()
                .navigateToCreateNote()
                .createNewNote(json.getData("NoteDataInputs[1]"),json.getData("NoteDataInputs[1].Title"), json.getData("NoteDataInputs[1].Description"),json.getData("NoteDataInputs[1].Category"), json.getData("NoteDataInputs[1].NoteStatus"))
                .noUpdate()
                .createNewNoteWithDynamicRandomValues()
                .noUpdate()
                .createNewNote(json.getData("NoteDataInputs[0]"),json.getData("NoteDataInputs[0].Title"), json.getData("NoteDataInputs[0].Description"),json.getData("NoteDataInputs[0].Category"), json.getData("NoteDataInputs[0].NoteStatus"))
                .noUpdate();
    }

}
