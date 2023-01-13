package cl.bci.bciexercise.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private transient String password;
    private String role;
    private List<PhoneDTO> phones;
}
