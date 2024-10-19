package tests.UpdateUserPassword;

import objectModels.LoginRequestModel;
import objectModels.RegisterRequestModel;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pojoClasses.LoginRequestPojo;
import utils.JsonManager;

import java.io.IOException;

@Listeners(utils.TestNGListners.class)
public class UpdateUserPasswordFacade {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/UpdateUserPasswordTestData.json";
    JsonManager json;
    LoginRequestPojo loginRequestObject;

    @Test
    public void updateUserPasswordFacade() throws IOException, ParseException {
        json = new JsonManager(jsonFilePath);
        loginRequestObject =
                new RegisterRequestModel()
                        .registerNewUserWithRandomData()
                        .navigateToChangePassword()
                        .changeUserPassword();
    }

    @AfterMethod()
    //Update the User Credentials in Json File with the New Password
    public void updateUserCredentialsWithNewPassword() throws IOException, ParseException {
        json = new JsonManager(jsonFilePath);
        json.setData("UserCredentials2.Email",loginRequestObject.getEmail());
        json.setData("UserCredentials2.Password",loginRequestObject.getPassword());
    }
}
