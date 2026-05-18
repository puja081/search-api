package search_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DocumentRequest {
    private String title;
    private String content;
    private String tag;

    @NotBlank(message = "Title is required")
    @Size(min = 2 ,max = 200, message = "title must be of 2-200 chars")
    public String getTitle() {
        return title;
    }
}
