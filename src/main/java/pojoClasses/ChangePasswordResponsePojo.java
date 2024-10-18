package pojoClasses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangePasswordResponsePojo {
    //variables
    private boolean success;
    private int status;
    private String message;
}
