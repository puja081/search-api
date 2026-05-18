package search_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class DocumentResponse {

    private Long id;
    private String title;
    private String content;
    private String tags;
    private LocalDateTime createdAt;

    public DocumentResponse() {}
    public DocumentResponse(Long id, String title, String content, String tags, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.createdAt = createdAt;
    }


}
