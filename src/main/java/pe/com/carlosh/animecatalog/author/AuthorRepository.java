package pe.com.carlosh.animecatalog.author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Page<Author> findByActiveTrue(Pageable pageable);
    Optional<Author> findByIdAndActiveTrue(Long id);

    @Query("SELECT a FROM Author a WHERE a.active = true AND " +
            "LOWER(CONCAT(a.firstName, ' ', a.lastName)) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Author> searchByName(@Param("name") String name, Pageable pageable);

    //verify that this author name does not exist already
    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}
