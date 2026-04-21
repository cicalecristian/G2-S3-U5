package cristiancicale.G2S3U5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DipendenteDTO(

        @NotBlank(message = "Lo username è obbligatorio e non può essere vuoto")
        @Size(min = 3, max = 30, message = "Lo username deve essere tra 3 e 30 caratteri")
        String username,

        @NotBlank(message = "Il nome è obbligatorio e non può essere vuoto")
        @Size(min = 2, max = 30, message = "Il nome deve essere tra 2 e 30 caratteri")
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio e non può essere vuoto")
        @Size(min = 2, max = 30, message = "Il cognome deve essere tra 2 e 30 caratteri")
        String cognome,

        @NotBlank(message = "L'email è obbligatoria e non può essere vuota")
        @Email(message = "Formato email non valido")
        String email,

        @NotBlank(message = "La password è obbligatoria")
        @Size(min = 4, message = "La password deve avere almeno 4 caratteri")
        String password
) {
}
