package cl.bci.bciexercise.exception;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException {
    private String mensaje;
}
