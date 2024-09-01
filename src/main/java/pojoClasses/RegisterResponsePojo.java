package pojoClasses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponsePojo {
    //variables
    private boolean success;
    private int status;
    private String message;
    private Data data;

    @Getter
    @Setter
    public class Data {
        private String id;
        private String name;
        private String email;
    }

}

