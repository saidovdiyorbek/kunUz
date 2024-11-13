package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.entity.ArticleTypes;
import dasturlash.uz.kun_uz.repository.ArticleTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleTypesService {
    @Autowired
    private ArticleTypesRepository articleTypesRepository;

    public List<String> getArticleId(String id, int n){
        Pageable pageable = PageRequest.of(0, n);
        List<String> list = articleTypesRepository.findTop5ByArticleTypeIdOrderByIdDesc(id, pageable);
        return list;
    }




    public void merge(String articleId, List<Integer> newIdList) {
        List<Integer> oldIdList = articleTypesRepository.findAllByArticleId(articleId);

        if (newIdList == null) {
            newIdList = new ArrayList<>();
        }
        for (Integer attachId : oldIdList) {
            if (!newIdList.contains(attachId)) {
                // delete operation {attachId}
                articleTypesRepository.deleteByArticleIdAndArticleTypeId(articleId, attachId);
            }
        }
        for (Integer newItemId : newIdList) {
            if (!oldIdList.contains(newItemId)) {
                // save
                ArticleTypes entity = new ArticleTypes();
                entity.setArticleId(articleId);
                entity.setArticleTypeId(newItemId);
                articleTypesRepository.save(entity);
            }
        }
    }
}
