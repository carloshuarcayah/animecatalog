package pe.com.carlosh.animecatalog.studio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudioRepository extends JpaRepository<Studio, Long> {

    Page<Studio> findByActiveTrue(Pageable pageable);

    Optional<Studio> findByIdAndActiveTrue(Long id);

    Page<Studio> findByNameContainingIgnoreCaseAndActiveTrue(String name, Pageable pageable);

    boolean existsByNameIgnoreCase(String name);
}
