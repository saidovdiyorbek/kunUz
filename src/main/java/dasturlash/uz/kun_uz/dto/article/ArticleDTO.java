package dasturlash.uz.kun_uz.dto.article;

import dasturlash.uz.kun_uz.enums.ArticleStatus;
import dasturlash.uz.kun_uz.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class ArticleDTO {
    private String title;
    private String description;
    private String content;
    private String image_id;
    private Integer region_id;
    private Integer category_id;
    private List<Integer> articleTypesList;
    private String urlLike;
    private String urlDislike;


}
