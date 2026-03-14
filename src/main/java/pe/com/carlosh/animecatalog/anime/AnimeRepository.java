package pe.com.carlosh.animecatalog.anime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    Page<Anime> findByActiveTrue(Pageable pageable);
    Optional<Anime> findByIdAndActiveTrue(Long id);
    Page<Anime> findByNameContainingIgnoreCaseAndActiveTrue(String name, Pageable pageable);
    boolean existsByNameIgnoreCase(String name);
}