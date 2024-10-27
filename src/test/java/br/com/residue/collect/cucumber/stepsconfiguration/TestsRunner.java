package br.com.residue.collect.cucumber.stepsconfiguration;

import br.com.residue.collect.cucumber.steps.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@SelectClasses({
        AuthUserSteps.class,
        AuthLoginUserSteps.class,
        MotoristaSteps.class,
        CaminhaoSteps.class,
        RelacionamentoSteps.class,
        ColetaSteps.class})
public class TestsRunner {
}
