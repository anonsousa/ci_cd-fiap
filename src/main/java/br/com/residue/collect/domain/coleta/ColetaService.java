package br.com.residue.collect.domain.coleta;

import br.com.residue.collect.domain.caminhao.Caminhao;
import br.com.residue.collect.domain.caminhao.CaminhaoRepository;
import br.com.residue.collect.domain.caminhao.TiposResiduos;
import br.com.residue.collect.infra.exceptions.ItemNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ColetaService {
    @Autowired
    private ColetaRepository coletaRepository;

    @Autowired
    private CaminhaoRepository caminhaoRepository;

    @Transactional
    public Coleta save(ColetaCadastroDto coletaCadastroDto){
        Coleta coleta = new Coleta();
        BeanUtils.copyProperties(coletaCadastroDto, coleta);
        List<Caminhao> caminhaoPage = caminhaoRepository.findCaminhoesComCapacidadeParaColeta(coletaCadastroDto.volumePeso());
        if (caminhaoPage.isEmpty()){
            coleta.setIdCaminhao(null);
        } else {
            Caminhao caminhao = caminhaoPage.get(0);
            coleta.setIdCaminhao(caminhao.getIdCaminhao());
        }
        coleta.setStatus(TiposStatus.ATIVO);
        return coletaRepository.save(coleta);
    }

    public Coleta findById(UUID idcoleta){
        Optional<Coleta> coletaOptional = coletaRepository.findById(idcoleta);
        if(coletaOptional.isPresent()){
            return coletaOptional.get();
        } else {
            throw new ItemNotFoundException("Coleta nao encontrada!");
        }
    }

    public Page<Coleta> findAll(Pageable pageable){
        return coletaRepository.findAll(pageable);
    }
}
