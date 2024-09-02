package pojoClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateNoteRequestPojo {
    //variables
    private String title;
    private String description;
    private String category;

    @JsonIgnore
    private String id;
}
