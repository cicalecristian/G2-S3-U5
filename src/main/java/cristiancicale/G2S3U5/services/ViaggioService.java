package cristiancicale.G2S3U5.services;

import cristiancicale.G2S3U5.entities.StatoViaggio;
import cristiancicale.G2S3U5.entities.Viaggio;
import cristiancicale.G2S3U5.exceptions.NotFoundException;
import cristiancicale.G2S3U5.exceptions.ValidationException;
import cristiancicale.G2S3U5.payloads.ViaggioDTO;
import cristiancicale.G2S3U5.payloads.ViaggioPayload;
import cristiancicale.G2S3U5.repositories.ViaggioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ViaggioService {

    private final ViaggioRepository viaggioRepository;

    public ViaggioService(ViaggioRepository viaggioRepository) {
        this.viaggioRepository = viaggioRepository;
    }

    public Viaggio save(ViaggioDTO body) {

        Viaggio newViaggio = new Viaggio(body.destinazione(), body.data());
        Viaggio savedViaggio = this.viaggioRepository.save(newViaggio);

        log.info("Il viaggio con id " + savedViaggio.getId() + "è stato salvato correttamente");

        return savedViaggio;
    }

    public Page<Viaggio> findAll(int page, int size, String sortBy) {
        if (size > 100 || size < 0) size = 10;
        if (page < 10) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.viaggioRepository.findAll(pageable);
    }

    public Viaggio findById(UUID viaggioId) {
        return this.viaggioRepository.findById(viaggioId).orElseThrow(() -> new NotFoundException(viaggioId));
    }

    public Viaggio completaViaggio(UUID viaggioId) {

        Viaggio found = this.findById(viaggioId);

        if (found.getStatoViaggio() == StatoViaggio.COMPLETATO) {
            throw new ValidationException(
                    List.of("Il viaggio è già completato")
            );
        }

        found.setStatoViaggio(StatoViaggio.COMPLETATO);

        Viaggio viaggioUpdated = this.viaggioRepository.save(found);

        log.info("Il viaggio con id " + viaggioUpdated.getId() + " è stato completato");

        return viaggioUpdated;
    }

    public Viaggio findByIdAndUpdate(UUID viaggioId, ViaggioPayload body) {
        Viaggio found = this.findById(viaggioId);

        found.setDestinazione(body.getDestinazione());
        found.setData(body.getData());

        Viaggio updateViaggio = this.viaggioRepository.save(found);

        log.info("Il viaggio con id " + updateViaggio.getId() + "è stato aggiornato correttamente");

        return updateViaggio;
    }

    public void findByIdAndDelete(UUID viaggioId) {
        Viaggio found = this.findById(viaggioId);
        this.viaggioRepository.delete(found);
    }
}
