package br.com.residue.collect.cucumber.steps;

import br.com.residue.collect.cucumber.constant.CaminhaoContext;
import br.com.residue.collect.cucumber.constant.MotoristaContext;
import br.com.residue.collect.cucumber.constant.TokenContext;
import br.com.residue.collect.cucumber.stepsconfiguration.PageResponse;
import br.com.residue.collect.domain.caminhao.Caminhao;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RelacionamentoSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    private List<ResponseEntity<PageResponse<Caminhao>>> caminhoesResponse;

    @E("tenho um id de caminhao invalido")
    public UUID tenhoUmIdDeCaminhaoInvalido() {
        return UUID.randomUUID();
    }

    @Quando("eu envio uma solicitação POST para {string} com id do caminhao errado")
    public void euEnvioUmaSolicitaçãoPOSTParaComIdDoCaminhaoErrado(String endPoint) {
        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        UUID motoristaId = MotoristaContext.getMotoristaId();
        UUID caminhaoId = tenhoUmIdDeCaminhaoInvalido();

        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("?idMotorista=").append(motoristaId);
        urlBuilder.append("&idCaminhao=").append(caminhaoId);

        HttpEntity requestEntity = new HttpEntity(headers);

        response = restTemplate.postForEntity(urlBuilder.toString(), requestEntity, String.class);
    }

    @Então("o retorno deve ter status {int}")
    public void oRetornoDeveTerStatus(int status) {
        assertThat(response.getStatusCodeValue()).isEqualTo(status);
    }

    @E("tenho um id de motorista invalido")
    public UUID tenhoUmIdDeMotoristaInvalido() {
        return UUID.randomUUID();
    }

    @Quando("eu envio uma solicitação POST para {string} com id do motorista errado")
    public void euEnvioUmaSolicitaçãoPOSTParaComIdDoMotoristaErrado(String endPoint) {
        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        UUID motoristaId = tenhoUmIdDeMotoristaInvalido();
        UUID caminhaoId = CaminhaoContext.getCaminhaoid();

        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("?idMotorista=").append(motoristaId);
        urlBuilder.append("&idCaminhao=").append(caminhaoId);

        HttpEntity requestEntity = new HttpEntity(headers);

        response = restTemplate.postForEntity(urlBuilder.toString(), requestEntity, String.class);
    }

    @E("tenho um id de motorista valido")
    public UUID tenhoUmIdDeMotoristaValido() {
        return MotoristaContext.getMotoristaId();
    }

    @E("tenho um id de caminhao valido")
    public UUID tenhoUmIdDeCaminhaoValido() {
        return CaminhaoContext.getCaminhaoid();
    }

    @Quando("eu envio uma solicitação POST para {string} com os ids corretos")
    public void euEnvioUmaSolicitaçãoPOSTParaComOsIdsCorretos(String endPoint) {
        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        UUID motoristaId = tenhoUmIdDeMotoristaValido();
        UUID caminhaoId = tenhoUmIdDeCaminhaoValido();

        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("?idMotorista=").append(motoristaId);
        urlBuilder.append("&idCaminhao=").append(caminhaoId);

        HttpEntity requestEntity = new HttpEntity(headers);

        response = restTemplate.postForEntity(urlBuilder.toString(), requestEntity, String.class);
    }

    @Quando("eu envio uma solicitação GET para {string} para buscar todos os caminhoes com motorista")
    public void euEnvioUmaSolicitaçãoGETParaParaBuscarTodosOsCaminhoesComMotorista(String endPoint) {
        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity(headers);

        caminhoesResponse = Collections.singletonList(
                restTemplate.exchange(
                        endPoint,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<PageResponse<Caminhao>>() {}
                ));

    }

    @E("o retorno deve conter todos os caminhoes que tem motoristas")
    public void oRetornoDeveConterTodosOsCaminhoesQueTemMotoristas() {
        List<Caminhao> caminhaoList = caminhoesResponse.stream()

                .flatMap(entity -> {
                    PageResponse<Caminhao> pageResponse = entity.getBody();
                    return pageResponse != null? pageResponse.getContent().stream() : Stream.empty();
                })

                .collect(Collectors.toList());

        if (!caminhaoList.isEmpty()) {
            assertEquals(CaminhaoContext.getCaminhaoid(), caminhaoList.getFirst().getIdCaminhao());
        } else {
            throw new AssertionError("A Lista de caminhoes esta vazia.");
        }
    }

    @Então("o retorno deve conter o status {int}")
    public void oRetornoDeveConterOStatus(int status) {
        assertThat(caminhoesResponse.getFirst().getStatusCodeValue()).isEqualTo(status);
    }
}
