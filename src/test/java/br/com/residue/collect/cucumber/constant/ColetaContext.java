package br.com.residue.collect.cucumber.constant;

import java.util.UUID;

public class ColetaContext {
    private static UUID idColeta;

    public static UUID getIdColeta() {
        return idColeta;
    }

    public static void setIdColeta(UUID idColeta) {
        ColetaContext.idColeta = idColeta;
    }
}
