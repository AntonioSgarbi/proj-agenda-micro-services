package aula.agenda.msagenda.controller;

import aula.agenda.msagenda.dto.AgendaDTO;
import aula.agenda.msagenda.service.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AgendaController {
    private final AgendaService agendaService;


    @GetMapping
    public ResponseEntity<Page<AgendaDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(this.agendaService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<AgendaDTO> insert(@RequestBody AgendaDTO agendaDTO) {
        URI uri = URI.create("/agenda").normalize();
        return ResponseEntity.created(uri).body(this.agendaService.insert(agendaDTO));
    }

    @GetMapping("/{id}")
    ResponseEntity<AgendaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.agendaService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendaDTO> update(@PathVariable Long id, @RequestBody AgendaDTO dto){
        return ResponseEntity.ok(this.agendaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        String message = this.agendaService.delete(id);
        return ResponseEntity.accepted().body(message);
    }

}
