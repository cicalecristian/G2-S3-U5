package cristiancicale.G2S3U5.repositories;

import cristiancicale.G2S3U5.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {

    List<Prenotazione> findByDipendenteId(UUID dipendenteId);

    boolean existsByDipendenteIdAndDataViaggio(UUID dipendenteId, LocalDate dataViaggio);

    boolean existsByDipendenteIdAndDataViaggio(UUID dipendenteId, LocalDate dataViaggio, UUID id);
}
