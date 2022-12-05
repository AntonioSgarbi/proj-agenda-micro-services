package aula.funcionario.msfuncionario.control;

import aula.funcionario.msfuncionario.dto.FuncionarioDTO;
import aula.funcionario.msfuncionario.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;


    @GetMapping
    public Page<FuncionarioDTO> findAll(Pageable pageable){
        return funcionarioService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public FuncionarioDTO findById(@PathVariable Long id){
        return funcionarioService.findById(id);
    }

    @PostMapping
    public ResponseEntity<FuncionarioDTO> insert(@RequestBody FuncionarioDTO sala){
        FuncionarioDTO salaDTO = this.funcionarioService.save(sala);
        URI uri = URI.create("/sala/" + salaDTO.getId());

        return ResponseEntity.created(uri).body(salaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> update(@PathVariable Long id, @RequestBody FuncionarioDTO dto){
        return ResponseEntity.ok(this.funcionarioService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        String message = this.funcionarioService.delete(id);
        return ResponseEntity.accepted().body(message);
    }

    @PutMapping("/by-set")
    public ResponseEntity<Set<FuncionarioDTO>> findBySet(@RequestBody Set<Long> set) {
        return ResponseEntity.ok(this.funcionarioService.findBySet(set));
    }

}
