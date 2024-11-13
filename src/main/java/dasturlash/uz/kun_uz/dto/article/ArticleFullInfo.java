package dasturlash.uz.kun_uz.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ArticleFullInfo {
    private UUID id;                   // Article ID
    private String title;               // Sarlavha
    private String content;             // To‘liq matn
    private String description;         // Tavsif
    private Integer sharedCount;        // Bo‘lishilgan soni
    private Integer imageId;            // Rasm ID
    private Integer regionId;           // Hudud ID
    private Integer categoryId;         // Kategoriya ID
    private Integer moderatorId;        // Moderator ID
    private Integer publisherId;        // Nashriyotchi ID
    private String status;              // Status (nashr etilgan/nashr etilmagan)
    private LocalDateTime createdDate;  // Yaratilgan sana
    private LocalDateTime publishedDate; // Nashr etilgan sana
    private Boolean visible;            // Ko‘rinish holati
    private Integer viewCount;          // Ko‘rish soni
    private List<String> articleTypes;  // Maqola turlari (misol uchun, ["News", "Sports"])

    // Qo‘shimcha maydonlar qo‘shilishi mumkin, agar kerak bo‘lsa.
}
