package aula.sala.mssala.control;

import aula.sala.mssala.dto.SalaDTO;
import aula.sala.mssala.service.SalaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class SalaController {

    private final SalaService salaService;

    @GetMapping
    public Page<SalaDTO> findAll(Pageable pageable){
        return salaService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public SalaDTO findById(@PathVariable Long id){
        return salaService.findById(id);
    }

    @PostMapping
    public ResponseEntity<SalaDTO> insert(@RequestBody SalaDTO sala){
        SalaDTO salaDTO = this.salaService.save(sala);
        URI uri = URI.create("/sala/" + salaDTO.getId());

        return ResponseEntity.created(uri).body(salaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDTO> update(@PathVariable Long id, @RequestBody SalaDTO dto){
        return ResponseEntity.ok(this.salaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        String message = this.salaService.delete(id);
        return ResponseEntity.accepted().body(message);
    }

    @PutMapping("/by-set")
    ResponseEntity<Set<SalaDTO>> findBySet(@RequestBody Set<Long> set) {
        return ResponseEntity.ok(this.salaService.findBySet(set));
    }
}
