package br.com.residue.collect.domain.relacionamento;

import br.com.residue.collect.domain.caminhao.Caminhao;
import br.com.residue.collect.domain.caminhao.CaminhaoRepository;
import br.com.residue.collect.domain.motorista.Motorista;
import br.com.residue.collect.domain.motorista.MotoristaRepository;
import br.com.residue.collect.infra.exceptions.ItemNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RelacionamentoService {

    @Autowired
    private MotoristaRepository motoristaRepository;

    @Autowired
    private CaminhaoRepository caminhaoRepository;

    @Transactional
    public void joinMotoristaCaminhao(UUID idMotorista, UUID idCaminhao){
        Motorista motorista = motoristaRepository.findById(idMotorista).orElseThrow(() -> new ItemNotFoundException("Motorista nao encontrado"));
        Caminhao caminhao = caminhaoRepository.findById(idCaminhao).orElseThrow(() -> new ItemNotFoundException("Caminhao nao encontrado"));

        caminhao.setMotorista(motorista);
        motorista.setCaminhao(caminhao);

        motoristaRepository.save(motorista);
    }
}
