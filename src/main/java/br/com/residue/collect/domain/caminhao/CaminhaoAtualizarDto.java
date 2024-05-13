package br.com.residue.collect.domain.caminhao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record CaminhaoAtualizarDto(

        UUID idCaminhao,

        @NotBlank(message = "Placa é obrigatória")
        @Size(min = 7, max = 7, message = "A placa precisa de 7 caracteres")
        String placa,

        @NotBlank(message = "Modelo é obrigatório")
        String modelo,

        @NotBlank(message = "Renavam é obrigatório")
        @Size(min = 11, max = 11, message = "O renavam precisa de 11 digitos")
        String renavam,

        TiposResiduos tiposResiduos

) { }
