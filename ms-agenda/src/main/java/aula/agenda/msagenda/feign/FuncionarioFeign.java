package aula.agenda.msagenda.feign;

import aula.agenda.msagenda.dto.FuncionarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@FeignClient(value = "funcionario")
public interface FuncionarioFeign {

    @GetMapping("/{id}")
    ResponseEntity<FuncionarioDTO> findById(@PathVariable Long id);

    @PutMapping("/by-set")
    ResponseEntity<Set<FuncionarioDTO>> findByIdList(@RequestBody Set<Long> idSet);

}
