package cl.bci.bciexercise.service.impl;

import cl.bci.bciexercise.dto.PhoneDTO;
import cl.bci.bciexercise.dto.UserDTO;
import cl.bci.bciexercise.entity.UserEntity;
import cl.bci.bciexercise.exception.WeakPasswordException;
import cl.bci.bciexercise.repository.UserRepository;
import cl.bci.bciexercise.validation.EmailValidator;
import cl.bci.bciexercise.validation.PasswordValidator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("classpath:application.yml")
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    UserRepository userRepository;
    @Mock
    private EmailValidator emailValidator;
    @Mock
    private PasswordValidator passwordValidator;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void shlouldNotSaveUser_invalidPassword(){
        WeakPasswordException exception = assertThrows(WeakPasswordException.class, () -> {
            userServiceImpl.createUser(UserDTO.builder().password("123").build());
        });
        String expectedMessage = "Debe elegir una password más segura.";
        String actualMessage = exception.getMensaje();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void shlouldSaveUser_Ok(){

        when(userRepository.findUserByEmail("user@dummy.com")).thenReturn(UserEntity.builder()
                        .id(UUID.randomUUID())
                        .name("Dummy User")
                        .email("user@dummy.com")
                        .isActive(true)
                        .build());

        List<PhoneDTO> phones = new ArrayList<>();
        phones.add(PhoneDTO.builder().number("76543211").citycode("2").contrycode("56").build());

        assertNotNull(userServiceImpl.createUser(UserDTO.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("AAAbbbCCC@123")
                .phones(phones)
                .build()));
    }
}