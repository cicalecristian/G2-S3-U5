package cristiancicale.G2S3U5.controllers;

import cristiancicale.G2S3U5.entities.Dipendente;
import cristiancicale.G2S3U5.exceptions.ValidationException;
import cristiancicale.G2S3U5.payloads.DipendenteDTO;
import cristiancicale.G2S3U5.payloads.DipendentePayload;
import cristiancicale.G2S3U5.payloads.DipendenteRespDTO;
import cristiancicale.G2S3U5.services.DipendenteService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    private final DipendenteService dipendenteService;

    public DipendenteController(DipendenteService dipendenteService) {
        this.dipendenteService = dipendenteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public DipendenteRespDTO saveDipendente(@RequestBody @Validated DipendenteDTO body, BindingResult validationResult) {


        if (validationResult.hasErrors()) {

            List<String> errors = validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            throw new ValidationException(errors);
        }

        Dipendente newDipendente = this.dipendenteService.save(body);
        return new DipendenteRespDTO(newDipendente.getId());
    }

    @GetMapping
    public Page<Dipendente> getDipendenti(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "username") String sortBy) {
        return this.dipendenteService.findAll(page, size, sortBy);
    }

    @GetMapping("/{dipendenteId}")
    public Dipendente getById(@PathVariable UUID dipendenteId) {
        return this.dipendenteService.findById(dipendenteId);
    }

    @PutMapping("/{dipendenteId}")
    public Dipendente getByIdAndUpdate(@PathVariable UUID dipendenteId, @RequestBody DipendentePayload body) {
        return this.dipendenteService.findByIdAndUpdate(dipendenteId, body);
    }

    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void getByIdAndDelete(@PathVariable UUID dipendenteId) {
        this.dipendenteService.findByIdAndDelete(dipendenteId);
    }
}
