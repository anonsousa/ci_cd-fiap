package br.com.residue.collect.cucumber.steps;

import br.com.residue.collect.cucumber.constant.CaminhaoContext;
import br.com.residue.collect.cucumber.constant.TokenContext;
import br.com.residue.collect.cucumber.stepsconfiguration.PageResponse;
import br.com.residue.collect.domain.caminhao.CaminhaoAtualizarDto;
import br.com.residue.collect.domain.caminhao.CaminhaoCadastroDto;
import br.com.residue.collect.domain.caminhao.CaminhaoMostrarDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


public class CaminhaoSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private static ResponseEntity<CaminhaoMostrarDto> response;

    private static List<ResponseEntity<PageResponse<CaminhaoMostrarDto>>> responseEntityList = new ArrayList<>();

    private ResponseEntity<String> responseInvalid;

    @Quando("eu envio uma solicitação POST para {string} com os dados do caminhao:")
    public void euEnvioUmaSolicitaçãoPOSTParaComOsDadosDoCaminhao(String endPoint, DataTable dataTable) {
        Map<String, String> caminhaoRow = dataTable.asMaps(String.class, String.class).get(0);

        CaminhaoCadastroDto caminhaoCadastroDto = new CaminhaoCadastroDto(
                caminhaoRow.get("placa"),
                caminhaoRow.get("modelo"),
                caminhaoRow.get("renavam")
        );

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CaminhaoCadastroDto> requestEntity = new HttpEntity<>(caminhaoCadastroDto, headers);

        response = restTemplate.exchange(
                endPoint,
                HttpMethod.POST,
                requestEntity,
                CaminhaoMostrarDto.class
        );
    }

    @Então("eu devo receber um status {int}")
    public void euDevoReceberUmStatus(int status) {
        assertThat(response.getStatusCodeValue()).isEqualTo(status);
    }

    @E("a resposta deve conter os dados do caminhão")
    public void aRespostaDeveConterOsDadosDoCaminhão() {
        CaminhaoMostrarDto caminhao = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode responseNode = objectMapper.valueToTree(caminhao);

        try {
            JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
            JsonSchema schema = factory.getJsonSchema(
                    getClass().getResource("/schemas/caminhao_response_schema.json").toString());

            ProcessingReport report = schema.validate(responseNode);

            if (!report.isSuccess()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "JSON response diferente do schema:" + report);
            }
            CaminhaoContext.setCaminhaoid(caminhao.idCaminhao());
        } catch (Exception e) {
            fail("Falha ao validar o Resposta JSON: " + e.getMessage());
        }
    }

    @Quando("eu envio uma solicitação POST para {string} com os dados:")
    public void euEnvioUmaSolicitaçãoPOSTParaComOsDados(String endPoint, DataTable dataTable) {
        Map<String, String> caminhaoRow = dataTable.asMaps(String.class, String.class).get(0);

        CaminhaoCadastroDto caminhaoDto = new CaminhaoCadastroDto(
                caminhaoRow.get("placa"),
                caminhaoRow.get("modelo"),
                caminhaoRow.get("renavam")
        );

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CaminhaoCadastroDto> requestEntity = new HttpEntity<>(caminhaoDto, headers);

        response = restTemplate.exchange(endPoint, HttpMethod.POST, requestEntity, CaminhaoMostrarDto.class);
    }

    @E("tenho um ID invalido")
    public UUID tenhoUmIDInvalido() {
        UUID id = UUID.randomUUID();
        return id;
    }

    @Quando("eu envio uma solicitação GET para o endpoint {string}")
    public void euEnvioUmaSolicitaçãoGETParaOEndpoint(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("/").append(tenhoUmIDValido());

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.GET,
                requestEntity,
                CaminhaoMostrarDto.class);

    }

    @E("tenho um ID valido")
    public UUID tenhoUmIDValido() {
        return CaminhaoContext.getCaminhaoid();
    }

    @Quando("eu envio uma solicitação invalida GET para o endpoint {string}")
    public void euEnvioUmaSolicitaçãoInvalidaGETParaOEndpoint(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("/").append(tenhoUmIDInvalido());

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity<>(headers);

        responseInvalid = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.GET,
                requestEntity,
                String.class);

    }

    @Quando("eu envio uma solicitação GET para o endpoint {string} buscando os motoristas")
    public void euEnvioUmaSolicitaçãoGETParaOEndpointBuscandoOsMotoristas(String endPoint) {
        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity requestEntity = new HttpEntity<>(headers);

        responseEntityList = Collections.singletonList(
                restTemplate.exchange(
                        endPoint,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<PageResponse<CaminhaoMostrarDto>>() {}
                )
        );

    }

    @E("o retorno deve conter todos os caminhoes registrados")
    public void oRetornoDeveConterTodosOsCaminhoesRegistrados() {
        CaminhaoMostrarDto caminhaoEsperado = response.getBody();

        boolean caminhaoEncontrado = responseEntityList.stream()
                .flatMap(entityList -> entityList.getBody().getContent().stream())
                .anyMatch(caminhao -> caminhao.equals(caminhaoEsperado));

        assertThat(caminhaoEncontrado)
                .as("O caminhao esperado deve estar presente na lista de caminhoes")
                .isTrue();
    }

    @Quando("eu envio uma solicitação PUT para {string} com os dados de atualização:")
    public void euEnvioUmaSolicitaçãoPUTParaComOsDadosDeAtualização(String endPoint, DataTable dataTable) {
        Map<String, String> caminhaoRow = dataTable.asMaps(String.class, String.class).get(0);

        UUID id = tenhoUmIDValido();
        CaminhaoAtualizarDto caminhaoAtualizarDto = new CaminhaoAtualizarDto(
                id,
                caminhaoRow.get("placa"),
                caminhaoRow.get("modelo"),
                caminhaoRow.get("renavam")
        );

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CaminhaoAtualizarDto> requestEntity = new HttpEntity<>(caminhaoAtualizarDto, headers);

        response = restTemplate.exchange(
                endPoint,
                HttpMethod.PUT,
                requestEntity,
                CaminhaoMostrarDto.class
        );
    }

}
