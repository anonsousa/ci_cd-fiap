package br.com.residue.collect.domain.coleta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ColetaRepository extends JpaRepository<Coleta, UUID> {

    @Query("SELECT c FROM Coleta c WHERE c.status = br.com.residue.collect.domain.coleta.TiposStatus.ATIVO")
    Page<Coleta> findByStatusAtivo(Pageable pageable);

    @Query("SELECT c FROM Coleta c WHERE c.status = br.com.residue.collect.domain.coleta.TiposStatus.COLETADO")
    Page<Coleta> findByStatusColetado(Pageable pageable);

}
