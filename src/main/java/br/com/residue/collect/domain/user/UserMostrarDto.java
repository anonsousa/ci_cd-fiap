package br.com.residue.collect.domain.user;

import java.util.UUID;

public record UserMostrarDto(
        UUID userId,
        String nome,
        String email,
        UserRole role
) {
    public UserMostrarDto(User user){
        this(
                user.getUserId(),
                user.getNome(),
                user.getEmail(),
                user.getRole()
        );
    }
}
