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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;

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

        this.saveUser(user);

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

    @Override
    public void saveUser(UserDTO user) {
        UUID id = UUID.randomUUID();

        List<PhoneEntity> phones = new ArrayList<>();
        if(Objects.nonNull(user.getPhones())){
            for(PhoneDTO p : user.getPhones()){
                phones.add(PhoneEntity.builder()
                        .citycode(p.getCitycode())
                        .contrycode(p.getContrycode())
                        .number(p.getNumber())
                        .build());
            }
        }

        userRepository.save(UserEntity.builder()
                .id(id)
                .email(user.getEmail())
                .name(user.getName())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole())
                .phones(phones)
                .created(LocalDate.now())
                .modified(LocalDate.now())
                .lastLogin(LocalDateTime.now())
                .token(id.toString())
                .isActive(true)
                .build());

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if(Objects.isNull(userEntity)){
            throw new UsernameNotFoundException("Invalid credentials.");
        }
        Collection<SimpleGrantedAuthority> authRol = new ArrayList<>();
        authRol.add(new SimpleGrantedAuthority(userEntity.getRole()));
        return new User(userEntity.getEmail(), userEntity.getPassword(), authRol);
    }
}
