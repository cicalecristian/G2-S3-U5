package cristiancicale.G2S3U5.services;

import cristiancicale.G2S3U5.entities.Dipendente;
import cristiancicale.G2S3U5.exceptions.NotFoundException;
import cristiancicale.G2S3U5.exceptions.UnauthorizedException;
import cristiancicale.G2S3U5.payloads.LoginDTO;
import cristiancicale.G2S3U5.security.TokenTools;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final DipendenteService dipendenteService;
    private final TokenTools tokenTools;

    public AuthService(DipendenteService dipendenteService, TokenTools tokenTools) {

        this.dipendenteService = dipendenteService;
        this.tokenTools = tokenTools;
    }

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        try {
            Dipendente found = this.dipendenteService.findByEmail(body.email());
            if (found.getUsername().equals(body.password())) {
                return this.tokenTools.generateToken(found);
            } else {
                throw new UnauthorizedException("Credenziali errate");
            }
        } catch (NotFoundException ex) {
            throw new UnauthorizedException("Credenziali errate");
        }
    }
}
