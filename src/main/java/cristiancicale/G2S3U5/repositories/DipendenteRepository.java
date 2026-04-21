package cristiancicale.G2S3U5.repositories;

import cristiancicale.G2S3U5.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, UUID> {
    boolean existsByUsername(String email);

    boolean existsByEmail(String email);

    Optional<Dipendente> findByEmail(String email);
}
