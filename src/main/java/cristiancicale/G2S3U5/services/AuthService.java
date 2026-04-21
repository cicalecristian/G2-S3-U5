package cristiancicale.G2S3U5.services;

import cristiancicale.G2S3U5.entities.Dipendente;
import cristiancicale.G2S3U5.exceptions.NotFoundException;
import cristiancicale.G2S3U5.exceptions.UnauthorizedException;
import cristiancicale.G2S3U5.payloads.LoginDTO;
import cristiancicale.G2S3U5.security.TokenTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final DipendenteService dipendenteService;
    private final TokenTools tokenTools;
    private final PasswordEncoder bcrypt;

    public AuthService(DipendenteService dipendenteService, TokenTools tokenTools, PasswordEncoder bcrypt) {

        this.dipendenteService = dipendenteService;
        this.tokenTools = tokenTools;
        this.bcrypt = bcrypt;
    }

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        try {
            Dipendente found = this.dipendenteService.findByEmail(body.email());
            if (this.bcrypt.matches(body.password(), found.getPassword())) {
                return this.tokenTools.generateToken(found);
            } else {
                throw new UnauthorizedException("Credenziali errate");
            }
        } catch (NotFoundException ex) {
            throw new UnauthorizedException("Credenziali errate");
        }
    }
}
