package dasturlash.uz.kun_uz.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlash.uz.kun_uz.entity.Attach;
import dasturlash.uz.kun_uz.enums.ProfileRole;
import dasturlash.uz.kun_uz.enums.ProfileStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    /*name,surname,email,phone,password,status,role*/
    private Integer id;
    @NotNull
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    private String surname;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    @Size(min = 6, message = "Password must be greater than 6")
    private String password;

    private ProfileStatus profileStatus;

    private ProfileRole role;

    private String jwtToken;

    private Attach photo;





}
