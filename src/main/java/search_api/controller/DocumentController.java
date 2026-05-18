package search_api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import search_api.dto.DocumentRequest;
import search_api.dto.DocumentResponse;
import search_api.service.DocumentService;
import search_api.service.SearchTracker;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class DocumentController {

    private final DocumentService documentService;
    private final SearchTracker searchTracker;

    public DocumentController(DocumentService documentService, SearchTracker searchTracker) {
        this.documentService = documentService;
        this.searchTracker = searchTracker;
    }

    @PostMapping("/documents")
    public ResponseEntity<DocumentResponse> create(@Valid @RequestBody DocumentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.create(request));
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<DocumentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getById(id));
    }

    @GetMapping("/documents")
    public ResponseEntity<Page<DocumentResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(documentService.getAll(pageable));
    }

    @PutMapping("/documents/{id}")
    public ResponseEntity<DocumentResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody DocumentRequest request) {
        return ResponseEntity.ok(documentService.update(id, request));
    }

    @DeleteMapping("/documents/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        documentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/top")
    public ResponseEntity<List<Map<String, Object>>> topSearches(
            @RequestParam(defaultValue = "10") int k) {
        return ResponseEntity.ok(searchTracker.getTopK(k));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DocumentResponse>> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(documentService.search(q, tag, pageable));
    }
}
