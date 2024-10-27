package br.com.residue.collect.cucumber.steps;

import br.com.residue.collect.cucumber.constant.CaminhaoContext;
import br.com.residue.collect.cucumber.constant.ColetaContext;
import br.com.residue.collect.cucumber.constant.TokenContext;
import br.com.residue.collect.cucumber.stepsconfiguration.PageResponse;
import br.com.residue.collect.domain.coleta.Coleta;
import br.com.residue.collect.domain.coleta.ColetaAtualizarDto;
import br.com.residue.collect.domain.coleta.ColetaCadastroDto;
import br.com.residue.collect.domain.coleta.TiposResiduos;
import br.com.residue.collect.domain.coleta.TiposStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ColetaSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<Coleta> response;

    private ResponseEntity<String> invalidResponse;

    private ResponseEntity<PageResponse<Coleta>> pageColetaResponse;

    @Quando("eu envio uma solicitação POST para {string} com os dados da coleta:")
    public void euEnvioUmaSolicitaçãoPOSTParaComOsDadosDaColeta(String endPoint, DataTable dataTable) {
        Map<String, String> coletaRow = dataTable.asMaps(String.class, String.class).get(0);

        ColetaCadastroDto coletaCadastroDto = new ColetaCadastroDto(
                coletaRow.get("cep"),
                coletaRow.get("numeroCasa"),
                TiposResiduos.valueOf(coletaRow.get("tipoResiduo")),
                new BigDecimal(coletaRow.get("volumePeso")),
                coletaRow.get("informacoesAdicionais")
        );

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ColetaCadastroDto> requestEntity = new HttpEntity<>(coletaCadastroDto, headers);

        response = restTemplate.postForEntity(endPoint, requestEntity, Coleta.class);
    }

    @Então("o status da resposta deve ser {int}")
    public void oStatusDaRespostaDeveSer(int status) {
        assertThat(response.getStatusCodeValue()).isEqualTo(status);
    }

    @E("devo ter o response com todos os dados corretos")
    public void devoTerOResponseComTodosOsDadosCorretos() {
        Coleta coleta = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        JsonNode responseNode = objectMapper.valueToTree(coleta);

        if(responseNode.has("dataColeta") && responseNode.get("dataColeta").isArray()) {
            LocalDate dataColeta = coleta.getDataColeta();
            String formattedDate = dataColeta.toString();
            ((ObjectNode) responseNode).put("dataColeta", formattedDate);
        }
        try {
            JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
            JsonSchema schema = factory.getJsonSchema(
                    getClass().getResource("/schemas/coleta_response_schema.json").toString());

            ProcessingReport report = schema.validate(responseNode);

            if (!report.isSuccess()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "JSON response diferente do schema: " + report);
            }
            ColetaContext.setIdColeta(coleta.getIdColeta());
        } catch (Exception e) {
            fail("Falha ao validar a Responsta JSON: " + e.getMessage());
        }
    }

    @Quando("eu envio uma solicitação GET para {string} com a coleta invalida")
    public void euEnvioUmaSolicitaçãoGETParaComAColetaInvalida(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("/").append(tenhoUmIDDeColetaInvalido());

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        invalidResponse = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.GET,
                requestEntity,
                String.class);

    }

    @E("tenho um ID de coleta invalido")
    public UUID tenhoUmIDDeColetaInvalido() {
        return UUID.randomUUID();
    }

    @Então("a resposta deve conter o status {int}")
    public void aRespostaDeveConterOStatus(int status) {
        assertThat(invalidResponse.getStatusCodeValue()).isEqualTo(status);
    }

    @E("tenho um ID de coleta valido")
    public UUID tenhoUmIDDeColetaValido() {
        return ColetaContext.getIdColeta();
    }

    @Quando("eu envio uma solicitação GET para {string} com a coleta valida")
    public void euEnvioUmaSolicitaçãoGETParaComAColetaValida(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("/").append(tenhoUmIDDeColetaValido());

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.GET,
                requestEntity,
                Coleta.class);
    }

    @E("tenho um Id de Caminhao Invalido")
    public UUID tenhoUmIdDeCaminhaoInvalido() {
        return UUID.randomUUID();
    }

    @Quando("eu envio uma solicitação GET para {string} com o caminhao invalido")
    public void euEnvioUmaSolicitaçãoGETParaComOCaminhaoInvalido(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("/").append(tenhoUmIdDeCaminhaoInvalido());

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        pageColetaResponse = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<PageResponse<Coleta>>() {});
    }

    @E("o corpo do Page Response vazio")
    public void oCorpoDoPageResponseVazio() {
        assertThat(pageColetaResponse.getBody().getContent()).isEmpty();
    }

    @Então("a resposta do page response deve conter o status {int}")
    public void aRespostaDoPageResponseDeveConterOStatus(int status) {
        assertThat(pageColetaResponse.getStatusCodeValue()).isEqualTo(status);
    }

    @E("tenho um Id de Caminhao Valido")
    public UUID tenhoUmIdDeCaminhaoValido() {
        return CaminhaoContext.getCaminhaoid();
    }

    @Quando("eu envio uma solicitação GET para {string} com o caminhao valido")
    public void euEnvioUmaSolicitaçãoGETParaComOCaminhaoValido(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("/").append(tenhoUmIdDeCaminhaoValido());

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        pageColetaResponse = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<PageResponse<Coleta>>() {});
    }

    @E("o corpo do Page Response nao pode estar vazio")
    public void oCorpoDoPageResponseNaoPodeEstarVazio() {
        assertThat(pageColetaResponse.getBody().getContent()).isNotEmpty();
    }

    @Quando("eu envio uma solicitação de coletas ativas GET para {string}")
    public void euEnvioUmaSolicitaçãoDeColetasAtivasGETPara(String endPoint) {
        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity<>(headers);

        pageColetaResponse = restTemplate.exchange(
                endPoint,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<PageResponse<Coleta>>() {}
        );
    }

    @Quando("eu envio uma solicitação de finalizar coleta POST para {string} com o id valido")
    public void euEnvioUmaSolicitaçãoDeFinalizarColetaPOSTParaComOIdValido(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("/").append(tenhoUmIDDeColetaValido());

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.POST,
                requestEntity,
                Coleta.class);

    }

    @E("o corpo da resposta com uma Coleta com o status COLETADO")
    public void oCorpoDaRespostaComUmaColetaComOStatusCOLETADO() {
        Coleta coleta = response.getBody();
        assertThat(coleta.getStatus()).isEqualTo(TiposStatus.COLETADO);
    }

    @Quando("eu envio uma solicitação para buscar coletas coletadas GET para {string}")
    public void euEnvioUmaSolicitaçãoParaBuscarColetasColetadasGETPara(String endPoint) {
        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity<>(headers);

        pageColetaResponse = restTemplate.exchange(
                endPoint,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<PageResponse<Coleta>>() {});

    }

    @E("o corpo da resposta com as coletas coletadas")
    public void oCorpoDaRespostaComAsColetasColetadas() {
        PageResponse<Coleta> coletasPage = pageColetaResponse.getBody();

        assertNotNull(coletasPage, "A Resposta do Endpoint e nula");

        assertNotNull(coletasPage.getContent(), "A lista de coletas está nula.");
        assertFalse(coletasPage.getContent().isEmpty(), "A lista de coletas está vazia.");

        for (Coleta coleta : coletasPage.getContent()) {
            assertEquals(TiposStatus.COLETADO, coleta.getStatus(),
                    "A coleta com ID " + coleta.getIdColeta() + " não tem o status COLETADO.");
        }
    }

    @Quando("eu envio uma solicitação GET para {string} para buscar todas as coletas")
    public void euEnvioUmaSolicitaçãoGETParaParaBuscarTodasAsColetas(String endPoint) {
        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity requestEntity = new HttpEntity<>(headers);

        pageColetaResponse = restTemplate.exchange(
                endPoint,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<PageResponse<Coleta>>() {}
        );
    }

    @E("o corpo da resposta com todas as coletas")
    public void oCorpoDaRespostaComTodasAsColetas() {
        assertNotNull(pageColetaResponse.getBody().getContent(), "A Resposta do Endpoint e nula");
        assertFalse(pageColetaResponse.getBody().getContent().isEmpty(), "O conteudo e nulo");
    }

    @Quando("eu envio uma solicitação de atualizacao PUT para {string} com os dados:")
    public void euEnvioUmaSolicitaçãoDeAtualizacaoPUTParaComOsDados(String endPoint, DataTable dataTable) {
        Map<String, String> coletaRow = dataTable.asMaps(String.class, String.class).get(0);

        UUID id = ColetaContext.getIdColeta();
        ColetaAtualizarDto coletaAtualizarDto = new ColetaAtualizarDto(
                id,
                coletaRow.get("cep"),
                coletaRow.get("numeroCasa"),
                TiposResiduos.valueOf(coletaRow.get("tipoResiduo")),
                new BigDecimal(coletaRow.get("volumePeso")),
                coletaRow.get("informacoesAdicionais")
        );

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ColetaAtualizarDto> requestEntity = new HttpEntity<>(coletaAtualizarDto, headers);

        response = restTemplate.exchange(
                endPoint,
                HttpMethod.PUT,
                requestEntity,
                Coleta.class
        );
    }

}
