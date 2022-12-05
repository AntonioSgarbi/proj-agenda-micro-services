package aula.funcionario.msfuncionario.mapper;

import aula.funcionario.msfuncionario.dto.FuncionarioDTO;
import aula.funcionario.msfuncionario.model.Funcionario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FuncionarioMapper {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static Funcionario fromDTO(FuncionarioDTO dto) {
        Funcionario entity = new Funcionario();

        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setDataAdmissao(LocalDate.parse(dto.getDataAdmissao(),FORMATO_DATA));
        entity.setDataNascimento(LocalDate.parse(dto.getDataNascimento(),FORMATO_DATA));

        return entity;
    }

    public static FuncionarioDTO fromEntity(Funcionario funcionario) {
        FuncionarioDTO dto = new FuncionarioDTO();

        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        dto.setDataAdmissao(funcionario.getDataAdmissao().format(FORMATO_DATA));
        dto.setDataNascimento(funcionario.getDataNascimento().format(FORMATO_DATA));

        return dto;
    }

}
