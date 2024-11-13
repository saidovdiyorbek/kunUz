package dasturlash.uz.kun_uz.repository;

import dasturlash.uz.kun_uz.entity.ArticleViewTracker;
import org.springframework.data.repository.CrudRepository;

public interface ArticleViesTrackerRepository extends CrudRepository<ArticleViewTracker, Long> {
    boolean existsByArticleIdAndDeviceId(String articleId, String deviceId);
}
