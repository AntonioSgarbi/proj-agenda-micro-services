package aula.sala.mssala.service;

import aula.sala.mssala.dto.SalaDTO;
import aula.sala.mssala.mapper.SalaMapper;
import aula.sala.mssala.model.Sala;
import aula.sala.mssala.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaService {
    private final SalaRepository salaRepository;


    public Page<SalaDTO> findAll(Pageable pageable) {
        return salaRepository.findAll(pageable).map(SalaMapper::fromEntity);
    }

    public SalaDTO findById(Long id) {
        return SalaMapper.fromEntity(this.findModel(id));
    }

    public SalaDTO save(SalaDTO dto) {
        Sala entity = SalaMapper.fromDTO(dto);

        entity = this.salaRepository.save(entity);
        dto.setId(entity.getId());

        return dto;
    }

    public SalaDTO update(Long id, SalaDTO dto) {
        Sala entity = this.findModel(id);

        entity.setNome(dto.getNome());
        this.salaRepository.save(entity);

        return dto;
    }

    public String delete(Long id) {
        try {
            Sala entity = this.findModel(id);
            this.salaRepository.delete(entity);
            return "Deletado";
        } catch (EntityNotFoundException e) {
            return "não existe";
        }
    }

    private Sala findModel(Long id) {
        return this.salaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found"));

    }

    public Set<SalaDTO> findBySet(Set<Long> set) {
        return this.salaRepository.findAllById(set)
                .stream().map(SalaMapper::fromEntity)
                .collect(Collectors.toSet());
    }
}
