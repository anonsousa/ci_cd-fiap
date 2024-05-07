package br.com.residue.collect.domain.caminhao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CaminhaoRepository extends JpaRepository<Caminhao, UUID> {

    @Query("SELECT c FROM Caminhao c WHERE c.motorista IS NOT NULL")
    Page<Caminhao> findAllWithMotorista(Pageable pageable);
}
