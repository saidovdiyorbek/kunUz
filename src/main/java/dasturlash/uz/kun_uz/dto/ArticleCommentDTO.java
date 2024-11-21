package dasturlash.uz.kun_uz.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleCommentDTO {
    private String id;
    private String comment;
    private String articleId;
    private Integer profileId;
    private String replyId;


}
