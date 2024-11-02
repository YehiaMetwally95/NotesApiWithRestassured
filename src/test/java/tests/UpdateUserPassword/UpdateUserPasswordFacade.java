package tests.UpdateUserPassword;

import objectModels.RegisterRequestModel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pojoClasses.LoginRequestPojo;
import yehiaEngine.managers.JsonManager;

import java.io.IOException;

public class UpdateUserPasswordFacade {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/UpdateUserPasswordTestData.json";
    JsonManager json;
    LoginRequestPojo loginRequestObject;

    @Test
    public void updateUserPasswordFacade() throws IOException {
        json = new JsonManager(jsonFilePath);
        loginRequestObject =
                new RegisterRequestModel()
                        .registerNewUserWithRandomData()
                        .navigateToChangePassword()
                        .changeUserPassword();
    }

    @AfterMethod()
    //Update the User Credentials in Json File with the New Password
    public void updateUserCredentialsWithNewPassword() throws IOException {
        json = new JsonManager(jsonFilePath);
        json.setData("UserCredentials2.Email",loginRequestObject.getEmail());
        json.setData("UserCredentials2.Password",loginRequestObject.getPassword());
    }
}
