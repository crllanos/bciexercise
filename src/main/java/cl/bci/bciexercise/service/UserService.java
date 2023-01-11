package cl.bci.bciexercise.service;

import cl.bci.bciexercise.dto.UserDTO;
import cl.bci.bciexercise.dto.UserResponseDTO;
import cl.bci.bciexercise.exception.EmailAlreadyExistsException;
import cl.bci.bciexercise.exception.WeakPasswordException;
import cl.bci.bciexercise.repository.UserRepository;
import cl.bci.bciexercise.validation.EmailValidator;
import cl.bci.bciexercise.validation.PasswordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
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
            throw new WeakPasswordException("Debe elegir una contraseña más segura.");
        }

        // valida email
        if(!emailValidator.isValidMail(user.getEmail())){
            throw new IllegalArgumentException("Formato de email es incorrecto.");
        }

        // 409 Conflict
        if (Objects.nonNull(userRepository.findUserByEmail(user.getEmail()))){
            throw new EmailAlreadyExistsException("El correo ya esta registrado.");
        }

        return null;
    }

}
