package br.com.residue.collect.controller;

import br.com.residue.collect.domain.coleta.Coleta;
import br.com.residue.collect.domain.coleta.ColetaAtualizarDto;
import br.com.residue.collect.domain.coleta.ColetaCadastroDto;
import br.com.residue.collect.domain.coleta.ColetaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/coleta")
public class ColetaController {

    @Autowired
    ColetaService coletaService;


    @PostMapping
    public ResponseEntity save(@RequestBody @Valid ColetaCadastroDto coletaCadastroDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(coletaService.save(coletaCadastroDto));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity findById(@PathVariable UUID uuid){
        return ResponseEntity.status(HttpStatus.OK).body(coletaService.findById(uuid));
    }

    @GetMapping
    public ResponseEntity<Page<Coleta>> findAll(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(coletaService.findAll(pageable));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid ColetaAtualizarDto coletaAtualizarDto){
        return ResponseEntity.status(HttpStatus.OK).body(coletaService.update(coletaAtualizarDto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity delete(@PathVariable UUID uuid){
        coletaService.delete(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Coleta deletada com sucesso!");
    }
}
