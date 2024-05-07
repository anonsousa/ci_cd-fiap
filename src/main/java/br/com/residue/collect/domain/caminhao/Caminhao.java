package br.com.residue.collect.domain.caminhao;

import br.com.residue.collect.domain.motorista.Motorista;
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
    private Motorista motorista;

    private String placa;
    private String modelo;
    @Column(unique = true)
    private String renavam;
    @Column(name = "tipo_residuo")
    @Enumerated(EnumType.STRING)
    private TiposdeResiduos tiposdeResiduos;
    @Column(name = "bairro_coleta")
    private String bairroColeta;
    private BigDecimal capacidade;


    public UUID getIdCaminhao() {
        return idCaminhao;
    }

    public void setIdCaminhao(UUID idCaminhao) {
        this.idCaminhao = idCaminhao;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
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

    public TiposdeResiduos getTiposdeResiduos() {
        return tiposdeResiduos;
    }

    public void setTiposdeResiduos(TiposdeResiduos tiposdeResiduos) {
        this.tiposdeResiduos = tiposdeResiduos;
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
