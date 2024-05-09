package br.com.residue.collect.domain.coleta;

import br.com.residue.collect.domain.caminhao.TiposResiduos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record ColetaAtualizarDto(


        @NotBlank(message = "Id da coleta e obrigatorio!")
        UUID idColeta,

        @NotBlank(message = "Bairro e obrigatorio!")
        @Size(min = 5, max = 230)
        String bairroColeta,

        @NotNull(message = "Tipo do residuo e obrigatorio!")
        TiposResiduos tipoResiduo,


        String informacoesAdicionais

) {
}
