package dasturlash.uz.kun_uz.repository;

import dasturlash.uz.kun_uz.entity.ArticleLike;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleLikeRepository extends CrudRepository<ArticleLike, String> {


    @Query("SELECT COUNT(al) > 0 FROM ArticleLike al WHERE al.articleId = :articleId AND al.profileId = :profileId")
    boolean existsByArticleIdAndProfileId(@Param("articleId") String articleId, @Param("profileId") Integer profileId);

    @Transactional
    @Modifying
    @Query("delete from ArticleLike where articleId = :articleId and profileId = :profileId")
    void deleteByArticleIdAndProfileId(@Param("articleId") String articleId, @Param("profileId") Integer profileId);

}
