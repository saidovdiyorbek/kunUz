package dasturlash.uz.kun_uz.controller;

import dasturlash.uz.kun_uz.dto.ArticleDTO;
import dasturlash.uz.kun_uz.service.ArticleService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService service;

    @PostMapping("")
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {
        return ResponseEntity.ok(service.create(articleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteArticle(@PathVariable UUID id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}/")
    public ResponseEntity<Boolean> changeStatus(@PathVariable UUID id,
                                                @RequestParam String statusPublish) {
        return ResponseEntity.ok(service.changeStatus(id,statusPublish));
    }
}
