package br.com.residue.collect.controller;

import br.com.residue.collect.domain.relacionamento.RelacionamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/relacionamento")
public class RelacionamentoController {
    @Autowired
    private RelacionamentoService relacionamentoService;

    @PostMapping
    public ResponseEntity<String> joinMotoristaCaminhao(@RequestParam UUID idMotorista,
                                                        @RequestParam UUID idCaminhao){
        relacionamentoService.joinMotoristaCaminhao(idMotorista, idCaminhao);
        return ResponseEntity.ok("Relacionamento estabelecido com sucesso!");
    }
}
