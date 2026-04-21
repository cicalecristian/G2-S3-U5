package cristiancicale.G2S3U5.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PrenotazionePayload {

    private String note;
    private UUID dipendenteId;
    private UUID viaggioId;
}
