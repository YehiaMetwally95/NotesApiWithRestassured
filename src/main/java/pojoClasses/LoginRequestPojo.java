package pojoClasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class LoginRequestPojo {
    //variables
    private String email;
    private String password;
}
