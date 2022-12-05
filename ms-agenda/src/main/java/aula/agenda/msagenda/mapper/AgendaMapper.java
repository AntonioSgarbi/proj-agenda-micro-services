package aula.agenda.msagenda.mapper;

import aula.agenda.msagenda.dto.AgendaDTO;
import aula.agenda.msagenda.model.Agenda;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AgendaMapper {

    private static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static Agenda fromDTO(AgendaDTO dto) {
        Agenda agenda = new Agenda();

        agenda.setId(dto.getId());
        agenda.setSala(dto.getIdSala());
        agenda.setResponsavel(dto.getIdResponsavel());
        agenda.setDataHoraReserva(LocalDateTime.parse(dto.getDataHoraReserva(), FORMATO_DATA_HORA));

        return agenda;
    }

    public static AgendaDTO fromEntity(Agenda entity) {
        AgendaDTO dto = new AgendaDTO();

        dto.setId(entity.getId());
        dto.setIdSala(entity.getSala());
        dto.setIdResponsavel(entity.getResponsavel());
        dto.setDataHoraReserva(entity.getDataHoraReserva().format(FORMATO_DATA_HORA));

        return dto;
    }

    public static Page<AgendaDTO> fromPage(Page<Agenda> pageEntity) {
        return pageEntity.map(AgendaMapper::fromEntity);
    }

}
