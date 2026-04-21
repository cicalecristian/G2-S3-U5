package cristiancicale.G2S3U5.services;

import cristiancicale.G2S3U5.entities.Dipendente;
import cristiancicale.G2S3U5.entities.Prenotazione;
import cristiancicale.G2S3U5.entities.Viaggio;
import cristiancicale.G2S3U5.exceptions.NotFoundException;
import cristiancicale.G2S3U5.exceptions.ValidationException;
import cristiancicale.G2S3U5.payloads.PrenotazioneDTO;
import cristiancicale.G2S3U5.payloads.PrenotazionePayload;
import cristiancicale.G2S3U5.repositories.PrenotazioneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final DipendenteService dipendenteService;
    private final ViaggioService viaggioService;


    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, DipendenteService dipendenteService, ViaggioService viaggioService) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.dipendenteService = dipendenteService;
        this.viaggioService = viaggioService;
    }

    public Prenotazione save(PrenotazioneDTO body) {

        Dipendente dipendente = dipendenteService.findById(body.dipendenteId());

        Viaggio viaggio = viaggioService.findById(body.viaggioId());

        LocalDate dataViaggio = viaggio.getData();

        boolean exists = prenotazioneRepository
                .existsByDipendenteIdAndDataViaggio(dipendente.getId(), dataViaggio);

        if (exists) {
            throw new ValidationException(
                    List.of("Hai già una prenotazione per questa data")
            );
        }

        Prenotazione newPrenotazione = new Prenotazione(dipendente, viaggio, body.note());

        newPrenotazione.setDataRichiesta(LocalDate.now());
        newPrenotazione.setDataViaggio(dataViaggio);

        Prenotazione savedPrenotazione = this.prenotazioneRepository.save(newPrenotazione);

        log.info("La prenotazione con id " + savedPrenotazione.getId() + "è stata salvata correttamente");

        return savedPrenotazione;
    }

    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        if (size > 100 || size < 0) size = 10;
        if (page < 10) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione findById(UUID prenotazioneId) {
        return this.prenotazioneRepository.findById(prenotazioneId).orElseThrow(() -> new NotFoundException(prenotazioneId));
    }

    public List<Prenotazione> findPrenotazioniByDipendente(UUID dipendenteId) {

        Dipendente dipendente = dipendenteService.findById(dipendenteId);

        return prenotazioneRepository.findByDipendenteId(dipendente.getId());
    }

    public Prenotazione findByIdAndUpdate(UUID id, PrenotazionePayload payload) {

        Prenotazione found = this.findById(id);

        Dipendente dipendente = dipendenteService.findById(payload.getDipendenteId());
        Viaggio viaggio = viaggioService.findById(payload.getViaggioId());

        LocalDate dataViaggio = viaggio.getData();

        boolean exists = prenotazioneRepository
                .existsByDipendenteIdAndDataViaggio(
                        dipendente.getId(),
                        dataViaggio,
                        found.getId()
                );

        if (exists) {
            throw new ValidationException(
                    List.of("Hai già una prenotazione per questa data")
            );
        }

        found.setDipendente(dipendente);
        found.setViaggio(viaggio);
        found.setNote(payload.getNote());
        found.setDataViaggio(dataViaggio);

        return prenotazioneRepository.save(found);
    }

    public void findByIdAndDelete(UUID id) {
        Prenotazione found = this.findById(id);
        prenotazioneRepository.delete(found);
    }
}