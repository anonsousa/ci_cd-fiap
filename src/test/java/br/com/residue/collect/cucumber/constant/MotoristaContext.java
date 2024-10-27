package br.com.residue.collect.cucumber.constant;

import java.util.UUID;

public class MotoristaContext {
    private static UUID motoristaId;

    public static UUID getMotoristaId() {
        return motoristaId;
    }

    public static void setMotoristaId(UUID motoristaId) {
        MotoristaContext.motoristaId = motoristaId;
    }
}
