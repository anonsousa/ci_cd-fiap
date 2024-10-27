package br.com.residue.collect.cucumber.steps;

import br.com.residue.collect.cucumber.constant.MotoristaContext;
import br.com.residue.collect.cucumber.constant.TokenContext;
import br.com.residue.collect.cucumber.stepsconfiguration.PageResponse;
import br.com.residue.collect.domain.motorista.MotoristaAtualizarDto;
import br.com.residue.collect.domain.motorista.MotoristaCadastroDto;
import br.com.residue.collect.domain.motorista.MotoristaMostrarDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


public class MotoristaSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private static ResponseEntity<MotoristaMostrarDto> response;

    private static List<ResponseEntity<PageResponse<MotoristaMostrarDto>>> responseEntityList = new ArrayList<>();

    @Dado("que eu estou autenticado")
    public void queEuEstouAutenticado() {
        assertThat(TokenContext.getToken()).isNotNull();
    }

    @Quando("eu envio uma solicitação POST para {string} com os dados do motorista:")
    public void euEnvioUmaSolicitaçãoPOSTParaComOsDadosDoMotorista(String endPoint, DataTable dataTable) {
        Map<String, String> motoristaRow = dataTable.asMaps(String.class, String.class).get(0);

        MotoristaCadastroDto motoristaCadastroDto = new MotoristaCadastroDto(
                motoristaRow.get("nome"),
                motoristaRow.get("email"),
                motoristaRow.get("telefone"),
                motoristaRow.get("carteiraHabilitacao")
        );

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MotoristaCadastroDto> requestEntity = new HttpEntity<>(motoristaCadastroDto, headers);

        response = restTemplate.exchange(
                endPoint,
                HttpMethod.POST,
                requestEntity,
                MotoristaMostrarDto.class
        );
    }

    @Então("a resposta do motorista deve ter o status {int}")
    public void aRespostaDoMotoristaDeveTerOStatus(int status) {
        assertThat(response.getStatusCodeValue()).isEqualTo(status);
    }

    @E("a resposta deve conter os dados do motorista")
    public void aRespostaDeveConterOsDadosDoMotorista() {
        MotoristaMostrarDto motorista = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        JsonNode responseNode = objectMapper.valueToTree(motorista);

        if (responseNode.has("dataCadastro") && responseNode.get("dataCadastro").isArray()) {
            LocalDate dataCadastro = motorista.dataCadastro();
            String formattedDate = dataCadastro.toString(); // Converte para string no formato ISO
            ((ObjectNode) responseNode).put("dataCadastro", formattedDate);
        }
        try {
            JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
            JsonSchema schema = factory.getJsonSchema(
                    getClass().getResource("/schemas/motorista_response_schema.json").toString());

            ProcessingReport report = schema.validate(responseNode);

            if (!report.isSuccess()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "JSON response diferente do schema: " + report);
            }
            MotoristaContext.setMotoristaId(motorista.idMotorista());
        } catch (Exception e) {
            fail("Falha ao validar a Resposta JSON: " + e.getMessage());
        }

    }

    @Quando("eu envio uma solicitação GET para {string} com o ID do motorista invalido")
    public void euEnvioUmaSolicitaçãoGETParaComOIDDoMotoristaInvalido(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("/").append(UUID.randomUUID());

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        response = restTemplate.exchange(urlBuilder.toString(), HttpMethod.GET, requestEntity, MotoristaMostrarDto.class);
    }

    @E("que eu tenho o id do motorista")
    public void queEuTenhoOIdDoMotorista() {
        assertThat(MotoristaContext.getMotoristaId()).isNotNull();
    }

    @Quando("eu envio uma solicitação GET para {string} com o ID do motorista")
    public void euEnvioUmaSolicitaçãoGETParaComOIDDoMotorista(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("/").append(MotoristaContext.getMotoristaId());

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        response = restTemplate.exchange(urlBuilder.toString(), HttpMethod.GET, requestEntity, MotoristaMostrarDto.class);
    }

    @Então("a resposta da busca do motorista deve ter o status {int}")
    public void aRespostaDaBuscaDoMotoristaDeveTerOStatus(int status) {
        assertThat(response.getStatusCodeValue()).isEqualTo(status);
    }

    @E("a resposta deve conter os dados do motorista buscado")
    public void aRespostaDeveConterOsDadosDoMotoristaBuscado() {
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().idMotorista()).isNotNull();
        assertThat(response.getBody().nome()).isNotNull();
        assertThat(response.getBody().email()).isNotNull();
        assertThat(response.getBody().telefone()).isNotNull();
        assertThat(response.getBody().carteiraHabilitacao()).isNotNull();
        assertThat(response.getBody().dataCadastro()).isNotNull();
    }

    @Quando("eu envio uma solicitação GET para {string}")
    public void euEnvioUmaSolicitaçãoGETPara(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        responseEntityList = Collections.singletonList(
                restTemplate.exchange(
                        urlBuilder.toString(),
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<PageResponse<MotoristaMostrarDto>>() {}
                )
        );
    }

    @Então("a resposta da busca dos motoristas deve ter o status {int}")
    public void aRespostaDaBuscaDosMotoristasDeveTerOStatus(int status) {
        assertThat(responseEntityList.getFirst().getStatusCodeValue()).isEqualTo(status);
    }

    @E("o retorno deve conter todos os motoristas cadastrados")
    public void oRetornoDeveConterTodosOsMotoristasCadastrados() {
        MotoristaMostrarDto motoristaEsperado = response.getBody();

        boolean motoristaEncontrado = responseEntityList.stream()
                .flatMap(entityList -> entityList.getBody().getContent().stream())
                .anyMatch(motorista -> motorista.equals(motoristaEsperado));

        assertThat(motoristaEncontrado)
                .as("O motorista esperado deve estar presente na lista de motoristas cadastrados.")
                .isTrue();
    }

    @Quando("eu envio uma solicitação PUT para {string} com os dados do motorista atualizados:")
    public void euEnvioUmaSolicitaçãoPUTParaComOsDadosDoMotoristaAtualizados(String endPoint, DataTable dataTable) {
        Map<String, String> motoristaRow = dataTable.asMaps(String.class, String.class).get(0);

        UUID id = MotoristaContext.getMotoristaId();
        MotoristaAtualizarDto motoristaAtualizarDto = new MotoristaAtualizarDto(
                id,
                motoristaRow.get("nome"),
                motoristaRow.get("email"),
                motoristaRow.get("telefone"),
                motoristaRow.get("carteiraHabilitacao")
        );

        String generateToken = "Bearer " + TokenContext.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MotoristaAtualizarDto> requestEntity = new HttpEntity<>(motoristaAtualizarDto, headers);

        response = restTemplate.exchange(
                endPoint,
                HttpMethod.PUT,
                requestEntity,
                MotoristaMostrarDto.class
        );
    }

    @Então("a resposta da atualizacao deve ter o status {int}")
    public void aRespostaDaAtualizacaoDeveTerOStatus(int status) {
        assertThat(response.getStatusCodeValue()).isEqualTo(status);
    }
}



















