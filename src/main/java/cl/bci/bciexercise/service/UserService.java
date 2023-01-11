package cl.bci.bciexercise.service;

import cl.bci.bciexercise.dto.UserDTO;
import cl.bci.bciexercise.dto.UserResponseDTO;
import cl.bci.bciexercise.repository.UserRepository;
import cl.bci.bciexercise.validation.EmailValidator;
import cl.bci.bciexercise.validation.PasswordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;

    public UserResponseDTO createUser(UserDTO user){
        // valida la password
        if(!passwordValidator.isValid(user.getPassword())){
            throw new IllegalArgumentException("Debe elegir una contraseña más segura.");
        }

        // valida email
        if(!emailValidator.isValidMail(user.getEmail())){
            throw new IllegalArgumentException("Formato de email es incorrecto.");
        }
        if (Objects.nonNull(userRepository.findUserByEmail(user.getEmail()))){
            throw new IllegalArgumentException("El correo ya esta registrado.");
        }

        return null;
    }

}
