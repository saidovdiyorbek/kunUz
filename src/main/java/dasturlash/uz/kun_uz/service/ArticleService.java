package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.dto.ArticleDTO;
import dasturlash.uz.kun_uz.entity.Article;
import dasturlash.uz.kun_uz.entity.Category;
import dasturlash.uz.kun_uz.entity.Profile;
import dasturlash.uz.kun_uz.entity.Region;
import dasturlash.uz.kun_uz.exp.AppBadException;
import dasturlash.uz.kun_uz.repository.ArticleRepository;
import dasturlash.uz.kun_uz.repository.CategoryRepository;
import dasturlash.uz.kun_uz.repository.ProfileRepository;
import dasturlash.uz.kun_uz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository repository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RegionRepository regionRepository;

    public ArticleDTO create(ArticleDTO articleDTO) {
        Optional<Profile> optionalProfile = profileRepository.findById(articleDTO.getModerator_id());
        if (!optionalProfile.isPresent()) {
            throw new AppBadException("Moderator not found");
        }
        Optional<Category> optionalCategory = categoryRepository.findById(articleDTO.getCategory_id());
        if (!optionalProfile.isPresent()) {
            throw new AppBadException("Category not found");
        }
        Optional<Region> optionalRegion = regionRepository.findById(articleDTO.getRegion_id());
        if (!optionalProfile.isPresent()) {
            throw new AppBadException("Region not found");
        }
        Optional<Profile> optionalProfile1 = profileRepository.findById(articleDTO.getPublisher_id());
        if (!optionalProfile1.isPresent()) {
            throw new AppBadException("Region not found");
        }


        Article article = new Article();
        article.setCreatedDate(LocalDateTime.now());
            article.setContent(articleDTO.getContent());
            article.setTitle(articleDTO.getTitle());
            article.setDescription(articleDTO.getDescription());
        article.setModerator_id(optionalProfile.get().getId());
        article.setShared_count(0);
            article.setImage_id(articleDTO.getImage_id());
            article.setRegion_id(optionalRegion.get().getId());
            article.setCategory_id(optionalCategory.get().getId());
        article.setModerator_id(optionalProfile.get().getId());
        article.setPublisher_id(optionalProfile1.get().getId());
        article.setStatus("Not Published");
        article.setPublishedDate(null);
        article.setVisible(false);
        article.setViewCount(0);
        repository.save(article);
        articleDTO.setId(article.getId());
        return articleDTO;
    }

    public Boolean delete(UUID id){
        Optional<Article> optionalArticle = repository.findById(id);
        if (optionalArticle.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ArticleDTO> getAll() {
        List<ArticleDTO> articleDTOList = new LinkedList<>();
        for (Article article : repository.findAll()) {
            ArticleDTO dto = toDTO(article);
            articleDTOList.add(dto);
        }
        return articleDTOList;
    }

    public ArticleDTO toDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();

        articleDTO.setCreatedDate(LocalDateTime.now());
        articleDTO.setContent(article.getContent());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setDescription(article.getDescription());
        articleDTO.setModerator_id(article.getModerator_id());
        articleDTO.setShared_count(0);
        articleDTO.setImage_id(article.getImage_id());
        articleDTO.setRegion_id(article.getRegion_id());
        articleDTO.setCategory_id(article.getCategory_id());
        articleDTO.setModerator_id(article.getModerator_id());
        articleDTO.setPublisher_id(article.getPublisher_id());
        articleDTO.setStatus(article.getStatus());
        articleDTO.setPublishedDate(article.getPublishedDate());
        articleDTO.setVisible(article.getVisible());
        articleDTO.setViewCount(article.getViewCount());

        articleDTO.setId(article.getId());
        return articleDTO;
    }

    public Boolean changeStatus(UUID id, String statusPublish) {
        Optional<Article> byId = repository.findById(id);
        if (!byId.isPresent()) {
            throw new AppBadException("Article not found");
        }
        Article article = byId.get();
        article.setStatus(statusPublish);
        repository.save(article);
        return true;
    }
}
