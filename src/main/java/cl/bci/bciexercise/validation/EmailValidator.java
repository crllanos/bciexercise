package cl.bci.bciexercise.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidator {

    private final String mailRegexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public Boolean isValidMail(String email) {
        return Pattern.compile(mailRegexPattern)
                .matcher(email)
                .matches();
    }
}
