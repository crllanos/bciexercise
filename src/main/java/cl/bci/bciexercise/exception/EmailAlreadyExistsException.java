package cl.bci.bciexercise.exception;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailAlreadyExistsException extends RuntimeException {
    private String mensaje;
}

