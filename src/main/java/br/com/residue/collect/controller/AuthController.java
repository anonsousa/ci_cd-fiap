package br.com.residue.collect.controller;

import br.com.residue.collect.domain.authuser.AuthUserLoginDto;
import br.com.residue.collect.domain.authuser.TokenDto;
import br.com.residue.collect.domain.user.User;
import br.com.residue.collect.domain.user.UserCadastroDto;
import br.com.residue.collect.domain.user.UserMostrarDto;
import br.com.residue.collect.domain.user.UserService;
import br.com.residue.collect.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthUserLoginDto authUserLoginDto){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authUserLoginDto.email(),
                        authUserLoginDto.senha()
                );
        Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        TokenDto token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserCadastroDto userCadastroDto){
        UserMostrarDto userSaved = null;
        userSaved = userService.save(userCadastroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }
}
