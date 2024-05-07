package br.com.residue.collect.domain.caminhao;

import br.com.residue.collect.infra.exceptions.ItemNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CaminhaoService {

    @Autowired
    CaminhaoRepository caminhaoRepository;


    public Caminhao save(CaminhaoCadastroDto caminhaoCadastroDto){

        Caminhao caminhao = new Caminhao();
        BeanUtils.copyProperties(caminhaoCadastroDto, caminhao);
        if (caminhao.getTiposdeResiduos() == null){
            caminhao.setTiposdeResiduos(TiposdeResiduos.CONSTRUCAOeDEMOLICAO);
        }
        return caminhaoRepository.save(caminhao);
    }

    public Caminhao findById(UUID uuid){
        Optional<Caminhao> caminhaoOptional = caminhaoRepository.findById(uuid);
        if(caminhaoOptional.isPresent()){
            return caminhaoOptional.get();
        } else {
            throw new ItemNotFoundException("Caminhao nao encontrado!");
        }
    }

    public Page<Caminhao> findAll(Pageable pageable){
        return caminhaoRepository.findAll(pageable);
    }

    public void delete(UUID uuid){
        Optional<Caminhao> caminhaoOptional = caminhaoRepository.findById(uuid);
        if(caminhaoOptional.isPresent()){
            caminhaoRepository.deleteById(uuid);
        } else {
            throw new ItemNotFoundException("Caminhao nao encontrado!");
        }

    }

    public CaminhaoMostrarDto update(CaminhaoAtualizarDto caminhaoAtualizarDto){
        Optional<Caminhao> caminhaoOptional = caminhaoRepository.findById(caminhaoAtualizarDto.idCaminhao());
        if (caminhaoOptional.isPresent()){
            Caminhao caminhao = caminhaoOptional.get();
            BeanUtils.copyProperties(caminhaoAtualizarDto, caminhao);
            return new CaminhaoMostrarDto(caminhaoRepository.save(caminhao));
        } else {
            throw new ItemNotFoundException("Caminhao nao encontrado!");
        }
    }
}
