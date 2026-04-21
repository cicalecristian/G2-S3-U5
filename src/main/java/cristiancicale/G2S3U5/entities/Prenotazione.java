package cristiancicale.G2S3U5.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prenotazioni",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"dipendente_id", "data_viaggio"}))
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Prenotazione {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "data_richiesta", nullable = false)
    private LocalDate dataRichiesta;

    @Column(length = 500)
    private String note;

    @Column(name = "data_viaggio", nullable = false)
    private LocalDate dataViaggio;

    @ManyToOne
    @JoinColumn(name = "dipendente_id", nullable = false)
    private Dipendente dipendente;

    @ManyToOne
    @JoinColumn(name = "viaggio_id", nullable = false)
    private Viaggio viaggio;

    public Prenotazione(Dipendente dipendente, Viaggio viaggio, String note) {
        this.dipendente = dipendente;
        this.viaggio = viaggio;
        this.note = note;
    }
}
