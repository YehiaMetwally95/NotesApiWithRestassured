package pojoClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String name;
    private String email;
    private String password;

    @JsonIgnore
    private String id;

}
