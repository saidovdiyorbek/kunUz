package dasturlash.uz.kun_uz.repository;

import dasturlash.uz.kun_uz.entity.ArticleTypes;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleTypesRepository extends CrudRepository<ArticleTypes,String > {

    @Query("select articleType from ArticleTypes where articleId = ?1")
    List<Integer> findAllByArticleId(String articleId);




    @Transactional
    @Modifying
    @Query("delete  from ArticleTypes where articleId = ?1 and articleTypeId =?2 ")
    void deleteByArticleIdAndArticleTypeId(String articleId, Integer articleTypeId);

    @Query("select aty.articleId from ArticleTypes aty where aty.article.status = 'PUBLISHED' and aty.articleTypeId = ?1")
    List<String> findTop5ByArticleTypeIdOrderByIdDesc(String articleTypeId, Pageable pageable);
}
