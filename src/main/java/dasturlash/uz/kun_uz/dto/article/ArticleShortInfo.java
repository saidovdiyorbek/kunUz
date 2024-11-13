package dasturlash.uz.kun_uz.dto.article;

import dasturlash.uz.kun_uz.dto.AttachDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ArticleShortInfo {
    private String id;
    private String title;
    private String description;
    private String image;
    private LocalDateTime publishedDate;

}
