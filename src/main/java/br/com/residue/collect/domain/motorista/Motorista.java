package br.com.residue.collect.domain.motorista;

import br.com.residue.collect.domain.caminhao.Caminhao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tbl_motorista")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idMotorista;

    @OneToOne(mappedBy = "motorista")
    @JsonIgnore
    private Caminhao caminhao;

    private String nome;
    @Column(unique = true)
    private String email;
    private String telefone;

    @Column(name = "cateira_habilitacao", unique = true)
    private String carteiraHabilitacao;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;


    public Caminhao getCaminhao() {
        return caminhao;
    }

    public void setCaminhao(Caminhao caminhao) {
        this.caminhao = caminhao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(UUID idMotorista) {
        this.idMotorista = idMotorista;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCarteiraHabilitacao() {
        return carteiraHabilitacao;
    }

    public void setCarteiraHabilitacao(String carteiraHabilitacao) {
        this.carteiraHabilitacao = carteiraHabilitacao;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
