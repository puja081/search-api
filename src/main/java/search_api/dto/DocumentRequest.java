package search_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 200, message = "Title must be 2-200 characters")
    private String title;

    private String content;
}
