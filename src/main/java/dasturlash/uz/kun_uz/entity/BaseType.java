package dasturlash.uz.kun_uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
public abstract class BaseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name_uz;

    @Column(nullable = false, unique = true)
    private String name_ru;

    @Column(nullable = false, unique = true)
    private String name_en;

    @Column(nullable = false)
    private Boolean visible;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private Integer orderNumber;
}
