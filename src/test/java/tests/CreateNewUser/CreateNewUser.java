package tests.CreateNewUser;

import objectModels.RegisterRequestModel;
import org.testng.annotations.Test;
import yehiaEngine.managers.JsonManager;

import java.io.IOException;

public class CreateNewUser {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/CreateNewUserTestData.json";
    JsonManager json;

    @Test
    public void createNewUserByStaticDataFromJsonFile() throws IOException {
        json = new JsonManager(jsonFilePath);
        new RegisterRequestModel()
                //Prepare Register Request then Send it
                .prepareRegisterRequestFromJsonFile(json.getData("RegisterDataInputs[0]"))
                .sendRegisterRequest()
                //Validations on Register Response
                .validateMassageFromResponse(json.getData("Messages.Register"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.Register"))
                .validateStatusFromResponse(json.getData("StatusCode.Register"))
                .validateNameFromResponse()
                .validateEmailFromResponse()

                //Get User Credentials from Register to Pass it & Prepare Login Request then Send it
                .getNewUserCredentials()
                .prepareLoginRequest()
                .sendLoginRequest()
                //Validations on Login Response
                .validateMassageFromResponse(json.getData("Messages.Login"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.Login"))
                .validateStatusFromResponse(json.getData("StatusCode.Login"))
                .validateTokenExists();
    }

    @Test
    public void createNewUserByDynamicRandomData() throws IOException {
        json = new JsonManager(jsonFilePath);
        new RegisterRequestModel()
                //Prepare Register Request then Send it
                .prepareRegisterRequestWithRandomValues()
                .sendRegisterRequest()
                //Validations on Register Response
                .validateMassageFromResponse(json.getData("Messages.Register"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.Register"))
                .validateStatusFromResponse(json.getData("StatusCode.Register"))
                .validateNameFromResponse()
                .validateEmailFromResponse()

                //Get User Credentials from Register to Pass it & Prepare Login Request then Send it
                .getNewUserCredentials()
                .prepareLoginRequest()
                .sendLoginRequest()
                //Validations on Login Response
                .validateMassageFromResponse(json.getData("Messages.Login"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.Login"))
                .validateStatusFromResponse(json.getData("StatusCode.Login"))
                .validateTokenExists();
    }
}
