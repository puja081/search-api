package search_api.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import search_api.dto.DocumentRequest;
import search_api.dto.DocumentResponse;
import search_api.model.Document;
import search_api.repository.DocumentRepository;

@Service
@Transactional
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public DocumentResponse create(DocumentRequest documentRequest){
        Document document = new Document();
        document.setTitle(documentRequest.getTitle());
        document.setTag(documentRequest.getTag());
        document.setContent(documentRequest.getContent());
        document.setTag(documentRequest.getTag());
        return toResponse(documentRepository.save(document));

    }

    private DocumentResponse toResponse(Document document){
        return new DocumentResponse(document.getId(), document.getTitle(),
                document.getContent(), document.getTag(), document.getCreatedAt() );
    }

}
