package br.com.residue.collect.domain.caminhao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CaminhaoRepository extends JpaRepository<Caminhao, UUID> {
}
