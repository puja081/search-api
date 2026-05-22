package search_api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import search_api.dto.DocumentRequest;
import search_api.dto.DocumentResponse;
import search_api.exception.ResourceNotFoundException;
import search_api.model.Document;
import search_api.repository.DocumentRepository;

import java.util.List;

@Service
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public DocumentResponse create(DocumentRequest request) {
        Document document = new Document();
        document.setTitle(request.getTitle());
        document.setContent(request.getContent());
        return toResponse(documentRepository.save(document));
    }

    public DocumentResponse getById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
        return toResponse(document);
    }

    public List<DocumentResponse> getAll() {
        return documentRepository.findAll().stream().map(this::toResponse).toList();
    }

    public DocumentResponse update(Long id, DocumentRequest request) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
        document.setTitle(request.getTitle());
        document.setContent(request.getContent());
        return toResponse(documentRepository.save(document));
    }

    public void delete(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Document not found with id: " + id);
        }
        documentRepository.deleteById(id);
    }

    private DocumentResponse toResponse(Document document) {
        return new DocumentResponse(
                document.getId(),
                document.getTitle(),
                document.getContent(),
                document.getCreatedAt(),
                document.getUpdatedAt()
        );
    }
}
