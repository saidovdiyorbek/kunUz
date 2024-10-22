package dasturlash.uz.kun_uz.repository;

import dasturlash.uz.kun_uz.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ArticleRepository extends CrudRepository<Article, UUID> {
}
