package cristiancicale.G2S3U5.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DipendentePayload {

    private String username;
    private String nome;
    private String cognome;
    private String email;
}
