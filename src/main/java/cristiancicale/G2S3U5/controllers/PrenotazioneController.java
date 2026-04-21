package cristiancicale.G2S3U5.controllers;

import cristiancicale.G2S3U5.entities.Prenotazione;
import cristiancicale.G2S3U5.exceptions.ValidationException;
import cristiancicale.G2S3U5.payloads.PrenotazioneDTO;
import cristiancicale.G2S3U5.payloads.PrenotazionePayload;
import cristiancicale.G2S3U5.payloads.PrenotazioneRespDTO;
import cristiancicale.G2S3U5.services.PrenotazioneService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PrenotazioneRespDTO savePrenotazione(@RequestBody @Validated PrenotazioneDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {

            List<String> errors = validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

            throw new ValidationException(errors);
        }

        Prenotazione newPrenotazione = this.prenotazioneService.save(body);
        return new PrenotazioneRespDTO(newPrenotazione.getId());
    }

    @GetMapping
    public Page<Prenotazione> getPrenotazioni(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "dataViaggio") String sortBy) {
        return prenotazioneService.findAll(page, size, sortBy);
    }

    @GetMapping("/{prenotazioneId}")
    public Prenotazione getById(@PathVariable UUID prenotazioneId) {
        return prenotazioneService.findById(prenotazioneId);
    }

    @GetMapping("/dipendente/{dipendenteId}")
    public List<Prenotazione> getByDipendente(@PathVariable UUID dipendenteId) {
        return prenotazioneService.findPrenotazioniByDipendente(dipendenteId);
    }

    @PutMapping("/{prenotazioneId}")
    public Prenotazione updatePrenotazione(@PathVariable UUID prenotazioneId,
                                           @RequestBody @Validated PrenotazionePayload body,
                                           BindingResult validationResult) {

        if (validationResult.hasErrors()) {

            List<String> errors = validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

            throw new ValidationException(errors);
        }

        return prenotazioneService.findByIdAndUpdate(prenotazioneId, body);
    }

    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrenotazione(@PathVariable UUID prenotazioneId) {
        prenotazioneService.findByIdAndDelete(prenotazioneId);
    }
}
