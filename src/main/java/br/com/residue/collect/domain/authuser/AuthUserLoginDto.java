package br.com.residue.collect.domain.authuser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthUserLoginDto(

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Insira o email com um formato válido.")
        String email,

        @NotBlank(message = "Senha é obrigatório!")
        @Size(max = 20, min = 8, message = "Tamanho minimo = 8, Tamanho maximo = 20")
        String senha

) { }
