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
    private String tag;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DocumentResponse() {}

    public DocumentResponse(Long id, String title, String content, String tag,
                            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
