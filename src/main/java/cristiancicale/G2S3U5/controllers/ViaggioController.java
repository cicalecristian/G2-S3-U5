package cristiancicale.G2S3U5.controllers;

import cristiancicale.G2S3U5.entities.Viaggio;
import cristiancicale.G2S3U5.exceptions.ValidationException;
import cristiancicale.G2S3U5.payloads.ViaggioDTO;
import cristiancicale.G2S3U5.payloads.ViaggioPayload;
import cristiancicale.G2S3U5.payloads.ViaggioRespDTO;
import cristiancicale.G2S3U5.services.ViaggioService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {

    private final ViaggioService viaggioService;

    public ViaggioController(ViaggioService viaggioService) {
        this.viaggioService = viaggioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ViaggioRespDTO saveViaggio(@RequestBody @Validated ViaggioDTO body, BindingResult validationResult) {


        if (validationResult.hasErrors()) {

            List<String> errors = validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            throw new ValidationException(errors);
        }

        Viaggio newViaggio = this.viaggioService.save(body);
        return new ViaggioRespDTO(newViaggio.getId());
    }

    @GetMapping
    public Page<Viaggio> getViaggi(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "destinazione") String sortBy) {
        return this.viaggioService.findAll(page, size, sortBy);
    }

    @GetMapping("/{viaggioId}")
    public Viaggio getById(@PathVariable UUID viaggioId) {
        return this.viaggioService.findById(viaggioId);
    }

    @PatchMapping("/{viaggioId}/completa")
    public Viaggio completaViaggio(@PathVariable UUID viaggioId) {
        return viaggioService.completaViaggio(viaggioId);
    }

    @PutMapping("/{viaggioId}")
    public Viaggio getByIdAndUpdate(@PathVariable UUID viaggioId, @RequestBody ViaggioPayload body) {
        return this.viaggioService.findByIdAndUpdate(viaggioId, body);
    }

    @DeleteMapping("/{viaggioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void getByIdAndDelete(@PathVariable UUID viaggioId) {
        this.viaggioService.findByIdAndDelete(viaggioId);
    }
}
