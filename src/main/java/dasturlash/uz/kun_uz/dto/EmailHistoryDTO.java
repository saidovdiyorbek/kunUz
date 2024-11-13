package dasturlash.uz.kun_uz.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmailHistoryDTO {
    private String email;
    private String message;
    private LocalDateTime createDate;

}
