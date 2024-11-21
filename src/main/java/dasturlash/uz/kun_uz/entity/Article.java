package dasturlash.uz.kun_uz.entity;

import dasturlash.uz.kun_uz.enums.ArticleStatus;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer shared_count = 0;

    @Column
    private String image_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", updatable = false, insertable = false)
    private Attach image;

    @Column(nullable = false)
    private Integer region_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id",updatable = false, insertable = false)
    private Region region;

    @Column(name = "category_id", nullable = false)
    private Integer category_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", updatable = false, insertable = false)
    private Category category;

    @Column(nullable = false)
    private Integer moderator_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", updatable = false, insertable = false)
    private Profile moderator;

    @Column
    private Integer publisher_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", updatable = false, insertable = false)
    private Profile publisher;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime publishedDate;

    @Column(nullable = false)
    private Boolean visible;

    @Column
    private Integer viewCount;

    @Column
    private Integer likeCount = 0;

    @Column
    private Integer dislikeCount = 0;


}
