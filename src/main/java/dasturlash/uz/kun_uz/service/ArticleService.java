package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.dto.article.ArticleDTO;
import dasturlash.uz.kun_uz.dto.article.ArticleShortInfo;
import dasturlash.uz.kun_uz.entity.Article;
import dasturlash.uz.kun_uz.entity.ArticleViewTracker;
import dasturlash.uz.kun_uz.enums.ArticleStatus;
import dasturlash.uz.kun_uz.exp.AppBadException;
import dasturlash.uz.kun_uz.repository.*;
import dasturlash.uz.kun_uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    ArticleTypesService articleTypesService;
    @Autowired
    AttachService attachService;
    @Autowired
    ArticleViesTrackerRepository articleViesTrackerRepository;

    public ArticleDTO create(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setDescription(articleDTO.getDescription());
        article.setImage_id(articleDTO.getImage_id());
        article.setRegion_id(articleDTO.getRegion_id());
        article.setCategory_id(articleDTO.getCategory_id());
        article.setShared_count(0);
        article.setViewCount(0);
        article.setStatus(ArticleStatus.NOT_PUBLISHED);
        article.setVisible(true);
        article.setCreatedDate(LocalDateTime.now());
        article.setModerator_id(SpringSecurityUtil.getCurrentUserId());
        articleRepository.save(article);

        articleTypesService.merge(article.getId(), articleDTO.getArticleTypesList());
        return articleDTO;
    }

    public ArticleDTO update(String id, ArticleDTO articleDTO) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (!optionalArticle.isPresent()) {
            throw new AppBadException("Article not found");
        }
        Article article = optionalArticle.get();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setDescription(articleDTO.getDescription());
        article.setImage_id(articleDTO.getImage_id());
        article.setRegion_id(articleDTO.getRegion_id());
        article.setCategory_id(articleDTO.getCategory_id());
        article.setShared_count(0);
        article.setModerator_id(SpringSecurityUtil.getCurrentUserId());
        article.setVisible(true);
        if (article.getStatus() == ArticleStatus.PUBLISHED) {
            article.setStatus(ArticleStatus.NOT_PUBLISHED);
        }
        articleRepository.save(article);
        return articleDTO;
    }

    public Boolean delete(String id){
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            articleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ArticleDTO> getAll() {
        List<ArticleDTO> articleDTOList = new LinkedList<>();
        for (Article article : articleRepository.findAll()) {
            ArticleDTO dto = toDTO(article);
            articleDTOList.add(dto);
        }
        return articleDTOList;
    }

    public ArticleDTO toDTO(Article article) {
        /*ArticleDTO articleDTO = new ArticleDTO();

        articleDTO.setCreatedDate(LocalDateTime.now());
        articleDTO.setContent(article.getContent());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setDescription(article.getDescription());
        articleDTO.setModerator_id(article.getModerator_id());
        articleDTO.setShared_count(0);
      *//*  articleDTO.setImage_id(article.getImage_id());*//*
        articleDTO.setRegion_id(article.getRegion_id());
        articleDTO.setCategory_id(article.getCategory_id());
        articleDTO.setModerator_id(article.getModerator_id());
        articleDTO.setPublisher_id(article.getPublisher_id());
        articleDTO.setStatus(article.getStatus());
        articleDTO.setPublishedDate(article.getPublishedDate());
        articleDTO.setVisible(article.getVisible());
        articleDTO.setViewCount(article.getViewCount());

        articleDTO.setId(article.getId());*/
        return null;
    }

    public Boolean changeStatus(String id, Boolean statusPublish) {
        Optional<Article> byId = articleRepository.findById(id);
        if (!byId.isPresent()) {
            throw new AppBadException("Article not found");
        }
        Article article = byId.get();
        if (statusPublish) {
            article.setStatus(ArticleStatus.PUBLISHED);
                            }
        if (!statusPublish){
            article.setStatus(ArticleStatus.NOT_PUBLISHED);
        }

        articleRepository.save(article);
        return true;
    }



    public List<ArticleShortInfo> getLast5ArticlesByType(String id, int n) {

        List<String> listArticleId = articleTypesService.getArticleId(id, n);
            return articleRepository.findTop5ArticlesById(listArticleId)
                    .stream()
                    .map(this::convertToShortInfo)
                    .collect(Collectors.toList());
        }

        // 7. Get Last 8 Articles excluding given
    public List<ArticleShortInfo> getLast8ArticlesExcludingIds(List<String> excludeIds) {
        return articleRepository.findTop8ByIdNotInAndVisibleIsTrueOrderByCreatedDateDesc(excludeIds)
                .stream()
                .map(this::convertToShortInfo)
                .collect(Collectors.toList());
    }


    // 16. Increase Article View Count by Article ID
    public void incrementViewCount(String id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
        article.setViewCount(article.getViewCount() + 1);
        articleRepository.save(article);
    }

    public void increaseArticleViewCount(String articleId, String deviceId) {
        boolean hasViewed = articleViesTrackerRepository.existsByArticleIdAndDeviceId(articleId, deviceId);

        if (!hasViewed) {
            // Increment view count
            Article article = articleRepository.findById(articleId).orElseThrow();
            article.setViewCount(article.getViewCount() + 1);
            articleRepository.save(article);

            // Save to tracker
            ArticleViewTracker tracker = new ArticleViewTracker();
            tracker.setArticleId(articleId);
            tracker.setDeviceId(deviceId);
            tracker.setViewDate(LocalDateTime.now());
            articleViesTrackerRepository.save(tracker);
        }
    }

/*
    public List<ArticleShortInfo> getLast3ArticlesByType(String type, Pageable pageable) {
        return articleRepository.findTop3ByArticleTypeAndVisibleIsTrueOrderByCreatedDateDesc(type, pageable)
                .stream()
                .map(this::convertToShortInfo)
                .collect(Collectors.toList());
    }



    // 8. Get Article by ID and Language
    public ArticleFullInfo getArticleByIdAndLang(String id, String lang) {
        Article article = articleRepository.findByIdAndVisibleIsTrue(id);
        // Convert based on language
        return convertToFullInfo(article, lang);
    }

    // 9. Get Last 4 Articles by Type excluding specific article ID
    public List<ArticleShortInfo> getLast4ArticlesByTypeExcludingId(String type, String excludeId) {
        return articleRepository.findTop4ByArticleTypeAndIdNotAndVisibleIsTrueOrderByCreatedDateDesc(type, excludeId)
                .stream()
                .map(this::convertToShortInfo)
                .collect(Collectors.toList());
    }

    // 10. Get 4 most read articlesUUID

    public List<ArticleShortInfo> getTopReadArticles() {
        return articleRepository.findTop4ByVisibleIsTrueOrderByViewCountDesc()
                .stream()
                .map(this::convertToShortInfo)
                .collect(Collectors.toList());
    }

    // 11. Get Last 4 Articles By TagName
    public List<ArticleShortInfo> getLast4ArticlesByTag(String tagName) {
        return articleRepository.findTop4ByTags_NameAndVisibleIsTrueOrderByCreatedDateDesc(tagName)
                .stream()
                .map(this::convertToShortInfo)
                .collect(Collectors.toList());
    }

    // 12. Get Last 5 Articles By Type And Region Key
    public List<ArticleShortInfo> getLast5ArticlesByTypeAndRegion(String type, Integer regionKey) {
        return articleRepository.findTop5ByArticleTypeAndRegionIdAndVisibleIsTrueOrderByCreatedDateDesc(type, regionKey)
                .stream()
                .map(this::convertToShortInfo)
                .collect(Collectors.toList());
    }

    // 13. Get Article list by Region Key with Pagination
    public List<ArticleShortInfo> getArticlesByRegionKey(Integer regionKey, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findByRegionIdAndVisibleIsTrue(regionKey, pageable)
                .stream()
                .map(this::convertToShortInfo)
                .collect(Collectors.toList());
    }

    // 14. Get Last 5 Articles by Category Key
    public List<ArticleShortInfo> getLast5ArticlesByCategoryKey(Integer categoryKey) {
        return articleRepository.findTop5ByCategoryIdAndVisibleIsTrueOrderByCreatedDateDesc(categoryKey)
                .stream()
                .map(this::convertToShortInfo)
                .collect(Collectors.toList());
    }

    // 15. Get Articles by Category Key with Pagination
    public List<ArticleShortInfo> getArticlesByCategoryKey(Integer categoryKey, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findByCategoryIdAndVisibleIsTrue(categoryKey, pageable)
                .stream()
                .map(this::convertToShortInfo)
                .collect(Collectors.toList());
    }



    // 17. Increase Share View Count by Article ID
    public void incrementShareCount(String id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
        article.setShared_count(article.getShared_count() + 1);
        articleRepository.save(article);
    }

    // 18. Filter Article with Pagination (PUBLISHER)
    public List<ArticleShortInfo> filterArticles(String id, String title, Integer regionId, Integer categoryId,
                                                 String createdDateFrom, String createdDateTo,
                                                 String publishedDateFrom, String publishedDateTo,
                                                 Integer moderatorId, Integer publisherId,
                                                 String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        LocalDateTime createdFrom = createdDateFrom != null ? LocalDateTime.parse(createdDateFrom) : null;
        LocalDateTime createdTo = createdDateTo != null ? LocalDateTime.parse(createdDateTo) : null;
        LocalDateTime publishedFrom = publishedDateFrom != null ? LocalDateTime.parse(publishedDateFrom) : null;
        LocalDateTime publishedTo = publishedDateTo != null ? LocalDateTime.parse(publishedDateTo) : null;

        // Assuming you have a method in your repository for filtering with these fields
        return articleRepository.filterArticles(id, title, regionId, categoryId, createdFrom, createdTo,
                        publishedFrom, publishedTo, moderatorId, publisherId,
                        status, pageable)
                .stream()
                .map(this::convertToShortInfo)
                .collect(Collectors.toList());
    }



    private ArticleFullInfo convertToFullInfo(Article article, String lang) {
        ArticleFullInfo info = new ArticleFullInfo();
        info.setId(article.getId());
        info.setTitle(article.getTitle());
        info.setContent(article.getContent());
        info.setCreatedDate(article.getCreatedDate());
        info.setDescription(article.getDescription());
        return info;
    }

    public ArticleShortInfo toShortInfo(ArticleShortInfoMapper mapper) {
        ArticleShortInfo dto = new ArticleShortInfo();
        dto.setId(mapper.getId());
        dto.setTitle(mapper.getTitle());
        dto.setDescription(mapper.getDescription());
        dto.setPublishedDate(mapper.getPublishDate());
        dto.setImage(attachService.getDTO(mapper.getImageId()));
        return dto;
    }

    public List<ArticleShortInfo>*/

    /*public void merge()*/

    private ArticleShortInfo convertToShortInfo(Article article) {
        ArticleShortInfo info = new ArticleShortInfo();
        info.setId(article.getId());
        info.setTitle(article.getTitle());
        info.setDescription(article.getDescription());
        info.setPublishedDate(article.getPublishedDate());
        info.setImage(attachService.getUrl(article.getImage_id()));
        return info;
    }
}
