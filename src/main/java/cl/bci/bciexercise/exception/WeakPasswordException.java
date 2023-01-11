package cl.bci.bciexercise.exception;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeakPasswordException extends RuntimeException {
    private String mensaje;
}
