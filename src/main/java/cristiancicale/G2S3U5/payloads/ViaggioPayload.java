package cristiancicale.G2S3U5.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ViaggioPayload {

    private String destinazione;
    private LocalDate data;
}
