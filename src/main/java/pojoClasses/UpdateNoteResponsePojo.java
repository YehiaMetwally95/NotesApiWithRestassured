package pojoClasses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateNoteResponsePojo {
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
        private String title;
        private String description;
        private boolean completed;
        private String created_at;
        private String updated_at;
        private String category;
        private String user_id;
    }
}
