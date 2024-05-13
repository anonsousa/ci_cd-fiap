package br.com.residue.collect.infra.security;

import br.com.residue.collect.domain.authuser.TokenDto;
import br.com.residue.collect.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${my.secret.key}")
    private String secret;

    public TokenDto generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT
                    .create()
                    .withIssuer("collect-ms")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpiresDate())
                    .sign(algorithm);
            return new TokenDto(user.getEmail(), token);
        } catch (JWTCreationException error){
            throw new RuntimeException("Nao foi possivel gerar o Token");
        }
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("collect-ms")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException error){
            return "";
        }

    }


    public Instant generateExpiresDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
