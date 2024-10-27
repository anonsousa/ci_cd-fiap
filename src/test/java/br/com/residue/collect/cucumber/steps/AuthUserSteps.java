package br.com.residue.collect.cucumber.steps;

import br.com.residue.collect.cucumber.constant.TokenContext;
import br.com.residue.collect.cucumber.constant.UserContext;
import br.com.residue.collect.domain.user.UserCadastroDto;
import br.com.residue.collect.domain.user.UserMostrarDto;
import br.com.residue.collect.domain.user.UserRole;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AuthUserSteps{

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static ResponseEntity<UserMostrarDto> response;

    @Quando("eu envio uma solicitação POST para {string} com os dados do usuário:")
    public void euEnvioUmaSolicitaçãoPOSTParaComOsDadosDoUsuário(String endPoint, DataTable dataTable) {
        Map<String, String> usuarioMap = dataTable.asMaps(String.class, String.class).get(0);

        UserCadastroDto userCadastroDto = new UserCadastroDto(
                usuarioMap.get("nome"),
                usuarioMap.get("email"),
                usuarioMap.get("senha"),
                UserRole.USER
        );

        response = testRestTemplate.postForEntity(endPoint, userCadastroDto, UserMostrarDto.class);
    }

    @Então("a resposta deve ter o status {int}")
    public void aRespostaDeveTerOStatus(int status) {
        assertThat(response.getStatusCodeValue()).isEqualTo(status);
    }

    @E("a resposta deve conter os dados que foram cadastrados previamente")
    public void aRespostaDeveConterOsDadosQueForamCadastradosPreviamente() {
        UserMostrarDto usuarioResponse = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode responseNode = objectMapper.valueToTree(usuarioResponse);

        try {
            JsonSchemaFactory factory = JsonSchemaFactory.byDefault();


            JsonSchema schema = factory.getJsonSchema(
                    getClass().getResource("/schemas/user_response_schema.json").toString());

            ProcessingReport report = schema.validate(responseNode);

            if (!report.isSuccess()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "JSON response diferente do schema: " + report);
            }

            UserContext.setUserId(usuarioResponse.userId());
        } catch (Exception e) {
            fail("Falha ao validar a Resposta JSON" + e.getMessage());
        }

    }
}
