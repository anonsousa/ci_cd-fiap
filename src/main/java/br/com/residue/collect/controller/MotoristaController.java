package br.com.residue.collect.controller;


import br.com.residue.collect.domain.motorista.MotoristaCadastroDto;
import br.com.residue.collect.domain.motorista.MotoristaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/motorista")
public class MotoristaController {

    @Autowired
    MotoristaService motoristaService;

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid MotoristaCadastroDto motoristaCadastroDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(motoristaService.save(motoristaCadastroDto));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity findOneId(@PathVariable UUID uuid){
        return ResponseEntity.status(HttpStatus.OK).body(motoristaService.findOneId(uuid));
    }

    @GetMapping
    public ResponseEntity findAll(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(motoristaService.findAll(pageable));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteById(@PathVariable UUID uuid){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(motoristaService.deleteById(uuid));
    }






}
