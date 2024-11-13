package dasturlash.uz.kun_uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ArticleTag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String tag;

    @Column( nullable = false)
    private String articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private Article article;
}
