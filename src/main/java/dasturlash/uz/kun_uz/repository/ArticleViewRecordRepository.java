package dasturlash.uz.kun_uz.repository;

import dasturlash.uz.kun_uz.entity.ArticleViewRecord;
import org.springframework.data.repository.CrudRepository;

public interface ArticleViewRecordRepository extends CrudRepository<ArticleViewRecord, Long> {
    boolean existsByArticleIdAndIpAddress(String articleId, String ip);
}
