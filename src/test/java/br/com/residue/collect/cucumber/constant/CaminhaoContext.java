package br.com.residue.collect.cucumber.constant;

import java.util.UUID;

public class CaminhaoContext {
    private static UUID caminhaoid;

    public static UUID getCaminhaoid() {
        return caminhaoid;
    }

    public static void setCaminhaoid(UUID caminhaoid) {
        CaminhaoContext.caminhaoid = caminhaoid;
    }
}
