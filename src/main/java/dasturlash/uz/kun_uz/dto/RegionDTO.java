package dasturlash.uz.kun_uz.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class RegionDTO {
    private Integer id;


    private String name_uz;
    private String name_ru;
    private String name_en;
    private Integer orderNumber;

}
