package dasturlash.uz.kun_uz.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
