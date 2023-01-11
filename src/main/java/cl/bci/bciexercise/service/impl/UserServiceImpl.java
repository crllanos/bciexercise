package cl.bci.bciexercise.service.impl;

import cl.bci.bciexercise.dto.PhoneDTO;
import cl.bci.bciexercise.dto.UserDTO;
import cl.bci.bciexercise.dto.UserResponseDTO;
import cl.bci.bciexercise.entity.PhoneEntity;
import cl.bci.bciexercise.entity.UserEntity;
import cl.bci.bciexercise.exception.EmailAlreadyExistsException;
import cl.bci.bciexercise.exception.WeakPasswordException;
import cl.bci.bciexercise.repository.UserRepository;
import cl.bci.bciexercise.service.UserService;
import cl.bci.bciexercise.validation.EmailValidator;
import cl.bci.bciexercise.validation.PasswordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;

    @Transactional
    public UserResponseDTO createUser(UserDTO user){

        if(!passwordValidator.isValid(user.getPassword())){
            throw new WeakPasswordException("Debe elegir una contraseña más segura.");
        }

        if(!emailValidator.isValidMail(user.getEmail())){
            throw new IllegalArgumentException("Formato de email es incorrecto.");
        }

        if (Objects.nonNull(userRepository.findUserByEmail(user.getEmail()))){
            throw new EmailAlreadyExistsException("El correo ya esta registrado.");
        }

        List<PhoneEntity> phones = new ArrayList<>();
        for(PhoneDTO p : user.getPhones()){
            phones.add(PhoneEntity.builder()
                    .citycode(p.getCitycode())
                    .contrycode(p.getContrycode())
                    .number(p.getNumber())
                    .build());
        }

        userRepository.save(UserEntity.builder()
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .phones(phones)
                .created(LocalDate.now())
                .modified(LocalDate.now())
                .lastLogin(LocalDateTime.now())
                .token("token") // @fixme
                .isActive(true)
                .build());

        UserEntity userEntity = userRepository.findUserByEmail(user.getEmail());

        return UserResponseDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .phones(user.getPhones())
                .created(userEntity.getCreated())
                .modified(userEntity.getModified())
                .lastLogin(userEntity.getLastLogin())
                .token(userEntity.getToken())
                .isActive(userEntity.getIsActive())
                .build();
    }

}
