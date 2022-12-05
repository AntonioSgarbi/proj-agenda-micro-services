package aula.funcionario.msfuncionario.service;

import aula.funcionario.msfuncionario.dto.FuncionarioDTO;
import aula.funcionario.msfuncionario.mapper.FuncionarioMapper;
import aula.funcionario.msfuncionario.model.Funcionario;
import aula.funcionario.msfuncionario.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public Page<FuncionarioDTO> findAll(Pageable pageable) {
        return funcionarioRepository.findAll(pageable).map(FuncionarioMapper::fromEntity);
    }

    public FuncionarioDTO findById(Long id) {
        return FuncionarioMapper.fromEntity(this.findModel(id));
    }

    public FuncionarioDTO save(FuncionarioDTO dto) {
        Funcionario entity = FuncionarioMapper.fromDTO(dto);

        entity = this.funcionarioRepository.save(entity);
        dto.setId(entity.getId());

        return dto;
    }

    public FuncionarioDTO update(Long id, FuncionarioDTO dto) {
        this.findModel(id);

        dto.setId(id);
        Funcionario entity = FuncionarioMapper.fromDTO(dto);

        this.funcionarioRepository.save(entity);
        return dto;
    }

    public String delete(Long id) {
        try {
            Funcionario entity = this.findModel(id);
            this.funcionarioRepository.delete(entity);
            return "Deletado";
        } catch (EntityNotFoundException e) {
            return "não existe";
        }
    }

    private Funcionario findModel(Long id) {
        return this.funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found"));

    }

    public Set<FuncionarioDTO> findBySet(Set<Long> set) {
        return this.funcionarioRepository.findAllById(set)
                .stream().map(FuncionarioMapper::fromEntity)
                .collect(Collectors.toSet());
    }
}