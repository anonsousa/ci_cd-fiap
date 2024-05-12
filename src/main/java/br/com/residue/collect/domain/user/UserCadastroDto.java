package br.com.residue.collect.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCadastroDto(

        @NotBlank(message = "Nome e obrigatorio!")
        String nome,

        @NotBlank(message = "Email e obrigatorio")
        @Email
        String email,

        @NotBlank(message = "Senha e obrigatoria!")
        @Size(max = 20, min = 8, message = "Tamanho minimo 8, maximo 20")
        String senha
) {
}
