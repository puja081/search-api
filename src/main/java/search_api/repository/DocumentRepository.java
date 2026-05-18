package search_api.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import search_api.model.Document;



@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT d FROM Document d WHERE " +
            "LOWER(d.title) LIKE LOWER(CONCAT('%',:q,'%')) OR " +
            "LOWER(d.content) LIKE LOWER(CONCAT('%',:q,'%'))")
    Page<Document> searchByKeyword(@Param("q") String keyword, Pageable pageable);

    // Layer 4 — keyword + optional tag filter
    @Query("SELECT d FROM Document d WHERE " +
            "(:q IS NULL OR LOWER(d.title) LIKE LOWER(CONCAT('%',:q,'%')) OR " +
            "LOWER(d.content) LIKE LOWER(CONCAT('%',:q,'%'))) AND " +
            "(:tag IS NULL OR LOWER(d.tag) LIKE LOWER(CONCAT('%',:tag,'%')))")
    Page<Document> searchWithFilters(
            @Param("q") String keyword,
            @Param("tag") String tag,
            Pageable pageable);
}
