package testcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import objectModels.RegisterRequestModel;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(utils.TestNGListners.class)
public class UpdateNoteStatus {

    @Test
    public void updateNoteStatus() throws JsonProcessingException {
        new RegisterRequestModel()
                .prepareRegistrationRequestWithRandomValues()
                .registerNewUser()
                .getNewUserCredentials()
                .prepareLoginRequest()
                .loginWithExistingUser()
                .getAuthTokenForNotes()
                .prepareNewNoteRequestFromMockServer(1)
                .createNewNote()
                .getNoteIdForUpdate()
                .prepareUpdateNoteRequest()
                .updateNoteStatus()
                .getNoteIdForRetrieve()
                .retrieveNoteDetails()
                .isNoteStatusCompleted();
    }

    @Test
    public void updateNoteStatusWithFacadeDesign() throws JsonProcessingException {
        new RegisterRequestModel()
                .register()
                .login()
                .createNote(1)
                .updateNote()
                .retrieveNote();
    }

}
