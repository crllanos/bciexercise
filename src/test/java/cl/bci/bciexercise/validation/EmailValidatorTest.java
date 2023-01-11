package cl.bci.bciexercise.validation;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailValidatorTest {

    @InjectMocks
    private EmailValidator emailValidator;

    @Test
    public void shouldCheckValidEmails(){
        assertTrue(emailValidator.isValidMail("username@domain.com"));
        assertTrue(emailValidator.isValidMail("user.name@domain.cl"));
        assertTrue(emailValidator.isValidMail("user-name@domain.cl"));
        assertTrue(emailValidator.isValidMail("username@domain.com.ar"));
        assertTrue(emailValidator.isValidMail("user_name@domain.cl"));
    }

    @Test
    public void shouldCheckInvalidEmails(){
        assertFalse(emailValidator.isValidMail("username.@domain.cl"));
        assertFalse(emailValidator.isValidMail(".user.name@domain.cl"));
        assertFalse(emailValidator.isValidMail("user-name@domain.cl."));
        assertFalse(emailValidator.isValidMail("username@.cl"));
    }

}