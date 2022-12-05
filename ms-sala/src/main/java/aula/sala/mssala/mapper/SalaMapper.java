package aula.sala.mssala.mapper;

import aula.sala.mssala.dto.SalaDTO;
import aula.sala.mssala.model.Sala;

public class SalaMapper {

    public static Sala fromDTO(SalaDTO dto) {
        Sala entity = new Sala();
        entity.setNome(dto.getNome());
        entity.setId(dto.getId());

        return entity;
    }

    public static SalaDTO fromEntity(Sala entity) {
        SalaDTO dto = new SalaDTO();
        dto.setNome(entity.getNome());
        dto.setId(entity.getId());

        return dto;
    }
}
