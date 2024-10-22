package dasturlash.uz.kun_uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Setter
@Getter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer shared_count;

    @Column
    private Integer image_id;

    @Column(nullable = false)
    private Integer region_id;

    @Column(nullable = false)
    private Integer category_id;

    @Column(nullable = false)
    private Integer moderator_id;

    @Column(nullable = false)
    private Integer publisher_id;

    @Column(nullable = false)
    private String  status;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime publishedDate;

    @Column(nullable = false)
    private Boolean visible;

    @Column
    private Integer viewCount;

    @OneToMany(mappedBy = "article")
    private List<ArticleType> articleType;

}
