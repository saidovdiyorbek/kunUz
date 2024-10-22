package dasturlash.uz.kun_uz.dto;

import dasturlash.uz.kun_uz.enums.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ProfileDTO {
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

    private Role role;



    private LocalDate createdDate;
}
