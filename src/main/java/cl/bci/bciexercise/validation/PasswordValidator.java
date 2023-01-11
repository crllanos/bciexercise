package cl.bci.bciexercise.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordValidator {

    @Value("${application.security.password.pattern}")
    private String pwdPattern;

    public Boolean isValid(String password){
        return Pattern.compile(pwdPattern)
                .matcher(password)
                .matches();
    }
}
