package dasturlash.uz.kun_uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
public class ArticleTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(name = "article_id")
    private String  articleId;  // ID for the Article

    @Column(name = "article_type_id")
    private Integer articleTypeId; // ID for the ArticleType


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", updatable = false, insertable = false)
    private Article article;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_type_id", updatable = false, insertable = false)
    private ArticleType articleType; // SectionEntity

}
