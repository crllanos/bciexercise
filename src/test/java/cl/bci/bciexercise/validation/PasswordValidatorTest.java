package cl.bci.bciexercise.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PasswordValidatorTest {

    @InjectMocks
    private PasswordValidator passwordValidator;

    @Value("${application.security.password.pattern}")
    private String pwdPattern;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(passwordValidator, "pwdPattern", pwdPattern);
    }

    @Test
    public void shouldCheckValidPassword(){
        assertTrue(passwordValidator.isValid("AAAbbbccc@123"));
        assertTrue(passwordValidator.isValid("A!@#&()–a1"));
        assertTrue(passwordValidator.isValid("A~$^+=<>a1"));
        assertTrue(passwordValidator.isValid("0123456789$abcdefgAB"));
    }

    @Test
    public void shouldCheckInvalidPassword(){
        assertFalse(passwordValidator.isValid("a"));
        assertFalse(passwordValidator.isValid("admin"));
        assertFalse(passwordValidator.isValid("123"));
        assertFalse(passwordValidator.isValid("password"));
    }
}