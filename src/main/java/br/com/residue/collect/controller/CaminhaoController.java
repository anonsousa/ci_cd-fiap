package br.com.residue.collect.controller;


import br.com.residue.collect.domain.caminhao.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/caminhao")
public class CaminhaoController {

    @Autowired
    CaminhaoService caminhaoService;

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid CaminhaoCadastroDto caminhaoCadastroDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(caminhaoService.save(caminhaoCadastroDto));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity findById(@PathVariable UUID uuid){
        return ResponseEntity.status(HttpStatus.OK).body(caminhaoService.findById(uuid));
    }

    @GetMapping
    public ResponseEntity<Page<CaminhaoMostrarDto>> findAllId(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(caminhaoService.findAll(pageable));
    }


    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteById(@PathVariable UUID uuid){
        caminhaoService.delete(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Caminhao deletado com sucesso!");
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid CaminhaoAtualizarDto caminhaoAtualizarDto){
        return ResponseEntity.status(HttpStatus.OK).body(caminhaoService.update(caminhaoAtualizarDto));
    }
}
