package dasturlash.uz.kun_uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class SmsHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String phone;

    @Column
    private String message;

    private LocalDateTime createdDate;

    @Column
    private Integer attemptCount = 0;
}
