package cristiancicale.G2S3U5.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PrenotazioneDTO(

        @Size(max = 500, message = "Le note non possono superare 500 caratteri")
        String note,

        @NotNull(message = "Il dipendente è obbligatorio")
        UUID dipendenteId,

        @NotNull(message = "Il viaggio è obbligatorio")
        UUID viaggioId
) {
}
