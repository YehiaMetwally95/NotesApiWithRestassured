package testcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import objectModels.RegisterRequestModel;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(utils.TestNGListners.class)
public class Login {

    String AuthToken;

    @Test
    public void loginWithExistingUser() throws JsonProcessingException {

         new RegisterRequestModel()
                .prepareRegistrationRequestWithRandomValues()
                .registerNewUser()
                .getNewUserCredentials()
                .prepareLoginRequest()
                .loginWithExistingUser()
                .validateStatusFromResponse()
                .validateMassageFromResponse()
                 .validateTokenExists();
    }

    @Test
    public void loginWithExistingUserWithFacadeDesign() throws JsonProcessingException {

         new RegisterRequestModel()
                .register()
                .login();
    }
}
