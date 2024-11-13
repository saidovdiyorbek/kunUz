package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.repository.ArticleTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTagService {
    @Autowired
    ArticleTagRepository articleTagRepository;

    public List<String> getByTagName(String tagName) {
        return articleTagRepository.findTop4ByTag(tagName);
    }
}
