package tests.CreateNewUser;

import objectModels.RegisterRequestModel;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import yehiaEngine.managers.JsonManager;

import java.io.IOException;

public class CreateNewUserFacade {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/CreateNewUserTestData.json";
    JsonManager json;

    @Test
    public void createNewUserWithDynamicRandomDataFacade() throws IOException {
        json = new JsonManager(jsonFilePath);
        new RegisterRequestModel()
                .registerNewUserWithRandomData();
    }
}
