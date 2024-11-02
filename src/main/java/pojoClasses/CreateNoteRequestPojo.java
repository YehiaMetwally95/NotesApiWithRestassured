package pojoClasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@ToString
@Jacksonized
@Builder
public class CreateNoteRequestPojo {
    //variables
    @JsonProperty("Title") private String title;
    @JsonProperty("Description") private String description;
    @JsonProperty("Category") private String category;
    @JsonProperty ("NoteStatus") private boolean completed;
}
