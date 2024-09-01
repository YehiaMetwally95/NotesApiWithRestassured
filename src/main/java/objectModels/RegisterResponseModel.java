package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.Assert;
import pojoClasses.RegisterRequestPojo;
import pojoClasses.RegisterResponsePojo;

public class RegisterResponseModel {

    RegisterRequestPojo requestObject;
    RegisterResponsePojo responseObject;

    boolean expectedSuccess = true;
    int expectedStatus = 201;
    String expectedMassage = "User account created successfully";

    public RegisterResponseModel(RegisterRequestPojo requestObject , RegisterResponsePojo responseObject) {
        this.requestObject = requestObject;
        this.responseObject = responseObject;
    }

    public RegisterResponseModel validateMassageFromResponse() throws JsonProcessingException {
        Assert.assertEquals(responseObject.getMessage(),expectedMassage);
        return this;
    }

    public RegisterResponseModel validateStatusFromResponse() throws JsonProcessingException {
        Assert.assertEquals(responseObject.getStatus(),expectedStatus);
        return this;
    }

    public RegisterResponseModel validateSuccessFromResponse() throws JsonProcessingException {
        Assert.assertEquals(responseObject.isSuccess(),expectedSuccess);
        return this;
    }

    public RegisterResponseModel validateNameFromResponse() throws JsonProcessingException {
        Assert.assertEquals(responseObject.getData().getName(),requestObject.getName());
        return this;
    }

    public RegisterResponseModel validateEmailFromResponse() throws JsonProcessingException {
        Assert.assertEquals(responseObject.getData().getEmail(),requestObject.getEmail());
        return this;
    }

    public String getUserIDFromResponse() throws JsonProcessingException {
        return responseObject.getData().getId();
    }


}
