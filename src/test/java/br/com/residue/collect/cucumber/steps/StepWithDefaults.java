package br.com.residue.collect.cucumber.steps;

import br.com.residue.collect.CollectApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = CollectApplication.class)
public class StepWithDefaults {
}
