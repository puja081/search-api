package search_api.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import search_api.dto.DocumentRequest;
import search_api.dto.DocumentResponse;
import search_api.exception.ResourceNotFoundException;
import search_api.model.Document;
import search_api.repository.DocumentRepository;

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
        document.setTag(request.getTag());
        return toResponse(documentRepository.save(document));
    }

    public DocumentResponse getById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
        return toResponse(document);
    }

    public Page<DocumentResponse> getAll(Pageable pageable) {
        return documentRepository.findAll(pageable).map(this::toResponse);
    }

    public DocumentResponse update(Long id, DocumentRequest request) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
        document.setTitle(request.getTitle());
        document.setContent(request.getContent());
        document.setTag(request.getTag());
        return toResponse(documentRepository.save(document));
    }

    public void delete(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Document not found with id: " + id);
        }
        documentRepository.deleteById(id);
    }

    public Page<DocumentResponse> search(String keyword, Pageable pageable) {
        return documentRepository.searchByKeyword(keyword, pageable).map(this::toResponse);
    }

    private DocumentResponse toResponse(Document document) {
        return new DocumentResponse(
                document.getId(),
                document.getTitle(),
                document.getContent(),
                document.getTag(),
                document.getCreatedAt(),
                document.getUpdatedAt()
        );
    }
}
