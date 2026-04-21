package cristiancicale.G2S3U5.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "viaggi")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Viaggio {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String destinazione;

    @Column(nullable = false)
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "stato_viaggio")
    private StatoViaggio statoViaggio = StatoViaggio.IN_PROGRAMMA;

    public Viaggio(String destinazione, LocalDate data) {
        this.destinazione = destinazione;
        this.data = data;
    }
}