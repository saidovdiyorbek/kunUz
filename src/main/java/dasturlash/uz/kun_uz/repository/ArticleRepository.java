package dasturlash.uz.kun_uz.repository;

import dasturlash.uz.kun_uz.dto.article.ArticleFullInfo;
import dasturlash.uz.kun_uz.dto.article.ArticleShortInfo;
import dasturlash.uz.kun_uz.entity.Article;
import dasturlash.uz.kun_uz.enums.ArticleStatus;
import dasturlash.uz.kun_uz.mapper.ArticleShortInfoMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.lang.String;

public interface ArticleRepository extends CrudRepository<Article, String>, JpaRepository<Article, String>, PagingAndSortingRepository<Article, String> {

    @Query("from Article a where a in ?1")
    List<Article> findTop5ArticlesById(List<String> id);

    List<Article> findTop8ByIdNotInAndVisibleIsTrueOrderByCreatedDateDesc(List<String> excludeIds);

    Article findByIdAndVisibleIsTrue(String id);

    @Query("from Article where id in ?1 order by createdDate desc ")
    List<Article> findTop5(List<String> id);

    List<Article> findTop4ByVisibleIsTrueOrderByViewCountDesc();

    @Query("from Article a where a in ?1")
    List<Article> findTopNArticle(List<String> id, Pageable pageable);
    /*


    List<Article> findTop4ByTags_NameAndVisibleIsTrueOrderByCreatedDateDesc(String tagName);

    List<Article> findTop5ByArticleTypeAndRegionIdAndVisibleIsTrueOrderByCreatedDateDesc(String type, Integer regionKey);

    List<Article    > findByRegionIdAndVisibleIsTrue(Integer regionKey, Pageable pageable);

    List<Article    > findTop5ByCategoryIdAndVisibleIsTrueOrderByCreatedDateDesc(Integer categoryKey);

    List<Article    > findByCategoryIdAndVisibleIsTrue(Integer categoryKey, Pageable pageable);

    @Query("SELECT a FROM Article a WHERE (:id IS NULL OR a.id = :id) AND (:title IS NULL OR a.title LIKE %:title%) "
            + "AND (:regionId IS NULL OR a.region_id = :regionId) AND (:categoryId IS NULL OR a.category_id = :categoryId) "
            + "AND (:createdDateFrom IS NULL OR a.createdDate >= :createdDateFrom) "
            + "AND (:createdDateTo IS NULL OR a.createdDate <= :createdDateTo) "
            + "AND (:publishedDateFrom IS NULL OR a.publishedDate >= :publishedDateFrom) "
            + "AND (:publishedDateTo IS NULL OR a.publishedDate <= :publishedDateTo) "
            + "AND (:moderatorId IS NULL OR a.moderator_id = :moderatorId) "
            + "AND (:publisherId IS NULL OR a.publisher_id = :publisherId) "
            + "AND (:status IS NULL OR a.status = :status)")
    List<Article> filterArticles(String id, String title, Integer regionId, Integer categoryId,
                                 LocalDateTime createdDateFrom, LocalDateTime createdDateTo,
                                 LocalDateTime publishedDateFrom, LocalDateTime publishedDateTo,
                                 Integer moderatorId, Integer publisherId, String status, Pageable pageable);*/
}

