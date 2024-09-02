package testcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import objectModels.RegisterRequestModel;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(utils.TestNGListners.class)
public class CreateNote {

    @Test
    public void createNewNode() throws JsonProcessingException {

       String nodeID =  new RegisterRequestModel()
                .prepareRegistrationRequestWithRandomValues()
                .registerNewUser()
                .getNewUserCredentials()
                .prepareLoginRequest()
                .loginWithExistingUser()
                .getAuthTokenForNotes()
                .prepareNewNoteRequestFromMockServer(1)
                .createNewNote()
                .getNoteID();

       System.out.println(nodeID);
    }

    @Test
    public void createNewNodeWithFacadeDesign() throws JsonProcessingException {

        new RegisterRequestModel()
                .register()
                .login()
                .createNote(1);
    }
}
