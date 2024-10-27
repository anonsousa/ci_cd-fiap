package br.com.residue.collect.cucumber.steps;

import br.com.residue.collect.cucumber.constant.*;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteEntitySteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    @E("tenho um ID de coleta valida")
    public UUID tenhoUmIDDeColetaValida() {
        return ColetaContext.getIdColeta();
    }

    @Quando("eu envio uma solicitação DELETE para {string} com a coleta que desejo deletar")
    public void euEnvioUmaSolicitaçãoDELETEParaComAColetaQueDesejoDeletar(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("/").append(tenhoUmIDDeColetaValida());

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );
    }

    @Então("a resposta da delecao deve conter o status {int}")
    public void aRespostaDaDelecaoDeveConterOStatus(int status) {
        assertThat(response.getStatusCodeValue()).isEqualTo(status);
    }

    @E("tenho um ID de um caminhao")
    public UUID tenhoUmIDDeUmCaminhao() {
        return CaminhaoContext.getCaminhaoid();
    }

    @Quando("eu envio uma solicitação DELETE para {string} com o caminhao que desejo deletar o relacionamento")
    public void euEnvioUmaSolicitaçãoDELETEParaComOCaminhaoQueDesejoDeletarORelacionamento(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("?idCaminhao=").append(tenhoUmIDDeUmCaminhao());

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );
    }

    @E("o corpo da resposta deve conter a mensagem de delecao")
    public void oCorpoDaRespostaDeveConterAMensagemDeDelecao() {
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        String body = response.getBody();

        UUID idCaminhao = tenhoUmIDDeUmCaminhao();
        String mensagemEsperada = String.format("Caminhao de id: %s agora encontra-se sem motorista!", idCaminhao);

        assertTrue(body.contains(mensagemEsperada), "A mensagem de deleção não está correta: " + body);
    }

    @E("tenho um ID de um motorista")
    public UUID tenhoUmIDDeUmMotorista() {
        return MotoristaContext.getMotoristaId();
    }

    @Quando("eu envio uma solicitação DELETE para {string} com o motorista que desejo deletar")
    public void euEnvioUmaSolicitaçãoDELETEParaComOMotoristaQueDesejoDeletar(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("/").append(tenhoUmIDDeUmMotorista());

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );
    }

    @E("tenho um ID de um caminhao para deletar")
    public UUID tenhoUmIDDeUmCaminhaoParaDeletar() {
        return CaminhaoContext.getCaminhaoid();
    }

    @Quando("eu envio uma solicitação DELETE para {string} com o caminhao que desejo deletar")
    public void euEnvioUmaSolicitaçãoDELETEParaComOCaminhaoQueDesejoDeletar(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("/").append(tenhoUmIDDeUmCaminhao());

        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );
    }

    @E("tenho um ID de um usuario")
    public UUID tenhoUmIDDeUmUsuario() {
        return UserContext.getUserId();
    }

    @Quando("eu envio uma solicitação DELETE para {string} com o usuario que desejo deletar")
    public void euEnvioUmaSolicitaçãoDELETEParaComOUsuarioQueDesejoDeletar(String endPoint) {
        StringBuilder urlBuilder = new StringBuilder(endPoint);
        urlBuilder.append("/").append(tenhoUmIDDeUmUsuario());


        String generateToken = "Bearer " + TokenContext.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", generateToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );
    }
}
