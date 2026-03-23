package pe.com.carlosh.animecatalog.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findAllByActiveTrue(Pageable pageable);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
