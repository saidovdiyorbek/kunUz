package dasturlash.uz.kun_uz.controller;

import dasturlash.uz.kun_uz.dto.article.ArticleDTO;
import dasturlash.uz.kun_uz.dto.article.ArticleFullInfo;
import dasturlash.uz.kun_uz.dto.article.ArticleShortInfo;
import dasturlash.uz.kun_uz.enums.Language;
import dasturlash.uz.kun_uz.service.ArticleService;
import dasturlash.uz.kun_uz.util.HeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {
        return ResponseEntity.ok(articleService.create(articleDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ArticleDTO> update(@PathVariable String id,
                                             @RequestBody ArticleDTO articleDTO) {
        return ResponseEntity.ok(articleService.update(id, articleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteArticle(@PathVariable String id) {
        return ResponseEntity.ok(articleService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAll());
    }

    @GetMapping("/changeStatus/{id}/{statusPublish}")
    public ResponseEntity<Boolean> changeStatus(@PathVariable String id,
                                                @PathVariable Boolean statusPublish) {
        return ResponseEntity.ok(articleService.changeStatus(id,statusPublish));
    }

    // 5. Get Last 5 Published Articles By Type
    @GetMapping("/{type}/{n}")
    public ResponseEntity<List<ArticleShortInfo>> getTop5Articles(@PathVariable String type, @PathVariable int n) {
        return ResponseEntity.ok().body(articleService.getLast5ArticlesByType(type, n));
    }

    // 6. Get Last 3 Published Articles By Type
    @GetMapping("/{typeId}/{n}")
    public ResponseEntity<List<ArticleShortInfo>> getLast3ByType(@PathVariable String typeId, @PathVariable int n) {
        return ResponseEntity.ok().body(articleService.getLast5ArticlesByType(typeId, n));
    }

    // 7. Get Last 8 Articles excluding given IDs
    @GetMapping("/last8/exclude")
    public ResponseEntity<List<ArticleShortInfo>>getLast8ExcludingIds(@RequestParam List<String> excludeIds) {
        return ResponseEntity.ok(articleService.getLast8ArticlesExcludingIds(excludeIds));
    }

//    // 8. Get Article by ID and Language
    @GetMapping("/{id}/lang")
    public ResponseEntity<ArticleFullInfo> getArticleByIdAndLang(@PathVariable String id, @RequestParam String lang) {
        Language language = Language.valueOf(lang.toUpperCase());
        return ResponseEntity.ok(articleService.getArticleByIdAndLang(id, language));
    }


    // 9. Get Last 4 Articles by Type excluding specific article ID
    @GetMapping("/last4/type/{type}/exclude/{excludeId}")
    public ResponseEntity<List<ArticleShortInfo>> getLast4ByTypeExcludingId(@PathVariable String type, @PathVariable String excludeId) {
        return ResponseEntity.ok(articleService.getLast4ArticlesByTypeExcludingId(type, excludeId));
    }

    // 10. Get 4 most read articles
    @GetMapping("/top-read")
    public ResponseEntity<List<ArticleShortInfo>> getTopReadArticles() {
        return ResponseEntity.ok(articleService.getTopReadArticles());
    }

    // 11. Get Last 4 Articles By TagName
    @GetMapping("/last4/tag/{tagName}")
    public ResponseEntity<List<ArticleShortInfo>> getLast4ByTag(@PathVariable String tagName) {
        return ResponseEntity.ok(articleService.getLast4ArticlesByTag(tagName));
    }

/* @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> byId(@PathVariable("id") String id, HttpServletRequest request) {
        return ResponseEntity.ok(articleService.getById(id, HeaderUtil.getUserIP(request)));
    }*/

    /*







    // 12. Get Last 5 Articles By Type And Region Key
    @GetMapping("/last5/type/{type}/region/{regionKey}")
    public ResponseEntity<List<ArticleShortInfo>> getLast5ByTypeAndRegion(@PathVariable String type, @PathVariable Integer regionKey) {
        return ResponseEntity.ok(articleService.getLast5ArticlesByTypeAndRegion(type, regionKey));
    }

    // 13. Get Article list by Region Key with Pagination
    @GetMapping("/region/{regionKey}")
    public ResponseEntity<List<ArticleShortInfo>> getArticlesByRegionKey(@PathVariable Integer regionKey,
                                                                         @RequestParam int page,
                                                                         @RequestParam int size) {
        return ResponseEntity.ok(articleService.getArticlesByRegionKey(regionKey, page, size));
    }

    // 14. Get Last 5 Articles by Category Key
    @GetMapping("/last5/category/{categoryKey}")
    public ResponseEntity<List<ArticleShortInfo>> getLast5ByCategoryKey(@PathVariable Integer categoryKey) {
        return ResponseEntity.ok(articleService.getLast5ArticlesByCategoryKey(categoryKey));
    }

    // 15. Get Articles by Category Key with Pagination
    @GetMapping("/category/{categoryKey}")
    public ResponseEntity<List<ArticleShortInfo>> getArticlesByCategoryKey(@PathVariable Integer categoryKey,
                                                                           @RequestParam int page,
                                                                           @RequestParam int size) {
        return ResponseEntity.ok(articleService.getArticlesByCategoryKey(categoryKey, page, size));
    }

    // 16. Increase Article View Count by Article ID
    @PostMapping("/{id}/increment-view")
    public ResponseEntity<?> incrementArticleViewCount(@PathVariable String id) {
        articleService.incrementViewCount(id);
        return ResponseEntity.ok("View count incremented successfully");
    }

    // 17. Increase Share View Count by Article ID
    @PostMapping("/{id}/increment-share")
    public ResponseEntity<?> incrementShareViewCount(@PathVariable String id) {
        articleService.incrementShareCount(id);
        return ResponseEntity.ok("Share count incremented successfully");
    }

    // 18. Filter Article with Pagination (PUBLISHER)
    @GetMapping("/filter")
    public ResponseEntity<List<ArticleShortInfo>> filterArticles(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer regionId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String createdDateFrom,
            @RequestParam(required = false) String createdDateTo,
            @RequestParam(required = false) String publishedDateFrom,
            @RequestParam(required = false) String publishedDateTo,
            @RequestParam(required = false) Integer moderatorId,
            @RequestParam(required = false) Integer publisherId,
            @RequestParam(required = false) String status,
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(articleService.filterArticles(id,
                title, regionId, categoryId, createdDateFrom,
                createdDateTo, publishedDateFrom, publishedDateTo,
                moderatorId, publisherId, status, page, size));
    }*/

}
