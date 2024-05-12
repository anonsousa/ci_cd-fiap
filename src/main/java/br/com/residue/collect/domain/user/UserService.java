package br.com.residue.collect.domain.user;

import br.com.residue.collect.infra.exceptions.InvalidPasswordException;
import br.com.residue.collect.infra.exceptions.ItemNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserMostrarDto save(UserCadastroDto userCadastroDto){
        String senhaEncryptada = new BCryptPasswordEncoder().encode(userCadastroDto.senha());

        User user = new User();
        BeanUtils.copyProperties(userCadastroDto, user);
        user.setSenha(senhaEncryptada);
        user.setRole(UserRole.USER);

        return new UserMostrarDto(userRepository.save(user));

    }

    public UserMostrarDto findById(UUID uuid){
        Optional<User> userOptional= userRepository.findById(uuid);
        if(userOptional.isPresent()){
            return new UserMostrarDto(userOptional.get());
        } else {
            throw new ItemNotFoundException("Usuario nao encontrado!");
        }
    }

    public UserMostrarDto updateUser(UserAtualizarDto userAtualizarDto){
        Optional<User> userOptional = userRepository.findById(userAtualizarDto.userId());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(user.getPassword().equals(userAtualizarDto.senha())){
                BeanUtils.copyProperties(userAtualizarDto, user);
                return new UserMostrarDto(userRepository.save(user));
            } else {
                throw new InvalidPasswordException("Senha invalida!");
            }
        } else {
            throw new ItemNotFoundException("Id do usuario nao encontrado!");
        }
    }

    public void deleteUser(UUID uuid){
        Optional<User> userOptional = userRepository.findById(uuid);
        if(userOptional.isPresent()){
            userRepository.deleteById(uuid);
        } else{
            throw new ItemNotFoundException("Usuario nao encontrado!");
        }
    }
}
