package tests.CreateNewUser;

import objectModels.RegisterRequestModel;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.JsonManager;

import java.io.IOException;

@Listeners(utils.TestNGListners.class)
public class CreateNewUserFacade {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/CreateNewUserTestData.json";
    JsonManager json;

    @Test
    public void createNewUserWithDynamicRandomDataFacade() throws IOException, ParseException {
        json = new JsonManager(jsonFilePath);
        new RegisterRequestModel()
                .registerNewUserWithRandomData();
    }
}
