package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.entity.ArticleDislike;
import dasturlash.uz.kun_uz.entity.ArticleLike;
import dasturlash.uz.kun_uz.repository.ArticleDislikeRepository;
import dasturlash.uz.kun_uz.repository.ArticleLikeRepository;
import dasturlash.uz.kun_uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    @Autowired
    private ArticleDislikeRepository articleDislikeRepository;


    public String isLike(String articleId) {
        boolean b = articleLikeRepository.existsByArticleIdAndProfileId(articleId, SpringSecurityUtil.getCurrentUserId());
        if (b) {
            unLike(articleId);
            return "Like olindi";}
        like(articleId);
        return "Like bosildi";
    }

    public void unLike(String articleId) {
        articleLikeRepository.deleteByArticleIdAndProfileId(articleId, SpringSecurityUtil.getCurrentUserId());
    }
    public void like(String articleId) {
        articleLikeRepository.save(new ArticleLike(articleId, SpringSecurityUtil.getCurrentUserId()));
    }

    public String isDisLike(String id) {
        boolean b = articleDislikeRepository.existsByArticleIdAndProfileId(id, SpringSecurityUtil.getCurrentUserId());
        if (b) {
            articleDislikeRepository.deleteByArticleIdAndProfileId(id, SpringSecurityUtil.getCurrentUserId());
            return "Dislike olindi";
        }
        articleDislikeRepository.save(new ArticleDislike(id, SpringSecurityUtil.getCurrentUserId()));
        return "Dislike bosildi";
    }
}
