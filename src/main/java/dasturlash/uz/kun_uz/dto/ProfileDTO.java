package dasturlash.uz.kun_uz.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ProfileDTO {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String surname;
    private LocalDate createdDate;
}
