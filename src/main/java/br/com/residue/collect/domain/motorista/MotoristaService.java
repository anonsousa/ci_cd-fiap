package br.com.residue.collect.domain.motorista;


import br.com.residue.collect.infra.exceptions.ItemNotFoundException;
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

    public void deleteById(UUID uuid){
        Optional<Motorista> motoristaOptional = motoristaRepository.findById(uuid);
        if (motoristaOptional.isPresent()){
            motoristaRepository.deleteById(motoristaOptional.get().getIdMotorista());
        } else {
            throw new ItemNotFoundException("Motorista nao encontrado.");
        }
    }

    public MotoristaMostrarDto update(MotoristaAtualizarDto motoristaAtualizarDto){
        Optional<Motorista> motoristaOptional = motoristaRepository.findById(motoristaAtualizarDto.idMotorista());
        if (motoristaOptional.isPresent()){
            Motorista motorista = motoristaOptional.get();
            BeanUtils.copyProperties(motoristaAtualizarDto, motorista);
            return new MotoristaMostrarDto(motoristaRepository.save(motorista));
        } else {
            throw  new ItemNotFoundException("Motorista nao encontrado, verifique o Id");
        }
    }














}
