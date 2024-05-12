package br.com.residue.collect.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserAtualizarDto(

        @NotBlank(message = "Id é obrigatório")
        UUID userId,

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(max = 20, min = 8, message = "Tamanho minimo 8, maximo 20")
        String senha

) {
}
