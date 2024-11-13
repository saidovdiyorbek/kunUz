package dasturlash.uz.kun_uz.repository;

import dasturlash.uz.kun_uz.entity.ArticleTag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleTagRepository extends CrudRepository<ArticleTag, String> {

    @Query("select at.articleId from ArticleTag at where at.tag = ?1")
    List<String> findTop4ByTag(String tag);
}
