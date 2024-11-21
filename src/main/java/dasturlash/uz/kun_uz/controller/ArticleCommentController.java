package dasturlash.uz.kun_uz.controller;

import dasturlash.uz.kun_uz.dto.ArticleCommentDTO;
import dasturlash.uz.kun_uz.entity.ArticleComment;
import dasturlash.uz.kun_uz.service.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class ArticleCommentController {
    @Autowired
    ArticleCommentService articleCommentService;

    @PostMapping("/write")
    public ResponseEntity<ArticleCommentDTO> writeArticleComment(@RequestBody ArticleCommentDTO articleCommentDTO) {
        return ResponseEntity.ok(articleCommentService.addComment(articleCommentDTO));
    }
}
