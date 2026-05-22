package search_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import search_api.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
