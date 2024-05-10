package br.com.residue.collect.domain.caminhao;

import br.com.residue.collect.domain.motorista.Motorista;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tbl_caminhao")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Caminhao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idCaminhao;

    @OneToOne
    @JoinColumn(name = "motorista_id")
    @JsonBackReference
    private Motorista motorista;

    private String placa;
    private String modelo;
    @Column(unique = true)
    private String renavam;
    @Column(name = "tipo_residuo")
    @Enumerated(EnumType.STRING)
    private TiposResiduos tiposResiduos;
    @Column(name = "bairro_coleta")
    private String bairroColeta;

    private BigDecimal capacidade;


    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public UUID getIdCaminhao() {
        return idCaminhao;
    }

    public void setIdCaminhao(UUID idCaminhao) {
        this.idCaminhao = idCaminhao;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public TiposResiduos getTiposdeResiduos() {
        return tiposResiduos;
    }

    public void setTiposdeResiduos(TiposResiduos tiposResiduos) {
        this.tiposResiduos = tiposResiduos;
    }

    public String getBairroColeta() {
        return bairroColeta;
    }

    public void setBairroColeta(String bairroColeta) {
        this.bairroColeta = bairroColeta;
    }

    public BigDecimal getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(BigDecimal capacidade) {
        this.capacidade = capacidade;
    }
}
