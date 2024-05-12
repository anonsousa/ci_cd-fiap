package br.com.residue.collect.domain.authuser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthUserLoginDto(

        @NotBlank(message = "Email e obrigatorio!")
        @Email
        String email,

        @NotBlank(message = "Senha e obrigatorio!")
        @Size(max = 20, min = 8, message = "Tamanho minimo = 8, Tamanho maximo = 20")
        String senha



) {
}
