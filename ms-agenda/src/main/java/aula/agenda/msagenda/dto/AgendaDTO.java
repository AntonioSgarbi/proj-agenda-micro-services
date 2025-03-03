package aula.agenda.msagenda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaDTO {
    private Long id;
    private Long idResponsavel;
    private String responsavel;
    private Long idSala;
    private String sala;
    private String dataHoraReserva;
}
