package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.dto.ArticleCommentDTO;
import dasturlash.uz.kun_uz.entity.ArticleComment;
import dasturlash.uz.kun_uz.repository.ArticleCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class ArticleCommentService {
    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    public ArticleCommentDTO addComment(ArticleCommentDTO dto) {
        ArticleComment articleComment = new ArticleComment();

        articleComment.setArticleId(dto.getArticleId());
        articleComment.setProfileId(dto.getProfileId());
        articleComment.setComment(dto.getComment());
        articleComment.setReplyId(dto.getReplyId());
        articleComment.setCreateTime(LocalDateTime.now());
        articleCommentRepository.save(articleComment);
        dto.setId(articleComment.getId());
        return dto;
    }
}
