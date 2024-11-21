package dasturlash.uz.kun_uz.dto.profile;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ProfileFilterDTO {
    private String name;
    private String surname;
    private String phone;
    private String role;
    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;
}
