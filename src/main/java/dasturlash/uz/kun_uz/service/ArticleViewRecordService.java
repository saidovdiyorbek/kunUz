package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.entity.ArticleViewRecord;
import dasturlash.uz.kun_uz.repository.ArticleViewRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ArticleViewRecordService {

    private final ArticleViewRecordRepository articleViewRecordRepository;

    // TODO
    // 1. Trigger
    // 2. Function bilan
    public boolean increaseViewCount(String articleId, String ip) {
        boolean result = articleViewRecordRepository.existsByArticleIdAndIpAddress(articleId, ip);
        if (!result) {
            ArticleViewRecord entity = new ArticleViewRecord();
            entity.setArticleId(articleId);
            entity.setIpAddress(ip);
            entity.setCreatedDate(LocalDateTime.now());
            articleViewRecordRepository.save(entity);
            return true;
        }
        return false;
    }
}
