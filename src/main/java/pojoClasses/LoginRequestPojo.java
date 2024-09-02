package pojoClasses;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class LoginRequestPojo {
    //variables
    private String name;
    private String email;
    private String password;
}
