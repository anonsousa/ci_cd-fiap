package br.com.residue.collect.cucumber.constant;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class TokenContext {
    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        TokenContext.token = token;
    }
}
