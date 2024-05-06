package br.com.residue.collect.domain.motorista;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class MotoristaService {

    @Autowired
    private MotoristaRepository motoristaRepository;

    public MotoristaMostrarDto save(MotoristaCadastroDto motoristaCadastroDto){

        Motorista motorista = new Motorista();
        BeanUtils.copyProperties(motoristaCadastroDto, motorista);
        motorista.setDataCadastro(LocalDate.now());

        return new MotoristaMostrarDto(motoristaRepository.save(motorista));
    }
    
    public MotoristaMostrarDto findOneId(UUID uuid){
        Optional<Motorista> motorista = motoristaRepository.findById(uuid);
        if (motorista.isPresent()){
            return new MotoristaMostrarDto(motorista.get());
        } else {
            throw new RuntimeException("Motorista nao encontrado.");
        }
    }

    public Page<MotoristaMostrarDto> findAll(Pageable pageable){
        Page<Motorista> motoristaPage = motoristaRepository.findAll(pageable);
        return motoristaPage.map(MotoristaMostrarDto::new);
    }














}
