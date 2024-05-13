package br.com.residue.collect.domain.authuser;

import br.com.residue.collect.domain.user.User;

public record TokenDto(
        String email,
        String token) {

    public TokenDto(User user, String token){
        this(
                user.getEmail(),
                token
        );
    }
}
