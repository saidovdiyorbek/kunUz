package dasturlash.uz.kun_uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ArticleViewTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String articleId;

    @Column(nullable = false)
    private String deviceId; // e.g., unique device identifier or IP address

    @Column(nullable = false)
    private LocalDateTime viewDate;

}
