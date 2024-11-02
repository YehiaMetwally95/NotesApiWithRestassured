package tests.CreateNewNote;

import objectModels.RegisterRequestModel;
import org.testng.annotations.Test;
import yehiaEngine.managers.JsonManager;

import java.io.IOException;

public class CreateNewNoteFacade {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/CreateNewNoteTestData.json";
    JsonManager json;

    @Test
    public void createNewNoteFacade() throws IOException {
        json = new JsonManager(jsonFilePath);
        new RegisterRequestModel()
                .registerNewUserWithRandomData()
                .navigateToCreateNote()
                .createNewNote(json.getData("NoteDataInputs[1]"),
                        json.getData("NoteDataInputs[1].Title"),json.getData("NoteDataInputs[1].Description"),
                        json.getData("NoteDataInputs[1].Category"), json.getData("NoteDataInputs[1].NoteStatus"));
    }

    @Test
    public void createMultipleNotesFacade() throws IOException {
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
