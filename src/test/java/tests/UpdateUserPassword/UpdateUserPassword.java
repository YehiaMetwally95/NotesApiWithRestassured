package tests.UpdateUserPassword;

import objectModels.RegisterRequestModel;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pojoClasses.LoginRequestPojo;
import utils.JsonManager;

import java.io.IOException;

@Listeners(utils.TestNGListners.class)
public class UpdateUserPassword {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/UpdateUserPasswordTestData.json";
    JsonManager json;
    LoginRequestPojo loginRequestObject;

    @Test
    public void changeUserPassword() throws IOException, ParseException {
        json = new JsonManager(jsonFilePath);
        loginRequestObject =
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
                .validateTokenExists()

                //Get User Credentials from Login to Pass it & Prepare ChangePassword Request then Send it
                .navigateToChangePassword()
                .prepareChangePasswordRequestWithRandomPassword()
                .sendChangePasswordRequest()
                //Validations on ChangePassword Response
                .validateMassageFromResponse(json.getData("Messages.ChangePassword"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.ChangePassword"))
                .validateStatusFromResponse(json.getData("StatusCode.ChangePassword"))

                //Get User Credentials with the new password to Pass it & Prepare Login Request then Send it
                .getUserCredentialsWithNewPassword()
                .prepareLoginRequest()
                .sendLoginRequest()
                //Validations on Login Response
                .validateMassageFromResponse(json.getData("Messages.Login"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.Login"))
                .validateStatusFromResponse(json.getData("StatusCode.Login"))
                .validateTokenExists()
                .getRequestPojoObject();
    }

    @AfterMethod()
    //Update the User Credentials in Json File with the New Password
    public void updateUserCredentialsWithNewPassword() throws IOException, ParseException {
        json = new JsonManager(jsonFilePath);
        json.setData("UserCredentials1.Email",loginRequestObject.getEmail());
        json.setData("UserCredentials1.Password",loginRequestObject.getPassword());
    }
}
