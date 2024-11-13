package dasturlash.uz.kun_uz.mapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

public interface EmailHistoryInfoMapper {
    LocalDateTime getCreatedDate();
}
