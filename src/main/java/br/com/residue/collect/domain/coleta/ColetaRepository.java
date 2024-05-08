package br.com.residue.collect.domain.coleta;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ColetaRepository extends JpaRepository<Coleta, UUID> {


}
