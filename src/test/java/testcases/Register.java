package testcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import objectModels.RegisterRequestModel;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(utils.TestNGListners.class)
public class Register {

    @Test
    public void registerNewUser() throws JsonProcessingException {

        String userID = new RegisterRequestModel()
                .getRequestInputsFromMockServer(4)
                .registerUser()
                .validateStatusFromResponse()
                .validateMassageFromResponse()
                .validateSuccessFromResponse()
                .validateNameFromResponse()
                .validateEmailFromResponse()
                .getUserIDFromResponse();

        System.out.println(userID);
    }
}
