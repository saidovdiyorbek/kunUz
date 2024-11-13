package dasturlash.uz.kun_uz.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PostDTO {
    private Integer id;
    private String title;
    private String content;
    private List<AttachDTO> attachList;
}
