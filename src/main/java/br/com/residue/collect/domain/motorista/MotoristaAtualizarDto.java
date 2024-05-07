package br.com.residue.collect.domain.motorista;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record MotoristaAtualizarDto(

        UUID idMotorista,

        @NotBlank(message = "Nome é obrigatorio")
        @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]+$", message = "Nome inválido.")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Insira o email com um formato válido.")
        String email,

        @NotBlank(message = "Telefone é obrigatório!")
        @Pattern(regexp = "^[0-9]+$", message = "Somente números são permitidos.")
        String telefone,

        @NotBlank(message = "Carteira de Habilitação é obrigatório!")
        @Pattern(regexp = "^[0-9]+$", message = "Somente números são permitidos.")
        @Size(min = 12, max = 12, message = "A CNH possui 12 caracteres!")
        String carteiraHabilitacao

) {
}
