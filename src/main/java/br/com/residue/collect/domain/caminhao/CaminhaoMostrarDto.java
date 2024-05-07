package br.com.residue.collect.domain.caminhao;

import br.com.residue.collect.domain.motorista.Motorista;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record CaminhaoMostrarDto(

        UUID idCaminhao,
        Motorista motorista,
        String placa,
        String modelo,
        String renavam,
        TiposdeResiduos tiposdeResiduos,
        String bairroColeta,
        BigDecimal capacidade

) {
    public CaminhaoMostrarDto(Caminhao caminhao){
        this(
                caminhao.getIdCaminhao(),
                caminhao.getMotorista(),
                caminhao.getPlaca(),
                caminhao.getModelo(),
                caminhao.getRenavam(),
                caminhao.getTiposdeResiduos(),
                caminhao.getBairroColeta(),
                caminhao.getCapacidade()
        );
    }
}
