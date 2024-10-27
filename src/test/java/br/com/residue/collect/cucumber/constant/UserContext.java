package br.com.residue.collect.cucumber.constant;


import java.util.UUID;

public class UserContext {
    private static UUID userId;

    public static UUID getUserId() {
        return userId;
    }

    public static void setUserId(UUID userId) {
        UserContext.userId = userId;
    }
}
