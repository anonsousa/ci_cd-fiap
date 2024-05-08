package br.com.residue.collect.domain.coleta;

import br.com.residue.collect.domain.caminhao.TiposResiduos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;


public record ColetaCadastroDto(

        @NotBlank(message = "Bairro e obrigatorio!")
                @Size(min = 5, max = 230)
        String bairroColeta,

        @NotNull(message = "Tipo do residuo e obrigatorio!")
        TiposResiduos tipoResiduo,

        @NotNull(message = "Peso e obrigatorio!")
        BigDecimal volumePeso,


        String informacoesAdicionais
) {
}
