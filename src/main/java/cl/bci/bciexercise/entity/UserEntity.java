package cl.bci.bciexercise.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    private String name;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private List<PhoneEntity> phones;
    private LocalDate created;
    private LocalDate modified;
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive;

}
