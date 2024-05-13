package br.com.residue.collect.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private VerifyToken verifyToken;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        //AUTH
                        .requestMatchers(HttpMethod.POST, "/auth/*").permitAll()
                        //CAMINHAO
                        .requestMatchers(HttpMethod.POST, "/caminhao").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/caminhao/{uuid}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/caminhao").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/caminhao/{uuid}").hasRole("ADMIN")
                        //COLETA
                        .requestMatchers(HttpMethod.POST, "/coleta").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/coleta/*").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/coleta/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/coleta/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/coleta/{uuid}").hasRole("ADMIN")
                        //MOTORISTA
                        .requestMatchers(HttpMethod.POST, "/motorista").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/motorista/{uuid}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/motorista").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/motorista").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/motorista/{uuid}").hasRole("ADMIN")
                        //RELACIONAMENTO
                        .requestMatchers(HttpMethod.POST, "/relacionamento").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/relacionamento").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/relacionamento").hasRole("ADMIN")

                        .anyRequest()
                        .authenticated())
                .addFilterBefore(
                        verifyToken,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {return authenticationConfiguration.getAuthenticationManager();}

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
