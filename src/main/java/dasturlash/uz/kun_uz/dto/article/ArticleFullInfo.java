package dasturlash.uz.kun_uz.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ArticleFullInfo {
    private String id;
    private String title;
    private String content;
    private String description;
    private Integer sharedCount;
    private Integer regionId;
    private Integer categoryId;
    private LocalDateTime publishedDate;
    private Integer viewCount;
    private Integer likeCount;
    private List<String> tagList;



}
