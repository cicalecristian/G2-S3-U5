package cristiancicale.G2S3U5.controllers;

import cristiancicale.G2S3U5.entities.Dipendente;
import cristiancicale.G2S3U5.exceptions.ValidationException;
import cristiancicale.G2S3U5.payloads.DipendenteDTO;
import cristiancicale.G2S3U5.payloads.DipendenteRespDTO;
import cristiancicale.G2S3U5.payloads.LoginDTO;
import cristiancicale.G2S3U5.payloads.LoginRespDTO;
import cristiancicale.G2S3U5.services.AuthService;
import cristiancicale.G2S3U5.services.DipendenteService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final DipendenteService dipendenteService;

    public AuthController(AuthService authService, DipendenteService dipendenteService) {

        this.authService = authService;
        this.dipendenteService = dipendenteService;
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        return new LoginRespDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public DipendenteRespDTO saveUser(@RequestBody @Validated DipendenteDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }

        Dipendente newDipendente = this.dipendenteService.save(body);
        return new DipendenteRespDTO(newDipendente.getId());
    }
}
