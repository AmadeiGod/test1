package doc.ex.test.Repository;


import doc.ex.test.models.DocTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RepositoryDoc extends JpaRepository<DocTest, Long> {
    @Query(value = "select * from doc_test s where s.name like %:keyword% or s.content_type like %:keyword%", nativeQuery = true)
    List<DocTest> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
    DocTest findByName(String name);
}
