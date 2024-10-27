package br.com.residue.collect.domain.motorista;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.UUID;


@JsonIgnoreProperties(ignoreUnknown = true)
public record MotoristaMostrarDto(
        UUID idMotorista,
        String nome,
        String email,
        String telefone,
        String carteiraHabilitacao,
        LocalDate dataCadastro)
{
    public MotoristaMostrarDto(Motorista motorista){
        this(
                motorista.getIdMotorista(),
                motorista.getNome(),
                motorista.getEmail(),
                motorista.getTelefone(),
                motorista.getCarteiraHabilitacao(),
                motorista.getDataCadastro()
        );
    }
}
