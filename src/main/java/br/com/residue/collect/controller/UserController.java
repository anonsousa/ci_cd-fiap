package br.com.residue.collect.controller;


import br.com.residue.collect.domain.user.UserAtualizarDto;
import br.com.residue.collect.domain.user.UserCadastroDto;
import br.com.residue.collect.domain.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid UserCadastroDto userCadastroDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userCadastroDto));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity findById(@PathVariable UUID uuid){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(uuid));
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody @Valid UserAtualizarDto userAtualizarDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userAtualizarDto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteById(@PathVariable UUID uuid){
        userService.deleteUser(uuid);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario deletado com sucesso!");
    }



    //ignore esse comentário









}
