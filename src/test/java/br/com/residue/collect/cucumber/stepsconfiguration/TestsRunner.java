package br.com.residue.collect.cucumber.stepsconfiguration;

import br.com.residue.collect.cucumber.steps.AuthLoginUserSteps;
import br.com.residue.collect.cucumber.steps.AuthUserSteps;
import br.com.residue.collect.cucumber.steps.CaminhaoSteps;
import br.com.residue.collect.cucumber.steps.ColetaSteps;
import br.com.residue.collect.cucumber.steps.MotoristaSteps;
import br.com.residue.collect.cucumber.steps.RelacionamentoSteps;
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
