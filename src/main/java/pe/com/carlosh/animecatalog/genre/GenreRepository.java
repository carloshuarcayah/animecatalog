package pe.com.carlosh.animecatalog.genre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface GenreRepository extends JpaRepository<Genre, Long> {
    Page<Genre> findByActiveTrue(Pageable pageable);
    Optional<Genre> findByIdAndActiveTrue(Long id);
    Page<Genre> findByNameContainingIgnoreCase(String name, Pageable pageable);
    boolean existsByNameIgnoreCase(String name);
}