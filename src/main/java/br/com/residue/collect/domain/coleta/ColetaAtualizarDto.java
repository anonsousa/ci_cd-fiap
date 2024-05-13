package br.com.residue.collect.domain.coleta;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record ColetaAtualizarDto(


        @NotBlank(message = "Id da coleta é obrigatório")
        UUID idColeta,

        @NotBlank(message = "Cep é obrigatório")
        @Pattern(regexp = "\\d{5}-\\d{3}")
        @Size(min = 8, max = 8, message = "Cep deve conter 8 caracteres no formato: XXXXX-XXX")
        String cep,

        @NotBlank(message = "Numero da casa é obrigatório")
        @Size(min = 2, max = 6, message = "O numero da casa deve conter no minimo 2 e no maximo 6 de tamanho!")
        String numeroCasa,

        @NotNull(message = "Tipo do residuo é obrigatório")
        TiposResiduos tipoResiduo,

        @NotNull(message = "Peso é obrigatório")
        @Positive(message = "O numero deve ser positivo!")
        BigDecimal volumePeso,

        @Size(min = 0, max = 240, message = "Informacoes adicionais devem conter no maximo 240 caracteres!")
        String informacoesAdicionais

) { }
