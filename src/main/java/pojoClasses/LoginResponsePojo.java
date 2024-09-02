package pojoClasses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginResponsePojo {
    //variables
    private boolean success;
    private int status;
    private String message;
    private Data data;

    @Getter
    @Setter
    @ToString
    public class Data {
        private String id;
        private String name;
        private String email;
        private String token;
    }
}
