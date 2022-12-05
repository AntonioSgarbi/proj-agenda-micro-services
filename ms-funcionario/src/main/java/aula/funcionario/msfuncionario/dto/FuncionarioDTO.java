package aula.funcionario.msfuncionario.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FuncionarioDTO {
    private Long id;
    private String nome;
    private String dataAdmissao;
    private String dataNascimento;

}
