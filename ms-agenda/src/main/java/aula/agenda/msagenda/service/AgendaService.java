package aula.agenda.msagenda.service;

import aula.agenda.msagenda.dto.AgendaDTO;
import aula.agenda.msagenda.dto.FuncionarioDTO;
import aula.agenda.msagenda.dto.SalaDTO;
import aula.agenda.msagenda.feign.FuncionarioFeign;
import aula.agenda.msagenda.feign.SalaFeign;
import aula.agenda.msagenda.mapper.AgendaMapper;
import aula.agenda.msagenda.model.Agenda;
import aula.agenda.msagenda.repository.AgendaRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository agendaRepository;
    private final FuncionarioFeign funcionarioFeign;
    private final SalaFeign salaFeign;
    private static final String MESSAGE_NOT_FOUND = "id de registro inexistente";


    public AgendaDTO insert(AgendaDTO dto) {
        this.verificarHora(dto);

        Agenda entity = AgendaMapper.fromDTO(dto);
        this.verificarDisponibilidade(entity);
        entity = this.agendaRepository.save(entity);

        dto.setId(entity.getId());

        return dto;
    }

    public AgendaDTO update(Long id, AgendaDTO dto) {
        this.verificarHora(dto);

        this.findModel(id);

        dto.setId(id);
        Agenda entity = AgendaMapper.fromDTO(dto);
        this.verificarDisponibilidade(entity);

        this.agendaRepository.save(entity);
        return dto;
    }

    public AgendaDTO findById(Long id) {
        AgendaDTO dto = AgendaMapper.fromEntity(this.findModel(id));

        this.carregarFuncionario(dto);
        this.carregarSala(dto);

        return dto;
    }

    public Page<AgendaDTO> findAll(Pageable pageable) {
        Page<AgendaDTO> page = AgendaMapper.fromPage(this.agendaRepository.findAll(pageable));

        this.carregarFuncionarios(page);
        this.carregarSalas(page);

        return page;
    }

    public String delete(Long id) {
        try {
            Agenda entity = this.findModel(id);
            this.agendaRepository.delete(entity);
            return "Deletado";
        } catch (EntityNotFoundException e) {
            return "Não existe";
        }
    }

    private void verificarHora(AgendaDTO agendaDTO) {
        try {
            boolean isValid = agendaDTO.getDataHoraReserva().substring(14).equals("00");
            if (!isValid) {
                throw new RuntimeException("data com minutos invalidos");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("data com minutos invalidos");
        }
    }

    private void verificarDisponibilidade(Agenda agenda) {
        Optional<Agenda> optional = this.agendaRepository
                .findAgendaByDataHoraReservaAndSala(agenda.getDataHoraReserva(), agenda.getSala());
        if(optional.isPresent()) {
            throw new RuntimeException("A sala escolhida já está ocupada no horário inserido");
        }


    }

    private Agenda findModel(Long id) {
        return this.agendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found"));
    }

    private void carregarFuncionarios(Page<AgendaDTO> page) {
        Set<Long> funcionariosId = new HashSet<>();

        page.getContent().forEach(agenda -> funcionariosId.add(agenda.getIdResponsavel()));

        ResponseEntity<Set<FuncionarioDTO>> response = this.funcionarioFeign.findByIdList(funcionariosId);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {

            Set<FuncionarioDTO> funcionarios = response.getBody();

            for (AgendaDTO agenda : page.getContent()) {
                for (FuncionarioDTO f : funcionarios) {
                    if (agenda.getIdResponsavel().longValue() == f.getId().longValue()) {
                        agenda.setResponsavel(f.getNome());
                        break;
                    }
                }
                if (agenda.getResponsavel() == null) {
                    agenda.setResponsavel(MESSAGE_NOT_FOUND);
                }
            }
        }
    }

    private void carregarSalas(Page<AgendaDTO> page) {
        Set<Long> salasId = new HashSet<>();

        page.getContent().forEach(agenda -> salasId.add(agenda.getIdSala()));

        ResponseEntity<Set<SalaDTO>> response = this.salaFeign.findByIdList(salasId);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {

            Set<SalaDTO> salas = response.getBody();

            for (AgendaDTO agenda : page.getContent()) {
                for (SalaDTO sala : salas) {
                    if (agenda.getIdSala().longValue() == sala.getId().longValue()) {
                        agenda.setSala(sala.getNome());
                        break;
                    }
                }
                if (agenda.getSala() == null) {
                    agenda.setSala(MESSAGE_NOT_FOUND);
                }
            }
        }
    }

    private void carregarFuncionario(AgendaDTO dto) {
        try {
            ResponseEntity<FuncionarioDTO> response = this.funcionarioFeign.findById(dto.getIdResponsavel());

            FuncionarioDTO funcionarioDTO = response.getBody();

            if (funcionarioDTO != null) {
                dto.setResponsavel(funcionarioDTO.getNome());
            } else {
                dto.setResponsavel(MESSAGE_NOT_FOUND);
            }
        } catch (FeignException e) {
            dto.setResponsavel(MESSAGE_NOT_FOUND);
        }
    }

    private void carregarSala(AgendaDTO dto) {
        try {
            ResponseEntity<SalaDTO> response = this.salaFeign.findById(dto.getIdSala());

            SalaDTO salaDTO = response.getBody();
            if (salaDTO != null) {
                dto.setSala(salaDTO.getNome());
            } else {
                dto.setSala(MESSAGE_NOT_FOUND);
            }
        } catch (FeignException e) {
            dto.setSala(MESSAGE_NOT_FOUND);
        }
    }

}
