package br.com.residue.collect.controller;

import br.com.residue.collect.domain.caminhao.Caminhao;
import br.com.residue.collect.domain.relacionamento.RelacionamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/relacionamento")
public class RelacionamentoController {
    @Autowired
    private RelacionamentoService relacionamentoService;

    @PostMapping
    public ResponseEntity<String> joinMotoristaCaminhao(@RequestParam UUID idMotorista, @RequestParam UUID idCaminhao){
        relacionamentoService.joinMotoristaCaminhao(idMotorista, idCaminhao);
        String message = String.format("Motorista de id:%s agregado ao caminhao de id:%s", idMotorista, idCaminhao);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<Page<Caminhao>> findAllWithMotorista(Pageable pageable){
        return ResponseEntity.ok(relacionamentoService.findAllWithMotorista(pageable));
    }


    @DeleteMapping
    public ResponseEntity<String> deleteRelationship(@RequestParam UUID idCaminhao){
        relacionamentoService.deleteMotoristaCaminhao(idCaminhao);
        String message = String.format("Caminhao de id: %s agora encontra-se sem motorista!", idCaminhao);
        return ResponseEntity.ok(message);
    }


}
