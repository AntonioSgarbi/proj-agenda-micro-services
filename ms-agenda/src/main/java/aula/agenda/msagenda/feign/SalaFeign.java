package aula.agenda.msagenda.feign;

import aula.agenda.msagenda.dto.SalaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@FeignClient(value = "sala")
public interface SalaFeign {

    @GetMapping("/{id}")
    ResponseEntity<SalaDTO> findById(@PathVariable Long id);

    @PutMapping("/by-set")
    ResponseEntity<Set<SalaDTO>> findByIdList(@RequestBody Set<Long> idSet);

}
