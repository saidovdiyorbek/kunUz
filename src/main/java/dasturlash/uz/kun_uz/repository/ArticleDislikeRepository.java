package dasturlash.uz.kun_uz.repository;

import dasturlash.uz.kun_uz.entity.ArticleDislike;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleDislikeRepository extends CrudRepository<ArticleDislike, String> {


    @Query("SELECT COUNT(al) > 0 FROM ArticleDislike al WHERE al.articleId = :articleId AND al.profileId = :profileId")
    boolean existsByArticleIdAndProfileId(@Param("articleId") String articleId, @Param("profileId") Integer profileId);

    @Transactional
    @Modifying
    @Query("delete from ArticleDislike where articleId = :articleId and profileId = :profileId")
    void deleteByArticleIdAndProfileId(@Param("articleId") String articleId, @Param("profileId") Integer profileId);


}
