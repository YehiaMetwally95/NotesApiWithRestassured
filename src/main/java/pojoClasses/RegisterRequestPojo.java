package pojoClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import objectModels.RegisterRequestModel;

@Getter
@Setter
@ToString
@Jacksonized
@Builder
public class RegisterRequestPojo {
    //variables
    @JsonProperty("Name") private String name;
    @JsonProperty("Email") private String email;
    @JsonProperty("Password") private String password;
}
