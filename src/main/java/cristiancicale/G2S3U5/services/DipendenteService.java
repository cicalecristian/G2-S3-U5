package cristiancicale.G2S3U5.services;

import cristiancicale.G2S3U5.entities.Dipendente;
import cristiancicale.G2S3U5.exceptions.BadRequestException;
import cristiancicale.G2S3U5.exceptions.NotFoundException;
import cristiancicale.G2S3U5.payloads.DipendenteDTO;
import cristiancicale.G2S3U5.payloads.DipendentePayload;
import cristiancicale.G2S3U5.repositories.DipendenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class DipendenteService {

    private final DipendenteRepository dipendenteRepository;

    public DipendenteService(DipendenteRepository dipendenteRepository) {
        this.dipendenteRepository = dipendenteRepository;
    }

    public Dipendente save(DipendenteDTO body) {
        if (this.dipendenteRepository.existsByEmail(body.email()))
            throw new BadRequestException("L'indirizzo email " + body.email() + "è già in uso");

        Dipendente newDipendente = new Dipendente(body.username(), body.nome(), body.cognome(), body.email());
        Dipendente savedDipendente = this.dipendenteRepository.save(newDipendente);

        log.info("Il dipendente con id " + savedDipendente.getId() + "è stato salvato correttamente");

        return savedDipendente;
    }

    public Page<Dipendente> findAll(int page, int size, String sortBy) {
        if (size > 100 || size < 0) size = 10;
        if (page < 10) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.dipendenteRepository.findAll(pageable);
    }

    public Dipendente findById(UUID dipendenteId) {
        return this.dipendenteRepository.findById(dipendenteId).orElseThrow(() -> new NotFoundException(dipendenteId));
    }

    public Dipendente findByIdAndUpdate(UUID dipendenteId, DipendentePayload body) {
        Dipendente found = this.findById(dipendenteId);

        if (!found.getEmail().equals(body.getEmail())) {

            if (this.dipendenteRepository.existsByUsername(body.getEmail()))
                throw new BadRequestException("L'username" + body.getUsername() + "è gia in uso");

            if (this.dipendenteRepository.existsByEmail(body.getEmail()))
                throw new BadRequestException("L'indirizzo email" + body.getEmail() + "è gia in uso");
        }

        found.setUsername(body.getUsername());
        found.setNome(body.getNome());
        found.setCognome(body.getCognome());
        found.setEmail(body.getEmail());
        found.setAvatar("https://ui-avatars.com/api/?name=" + body.getNome() + "+" + body.getCognome());

        Dipendente updateDipendente = this.dipendenteRepository.save(found);

        log.info("Il dipendente " + updateDipendente.getId() + "è stato aggiornato correttamente");

        return updateDipendente;
    }

    public void findByIdAndDelete(UUID dipendenteId) {
        Dipendente found = this.findById(dipendenteId);
        this.dipendenteRepository.delete(found);
    }

    public Dipendente findByEmail(String email) {
        return this.dipendenteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato!"));
    }
}
