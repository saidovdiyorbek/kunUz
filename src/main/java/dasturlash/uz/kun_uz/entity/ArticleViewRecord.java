package dasturlash.uz.kun_uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "article_view_record_entity")
public class ArticleViewRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "article_id")
    private String articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", updatable = false, insertable = false)
    private Article article;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}