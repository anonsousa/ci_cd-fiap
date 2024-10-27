package br.com.residue.collect.cucumber.steps;

import br.com.residue.collect.cucumber.constant.TokenContext;
import br.com.residue.collect.cucumber.constant.UserContext;
import br.com.residue.collect.domain.authuser.AuthUserLoginDto;
import br.com.residue.collect.domain.authuser.TokenDto;
import br.com.residue.collect.domain.user.UserAtualizarDto;
import br.com.residue.collect.domain.user.UserMostrarDto;
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
import io.cucumber.spring.ScenarioScope;
import org.junit.jupiter.api.Order;
import org.opentest4j.TestAbortedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


public class AuthLoginUserSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private static ResponseEntity<TokenDto> response;

    private static ResponseEntity<UserMostrarDto> userResponse;

    @Quando("eu envio uma solicitação POST para {string} com os dados de autenticacao do usuário:")
    public void euEnvioUmaSolicitaçãoPOSTParaComOsDadosDeAutenticacaoDoUsuário(String endPoint, DataTable dataTable) {
        Map<String, String> usuarioMap = dataTable.asMaps(String.class, String.class).get(0);

        AuthUserLoginDto authUserLogin = new AuthUserLoginDto(
                usuarioMap.get("email"),
                usuarioMap.get("senha")
        );

        response = restTemplate.postForEntity(endPoint, authUserLogin, TokenDto.class);
    }

    @Então("a resposta deve ser o status {int}")
    public void aRespostaDeveSerOStatus(int status) {
        assertThat(response.getStatusCodeValue()).isEqualTo(status);
    }

    @E("a resposta deve conter um token de autenticação")
    public void aRespostaDeveConterUmTokenDeAutenticação() {
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().token()).isNotNull();
    }

    @E("a resposta deve ter o retorno de acordo com o json de contrato do token")
    public void aRespostaDeveTerORetornoDeAcordoComOJsonDeContratoDoToken() {
        TokenDto tokenDto = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode responseNode = objectMapper.valueToTree(tokenDto);

        try {
            JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
            JsonSchema schema = factory.getJsonSchema(
                    getClass().getResource("/schemas/token_login_response_schema.json").toString());

            ProcessingReport report = schema.validate(responseNode);

            if (!report.isSuccess()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "JSON response diferente do schema: " + report);
            }
            TokenContext.setToken(tokenDto.token());
        } catch (Exception e) {
            fail("Falha ao validar a Resposta JSON" + e.getMessage());
        }
    }

    @E("eu tenho o ID do usuário registrado")
    public UUID euTenhoOIDDoUsuárioRegistrado() {
        if (UserContext.getUserId() == null) {
            throw new TestAbortedException("User Id Nulo!");
        }
        return UserContext.getUserId();
    }

    @Quando("eu envio uma solicitação GET para {string} com o ID do usuário")
    public void euEnvioUmaSolicitaçãoGETParaComOIDDoUsuário(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("/").append(euTenhoOIDDoUsuárioRegistrado());

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity<>(headers);

        userResponse = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.GET,
                entity,
                UserMostrarDto.class);
    }

    @Então("a resposta da solicitacao deve ter o status {int}")
    public void aRespostaDaSolicitacaoDeveTerOStatus(int status) {
        assertThat(userResponse.getStatusCodeValue()).isEqualTo(status);
    }

    @E("a resposta deve conter os dados do usuário que eu loguei")
    public void aRespostaDeveConterOsDadosDoUsuárioQueEuLoguei() {
        assertThat(userResponse.getBody().email()).isEqualTo(response.getBody().email());
    }

    @Quando("eu envio uma solicitação PUT para {string} com os seguintes dados atualizados:")
    public void euEnvioUmaSolicitaçãoPUTParaComOsSeguintesDadosAtualizados(String endPoint, DataTable dataTable) {
        Map<String, String> userAtualizadoMap = dataTable.asMaps(String.class, String.class).get(0);

        UUID idUsuarioAtualizar = UserContext.getUserId();
        UserAtualizarDto userAtualizarDto = new UserAtualizarDto(
                idUsuarioAtualizar,
                userAtualizadoMap.get("nome"),
                userAtualizadoMap.get("email"),
                userAtualizadoMap.get("senha")
        );

        String generatedToken = "Bearer " + TokenContext.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generatedToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserAtualizarDto> entity = new HttpEntity<>(userAtualizarDto, headers);

        userResponse = restTemplate.exchange(
                endPoint,
                HttpMethod.PUT,
                entity,
                UserMostrarDto.class
        );
    }

    @E("a resposta deve conter os dados atualizados do usuário:")
    public void aRespostaDeveConterOsDadosAtualizadosDoUsuário(DataTable table) {
        Map<String, String> userAtualizado = table.asMaps(String.class, String.class).get(0);

        assertThat(userResponse.getBody().email()).isEqualTo(userAtualizado.get("email"));
        assertThat(userResponse.getBody().nome()).isEqualTo(userAtualizado.get("nome"));
    }
}
