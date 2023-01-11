package cl.bci.bciexercise.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {
    private String number;
    private String citycode;
    private String contrycode;
}
