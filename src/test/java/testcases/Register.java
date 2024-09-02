package testcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import objectModels.RegisterRequestModel;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(utils.TestNGListners.class)
public class Register {

    @Test
    public void registerNewUserByStaticDataFromMockServer() throws JsonProcessingException {

        new RegisterRequestModel()
                .prepareRegistrationRequestFromMockServer(4)
                .registerNewUser()
                .validateStatusFromResponse()
                .validateMassageFromResponse()
                .validateSuccessFromResponse()
                .validateNameFromResponse()
                .validateEmailFromResponse();
    }

    @Test
    public void registerNewUserByDynamicDataFromTimeStamp() throws JsonProcessingException {

       new RegisterRequestModel()
                .prepareRegistrationRequestWithRandomValues()
                .registerNewUser()
                .validateStatusFromResponse()
                .validateMassageFromResponse()
                .validateSuccessFromResponse()
                .validateNameFromResponse()
                .validateEmailFromResponse();
    }
}
