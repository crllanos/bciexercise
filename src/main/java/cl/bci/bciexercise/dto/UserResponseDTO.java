package cl.bci.bciexercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String id; // @todo pasar a UUID
    private String name;
    private String email;
    private String password;
    private List<PhoneDTO> phones;
    private LocalDate created;
    private LocalDate modified;
    @JsonProperty("last_login")
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive;
}
