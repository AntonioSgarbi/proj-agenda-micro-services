package aula.agenda.msagenda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long responsavel;
    @Column(nullable = false)
    private Long sala;
    /* Reservas sempre serão de 1 hora e incia em hora cheia ex: 11hs, 10hs, 12hs, etc.*/
    @Column(nullable = false)
    private LocalDateTime dataHoraReserva;

}
